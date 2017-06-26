package com.lord.biz.service.sys;

import com.lord.biz.dao.sys.SysFileDao;
import com.lord.biz.dao.sys.specs.SysFileSpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.model.sys.SysFile;
import com.lord.common.service.sys.SysFileService;
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
 * 系统菜单sys_file的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年06月26日 15:37:35
 */
@Component
public class SysFileServiceImpl implements SysFileService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysFileDao sysFileDao;

    @Override
    public SysFile getSysFile(Long id) {
        return sysFileDao.findOne(id);
    }

    @Override
    @Transactional
    public SysFile saveOrUpdate(SysFile pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);

        //验证字段的唯一性
        Preconditions.checkArgument(isExist(pageObj.getId(), "name", pageObj.getName()), "名称[" + pageObj.getName() + "]已经存在");

        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性
            pageObj.setCreateTime(new Date());
            pageObj.setUpdateTime(new Date());

            sysFileDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        SysFile dbObj = sysFileDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段
        pageObj.setCreateTime(dbObj.getCreateTime());
        pageObj.setUpdateTime(new Date());//更新时间

        sysFileDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<SysFile> pageSysFile(SysFile param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageSysFile(param, pagerParam);

    }

    @Override
    public Pager<SysFile> pageSysFile(SysFile param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<SysFile> pageResult = sysFileDao.findAll(SysFileSpecs.queryBySysFile(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<SysFile> pageSysFile(SysFile param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<SysFile> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            SysFile obj = sysFileDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = sysFileDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = sysFileDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteSysFile(Long... ids) {
        sysFileDao.deleteSysFile(ids);
    }


    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<SysFile> list = sysFileDao.findAll(SysFileSpecs.queryBy(rowName, rowValue, SysFile.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        SysFile dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }
}