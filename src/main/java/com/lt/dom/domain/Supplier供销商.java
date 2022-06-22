package com.lt.dom.domain;

public class Supplier供销商 {
    @com.fasterxml.jackson.annotation.JsonProperty("address")
    private String address;
    @com.fasterxml.jackson.annotation.JsonProperty("city")
    private Long city;
    @com.fasterxml.jackson.annotation.JsonProperty("city_name")
    private String cityName;
    @com.fasterxml.jackson.annotation.JsonProperty("id")
    private String id;
    @com.fasterxml.jackson.annotation.JsonProperty("province")
    private Long province;
    @com.fasterxml.jackson.annotation.JsonProperty("province_name")
    private String provinceName;
    @com.fasterxml.jackson.annotation.JsonProperty("title")
    private String title;
}
