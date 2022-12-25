package com.lt.dom.serviceOtc.product;


import cn.hutool.core.util.ObjectUtil;
import com.lt.dom.FulfiiledItemRepository;
import com.lt.dom.OctResp.*;
import com.lt.dom.controllerOct.RedemptionRestController;
import com.lt.dom.controllerOct.VoucherTicketPcRestController;
import com.lt.dom.controllerOct.VoucherTicketRestController;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.freemarker.EticketController;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.RedemBycodePojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.proto.rabit.CityPassBooking;
import com.lt.dom.proto.rabit.EnumRabbitMessageType;
import com.lt.dom.proto.rabit.RabbitMessage;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.BookingTypeTowhoVo;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.thirdTS.LtToTsServiceImpl;
import com.lt.dom.thirdTS.domainLtToTs.LtReqTs验证核销通知;
import com.lt.dom.thirdTS.domainLtToTs.TsRespLt验证核销通知;
import com.lt.dom.vo.*;
import org.apache.commons.lang.RandomStringUtils;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.lt.dom.serviceOtc.JsonParse.GSON;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class CityPassServiceImpl {
    Logger logger = LoggerFactory.getLogger(CityPassServiceImpl.class);

    @Autowired
    private PassServiceImpl passService;

    @Autowired
    private ComponentRightServiceImpl componentRightService;

    @Autowired
    private ContactServiceImpl contactService;

    @Autowired
    private AvailabilityServiceImpl availabilityService;



    @Autowired
    private CryptoServiceImpl cryptoService;


    @Autowired
    private FulfillServiceImpl fulfillService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SMSServiceImpl smsService;


    @Autowired
    private LineItemRepository lineItemRepository;

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private CardholderRepository cardholderRepository;
    @Autowired
    private ValidatorServiceImpl validatorService;



    @Autowired
    private RedemptionServiceImpl redemptionService;

    @Autowired
    private RightRedemptionEntryRepository rightRedemptionEntryRepository;




    @Autowired
    private PassRepository passRepository;


    @Autowired
    private ValidatorRepository validatorRepository;


    @Autowired
    private FileStorageServiceImpl fileStorageService
            ;



    @Autowired
    private IntelliCodeRepository intelliCodeRepository;

    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private ComponentRightRepository componentRightRepository;


    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private IdGenServiceImpl idGenService;
    @Autowired
    private BookingBaseServiceImpl bookingBaseService;


    @Autowired
    private ComponentVounchRepository componentVounchRepository;

    @Autowired
    private LtToTsServiceImpl ltToTsService;





    // 这里是订单 支付后
    public Asset pay(Supplier supplier) {

        return null;
    }

    // 这里是 订单 确认后
    public Asset fullfil_behaivor(Supplier supplier) {

        return null;
    }
    public List<Asset> component_redeam_behaivor(List<Pair<String, Long>> collect) {
// TODO 权益核销
        return null;
    }

    public List<Asset> bill_behaiver(List<Pair<String, Long>> collect) {
// TODO 对权益进行 核销
        return null;
    }


    public void show(ProductResp productResp, Product product) {

        List<Component> componentRightPage= componentRepository.findAllByProduct(product.getId());

        if(product.getType().equals(EnumProductType.Pass)){

            List<ComponentRight> componentRightList= componentRightRepository.findAllById(componentRightPage.stream().map(e->e.getComponentRightId()).collect(Collectors.toList()));

            ProductOptionResp productOption = new ProductOptionResp();


            productOption.setType(EnumProductOptionType.radio_buttons);
            productOption.setDisplay_name("权益组合");
            productOption.setTip("支持客户自定义游玩景点");
            productOption.setKey("rights");
            productOption.setOption_values(componentRightList.stream().map(e->{

                ProductOptionValueResp productOptionValue = new ProductOptionValueResp();
                productOptionValue.setLabel(e.getName());
                productOptionValue.setId(e.getId());
                productOptionValue.setDefault(true);
                return productOptionValue;

            }).collect(Collectors.toList()));


            ProductOptionResp productOption_guide = new ProductOptionResp();


            productOption_guide.setType(EnumProductOptionType.radio_buttons);
            productOption_guide.setDisplay_name("导游");
            productOption_guide.setTip("支持客户自定义游玩景点");
            productOption_guide.setKey("guide");



            productOption_guide.setOption_values(Arrays.stream(EnumGuideType.values()).map(e->{

                ProductOptionValueRespStringId productOptionValue = new ProductOptionValueRespStringId();
                productOptionValue.setLabel(e.name());
                productOptionValue.setId(e.name());
                productOptionValue.setDefault(true);
                return productOptionValue;

            }).collect(Collectors.toList()));

            productResp.setOptions(Arrays.asList(productOption,productOption_guide));

        }



    }

    private static final EnumProductType productType = EnumProductType.Pass;
    private static final EnumLineType lineType = EnumLineType.Pass;
    private static final EnumVoucherType voucherType = EnumVoucherType.PASS;
    @Autowired
    private VoucherTicketRepository voucherTicketRepository;


    public void booking_trial(List<BookingTypeTowhoVo> list) {

        List<BookingTypeTowhoVo> bookingTypeTowhoVoList = list.stream().filter(e->e.getProduct().getType().equals(productType)).map(e->{
            return e;
        }).collect(Collectors.toList());


        if(bookingTypeTowhoVoList.size() == 0){
            return;
        }

      List<Pair<Boolean,String>> check =   bookingTypeTowhoVoList.stream().map(e->{

            if(e.getProduct().getRestriction_passenger_identity_documents_required()){

                List<String> error_traveler  =e.getTraveler().stream().map(tra-> {
                    List<String> error = new ArrayList<>();
                    if(ObjectUtil.isEmpty(tra.getName())){
                        error.add("身份证为空");
                    };
                    if(ObjectUtil.isEmpty(tra.getId_card())){
                        error.add("身份证号码不能为空");
                    };
                    if(ObjectUtil.isEmpty(tra.getIdType())){
                        error.add("身份证类型不能为空");
                    };
                    return error.stream().collect(Collectors.joining(","));
                }).collect(Collectors.toList());


                if(error_traveler.isEmpty()){
                    return Pair.with(false,error_traveler.stream().collect(Collectors.joining(",")));
                }
                return Pair.with(true,"suucess");
            }

               return Pair.with(true,"suucess");
        }).collect(Collectors.toList());


        if(check.stream().filter(e->e.getValue0() == false).findAny().isPresent()){

            String message = check.stream().filter(e->e.getValue0() == false).map(e->e.getValue1()).collect(Collectors.joining(""));


            throw new BookNotFoundException(Enumfailures.not_found,message);


        }




        List<String> id_number_list = bookingTypeTowhoVoList.stream().map(e->{
            return e.getTraveler().stream().map(travelerReq->travelerReq.getId_card()).collect(Collectors.toList());
        }).flatMap(List::stream).collect(Collectors.toList());

        if(id_number_list.size()==0){
            throw new ExistException(Enumfailures.invalid_amount,"至少需要添加一名旅客");

        }
        String pattern = "^(6127|6108)\\d{2}[1-9]\\d{3}((0[1-9])|(1[0-2]))(0[1-9]|([1|2][0-9])|3[0-1])((\\d{4})|\\d{3}X)$";

        passService.allowedHolder(id_number_list, pattern);

    }






    public void booking(LineItem lineItem, Product product, PricingRate pricingRate) {
        if(!product.getType().equals(productType)){
            return;
        }


        lineItem.setLineType(lineType);
        lineItem.setStatus(EnumLineItemStatus.Executing);
        lineItem.setBillingTriggerRule(EnumBillingTriggerRule.WithoutFulfillment);

        logger.debug("这里来看看 base_code_price {}  unitPrice{}",lineItem.getBase_cost_price(),lineItem.getUnitPrice());
      //  lineItem.setBase_cost_price(pricingRate.getNet().floatValue());

        lineItem.setFulfillmentInstructionsType(EnumFulfillmentInstructionsType.DIGITAL);
        lineItem.setDeliveryFormats(EnumDeliveryFormats.虚拟卡);
        lineItem.setFulfillment_behavior(EnumFulfillment_behavior.Create_pass);
    }

    public void fullfil(Reservation reservation, List<LineItem> lineItemList, PlatUserVo user) {


/*    Fulfillment fulfillment = new Fulfillment();


        fulfillment.setEnumFulfillmentInstructionType(EnumFulfillmentInstructionsType.DIGITAL);
        fulfillment.setType(EnumFulfillmentType.Delivery);
        fulfillment.setTracking_number("1111111111");
        //fulfillment.setActivityDate();

        fulfillmentRepository.save(fulfillment);*/




        lineItemList = lineItemList.stream().filter(e->e.getLineType().equals(lineType)).collect(Collectors.toList());



/*

        lineItemRepository.saveAll(list.stream().map(e->{

            e.setFullfullmentStatus(EnumProductBookingFullfullmentStatus.Ordered);

            return e;
        }).collect(Collectors.toList()));
*/



/*
        Map<String,List<Traveler>> travelers = travelerRepository.findAllByBooking(reservation.getId())
                .stream().collect(Collectors.groupingBy(e->e.getReferSku()));

*/

        List<LineItem> finalLineItemList = lineItemList;
        IntStream.range(0,lineItemList.size()).forEach(i-> {
            LineItem lineItem = finalLineItemList.get(i);



            Optional<Product> optionalProduct = productRepository.findById(lineItem.getProduct());
            Product product = optionalProduct.get();




            List<Traveler> traveler = lineItem.getTravelers() ;//travelers.getOrDefault(lineItem.getReferTraveler(),new ArrayList<>());
            Assert.notEmpty(traveler, "游客数量不容许为0");

            if(lineItem.getDeliveryFormats().equals(EnumDeliveryFormats.虚拟卡)){

                System.out.println("订单数量和 提供的游客数量不相符"+ traveler.size());


                if(lineItem.getQuantity() != traveler.size()){

                    logger.error("订单数量{},和 供的游客数量{} 不相符 " ,lineItem.getQuantity(),traveler.size());
                    System.out.println("订单数量和 提供的游客数量不相符");
                    throw new BookNotFoundException(Enumfailures.resource_not_found,"订单数和 游客数不相符");
                }
                traveler.stream().forEach(tra->{

                    Cardholder cardholder = new Cardholder();
                    //cardholder.setCompany(pass.getId());
                    cardholder.setName(tra.getName());
                    cardholder.setIdentity(tra.getIdNo());




                    CompoentRightAssigtToTargeVo compoentRightAssigtToTargeVo = new CompoentRightAssigtToTargeVo();


                    logger.error("订单数量和 新建一个年卡 "+reservation.getPlatform());


                    Pass pass = passService.create_virtual(lineItem,cardholder,10);
                    passService.active(pass);



                    VoucherTicket fulfilled_item = fulfillService.create(reservation,
                            product,
                            voucherType,
                            pass.getId());


                    compoentRightAssigtToTargeVo.setPass(pass);
                    compoentRightAssigtToTargeVo.setBooking(reservation);
                    compoentRightAssigtToTargeVo.setReferenceId(pass.getId());
                    compoentRightAssigtToTargeVo.setReferenceType(EnumRelatedObjectType.pass);
                    compoentRightAssigtToTargeVo.setReference(pass.getCode());



                    if(user.getPlatform().equals(EnumPlatform.TS)){
/*                        Pass pass = passService.create_virtual(lineItem,cardholder,10);
                        compoentRightAssigtToTargeVo.setPass(pass);*/

                    }

                    if(reservation.getPlatform().equals(EnumPlatform.DERECT)){
                        logger.error("订单数量和 新建一个年卡 ");
                        //   Pass pass = passService.create_virtual(lineItem,cardholder,user.getUser().getId(),10);
                        if(user.getUser().isRealNameVerified()){
                            if(pass.getCardholder().getName().equals(user.getUser().getRealName())
                                    && pass.getCardholder().getIdentity().equals(user.getUser().getIdCard())){
                                passService.link(pass, user.getUser());
                                //  throw new BookNotFoundException(Enumfailures.not_found,"该卡已激活，您非该卡持卡人");

                            }

                        }
                    }




                    Long limit = 2l;

                    List<Component> componentRightList = componentRepository.findAllByProduct(lineItem.getProduct());

/*                    if(true){
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

                      //  throw new RuntimeException();
                    }*/


                    componentRightService.assingtoComponent(compoentRightAssigtToTargeVo,
                            componentRightList.stream().map(e->{
                                Triplet<Component,LineItem,EnumComponentVoucherType> componentVounchLineItemPair =
                                        Triplet.with(e,lineItem,e.getType());
                                return componentVounchLineItemPair;
                            }).collect(Collectors.toList()),
                            limit);






                    if(tra.getTel_home()!= null){

                        try {


                            CityPassBooking cityPassBooking = new CityPassBooking();
                            cityPassBooking.set姓名(cardholder.getName());
                            cityPassBooking.set码号(fulfilled_item.getCode());
                            cityPassBooking.setSKU名称(lineItem.getTitle());
                            cityPassBooking.set购买数量("1");

                            cityPassBooking.set开始日期(pass.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
                            cityPassBooking.set过期日期(pass.getExpiringDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));


                            String urllink = linkTo(methodOn(EticketController.class).dig_ticket(cryptoService.hashids_encode(fulfilled_item.getId()),null)).withSelfRel().getHref();
                            cityPassBooking.setEticket(urllink);


                            Optional<String> contact =  contactService.find_phone(EnumRelatedObjectType.product,lineItem.getProduct());
                            if(contact.isPresent()){
                                cityPassBooking.set客服电话( contact.get());

                            }



                            String greetings = String.format(
                                    "尊敬的%s，凭此电子票（门票码号%s）扫描（或告知门票码号）验证成功即可进入，%s%s张；有效期%s至%s，客服电话：%s",
                                    cityPassBooking.get姓名(),
                                    cityPassBooking.get码号(),
                                    cityPassBooking.getSKU名称(),
                                    cityPassBooking.get购买数量(),
                                    cityPassBooking.get开始日期(),
                                    cityPassBooking.get过期日期(),cityPassBooking.get客服电话());

                         //   smsService.singleSend(greetings,tra.getTel_home());

                            RabbitMessage message = new RabbitMessage();
                            message.setType(EnumRabbitMessageType.BUY_CITY_HERO_NOTIFICATION);
                            message.setCityPassBooking(cityPassBooking);
                            message.setPhone(tra.getTel_home());

                            smsService.send_(message);

                        }catch (Exception e){
                            e.printStackTrace();

                            logger.error("短信发送失败 {} ",e.getMessage());
                        }
                    }


                });
            }
            if(lineItem.getDeliveryFormats().equals(EnumDeliveryFormats.激活码)){
                List<IntelliCode> codes =  IntStream.range(0,lineItem.getQuantity()).mapToObj(e-> {
                    IntelliCode intelliCode = new IntelliCode();
                    intelliCode.setCode(idGenService.nextId("jh_"));
                    intelliCode.setMax_redemptions(1);
                    intelliCode.setActive(true);
                    intelliCode.setLineItem(lineItem.getId());
                    String generatedString = RandomStringUtils.randomNumeric(4);
                    intelliCode.setPin(generatedString);
                    intelliCode.setStatus(EnumIntelliCodeStatus.not_redeemed);
                    intelliCode.setBooking(lineItem.getBooking());

                    return intelliCode;
                }).collect(Collectors.toList());


                intelliCodeRepository.saveAll(codes);
                lineItem.setDeliveryFormats(EnumDeliveryFormats.激活码);


            }








          //  bookingProduct.setCVC(idGenService.nextId(""));
            lineItem.setStatus(EnumLineItemStatus.SentToBilling);
            lineItemRepository.save(lineItem);


        });


       // reservation.setStatus(EnumBookingStatus.Fulfilled);
        reservationRepository.save(reservation);

    }

    public void show(LineItemResp lineItemResp, LineItem lineItem) {


        if(!lineItem.getLineType().equals(lineType)){
            return;
        }

        List<IntelliCode> intelliCodes = intelliCodeRepository.findByLineItem(lineItem.getId());

        lineItemResp.setIntelliCodes(intelliCodes.stream().map(e->{

            return e;
        }).collect(Collectors.toList()));

    }














    public EntityModel validate_by_idnumber(RedemBycodePojo.Code code, UserVo userVo) {



        List<Cardholder> cardholderList = cardholderRepository.findByIdentity(code.getCode());


        if(cardholderList.isEmpty()) {

            return null;
        }

        Cardholder cardholder = cardholderList.get(0);



        Pass pass = cardholder.getPass();



        List<ComponentVounch> componentVounchList = validatorService.check(userVo.getUser_id(), userVo.getSupplier().getId(),pass.getCode());



        logger.error("和验证 可用核销的权益"+componentVounchList);


        if(pass.getBooking()!= null){

            bookingBaseService.checkin(pass.getBookingLine());
        }




        RedemptionTryResp resp = new RedemptionTryResp();

        resp.setType_text(EnumRedeamptionType.PASS.toString());


        componentRightService.sentEntries(pass.getCode(),resp,componentVounchList);
        resp.setCrypto_code(pass.getCode());


        EntityModel redemptionEntryEntityModel =  EntityModel.of(resp);



        redemptionEntryEntityModel.add(linkTo(methodOn(RedemptionRestController.class).redeemVonchor(null)).withRel("redeemByCryptoCode"));
        redemptionEntryEntityModel.add(linkTo(methodOn(RedemptionRestController.class).redeemVonchorByCryptoCode(null)).withRel("redeem"));

        return redemptionEntryEntityModel;



    }











    public RedemptionTryResp validateByDevice(Pass pass, Device device) {




        List<ComponentVounch> componentVounchList = validatorService.check(device.getId(),pass.getCode());

        logger.error("和验证 可用核销的权益"+componentVounchList);


        if(pass.getBooking()!= null){

            bookingBaseService.checkin(pass.getBookingLine());
        }


        RedemptionTryResp resp = new RedemptionTryResp();

        resp.setType_text(EnumRedeamptionType.PASS.toString());


        componentRightService.sentEntries(pass.getCode(),resp,componentVounchList);
        resp.setCrypto_code(pass.getCode());


        return resp;



    }



    public EntityModel validate(VoucherTicket voucherTicket, UserVo userVo) {

        logger.debug("验证 type:{}",voucherTicket.getType());

        if(!voucherTicket.getType().equals(voucherType)) {
            return null;

        }



        Optional<Pass> cardholderList = passRepository.findById(voucherTicket.getRelateId());


        if(cardholderList.isEmpty()) {
            throw  new BookNotFoundException(Enumfailures.resource_not_found,"找不到门票"+voucherTicket.getRelateId());
        }



        Pass voucher =cardholderList.get();
/*
        List<Cardholder> cardholderList = cardholderRepository.findByIdentity(code.getCode());

        if(cardholderList.isEmpty()) {
            throw  new BookNotFoundException(Enumfailures.resource_not_found,"找不到门票"+code.getCode());
        }
        */

      //  Pass voucher =cardholderList.get(0).getPass();
        if(!voucher.isActive()){
            throw new BookNotFoundException(Enumfailures.voucher_not_active,"该券状态不活跃");
        }


        List<ComponentVounch> componentVounchList = validatorService.check(userVo.getUser_id(), userVo.getSupplier().getId(),voucher.getCode());



        if(voucher.getBooking()!= null){

            bookingBaseService.checkin(voucher.getBookingLine());
        }




        RedemptionTryResp resp = new RedemptionTryResp();

        resp.setType_text(EnumRedeamptionType.PASS.toString());


        RedemptionTryResp.PhotoId photoId = new RedemptionTryResp.PhotoId();

        photoId.setRealname(true);
        photoId.setFaceImage(fileStorageService.loadDocumentWithDefault(EnumDocumentType.card_cover,voucher.getCode()));
        photoId.setDateOfbirth(voucher.getCardholder().getIdentity());

        photoId.setSelfie(fileStorageService.loadDocumentWithDefault(EnumDocumentType.card_cover,voucher.getCode()));

        photoId.setDocument_front(fileStorageService.loadDocumentWithDefault(EnumDocumentType.card_cover,voucher.getCode()));

        photoId.setIdentity(voucher.getCardholder().getIdentity());

        photoId.setName(voucher.getCardholder().getName());



        resp.setPhotoId(photoId);

/*
        "documents": [
        {
            "status": "success",
                "attempt": 1,
                "images": {
            "original_front": "https://example.plaid.com/verifications/idv_52xR9LKo77r1Np/documents/1/original_front.jpeg",
                    "original_back": "https://example.plaid.com/verifications/idv_52xR9LKo77r1Np/documents/1/original_back.jpeg",
                    "cropped_front": "https://example.plaid.com/verifications/idv_52xR9LKo77r1Np/documents/1/cropped_front.jpeg",
                    "cropped_back": "https://example.plaid.com/verifications/idv_52xR9LKo77r1Np/documents/1/cropped_back.jpeg",
                    "face": "https://example.plaid.com/verifications/idv_52xR9LKo77r1Np/documents/1/face.jpeg"
        },*/


        componentRightService.sentEntries(voucher.getCode(), resp,componentVounchList);

        resp.setCrypto_code(cryptoService.encode(voucherTicket.getCode()));

        EntityModel redemptionEntryEntityModel =  EntityModel.of(resp);


   /*     String jwt = jwtUtils.generateJwtToken(gson.toJson(codeWithLatLngVo));

        resp.setCrypto_code(jwt);*/

        redemptionEntryEntityModel.add(linkTo(methodOn(RedemptionRestController.class).redeemVonchor(null)).withRel("redeemByCryptoCode"));
        redemptionEntryEntityModel.add(linkTo(methodOn(RedemptionRestController.class).redeemVonchorByCryptoCode(null)).withRel("redeem"));

        return redemptionEntryEntityModel;



    }





    public EntityModel redeem(VoucherTicket voucherTicket, UserVo userVo) {


        Optional<User> user = userRepository.findById(userVo.getUser_id());


        if(!voucherTicket.getType().equals(voucherType)) {

            return null;
        }
        Optional<Pass> optionalVoucher = passRepository.findById(voucherTicket.getRelateId());





        if(optionalVoucher.isEmpty()) {
            throw  new BookNotFoundException(Enumfailures.resource_not_found,"找不到门票"+voucherTicket.getRelateId());
        }
        Pass voucher =optionalVoucher.get();


        if(voucher.getExpiringDate().isBefore(LocalDateTime.now())){
            throw new ExistException(Enumfailures.invalid_voucher,"该PASS已过期");
        }


        if(!voucher.isActive()){
            throw new BookNotFoundException(Enumfailures.voucher_not_active,"该PASS未激活");
        }



        List<Validator_> validator_s = validatorRepository.findAllByTypeAndUser(EnumValidatorType.特定的人员,userVo.getUser_id());


        if(validator_s.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"职工得 核销分配 对象 为空，请添加"+userVo.getPhone());
        }

        List<Long> triplet来自设备 =
                validator_s.stream().map(e->e.getComponentRightId()).collect(Collectors.toList());


        List<ComponentVounch> components = componentVounchRepository.findAllByReference(voucher.getCode());

        if(components.size() ==0){
            throw new BookNotFoundException(Enumfailures.not_found,"该券 无可核销得 权益"+voucher.getCode());
        }


        List<ComponentVounch> componentVounchList = components.stream()  // TODO 找到了这里的 权限
                .filter(e->{
                    return triplet来自设备.contains(e.getComponentRight());
                }).collect(Collectors.toList());

        componentVounchList =  componentVounchList.stream()
                .filter(e->!(e.getStatus().equals(EnumComponentVoucherStatus.AlreadyRedeemed)))
                .map(e->{
                    System.out.println("存在的"+e.toString());
                    return e;
                })
         //       .filter(e->triplet来自设备.contains(e.getComponentRight()))
                .collect(Collectors.toList());


        System.out.println("测试测试  "+componentVounchList.size());
        if(componentVounchList.size() == 0){
            throw new BookNotFoundException(Enumfailures.not_found,"该券 已经 无 剩余可核销的 权益"+voucher.getCode());

        }

        ValidatedByTypeVo verifier核销人员 = new ValidatedByTypeVo();
        verifier核销人员.setValidatorType(EnumValidatorType.特定的人员);

        verifier核销人员.setUser(user.get());

        Cardholder traveler用户 = cardholderRepository.findByPass(voucher).get();

        RedemptionForObjectVo redemptionForObjectVo = new RedemptionForObjectVo();
        redemptionForObjectVo.setRelatedObjectId(voucher.getId());
        redemptionForObjectVo.setRelatedObjectType(EnumRelatedObjectType.pass);
        redemptionForObjectVo.setRelatedObjectCode(voucher.getCode());
        redemptionForObjectVo.setRelatedObject_subType(voucher.getType().name());
        redemptionForObjectVo.setLable(voucher.getLabel());

        RedemptionForCustomerVo redemptionForCustomerVo = new RedemptionForCustomerVo();

        redemptionForCustomerVo.setId(traveler用户.getId());
        redemptionForCustomerVo.setRealName(traveler用户.getName());
        redemptionForCustomerVo.setCode(traveler用户.getCode());


        List<RightRedemptionEntry> redemptionEntryList = redemptionService.redeemRight(redemptionForObjectVo,verifier核销人员,redemptionForCustomerVo,componentVounchList);

        redemptionEntryList = rightRedemptionEntryRepository.saveAll(redemptionEntryList);




        List<ComponentVounch> component_for_update_voucher = componentVounchRepository.findAllByReference(voucher.getCode());

/*        if(component_for_update_voucher
                .stream().filter(e->e.getStatus().equals(EnumComponentVoucherStatus.PartialyRedeemed))
                .findAny().isPresent()){
            voucher.setStatus(EnumVoucherStatus.PartialyRedeemed);
        };
        if(component_for_update_voucher
                .stream().filter(e->!e.getStatus().equals(EnumComponentVoucherStatus.AlreadyRedeemed))
                .findAny().isEmpty()){
            voucher.setStatus(EnumVoucherStatus.Redeemed);
        };

        voucherTicketRepository.save(voucher);*/




        Optional<Reservation> reservationOptional = reservationRepository.findById(voucher.getBooking());
        Reservation reservation = reservationOptional.get();

        if(reservationOptional.get().getPlatform().equals(EnumPlatform.TS)){
            LtReqTs验证核销通知.ToLtReqTs验证核销通知 ltReqTs验证核销通知 = new LtReqTs验证核销通知.ToLtReqTs验证核销通知();

            ltReqTs验证核销通知.setAmount(4);//:当前使用数量
            ltReqTs验证核销通知.setAmount_used(1);//:累计使用数量(包含本次)
            ltReqTs验证核销通知.setAnother_orders_id(reservation.getTrackingId()); //:本平台订单ID（天时同城）
            ltReqTs验证核销通知.setMy_orders_id(reservation.getCode()); //:第三方订单ID
            List<VoucherTicket> fulfilled_items = fulfillService.find_(reservation);

            ltReqTs验证核销通知.setCodes(fulfilled_items.stream().map(e->e.getCode()).collect(Collectors.joining(","))); //:使用码号,多个','分割
            TsRespLt验证核销通知 ltReqTs退单审核通知 = ltToTsService.ltReqTs验证核销通知(ltReqTs验证核销通知);

        }

        EntityModel redemptionEntryEntityModel =  EntityModel.of(Map.of("dd",redemptionEntryList));


        return redemptionEntryEntityModel;



    }


    public void refund(Reservation reservation, List<LineItem> lineItemList, PlatRefundVo platRefundVo) {

        lineItemList = lineItemList.stream().filter(e->e.getLineType().equals(lineType)).collect(Collectors.toList());
        if(lineItemList.size()==0){
            return;
        }
        if(lineItemList.stream()
                .filter(e->e.isCheckin()!= null)
                .filter(e->e.isCheckin()).findAny().isPresent()){


            String info = lineItemList.stream().filter(e->e.isCheckin() != null && e.isCheckin()).map(e->{

                String lable = String.format("%s-%s-%s",e.getId(),e.getTitle(),e.getBooking());

                return lable;
            }).collect(Collectors.joining(","));

            throw new BookNotFoundException(Enumfailures.resource_not_found,"存在已核销的，不能退款"+info);
        }

    }


}
