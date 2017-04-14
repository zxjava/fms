package com.fms.dto;

/**
 * Created by xujiawei on 2015/12/16.
 */
public class PageTO {

    private int total; // 总条数
    private int totalPage; // 总页数
    private int currentPage; // 当前页
    private int pageSize; // 页大小
    private int beginPage;
    private int endPage;


    public int getBeginPage() {
        return beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        if (this.total % this.pageSize == 0)
            this.totalPage = this.total / this.pageSize;
        else
            this.totalPage = (this.total / this.pageSize) + 1;

        int max =5;
        if(this.totalPage<max){
            max=this.totalPage;
        }

        this.calcBeginPage();
        this.calcEndPage();
    }

    private void calcEndPage(){
        if((this.currentPage+2)<=this.totalPage){
            this.endPage=this.currentPage+2;return;
        }else if((this.currentPage+1)<=this.totalPage){
            this.endPage=this.currentPage+1;return;
        }else{
            this.endPage=this.currentPage;return;
        }
    }

    private void calcBeginPage(){
        if((this.currentPage-2)>=1){
            this.beginPage=this.currentPage-2;
            return;
        }else if((this.currentPage-1)>=1){
            this.beginPage=this.currentPage-1;return;
        }else{
            this.beginPage=1;return;
        }
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
