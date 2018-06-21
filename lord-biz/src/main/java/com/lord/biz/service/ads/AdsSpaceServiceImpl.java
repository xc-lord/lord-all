package com.lord.biz.service.ads;

import com.alibaba.fastjson.JSON;
import com.lord.biz.dao.ads.AdsElementDao;
import com.lord.biz.dao.ads.AdsPageDao;
import com.lord.biz.dao.ads.AdsSpaceDao;
import com.lord.biz.dao.ads.specs.AdsSpaceSpecs;
import com.lord.biz.service.cat.CategorySimpleServiceImpl;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.constant.ads.AdsElementType;
import com.lord.common.constant.ads.AdsSpaceType;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.ads.AdsArticle;
import com.lord.common.dto.ads.AdsSpaceInfo;
import com.lord.common.dto.cat.CategorySimple;
import com.lord.common.dto.cat.TreeNode;
import com.lord.common.model.ads.AdsElement;
import com.lord.common.model.ads.AdsPage;
import com.lord.common.model.ads.AdsSpace;
import com.lord.common.model.cms.CmsArticle;
import com.lord.common.service.RedisService;
import com.lord.common.service.ads.AdsSpaceService;
import com.lord.common.service.ads.AdsTemplateService;
import com.lord.common.service.cms.CmsArticleService;
import com.lord.utils.Preconditions;
import org.apache.commons.beanutils.BeanUtils;
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
import java.util.concurrent.TimeUnit;

/**
 * 广告位ads_space的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月03日 11:12:10
 */
@Component
public class AdsSpaceServiceImpl extends CategorySimpleServiceImpl implements AdsSpaceService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AdsSpaceDao adsSpaceDao;

    @Autowired
    private AdsElementDao adsElementDao;

    @Autowired
    private AdsPageDao adsPageDao;

    @Autowired
    private CmsArticleService cmsArticleService;

    @Autowired
    private RedisService redisService;

    @Override
    public AdsSpace getAdsSpace(Long id) {
        return adsSpaceDao.findOne(id);
    }

    @Override
    @Transactional
    public AdsSpace saveOrUpdate(AdsSpace pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");
        Preconditions.checkArgument(StringUtils.isEmpty(pageObj.getName()), "名称不能为空");
        Preconditions.checkArgument(StringUtils.isEmpty(pageObj.getSubKeyword()), "子关键字不能为空");
        Preconditions.checkArgument(StringUtils.isEmpty(pageObj.getAdsType()), "类型不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);

        //验证字段的唯一性
        Preconditions.checkArgument(
                isExist(pageObj.getId(), pageObj.getPageId(), pageObj.getParentId(), "name", pageObj.getName()),
                "名称[" + pageObj.getName() + "]已经存在");
        Preconditions.checkArgument(
                isExist(pageObj.getId(), pageObj.getPageId(), pageObj.getParentId(), "subKeyword", pageObj.getSubKeyword()),
                "子关键字[" + pageObj.getName() + "]已经存在");
        //父节点
        AdsSpace parent = null;
        //新增记录
        if (pageObj.getId() == null)
        {
            //设置默认属性
            pageObj.setCreateTime(new Date());
            pageObj.setUpdateTime(new Date());
            if (pageObj.getParentId() != null)
            {
                parent = adsSpaceDao.findOne(pageObj.getParentId());
                super.setAddCategory(pageObj, parent);//设置分类的公共属性
            }
            if (parent != null)
            {
                pageObj.setKeyword(parent.getKeyword() + "/" + pageObj.getSubKeyword());
            } else {
                Preconditions.checkArgument(pageObj.getPageId() == null, "页面不能为空");
                AdsPage adsPage = adsPageDao.findOne(pageObj.getPageId());
                Preconditions.checkArgument(adsPage == null, "页面不存在");
                pageObj.setKeyword(adsPage.getPageCode() + "/" + pageObj.getSubKeyword());
                pageObj.setLevel(1);
            }
            adsSpaceDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        AdsSpace dbObj = adsSpaceDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段
        pageObj.setCreateTime(dbObj.getCreateTime());
        pageObj.setUpdateTime(new Date());//更新时间
        if (dbObj.getParentId() != null) {
            parent = adsSpaceDao.findOne(dbObj.getParentId());
            super.setUpdateCategory(pageObj, parent, dbObj);//更新时不能修改的公共属性
        }
        pageObj.setKeyword(dbObj.getKeyword());
        pageObj.setSubKeyword(dbObj.getSubKeyword());

        adsSpaceDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<AdsSpace> pageAdsSpace(AdsSpace param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageAdsSpace(param, pagerParam);

    }

    @Override
    public Pager<AdsSpace> pageAdsSpace(AdsSpace param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<AdsSpace> pageResult = adsSpaceDao.findAll(AdsSpaceSpecs.queryByAdsSpace(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<AdsSpace> pageAdsSpace(AdsSpace param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<AdsSpace> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            AdsSpace obj = adsSpaceDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = adsSpaceDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = adsSpaceDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteAdsSpace(Long... ids) {
        List<AdsSpace> children = adsSpaceDao.findParentByIds(ids);
        Preconditions.checkArgument(children != null && children.size() > 0, "存在子分类，不能删除");

        List<AdsSpace> list = adsSpaceDao.findByIds(ids);
        Map<Long, Boolean> map = new HashMap<>();
        for (AdsSpace space : list)
        {
            map.put(space.getPageId(), true);
        }
        adsSpaceDao.deleteAdsSpace(ids);
        for (Long pageId : map.keySet())
        {
            redisService.expire(AdsTemplateService.ADS_ALL_PAGE + pageId, 3000L);//更新缓存
        }
    }


    @Override
    @Transactional
    public void updateOrderValue(Long id, Long orderValue) {
        AdsSpace dbObj = adsSpaceDao.findOne(id);
        Preconditions.checkNotNull(dbObj, "更新排序的记录不存在");
        if (orderValue.equals(dbObj.getOrderValue())) {
            return;
        }
        adsSpaceDao.updateOrderValue(id, orderValue);
    }

    @Override
    public boolean isExist(Long id, Long pageId, Long parentId, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("subKeyword");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<AdsSpace> list = adsSpaceDao.findAll(AdsSpaceSpecs.queryBy(pageId, parentId, rowName, rowValue, AdsSpace.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        AdsSpace dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }

    @Override
    protected List<CategorySimple> findAllCategory()
    {
        List<AdsSpace> categoryList = adsSpaceDao.findAll(new Sort(new Sort.Order(Sort.Direction.ASC, "level"),
                new Sort.Order(Sort.Direction.ASC, "orderValue")));
        List<CategorySimple> categories = new ArrayList<>();
        categories.addAll(categoryList);
        return categories;
    }

    public List<TreeNode> getTreeByPageId(Long pageId) {
        List<TreeNode> list = new ArrayList<>();
        List<AdsSpace> adsSpaces = adsSpaceDao.findAllByPageId(pageId);
        List<CategorySimple> categoryList = new ArrayList<>();
        categoryList.addAll(adsSpaces);
        if (categoryList.size() < 1) {
            return list;
        }
        long rootParentId = 0L;
        Map<Long, List<CategorySimple>> parentMap = getParentMap(rootParentId, categoryList);

        List<CategorySimple> rootList = parentMap.get(rootParentId);
        for (CategorySimple sub : rootList) {
            TreeNode treeNode = setTreeNode(sub, parentMap);
            list.add(treeNode);
        }
        return list;
    }

    @Override
    @Transactional
    public AdsSpace getAndCreate(AdsSpace pageObj)
    {
        if(pageObj == null) return null;
        AdsSpace adsSpace = null;
        if(pageObj.getParentId() == null)
            adsSpace = adsSpaceDao.findByPageIdAndSubKeyword(pageObj.getPageId(), pageObj.getSubKeyword());
        else
            adsSpace = adsSpaceDao.findByParentIdAndSubKeyword(pageObj.getParentId(), pageObj.getSubKeyword());

        if(adsSpace != null) {
            logger.info("广告位" + adsSpace.getKeyword() + "的数据已经存在不需要再导入");
            return adsSpace;
        }
        pageObj.setCreateTime(new Date());
        pageObj.setUpdateTime(new Date());
        return adsSpaceDao.save(pageObj);
    }

    @Override
    public Map<String, AdsSpaceInfo> loadAllSpaceAndElementData(AdsPage adsPage)
    {
        Map<String, AdsSpaceInfo> map = new TreeMap<>();
        List<AdsSpace> spacesList = loadAllSpace(adsPage.getId());
        for (AdsSpace adsSpace : spacesList)
        {
            AdsSpaceInfo info = new AdsSpaceInfo();
            info.setSpace(adsSpace);
            List elements = loadElementData(adsSpace);
            info.setElements(elements);
            map.put(adsSpace.getKeyword(), info);
        }
        return map;
    }

    private List<AdsSpace> loadAllSpace(Long id)
    {
        final String cacheKey = AdsTemplateService.ADS_ALL_PAGE + id;
        List<AdsSpace> cacheList = redisService.getList(cacheKey, AdsSpace.class);
        if (cacheList != null)
            return cacheList;
        cacheList = adsSpaceDao.findAllByPageId(id);
        redisService.set(cacheKey, cacheList, 30, TimeUnit.MINUTES);
        return cacheList;
    }

    public void removeCache(Long pageId)
    {
        final String cacheKey = AdsTemplateService.ADS_ALL_PAGE + pageId;
        redisService.delete(cacheKey);
    }

    private List loadElementData(AdsSpace adsSpace)
    {
        final String cacheKey = AdsTemplateService.ADS_ALL_SPACE + adsSpace.getId();
        String adsType = adsSpace.getAdsType();
        List cacheList = getCacheElements(cacheKey, adsType);
        if (cacheList != null)
            return cacheList;

        List<AdsElement> elements = adsElementDao.listEffectElement(adsSpace.getId(), new Date());
        if(elements.size() < 1)
        {
            return putElementToRedis(cacheKey, null, null);
        }
        Date minTime = null;
        List<String> ids = new ArrayList<>();
        for (AdsElement element : elements)
        {
            element.setCreateTime(null);
            element.setUpdateTime(null);
            element.setOrderValue(null);
            if(!AdsElementType.BigText.toString().equals(element.getTargetType()))
                element.setTex(null);
            String targetId = element.getTargetId();
            if (StringUtils.isNotEmpty(targetId))
            {
                ids.add(targetId);
            }
            if(minTime == null)
            {
                minTime = element.getEndTime();
            } else if (element.getEndTime().before(minTime))
            {
                minTime = element.getEndTime();
            }
        }
        if (AdsSpaceType.ImageLink.toString().equals(adsType) || AdsSpaceType.TextLink.toString().equals(adsType) ||
                AdsSpaceType.BigText.toString().equals(adsType) || AdsSpaceType.Model.toString().equals(adsType))
        {
            return putElementToRedis(cacheKey, elements, minTime);
        }

        if(ids.size() < 1)
            return putElementToRedis(cacheKey, elements, minTime);
        try
        {
            if (AdsSpaceType.Article.toString().equals(adsType))
            {
                List list = listArticleElement(elements, ids);
                return putElementToRedis(cacheKey, list, minTime);
            }
        }
        catch (Exception e)
        {
            logger.error("获取广告位元素时，转换对象出错：" + e.getMessage(), e);
        }
        return putElementToRedis(cacheKey, null, minTime);
    }

    private List putElementToRedis(String cacheKey, List elements, Date minTime)
    {
        if(elements == null || elements.size() < 1)
            redisService.set(cacheKey, new ArrayList<>(), 30000);
        else if(minTime != null)
        {
            long exTime = minTime.getTime() - new Date().getTime();
            if(exTime <= 1000)
                exTime = 30000;
            redisService.set(cacheKey, elements, exTime);
        }
        return elements;
    }

    private List getCacheElements(String cacheKey, String adsType)
    {
        String cacheStr = redisService.get(cacheKey);
        if(StringUtils.isNotEmpty(cacheStr))
        {
            if (AdsSpaceType.ImageLink.toString().equals(adsType) || AdsSpaceType.TextLink.toString().equals(adsType) ||
                    AdsSpaceType.BigText.toString().equals(adsType) || AdsSpaceType.Model.toString().equals(adsType))
            {
                List<AdsElement> elements = JSON.parseArray(cacheStr, AdsElement.class);
                return elements;
            }
            else if (AdsSpaceType.Article.toString().equals(adsType))
            {
                List<AdsArticle> elements = JSON.parseArray(cacheStr, AdsArticle.class);
                return elements;
            }
        }
        return null;
    }

    private List listArticleElement(List<AdsElement> elements, List<String> ids) throws Exception
    {
        Map<String, CmsArticle> dataMap = cmsArticleService.findMapByIds(ids);
        List<AdsArticle> list = new ArrayList<>();
        for (AdsElement element : elements)
        {
            AdsArticle obj = new AdsArticle();
            BeanUtils.copyProperties(obj, element);
            CmsArticle data = dataMap.get(element.getTargetId());
            if(data == null) continue;
            if(StringUtils.isEmpty(obj.getTitle())) obj.setTitle(data.getTitle());//标题
            if(StringUtils.isEmpty(obj.getSubTitle())) obj.setSubTitle(data.getTitleSub());//副标题
            if(StringUtils.isEmpty(obj.getAdsImg())) obj.setAdsImg(data.getCoverImg());//封面
            obj.setDesc(data.getIntro());//简介
            list.add(obj);
        }
        return list;
    }

    @Override
    public List<AdsSpace> listSpaceByKeywordStart(Long pageId, Long spaceId, String keyword)
    {
        if(StringUtils.isEmpty(keyword))
            return new ArrayList<>();
        keyword = keyword + "%";
        return adsSpaceDao.findByPageIdAndParentIdAndKeywordLike(pageId, spaceId, keyword);
    }
}