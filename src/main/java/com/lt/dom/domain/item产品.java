package com.lt.dom.domain;

import java.util.List;

//@lombok.NoArgsConstructor
//@lombok.Data
public class item产品 {

    @com.fasterxml.jackson.annotation.JsonProperty("errorn")
    private Long errorn;
    @com.fasterxml.jackson.annotation.JsonProperty("g_err")
    private Long gErr;
    @com.fasterxml.jackson.annotation.JsonProperty("list")
    private List<ListDTO> list;
    @com.fasterxml.jackson.annotation.JsonProperty("message")
    private String message;
    @com.fasterxml.jackson.annotation.JsonProperty("success")
    private Boolean success;
    @com.fasterxml.jackson.annotation.JsonProperty("total")
    private Long total;

   // @lombok.NoArgsConstructor
   // @lombok.Data
    public static class ListDTO {
        @com.fasterxml.jackson.annotation.JsonProperty("amount")
        private Long amount;
        @com.fasterxml.jackson.annotation.JsonProperty("amount_day")
        private Long amountDay;
        @com.fasterxml.jackson.annotation.JsonProperty("back_cash")
        private String backCash;
        @com.fasterxml.jackson.annotation.JsonProperty("brief")
        private String brief;
        @com.fasterxml.jackson.annotation.JsonProperty("category_group")
        private Long categoryGroup;
        @com.fasterxml.jackson.annotation.JsonProperty("category_group_name")
        private String categoryGroupName;
        @com.fasterxml.jackson.annotation.JsonProperty("category_groups")
        private List<?> categoryGroups;
        @com.fasterxml.jackson.annotation.JsonProperty("category_zone")
        private Long categoryZone;
        @com.fasterxml.jackson.annotation.JsonProperty("city_id")
        private Long cityId;
        @com.fasterxml.jackson.annotation.JsonProperty("city_name")
        private String cityName;
        @com.fasterxml.jackson.annotation.JsonProperty("county_id")
        private Long countyId;
        @com.fasterxml.jackson.annotation.JsonProperty("county_name")
        private String countyName;
        @com.fasterxml.jackson.annotation.JsonProperty("date_prices")
        private ListDTO.DatePricesDTO datePrices;
        @com.fasterxml.jackson.annotation.JsonProperty("date_stocks")
        private ListDTO.DateStocksDTO dateStocks;
        @com.fasterxml.jackson.annotation.JsonProperty("description")
        private String description;
        @com.fasterxml.jackson.annotation.JsonProperty("desc_css")
        private String descCss;
        @com.fasterxml.jackson.annotation.JsonProperty("expire_time")
        private Long expireTime;
        @com.fasterxml.jackson.annotation.JsonProperty("goods_id")
        private Long goodsId;
        @com.fasterxml.jackson.annotation.JsonProperty("goods_name")
        private String goodsName;
        @com.fasterxml.jackson.annotation.JsonProperty("goods_title")
        private String goodsTitle;
        @com.fasterxml.jackson.annotation.JsonProperty("goods_type")
        private Long goodsType;
        @com.fasterxml.jackson.annotation.JsonProperty("id")
        private Long id;
        @com.fasterxml.jackson.annotation.JsonProperty("id_limit_amount")
        private Long idLimitAmount;
        @com.fasterxml.jackson.annotation.JsonProperty("id_limit_orders")
        private Long idLimitOrders;
        @com.fasterxml.jackson.annotation.JsonProperty("id_limit_time")
        private Long idLimitTime;
        @com.fasterxml.jackson.annotation.JsonProperty("image")
        private String image;
        @com.fasterxml.jackson.annotation.JsonProperty("is_import")
        private Long isImport;
        @com.fasterxml.jackson.annotation.JsonProperty("last_order_date")
        private String lastOrderDate;
        @com.fasterxml.jackson.annotation.JsonProperty("last_order_time")
        private Long lastOrderTime;
        @com.fasterxml.jackson.annotation.JsonProperty("last_order_time_delay")
        private Long lastOrderTimeDelay;
        @com.fasterxml.jackson.annotation.JsonProperty("market_price")
        private String marketPrice;
        @com.fasterxml.jackson.annotation.JsonProperty("max_num")
        private Long maxNum;
        @com.fasterxml.jackson.annotation.JsonProperty("mb_limit_amount")
        private Long mbLimitAmount;
        @com.fasterxml.jackson.annotation.JsonProperty("mb_limit_orders")
        private Long mbLimitOrders;
        @com.fasterxml.jackson.annotation.JsonProperty("mb_limit_time")
        private Long mbLimitTime;
        @com.fasterxml.jackson.annotation.JsonProperty("min_num")
        private Long minNum;
        @com.fasterxml.jackson.annotation.JsonProperty("mms_content")
        private String mmsContent;
        @com.fasterxml.jackson.annotation.JsonProperty("modify_time")
        private Long modifyTime;
        @com.fasterxml.jackson.annotation.JsonProperty("must_id_number")
        private Long mustIdNumber;
        @com.fasterxml.jackson.annotation.JsonProperty("nett_price")
        private String nettPrice;
        @com.fasterxml.jackson.annotation.JsonProperty("notice")
        private String notice;
        @com.fasterxml.jackson.annotation.JsonProperty("original_price")
        private String originalPrice;
        @com.fasterxml.jackson.annotation.JsonProperty("params")
        private ListDTO.ParamsDTO params;
        @com.fasterxml.jackson.annotation.JsonProperty("pay_type")
        private Long payType;
        @com.fasterxml.jackson.annotation.JsonProperty("print_content")
        private String printContent;
        @com.fasterxml.jackson.annotation.JsonProperty("prov_id")
        private Long provId;
        @com.fasterxml.jackson.annotation.JsonProperty("prov_name")
        private String provName;
        @com.fasterxml.jackson.annotation.JsonProperty("refund_chanrge")
        private Long refundChanrge;
        @com.fasterxml.jackson.annotation.JsonProperty("refund_chanrge_type")
        private Long refundChanrgeType;
        @com.fasterxml.jackson.annotation.JsonProperty("refund_message")
        private String refundMessage;
        @com.fasterxml.jackson.annotation.JsonProperty("refund_type")
        private Long refundType;
        @com.fasterxml.jackson.annotation.JsonProperty("self_refund_fixed")
        private String selfRefundFixed;
        @com.fasterxml.jackson.annotation.JsonProperty("self_refund_scale")
        private Long selfRefundScale;
        @com.fasterxml.jackson.annotation.JsonProperty("self_refund_time")
        private String selfRefundTime;
        @com.fasterxml.jackson.annotation.JsonProperty("self_refund_time_type")
        private Long selfRefundTimeType;
        @com.fasterxml.jackson.annotation.JsonProperty("send_type")
        private Long sendType;
        @com.fasterxml.jackson.annotation.JsonProperty("sku_back_cash")
        private String skuBackCash;
        @com.fasterxml.jackson.annotation.JsonProperty("sku_market_price")
        private String skuMarketPrice;
        @com.fasterxml.jackson.annotation.JsonProperty("sku_suggest_price")
        private String skuSuggestPrice;
        @com.fasterxml.jackson.annotation.JsonProperty("sku_user_price")
        private String skuUserPrice;
        @com.fasterxml.jackson.annotation.JsonProperty("sms_content")
        private String smsContent;
        @com.fasterxml.jackson.annotation.JsonProperty("sort_order")
        private Long sortOrder;
        @com.fasterxml.jackson.annotation.JsonProperty("start_time")
        private Long startTime;
        @com.fasterxml.jackson.annotation.JsonProperty("status")
        private Long status;
        @com.fasterxml.jackson.annotation.JsonProperty("supplier_id")
        private String supplierId;
        @com.fasterxml.jackson.annotation.JsonProperty("supplier_list")
        private ListDTO.SupplierListDTO supplierList;
        @com.fasterxml.jackson.annotation.JsonProperty("supplier_list2")
        private ListDTO.SupplierList2DTO supplierList2;
        @com.fasterxml.jackson.annotation.JsonProperty("spec_type_list")
        private List<SpecTypeListDTO> specTypeList;
        @com.fasterxml.jackson.annotation.JsonProperty("title")
        private String title;
        @com.fasterxml.jackson.annotation.JsonProperty("type")
        private Long type;
        @com.fasterxml.jackson.annotation.JsonProperty("validity_day")
        private Long validityDay;
        @com.fasterxml.jackson.annotation.JsonProperty("validity_time")
        private Long validityTime;
        @com.fasterxml.jackson.annotation.JsonProperty("validity_type")
        private Long validityType;
        @com.fasterxml.jackson.annotation.JsonProperty("week")
        private String week;

       // @lombok.NoArgsConstructor
       // @lombok.Data
        public static class DatePricesDTO {
            @com.fasterxml.jackson.annotation.JsonProperty("2015-12-31")
            private ListDTO.DatePricesDTO._$20151231DTO $20151231;

           // @lombok.NoArgsConstructor
          //  @lombok.Data
            public static class _$20151231DTO {
                @com.fasterxml.jackson.annotation.JsonProperty("market_price")
                private String marketPrice;
                @com.fasterxml.jackson.annotation.JsonProperty("price")
                private String price;
                @com.fasterxml.jackson.annotation.JsonProperty("stock")
                private String stock;
                @com.fasterxml.jackson.annotation.JsonProperty("suggest_price")
                private String suggestPrice;
            }
        }

        //@lombok.NoArgsConstructor
      //  @lombok.Data
        public static class DateStocksDTO {
            @com.fasterxml.jackson.annotation.JsonProperty("2015-12-31")
            private ListDTO.DateStocksDTO._$20151231DTO $20151231;

          //  @lombok.NoArgsConstructor
          //  @lombok.Data
            public static class _$20151231DTO {
                @com.fasterxml.jackson.annotation.JsonProperty("stock")
                private Long stock;
            }
        }

       // @lombok.NoArgsConstructor
      //  @lombok.Data
        public static class ParamsDTO {
            @com.fasterxml.jackson.annotation.JsonProperty("orders_tips")
            private String ordersTips;
            @com.fasterxml.jackson.annotation.JsonProperty("booking_reading")
            private ListDTO.ParamsDTO.BookingReadingDTO bookingReading;
            @com.fasterxml.jackson.annotation.JsonProperty("buy_lately_time")
            private String buyLatelyTime;
            @com.fasterxml.jackson.annotation.JsonProperty("check_expire_time")
            private String checkExpireTime;
            @com.fasterxml.jackson.annotation.JsonProperty("cog_ids")
            private String cogIds;
            @com.fasterxml.jackson.annotation.JsonProperty("contact_number")
            private String contactNumber;
            @com.fasterxml.jackson.annotation.JsonProperty("idcard_limit")
            private String idcardLimit;
            @com.fasterxml.jackson.annotation.JsonProperty("idcard_limit_type")
            private String idcardLimitType;
            @com.fasterxml.jackson.annotation.JsonProperty("lng_lat")
            private String lngLat;
            @com.fasterxml.jackson.annotation.JsonProperty("map_num")
            private String mapNum;
            @com.fasterxml.jackson.annotation.JsonProperty("mobiles_limit")
            private String mobilesLimit;
            @com.fasterxml.jackson.annotation.JsonProperty("mobiles_limit_type")
            private String mobilesLimitType;
            @com.fasterxml.jackson.annotation.JsonProperty("moreid_player")
            private String moreidPlayer;
            @com.fasterxml.jackson.annotation.JsonProperty("much_no_post_fee")
            private String muchNoPostFee;
            @com.fasterxml.jackson.annotation.JsonProperty("much_province_money")
            private String muchProvinceMoney;
            @com.fasterxml.jackson.annotation.JsonProperty("need_email_address")
            private String needEmailAddress;
            @com.fasterxml.jackson.annotation.JsonProperty("need_plate_number")
            private String needPlateNumber;
            @com.fasterxml.jackson.annotation.JsonProperty("need_post_address")
            private String needPostAddress;
            @com.fasterxml.jackson.annotation.JsonProperty("need_shuttle_address")
            private String needShuttleAddress;
            @com.fasterxml.jackson.annotation.JsonProperty("need_tickets_address")
            private String needTicketsAddress;
            @com.fasterxml.jackson.annotation.JsonProperty("post_city_limit")
            private String postCityLimit;
            @com.fasterxml.jackson.annotation.JsonProperty("post_citys")
            private String postCitys;
            @com.fasterxml.jackson.annotation.JsonProperty("post_custom_province")
            private String postCustomProvince;
            @com.fasterxml.jackson.annotation.JsonProperty("post_fee")
            private String postFee;
            @com.fasterxml.jackson.annotation.JsonProperty("price_include")
            private String priceInclude;
            @com.fasterxml.jackson.annotation.JsonProperty("price_uninclude")
            private String priceUninclude;
            @com.fasterxml.jackson.annotation.JsonProperty("refund_part")
            private Long refundPart;
            @com.fasterxml.jackson.annotation.JsonProperty("skey_word")
            private String skeyWord;
            @com.fasterxml.jackson.annotation.JsonProperty("sold_end_date")
            private String soldEndDate;
            @com.fasterxml.jackson.annotation.JsonProperty("sold_start_date")
            private String soldStartDate;
            @com.fasterxml.jackson.annotation.JsonProperty("start_time")
            private String startTime;
            @com.fasterxml.jackson.annotation.JsonProperty("start_time2")
            private String startTime2;
            @com.fasterxml.jackson.annotation.JsonProperty("start_time3")
            private String startTime3;
            @com.fasterxml.jackson.annotation.JsonProperty("stop_time")
            private String stopTime;
            @com.fasterxml.jackson.annotation.JsonProperty("stop_time2")
            private String stopTime2;
            @com.fasterxml.jackson.annotation.JsonProperty("stop_time3")
            private String stopTime3;
            @com.fasterxml.jackson.annotation.JsonProperty("time_slot_auto")
            private ListDTO.ParamsDTO.TimeSlotAutoDTO timeSlotAuto;
            @com.fasterxml.jackson.annotation.JsonProperty("time_slot_stocks")
            private List<TimeSlotStocksDTO> timeSlotStocks;
            @com.fasterxml.jackson.annotation.JsonProperty("timeSlot_refund_set")
            private List<TimeSlotRefundSetDTO> timeslotRefundSet;
            @com.fasterxml.jackson.annotation.JsonProperty("user_reading")
            private String userReading;
            @com.fasterxml.jackson.annotation.JsonProperty("valid_to_hours")
            private String validToHours;

           // @lombok.NoArgsConstructor
          //  @lombok.Data
            public static class BookingReadingDTO {
            }

            //@lombok.NoArgsConstructor
          //  @lombok.Data
            public static class TimeSlotAutoDTO {
            }

          //  @lombok.NoArgsConstructor
          //  @lombok.Data
            public static class TimeSlotStocksDTO {
                @com.fasterxml.jackson.annotation.JsonProperty("mch_code")
                private ListDTO.ParamsDTO.TimeSlotStocksDTO.MchCodeDTO mchCode;
                @com.fasterxml.jackson.annotation.JsonProperty("start_time")
                private String startTime;
                @com.fasterxml.jackson.annotation.JsonProperty("stocks")
                private String stocks;
                @com.fasterxml.jackson.annotation.JsonProperty("stop_time")
                private String stopTime;

              //  @lombok.NoArgsConstructor
              //  @lombok.Data
                public static class MchCodeDTO {
                }
            }

           // @lombok.NoArgsConstructor
          //  @lombok.Data
            public static class TimeSlotRefundSetDTO {
                @com.fasterxml.jackson.annotation.JsonProperty("days")
                private String days;
                @com.fasterxml.jackson.annotation.JsonProperty("fixed")
                private String fixed;
                @com.fasterxml.jackson.annotation.JsonProperty("hours")
                private String hours;
                @com.fasterxml.jackson.annotation.JsonProperty("percent")
                private String percent;
                @com.fasterxml.jackson.annotation.JsonProperty("type")
                private String type;
            }
        }

       // @lombok.NoArgsConstructor
      //  @lombok.Data
        public static class SupplierListDTO {
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
            private ListDTO.SupplierListDTO.ParamsDTO params;
            @com.fasterxml.jackson.annotation.JsonProperty("province")
            private Long province;
            @com.fasterxml.jackson.annotation.JsonProperty("province_name")
            private String provinceName;
            @com.fasterxml.jackson.annotation.JsonProperty("title")
            private String title;

          //  @lombok.NoArgsConstructor
          //  @lombok.Data
            public static class ParamsDTO {
                @com.fasterxml.jackson.annotation.JsonProperty("lng_lat")
                private String lngLat;
            }
        }

      //  @lombok.NoArgsConstructor
     //   @lombok.Data
        public static class SupplierList2DTO {
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

      //  @lombok.NoArgsConstructor
      //  @lombok.Data
        public static class SpecTypeListDTO {
          //  @com.fasterxml.jackson.annotation.JsonProperty("list")
          //  private ListDTO.SpecTypeListDTO.ListDTO list;
          //  @com.fasterxml.jackson.annotation.JsonProperty("info")
          //  private ListDTO.SpecTypeListDTO.InfoDTO info;

         //   @lombok.NoArgsConstructor
        //    @lombok.Data
  /*          public static class ListDTO {
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
*/
         //   @lombok.NoArgsConstructor
         //   @lombok.Data
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
    }
}
