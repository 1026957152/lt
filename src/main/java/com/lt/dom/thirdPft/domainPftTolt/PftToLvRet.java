package com.lt.dom.thirdPft.domainPftTolt;

public class PftToLvRet {

    private int code;//	int	200：成功 1000 失败
    private String msg;//	string	错误信息
    private String data;//	array	返回数据

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
