package com.lt.dom.thirdTS;

public enum Enum是否下单同时支付 {
   // 是否下单同时支付（1:同时支付，0:不支付）缺省时默认1

   // 是否自动设置游玩时间，1:是；0:否

    同时支付(1),
    不支付(1),







            ;

    private Integer id;
    Enum是否下单同时支付(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
