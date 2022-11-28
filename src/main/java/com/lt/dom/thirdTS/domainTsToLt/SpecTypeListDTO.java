package com.lt.dom.thirdTS.domainTsToLt;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
        @Data
        public class SpecTypeListDTO {


    private String id;
           @JsonProperty("list")
                   private ListDTO_ list;
                   @JsonProperty("info")
                   private InfoDTO info;

    @NoArgsConstructor
    @Data
    public static class ListDTO_ {


        @JsonProperty("spec_id")
        private String specId_规格ID;
        @JsonProperty("spec_typeid")
        private String specTypeid_所属规格类型ID;
        @JsonProperty("price")
        private String price_分销价; //分销价+
        @JsonProperty("market_price")
        private String marketPrice_门市价; //门市价+
        @JsonProperty("purchase_price")
        private String purchasePrice_采购价;  //采购价+
        @JsonProperty("suggest_price")
        private String suggestPrice_指导价;  //指导价+
        @JsonProperty("stock")
        private String stock_规格库存; //规格库存
        @JsonProperty("name")
        private String name_规则名称;  //规则名称，如：大
    }

    @NoArgsConstructor
    @Data
    public static class InfoDTO {
        @JsonProperty("spec_typeid")
        private String specTypeid_规格类型ID;
        @JsonProperty("goods_id")
        private String goodsId_产品ID;
        @JsonProperty("name")
        private String name_类型名称; //类型名称，如：大小
        @JsonProperty("sort")
        private String sort_排序; //排序
    }

            @NoArgsConstructor
            @Data
            public static class _$0DTO {
 /*               @JsonProperty("list")
                private ListDTO.SpecTypeListDTO._$0DTO.ListDTO list;
                @JsonProperty("info")
                private ListDTO.SpecTypeListDTO._$0DTO.InfoDTO info;
*/

            }
        }