package com.lt.dom.credit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class _信合_纳税信息 {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;


    private String 纳税信息_ID_id;
    private String 纳税信息_NSRMC_企业名称;
    private String 纳税信息_SHXYDM_统一社会信用代码;
    private String 纳税信息_ZZSYNSE_增值税应纳税金额_万元_;
    private String 纳税信息_ZZSSJJE_增值税实际缴税金额_万元_;
    private String 纳税信息_SDSYNSE_企业所得税应纳税金额_万元_;
    private String 纳税信息_SDSSJJE_企业所得税实际缴税金额_万元_;
    private String 纳税信息_RKYF_缴税日期;
    private long request;
    public _信合_纳税信息(RequestCredit requestCredit) {
        this.request = requestCredit.getId();
    }

    public _信合_纳税信息() {

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

    public String get纳税信息_ID_id() {
        return 纳税信息_ID_id;
    }

    public void set纳税信息_ID_id(String 纳税信息_ID_id) {
        this.纳税信息_ID_id = 纳税信息_ID_id;
    }

    public String get纳税信息_NSRMC_企业名称() {
        return 纳税信息_NSRMC_企业名称;
    }

    public void set纳税信息_NSRMC_企业名称(String 纳税信息_NSRMC_企业名称) {
        this.纳税信息_NSRMC_企业名称 = 纳税信息_NSRMC_企业名称;
    }

    public String get纳税信息_SHXYDM_统一社会信用代码() {
        return 纳税信息_SHXYDM_统一社会信用代码;
    }

    public void set纳税信息_SHXYDM_统一社会信用代码(String 纳税信息_SHXYDM_统一社会信用代码) {
        this.纳税信息_SHXYDM_统一社会信用代码 = 纳税信息_SHXYDM_统一社会信用代码;
    }

    public String get纳税信息_ZZSYNSE_增值税应纳税金额_万元_() {
        return 纳税信息_ZZSYNSE_增值税应纳税金额_万元_;
    }

    public void set纳税信息_ZZSYNSE_增值税应纳税金额_万元_(String 纳税信息_ZZSYNSE_增值税应纳税金额_万元_) {
        this.纳税信息_ZZSYNSE_增值税应纳税金额_万元_ = 纳税信息_ZZSYNSE_增值税应纳税金额_万元_;
    }

    public String get纳税信息_ZZSSJJE_增值税实际缴税金额_万元_() {
        return 纳税信息_ZZSSJJE_增值税实际缴税金额_万元_;
    }

    public void set纳税信息_ZZSSJJE_增值税实际缴税金额_万元_(String 纳税信息_ZZSSJJE_增值税实际缴税金额_万元_) {
        this.纳税信息_ZZSSJJE_增值税实际缴税金额_万元_ = 纳税信息_ZZSSJJE_增值税实际缴税金额_万元_;
    }

    public String get纳税信息_SDSYNSE_企业所得税应纳税金额_万元_() {
        return 纳税信息_SDSYNSE_企业所得税应纳税金额_万元_;
    }

    public void set纳税信息_SDSYNSE_企业所得税应纳税金额_万元_(String 纳税信息_SDSYNSE_企业所得税应纳税金额_万元_) {
        this.纳税信息_SDSYNSE_企业所得税应纳税金额_万元_ = 纳税信息_SDSYNSE_企业所得税应纳税金额_万元_;
    }

    public String get纳税信息_SDSSJJE_企业所得税实际缴税金额_万元_() {
        return 纳税信息_SDSSJJE_企业所得税实际缴税金额_万元_;
    }

    public void set纳税信息_SDSSJJE_企业所得税实际缴税金额_万元_(String 纳税信息_SDSSJJE_企业所得税实际缴税金额_万元_) {
        this.纳税信息_SDSSJJE_企业所得税实际缴税金额_万元_ = 纳税信息_SDSSJJE_企业所得税实际缴税金额_万元_;
    }

    public String get纳税信息_RKYF_缴税日期() {
        return 纳税信息_RKYF_缴税日期;
    }

    public void set纳税信息_RKYF_缴税日期(String 纳税信息_RKYF_缴税日期) {
        this.纳税信息_RKYF_缴税日期 = 纳税信息_RKYF_缴税日期;
    }
}
