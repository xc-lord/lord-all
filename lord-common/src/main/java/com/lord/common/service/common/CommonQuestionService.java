package com.lord.common.service.common;

import com.lord.common.dto.PagerSort;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.user.LoginUser;
import com.lord.common.model.common.CommonQuestion;

import java.util.List;

/**
 * 常见问题common_question的Service
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年06月16日 18:06:42
 */
public interface CommonQuestionService {

    /**
     * 根据主键Id查询
     * @param id    主键Id
     * @return  数据库对象
     */
    CommonQuestion getCommonQuestion(Long id);


    /**
     * 新增或者更新
     * @param pageObj   页面传参
     * @return  数据库对象
     */
    CommonQuestion saveOrUpdate(CommonQuestion pageObj);
    /**
     * 分页查询
     * @param param     查询参数
     * @param page      当前页数
     * @param pageSize  分页大小
     * @return  分页结果
     */
    Pager<CommonQuestion> pageCommonQuestion(CommonQuestion param, int page, int pageSize);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @return  分页结果
     */
    Pager<CommonQuestion> pageCommonQuestion(CommonQuestion param, PagerParam pagerParam);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @param sorts         排序字段
     * @return  分页结果
     */
    Pager<CommonQuestion> pageCommonQuestion(CommonQuestion param, PagerParam pagerParam, PagerSort... sorts);

    /**
     * 物理删除
     * @param ids    主键ID
     */
    void deleteCommonQuestion(Long... ids);


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
     * 查询常见问题
     * @param entityCode    实体编码
     * @param entityId      实体ID
     * @return
     */
    List<CommonQuestion> listQuestion(String entityCode, Long entityId);
}