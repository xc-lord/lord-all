package com.lord.biz.service.excel;

import com.alibaba.fastjson.JSON;
import com.lord.biz.dao.DbSqlDao;
import com.lord.biz.dao.excel.ExcelCategoryDao;
import com.lord.biz.dao.excel.ExcelColumnDao;
import com.lord.biz.dao.excel.ExcelTemplateDao;
import com.lord.biz.dao.excel.specs.ExcelTemplateSpecs;
import com.lord.biz.dao.mis.MisUserDao;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.constant.excel.ExcelColumnType;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.excel.ExcelColumnDto;
import com.lord.common.dto.excel.ExcelQueryParams;
import com.lord.common.dto.excel.ExcelTemplateFormDto;
import com.lord.common.dto.user.LoginUser;
import com.lord.common.model.excel.ExcelCategory;
import com.lord.common.model.excel.ExcelColumn;
import com.lord.common.model.excel.ExcelTemplate;
import com.lord.common.model.mis.MisUser;
import com.lord.common.service.excel.ExcelTemplateService;
import com.lord.utils.CommonUtils;
import com.lord.utils.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Excel模板配置excel_template的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月16日 15:51:05
 */
@Component
public class ExcelTemplateServiceImpl implements ExcelTemplateService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ExcelTemplateDao excelTemplateDao;

    @Autowired
    private ExcelCategoryDao excelCategoryDao;

    @Autowired
    private ExcelColumnDao excelColumnDao;

    @Autowired
    private MisUserDao misUserDao;

    @Autowired
    private DbSqlDao dbSqlDao;

    @Override
    public ExcelTemplate getExcelTemplate(Long id) {
        return excelTemplateDao.findOne(id);
    }

    @Override
    @Transactional
    public ExcelTemplate saveOrUpdate(ExcelTemplateFormDto pageDto) {
        Preconditions.checkNotNull(pageDto, "保存对象不能为空");
        if(logger.isDebugEnabled())
            logger.debug("保存" + pageDto);

        ExcelTemplate pageObj = new ExcelTemplate();
        CommonUtils.copyProperties(pageObj, pageDto);

        String columnStr = pageDto.getColumnJsonStr();
        Preconditions.checkNotNull(columnStr, "列信息不能为空！");
        List<ExcelColumnDto> columnList = JSON.parseArray(columnStr, ExcelColumnDto.class);
        Preconditions.checkArgument(columnList.size() < 1, "只是需要新增一列！");

        MisUser misUser = misUserDao.findOne(pageDto.getLoginUser().getUserId());
        Preconditions.checkNotNull(misUser, "用户不存在！");
        Preconditions.checkNotNull(pageDto.getCategoryId(), "分类ID不能为空！");
        ExcelCategory excelCategory = excelCategoryDao.findOne(pageDto.getCategoryId());
        Preconditions.checkNotNull(excelCategory, "分类不存在！");
        pageObj.setCategory(excelCategory);

        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性
            pageObj.setCreateTime(new Date());
            pageObj.setUpdateTime(new Date());
            pageObj.setCreater(misUser);
            pageObj.setModifier(misUser);
            pageObj.setTableCreated(false);
            excelTemplateDao.save(pageObj);//新增
        } else {
            //更新记录
            ExcelTemplate dbObj = excelTemplateDao.findOne(pageObj.getId());
            Preconditions.checkNotNull(dbObj, "更新的记录不存在");
            //不能修改的字段
            pageObj.setCreateTime(dbObj.getCreateTime());
            pageObj.setUpdateTime(new Date());//更新时间
            pageObj.setCreater(dbObj.getCreater());
            pageObj.setModifier(misUser);
            pageObj.setTableCreated(dbObj.getTableCreated());
            pageObj.setTableCreatedTime(dbObj.getTableCreatedTime());

            excelTemplateDao.save(pageObj);//更新
        }
        //保存Excel的列信息
        saveExcelColumn(pageObj, columnList, misUser);
        return pageObj;
    }

    /**
     * 保存Excel的列信息
     * @param template   Excel模板
     * @param columnList 列集合
     * @param misUser    当前用户
     */
    private void saveExcelColumn(ExcelTemplate template, List<ExcelColumnDto> columnList, MisUser misUser)
    {
        List<ExcelColumn> list = getExcelColumns(template.getId());
        Map<String, ExcelColumn> map = new HashMap<>();
        for (ExcelColumn column : list)
        {
            map.put(column.getDbColumn(), column);
        }

        for (ExcelColumnDto dto : columnList)
        {
            ExcelColumn column = new ExcelColumn();
            CommonUtils.copyProperties(column, dto);
            column.setExcelTemplateId(template.getId());
            column.setUpdateTime(new Date());
            column.setModifier(misUser.getId());

            ExcelColumn dbColumn = map.remove(dto.getDbColumn());
            if(dbColumn == null)
            {
                column.setId(null);
                column.setCreater(misUser.getId());
                column.setCreateTime(new Date());
            }
            else
            {
                column.setCreater(dbColumn.getCreater());
                column.setCreateTime(dbColumn.getCreateTime());
                column.setId(dbColumn.getId());
            }
            excelColumnDao.save(column);
        }

        //删除旧的记录
        for (String key : map.keySet())
        {
            excelColumnDao.delete(map.get(key).getId());
        }
    }

    public List<ExcelColumn> getExcelColumns(Long templateId)
    {
        return excelColumnDao.findByTemplateId(templateId);
    }

    @Override
    public Pager<ExcelTemplate> pageExcelTemplate(ExcelQueryParams param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageExcelTemplate(param, pagerParam);

    }

    @Override
    public Pager<ExcelTemplate> pageExcelTemplate(ExcelQueryParams param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<ExcelTemplate> pageResult = excelTemplateDao.findAll(ExcelTemplateSpecs.queryByExcelTemplate(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<ExcelTemplate> pageExcelTemplate(ExcelQueryParams param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<ExcelTemplate> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            ExcelTemplate obj = excelTemplateDao.findOne(param.getLongId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = excelTemplateDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = excelTemplateDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteExcelTemplate(Long... ids) {
        excelTemplateDao.deleteExcelTemplate(ids);
    }


    @Override
    @Transactional
    public void updateOrderValue(Long id, Long orderValue) {
        ExcelTemplate dbObj = excelTemplateDao.findOne(id);
        Preconditions.checkNotNull(dbObj, "更新排序的记录不存在");
        if (orderValue.equals(dbObj.getOrderValue())) {
            return;
        }
        excelTemplateDao.updateOrderValue(id, orderValue);
    }

    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("tableName");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<ExcelTemplate> list = excelTemplateDao.findAll(ExcelTemplateSpecs.queryBy(rowName, rowValue, ExcelTemplate.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        ExcelTemplate dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public void createTable(Long id, LoginUser loginUser)
    {
        MisUser misUser = misUserDao.findOne(loginUser.getUserId());
        ExcelTemplate template = excelTemplateDao.findOne(id);
        List<ExcelColumn> columns = excelColumnDao.findByTemplateId(id);
        Preconditions.checkNotNull(template, "Excel模板不存在");
        Preconditions.checkArgument(columns == null || columns.size() < 1, "Excel模板的列不存在");

        createMysqlTable(template, columns);//创建Mysql的表

        template.setTableCreated(true);
        template.setTableCreatedTime(new Date());
        template.setUpdateTime(new Date());
        template.setModifier(misUser);
        excelTemplateDao.save(template);//更新Excel模板的状态
    }

    /**
     * 创建Mysql的表
     * @param template
     * @param columns
     */
    private void createMysqlTable(ExcelTemplate template, List<ExcelColumn> columns)
    {
        //生成sql
        String columnSql = "";
        for (ExcelColumn column : columns)
        {
            String type = column.getColumnType();
            String columnType = "varchar(50)";
            if(ExcelColumnType.Varchar.toString().equals(type)) {
                columnType = "varchar(" + column.getColumnLength() + ")";
            }
            else if(ExcelColumnType.Datetime.toString().equals(type)) {
                columnType = "datetime";
            }
            else if(ExcelColumnType.Number.toString().equals(type)) {
                columnType = "double";
            }
            if(!column.getNullable()) {
                columnType += " not null";
            }
            columnSql += "   " + column.getDbColumn() + "                 " + columnType + " comment '" + column.getExcelColumn() + "',\n";
        }
        String tableName = template.getTableName();
        String dropTableSql = "drop table if exists " + tableName;
        String createSql = "create table " + tableName + "\n" +
                "(\n" +
                "   id                   bigint not null comment '主键ID',\n" +
                columnSql +
                "   modifier             varchar(50) comment '修改人',\n" +
                "   modifier_id          bigint comment '修改人ID',\n" +
                "   import_record_id     bigint comment '导入记录ID',\n" +
                "   create_time          datetime comment '创建时间',\n" +
                "   update_time          datetime comment '更新时间',\n" +
                "   primary key (id)\n" +
                ")";

        //打印sql
        String commentSql = "alter table " + tableName + " comment '" + template.getExcelName() + "'";
        logger.info("生成表的SQL为：\n" + dropTableSql + ";\n" + createSql + ";\n" + commentSql + ";");

        //执行sql
        dbSqlDao.execute(dropTableSql);
        dbSqlDao.execute(createSql);
        dbSqlDao.execute(commentSql);
    }
}