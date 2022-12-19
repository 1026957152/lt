package com.lt.dom.thirdTS.domainTsToLt;

import org.javatuples.Triplet;
import org.springframework.util.MultiValueMap;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TsReqLt退单接口 extends TsReqLtBase{



    private Integer size;//:退票数,缺省退票所有未使用票数
    private String orders_id;//要退票的订单号
    private String serial_no;//退票流水号，如传此字段时，审核通知会返回对应的流水号
    private String codes;//退单凭证，多个','分割

    private String remark;//:退单备注

    public static TsReqLt退单接口 from_(MultiValueMap<String, String> ob_) {
        TsReqLt退单接口 tsReqLtBase = new TsReqLt退单接口();

        tsReqLtBase.setSerial_no((String)ob_.getFirst("serial_no"));

        tsReqLtBase.setOrders_id(ob_.getFirst("orders_id"));
        tsReqLtBase.setSize(Integer.valueOf( ob_.getFirst("size")));
        tsReqLtBase.setCodes((String)  ob_.getFirst("codes"));

        tsReqLtBase.setRemark((String)  ob_.getFirst("remark"));

        return tsReqLtBase;
    }

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
