package com.lord.biz.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lord.biz.BizApplication;
import com.lord.biz.dao.sys.SysDistrictDao;
import com.lord.common.model.sys.SysDistrict;
import com.lord.utils.CommonUtils;
import com.lord.utils.HttpUtils;
import com.lord.utils.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.*;

/**
 * 功能：测试行政区域
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年04月24日 11:55
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BizApplication.class)
public class TestSysDistrictDao
{
    @Autowired
    private SysDistrictDao sysDistrictDao;

    @Autowired
    private DbSqlDao dbSqlDao;

    @Test
    public void testReadJson() {
        System.out.println("读取高德地图的行政区域接口：https://lbs.amap.com/api/webservice/guide/api/district");

        String url = "http://restapi.amap.com/v3/config/district";
        Map<String, String> params = new HashMap<>();
        params.put("key", "f4bc691c44ae54606c6623f68037c64f");
        params.put("subdistrict", "1");
        String provinceStr = HttpUtils.doGet(url, params);
        System.out.println(provinceStr);
        Preconditions.checkNotNull(provinceStr);

        JSONObject provinceJson = JSON.parseObject(provinceStr);
        JSONArray districts = provinceJson.getJSONArray("districts");
        Preconditions.checkNotNull(districts, "区域为空");
        JSONObject disObj = districts.getJSONObject(0);
        printDistricts(disObj, "0");
        String province_adcode = disObj.getString("adcode");
        JSONArray children = disObj.getJSONArray("districts");
        for (int i = 0; i < children.size(); i++)
        {
            long start = System.currentTimeMillis();
            JSONObject province = children.getJSONObject(i);
            if(province == null) continue;
            String name = province.getString("name");
            String adcode = province.getString("adcode");
            printDistricts(province, province_adcode);

            params.put("keywords", name);
            params.put("subdistrict", "3");
            String cityStr = HttpUtils.doGet(url, params);
            if(StringUtils.isEmpty(cityStr))
                continue;
            JSONObject cityJson = JSON.parseObject(cityStr);
            JSONArray cityArr = cityJson.getJSONArray("districts");
            if(cityArr == null)
                continue;
            JSONObject city = cityArr.getJSONObject(0);
            String cityAdcode = city.getString("adcode");
            if (!adcode.equals(cityAdcode))
                continue;
            JSONArray cityChildren = city.getJSONArray("districts");
            readAllChildren(cityChildren, adcode);
            /*if (i >= 1)
            {
                break;
            }*/
            long end = System.currentTimeMillis();
            System.out.println("生成" + name + "的数据，用时：" + (end - start) + "ms");
        }
    }

    private void readAllChildren(JSONArray children, String parent_adcode)
    {
        if(children == null || children.size() < 1)
            return;
        for (int i = 0; i < children.size(); i++)
        {
            JSONObject disObj = children.getJSONObject(i);
            if(disObj == null) continue;
            String adcode = disObj.getString("adcode");
            if (parent_adcode.equals(adcode))
            {
                adcode += CommonUtils.fillZero(i, 3);
                disObj.put("id", adcode);
            }
            printDistricts(disObj, parent_adcode);
            JSONArray subChildren = disObj.getJSONArray("districts");
            readAllChildren(subChildren, adcode);
        }
    }

    private void printDistricts(JSONObject disObj, String parent_adcode)
    {
        if(disObj == null) return;
        String name = disObj.getString("name");
        String center = disObj.getString("center");
        String adcode = disObj.getString("adcode");
        String id = disObj.getString("id");
        if(StringUtils.isEmpty(id))
            id = adcode;
        String level = disObj.getString("level");
        String citycode = disObj.getString("citycode");
        //System.out.println(name + " " + adcode + " " + level + " " + parent_adcode);
        Double longitude = null;//经度
        Double latitude = null;//纬度
        if (StringUtils.isNotBlank(center))
        {
            String[] arr = center.split(",");
            longitude = Double.parseDouble(arr[0]);
            latitude = Double.parseDouble(arr[1]);
        }
        SysDistrict sysDistrict = new SysDistrict();
        sysDistrict.setAdcode(adcode);
        sysDistrict.setId(Long.parseLong(id));
        sysDistrict.setName(name);
        sysDistrict.setCreateTime(new Date());
        sysDistrict.setUpdateTime(new Date());
        sysDistrict.setOrderValue(Long.parseLong(id));
        sysDistrict.setParentId(Long.parseLong(parent_adcode));
        sysDistrict.setCitycode(citycode);
        sysDistrict.setLatitude(latitude);
        sysDistrict.setLongitude(longitude);
        sysDistrict.setLevel(level);

        Preconditions.checkArgument(sysDistrict.getId().equals(sysDistrict.getParentId()), "重复：" + adcode + " " + name);
        sysDistrictDao.save(sysDistrict);
    }

    @Test
    public void testCityArea()
    {
        Long parentId = 0L;
        readCityArea(parentId);
    }

    private void readCityArea(Long parentId)
    {
        String sql = "SELECT * FROM city_area WHERE pid=?";
        List<Map<String, Object>> list = dbSqlDao.select(sql, parentId);
        if (list == null || list.size() < 1)
        {
            return;
        }
        List<Long> ids = new ArrayList<>();
        for (Map<String, Object> map : list)
        {
            Integer id = (Integer) map.get("id");
            ids.add(id.longValue());
        }
        List<SysDistrict> districts = sysDistrictDao.findByIds(ids.toArray(new Long[ids.size()]));
        Map<Long, SysDistrict> districtMap = new HashMap<>();
        for (SysDistrict district : districts)
        {
            districtMap.put(district.getId(), district);
        }

        for (Map<String, Object> map : list)
        {
            System.out.println(map);
            Long id = ((Integer) map.get("id")).longValue();
            Long pid = ((Integer) map.get("pid")).longValue();
            String zipCode = (String) map.get("zipcode");
            String name = (String) map.get("name");
            SysDistrict sysDistrict = districtMap.get(id);
            if(sysDistrict == null) {
                sysDistrict = new SysDistrict();
                sysDistrict.setAdcode(id + "");
                sysDistrict.setId(id);
                sysDistrict.setName(name);
                sysDistrict.setZipCode(zipCode);
                sysDistrict.setCreateTime(new Date());
                sysDistrict.setUpdateTime(new Date());
                sysDistrict.setOrderValue(id);
                sysDistrict.setParentId(pid);
                //sysDistrict.setCitycode(citycode);
                //sysDistrict.setLatitude(latitude);
                //sysDistrict.setLongitude(longitude);
                //sysDistrict.setLevel(level);
                sysDistrictDao.save(sysDistrict);
            } else if (StringUtils.isNotEmpty(zipCode))
            {
                sysDistrict.setZipCode(zipCode);
                sysDistrict.setUpdateTime(new Date());
                sysDistrictDao.save(sysDistrict);
            }

            //递归调用
            readCityArea(id);
        }
    }

    @Test
    public void testRemove()
    {
        Long parentId = 100000L;
        List<SysDistrict> districts = sysDistrictDao.findByParentId(parentId);
        for (SysDistrict district : districts)
        {
            System.out.println("---------- " + district.getName());
            if (district.getId().equals(710000L))
            {
                continue;
            }
            removeChildren(parentId);

        }
    }

    private void removeChildren(Long parentId)
    {
        List<SysDistrict> list = sysDistrictDao.findByParentId(parentId);
        List<Long> ids = new ArrayList<>();
        for (SysDistrict sysDistrict : list)
        {
            Long id = sysDistrict.getId();
            if(StringUtils.isEmpty(sysDistrict.getCitycode()))
            {
                //System.out.println(id + " " + sysDistrict.getName());
                ids.add(id);
            }
            removeChildren(id);
        }
        if(ids.size() > 0)
        {
            System.out.println("删除了" + ids.size() + "条数据");
            sysDistrictDao.deleteSysDistrict(ids.toArray(new Long[ids.size()]));
        }

    }
}
