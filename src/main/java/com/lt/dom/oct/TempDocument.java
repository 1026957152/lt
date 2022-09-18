package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumDocumentType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TempDocument {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;


    
@Column(unique=true) 
private String code;


    private LocalDateTime created_at;
    private String fileName;
    private LocalDateTime updated_at;
    private String originalFilename;
    private String url;
    private Long size;
    private boolean image;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    public boolean getImage() {
        return image;
    }
}
