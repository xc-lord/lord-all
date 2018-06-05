package com.lord.biz.service.sys;

import com.lord.biz.dao.sys.SysDistrictDao;
import com.lord.biz.dao.sys.specs.SysDistrictSpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.sys.DistrictDto;
import com.lord.common.model.sys.SysDistrict;
import com.lord.common.service.sys.SysDistrictService;
import com.lord.utils.IdGenerator;
import com.lord.utils.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 行政区域sys_district的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年04月24日 15:04:09
 */
@Component
public class SysDistrictServiceImpl implements SysDistrictService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysDistrictDao sysDistrictDao;

    @Override
    public SysDistrict getSysDistrict(Long id) {
        return sysDistrictDao.findOne(id);
    }

    @Override
    @Transactional
    public SysDistrict saveOrUpdate(SysDistrict pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");
        Preconditions.checkNotNull(pageObj.getParentId(), "父区域ID不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);
        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性
            pageObj.setId(IdGenerator.nextId());//主键ID
            pageObj.setUpdateTime(new Date());
            pageObj.setCreateTime(new Date());

            sysDistrictDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        SysDistrict dbObj = sysDistrictDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段
        pageObj.setUpdateTime(new Date());//更新时间
        pageObj.setCreateTime(dbObj.getCreateTime());

        sysDistrictDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<SysDistrict> pageSysDistrict(SysDistrict param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageSysDistrict(param, pagerParam);

    }

    @Override
    public Pager<SysDistrict> pageSysDistrict(SysDistrict param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<SysDistrict> pageResult = sysDistrictDao.findAll(SysDistrictSpecs.queryBySysDistrict(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<SysDistrict> pageSysDistrict(SysDistrict param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<SysDistrict> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            SysDistrict obj = sysDistrictDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = sysDistrictDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = sysDistrictDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteSysDistrict(Long... ids) {
        sysDistrictDao.deleteSysDistrict(ids);
    }


    @Override
    @Transactional
    public void updateOrderValue(Long id, Long orderValue) {
        SysDistrict dbObj = sysDistrictDao.findOne(id);
        Preconditions.checkNotNull(dbObj, "更新排序的记录不存在");
        if (orderValue.equals(dbObj.getOrderValue())) {
            return;
        }
        sysDistrictDao.updateOrderValue(id, orderValue);
    }

    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<SysDistrict> list = sysDistrictDao.findAll(SysDistrictSpecs.queryBy(rowName, rowValue, SysDistrict.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        SysDistrict dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }

    @Override
    public List<SysDistrict> listParentDistrict(Long id)
    {
        List<SysDistrict> list = new ArrayList<>();
        SysDistrict district = sysDistrictDao.findOne(id);
        if(district == null)
        {
            return list;
        }
        list.add(district);
        addParentDistrict(list, district);
        Collections.reverse(list);
        return list;
    }

    @Override
    public List<DistrictDto> listChildrenDistrict(Long parentId)
    {
        List<SysDistrict> list = sysDistrictDao.findByParentId(parentId);
        List<DistrictDto> dtoList = new ArrayList<>();
        for (SysDistrict district : list)
        {
            DistrictDto dto = parseDistrictDto(district);
            dtoList.add(dto);
        }
        return dtoList;
    }

    private DistrictDto parseDistrictDto(SysDistrict district)
    {
        DistrictDto dto = new DistrictDto();
        dto.setId(district.getId());
        dto.setName(district.getName());
        return dto;
    }

    @Override
    public List<DistrictDto> getDistrict(Long provinceId, Long cityId, Long countyId, Long townId)
    {
        List<DistrictDto> list = listChildrenDistrict(100000L);
        if(provinceId != null) {
            for (DistrictDto provinceDto : list)
            {
                if(!provinceDto.getId().equals(provinceId) || cityId == null) continue;
                List<DistrictDto> provinceChildren = listChildrenDistrict(provinceId);
                for (DistrictDto cityDto : provinceChildren)
                {
                    if (!cityDto.getId().equals(cityId) || countyId == null) continue;
                    List<DistrictDto> cityChildren = listChildrenDistrict(cityId);
                    for (DistrictDto countyDto : cityChildren)
                    {
                        if (!countyDto.getId().equals(countyId) || townId == null) continue;
                        List<DistrictDto> countyChildren = listChildrenDistrict(countyId);
                        countyDto.setChildren(countyChildren);
                    }
                    cityDto.setChildren(cityChildren);
                }
                provinceDto.setChildren(provinceChildren);

            }
        }
        return list;
    }

    private void addParentDistrict(List<SysDistrict> list, SysDistrict district)
    {
        if(district.getParentId() == null)
            return;
        SysDistrict parent = sysDistrictDao.findOne(district.getParentId());
        if(parent == null)
            return;
        list.add(parent);
        addParentDistrict(list, parent);
    }
}