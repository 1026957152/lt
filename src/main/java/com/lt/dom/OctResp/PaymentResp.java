package com.lt.dom.OctResp;

import cn.hutool.core.io.unit.DataUnit;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Payment;
import com.lt.dom.otcenum.EnumPaymentStatus;
import com.lt.dom.util.DateUtils;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentResp  extends RepresentationModel<PaymentResp> {


    private String object;//	string	Object type, payment
    private Long customer;//	Integereger	Associated Customer, if any
    private LocalDateTime date;//	timestamp	Payment date
    private String currency;//	string	3-letter ISO code
    private Integer amount;//	number	Payment amount
    private Integer balance;//	number	Unapplied amount remaining
    private Boolean matched;//	Boolean	When true there is a CashMatch suggestion
    private Boolean voided;//	Boolean	Indicates that the payment was voided
    private String method;//	string	Payment instrument used
    private String  ach_sender_id;//	string	ACH PPD ID
    private String reference;//	string	Reference number, i.e. check #
    private String  source;//	string	Source of the payment
    private String notes;//	string	Integerernal notes
    private String  charge;//	object	Charge object included for processed payments
    private String applied_to;//	array	List of Payment Applications
    private String  pdf_url;//	string	URL where receipt PDF can be downloaded
    private LocalDateTime created_at;//	timestamp	Timestamp when created
    private LocalDateTime updated_at;//	timestamp	Timestamp when updated
    private List<EntityModel<PaymentMethodResp>> methods;
    private BookingResp booking;
    private String status_text;
    private EnumPaymentStatus status;
    private LocalDateTime expireTime;
    private String time_remaining;
    private long seconds_remaining;

    public static PaymentResp from(Payment payment) {

        PaymentResp paymentResp = new PaymentResp();
      //  paymentResp.setCustomer(payment.getCustomer());
     //   paymentResp.setBalance(payment.getBalance());
        paymentResp.setCreated_at(payment.getCreated_at());
        paymentResp.setAmount(payment.getAmount());
        paymentResp.setStatus(payment.getStatus());
        paymentResp.setExpire_time(payment.getExpireTime());

        Instant instant = payment.getExpireTime().toInstant(ZoneOffset.UTC);
        Date date = Date.from(instant);
        paymentResp.setTime_remaining(DateUtils.fromDeadline(date));
        paymentResp.setSeconds_remaining(DateUtils.secondfromDeadline(date));

        paymentResp.setStatus_text(payment.getStatus().toString());
        return paymentResp;
    }



    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Long getCustomer() {
        return customer;
    }

    public void setCustomer(Long customer) {
        this.customer = customer;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Boolean isMatched() {
        return matched;
    }

    public void setMatched(Boolean matched) {
        this.matched = matched;
    }

    public Boolean isVoided() {
        return voided;
    }

    public void setVoided(Boolean voided) {
        this.voided = voided;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAch_sender_id() {
        return ach_sender_id;
    }

    public void setAch_sender_id(String ach_sender_id) {
        this.ach_sender_id = ach_sender_id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getApplied_to() {
        return applied_to;
    }

    public void setApplied_to(String applied_to) {
        this.applied_to = applied_to;
    }

    public String getPdf_url() {
        return pdf_url;
    }

    public void setPdf_url(String pdf_url) {
        this.pdf_url = pdf_url;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public void setMethods(List<EntityModel<PaymentMethodResp>> methods) {
        this.methods = methods;
    }

    public List<EntityModel<PaymentMethodResp>> getMethods() {
        return methods;
    }

    public void setBooking(BookingResp booking) {
        this.booking = booking;
    }

    public BookingResp getBooking() {
        return booking;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus(EnumPaymentStatus status) {
        this.status = status;
    }

    public EnumPaymentStatus getStatus() {
        return status;
    }

    public void setExpire_time(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setTime_remaining(String time_remaining) {
        this.time_remaining = time_remaining;
    }

    public String getTime_remaining() {
        return time_remaining;
    }

    public void setSeconds_remaining(long seconds_remaining) {
        this.seconds_remaining = seconds_remaining;
    }

    public long getSeconds_remaining() {
        return seconds_remaining;
    }
}
