package com.lt.dom.serviceOtc.product;


import com.google.protobuf.InvalidProtocolBufferException;
import com.lt.dom.OctResp.ProductResp;
import com.lt.dom.OctResp.RedemptionTryResp;
import com.lt.dom.config.LtConfig;
import com.lt.dom.controllerOct.RedemptionRestController;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.RedemBycodePojo;
import com.lt.dom.otcReq.VoucherTicketResp;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.util.SecurityUtils;
import com.lt.dom.vo.*;
import org.hashids.Hashids;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.lt.dom.serviceOtc.JsonParse.GSON;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BusTicketServiceImpl {
    Logger logger = LoggerFactory.getLogger(CityPassServiceImpl.class);


    @Autowired
    private LtConfig ltConfig;

    @Autowired
    private AvailabilityServiceImpl availabilityService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private VoucherTicketRepository voucherTicketRepository;
    @Autowired
    private ComponentRightServiceImpl componentRightService;

    @Autowired
    private IdGenServiceImpl idGenService;


    @Autowired
    private FileStorageServiceImpl fileStorageService;


    @Autowired
    private CardholderRepository cardholderRepository;

    @Autowired
    private RedemptionServiceImpl redemptionService;

    @Autowired
    private RightRedemptionEntryRepository rightRedemptionEntryRepository;



    @Autowired
    private ValidatorServiceImpl validatorService;



    @Autowired
    private BookingBaseServiceImpl bookingBaseService;


    @Autowired
    private PassRepository passRepository;






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

        if(product.getType().equals(EnumProductType.BusTicket)){

            productResp.setOptions(Arrays.asList());


        }
    }


    private static final  EnumLineType type = EnumLineType.BUS_TICKET;


    public void booking(LineItem lineItem, Product product) {
        if(!product.getType().equals(EnumProductType.BusTicket)){

            return;
        }
        lineItem.setFulfillmentInstructionsType(EnumFulfillmentInstructionsType.DIGITAL);
        lineItem.setLineType(type);

        lineItem.setFulfillment_behavior(EnumFulfillment_behavior.Create_voucher);
    }





    public void fullfil(Reservation reservation, List<LineItem> lineItemList, User payer) {


        lineItemList = lineItemList.stream().filter(e->e.getLineType().equals(type)).collect(Collectors.toList());


        lineItemList.stream().forEach(lineItem -> {




            Optional<Product> productOptional = productRepository.findById(lineItem.getProduct());

            Product product = productOptional.get();



            List<Component> componentRightList = componentRepository.findAllByProduct(lineItem.getProduct());


                    List<VoucherTicket> vouchers = LongStream.range(0, lineItem.getQuantity()).boxed().map(x -> {
                        VoucherTicket voucher = new VoucherTicket();

                        voucher.setBooking(reservation.getId());
                        voucher.setUser(payer.getId());




                        voucher.setLable("同城小易-观光车电子票"+product.getName());
                      //  voucher.setEventDate(LocalDate.now());


                        LocalDateTime ex = availabilityService.getExpiry_datetime(product);
                        voucher.setExpiry_datetime(ex);
                        voucher.setExpiration_date(ex);

                        VoucherTicket.BusTicket busTicket = new VoucherTicket.BusTicket();
                        busTicket.setSku(lineItem.getProduct());
                      voucher.setData_json(GSON.toJson(busTicket));

            /*            voucher.setVenue(theatre.getId());
                        voucher.setVenueName(theatre.getName());
                        voucher.setSection(zone.getName());
                        voucher.setRow(11);
                        voucher.setSeat(11);*/


                        voucher.setCode(idGenService.ticketCode());
                        voucher.setType(EnumVoucherType.TICKET_BUS);

                        voucher.setStatus(EnumVoucherStatus.Created);
                        voucher.setPublished(false);

                        voucher.setActive(false);

                        return voucher;
                    }).collect(Collectors.toList());


            Long limit = 1l;

                    vouchers = voucherTicketRepository.saveAll(vouchers);
                    vouchers.stream().forEach(e->{


                        CompoentRightAssigtToTargeVo compoentRightAssigtToTargeVo = new CompoentRightAssigtToTargeVo();


                        logger.error("订单数量和 新建一个年卡 "+reservation.getPlatform());
                        compoentRightAssigtToTargeVo.setBooking(reservation);

                        componentRightService.assingtoComponent(compoentRightAssigtToTargeVo,
                                componentRightList.stream().map(ee->{
                                    Triplet<Component,LineItem,EnumComponentVoucherType> componentVounchLineItemPair =
                                            Triplet.with(ee,lineItem,ee.getType());
                                    return componentVounchLineItemPair;
                                }).collect(Collectors.toList()),
                                limit);
                      //  componentRightService.assingtoTicket(e,componentRightList, limit);

                    });


                }

        );


    }

    private static final EnumVoucherType voucherType = EnumVoucherType.TICKET_BUS;

    public void ticketShow(VoucherTicketResp voucherTicketResp, VoucherTicket voucherTicket) {
        if(!voucherTicket.getType().equals(voucherType)) {
            return;
        }

        VoucherTicketResp.BusTicket venue1 = new VoucherTicketResp.BusTicket();
        venue1.setName("同城小易逛逛车");

        EntityModel entityModel = EntityModel.of(venue1);
        voucherTicketResp.setVenue(entityModel);

    }

    public void listShow(VoucherTicketResp voucherTicketResp, VoucherTicket voucherTicket) {

        if(!voucherTicket.getType().equals(voucherType)) {

            return;
        }
        voucherTicketResp.setText("这是车票");
        voucherTicketResp.setData(GSON.fromJson(voucherTicket.getData_json(),VoucherTicket.BusTicket.class));

    }



    public EntityModel validate(RedemBycodePojo.Code code, Device device) {



        if(!code.getCode().startsWith("pass_")) {

            return null;
        }





        Optional<Pass> passOptional = passRepository.findByCode(code.getCode());
        if(passOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到主人卡");
        }



        Pass pass = passOptional.get();


        List<ComponentVounch> componentVounchList = validatorService.check(device.getId(),pass.getCode());




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






    public EntityModel redeem(CodeWithLatLngVo code, Device device) {


        Optional<Pass> optionalVoucher = passRepository.findByCode(code.getC());





        if(optionalVoucher.isEmpty()) {
            throw  new BookNotFoundException(Enumfailures.resource_not_found,"找不到门票"+code.getC());
        }
        Pass voucher =optionalVoucher.get();


        if(voucher.getExpiringDate().isBefore(LocalDateTime.now())){
            throw new ExistException(Enumfailures.invalid_voucher,"该PASS已过期");
        }


        if(!voucher.isActive()){
            throw new BookNotFoundException(Enumfailures.voucher_not_active,"该PASS未激活");
        }

        List<ComponentVounch> componentVounchList = validatorService.check(device.getId(),voucher.getCode() );



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
        verifier核销人员.setValidatorType(EnumValidatorType.特定机器);

        verifier核销人员.setDevice(device);

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






        EntityModel redemptionEntryEntityModel =  EntityModel.of(Map.of("dd",redemptionEntryList));


        return redemptionEntryEntityModel;



    }

}
