package com.lt.dom.credit;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


public class Request授信Resp {


    private long id;


    private String credit_SHXYDM_统一社会信用代码;
    private String credit_NSRMC_企业名称;


    private LocalDateTime created_at;
    private List<Credit_信合_税票信息> credit_信合_税票信息;
    private List<_信合_欠税信息> credit_信合_欠税信息;
    private List<_信合_非正常户信息> credit_信合_非正常户信息;
    private List<_信合_连续缴税周期信息> credit_信合_连续缴税周期信息;
    private List<_信合_纳税信息> credit_信合_纳税信息;
    private EnumCreditStatus status;



    private String credit_leader_phone_联系人电话;
    private String credit_leader_name_联系人姓名;
    private String credit_bussiness_location_注册地址;
    private String credit_bussiness_经营范围;
    private String credit_行业类别;
    private String credit_product_type_信贷产品类型;
    private int volume_up_授权额度;
    private List<CreditStep> steps;
    private Map steps_summary;

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

    public String getCredit_bussiness_location_注册地址() {
        return credit_bussiness_location_注册地址;
    }

    public void setCredit_bussiness_location_注册地址(String credit_bussiness_location_注册地址) {
        this.credit_bussiness_location_注册地址 = credit_bussiness_location_注册地址;
    }

    public String getCredit_bussiness_经营范围() {
        return credit_bussiness_经营范围;
    }

    public void setCredit_bussiness_经营范围(String credit_bussiness_经营范围) {
        this.credit_bussiness_经营范围 = credit_bussiness_经营范围;
    }

    public String getCredit_行业类别() {
        return credit_行业类别;
    }

    public void setCredit_行业类别(String credit_行业类别) {
        this.credit_行业类别 = credit_行业类别;
    }

    public String getCredit_product_type_信贷产品类型() {
        return credit_product_type_信贷产品类型;
    }

    public void setCredit_product_type_信贷产品类型(String credit_product_type_信贷产品类型) {
        this.credit_product_type_信贷产品类型 = credit_product_type_信贷产品类型;
    }

    public static Request授信Resp from(RequestCredit x) {
        Request授信Resp request授信Resp = new Request授信Resp();

        request授信Resp.setCode(x.getCode());
        request授信Resp.setCreated_at(x.getCreated_at());
        request授信Resp.setCredit_NSRMC_企业名称(x.getCredit_NSRMC_企业名称());
        request授信Resp.setCredit_SHXYDM_统一社会信用代码(x.getCreditSHXYDM统一社会信用代码());



        request授信Resp.setStatus(EnumCreditStatus.请求);
        request授信Resp.setVolume_up_授权额度(0);
        request授信Resp.setCredit_leader_name_联系人姓名(x.getCredit_leader_name_联系人姓名());
        request授信Resp.setCredit_leader_phone_联系人电话(x.getCredit_leader_phone_联系人电话());



        request授信Resp.setCredit_bussiness_经营范围(x.getCredit_bussiness_经营范围());
        request授信Resp.setCredit_bussiness_location_注册地址(x.getCredit_bussiness_location_注册地址());

        request授信Resp.setCredit_行业类别(x.getCredit_行业类别());
        request授信Resp.setCredit_product_type_信贷产品类型(x.getCredit_product_type_信贷产品类型());
        return request授信Resp;
    }


    public String getCredit_SHXYDM_统一社会信用代码() {
        return credit_SHXYDM_统一社会信用代码;
    }

    public void setCredit_SHXYDM_统一社会信用代码(String credit_SHXYDM_统一社会信用代码) {
        this.credit_SHXYDM_统一社会信用代码 = credit_SHXYDM_统一社会信用代码;
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

    public void setCredit_信合_税票信息(List<Credit_信合_税票信息> credit_信合_税票信息) {
        this.credit_信合_税票信息 = credit_信合_税票信息;
    }

    public List<Credit_信合_税票信息> getCredit_信合_税票信息() {
        return credit_信合_税票信息;
    }

    public void setCredit_信合_欠税信息(List<_信合_欠税信息> credit_信合_欠税信息) {
        this.credit_信合_欠税信息 = credit_信合_欠税信息;
    }

    public List<_信合_欠税信息> getCredit_信合_欠税信息() {
        return credit_信合_欠税信息;
    }

    public void setCredit_信合_非正常户信息(List<_信合_非正常户信息> credit_信合_非正常户信息) {
        this.credit_信合_非正常户信息 = credit_信合_非正常户信息;
    }

    public List<_信合_非正常户信息> getCredit_信合_非正常户信息() {
        return credit_信合_非正常户信息;
    }

    public void setCredit_信合_连续缴税周期信息(List<_信合_连续缴税周期信息> credit_信合_连续缴税周期信息) {
        this.credit_信合_连续缴税周期信息 = credit_信合_连续缴税周期信息;
    }

    public List<_信合_连续缴税周期信息> getCredit_信合_连续缴税周期信息() {
        return credit_信合_连续缴税周期信息;
    }

    public void setCredit_信合_纳税信息(List<_信合_纳税信息> credit_信合_纳税信息) {
        this.credit_信合_纳税信息 = credit_信合_纳税信息;
    }

    public List<_信合_纳税信息> getCredit_信合_纳税信息() {
        return credit_信合_纳税信息;
    }

    public void setStatus(EnumCreditStatus status) {
        this.status = status;
    }

    public EnumCreditStatus getStatus() {
        return status;
    }

    public void setVolume_up_授权额度(int volume_up_授权额度) {
        this.volume_up_授权额度 = volume_up_授权额度;
    }

    public int getVolume_up_授权额度() {
        return volume_up_授权额度;
    }

    public void setSteps(List<CreditStep> steps) {
        this.steps = steps;
    }

    public List<CreditStep> getSteps() {
        return steps;
    }

    public  void setSteps_summary(Map steps_summary) {
        this.steps_summary = steps_summary;
    }

    public Map getSteps_summary() {
        return steps_summary;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
