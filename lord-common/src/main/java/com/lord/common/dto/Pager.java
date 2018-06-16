package com.lord.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 分页信息工具类
 * @author xiaocheng
 * @version 1.0
 */
public class Pager<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 总的记录数
     */
    private Long totalRows;
    /**
     * 每页的大小
     */
    private Integer pageSize = 10;
    /**
     * 当前页数
     */
    private Integer page = 1;
    /**
     * 总页数
     */
    private Integer totalPage = 0;
    /**
     * 分页记录开始的行数
     */
    private Integer startRow = 0;
    /**
     * 当前页中的数据集
     */
    private List<T> list;

    /**
     * 是否需要分页
     */
    private boolean needPaging;
    /**
     * 是否有上一页
     */
    private boolean hasPrev;
    /**
     * 是否有下一页
     */
    private boolean hasNext;

    private List<Integer> pageNavs;

    public Pager(PagerParam pagerParam, Integer totalRows) {
        this(pagerParam.getPage(), pagerParam.getPageSize(), totalRows);
    }

    public Pager(PagerParam pagerParam, Long totalRows) {
        this(pagerParam.getPage(), pagerParam.getPageSize(), totalRows);
    }

    public Pager(PagerParam pagerParam, Long totalRows, List<T> list) {
        this(pagerParam.getPage(), pagerParam.getPageSize(), totalRows, list);
    }

    public Pager(Integer page, Integer totalRows) {
        this(page, 10, totalRows);
    }

    public Pager(Integer page, Long totalRows) {
        this(page, 10, totalRows);
    }

    public Pager(Integer page, Long totalRows, List<T> list) {
        this(page, 10, totalRows, list);
    }

    public Pager(Integer page, Integer pageSize, Integer totalRows) {
        this(page, pageSize, totalRows.longValue());
    }

    public Pager(Integer page, Integer pageSize, Long totalRows) {
        this(page, pageSize, totalRows, null);
    }

    public Pager(Integer page, Integer pageSize, Long totalRows, List<T> list) {
        this.totalRows = totalRows;
        this.page = page;
        this.pageSize = pageSize;
        this.list = list;
        init();
    }

    public void init() {
        if (this.totalRows != null && this.totalRows > 0L) {
            //计算总的页数
            Long varTotalPageSize = this.totalRows / this.pageSize;
            if (this.totalRows % this.pageSize > 0L) {
                varTotalPageSize = varTotalPageSize + 1L;
            }
            this.totalPage = varTotalPageSize.intValue();

            //计算分页开始记录数
            if (this.page != null) {
                if (this.page <= 0)
                    this.startRow = 0;
                else if (this.page > this.totalPage)
                    this.startRow = this.totalPage * this.pageSize;
                else
                    this.startRow = (this.page - 1) * this.pageSize;
            } else {
                this.startRow = 0;
            }
        }

        //计算是否需要分页
        if (this.totalPage == null)
            this.needPaging = false;
        else if (this.totalPage < 2)
            this.needPaging = false;
        else {
            this.needPaging = true;
        }

        //计算是否有上一页和下一页
        if (!needPaging) {
            this.hasPrev = false;
            this.hasNext = false;
        } else if (this.page != null) {
            if (this.page >= this.totalPage) {
                this.page = this.totalPage;
                this.hasPrev = true;
                this.hasNext = false;
            } else if (this.page <= 1) {
                this.page = 1;
                this.hasPrev = false;
                this.hasNext = true;
            } else {
                this.hasPrev = true;
                this.hasNext = true;
            }
        } else {
            this.hasPrev = false;
            this.hasNext = true;
        }
    }

    public Long getTotalRows() {
        return this.totalRows;
    }

    public void setTotalRows(Long totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPage() {
        return this.totalPage;
    }

    public Integer getStartRow() {
        return this.startRow;
    }

    public boolean isHasPrev() {
        return this.hasPrev;
    }

    public boolean isHasNext() {
        return this.hasNext;
    }

    public boolean isNeedPaging() {
        return this.needPaging;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public List<Integer> getPageNavs()
    {
        return pageNavs;
    }

    public void setPageNavs(List<Integer> pageNavs)
    {
        this.pageNavs = pageNavs;
    }

    @Override
    public String toString() {
        StringBuffer msg = new StringBuffer();
        msg.append("分页信息Pager： 总页数=").append(totalPage).append(" 总记录数=").append(totalRows)
                .append(" 当前页=").append(page);
        return msg.toString();
    }
}