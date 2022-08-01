package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumDocumentType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Document {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private EnumDocumentType type;

    private String code;


    private long raletiveId;
    private LocalDateTime created_at;
    private String fileName;
    private LocalDateTime updated_at;
    private String originalFilename;
    private String extension;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EnumDocumentType getType() {
        return type;
    }

    public void setType(EnumDocumentType type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getRaletiveId() {
        return raletiveId;
    }

    public void setRaletiveId(long raletiveId) {
        this.raletiveId = raletiveId;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}
