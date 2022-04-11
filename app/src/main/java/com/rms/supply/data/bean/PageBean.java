package com.rms.supply.data.bean;

import com.drake.brv.item.ItemHover;

import java.util.List;

public class PageBean implements ItemHover {
    public int pageNum;
    public List<OrderBean> orderBeanList;

    public PageBean(int pageNum, List<OrderBean> orderBeanList) {
        this.pageNum = pageNum;
        this.orderBeanList = orderBeanList;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<OrderBean> getOrderBeanList() {
        return orderBeanList;
    }

    public void setOrderBeanList(List<OrderBean> orderBeanList) {
        this.orderBeanList = orderBeanList;
    }

    @Override
    public boolean getItemHover() {
        return true;
    }

    @Override
    public void setItemHover(boolean b) {

    }
}
