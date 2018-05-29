package com.lord.biz.service.sys;

import com.alibaba.fastjson.JSON;
import com.lord.biz.dao.sys.SysExtendAttrDao;
import com.lord.biz.dao.sys.SysExtendAttributeDao;
import com.lord.biz.dao.sys.SysExtendTemplateDao;
import com.lord.biz.dao.sys.specs.SysExtendTemplateSpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.constant.sys.ExtendAttrDataType;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.sys.ExtendAttrDto;
import com.lord.common.dto.sys.ExtendDetails;
import com.lord.common.dto.sys.ExtendTemplateDto;
import com.lord.common.dto.user.LoginUser;
import com.lord.common.model.sys.SysExtendAttr;
import com.lord.common.model.sys.SysExtendAttribute;
import com.lord.common.model.sys.SysExtendTemplate;
import com.lord.common.service.sys.SysExtendTemplateService;
import com.lord.utils.CommonUtils;
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

import java.util.*;

/**
 * 扩展属性模板sys_extend_template的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月09日 17:34:08
 */
@Component
public class SysExtendTemplateServiceImpl implements SysExtendTemplateService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysExtendTemplateDao sysExtendTemplateDao;

    @Autowired
    private SysExtendAttrDao sysExtendAttrDao;

    @Autowired
    private SysExtendAttributeDao sysExtendAttributeDao;

    @Override
    public SysExtendTemplate getSysExtendTemplate(Long id) {
        return sysExtendTemplateDao.findOne(id);
    }

    @Override
    @Transactional
    public SysExtendTemplate saveOrUpdate(ExtendTemplateDto obj, LoginUser loginUser) {
        Preconditions.checkNotNull(obj, "保存对象不能为空");
        SysExtendTemplate pageObj = new SysExtendTemplate();
        CommonUtils.copyProperties(pageObj, obj);
        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);

        //验证字段的唯一性
        Preconditions.checkArgument(isExist(pageObj.getId(), "name", pageObj.getName()), "名称[" + pageObj.getName() + "]已经存在");
        String columnStr = obj.getColumnJsonStr();
        Preconditions.checkNotNull(columnStr, "扩展属性不能为空！");
        List<SysExtendAttr> columnList = JSON.parseArray(columnStr, SysExtendAttr.class);
        Preconditions.checkArgument(columnList.size() < 1, "最少需要新增一个扩展属性！");

        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性
            pageObj.setCreator(loginUser.getUserId());
            pageObj.setCreateTime(new Date());
            pageObj.setModifier(loginUser.getUserId());
            pageObj.setUpdateTime(new Date());
            pageObj.setRemoved(false);
        } else {
            //更新记录
            SysExtendTemplate dbObj = sysExtendTemplateDao.findOne(pageObj.getId());
            Preconditions.checkNotNull(dbObj, "更新的记录不存在");
            //不能修改的字段
            pageObj.setCreator(dbObj.getCreator());
            pageObj.setCreateTime(dbObj.getCreateTime());
            pageObj.setModifier(loginUser.getUserId());
            pageObj.setUpdateTime(new Date());//更新时间
            pageObj.setRemoved(dbObj.isRemoved());
        }
        sysExtendTemplateDao.save(pageObj);//保存到数据库

        //删除旧的列属性
        sysExtendAttrDao.deleteByTemplateId(pageObj.getId());
        for (SysExtendAttr attr : columnList)
        {
            attr.setId(null);
            attr.setTemplateId(pageObj.getId());
            attr.setEntityCode(pageObj.getEntityCode());
            sysExtendAttrDao.save(attr);
        }
        return pageObj;
    }

    @Override
    public Pager<SysExtendTemplate> pageSysExtendTemplate(SysExtendTemplate param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageSysExtendTemplate(param, pagerParam);

    }

    @Override
    public Pager<SysExtendTemplate> pageSysExtendTemplate(SysExtendTemplate param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<SysExtendTemplate> pageResult = sysExtendTemplateDao.findAll(SysExtendTemplateSpecs.queryBySysExtendTemplate(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<SysExtendTemplate> pageSysExtendTemplate(SysExtendTemplate param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<SysExtendTemplate> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            SysExtendTemplate obj = sysExtendTemplateDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = sysExtendTemplateDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = sysExtendTemplateDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteSysExtendTemplate(Long... ids) {
        sysExtendTemplateDao.deleteSysExtendTemplate(ids);
    }


    @Override
    @Transactional
    public void updateOrderValue(Long id, Long orderValue) {
        SysExtendTemplate dbObj = sysExtendTemplateDao.findOne(id);
        Preconditions.checkNotNull(dbObj, "更新排序的记录不存在");
        if (orderValue.equals(dbObj.getOrderValue())) {
            return;
        }
        sysExtendTemplateDao.updateOrderValue(id, orderValue);
    }

    @Override
    @Transactional
    public void removeSysExtendTemplate(Long... ids) {
        sysExtendTemplateDao.removeSysExtendTemplate(ids);
    }

    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<SysExtendTemplate> list = sysExtendTemplateDao.findAll(SysExtendTemplateSpecs.queryBy(rowName, rowValue, false, SysExtendTemplate.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        SysExtendTemplate dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }

    @Override
    public List<SysExtendAttr> listSysExtendAttr(Long templateId)
    {
        return sysExtendAttrDao.findByTemplateId(templateId);
    }

    @Override
    public SysExtendTemplate getSysExtendTemplate(String entityCode)
    {
        List<SysExtendTemplate> list = sysExtendTemplateDao.findByEntityCode(entityCode);
        if (list.size() > 0)
        {
            return list.get(0);
        }
        return null;
    }

    @Override
    public ExtendDetails getExtendDetails(String entityCode, Long entityId)
    {
        SysExtendTemplate template = getSysExtendTemplate(entityCode);
        Preconditions.checkNotNull(template, "模板不存在");
        List<SysExtendAttr> list = listSysExtendAttr(template.getId());
        List<ExtendAttrDto> columns = new ArrayList<>();
        Map<Long, Object> valMap = getValMap(entityCode, entityId);
        for (SysExtendAttr attr : list)
        {
            ExtendAttrDto dto = new ExtendAttrDto();
            dto.setAttrId(attr.getId());
            dto.setDataKey(attr.getDataKey());
            dto.setDataType(attr.getDataType());
            dto.setInputType(attr.getInputType());
            dto.setNullable(attr.getNullable());
            dto.setName(attr.getName());
            if(StringUtils.isNotEmpty(attr.getValJsonStr()))
                dto.setValJson(JSON.parseObject(attr.getValJsonStr()));
            if(valMap.get(attr.getId()) != null)
                dto.setVal(valMap.get(attr.getId()));
            columns.add(dto);
        }
        ExtendDetails details = new ExtendDetails();
        details.setEntityCode(entityCode);
        details.setEntityId(entityId);
        details.setTemplateId(template.getId());
        details.setTemplateName(template.getName());
        details.setEntityTable(template.getEntityTable());
        details.setColumnList(columns);
        return details;
    }

    /**
     * 获取实体的属性值
     * @param entityCode    实体编码
     * @param entityId      实体ID
     * @return
     */
    private Map<Long, Object> getValMap(String entityCode, Long entityId)
    {
        Map<Long, Object> valMap = new HashMap<>();
        if(entityId != null)
        {
            List<SysExtendAttribute> attributes = sysExtendAttributeDao
                    .findByEntityCodeAndEntityId(entityCode, entityId);
            for (SysExtendAttribute attribute : attributes)
            {
                if(ExtendAttrDataType.Datetime.toString().equals(attribute.getDataType()))
                    valMap.put(attribute.getAttrId(), attribute.getAttrValueTime());
                else if(ExtendAttrDataType.Number.toString().equals(attribute.getDataType()))
                    valMap.put(attribute.getAttrId(), attribute.getAttrValueNum());
                else
                    valMap.put(attribute.getAttrId(), attribute.getAttrValue());
            }
        }
        return valMap;
    }

    @Override
    public void saveExtendDetails(ExtendDetails extendDetails, LoginUser loginUser)
    {
        Preconditions.checkNotNull(extendDetails, "扩展属性对象不能为空");
        Long entityId = extendDetails.getEntityId();
        String entityCode = extendDetails.getEntityCode();
        Preconditions.checkNotNull(entityId, "扩展属性entityId不能为空");
        Preconditions.checkNotNull(extendDetails, "扩展属性entityCode不能为空");

        //获取旧属性列表
        List<SysExtendAttribute> attributes = sysExtendAttributeDao
                .findByEntityCodeAndEntityId(entityCode, entityId);
        Map<Long, SysExtendAttribute> attributeMap = new HashMap<>();
        for (SysExtendAttribute attribute : attributes)
        {
            attributeMap.put(attribute.getAttrId(), attribute);
        }

        //设置新属性和更新旧属性
        for (ExtendAttrDto attrDto : extendDetails.getColumnList())
        {
            SysExtendAttribute attribute = attributeMap.remove(attrDto.getAttrId());
            if(attribute == null)
                attribute = new SysExtendAttribute();
            attribute.setAttrId(attrDto.getAttrId());
            attribute.setAttrName(attrDto.getName());
            attribute.setAttrKey(attrDto.getDataKey());
            attribute.setDataType(attrDto.getDataType());
            attribute.setEntityCode(entityCode);
            attribute.setEntityId(entityId);
            if (ExtendAttrDataType.Datetime.toString().equals(attrDto.getDataType()))
            {
                Date time = (Date) attrDto.getVal();
                attribute.setAttrValueTime(time);
                if(time != null)
                    attribute.setAttrValue(CommonUtils.dateFormat(time));
            } else if (ExtendAttrDataType.Number.toString().equals(attrDto.getDataType()))
            {
                Double number = (Double) attrDto.getVal();
                attribute.setAttrValueNum(number);
                if(number != null)
                    attribute.setAttrValue("" + attrDto.getVal());
            } else {
                attribute.setAttrValue((String) attrDto.getVal());
            }
            sysExtendAttributeDao.save(attribute);
        }

        //删除无效属性
        for (Long attrId : attributeMap.keySet())
        {
            sysExtendAttributeDao.delete(attributeMap.get(attrId).getId());
        }
    }
}