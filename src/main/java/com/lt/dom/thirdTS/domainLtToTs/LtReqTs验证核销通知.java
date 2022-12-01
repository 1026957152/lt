package com.lt.dom.thirdTS.domainLtToTs;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class LtReqTs验证核销通知 {

    @NotNull
    private String method ;//:接口方法名,固定值：item_list


    public static class ToLtReqTs验证核销通知 {

        private Integer amount;//:当前使用数量
        private Integer amount_used;//:累计使用数量(包含本次)
        private String another_orders_id;//:本平台订单ID（天时同城）
        private String my_orders_id;//:第三方订单ID
        private String codes;//:使用码号,多个','分割

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public Integer getAmount_used() {
            return amount_used;
        }

        public void setAmount_used(Integer amount_used) {
            this.amount_used = amount_used;
        }

        public String getAnother_orders_id() {
            return another_orders_id;
        }

        public void setAnother_orders_id(String another_orders_id) {
            this.another_orders_id = another_orders_id;
        }

        public String getMy_orders_id() {
            return my_orders_id;
        }

        public void setMy_orders_id(String my_orders_id) {
            this.my_orders_id = my_orders_id;
        }

        public String getCodes() {
            return codes;
        }

        public void setCodes(String codes) {
            this.codes = codes;
        }

        public LtReqTs验证核销通知 To() {
            LtReqTs验证核销通知 ltReqTs验证核销通知 = new LtReqTs验证核销通知();

            ltReqTs验证核销通知.setAmount(this.amount);
            ltReqTs验证核销通知.setAmount_used(this.amount_used);
            ltReqTs验证核销通知.setAnother_orders_id(this.another_orders_id);
            ltReqTs验证核销通知.setCodes(this.codes);
            ltReqTs验证核销通知.setMy_orders_id(this.my_orders_id);
            return ltReqTs验证核销通知;


        }
    }

    private Integer amount;//:当前使用数量
    private Integer amount_used;//:累计使用数量(包含本次)
    private String another_orders_id;//:本平台订单ID（天时同城）
    private String my_orders_id;//:第三方订单ID
    private String codes;//:使用码号,多个','分割

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount_used() {
        return amount_used;
    }

    public void setAmount_used(Integer amount_used) {
        this.amount_used = amount_used;
    }

    public String getAnother_orders_id() {
        return another_orders_id;
    }

    public void setAnother_orders_id(String another_orders_id) {
        this.another_orders_id = another_orders_id;
    }

    public String getMy_orders_id() {
        return my_orders_id;
    }

    public void setMy_orders_id(String my_orders_id) {
        this.my_orders_id = my_orders_id;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
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
