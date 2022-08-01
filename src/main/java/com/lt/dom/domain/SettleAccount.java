package com.lt.dom.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SettleAccount {

    @Id
    private String id;//	用户结算账户对象 ID。
    private String object;//	值为 "settle_account"。
    private String  created;//	创建时的 Unix 时间戳。timestamp
    private String channel;//	结算账号渠道名称，目前支持：alipay（支付宝）、wx（微信 App）、wx_pub（微信 JSAPI）、wx_lite（微信小程序）、bank_account（银行卡）。
    private String recipient;//	脱敏的结算账号接收者信息，详情参见请求参数 recipient 部分。object
    private long supplierId;

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public long getSupplierId() {
        return supplierId;
    }


/*    {
        "id": "320217022818035400000601",
            "object": "settle_account",
            "created": 1488276234,
            "livemode": true,
            "channel": "bank_account",
            "recipient": {
        "open_bank_code": "0102",
                "account": "622262***2145",
                "name": "***哥"
    }
    }*/
}
