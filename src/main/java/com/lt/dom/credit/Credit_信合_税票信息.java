package com.lt.dom.credit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Credit_信合_税票信息 {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;


    private String 税票信息_ID_id;
    private String 税票信息_XFMC_销售名称;
    private String 税票信息_XFSBH_销售识别号;
    private String 税票信息_XFHYDM_销售行业代码;
    private String 税票信息_GFMC_购买名称;
    private String 税票信息_GFSBH_购买识别号;
    private String 税票信息_GFHYDM_购买行业代码;
    private String 税票信息_SPBM_商品编码;
    private String 税票信息_SPMC_商品名称;
    private String 税票信息_JE_同类商品月销售金额;
    private String 税票信息_KPCS_月开票次数;
    private String 税票信息_KPYF_开票月份;
    private long request;

    public Credit_信合_税票信息() {

    }

    public long getRequest() {
        return request;
    }

    public void setRequest(long request) {
        this.request = request;
    }

    public Credit_信合_税票信息(RequestCredit requestCredit) {
        this.request = requestCredit.getId();
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String get税票信息_ID_id() {
        return 税票信息_ID_id;
    }

    public void set税票信息_ID_id(String 税票信息_ID_id) {
        this.税票信息_ID_id = 税票信息_ID_id;
    }

    public String get税票信息_XFMC_销售名称() {
        return 税票信息_XFMC_销售名称;
    }

    public void set税票信息_XFMC_销售名称(String 税票信息_XFMC_销售名称) {
        this.税票信息_XFMC_销售名称 = 税票信息_XFMC_销售名称;
    }

    public String get税票信息_XFSBH_销售识别号() {
        return 税票信息_XFSBH_销售识别号;
    }

    public void set税票信息_XFSBH_销售识别号(String 税票信息_XFSBH_销售识别号) {
        this.税票信息_XFSBH_销售识别号 = 税票信息_XFSBH_销售识别号;
    }

    public String get税票信息_XFHYDM_销售行业代码() {
        return 税票信息_XFHYDM_销售行业代码;
    }

    public void set税票信息_XFHYDM_销售行业代码(String 税票信息_XFHYDM_销售行业代码) {
        this.税票信息_XFHYDM_销售行业代码 = 税票信息_XFHYDM_销售行业代码;
    }

    public String get税票信息_GFMC_购买名称() {
        return 税票信息_GFMC_购买名称;
    }

    public void set税票信息_GFMC_购买名称(String 税票信息_GFMC_购买名称) {
        this.税票信息_GFMC_购买名称 = 税票信息_GFMC_购买名称;
    }

    public String get税票信息_GFSBH_购买识别号() {
        return 税票信息_GFSBH_购买识别号;
    }

    public void set税票信息_GFSBH_购买识别号(String 税票信息_GFSBH_购买识别号) {
        this.税票信息_GFSBH_购买识别号 = 税票信息_GFSBH_购买识别号;
    }

    public String get税票信息_GFHYDM_购买行业代码() {
        return 税票信息_GFHYDM_购买行业代码;
    }

    public void set税票信息_GFHYDM_购买行业代码(String 税票信息_GFHYDM_购买行业代码) {
        this.税票信息_GFHYDM_购买行业代码 = 税票信息_GFHYDM_购买行业代码;
    }

    public String get税票信息_SPBM_商品编码() {
        return 税票信息_SPBM_商品编码;
    }

    public void set税票信息_SPBM_商品编码(String 税票信息_SPBM_商品编码) {
        this.税票信息_SPBM_商品编码 = 税票信息_SPBM_商品编码;
    }

    public String get税票信息_SPMC_商品名称() {
        return 税票信息_SPMC_商品名称;
    }

    public void set税票信息_SPMC_商品名称(String 税票信息_SPMC_商品名称) {
        this.税票信息_SPMC_商品名称 = 税票信息_SPMC_商品名称;
    }

    public String get税票信息_JE_同类商品月销售金额() {
        return 税票信息_JE_同类商品月销售金额;
    }

    public void set税票信息_JE_同类商品月销售金额(String 税票信息_JE_同类商品月销售金额) {
        this.税票信息_JE_同类商品月销售金额 = 税票信息_JE_同类商品月销售金额;
    }

    public String get税票信息_KPCS_月开票次数() {
        return 税票信息_KPCS_月开票次数;
    }

    public void set税票信息_KPCS_月开票次数(String 税票信息_KPCS_月开票次数) {
        this.税票信息_KPCS_月开票次数 = 税票信息_KPCS_月开票次数;
    }

    public String get税票信息_KPYF_开票月份() {
        return 税票信息_KPYF_开票月份;
    }

    public void set税票信息_KPYF_开票月份(String 税票信息_KPYF_开票月份) {
        this.税票信息_KPYF_开票月份 = 税票信息_KPYF_开票月份;
    }
}
