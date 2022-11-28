package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.ComponentVounch;
import com.lt.dom.oct.RatePlan;
import com.lt.dom.oct.RoyaltyRule;
import com.lt.dom.otcenum.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;



public class BillRunReq {   // 这个是 下单的时候， 从 product 中生成 的


    private List<Long> subscriptions;

    public List<Long> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Long> subscriptions) {
        this.subscriptions = subscriptions;
    }

    private LocalDateTime targetDate;

    public LocalDateTime getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDateTime targetDate) {
        this.targetDate = targetDate;
    }


    private EnumBillRecurringInterval repeats;



    private EnumTypesOfBillRuns type;

    public EnumTypesOfBillRuns getType() {
        return type;
    }

    public void setType(EnumTypesOfBillRuns type) {
        this.type = type;
    }

    private String name;
    private String note;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public void setRepeats(EnumBillRecurringInterval repeats) {
        this.repeats = repeats;
    }

    public EnumBillRecurringInterval getRepeats() {
        return repeats;
    }



}
