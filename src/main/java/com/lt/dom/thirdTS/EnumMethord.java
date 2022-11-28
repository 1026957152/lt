package com.lt.dom.thirdTS;

public enum EnumMethord {

    // 旅投 to 票付通
    item_list("item_list"),
    item_orders("item_orders"),




    item_refund("item_refund"),


    item_orders_modify("Order.ApiCancel"),



    validate("验证核销通知(第三方通知天时同城)"),



            ;

    private String name;
    EnumMethord(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
