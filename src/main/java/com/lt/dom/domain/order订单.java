package com.lt.dom.domain;

import java.util.List;

//@lombok.NoArgsConstructor
//@lombok.Data
public class order订单 {

    @com.fasterxml.jackson.annotation.JsonProperty("orders_id")
    private String ordersId;
    @com.fasterxml.jackson.annotation.JsonProperty("id")
    private String id;
    @com.fasterxml.jackson.annotation.JsonProperty("settl_supplier_id")
    private String settlSupplierId;
    @com.fasterxml.jackson.annotation.JsonProperty("extend_type")
    private String extendType;
    @com.fasterxml.jackson.annotation.JsonProperty("code")
    private String code;
    @com.fasterxml.jackson.annotation.JsonProperty("codes")
    private String codes;
    @com.fasterxml.jackson.annotation.JsonProperty("qrcode")
    private String qrcode;
    @com.fasterxml.jackson.annotation.JsonProperty("qrcode_image")
    private String qrcodeImage;
    @com.fasterxml.jackson.annotation.JsonProperty("qrcode_href")
    private String qrcodeHref;
    @com.fasterxml.jackson.annotation.JsonProperty("ext_code")
    private String extCode;
    @com.fasterxml.jackson.annotation.JsonProperty("user_id")
    private String userId;
    @com.fasterxml.jackson.annotation.JsonProperty("seller_id")
    private String sellerId;
    @com.fasterxml.jackson.annotation.JsonProperty("super_seller_id")
    private String superSellerId;
    @com.fasterxml.jackson.annotation.JsonProperty("parent_seller_id")
    private String parentSellerId;
    @com.fasterxml.jackson.annotation.JsonProperty("member_id")
    private String memberId;
    @com.fasterxml.jackson.annotation.JsonProperty("title")
    private String title;
    @com.fasterxml.jackson.annotation.JsonProperty("goods_name")
    private String goodsName;
    @com.fasterxml.jackson.annotation.JsonProperty("goods_title")
    private String goodsTitle;
    @com.fasterxml.jackson.annotation.JsonProperty("name")
    private String name;
    @com.fasterxml.jackson.annotation.JsonProperty("mobile")
    private String mobile;
    @com.fasterxml.jackson.annotation.JsonProperty("id_number")
    private String idNumber;
    @com.fasterxml.jackson.annotation.JsonProperty("payment_id")
    private String paymentId;
    @com.fasterxml.jackson.annotation.JsonProperty("pay_type")
    private String payType;
    @com.fasterxml.jackson.annotation.JsonProperty("item_id")
    private String itemId;
    @com.fasterxml.jackson.annotation.JsonProperty("item_type")
    private String itemType;
    @com.fasterxml.jackson.annotation.JsonProperty("auto_sign")
    private String autoSign;
    @com.fasterxml.jackson.annotation.JsonProperty("item_price_type")
    private String itemPriceType;
    @com.fasterxml.jackson.annotation.JsonProperty("is_send")
    private String isSend;
    @com.fasterxml.jackson.annotation.JsonProperty("is_pay")
    private String isPay;
    @com.fasterxml.jackson.annotation.JsonProperty("send_type")
    private String sendType;
    @com.fasterxml.jackson.annotation.JsonProperty("orders_type")
    private String ordersType;
    @com.fasterxml.jackson.annotation.JsonProperty("amount")
    private String amount;
    @com.fasterxml.jackson.annotation.JsonProperty("apply_amount")
    private String applyAmount;
    @com.fasterxml.jackson.annotation.JsonProperty("cancel_amount")
    private String cancelAmount;
    @com.fasterxml.jackson.annotation.JsonProperty("valid_amount")
    private String validAmount;
    @com.fasterxml.jackson.annotation.JsonProperty("used_amount")
    private String usedAmount;
    @com.fasterxml.jackson.annotation.JsonProperty("send_price")
    private String sendPrice;
    @com.fasterxml.jackson.annotation.JsonProperty("price")
    private String price;
    @com.fasterxml.jackson.annotation.JsonProperty("total_price")
    private String totalPrice;
    @com.fasterxml.jackson.annotation.JsonProperty("suggest_price")
    private String suggestPrice;
    @com.fasterxml.jackson.annotation.JsonProperty("suggest_total_price")
    private String suggestTotalPrice;
    @com.fasterxml.jackson.annotation.JsonProperty("agent_price")
    private String agentPrice;
    @com.fasterxml.jackson.annotation.JsonProperty("agent_total_price")
    private String agentTotalPrice;
    @com.fasterxml.jackson.annotation.JsonProperty("back_cash")
    private String backCash;
    @com.fasterxml.jackson.annotation.JsonProperty("agent_back_cash")
    private String agentBackCash;
    @com.fasterxml.jackson.annotation.JsonProperty("settlement_price")
    private String settlementPrice;
    @com.fasterxml.jackson.annotation.JsonProperty("discount")
    private String discount;
    @com.fasterxml.jackson.annotation.JsonProperty("week")
    private String week;
    @com.fasterxml.jackson.annotation.JsonProperty("play_date")
    private String playDate;
    @com.fasterxml.jackson.annotation.JsonProperty("csend_time")
    private String csendTime;
    @com.fasterxml.jackson.annotation.JsonProperty("start_time")
    private String startTime;
    @com.fasterxml.jackson.annotation.JsonProperty("expire_time")
    private String expireTime;
    @com.fasterxml.jackson.annotation.JsonProperty("create_time")
    private String createTime;
    @com.fasterxml.jackson.annotation.JsonProperty("content")
    private String content;
    @com.fasterxml.jackson.annotation.JsonProperty("refund_type")
    private String refundType;
    @com.fasterxml.jackson.annotation.JsonProperty("refund_chanrge_type")
    private String refundChanrgeType;
    @com.fasterxml.jackson.annotation.JsonProperty("refund_chanrge")
    private String refundChanrge;
    @com.fasterxml.jackson.annotation.JsonProperty("send_method")
    private String sendMethod;
    @com.fasterxml.jackson.annotation.JsonProperty("another_orders_id")
    private String anotherOrdersId;
    @com.fasterxml.jackson.annotation.JsonProperty("remark")
    private String remark;
    @com.fasterxml.jackson.annotation.JsonProperty("remark_user")
    private String remarkUser;
    @com.fasterxml.jackson.annotation.JsonProperty("remark_status")
    private String remarkStatus;
    @com.fasterxml.jackson.annotation.JsonProperty("remark_status_supplier")
    private String remarkStatusSupplier;
    @com.fasterxml.jackson.annotation.JsonProperty("remark_supplier")
    private String remarkSupplier;
    @com.fasterxml.jackson.annotation.JsonProperty("create_by")
    private String createBy;
    @com.fasterxml.jackson.annotation.JsonProperty("supplier_list")
    private List<SupplierListDTO> supplierList;
    @com.fasterxml.jackson.annotation.JsonProperty("is_return_code")
    private String isReturnCode;
    @com.fasterxml.jackson.annotation.JsonProperty("supplier_list2")
    private List<SupplierList2DTO> supplierList2;

    //@lombok.NoArgsConstructor
  //  @lombok.Data
    public static class SupplierListDTO {
        @com.fasterxml.jackson.annotation.JsonProperty("id")
        private String id;
        @com.fasterxml.jackson.annotation.JsonProperty("title")
        private String title;
        @com.fasterxml.jackson.annotation.JsonProperty("address")
        private String address;
        @com.fasterxml.jackson.annotation.JsonProperty("info")
        private String info;
        @com.fasterxml.jackson.annotation.JsonProperty("city")
        private String city;
        @com.fasterxml.jackson.annotation.JsonProperty("city_name")
        private String cityName;
        @com.fasterxml.jackson.annotation.JsonProperty("province")
        private String province;
        @com.fasterxml.jackson.annotation.JsonProperty("province_name")
        private String provinceName;
        @com.fasterxml.jackson.annotation.JsonProperty("county")
        private String county;
        @com.fasterxml.jackson.annotation.JsonProperty("county_name")
        private String countyName;
     //   @com.fasterxml.jackson.annotation.JsonProperty("params")
     //   private ListDTO.SupplierListDTO.ParamsDTO params;

      //  @lombok.NoArgsConstructor
      //  @lombok.Data
        public static class ParamsDTO {
            @com.fasterxml.jackson.annotation.JsonProperty("lng_lat")
            private String lngLat;
        }
    }

   // @lombok.NoArgsConstructor
   // @lombok.Data
    public static class SupplierList2DTO {
        @com.fasterxml.jackson.annotation.JsonProperty("id")
        private String id;
        @com.fasterxml.jackson.annotation.JsonProperty("title")
        private String title;
        @com.fasterxml.jackson.annotation.JsonProperty("address")
        private String address;
        @com.fasterxml.jackson.annotation.JsonProperty("info")
        private String info;
        @com.fasterxml.jackson.annotation.JsonProperty("city")
        private String city;
        @com.fasterxml.jackson.annotation.JsonProperty("city_name")
        private String cityName;
        @com.fasterxml.jackson.annotation.JsonProperty("province")
        private String province;
        @com.fasterxml.jackson.annotation.JsonProperty("province_name")
        private String provinceName;
        @com.fasterxml.jackson.annotation.JsonProperty("county")
        private String county;
        @com.fasterxml.jackson.annotation.JsonProperty("county_name")
        private String countyName;
    }


}
