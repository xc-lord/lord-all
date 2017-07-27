package com.lord.common.service.mis;

import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.user.UserLoginInput;
import com.lord.common.dto.user.UserLoginOutput;
import com.lord.common.model.mis.MisUser;

/**
 * 后台用户mis_user的Service
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月09日 16:43:46
 */
public interface MisUserService {

    /**
     * 根据主键Id查询
     * @param id    主键Id
     * @return  数据库对象
     */
    MisUser getMisUser(Long id);

    /**
     * 新增或者更新
     * @param pageObj   页面传参
     * @return  数据库对象
     */
    MisUser saveOrUpdate(MisUser pageObj);

    /**
     * 分页查询
     * @param param     查询参数
     * @param page      当前页数
     * @param pageSize  分页大小
     * @return  分页结果
     */
    Pager<MisUser> pageMisUser(MisUser param, int page, int pageSize);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @return  分页结果
     */
    Pager<MisUser> pageMisUser(MisUser param, PagerParam pagerParam);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @param sorts         排序字段
     * @return  分页结果
     */
    Pager<MisUser> pageMisUser(MisUser param, PagerParam pagerParam, PagerSort... sorts);

    /**
     * 物理删除
     * @param ids    主键ID
     */
    void deleteMisUser(Long... ids);

    /**
     * 逻辑删除
     * @param ids    主键ID
     */
    void removeMisUser(Long... ids);

    /**
     * 更新排序值
     * @param id            主键Id
     * @param orderValue    排序值
     */
    void updateOrderValue(Long id, Long orderValue);

    /**
     * 更新排序值
     * @param id            主键Id
     * @param password      密码
     */
    void updatePassword(Long id, String password);

    /**
     * 是否存在相同的记录
     * @param id        主键Id
     * @param rowName   属性名
     * @param rowValue  属性值
     * @return
     */
    boolean isExist(Long id, String rowName, String rowValue);

    /**
     * 用户登录
     * @param input
     * @return
     */
    UserLoginOutput login(UserLoginInput input);

    /**
     * 修改用户自己的密码
     * @param userId        用户ID
     * @param oldPassword   旧密码
     * @param newPassword   新密码
     */
    void updateMyPassword(Long userId, String oldPassword, String newPassword);
}