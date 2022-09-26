package com.lt.dom.controllerOct.Axh.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class XhToYxdResponse {


    @JsonProperty("datas")
    private Object datas;
    @JsonProperty("resp_code")
    private Integer respCode;
    @JsonProperty("resp_msg")
    private String respMsg;
    private Object requestDatas;

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

    public void setRequestDatas(Object requestDatas) {
        this.requestDatas = requestDatas;
    }

    public Object getRequestDatas() {
        return requestDatas;
    }
}
