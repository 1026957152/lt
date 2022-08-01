package com.lt.dom.thirdPft;

public enum EnumMethord {

    // 旅投 to 票付通
    LtToPft_Order_ApiTicket("Order.ApiTicket"),
    LtToPft_Order_ApiTicketFail("Order.ApiTicketFail"),
    LtToPft_Order_ApiVerify("Order.ApiVerify"),
    LtToPft_Order_ApiCancel("Order.ApiCancel"),
    LtToPft_Order_ApiGetOrderDetail("Order.ApiGetOrderDetail"),
    LtToPft_Order_ApiGetOrderDetailPro("Order.ApiGetOrderDetailPro"),



    //票付通 to 旅投
    PftToLt_Server_CheckAlive("Server.CheckAlive"),
    PftToLt_Order_Create("Order.Create"),
    PftToLt_Order_Pay("Order.Pay"),
    PftToLt_Order_Cancel("Order.Cancel"),
    PftToLt_Order_ResendMsg("Order.ResendMsg"),
    PftToLt_Order_ApiOrderDetail("Order.ApiOrderDetail"),


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
