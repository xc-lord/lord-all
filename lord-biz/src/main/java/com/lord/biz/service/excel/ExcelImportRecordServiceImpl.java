package com.lord.biz.service.excel;

import com.lord.biz.dao.DbSqlDao;
import com.lord.biz.dao.excel.ExcelColumnDao;
import com.lord.biz.dao.excel.ExcelImportRecordDao;
import com.lord.biz.dao.excel.ExcelTemplateDao;
import com.lord.biz.dao.excel.specs.ExcelImportRecordSpecs;
import com.lord.biz.dao.mis.MisUserDao;
import com.lord.biz.service.AppConfig;
import com.lord.biz.utils.ExcelUtils;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.constant.excel.ExcelColumnType;
import com.lord.common.constant.excel.ExcelImportState;
import com.lord.common.constant.excel.ExcelImportWay;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.excel.ExcelCellDto;
import com.lord.common.dto.excel.ExcelQueryParams;
import com.lord.common.dto.excel.ExcelSheetDto;
import com.lord.common.dto.user.LoginUser;
import com.lord.common.model.excel.ExcelColumn;
import com.lord.common.model.excel.ExcelImportRecord;
import com.lord.common.model.excel.ExcelTemplate;
import com.lord.common.model.mis.MisUser;
import com.lord.common.service.excel.ExcelImportRecordService;
import com.lord.utils.CommonUtils;
import com.lord.utils.IdGenerator;
import com.lord.utils.Preconditions;
import com.lord.utils.exception.CommonException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
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
    private ExcelTemplateDao excelTemplateDao;

    @Autowired
    private ExcelColumnDao excelColumnDao;

    @Autowired
    private MisUserDao misUserDao;

    @Autowired
    private DbSqlDao dbSqlDao;

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
    @Transactional
    public long importData(Long id, ExcelImportWay importWay, LoginUser loginUser)
    {
        long start = System.currentTimeMillis();
        ExcelImportRecord record = excelImportRecordDao.findOne(id);
        Preconditions.checkNotNull(record, "记录不存在");
        MisUser misUser = misUserDao.findOne(loginUser.getUserId());
        Preconditions.checkNotNull(misUser, "用户未登录");
        ExcelTemplate template = excelTemplateDao.getOne(record.getTemplateId());
        Preconditions.checkNotNull(template, "模板不存在");
        List<ExcelColumn> columnList = excelColumnDao.findByTemplateId(template.getId());
        Preconditions.checkArgument(CollectionUtils.isEmpty(columnList), "模板的列信息不存在");

        //导入数据
        long lineNum = importData(record, template, columnList, importWay, loginUser);

        ExcelImportState importState = ExcelImportState.Imported;
        if(ExcelImportState.Imported.equals(record.getImportState()) ||
                ExcelImportState.ReImported.equals(record.getImportState()))
            importState = ExcelImportState.ReImported;

        record.setUpdateTime(new Date());
        record.setImportUser(misUser);
        record.setLineNum(lineNum);
        record.setImportNum(record.getImportNum() + 1);
        record.setImportState(importState);
        excelImportRecordDao.save(record);//保存记录
        long end = System.currentTimeMillis();
        logger.info("用户{}{}，表{}导入record={}文件{}的数据共{}条数据成功，用时：{}ms",
                loginUser.getUserId(),loginUser.getNickname(),
                template.getTableName(), record.getId(), record.getFilePath(), lineNum, end-start);
        return lineNum;
    }

    private long importData(ExcelImportRecord record, ExcelTemplate template, List<ExcelColumn> columnList,
            ExcelImportWay importWay, LoginUser loginUser)
    {
        String filePath = AppConfig.uploadDir + record.getFilePath();

        logger.info("读取Excel文件{}的数据", filePath);
        ExcelSheetDto sheet = ExcelUtils
                .readXls(filePath, template.getExcelSheetIndex() - 1, template.getExcelStartRow() - 1);
        if (sheet == null || sheet.getRows() == null || sheet.getRows().size() < 1)
        {
            logger.info("读取Excel文件{}的数据，读取不到数据。", filePath);
            return 0;
        }
        List<List<ExcelCellDto>> rowList = sheet.getRows();
        long rowSize = rowList.size();
        checkRowAndSetAtrr(rowList, columnList);//检查Excel格式并设置属性

        logger.info("读取Excel文件{}的{}Sheet的数据，共{}数据。", filePath, sheet.getSheetName(), rowSize);

        String insertColumnStr = "";
        String insertValueStr = "";
        String updateColumnStr = "" ;
        String selectWhereStr = "";
        boolean needUpdate = false;
        for (ExcelColumn column : columnList)
        {
            insertColumnStr += column.getDbColumn() + ",";
            insertValueStr += "?,";
            updateColumnStr += column.getDbColumn() + " = ?,";
            if(column.isIdentification())
            {
                selectWhereStr += column.getDbColumn() + " = ?,";
                needUpdate = true;
            }
        }
        selectWhereStr = CommonUtils.subEndString(selectWhereStr, ",");
        String insertSql = "INSERT INTO " + template.getTableName() + "(" +
                insertColumnStr + "id,modifier,modifier_id,import_record_id,create_time,update_time) VALUES (" +
                insertValueStr + "?,?,?,?,?,?)";
        String updateSql = "UPDATE " + template.getTableName() + " SET " + updateColumnStr +
                "import_record_id = ?,modifier= ?,modifier_id= ?,update_time= ? WHERE id = ?";
        String selectSql = "SELECT id FROM " + template.getTableName() + " WHERE " + selectWhereStr;
        logger.info("新增的SQL语句为：" + insertSql);
        logger.info("更新的SQL语句为：" + updateSql);
        logger.info("查询的SQL语句为：" + selectSql);
        logger.info("是否需要检查唯一：" + needUpdate);

        if(importWay == null)
        {
            //默认为覆盖方式，有数据存在则更新，无数据则新增
            importWay = ExcelImportWay.CoverAppend;
        }
        if (ExcelImportWay.CoverAll.equals(importWay))
        {
            //全量覆盖方式，会清空表数据，再写入
            dbSqlDao.execute("truncate table " + template.getTableName());
        }
        for (int i = 0; i < rowList.size(); i++)
        {
            List<Object> params = new ArrayList<>();
            List<Object> selectParams = new ArrayList<>();
            List<ExcelCellDto> cellList = rowList.get(i);
            for (int j = 0; j < cellList.size(); j++)
            {
                ExcelCellDto cell = cellList.get(j);
                params.add(cell.getValue());
                if (cell.isIdentification())
                {
                    selectParams.add(cell.getValue());
                }
            }
            if(needUpdate) {
                BigInteger id = (BigInteger) dbSqlDao.selectOne(selectSql, selectParams.toArray());
                if(id != null)
                {
                    executeUpdate(updateSql, params, id.longValue(), record, loginUser);
                    continue;
                }
            }
            if (ExcelImportWay.CoverOld.equals(importWay))
            {
                continue;//覆盖更新方式，只会进行更新
            }
            executeInsert(insertSql, params, record, loginUser);
        }
        return rowSize;
    }

    private void executeUpdate(String updateSql, List<Object> params, Long id, ExcelImportRecord record,
            LoginUser loginUser)
    {
        params.add(record.getId());//Excel记录ID
        params.add(loginUser.getNickname());//修改人
        params.add(loginUser.getUserId());//修改人ID
        params.add(new Date());//更新时间
        params.add(id);//ID
        dbSqlDao.execute(updateSql, params.toArray());
    }

    private void executeInsert(String insertSql, List<Object> params, ExcelImportRecord record, LoginUser loginUser)
    {
        params.add(IdGenerator.nextId());//ID
        params.add(loginUser.getNickname());//修改人
        params.add(loginUser.getUserId());//修改人ID
        params.add(record.getId());//Excel记录ID
        params.add(new Date());//创建时间
        params.add(new Date());//更新时间
        dbSqlDao.execute(insertSql, params.toArray());
    }

    private void checkRowAndSetAtrr(List<List<ExcelCellDto>> rowList, List<ExcelColumn> columnList)
    {
        for (int i = 0; i < rowList.size(); i++)
        {
            List<ExcelCellDto> cellList = rowList.get(i);
            if (cellList.size() < columnList.size())
            {
                throw new CommonException("Excel的列数量小于模板配置的列数量");
            }
            for (int j = 0; j < cellList.size(); j++)
            {
                ExcelCellDto cell = cellList.get(j);
                ExcelColumn column = columnList.get(j);
                checkCell(cell, column);
                //设置属性
                cell.setDbColumn(column.getDbColumn());
                cell.setExcelColumn(column.getExcelColumn());
                cell.setIdentification(column.getIdentification() == null ? false : column.getIdentification());
            }
        }
    }

    /**
     * 检查单元格的格式，并且设置属性
     * @param cell
     * @param column
     */
    private void checkCell(ExcelCellDto cell, ExcelColumn column)
    {
        String cellName =
                "第" + (cell.getRowNum() + 1) + "行第" + (cell.getColumnNum() + 1) + "列的" + column.getExcelColumn();
        if (!cell.getColumnType().toString().equals(column.getColumnType()))
        {
            throw new CommonException(cellName + "格式不正确，格式应为" + column.getColumnType() + "，数据为" + cell);
        }
        if (ExcelColumnType.Varchar.equals(cell.getColumnType()))
        {
            String value = cell.getStringValue();
            if (!column.isNullable())
            {
                if (StringUtils.isEmpty(value))
                    throw new CommonException(cellName + "不能为空");
                if (column.getColumnLength() != null && value.length() > column.getColumnLength())
                    throw new CommonException(cellName + "的长度不正确，长度应为" + column.getColumnLength());
            }
        }
        else if (ExcelColumnType.Number.equals(cell.getColumnType()))
        {
            Double value = cell.getDoubleValue();
            if (!column.isNullable())
            {
                if (value == null)
                    throw new CommonException(cellName + "不能为空");
                if (column.getMaxValue() != null && value > column.getMaxValue())
                    throw new CommonException(cellName + "超过最大值" + column.getMaxValue());
                if (column.getMinValue() != null && value > column.getMinValue())
                    throw new CommonException(cellName + "小于最小值" + column.getMinValue());
            }
        }
        else if (ExcelColumnType.Datetime.equals(cell.getColumnType()))
        {
            Date value = cell.getDateValue();
            if (!column.isNullable())
            {
                if (value == null)
                    throw new CommonException(cellName + "不能为空");
                if (column.getEndTime() != null && value.after(column.getEndTime()))
                    throw new CommonException(cellName + "超过结束时间" + CommonUtils.dateFormat(column.getEndTime()));
                if (column.getStartTime() != null && value.before(column.getStartTime()))
                    throw new CommonException(cellName + "小于开始时间" + CommonUtils.dateFormat(column.getEndTime()));
            }
        }
    }

    @Override
    public void deleteData(Long id, LoginUser loginUser)
    {
        ExcelImportRecord record = excelImportRecordDao.findOne(id);
        Preconditions.checkNotNull(record, "记录不存在");
        /*Preconditions.checkArgument(ExcelImportState.NotImported.equals(record.getImportState()) ||
                ExcelImportState.RemoveImported.equals(record.getImportState()), "数据未导入或者已删除");*/
        MisUser misUser = misUserDao.findOne(loginUser.getUserId());
        Preconditions.checkNotNull(misUser, "用户未登录");
        ExcelTemplate template = excelTemplateDao.getOne(record.getTemplateId());
        Preconditions.checkNotNull(template, "模板不存在");

        //删除数据
        dbSqlDao.execute("DELETE FROM " + template.getTableName() + " WHERE id=?", record.getId());

        record.setUpdateTime(new Date());
        record.setImportState(ExcelImportState.RemoveImported);
        excelImportRecordDao.save(record);//保存记录
    }
}