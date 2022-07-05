package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;
@Entity
public class ComponentRightVounch {   // 这个是 下单的时候， 从 product 中生成 的
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @JsonProperty("id")
    private long id;

    @Transient
    private ComponentRight componentRight;

    private long componentRightId;

    public long getComponentRightId() {
        return componentRightId;
    }

    public void setComponentRightId(long componentRightId) {
        this.componentRightId = componentRightId;
    }

    private String name;
    private EnumVoucherRedemptionStatus voucher_redemption_status;
    private String redeem_voucher_key;
    @Transient
    private Voucher voucher;
    private long voucherId;

    public long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(long voucherId) {
        this.voucherId = voucherId;
    }

    @Transient
    private Product product;


    private String note;


    private int count;  //一次， 无数次，  五次，

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
