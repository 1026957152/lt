package com.lt.dom.thirdTS.domainTsToLt;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.Supplier;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
        @Data
        public class SupplierListDTO {
            @JsonProperty("address")
            private String address;
            @JsonProperty("city")
            private Long city;
            @JsonProperty("city_name")
            private String cityName;
            @JsonProperty("id")
            private Long id;
            @JsonProperty("info")
            private String info;
            @JsonProperty("params")
            private ParamsDTO params;
            @JsonProperty("province")
            private Long province;
            @JsonProperty("province_name")
            private String provinceName;
            @JsonProperty("title")
            private String title;

    public static SupplierListDTO from(Supplier supplier) {

        SupplierListDTO supplierListDTO = new SupplierListDTO();
        supplierListDTO.setId(supplier.getId());
        supplierListDTO.setTitle(supplier.getName());
        supplierListDTO.setInfo(supplier.getDesc());
        supplierListDTO.setAddress(supplier.getLocation().getAddressLine1());

        supplierListDTO.setProvinceName(supplier.getLocation().getState());
        supplierListDTO.setCityName(supplier.getLocation().getCity());
        return supplierListDTO;
    }

    @NoArgsConstructor
            @Data
            public static class ParamsDTO {
                @JsonProperty("lng_lat")
                private String lngLat;
            }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ParamsDTO getParams() {
        return params;
    }

    public void setParams(ParamsDTO params) {
        this.params = params;
    }

    public Long getProvince() {
        return province;
    }

    public void setProvince(Long province) {
        this.province = province;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}