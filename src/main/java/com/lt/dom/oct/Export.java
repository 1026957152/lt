package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumExportStatus;
import com.lt.dom.otcenum.EnumExportVoucher;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Export {
    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private String source_id;
    private EnumExportVoucher exported_object;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private EnumExportStatus status;
    private long amount;

    private String parameters_order;
    private String parameters_fields;
    
//##@Column(unique=true) 
private String code;
    private LocalDateTime done_at;

    public EnumExportVoucher getExported_object() {
        return exported_object;
    }

    public void setExported_object(EnumExportVoucher exported_object) {
        this.exported_object = exported_object;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }


    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public EnumExportStatus getStatus() {
        return status;
    }

    public void setStatus(EnumExportStatus status) {
        this.status = status;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setDone_at(LocalDateTime done_at) {
        this.done_at = done_at;
    }

    public LocalDateTime getDone_at() {
        return done_at;
    }

    private long total_succeeded;

    public long getTotal_succeeded() {
        return total_succeeded;
    }

    public void setTotal_succeeded(long total_succeeded) {
        this.total_succeeded = total_succeeded;
    }
}
