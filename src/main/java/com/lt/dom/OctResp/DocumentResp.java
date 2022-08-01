package com.lt.dom.OctResp;

import com.lt.dom.controllerOct.FileUploadController;
import com.lt.dom.oct.Document;
import com.lt.dom.oct.Export;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumExportStatus;
import com.lt.dom.otcenum.EnumExportVoucher;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;


public class DocumentResp {


    private String source_id;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;


    private String resultUrl;
    private String code;
    private String original_filename;
    private EnumDocumentType category;
    public static DocumentResp onefrom(Document x) {
        DocumentResp exportReq = new DocumentResp();

        exportReq.setCode(x.getCode());
        exportReq.setCategory(x.getType());
        exportReq.setCreated_at(x.getCreated_at());
        exportReq.setUpdated_at(x.getUpdated_at());
        exportReq.setOriginal_filename(x.getOriginalFilename());
        String url = MvcUriComponentsBuilder
                .fromMethodName(FileUploadController.class,
                        "getFile",
                        x.getFileName()
                ).build().toString();

        exportReq.setResultUrl(url);
        return exportReq;

    }
    public static List<DocumentResp> Listfrom(List<Document> travelers) {
        List<DocumentResp> documentResps = travelers.stream().map(x->{
            DocumentResp exportReq = new DocumentResp();

            exportReq.setCode(x.getCode());
            exportReq.setCategory(x.getType());
            exportReq.setCreated_at(x.getCreated_at());
            exportReq.setUpdated_at(x.getUpdated_at());
            exportReq.setOriginal_filename(x.getOriginalFilename());
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileUploadController.class,
                            "getFile",
                            x.getFileName()
                    ).build().toString();

            exportReq.setResultUrl(url);
            return exportReq;
        }).collect(Collectors.toList());
        return documentResps;
    }


    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
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



    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }


    public static Map<EnumDocumentType,List<DocumentResp>> from(List<Document> export) {

        List<DocumentResp> documentResps = export.stream().map(x->{
            DocumentResp exportReq = new DocumentResp();

            exportReq.setCode(x.getCode());
            exportReq.setCategory(x.getType());
            exportReq.setCreated_at(x.getCreated_at());
            exportReq.setUpdated_at(x.getUpdated_at());
            exportReq.setOriginal_filename(x.getOriginalFilename());
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileUploadController.class,
                            "getFile",
                            x.getFileName()
                    ).build().toString();

            exportReq.setResultUrl(url);
            return exportReq;
        }).collect(Collectors.toList());


        Map<EnumDocumentType,List<DocumentResp>> mm = documentResps.stream().collect(groupingBy(x->x.getCategory()));

        return mm;
    }


    public void setOriginal_filename(String original_filename) {
        this.original_filename = original_filename;
    }

    public String getOriginal_filename() {
        return original_filename;
    }

    public void setCategory(EnumDocumentType category) {
        this.category = category;
    }

    public EnumDocumentType getCategory() {
        return category;
    }
}
