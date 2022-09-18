package com.lt.dom.credit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class _信合 {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private String 信用等级信息_ID_id;
    private String 信用等级信息_NSRMC_企业名称;
    private String 信用等级信息_SHXYDM_统一社会信用代码;
    private String 信用等级信息_PJND_评审年度;
    private String 信用等级信息_PDJB_纳税信用等级;
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


    private String 欠税信息_ID_id;
    private String 欠税信息_DJXH_登记序号;
    private String 欠税信息_NSRMC_企业名称;
    private String 欠税信息_SHXYDM_统一社会信用代码;
    private String 欠税信息_SFQS_当前是否欠税;
    private String 欠税信息_QSJE_欠税金额;
    private String 欠税信息_NDYF_月份;



    private String 纳税信息_ID_id;
    private String 纳税信息_NSRMC_企业名称;
    private String 纳税信息_SHXYDM_统一社会信用代码;
    private String 纳税信息_ZZSYNSE_增值税应纳税金额_万元_;
    private String 纳税信息_ZZSSJJE_增值税实际缴税金额_万元_;
    private String 纳税信息_SDSYNSE_企业所得税应纳税金额_万元_;
    private String 纳税信息_SDSSJJE_企业所得税实际缴税金额_万元_;
    private String 纳税信息_RKYF_缴税日期;
    private String 连续缴税周期信息_ID_id;
    private String 连续缴税周期信息_SHXYDM_统一社会信用代码;
    private String 连续缴税周期信息_NSRMC_企业名称;
    private String 连续缴税周期信息_LYYF_连续正常缴税周期_月_;
    private String 连续缴税周期信息_ZJRKYF_月份;
    private String 连续缴税周期信息_JSSJ_计算时间;
    private String 非正常户信息_ID_id;
    private String 非正常户信息_NSRMC_纳税人名称;
    private String 非正常户信息_SHXYDM_社会信用代码;
    private String 非正常户信息_RDRQ_认定日期;
    private String 非正常户信息_SFFZC_是否非正常;


    public String get信用等级信息_ID_id() {
        return 信用等级信息_ID_id;
    }

    public void set信用等级信息_ID_id(String 信用等级信息_ID_id) {
        this.信用等级信息_ID_id = 信用等级信息_ID_id;
    }

    public String get信用等级信息_NSRMC_企业名称() {
        return 信用等级信息_NSRMC_企业名称;
    }

    public void set信用等级信息_NSRMC_企业名称(String 信用等级信息_NSRMC_企业名称) {
        this.信用等级信息_NSRMC_企业名称 = 信用等级信息_NSRMC_企业名称;
    }

    public String get信用等级信息_SHXYDM_统一社会信用代码() {
        return 信用等级信息_SHXYDM_统一社会信用代码;
    }

    public void set信用等级信息_SHXYDM_统一社会信用代码(String 信用等级信息_SHXYDM_统一社会信用代码) {
        this.信用等级信息_SHXYDM_统一社会信用代码 = 信用等级信息_SHXYDM_统一社会信用代码;
    }

    public String get信用等级信息_PJND_评审年度() {
        return 信用等级信息_PJND_评审年度;
    }

    public void set信用等级信息_PJND_评审年度(String 信用等级信息_PJND_评审年度) {
        this.信用等级信息_PJND_评审年度 = 信用等级信息_PJND_评审年度;
    }

    public String get信用等级信息_PDJB_纳税信用等级() {
        return 信用等级信息_PDJB_纳税信用等级;
    }

    public void set信用等级信息_PDJB_纳税信用等级(String 信用等级信息_PDJB_纳税信用等级) {
        this.信用等级信息_PDJB_纳税信用等级 = 信用等级信息_PDJB_纳税信用等级;
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

    public String get非正常户信息_ID_id() {
        return 非正常户信息_ID_id;
    }

    public void set非正常户信息_ID_id(String 非正常户信息_ID_id) {
        this.非正常户信息_ID_id = 非正常户信息_ID_id;
    }

    public String get非正常户信息_NSRMC_纳税人名称() {
        return 非正常户信息_NSRMC_纳税人名称;
    }

    public void set非正常户信息_NSRMC_纳税人名称(String 非正常户信息_NSRMC_纳税人名称) {
        this.非正常户信息_NSRMC_纳税人名称 = 非正常户信息_NSRMC_纳税人名称;
    }

    public String get非正常户信息_SHXYDM_社会信用代码() {
        return 非正常户信息_SHXYDM_社会信用代码;
    }

    public void set非正常户信息_SHXYDM_社会信用代码(String 非正常户信息_SHXYDM_社会信用代码) {
        this.非正常户信息_SHXYDM_社会信用代码 = 非正常户信息_SHXYDM_社会信用代码;
    }

    public String get非正常户信息_RDRQ_认定日期() {
        return 非正常户信息_RDRQ_认定日期;
    }

    public void set非正常户信息_RDRQ_认定日期(String 非正常户信息_RDRQ_认定日期) {
        this.非正常户信息_RDRQ_认定日期 = 非正常户信息_RDRQ_认定日期;
    }

    public String get非正常户信息_SFFZC_是否非正常() {
        return 非正常户信息_SFFZC_是否非正常;
    }

    public void set非正常户信息_SFFZC_是否非正常(String 非正常户信息_SFFZC_是否非正常) {
        this.非正常户信息_SFFZC_是否非正常 = 非正常户信息_SFFZC_是否非正常;
    }
}
