package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.OctResp.ComponentRightResp;
import com.lt.dom.OctResp.EnumLongIdResp;
import com.lt.dom.otcenum.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Entity
public class ComponentRight extends Base {   // 这个是 下单的时候， 从 product 中生成 的


    @NotNull
    private String long_desc;
    @NotNull
    private String name_long;

    @NotNull
    private Boolean private_;

    //##@Column(unique=true)

private String code;

    @Enumerated(EnumType.STRING)
    private EnumRoyaltyRuleCategory royalty_mode = EnumRoyaltyRuleCategory.AMOUNT;
    private int value;
    private Long subscription;

    @Enumerated(EnumType.STRING)
    private EnumPrivacyLevel privacy_level;
    @Enumerated(EnumType.STRING)
    @NotNull
    private EnumProductComponentSource source;
    private Long relateRight;
    private String relateRightCode;
    private Long ratePlan;

    public static List<EnumLongIdResp> EnumList(List<ComponentRight> componentRightList) {
        return componentRightList.stream().map(x->{

            EnumLongIdResp enumResp = new EnumLongIdResp();
            enumResp.setId(x.getId());
            //  enumResp.setName(x.name());
            enumResp.setText(x.getName()+"_"+x.getDuration().toString()+"_"+x.getLimit_quantity());
            return enumResp;
        }).collect(Collectors.toList());
    }
    public static List<ComponentRightResp.ComponentRightOption> List(List<ComponentRight> componentRightList, Supplier supplier, Map<Long, List<RoyaltyRule>> royaltyRuleListMap) {
        return componentRightList.stream().map(x->{

            ComponentRightResp.ComponentRightOption enumResp = new ComponentRightResp.ComponentRightOption();
            enumResp.setId(x.getId());
            enumResp.setValidatorWays(EnumValidateWay.EnumList());
            enumResp.setRoyalty_modes(EnumRoyaltyRuleCategory.EnumList());
            if(supplier.getId() == x.getSupplier()){
                enumResp.setOrigin(EnumProductComponentSource.own);
            }else{
                enumResp.setOrigin(EnumProductComponentSource.partner);
            }

            if(royaltyRuleListMap.containsKey(x.getId())){
                List<RoyaltyRule> royaltyRule = royaltyRuleListMap.get(x.getId());

            }
            enumResp.setText(x.getName()+"_"+x.getDuration().toString()+"_"+x.getLimit_quantity());
            return enumResp;
        }).collect(Collectors.toList());
    }

    public static List List(List<ComponentRight> componentRightMap) {
        return componentRightMap.stream().map(x->{

            EnumLongIdResp enumResp = new EnumLongIdResp();
            enumResp.setId(x.getId());

            enumResp.setText(x.getName()+"_"+x.getCode()+"_"+x.getDuration().toString()+"_"+x.getLimit_quantity());
            return enumResp;
        }).collect(Collectors.toList());
    }



    @Transient
    List<RatePlan> ratePlans;

    @NotNull
    private Long supplier;

    public Long getSupplier() {
        return supplier;
    }

    public void setSupplier(Long supplierId) {
        this.supplier = supplierId;
    }

    @Transient
    private RoyaltyRule royaltyRule ;

    public RoyaltyRule getRoyaltyRule() {
        return royaltyRule;
    }

    public void setRoyaltyRule(RoyaltyRule royaltyRule) {
        this.royaltyRule = royaltyRule;
    }


    private String name;
    private String note;

    @Transient
    private List<AccessValidator> accessValidators ;
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

    public List<AccessValidator> getAccessValidators() {
        return accessValidators;
    }

    public void setAccessValidators(List<AccessValidator> accessValidators) {
        this.accessValidators = accessValidators;
    }




    private String additionalInfo;

    private LocalDateTime expiry_datetime;

    @Enumerated(EnumType.STRING)
    private EnumComponentStatus status;


    @Enumerated(EnumType.STRING)
    private EnumDuration duration;

    @NotNull
    private Long limit_quantity;// (integer, required) - Default: null. How many times a voucher can be redeemed. A null value means unlimited.

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public LocalDateTime getExpiry_datetime() {
        return expiry_datetime;
    }

    public void setExpiry_datetime(LocalDateTime expiry_datetime) {
        this.expiry_datetime = expiry_datetime;
    }

    public EnumComponentStatus getStatus() {
        return status;
    }

    public void setStatus(EnumComponentStatus status) {
        this.status = status;
    }

    public EnumDuration getDuration() {
        return duration;
    }

    public void setDuration(EnumDuration duration) {
        this.duration = duration;
    }

    public Long getLimit_quantity() {
        return limit_quantity;
    }

    public void setLimit_quantity(Long quantity) {
        this.limit_quantity = quantity;
    }

    public void setLong_desc(String long_desc) {
        this.long_desc = long_desc;
    }

    public String getLong_desc() {
        return long_desc;
    }

    public void setName_long(String name_long) {
        this.name_long = name_long;
    }

    public String getName_long() {
        return name_long;
    }

    public void setPrivate_(Boolean private_) {
        this.private_ = private_;
    }

    public Boolean getPrivate_() {
        return private_;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public EnumRoyaltyRuleCategory getRoyalty_mode() {
        return royalty_mode;
    }

    public void setRoyalty_mode(EnumRoyaltyRuleCategory royalty_mode) {
        this.royalty_mode = royalty_mode;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setSubscription(Long subscription) {
        this.subscription = subscription;
    }

    public Long getSubscription() {
        return subscription;
    }

    public void setPrivacy_level(EnumPrivacyLevel privacy_level) {
        this.privacy_level = privacy_level;
    }

    public EnumPrivacyLevel getPrivacy_level() {
        return privacy_level;
    }

    public void setSource(EnumProductComponentSource source) {
        this.source = source;
    }

    public EnumProductComponentSource getSource() {
        return source;
    }

    public void setRelateRight(Long relateRight) {
        this.relateRight = relateRight;
    }

    public Long getRelateRight() {
        return relateRight;
    }

    public void setRelateRightCode(String relateRightCode) {
        this.relateRightCode = relateRightCode;
    }

    public String getRelateRightCode() {
        return relateRightCode;
    }

    public Long getRatePlan() {
        return ratePlan;
    }

    public void setRatePlan(Long ratePlan) {
        this.ratePlan = ratePlan;
    }
}
