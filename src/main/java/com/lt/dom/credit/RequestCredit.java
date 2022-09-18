package com.lt.dom.credit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Entity
public class RequestCredit {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;


    private String creditSHXYDM统一社会信用代码;
    private String credit_NSRMC_企业名称;


    private String credit_leader_phone_联系人电话;
    private String credit_leader_name_联系人姓名;
    private String credit_bussiness_location_注册地址;
    private String credit_bussiness_经营范围;
    private String credit_行业类别;
    private String credit_product_type_信贷产品类型;
    

private String code;

    public String getCredit_leader_phone_联系人电话() {
        return credit_leader_phone_联系人电话;
    }

    public void setCredit_leader_phone_联系人电话(String credit_leader_phone_联系人电话) {
        this.credit_leader_phone_联系人电话 = credit_leader_phone_联系人电话;
    }

    public String getCredit_leader_name_联系人姓名() {
        return credit_leader_name_联系人姓名;
    }

    public void setCredit_leader_name_联系人姓名(String credit_leader_name_联系人姓名) {
        this.credit_leader_name_联系人姓名 = credit_leader_name_联系人姓名;
    }

    private EnumCreditStatus status;
    private int volume_up_授权额度;

    public EnumCreditStatus getStatus() {
        return status;
    }

    public void setStatus(EnumCreditStatus status) {
        this.status = status;
    }

    private LocalDateTime created_at;

    public String getCreditSHXYDM统一社会信用代码() {
        return creditSHXYDM统一社会信用代码;
    }

    public void setCreditSHXYDM统一社会信用代码(String credit_SHXYDM_统一社会信用代码) {
        this.creditSHXYDM统一社会信用代码 = credit_SHXYDM_统一社会信用代码;
    }

    public String getCredit_NSRMC_企业名称() {
        return credit_NSRMC_企业名称;
    }

    public void setCredit_NSRMC_企业名称(String credit_NSRMC_企业名称) {
        this.credit_NSRMC_企业名称 = credit_NSRMC_企业名称;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setVolume_up_授权额度(int volume_up_授权额度) {
        this.volume_up_授权额度 = volume_up_授权额度;
    }

    public int getVolume_up_授权额度() {
        return volume_up_授权额度;
    }

    public void setCredit_bussiness_location_注册地址(String credit_bussiness_location_注册地址) {
        this.credit_bussiness_location_注册地址 = credit_bussiness_location_注册地址;
    }

    public String getCredit_bussiness_location_注册地址() {
        return credit_bussiness_location_注册地址;
    }

    public void setCredit_bussiness_经营范围(String credit_bussiness_经营范围) {
        this.credit_bussiness_经营范围 = credit_bussiness_经营范围;
    }

    public String getCredit_bussiness_经营范围() {
        return credit_bussiness_经营范围;
    }

    public void setCredit_行业类别(String credit_行业类别) {
        this.credit_行业类别 = credit_行业类别;
    }

    public String getCredit_行业类别() {
        return credit_行业类别;
    }

    public void setCredit_product_type_信贷产品类型(String credit_product_type_信贷产品类型) {
        this.credit_product_type_信贷产品类型 = credit_product_type_信贷产品类型;
    }

    public String getCredit_product_type_信贷产品类型() {
        return credit_product_type_信贷产品类型;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
