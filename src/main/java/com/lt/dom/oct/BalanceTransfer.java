package com.lt.dom.oct;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class BalanceTransfer {  //余额结算
    @Version
    private Integer version;
    @Id
    private long id; // 用户余额转账对象 ID，由Ping++ 生成。



/*    object
            string
    值为 "balance_transfer"。
    app
            string
    对应 app 对象的 id，查看 如何获取App ID。*/

    private boolean livemode; //是否是 live 模式。
    private String status;//目前值为转账成功：succeeded。
    private int amount;//接收方收到转账的金额，单位为分。
    private String order_no;//商户订单号，适配每个渠道对此参数的要求，必须在商户的系统内唯一。
    private int user_fee;//向发起转账的用户额外收取的手续费，单位为分，且值需小于 amount。
    private String user;//转出方的 user 对象的 id。
    private String recipient;//接收方的 user 对象的 id。
    private String user_balance_transaction;//转账关联的转出方 balance_transaction 对象的 id。
    private String recipient_balance_transaction;//转账关联的接收方 balance_transaction 对象的 id。
    private String description;//附加说明，最多 60 个 Unicode 字符。
    private String metadata;//详见 元数据。


}
