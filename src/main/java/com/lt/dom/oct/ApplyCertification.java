package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumApplicationType;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumRequestStatus;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class ApplyCertification {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private long supplier;

    @Size(max = 10000)
    private String additional_info;

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private EnumApplicationType type;
    private String idId;
    private String url;
    private long source;
    private EnumRequestStatus status;

    public EnumRequestStatus getStatus() {
        return status;
    }

    public void setStatus(EnumRequestStatus status) {
        this.status = status;
    }

    public long getSource() {
        return source;
    }

    public void setSource(long source) {
        this.source = source;
    }

    public EnumApplicationType getType() {
        return type;
    }

    public void setType(EnumApplicationType type) {
        this.type = type;
    }

    public String getIdId() {
        return idId;
    }

    public void setIdId(String idId) {
        this.idId = idId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getSupplier() {
        return supplier;
    }
}
