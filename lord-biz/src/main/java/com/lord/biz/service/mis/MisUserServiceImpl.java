package com.lord.biz.service.mis;

import com.lord.biz.dao.mis.MisUserDao;
import com.lord.biz.dao.mis.specs.MisUserSpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.constant.mis.UserStatus;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.user.LoginInput;
import com.lord.common.dto.user.LoginUser;
import com.lord.common.model.mis.MisUser;
import com.lord.common.service.mis.MisUserService;
import com.lord.utils.EncryptUtils;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后台用户mis_user的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月09日 16:43:46
 */
@Component
public class MisUserServiceImpl implements MisUserService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MisUserDao misUserDao;

    @Override
    public MisUser getMisUser(Long id) {
        return misUserDao.findOne(id);
    }

    @Override
    @Transactional
    public MisUser saveOrUpdate(MisUser pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");
        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);

        //新增记录
        if (pageObj.getId() == null) {
            //表单的字段验证
            Preconditions.checkArgument(StringUtils.isEmpty(pageObj.getName()), "名称不能为空");
            Preconditions.checkArgument(StringUtils.isEmpty(pageObj.getUsername()), "用户名不能为空");
            Preconditions.checkArgument(StringUtils.isEmpty(pageObj.getPassword()), "密码不能为空");
            Preconditions.checkArgument(pageObj.getRoleId() == null, "用户角色不能为空");
            Preconditions.checkArgument(isExist(pageObj.getId(), "username", pageObj.getUsername()), "用户名不能重复");
            Preconditions.checkArgument(isExist(pageObj.getId(), "name", pageObj.getName()), "名称不能重复");

            //设置默认属性
            pageObj.setCreateTime(new Date());
            pageObj.setUpdateTime(new Date());
            pageObj.setRemoved(false);
            pageObj.setPassword(EncryptUtils.passwordEncode(pageObj.getPassword()));//设置密码

            misUserDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        MisUser dbObj = misUserDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段
        pageObj.setCreateTime(dbObj.getCreateTime());
        pageObj.setUpdateTime(new Date());//更新时间
        pageObj.setRemoved(dbObj.isRemoved());
        pageObj.setPassword(dbObj.getPassword());//更新时不重设设密码
        pageObj.setLoginTime(dbObj.getLoginTime());
        pageObj.setUsername(dbObj.getUsername());
        pageObj.setName(dbObj.getName());

        misUserDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<MisUser> pageMisUser(MisUser param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageMisUser(param, pagerParam);

    }

    @Override
    public Pager<MisUser> pageMisUser(MisUser param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<MisUser> pageResult = misUserDao.findAll(MisUserSpecs.queryByMisUser(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<MisUser> pageMisUser(MisUser param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<MisUser> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            MisUser obj = misUserDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = misUserDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = misUserDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteMisUser(Long... ids) {
        misUserDao.deleteMisUser(ids);
    }


    @Override
    @Transactional
    public void removeMisUser(Long... ids) {
        misUserDao.removeMisUser(ids);
    }

    @Override
    @Transactional
    public void updateOrderValue(Long id, Long orderValue) {
        MisUser dbObj = misUserDao.findOne(id);
        Preconditions.checkNotNull(dbObj, "更新排序的记录不存在");
        if (orderValue.equals(dbObj.getOrderValue())) {
            return;
        }
        misUserDao.updateOrderValue(id, orderValue);
    }

    @Override
    @Transactional
    public void updatePassword(Long id, String password) {
        MisUser dbObj = misUserDao.findOne(id);
        Preconditions.checkNotNull(dbObj, "用户的记录不存在");
        String oldPwd = dbObj.getPassword();//旧密码
        String newPwd = EncryptUtils.passwordEncode(password);//新密码
        if (oldPwd.equals(newPwd)) {
            return;
        }
        misUserDao.updatePassword(id, newPwd);
    }

    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<MisUser> list = misUserDao.findAll(MisUserSpecs.queryBy(rowName, rowValue, false, MisUser.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        MisUser dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }

    @Override
    public LoginUser login(LoginInput input)
    {
        MisUser user = misUserDao.findByUsername(input.getUsername());
        Preconditions.checkArgument(user == null, "用户" + input.getUsername() + "未注册");
        Preconditions.checkArgument(UserStatus.Invalid.toString().equals(user.getStatus()), "用户" + input.getUsername() + "已无效");
        Preconditions.checkArgument(UserStatus.Frozen.toString().equals(user.getStatus()), "用户" + input.getUsername() + "已被冻结");
        String pwd = EncryptUtils.passwordEncode(input.getPassword());
        Preconditions.checkArgument(!pwd.equals(user.getPassword()), "输入的密码错误，请重新输入");

        LoginUser output = new LoginUser();
        output.setEmail(user.getEmail());
        output.setIcon(user.getIcon());
        output.setLoginTime(new Date());
        output.setNickname(user.getNickName());
        output.setUsername(user.getUsername());
        output.setUserId(user.getId());
        output.setSex(user.getSex());
        output.setPhone(user.getPhone());
        output.setIp(input.getIp());
        output.setWebChannel(input.getWebChannel());
        output.setRoleId(user.getRoleId());
        output.setSuperAdmin(user.getSuperAdmin());
        return output;
    }

    @Override
    @Transactional
    public void updateMyPassword(Long userId, String oldPassword, String newPassword)
    {
        MisUser user = misUserDao.findOne(userId);
        Preconditions.checkArgument(user == null, "用户" + userId + "不存在");
        String oldPwd = EncryptUtils.passwordEncode(oldPassword);//新密码
        String newPwd = EncryptUtils.passwordEncode(newPassword);//新密码
        Preconditions.checkArgument(!oldPwd.equals(user.getPassword()), "旧密码输入错误，请重新输入");
        if (newPwd.equals(user.getPassword())) {
            return;
        }
        misUserDao.updatePassword(userId, newPwd);
    }
}