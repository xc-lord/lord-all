package com.lord.biz.dao.edu;

import com.lord.common.model.edu.EduSchool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 学校edu_school的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年06月06日 10:15:50
 */
@Repository
public interface EduSchoolDao extends JpaRepository<EduSchool, Long>, JpaSpecificationExecutor<EduSchool> {

    @Modifying
    @Query("delete from EduSchool where id in ?1")
    void deleteEduSchool(Long... ids);

    @Query("select u from EduSchool u where u.id in ?1")
    List<EduSchool> findByIds(Long... ids);


    @Modifying
    @Query("update EduSchool u set u.orderValue = ?2 where u.id = ?1")
    void updateOrderValue(Long id, Long orderValue);

	//在此添加你的自定义方法...
}