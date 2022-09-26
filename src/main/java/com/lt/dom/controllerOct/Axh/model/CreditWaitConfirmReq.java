package com.lt.dom.controllerOct.Axh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreditWaitConfirmReq {

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }



    @NotNull
   // @JsonProperty("creditAmount")
    private Integer creditAmount_授信额度; // 授信额度(万元)：

    @NotNull
    //@JsonProperty("endTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime_授信额度有效期至;  // 授信额度有效期至：yyyy-MM-dd

    public Integer getCreditAmount_授信额度() {
        return creditAmount_授信额度;
    }

    public void setCreditAmount_授信额度(Integer creditAmount_授信额度) {
        this.creditAmount_授信额度 = creditAmount_授信额度;
    }

    public LocalDate getEndTime_授信额度有效期至() {
        return endTime_授信额度有效期至;
    }

    public void setEndTime_授信额度有效期至(LocalDate endTime_授信额度有效期至) {
        this.endTime_授信额度有效期至 = endTime_授信额度有效期至;
    }
}
