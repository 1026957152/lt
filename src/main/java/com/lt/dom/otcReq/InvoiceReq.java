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


    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumInvoiceStatus status;

    @NotNull
    private LocalDateTime generatdOn;

    private LocalDateTime postedOn;
    private Long subscription;

    @NotNull
    private Float total;
    @NotNull
    private Float subTotal;
    private String pdf_url;

    @OneToMany(mappedBy="invoice",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<InvoiceItem> items;


    private Boolean autopay;
    private Boolean paid;
    private LocalDate date;
    private String status_text;
    private String code;
    private String customer;
    private List histories;

    public static InvoiceReq from(Invoice subscription) {

        InvoiceReq invoiceResp = new InvoiceReq();
        invoiceResp.setAutopay(subscription.getAutopay());
        invoiceResp.setDate(subscription.getDate());
        invoiceResp.setItems(subscription.getItems());
        invoiceResp.setStatus(subscription.getStatus());
        invoiceResp.setStatus_text(subscription.getStatus().toString());
        invoiceResp.setGeneratdOn(subscription.getGeneratdOn());
        invoiceResp.setTotal(subscription.getTotal());
        invoiceResp.setPdf_url(subscription.getPdf_url());
        invoiceResp.setSubscription(subscription.getSubscription());
        invoiceResp.setSubTotal(subscription.getSubTotal());
        invoiceResp.setPostedOn(subscription.getPostedOn());
        invoiceResp.setCreatedDate(subscription.getCreatedDate());
        invoiceResp.setCode(subscription.getCode());
        invoiceResp.setCustomer("subscription.getCustomer()");

        return invoiceResp;
    }

    public EnumInvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(EnumInvoiceStatus status) {
        this.status = status;
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

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Float subTotal) {
        this.subTotal = subTotal;
    }

    public String getPdf_url() {
        return pdf_url;
    }

    public void setPdf_url(String pdf_url) {
        this.pdf_url = pdf_url;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
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

    public void withRateplans(Invoice subscription) {

        this.setItems(subscription.getItems());
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomer() {
        return customer;
    }

    public void withHistory(List list) {

        this.setHistories(list);

    }


    public void setHistories(List histories) {
        this.histories = histories;
    }

    public List<History> getHistories() {
        return histories;
    }
}
