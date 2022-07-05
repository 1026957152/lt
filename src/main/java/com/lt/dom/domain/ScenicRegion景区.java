package com.lt.dom.domain;

public class ScenicRegion景区 {

    @com.fasterxml.jackson.annotation.JsonProperty("address")
    private String address;
    @com.fasterxml.jackson.annotation.JsonProperty("city")
    private Long city;
    @com.fasterxml.jackson.annotation.JsonProperty("city_name")
    private String cityName;
    @com.fasterxml.jackson.annotation.JsonProperty("id")
    private Long id;
    @com.fasterxml.jackson.annotation.JsonProperty("info")
    private String info;
    @com.fasterxml.jackson.annotation.JsonProperty("params")
    private item产品.ListDTO.SupplierListDTO.ParamsDTO params;
    @com.fasterxml.jackson.annotation.JsonProperty("province")
    private Long province;
    @com.fasterxml.jackson.annotation.JsonProperty("province_name")
    private String provinceName;
    @com.fasterxml.jackson.annotation.JsonProperty("title")
    private String title;

   // @lombok.NoArgsConstructor
   // @lombok.Data
    public static class ParamsDTO {
        @com.fasterxml.jackson.annotation.JsonProperty("lng_lat")
        private String lngLat;
    }
}
