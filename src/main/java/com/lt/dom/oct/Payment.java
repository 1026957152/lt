package com.lt.dom.oct;

import com.lt.dom.otcenum.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Payment extends Base{

//##@Column(unique=true) 
private String code;//	string	Object type, payment

    private String object;//	string	Object type, payment
    private long customer;//	integer	Associated Customer, if any
    private LocalDateTime date;//	timestamp	Payment date
    private String currency;//	string	3-letter ISO code
    private int amount;//	number	Payment amount
    private int balance;//	number	Unapplied amount remaining
    private boolean matched;//	boolean	When true there is a CashMatch suggestion
    private boolean voided;//	boolean	Indicates that the payment was voided
    private String method;//	string	Payment instrument used
    private String  ach_sender_id;//	string	ACH PPD ID
    private String reference;//	string	Reference number, i.e. check #
    private String  source;//	string	Source of the payment
    private String notes;//	string	Internal notes
    private String  charge;//	object	Charge object included for processed payments
    private String applied_to;//	array	List of Payment Applications
    private String  pdf_url;//	string	URL where receipt PDF can be downloaded
    private LocalDateTime created_at;//	timestamp	Timestamp when created
    private LocalDateTime updated_at;//	timestamp	Timestamp when updated
    private long recipient;

    private String payment_method_json;


    private EnumPayChannel payment_method;


    private EnumPaymentSourceType sourceType;
    private long booking;
    private EnumIdType log_customer_id_type;
    private String log_customer_phone;
    private String log_customer_id_number;
    private String log_customer_name;

    public EnumPaymentSourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(EnumPaymentSourceType sourceType) {
        this.sourceType = sourceType;
    }

    @Enumerated(EnumType.STRING)
    private EnumPaymentStatus status;
    private EnumPaymentFlow payment_flow;
    private boolean split;
    private LocalDateTime expireTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public long getCustomer() {
        return customer;
    }

    public void setCustomer(long customer) {
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public boolean isVoided() {
        return voided;
    }

    public void setVoided(boolean voided) {
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

    public void setRecipient(long recipient) {
        this.recipient = recipient;
    }

    public long getRecipient() {
        return recipient;
    }



    public String getPayment_method_json() {
        return payment_method_json;
    }

    public void setPayment_method_json(String payment_method_json) {
        this.payment_method_json = payment_method_json;
    }


    public void setStatus(EnumPaymentStatus status) {
        this.status = status;
    }

    public EnumPaymentStatus getStatus() {
        return status;
    }

    public void setPayment_flow(EnumPaymentFlow payment_flow) {
        this.payment_flow = payment_flow;
    }

    public EnumPaymentFlow getPayment_flow() {
        return payment_flow;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }

    public boolean getSplit() {
        return split;
    }

    public void setExpireTime(LocalDateTime expireTime) {

        this.expireTime = expireTime;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setPayment_method(EnumPayChannel payment_method) {

        this.payment_method = payment_method;
    }

    public EnumPayChannel getPayment_method() {
        return payment_method;
    }

    public long getBooking() {
        return booking;
    }

    public void setBooking(long booking) {
        this.booking = booking;
    }

    public void setLog_customer_id_type(EnumIdType log_customer_id_type) {

        this.log_customer_id_type = log_customer_id_type;
    }

    public EnumIdType getLog_customer_id_type() {
        return log_customer_id_type;
    }

    public void setLog_customer_phone(String log_customer_phone) {
        this.log_customer_phone = log_customer_phone;
    }

    public String getLog_customer_phone() {
        return log_customer_phone;
    }

    public void setLog_customer_id_number(String log_customer_id_number) {
        this.log_customer_id_number = log_customer_id_number;
    }

    public String getLog_customer_id_number() {
        return log_customer_id_number;
    }

    public void setLog_customer_name(String log_customer_name) {
        this.log_customer_name = log_customer_name;
    }

    public String getLog_customer_name() {
        return log_customer_name;
    }
}
