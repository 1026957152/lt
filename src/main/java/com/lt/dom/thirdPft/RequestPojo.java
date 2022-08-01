package com.lt.dom.thirdPft;

public class RequestPojo {

    private String method;//	string	请求方法	是
    private String version;//		string	版本号	是
    private int timeStamp;//		int	时间戳	是
    private String sign;//		string	签名	是
    private String data;//		string	对json串base64加密后的字符串 -- 业务数组数据	是
    private String appid;//		string	票付通分配的三方系统标识	是

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
