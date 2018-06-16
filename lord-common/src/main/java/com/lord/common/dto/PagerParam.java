package com.lord.common.dto;

import java.io.Serializable;

/**
 * 分页参数
 */
public class PagerParam implements Serializable {
    /** 每页的大小 */
    private Integer pageSize = 10;
    /** 当前页数 */
    private Integer page = 1;
    /** 开始行数 */
    private Integer startRow = 0;

    public PagerParam(){}

    public PagerParam(Integer page) {
        this.page = page;
        initStartRow();
    }

    public PagerParam(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
        initStartRow();
    }

    private void initStartRow() {
        if(page == null || page < 1)
            page = 1;
        if(pageSize == null || pageSize < 1)
            pageSize = 10;
        startRow = (page - 1) * pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        initStartRow();
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
        initStartRow();
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }
}
