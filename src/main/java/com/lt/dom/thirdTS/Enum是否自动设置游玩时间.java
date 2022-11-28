package com.lt.dom.thirdTS;

public enum Enum是否自动设置游玩时间 {


   // 是否自动设置游玩时间，1:是；0:否

    是("1"),
    否("0"),







            ;

    private String name;
    Enum是否自动设置游玩时间(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
