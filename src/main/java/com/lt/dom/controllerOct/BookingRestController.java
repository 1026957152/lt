package com.lt.dom.controllerOct;

import com.google.gson.Gson;
import com.lt.dom.OctResp.*;
import com.lt.dom.OctResp.ShippingRateResp;
import com.lt.dom.RealNameAuthentication.RealNameAuthenticationServiceImpl;
import com.lt.dom.controllerOct.pay.BalancePaymentRestController;
import com.lt.dom.domain.CouponTemplatePojoList;
import com.lt.dom.error.*;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.BookingTypeTowhoVo;
import com.lt.dom.requestvo.BookingTypeTowhoVoSku;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.serviceOtc.pay.BalancePaymentServiceImpl;
import com.lt.dom.serviceOtc.product.AttractionTicketServiceImpl;
import com.lt.dom.serviceOtc.product.CityPassServiceImpl;
import com.lt.dom.specification.BookingQueryfieldsCriteria;
import com.lt.dom.specification.BookingSpecification;
import com.lt.dom.util.HttpUtils;
import com.lt.dom.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.lt.dom.serviceOtc.JsonParse.GSON;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.groupingBy;
import static org.springframework.data.jpa.domain.Specification.where;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//https://caroly.fun/archives/springboot%E6%95%B4%E5%90%88%E5%BE%AE%E4%BF%A1%E6%94%AF%E4%BB%98

@RestController
@RequestMapping("/oct")
public class BookingRestController {
    Logger logger = LoggerFactory.getLogger(BookingRestController.class);

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private PaymentServiceImpl paymentService;
    @Autowired
    private PaymentFreeServiceImpl paymentFreeService;

    @Autowired
    private RefundRepository refundRepository;
    @Autowired
    private ShippingRateRepository shippingRateRepository;

    @Autowired
    private RealNameAuthenticationServiceImpl realnameAuthsService;




    @Autowired
    private PartnerServiceImpl partnerService;
    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private     PriceServiceImpl priceService
    ;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private OpenidRepository openidRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private SkuRepository zonePricingRepository;
    @Autowired
    private UserAuthorityServiceImpl userAuthorityService;


    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CancellationServiceImpl cancellationService;
    @Autowired
    private BookingServiceImpl bookingService;
    @Autowired
    private TempDocumentRepository tempDocumentRepository;
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private TravelerRepository travelerRepository;

    @Autowired
    private CityPassServiceImpl cityPassService;
    @Autowired
    private AttractionTicketServiceImpl attractionTicketService;


    @Autowired
    private ComponentRightResolveServiceImpl componentRightResolveService;
    @Autowired
    private PricingTypeRepository pricingTypeRepository;
    @Autowired
    private PartnerShareRatePlanRepository partnerShareRatePlanRepository;

    @Autowired
    private ShippingRatePlanRepository shippingRatePlanRepository;

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;



    @Autowired
    private CampaignServiceImpl campaignService;
    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    CampaineAssignToBookingServiceImpl campaineAssignToBookingService;

    @Autowired
    ExtraServiceImpl extraService;

    @Autowired
    CrossSellServiceImpl crossSellService;


    @Autowired
    private AvailabilityServiceImpl availabilityService;
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private LineItemRepository lineItemRepository;

    @Autowired
    private PassRepository passRepository;
    @Autowired
    private CancellationRepository cancellationRepository;





    @Autowired
    private BookingRuleRepository bookingRuleRepository;
    @Autowired
    private ReferralServiceImpl referralService;
    @Autowired
    private BalancePaymentServiceImpl balancePaymentService;
    private List lines;


    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/bookings/{RESERVATOIN_ID}", produces = "application/json")
    public ResponseEntity<EntityModel<BookingResp>> getReservation(@PathVariable long RESERVATOIN_ID) {


        Optional<Reservation> optionalProduct = reservationRepository.findById(RESERVATOIN_ID);

        if(optionalProduct.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.not_found,"找不到订单");

        }
        Reservation reservation = optionalProduct.get();




        List<LineItem> lineItemList = lineItemRepository.findAllByBooking(reservation.getId());

       // BookingResp bookingResp = BookingResp.toResp_LIST(Pair.with(reservation,bookingProductFuckList));
        BookingResp bookingResp = BookingResp.from(reservation);



        List<Cancellation> cancellation = cancellationRepository.findAllByBooking(reservation.getId());

        bookingResp.setParameterList(
                Map.of(
                        "cancel_reason_list", EnumCancel_reason.list(),
                        "refund_reason_list", EnumRefundReason.list()

                        ));
    //    profileResp.setPreferenceSpace(EnumPreferenceSpace.valueOf(preference.getString_value()));






/*        bookingResp.setProducts(bookingProductFuckList.stream().map(e->{
            BookingProductFuckResp bookingProductFuckResp = BookingProductFuckResp.from(e);
            bookingProductFuckResp.setImage(fileStorageService.loadDocumentWithDefault(EnumDocumentType.product_thumb,e.getProductCode()));

            List<Traveler> travelers = travelerRepository.findAllByBooking(reservation.getId());
            bookingProductFuckResp.setPassengers(TravelerResp.Listfrom(travelers));

            EntityModel entityModel = EntityModel.of(bookingProductFuckResp);

            entityModel.add(linkTo(methodOn(PaymentRestController.class).refund(reservation.getId(),null)).withRel("refund"));


            return entityModel;
        }).collect(Collectors.toList()));*/

        bookingResp.setLines(lineItemList.stream().map(e->{
            LineItemResp lineItemResp = LineItemResp.from(e);
            lineItemResp.setImage(fileStorageService.loadDocumentWithDefault(EnumDocumentType.product_thumb,e.getProductCode()));

            List<Traveler> travelers = travelerRepository.findAllByReferSku(e.getReferTraveler());
            lineItemResp.setPassengers(TravelerResp.Listfrom(travelers));

            EntityModel entityModel = EntityModel.of(lineItemResp);

            entityModel.add(linkTo(methodOn(PaymentRestController.class).refund(reservation.getId(),null,null)).withRel("refund"));


            return entityModel;
        }).collect(Collectors.toList()));

      //  bookingResp.withLines(bookingProductFuckList);

        List<Traveler> travelers = travelerRepository.findAllByBooking(reservation.getId());
        bookingResp.setTravelers(TravelerResp.Listfrom(travelers));



 /*       Optional<Product> product = productRepository.findById(optionalProduct.get().getProductId());

            if(optionalProduct.get().getProductType().equals(EnumProductType.Daytour)){

                // reservationResp = new ReservationResp();
                Optional<Tour> optionalTour = tourRepository.findById(product.get().getTypeTo());
                List<Traveler> travelers = travelerRepository.findAllByBooking(reservation.getId());
                List<Document> documents = documentRepository.findAllByRaletiveId(reservation.getId());
                Optional<Asset> assetResp  = assetService.getQrOptional(reservation.getCode());
                BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
                bookingTypeTowhoVo.setProduct(product.get());
                bookingTypeTowhoVo.setTour(optionalTour.get());


                bookingResp = BookingResp.with(bookingResp,travelers,documents,assetResp.get());

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


            bookingResp = BookingResp.with(bookingResp,travelers);
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

            bookingResp = BookingResp.with(bookingResp,travelers);


            bookingResp.setAsset(assetService.getQrEntityModel(reservation.getCode()));

            entityModel= EntityModel.of(bookingResp);
            entityModel.add(linkTo(methodOn(BookingRestController.class).createDocuments(reservation.getId(),new BookingDocumentIdsResp())).withRel("add_documents_url"));

        }*/





        EntityModel entityModel= EntityModel.of(bookingResp);

        if(reservation.getValidationStatus().equals(EnumValidationStatus.NewAwaitingValidation)){


            entityModel.add(linkTo(methodOn(BookingRestController.class).offlineValidate(reservation.getId())).withRel("offlineValidate"));

        }


        List<Payment> optionalPayment = paymentRepository.findByReference(reservation.getCode());

        bookingResp.setPayments(optionalPayment.stream().map(e->{
            PaymentResp paymentResp = PaymentResp.detailfrom(e);

            EntityModel entityModel_payment= EntityModel.of(paymentResp);
            entityModel_payment.add(linkTo(methodOn(BookingRestController.class).refund(e.getId(),null)).withRel("refund"));



            return entityModel_payment;
        }).collect(Collectors.toList()));



        if(!optionalPayment.isEmpty()){
            Payment payment = optionalPayment.get(0);



            PaymentResp paymentResp = PaymentResp.from(payment);

           // PaymentResp.from(payment);

            Map map = Map.of("amount",10,"payment-method",EnumPaymentOption.alipay,"card",Map.of("",11));

            Map map2 = Map.of("amount",10,"payment-method",EnumPaymentOption.alipay,"card",Map.of("",11));

      //      paymentResp.setRouting(Arrays.asList(map,map2));


            List<EntityModel<PaymentMethodResp>>  entityModelList = paymentService.getPaymentMethods(payment);

            paymentResp.setMethods(entityModelList);



            List<Charge> chargeList = paymentService.getCharge(reservation);


            paymentResp.setCharges(chargeList.stream().map(e->{


                ChargeResp chargeResp = ChargeResp.from(e);

                List<Refund> refundList = refundRepository.findAllByCharge(e.getId());
                chargeResp.setRefunds(refundList.stream().map(refund->{
                    RefundResp resp = RefundResp.from(refund);
                    return resp;
                }).collect(Collectors.toList()));

                EntityModel entityModel1 = EntityModel.of(chargeResp);

                if(e.getStatus().equals(EnumChargeStatus.succeeded)){
                    entityModel1.add(linkTo(methodOn(PaymentRestController.class).refund(e.getId(),null,null)).withRel("refund"));
                }

                return entityModel1;

            }).collect(Collectors.toList()));


            EntityModel entityModel_payment= EntityModel.of(paymentResp);
            entityModel_payment.add(linkTo(methodOn(BookingRestController.class).refund(reservation.getId(),null)).withRel("refund"));

            bookingResp.setPayment(entityModel_payment);
        }

        List<Charge> chargeList = paymentService.getCharge(reservation);



        List<Refund> refundList = refundRepository.findAllByBooking(reservation.getId());
        bookingResp.setRefunds(refundList.stream().map(refund->{
            RefundResp resp = RefundResp.from(refund);
            return resp;
        }).collect(Collectors.toList()));


        //TODO 已经默认给建立了 支付对象了

        entityModel.add(linkTo(methodOn(BookingRestController.class).getReservation(reservation.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(BookingRestController.class).cancel(reservation.getId(),null)).withRel("cancel"));
        entityModel.add(linkTo(methodOn(BookingRestController.class).amendment(reservation.getId(),null)).withRel("amend"));

    //    entityModel.add(linkTo(methodOn(PaymentRestController.class).refund(reservation.getId(),null)).withRel("refund"));
        entityModel.add(linkTo(methodOn(BookingRestController.class).refund(reservation.getId(),null)).withRel("refund"));



        BookingResp.Refund refundTab = new BookingResp.Refund();

        refundTab.setAmountRefunded(refundList.stream()
                        .filter(e->e.getStatus().equals(EnumRefundStatus.succeeded))
                .mapToInt(e->{
                    return  e.getAmount();

        }).sum());

        refundTab.setRefunds(refundList.stream().map(refund->{

            RefundResp resp = RefundResp.from(refund);
            return resp;
        }).collect(Collectors.toList()));
        EntityModel entityModel_refundTab= EntityModel.of(refundTab);

        bookingResp.setRefund(entityModel_refundTab);

        BookingResp.Shipping shippingTab = new BookingResp.Shipping();

        if(EnumShippingAddressCollection.required.equals(reservation.getShippingAddressCollection())){
            shippingTab.setShippingCardAddressResp(ShippingAddressResp.from(GSON.fromJson(reservation.getShipingAddress_json(),ShippingCardAddress.class)));

            ShippingRate shippingRate = shippingRateRepository.findById(reservation.getShippingRate()).get();

            shippingTab.setShippingRate(ShippingRateResp.from(shippingRate));
            bookingResp.setShipping(shippingTab);
        }
        return ResponseEntity.ok(entityModel);



    }





    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/bookings/{RESERVATOIN_ID}/edit", produces = "application/json")
    public ResponseEntity<EntityModel<BookingEditResp>> getBookingEdit(@PathVariable long RESERVATOIN_ID) {

        Optional<Reservation> optionalProduct = reservationRepository.findById(RESERVATOIN_ID);

        if(optionalProduct.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.not_found,"找不到订单");
        }
        Reservation reservation = optionalProduct.get();




        List<LineItem> lineItemList = lineItemRepository.findAllByBooking(reservation.getId());

        BookingResp bookingResp = BookingResp.from(reservation);



        List<Cancellation> cancellation = cancellationRepository.findAllByBooking(reservation.getId());

        bookingResp.setParameterList(
                Map.of(
                        "cancel_reason_list", EnumCancel_reason.list()

                ));


        bookingResp.setLines(lineItemList.stream().map(e->{
            LineItemResp lineItemResp = LineItemResp.from(e);
            lineItemResp.setImage(fileStorageService.loadDocumentWithDefault(EnumDocumentType.product_thumb,e.getProductCode()));

            List<Traveler> travelers = travelerRepository.findAllByLineItem(e);
            lineItemResp.setPassengers(TravelerResp.Listfrom(travelers));


            cityPassService.show(lineItemResp,e);

            EntityModel entityModel = EntityModel.of(lineItemResp);
            entityModel.add(linkTo(methodOn(PaymentRestController.class).refund(reservation.getId(),null,null)).withRel("refund"));


            return entityModel;
        }).collect(Collectors.toList()));


        ;


     //   List<Traveler> travelers = travelerRepository.findAllByBooking(reservation.getId());
        bookingResp.setTravelers(

                TravelerResp.Listfrom(lineItemList.stream().map(e->{

                    List<Traveler> travelers = travelerRepository.findAllByLineItem(e);
                    return travelers;
                }).flatMap(List::stream).collect(Collectors.toList())));


        EntityModel entityModel= EntityModel.of(bookingResp);

        if(reservation.getValidationStatus().equals(EnumValidationStatus.NewAwaitingValidation)){
            entityModel.add(linkTo(methodOn(BookingRestController.class).offlineValidate(reservation.getId())).withRel("offlineValidate"));

        }




       // bookingResp.setFulfillment();

        List<Payment> optionalPayment = paymentRepository.findByReference(reservation.getCode());

        bookingResp.setPayments(optionalPayment.stream().map(e->{
            PaymentResp paymentResp = PaymentResp.detailfrom(e);

            EntityModel entityModel_payment= EntityModel.of(paymentResp);
            entityModel_payment.add(linkTo(methodOn(BookingRestController.class).refund(e.getId(),null)).withRel("refund"));

            return entityModel_payment;
        }).collect(Collectors.toList()));



        if(!optionalPayment.isEmpty()){
            Payment payment = optionalPayment.get(0);

            PaymentResp paymentResp = PaymentResp.detailfrom(payment);

            // PaymentResp.from(payment);

            Map map = Map.of("amount",10,"payment-method",EnumPaymentOption.alipay,"card",Map.of("",11));

            Map map2 = Map.of("amount",10,"payment-method",EnumPaymentOption.alipay,"card",Map.of("",11));

            //      paymentResp.setRouting(Arrays.asList(map,map2));


            List<EntityModel<PaymentMethodResp>>  entityModelList = paymentService.getPaymentMethods(payment);

            paymentResp.setMethods(entityModelList);



            List<Charge> chargeList = paymentService.getCharge(reservation);


            paymentResp.setCharges(chargeList.stream().map(e->{


                ChargeResp chargeResp = ChargeResp.from(e);

                List<Refund> refundList = refundRepository.findAllByCharge(e.getId());
                chargeResp.setRefunds(refundList.stream().map(refund->{
                    RefundResp resp = RefundResp.from(refund);
                    return resp;
                }).collect(Collectors.toList()));

                EntityModel entityModel1 = EntityModel.of(chargeResp);

                if(e.getStatus().equals(EnumChargeStatus.succeeded)){
                    entityModel1.add(linkTo(methodOn(PaymentRestController.class).refund(e.getId(),null,null)).withRel("refund"));
                }

                return entityModel1;

            }).collect(Collectors.toList()));


            EntityModel entityModel_payment= EntityModel.of(paymentResp);
            entityModel_payment.add(linkTo(methodOn(BookingRestController.class).refund(reservation.getId(),null)).withRel("refund"));

            bookingResp.setPayment(entityModel_payment);
        }

        List<Charge> chargeList = paymentService.getCharge(reservation);



        List<Refund> refundList = refundRepository.findAllByBooking(reservation.getId());
/*        bookingResp.setRefunds(refundList.stream().map(refund->{
            RefundResp resp = RefundResp.from(refund);
            return resp;
        }).collect(Collectors.toList()));
        ;*/

        //TODO 已经默认给建立了 支付对象了

        entityModel.add(linkTo(methodOn(BookingRestController.class).getReservation(reservation.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(BookingRestController.class).cancel(reservation.getId(),null)).withRel("cancel"));
        entityModel.add(linkTo(methodOn(BookingRestController.class).amendment(reservation.getId(),null)).withRel("amend"));

        //    entityModel.add(linkTo(methodOn(PaymentRestController.class).refund(reservation.getId(),null)).withRel("refund"));
        entityModel.add(linkTo(methodOn(BookingRestController.class).refund(reservation.getId(),null)).withRel("refund"));



        BookingResp.Refund refundTab = new BookingResp.Refund();

        refundTab.setAmountRefunded(refundList.stream()
                .filter(e->e.getStatus().equals(EnumRefundStatus.succeeded))
                .mapToInt(e->{
                    return  e.getAmount();

                }).sum());

        refundTab.setRefunds(refundList.stream().map(refund->{

            RefundResp resp = RefundResp.from(refund);
            return resp;
        }).collect(Collectors.toList()));
        EntityModel entityModel_refundTab= EntityModel.of(refundTab);

        bookingResp.setRefund(entityModel_refundTab);

        BookingResp.Shipping shippingTab = new BookingResp.Shipping();

        if(EnumShippingAddressCollection.required.equals(reservation.getShippingAddressCollection())){
            shippingTab.setShippingCardAddressResp(ShippingAddressResp.from(GSON.fromJson(reservation.getShipingAddress_json(),ShippingCardAddress.class)));

            ShippingRate shippingRate = shippingRateRepository.findById(reservation.getShippingRate()).get();

            shippingTab.setShippingRate(ShippingRateResp.from(shippingRate));
            bookingResp.setShipping(shippingTab);
        }
        return ResponseEntity.ok(entityModel);




    }
    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/bookings_backup", produces = "application/json")
    public ResponseEntity<PagedModel> getReservationListbookings_backup(Pageable pageReq , PagedResourcesAssembler<EntityModel<BookingResp>>assembler) {


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





    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/bookings/Page_listBooking", produces = "application/json")
    public EntityModel<Media> Page_listBooking(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("status_list", EnumBookingStatus.from(),
                "platform_list", EnumPlatform.List());


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(BookingRestController.class).getPcBookingList(null,null,null)).withRel("list"));


        return entityModel;

    }




    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/bookings", produces = "application/json")
    public ResponseEntity<PagedModel> getReservationList(@PageableDefault(sort = {"modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageReq ,
                                                         PagedResourcesAssembler<EntityModel<BookingResp>>assembler) {


        Page<Reservation> optionalProduct = reservationRepository.findAll(pageReq);

        Page<EntityModel<BookingResp>> page =  optionalProduct.map(x->{


            List<LineItem> lineItemList = lineItemRepository.findAllByBooking(x.getId());

            BookingResp bookingResp = BookingResp.toResp_LIST(Pair.with(x, lineItemList));

            Optional<Payment> optionalPayment = paymentService.getByBooking(x);

            if(optionalPayment.isPresent()){
                Payment payment = optionalPayment.get();



                PaymentResp paymentResp = PaymentResp.from(payment);

                List<EntityModel<PaymentMethodResp>>  entityModelList = paymentService.getPaymentMethods(payment);

                paymentResp.setMethods(entityModelList);

                EntityModel entityModel_payment= EntityModel.of(paymentResp);
                entityModel_payment.add(linkTo(methodOn(BookingRestController.class).refund(payment.getId(),null)).withRel("refund"));

                bookingResp.setPayment(entityModel_payment);
            }
 /*
                Optional<Product> product = productRepository.findById(x.getProductId());

                List<Traveler> travelers = travelerRepository.findAllByBooking(x.getId());
                List<Document> documents = documentRepository.findAllByRaletiveId(x.getId());

                BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
                bookingTypeTowhoVo.setProduct(product.get());
                bookingTypeTowhoVo.setToWhoTyp(EnumBookingOjbectType.Product);
                BookingResp resp = BookingResp.toResp(Quartet.with(x,bookingTypeTowhoVo,travelers,documents));

                */
                EntityModel entityModel = EntityModel.of(bookingResp);
            entityModel.add(linkTo(methodOn(BookingRestController.class).getReservation(x.getId())).withSelfRel());

                //  entityModel.add(linkTo(methodOn(BookingRestController.class).选择支付方式_并完成发出支付请求(x.getId(),null,null)).withRel("pay_url"));
                entityModel.add(linkTo(methodOn(BookingRestController.class).addTravelers(x.getId(),null)).withRel("addTraveler"));
                return entityModel;



        });

        return ResponseEntity.ok(assembler.toModel(page.map(e->{

            return e;
        })));


    }





    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/users/{USER_ID}/bookings/statusRefund", produces = "application/json")
    public ResponseEntity<PagedModel> getReservationListForRefund(
            @PathVariable long USER_ID,
            @PageableDefault(sort = {"modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageReq ,
                                                         PagedResourcesAssembler<EntityModel<BookingResp>>assembler) {


       List<EnumBookingStatus> statusList =         Arrays.asList(EnumBookingStatus.RefundInProgress,EnumBookingStatus.Refunded,EnumBookingStatus.PartialyRefunded);


        Page<Reservation> optionalProduct = reservationRepository.findAllByStatusIn(statusList,pageReq);

        Page<EntityModel<BookingResp>> page =  optionalProduct.map(x->{


            List<LineItem> lineItemList = lineItemRepository.findAllByBooking(x.getId());

            BookingResp bookingResp = BookingResp.toResp_LIST(Pair.with(x, lineItemList));

            Optional<Payment> optionalPayment = paymentService.getByBooking(x);

            if(optionalPayment.isPresent()){
                Payment payment = optionalPayment.get();

                PaymentResp paymentResp = PaymentResp.from(payment);

                List<EntityModel<PaymentMethodResp>>  entityModelList = paymentService.getPaymentMethods(payment);

                paymentResp.setMethods(entityModelList);

                EntityModel entityModel_payment= EntityModel.of(paymentResp);
                entityModel_payment.add(linkTo(methodOn(BookingRestController.class).refund(payment.getId(),null)).withRel("refund"));

                bookingResp.setPayment(entityModel_payment);
            }
 /*
                Optional<Product> product = productRepository.findById(x.getProductId());

                List<Traveler> travelers = travelerRepository.findAllByBooking(x.getId());
                List<Document> documents = documentRepository.findAllByRaletiveId(x.getId());

                BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
                bookingTypeTowhoVo.setProduct(product.get());
                bookingTypeTowhoVo.setToWhoTyp(EnumBookingOjbectType.Product);
                BookingResp resp = BookingResp.toResp(Quartet.with(x,bookingTypeTowhoVo,travelers,documents));

                */
            EntityModel entityModel = EntityModel.of(bookingResp);
            entityModel.add(linkTo(methodOn(BookingRestController.class).getReservation(x.getId())).withSelfRel());

            //  entityModel.add(linkTo(methodOn(BookingRestController.class).选择支付方式_并完成发出支付请求(x.getId(),null,null)).withRel("pay_url"));
            entityModel.add(linkTo(methodOn(BookingRestController.class).addTravelers(x.getId(),null)).withRel("addTraveler"));
            return entityModel;



        });

        return ResponseEntity.ok(assembler.toModel(page.map(e->{

            return e;
        })));


    }



    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/bookings/pc", produces = "application/json")
    public ResponseEntity<PagedModel> getPcBookingList(
            BookingQueryfieldsCriteria searchQuery,
            @PageableDefault(sort = {"createdDate",
                    "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable ,
                                                       PagedResourcesAssembler<EntityModel<BookingResp>>assembler) {


        logger.debug("搜索参数： {}",searchQuery.toString());

        BookingSpecification spec =
                new BookingSpecification(searchQuery);


        Page<Reservation> optionalProduct = reservationRepository.findAll(where(spec),pageable);

        Page<EntityModel<BookingResp>> page =  optionalProduct.map(x->{


            List<LineItem> lineItemList = lineItemRepository.findAllByBooking(x.getId());

            BookingResp bookingResp = BookingResp.toResp_LIST(Pair.with(x, lineItemList));

            Optional<Payment> optionalPayment = paymentService.getByBooking(x);

            if(optionalPayment.isPresent()){
                Payment payment = optionalPayment.get();



                PaymentResp paymentResp = PaymentResp.from(payment);

                List<EntityModel<PaymentMethodResp>>  entityModelList = paymentService.getPaymentMethods(payment);

                paymentResp.setMethods(entityModelList);

                EntityModel entityModel_payment= EntityModel.of(paymentResp);
                entityModel_payment.add(linkTo(methodOn(BookingRestController.class).refund(payment.getId(),null)).withRel("refund"));

                bookingResp.setPayment(entityModel_payment);
            }

            EntityModel entityModel = EntityModel.of(bookingResp);
            entityModel.add(linkTo(methodOn(BookingRestController.class).getBookingEdit(x.getId())).withSelfRel());

            //  entityModel.add(linkTo(methodOn(BookingRestController.class).选择支付方式_并完成发出支付请求(x.getId(),null,null)).withRel("pay_url"));
            entityModel.add(linkTo(methodOn(BookingRestController.class).addTravelers(x.getId(),null)).withRel("addTraveler"));
            return entityModel;



        });

        return ResponseEntity.ok(assembler.toModel(page.map(e->{

            return e;
        })));


    }






    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/bookings/backup", produces = "application/json")
    public ResponseEntity createBooking_bbbbbu(@RequestBody @Valid BookingSkuPojo pojo) {


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);




/*        BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
        bookingTypeTowhoVo.setToWhoTyp(pojo.getType());*/

/*        if(pojo.getType().equals(EnumBookingOjbectType.Voucher)){
            Optional<Campaign> optionalCampaign = campaignRepository.findById(pojo.getProductId());
            if(optionalCampaign.isEmpty()) {
                throw new BookNotFoundException(pojo.getProductId(),"找不到产品");
            }
            Campaign campaign = optionalCampaign.get();
            if(!campaign.getPay()){
                throw new BookNotFoundException(pojo.getProductId(),"改券不属于支付类型");
            }
            bookingTypeTowhoVo.setCampaign(optionalCampaign.get());
        }*/
/*        if(pojo.getType().equals(EnumBookingOjbectType.Product)){
            if(optionalProduct.isEmpty()) {
                throw new BookNotFoundException(pojo.getProductId(),"找不到产品");
            }
            bookingTypeTowhoVo.setProduct(optionalProduct.get());
        }
        */

        List<Product> productList = productRepository.findAllByCodeIn(pojo.getProducts().stream().map(e->e.getId()).collect(Collectors.toList()));

        Map<Long, Product> longProductMap = productList.stream().collect(Collectors.toMap(e->e.getId(),e->e));
        List<BookingTypeTowhoVo> list =  pojo.getProducts().stream().map(e->{

            Product product = longProductMap.get(e.getId());

            BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
            bookingTypeTowhoVo.setToWhoTyp(EnumBookingOjbectType.Product);
            bookingTypeTowhoVo.setProduct(product);
            bookingTypeTowhoVo.setQuantity(e.getQuantity().longValue());

            return bookingTypeTowhoVo;
        }).collect(Collectors.toList());

        Pair<Reservation, List<LineItem> > booking =null;/// bookingService.booking(list,pojo,userVo);

        Reservation reservation = booking.getValue0();



        BookingResp resp = BookingResp.toResp_LIST(booking);



/*
        List<EntityModel<PaymentMethodResp>>  paymentOptional = paymentService.getPayment( Arrays.asList(EnumPayChannel.wx,EnumPayChannel.alipay),reservation);
        resp.setPaymentMethods(paymentOptional);*/
        System.out.println("================================="+ resp.toString());
        return ResponseEntity.ok(resp);

    }









/*


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

*/





    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/price_types/{PRICE_TYPE_ID}/bookings", produces = "application/json")
    public ResponseEntity createBookingFromPriceType(@PathVariable long PRICE_TYPE_ID,@RequestBody @Valid BookingPojo pojo) {


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);

        BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();

        bookingTypeTowhoVo.setToWhoTyp(pojo.getType());

        Optional<PricingRate> pricingTypeOptional = pricingTypeRepository.findById(PRICE_TYPE_ID);

        if(pricingTypeOptional.isEmpty()){
            throw new BookNotFoundException(PRICE_TYPE_ID,"找不到产品");
        }

        PricingRate pricingRate = pricingTypeOptional.get();

        Optional<Product> productOptional = productRepository.findById(pricingRate.getProductId());

        if(productOptional.isEmpty()){
            throw new BookNotFoundException(PRICE_TYPE_ID,"找不到产品");
        }

        Product product = productOptional.get();


        bookingTypeTowhoVo.setProduct(product);


        Pair<Reservation, BookingTypeTowhoVo> booking = bookingService.bookingOnlySku(bookingTypeTowhoVo,pojo,userVo);

        Reservation reservation = booking.getValue0();



        BookingResp resp = BookingResp.toResp(booking);


        System.out.println("================================="+ resp.toString());
        return ResponseEntity.ok(resp);

    }















    @GetMapping(value = "/products/{PRODUCT_ID}/bookings/page", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_createBooking(@PathVariable long PRODUCT_ID, @RequestParam(required = false) List<Long> skus) {


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVoWithUser(authentication);

        System.out.println("----参数 ------"+skus);
        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }


        Product product = validatorOptional.get();

        List<PricingRate> pricingRates = pricingTypeRepository.findAllById(skus);//.stream()

/*        List<PricingType> pricingTypes = pricingTypeRepository.findByProductId(product.getId()).stream()
                .filter(e->{
                    if(skus != null){
                        return true;// skus.contains(e.getId());

                    }else{
                        return true;
                    }
                })
                .collect(Collectors.toList());*/


        //Map<Long,PricingType> longPricingTypeMap =  pricingTypes.stream().collect(Collectors.toMap(e->e.getId(),e->e));






        List<BookingRule> bookingRuleList = bookingRuleRepository.findByProduct(product.getId());

        List<AvailabilityCalendarVO> availabilityCalendarVOList = availabilityService.getAvailability_(product,bookingRuleList, LocalDate.now().plusDays(5));



        ProductBookingResp productResp =  ProductBookingResp.from(product);





        BookingSkuPojo trial_pojo = new BookingSkuPojo();



        BookingSkuPojo.Shipping shipping_POJO = new BookingSkuPojo.Shipping();

        productResp.setShippingAddressCollection(product.getShippingAddressCollection());

        if(product.getShippingAddressCollection().equals(EnumShippingAddressCollection.required)){


            ProductBookingResp.Shipping shipping = new ProductBookingResp.Shipping();


            List<ShippingCardAddress> shippingCardAddresses  = shippingAddressRepository.findAllByUser(userVo.getUser_id());

            Optional<ShippingCardAddress> shippingCardAddressOptional = shippingCardAddresses.stream().filter(e->e.getDefault_()).findAny();
            if(shippingCardAddressOptional.isPresent()){
                ShippingCardAddress shippingCardAddress = shippingCardAddressOptional.get();

                shipping.setDefaultShippingAddress(ShippingAddressResp.from(shippingCardAddress));
            }
            List<ShippingRate> shippingRates = shippingRatePlanRepository.findAll();

            List<ShippingRateResp> shippingRateRespList = shippingRates.stream().map(e->{
                ShippingRateResp shippingRateResp = ShippingRateResp.shimpleFrom(e);

                return shippingRateResp;
            }).collect(Collectors.toList());
            shippingRateRespList.get(0).setDefault(true);
            shipping_POJO.setShippingRate(shippingRateRespList.get(0).getId());

            shipping.setShipping_options(shippingRateRespList);




            productResp.setShipping(shipping);
        }




        trial_pojo.setShipping(shipping_POJO);





        productResp.setSkus(pricingRates.stream().map(pricingType->{

            PricingTypeResp pricingTypeResp_default = PricingTypeResp.sku(pricingType);
            pricingTypeResp_default.setImage(fileStorageService.loadDocumentWithDefault(EnumDocumentType.product_thumb,product.getCode()));

            return pricingTypeResp_default;
        }).collect(Collectors.toList()));


//https://stripe.com/docs/payments/checkout/cross-sells#create-cross-sell

        extraService.prepay_booking_page(product,productResp);
        crossSellService.prepay_booking_page(product,productResp);


        ProductResp.Availability availability1 = ProductResp.Availability.from(product);
        availability1.setBookingAvailability(availabilityCalendarVOList);

        availability1.setFixedPassExpiryDate(product.getFixedPassExpiryDate());
        availability1.setPassValidForDays(product.getPassValidForDays());
        availability1.setPassExpiry(product.getPassExpiry());
        availability1.setPassCapacityType(product.getPassCapacityType());
        availability1.setPassCapacity(product.getPassCapacity());

        productResp.setAvailability(EntityModel.of(availability1));




     //   productResp.setBookingAvailability(availabilityCalendarVOList);

/*        PricingType pricingType = longPricingTypeMap.get(product.getDefault_price());
        PricingTypeResp pricingTypeResp_default = PricingTypeResp.sku(pricingType);
        productResp.setDefault_price(pricingTypeResp_default);*/

        ReferralPojo pojo = new ReferralPojo();
        pojo.setFill_up_passager_info(null);

       String message = String.format("我正在为你购买【%s】的门票，请填写以下游客信息，我们一起出游吧",product.getName());
        Referral referral = referralService.create(userVo,EnumReferralType.fill_up_passager_info,message,pojo);





        String link = linkTo(methodOn(ReferralRestController.class).getReferral(referral.getCode())).withRel("create").getHref();

        String url_with_link = String.format(EnumReferralType.fill_up_passager_info.getUrl(),link);





        List<Pass> passList = passRepository.findByUser(userVo.getUser_id());

        passList.stream().map(e->PassResp.SimplefromWithId(e)).collect(Collectors.toList());









        trial_pojo.setSkus(pricingRates.stream().map(e->{
            BookingSkuPojo.ProductReq.Sku sku = new BookingSkuPojo.ProductReq.Sku();
            sku.setId(e.getId());
            sku.setQuantity(e.getRestriction_MinQuantity());
            sku.setTravelers(new ArrayList<>());
            return sku;
        }).collect(Collectors.toList()));


        trial_pojo.setPasses(new ArrayList<>());
        trial_pojo.setExtras(new ArrayList<>());



        BookingTrialResp bookingTrialResp = bookingforsku_preview_price__(trial_pojo);



        Map map = Map.of(


                "vouchers",Arrays.asList(Map.of("id",123,"amount",10,"text","优惠券"),
                        Map.of("id",12,"amount",100,"text","优惠券")),

                "passes",passList.stream().map(e->{
                    PassResp passResp= PassResp.SimplefromWithId(e);
                    passResp.setCover(fileStorageService.loadDocumentWithDefault(EnumDocumentType.card_cover,e.getProductPassUuid()));

                    return passResp;


                }).collect(Collectors.toList()),

                "product",productResp,

                "summary",bookingTrialResp.getSummary(),//Arrays.asList(map_subTotal,map_shippingFee,map_total_discount_amount,map_total_amount),

                "total",bookingTrialResp.getTotal(),//map_total_amount,

"referral",Map.of(
                "referral_path",url_with_link,
                "referral_id",referral.getCode(),
                //"referral_upath",url_with_link,
                "referral_url",link)



        );


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(DocumentRestController.class).idScan(null)).withRel("idcardOcr"));



        entityModel.add(linkTo(methodOn(BookingRestController.class).createBooking_sku(null)).withRel("booking"));

        entityModel.add(linkTo(methodOn(PassengerRestController.class).createPassenger(userVo.getUser_id(),null)).withRel("createPassenger"));

        entityModel.add(linkTo(methodOn(PassengerRestController.class).listPassenger(userVo.getUser_id(),null,null)).withRel("getPassengerList"));

        entityModel.add(linkTo(methodOn(AddressRestController.class).listAddress(userVo.getUser_id(),null,null)).withRel("listAddress"));
        entityModel.add(linkTo(methodOn(AddressRestController.class).Page_listAddress(userVo.getUser_id())).withRel("Page_listAddress"));
        entityModel.add(linkTo(methodOn(AddressRestController.class).createaUserAddress(userVo.getUser_id(),null)).withRel("createAddress"));

        entityModel.add(linkTo(methodOn(ReferralRestController.class).createReferral(userVo.getUser_id(),null)).withRel("getUrl_for_fill_up_info"));

        entityModel.add(linkTo(methodOn(BookingRestController.class).bookingforsku_preview_price(null)).withRel("check_trial"));


        return entityModel;
    }









    @GetMapping(value = "/products/{PRODUCT_ID}/bookings/sku_page", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_BookingSku(@PathVariable long PRODUCT_ID, @RequestParam(required = false) List<Long> skus) {


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);

        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }

        Product product = validatorOptional.get();

        List<BookingRule> bookingRuleList = bookingRuleRepository.findByProduct(product.getId());
        List<AvailabilityCalendarVO> availabilityCalendarVOList = availabilityService.getAvailability_(product,bookingRuleList, LocalDate.now().plusDays(5));



        System.out.println("=========== skus "+skus);
        List<Sku> skuList = zonePricingRepository.findAllById(skus);

        if(skuList.isEmpty()){
            throw new  BookNotFoundException(Enumfailures.not_found,"找不到skus zonePricing"+ skus.toString());
        }

        ProductBookingResp productResp =  ProductBookingResp.from(product);




        productResp.setSkus(skuList.stream().map(pricingType->{

            PricingRate pricingRate1 = pricingTypeRepository.findById(pricingType.getPricingType()).get();
            PricingTypeResp pricingTypeResp_default = PricingTypeResp.sku(pricingRate1);
            pricingTypeResp_default.setImage(fileStorageService.loadDocumentWithDefault(EnumDocumentType.product_thumb,product.getCode()));
            pricingTypeResp_default.setId(pricingType.getId());
            return pricingTypeResp_default;
        }).collect(Collectors.toList()));


        productResp.setShippable(product.getShippable());
        productResp.setShippable(true);

        productResp.setShippingAddressCollection(product.getShippingAddressCollection());

        List<ShippingRate> shippingRates = shippingRatePlanRepository.findAll();
        productResp.setShipping_options(shippingRates.stream().map(e->{
            ShippingRateResp shippingRateResp = ShippingRateResp.shimpleFrom(e);
            shippingRateResp.setDefault(true);
            return shippingRateResp;
        }).collect(Collectors.toList()));

        ProductResp.Availability availability1 = ProductResp.Availability.from(product);
        availability1.setBookingAvailability(availabilityCalendarVOList);

        availability1.setFixedPassExpiryDate(product.getFixedPassExpiryDate());
        availability1.setPassValidForDays(product.getPassValidForDays());
        availability1.setPassExpiry(product.getPassExpiry());
        availability1.setPassCapacityType(product.getPassCapacityType());
        availability1.setPassCapacity(product.getPassCapacity());

        productResp.setAvailability(EntityModel.of(availability1));

//https://stripe.com/docs/payments/checkout/cross-sells#create-cross-sell
        extraService.prepay_booking_page(product,productResp);
        crossSellService.prepay_booking_page(product,productResp);

       // productResp.setBookingAvailability(availabilityCalendarVOList);


        ReferralPojo pojo = new ReferralPojo();
        pojo.setFill_up_passager_info(null);

        String message = String.format("我正在为你购买【%s】的门票，请填写以下游客信息，我们一起出游吧",product.getName());
        Referral referral = referralService.create(userVo,EnumReferralType.fill_up_passager_info,message,pojo);


        String link = linkTo(methodOn(ReferralRestController.class).getReferral(referral.getCode())).withRel("create").getHref();

        String url_with_link = String.format(EnumReferralType.fill_up_passager_info.getUrl(),link);

        Map map_subTotal = Map.of("name","小计","subtotal",22,"key","subTotal");
        Map map_shippingFee = Map.of("name","运费","fees",1,"key","shippingFee");
        Map map_total_discount_amount = Map.of("name","折扣","amount",-2,"key","total_discount_amount");
        Map map_total_amount = Map.of("name","合计","total",0,"key","total_amount");


        List<Pass> passList = passRepository.findByUser(userVo.getUser_id());

        passList.stream().map(e->PassResp.SimplefromWithId(e)).collect(Collectors.toList());


        BookingSkuPojo pojo1 = new BookingSkuPojo();



        BookingTrialResp bookingTrialResp = booking_trial(pojo1);



        //bookingTrialResp.setSummary(Arrays.asList(map_subTotal,map_shippingFee,map_total_discount_amount,map_total_amount));
        Map map = Map.of(
                "product",productResp,
                "payment",Arrays.asList(map_subTotal,map_shippingFee,map_total_discount_amount,map_total_amount),

                "summary",bookingTrialResp.getSummary(),//Arrays.asList(map_subTotal,map_shippingFee,map_total_discount_amount,map_total_amount),
                "vouncher",Map.of("amount",10,"text","优惠券"),

                "passes",passList.stream().map(e->PassResp.SimplefromWithId(e)).collect(Collectors.toList()),
                "total",bookingTrialResp.getTotal_amount(),//map_total_amount,

                "referral_path",url_with_link,
                "referral_id",referral.getCode(),
                "referral_url",link
        );



        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(DocumentRestController.class).idScan(null)).withRel("idcardOcr"));
        entityModel.add(linkTo(methodOn(BookingRestController.class).bookingforsku_preview(null)).withRel("check_trial"));

        entityModel.add(linkTo(methodOn(BookingRestController.class).bookingforsku(null)).withRel("booking"));

        entityModel.add(linkTo(methodOn(PassengerRestController.class).createPassenger(userVo.getUser_id(),null)).withRel("createPassenger"));

        entityModel.add(linkTo(methodOn(PassengerRestController.class).listPassenger(userVo.getUser_id(),null,null)).withRel("getPassengerList"));


        entityModel.add(linkTo(methodOn(AddressRestController.class).createaUserAddress(userVo.getUser_id(),null)).withRel("createAddress"));


        entityModel.add(linkTo(methodOn(AddressRestController.class).listAddress(userVo.getUser_id(),null,null)).withRel("listAddress"));

        entityModel.add(linkTo(methodOn(ReferralRestController.class).createReferral(userVo.getUser_id(),null)).withRel("getUrl_for_fill_up_info"));



        return entityModel;
    }






    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/products/{PRODUCT_ID}/bookings", produces = "application/json")
    public ResponseEntity createBooking(@PathVariable long PRODUCT_ID,@RequestBody @Valid BookingPojo pojo) {


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);

/*
    //    BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo()
        //  bookingTypeTowhoVo.setToWhoTyp(pojo.getType());*/



        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);


        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }
        Product product_ = validatorOptional.get();


     //   bookingTypeTowhoVo.setProduct(product);



        List<Product> productList = Arrays.asList(product_);//productRepository.findAllByCodeIn(pojo.getProducts().stream().map(e->e.getId()).collect(Collectors.toList()));

        product_.setCode(pojo.getProducts().get(0).getId());

        Map<String, Product> longProductMap = productList.stream().collect(Collectors.toMap(e->e.getCode(),e->e));
        List<BookingTypeTowhoVo> list =  pojo.getProducts().stream().map(e->{

            Product product = longProductMap.get(e.getId());
            Assert.notNull(product, "产品不能为空");
            BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
            bookingTypeTowhoVo.setToWhoTyp(EnumBookingOjbectType.Product);
            bookingTypeTowhoVo.setProduct(product);

            if(e.getSkus() != null){
                if(e.getSkus().size()==0){
                    bookingTypeTowhoVo.setQuantity(e.getQuantity().longValue());
                }

                List<PricingRate> priceType = pricingTypeRepository.findAllById(e.getSkus().stream().map(ee->ee.getId()).collect(Collectors.toList()));

                if(priceType.size() != e.getSkus().size()){
                    throw new BookNotFoundException(Enumfailures.not_found,"找不到"+product.getId()+ " 得价格");

                }
                Map<Long, PricingRate> pricingTypeMap = priceType.stream().collect(Collectors.toMap(ee->ee.getId(), ee->ee));


                bookingTypeTowhoVo.setSkus(e.getSkus().stream().map(ee->{
                    PricingRate pricingRate = pricingTypeMap.get(ee.getId());
                    BookingTypeTowhoVo.Sku sku = new BookingTypeTowhoVo.Sku();
                    sku.setId(pricingRate);
                    sku.setQuantity(ee.getQuantity());
                    return sku;
                }).collect(Collectors.toList()));
            }else{
                bookingTypeTowhoVo.setQuantity(e.getQuantity().longValue());

                List<PricingRate> priceType = pricingTypeRepository.findByProductId(product.getId());//.collect(Collectors.toList()));
                PricingRate pricingRate = priceType.get(0);
                BookingTypeTowhoVo.Sku sku = new BookingTypeTowhoVo.Sku();
                sku.setId(pricingRate);
                sku.setQuantity(e.getQuantity());

                bookingTypeTowhoVo.setSkus(Arrays.asList(sku));
            }


            return bookingTypeTowhoVo;
        }).collect(Collectors.toList());

        Pair<Reservation, List<LineItem> > booking =null;// bookingService.booking(list,pojo,userVo);

        Reservation reservation = booking.getValue0();



        Reservation reservation_after = booking.getValue0();

        BookingResp resp = BookingResp.toResp_LIST(booking);


        if(EnumBookingType.Free.equals(reservation.getType())){
            paymentFreeService.freePayFree(reservation,userVo);
        }


        if(EnumBookingType.Standard.equals(reservation.getType())){
            Payment payment = paymentService.createPayment(
                    reservation.getPaymentMethods_json(),
                    reservation.getAmount(),
                    userVo,
                    reservation);
            payment.setRecipient(reservation.getSupplier());
        }


        EntityModel entityModel = EntityModel.of(resp);

        entityModel.add(linkTo(methodOn(BookingRestController.class).选择支付方式_并完成发出支付请求(reservation.getId(),null)).withRel("pay_url"));


        System.out.println("================================="+ resp.toString());
        return ResponseEntity.ok(entityModel);

    }





    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/bookings", produces = "application/json")
    public ResponseEntity createBooking_sku(@RequestBody @Valid BookingSkuPojo pojo) {


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);
        List<PricingRate> pricingRates = pricingTypeRepository.findAllById(pojo.getSkus().stream().map(ee->ee.getId()).collect(Collectors.toList()));

        Map<Long, PricingRate> pricingTypeMap = pricingRates.stream().collect(Collectors.toMap(e->e.getId(), e->e));



        List<Product> productList = productRepository.findAllById(pricingRates.stream().map(e->e.getProductId()).collect(Collectors.toList()));


        Map<Long, Product> longProductMap = productList.stream().collect(Collectors.toMap(e->e.getId(),e->e));


        BookingSkuPojo pojo1 = new BookingSkuPojo();


        pojo1.setSkus(pojo.getSkus().stream().map(e->{
            BookingSkuPojo.ProductReq.Sku sku = new BookingSkuPojo.ProductReq.Sku();
            sku.setId(e.getId());
            sku.setQuantity(e.getQuantity());
            e.getTravelers().stream().forEach(tttt->System.out.println("--------"+tttt.toString()));

            sku.setTravelers(e.getTravelers());
            return sku;
        }).collect(Collectors.toList()));


        pojo1.setPasses(pojo.getPasses());
        pojo1.setExtras(pojo.getExtras());




        BookingTrialResp bookingTrialResp = bookingforsku_preview_price__(pojo1);




        List<BookingSkuPojo.TravelerReq> travelerReqs =   pojo.getSkus().stream().map(e->{
            PricingRate pricingRate = pricingTypeMap.get(e.getId());
            Product product = longProductMap.get(pricingRate.getProductId());
            if(product.getRestriction_passenger_identity_documents_required()){

                return e.getTravelers();
            }else{

                return new ArrayList<BookingSkuPojo.TravelerReq>();
            }


        }).flatMap(List::stream).collect(Collectors.toList());





        realnameAuthsService.checkRealname(travelerReqs);







        List<BookingTypeTowhoVo> list =  pojo.getSkus().stream().map(e->{
            PricingRate pricingRate = pricingTypeMap.get(e.getId());

            Product product = longProductMap.get(pricingRate.getProductId());
            Assert.notNull(product, "产品不能为空");
            Optional<Partner> partner = partnerService.getPartner(product.getSupplierId(),product.getSupplierId());

            BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
            bookingTypeTowhoVo.setToWhoTyp(EnumBookingOjbectType.Product);

            bookingTypeTowhoVo.setTraveler(e.getTravelers());
            if(partner.isEmpty()){
                Partner partner1 = partner.get();
                Optional<PartnerShareRatePlan> partnerList = partnerShareRatePlanRepository.findAllByPartnerAndProduct(partner1.getId(),product.getId());

                NegotiatedPricingType negotiatedPricingType = priceService.fill(pricingRate,partnerList);

                bookingTypeTowhoVo.setNegotiatedSku(negotiatedPricingType);

            }




            bookingTypeTowhoVo.setProduct(product);
            bookingTypeTowhoVo.setSku(pricingRate);
            bookingTypeTowhoVo.setQuantity(e.getQuantity().longValue());

            if(product.getType().equals(EnumProductType.Pass)){
                bookingTypeTowhoVo.setPurchaseMode(EnumPurchaseMode.LineItem_every_traveler);

            }




/*
            BookingTypeTowhoVo.Sku sku = new BookingTypeTowhoVo.Sku();
            sku.setId(pricingType);
            sku.setQuantity(e.getQuantity());
            bookingTypeTowhoVo.setSku(sku);*/
/*

            if(e.getSkus() != null){
                if(e.getSkus().size()==0){
                }

                List<PricingType> priceType = pricingTypeRepository.findAllById(e.getSkus().stream().map(ee->ee.getId()).collect(Collectors.toList()));

                if(priceType.size() != e.getSkus().size()){
                    throw new BookNotFoundException(Enumfailures.not_found,"找不到"+product.getId()+ " 得价格");

                }
                Map<Long,PricingType> pricingTypeMap = priceType.stream().collect(Collectors.toMap(ee->ee.getId(), ee->ee));



                bookingTypeTowhoVo.setSkus(e.getSkus().stream().map(ee->{
                    PricingType pricingType = pricingTypeMap.get(ee.getId());
                    BookingTypeTowhoVo.Sku sku = new BookingTypeTowhoVo.Sku();
                    sku.setId(pricingType);
                    sku.setQuantity(ee.getQuantity());
                    return sku;
                }).collect(Collectors.toList()));
            }else{
                bookingTypeTowhoVo.setQuantity(e.getQuantity().longValue());

                List<PricingType> priceType = pricingTypeRepository.findByProductId(product.getId());//.collect(Collectors.toList()));
                PricingType pricingType = priceType.get(0);
                BookingTypeTowhoVo.Sku sku = new BookingTypeTowhoVo.Sku();
                sku.setId(pricingType);
                sku.setQuantity(e.getQuantity());

                bookingTypeTowhoVo.setSkus(Arrays.asList(sku));
            }
*/


            return bookingTypeTowhoVo;
        }).collect(Collectors.toList());







        PlatUserVo platUserVo = new PlatUserVo();
        platUserVo.setUserVo(userVo);
        platUserVo.setPlatform(EnumPlatform.DERECT);

        Triplet<Reservation,List<LineItem>,PlatUserVo > booking = bookingService.bookingWithPayment(list,pojo,platUserVo);

        Reservation reservation = booking.getValue0();


        BookingResp resp = BookingResp.toResp_LIST(Pair.with(booking.getValue0(),booking.getValue1()));



        EntityModel entityModel = EntityModel.of(resp);

        entityModel.add(linkTo(methodOn(BookingRestController.class).选择支付方式_并完成发出支付请求(reservation.getId(),null)).withRel("pay_url"));


        System.out.println("================================="+ resp.toString());
        return ResponseEntity.ok(entityModel);

    }



    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/sku/{PRICE_TYPE_ID}/bookings/quick", produces = "application/json")
    public Map quickPayment(@PathVariable Long PRICE_TYPE_ID,HttpServletRequest request) {


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);

        Optional<PricingRate> pricingRateOptional = pricingTypeRepository.findById(PRICE_TYPE_ID);

        PricingRate pricingRate = pricingRateOptional.get();

        Optional<Product> productOptional = productRepository.findById(pricingRate.getProductId());

        BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
        bookingTypeTowhoVo.setToWhoTyp(EnumBookingOjbectType.Product);

        bookingTypeTowhoVo.setProduct(productOptional.get());
        bookingTypeTowhoVo.setSku(pricingRate);
        bookingTypeTowhoVo.setQuantity(1l);


        BookingSkuPojo.TravelerReq traveler = new BookingSkuPojo.TravelerReq();
        traveler.setName(userVo.getReal_name());
        traveler.setId_card(userVo.getId_card());
        traveler.setPhone(userVo.getPhone());
        bookingTypeTowhoVo.setTraveler(Arrays.asList(traveler));







        List<BookingTypeTowhoVo> list = Arrays.asList(bookingTypeTowhoVo);

/*        pojo.getSkus().stream().map(e->{
            PricingRate pricingRate = pricingTypeMap.get(e.getId());

            Product product = longProductMap.get(pricingRate.getProductId());
            Assert.notNull(product, "产品不能为空");


        }).collect(Collectors.toList());*/


        PlatUserVo platUserVo = new PlatUserVo();
        platUserVo.setPlatform(EnumPlatform.DERECT);
        platUserVo.setUserVo(userVo);

        BookingSkuPojo bookingSkuPojo = new BookingSkuPojo();


        Triplet<Reservation,List<LineItem>,PlatUserVo > booking = bookingService.bookingWithPayment(list,bookingSkuPojo,platUserVo);

        Reservation reservation = booking.getValue0();


        BookingResp resp = BookingResp.toResp_LIST(Pair.with(booking.getValue0(),booking.getValue1()));

        logger.debug("用户id:{}",userVo.getUser_id());
        Optional<Openid> optional = userAuthorityService.checkWeixinBind(userVo.getUser_id());
        ChargeMetadataVo chargeMetadataVo = new ChargeMetadataVo();
        //  chargeMetadataVo.setCampaign(campaign.getId());
        //   chargeMetadataVo.setCampaign_code(campaign.getCode());
        //   chargeMetadataVo.setVolume_up_voucher(voucher.getId());
        chargeMetadataVo.setPayer(userVo.getUser_id());
        // chargeMetadataVo.setBooking(reservation.getId());
        String metadata = new Gson().toJson(chargeMetadataVo);

        List<Payment> optionalReservation = paymentRepository.findByReference(booking.getValue0().getCode());
        Payment payment = optionalReservation.get(0);

        String ip = HttpUtils.getRequestIP(request);

        int totalAmount = payment.getAmount();
        // 微信的支付单位是分所以要转换一些单位
        long  totalproce =Long.parseUnsignedLong(totalAmount+"");//Long.parseUnsignedLong(totalAmount*100+"");

        Map  charge = paymentService.createCharge(ip,payment.getCode(),reservation,totalproce,optional.get().getOpenid(),userVo,metadata, payment);



        return charge;

    }


    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/bookings/check_trial", produces = "application/json")
    public ResponseEntity bookingforsku_preview(@RequestBody @Valid BookingSkuPojo pojo) {




        EntityModel entityModel = EntityModel.of(booking_trial(pojo));


        return ResponseEntity.ok(entityModel);

    }





    private BookingTrialResp booking_trial(BookingSkuPojo pojo) {


        System.out.println("参数"+pojo.toString());

        List<Sku> pricingTypes = zonePricingRepository.findAllById(pojo.getSkus().stream().map(ee->ee.getId()).collect(Collectors.toList()));

        Map<Long, Sku> pricingTypeMap = pricingTypes.stream().collect(Collectors.toMap(e->e.getId(),e->e));



        List<Product> productList = productRepository.findAllById(pricingTypes.stream().map(e->e.getProduct()).collect(Collectors.toList()));


        Map<Long, Product> longProductMap = productList.stream().collect(Collectors.toMap(e->e.getId(),e->e));


        List<BookingTypeTowhoVoSku> list =  pojo.getSkus().stream().map(e->{

            System.out.println("参数"+e.toString());

            Sku sku = pricingTypeMap.get(e.getId());


            PricingRate pricingRate =  pricingTypeRepository.findById(sku.getPricingType()).get();

            Product product = longProductMap.get(sku.getProduct());
            Assert.notNull(product, "产品不能为空");
            Optional<Partner> partner = partnerService.getPartner(product.getSupplierId(),product.getSupplierId());

            BookingTypeTowhoVoSku bookingTypeTowhoVo = new BookingTypeTowhoVoSku();
            bookingTypeTowhoVo.setToWhoTyp(EnumBookingOjbectType.Product);

            bookingTypeTowhoVo.setTraveler(e.getTravelers());
            if(partner.isEmpty()){
                Partner partner1 = partner.get();
                Optional<PartnerShareRatePlan> partnerList = partnerShareRatePlanRepository.findAllByPartnerAndProduct(partner1.getId(),product.getId());

                NegotiatedPricingType negotiatedPricingType = priceService.fill(pricingRate,partnerList);

                bookingTypeTowhoVo.setNegotiatedSku(negotiatedPricingType);

            }


            bookingTypeTowhoVo.setProduct(product);
            bookingTypeTowhoVo.setSku(sku);
            bookingTypeTowhoVo.setPricingType(pricingRate);
            bookingTypeTowhoVo.setQuantity(e.getQuantity().longValue());

            return bookingTypeTowhoVo;
        }).collect(Collectors.toList());
        BookingTrialResp bookingTrialResp = new BookingTrialResp();


        List<Pass> passList = passRepository.findAllById(pojo.getPasses());


        bookingTrialResp.setSubTotal(Long.valueOf(list.stream().mapToLong(e->e.getQuantity()*e.getPricingType().getRetail()).sum()).intValue());


        bookingTrialResp.setTotal_discount_amount(0);
        bookingTrialResp.setHeroCard_amount(20);
        bookingTrialResp.setShippingFee(1);

        if(passList.size()> 0){
            BookingTypeTowhoVoSku sku =  list.get(0);
            sku.setQuantity(sku.getQuantity()-1);
            bookingTrialResp.setTotal_discount_amount(sku.getPricingType().getPrice());
        }
        bookingTrialResp.setAmount_due(Long.valueOf(list.stream().mapToLong(e->e.getQuantity()*e.getPricingType().getRetail()).sum()).intValue());

        bookingTrialResp.setAmount(Long.valueOf(list.stream().mapToLong(e->e.getQuantity()*e.getPricingType().getRetail()).sum()).intValue());



        Map map_subTotal = Map.of("name","小计","amount",bookingTrialResp.getSubTotal(),"key","subTotal");
        Map map_shippingFee = Map.of("name","运费","amount",0,"key","shippingFee");
        Map map_total_discount_amount = Map.of("name","折扣","amount",-bookingTrialResp.getTotal_discount_amount(),"key","total_discount_amount");
        Map map_total_amount = Map.of("name","合计","amount",bookingTrialResp.getAmount_due(),"key","total_amount");
        bookingTrialResp.setSummary(Arrays.asList(map_subTotal,map_shippingFee,map_total_discount_amount,map_total_amount));

        bookingTrialResp.setTotal(map_total_amount);



        bookingTrialResp.setTotal_amount(bookingTrialResp.getSubTotal()- bookingTrialResp.getShippingFee()- bookingTrialResp.getTotal_discount_amount() - bookingTrialResp.getHeroCard_amount());


        return bookingTrialResp;



    }

    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/bookings/check_trial_proceType", produces = "application/json")
    public ResponseEntity bookingforsku_preview_price(@RequestBody @Valid BookingSkuPojo pojo) {


        BookingTrialResp bookingTrialResp = bookingforsku_preview_price__(pojo);


        EntityModel entityModel = EntityModel.of(bookingTrialResp);


        return ResponseEntity.ok(entityModel);

    }


    private BookingTrialResp bookingforsku_preview_price__(BookingSkuPojo pojo) {


        System.out.println("参数"+pojo.toString());



        List<PricingRate> pricingRates = pricingTypeRepository.findAllById(pojo.getSkus().stream().map(ee->ee.getId()).collect(Collectors.toList()));

        Map<Long, PricingRate> pricingTypeMap = pricingRates.stream().collect(Collectors.toMap(e->e.getId(), e->e));



        List<Product> productList = productRepository.findAllById(pricingRates.stream().map(e->e.getProductId()).collect(Collectors.toList()));


        Map<Long, Product> longProductMap = productList.stream().collect(Collectors.toMap(e->e.getId(),e->e));


        List<BookingTypeTowhoVoSku> list =  pojo.getSkus().stream().map(e->{

            System.out.println("参数"+e.toString());

            PricingRate sku = pricingTypeMap.get(e.getId());


            //   PricingType pricingType =  pricingTypeRepository.findById(sku.getPricingType()).get();

            Product product = longProductMap.get(sku.getProductId());
            Assert.notNull(product, "产品不能为空");
            Optional<Partner> partner = partnerService.getPartner(product.getSupplierId(),product.getSupplierId());

            BookingTypeTowhoVoSku bookingTypeTowhoVo = new BookingTypeTowhoVoSku();
            bookingTypeTowhoVo.setToWhoTyp(EnumBookingOjbectType.Product);

            bookingTypeTowhoVo.setTraveler(e.getTravelers());
/*            if(partner.isEmpty()){
                Partner partner1 = partner.get();
                Optional<PartnerShareRatePlan> partnerList = partnerShareRatePlanRepository.findAllByPartnerAndProduct(partner1.getId(),product.getId());

                NegotiatedPricingType negotiatedPricingType = priceService.fill(pricingType,partnerList);

                bookingTypeTowhoVo.setNegotiatedSku(negotiatedPricingType);

            }*/


            bookingTypeTowhoVo.setProduct(product);
            bookingTypeTowhoVo.setSku(new Sku());
            bookingTypeTowhoVo.setPricingType(sku);
            bookingTypeTowhoVo.setQuantity(e.getQuantity().longValue());

            return bookingTypeTowhoVo;
        }).collect(Collectors.toList());
        BookingTrialResp bookingTrialResp = new BookingTrialResp();





        List<Pass> passList = passRepository.findAllById(pojo.getPasses());


        Double tot =  extraService.getTotalPrice(pojo.getExtras());

        Double subTotal = list.stream().mapToLong(e->e.getQuantity()*e.getPricingType().getRetail()).sum()+0d;

        subTotal += tot;

        Double total_discount_amount = 0d;

        if(passList.size()> 0){
            BookingTypeTowhoVoSku sku =  list.get(0);
            sku.setQuantity(sku.getQuantity()-1);
            total_discount_amount = sku.getPricingType().getPrice()+0d;
        }
/*
        "subTotal": 22,// 小计
                "shippingFee": 1, // 运费
                "total_discount_amount": 1, //折扣
                "total_amount": 0 // 合计*/
        bookingTrialResp.setSubTotal(subTotal.intValue());



        bookingTrialResp.setHeroCard_amount(20);


        Double shippingFee = 0d;
        if(pojo.getShipping()!= null && pojo.getShipping().getShippingRate()!= null){
            Optional<ShippingRate> shippingRates = shippingRatePlanRepository.findById(pojo.getShipping().getShippingRate());
            if(shippingRates.isEmpty()){


                bookingTrialResp.setShippingFee(0);
            }else{
                ShippingRate shippingRate = shippingRates.get();
                shippingFee = Integer.valueOf(shippingRate.getFixed_amount()).doubleValue();

                bookingTrialResp.setShippingFee(Integer.valueOf(shippingRate.getFixed_amount()));
            }


        }







        bookingTrialResp.setTotal_discount_amount(total_discount_amount.intValue());

        bookingTrialResp.setAmount_due(Double.valueOf(subTotal+shippingFee - total_discount_amount).intValue());

        bookingTrialResp.setAmount(Double.valueOf(subTotal+shippingFee - total_discount_amount).intValue());


        Map map_subTotal = Map.of("name","小计","amount",bookingTrialResp.getSubTotal(),"key","subTotal");
        Map map_shippingFee = Map.of("name","运费","amount",shippingFee,"key","shippingFee");
        Map map_total_discount_amount = Map.of("name","折扣","amount",-bookingTrialResp.getTotal_discount_amount(),"key","total_discount_amount");
        Map map_total_amount = Map.of("name","合计","amount",bookingTrialResp.getAmount_due(),"key","total_amount");
        bookingTrialResp.setSummary(Arrays.asList(map_subTotal,map_shippingFee,map_total_discount_amount,map_total_amount));


        bookingTrialResp.setTotal(map_total_amount);



        return bookingTrialResp;



    }



    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/bookings/skus", produces = "application/json")
    public ResponseEntity bookingforsku(@RequestBody @Valid BookingSkuPojo pojo) {


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);

        List<Sku> pricingTypes = zonePricingRepository.findAllById(pojo.getSkus().stream().map(ee->ee.getId()).collect(Collectors.toList()));

        Map<Long, Sku> pricingTypeMap = pricingTypes.stream().collect(Collectors.toMap(e->e.getId(),e->e));



        List<Product> productList = productRepository.findAllById(pricingTypes.stream().map(e->e.getProduct()).collect(Collectors.toList()));


        Map<Long, Product> longProductMap = productList.stream().collect(Collectors.toMap(e->e.getId(),e->e));








        List<BookingTypeTowhoVoSku> list =  pojo.getSkus().stream().map(e->{


            Sku sku = pricingTypeMap.get(e.getId());


            PricingRate pricingRate =  pricingTypeRepository.findById(sku.getPricingType()).get();

            Product product = longProductMap.get(sku.getProduct());


            Assert.notNull(product, "产品不能为空");
            Optional<Partner> partner = partnerService.getPartner(product.getSupplierId(),product.getSupplierId());

            BookingTypeTowhoVoSku bookingTypeTowhoVo = new BookingTypeTowhoVoSku();
            bookingTypeTowhoVo.setToWhoTyp(EnumBookingOjbectType.Product);

            bookingTypeTowhoVo.setTraveler(e.getTravelers());
            if(partner.isEmpty()){
                Partner partner1 = partner.get();
                Optional<PartnerShareRatePlan> partnerList = partnerShareRatePlanRepository.findAllByPartnerAndProduct(partner1.getId(),product.getId());

                NegotiatedPricingType negotiatedPricingType = priceService.fill(pricingRate,partnerList);

                bookingTypeTowhoVo.setNegotiatedSku(negotiatedPricingType);

            }


            bookingTypeTowhoVo.setProduct(product);
            bookingTypeTowhoVo.setSku(sku);
            bookingTypeTowhoVo.setPricingType(pricingRate);
            bookingTypeTowhoVo.setQuantity(e.getQuantity().longValue());

            return bookingTypeTowhoVo;
        }).collect(Collectors.toList());

        Pair<Reservation, List<LineItem> > booking = bookingService.bookingsku(list,pojo,userVo);

        Reservation reservation = booking.getValue0();




        BookingResp resp = BookingResp.toResp_LIST(booking);


        if(EnumBookingType.Free.equals(reservation.getType())){
            paymentFreeService.freePayFree(reservation,userVo);
        }


        if(EnumBookingType.Standard.equals(reservation.getType())){
            Payment payment = paymentService.createPayment(
                    reservation.getPaymentMethods_json(),
                    reservation.getAmount(),
                    userVo,
                    reservation);
            payment.setRecipient(reservation.getSupplier());
        }


        EntityModel entityModel = EntityModel.of(resp);

        entityModel.add(linkTo(methodOn(BookingRestController.class).选择支付方式_并完成发出支付请求(reservation.getId(),null)).withRel("pay_url"));


        System.out.println("================================="+ resp.toString());
        return ResponseEntity.ok(entityModel);

    }



    @PostMapping(value = "/bookings/{RESERVATOIN_ID}/validation", produces = "application/json")
    public ResponseEntity<Reservation> offlineValidate(@PathVariable long RESERVATOIN_ID) {

        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isEmpty()) {
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());

        }
            Reservation reservation = optionalReservation.get();


        componentRightResolveService.resolve_(reservation,reservation.getFulfillment_behavior());



            return ResponseEntity.ok(reservation);


    }


    @PostMapping(value = "/bookings/{RESERVATOIN_ID}/cancel", produces = "application/json")
    public ResponseEntity<Reservation> cancel(@PathVariable long RESERVATOIN_ID,BookingCancelPojo cancelBookingRequest) {

        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isEmpty()) {
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());

        }
        Reservation reservation = optionalReservation.get();


        if(!reservation.getStatus().equals(EnumBookingStatus.Pending)){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"状态错误不是 pending ");

        }

        cancellationService.cancel(reservation,cancelBookingRequest);





        return ResponseEntity.ok(reservation);


    }

    @PostMapping(value = "/bookings/{RESERVATOIN_ID}/refunds", produces = "application/json")
    public ResponseEntity<Reservation> refund(@PathVariable long RESERVATOIN_ID, @RequestBody  RefundFromChannelReq refundReq) {

        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isEmpty()) {
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());

        }
        Reservation reservation = optionalReservation.get();


        if(!reservation.getStatus().equals(EnumBookingStatus.PAID)){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"状态错误不是 pending ");
        }

        PlatRefundVo platRefundVo = new PlatRefundVo();
       // platRefundVo.setSerial_no(data.getSerial_no());
      //  platRefundVo.setOrders_id(data.getOrders_id());
        platRefundVo.setPlatform(EnumPlatform.DERECT);
        Pair<Refund,PlatRefundVo> pair = bookingService.refund(platRefundVo,reservation);


        return ResponseEntity.ok(reservation);

    }

    @PostMapping(value = "/bookings/{RESERVATOIN_ID}/amendment", produces = "application/json")
    public ResponseEntity<Reservation> amendment(@PathVariable long RESERVATOIN_ID,BookingAmendmentReq cancelBookingRequest) {

        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isEmpty()) {
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());

        }
        Reservation reservation = optionalReservation.get();


        bookingService.amendment(reservation,cancelBookingRequest);



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
        if(optionalReservation.isEmpty()) {

            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());

        }
            Reservation reservation = optionalReservation.get();
            List<Campaign> documents = bookingService.listCampaigns(optionalReservation.get());
            //      ResponseEntity responseEntity = ResponseEntity.ok(DocumentResp.from(documents));

            return ResponseEntity.ok(documents);

    }




    @RequestMapping(value = "/bookings/{RESERVATOIN_ID}/payments", produces = "application/json")
    public Payment createPayment(@PathVariable long RESERVATOIN_ID,@RequestBody @Valid PaymentReq paymentReq) {




        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);

        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到订单");
        }
        Reservation reservation = optionalReservation.get();

        Payment payment = paymentService.createPayment(paymentReq,userVo,reservation);
        payment.setRecipient(reservation.getSupplier());



        return payment;
    }







    // TODO 下单后， 支付时 ，弹出选择 支付方式， 在这里。
    @GetMapping(value = "/bookings/{RESERVATOIN_ID}/payments", produces = "application/json")
    public EntityModel 选择支付方式_并完成发出支付请求(@PathVariable long RESERVATOIN_ID,HttpServletRequest request) {



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
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到订单");
        }
            Reservation reservation = optionalReservation.get();


        List<LineItem> lineItemList = lineItemRepository.findAllByBooking(reservation.getId());







      //  ChargeMetadataVo chargeMetadataVo = new ChargeMetadataVo();
       // chargeMetadataVo.setCampaign(campaign.getId());
       // chargeMetadataVo.setCampaign_code(campaign.getCode());
      //  chargeMetadataVo.setVolume_up_voucher(voucher.getId());
    //    chargeMetadataVo.setPayer(userVo.getUser_id());
     //   chargeMetadataVo.setBooking(reservation.getId());
    //    String metadata = gson.toJson(chargeMetadataVo);


        BookingResp bookingResp = BookingResp.toResp_LIST(Pair.with(reservation, lineItemList));


        List<Traveler> travelers = travelerRepository.findAllByBooking(reservation.getId());
        bookingResp.setTravelers(TravelerResp.Listfrom(travelers));

        if(reservation.getType().equals(EnumBookingType.Standard)){
            Payment payment = paymentRepository.findByReference(reservation.getCode()).get(0);

            PaymentResp paymentResp = PaymentResp.from(payment);

            List<EntityModel<PaymentMethodResp>>  entityModelList = paymentService.getPaymentMethods(payment);
            paymentResp.setMethods(entityModelList);
            EntityModel entityModel_payment= EntityModel.of(paymentResp);
            entityModel_payment.add(linkTo(methodOn(BookingRestController.class).refund(payment.getId(),null)).withRel("refund"));

            bookingResp.setPayment(entityModel_payment);
        }else{
        //    paymentResp.setMethods(Arrays.asList());
        }





        return EntityModel.of(bookingResp);



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

        Optional<Openid> optional = userAuthorityService.checkWeixinBind(userVo.getUser_id());


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

        Pair<Reservation, BookingTypeTowhoVo> booking = bookingService.bookingOnlySku(bookingTypeTowhoVo, pojo, userVo);

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
        Map  charge = paymentService.createCharge(ip,payment.getCode(),reservation,totalproce,optional.get().getOpenid(),userVo,metadata, payment);



        return charge;
    }
















    @GetMapping(value = "/bookings/{PAYMENT_ID}/主人卡支付", produces = "application/json")
    public EntityModel  主人卡支付(@PathVariable long PAYMENT_ID) {

        Optional<Payment> optionalReservation = paymentRepository.findById(PAYMENT_ID);

        if(optionalReservation.isEmpty()){
            System.out.println("找不到产品");
            throw new BookNotFoundException(Enumfailures.not_found,"找不到支付");
        }


        Payment payment = optionalReservation.get();


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);


        Optional<Reservation> reservationOptional = reservationRepository.findByCode(payment.getReference());



        if(reservationOptional.isPresent()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到订单");
        }


        Reservation reservation = reservationOptional.get();
        Charge charge = balancePaymentService.pay(payment,reservation,null,userVo);

        EntityModel entityModel = EntityModel.of(charge);

        entityModel.add(linkTo(methodOn(BalancePaymentRestController.class).doPay(charge.getId(),null)).withRel("pay"));

        return entityModel;
    }


    @GetMapping(value = "/bookings/{PAYMENT_ID}/惠民卡支付", produces = "application/json")
    public EntityModel  惠民卡支付(@PathVariable long PAYMENT_ID) {

        Optional<Payment> optionalReservation = paymentRepository.findById(PAYMENT_ID);

        if(optionalReservation.isEmpty()){
            System.out.println("找不到产品");
            throw new BookNotFoundException(Enumfailures.not_found,"找不到支付");
        }


        Payment payment = optionalReservation.get();


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);


        Optional<Reservation> reservationOptional = reservationRepository.findByCode(payment.getReference());



        if(reservationOptional.isPresent()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到订单");

        }
        Reservation reservation = reservationOptional.get();
        Charge charge = balancePaymentService.pay(payment,reservation,null,userVo);

        EntityModel entityModel = EntityModel.of(charge);

        entityModel.add(linkTo(methodOn(BalancePaymentRestController.class).doPay(charge.getId(),null)).withRel("pay"));

        return entityModel;
    }


    public <R> void setLines(List lines) {
        this.lines = lines;
    }

    public List getLines() {
        return lines;
    }
}