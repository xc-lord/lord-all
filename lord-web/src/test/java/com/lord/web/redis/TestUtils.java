package com.lord.web.redis;

import org.junit.Test;

/**
 * Created by xiaocheng on 2017/3/28.
 */
public class TestUtils {

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            int num = randomNum(0, 1);
            System.out.println(num);
        }
    }

    @Test
    public void test2() {
        for (int i = 0; i < 10; i++) {
            int count = 5;
            int size = 3;
            int num = randomNum(0, count-size);
            System.out.println(num);
        }
    }

    /**
     * 随机生成一定范围的随机数
     * @param start	开始
     * @param end	结束
     * @return
     */
    public static Integer randomNum(int start, int end) {
        int num = (int)(start+Math.random()*(end-start+1));
        return num;
    }
}
