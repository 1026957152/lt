package com.lt.dom.thirdTS.domainLtToTs;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.constraints.NotNull;

public class LtReqTs产品信息变更通知 {

    @NotNull
    private String method ;//:接口方法名,固定值：item_list


    public static class ToLtReqTs产品信息变更通知 {

        private Integer status;//产品状态（1：正常，12：下架）

        private String seller_code;//产品ID

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getSeller_code() {
            return seller_code;
        }

        public void setSeller_code(String seller_code) {
            this.seller_code = seller_code;
        }
        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }
        public LtReqTs产品信息变更通知 To() {
            LtReqTs产品信息变更通知 ltReqTs验证核销通知 = new LtReqTs产品信息变更通知();
            ltReqTs验证核销通知.setStatus(this.getStatus()); //产品状态（1：正常，12：下架）
            ltReqTs验证核销通知.setSeller_code(this.getSeller_code()); //产品ID

            System.out.println("============"+ this.toString());
            return ltReqTs验证核销通知;


        }
    }
    private Integer status;//产品状态（1：正常，12：下架）

    private String seller_code;//产品ID

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSeller_code() {
        return seller_code;
    }

    public void setSeller_code(String seller_code) {
        this.seller_code = seller_code;
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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
