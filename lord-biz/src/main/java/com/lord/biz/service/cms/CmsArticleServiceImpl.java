package com.lord.biz.service.cms;

import com.lord.biz.dao.cms.CmsArticleDao;
import com.lord.biz.dao.cms.specs.CmsArticleSpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.model.cms.CmsArticle;
import com.lord.common.service.cms.CmsArticleService;
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

        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性
            pageObj.setCreateTime(new Date());
            pageObj.setUpdateTime(new Date());
            pageObj.setRemoved(false);

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
        rowList.add("name");
        rowList.add("username");
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
}