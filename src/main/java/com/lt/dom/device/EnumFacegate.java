package com.lt.dom.device;

public enum EnumFacegate {
    GetSysParam("GetSysParam"),
    DeletePerson("DeletePerson"),
    Subscribe("Subscribe"),



    IDCardInfoPush("IDCardInfoPush"),
    CardVerify("CardVerify"),//IC 卡号认证结果上报(门禁机)

    PwdVerify("PwdVerify"),//密码方式认证结果推送

    ;


    EnumFacegate(String barcode) {

    }
}
