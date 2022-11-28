package com.lt.dom.thirdTS;


import cn.gjing.EncryptionUtil;
import com.beust.jcommander.internal.Lists;
import com.lt.dom.OctResp.TermResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingSkuPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.ProductRepository;

import com.lt.dom.repository.ReservationRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.requestvo.BookingTypeTowhoVo;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.thirdTS.domainTsToLt.*;
import com.lt.dom.vo.PlatRefundVo;
import com.lt.dom.vo.PlatUserVo;
import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.threeten.bp.LocalDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TsToLtServiceImpl {


    String baseUrl_请求地址 = "baseUrl_请求地址";//
    String 合作伙伴ID = "合作伙伴ID";//
    String 授权编码 = "授权编码";//

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReservationRepository reservationRepository;


    @Autowired
    private LtToTsServiceImpl ltToTsService;



    @Autowired
    private ThirdPartyServiceImpl thirdPartyService;


    @Autowired
    private BookingServiceImpl bookingService;


    @Autowired
    private FileStorageServiceImpl fileStorageService;

    @Autowired
    private TermServiceImpl termService;
    @Autowired
    private PriceServiceImpl priceService;


    @Autowired
    private SupplierRepository supplierRepository;



    /**
     * sign 签名 （参数名按ASCII码从小到大排序（字典序）+key+MD5+转大写签名）
     * @param map
     * @return
     */
    public static String encodeSign(SortedMap<String,String> map){
/*        if(StringUtils.isEmpty(key)){
            throw new RuntimeException("签名key不能为空");
        }*/
        Set<Map.Entry<String, String>> entries = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        List<String> values = Lists.newArrayList();

        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            String k = String.valueOf(entry.getKey());
            String v = String.valueOf(entry.getValue());
            if (StringUtils.isNotEmpty(v) && entry.getValue() !=null && !"sign".equals(k) && !"key".equals(k)) {
                values.add(k + "=" + v);
            }
        }
     //   values.add("key="+ key);
        String sign = StringUtils.join(values, "&");
        //return encodeByMD5(sign);
        return EncryptionUtil.md5(sign);
    }
    /*
     * 通过MD5加密
     *
     * @param algorithmStr
     * @return String

  public static String encodeByMD5(String algorithmStr) {
        if (algorithmStr == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("ALGORITHM_MD5");
            messageDigest.update(algorithmStr.getBytes("utf-8"));
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }*/

/*————————————————
        版权声明：本文为CSDN博主「lv_hang515888」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
        原文链接：https://blog.csdn.net/lv_hang515888/article/details/86621898*/





    public   LtRespToTs产品列表 getTsReqLt产品列表( TsReqLt产品列表 data){



        EnumMethord enumMethord = EnumMethord.valueOf(data.getMethod());


        if(!enumMethord.equals(EnumMethord.item_list)){
            return null;
        }

        if(!data.getFormat().equals("json")){
            return null;
        }

        if(!data.getFormat().equals("json")){
            return null;
        }


        List<Product> products = new ArrayList<>();

        if(data.getItem_id()!= null){

            Optional<Product> optional = productRepository.findById(data.getItem_id().longValue());

            if(optional.isPresent()){
                products.add(optional.get());

            }
        }else{
            Page<Product> productPage = productRepository.findAll(PageRequest.of(data.getPage(),data.getSize()));


            products.addAll(productPage.getContent());
        }





        List<ListDTO> list = products.stream().map(e->{






            //TODO 增加价格 MAP
            ListDTO.DatePricesDTO datePricesDTO = new ListDTO.DatePricesDTO();
            datePricesDTO.setDate(LocalDate.now());
            // datePricesDTO;//

            Map<LocalDate,ListDTO.DatePricesDTO> dateDatePricesDTOMap =
                    Arrays.asList(datePricesDTO).stream()
                            .collect(Collectors.toMap(pricesDTO->pricesDTO.getDate(), pricesDTO->{
                                pricesDTO.setDate(null);
                                return pricesDTO;
                            }));

           // LtRespToTs产品列表.ListDTO listDTO = getFul(e);
            Optional<PricingType> optionalPricingType = priceService.getDefault_price(e);

            if(optionalPricingType.isEmpty()){
                throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 sku priceingType");

            }
            PricingType pricingType = optionalPricingType.get();
            ListDTO listDTO = get(e,pricingType);

            listDTO.setDatePrices(dateDatePricesDTOMap);





            //TODO 增加库存 MAP
            ListDTO.DateStocksDTO dateStocksDTO = new ListDTO.DateStocksDTO();
            dateStocksDTO.setDate(LocalDate.now());
           Map<LocalDate,ListDTO.DateStocksDTO> dateDateStocksDTOMap =
                   Arrays.asList(dateStocksDTO).stream()
                           .collect(Collectors.toMap(pricesDTO->pricesDTO.getDate(), pricesDTO->{
                pricesDTO.setDate(null);
                return pricesDTO;

           }));
            listDTO.setDateStocks(dateDateStocksDTOMap);



            Supplier supplier = supplierRepository.findById(e.getSupplierId()).get();
            //TODO 增加供应商 Object
            SupplierListDTO supplierListDTO = SupplierListDTO.from(supplier);
            listDTO.setSupplierList(supplierListDTO);


            //TODO 增加Param Object
            ParamsDTO paramsDTO = new ParamsDTO();
            paramsDTO.setBookingReading_预约须知("城市主人卡");
            listDTO.setParams(paramsDTO);



            //TODO 增加规格 Arrays
            SpecTypeListDTO specTypeListDTO = new SpecTypeListDTO();
  /*          Map<String,SpecTypeListDTO> stringSpecTypeListDTOMap =
                    Arrays.asList(specTypeListDTO).stream().collect(Collectors.toMap(pricesDTO->pricesDTO.getId(), pricesDTO->pricesDTO));
*/
          //  listDTO.setSpecTypeList(Arrays.asList(specTypeListDTO));


            return listDTO;


        }).collect(Collectors.toList());

        LtRespToTs产品列表 ltRespToTs产品列表 = new LtRespToTs产品列表();
        ltRespToTs产品列表.setList(list);
        return ltRespToTs产品列表;
    }




    public ListDTO get(Product e, PricingType pricingType){



        //TODO 这里设置基本信息 Object
        ListDTO listDTO = new ListDTO();

        listDTO.setImage_产品封面图片网址(fileStorageService.loadDocumentWithDefault(EnumDocumentType.product_thumb,e.getCode()).getUrl());

        listDTO.setSendType_短信发送类型(2);   //"短信发送类型;1二维码，2文字码"
        listDTO.setSmsContent_短信内容("您好，您购买了榆林城市主人卡（年卡）购买单号HERO_12345678,请到景区首刷激活);");
        listDTO.setIdLimitAmount_同一身份证限购设置_最多可购买多少张(10);
        listDTO.setIdLimitOrders_同一身份证限购设置_最多可购买多少笔订单(11);

        listDTO.setModifyTime_产品信息最近更新时间(Timestamp.valueOf(e.getModifiedDate()).getTime());
        if(e.getRestriction_passenger_identity_documents_required()){
            listDTO.setMustIdNumber_下单时是否必须提供身份证号码字段(1); // //下单时是否必须提供身份证号码字段，1是，0否
        }else{
            listDTO.setMustIdNumber_下单时是否必须提供身份证号码字段(0); // //下单时是否必须提供身份证号码字段，1是，0否
        }

        Map<EnumTermType, TermResp> termTypeTermRespMap = termService.getMapResp(e);
        if(termTypeTermRespMap.containsKey(EnumTermType.booking)){
            listDTO.setNotice_预订须知(termTypeTermRespMap.get(EnumTermType.booking).getText());
        }


        if(e.getRefund()!= null && !e.getRefund()){
            listDTO.setRefundType_退票类型(2); //"退票类型：1：允许退票；2：不可退票；3：管理员审核",

        }else{
            listDTO.setRefundType_退票类型(1); //"退票类型：1：允许退票；2：不可退票；3：管理员审核",
            listDTO.setRefundMessage_退款说明(e.getRefund_note());
        }


        listDTO.setAmount_门票数量_总库存(1000);  //必须
        listDTO.setAmountDay_门票数量_每日(1000); //必须

        Timestamp timestamp_object = Timestamp.valueOf(LocalDateTime.now().plusDays(30));

        listDTO.setExpireTime_产品有效期结束时间(timestamp_object.getTime());  //必须
        listDTO.setGoodsType_门票类型(2); //必须  ////门票类型;1普通票，2套票，3线路

        listDTO.setId_产品ID(e.getId().intValue());  //必须



        listDTO.setMarketPrice_指导价_建议价(pricingType.getOriginal().toString()); //必须
        listDTO.setMaxNum_最多可购买数(10); //必须


        listDTO.setMinNum_至少须购买数(10); //必须

        listDTO.setNettPrice_分销价("11"); //必须
        listDTO.setOriginalPrice_市场价_门市价_票面原价("11");//必须


        listDTO.setStartTime_产品有效期开始时间(Timestamp.valueOf(e.getCreatedDate()).getTime()); //必须  //产品有效期开始时间；时间戳
        listDTO.setStatus_产品状态(1); //必须 产品状态（1：正常，0：下架）


        listDTO.setTitle_门票标题(e.getName()); //必须
        listDTO.setType_门票类型(2);//必须 //门票类型;1普通票，2套票，3线路

        listDTO.setWeek_有效星期("1,2,3,4,5,6,7");  //必须

        return listDTO;

    }





    public ListDTO getFul(Product e){



        //TODO 这里设置基本信息 Object
        ListDTO listDTO = new ListDTO();
        listDTO.setAmount_门票数量_总库存(1000);  //必须
        listDTO.setAmountDay_门票数量_每日(1000); //必须
        listDTO.setBackCash_返现金额_订单完成消费_验证__核销_后返给分销商的金额("11");
        listDTO.setBackCash_返现金额_订单完成消费_验证__核销_后返给分销商的金额("11");
        listDTO.setBrief_产品简介(e.getDesc_short());


        listDTO.setCategoryGroup_分类ID(11);
        listDTO.setCategoryGroupName_分类名称("11");


        listDTO.setCategoryGroups_分类ID_数组_("11");
        listDTO.setCategoryZone_同city_id_城市ID(11);


        listDTO.setCityId_城市ID(11);
        listDTO.setCityName_城市名称("11");
        listDTO.setCountyId_区县ID(11);
        listDTO.setCountyName_区县名称("11");


        listDTO.setDescription_产品详情(e.getLong_desc());

        Timestamp timestamp_object = Timestamp.valueOf(LocalDateTime.now().plusDays(30));

        listDTO.setExpireTime_产品有效期结束时间(timestamp_object.getTime());  //必须
        listDTO.setGoodsType_门票类型(2); //必须  ////门票类型;1普通票，2套票，3线路

        listDTO.setId_产品ID(e.getId().intValue());  //必须

        listDTO.setIdLimitAmount_同一身份证限购设置_最多可购买多少张(11);
        listDTO.setIdLimitOrders_同一身份证限购设置_最多可购买多少笔订单(11);
        listDTO.setIdLimitTime_同一身份证限购设置_检查天数(1);

        ;
        listDTO.setImage_产品封面图片网址(fileStorageService.loadDocumentWithDefault(EnumDocumentType.product_thumb,e.getCode()).getUrl());
        listDTO.setIsImport_是否导码产品(11);



        listDTO.setLastOrderDate_最晚下单时间("11");
        listDTO.setLastOrderTime_最晚下单时间(11);
        listDTO.setLastOrderTimeDelay_超过最晚下单时间_当下单时间超过最晚下单时间时是否允许下单且有效期自动延后(11);
        listDTO.setMarketPrice_指导价_建议价("11"); //必须
        listDTO.setMaxNum_最多可购买数(11); //必须

        listDTO.setMbLimitAmount_同一手机号限购设_最多可购买多少张(11);


        listDTO.setMbLimitOrders_mbLimitAmount_同一手机号限购设置_最多可购买多少笔订单(11);
        listDTO.setMbLimitTime_同一手机号限购设置_检查天数(11);
        listDTO.setMinNum_至少须购买数(11); //必须
        listDTO.setMmsContent_彩信内容("11");
        listDTO.setModifyTime_产品信息最近更新时间(Timestamp.valueOf(e.getModifiedDate()).getTime());

        listDTO.setMustIdNumber_下单时是否必须提供身份证号码字段(1); //下单时是否必须提供身份证号码字段，1是，0否


        listDTO.setNettPrice_分销价("11"); //必须
        listDTO.setNotice_预订须知("11");
        listDTO.setOriginalPrice_市场价_门市价_票面原价("11");//必须


        listDTO.setPayType_支付类型(1);
        listDTO.setPrintContent_验证打印内容("11");
        listDTO.setProvId_省份ID(11);

        listDTO.setProvName_省份名称("11");


        listDTO.setRefundChanrge_退票手续费(11);
        listDTO.setRefundChanrgeType_退票手续费类型(1);

        listDTO.setRefundMessage_退款说明("11");
        listDTO.setRefundType_退票类型(1); //"退票类型：1：允许退票；2：不可退票；3：管理员审核",
        listDTO.setSelfRefundFixed_退票手续费固定值("11");


        listDTO.setSelfRefundScale_退票手续费百分比值(11);
        listDTO.setSelfRefundTime_退单限制时间("11");
        listDTO.setSelfRefundScale_退票手续费百分比值(11);
        listDTO.setSelfRefundTimeType_退单限制类型(11);


        listDTO.setSendType_短信发送类型(1);
        listDTO.setSkuBackCash_SKU默认返现("11");
        listDTO.setSkuMarketPrice_SKU默认市场价("11");
        listDTO.setSkuSuggestPrice_SKU默认指导_建议价("11");
        listDTO.setSkuUserPrice_SKU默认分销价("11");
        listDTO.setSmsContent_短信内容("11");
        listDTO.setSortOrder_产品列表中排序_倒序(11);

        listDTO.setStartTime_产品有效期开始时间(Timestamp.valueOf(e.getCreatedDate()).getTime()); //必须  //产品有效期开始时间；时间戳

        listDTO.setStatus_产品状态(1); //必须  产品状态（1：正常，0：下架）
        listDTO.setSupplierId_景区ID("11");


        listDTO.setTitle_门票标题("11"); //必须
        listDTO.setType_门票类型(11);//必须 门票类型;1普通票，2套票，3线路


        listDTO.setValidityDay_订单有效期(11);
        listDTO.setValidityTime_生效时间(11);
        listDTO.setValidityType_订单有效期类型1(11);

        listDTO.setSupplierId_景区ID("11");
        listDTO.setWeek_有效星期("11");  //必须


        return listDTO;

    }

    public   LtRespToTs退单接口.InfoDTO getTsReqLt退单接口( TsReqLt退单接口 data){


        EnumMethord enumMethord = EnumMethord.valueOf(data.getMethod());


        data.getSerial_no(); // //退票流水号，如传此字段时，审核通知会返回对应的流水号

        if(!enumMethord.equals(EnumMethord.item_refund)){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"方法名称错误 item_refund");
        }

        if(!data.getFormat().equals("json")){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"格式 json");

        }




        List<String> codes =Arrays.asList(data.getCodes().split(","));


        PlatRefundVo platRefundVo = new PlatRefundVo();
        platRefundVo.setSerial_no(data.getSerial_no());
        platRefundVo.setOrders_id(data.getOrders_id());
        platRefundVo.setPlatform(EnumPlatform.TS);
        Optional<Reservation> reservation = reservationRepository.findByTrackingId(data.getOrders_id());

        Pair<Refund,PlatRefundVo> pair = bookingService.refund(platRefundVo,reservation.get());

        Refund refund = pair.getValue0();

        PlatRefundVo platRefundVo1 = pair.getValue1();

        LtRespToTs退单接口.InfoDTO infoDTO = new LtRespToTs退单接口.InfoDTO();
        infoDTO.setId(refund.getId());
        infoDTO.setStatus(1); //   订单状态：2申请退票，3退票成功，4退票不通过（如果产品在天时系统中属于采购产品，退票默认需要审核【退票审核通知接口】）





/*        LtReqTs退单审核通知.ToLtReqTs验证核销通知 toLtReqTs验证核销通知= new LtReqTs退单审核通知.ToLtReqTs验证核销通知();
        toLtReqTs验证核销通知.setSerial_no(platRefundVo1.getSerial_no());//退票记录id（申请退票有传此数据时，会返回申请退票时传入的流水号）
        toLtReqTs验证核销通知.setOrders_id(platRefundVo.getOrders_id()); //本平台订单ID（天时同城）
        toLtReqTs验证核销通知.setType(3);//审核结果： 3退票成功，4退票不通过
        toLtReqTs验证核销通知.setMessage("退票成功");//管理员审核备注/说明
        ltToTsService.ltReqTs退单审核通知(toLtReqTs验证核销通知);*/


        return infoDTO;
    }





    public   LtRespToTs下单接口.InfoDTO  getTsReqLt下单接口(TsReqLt下单接口 data){



        EnumMethord enumMethord = EnumMethord.valueOf(data.getMethod());


        if(!enumMethord.equals(EnumMethord.item_orders)){
            return null;
        }

        if(!data.getFormat().equals("json")){
            return null;
        }



        Optional<Product> optional =  thirdPartyService.find(EnumThirdParty.TS,data.getItem_id());

        if(optional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"产品");
        }
        Product product = optional.get();


        if(!product.getType().equals(EnumProductType.Pass)
                || !product.getStatus().equals(EnumProductStatus.active) ||
                !product.getPrivacyLevel().equals(EnumPrivacyLevel.public_)){
            List<String> errors = new ArrayList<>();

            if(!product.getType().equals(EnumProductType.Pass)) {
                errors.add("目前支持 一卡通 产品类型");
            }
            if(!product.getStatus().equals(EnumProductStatus.active)){
                errors.add("产品目前非发布状态");
            }
            if(!product.getPrivacyLevel().equals(EnumPrivacyLevel.public_)){
                errors.add("产品目前非公开");
            }

            throw new BookNotFoundException(Enumfailures.resource_not_found,errors.toString());
        }







        List<PricingType> pricingTypes = priceService.find(product);




        PricingType pricingType = pricingTypes.get(0);



        BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
        bookingTypeTowhoVo.setToWhoTyp(EnumBookingOjbectType.Product);

        bookingTypeTowhoVo.setTraveler(Arrays.stream(data.getPlayers()).map(e->{
            BookingSkuPojo.TravelerReq traveler = new BookingSkuPojo.TravelerReq();
            traveler.setId_card(e.getId_number());
            traveler.setName(e.getName());
            traveler.setPhone(e.getMobile());
            return traveler;
        }).collect(Collectors.toList()));



        bookingTypeTowhoVo.setProduct(product);
        bookingTypeTowhoVo.setSku(pricingType);
        bookingTypeTowhoVo.setQuantity(data.getSize().longValue());
        bookingTypeTowhoVo.setRemark(data.getRemark());




        bookingTypeTowhoVo.setDeliveryFormat(EnumDeliveryFormats.虚拟卡);


        if(bookingTypeTowhoVo.getDeliveryFormat().equals(EnumDeliveryFormats.虚拟卡)){

            if(data.getSize() != data.getPlayers().length){
                throw new BookNotFoundException(Enumfailures.resource_not_found,"订单数量和 游客数量不想否");

            }
        }


        BookingSkuPojo bookingSkuPojo = new BookingSkuPojo();
        bookingSkuPojo.setAdditional_info(new BookingSkuPojo.Tour());
        bookingSkuPojo.setShipping(new BookingSkuPojo.Shipping());




        PlatUserVo platUserVo = new PlatUserVo();
        platUserVo.setPlatform(EnumPlatform.TS);
        platUserVo.setId_number(data.getId_number());
        platUserVo.setName(data.getName());
        platUserVo.setPhone(data.getMobile());
        platUserVo.setId_type(EnumIdType.身份证);

        platUserVo.setRemark(data.getRemark());


        platUserVo.setTracking_id(data.getOrders_id());

        if(data.getIs_pay() == 1){ ////是否下单同时支付（1:同时支付，0:不支付）缺省时默认1
            platUserVo.setPaid(true);
            platUserVo.setPaidAmount(data.getSize().intValue()*Float.valueOf(data.getPrice()));
        }
        if(data.getIs_pay() == 0){ ////是否下单同时支付（1:同时支付，0:不支付）缺省时默认1
            platUserVo.setPaid(false);

        }



        Triplet<Reservation,List<LineItem>,PlatUserVo > booking =  bookingService.bookingWithPayment(Arrays.asList(bookingTypeTowhoVo),
                bookingSkuPojo,
                platUserVo);

        Reservation reservation = booking.getValue0();
        List<LineItem> lineItems = booking.getValue1();


        LineItem lineItem = lineItems.get(0);




        LtRespToTs下单接口.InfoDTO infoDTO = new LtRespToTs下单接口.InfoDTO();
        infoDTO.setId(reservation.getCode());  //第三方订单ID(非天时)   //必含
        infoDTO.setIsSend(""); //是否发送' //必含
        infoDTO.setCode(reservation.getCode()); //必含   //文字码(码号)【注:当是采购产品或审核发送的会异步返码,会通过码号推送接口推送至回调地址】




        infoDTO.setCodes(Arrays.asList(reservation.getCode())); //文字码(码号)组，一人一码的产品会有数据
        infoDTO.setContent(reservation.getCode()); //发送内容

        LtRespToTs下单接口.InfoDTO.ParamsDTO paramsDTO = new  LtRespToTs下单接口.InfoDTO.ParamsDTO();
        infoDTO.setParams(paramsDTO);
        paramsDTO.setIsRealCode(1); //游客是否可凭码号消费，是否是真实有效码号，0表示否，1表示真实可消费
        paramsDTO.setQrcodeImages(Arrays.asList(reservation.getCode())) ; //二维码链接组，需要与codes一一对应
        infoDTO.setParams(paramsDTO);

        infoDTO.setPrice(lineItem.getUnitPrice().toString());//结算单价
        infoDTO.setQrcode(reservation.getCode());//二维码数据(用于生成二维码图片)
        infoDTO.setSendPrice(lineItem.getUnitPrice().toString()); //发送价格

        return infoDTO;
    }









}
