package com.lord.common.service.mis;

import com.lord.common.dto.PagerSort;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.model.mis.MisMenuRight;

/**
 * 后台菜单的具体权限mis_menu_right的Service
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月28日 18:03:31
 */
public interface MisMenuRightService {

    /**
     * 根据主键Id查询
     * @param id    主键Id
     * @return  数据库对象
     */
    MisMenuRight getMisMenuRight(Long id);

    /**
     * 新增或者更新
     * @param pageObj   页面传参
     * @return  数据库对象
     */
    MisMenuRight saveOrUpdate(MisMenuRight pageObj);

    /**
     * 分页查询
     * @param param     查询参数
     * @param page      当前页数
     * @param pageSize  分页大小
     * @return  分页结果
     */
    Pager<MisMenuRight> pageMisMenuRight(MisMenuRight param, int page, int pageSize);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @return  分页结果
     */
    Pager<MisMenuRight> pageMisMenuRight(MisMenuRight param, PagerParam pagerParam);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @param sorts         排序字段
     * @return  分页结果
     */
    Pager<MisMenuRight> pageMisMenuRight(MisMenuRight param, PagerParam pagerParam, PagerSort... sorts);

    /**
     * 物理删除
     * @param ids    主键ID
     */
    void deleteMisMenuRight(Long... ids);


    /**
     * 更新排序值
     * @param id            主键Id
     * @param orderValue    排序值
     */
    void updateOrderValue(Long id, Long orderValue);

    /**
     * 判断同一菜单下，是否存在相同的记录
     * @param id        主键Id
     * @param menuId    菜单Id
     * @param rowName   属性名
     * @param rowValue  属性值
     * @return
     */
    boolean isExist(Long id, Long menuId, String rowName, String rowValue);

    /**
     * 根据菜单查询菜单的权限列表
     * @param menuId    菜单Id
     * @return  权限列表
     */
    Pager<MisMenuRight> pageByMenu(String menuId);

    /**
     * 根据菜单Id，新增菜单的基础权限配置
     * @param menuId 菜单Id
     */
    void addDefaultRight(Long menuId);
}