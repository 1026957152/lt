package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lt.dom.controllerOct.FileUploadController;
import com.lt.dom.oct.Export;
import com.lt.dom.otcenum.EnumExportStatus;
import com.lt.dom.otcenum.EnumExportVoucher;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;


public class ExportResp {





    private String source_id;
    private EnumExportVoucher exported_object;
    private String exported_object_text;

    public String getExported_object_text() {
        return exported_object_text;
    }

    public void setExported_object_text(String exported_object_text) {
        this.exported_object_text = exported_object_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    /** 响应时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created_at;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated_at;
    private EnumExportStatus status;
    private String status_text;
    private String resultUrl;
    private String code;
    private LocalDateTime done_at;
    private long duration;
    private long total_succeeded;

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    public EnumExportVoucher getExported_object() {
        return exported_object;
    }

    public void setExported_object(EnumExportVoucher exported_object) {
        this.exported_object = exported_object;
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

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }


    public static ExportResp from(Export export) {
        ExportResp exportReq = new ExportResp();
        exportReq.setExported_object(export.getExported_object());
        exportReq.setExported_object_text(export.getExported_object().toString());

        exportReq.setCode(export.getCode());
        exportReq.setStatus(export.getStatus());
        exportReq.setStatus_text(export.getStatus().toString());

        exportReq.setCreated_at(export.getCreated_at());

        exportReq.setTotal_succeeded(export.getTotal_succeeded());
        if(export.getStatus().equals(EnumExportStatus.DONE)){


            exportReq.setResultUrl(FileStorageServiceImpl.url(export.getCode()+".xlsx"));
            exportReq.setDone_at(export.getDone_at());
            exportReq.setDuration(Duration.between(export.getCreated_at(),export.getDone_at()).getSeconds());
        }




        return exportReq;
    }


    public void setDone_at(LocalDateTime done_at) {
        this.done_at = done_at;
    }

    public LocalDateTime getDone_at() {
        return done_at;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

    public void setTotal_succeeded(long total_succeeded) {
        this.total_succeeded = total_succeeded;
    }

    public long getTotal_succeeded() {
        return total_succeeded;
    }
}
