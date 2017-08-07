package com.lord.biz.service.ads;

import com.lord.biz.dao.ads.AdsElementDao;
import com.lord.biz.dao.ads.AdsSpaceDao;
import com.lord.biz.dao.ads.specs.AdsElementSpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.constant.ads.AdsElementType;
import com.lord.common.constant.ads.AdsSpaceType;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.model.ads.AdsElement;
import com.lord.common.model.ads.AdsSpace;
import com.lord.common.service.RedisService;
import com.lord.common.service.ads.AdsElementService;
import com.lord.common.service.ads.AdsTemplateService;
import com.lord.common.service.cms.CmsArticleService;
import com.lord.utils.CommonUtils;
import com.lord.utils.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 广告位的元素ads_element的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月03日 16:18:22
 */
@Component
public class AdsElementServiceImpl implements AdsElementService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static Map<String, List<String>> elementDataMap = new HashMap<>();

    @Autowired
    private AdsElementDao adsElementDao;

    @Autowired
    private AdsSpaceDao adsSpaceDao;

    @Autowired
    private CmsArticleService cmsArticleService;

    @Autowired
    private RedisService redisService;

    @Override
    public AdsElement getAdsElement(Long id) {
        return adsElementDao.findOne(id);
    }

    @Override
    @Transactional
    public AdsElement saveOrUpdate(AdsElement pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");
        Preconditions.checkArgument(pageObj.getSpaceId() == null, "广告位Id不能为空");
        Preconditions.checkArgument(StringUtils.isEmpty(pageObj.getTargetType()), "类型不能为空");
        Preconditions.checkArgument(StringUtils.isEmpty(pageObj.getName()), "名称不能为空");

        AdsSpace adsSpace = adsSpaceDao.findOne(pageObj.getSpaceId());
        Preconditions.checkArgument(adsSpace == null, "广告位不存在");
        pageObj.setKeyword(adsSpace.getKeyword());
        pageObj.setPageId(adsSpace.getPageId());

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);

        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性
            pageObj.setCreateTime(new Date());
            pageObj.setUpdateTime(new Date());

            adsElementDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        AdsElement dbObj = adsElementDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段
        pageObj.setCreateTime(dbObj.getCreateTime());
        pageObj.setUpdateTime(new Date());//更新时间

        adsElementDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<AdsElement> pageAdsElement(AdsElement param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageAdsElement(param, pagerParam);

    }

    @Override
    public Pager<AdsElement> pageAdsElement(AdsElement param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<AdsElement> pageResult = adsElementDao.findAll(AdsElementSpecs.queryByAdsElement(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<AdsElement> pageAdsElement(AdsElement param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<AdsElement> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            AdsElement obj = adsElementDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = adsElementDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = adsElementDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteAdsElement(Long... ids)
    {
        List<AdsElement> list = adsElementDao.findByIds(ids);
        Map<Long, Boolean> map = new HashMap<>();
        for (AdsElement adsElement : list)
        {
            map.put(adsElement.getSpaceId(), true);
        }
        adsElementDao.deleteAdsElement(ids);
        for (Long spaceId : map.keySet())
        {
            redisService.expire(AdsTemplateService.ADS_ALL_SPACE + spaceId, 3000L);//更新广告位的缓存
        }
    }

    @Override
    @Transactional
    public void updateOrderValue(Long id, Long orderValue) {
        AdsElement dbObj = adsElementDao.findOne(id);
        Preconditions.checkNotNull(dbObj, "更新排序的记录不存在");
        if (orderValue.equals(dbObj.getOrderValue())) {
            return;
        }
        adsElementDao.updateOrderValue(id, orderValue);
        redisService.expire(AdsTemplateService.ADS_ALL_SPACE + dbObj.getSpaceId(), 3000L);//更新广告位的缓存
    }

    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<AdsElement> list = adsElementDao.findAll(AdsElementSpecs.queryBy(rowName, rowValue, AdsElement.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        AdsElement dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }

    @Override
    public void batchCreateElement(AdsSpace adsSpace)
    {
        if(adsSpace == null || AdsSpaceType.Model.toString().equals(adsSpace.getAdsType()) || adsSpace.getAdsNum() == null)
            return;
        Date now = new Date();
        List<AdsElement> adsElementList = adsElementDao.listEffectElement(adsSpace.getId(), now);
        if(CollectionUtils.isNotEmpty(adsElementList))
        {
            logger.info("广告位" + adsSpace.getId() + "的元素数据已经存在不需要再导入");
            return;
        }
        List<AdsElement> adsElements = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 3);
        for (int i = 0; i < adsSpace.getAdsNum(); i++)
        {
            String name = adsSpace.getName() + "_" + (i+1) + "_";
            AdsElement adsElement = new AdsElement();
            adsElement.setSpaceId(adsSpace.getId());
            adsElement.setKeyword(adsSpace.getKeyword());
            adsElement.setPageId(adsSpace.getPageId());
            adsElement.setTargetType(adsSpace.getAdsType());
            adsElement.setUpdateTime(now);
            adsElement.setCreateTime(now);
            adsElement.setStartTime(now);
            adsElement.setEndTime(calendar.getTime());
            adsElement.setFouce(false);
            adsElement.setOrderValue(i + 1L);
            adsElement.setName(name + "名称");
            adsElement.setTitle(name + "标题");
            adsElement.setSubTitle(name + "子标题");
            putElementInfo(adsElement, adsSpace, name);//设置元素类型对应的数据
            adsElements.add(adsElement);
        }
        adsElementDao.save(adsElements);//批量保存
    }

    private void putElementInfo(AdsElement adsElement, AdsSpace adsSpace, String name)
    {
        String adsType = adsSpace.getAdsType();
        if (AdsElementType.BigText.toString().equals(adsType))
        {
            adsElement.setTex("<a>" + name + "大文本内容</a>");
        } else if (AdsElementType.TextLink.toString().equals(adsType)) {
            adsElement.setAdsUrl(randomData(AdsElementType.TextLink.toString()));//链接
        } else if (AdsElementType.ImageLink.toString().equals(adsType)) {
            adsElement.setAdsImg(randomData(AdsElementType.ImageLink.toString()));//图片
            adsElement.setAdsUrl(randomData(AdsElementType.TextLink.toString()));//链接
        } else {
            adsElement.setTargetId(randomData(adsType));
        }
    }

    private String randomData(String type)
    {
        List<String> list = elementDataMap.get(type);
        if(CollectionUtils.isEmpty(list))
        {
            list = new ArrayList<>();
            if (AdsElementType.TextLink.toString().equals(type)) {
                //链接
                list.add("http://blog.csdn.net/x_lord");
                list.add("http://www.linshimuye.com/");
                list.add("http://www.baidu.com/");
                list.add("http://www.jd.com/");
                list.add("http://www.tmall.com/");
                list.add("http://www.qq.com/");
                list.add("http://www.163.com/");
                list.add("http://www.gome.com.cn/");
                list.add("http://m.jd.com/");
                list.add("http://m.tmall.com/");
                list.add("http://m.qq.com/");
                list.add("http://m.163.com/");
                list.add("http://m.gome.com.cn/");
                list.add("http://m.baidu.com/");
                list.add("http://m.linshimuye.com/");
            }
            else if (AdsElementType.ImageLink.toString().equals(type)) {
                for (int i = 0; i < 22; i++)
                {
                    list.add("/image/2017/08/05/" + CommonUtils.fillZero(i + 1, 2) + ".jpg");//图片
                }
            }
            else if (AdsElementType.Article.toString().equals(type))
            {
                List<String> ids = cmsArticleService.listArticleIds(1, 10);//文章Id
                list.addAll(ids);
            }
            elementDataMap.put(type, list);
        }
        return list.get(CommonUtils.genRandom(0, list.size() - 1));
    }
}