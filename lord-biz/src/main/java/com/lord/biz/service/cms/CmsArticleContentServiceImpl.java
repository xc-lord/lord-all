package com.lord.biz.service.cms;

import com.lord.biz.dao.cms.CmsArticleContentDao;
import com.lord.biz.dao.cms.specs.CmsArticleContentSpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.model.cms.CmsArticleContent;
import com.lord.common.service.cms.CmsArticleContentService;
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
import java.util.Date;
import java.util.List;

/**
 * 文章内容cms_article_content的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月08日 15:04:19
 */
@Component
public class CmsArticleContentServiceImpl implements CmsArticleContentService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CmsArticleContentDao cmsArticleContentDao;

    @Override
    public CmsArticleContent getCmsArticleContent(Long id) {
        return cmsArticleContentDao.findOne(id);
    }

    @Override
    @Transactional
    public CmsArticleContent saveOrUpdate(CmsArticleContent pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);

        //验证字段的唯一性

        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性
            pageObj.setCreateTime(new Date());
            pageObj.setUpdateTime(new Date());

            cmsArticleContentDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        CmsArticleContent dbObj = cmsArticleContentDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段
        pageObj.setCreateTime(dbObj.getCreateTime());
        pageObj.setUpdateTime(new Date());//更新时间

        cmsArticleContentDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<CmsArticleContent> pageCmsArticleContent(CmsArticleContent param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageCmsArticleContent(param, pagerParam);

    }

    @Override
    public Pager<CmsArticleContent> pageCmsArticleContent(CmsArticleContent param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<CmsArticleContent> pageResult = cmsArticleContentDao.findAll(CmsArticleContentSpecs.queryByCmsArticleContent(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<CmsArticleContent> pageCmsArticleContent(CmsArticleContent param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<CmsArticleContent> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            CmsArticleContent obj = cmsArticleContentDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = cmsArticleContentDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = cmsArticleContentDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteCmsArticleContent(Long... ids) {
        cmsArticleContentDao.deleteCmsArticleContent(ids);
    }


    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<CmsArticleContent> list = cmsArticleContentDao.findAll(CmsArticleContentSpecs.queryBy(rowName, rowValue, CmsArticleContent.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        CmsArticleContent dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }
}