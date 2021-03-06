package com.lord.common.service.sys;

import com.lord.common.dto.PagerSort;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.sys.ExtendContentDto;
import com.lord.common.dto.user.LoginUser;
import com.lord.common.model.sys.SysExtendContent;

import java.util.List;
import java.util.Map;

/**
 * 扩展内容sys_extend_content的Service
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月29日 20:18:20
 */
public interface SysExtendContentService {

    /**
     * 根据主键Id查询
     * @param id    主键Id
     * @return  数据库对象
     */
    SysExtendContent getSysExtendContent(Long id);


    /**
     * 新增或者更新
     * @param pageObj   页面传参
     * @return  数据库对象
     */
    SysExtendContent saveOrUpdate(SysExtendContent pageObj);
    /**
     * 分页查询
     * @param param     查询参数
     * @param page      当前页数
     * @param pageSize  分页大小
     * @return  分页结果
     */
    Pager<SysExtendContent> pageSysExtendContent(SysExtendContent param, int page, int pageSize);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @return  分页结果
     */
    Pager<SysExtendContent> pageSysExtendContent(SysExtendContent param, PagerParam pagerParam);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @param sorts         排序字段
     * @return  分页结果
     */
    Pager<SysExtendContent> pageSysExtendContent(SysExtendContent param, PagerParam pagerParam, PagerSort... sorts);

    /**
     * 物理删除
     * @param ids    主键ID
     */
    void deleteSysExtendContent(Long... ids);


    /**
     * 是否存在相同的记录
     * @param id        主键Id
     * @param rowName   属性名
     * @param rowValue  属性值
     * @return
     */
    boolean isExist(Long id, String rowName, String rowValue);

    /**
     * 获取实体的内容
     * @param entityCode    实体编码
     * @param entityId      实体ID
     * @return
     */
    SysExtendContent getExtendContent(String entityCode, Long entityId);

    /**
     * 获取实体的内容
     * @param entityId      实体ID
     * @param entityCodes    实体编码
     * @return
     */
    List<SysExtendContent> listByEntity(Long entityId, String... entityCodes);

    /**
     * 获取实体的内容
     * @param entityId      实体ID
     * @param entityCodes    实体编码
     * @return
     */
    Map<String, SysExtendContent> getMapByEntity(Long entityId, String... entityCodes);
}