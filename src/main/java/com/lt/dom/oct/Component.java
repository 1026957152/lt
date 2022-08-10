package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumProductComponentSource;

import javax.persistence.*;
import java.util.List;


@Entity
public class Component {   // 这个是 下单的时候， 从 product 中生成 的
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @JsonProperty("id")
    private long id;

    private long royaltyRuleId;
    private int royaltyRuleCount;
    private EnumProductComponentSource supplier;
    private long recipient;

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

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getComponentRightId() {
        return componentRightId;
    }

    public void setComponentRightId(long componentRightId) {
        this.componentRightId = componentRightId;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
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




    private int unit_off;

    public int getUnit_off() {
        return unit_off;
    }

    public void setUnit_off(int unit_off) {
        this.unit_off = unit_off;
    }

    public void setRoyaltyRuleCount(int royaltyRuleCount) {

        this.royaltyRuleCount = royaltyRuleCount;
    }

    public int getRoyaltyRuleCount() {
        return royaltyRuleCount;
    }

    public void setSupplier(EnumProductComponentSource supplier) {
        this.supplier = supplier;
    }

    public EnumProductComponentSource getSupplier() {
        return supplier;
    }

    public void setRecipient(long recipient) {
        this.recipient = recipient;
    }

    public long getRecipient() {
        return recipient;
    }
}
