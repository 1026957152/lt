package com.lt.dom.thirdTS.domainTsToLt;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class TsReqLt退单接口 extends TsReqLtBase{



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

}
