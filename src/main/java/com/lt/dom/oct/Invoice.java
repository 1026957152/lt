package com.lt.dom.oct;

import com.lt.dom.InvoiceItem;
import com.lt.dom.OctResp.InvoiceResp;
import com.lt.dom.otcenum.EnumInvoiceStatus;
import com.lt.dom.otcenum.EnumInvoiceTypes;
import com.lt.dom.otcenum.EnumSubscriptionStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Invoice extends Base{
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumInvoiceTypes type;

    public EnumInvoiceTypes getType() {
        return type;
    }

    public void setType(EnumInvoiceTypes type) {
        this.type = type;
    }

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
    private String code;

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


    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
