package com.lt.dom.credit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class _信合_信用等级信息 {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private String 信用等级信息_ID_id;
    private String 信用等级信息_NSRMC_企业名称;
    private String 信用等级信息_SHXYDM_统一社会信用代码;
    private String 信用等级信息_PJND_评审年度;
    private String 信用等级信息_PDJB_纳税信用等级;
    private long request;

    public long getRequest() {
        return request;
    }

    public void setRequest(long request) {
        this.request = request;
    }

    public _信合_信用等级信息(RequestCredit requestCredit) {
        this.request = requestCredit.getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
}
