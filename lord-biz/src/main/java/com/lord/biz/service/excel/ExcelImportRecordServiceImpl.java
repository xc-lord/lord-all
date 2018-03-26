package com.lord.biz.service.excel;

import com.lord.biz.dao.excel.ExcelImportRecordDao;
import com.lord.biz.dao.excel.specs.ExcelImportRecordSpecs;
import com.lord.biz.dao.mis.MisUserDao;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.constant.excel.ExcelImportState;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.excel.ExcelQueryParams;
import com.lord.common.dto.user.LoginUser;
import com.lord.common.model.excel.ExcelImportRecord;
import com.lord.common.model.mis.MisUser;
import com.lord.common.service.excel.ExcelImportRecordService;
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
 * Excel导入记录excel_import_record的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月26日 11:31:45
 */
@Component
public class ExcelImportRecordServiceImpl implements ExcelImportRecordService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ExcelImportRecordDao excelImportRecordDao;

    @Autowired
    private MisUserDao misUserDao;

    @Override
    public ExcelImportRecord getExcelImportRecord(Long id) {
        return excelImportRecordDao.findOne(id);
    }

    @Override
    @Transactional
    public ExcelImportRecord saveOrUpdate(ExcelImportRecord pageObj, LoginUser loginUser) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);

        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性
            pageObj.setCreateTime(new Date());
            pageObj.setUpdateTime(new Date());
            MisUser misUser = misUserDao.findOne(loginUser.getUserId());
            pageObj.setUploadUser(misUser);
            pageObj.setImportState(ExcelImportState.NotImported);
            excelImportRecordDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        ExcelImportRecord dbObj = excelImportRecordDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段
        pageObj.setCreateTime(dbObj.getCreateTime());
        pageObj.setUpdateTime(new Date());//更新时间

        excelImportRecordDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<ExcelImportRecord> pageExcelImportRecord(ExcelQueryParams param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageExcelImportRecord(param, pagerParam);

    }

    @Override
    public Pager<ExcelImportRecord> pageExcelImportRecord(ExcelQueryParams param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<ExcelImportRecord> pageResult = excelImportRecordDao.findAll(ExcelImportRecordSpecs.queryByExcelImportRecord(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<ExcelImportRecord> pageExcelImportRecord(ExcelQueryParams param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<ExcelImportRecord> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            ExcelImportRecord obj = excelImportRecordDao.findOne(param.getLongId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = excelImportRecordDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = excelImportRecordDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteExcelImportRecord(Long... ids) {
        excelImportRecordDao.deleteExcelImportRecord(ids);
    }

    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<ExcelImportRecord> list = excelImportRecordDao.findAll(ExcelImportRecordSpecs.queryBy(rowName, rowValue, ExcelImportRecord.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        ExcelImportRecord dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }

    @Override
    public void importData(Long id)
    {

    }

    @Override
    public void deleteData(Long id)
    {

    }
}