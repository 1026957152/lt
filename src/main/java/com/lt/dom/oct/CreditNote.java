package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lt.dom.InvoiceItem;
import com.lt.dom.otcenum.EnumInvoiceStatus;
import com.lt.dom.otcenum.EnumInvoiceTypes;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class CreditNote extends Base{
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumInvoiceTypes type;
    private Long supplier;

    private Double amount;

    public EnumInvoiceTypes getType() {
        return type;
    }

    public void setType(EnumInvoiceTypes type) {
        this.type = type;
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumInvoiceStatus status;


    private Double discount_amount;

    private Double total;
    @NotNull
    private Double subTotal;
    private String pdf_url;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="invoice", nullable=false)
    private Invoice invoice;


    private String memo;






    private String code;

    private String privateNote;

    public String getPrivateNote() {
        return privateNote;
    }

    public void setPrivateNote(String privateNote) {
        this.privateNote = privateNote;
    }

    public EnumInvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(EnumInvoiceStatus status) {
        this.status = status;
    }



    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public String getPdf_url() {
        return pdf_url;
    }

    public void setPdf_url(String pdf_url) {
        this.pdf_url = pdf_url;
    }



    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public Long getSupplier() {
        return supplier;
    }

}
