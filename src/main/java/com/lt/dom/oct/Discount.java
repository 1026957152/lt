package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumDiscountEffect;
import com.lt.dom.otcenum.EnumDiscountVoucherCategory;
import com.lt.dom.otcenum.EnumVoucherType;

import javax.persistence.*;
import java.util.List;


@Entity
public class Discount {
    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    private long id  ;

    private int percent_off  ;
    private int amount_off   ;

    private long voucher  ;
    @Transient
    private List units  ;//     ; ADD_MISSING_ITEMS;ADD_NEW_ITEMS;ADD_MANY_ITEMS

    private int unit_off    ;
    private EnumDiscountVoucherCategory type     ;
    private EnumDiscountEffect effect      ;// ADD_MISSING_ITEMS;ADD_NEW_ITEMS;ADD_MANY_ITEMS

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPercent_off() {
        return percent_off;
    }

    public void setPercent_off(int percent_off) {
        this.percent_off = percent_off;
    }

    public int getAmount_off() {
        return amount_off;
    }

    public void setAmount_off(int amount_off) {
        this.amount_off = amount_off;
    }

    public long getVoucher() {
        return voucher;
    }

    public void setVoucher(long voucher) {
        this.voucher = voucher;
    }

    public List getUnits() {
        return units;
    }

    public void setUnits(List units) {
        this.units = units;
    }

    public int getUnit_off() {
        return unit_off;
    }

    public void setUnit_off(int unit_off) {
        this.unit_off = unit_off;
    }

    public EnumDiscountVoucherCategory getType() {
        return type;
    }

    public void setType(EnumDiscountVoucherCategory type) {
        this.type = type;
    }

    public EnumDiscountEffect getEffect() {
        return effect;
    }

    public void setEffect(EnumDiscountEffect effect) {
        this.effect = effect;
    }
}
