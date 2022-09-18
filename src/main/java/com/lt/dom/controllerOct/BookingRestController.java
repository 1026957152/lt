package com.lt.dom.controllerOct;

import com.google.gson.Gson;
import com.lt.dom.OctResp.*;
import com.lt.dom.OctResp.ChargeResp;
import com.lt.dom.domain.CouponTemplatePojoList;
import com.lt.dom.error.*;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.BookingTypeTowhoVo;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.util.HttpUtils;
import com.lt.dom.vo.AvailabilityVO;
import com.lt.dom.vo.ChargeMetadataVo;
import com.lt.dom.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.groupingBy;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//https://caroly.fun/archives/springboot%E6%95%B4%E5%90%88%E5%BE%AE%E4%BF%A1%E6%94%AF%E4%BB%98

@RestController
@RequestMapping("/oct")
public class BookingRestController {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private PaymentServiceImpl paymentService;
    @Autowired
    private PaymentFreeServiceImpl paymentFreeService;


    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private OpenidRepository openidRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BookingServiceImpl bookingService;
    @Autowired
    private TempDocumentRepository tempDocumentRepository;
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private TravelerRepository travelerRepository;
    @Autowired
    private ComponentRightResolveServiceImpl componentRightResolveService;
    @Autowired
    private PricingTypeRepository pricingTypeRepository;


    @Autowired
    private CampaignServiceImpl campaignService;
    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    CampaineAssignToBookingServiceImpl campaineAssignToBookingService;

    @Autowired
    private AvailabilityServiceImpl availabilityService;
    @Autowired
    private AssetServiceImpl assetService;

    @Autowired
    private AliPaymentServiceImpl aliPaymentService;
    @Autowired
    private BookingRuleRepository bookingRuleRepository;
    @Autowired
    private ReferralServiceImpl referralService;



    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/bookings/{RESERVATOIN_ID}", produces = "application/json")
    public ResponseEntity<EntityModel<BookingResp>> getReservation(@PathVariable long RESERVATOIN_ID) {
        Optional<Reservation> optionalProduct = reservationRepository.findById(RESERVATOIN_ID);


        if(optionalProduct.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.not_found,"找不到订单");

        }
        Reservation reservation = optionalProduct.get();

        BookingResp bookingResp = null;
        EntityModel entityModel = null;

        Optional<Product> product = productRepository.findById(optionalProduct.get().getProductId());

            if(optionalProduct.get().getProductType().equals(EnumProductType.Daytour)){

                // reservationResp = new ReservationResp();
                Optional<Tour> optionalTour = tourRepository.findById(product.get().getTypeTo());
                List<Traveler> travelers = travelerRepository.findAllByBooking(reservation.getId());
                List<Document> documents = documentRepository.findAllByRaletiveId(reservation.getId());
                Optional<Asset> assetResp  = assetService.getQrOptional(reservation.getCode());
                BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
                bookingTypeTowhoVo.setProduct(product.get());
                bookingTypeTowhoVo.setTour(optionalTour.get());


                bookingResp = BookingResp.toResp(Quartet.with(reservation,bookingTypeTowhoVo,travelers,documents),assetResp.get());
                bookingResp.setAsset(assetService.getQrEntityModel(reservation.getCode()));



                entityModel= EntityModel.of(bookingResp);
                entityModel.add(linkTo(methodOn(PaymentRestController.class).wxPayRequest(reservation.getId(),null)).withRel("pay_url"));
             //   BookingResp.add(linkTo(methodOn(BookingRestController.class).bulkAddTravelers(reservation.getId(),null)).withRel("add_travelers_url"));
                entityModel.add(linkTo(methodOn(BookingRestController.class).createDocuments(reservation.getId(),new BookingDocumentIdsResp())).withRel("add_documents_url"));




            }
        if(optionalProduct.get().getProductType().equals(EnumProductType.Attraction)){


            List<Traveler> travelers = travelerRepository.findAllByBooking(reservation.getId());
            BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
            bookingTypeTowhoVo.setProduct(product.get());
            bookingTypeTowhoVo.setToWhoTyp(EnumBookingOjbectType.Product);
            bookingResp = BookingResp.toResp(Triplet.with(reservation,bookingTypeTowhoVo,travelers));
            bookingResp.setAsset(assetService.getQrEntityModel(reservation.getCode()));



            entityModel= EntityModel.of(bookingResp);
            entityModel.add(linkTo(methodOn(BookingRestController.class).选择支付方式_并完成发出支付请求(reservation.getId(),null)).withRel("pay_url"));
            //   BookingResp.add(linkTo(methodOn(BookingRestController.class).bulkAddTravelers(reservation.getId(),null)).withRel("add_travelers_url"));
            entityModel.add(linkTo(methodOn(BookingRestController.class).createDocuments(reservation.getId(),new BookingDocumentIdsResp())).withRel("add_documents_url"));




        }
        if(optionalProduct.get().getProductType().equals(EnumProductType.Pass)){

            List<Traveler> travelers = travelerRepository.findAllByBooking(reservation.getId());
            BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
            bookingTypeTowhoVo.setProduct(product.get());
            bookingTypeTowhoVo.setToWhoTyp(EnumBookingOjbectType.Product);
            bookingResp = BookingResp.toResp(Triplet.with(reservation,bookingTypeTowhoVo,travelers));


            bookingResp.setAsset(assetService.getQrEntityModel(reservation.getCode()));

            entityModel= EntityModel.of(bookingResp);
            entityModel.add(linkTo(methodOn(BookingRestController.class).createDocuments(reservation.getId(),new BookingDocumentIdsResp())).withRel("add_documents_url"));




        }


        if(reservation.getValidationStatus().equals(EnumValidationStatus.NewAwaitingValidation)){


            entityModel.add(linkTo(methodOn(BookingRestController.class).offlineValidate(reservation.getId())).withRel("offlineValidate"));

        }

        return ResponseEntity.ok(entityModel);



    }

    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/bookings", produces = "application/json")
    public ResponseEntity<PagedModel> getReservationList(Pageable pageReq , PagedResourcesAssembler<EntityModel<BookingResp>>assembler) {


        Page<Reservation> optionalProduct = reservationRepository.findAll(pageReq);

        Page<EntityModel<BookingResp>> page =  optionalProduct.map(x->{

            if(x.getProductType().equals(EnumProductType.Daytour)){
                Optional<Product> product = productRepository.findById(x.getProductId());
                Optional<Tour> optionalTour = tourRepository.findById(product.get().getTypeTo());
                List<Traveler> travelers = travelerRepository.findAllByBooking(x.getId());
                List<Document> documents = documentRepository.findAllByRaletiveId(x.getId());

                BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
                bookingTypeTowhoVo.setProduct(product.get());
                bookingTypeTowhoVo.setTour(optionalTour.get());

                BookingResp resp = BookingResp.toResp(Quartet.with(x,bookingTypeTowhoVo,travelers,documents));
                EntityModel entityModel = EntityModel.of(resp);
                //entityModel.add(linkTo(methodOn(BookingRestController.class).选择支付方式_并完成发出支付请求(x.getId(),null,null)).withRel("pay_url"));

                entityModel.add(linkTo(methodOn(BookingRestController.class).createDocuments(x.getId(),new BookingDocumentIdsResp())).withRel("add_documents_url"));

                return entityModel;//BookingResp.toResp(Quartet.with(x,bookingTypeTowhoVo,travelers,documents));
            }

            if(x.getProductType().equals(EnumProductType.Attraction)){
                Optional<Product> product = productRepository.findById(x.getProductId());

                List<Traveler> travelers = travelerRepository.findAllByBooking(x.getId());
                List<Document> documents = documentRepository.findAllByRaletiveId(x.getId());

                BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
                bookingTypeTowhoVo.setProduct(product.get());
                bookingTypeTowhoVo.setToWhoTyp(EnumBookingOjbectType.Product);
                BookingResp resp = BookingResp.toResp(Quartet.with(x,bookingTypeTowhoVo,travelers,documents));
                EntityModel entityModel = EntityModel.of(resp);

              //  entityModel.add(linkTo(methodOn(BookingRestController.class).选择支付方式_并完成发出支付请求(x.getId(),null,null)).withRel("pay_url"));
                entityModel.add(linkTo(methodOn(BookingRestController.class).addTravelers(x.getId(),null)).withRel("addTraveler"));
                return entityModel;


            }


                return EntityModel.of(new BookingResp());

        });

        return ResponseEntity.ok(assembler.toModel(page.map(e->{

            return e;
        })));


    }













    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/bookings", produces = "application/json")
    public ResponseEntity createBooking(@RequestBody @Valid BookingPojo pojo) {


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);

        BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
        bookingTypeTowhoVo.setToWhoTyp(pojo.getType());

        if(pojo.getType().equals(EnumBookingOjbectType.Voucher)){
            Optional<Campaign> optionalCampaign = campaignRepository.findById(pojo.getProductId());
            if(optionalCampaign.isEmpty()) {
                throw new BookNotFoundException(pojo.getProductId(),"找不到产品");
            }
            Campaign campaign = optionalCampaign.get();
            if(!campaign.getPay()){
                throw new BookNotFoundException(pojo.getProductId(),"改券不属于支付类型");
            }
            bookingTypeTowhoVo.setCampaign(optionalCampaign.get());
        }
        if(pojo.getType().equals(EnumBookingOjbectType.Product)){
            Optional<Product> optionalProduct = productRepository.findById(pojo.getProductId());
            if(optionalProduct.isEmpty()) {
                throw new BookNotFoundException(pojo.getProductId(),"找不到产品");
            }
            bookingTypeTowhoVo.setProduct(optionalProduct.get());
        }

        Pair<Reservation, BookingTypeTowhoVo> booking = bookingService.booking(bookingTypeTowhoVo,pojo,userVo);

        Reservation reservation = booking.getValue0();



        BookingResp resp = BookingResp.toResp(booking);



/*
        List<EntityModel<PaymentMethodResp>>  paymentOptional = paymentService.getPayment( Arrays.asList(EnumPayChannel.wx,EnumPayChannel.alipay),reservation);
        resp.setPaymentMethods(paymentOptional);*/
        System.out.println("================================="+ resp.toString());
        return ResponseEntity.ok(resp);

    }











    @GetMapping(value = "/price_types/{PRICE_TYPE_ID}/bookings/page", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_createBookingFromPriceType(@PathVariable long PRICE_TYPE_ID) {

        Optional<PricingType> pricingTypeOptional = pricingTypeRepository.findById(PRICE_TYPE_ID);

        if(pricingTypeOptional.isEmpty()){
            throw new BookNotFoundException(PRICE_TYPE_ID,"找不到产品");
        }

        PricingType pricingType = pricingTypeOptional.get();

        Optional<Product> productOptional = productRepository.findById(pricingType.getProductId());

        if(productOptional.isEmpty()){
            throw new BookNotFoundException(PRICE_TYPE_ID,"找不到产品");
        }

        Product product = productOptional.get();

        ProductResp.from(product);



        EntityModel entityModel = EntityModel.of(Map.of(
                "pricingType",PricingTypeResp.from(pricingType),

                "product",ProductResp.from(product)
             //   "status_list", EnumTourBookingStatus_.from()
        ));


        entityModel.add(linkTo(methodOn(BookingRestController.class).createBookingFromPriceType(pricingType.getId(),null)).withRel("createBooking"));
        entityModel.add(linkTo(methodOn(BookingRestController.class).addTravelers(pricingType.getId(),null)).withRel("addTraveler"));

        return entityModel;
    }






    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/price_types/{PRICE_TYPE_ID}/bookings", produces = "application/json")
    public ResponseEntity createBookingFromPriceType(@PathVariable long PRICE_TYPE_ID,@RequestBody @Valid BookingPojo pojo) {


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);

        BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
        bookingTypeTowhoVo.setToWhoTyp(pojo.getType());

        Optional<PricingType> pricingTypeOptional = pricingTypeRepository.findById(PRICE_TYPE_ID);

        if(pricingTypeOptional.isEmpty()){
            throw new BookNotFoundException(PRICE_TYPE_ID,"找不到产品");
        }

        PricingType pricingType = pricingTypeOptional.get();

        Optional<Product> productOptional = productRepository.findById(pricingType.getProductId());

        if(productOptional.isEmpty()){
            throw new BookNotFoundException(PRICE_TYPE_ID,"找不到产品");
        }

        Product product = productOptional.get();


        bookingTypeTowhoVo.setProduct(product);


        Pair<Reservation, BookingTypeTowhoVo> booking = bookingService.booking(bookingTypeTowhoVo,pojo,userVo);

        Reservation reservation = booking.getValue0();



        BookingResp resp = BookingResp.toResp(booking);


        System.out.println("================================="+ resp.toString());
        return ResponseEntity.ok(resp);

    }















    @GetMapping(value = "/products/{PRODUCT_ID}/bookings/page", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_createBooking(@PathVariable long PRODUCT_ID) {


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);

        System.out.println("----------"+userVo.toString());
        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }
        Product product = validatorOptional.get();


        List<PricingType> pricingTypes = pricingTypeRepository.findByProductId(product.getId());
        Map<Long,PricingType> longPricingTypeMap =  pricingTypes.stream().collect(Collectors.toMap(e->e.getId(),e->e));





        List<BookingRule> bookingRuleList = bookingRuleRepository.findByProduct(product.getId());

        List<AvailabilityVO> availabilityVOList = availabilityService.getAvailability_(product,bookingRuleList, LocalDate.now().plusDays(5));




        ProductResp productResp =  ProductResp.from(product);


        productResp.setBookingAvailability(availabilityVOList);

        PricingType pricingType = longPricingTypeMap.get(product.getDefault_price());

        PricingTypeResp pricingTypeResp_default = PricingTypeResp.from(pricingType);

        productResp.setDefault_price(pricingTypeResp_default);

        ReferralPojo pojo = new ReferralPojo();
        pojo.setFill_up_passager_info(null);

        Referral referral = referralService.create(userVo,EnumReferralType.fill_up_passager_info,pojo);





        String link = linkTo(methodOn(ReferralRestController.class).getReferral(referral.getCode())).withRel("create").getHref();

        String url_with_link = String.format(EnumReferralType.fill_up_passager_info.getUrl(),link);

        Map map = Map.of(

                "product",productResp,

    /*            "price_type_list", pricingTypes.stream().map(e->{

            EntityModel pricingTypeEntityModel = EntityModel.of(e);
           //         pricingTypeEntityModel.add(linkTo(methodOn(BookingRestController.class).Page_createBookingFromPriceType(e.getId())).withRel("Page_createBooking"));


            return pricingTypeEntityModel;

        }).collect(Collectors.toList())*/

              //  "status_list", EnumTourBookingStatus_.from(),

                "referral_path",url_with_link,
                "referral_id",referral.getCode(),
                //"referral_upath",url_with_link,
                "referral_url",link




        );


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(DocumentRestController.class).idScan(null)).withRel("idcardOcr"));

        entityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(product.getId(),null)).withRel("booking"));

        entityModel.add(linkTo(methodOn(PassengerRestController.class).createPassenger(userVo.getUser_id(),null)).withRel("createPassenger"));

        entityModel.add(linkTo(methodOn(PassengerRestController.class).getPassengerList(userVo.getUser_id(),null,null)).withRel("getPassengerList"));


        entityModel.add(linkTo(methodOn(ReferralRestController.class).createReferral(userVo.getUser_id(),null)).withRel("getUrl_for_fill_up_info"));



        return entityModel;
    }


    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/products/{PRODUCT_ID}/bookings", produces = "application/json")
    public ResponseEntity createBooking(@PathVariable long PRODUCT_ID,@RequestBody @Valid BookingPojo pojo) {


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);

        BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
        bookingTypeTowhoVo.setToWhoTyp(pojo.getType());



        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);


        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }
        Product product = validatorOptional.get();


        bookingTypeTowhoVo.setProduct(product);


        Pair<Reservation, BookingTypeTowhoVo> booking = bookingService.booking(bookingTypeTowhoVo,pojo,userVo);

        Reservation reservation = booking.getValue0();

        BookingResp resp = BookingResp.toResp(booking);


        if(EnumBookingType.Free.equals(reservation.getType())){
            paymentFreeService.freePayFree(reservation,userVo);
        }



        System.out.println("================================="+ resp.toString());
        return ResponseEntity.ok(resp);

    }



    @PostMapping(value = "/bookings/{RESERVATOIN_ID}/validation", produces = "application/json")
    public ResponseEntity<Reservation> offlineValidate(@PathVariable long RESERVATOIN_ID) {

        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isEmpty()) {
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());

        }
            Reservation reservation = optionalReservation.get();


        componentRightResolveService.resolve_(reservation,reservation.getFollowupPaid());



            return ResponseEntity.ok(reservation);


    }



    @PostMapping(value = "/bookings/{RESERVATOIN_ID}/travelers", produces = "application/json")
    public ResponseEntity<Traveler> addTravelers(@PathVariable long RESERVATOIN_ID,
                                                 @RequestBody TravelerReq transferPojo) {

        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isPresent()){
            Reservation reservation = optionalReservation.get();
            if(!reservation.getStatus().equals(EnumBookingStatus.Pending)){
                throw new Booking_not_pendingException(reservation.getId(),Reservation.class.getSimpleName(),"订单非等待提交状态，不得提交"+reservation.getStatus());
            }
            Traveler booking = bookingService.addTraveler(optionalReservation.get(),transferPojo);
            return ResponseEntity.ok(booking);
        }else{
            System.out.println("找不到产品");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }

    }












    @GetMapping(value = "/bookings/{RESERVATOIN_ID}/documents", produces = "application/json")
    public ResponseEntity<Map<EnumDocumentType,List<EntityModel<DocumentResp>>>> listDocuments(@PathVariable long RESERVATOIN_ID, CouponTemplatePojoList  couponTemplatePojoList) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isPresent()){

            Reservation reservation = optionalReservation.get();
            List<Document> documents = documentRepository.findAllByRaletiveId(optionalReservation.get().getId());
      //      ResponseEntity responseEntity = ResponseEntity.ok(DocumentResp.from(documents));


            return ResponseEntity.ok(DocumentResp.from(documents));
        }else{
            System.out.println("找不到产品");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }

    }



    @PostMapping(value = "/bookings/{RESERVATOIN_ID}/documents/ids", produces = "application/json")
    public ResponseEntity<List<EntityModel<MessageFileResp>>> createDocuments(@PathVariable long RESERVATOIN_ID, @ModelAttribute BookingDocumentIdsResp bookingDocumentResp ) {
        //@RequestParam("files") List<MultipartFile> files
        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);

        List<Pair<EnumDocumentType,String>> docTypeWithDocCodepairList = new ArrayList<>();
        if(nonNull(bookingDocumentResp.getContract_files()))
            docTypeWithDocCodepairList.addAll(bookingDocumentResp.getContract_files().stream().map(x->Pair.with(EnumDocumentType.contract,x)).collect(Collectors.toList()));
        if(nonNull(bookingDocumentResp.getInsurance_policy_files()))
            docTypeWithDocCodepairList.addAll(bookingDocumentResp.getInsurance_policy_files().stream().map(x->Pair.with(EnumDocumentType.insurance_policy,x)).collect(Collectors.toList()));
        if(nonNull(bookingDocumentResp.getPhoto_files()))
            docTypeWithDocCodepairList.addAll(bookingDocumentResp.getPhoto_files().stream().map(x->Pair.with(EnumDocumentType.photo,x)).collect(Collectors.toList()));
        if(nonNull(bookingDocumentResp.getBill_files()))
            docTypeWithDocCodepairList.addAll(bookingDocumentResp.getBill_files().stream().map(x->Pair.with(EnumDocumentType.bill,x)).collect(Collectors.toList()));


        System.out.println("--getInsurance_policy_files------------------------"+bookingDocumentResp.getInsurance_policy_files());

        if(!optionalReservation.isPresent()){

            List<TempDocument> tempDocuments = tempDocumentRepository.findAllByCodeIn(docTypeWithDocCodepairList.stream().map(x->x.getValue1()).distinct().collect(Collectors.toList()));


            System.out.println("------------------------------------------------"+tempDocuments.toString());


            Map<String,TempDocument> documentMap = tempDocuments.stream().collect(Collectors.toMap(x->x.getCode(),x->x));

            List<Pair<EnumDocumentType,TempDocument>> docTypeWithTempDocPairList = docTypeWithDocCodepairList.stream().map(x->{
                TempDocument tempDocument = documentMap.get(x.getValue1());
                return Pair.with(x.getValue0(),tempDocument);
            }).collect(Collectors.toList());




            if(docTypeWithTempDocPairList.isEmpty()){
                throw new Missing_documentException(RESERVATOIN_ID,Reservation.class.getSimpleName(),"需要附上申请文档， 合同，合影照片，保险单和发票");
            }


            try {
                List<Document> documents = fileStorageService.saveFromTempDocumentCode(RESERVATOIN_ID,docTypeWithTempDocPairList);
                return ResponseEntity.ok(MessageFileResp.fromDocuments(documents.stream().map(x->{
                    return Pair.with(FileStorageServiceImpl.url(x.getFileName()),x);
                }).collect(Collectors.toList())));


            }catch (Exception e){

                e.printStackTrace();
                return null;
                //return ResponseEntity.badRequest()
                //        .body(new MessageFile("Could not upload the file:"+fileNames));
            }
        }else{
            System.out.println("找不到产品");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }

    }












    @GetMapping(value = "/bookings/{RESERVATOIN_ID}/campaigns", produces = "application/json")
    public ResponseEntity<List<Campaign>> listCampaigns(@PathVariable long RESERVATOIN_ID) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isPresent()){

            Reservation reservation = optionalReservation.get();
            List<Campaign> documents = bookingService.listCampaigns(optionalReservation.get());
            //      ResponseEntity responseEntity = ResponseEntity.ok(DocumentResp.from(documents));

            return ResponseEntity.ok(documents);
        }else{
            System.out.println("找不到产品");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }

    }





    // TODO 下单后， 支付时 ，弹出选择 支付方式， 在这里。
    @GetMapping(value = "/bookings/{RESERVATOIN_ID}/payments", produces = "application/json")
    public Map 选择支付方式_并完成发出支付请求(@PathVariable long RESERVATOIN_ID,HttpServletRequest request) {



        // 输入一个 支付类型， 然后，进行 支付
        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);

        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);
/*
        Reservation reservation_ = new Reservation();
        reservation_.setAmount(10);
        reservation_.setCode("111111111111111");
        optionalReservation = Optional.of(reservation_);*/

        if(optionalReservation.isEmpty()) {
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }
            Reservation reservation = optionalReservation.get();


            Payment payment = paymentService.createPayment(
                    reservation.getPaymentMethods_json(),
                    reservation.getAmount(),
                    userVo,
                    reservation);
            payment.setRecipient(reservation.getSupplier());


        Gson gson = new Gson();

        ChargeMetadataVo chargeMetadataVo = new ChargeMetadataVo();
       // chargeMetadataVo.setCampaign(campaign.getId());
       // chargeMetadataVo.setCampaign_code(campaign.getCode());
      //  chargeMetadataVo.setVolume_up_voucher(voucher.getId());
        chargeMetadataVo.setPayer(userVo.getUser_id());
        chargeMetadataVo.setBooking(reservation.getId());
        String metadata = gson.toJson(chargeMetadataVo);





        EnumPayChannel[] payChannelList = new Gson().fromJson(payment.getPayment_method_json(),EnumPayChannel[].class);

        List<EntityModel<PaymentMethodResp>>  entityModelList = Arrays.stream(payChannelList).map(e->{

            EntityModel<PaymentMethodResp> entityModel = EntityModel.of(PaymentMethodResp.of(e));

            switch (e){
                case wx:{
                    entityModel.add(linkTo(methodOn(PaymentRestController.class).wxPayRequest(payment.getId(),null)).withRel("wxpay"));
                }
                break;
                case alipay:{
                    entityModel.add(linkTo(methodOn(AlipayController.class).wapPayq_(payment.getId(),null)).withRel("alipay"));
                }
                case balance:{
                    entityModel.add(linkTo(methodOn(BookingRestController.class).惠民卡支付(payment.getId(),null)).withRel("balance_pay"));
                }
                break;
                default:{}
            }

            return entityModel;

        }).collect(Collectors.toList());




        return Map.of("payment",payment,"payment_methods",entityModelList);



/*
        if(channel.equals(EnumPayChannel.wx)){

            Optional<Openid> optional = openidRepository.findByUserIdAndLink(userVo.getUser_id(),true);

            if(optional.isEmpty()){
                throw new BookNotFoundException(userVo.getUser_id(),"找不到微信 openid");
            }
            Openid openid = optional.get();

      //    Map  charge = paymentService.createCharge(ip,payment.getCode(),reservation,reservation.getAmount(),openid.getOpenid(),userVo,metadata);



            PaymentResp resp = PaymentResp.from(payment);
            resp.add(linkTo(methodOn(PaymentRestController.class).wxPayRequest(payment.getId(),null)).withRel("wx_pay_url"));

            return resp;
        }

        if(channel.equals(EnumPayChannel.alipay)){

            PaymentResp resp = PaymentResp.from(payment);
            resp.add(linkTo(methodOn(AlipayController.class).wapPayq_(payment.getId(),null)).withRel("wx_pay_url"));

            return resp;
        }


        return null;*/
    }







    @RequestMapping(value = "/pay_now/{CAMPAIGN_ID}")
    public Map 立即下单(@PathVariable long CAMPAIGN_ID, HttpServletRequest request) {




        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);

        Optional<Openid> optional = openidRepository.findByUserIdAndLink(userVo.getUser_id(),true);

        if(optional.isEmpty()){
            throw new BookNotFoundException(CAMPAIGN_ID,"未绑定产品");
        }
        Optional<Campaign> optionalCampaign = campaignRepository.findById(CAMPAIGN_ID);
        if(optionalCampaign.isEmpty()) {
            throw new BookNotFoundException(CAMPAIGN_ID,"找不到产品");
        }
        Campaign campaign = optionalCampaign.get();
        if(!campaign.getPay()){
            throw new BookNotFoundException(CAMPAIGN_ID,"改券不属于支付类型");
        }

        BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
        bookingTypeTowhoVo.setToWhoTyp(EnumBookingOjbectType.Voucher);
        bookingTypeTowhoVo.setCampaign(optionalCampaign.get());

        BookingPojo pojo = new BookingPojo();

        Pair<Reservation, BookingTypeTowhoVo> booking = bookingService.booking(bookingTypeTowhoVo, pojo, userVo);

        String ip = HttpUtils.getRequestIP(request);

        Reservation reservation = booking.getValue0();

        Payment payment = paymentService.createPayment(new Gson().toJson(Arrays.asList(EnumPayChannel.wx)),reservation.getAmount(),userVo,reservation);
        payment.setRecipient(reservation.getSupplier());

      //  resp.add(linkTo(methodOn(PaymentRestController.class).wxPayRequest(payment.getId(),null)).withRel("wx_pay_url"));


        // double totalAmount = 0.01;
        int totalAmount = payment.getAmount();
        // 微信的支付单位是分所以要转换一些单位
        long  totalproce =Long.parseUnsignedLong(totalAmount+"");//Long.parseUnsignedLong(totalAmount*100+"");


        Voucher voucher = campaignService.retain(campaign);
        Gson gson = new Gson();
        ChargeMetadataVo chargeMetadataVo = new ChargeMetadataVo();
        chargeMetadataVo.setCampaign(campaign.getId());
        chargeMetadataVo.setCampaign_code(campaign.getCode());
        chargeMetadataVo.setVolume_up_voucher(voucher.getId());
        chargeMetadataVo.setPayer(userVo.getUser_id());
        chargeMetadataVo.setBooking(reservation.getId());
        String metadata = gson.toJson(chargeMetadataVo);
        Map  charge = paymentService.createCharge(ip,payment.getCode(),reservation,totalproce,optional.get().getOpenid(),userVo,metadata);



        return charge;
    }

















    @PostMapping(value = "/bookings/{RESERVATOIN_ID}/h5pay", produces = "application/json")
    public void h5Pay(@PathVariable long RESERVATOIN_ID, @RequestBody BookingPayPojo transferPojo) {


        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);


        if(optionalReservation.isPresent()){
            ChargeResp booking = bookingService.pay(optionalReservation.get(),transferPojo);

        }else{
            System.out.println("找不到产品");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }




    }





    @PostMapping(value = "/bookings/{RESERVATOIN_ID}/惠民卡支付", produces = "application/json")
    public ChargeResp  惠民卡支付(@PathVariable long RESERVATOIN_ID, @RequestBody BookingPayPojo transferPojo) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isPresent()){
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());

        }

        ChargeResp booking = bookingService.pay(optionalReservation.get(),transferPojo);

        return booking;
    }





}