package com.lord.biz.service.cms;

import com.lord.biz.dao.cms.CmsCategoryDao;
import com.lord.biz.dao.cms.specs.CmsCategorySpecs;
import com.lord.biz.service.CategoryServiceImpl;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.cat.Category;
import com.lord.common.model.cms.CmsCategory;
import com.lord.common.service.cms.CmsCategoryService;
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
 * 文章分类cms_category的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 16:25:37
 */
@Component
public class CmsCategoryServiceImpl extends CategoryServiceImpl implements CmsCategoryService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CmsCategoryDao cmsCategoryDao;

    @Override
    public CmsCategory getCmsCategory(Long id) {
        return cmsCategoryDao.findOne(id);
    }

    @Override
    @Transactional
    public CmsCategory saveOrUpdate(CmsCategory pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);
        //父节点
        CmsCategory parent = null;
        //新增记录
        boolean isAdd = true;
        if (pageObj.getId() == null) {
            //设置默认属性
            pageObj.setCreateTime(new Date());
            pageObj.setUpdateTime(new Date());
            if (pageObj.getParentId() != null) {
                parent = cmsCategoryDao.findOne(pageObj.getParentId());
            }
            super.setAddCategory(pageObj, parent);//设置分类的公共属性
            cmsCategoryDao.save(pageObj);//新增
        } else {
            //更新记录
            CmsCategory dbObj = cmsCategoryDao.findOne(pageObj.getId());
            Preconditions.checkNotNull(dbObj, "更新的记录不存在");
            //不能修改的字段
            pageObj.setCreateTime(dbObj.getCreateTime());
            pageObj.setUpdateTime(new Date());//更新时间
            if (dbObj.getParentId() != null) {
                parent = cmsCategoryDao.findOne(dbObj.getParentId());
            }
            super.setUpdateCategory(pageObj, parent, dbObj);//更新时不能修改的公共属性
            cmsCategoryDao.save(pageObj);//更新
            isAdd = false;
        }
        //更新父节点
        if(isAdd) updateParents(pageObj);
        return pageObj;
    }

    @Override
    protected void updateChildrenIds(String chilrenStr, boolean isLeaf, Long parentId)
    {
        cmsCategoryDao.updateChildrenIds(chilrenStr, isLeaf, parentId);
    }

    @Override
    protected List<Long> findAllChildrenIdsLike(String parentIds)
    {
        return cmsCategoryDao.findAllChildrenIdsLike(parentIds);
    }

    @Override
    protected List<Category> findAllCategory() {
        List<CmsCategory> categoryList = cmsCategoryDao.findAll(new Sort(new Sort.Order(Sort.Direction.ASC, "level"),
                new Sort.Order(Sort.Direction.ASC, "orderValue")));
        List<Category> categories = new ArrayList<>();
        categories.addAll(categoryList);
        return categories;
    }

    @Override
    public Pager<CmsCategory> pageCmsCategory(CmsCategory param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageCmsCategory(param, pagerParam);

    }

    @Override
    public Pager<CmsCategory> pageCmsCategory(CmsCategory param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<CmsCategory> pageResult = cmsCategoryDao.findAll(CmsCategorySpecs.queryByCmsCategory(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<CmsCategory> pageCmsCategory(CmsCategory param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<CmsCategory> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            CmsCategory obj = cmsCategoryDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = cmsCategoryDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = cmsCategoryDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteCmsCategory(Long... ids) {
        List<CmsCategory> list = cmsCategoryDao.findByIds(ids);
        cmsCategoryDao.deleteCmsCategory(ids);//删除数据
        for (CmsCategory category : list)
        {
            //更新父节点信息
            super.updateParents(category);
        }
    }


    @Override
    @Transactional
    public void updateOrderValue(Long id, Long orderValue) {
        CmsCategory dbObj = cmsCategoryDao.findOne(id);
        Preconditions.checkNotNull(dbObj, "更新排序的记录不存在");
        if (orderValue.equals(dbObj.getOrderValue())) {
            return;
        }
        cmsCategoryDao.updateOrderValue(id, orderValue);
    }

    @Override
    @Transactional
    public void removeCmsCategory(Long... ids) {
        cmsCategoryDao.removeCmsCategory(ids);
    }

    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<CmsCategory> list = cmsCategoryDao.findAll(CmsCategorySpecs.queryBy(rowName, rowValue, false, CmsCategory.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        CmsCategory dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }

}