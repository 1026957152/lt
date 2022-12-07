package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SmsResp {

    @JsonProperty("http_status_code")
    private Integer http_status_code;
    @JsonProperty("code")
    private Integer code;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("fee")
    private Double fee;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("sid")
    private Long sid;

    public Integer getHttp_status_code() {
        return http_status_code;
    }

    public void setHttp_status_code(Integer http_status_code) {
        this.http_status_code = http_status_code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }
}
