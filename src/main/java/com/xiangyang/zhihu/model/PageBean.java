package com.xiangyang.zhihu.model;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
public class PageBean {
    private int start;
    private int size;
    private  int page;

    public int getStart() {
        return (page-1)*size;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
