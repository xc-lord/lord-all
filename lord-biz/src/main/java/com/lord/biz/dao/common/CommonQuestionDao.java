package com.lord.biz.dao.common;

import com.lord.common.model.common.CommonQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 常见问题common_question的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年06月16日 18:06:42
 */
@Repository
public interface CommonQuestionDao extends JpaRepository<CommonQuestion, Long>, JpaSpecificationExecutor<CommonQuestion> {

    @Modifying
    @Query("delete from CommonQuestion where id in ?1")
    void deleteCommonQuestion(Long... ids);

    @Query("select u from CommonQuestion u where u.id in ?1")
    List<CommonQuestion> findByIds(Long... ids);

    @Modifying
    @Query("update CommonQuestion u set u.orderValue = ?2 where u.id = ?1")
    void updateOrderValue(Long id, Long orderValue);

    @Query("select u from CommonQuestion u where u.entityCode=?1 and entityId=?2")
    List<CommonQuestion> listQuestion(String entityCode, Long entityId);

}