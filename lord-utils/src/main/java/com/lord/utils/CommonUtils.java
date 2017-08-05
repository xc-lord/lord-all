package com.lord.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String dayFormat = "yyyy-MM-dd";

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
    public static Properties readConfigFile(String filePath) {
        Properties property = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = CommonUtils.class.getClassLoader().getResourceAsStream(filePath);
            property.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return property;
    }

    /**
     * 获得classPath下的文件路径
     * @param filePath 文件路径
     * @return 文件路径
     */
    public static String getClassPathFilePath(String filePath) {
        URL url = CommonUtils.class.getClassLoader().getResource(filePath);
        if(url!=null) return url.getPath();
        return null;
    }

    /**
     * 读取classpath下的文件
     * @param filePath  文件路径
     * @return  文件内容
     */
    public static String readClassPathFile(String filePath) {
        InputStream inputStream = null;
        try {
            inputStream = CommonUtils.class.getClassLoader().getResourceAsStream(filePath);
            String content = IOUtils.toString(inputStream, "UTF-8");
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
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

    /**
     * 时间格式化
     * @param date      时间
     * @param format    格式化的格式，默认：yyyy-MM-dd HH:mm:ss
     * @return 格式化的结果
     */
    public static String dateFormat(Date date, String format) {
        if (StringUtils.isEmpty(format)) {
            format = dateFormat;
        }
        SimpleDateFormat sdformat = new SimpleDateFormat(format);
        return sdformat.format(date);
    }

    /**
     * 时间格式化
     * @param date      时间
     * @return 格式化的结果
     */
    public static String dateFormat(Date date) {
        return dateFormat(date, dateFormat);
    }

    /**
     * 时间格式化
     * @param date      日期
     * @return 格式化的结果
     */
    public static String dayFormat(Date date) {
        return dateFormat(date, dayFormat);
    }

    /**
     * 字符串转换为Long列表
     * @param idStr 字符串
     * @param idStr 分隔符
     * @return Long列表
     */
    public static List<Long> parseLongList(String idStr,String spliter)
    {
        if(StringUtils.isEmpty(idStr)) return new ArrayList<>();
        String[] arr = idStr.split(spliter);
        List<Long> list = new ArrayList<>();
        for (String s : arr)
        {
            if(StringUtils.isNotEmpty(s))
            {
                list.add(Long.parseLong(s));
            }
        }
        return list;
    }

    /**
     * 字符串list转换为Long数组
     * @param ids 字符串list
     * @return Long数组
     */
    public static Long[] parseLongArray(List<String> ids)
    {
        Long[] arr = new Long[ids.size()];
        for (int i = 0; i < ids.size(); i++)
        {
            arr[i] = Long.parseLong(ids.get(i));
        }
        return arr;
    }

    /**
     * 去掉末尾的字符串
     * @param source    原字符串
     * @param str       需要去掉的字符串
     * @return  去掉末尾的新字符串
     */
    public static String subEndString(String source, String str)
    {
        if (StringUtils.isEmpty(source))
        {
            return source;
        }
        if (source.endsWith(str))
        {
            source = source.substring(0, source.length() - 1);
        }
        return source;
    }

    /**
     * 获得正则表达式匹配的第一个字符串
     * @param str   原字符串
     * @param regx  正则表达式
     * @return  匹配的第一个字符串
     */
    public static String matchedOne(String str, String regx)
    {
        List<String> list = matchedList(str, regx);
        if(list.size() > 0)
            return list.get(0);
        return null;
    }

    /**
     * 获得正则表达式匹配的字符串列表
     * @param str   原字符串
     * @param regx  正则表达式
     * @return  匹配的字符串列表
     */
    public static List<String> matchedList(String str, String regx)
    {
        List<String> list = new ArrayList<>();
        if(StringUtils.isEmpty(str) || StringUtils.isEmpty(regx)) return list;
        //1.将正在表达式封装成对象Patten 类来实现
        Pattern pattern = Pattern.compile(regx);
        //2.将字符串和正则表达式相关联
        Matcher matcher = pattern.matcher(str);
        //3.String 对象中的matches 方法就是通过这个Matcher和pattern来实现的。
        //查找符合规则的子串
        while (matcher.find())
        {
            list.add(matcher.group());
        }
        return list;
    }

    /**
     * 判断字符串是否匹配正则表达式
     * @param str   原字符串
     * @param regx  正则表达式
     * @return  匹配、不匹配
     */
    public static boolean isMatched(String str, String regx)
    {
        if(StringUtils.isEmpty(str) || StringUtils.isEmpty(regx)) return false;
        //1.将正在表达式封装成对象Patten 类来实现
        Pattern pattern = Pattern.compile(regx);
        //2.将字符串和正则表达式相关联
        Matcher matcher = pattern.matcher(str);
        //3.String 对象中的matches 方法就是通过这个Matcher和pattern来实现的。
        return matcher.matches();
    }

}
