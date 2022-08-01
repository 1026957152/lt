package com.lt.dom.RealNameAuthentication;

public class ResultPhoneAuth {


    private String resultcode;// String 返回结果码
    private String resultmsg;// String 结果描述：
    //一致与否
    private String orderno;// String 平台业务单号
    private String idCardName;// String 身份证姓名
    private String idCardNo;// String 身份证号码
    private String phoneNo;// String 手机号码
    private String orignOpe;// String 号段所属运营商 1:移动，2：联通，3：电信
    private String isTrans;// String 是否携号转网 0：否,1：是
    private String realOpe;// String 号段实际所属运营商 1:移动，2：联通，3：电信

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getResultmsg() {
        return resultmsg;
    }

    public void setResultmsg(String resultmsg) {
        this.resultmsg = resultmsg;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getIdCardName() {
        return idCardName;
    }

    public void setIdCardName(String idCardName) {
        this.idCardName = idCardName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getOrignOpe() {
        return orignOpe;
    }

    public void setOrignOpe(String orignOpe) {
        this.orignOpe = orignOpe;
    }

    public String getIsTrans() {
        return isTrans;
    }

    public void setIsTrans(String isTrans) {
        this.isTrans = isTrans;
    }

    public String getRealOpe() {
        return realOpe;
    }

    public void setRealOpe(String realOpe) {
        this.realOpe = realOpe;
    }
}
