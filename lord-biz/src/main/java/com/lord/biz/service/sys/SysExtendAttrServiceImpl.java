package com.lord.biz.service.sys;

import com.lord.biz.dao.sys.SysExtendAttrDao;
import com.lord.biz.dao.sys.specs.SysExtendAttrSpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.user.LoginUser;
import com.lord.common.model.sys.SysExtendAttr;
import com.lord.common.service.sys.SysExtendAttrService;
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
 * 扩展属性sys_extend_attr的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月09日 18:08:35
 */
@Component
public class SysExtendAttrServiceImpl implements SysExtendAttrService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysExtendAttrDao sysExtendAttrDao;

    @Override
    public SysExtendAttr getSysExtendAttr(Long id) {
        return sysExtendAttrDao.findOne(id);
    }

    @Override
    @Transactional
    public SysExtendAttr saveOrUpdate(SysExtendAttr pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);

        //验证字段的唯一性
        Preconditions.checkArgument(isExist(pageObj.getId(), "name", pageObj.getName()), "名称[" + pageObj.getName() + "]已经存在");

        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性

            sysExtendAttrDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        SysExtendAttr dbObj = sysExtendAttrDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段

        sysExtendAttrDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<SysExtendAttr> pageSysExtendAttr(SysExtendAttr param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageSysExtendAttr(param, pagerParam);

    }

    @Override
    public Pager<SysExtendAttr> pageSysExtendAttr(SysExtendAttr param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<SysExtendAttr> pageResult = sysExtendAttrDao.findAll(SysExtendAttrSpecs.queryBySysExtendAttr(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<SysExtendAttr> pageSysExtendAttr(SysExtendAttr param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<SysExtendAttr> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            SysExtendAttr obj = sysExtendAttrDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = sysExtendAttrDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = sysExtendAttrDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteSysExtendAttr(Long... ids) {
        sysExtendAttrDao.deleteSysExtendAttr(ids);
    }


    @Override
    @Transactional
    public void updateOrderValue(Long id, Long orderValue) {
        SysExtendAttr dbObj = sysExtendAttrDao.findOne(id);
        Preconditions.checkNotNull(dbObj, "更新排序的记录不存在");
        if (orderValue.equals(dbObj.getOrderValue())) {
            return;
        }
        sysExtendAttrDao.updateOrderValue(id, orderValue);
    }

    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<SysExtendAttr> list = sysExtendAttrDao.findAll(SysExtendAttrSpecs.queryBy(rowName, rowValue, SysExtendAttr.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        SysExtendAttr dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }
}