package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.credit.EnumActionType;
import com.lt.dom.credit.EnumHistoryType;
import com.lt.dom.oct.Base;
import com.lt.dom.oct.History;
import com.lt.dom.repository.HistoryRepository;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoryResp extends BaseResp {


    @JsonProperty("bill_id")
    private Long billId;
    @JsonProperty("description")
    private String description;


    private EnumHistoryType type;
/*    @JsonProperty("date")
    private String date;
    @JsonProperty("date_description")
    private String dateDescription;
    @JsonProperty("time")
    private String time;*/

    private EnumActionType operationType;

    public static HistoryResp from(History e) {
        HistoryResp historyResp = new HistoryResp();
        historyResp.setBillId(e.getBillId());
        historyResp.setDescription(e.getDescription());
        historyResp.setType(e.getType());
        historyResp.setOperationType(e.getOperationType());
        historyResp.setCreatedDate(e.getCreatedDate());
        return historyResp;
    }
/*
    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonProperty("transaction_type")
    private EnumActionType transactionType;
*/

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public EnumHistoryType getType() {
        return type;
    }

    public void setType(EnumHistoryType type) {
        this.type = type;
    }

    public EnumActionType getOperationType() {
        return operationType;
    }

    public void setOperationType(EnumActionType operationType) {
        this.operationType = operationType;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
