package com.lord.biz.service.cms;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lord.biz.dao.DbSqlDao;
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
import com.lord.utils.CommonUtils;
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
import java.math.BigInteger;
import java.util.*;

/**
 * 文章cms_article的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 18:09:10
 */
@Component
public class CmsArticleServiceImpl implements CmsArticleService {

    public static final String SPRIT = ",";

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

    @Autowired
    private DbSqlDao dbSqlDao;

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
        PageRequest pageRequest = null;
        if(sort != null) {
            pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort);
        } else {
            pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        }
        Page<CmsArticle> pageResult = cmsArticleDao.findAll(CmsArticleSpecs.queryByCmsArticle(param), pageRequest);
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
        cmsArticleDao.save(article);//保存文章的关联关系
        return article;
    }

    @Override
    public List<CmsArticle> listByIds(List<String> ids)
    {
        return cmsArticleDao.findByIds(CommonUtils.parseLongArray(ids));
    }

    @Override
    public List<String> listArticleIds(int page, int pageSize)
    {
        Page<CmsArticle> pageResult = cmsArticleDao.findAll(CmsArticleSpecs.queryAll(), new PageRequest(page - 1, pageSize));
        List<String> ids = new ArrayList<>();
        for (CmsArticle article : pageResult)
        {
            ids.add(article.getId() + "");
        }
        return ids;
    }

    @Override
    public Map<String, CmsArticle> findMapByIds(List<String> ids)
    {
        Long[] list = CommonUtils.parseLongArray(ids);
        List<CmsArticle> articles = cmsArticleDao.findByIds(list);
        Map<String, CmsArticle> map = new HashMap<>();
        for (CmsArticle article : articles)
        {
            map.put(article.getId() + "", article);
        }
        return map;
    }

    @Override
    public Pager<CmsArticle> pageByCategory(CmsCategory category, PagerParam pagerParam)
    {
        CmsArticle param = new CmsArticle();
        if (category != null)
        {
            if(category.getLevel() == 1) {
                param.setCatOneId(category.getId());
            }
            else if(category.getLevel() == 2) {
                param.setCatTwoId(category.getId());
            }
            else if(category.getLevel() == 3) {
                param.setCatThreeId(category.getId());
            }
            else if(category.getLevel() == 4) {
                param.setCatId(category.getId());
            }
        }
        return pageCmsArticle(param, pagerParam);
    }

    @Override
    public CmsArticleContent getArticleContent(Long articleId)
    {
        return cmsArticleContentDao.findOne(CmsArticleContentSpecs.queryBy("articleId", articleId, CmsArticleContent.class));
    }

    @Override
    public CmsArticle getPrevArticle(CmsArticle article)
    {
        BigInteger maxId = (BigInteger) dbSqlDao.selectOne("SELECT max(id) FROM cms_article WHERE id < ? AND cat_id=?", article.getId(), article.getCatId());
        if(maxId == null)
            return null;
        return getCmsArticle(maxId.longValue());
    }

    @Override
    public CmsArticle getNextArticle(CmsArticle article)
    {
        BigInteger minId = (BigInteger) dbSqlDao.selectOne("SELECT min(id) FROM cms_article WHERE id > ? AND cat_id=?", article.getId(), article.getCatId());
        if(minId == null)
            return null;
        return getCmsArticle(minId.longValue());
    }

    @Override
    public List<CmsArticle> listRandomArticle(Integer num, Long catId)
    {
        if(num == null)
            num = 5;

        BigInteger count = null;
        if(catId != null) {
            count = (BigInteger) dbSqlDao.selectOne("SELECT count(id) FROM cms_article where cat_id=?", catId);
        } else {
            count = (BigInteger) dbSqlDao.selectOne("SELECT count(id) FROM cms_article");
        }

        if(count.intValue() <= num)
        {
            if(catId != null && count.intValue() == 0)
                return listRandomArticle(num, null);//该分类下没有记录，则推荐其他分类
            else
                return cmsArticleDao.findAll();//数量不足时，获取全部文章
        }

        int total = count.intValue();
        Map<Long, Long> rowNumMap = new HashMap<>();
        int times = num;
        if(times > total)
            times = total;
        while (rowNumMap.size() < times)
        {
            Long rowNum = CommonUtils.genRandom(1, total) + 0L;
            rowNumMap.put(rowNum, rowNum);
        }
        Long[] arr = new Long[rowNumMap.size()];
        int i = 0;
        String idParamStr = "";
        String rowNumStr = "";
        for (Long rowNum : rowNumMap.keySet())
        {
            arr[i] = rowNum;
            rowNumStr += rowNum + ",";
            idParamStr += "?,";
            i++;
        }
        idParamStr = idParamStr.substring(0, idParamStr.length()-1);
        logger.debug("随机的文章行号为：" + rowNumStr + " 参数为：" + idParamStr);

        List<Map<String, Object>> dbList = null;
        if(catId != null) {
            dbList = dbSqlDao.select("SELECT A.* FROM (\n" +
                    "SELECT @rownum:=@rownum+1 AS rownum, art.*\n" +
                    "FROM (SELECT @rownum:=0) r, (SELECT * FROM cms_article WHERE cat_id=?) art) A\n" +
                    "WHERE A.rownum in (" + idParamStr + ")", catId, arr);
        } else {
            dbList = dbSqlDao.select("SELECT A.* FROM (\n" +
                    "SELECT @rownum\\:=@rownum+1 AS rownum, cms_article.*\n" +
                    "FROM (SELECT @rownum\\:=0) r, cms_article) A\n" +
                    "WHERE A.rownum in (" + idParamStr + ")", arr);
        }
        List<CmsArticle> list = parseCmsArticles(dbList);
        return list;
    }

    /**
     * 数据库对象转换为实体对象
     * @param dbList
     * @return
     */
    private List<CmsArticle> parseCmsArticles(List<Map<String, Object>> dbList)
    {
        List<CmsArticle> list = new ArrayList<>();
        for (Map<String, Object> objectMap : dbList)
        {
            CmsArticle article = new CmsArticle();
            article.setId(((BigInteger) objectMap.get("id")).longValue());
            article.setTitle((String) objectMap.get("title"));
            article.setCatId(((BigInteger) objectMap.get("cat_id")).longValue());
            article.setCatName((String) objectMap.get("cat_name"));
            article.setCoverImg((String) objectMap.get("cover_img"));
            article.setUpdateTime((Date) objectMap.get("update_time"));
            article.setCreateTime((Date) objectMap.get("create_time"));
            list.add(article);
        }
        return list;
    }

    @Override
    public List<CmsArticle> listRefArticle(Long articleId)
    {
        List<CmsArticleRef> refs = cmsArticleRefDao.findByArticleId(articleId);
        if(refs == null || refs.size() < 1)
            return null;
        Long[] ids = new Long[refs.size()];
        for (int i = 0; i < refs.size(); i++)
        {
            ids[i] = refs.get(i).getArticleId();
        }
        List<CmsArticle> list = cmsArticleDao.findByIds(ids);
        return list;
    }

    @Override
    public Pager<CmsArticle> pageByTags(Long tagsId, PagerParam pagerParam)
    {
        int count = dbSqlDao.count("SELECT count(1) FROM cms_article WHERE id IN (SELECT article_id FROM cms_article_tags WHERE tags_id=?)", tagsId);
        Pager<CmsArticle> pager = new Pager<>(pagerParam, count);
        if(count < 1)
            return pager;
        List<Map<String, Object>> dbList = dbSqlDao.select("SELECT * FROM cms_article WHERE id IN (SELECT article_id FROM cms_article_tags WHERE tags_id=?) limit ?,?", tagsId, pager.getStartRow(), pager.getEndRow());
        List<CmsArticle> list = parseCmsArticles(dbList);
        pager.setList(list);
        return pager;
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
        for (String tag : articleTags)
        {
            if (StringUtils.isEmpty(tag))
            {
                continue;
            }
            CmsTags cmsTags = cmsTagsDao.findOne(CmsTagsSpecs.queryBy("name", tag, CmsTags.class));
            if (cmsTags == null)
            {
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

            tagNames += tag.getName() + SPRIT;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tagId", tag.getId());
            jsonObject.put("tagName", tag.getName());
            array.add(jsonObject);
        }
        article.setTags(CommonUtils.subEndString(tagNames, SPRIT));
        article.setTagsJson(array.toJSONString());
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
        Long[] ids = articleRefIds.toArray(new Long[articleRefIds.size()]);
        List<CmsArticle> list = cmsArticleDao.findByIds(ids);
        String refIds = "";
        for (CmsArticle cmsArticle : list)
        {
            if (cmsArticle.getId().equals(article.getId()))
            {
                continue;//关联文章，不能关联自己
            }
            CmsArticleRef ref = new CmsArticleRef();
            ref.setArticleId(article.getId());
            ref.setRefArticleId(cmsArticle.getId());
            cmsArticleRefDao.save(ref);//保存关联文章
            refIds += cmsArticle.getId() + SPRIT;
        }
        article.setRefIds(CommonUtils.subEndString(refIds, SPRIT));
    }

}