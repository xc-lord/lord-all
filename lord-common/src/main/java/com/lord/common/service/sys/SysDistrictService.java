package com.lord.common.service.sys;

import com.lord.common.dto.PagerSort;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.model.sys.SysDistrict;

import java.util.List;

/**
 * 行政区域sys_district的Service
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年04月24日 15:04:09
 */
public interface SysDistrictService {

    /**
     * 根据主键Id查询
     * @param id    主键Id
     * @return  数据库对象
     */
    SysDistrict getSysDistrict(Long id);

    /**
     * 新增或者更新
     * @param pageObj   页面传参
     * @return  数据库对象
     */
    SysDistrict saveOrUpdate(SysDistrict pageObj);

    /**
     * 分页查询
     * @param param     查询参数
     * @param page      当前页数
     * @param pageSize  分页大小
     * @return  分页结果
     */
    Pager<SysDistrict> pageSysDistrict(SysDistrict param, int page, int pageSize);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @return  分页结果
     */
    Pager<SysDistrict> pageSysDistrict(SysDistrict param, PagerParam pagerParam);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @param sorts         排序字段
     * @return  分页结果
     */
    Pager<SysDistrict> pageSysDistrict(SysDistrict param, PagerParam pagerParam, PagerSort... sorts);

    /**
     * 物理删除
     * @param ids    主键ID
     */
    void deleteSysDistrict(Long... ids);


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
     * 根据主键id，获取行政区域的所有父级区域
     * @param id
     * @return
     */
    List<SysDistrict> listParentDistrict(Long id);
}