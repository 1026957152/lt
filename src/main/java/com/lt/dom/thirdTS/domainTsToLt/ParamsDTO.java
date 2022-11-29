package com.lt.dom.thirdTS.domainTsToLt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
        @Data
        public  class ParamsDTO {





            @JsonProperty("booking_reading")
            private String bookingReading_预约须知;
            @JsonProperty("buy_lately_time")
            private String buyLatelyTime_可购买最近时间设置; //可购买最近时间设置，只允许选择购买最近时间的订单，单位(天)
            @JsonProperty("check_expire_time")
            private String checkExpireTime_审核时间; //"审核时间（分钟数，如30表示下单后30分钟内处理订单）",
            @JsonProperty("cog_ids")
            private String cogIds_检查已购买产品ID; //检查已购买产品ID（下此产品的订单必须先购买某个产品后才可购买）
            @JsonProperty("contact_number")
            private String contactNumber_商家电话; //商家电话
            @JsonProperty("idcard_limit")
            private String idcardLimit_身份证限制规则;//身份证限制规则
            @JsonProperty("idcard_limit_type")
            private Integer idcardLimitType_身份证限制类型; //身份证限制类型，1：允许，2：禁止
            @JsonProperty("lng_lat")
            private String lngLat_产品经纬度; //产品经纬度（经度纬度英文逗号分隔）
            @JsonProperty("map_num")
            private String mapNum_每份对应数量;
            
            
            
                      
            
            
            @JsonProperty("mobiles_limit")
            private String mobilesLimit_手机号限制规则;
            @JsonProperty("mobiles_limit_type")
            private String mobilesLimitType_手机号限制类型; //手机号限制类型，1：允许，2：禁止
            @JsonProperty("moreid_player")
            private String moreidPlayer_购买多份时是否需要填写多个游客信息; //购买多份时是否需要填写多个游客信息(每份对应一个游客信息)
            @JsonProperty("much_no_post_fee")
            private String muchNoPostFee_满多少元以上包邮; //满多少元以上包邮
            @JsonProperty("much_province_money")
            private String muchProvinceMoney_偏远地区邮费设置; //偏远地区邮费设置(邮费金额)
            @JsonProperty("need_email_address")
            private String needEmailAddress_是否需要邮箱地址; //是否需要邮箱地址
            @JsonProperty("need_plate_number")
            private String needPlateNumber_下单时游客是否必须填写车牌号码; //下单时游客是否必须填写车牌号码
            
            
                  
            
            @JsonProperty("need_post_address")
            private String needPostAddress_是否实物快递;  //是否实物快递，1：是实物快递，下单需要传快递地址，0：否
            @JsonProperty("need_shuttle_address")
            private String needShuttleAddress_下单是否需要接送地址;
            @JsonProperty("need_tickets_address")
            private String needTicketsAddress_取票_或换票_地址;
            @JsonProperty("orders_tips")
            private String ordersTips_产品提示;
            @JsonProperty("post_city_limit")
            private String postCityLimit_快递地区限制;//快递地区限制，1：允许，2：禁止
            @JsonProperty("post_citys")
            private String postCitys_快递地区规则; //快递地区规则，省份代码，多个以英文逗号分隔
            @JsonProperty("post_custom_province")
            private String postCustomProvince_偏远地区邮费设置;  //偏远地区邮费设置(省份id)（不填表示不设置部分地区增加邮费，如设置新疆、西藏，则快递地址为该区域时订单总金额增加对应邮费）
            @JsonProperty("post_fee")
            private String postFee_快递邮费;
            @JsonProperty("price_include")
            private String priceInclude_费用包含;
            
            
            
               

            @JsonProperty("price_uninclude")
            private String priceUninclude_费用不包含;
            @JsonProperty("refund_part")
            private Integer refundPart_是否支持部分退单;// //是否支持部分退单。订单有5张票，支持部分退单时可退3张用2张，如果不支持部分退，则订单退单时只能5张全退
            @JsonProperty("skey_word")
            private String skeyWord_搜索关键字;
            @JsonProperty("sold_end_date")
            private String soldEndDate_限时抢设置_结束时间;
            @JsonProperty("sold_start_date")
            private String soldStartDate_限时抢设置_开始时间;
            @JsonProperty("start_time")
            private String startTime_营业时间1_开始时间;
            @JsonProperty("start_time2")
            private String startTime2_营业时间2_开始时间;
            @JsonProperty("start_time3")
            private String startTime3_营业时间3_开始时间;
            @JsonProperty("stop_time")
            private String stopTime_营业时间1_结束时间;
            @JsonProperty("stop_time2")
            private String stopTime2_营业时间2_结束时间;
            @JsonProperty("stop_time3")
            private String stopTime3_营业时间3_结束时间;
            @JsonProperty("time_slot_auto")
            private String timeSlotAuto_自动选择时段; //"自动选择时段，下单时如果没有选择时间段的话是否自动选择合适时间段，默认自动选择，
            @JsonProperty("time_slot_stocks")
            
            private List<TimeSlotStocksDTO> timeSlotStocks;
            @JsonProperty("timeSlot_refund_set")
            private List<TimeSlotRefundSetDTO> timeslotRefundSet;
            
            @JsonProperty("user_reading")
            private String userReading_分销商须知;
            @JsonProperty("valid_to_hours")
            private String validToHours_有效天数由原来天数为单位改为小时为单位以下单时间开始计算有效期;

            @NoArgsConstructor
            @Data
            public static class TimeSlotStocksDTO {
                @JsonProperty("0")
                private _$0DTO $0;

                @NoArgsConstructor
                @Data
                public static class _$0DTO {
                    @JsonProperty("start_time")
                    private String startTime;
                    @JsonProperty("stop_time")
                    private String stopTime;
                    @JsonProperty("stocks")
                    private String stocks;
                    @JsonProperty("mch_code")
                    private String mchCode;
                }
            }

            @NoArgsConstructor
            @Data
            public static class TimeSlotRefundSetDTO {
                @JsonProperty("days")
                private String days;
                @JsonProperty("fixed")
                private String fixed;
                @JsonProperty("hours")
                private String hours;
                @JsonProperty("percent")
                private String percent;
                @JsonProperty("type")
                private String type;











            }


    public String getBookingReading_预约须知() {
        return bookingReading_预约须知;
    }

    public void setBookingReading_预约须知(String bookingReading_预约须知) {
        this.bookingReading_预约须知 = bookingReading_预约须知;
    }

    public String getBuyLatelyTime_可购买最近时间设置() {
        return buyLatelyTime_可购买最近时间设置;
    }

    public void setBuyLatelyTime_可购买最近时间设置(String buyLatelyTime_可购买最近时间设置) {
        this.buyLatelyTime_可购买最近时间设置 = buyLatelyTime_可购买最近时间设置;
    }

    public String getCheckExpireTime_审核时间() {
        return checkExpireTime_审核时间;
    }

    public void setCheckExpireTime_审核时间(String checkExpireTime_审核时间) {
        this.checkExpireTime_审核时间 = checkExpireTime_审核时间;
    }

    public String getCogIds_检查已购买产品ID() {
        return cogIds_检查已购买产品ID;
    }

    public void setCogIds_检查已购买产品ID(String cogIds_检查已购买产品ID) {
        this.cogIds_检查已购买产品ID = cogIds_检查已购买产品ID;
    }

    public String getContactNumber_商家电话() {
        return contactNumber_商家电话;
    }

    public void setContactNumber_商家电话(String contactNumber_商家电话) {
        this.contactNumber_商家电话 = contactNumber_商家电话;
    }

    public String getIdcardLimit_身份证限制规则() {
        return idcardLimit_身份证限制规则;
    }

    public void setIdcardLimit_身份证限制规则(String idcardLimit_身份证限制规则) {
        this.idcardLimit_身份证限制规则 = idcardLimit_身份证限制规则;
    }

    public Integer getIdcardLimitType_身份证限制类型() {
        return idcardLimitType_身份证限制类型;
    }

    public void setIdcardLimitType_身份证限制类型(Integer idcardLimitType_身份证限制类型) {
        this.idcardLimitType_身份证限制类型 = idcardLimitType_身份证限制类型;
    }

    public String getLngLat_产品经纬度() {
        return lngLat_产品经纬度;
    }

    public void setLngLat_产品经纬度(String lngLat_产品经纬度) {
        this.lngLat_产品经纬度 = lngLat_产品经纬度;
    }

    public String getMapNum_每份对应数量() {
        return mapNum_每份对应数量;
    }

    public void setMapNum_每份对应数量(String mapNum_每份对应数量) {
        this.mapNum_每份对应数量 = mapNum_每份对应数量;
    }

    public String getMobilesLimit_手机号限制规则() {
        return mobilesLimit_手机号限制规则;
    }

    public void setMobilesLimit_手机号限制规则(String mobilesLimit_手机号限制规则) {
        this.mobilesLimit_手机号限制规则 = mobilesLimit_手机号限制规则;
    }

    public String getMobilesLimitType_手机号限制类型() {
        return mobilesLimitType_手机号限制类型;
    }

    public void setMobilesLimitType_手机号限制类型(String mobilesLimitType_手机号限制类型) {
        this.mobilesLimitType_手机号限制类型 = mobilesLimitType_手机号限制类型;
    }

    public String getMoreidPlayer_购买多份时是否需要填写多个游客信息() {
        return moreidPlayer_购买多份时是否需要填写多个游客信息;
    }

    public void setMoreidPlayer_购买多份时是否需要填写多个游客信息(String moreidPlayer_购买多份时是否需要填写多个游客信息) {
        this.moreidPlayer_购买多份时是否需要填写多个游客信息 = moreidPlayer_购买多份时是否需要填写多个游客信息;
    }

    public String getMuchNoPostFee_满多少元以上包邮() {
        return muchNoPostFee_满多少元以上包邮;
    }

    public void setMuchNoPostFee_满多少元以上包邮(String muchNoPostFee_满多少元以上包邮) {
        this.muchNoPostFee_满多少元以上包邮 = muchNoPostFee_满多少元以上包邮;
    }

    public String getMuchProvinceMoney_偏远地区邮费设置() {
        return muchProvinceMoney_偏远地区邮费设置;
    }

    public void setMuchProvinceMoney_偏远地区邮费设置(String muchProvinceMoney_偏远地区邮费设置) {
        this.muchProvinceMoney_偏远地区邮费设置 = muchProvinceMoney_偏远地区邮费设置;
    }

    public String getNeedEmailAddress_是否需要邮箱地址() {
        return needEmailAddress_是否需要邮箱地址;
    }

    public void setNeedEmailAddress_是否需要邮箱地址(String needEmailAddress_是否需要邮箱地址) {
        this.needEmailAddress_是否需要邮箱地址 = needEmailAddress_是否需要邮箱地址;
    }

    public String getNeedPlateNumber_下单时游客是否必须填写车牌号码() {
        return needPlateNumber_下单时游客是否必须填写车牌号码;
    }

    public void setNeedPlateNumber_下单时游客是否必须填写车牌号码(String needPlateNumber_下单时游客是否必须填写车牌号码) {
        this.needPlateNumber_下单时游客是否必须填写车牌号码 = needPlateNumber_下单时游客是否必须填写车牌号码;
    }

    public String getNeedPostAddress_是否实物快递() {
        return needPostAddress_是否实物快递;
    }

    public void setNeedPostAddress_是否实物快递(String needPostAddress_是否实物快递) {
        this.needPostAddress_是否实物快递 = needPostAddress_是否实物快递;
    }

    public String getNeedShuttleAddress_下单是否需要接送地址() {
        return needShuttleAddress_下单是否需要接送地址;
    }

    public void setNeedShuttleAddress_下单是否需要接送地址(String needShuttleAddress_下单是否需要接送地址) {
        this.needShuttleAddress_下单是否需要接送地址 = needShuttleAddress_下单是否需要接送地址;
    }

    public String getNeedTicketsAddress_取票_或换票_地址() {
        return needTicketsAddress_取票_或换票_地址;
    }

    public void setNeedTicketsAddress_取票_或换票_地址(String needTicketsAddress_取票_或换票_地址) {
        this.needTicketsAddress_取票_或换票_地址 = needTicketsAddress_取票_或换票_地址;
    }

    public String getOrdersTips_产品提示() {
        return ordersTips_产品提示;
    }

    public void setOrdersTips_产品提示(String ordersTips_产品提示) {
        this.ordersTips_产品提示 = ordersTips_产品提示;
    }

    public String getPostCityLimit_快递地区限制() {
        return postCityLimit_快递地区限制;
    }

    public void setPostCityLimit_快递地区限制(String postCityLimit_快递地区限制) {
        this.postCityLimit_快递地区限制 = postCityLimit_快递地区限制;
    }

    public String getPostCitys_快递地区规则() {
        return postCitys_快递地区规则;
    }

    public void setPostCitys_快递地区规则(String postCitys_快递地区规则) {
        this.postCitys_快递地区规则 = postCitys_快递地区规则;
    }

    public String getPostCustomProvince_偏远地区邮费设置() {
        return postCustomProvince_偏远地区邮费设置;
    }

    public void setPostCustomProvince_偏远地区邮费设置(String postCustomProvince_偏远地区邮费设置) {
        this.postCustomProvince_偏远地区邮费设置 = postCustomProvince_偏远地区邮费设置;
    }

    public String getPostFee_快递邮费() {
        return postFee_快递邮费;
    }

    public void setPostFee_快递邮费(String postFee_快递邮费) {
        this.postFee_快递邮费 = postFee_快递邮费;
    }

    public String getPriceInclude_费用包含() {
        return priceInclude_费用包含;
    }

    public void setPriceInclude_费用包含(String priceInclude_费用包含) {
        this.priceInclude_费用包含 = priceInclude_费用包含;
    }

    public String getPriceUninclude_费用不包含() {
        return priceUninclude_费用不包含;
    }

    public void setPriceUninclude_费用不包含(String priceUninclude_费用不包含) {
        this.priceUninclude_费用不包含 = priceUninclude_费用不包含;
    }

    public Integer getRefundPart_是否支持部分退单() {
        return refundPart_是否支持部分退单;
    }

    public void setRefundPart_是否支持部分退单(Integer refundPart_是否支持部分退单) {
        this.refundPart_是否支持部分退单 = refundPart_是否支持部分退单;
    }

    public String getSkeyWord_搜索关键字() {
        return skeyWord_搜索关键字;
    }

    public void setSkeyWord_搜索关键字(String skeyWord_搜索关键字) {
        this.skeyWord_搜索关键字 = skeyWord_搜索关键字;
    }

    public String getSoldEndDate_限时抢设置_结束时间() {
        return soldEndDate_限时抢设置_结束时间;
    }

    public void setSoldEndDate_限时抢设置_结束时间(String soldEndDate_限时抢设置_结束时间) {
        this.soldEndDate_限时抢设置_结束时间 = soldEndDate_限时抢设置_结束时间;
    }

    public String getSoldStartDate_限时抢设置_开始时间() {
        return soldStartDate_限时抢设置_开始时间;
    }

    public void setSoldStartDate_限时抢设置_开始时间(String soldStartDate_限时抢设置_开始时间) {
        this.soldStartDate_限时抢设置_开始时间 = soldStartDate_限时抢设置_开始时间;
    }

    public String getStartTime_营业时间1_开始时间() {
        return startTime_营业时间1_开始时间;
    }

    public void setStartTime_营业时间1_开始时间(String startTime_营业时间1_开始时间) {
        this.startTime_营业时间1_开始时间 = startTime_营业时间1_开始时间;
    }

    public String getStartTime2_营业时间2_开始时间() {
        return startTime2_营业时间2_开始时间;
    }

    public void setStartTime2_营业时间2_开始时间(String startTime2_营业时间2_开始时间) {
        this.startTime2_营业时间2_开始时间 = startTime2_营业时间2_开始时间;
    }

    public String getStartTime3_营业时间3_开始时间() {
        return startTime3_营业时间3_开始时间;
    }

    public void setStartTime3_营业时间3_开始时间(String startTime3_营业时间3_开始时间) {
        this.startTime3_营业时间3_开始时间 = startTime3_营业时间3_开始时间;
    }

    public String getStopTime_营业时间1_结束时间() {
        return stopTime_营业时间1_结束时间;
    }

    public void setStopTime_营业时间1_结束时间(String stopTime_营业时间1_结束时间) {
        this.stopTime_营业时间1_结束时间 = stopTime_营业时间1_结束时间;
    }

    public String getStopTime2_营业时间2_结束时间() {
        return stopTime2_营业时间2_结束时间;
    }

    public void setStopTime2_营业时间2_结束时间(String stopTime2_营业时间2_结束时间) {
        this.stopTime2_营业时间2_结束时间 = stopTime2_营业时间2_结束时间;
    }

    public String getStopTime3_营业时间3_结束时间() {
        return stopTime3_营业时间3_结束时间;
    }

    public void setStopTime3_营业时间3_结束时间(String stopTime3_营业时间3_结束时间) {
        this.stopTime3_营业时间3_结束时间 = stopTime3_营业时间3_结束时间;
    }

    public String getTimeSlotAuto_自动选择时段() {
        return timeSlotAuto_自动选择时段;
    }

    public void setTimeSlotAuto_自动选择时段(String timeSlotAuto_自动选择时段) {
        this.timeSlotAuto_自动选择时段 = timeSlotAuto_自动选择时段;
    }

    public List<TimeSlotStocksDTO> getTimeSlotStocks() {
        return timeSlotStocks;
    }

    public void setTimeSlotStocks(List<TimeSlotStocksDTO> timeSlotStocks) {
        this.timeSlotStocks = timeSlotStocks;
    }

    public List<TimeSlotRefundSetDTO> getTimeslotRefundSet() {
        return timeslotRefundSet;
    }

    public void setTimeslotRefundSet(List<TimeSlotRefundSetDTO> timeslotRefundSet) {
        this.timeslotRefundSet = timeslotRefundSet;
    }

    public String getUserReading_分销商须知() {
        return userReading_分销商须知;
    }

    public void setUserReading_分销商须知(String userReading_分销商须知) {
        this.userReading_分销商须知 = userReading_分销商须知;
    }

    public String getValidToHours_有效天数由原来天数为单位改为小时为单位以下单时间开始计算有效期() {
        return validToHours_有效天数由原来天数为单位改为小时为单位以下单时间开始计算有效期;
    }

    public void setValidToHours_有效天数由原来天数为单位改为小时为单位以下单时间开始计算有效期(String validToHours_有效天数由原来天数为单位改为小时为单位以下单时间开始计算有效期) {
        this.validToHours_有效天数由原来天数为单位改为小时为单位以下单时间开始计算有效期 = validToHours_有效天数由原来天数为单位改为小时为单位以下单时间开始计算有效期;
    }
}
