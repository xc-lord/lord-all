package com.lord.common.service.sys;

import com.lord.common.dto.PagerSort;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.sys.ExtendDetails;
import com.lord.common.dto.sys.ExtendTemplateDto;
import com.lord.common.dto.user.LoginUser;
import com.lord.common.model.sys.SysExtendAttr;
import com.lord.common.model.sys.SysExtendTemplate;

import java.util.List;

/**
 * 扩展属性模板sys_extend_template的Service
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月09日 17:34:08
 */
public interface SysExtendTemplateService {

    /**
     * 根据主键Id查询
     * @param id    主键Id
     * @return  数据库对象
     */
    SysExtendTemplate getSysExtendTemplate(Long id);

    /**
     * 新增或者更新
     * @param pageObj   页面传参
     * @param loginUser 登录用户
     * @return  数据库对象
     */
    SysExtendTemplate saveOrUpdate(ExtendTemplateDto pageObj, LoginUser loginUser);
    /**
     * 分页查询
     * @param param     查询参数
     * @param page      当前页数
     * @param pageSize  分页大小
     * @return  分页结果
     */
    Pager<SysExtendTemplate> pageSysExtendTemplate(SysExtendTemplate param, int page, int pageSize);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @return  分页结果
     */
    Pager<SysExtendTemplate> pageSysExtendTemplate(SysExtendTemplate param, PagerParam pagerParam);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @param sorts         排序字段
     * @return  分页结果
     */
    Pager<SysExtendTemplate> pageSysExtendTemplate(SysExtendTemplate param, PagerParam pagerParam, PagerSort... sorts);

    /**
     * 物理删除
     * @param ids    主键ID
     */
    void deleteSysExtendTemplate(Long... ids);


    /**
     * 更新排序值
     * @param id            主键Id
     * @param orderValue    排序值
     */
    void updateOrderValue(Long id, Long orderValue);
    /**
     * 逻辑删除
     * @param ids    主键ID
     */
    void removeSysExtendTemplate(Long... ids);

    /**
     * 是否存在相同的记录
     * @param id        主键Id
     * @param rowName   属性名
     * @param rowValue  属性值
     * @return
     */
    boolean isExist(Long id, String rowName, String rowValue);

    /**
     * 根据模板ID，获取扩展属性列表
     * @param templateId 模板ID
     * @return 扩展属性列表
     */
    List<SysExtendAttr> listSysExtendAttr(Long templateId);

    /**
     * 根据实体编码，获取模板配置
     * @param entityCode
     * @return
     */
    SysExtendTemplate getSysExtendTemplate(String entityCode);

    /**
     * 根据实体编码，获取实体模板配置信息
     * @param entityCode    实体编码
     * @param entityId      实体ID
     * @return
     */
    ExtendDetails getExtendDetails(String entityCode, Long entityId);

    void saveExtendDetails(ExtendDetails extendDetails, LoginUser loginUser);
}