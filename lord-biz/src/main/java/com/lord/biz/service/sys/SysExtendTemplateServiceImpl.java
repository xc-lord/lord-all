package com.lord.biz.service.sys;

import com.alibaba.fastjson.JSON;
import com.lord.biz.dao.sys.SysExtendAttrDao;
import com.lord.biz.dao.sys.SysExtendTemplateDao;
import com.lord.biz.dao.sys.specs.SysExtendTemplateSpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.sys.ExtendTemplateDto;
import com.lord.common.dto.user.LoginUser;
import com.lord.common.model.sys.SysExtendAttr;
import com.lord.common.model.sys.SysExtendTemplate;
import com.lord.common.service.sys.SysExtendTemplateService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
}