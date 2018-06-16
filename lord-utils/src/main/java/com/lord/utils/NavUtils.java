package com.lord.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年06月15日 20:30
 */
public class NavUtils
{
    private static int before = 2;//当前页数前面显示多少位分页数
    private static int after = 3;//当前页数后面显示多少位分页数
    private static int sp = 1;//页头多少位分页数
    private static int ep = 1;//页尾多少位分页数

    /**
     * 返回省略号样式的分页代码
     * @param page          当前页数
     * @param totalPage     总页数
     * @return  页数集合,用0表示省略号
     */
    public static List<Integer> getPageNav(int page, int totalPage) {
        int[] arr = new int[before + after + 1];
        int s = page - before;
        List<Integer> list = new ArrayList<>();

        int all = before + after + 1 + sp + ep;
        if(totalPage <= all) {
            for (int i = 0; i < totalPage; i++) {
                list.add(i+1);
            }
            return list;
        }

        for (int i = 0; i < arr.length; i++) {
            arr[i] = s;
            s++;
        }

        for (int i = 0; i < sp; i++) {
            list.add(i + 1);
        }

        if (arr[0] > sp + 1) {
            list.add(0);
        }

        for (int i = 0; i < arr.length; i++) {
            int n = arr[i];
            if(n > 0 && n <= totalPage && !list.contains(n)) {
                list.add(n);
            }
        }

        int end = arr[arr.length - 1];
        if (totalPage - ep > end) {
            list.add(0);
        }
        for (int i = totalPage - ep; i < totalPage; i++) {
            int e = i + 1;
            if (!list.contains(e))
                list.add(e);
        }

        return list;
    }

    public static void main(String[] args) {
        display(1, 1);
        display(11, 1);
        display(12, 8);
        display(12, 7);
        display(12, 9);
        display(18, 9);
    }

    private static void display(int totalPage, int page) {
        System.out.println();
        System.out.println("总页数:" + totalPage);
        System.out.println("当前页:" + page);

        List<Integer> list = NavUtils.getPageNav(page, totalPage);

        for (Integer s : list) {
            if(s == 0) {
                System.out.print("...\t");
                continue;
            }
            System.out.print(s + "\t");
        }
    }
}
