package com.lt.dom.oct.Axh;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.enum_.EnumXhPushRequestStatus;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.Length;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


@Entity
public class XydToXhPushRequest {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonProperty("id")
    private long id;


    @NotNull
    private Integer orderIdX申请id;
    private EnumXhPushRequestStatus status_xh;
    private LocalDateTime createdDate;

    public Integer getOrderIdX申请id() {
        return orderIdX申请id;
    }

    public void setOrderIdX申请id(Integer orderIdX申请id) {
        this.orderIdX申请id = orderIdX申请id;
    }

    @Length(max = 10000)
    private String json;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }


    public void setStatus_xh(EnumXhPushRequestStatus status_xh) {
        this.status_xh = status_xh;
    }

    public EnumXhPushRequestStatus getStatus_xh() {
        return status_xh;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}
