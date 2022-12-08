package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.controllerOct.BarcodesController;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumComponentVoucherStatus;
import com.lt.dom.otcenum.EnumDuration;
import com.lt.dom.util.ZxingBarcodeGenerator;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;




@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComponentVounchResp {   // 这个是 下单的时候， 从 product 中生成 的



    @Transient
    private RoyaltyRule royaltyRule;


    private Long component;
    private Long royaltyRuleId;
    private String code;
    private Long reservationId;


    private EnumDuration duration;


    private Long user;


    private String supplier;
    private String status_text;
    private String duration_text;
    private String name_long;
    private String Long_desc;
    private String supplier_code;
    private Long remaining;

    private String name;
    private String code_url;
    private String code_base64_src;
    private String title;
    private String componentRightCode;

    public static ComponentVounchResp from(ComponentVounch e) {
        ComponentVounchResp componentVounchResp = new ComponentVounchResp();
        componentVounchResp.setComponent(e.getComponent());
        componentVounchResp.setComponentRight(e.getComponentRight());

        componentVounchResp.setLimit(e.getLimit());
        componentVounchResp.setStatus(e.getStatus());
        componentVounchResp.setDuration(e.getDuration());
        componentVounchResp.setRedeemed(e.getRedeemed_quantity());
        componentVounchResp.setRemaining (e.getLimit() - e.getRedeemed_quantity());

        componentVounchResp.setReservationId(e.getReservation());
    //    componentVounchResp.setRoyaltyRule(e.getRoyaltyRule());
        componentVounchResp.setCode(e.getCode());
        componentVounchResp.setRoyaltyRuleId(e.getRoyaltyRuleId());
        componentVounchResp.setVoucherId(e.getVoucherId());
        componentVounchResp.setUser(e.getUser());
        componentVounchResp.setCode(e.getCode());

        componentVounchResp.setStatus_text(e.getStatus().toString());
        componentVounchResp.setDuration_text(e.getDuration().toString());

        return componentVounchResp;
    }

    public static ComponentVounchResp from(ComponentVounch e, ComponentRight componentRight) {
        ComponentVounchResp componentVounchResp = ComponentVounchResp.from(e);
        componentVounchResp.setName_long(componentRight.getName_long());
        componentVounchResp.setName(componentRight.getName());
        componentVounchResp.setLong_desc(componentRight.getLong_desc());

        String link = null;
        try {
            link = linkTo(methodOn(BarcodesController.class).getZxingQRCode(e.getCode())).withRel("editProduct").getHref();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        componentVounchResp.setCode_url(link);

        return componentVounchResp;
    }

    public static ComponentVounchResp from(ComponentVounch e, ComponentRight componentRight, Supplier supplier_) {
        ComponentVounchResp componentVounchResp = ComponentVounchResp.from(e,componentRight);
        componentVounchResp.setSupplier(supplier_.getName());
        componentVounchResp.setSupplier_code(supplier_.getCode());


            componentVounchResp.setCode_base64_src(ZxingBarcodeGenerator.base64_png_src(e.getCode()));

        return componentVounchResp;
    }

    public static ComponentVounchResp Simplefrom(ComponentVounch e, ComponentRight componentRight) {
        ComponentVounchResp componentVounchResp = new ComponentVounchResp();
        componentVounchResp.setTitle(componentRight.getName());
        componentVounchResp.setLimit(e.getLimit());
        componentVounchResp.setRedeemed(e.getRedeemed_quantity());
        componentVounchResp.setRemaining(e.getLimit()-e.getRedeemed_quantity());
        return componentVounchResp;
    }

    public RoyaltyRule getRoyaltyRule() {
        return royaltyRule;
    }

    public void setRoyaltyRule(RoyaltyRule royaltyRule) {
        this.royaltyRule = royaltyRule;
    }


    private Long componentRight;

    public Long getComponentRight() {
        return componentRight;
    }

    public void setComponentRight(Long componentRightId) {
        this.componentRight = componentRightId;
    }

    private EnumComponentVoucherStatus status;

    public EnumComponentVoucherStatus getStatus() {
        return status;
    }

    public void setStatus(EnumComponentVoucherStatus status) {
        this.status = status;
    }

    private String redeem_voucher_key;

    private Voucher voucher;
    private Long voucherId;

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }


    private Product product;


    private String note;


    private Long redeemed;// (integer, required) - How many times a voucher has already been redeemed.

    private Integer redeemed_amount;// (integer) - Total Amount redeemed by the voucher. Value is multiplied by 100 to precisely represent 2 decimal places. For example, $100 balance is written as 10000.



    private Long limit;  //一次， 无数次，  五次，

    public Long getLimit() {
        return limit;
    }

    public Long getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(Long redeemed) {
        this.redeemed = redeemed;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public void setComponent(Long componentId) {
        this.component = componentId;
    }

    public Long getComponent() {
        return component;
    }

    public void setRoyaltyRuleId(Long royaltyRuleId) {
        this.royaltyRuleId = royaltyRuleId;
    }

    public Long getRoyaltyRuleId() {
        return royaltyRuleId;
    }


    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setDuration(EnumDuration duration) {
        this.duration = duration;
    }

    public EnumDuration getDuration() {
        return duration;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getUser() {
        return user;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplier() {
        return supplier;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setDuration_text(String duration_text) {
        this.duration_text = duration_text;
    }

    public String getDuration_text() {
        return duration_text;
    }

    public void setName_long(String name_long) {
        this.name_long = name_long;
    }

    public String getName_long() {
        return name_long;
    }

    public void setLong_desc(String Long_desc) {
        this.Long_desc = Long_desc;
    }

    public String getLong_desc() {
        return Long_desc;
    }

    public void setSupplier_code(String supplier_code) {
        this.supplier_code = supplier_code;
    }

    public String getSupplier_code() {
        return supplier_code;
    }

    public void setRemaining(Long remaining) {
        this.remaining = remaining;
    }

    public Long getRemaining() {
        return remaining;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }

    public String getCode_url() {
        return code_url;
    }

    public void setCode_base64_src(String code_base64_src) {

        this.code_base64_src = code_base64_src;
    }

    public String getCode_base64_src() {
        return code_base64_src;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setComponentRightCode(String componentRightCode) {

        this.componentRightCode = componentRightCode;
    }

    public String getComponentRightCode() {
        return componentRightCode;
    }
}
