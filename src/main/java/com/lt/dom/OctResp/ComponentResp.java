package com.lt.dom.OctResp;

import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumDuration;
import com.lt.dom.otcenum.EnumProductComponentSource;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

public class ComponentResp{
        private String name;
        private String note;
    private String duration_text;
    private String source_text;
    private String long_name;
    private String long_desc;

    public static ComponentResp from(Component x) {
        ComponentResp componentResp = new ComponentResp();
        componentResp.setComponentRight(x.getComponentRightId());
        componentResp.setDuration(x.getDuration());
        componentResp.setDuration_text(x.getDuration().toString());
        componentResp.setName(x.getName());
        componentResp.setProduct(x.getProduct());
        componentResp.setRecipient(x.getRecipient());
        componentResp.setNote(x.getNote());
        componentResp.setPriceingType(x.getPriceingType());
        componentResp.setSource(x.getSource());
        componentResp.setSource_text(x.getSource().toString());
        componentResp.setUnit_off(x.getUnit_off());
        componentResp.setSupplier(x.getSupplier());
        componentResp.setRoyaltyRule(x.getRoyaltyRule());
        componentResp.setRoyaltyPercent(x.getRoyaltyPercent());
        componentResp.setRoyaltyRuleCount(x.getRoyaltyRuleCount());

        return componentResp;
    }


    private long royaltyRuleId;
    private int royaltyRuleCount;
    @NotNull
    private EnumProductComponentSource source;

    public static ComponentResp from(Component x, ComponentRight componentRight) {
        ComponentResp componentResp = ComponentResp.from(x);
        componentResp.setLong_name(componentRight.getName_long());
        componentResp.setLong_desc(componentRight.getLong_desc());
        return null;
    }

    public EnumProductComponentSource getSource() {
        return source;
    }

    public void setSource(EnumProductComponentSource source) {
        this.source = source;
    }

    @NotNull
    private Long supplier;
    private long recipient;

    @NotNull
    private EnumDuration duration;
    private int royaltyPercent;
    private long priceingType;





    private long supplierId;

    private long productId;
    private long componentRightId;

    public long getProductId() {
        return productId;
    }

    public void setProduct(long productId) {
        this.productId = productId;
    }

    public long getComponentRightId() {
        return componentRightId;
    }

    public void setComponentRight(long componentRightId) {
        this.componentRightId = componentRightId;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplier(long supplierId) {
        this.supplierId = supplierId;
    }

    @Transient
    private RoyaltyRule royaltyRule ;

    public RoyaltyRule getRoyaltyRule() {
        return royaltyRule;
    }

    public void setRoyaltyRule(RoyaltyRule royaltyRule) {
        this.royaltyRule = royaltyRule;
    }







    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }



    private Long unit_off;

    public Long getUnit_off() {
        return unit_off;
    }

    public void setUnit_off(Long unit_off) {
        this.unit_off = unit_off;
    }

    public void setRoyaltyRuleCount(int royaltyRuleCount) {

        this.royaltyRuleCount = royaltyRuleCount;
    }

    public int getRoyaltyRuleCount() {
        return royaltyRuleCount;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public Long getSupplier() {
        return supplier;
    }

    public void setRecipient(long recipient) {
        this.recipient = recipient;
    }

    public long getRecipient() {
        return recipient;
    }

    public void setDuration(EnumDuration duration) {
        this.duration = duration;
    }

    public EnumDuration getDuration() {
        return duration;
    }

    public void setRoyaltyPercent(int royaltyPercent) {
        this.royaltyPercent = royaltyPercent;
    }

    public int getRoyaltyPercent() {
        return royaltyPercent;
    }

    public void setPriceingType(long priceingType) {
        this.priceingType = priceingType;
    }

    public long getPriceingType() {
        return priceingType;
    }

    public void setDuration_text(String duration_text) {
        this.duration_text = duration_text;
    }

    public String getDuration_text() {
        return duration_text;
    }

    public void setSource_text(String source_text) {
        this.source_text = source_text;
    }

    public String getSource_text() {
        return source_text;
    }

    public void setLong_name(String long_name) {
        this.long_name = long_name;
    }

    public String getLong_name() {
        return long_name;
    }

    public void setLong_desc(String long_desc) {
        this.long_desc = long_desc;
    }

    public String getLong_desc() {
        return long_desc;
    }
}