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
import com.lt.dom.repository.PricingTypeRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.ReservationRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.requestvo.BookingTypeTowhoVo;
import com.lt.dom.serviceOtc.AuthenticationFacade;
import com.lt.dom.serviceOtc.BookingServiceImpl;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.pay.WeixinPaymentServiceImpl;
import com.lt.dom.vo.ChargeMetadataVo;
import com.lt.dom.vo.QrcodeReaderResult;
import com.lt.dom.vo.UserVo;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

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

        homeSummary.setBookings_Total_today(today_bookings.toString());

        homeSummary.setBookings_Total_today(today_bookings.toString());


        homeSummary.setPAX_Total_today(reservationList.stream().mapToInt(e->1).sum()+"");
        homeSummary.setPAX_Already_arrived(today_bookings.toString());



        homeSummary.setRevenueToday(reservationList.stream().mapToDouble(e->e.getAmount()).sum()+"");
        homeSummary.setRevenueToday(reservationList.stream().mapToDouble(e->e.getAmount()).sum()+"");

        List<Product> productList = productRepository.findBySupplierId(supplier.getId());


        Map<Long, List<PricingRate>>  pricingType = pricingTypeRepository.findByProductIdIn(productList.stream().map(e->e.getId()).collect(Collectors.toList()))
                .stream().collect(Collectors.groupingBy(e->e.getProductId()));



        HomeDashboardResp windowTicketResp = new HomeDashboardResp();

        windowTicketResp.setSummary(homeSummary);


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




        return entityModel;



    }



}