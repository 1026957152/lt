package com.lt.dom.OctResp;

import com.lt.dom.oct.Payment;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


public class PaymentResp  extends RepresentationModel<PaymentResp> {


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

    public static PaymentResp from(Payment payment) {

        PaymentResp paymentResp = new PaymentResp();
        paymentResp.setCustomer(payment.getCustomer());
        paymentResp.setBalance(payment.getBalance());
        paymentResp.setCreated_at(payment.getCreated_at());
        paymentResp.setAmount(payment.getAmount());
        return paymentResp;
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
}
