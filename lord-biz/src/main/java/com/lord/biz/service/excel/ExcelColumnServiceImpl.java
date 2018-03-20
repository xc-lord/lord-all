package com.lord.biz.service.excel;

import com.lord.biz.dao.excel.ExcelColumnDao;
import com.lord.biz.dao.excel.specs.ExcelColumnSpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.model.excel.ExcelColumn;
import com.lord.common.service.excel.ExcelColumnService;
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
 * Excel列映射关系excel_column的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月20日 11:39:54
 */
@Component
public class ExcelColumnServiceImpl implements ExcelColumnService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ExcelColumnDao excelColumnDao;

    @Override
    public ExcelColumn getExcelColumn(Long id) {
        return excelColumnDao.findOne(id);
    }

    @Override
    @Transactional
    public ExcelColumn saveOrUpdate(ExcelColumn pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);

        //验证字段的唯一性

        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性
            pageObj.setCreateTime(new Date());
            pageObj.setUpdateTime(new Date());

            excelColumnDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        ExcelColumn dbObj = excelColumnDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段
        pageObj.setCreateTime(dbObj.getCreateTime());
        pageObj.setUpdateTime(new Date());//更新时间

        excelColumnDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<ExcelColumn> pageExcelColumn(ExcelColumn param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageExcelColumn(param, pagerParam);

    }

    @Override
    public Pager<ExcelColumn> pageExcelColumn(ExcelColumn param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<ExcelColumn> pageResult = excelColumnDao.findAll(ExcelColumnSpecs.queryByExcelColumn(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<ExcelColumn> pageExcelColumn(ExcelColumn param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<ExcelColumn> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            ExcelColumn obj = excelColumnDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = excelColumnDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = excelColumnDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteExcelColumn(Long... ids) {
        excelColumnDao.deleteExcelColumn(ids);
    }


    @Override
    @Transactional
    public void updateOrderValue(Long id, Long orderValue) {
        ExcelColumn dbObj = excelColumnDao.findOne(id);
        Preconditions.checkNotNull(dbObj, "更新排序的记录不存在");
        if (orderValue.equals(dbObj.getOrderValue())) {
            return;
        }
        excelColumnDao.updateOrderValue(id, orderValue);
    }

    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<ExcelColumn> list = excelColumnDao.findAll(ExcelColumnSpecs.queryBy(rowName, rowValue, ExcelColumn.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        ExcelColumn dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }
}