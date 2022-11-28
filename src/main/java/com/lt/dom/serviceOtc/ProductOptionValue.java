package com.lt.dom.serviceOtc;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.Base;
import com.lt.dom.otcenum.EnumAssetQtStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;


@Entity
public class ProductOptionValue extends Base {




    private String name;
    private String code;



    private Long product;


    @JsonProperty("is_default")
    private Boolean isDefault;
    @JsonProperty("label")
    private String label;
    @JsonProperty("sort_order")
    private Integer sortOrder;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}
