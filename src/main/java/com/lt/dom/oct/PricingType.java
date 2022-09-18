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
    private int original; //The original price for this product which will be the same or higher than the sale amount. Use this to show a discount has been applied e.g. $10 $8.50
    private int retail; //The sale price you should charge your customers.
    private int net;  //The wholesale rate the supplier will charge you for this sale.

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public int getOriginal() {
        return original;
    }

    public void setOriginal(int original) {
        this.original = original;
    }

    public int getRetail() {
        return retail;
    }

    public void setRetail(int retail) {
        this.retail = retail;
    }

    public int getNet() {
        return net;
    }

    public void setNet(int net) {
        this.net = net;
    }

    public Long getMin_booking_size() {
        return min_booking_size;
    }

    public void setMin_booking_size(Long min_booking_size) {
        this.min_booking_size = min_booking_size;
    }

    private String lable;
    private String streamSeq;


    private Long min_booking_size;


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

    public void setStreamSeq(String streamSeq) {
        this.streamSeq = streamSeq;
    }

    public String getStreamSeq() {
        return streamSeq;
    }
}
