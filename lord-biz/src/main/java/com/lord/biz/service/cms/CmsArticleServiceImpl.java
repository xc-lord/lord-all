package com.lord.biz.service.cms;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lord.biz.dao.cms.*;
import com.lord.biz.dao.cms.specs.CmsArticleContentSpecs;
import com.lord.biz.dao.cms.specs.CmsArticleSpecs;
import com.lord.biz.dao.cms.specs.CmsTagsSpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.constant.CheckState;
import com.lord.common.constant.cms.CmsArticleState;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.cms.CmsArticleDto;
import com.lord.common.model.cms.*;
import com.lord.common.service.cms.CmsArticleService;
import com.lord.utils.Preconditions;
import org.apache.commons.beanutils.BeanUtils;
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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文章cms_article的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 18:09:10
 */
@Component
public class CmsArticleServiceImpl implements CmsArticleService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CmsArticleDao cmsArticleDao;

    @Autowired
    private CmsCategoryDao cmsCategoryDao;

    @Autowired
    private CmsTagsDao cmsTagsDao;

    @Autowired
    private CmsArticleTagsDao cmsArticleTagsDao;

    @Autowired
    private CmsArticleRefDao cmsArticleRefDao;

    @Autowired
    private CmsArticleContentDao cmsArticleContentDao;

    @Override
    public CmsArticle getCmsArticle(Long id) {
        return cmsArticleDao.findOne(id);
    }

    @Override
    @Transactional
    public CmsArticle saveOrUpdate(CmsArticle pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);
        //验证字段的唯一性
        Preconditions.checkArgument(isExist(pageObj.getId(), "title", pageObj.getTitle()), "文章的标题不能重复");

        CmsCategory category = cmsCategoryDao.findOne(pageObj.getCatId());
        Preconditions.checkNotNull(category, "文章的分类不存在");
        pageObj.setCatName(category.getName());
        Integer level = category.getLevel();
        Preconditions.checkArgument(level == null || level < 1 || level > 4, "文章的分类等级数据不正确");
        if (level.equals(1)) {
            pageObj.setCatOneId(category.getId());
        } else if(level.equals(2)) {
            pageObj.setCatTwoId(category.getId());
            pageObj.setCatOneId(category.getParentId());
        } else if(level.equals(3)) {
            pageObj.setCatThreeId(category.getId());
            pageObj.setCatTwoId(category.getParentId());
            CmsCategory catTwo = cmsCategoryDao.findOne(category.getParentId());
            pageObj.setCatOneId(catTwo.getParentId());
        } else if(level.equals(4)) {
            CmsCategory catThree = cmsCategoryDao.findOne(category.getParentId());
            pageObj.setCatThreeId(catThree.getId());
            pageObj.setCatTwoId(catThree.getParentId());
            CmsCategory catTwo = cmsCategoryDao.findOne(catThree.getParentId());
            pageObj.setCatOneId(catTwo.getParentId());
        }

        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性
            pageObj.setCreateTime(new Date());
            pageObj.setUpdateTime(new Date());
            pageObj.setRemoved(false);
            pageObj.setCheckState(CheckState.WaitCheck.toString());
            pageObj.setState(CmsArticleState.Create.toString());

            cmsArticleDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        CmsArticle dbObj = cmsArticleDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段
        pageObj.setCreateTime(dbObj.getCreateTime());
        pageObj.setUpdateTime(new Date());//更新时间
        pageObj.setRemoved(dbObj.isRemoved());

        cmsArticleDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<CmsArticle> pageCmsArticle(CmsArticle param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageCmsArticle(param, pagerParam);

    }

    @Override
    public Pager<CmsArticle> pageCmsArticle(CmsArticle param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<CmsArticle> pageResult = cmsArticleDao.findAll(CmsArticleSpecs.queryByCmsArticle(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<CmsArticle> pageCmsArticle(CmsArticle param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<CmsArticle> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            CmsArticle obj = cmsArticleDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = cmsArticleDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = cmsArticleDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteCmsArticle(Long... ids) {
        cmsArticleDao.deleteCmsArticle(ids);
    }


    @Override
    @Transactional
    public void updateOrderValue(Long id, Long orderValue) {
        CmsArticle dbObj = cmsArticleDao.findOne(id);
        Preconditions.checkNotNull(dbObj, "更新排序的记录不存在");
        if (orderValue.equals(dbObj.getOrderValue())) {
            return;
        }
        cmsArticleDao.updateOrderValue(id, orderValue);
    }

    @Override
    @Transactional
    public void removeCmsArticle(Long... ids) {
        cmsArticleDao.removeCmsArticle(ids);
    }

    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("title");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<CmsArticle> list = cmsArticleDao.findAll(CmsArticleSpecs.queryBy(rowName, rowValue, false, CmsArticle.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        CmsArticle dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public CmsArticle save(CmsArticleDto pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");
        Preconditions.checkNotNull(pageObj.getTitle(), "文章的标题不能为空");
        Preconditions.checkNotNull(pageObj.getCatId(), "文章的分类不能为空");
        CmsArticle article = new CmsArticle();
        try {
            BeanUtils.copyProperties(article, pageObj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        saveOrUpdate(article);//保存文章
        saveArticleContent(article, pageObj);//保存文章内容
        List<CmsTags> tags = saveArticleTags(pageObj.getArticleTags());//保存文章标签
        connectArticleAndTags(article, tags);//文章与标签进行关联
        connectArticleAndRef(article, pageObj.getArticleRefIds());//文章与关联文章进行关联
        return article;
    }

    /**
     * 保存文章标签
     * @param articleTags
     * @return
     */
    private List<CmsTags> saveArticleTags(List<String> articleTags) {
        List<CmsTags> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(articleTags)) {
            return list;
        }
        for (String tag : articleTags) {
            CmsTags cmsTags = cmsTagsDao.findOne(CmsTagsSpecs.queryBy("name", tag, CmsTags.class));
            if (cmsTags == null) {
                cmsTags = new CmsTags();
                cmsTags.setCreateTime(new Date());
                cmsTags.setUpdateTime(new Date());
                cmsTags.setRemoved(false);
                cmsTags.setName(tag);
                cmsTagsDao.save(cmsTags);//新增
            }
            list.add(cmsTags);
        }
        return list;
    }

    /**
     * 保存文章内容
     * @param article
     * @param pageObj
     */
    private void saveArticleContent(CmsArticle article, CmsArticleDto pageObj) {
        if (article == null || article.getId() == null || pageObj == null) {
            return;
        }
        if (StringUtils.isEmpty(pageObj.getContent()) && StringUtils.isEmpty(pageObj.getMcontent())) {
            return;
        }
        CmsArticleContent content = new CmsArticleContent();
        CmsArticleContent dbObj = cmsArticleContentDao.findOne(CmsArticleContentSpecs.queryBy("articleId", article.getId(), CmsArticleContent.class));
        if (dbObj == null) {
            //新增时，设置默认属性
            content.setCreateTime(new Date());
            content.setUpdateTime(new Date());
        } else {
            //更新时，设置不能修改的字段
            content.setId(dbObj.getId());
            content.setCreateTime(dbObj.getCreateTime());
            content.setUpdateTime(new Date());//更新时间
        }

        //TODO:待添加
        content.setContent(pageObj.getContent());
        content.setContentEdit(pageObj.getContent());
        content.setMcontent(pageObj.getMcontent());
        content.setMcontentEdit(pageObj.getMcontent());
        content.setArticleId(article.getId());

        cmsArticleContentDao.save(content);//新增
    }

    /**
     * 文章与标签进行关联
     * @param article
     * @param tags
     */
    private void connectArticleAndTags(CmsArticle article, List<CmsTags> tags) {
        if (article == null || article.getId() == null || CollectionUtils.isEmpty(tags)) {
            return;
        }
        cmsArticleTagsDao.deleteByArticle(article.getId());//删除旧的关联关系
        String tagNames = "";
        JSONArray array = new JSONArray();
        for (CmsTags tag : tags) {
            CmsArticleTags cmsArticleTags = new CmsArticleTags();
            cmsArticleTags.setArticleId(article.getId());
            cmsArticleTags.setTagsId(tag.getId());
            cmsArticleTagsDao.save(cmsArticleTags);

            tagNames += tag.getName() + ",";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tagId", tag.getId());
            jsonObject.put("tagName", tag.getName());
            array.add(jsonObject);
        }
        CmsArticle dbObj = cmsArticleDao.findOne(article.getId());
        dbObj.setTags(tagNames);
        dbObj.setTagsJson(array.toJSONString());
        cmsArticleDao.save(dbObj);
    }

    /**
     * 文章与关联文章进行关联
     * @param article
     * @param articleRefIds
     */
    private void connectArticleAndRef(CmsArticle article, List<Long> articleRefIds) {
        if (article == null || article.getId() == null || CollectionUtils.isEmpty(articleRefIds)) {
            return;
        }
        cmsArticleRefDao.deleteByArticle(article.getId());//删除旧的关联关系
        Long[] ids = (Long[]) articleRefIds.toArray();
        List<CmsArticle> list = cmsArticleDao.findByIds(ids);
        for (CmsArticle cmsArticle : list) {
            CmsArticleRef ref = new CmsArticleRef();
            ref.setArticleId(article.getId());
            ref.setRefArticleId(cmsArticle.getId());
            cmsArticleRefDao.save(ref);//保存关联文章
        }
    }

}