package com.lt.dom.otcenum.enum_;

public enum EnumBusinessType业务类型 {
    直租(1),
    回租(2),
    其它(3);


    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    EnumBusinessType业务类型(Integer id){
        this.id = id;
    }
}
