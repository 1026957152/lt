package com.lt.dom.RealNameAuthentication;

public enum Resultcode {
    
    _0(0,"请求成功"),
_1(1,"请求失败"),
_1000(1000,"不一致"),
_1001(1001,"无效的 token"),
_1002(1002,"参数不能为空"),
_1003(1003,"参数不符合 base64 的格式"),
_1004(1004,"数据不存在"),
_1005(1005,"参数格式不合法"),
_1006(1006,"无效的通道或者无权限"),
_1007(1007,"已注销"),
_1008(1008,"未认证"),
_1009(1009,"手机号身份证号验证一致；手机号姓名验证不一致"),
_1010(1010,"手机号身份证号验证不一致，手机号姓名验证一致"),
_1011(1011,"姓名和身份证号码不匹配"),
_1012(1012,"其他不一致"),
    ;

    private int code;
    private String desc;
    Resultcode(int i, String 其他不一致) {
        this.code = i;
        this.desc = 其他不一致;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
