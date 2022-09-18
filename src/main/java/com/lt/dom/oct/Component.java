package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
public class Component {   // 这个是 下单的时候， 从 product 中生成 的
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonProperty("id")
    private long id;

    private long royaltyRuleId;
    private int royaltyRuleCount;
    @NotNull
    private EnumProductComponentSource source;

    @NotNull
    private EnumValidateWay validate_way;

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




    public long getId() {
        return id;
    }




    @Transient
    List<RatePlan> ratePlans;

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

    public void setId(long id) {
        this.id = id;
    }

    private String name;
    private String note;

    @Transient
    private AccessValidator accessValidator ;
    private long accessValidatorId;

    public long getAccessValidatorId() {
        return accessValidatorId;
    }

    public void setAccessValidatorId(long accessValidatorId) {
        this.accessValidatorId = accessValidatorId;
    }

    @Transient
    List<ComponentVounch> componentVounches;


    public List<ComponentVounch> getComponentRightVounches() {
        return componentVounches;
    }

    public void setComponentRightVounches(List<ComponentVounch> componentVounches) {
        this.componentVounches = componentVounches;
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

    public AccessValidator getAccessValidator() {
        return accessValidator;
    }

    public void setAccessValidator(AccessValidator accessValidator) {
        this.accessValidator = accessValidator;
    }




    @NotNull
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


    public void setValidate_way(EnumValidateWay validate_way) {
        this.validate_way = validate_way;
    }

    public EnumValidateWay getValidate_way() {
        return validate_way;
    }
}
