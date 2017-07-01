package com.lord.biz.service.cms;

import com.lord.biz.dao.cms.CmsTagsDao;
import com.lord.biz.dao.cms.specs.CmsTagsSpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.model.cms.CmsTags;
import com.lord.common.service.cms.CmsTagsService;
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
 * 文章标签cms_tags的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 15:42:51
 */
@Component
public class CmsTagsServiceImpl implements CmsTagsService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CmsTagsDao cmsTagsDao;

    @Override
    public CmsTags getCmsTags(Long id) {
        return cmsTagsDao.findOne(id);
    }

    @Override
    @Transactional
    public CmsTags saveOrUpdate(CmsTags pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);

        //验证字段的唯一性
        Preconditions.checkArgument(isExist(pageObj.getId(), "name", pageObj.getName()), "名称[" + pageObj.getName() + "]已经存在");

        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性
            pageObj.setCreateTime(new Date());
            pageObj.setUpdateTime(new Date());
            pageObj.setRemoved(false);

            cmsTagsDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        CmsTags dbObj = cmsTagsDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段
        pageObj.setCreateTime(dbObj.getCreateTime());
        pageObj.setUpdateTime(new Date());//更新时间
        pageObj.setRemoved(dbObj.isRemoved());

        cmsTagsDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<CmsTags> pageCmsTags(CmsTags param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageCmsTags(param, pagerParam);

    }

    @Override
    public Pager<CmsTags> pageCmsTags(CmsTags param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<CmsTags> pageResult = cmsTagsDao.findAll(CmsTagsSpecs.queryByCmsTags(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<CmsTags> pageCmsTags(CmsTags param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<CmsTags> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            CmsTags obj = cmsTagsDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = cmsTagsDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = cmsTagsDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteCmsTags(Long... ids) {
        cmsTagsDao.deleteCmsTags(ids);
    }


    @Override
    @Transactional
    public void updateOrderValue(Long id, Long orderValue) {
        CmsTags dbObj = cmsTagsDao.findOne(id);
        Preconditions.checkNotNull(dbObj, "更新排序的记录不存在");
        if (orderValue.equals(dbObj.getOrderValue())) {
            return;
        }
        cmsTagsDao.updateOrderValue(id, orderValue);
    }

    @Override
    @Transactional
    public void removeCmsTags(Long... ids) {
        cmsTagsDao.removeCmsTags(ids);
    }

    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<CmsTags> list = cmsTagsDao.findAll(CmsTagsSpecs.queryBy(rowName, rowValue, false, CmsTags.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        CmsTags dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }
}