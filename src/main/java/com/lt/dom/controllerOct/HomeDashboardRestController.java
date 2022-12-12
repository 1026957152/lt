package com.lt.dom.controllerOct;

import com.github.binarywang.wxpay.bean.request.WxPayOrderQueryRequest;
import com.github.wxpay.sdk.WXPay;
import com.google.gson.Gson;
import com.lt.dom.OctResp.*;
import com.lt.dom.config.WxConfig;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.WindowsTicketBookingPojo;
import com.lt.dom.otcenum.EnumBookingOjbectType;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumPayChannel;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.BookingTypeTowhoVo;
import com.lt.dom.serviceOtc.AuthenticationFacade;
import com.lt.dom.serviceOtc.BookingServiceImpl;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.PaymentServiceImpl;
import com.lt.dom.serviceOtc.pay.WeixinPaymentServiceImpl;
import com.lt.dom.specification.BookingQueryfieldsCriteria;
import com.lt.dom.specification.BookingSpecification;
import com.lt.dom.vo.ChargeMetadataVo;
import com.lt.dom.vo.QrcodeReaderResult;
import com.lt.dom.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
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
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class HomeDashboardRestController {

    Logger logger = LoggerFactory.getLogger(WeixinPaymentServiceImpl.class);
    @Autowired
    private WxConfig wxConfig;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BookingServiceImpl bookingService;
    @Autowired
    private PaymentServiceImpl paymentService;

    @Autowired
    private LineItemRepository lineItemRepository;



    @Autowired
    private PricingTypeRepository pricingTypeRepository;


    @Autowired
    private FileStorageServiceImpl fileStorageService;



    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/dashboard", produces = "application/json")

        public EntityModel<WindowTicketResp> home(@PathVariable  long SUPPLIER_ID) {
            Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
            if(supplierOptional.isEmpty()) {
                throw new BookNotFoundException("没有找到供应商","没找到");
            }

            Supplier supplier = supplierOptional.get();



        HomeSummary homeSummary = new HomeSummary();


        List<Reservation> reservationList = reservationRepository.findAllBySupplier(supplier.getId());

        Long today_bookings = reservationList.stream().count();


        homeSummary.setRevenueToday(reservationList.stream().mapToDouble(e->e.getAmount()).sum()+"");
        homeSummary.setRevenueDiffToday(reservationList.stream().mapToDouble(e->e.getAmount()).sum()+"");

        homeSummary.setRevenueMTD(reservationList.stream().mapToDouble(e->e.getAmount()).sum()+"");
        homeSummary.setRevenueDiffMTD(reservationList.stream().mapToDouble(e->e.getAmount()).sum()+"");



        homeSummary.setBookings_Total_today(today_bookings.toString());
        homeSummary.setPAX_Total_today(reservationList.stream().mapToInt(e->1).sum()+"");


        homeSummary.setPAX_Already_arrived(today_bookings.toString());
        homeSummary.setBookings_Already_arrived(today_bookings.toString());



        Map summaryMap = new HashMap();
       Map RevenueToday =  Map.of("today",homeSummary.getRevenueToday(),
                "diff",homeSummary.getRevenueDiffToday());
        EntityModel entityModel_RevenueToday = EntityModel.of(RevenueToday);
        entityModel_RevenueToday.add(linkTo(methodOn(MarketRestController.class).listAgent(null,null)).withSelfRel());
        summaryMap.put("revenueToday",entityModel_RevenueToday);


        Map RevenueMTD =  Map.of("today",homeSummary.getRevenueMTD(),
                "diff",homeSummary.getRevenueDiffMTD());
        EntityModel entityModel_RevenueMTD = EntityModel.of(RevenueMTD);
        entityModel_RevenueMTD.add(linkTo(methodOn(MarketRestController.class).listAgent(null,null)).withSelfRel());
        summaryMap.put("revenueMTD",entityModel_RevenueMTD);





        Map Bookings_Total_today =  Map.of("bookings",homeSummary.getBookings_Total_today(),
                "pax",homeSummary.getPAX_Total_today());
        EntityModel entityModel_Bookings_Total_today = EntityModel.of(Bookings_Total_today);
        entityModel_Bookings_Total_today.add(linkTo(methodOn(MarketRestController.class).listAgent(null,null)).withSelfRel());

        summaryMap.put("bookings_Total_today",entityModel_Bookings_Total_today);





        Map Bookings_Already_arrived =  Map.of("Bookings",homeSummary.getBookings_Already_arrived(),
                "pax",homeSummary.getPAX_Already_arrived());
                EntityModel entityModel_Bookings_Already_arrived = EntityModel.of(Bookings_Already_arrived);
        entityModel_Bookings_Already_arrived.add(linkTo(methodOn(MarketRestController.class).listAgent(null,null)).withSelfRel());
        summaryMap.put("already_arrived",entityModel_Bookings_Already_arrived);







        List<Product> productList = productRepository.findBySupplierId(supplier.getId());


        Map<Long, List<PricingRate>>  pricingType = pricingTypeRepository.findByProductIdIn(productList.stream().map(e->e.getId()).collect(Collectors.toList()))
                .stream().collect(Collectors.groupingBy(e->e.getProductId()));



        HomeDashboardResp windowTicketResp = new HomeDashboardResp();

        windowTicketResp.setSummary(summaryMap);


        windowTicketResp.setLogin(Map.of("staff","赵元","organization","旅投"));


        windowTicketResp.setLatestBookings(reservationList.stream().map(e->{

            BookingResp resp = BookingResp.from(e);
            return  resp;
        }).collect(Collectors.toList()));

        windowTicketResp.setProducts(productList.stream().map(e->{

           // List<PricingRate> pricingRateList = pricingType.get(e.getId());
            ProductResp productResp = ProductResp.basefrom(e);

            //ProductResp productResp = ProductResp.from(e, pricingRateList);

            productResp.setThumbnail_image(fileStorageService.loadDocumentWithDefault(EnumDocumentType.product_thumb,e.getCode()));

            return productResp;
        }).collect(Collectors.toList()));






        EntityModel entityModel = EntityModel.of(windowTicketResp);


        entityModel.add(linkTo(methodOn(HomeDashboardRestController.class).getPcBookingList(null,null,null)).withRel("orderList"));


        return entityModel;



    }


    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/bookings/dashboard", produces = "application/json")
    public ResponseEntity<PagedModel> getPcBookingList(
            BookingQueryfieldsCriteria searchQuery,
            @PageableDefault(sort = {"createdDate",
                    "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable ,
            PagedResourcesAssembler<EntityModel<BookingResp>> assembler) {


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


}