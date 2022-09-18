package com.lt.dom.credit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class _信合_欠税信息 {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;




    private String 欠税信息_ID_id;
    private String 欠税信息_DJXH_登记序号;
    private String 欠税信息_NSRMC_企业名称;
    private String 欠税信息_SHXYDM_统一社会信用代码;
    private String 欠税信息_SFQS_当前是否欠税;
    private String 欠税信息_QSJE_欠税金额;
    private String 欠税信息_NDYF_月份;

    public _信合_欠税信息() {

    }

    public long getRequest() {
        return request;
    }

    public void setRequest(long request) {
        this.request = request;
    }

    private long request;
    public _信合_欠税信息(RequestCredit requestCredit) {
        this.request = requestCredit.getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String get欠税信息_ID_id() {
        return 欠税信息_ID_id;
    }

    public void set欠税信息_ID_id(String 欠税信息_ID_id) {
        this.欠税信息_ID_id = 欠税信息_ID_id;
    }

    public String get欠税信息_DJXH_登记序号() {
        return 欠税信息_DJXH_登记序号;
    }

    public void set欠税信息_DJXH_登记序号(String 欠税信息_DJXH_登记序号) {
        this.欠税信息_DJXH_登记序号 = 欠税信息_DJXH_登记序号;
    }

    public String get欠税信息_NSRMC_企业名称() {
        return 欠税信息_NSRMC_企业名称;
    }

    public void set欠税信息_NSRMC_企业名称(String 欠税信息_NSRMC_企业名称) {
        this.欠税信息_NSRMC_企业名称 = 欠税信息_NSRMC_企业名称;
    }

    public String get欠税信息_SHXYDM_统一社会信用代码() {
        return 欠税信息_SHXYDM_统一社会信用代码;
    }

    public void set欠税信息_SHXYDM_统一社会信用代码(String 欠税信息_SHXYDM_统一社会信用代码) {
        this.欠税信息_SHXYDM_统一社会信用代码 = 欠税信息_SHXYDM_统一社会信用代码;
    }

    public String get欠税信息_SFQS_当前是否欠税() {
        return 欠税信息_SFQS_当前是否欠税;
    }

    public void set欠税信息_SFQS_当前是否欠税(String 欠税信息_SFQS_当前是否欠税) {
        this.欠税信息_SFQS_当前是否欠税 = 欠税信息_SFQS_当前是否欠税;
    }

    public String get欠税信息_QSJE_欠税金额() {
        return 欠税信息_QSJE_欠税金额;
    }

    public void set欠税信息_QSJE_欠税金额(String 欠税信息_QSJE_欠税金额) {
        this.欠税信息_QSJE_欠税金额 = 欠税信息_QSJE_欠税金额;
    }

    public String get欠税信息_NDYF_月份() {
        return 欠税信息_NDYF_月份;
    }

    public void set欠税信息_NDYF_月份(String 欠税信息_NDYF_月份) {
        this.欠税信息_NDYF_月份 = 欠税信息_NDYF_月份;
    }
}
