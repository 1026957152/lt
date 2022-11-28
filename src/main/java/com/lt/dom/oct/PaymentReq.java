package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumPayChannel;
import com.lt.dom.otcenum.EnumPaymentSourceType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@Data
public class PaymentReq {

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


    private EnumPaymentSourceType sourceType;


    @JsonProperty("cash_details")
    private CashDetailsDTO cashDetails;

    public EnumPaymentSourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(EnumPaymentSourceType sourceType) {
        this.sourceType = sourceType;
    }

    private EnumPayChannel payment_method;


    private boolean split;
    private LocalDateTime expireTime;


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

    public EnumPayChannel getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(EnumPayChannel payment_method) {
        this.payment_method = payment_method;
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

    @NoArgsConstructor
    @Data
    public static class CashDetailsDTO {
        @JsonProperty("buyer_supplied_money")
        private BuyerSuppliedMoneyDTO buyerSuppliedMoney;

        @NoArgsConstructor
        @Data
        public static class BuyerSuppliedMoneyDTO {
            @JsonProperty("amount")
            private Integer amountX;
            @JsonProperty("currency")
            private String currency;
        }
    }
}
