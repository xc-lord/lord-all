package com.lord.biz.service.mis;

import com.lord.biz.dao.mis.MisMenuDao;
import com.lord.biz.dao.mis.MisMenuRightDao;
import com.lord.biz.dao.mis.MisRoleDao;
import com.lord.biz.dao.mis.MisRoleRightDao;
import com.lord.biz.dao.mis.specs.MisRoleSpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.model.mis.MisMenu;
import com.lord.common.model.mis.MisMenuRight;
import com.lord.common.model.mis.MisRole;
import com.lord.common.model.mis.MisRoleRight;
import com.lord.common.service.mis.MisRoleService;
import com.lord.utils.CommonUtils;
import com.lord.utils.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
 * 用户角色mis_role的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月08日 12:14:33
 */
@Component
public class MisRoleServiceImpl implements MisRoleService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MisRoleDao misRoleDao;

    @Autowired
    private MisRoleRightDao misRoleRightDao;

    @Autowired
    private MisMenuDao misMenuDao;

    @Autowired
    private MisMenuRightDao misMenuRightDao;

    @Override
    public MisRole getMisRole(Long id) {
        return misRoleDao.findOne(id);
    }

    @Override
    @Transactional
    public MisRole saveOrUpdate(MisRole pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);

        //验证字段的唯一性
        Preconditions.checkArgument(isExist(pageObj.getId(), "name", pageObj.getName()), "此角色名称已经存在");
        Preconditions.checkArgument(isExist(pageObj.getId(), "roleCode", pageObj.getRoleCode()), "此角色Code已经存在");

        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性
            pageObj.setCreateTime(new Date());
            pageObj.setUpdateTime(new Date());
            pageObj.setRemoved(false);

            misRoleDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        MisRole dbObj = misRoleDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段
        pageObj.setCreateTime(dbObj.getCreateTime());
        pageObj.setUpdateTime(new Date());//更新时间
        pageObj.setRemoved(dbObj.isRemoved());

        misRoleDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<MisRole> pageMisRole(MisRole param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageMisRole(param, pagerParam);
    }

    @Override
    public Pager<MisRole> pageMisRole(MisRole param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<MisRole> pageResult = misRoleDao.findAll(MisRoleSpecs.queryByMisRole(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<MisRole> pageMisRole(MisRole param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<MisRole> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            MisRole obj = misRoleDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = misRoleDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = misRoleDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteMisRole(Long... ids) {
        misRoleRightDao.deleteByRoleIds(ids);
        misRoleDao.deleteMisRole(ids);
    }

    @Override
    @Transactional
    public void removeMisRole(Long... ids) {
        misRoleDao.removeMisRole(ids);
    }

    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("roleCode");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<MisRole> list = misRoleDao.findAll(MisRoleSpecs.queryBy(rowName, rowValue, false, MisRole.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        MisRole dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public void saveRight(Long roleId, Long rightId, Boolean check)
    {
        MisRole role = misRoleDao.findOne(roleId);
        Preconditions.checkArgument(role == null, "角色不存在");
        MisMenuRight menuRight = misMenuRightDao.findOne(rightId);
        Preconditions.checkArgument(menuRight == null, "权限不存在");
        MisMenu misMenu = misMenuDao.findOne(menuRight.getMenuId());
        Preconditions.checkArgument(misMenu == null, "菜单不存在");
        misRoleRightDao.deleteByRightId(rightId);//先删除，再添加
        if(check)
        {
            MisRoleRight right = new MisRoleRight();
            right.setRightCode(misMenu.getLetter() + "." + menuRight.getRightCode());
            right.setRoleId(roleId);
            right.setMenuId(misMenu.getId());
            right.setRightId(rightId);
            right.setMenuRight(false);
            misRoleRightDao.save(right);
        }
    }

    @Override
    @Transactional
    public void saveMenuRight(Long roleId, Long[] menuIds)
    {
        MisRole role = misRoleDao.findOne(roleId);
        Preconditions.checkArgument(role == null, "角色不存在");
        List<MisMenu> misMenus = misMenuDao.findByIds(menuIds);
        Preconditions.checkArgument(CollectionUtils.isEmpty(misMenus), "未选择菜单");

        //父菜单也要加入权限配置
        /*Map<Long, Boolean> map = new HashMap<>();
        for (MisMenu misMenu : misMenus)
        {
            map.put(misMenu.getId(), true);
            List<Long> parentIds = CommonUtils.parseLongList(misMenu.getParentIds(), ",");
            if(parentIds == null) continue;

            for (Long parentId : parentIds)
            {
                map.put(parentId, true);
            }
        }
        menuIds = map.keySet().toArray(new Long[map.keySet().size()]);
        misMenus = misMenuDao.findByIds(menuIds);*/

        misRoleRightDao.deleteMenuRight(roleId);
        for (MisMenu misMenu : misMenus)
        {
            MisRoleRight right = new MisRoleRight();
            right.setRightCode(misMenu.getLetter());
            right.setRoleId(roleId);
            right.setMenuRight(true);
            right.setMenuId(misMenu.getId());
            misRoleRightDao.save(right);
        }
    }

}