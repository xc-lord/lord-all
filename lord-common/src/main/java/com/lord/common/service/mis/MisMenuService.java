package com.lord.common.service.mis;

import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.cat.TreeNode;
import com.lord.common.dto.mis.MenuRightTree;
import com.lord.common.dto.mis.UserMenu;
import com.lord.common.dto.user.LoginUser;
import com.lord.common.model.mis.MisMenu;
import com.lord.common.service.CategoryService;

import java.util.List;
import java.util.Map;

/**
 * 系统菜单mis_menu的Service
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月10日 15:51:31
 */
public interface MisMenuService extends CategoryService {

    String MIS_MENU_ROLE = "MIS_MENU_ROLE_";
    String MIS_RIGHT_ROLE = "MIS_RIGHT_ROLE_";

    /**
     * 根据主键Id查询
     * @param id    主键Id
     * @return  数据库对象
     */
    MisMenu getMisMenu(Long id);

    /**
     * 新增或者更新
     * @param pageObj   页面传参
     * @return  数据库对象
     */
    MisMenu saveOrUpdate(MisMenu pageObj);

    /**
     * 分页查询
     * @param param     查询参数
     * @param page      当前页数
     * @param pageSize  分页大小
     * @return  分页结果
     */
    Pager<MisMenu> pageMisMenu(MisMenu param, int page, int pageSize);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @return  分页结果
     */
    Pager<MisMenu> pageMisMenu(MisMenu param, PagerParam pagerParam);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @param sorts         排序字段
     * @return  分页结果
     */
    Pager<MisMenu> pageMisMenu(MisMenu param, PagerParam pagerParam, PagerSort... sorts);

    /**
     * 物理删除
     * @param ids    主键ID
     */
    void deleteMisMenu(Long... ids);


    /**
     * 更新排序值
     * @param id            主键Id
     * @param orderValue    排序值
     */
    void updateOrderValue(Long id, Long orderValue);

    /**
     * 是否存在相同的记录
     * @param id        主键Id
     * @param rowName   属性名
     * @param rowValue  属性值
     * @return
     */
    boolean isExist(Long id, String rowName, String rowValue);

    /**
     * 获得菜单的权限管理的树形结构
     * @param roleId        角色Id
     * @return 树
     */
    MenuRightTree getMenuRightTree(Long roleId);

    /**
     * 获得菜单树形列表
     * @param loginUser 当前用户
     * @return 菜单
     */
    List<TreeNode> getMenus(LoginUser loginUser);

    /**
     * 获得菜单按钮的权限
     * @param roleId        角色Id
     * @param superAdmin    是否超级管理员
     * @return 权限Map
     */
    Map<String,Boolean> getRightMap(Long roleId, Boolean superAdmin);

    /**
     * 获得菜单的权限
     * @param roleId        角色Id
     * @param superAdmin    是否超级管理员
     * @return 权限
     */
    UserMenu getUserMenu(Long roleId, Boolean superAdmin);
}