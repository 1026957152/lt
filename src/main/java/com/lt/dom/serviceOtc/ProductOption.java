package com.lt.dom.serviceOtc;

import com.lt.dom.oct.Base;
import com.lt.dom.otcenum.EnumAssetQtStatus;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumAssetType_dynamic_static;
import com.lt.dom.otcenum.EnumProductOptionType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ProductOption extends Base {

    private String display_name;
    private String code;



    private Long product;


    private EnumProductOptionType type;

    @Transient
    private List option_values;
    @Column(name = "key_")
    private String key;


    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
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

    public EnumProductOptionType getType() {
        return type;
    }

    public void setType(EnumProductOptionType type) {
        this.type = type;
    }

    public <R> void setOption_values(List option_values) {
        this.option_values = option_values;
    }

    public List getOption_values() {
        return option_values;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
