package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


@Entity
public class BillRun extends Base{   // 这个是 下单的时候， 从 product 中生成 的



    private EnumBillRunStatus status;
    private String code;
    private LocalDateTime current_period_end;
    private LocalDateTime current_period_start;
    private EnumBillRecurringInterval repeats;


    @OneToMany(mappedBy="billRun",
            cascade = CascadeType.ALL,
            orphanRemoval = true)

   // @OneToMany
    private List<BillRunProcessingSubscription> processingSubscriptions;

    public List<BillRunProcessingSubscription> getProcessingSubscriptions() {
        return processingSubscriptions;
    }

    public void setProcessingSubscriptions(List<BillRunProcessingSubscription> processingSubscriptions) {
        this.processingSubscriptions = processingSubscriptions;
    }

    @NotNull
    private Long supplier;

    private Boolean active = true;





    private String name;
    private String note;
    private EnumTypesOfBillRuns type;
    private LocalDateTime targetDate;



    public EnumBillRunStatus getStatus() {
        return status;
    }

    public void setStatus(EnumBillRunStatus status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCurrent_period_end() {
        return current_period_end;
    }

    public void setCurrent_period_end(LocalDateTime current_period_end) {
        this.current_period_end = current_period_end;
    }

    public LocalDateTime getCurrent_period_start() {
        return current_period_start;
    }

    public void setCurrent_period_start(LocalDateTime current_period_start) {
        this.current_period_start = current_period_start;
    }

    public EnumBillRecurringInterval getRepeats() {
        return repeats;
    }

    public void setRepeats(EnumBillRecurringInterval repeats) {
        this.repeats = repeats;
    }

    public Long getSupplier() {
        return supplier;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

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


    public void setType(EnumTypesOfBillRuns type) {
        this.type = type;
    }

    public EnumTypesOfBillRuns getType() {
        return type;
    }

    public void setTargetDate(LocalDateTime targetDate) {
        this.targetDate = targetDate;
    }

    public LocalDateTime getTargetDate() {
        return targetDate;
    }
}
