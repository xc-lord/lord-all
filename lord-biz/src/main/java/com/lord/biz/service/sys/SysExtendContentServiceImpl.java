package com.lord.biz.service.sys;

import com.lord.biz.dao.sys.SysExtendContentDao;
import com.lord.biz.dao.sys.specs.SysExtendContentSpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.sys.ExtendContentDto;
import com.lord.common.dto.user.LoginUser;
import com.lord.common.model.sys.SysExtendContent;
import com.lord.common.service.sys.SysExtendContentService;
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
 * 扩展内容sys_extend_content的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月29日 20:18:20
 */
@Component
public class SysExtendContentServiceImpl implements SysExtendContentService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysExtendContentDao sysExtendContentDao;

    @Override
    public SysExtendContent getSysExtendContent(Long id) {
        return sysExtendContentDao.findOne(id);
    }

    @Override
    @Transactional
    public SysExtendContent saveOrUpdate(SysExtendContent pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");
        Preconditions.checkNotNull(pageObj.getEntityId(), "实体ID不能为空");
        Preconditions.checkNotNull(pageObj.getEntityCode(), "实体编码不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);

        //验证字段的唯一性

        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性
            pageObj.setCreateTime(new Date());
            pageObj.setUpdateTime(new Date());

            sysExtendContentDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        SysExtendContent dbObj = sysExtendContentDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段
        pageObj.setCreateTime(dbObj.getCreateTime());
        pageObj.setUpdateTime(new Date());//更新时间

        sysExtendContentDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<SysExtendContent> pageSysExtendContent(SysExtendContent param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageSysExtendContent(param, pagerParam);

    }

    @Override
    public Pager<SysExtendContent> pageSysExtendContent(SysExtendContent param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<SysExtendContent> pageResult = sysExtendContentDao.findAll(SysExtendContentSpecs.queryBySysExtendContent(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<SysExtendContent> pageSysExtendContent(SysExtendContent param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<SysExtendContent> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            SysExtendContent obj = sysExtendContentDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = sysExtendContentDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = sysExtendContentDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteSysExtendContent(Long... ids) {
        sysExtendContentDao.deleteSysExtendContent(ids);
    }


    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<SysExtendContent> list = sysExtendContentDao.findAll(SysExtendContentSpecs.queryBy(rowName, rowValue, SysExtendContent.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        SysExtendContent dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }

    @Override
    public SysExtendContent getExtendContent(String entityCode, Long entityId)
    {
        SysExtendContent content = sysExtendContentDao.findOneByEntityCodeAndEntityId(entityCode, entityId);
        return content;
    }
}