package com.lt.dom.proto.rabit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.BaseResp;
import com.lt.dom.OctResp.BusVehicleResp;
import com.lt.dom.oct.BusRoute;
import com.lt.dom.otcenum.EnumRelatedObjectType;
import com.lt.dom.proto.BusStopRespProto;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;



public class CityPassBooking  {



    String 姓名 ;
    String 码号 ;
    //  String 景区 = "景区";
    String SKU名称;
    String 购买数量 ;

    String 客服电话 ;

    String 开始日期;
    String 过期日期 ;
    private String eticket;

    public String get姓名() {
        return 姓名;
    }

    public void set姓名(String 姓名) {
        this.姓名 = 姓名;
    }

    public String get码号() {
        return 码号;
    }

    public void set码号(String 码号) {
        this.码号 = 码号;
    }

    public String getSKU名称() {
        return SKU名称;
    }

    public void setSKU名称(String SKU名称) {
        this.SKU名称 = SKU名称;
    }

    public String get购买数量() {
        return 购买数量;
    }

    public void set购买数量(String 购买数量) {
        this.购买数量 = 购买数量;
    }

    public String get客服电话() {
        return 客服电话;
    }

    public void set客服电话(String 客服电话) {
        this.客服电话 = 客服电话;
    }

    public String get开始日期() {
        return 开始日期;
    }

    public void set开始日期(String 开始日期) {
        this.开始日期 = 开始日期;
    }

    public String get过期日期() {
        return 过期日期;
    }

    public void set过期日期(String 过期日期) {
        this.过期日期 = 过期日期;
    }

    public void setEticket(String eticket) {

        this.eticket = eticket;
    }

    public String getEticket() {
        return eticket;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
