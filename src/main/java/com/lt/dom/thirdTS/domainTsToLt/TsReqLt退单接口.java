package com.lt.dom.thirdTS.domainTsToLt;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class TsReqLt退单接口 {

    @NotNull
    private String method ;//:接口方法名,固定值：item_list




    private Integer size;//:退票数,缺省退票所有未使用票数
    private String orders_id;//要退票的订单号
    private String serial_no;//退票流水号，如传此字段时，审核通知会返回对应的流水号
    private String codes;//退单凭证，多个','分割

    private String remark;//:退单备注

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(String orders_id) {
        this.orders_id = orders_id;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
