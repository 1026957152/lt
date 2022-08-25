package com.lt.dom.specification;


import com.lt.dom.otcenum.EnumTourBookingStatus_;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public class RedeptionQueryfieldsCriteria {

   // @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
   @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
   private LocalDateTime created_to;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created_from;
    private String holder;//	email~"amyt"	string
    private String redeemer;//	metadata["key"]:"value"	token
    private String voucher_code;//		name~"amy"	string

    public LocalDateTime getCreated_to() {
        return created_to;
    }

    public void setCreated_to(LocalDateTime created_to) {
        this.created_to = created_to;
    }

    public LocalDateTime getCreated_from() {
        return created_from;
    }

    public void setCreated_from(LocalDateTime created_from) {
        this.created_from = created_from;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getRedeemer() {
        return redeemer;
    }

    public void setRedeemer(String redeemer) {
        this.redeemer = redeemer;
    }

    public String getVoucher_code() {
        return voucher_code;
    }

    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}