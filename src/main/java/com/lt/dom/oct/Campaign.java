package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumCampaignCreationStatus;
import com.lt.dom.otcenum.EnumCompaignType;
import com.lt.dom.otcenum.EnumDiscountVoucherCategory;
import com.lt.dom.otcenum.EnumVoucherType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Campaign {


    @GeneratedValue(strategy = GenerationType.IDENTITY)

   // @GeneratedValue//(strategy = GenerationType.AUTO)
    @Id
    private long id;

    private EnumCompaignType campaignType;


    private String name;

    @NotNull
    private long scenario;
    private String category;
    private String code;

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

    private LocalDate start_date;
    private LocalDate expiration_date;



    private int vouchers_count;
    private EnumCampaignCreationStatus vouchers_generation_status; //IN_PROGRESS, DONE, ERROR
    private String description;
    private boolean active;


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

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(LocalDate expiration_date) {
        this.expiration_date = expiration_date;
    }


    public int getVouchers_count() {
        return vouchers_count;
    }

    public void setVouchers_count(int vouchers_count) {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
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
}
