package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumBillRecurringInterval;
import com.lt.dom.otcenum.EnumBillRunStatus;
import com.lt.dom.otcenum.EnumTypesOfBillRuns;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
public class BillRunProcessingSubscription extends Base{   // 这个是 下单的时候， 从 product 中生成 的

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="bill_run", nullable=false)
    private BillRun billRun;

    private Long subscription;

    public BillRun getBillRun() {
        return billRun;
    }

    public void setBillRun(BillRun billRun) {
        this.billRun = billRun;
    }

    public Long getSubscription() {
        return subscription;
    }

    public void setSubscription(Long subscription) {
        this.subscription = subscription;
    }
}
