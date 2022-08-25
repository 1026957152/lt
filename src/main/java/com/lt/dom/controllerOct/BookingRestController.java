package com.lt.dom.controllerOct;

import com.google.gson.Gson;
import com.lt.dom.OctResp.*;
import com.lt.dom.OctResp.ChargeResp;
import com.lt.dom.config.WxPayConfig;
import com.lt.dom.domain.CouponTemplatePojoList;
import com.lt.dom.error.*;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.BookingTypeTowhoVo;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.util.HttpUtils;
import com.lt.dom.vo.ChargeMetadataVo;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    private WxPayConfig wxPayConfig;
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
    private SupplierRepository supplierRepository;


    @Autowired
    private CampaignServiceImpl campaignService;
    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    CampaineAssignToBookingServiceImpl campaineAssignToBookingService;

    @Autowired
    private GuideRepository guideRepository;
    @Autowired
    private AssetServiceImpl assetService;



    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/bookings/{RESERVATOIN_ID}", produces = "application/json")
    public ResponseEntity<BookingResp> getReservation(@PathVariable long RESERVATOIN_ID) {
        Optional<Reservation> optionalProduct = reservationRepository.findById(RESERVATOIN_ID);


        if(optionalProduct.isEmpty()) {
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());

        }
        Reservation reservation = optionalProduct.get();


            if(optionalProduct.get().getProductType().equals(EnumProductType.Daytour)){

                // reservationResp = new ReservationResp();
                Optional<Product> product = productRepository.findById(optionalProduct.get().getProductId());
                Optional<Tour> optionalTour = tourRepository.findById(product.get().getTypeToWho());
                List<Traveler> travelers = travelerRepository.findAllByBooking(reservation.getId());
                List<Document> documents = documentRepository.findAllByRaletiveId(reservation.getId());
                Optional<Asset> assetResp  = assetService.getQrOptional(reservation.getCode());
                BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
                bookingTypeTowhoVo.setProduct(product.get());
                bookingTypeTowhoVo.setTour(optionalTour.get());

                BookingResp bookingResp = BookingResp.toResp(Quartet.with(reservation,bookingTypeTowhoVo,travelers,documents),assetResp.get());

                bookingResp.add(linkTo(methodOn(PaymentRestController.class).wxPayRequest(reservation.getId(),null)).withRel("pay_url"));
             //   BookingResp.add(linkTo(methodOn(BookingRestController.class).bulkAddTravelers(reservation.getId(),null)).withRel("add_travelers_url"));
                bookingResp.add(linkTo(methodOn(BookingRestController.class).createDocuments(reservation.getId(),new BookingDocumentIdsResp())).withRel("add_documents_url"));

                return ResponseEntity.ok(bookingResp);
            }

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));


    }

    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/bookings", produces = "application/json")
    public ResponseEntity<Page<BookingResp>> pageReservation(@ModelAttribute PageReq pageReq ) {


        Page<Reservation> optionalProduct = reservationRepository.findAll(PageRequest.of(pageReq.getPage(),pageReq.getLimit()));

        Page<BookingResp> page =  optionalProduct.map(x->{

            if(x.getProductType().equals(EnumProductType.Daytour)){
                Optional<Product> product = productRepository.findById(x.getProductId());
                Optional<Tour> optionalTour = tourRepository.findById(product.get().getTypeToWho());
                List<Traveler> travelers = travelerRepository.findAllByBooking(x.getId());
                List<Document> documents = documentRepository.findAllByRaletiveId(x.getId());

                BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
                bookingTypeTowhoVo.setProduct(product.get());
                bookingTypeTowhoVo.setTour(optionalTour.get());

                BookingResp resp = BookingResp.toResp(Quartet.with(x,bookingTypeTowhoVo,travelers,documents));

                resp.add(linkTo(methodOn(BookingRestController.class).pay(x.getId(),null)).withRel("pay_url"));

                resp.add(linkTo(methodOn(BookingRestController.class).createDocuments(x.getId(),new BookingDocumentIdsResp())).withRel("add_documents_url"));

                return resp;//BookingResp.toResp(Quartet.with(x,bookingTypeTowhoVo,travelers,documents));
            }else{
                return new BookingResp();
            }
        });

        return ResponseEntity.ok(page);


    }









    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/bookings", produces = "application/json")
    public ResponseEntity createBooking(@RequestBody @Valid BookingPojo pojo) {


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

        Pair<Reservation, BookingTypeTowhoVo> booking = bookingService.book(bookingTypeTowhoVo,pojo);

        BookingResp resp = BookingResp.toResp(booking);

        return ResponseEntity.ok(resp);

    }







    @PostMapping(value = "/bookings/{RESERVATOIN_ID}/travelers", produces = "application/json")
    public ResponseEntity<Traveler> addTravelers(@PathVariable long RESERVATOIN_ID,
                                                 @RequestBody TravelerReq transferPojo) {

        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isPresent()){
            Reservation reservation = optionalReservation.get();
            if(!reservation.getStatus().equals(EnumTourBookingStatus.Pending)){
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
                List<Document> documents = fileStorageService.saveFromTempDocumentList(RESERVATOIN_ID,docTypeWithTempDocPairList);
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




    @PostMapping(value = "/bookings/{RESERVATOIN_ID}/payments", produces = "application/json")
    public PaymentResp pay(@PathVariable long RESERVATOIN_ID, @RequestBody BookingPayPojo transferPojo) {


        Optional<Reservation> optionalReservation = null;//reservationRepository.findById(RESERVATOIN_ID);

        Reservation reservation_ = new Reservation();
        reservation_.setAmount(10);
        reservation_.setCode("111111111111111");
        optionalReservation = Optional.of(reservation_);

        if(optionalReservation.isPresent()){
            Reservation reservation = optionalReservation.get();
            User user = new User();
            user.setId(1l);

         //   Payment payment = paymentService.createPayment(payment);

            Payment payment = paymentService.createPayment(reservation.getAmount(),user.getId(),reservation.getCode());
            payment.setRecipient(reservation.getRedeemer());
            PaymentResp resp = PaymentResp.from(payment);
            resp.add(linkTo(methodOn(PaymentRestController.class).wxPayRequest(payment.getId(),null)).withRel("wx_pay_url"));

            return resp;
        }else{
            System.out.println("找不到产品");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }

    }





    @RequestMapping(value = "/pay_now/{CAMPAIGN_ID}")
    public Map 立即下单(@PathVariable long CAMPAIGN_ID, HttpServletRequest request) {




        Authentication authentication = authenticationFacade.getAuthentication();

        User user = authenticationFacade.getUser(authentication);

        Optional<Openid> optional = openidRepository.findByUserIdAndLink(user.getId(),true);

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

        Pair<Reservation, BookingTypeTowhoVo> booking = bookingService.book(bookingTypeTowhoVo, pojo);

        String ip = HttpUtils.getRequestIP(request);

        Reservation reservation = booking.getValue0();

        Payment payment = paymentService.createPayment(reservation.getAmount(),user.getId(),reservation.getCode());
        payment.setRecipient(reservation.getRedeemer());

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
        chargeMetadataVo.setPayer(user.getId());
        chargeMetadataVo.setBooking(reservation.getId());
        String metadata = gson.toJson(chargeMetadataVo);
        Map  charge = paymentService.createRefund(ip,payment.getCode(),reservation,totalproce,optional.get().getOpenid(),user,metadata);



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
    public void 惠民卡支付(@PathVariable long RESERVATOIN_ID, @RequestBody BookingPayPojo transferPojo) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isPresent()){
            ChargeResp booking = bookingService.pay(optionalReservation.get(),transferPojo);
        }else{
            System.out.println("找不到产品");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }
    }





}