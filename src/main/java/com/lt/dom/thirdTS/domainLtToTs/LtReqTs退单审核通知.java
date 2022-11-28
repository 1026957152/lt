package com.lt.dom.thirdTS.domainLtToTs;

import javax.validation.constraints.NotNull;

public class LtReqTs退单审核通知 {

    @NotNull
    private String method ;//:接口方法名,固定值：item_list


    public static class ToLtReqTs验证核销通知 {


        private String serial_no;//退票记录id（申请退票有传此数据时，会返回申请退票时传入的流水号）
        private String orders_id;//本平台订单ID（天时同城）
        private Integer type;//审核结果： 3退票成功，4退票不通过

        private String message;//管理员审核备注/说明


        public String getSerial_no() {
            return serial_no;
        }

        public void setSerial_no(String serial_no) {
            this.serial_no = serial_no;
        }

        public String getOrders_id() {
            return orders_id;
        }

        public void setOrders_id(String orders_id) {
            this.orders_id = orders_id;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
        public LtReqTs退单审核通知 To() {
            LtReqTs退单审核通知 ltReqTs验证核销通知 = new LtReqTs退单审核通知();

            ltReqTs验证核销通知.setSerial_no(this.serial_no);
            ltReqTs验证核销通知.setOrders_id(this.orders_id);
            ltReqTs验证核销通知.setType(this.type);
            ltReqTs验证核销通知.setMessage(this.message);
            return ltReqTs验证核销通知;


        }
    }

    private String serial_no;//退票记录id（申请退票有传此数据时，会返回申请退票时传入的流水号）
    private String orders_id;//本平台订单ID（天时同城）
    private Integer type;//审核结果： 3退票成功，4退票不通过

    private String message;//管理员审核备注/说明


    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(String orders_id) {
        this.orders_id = orders_id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
