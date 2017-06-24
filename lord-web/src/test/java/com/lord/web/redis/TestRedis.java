package com.lord.web.redis;

import com.lord.web.Application;
import com.lord.web.controller.IndexController;
import com.lord.web.task.async.TestTask;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.Future;

/**
 * Created by xiaocheng on 2017/3/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestRedis {

    @Autowired
    private IndexController indexController;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testHello() {
        indexController.home();
    }

    @Test
    public void test() throws Exception {
        // 保存字符串
        String key = "lord-test-key";
        stringRedisTemplate.opsForValue().set(key, "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get(key));
    }

    @Autowired
    private TestTask task;

    @Test
    public void testTask() throws Exception {
        long start = System.currentTimeMillis();
        Future<String> task1 = task.doTaskOne();
        Future<String> task2 = task.doTaskTwo();
        Future<String> task3 = task.doTaskThree();
        while(true) {
            if(task1.isDone() && task2.isDone() && task3.isDone()) {
                // 三个任务都调用完成，退出循环等待
                break;
            }
            Thread.sleep(1000);
        }
        long end = System.currentTimeMillis();
        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");
    }
}
