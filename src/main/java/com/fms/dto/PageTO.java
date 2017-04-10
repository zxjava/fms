package com.fms.dto;

/**
 * Created by xujiawei on 2015/12/16.
 */
public class PageTO {

    private int total; // 总条数
    private int totalPage; // 总页数
    private int currentPage; // 当前页
    private int pageSize; // 页大小

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        if (this.total % this.pageSize == 0)
            this.totalPage = this.total / this.pageSize;
        else
            this.totalPage = (this.total / this.pageSize) + 1;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
