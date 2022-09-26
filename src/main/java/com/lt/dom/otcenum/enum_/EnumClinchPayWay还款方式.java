package com.lt.dom.otcenum.enum_;

public enum EnumClinchPayWay还款方式 {
    信用(1),
    保证(2),
    质押(3),
    抵押(4),
    其他(5);


    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    EnumClinchPayWay还款方式(Integer id){
        this.id = id;
    }
}
