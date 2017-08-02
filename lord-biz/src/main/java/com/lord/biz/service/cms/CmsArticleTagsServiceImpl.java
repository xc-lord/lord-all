package com.lord.biz.service.cms;

import com.lord.biz.dao.cms.CmsArticleTagsDao;
import com.lord.biz.dao.cms.specs.CmsArticleTagsSpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.model.cms.CmsArticleTags;
import com.lord.common.service.cms.CmsArticleTagsService;
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
import java.util.List;

/**
 * 文章标签关联表cms_article_tags的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 18:03:02
 */
@Component
public class CmsArticleTagsServiceImpl implements CmsArticleTagsService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CmsArticleTagsDao cmsArticleTagsDao;

    @Override
    public CmsArticleTags getCmsArticleTags(Long id) {
        return cmsArticleTagsDao.findOne(id);
    }

    @Override
    @Transactional
    public CmsArticleTags saveOrUpdate(CmsArticleTags pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);

        //验证字段的唯一性

        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性

            cmsArticleTagsDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        CmsArticleTags dbObj = cmsArticleTagsDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段

        cmsArticleTagsDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<CmsArticleTags> pageCmsArticleTags(CmsArticleTags param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageCmsArticleTags(param, pagerParam);

    }

    @Override
    public Pager<CmsArticleTags> pageCmsArticleTags(CmsArticleTags param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<CmsArticleTags> pageResult = cmsArticleTagsDao.findAll(CmsArticleTagsSpecs.queryByCmsArticleTags(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<CmsArticleTags> pageCmsArticleTags(CmsArticleTags param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<CmsArticleTags> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            CmsArticleTags obj = cmsArticleTagsDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = cmsArticleTagsDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = cmsArticleTagsDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteCmsArticleTags(Long... ids) {
        cmsArticleTagsDao.deleteCmsArticleTags(ids);
    }


    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<CmsArticleTags> list = cmsArticleTagsDao.findAll(CmsArticleTagsSpecs.queryBy(rowName, rowValue, CmsArticleTags.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        CmsArticleTags dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }
}