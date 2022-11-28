package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumDocumentType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Document {
    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumDocumentType type;

    @NotEmpty
    
//##@Column(unique=true) 
private String code;


    private long raletiveId;
    @NotNull
    private LocalDateTime created_at;
    @NotEmpty
    private String fileName;
    private LocalDateTime updated_at;
    @NotEmpty
    private String originalFilename;
    @NotEmpty
    private String extension;

    @NotNull
    private long size;
    private String mimeType; //MIME type
    private boolean image;
    private String reference;
    private String tempDocumentCode;


    @Column(name = "index_")
    private Integer index;
    @Column(name = "desc_")
    private String desc;
    private Boolean visiable;
    private String caption;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

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

    public void setImage(boolean image) {
        this.image = image;
    }

    public boolean getImage() {
        return image;
    }

    public void setReference(String reference) {

        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }

    public void setTempDocumentCode(String tempDocumentCode) {
        this.tempDocumentCode = tempDocumentCode;
    }

    public String getTempDocumentCode() {
        return tempDocumentCode;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setVisiable(Boolean visiable) {
        this.visiable = visiable;
    }

    public Boolean getVisiable() {
        return visiable;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
