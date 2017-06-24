package com.lord.utils;

import org.junit.Test;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年06月24日 18:28
 */
public class TestHttpUtils {

    @Test
    public void testHttp() {
        String res = HttpUtils.doGet("http://www.baidu.com/");
        System.out.println("响应内容：\n" + res);
    }
}
