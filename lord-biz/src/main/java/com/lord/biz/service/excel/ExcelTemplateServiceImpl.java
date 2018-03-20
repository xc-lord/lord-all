package com.lord.biz.service.excel;

import com.lord.biz.dao.excel.ExcelCategoryDao;
import com.lord.biz.dao.excel.ExcelTemplateDao;
import com.lord.biz.dao.excel.specs.ExcelTemplateSpecs;
import com.lord.biz.dao.mis.MisUserDao;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.excel.ExcelQueryParams;
import com.lord.common.dto.excel.ExcelTemplateFormDto;
import com.lord.common.model.excel.ExcelCategory;
import com.lord.common.model.excel.ExcelTemplate;
import com.lord.common.model.mis.MisUser;
import com.lord.common.service.excel.ExcelTemplateService;
import com.lord.utils.Preconditions;
import com.lord.utils.exception.CommonException;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private MisUserDao misUserDao;

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
        try
        {
            BeanUtils.copyProperties(pageObj,pageDto);
        }
        catch (Exception e)
        {
            throw new CommonException("对象转换失败");
        }

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
            excelTemplateDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        ExcelTemplate dbObj = excelTemplateDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段
        pageObj.setCreateTime(dbObj.getCreateTime());
        pageObj.setUpdateTime(new Date());//更新时间
        pageObj.setCreater(dbObj.getCreater());
        pageObj.setModifier(misUser);

        excelTemplateDao.save(pageObj);//更新
        return pageObj;
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
}