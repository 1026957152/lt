package com.lt.dom.controllerOct.Axh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;


public class YxdToXHResponse {


    @JsonProperty("datas")
    private Object datas;
    @JsonProperty("resp_code")
    private Integer respCode;
    @JsonProperty("resp_msg")
    private String respMsg;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public Object getDatas() {
        return datas;
    }

    public void setDatas(Object datas) {
        this.datas = datas;
    }

    public Integer getRespCode() {
        return respCode;
    }

    public void setRespCode(Integer respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
}
