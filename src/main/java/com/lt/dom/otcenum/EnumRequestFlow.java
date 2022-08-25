package com.lt.dom.otcenum;

public enum EnumRequestFlow {

    商户初次提交("supplier_first_request"),
    旅投初次审核("admin_first_approve"),
    商户二次提交("supplier_second_request"),
    旅投二次审核("admin_second_approve"),
    文旅局审核("goverment_approve"),
    ;

    public String id;

    EnumRequestFlow(String id) {

        this.id = id;
    }


}
