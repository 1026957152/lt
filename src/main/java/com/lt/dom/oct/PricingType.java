package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumProductPricingType;
import com.lt.dom.otcenum.EnumProductPricingTypeByPerson;

import javax.persistence.*;

@Entity
public class PricingType {
    @Version
    private Integer version;


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private long productId;

    private EnumProductPricingType type;
    private EnumProductPricingTypeByPerson by;
    private int min;
    private int max;
    private String unit; // per or total
    private int price;
    private String lable;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EnumProductPricingType getType() {
        return type;
    }

    public void setType(EnumProductPricingType type) {
        this.type = type;
    }

    public EnumProductPricingTypeByPerson getBy() {
        return by;
    }

    public void setBy(EnumProductPricingTypeByPerson by) {
        this.by = by;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }
}
