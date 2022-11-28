package com.lt.dom.oct;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.credit.EnumActionType;
import com.lt.dom.credit.EnumHistoryType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;


@Entity
public class History extends Base{


    @JsonProperty("bill_id")
    private Long billId;
    @JsonProperty("description")
    private String description;

    @JsonProperty("commented_by")
    private String commentedBy;
    private EnumHistoryType type;
/*    @JsonProperty("date")
    private String date;
    @JsonProperty("date_description")
    private String dateDescription;
    @JsonProperty("time")
    private String time;*/

    private EnumActionType operationType;
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

    public String getCommentedBy() {
        return commentedBy;
    }

    public void setCommentedBy(String commentedBy) {
        this.commentedBy = commentedBy;
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
