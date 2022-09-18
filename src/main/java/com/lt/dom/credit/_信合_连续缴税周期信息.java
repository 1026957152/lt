package com.lt.dom.credit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class _信合_连续缴税周期信息 {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private String 连续缴税周期信息_ID_id;
    private String 连续缴税周期信息_SHXYDM_统一社会信用代码;
    private String 连续缴税周期信息_NSRMC_企业名称;
    private String 连续缴税周期信息_LYYF_连续正常缴税周期_月_;
    private String 连续缴税周期信息_ZJRKYF_月份;
    private String 连续缴税周期信息_JSSJ_计算时间;
    private long request;
    public _信合_连续缴税周期信息(RequestCredit requestCredit) {
        this.request = requestCredit.getId();
    }

    public _信合_连续缴税周期信息() {

    }

    public long getRequest() {
        return request;
    }

    public void setRequest(long request) {
        this.request = request;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String get连续缴税周期信息_ID_id() {
        return 连续缴税周期信息_ID_id;
    }

    public void set连续缴税周期信息_ID_id(String 连续缴税周期信息_ID_id) {
        this.连续缴税周期信息_ID_id = 连续缴税周期信息_ID_id;
    }

    public String get连续缴税周期信息_SHXYDM_统一社会信用代码() {
        return 连续缴税周期信息_SHXYDM_统一社会信用代码;
    }

    public void set连续缴税周期信息_SHXYDM_统一社会信用代码(String 连续缴税周期信息_SHXYDM_统一社会信用代码) {
        this.连续缴税周期信息_SHXYDM_统一社会信用代码 = 连续缴税周期信息_SHXYDM_统一社会信用代码;
    }

    public String get连续缴税周期信息_NSRMC_企业名称() {
        return 连续缴税周期信息_NSRMC_企业名称;
    }

    public void set连续缴税周期信息_NSRMC_企业名称(String 连续缴税周期信息_NSRMC_企业名称) {
        this.连续缴税周期信息_NSRMC_企业名称 = 连续缴税周期信息_NSRMC_企业名称;
    }

    public String get连续缴税周期信息_LYYF_连续正常缴税周期_月_() {
        return 连续缴税周期信息_LYYF_连续正常缴税周期_月_;
    }

    public void set连续缴税周期信息_LYYF_连续正常缴税周期_月_(String 连续缴税周期信息_LYYF_连续正常缴税周期_月_) {
        this.连续缴税周期信息_LYYF_连续正常缴税周期_月_ = 连续缴税周期信息_LYYF_连续正常缴税周期_月_;
    }

    public String get连续缴税周期信息_ZJRKYF_月份() {
        return 连续缴税周期信息_ZJRKYF_月份;
    }

    public void set连续缴税周期信息_ZJRKYF_月份(String 连续缴税周期信息_ZJRKYF_月份) {
        this.连续缴税周期信息_ZJRKYF_月份 = 连续缴税周期信息_ZJRKYF_月份;
    }

    public String get连续缴税周期信息_JSSJ_计算时间() {
        return 连续缴税周期信息_JSSJ_计算时间;
    }

    public void set连续缴税周期信息_JSSJ_计算时间(String 连续缴税周期信息_JSSJ_计算时间) {
        this.连续缴税周期信息_JSSJ_计算时间 = 连续缴税周期信息_JSSJ_计算时间;
    }
}
