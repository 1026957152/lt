package com.lt.dom.serviceOtc.product;


import com.lt.dom.OctResp.*;
import com.lt.dom.controllerOct.BookingRestController;
import com.lt.dom.controllerOct.RedemptionRestController;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.LocationResp;
import com.lt.dom.otcReq.VoucherTicketResp;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.vo.*;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.lt.dom.serviceOtc.JsonParse.GSON;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class MultiTicketServiceImpl {
    Logger logger = LoggerFactory.getLogger(MultiTicketServiceImpl.class);



    @Autowired
    private MovieProductRepository movieProductRepository;
    @Autowired
    private ZoneRepository zoneRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ComponentVounchRepository componentVounchRepository;




    @Autowired
    private ValidatorServiceImpl validatorService;

    @Autowired
    private ValidatorRepository validatorRepository;
    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ComponentRightRepository componentRightRepository;


    @Autowired
    private RightRedemptionEntryRepository rightRedemptionEntryRepository;
    @Autowired
    private VoucherServiceImpl voucherService;
    @Autowired
    private RedemptionServiceImpl redemptionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoucherTicketRepository voucherTicketRepository;
    @Autowired
    private ComponentRightServiceImpl componentRightService;

    @Autowired
    private AvailabilityServiceImpl availabilityService;
    @Autowired
    private FileStorageServiceImpl fileStorageService;



    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private TheatreRepository theatreRepository;


    private static final EnumProductType productType = EnumProductType.Multi_Ticket;
    private static final EnumLineType lineType = EnumLineType.Multi_Ticket;
    private static final EnumVoucherType voucherType = EnumVoucherType.Multi_Ticket;





    public void create(Product product) {

        if(!product.getType().equals(productType)) {

            return ;
        }

        product.setShippingAddressCollection(EnumShippingAddressCollection.required);
        product.setFree(false);
        product.setStatus(EnumProductStatus.draft);

        product.setPaymentMethods_json(GSON.toJson(Arrays.asList()));
        product.setPrivacyLevel(EnumPrivacyLevel.private_);
        product.setShipping_rates_json(GSON.toJson(Arrays.asList()));
        product.setAvailability_type(EnumAvailabilityType.DATE);
        product.setDeliveryFormats_json(GSON.toJson(Arrays.asList()));
        product.setShippable(false);

        product.setRestriction_passenger_identity_documents_required(false);

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

        if(product.getType().equals(productType)){

            Optional<MovieProduct> passProduct = movieProductRepository.findByProduct(product.getId());
            MovieProduct movieProduct = passProduct.get();

            List<Sku> skuList = passProduct.get().getZonePricings();

            zoneRepository.findAllById(skuList.stream().map(e->e.getZone()).collect(Collectors.toList()));

            productResp.setZones(skuList.stream().map(e->e.getZone()).collect(Collectors.toList()));

            Optional<Movie>  movieOptional = movieRepository.findById(movieProduct.getMovie());
        }



    }

    public void booking(LineItem lineItem, Product product) {
        if(!product.getType().equals(productType)){

            return;
        }
        lineItem.setLineType(lineType);
        lineItem.setFulfillment_behavior(EnumFulfillment_behavior.Create_voucher);

        lineItem.setFulfillmentInstructionsType(EnumFulfillmentInstructionsType.DIGITAL);


    }





    public void fullfil(Reservation reservation, List<LineItem> lineItemList, User payer) {


        lineItemList = lineItemList.stream().filter(e->e.getLineType().equals(lineType)).collect(Collectors.toList());


        lineItemList.stream().forEach(lineItem -> {

/*


            Quartet<Zone,Movie,Theatre,Sku> triplet =  productService.showTime(lineItem.getSku());

            Zone zone = triplet.getValue0();
            Movie movie = triplet.getValue1();
            Theatre theatre = triplet.getValue2();
            Sku sku = triplet.getValue3();*/


            Optional<Product> productOptional = productRepository.findById(lineItem.getProduct());
            Product product = productOptional.get();


           // List<ProductBundle> productBundleList = product.getBundles();

/*
                    List<Component> componentRightList = new ArrayList<>();
                    productBundleList.stream().forEach(e->{
                        List<Component> components = componentRepository.findAllByProduct(e.getBurdle());
                        componentRightList.addAll(components);
                    });
*/


                    List<Component> componentRightList = componentRepository.findAllByProduct(product.getId());


                    List<VoucherTicket> vouchers = LongStream.range(0, lineItem.getQuantity()).boxed().map(x -> {

                        VoucherTicket voucher = new VoucherTicket();
                        voucher.setBooking(reservation.getId());
                        voucher.setUser(payer.getId());

                        voucher.setLable(product.getName());
                        voucher.setCode(idGenService.ticketCode());
                        voucher.setType(voucherType);

                        voucher.setStatus(EnumVoucherStatus.Created);
                        voucher.setPublished(false);

                        voucher.setActive(true);

                        LocalDateTime ex = availabilityService.getExpiry_datetime(product);
                        voucher.setExpiry_datetime(ex);
                        voucher.setExpiration_date(ex);



                        VoucherTicket.MultiTicket showtime = new VoucherTicket.MultiTicket();
                        showtime.setEventDate(LocalDate.now());
                       // showtime.setVenue(theatre.getId());
                     //   showtime.setVenueName(theatre.getName());
                      //  showtime.setSection(zone.getName());
                        showtime.setRow(11);
                        showtime.setSeat(11);
                        voucher.setData_json(GSON.toJson(showtime));






                        return voucher;
                    }).collect(Collectors.toList());



                    vouchers = voucherTicketRepository.saveAll(vouchers);
                    Long limit = 2l;
                   vouchers.stream().forEach(e->{
                      //  componentRightService.assingtoTicket(e,componentRightList,limit);







                       CompoentRightAssigtToTargeVo compoentRightAssigtToTargeVo = new CompoentRightAssigtToTargeVo();


                       logger.error("订单数量和 新建一个年卡 "+reservation.getPlatform());
                       compoentRightAssigtToTargeVo.setBooking(reservation);
                       compoentRightAssigtToTargeVo.setReference(e.getCode());
                       compoentRightAssigtToTargeVo.setReferenceId(e.getId());
                       compoentRightAssigtToTargeVo.setReferenceType(EnumRelatedObjectType.voucher);
                       componentRightService.assingtoComponent(compoentRightAssigtToTargeVo,
                               componentRightList.stream().map(ee->{
                                   Triplet<Component,LineItem,EnumComponentVoucherType> componentVounchLineItemPair =
                                           Triplet.with(ee,lineItem,ee.getType());
                                   return componentVounchLineItemPair;
                               }).collect(Collectors.toList()),
                               limit);

                    });


                }

        );


    }

    public void ticketShow(VoucherTicketResp voucherTicketResp, VoucherTicket voucherTicket) {

        if(!voucherTicket.getType().equals(voucherType)) {


            return;
        }

            VoucherTicket.Showtime showtime =  GSON.fromJson(voucherTicket.getData_json(),VoucherTicket.Showtime.class);

        VoucherTicketResp.ShowtimeResp showtimeResp = VoucherTicketResp.ShowtimeResp.from(showtime);


        Optional<Theatre> theatreOptional = theatreRepository.findById(showtime.getVenue());
        if(theatreOptional.isPresent()){
            Theatre theatre = theatreOptional.get();
            VoucherTicketResp.Venue venue1 = new VoucherTicketResp.Venue();
            venue1.setName(theatre.getName());


            venue1.setLocation(LocationResp.from(theatre.getLocation()));
            EntityModel entityModel = EntityModel.of(venue1);
            entityModel.add(linkTo(methodOn(BookingRestController.class).getReservation(voucherTicket.getBooking())).withSelfRel());

            showtimeResp.setVenue(entityModel);


        }
        EntityModel entityModel = EntityModel.of(showtimeResp);
        voucherTicketResp.setPerformance(entityModel);




    }

    public void listShow(VoucherTicketResp voucherTicketResp, VoucherTicket voucherTicket) {




        if(voucherTicket.getType().equals(voucherType)){

            voucherTicketResp.setText("这是演出门票");
            voucherTicketResp.setData(GSON.fromJson(voucherTicket.getData_json(),VoucherTicket.Showtime.class));

            return;
        }

    }

    public EntityModel validate(VoucherTicket voucher, UserVo userVo) {


        if(!voucher.getType().equals(voucherType)){
            return null;
        }

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


        List<ComponentVounch> components = componentVounchRepository.findAllByReference(voucher.getCode());

        if(components.size() ==0){
            throw new BookNotFoundException(Enumfailures.not_found,"该券 无可核销得 权益"+voucher.getCode());
        }


        List<ComponentVounch> componentVounchList = components.stream()  // TODO 找到了这里的 权限
           //     .filter(e-> triplet来自设备.contains(e.getComponentRight()))
                .filter(e-> e.getType().equals(EnumComponentVoucherType.Right))
                .map(e->{
                        Optional<ComponentRight> componentRight = componentRightRepository.findById(e.getComponentRight());
                        e.setName(componentRight.get().getName());
                    return e;
                }).collect(Collectors.toList());



        List<Product> productList = productRepository.findAllBySupplierId(userVo.getSupplier().getId());

        List<Long> longList_公司允许的 = productList.stream().map(e->e.getId()).collect(Collectors.toList());



        List<ComponentVounch> burdle_vouchers = components.stream()  // TODO 找到了这里的 权限
                .filter(e-> e.getType().equals(EnumComponentVoucherType.Burdle))
                .filter(e->longList_公司允许的.contains(e.getOriginalProduct()))
                .map(e->{
                    return e;
                }).collect(Collectors.toList());


        componentVounchList.addAll(burdle_vouchers);




        RedemptionTryResp resp = new RedemptionTryResp();
        resp.setType_text(EnumRedeamptionType.VOUCHER.toString());


        RedemptionTryResp.PhotoId photoId = new RedemptionTryResp.PhotoId();

        photoId.setRealname(true);

        photoId.setFaceImage(fileStorageService.loadDocumentWithDefault(EnumDocumentType.card_cover,voucher.getCode()));

        photoId.setDateOfbirth("ddd");

        photoId.setSelfie(fileStorageService.loadDocumentWithDefault(EnumDocumentType.card_cover,voucher.getCode()));

        photoId.setDocument_front(fileStorageService.loadDocumentWithDefault(EnumDocumentType.card_cover,voucher.getCode()));

        photoId.setName("ddd");

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



    public EntityModel redeem(CodeWithLatLngVo code, UserVo userVo) {


        logger.debug("核销联票类型");

        Optional<User> user = userRepository.findById(userVo.getUser_id());


        if(!code.getC().startsWith("tike")) {

            return null;
        }
        Optional<VoucherTicket> optionalVoucher = voucherTicketRepository.findByCode(code.getC());


        if(optionalVoucher.isEmpty()) {
            throw  new BookNotFoundException(Enumfailures.resource_not_found,"找不到门票"+code.getC());
        }
        VoucherTicket voucher =optionalVoucher.get();


        if(voucher.getStatus().equals(EnumVoucherStatus.Redeemed)){
            throw new ExistException(Enumfailures.invalid_voucher,"该券已核销,无法再次核销");
        }


        if(!voucher.isActive()){
            throw new BookNotFoundException(Enumfailures.voucher_not_active,"该券状态不活跃");
        }




        List<ComponentVounch> componentVounchList = validatorService.check(userVo.getUser_id(), userVo.getSupplier().getId(),voucher.getCode());




        logger.debug("核销联票类型 多少"+componentVounchList.size());
        ValidatedByTypeVo verifier核销人员 = new ValidatedByTypeVo();
        verifier核销人员.setValidatorType(EnumValidatorType.特定的人员);

        verifier核销人员.setUser(user.get());

        User traveler用户 = userRepository.findById(voucher.getUser()).get();

        RedemptionForObjectVo redemptionForObjectVo = new RedemptionForObjectVo();
        redemptionForObjectVo.setRelatedObjectId(voucher.getId());
        redemptionForObjectVo.setRelatedObjectType(EnumRelatedObjectType.voucher);
        redemptionForObjectVo.setRelatedObjectCode(voucher.getCode());
        redemptionForObjectVo.setRelatedObject_subType(voucher.getType().name());
        redemptionForObjectVo.setRelatedObject_subType(voucher.getLable());


        Optional<RedemptionForCustomerVo> redemptionForCustomerVo = voucherService.Cardholder(voucher);



        Pair<Redemption,List<RightRedemptionEntry>> redemptionListPair= redemptionService.redeemRight(redemptionForObjectVo,verifier核销人员,redemptionForCustomerVo,componentVounchList);

        Redemption redemption = redemptionListPair.getValue0();
        List<RightRedemptionEntry> redemptionEntryList = redemptionListPair.getValue1();

        redemptionEntryList = rightRedemptionEntryRepository.saveAll(redemptionEntryList);




        List<ComponentVounch> component_for_update_voucher = componentVounchRepository.findAllByReference(voucher.getCode());

        if(component_for_update_voucher
                .stream().filter(e->e.getStatus().equals(EnumComponentVoucherStatus.PartialyRedeemed))
                .findAny().isPresent()){
            voucher.setStatus(EnumVoucherStatus.PartiallyRedeemed);
        };
        if(component_for_update_voucher
                .stream().filter(e->!e.getStatus().equals(EnumComponentVoucherStatus.AlreadyRedeemed))
                .findAny().isEmpty()){
            voucher.setStatus(EnumVoucherStatus.Redeemed);
        };

        voucherTicketRepository.save(voucher);



        EntityModel redemptionEntryEntityModel =  EntityModel.of(Map.of("dd",redemptionEntryList));


        return redemptionEntryEntityModel;



    }


}
