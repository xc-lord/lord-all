package com.lord.biz.dao;

import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

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

    /**
     * 执行原生的Sql
     *
     * @param sql 原生sql
     */
    @Transactional
    public void execute(String sql, Object... params)
    {
        if(params == null) return;
        Query query = entityManager.createNativeQuery(sql);
        for (int i = 0; i < params.length; i++)
        {
            query.setParameter(i+1, params[i]);
        }
        query.executeUpdate();
    }

    public Object selectOne(String sql, Object... params)
    {
        if(params == null) return null;
        Query query = entityManager.createNativeQuery(sql);
        for (int i = 0; i < params.length; i++)
        {
            query.setParameter(i+1, params[i]);
        }
        List list = query.getResultList();
        if (list != null && list.size() > 0)
        {
            return list.get(0);
        }
        return null;
    }

    public List<Map<String, Object>> select(String sql, Object... params)
    {
        Query query = entityManager.createNativeQuery(sql);
        if(params != null)
        {
            for (int i = 0; i < params.length; i++)
            {
                query.setParameter(i + 1, params[i]);
            }
        }
        // 将结果转化为 Map<tableKey, keyValue>
        query.unwrap(org.hibernate.SQLQuery.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }
}
