package com.lt.dom.OctResp;


import com.lt.dom.oct.Traveler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class VoucherLiabilityResp {


    private LocalDateTime booking_data;///
    private String voucher_code;///
    private String status;///
    private int outstanding_value;///
    private LocalDate voucher_issued;///
    private LocalDate voucher_expiry;///
    private int type;///
    private String product_name;///
    private boolean includes_extras;///
    private boolean includes_taxes_fees;///
    private boolean is_reusable;///
    private String internal_notes;///

    public LocalDateTime getBooking_data() {
        return booking_data;
    }

    public void setBooking_data(LocalDateTime booking_data) {
        this.booking_data = booking_data;
    }

    public String getVoucher_code() {
        return voucher_code;
    }

    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOutstanding_value() {
        return outstanding_value;
    }

    public void setOutstanding_value(int outstanding_value) {
        this.outstanding_value = outstanding_value;
    }

    public LocalDate getVoucher_issued() {
        return voucher_issued;
    }

    public void setVoucher_issued(LocalDate voucher_issued) {
        this.voucher_issued = voucher_issued;
    }

    public LocalDate getVoucher_expiry() {
        return voucher_expiry;
    }

    public void setVoucher_expiry(LocalDate voucher_expiry) {
        this.voucher_expiry = voucher_expiry;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public boolean isIncludes_extras() {
        return includes_extras;
    }

    public void setIncludes_extras(boolean includes_extras) {
        this.includes_extras = includes_extras;
    }

    public boolean isIncludes_taxes_fees() {
        return includes_taxes_fees;
    }

    public void setIncludes_taxes_fees(boolean includes_taxes_fees) {
        this.includes_taxes_fees = includes_taxes_fees;
    }

    public boolean isIs_reusable() {
        return is_reusable;
    }

    public void setIs_reusable(boolean is_reusable) {
        this.is_reusable = is_reusable;
    }

    public String getInternal_notes() {
        return internal_notes;
    }

    public void setInternal_notes(String internal_notes) {
        this.internal_notes = internal_notes;
    }
}
