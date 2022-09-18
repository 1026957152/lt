package com.lt.dom.oct;

import com.lt.dom.OctResp.EnumLongIdResp;
import com.lt.dom.otcenum.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Campaign {

    @CreatedDate
    LocalDateTime created_at ;

    @LastModifiedDate
    LocalDateTime updated_at ;
    private long total_unpublished;

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    @NotNull
    private EnumCampaignStatus status;

    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)

   // @GeneratedValue//(strategy = GenerationType.AUTO)
    @Id
    private long id;
    @Column(unique=true)
    private String code;
    private EnumCompaignType campaignType;

 /*   @Generated(GenerationTime.INSERT)
    @Column(name = "internal_id", columnDefinition = "serial", updatable = false)*/
    private long seq;

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    private String name;

    @NotNull
    private long scenario;
    private String category;
    

    private boolean claimable;
    private String claim_note;
    private String claim_text;
    private int clain_limit;
    private boolean over;

    @Column(name = "limit_")
    private int limit;

    @NotNull
    private Boolean pay;
    private Integer payAmount;
    private String name_long;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getScenario() {
        return scenario;
    }

    public void setScenario(long scenario) {
        this.scenario = scenario;
    }

    private LocalDateTime start_date;
    private LocalDateTime expiration_date;

    private int expiry_days;

    public int getExpiry_days() {
        return expiry_days;
    }

    public void setExpiry_days(int expiry_days) {
        this.expiry_days = expiry_days;
    }

    private Long vouchers_count;
    private EnumCampaignCreationStatus vouchers_generation_status; //IN_PROGRESS, DONE, ERROR
    private String description;

    @NotNull
    private Boolean active;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EnumCompaignType getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(EnumCompaignType campaignType) {
        this.campaignType = campaignType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public LocalDateTime getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(LocalDateTime expiration_date) {
        this.expiration_date = expiration_date;
    }


    public Long getVoucher_count() {
        return vouchers_count;
    }

    public void setVouchers_count(Long vouchers_count) {
        this.vouchers_count = vouchers_count;
    }


    public EnumCampaignCreationStatus getVouchers_generation_status() {
        return vouchers_generation_status;
    }

    public void setVouchers_generation_status(EnumCampaignCreationStatus vouchers_generation_status) {
        this.vouchers_generation_status = vouchers_generation_status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }



    private  int length ;
    private  String charset;
    private  String prefix;
    private  String postfix;
    private  String pattern;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }










    private EnumVoucherType vouchertype;
    private int amount_off;
    private int percent_off;
    private int unit_off;
    private EnumDiscountVoucherCategory discountCategory;

    public EnumVoucherType getVouchertype() {
        return vouchertype;
    }

    public void setVouchertype(EnumVoucherType vouchertype) {
        this.vouchertype = vouchertype;
    }

    public int getAmount_off() {
        return amount_off;
    }

    public void setAmount_off(int amount_off) {
        this.amount_off = amount_off;
    }

    public int getPercent_off() {
        return percent_off;
    }

    public void setPercent_off(int percent_off) {
        this.percent_off = percent_off;
    }

    public int getUnit_off() {
        return unit_off;
    }

    public void setUnit_off(int unit_off) {
        this.unit_off = unit_off;
    }

    public EnumDiscountVoucherCategory getDiscountCategory() {
        return discountCategory;
    }

    public void setDiscountCategory(EnumDiscountVoucherCategory discountCategory) {
        this.discountCategory = discountCategory;
    }


    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }











    private long total_published;

    public long getTotal_published() {
        return total_published;
    }

    public void setTotal_published(long total_published) {
        this.total_published = total_published;
    }

    private long total_redeemed;
    private long total_failed;
    private long total_succeeded;
    private long total_rolled_back;
    private long total_rollback_failed;
    private long total_rollback_succeeded;

    public long getTotal_redeemed() {
        return total_redeemed;
    }

    public void setTotal_redeemed(long total_redeemed) {
        this.total_redeemed = total_redeemed;
    }

    public long getTotal_failed() {
        return total_failed;
    }

    public void setTotal_failed(long total_failed) {
        this.total_failed = total_failed;
    }

    public long getTotal_succeeded() {
        return total_succeeded;
    }

    public void setTotal_succeeded(long total_succeeded) {
        this.total_succeeded = total_succeeded;
    }

    public long getTotal_rolled_back() {
        return total_rolled_back;
    }

    public void setTotal_rolled_back(long total_rolled_back) {
        this.total_rolled_back = total_rolled_back;
    }

    public long getTotal_rollback_failed() {
        return total_rollback_failed;
    }

    public void setTotal_rollback_failed(long total_rollback_failed) {
        this.total_rollback_failed = total_rollback_failed;
    }

    public long getTotal_rollback_succeeded() {
        return total_rollback_succeeded;
    }

    public void setTotal_rollback_succeeded(long total_rollback_succeeded) {
        this.total_rollback_succeeded = total_rollback_succeeded;
    }

    public void setClaimable(boolean claimable) {
        this.claimable = claimable;
    }

    public boolean getClaimable() {
        return claimable;
    }

    public void setClaim_note(String claim_note) {
        this.claim_note = claim_note;
    }

    public String getClaim_note() {
        return claim_note;
    }

    public void setClaim_text(String claim_text) {
        this.claim_text = claim_text;
    }

    public String getClaim_text() {
        return claim_text;
    }

    public void setClain_limit(int clain_limit) {
        this.clain_limit = clain_limit;
    }

    public int getClain_limit() {
        return clain_limit;
    }

    public void setStatus(EnumCampaignStatus status) {
        this.status = status;
    }

    public EnumCampaignStatus getStatus() {
        return status;
    }

    public void setPay(Boolean pay) {
        this.pay = pay;
    }

    public Boolean getPay() {
        return pay;
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getPayAmount() {
        return payAmount;
    }

    public String getName_long() {
        return name_long;
    }

    public void setName_long(String name_long) {
        this.name_long = name_long;
    }

    public long getTotal_unpublished() {
        return total_unpublished;
    }

    public void setTotal_unpublished(long total_unpublished) {
        this.total_unpublished = total_unpublished;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public static List<EnumLongIdResp> EnumList(List<Campaign> componentRightList) {
        return  componentRightList.stream().map(x->{
            EnumLongIdResp enumResp = new EnumLongIdResp();
            enumResp.setId(x.getId());
            enumResp.setText(x.getName());
            return enumResp;
        }).collect(Collectors.toList());
    }
}
