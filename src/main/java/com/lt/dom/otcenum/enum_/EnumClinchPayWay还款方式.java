package com.lt.dom.otcenum.enum_;

public enum EnumClinchPayWay还款方式 {
    等额本息(1),
    等额本金(2),
    一次结清(3),
    随借随还(4),
    月息结算(5),
    先息后本(4);

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
