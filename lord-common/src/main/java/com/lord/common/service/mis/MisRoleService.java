package com.lord.common.service.mis;

import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.model.mis.MisRole;

/**
 * 用户角色mis_role的Service
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月08日 12:14:33
 */
public interface MisRoleService {

    /**
     * 根据主键Id查询
     * @param id    主键Id
     * @return  数据库对象
     */
    MisRole getMisRole(Long id);

    /**
     * 新增或者更新
     * @param pageObj   页面传参
     * @return  数据库对象
     */
    MisRole saveOrUpdate(MisRole pageObj);

    /**
     * 分页查询
     * @param param     查询参数
     * @param page      当前页数
     * @param pageSize  分页大小
     * @return  分页结果
     */
    Pager<MisRole> pageMisRole(MisRole param, int page, int pageSize);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @return  分页结果
     */
    Pager<MisRole> pageMisRole(MisRole param, PagerParam pagerParam);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @param sorts         排序字段
     * @return  分页结果
     */
    Pager<MisRole> pageMisRole(MisRole param, PagerParam pagerParam, PagerSort... sorts);

    /**
     * 物理删除
     * @param ids    主键ID
     */
    void deleteMisRole(Long... ids);

    /**
     * 逻辑删除
     * @param ids    主键ID
     */
    void removeMisRole(Long... ids);

    /**
     * 是否存在相同的记录
     * @param id        主键Id
     * @param rowName   属性名
     * @param rowValue  属性值
     * @return
     */
    boolean isExist(Long id, String rowName, String rowValue);
}