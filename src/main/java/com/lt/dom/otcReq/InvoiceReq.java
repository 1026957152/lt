package com.lt.dom.otcReq;

import com.lt.dom.InvoiceItem;
import com.lt.dom.OctResp.BaseResp;
import com.lt.dom.oct.History;
import com.lt.dom.oct.Invoice;
import com.lt.dom.otcenum.EnumInvoiceStatus;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class InvoiceReq extends BaseResp {




    private LocalDateTime generatdOn;

    private LocalDateTime postedOn;
    private Long subscription;

    private String privateNote;

    public String getPrivateNote() {
        return privateNote;
    }

    public void setPrivateNote(String privateNote) {
        this.privateNote = privateNote;
    }

    public List<LinkedTxnReq> getLinkedTxns() {
        return linkedTxns;
    }

    public void setLinkedTxns(List<LinkedTxnReq> linkedTxns) {
        this.linkedTxns = linkedTxns;
    }

    private List<LinkedTxnReq> linkedTxns;




    private Double subTotal;
    private Double taxAmount;
    private Double totalAmount;
    private Double amountDue;

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }

    private String pdf_url;


    private Boolean autopay;
    private Boolean paid;
    private LocalDate date;


    private String customer;


    public static InvoiceReq from(Invoice subscription) {

        InvoiceReq invoiceResp = new InvoiceReq();
        invoiceResp.setAutopay(subscription.getAutopay());
        invoiceResp.setDate(subscription.getDate());


        invoiceResp.setGeneratdOn(subscription.getGeneratdOn());

        invoiceResp.setPdf_url(subscription.getPdf_url());
        invoiceResp.setSubscription(subscription.getSubscription());
        invoiceResp.setSubTotal(subscription.getSubTotal());
        invoiceResp.setPostedOn(subscription.getPostedOn());
        invoiceResp.setCreatedDate(subscription.getCreatedDate());

        invoiceResp.setCustomer("subscription.getCustomer()");

        return invoiceResp;
    }



    public LocalDateTime getGeneratdOn() {
        return generatdOn;
    }

    public void setGeneratdOn(LocalDateTime generatdOn) {
        this.generatdOn = generatdOn;
    }

    public LocalDateTime getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(LocalDateTime postedOn) {
        this.postedOn = postedOn;
    }

    public Long getSubscription() {
        return subscription;
    }

    public void setSubscription(Long subscription) {
        this.subscription = subscription;
    }


    public Double getSubTotal() {
        return subTotal;
    }

    public String getPdf_url() {
        return pdf_url;
    }

    public void setPdf_url(String pdf_url) {
        this.pdf_url = pdf_url;
    }



    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Boolean getAutopay() {
        return autopay;
    }

    public void setAutopay(Boolean autopay) {
        this.autopay = autopay;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }




    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomer() {
        return customer;
    }



}
