package com.lord.biz.service.mis;

import com.lord.biz.dao.mis.MisMenuDao;
import com.lord.biz.dao.mis.specs.MisMenuSpecs;
import com.lord.biz.service.CategoryServiceImpl;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.*;
import com.lord.common.model.mis.MisMenu;
import com.lord.common.service.mis.MisMenuService;
import com.lord.utils.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Order;
import java.util.*;

/**
 * 系统菜单mis_menu的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月10日 15:51:31
 */
@Component
public class MisMenuServiceImpl extends CategoryServiceImpl implements MisMenuService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MisMenuDao misMenuDao;

    @Override
    public MisMenu getMisMenu(Long id) {
        return misMenuDao.findOne(id);
    }

    @Override
    @Transactional
    public MisMenu saveOrUpdate(MisMenu pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);
        //父节点
        MisMenu parent = null;
        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性
            pageObj.setCreateTime(new Date());
            pageObj.setUpdateTime(new Date());
            if (pageObj.getParentId() != null) {
                parent = misMenuDao.findOne(pageObj.getParentId());
            }
            setAddCategory(pageObj, parent);//设置分类的公共属性
            misMenuDao.save(pageObj);//新增
        } else {
            //更新记录
            MisMenu dbObj = misMenuDao.findOne(pageObj.getId());
            Preconditions.checkNotNull(dbObj, "更新的记录不存在");
            //不能修改的字段
            pageObj.setCreateTime(dbObj.getCreateTime());
            pageObj.setUpdateTime(new Date());//更新时间
            if (dbObj.getParentId() != null) {
                parent = misMenuDao.findOne(dbObj.getParentId());
            }
            setUpdateCategory(pageObj, parent, dbObj);//更新时不能修改的公共属性
            misMenuDao.save(pageObj);//更新
        }
        //是否需要修改父节点
        if (needUpdateParent(pageObj, parent)) {
            misMenuDao.save(parent);
        }
        return pageObj;
    }

    public List<TreeNode> getTree() {
        return getTreeNodes();
    }

    @Override
    protected List<Category> findAllCategory() {
        List<MisMenu> categoryList = misMenuDao.findAll(new Sort(new Sort.Order(Sort.Direction.ASC, "level"),
                new Sort.Order(Sort.Direction.ASC, "orderValue")));
        List<Category> categories = new ArrayList<>();
        categories.addAll(categoryList);
        return categories;
    }

    @Override
    public Pager<MisMenu> pageMisMenu(MisMenu param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageMisMenu(param, pagerParam);

    }

    @Override
    public Pager<MisMenu> pageMisMenu(MisMenu param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<MisMenu> pageResult = misMenuDao.findAll(MisMenuSpecs.queryByMisMenu(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<MisMenu> pageMisMenu(MisMenu param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<MisMenu> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            MisMenu obj = misMenuDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = misMenuDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = misMenuDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteMisMenu(Long... ids) {
        List<MisMenu> parents = misMenuDao.findParentByIds(ids);
        Preconditions.checkArgument(parents != null && parents.size() > 0, "存在子菜单，不能删除");
        misMenuDao.deleteMisMenu(ids);
    }


    @Override
    @Transactional
    public void updateOrderValue(Long id, Long orderValue) {
        MisMenu dbObj = misMenuDao.findOne(id);
        Preconditions.checkNotNull(dbObj, "更新排序的记录不存在");
        if (orderValue.equals(dbObj.getOrderValue())) {
            return;
        }
        misMenuDao.updateOrderValue(id, orderValue);
    }

    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<MisMenu> list = misMenuDao.findAll(MisMenuSpecs.queryBy(rowName, rowValue, MisMenu.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        MisMenu dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }

}