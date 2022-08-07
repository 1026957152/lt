package com.lt.dom.vo;

public class LogVo {
    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LogVo(int i, String username) {

        this.type = i;
        this.name = username;
    }
}
