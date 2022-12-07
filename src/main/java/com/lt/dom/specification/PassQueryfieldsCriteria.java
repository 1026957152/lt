package com.lt.dom.specification;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.lt.dom.otcenum.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PassQueryfieldsCriteria {



    private String keyword;//

/*    @JsonFormat(pattern = "yyyy-MM-dd")

    @DateTimeFormat(pattern = "yyyy-MM-dd")*/
 //   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
 //   private LocalDateTime created_from;
/*
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
*/

    // @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
   // private LocalDateTime created_to;
   // @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  //  @DateTimeFormat(pattern = "yyyy-MM-dd")
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd",iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime created_to;

    public LocalDateTime getCreated_to() {
        return created_to;
    }

    public void setCreated_to(LocalDateTime created_to) {
        this.created_to = created_to;
    }

    private EnumFormFactorType formFactorType;
    private Long product;


/*
    public LocalDateTime getCreated_from() {
        return created_from;
    }

    public void setCreated_from(LocalDateTime created_from) {
        this.created_from = created_from;
    }

    public LocalDateTime getCreated_to() {
        return created_to;
    }

    public void setCreated_to(LocalDateTime created_to) {
        this.created_to = created_to;
    }
*/

    private List<EnumCardStatus> statuses;

    public List<EnumCardStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<EnumCardStatus> statuses) {
        this.statuses = statuses;
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }


    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setFormFactorType(EnumFormFactorType formFactorType) {
        this.formFactorType = formFactorType;
    }

    public EnumFormFactorType getFormFactorType() {
        return formFactorType;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

    public Long getProduct() {
        return product;
    }
}