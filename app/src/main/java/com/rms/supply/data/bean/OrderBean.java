package com.rms.supply.data.bean;

public class OrderBean {
    private String name;
    private int orgNum;
    private int editNum;

    public OrderBean(String name, int orgNum, int editNum) {
        this.name = name;
        this.orgNum = orgNum;
        this.editNum = editNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrgNum() {
        return orgNum;
    }

    public void setOrgNum(int orgNum) {
        this.orgNum = orgNum;
    }

    public int getEditNum() {
        return editNum;
    }

    public void setEditNum(int editNum) {
        this.editNum = editNum;
    }
}
