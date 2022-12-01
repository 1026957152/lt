package com.lt.dom.thirdTS.domainTsToLt;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class TsReqLt重发接口_修改订单 {

    @NotNull
    private String method ;//:接口方法名,固定值：item_list




    public static class ToTsReqLt重发接口_修改订单 extends TsReqLtBase{


        private Integer orders_id;//本平台订单ID（天时同城）
        private Integer is_pay;//:是否已付款，1：已付款；0：未付款(未付款的订单不可修改)
        private String mobile;//:购票人手机号，缺省不修改
        private String name;//:购票人名称，缺省不修改
        private String id_number;//:购票人身份证号码，缺省不修改
        private Integer sms_send;//:是否发送短信,0否，1是，2自动（是支付操作时"是",是修改信息时"否"）
        private Integer re_code;//:是否生成新的码号,0:否，1:是，2自动（是支付操作时"否",是修改信息时"是"）缺省0
        private LocalDate start_time;//:订单有效期，开始时间（格式：yyyy-mm-dd，缺省不修改）
        private LocalDate expire_time;//:订单有效期，结束时间（格式：yyyy-mm-dd，缺省不修改）

        public Integer getOrders_id() {
            return orders_id;
        }

        public void setOrders_id(Integer orders_id) {
            this.orders_id = orders_id;
        }

        public Integer getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(Integer is_pay) {
            this.is_pay = is_pay;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId_number() {
            return id_number;
        }

        public void setId_number(String id_number) {
            this.id_number = id_number;
        }

        public Integer getSms_send() {
            return sms_send;
        }

        public void setSms_send(Integer sms_send) {
            this.sms_send = sms_send;
        }

        public Integer getRe_code() {
            return re_code;
        }

        public void setRe_code(Integer re_code) {
            this.re_code = re_code;
        }

        public LocalDate getStart_time() {
            return start_time;
        }

        public void setStart_time(LocalDate start_time) {
            this.start_time = start_time;
        }

        public LocalDate getExpire_time() {
            return expire_time;
        }

        public void setExpire_time(LocalDate expire_time) {
            this.expire_time = expire_time;
        }
    }



    private Integer orders_id;//本平台订单ID（天时同城）
    private Integer is_pay;//:是否已付款，1：已付款；0：未付款(未付款的订单不可修改)
    private String mobile;//:购票人手机号，缺省不修改
    private String name;//:购票人名称，缺省不修改
    private String id_number;//:购票人身份证号码，缺省不修改
    private Integer sms_send;//:是否发送短信,0否，1是，2自动（是支付操作时"是",是修改信息时"否"）
    private Integer re_code;//:是否生成新的码号,0:否，1:是，2自动（是支付操作时"否",是修改信息时"是"）缺省0
    private LocalDate start_time;//:订单有效期，开始时间（格式：yyyy-mm-dd，缺省不修改）
    private LocalDate expire_time;//:订单有效期，结束时间（格式：yyyy-mm-dd，缺省不修改）









    @NotNull
    private String _sig;//:请求签名，见说明文档

    @NotNull
    private String _pid;//:合作伙伴id

    @NotNull
    private String format;//:返回数据格式，json

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }


    public String get_sig() {
        return _sig;
    }

    public void set_sig(String _sig) {
        this._sig = _sig;
    }

    public String get_pid() {
        return _pid;
    }

    public void set_pid(String _pid) {
        this._pid = _pid;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
