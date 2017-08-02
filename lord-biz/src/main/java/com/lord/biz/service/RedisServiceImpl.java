package com.lord.biz.service;

import com.alibaba.fastjson.JSON;
import com.lord.common.service.RedisService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 功能：Redis操作实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月02日 17:32
 */
@Service
public class RedisServiceImpl implements RedisService
{
    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void set(final String key, final Object obj)
    {
        if (obj instanceof String)
        {
            stringRedisTemplate.opsForValue().set(key, (String) obj);
        }
        String json = JSON.toJSONString(obj);
        stringRedisTemplate.opsForValue().set(key, json);
    }

    public void set(final String key, final Object obj, final long timeout, final TimeUnit timeUnit)
    {
        if (obj instanceof String)
        {
            stringRedisTemplate.opsForValue().set(key, (String) obj, timeout, timeUnit);
        }
        String json = JSON.toJSONString(obj);
        stringRedisTemplate.opsForValue().set(key, json, timeout, timeUnit);
    }

    public String get(final String key)
    {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public <T> T get(final String key, Class<T> clz)
    {
        String str = get(key);
        if(StringUtils.isEmpty(str)) return null;
        return JSON.parseObject(str, clz);
    }

    public <T> List<T> getList(String key, Class<T> clz) {
        String json = get(key);
        if (json != null)
        {
            List<T> list = JSON.parseArray(json, clz);
            return list;
        }
        return null;
    }

    @Override
    public void delete(String key)
    {
        stringRedisTemplate.delete(key);
    }

    @Override
    public Set<String> keys(String pattern)
    {
        return stringRedisTemplate.keys(pattern);
    }
}
