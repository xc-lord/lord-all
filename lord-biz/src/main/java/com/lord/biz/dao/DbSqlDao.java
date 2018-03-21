package com.lord.biz.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月21日 20:10
 */
@Component
public class DbSqlDao
{
    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 执行原生的Sql
     * @param sql 原生sql
     */
    @Transactional
    public void execute(String sql)
    {
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }
}
