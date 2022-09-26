package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumExportStatus;
import com.lt.dom.otcenum.EnumExportVoucher;
import com.lt.dom.otcenum.EnumImportCVS;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
//@TypeDef(name = "json", typeClass = JsonStringType.class)
public class ImportExcel {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private String source_id;
    private EnumImportCVS importCSV_object;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private EnumExportStatus status;
    private long amount;

    private String parameters_order;
    private String parameters_fields;
    
//##@Column(unique=true) 
private String code;
    private LocalDateTime done_at;


    private String attachment;
    private long relativeid;

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
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

    public EnumImportCVS getImportCSV_object() {
        return importCSV_object;
    }

    public void setImportCSV_object(EnumImportCVS importCSV_object) {
        this.importCSV_object = importCSV_object;
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

    public String getParameters_order() {
        return parameters_order;
    }

    public void setParameters_order(String parameters_order) {
        this.parameters_order = parameters_order;
    }

    public String getParameters_fields() {
        return parameters_fields;
    }

    public void setParameters_fields(String parameters_fields) {
        this.parameters_fields = parameters_fields;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getDone_at() {
        return done_at;
    }

    public void setDone_at(LocalDateTime done_at) {
        this.done_at = done_at;
    }

    public void setRelativeid(long relativeid) {
        this.relativeid = relativeid;
    }

    public long getRelativeid() {
        return relativeid;
    }
}
