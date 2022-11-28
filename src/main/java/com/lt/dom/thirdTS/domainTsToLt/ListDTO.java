package com.lt.dom.thirdTS.domainTsToLt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.threeten.bp.LocalDate;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
    @Data
    public class ListDTO {
        @JsonProperty("amount")
        private Integer amount_门票数量_总库存;
        @JsonProperty("amount_day")
        private Integer amountDay_门票数量_每日;
        @JsonProperty("back_cash")
        private String backCash_返现金额_订单完成消费_验证__核销_后返给分销商的金额;
        @JsonProperty("brief")
        private String brief_产品简介;
        @JsonProperty("category_group")
        private Integer categoryGroup_分类ID;
        @JsonProperty("category_group_name")
        private String categoryGroupName_分类名称;
        @JsonProperty("category_groups")
        private String categoryGroups_分类ID_数组_;
        @JsonProperty("category_zone")
        private Integer categoryZone_同city_id_城市ID;
        @JsonProperty("city_id")
        private Integer cityId_城市ID;
        @JsonProperty("city_name")
        private String cityName_城市名称;
        @JsonProperty("county_id")
        private Integer countyId_区县ID;
        @JsonProperty("county_name")
        private String countyName_区县名称;


        @JsonProperty("date_prices")
        private Map datePrices;

        @JsonProperty("date_stocks")
        private Map dateStocks;







        @JsonProperty("description")
        private String description_产品详情; //产品详情，带html代码
        @JsonProperty("expire_time")
        private Long expireTime_产品有效期结束时间; //产品有效期结束时间；时间戳
        @JsonProperty("goods_type")
        private Integer goodsType_门票类型; //门票类型;1普通票，2套票，3线路
        @JsonProperty("id")
        private Integer id_产品ID; //产品ID
        @JsonProperty("id_limit_amount")
        private Integer idLimitAmount_同一身份证限购设置_最多可购买多少张; //同一身份证限购设置，最多可购买多少张，默认0【可与id_limit_orders同时存在】
        @JsonProperty("id_limit_orders")
        private Integer idLimitOrders_同一身份证限购设置_最多可购买多少笔订单;  //同一身份证限购设置，最多可购买多少笔订单，默认【可与id_limit_amount同时存在】
        @JsonProperty("id_limit_time")
        private Integer idLimitTime_同一身份证限购设置_检查天数;  //同一身份证限购设置，检查天数，默认0
        @JsonProperty("image")
        private String image_产品封面图片网址;
        @JsonProperty("is_import")
        private Integer isImport_是否导码产品;





        @JsonProperty("last_order_date")
        private String lastOrderDate_最晚下单时间;  //最晚下单时间：字符串，\"d天H:i:s\"：须在H:i:s之前下单，d>0则顺延 d×24小时"
        @JsonProperty("last_order_time")
        private Integer lastOrderTime_最晚下单时间;  //最晚下单时间：秒数；0为不限
        @JsonProperty("last_order_time_delay")
        private Integer lastOrderTimeDelay_超过最晚下单时间_当下单时间超过最晚下单时间时是否允许下单且有效期自动延后;  //超过最晚下单时间：当下单时间超过最晚下单时间时是否允许下单且有效期自动延后，有3个选项1、禁止下单；比如最晚下单时间0天12点，12点前允许下单，12点后禁止下单。 2、允许下单，游玩日期自动调整为次日可用；比如最晚下单时间0天12点，12点前下的单当天可以使用，12点后下的单明天才能使用且游玩时间自动调整为第二天。 3、禁止下当天订单，允许下次日及以后的订单；比如最晚下单时间0天12点，12点后禁止下当天的订单，允许选择明天及以后的日期下单。 特别注意：此限制需要配合sku价格设置里的【有效天数】使用，如果【有效天数】设置为0的情况下不限制下单，因为订单购买后对整个有效期都是生效的。
        @JsonProperty("market_price")
        private String marketPrice_指导价_建议价;//指导价/建议价；由管理员设置供分销商参考
        @JsonProperty("max_num")
        private Integer maxNum_最多可购买数; //最多可购买数，0：不限
        @JsonProperty("mb_limit_amount")
        private Integer mbLimitAmount_同一手机号限购设_最多可购买多少张;  //同一手机号限购设置，最多可购买多少笔订单，默认0
        @JsonProperty("mb_limit_orders")
        private Integer mbLimitOrders_mbLimitAmount_同一手机号限购设置_最多可购买多少笔订单;  //同一手机号限购设置，检查天数，默认0
        @JsonProperty("mb_limit_time")
        private Integer mbLimitTime_同一手机号限购设置_检查天数;  //同一手机号限购设置，检查天数，默认0
        @JsonProperty("min_num")
        private Integer minNum_至少须购买数;// 至少须购买数，0：不限
        @JsonProperty("mms_content")
        private String mmsContent_彩信内容;
        @JsonProperty("modify_time")
        private Long modifyTime_产品信息最近更新时间;  //产品信息最近更新时间，时间戳
        @JsonProperty("must_id_number")
        private Integer mustIdNumber_下单时是否必须提供身份证号码字段;  //下单时是否必须提供身份证号码字段，1是，0否
        @JsonProperty("nett_price")
        private String nettPrice_分销价;  //分销价，分销商以此与管理员结算
        @JsonProperty("notice")
        private String notice_预订须知;  //预订须知, 可带html代码
        @JsonProperty("original_price")
        private String originalPrice_市场价_门市价_票面原价;  //市场价/门市价/票面原价


        @JsonProperty("params")
        private ParamsDTO params;

        public Map getDatePrices() {
            return datePrices;
        }

        public void setDatePrices(Map datePrices) {
            this.datePrices = datePrices;
        }

        public Map getDateStocks() {
            return dateStocks;
        }

        public void setDateStocks(Map dateStocks) {
            this.dateStocks = dateStocks;
        }

        public ParamsDTO getParams() {
            return params;
        }

        public void setParams(ParamsDTO params) {
            this.params = params;
        }

        public SupplierListDTO getSupplierList() {
            return supplierList;
        }

        public void setSupplierList(SupplierListDTO supplierList) {
            this.supplierList = supplierList;
        }

        public SupplierList2DTO getSupplierList2() {
            return supplierList2;
        }

        public void setSupplierList2(SupplierList2DTO supplierList2) {
            this.supplierList2 = supplierList2;
        }

        public List<SpecTypeListDTO> getSpecTypeList() {
            return specTypeList;
        }

        public void setSpecTypeList(List<SpecTypeListDTO> specTypeList) {
            this.specTypeList = specTypeList;
        }

        @JsonProperty("pay_type")
        private Integer payType_支付类型; //"支付类型：1：在线支付；2：景区到付",
        @JsonProperty("print_content")
        private String printContent_验证打印内容;
        @JsonProperty("prov_id")
        private Integer provId_省份ID;
        @JsonProperty("prov_name")
        private String provName_省份名称;
        @JsonProperty("refund_chanrge")
        private Integer refundChanrge_退票手续费; //"退票手续费：数值，例 5.00 单位/元；结果等于百分比+固定值",
        @JsonProperty("refund_chanrge_type")
        private Integer refundChanrgeType_退票手续费类型; //"退票手续费类型：1: 按票数计算；2：按订单计算，每退一张票需扣手续费 1/票数",
        @JsonProperty("refund_message")
        private String refundMessage_退款说明;
        @JsonProperty("refund_type")
        private Integer refundType_退票类型; // "退票类型：1：允许退票；2：不可退票；3：管理员审核",
        @JsonProperty("self_refund_fixed")
        private String selfRefundFixed_退票手续费固定值; //"退票手续费固定值，单位/元",
        @JsonProperty("self_refund_scale")
        private Integer selfRefundScale_退票手续费百分比值; //"退票手续费百分比值：数值 按票价的百分比计算",
        @JsonProperty("self_refund_time")
        private String selfRefundTime_退单限制时间; //"退单限制时间，与退单限制类型配合使用",

        @JsonProperty("self_refund_time_type")
        private Integer selfRefundTimeType_退单限制类型; //"退单限制类型，1：订单过期之后，2：订单过期之前",
        @JsonProperty("send_type")
        private Integer sendType_短信发送类型; //"短信发送类型;1二维码，2文字码"
        @JsonProperty("sku_back_cash")
        private String skuBackCash_SKU默认返现; //SKU默认返现
        @JsonProperty("sku_market_price")
        private String skuMarketPrice_SKU默认市场价; //SKU默认市场价
        @JsonProperty("sku_suggest_price")
        private String skuSuggestPrice_SKU默认指导_建议价; //SKU默认指导/建议价
        @JsonProperty("sku_user_price")
        private String skuUserPrice_SKU默认分销价; //SKU默认分销价【SKU默认价格信息，若日历价格无独立设置，则订单采用SKU信息】
        @JsonProperty("sms_content")
        private String smsContent_短信内容;
        @JsonProperty("sort_order")
        private Integer sortOrder_产品列表中排序_倒序; //产品列表中排序，倒序
        @JsonProperty("start_time")
        private Long startTime_产品有效期开始时间; //产品有效期开始时间；时间戳
        @JsonProperty("status")
        private Integer status_产品状态;  //产品状态（1：正常，0：下架）
        @JsonProperty("supplier_id")
        private String supplierId_景区ID;  //景区ID


        @JsonProperty("supplier_list")
        private SupplierListDTO supplierList;
        @JsonProperty("supplier_list2")
        private SupplierList2DTO supplierList2;



        @JsonProperty("title")
        private String title_门票标题;
        @JsonProperty("type")
        private Integer type_门票类型; //门票类型;1普通票，2套票，3线路
        @JsonProperty("validity_day")
        private Integer validityDay_订单有效期;
        @JsonProperty("validity_time")
        private Integer validityTime_生效时间; //生效时间，下单后经多少时间后可用，按小时计
        @JsonProperty("validity_type")
        private Integer validityType_订单有效期类型1;  //订单有效期类型1：订单有效期>0，2：订单有效期 = 0（与产品有效期一致）


        @JsonProperty("spec_type_list")
        private List<SpecTypeListDTO> specTypeList;
        @JsonProperty("week")
        private String week_有效星期; //  "week": "有效星期：字符串，一周中哪些天可用，0表示周日，用英文逗号分隔，如0,1,2,3,4,5,6"

        @NoArgsConstructor
        @Data
        public static class DatePricesDTO {
            @JsonProperty("original_price")
            private String originalPrice;
            @JsonProperty("nett_price")
            private String nettPrice;
            @JsonProperty("price")
            private String price;
            @JsonProperty("stock")
            private String stock;
            @JsonProperty("date")
            private LocalDate date;

            public LocalDate getDate() {
                return date;
            }

            public void setDate(LocalDate date) {
                this.date = date;
            }

            @JsonProperty("2015-12-31")
            private _$20151231DTO $20151231;

            @NoArgsConstructor
            @Data
            public static class _$20151231DTO {
                @JsonProperty("original_price")
                private String originalPrice;
                @JsonProperty("nett_price")
                private String nettPrice;
                @JsonProperty("price")
                private String price;
                @JsonProperty("stock")
                private String stock;
            }
        }

        @NoArgsConstructor
        @Data
        public static class DateStocksDTO {
            @JsonProperty("date")
            private LocalDate date;

            public LocalDate getDate() {
                return date;
            }

            public void setDate(LocalDate date) {
                this.date = date;
            }

            @JsonProperty("stock")
            private String stock;
            @NoArgsConstructor
            @Data
            public static class _$20151231DTO {
                @JsonProperty("stock")
                private String stock;
            }
        }



        @NoArgsConstructor
        @Data
        public static class SupplierList2DTO {
            @JsonProperty("address")
            private String address;
            @JsonProperty("city")
            private String city;
            @JsonProperty("city_name")
            private String cityName;
            @JsonProperty("id")
            private String id;
            @JsonProperty("province")
            private String province;
            @JsonProperty("province_name")
            private String provinceName;
            @JsonProperty("title")
            private String title;
        }


        public Integer getAmount_门票数量_总库存() {
            return amount_门票数量_总库存;
        }

        public void setAmount_门票数量_总库存(Integer amount_门票数量_总库存) {
            this.amount_门票数量_总库存 = amount_门票数量_总库存;
        }

        public Integer getAmountDay_门票数量_每日() {
            return amountDay_门票数量_每日;
        }

        public void setAmountDay_门票数量_每日(Integer amountDay_门票数量_每日) {
            this.amountDay_门票数量_每日 = amountDay_门票数量_每日;
        }

        public String getBackCash_返现金额_订单完成消费_验证__核销_后返给分销商的金额() {
            return backCash_返现金额_订单完成消费_验证__核销_后返给分销商的金额;
        }

        public void setBackCash_返现金额_订单完成消费_验证__核销_后返给分销商的金额(String backCash_返现金额_订单完成消费_验证__核销_后返给分销商的金额) {
            this.backCash_返现金额_订单完成消费_验证__核销_后返给分销商的金额 = backCash_返现金额_订单完成消费_验证__核销_后返给分销商的金额;
        }

        public String getBrief_产品简介() {
            return brief_产品简介;
        }

        public void setBrief_产品简介(String brief_产品简介) {
            this.brief_产品简介 = brief_产品简介;
        }

        public Integer getCategoryGroup_分类ID() {
            return categoryGroup_分类ID;
        }

        public void setCategoryGroup_分类ID(Integer categoryGroup_分类ID) {
            this.categoryGroup_分类ID = categoryGroup_分类ID;
        }

        public String getCategoryGroupName_分类名称() {
            return categoryGroupName_分类名称;
        }

        public void setCategoryGroupName_分类名称(String categoryGroupName_分类名称) {
            this.categoryGroupName_分类名称 = categoryGroupName_分类名称;
        }

        public String getCategoryGroups_分类ID_数组_() {
            return categoryGroups_分类ID_数组_;
        }

        public void setCategoryGroups_分类ID_数组_(String categoryGroups_分类ID_数组_) {
            this.categoryGroups_分类ID_数组_ = categoryGroups_分类ID_数组_;
        }

        public Integer getCategoryZone_同city_id_城市ID() {
            return categoryZone_同city_id_城市ID;
        }

        public void setCategoryZone_同city_id_城市ID(Integer categoryZone_同city_id_城市ID) {
            this.categoryZone_同city_id_城市ID = categoryZone_同city_id_城市ID;
        }

        public Integer getCityId_城市ID() {
            return cityId_城市ID;
        }

        public void setCityId_城市ID(Integer cityId_城市ID) {
            this.cityId_城市ID = cityId_城市ID;
        }

        public String getCityName_城市名称() {
            return cityName_城市名称;
        }

        public void setCityName_城市名称(String cityName_城市名称) {
            this.cityName_城市名称 = cityName_城市名称;
        }

        public Integer getCountyId_区县ID() {
            return countyId_区县ID;
        }

        public void setCountyId_区县ID(Integer countyId_区县ID) {
            this.countyId_区县ID = countyId_区县ID;
        }

        public String getCountyName_区县名称() {
            return countyName_区县名称;
        }

        public void setCountyName_区县名称(String countyName_区县名称) {
            this.countyName_区县名称 = countyName_区县名称;
        }

        public String getDescription_产品详情() {
            return description_产品详情;
        }

        public void setDescription_产品详情(String description_产品详情) {
            this.description_产品详情 = description_产品详情;
        }

        public Long getExpireTime_产品有效期结束时间() {
            return expireTime_产品有效期结束时间;
        }

        public void setExpireTime_产品有效期结束时间(Long expireTime_产品有效期结束时间) {
            this.expireTime_产品有效期结束时间 = expireTime_产品有效期结束时间;
        }

        public Integer getGoodsType_门票类型() {
            return goodsType_门票类型;
        }

        public void setGoodsType_门票类型(Integer goodsType_门票类型) {
            this.goodsType_门票类型 = goodsType_门票类型;
        }

        public Integer getId_产品ID() {
            return id_产品ID;
        }

        public void setId_产品ID(Integer id_产品ID) {
            this.id_产品ID = id_产品ID;
        }

        public Integer getIdLimitAmount_同一身份证限购设置_最多可购买多少张() {
            return idLimitAmount_同一身份证限购设置_最多可购买多少张;
        }

        public void setIdLimitAmount_同一身份证限购设置_最多可购买多少张(Integer idLimitAmount_同一身份证限购设置_最多可购买多少张) {
            this.idLimitAmount_同一身份证限购设置_最多可购买多少张 = idLimitAmount_同一身份证限购设置_最多可购买多少张;
        }

        public Integer getIdLimitOrders_同一身份证限购设置_最多可购买多少笔订单() {
            return idLimitOrders_同一身份证限购设置_最多可购买多少笔订单;
        }

        public void setIdLimitOrders_同一身份证限购设置_最多可购买多少笔订单(Integer idLimitOrders_同一身份证限购设置_最多可购买多少笔订单) {
            this.idLimitOrders_同一身份证限购设置_最多可购买多少笔订单 = idLimitOrders_同一身份证限购设置_最多可购买多少笔订单;
        }

        public Integer getIdLimitTime_同一身份证限购设置_检查天数() {
            return idLimitTime_同一身份证限购设置_检查天数;
        }

        public void setIdLimitTime_同一身份证限购设置_检查天数(Integer idLimitTime_同一身份证限购设置_检查天数) {
            this.idLimitTime_同一身份证限购设置_检查天数 = idLimitTime_同一身份证限购设置_检查天数;
        }

        public String getImage_产品封面图片网址() {
            return image_产品封面图片网址;
        }

        public void setImage_产品封面图片网址(String image_产品封面图片网址) {
            this.image_产品封面图片网址 = image_产品封面图片网址;
        }

        public Integer getIsImport_是否导码产品() {
            return isImport_是否导码产品;
        }

        public void setIsImport_是否导码产品(Integer isImport_是否导码产品) {
            this.isImport_是否导码产品 = isImport_是否导码产品;
        }

        public String getLastOrderDate_最晚下单时间() {
            return lastOrderDate_最晚下单时间;
        }

        public void setLastOrderDate_最晚下单时间(String lastOrderDate_最晚下单时间) {
            this.lastOrderDate_最晚下单时间 = lastOrderDate_最晚下单时间;
        }

        public Integer getLastOrderTime_最晚下单时间() {
            return lastOrderTime_最晚下单时间;
        }

        public void setLastOrderTime_最晚下单时间(Integer lastOrderTime_最晚下单时间) {
            this.lastOrderTime_最晚下单时间 = lastOrderTime_最晚下单时间;
        }

        public Integer getLastOrderTimeDelay_超过最晚下单时间_当下单时间超过最晚下单时间时是否允许下单且有效期自动延后() {
            return lastOrderTimeDelay_超过最晚下单时间_当下单时间超过最晚下单时间时是否允许下单且有效期自动延后;
        }

        public void setLastOrderTimeDelay_超过最晚下单时间_当下单时间超过最晚下单时间时是否允许下单且有效期自动延后(Integer lastOrderTimeDelay_超过最晚下单时间_当下单时间超过最晚下单时间时是否允许下单且有效期自动延后) {
            this.lastOrderTimeDelay_超过最晚下单时间_当下单时间超过最晚下单时间时是否允许下单且有效期自动延后 = lastOrderTimeDelay_超过最晚下单时间_当下单时间超过最晚下单时间时是否允许下单且有效期自动延后;
        }

        public String getMarketPrice_指导价_建议价() {
            return marketPrice_指导价_建议价;
        }

        public void setMarketPrice_指导价_建议价(String marketPrice_指导价_建议价) {
            this.marketPrice_指导价_建议价 = marketPrice_指导价_建议价;
        }

        public Integer getMaxNum_最多可购买数() {
            return maxNum_最多可购买数;
        }

        public void setMaxNum_最多可购买数(Integer maxNum_最多可购买数) {
            this.maxNum_最多可购买数 = maxNum_最多可购买数;
        }

        public Integer getMbLimitAmount_同一手机号限购设_最多可购买多少张() {
            return mbLimitAmount_同一手机号限购设_最多可购买多少张;
        }

        public void setMbLimitAmount_同一手机号限购设_最多可购买多少张(Integer mbLimitAmount_同一手机号限购设_最多可购买多少张) {
            this.mbLimitAmount_同一手机号限购设_最多可购买多少张 = mbLimitAmount_同一手机号限购设_最多可购买多少张;
        }

        public Integer getMbLimitOrders_mbLimitAmount_同一手机号限购设置_最多可购买多少笔订单() {
            return mbLimitOrders_mbLimitAmount_同一手机号限购设置_最多可购买多少笔订单;
        }

        public void setMbLimitOrders_mbLimitAmount_同一手机号限购设置_最多可购买多少笔订单(Integer mbLimitOrders_mbLimitAmount_同一手机号限购设置_最多可购买多少笔订单) {
            this.mbLimitOrders_mbLimitAmount_同一手机号限购设置_最多可购买多少笔订单 = mbLimitOrders_mbLimitAmount_同一手机号限购设置_最多可购买多少笔订单;
        }

        public Integer getMbLimitTime_同一手机号限购设置_检查天数() {
            return mbLimitTime_同一手机号限购设置_检查天数;
        }

        public void setMbLimitTime_同一手机号限购设置_检查天数(Integer mbLimitTime_同一手机号限购设置_检查天数) {
            this.mbLimitTime_同一手机号限购设置_检查天数 = mbLimitTime_同一手机号限购设置_检查天数;
        }

        public Integer getMinNum_至少须购买数() {
            return minNum_至少须购买数;
        }

        public void setMinNum_至少须购买数(Integer minNum_至少须购买数) {
            this.minNum_至少须购买数 = minNum_至少须购买数;
        }

        public String getMmsContent_彩信内容() {
            return mmsContent_彩信内容;
        }

        public void setMmsContent_彩信内容(String mmsContent_彩信内容) {
            this.mmsContent_彩信内容 = mmsContent_彩信内容;
        }

        public Long getModifyTime_产品信息最近更新时间() {
            return modifyTime_产品信息最近更新时间;
        }

        public void setModifyTime_产品信息最近更新时间(Long modifyTime_产品信息最近更新时间) {
            this.modifyTime_产品信息最近更新时间 = modifyTime_产品信息最近更新时间;
        }

        public Integer getMustIdNumber_下单时是否必须提供身份证号码字段() {
            return mustIdNumber_下单时是否必须提供身份证号码字段;
        }

        public void setMustIdNumber_下单时是否必须提供身份证号码字段(Integer mustIdNumber_下单时是否必须提供身份证号码字段) {
            this.mustIdNumber_下单时是否必须提供身份证号码字段 = mustIdNumber_下单时是否必须提供身份证号码字段;
        }

        public String getNettPrice_分销价() {
            return nettPrice_分销价;
        }

        public void setNettPrice_分销价(String nettPrice_分销价) {
            this.nettPrice_分销价 = nettPrice_分销价;
        }

        public String getNotice_预订须知() {
            return notice_预订须知;
        }

        public void setNotice_预订须知(String notice_预订须知) {
            this.notice_预订须知 = notice_预订须知;
        }

        public String getOriginalPrice_市场价_门市价_票面原价() {
            return originalPrice_市场价_门市价_票面原价;
        }

        public void setOriginalPrice_市场价_门市价_票面原价(String originalPrice_市场价_门市价_票面原价) {
            this.originalPrice_市场价_门市价_票面原价 = originalPrice_市场价_门市价_票面原价;
        }

        public Integer getPayType_支付类型() {
            return payType_支付类型;
        }

        public void setPayType_支付类型(Integer payType_支付类型) {
            this.payType_支付类型 = payType_支付类型;
        }

        public String getPrintContent_验证打印内容() {
            return printContent_验证打印内容;
        }

        public void setPrintContent_验证打印内容(String printContent_验证打印内容) {
            this.printContent_验证打印内容 = printContent_验证打印内容;
        }

        public Integer getProvId_省份ID() {
            return provId_省份ID;
        }

        public void setProvId_省份ID(Integer provId_省份ID) {
            this.provId_省份ID = provId_省份ID;
        }

        public String getProvName_省份名称() {
            return provName_省份名称;
        }

        public void setProvName_省份名称(String provName_省份名称) {
            this.provName_省份名称 = provName_省份名称;
        }

        public Integer getRefundChanrge_退票手续费() {
            return refundChanrge_退票手续费;
        }

        public void setRefundChanrge_退票手续费(Integer refundChanrge_退票手续费) {
            this.refundChanrge_退票手续费 = refundChanrge_退票手续费;
        }

        public Integer getRefundChanrgeType_退票手续费类型() {
            return refundChanrgeType_退票手续费类型;
        }

        public void setRefundChanrgeType_退票手续费类型(Integer refundChanrgeType_退票手续费类型) {
            this.refundChanrgeType_退票手续费类型 = refundChanrgeType_退票手续费类型;
        }

        public String getRefundMessage_退款说明() {
            return refundMessage_退款说明;
        }

        public void setRefundMessage_退款说明(String refundMessage_退款说明) {
            this.refundMessage_退款说明 = refundMessage_退款说明;
        }

        public Integer getRefundType_退票类型() {
            return refundType_退票类型;
        }

        public void setRefundType_退票类型(Integer refundType_退票类型) {
            this.refundType_退票类型 = refundType_退票类型;
        }

        public String getSelfRefundFixed_退票手续费固定值() {
            return selfRefundFixed_退票手续费固定值;
        }

        public void setSelfRefundFixed_退票手续费固定值(String selfRefundFixed_退票手续费固定值) {
            this.selfRefundFixed_退票手续费固定值 = selfRefundFixed_退票手续费固定值;
        }

        public Integer getSelfRefundScale_退票手续费百分比值() {
            return selfRefundScale_退票手续费百分比值;
        }

        public void setSelfRefundScale_退票手续费百分比值(Integer selfRefundScale_退票手续费百分比值) {
            this.selfRefundScale_退票手续费百分比值 = selfRefundScale_退票手续费百分比值;
        }

        public String getSelfRefundTime_退单限制时间() {
            return selfRefundTime_退单限制时间;
        }

        public void setSelfRefundTime_退单限制时间(String selfRefundTime_退单限制时间) {
            this.selfRefundTime_退单限制时间 = selfRefundTime_退单限制时间;
        }

        public Integer getSelfRefundTimeType_退单限制类型() {
            return selfRefundTimeType_退单限制类型;
        }

        public void setSelfRefundTimeType_退单限制类型(Integer selfRefundTimeType_退单限制类型) {
            this.selfRefundTimeType_退单限制类型 = selfRefundTimeType_退单限制类型;
        }

        public Integer getSendType_短信发送类型() {
            return sendType_短信发送类型;
        }

        public void setSendType_短信发送类型(Integer sendType_短信发送类型) {
            this.sendType_短信发送类型 = sendType_短信发送类型;
        }

        public String getSkuBackCash_SKU默认返现() {
            return skuBackCash_SKU默认返现;
        }

        public void setSkuBackCash_SKU默认返现(String skuBackCash_SKU默认返现) {
            this.skuBackCash_SKU默认返现 = skuBackCash_SKU默认返现;
        }

        public String getSkuMarketPrice_SKU默认市场价() {
            return skuMarketPrice_SKU默认市场价;
        }

        public void setSkuMarketPrice_SKU默认市场价(String skuMarketPrice_SKU默认市场价) {
            this.skuMarketPrice_SKU默认市场价 = skuMarketPrice_SKU默认市场价;
        }

        public String getSkuSuggestPrice_SKU默认指导_建议价() {
            return skuSuggestPrice_SKU默认指导_建议价;
        }

        public void setSkuSuggestPrice_SKU默认指导_建议价(String skuSuggestPrice_SKU默认指导_建议价) {
            this.skuSuggestPrice_SKU默认指导_建议价 = skuSuggestPrice_SKU默认指导_建议价;
        }

        public String getSkuUserPrice_SKU默认分销价() {
            return skuUserPrice_SKU默认分销价;
        }

        public void setSkuUserPrice_SKU默认分销价(String skuUserPrice_SKU默认分销价) {
            this.skuUserPrice_SKU默认分销价 = skuUserPrice_SKU默认分销价;
        }

        public String getSmsContent_短信内容() {
            return smsContent_短信内容;
        }

        public void setSmsContent_短信内容(String smsContent_短信内容) {
            this.smsContent_短信内容 = smsContent_短信内容;
        }

        public Integer getSortOrder_产品列表中排序_倒序() {
            return sortOrder_产品列表中排序_倒序;
        }

        public void setSortOrder_产品列表中排序_倒序(Integer sortOrder_产品列表中排序_倒序) {
            this.sortOrder_产品列表中排序_倒序 = sortOrder_产品列表中排序_倒序;
        }

        public Long getStartTime_产品有效期开始时间() {
            return startTime_产品有效期开始时间;
        }

        public void setStartTime_产品有效期开始时间(Long startTime_产品有效期开始时间) {
            this.startTime_产品有效期开始时间 = startTime_产品有效期开始时间;
        }

        public Integer getStatus_产品状态() {
            return status_产品状态;
        }

        public void setStatus_产品状态(Integer status_产品状态) {
            this.status_产品状态 = status_产品状态;
        }

        public String getSupplierId_景区ID() {
            return supplierId_景区ID;
        }

        public void setSupplierId_景区ID(String supplierId_景区ID) {
            this.supplierId_景区ID = supplierId_景区ID;
        }

        public String getTitle_门票标题() {
            return title_门票标题;
        }

        public void setTitle_门票标题(String title_门票标题) {
            this.title_门票标题 = title_门票标题;
        }

        public Integer getType_门票类型() {
            return type_门票类型;
        }

        public void setType_门票类型(Integer type_门票类型) {
            this.type_门票类型 = type_门票类型;
        }

        public Integer getValidityDay_订单有效期() {
            return validityDay_订单有效期;
        }

        public void setValidityDay_订单有效期(Integer validityDay_订单有效期) {
            this.validityDay_订单有效期 = validityDay_订单有效期;
        }

        public Integer getValidityTime_生效时间() {
            return validityTime_生效时间;
        }

        public void setValidityTime_生效时间(Integer validityTime_生效时间) {
            this.validityTime_生效时间 = validityTime_生效时间;
        }

        public Integer getValidityType_订单有效期类型1() {
            return validityType_订单有效期类型1;
        }

        public void setValidityType_订单有效期类型1(Integer validityType_订单有效期类型1) {
            this.validityType_订单有效期类型1 = validityType_订单有效期类型1;
        }

        public String getWeek_有效星期() {
            return week_有效星期;
        }

        public void setWeek_有效星期(String week_有效星期) {
            this.week_有效星期 = week_有效星期;
        }
    }
