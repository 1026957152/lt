package com.lt.dom.serviceOtc.product;


import com.lt.dom.OctResp.*;
import com.lt.dom.controllerOct.RedemptionRestController;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.RedemBycodePojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.ComponentRightServiceImpl;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.IdGenServiceImpl;
import com.lt.dom.serviceOtc.PassServiceImpl;
import com.lt.dom.vo.CompoentRightAssigtToTargeVo;
import com.lt.dom.vo.PlatUserVo;
import com.lt.dom.vo.UserVo;
import org.apache.commons.lang.RandomStringUtils;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
    private LineItemRepository lineItemRepository;

    @Autowired
    private ReservationRepository reservationRepository;



    @Autowired
    private CardholderRepository cardholderRepository
            ;

    @Autowired
    private PassRepository passRepository
            ;


    @Autowired
    private ValidatorRepository validatorRepository
            ;


    @Autowired
    private FileStorageServiceImpl fileStorageService
            ;


    @Autowired
    private FulfillmentRepository fulfillmentRepository;
    @Autowired
    private IntelliCodeRepository intelliCodeRepository;

    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private ComponentRightRepository componentRightRepository;

    @Autowired
    private IdGenServiceImpl idGenService;


    @Autowired
    private ComponentVounchRepository componentVounchRepository;




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
    private static final EnumVoucherType voucherType = EnumVoucherType.Multi_Ticket;


    public void booking(LineItem lineItem, Product product) {
        if(!product.getType().equals(productType)){
            return;
        }

        lineItem.setLineType(lineType);
        lineItem.setStatus(EnumLineItemStatus.Executing);
        lineItem.setBillingTriggerRule(EnumBillingTriggerRule.WithoutFulfillment);


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



            List<Component> componentRightList = componentRepository.findAllByProduct(lineItem.getProduct());




            List<Traveler> traveler = lineItem.getTravelers() ;//travelers.getOrDefault(lineItem.getReferTraveler(),new ArrayList<>());
            Assert.notEmpty(traveler, "游客数量不容许为0");

            if(lineItem.getDeliveryFormats().equals(EnumDeliveryFormats.虚拟卡)){

                System.out.println("订单数量和 提供的游客数量不相符"+ traveler.size());


                if(lineItem.getQuantity() != traveler.size()){

                    logger.error("订单数量和 提供的游客数量不相符");
                    System.out.println("订单数量和 提供的游客数量不相符");
                    throw new BookNotFoundException(Enumfailures.resource_not_found,"订单数和 游客数不相符");
                }
                traveler.stream().forEach(tra->{

                    Cardholder cardholder = new Cardholder();
                    //cardholder.setCompany(pass.getId());
                    cardholder.setName(tra.getName());
                    cardholder.setIdentity(tra.getIdNo());
                    CompoentRightAssigtToTargeVo compoentRightAssigtToTargeVo = new CompoentRightAssigtToTargeVo();

                    if(user.getPlatform().equals(EnumPlatform.TS)){
                        Pass pass = passService.create_virtual(lineItem,cardholder,10);
                        compoentRightAssigtToTargeVo.setPass(pass);

                    }
                    logger.error("订单数量和 新建一个年卡 "+reservation.getPlatform());
                    if(reservation.getPlatform().equals(EnumPlatform.LT)){


                        logger.error("订单数量和 新建一个年卡 ");
                        Pass pass = passService.create_virtual(lineItem,cardholder,user.getUser().getId(),10);
                        compoentRightAssigtToTargeVo.setPass(pass);
                    }
                    Long limit = 2l;
                    componentRightService.assingtoTicket(compoentRightAssigtToTargeVo,componentRightList,limit);


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


        reservation.setStatus(EnumBookingStatus.Fulfilled);
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






















    public EntityModel validate(RedemBycodePojo.Code code, UserVo userVo) {



        if(!code.getCode().startsWith("pass_")) {

            return null;
        }

        Optional<Pass> cardholderList = passRepository.findByCode(code.getCode());

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
/*
        if(voucher.getStatus().equals(EnumVoucherStatus.Redeemed)){
            throw new ExistException(Enumfailures.invalid_voucher,"该券已核销,无法再次核销");
        }
*/



        List<Validator_> validator_s = validatorRepository.findAllByTypeAndUser(EnumValidatorType.特定的人员,userVo.getUser_id());



        if(validator_s.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"职工得 核销分配 对象 为空，请添加"+userVo.getPhone());
        }
        List<Long> triplet来自设备 =
                validator_s.stream().map(e->e.getComponentRightId()).collect(Collectors.toList());


        logger.error("和验证 可用核销的权益"+triplet来自设备);
        List<ComponentVounch> components = componentVounchRepository.findAllByReference(voucher.getCode());

        if(components.size() ==0){
            throw new BookNotFoundException(Enumfailures.not_found,"该券 无可核销得 权益"+voucher.getCode());
        }


        List<ComponentVounch> componentVounchList = components.stream()  // TODO 找到了这里的 权限
                //     .filter(e-> triplet来自设备.contains(e.getComponentRight()))
                .map(e->{

                    Optional<ComponentRight> componentRight = componentRightRepository.findById(e.getComponentRight());
                    e.setName(componentRight.get().getName());
                    return e;
                }).collect(Collectors.toList());






        RedemptionTryResp resp = new RedemptionTryResp();

        resp.setType_text(EnumRedeamptionType.PASS.toString());


        RedemptionTryResp.PhotoId photoId = new RedemptionTryResp.PhotoId();

        photoId.setRealname(true);

        photoId.setFaceImage(fileStorageService.loadDocumentWithDefault(EnumDocumentType.card_cover,voucher.getCode()));

        photoId.setDateOfbirth(voucher.getCardholder().getName());

        photoId.setSelfie(fileStorageService.loadDocumentWithDefault(EnumDocumentType.card_cover,voucher.getCode()));

        photoId.setDocument_front(fileStorageService.loadDocumentWithDefault(EnumDocumentType.card_cover,voucher.getCode()));

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
        resp.setEntries(componentVounchList.stream()
                .filter(e->!(e.getStatus().equals(EnumComponentVoucherStatus.AlreadyRedeemed)))
                .map(e->{
                    RedemptionTryResp.RedemptionEntryResp redemptionEntryResp = new RedemptionTryResp.RedemptionEntryResp();



                    redemptionEntryResp.setLable(e.getName());
                    redemptionEntryResp.setLimit(e.getLimit().intValue());
                    redemptionEntryResp.setRemaining(e.getTry_().intValue());

                    //    redemptionEntryResp.setRemaining(Long.valueOf(e.getLimit()-e.getRedeemed_quantity()).intValue());
                    redemptionEntryResp.setCheck_in(triplet来自设备.contains(e.getComponentRight()));
                    return redemptionEntryResp;

                })
                .sorted(Comparator.comparing(RedemptionTryResp.RedemptionEntryResp::isCheck_in))
                .collect(Collectors.toList()));



        resp.setCrypto_code(voucher.getCode());

        EntityModel redemptionEntryEntityModel =  EntityModel.of(resp);
   /*     String jwt = jwtUtils.generateJwtToken(gson.toJson(codeWithLatLngVo));

        resp.setCrypto_code(jwt);*/

        redemptionEntryEntityModel.add(linkTo(methodOn(RedemptionRestController.class).redeemVonchor(null)).withRel("redeemByCryptoCode"));
        redemptionEntryEntityModel.add(linkTo(methodOn(RedemptionRestController.class).redeemVonchorByCryptoCode(null)).withRel("redeem"));

        return redemptionEntryEntityModel;



    }

}
