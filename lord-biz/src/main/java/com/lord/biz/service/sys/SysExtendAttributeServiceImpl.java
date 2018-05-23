package com.lord.biz.service.sys;

import com.lord.biz.dao.sys.SysExtendAttributeDao;
import com.lord.biz.dao.sys.specs.SysExtendAttributeSpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.user.LoginUser;
import com.lord.common.model.sys.SysExtendAttribute;
import com.lord.common.service.sys.SysExtendAttributeService;
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
 * 实体的扩展属性值sys_extend_attribute的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月22日 16:53:03
 */
@Component
public class SysExtendAttributeServiceImpl implements SysExtendAttributeService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysExtendAttributeDao sysExtendAttributeDao;

    @Override
    public SysExtendAttribute getSysExtendAttribute(Long id) {
        return sysExtendAttributeDao.findOne(id);
    }

    @Override
    @Transactional
    public SysExtendAttribute saveOrUpdate(SysExtendAttribute pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);

        //验证字段的唯一性

        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性

            sysExtendAttributeDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        SysExtendAttribute dbObj = sysExtendAttributeDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段

        sysExtendAttributeDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<SysExtendAttribute> pageSysExtendAttribute(SysExtendAttribute param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageSysExtendAttribute(param, pagerParam);

    }

    @Override
    public Pager<SysExtendAttribute> pageSysExtendAttribute(SysExtendAttribute param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<SysExtendAttribute> pageResult = sysExtendAttributeDao.findAll(SysExtendAttributeSpecs.queryBySysExtendAttribute(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<SysExtendAttribute> pageSysExtendAttribute(SysExtendAttribute param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<SysExtendAttribute> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            SysExtendAttribute obj = sysExtendAttributeDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = sysExtendAttributeDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = sysExtendAttributeDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteSysExtendAttribute(Long... ids) {
        sysExtendAttributeDao.deleteSysExtendAttribute(ids);
    }


    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<SysExtendAttribute> list = sysExtendAttributeDao.findAll(SysExtendAttributeSpecs.queryBy(rowName, rowValue, SysExtendAttribute.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        SysExtendAttribute dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }
}