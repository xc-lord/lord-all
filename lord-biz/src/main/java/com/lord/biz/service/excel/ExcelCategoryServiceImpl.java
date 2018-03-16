package com.lord.biz.service.excel;

import com.lord.biz.dao.excel.ExcelCategoryDao;
import com.lord.biz.dao.excel.specs.ExcelCategorySpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.model.excel.ExcelCategory;
import com.lord.common.service.excel.ExcelCategoryService;
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
 * Excel分类excel_category的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月16日 15:37:37
 */
@Component
public class ExcelCategoryServiceImpl implements ExcelCategoryService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ExcelCategoryDao excelCategoryDao;

    @Override
    public ExcelCategory getExcelCategory(Long id) {
        return excelCategoryDao.findOne(id);
    }

    @Override
    @Transactional
    public ExcelCategory saveOrUpdate(ExcelCategory pageObj) {
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

            excelCategoryDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        ExcelCategory dbObj = excelCategoryDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段
        pageObj.setCreateTime(dbObj.getCreateTime());
        pageObj.setUpdateTime(new Date());//更新时间

        excelCategoryDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<ExcelCategory> pageExcelCategory(ExcelCategory param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageExcelCategory(param, pagerParam);

    }

    @Override
    public Pager<ExcelCategory> pageExcelCategory(ExcelCategory param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<ExcelCategory> pageResult = excelCategoryDao.findAll(ExcelCategorySpecs.queryByExcelCategory(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<ExcelCategory> pageExcelCategory(ExcelCategory param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<ExcelCategory> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            ExcelCategory obj = excelCategoryDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = excelCategoryDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = excelCategoryDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteExcelCategory(Long... ids) {
        excelCategoryDao.deleteExcelCategory(ids);
    }


    @Override
    @Transactional
    public void updateOrderValue(Long id, Long orderValue) {
        ExcelCategory dbObj = excelCategoryDao.findOne(id);
        Preconditions.checkNotNull(dbObj, "更新排序的记录不存在");
        if (orderValue.equals(dbObj.getOrderValue())) {
            return;
        }
        excelCategoryDao.updateOrderValue(id, orderValue);
    }

    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<ExcelCategory> list = excelCategoryDao.findAll(ExcelCategorySpecs.queryBy(rowName, rowValue, ExcelCategory.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        ExcelCategory dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }
}