package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lt.dom.otcenum.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class RefundLineItem extends Base {


    private Integer quantity;
    private Long line_item;

    @Enumerated(EnumType.STRING)
    private EnumRestock_type restock_type;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="refund", nullable=false)
    Refund refund;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getLine_item() {
        return line_item;
    }

    public void setLine_item(Long line_item) {
        this.line_item = line_item;
    }

    public EnumRestock_type getRestock_type() {
        return restock_type;
    }

    public void setRestock_type(EnumRestock_type restock_type) {
        this.restock_type = restock_type;
    }

    public Refund getRefund() {
        return refund;
    }

    public void setRefund(Refund refund) {
        this.refund = refund;
    }
}
