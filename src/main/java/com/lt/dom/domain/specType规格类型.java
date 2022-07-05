package com.lt.dom.domain;

public class specType规格类型 {
  //  @com.fasterxml.jackson.annotation.JsonProperty("list")
  //  private item产品.ListDTO.SpecTypeListDTO.ListDTO.SpecTypeListDTO.ListDTO list;
  //  @com.fasterxml.jackson.annotation.JsonProperty("info")
  //  private item产品.ListDTO.SpecTypeListDTO.ListDTO.SpecTypeListDTO.InfoDTO info;

   // @lombok.NoArgsConstructor
   // @lombok.Data
    public static class ListDTO {
        @com.fasterxml.jackson.annotation.JsonProperty("spec_id")
        private String specId;
        @com.fasterxml.jackson.annotation.JsonProperty("spec_typeid")
        private String specTypeid;
        @com.fasterxml.jackson.annotation.JsonProperty("price")
        private String price;
        @com.fasterxml.jackson.annotation.JsonProperty("market_price")
        private String marketPrice;
        @com.fasterxml.jackson.annotation.JsonProperty("purchase_price")
        private String purchasePrice;
        @com.fasterxml.jackson.annotation.JsonProperty("suggest_price")
        private String suggestPrice;
        @com.fasterxml.jackson.annotation.JsonProperty("stock")
        private String stock;
        @com.fasterxml.jackson.annotation.JsonProperty("name")
        private String name;
    }

   // @lombok.NoArgsConstructor
   // @lombok.Data
    public static class InfoDTO {
        @com.fasterxml.jackson.annotation.JsonProperty("spec_typeid")
        private String specTypeid;
        @com.fasterxml.jackson.annotation.JsonProperty("goods_id")
        private String goodsId;
        @com.fasterxml.jackson.annotation.JsonProperty("name")
        private String name;
        @com.fasterxml.jackson.annotation.JsonProperty("sort")
        private String sort;
    }
}
