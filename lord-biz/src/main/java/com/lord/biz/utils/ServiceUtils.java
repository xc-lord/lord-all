package com.lord.biz.utils;

import com.lord.common.constant.PagerDirection;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：业务层的工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年04月02日 14:56
 */
public class ServiceUtils {

    /**
     * 将自定义的排序字段转换为Spring Jpa Data的排序对象
     * @param sorts 自定义的排序字段
     * @return  排序对象
     */
    public static Sort parseSort(PagerSort... sorts) {
        Sort sort = null;
        if (sorts != null) {
            int count = 1;
            for (PagerSort pagerSort : sorts) {
                if (count == 1) {
                    if (pagerSort.getDirection().equals(PagerDirection.DESC)) {
                        sort = new Sort(Sort.Direction.DESC, pagerSort.getParam());
                    } else {
                        sort = new Sort(Sort.Direction.ASC, pagerSort.getParam());
                    }
                } else {
                    if (pagerSort.getDirection().equals(PagerDirection.DESC)) {
                        sort.and(new Sort(Sort.Direction.DESC, pagerSort.getParam()));
                    } else {
                        sort.and(new Sort(Sort.Direction.ASC, pagerSort.getParam()));
                    }
                }
                count++;
            }
        }
        return sort;
    }

    /**
     * 将Spring Jpa Data的分页结果转换成自定义的分页结果
     * @param pageResult    Spring Jpa Data的分页结果
     * @param pagerParam    分页参数
     * @return  自定义的分页结果
     */
    public static Pager toPager(Page pageResult, PagerParam pagerParam) {
        if (pagerParam == null) {
            return new Pager(pagerParam, 0);
        }
        return new Pager(pagerParam, pageResult.getTotalElements(), pageResult.getContent());
    }

    /**
     * 将一条记录的查询结果转换为分页结果
     * @param obj           查询结果
     * @param pagerParam    分页参数
     * @return
     */
    public static Pager toPager(Object obj, PagerParam pagerParam) {
        if (pagerParam == null || obj == null) {
            return new Pager(pagerParam, 0);
        }
        Pager pager = new Pager(pagerParam, 1);
        List list = new ArrayList();
        list.add(obj);
        pager.setList(list);
        return pager;
    }
}
