package com.lt.dom.credit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class _信合_非正常户信息 {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private String 非正常户信息_ID_id;
    private String 非正常户信息_NSRMC_纳税人名称;
    private String 非正常户信息_SHXYDM_社会信用代码;
    private String 非正常户信息_RDRQ_认定日期;
    private String 非正常户信息_SFFZC_是否非正常;
    private long request;
    public _信合_非正常户信息(RequestCredit requestCredit) {
        this.request = requestCredit.getId();
    }

    public _信合_非正常户信息() {

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
