package com.lord.common.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 功能：Redis操作接口
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月02日 17:30
 */
public interface RedisService
{
    void set(String key, Object obj);

    void set(String key, Object obj, long timeout, TimeUnit timeUnit);

    void set(String key, Object obj, long timeout);

    String get(String key);

    <T> T get(String key, Class<T> clz);

    <T> List<T> getList(String key, Class<T> clz);

    void delete(String key);

    void expire(String key, Long timeOut);

    Set<String> keys(String pattern);

}
