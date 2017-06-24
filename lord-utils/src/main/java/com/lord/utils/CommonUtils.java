package com.lord.utils;

import java.util.Properties;
import java.util.Random;
import java.util.UUID;

/**
 * 功能：公共工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年03月2017/3/30日
 */
public class CommonUtils {
    final static int[] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE };

    /**
     * 快速判断一个int值是几位数
     * @param x int值
     * @return
     */
    public static int sizeOfInt(int x) {
        for (int i = 0;; i++)
            if (x <= sizeTable[i])
                return i + 1;
    }

    /**
     * 读取配置文件
     * @param filePath  配置文件路径（默认搜寻路径为classpath路径下）
     * @return  返回配置属性键值对
     */
    public static Properties readConfigFile(String filePath) throws java.io.IOException {
        Properties property = new Properties();
        property.load(CommonUtils.class.getClassLoader().getResourceAsStream(filePath));
        return property;
    }

    /**
     * 获得32位唯一码
     * @return
     */
    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        s = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
        return s;
    }

    /**
     * 生成一个字段范围的随机数
     * @param min   最小
     * @param max   最大
     * @return
     */
    public static int genRandom(int min, int max) {
        Random random = new Random();
        int sourceDate = random.nextInt(max) % (max - min + 1) + min;//获取指定范围的随机数
        return sourceDate;
    }

    /**
     * 获得指定位数的随机数字符串
     * @param stringSize    位数
     * @return
     */
    public static String genRandom(int stringSize) {
        int num = genRandom(0, (int) (Math.pow(10, stringSize) - 1));
        return fillZero(num, stringSize);
    }

    /**
     * 数字前面补0，变成字符串，128->00128
     * @param num            数字
     * @param stringSize    字符串长度
     * @return
     */
    public static String fillZero(int num, int stringSize) {
        /**
         *  0 指前面补充零
         *  stringSize 字符总长度为 stringSize
         *  d 代表为正数。
         */
        String newString = String.format("%0" + stringSize + "d", num);
        return newString;
    }
}
