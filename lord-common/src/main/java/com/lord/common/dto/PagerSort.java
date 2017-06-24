package com.lord.common.dto;

import com.lord.common.constant.PagerDirection;

import java.io.Serializable;

/**
 * 功能：排序时使用的对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年04月02日 14:56
 */
public class PagerSort implements Serializable {
    private static final long serialVersionUID = 1L;

    private String param;
    private PagerDirection direction = PagerDirection.ASC;

    public PagerSort() {
    }

    public PagerSort(String param) {
        this.param = param;
    }

    public PagerSort(String param, PagerDirection direction) {
        this.param = param;
        this.direction = direction;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public PagerDirection getDirection() {
        return direction;
    }

    public void setDirection(PagerDirection direction) {
        this.direction = direction;
    }
}
