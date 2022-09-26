package com.lt.dom.controllerOct;

import com.github.binarywang.wxpay.bean.request.WxPayOrderQueryRequest;
import com.github.wxpay.sdk.WXPay;
import com.google.gson.Gson;
import com.lt.dom.OctResp.ProductResp;
import com.lt.dom.OctResp.WindowTicketResp;
import com.lt.dom.OctResp.WxPayOrderQueryResultResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.WindowsTicketBookingPojo;
import com.lt.dom.otcenum.EnumBookingOjbectType;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumPayChannel;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.PricingTypeRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.requestvo.BookingTypeTowhoVo;
import com.lt.dom.serviceOtc.*;
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
public class WindowTicketRestController {

    Logger logger = LoggerFactory.getLogger(WeixinPaymentServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private WeixinPaymentServiceImpl paymentService;

    @Autowired
    private BookingServiceImpl bookingService;

    @Autowired
    private PricingTypeRepository pricingTypeRepository;


    private WindowsTicketBookingPojo currentBooking;
    @Autowired
    private FileStorageServiceImpl fileStorageService;



    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/window_ticket", produces = "application/json")


        public EntityModel<WindowTicketResp> getCasher(@PathVariable  long SUPPLIER_ID) {
            Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
            if(supplierOptional.isEmpty()) {
                throw new BookNotFoundException("没有找到供应商","没找到");
            }

            Supplier supplier = supplierOptional.get();

        List<Product> productList = productRepository.findBySupplierId(supplier.getId());


        Map<Long, List<PricingType>>  pricingType = pricingTypeRepository.findByProductIdIn(productList.stream().map(e->e.getId()).collect(Collectors.toList()))
                .stream().collect(Collectors.groupingBy(e->e.getProductId()));



        WindowTicketResp windowTicketResp = new WindowTicketResp();
        windowTicketResp.setProducts(productList.stream().map(e->{

            List<PricingType> pricingTypeList = pricingType.get(e.getId());

            ProductResp productResp = ProductResp.from(e,pricingTypeList);

            productResp.setImages(fileStorageService.loadDocuments(EnumDocumentType.product_photos,e.getCode()));

            productResp.setThumbnail_image(fileStorageService.loadDocument(EnumDocumentType.product_thumb,e.getCode()));

            return productResp;
        }).collect(Collectors.toList()));






        EntityModel entityModel = EntityModel.of(windowTicketResp);



        entityModel.add(linkTo(methodOn(WindowTicketRestController.class).立即下单_(null,null)).withRel("createBooking"));

        return entityModel;



    }


/*








    @PostMapping(value = "/___________window_ticket/pay/{BOOKING_ID}", produces = "application/json")
    public Charge 立即下单(@PathVariable long BOOKING_ID, @RequestBody BookingPojo pojo , HttpServletRequest request) {


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);




        Optional<Product> optionalProduct = productRepository.findById(BOOKING_ID);

        if(optionalProduct.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Product product = optionalProduct.get();



*/
/*
        Optional<Reservation> reservationOptional = reservationRepository.findById(BOOKING_ID);
        if(reservationOptional.isEmpty()) {
            throw new BookNotFoundException(BOOKING_ID,"找不到产品");
        }
        Reservation reservation = reservationOptional.get();
*//*



        BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
        bookingTypeTowhoVo.setToWhoTyp(EnumBookingOjbectType.Product);
        bookingTypeTowhoVo.setProduct(product);

        String ip = HttpUtils.getRequestIP(request);

        Pair<Reservation, BookingTypeTowhoVo> booking =bookingService.book(bookingTypeTowhoVo, pojo, userVo);
        Reservation reservation = booking.getValue0();

*/
/*        BookingPojo pojo = new BookingPojo();

        Pair<Reservation, BookingTypeTowhoVo> booking = bookingService.book(bookingTypeTowhoVo, pojo, userVo);


        Reservation reservation = booking.getValue0();*//*



        Payment payment = paymentService.createPayment(new Gson().toJson(Arrays.asList(EnumPayChannel.wx)),reservation.getAmount(),userVo.getUser_id(),reservation);
        payment.setRecipient(reservation.getSupplier());

        //  resp.add(linkTo(methodOn(PaymentRestController.class).wxPayRequest(payment.getId(),null)).withRel("wx_pay_url"));


        // double totalAmount = 0.01;
        int totalAmount = payment.getAmount();
        // 微信的支付单位是分所以要转换一些单位
        long  totalproce =Long.parseUnsignedLong(totalAmount+"");//Long.parseUnsignedLong(totalAmount*100+"");


        Gson gson = new Gson();
        ChargeMetadataVo chargeMetadataVo = new ChargeMetadataVo();


        chargeMetadataVo.setPayer(userVo.getUser_id());
        chargeMetadataVo.setBooking(reservation.getId());
        String metadata = gson.toJson(chargeMetadataVo);


        Charge  charge = paymentService.createCharge("auth_code",ip,payment.getCode(),reservation,totalproce,metadata);



        return charge;
    }







    @PostMapping(value = "/__window_ticket/pay/{BOOKING_ID}", produces = "application/json")
    public Charge 立即下单_(@PathVariable long BOOKING_ID, @RequestBody BookingPojo pojo , HttpServletRequest request) {


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);




        Optional<Product> optionalProduct = productRepository.findById(BOOKING_ID);

        if(optionalProduct.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Product product = optionalProduct.get();



*/
/*
        Optional<Reservation> reservationOptional = reservationRepository.findById(BOOKING_ID);
        if(reservationOptional.isEmpty()) {
            throw new BookNotFoundException(BOOKING_ID,"找不到产品");
        }
        Reservation reservation = reservationOptional.get();
*//*



        BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
        bookingTypeTowhoVo.setToWhoTyp(EnumBookingOjbectType.Product);
        bookingTypeTowhoVo.setProduct(product);

        String ip = HttpUtils.getRequestIP(request);

        Pair<Reservation, BookingTypeTowhoVo> booking =bookingService.book(bookingTypeTowhoVo, pojo, userVo);
        Reservation reservation = booking.getValue0();

*/
/*        BookingPojo pojo = new BookingPojo();

        Pair<Reservation, BookingTypeTowhoVo> booking = bookingService.book(bookingTypeTowhoVo, pojo, userVo);


        Reservation reservation = booking.getValue0();*//*



        Payment payment = paymentService.createPayment(new Gson().toJson(Arrays.asList(EnumPayChannel.wx)),reservation.getAmount(),userVo.getUser_id(),reservation);
        payment.setRecipient(reservation.getSupplier());

        //  resp.add(linkTo(methodOn(PaymentRestController.class).wxPayRequest(payment.getId(),null)).withRel("wx_pay_url"));


        // double totalAmount = 0.01;
        int totalAmount = payment.getAmount();
        // 微信的支付单位是分所以要转换一些单位
        long  totalproce =Long.parseUnsignedLong(totalAmount+"");//Long.parseUnsignedLong(totalAmount*100+"");


        Gson gson = new Gson();
        ChargeMetadataVo chargeMetadataVo = new ChargeMetadataVo();


        chargeMetadataVo.setPayer(userVo.getUser_id());
        chargeMetadataVo.setBooking(reservation.getId());
        String metadata = gson.toJson(chargeMetadataVo);


        Charge  charge = paymentService.createCharge("auth_code",ip,payment.getCode(),reservation,totalproce,metadata);



        return charge;
    }

*/



    @PostMapping(value = "/window_ticket/pay", produces = "application/json")
    public EntityModel<WindowsTicketBookingPojo> 立即下单_(@RequestBody @Valid WindowsTicketBookingPojo pojo, HttpServletRequest request) {



        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);


        List<Product> productList = productRepository.findAllByCodeIn(pojo.getProducts().stream().map(e->e.getId()).collect(Collectors.toList()));

        if(productList.size() == 0 ){
            throw new BookNotFoundException(Enumfailures.not_found,"产品不能为空");
        }

        currentBooking = pojo;

        currentBooking.setId(UUID.randomUUID().toString());

        EntityModel entityModel = EntityModel.of(currentBooking);

        entityModel.add(linkTo(methodOn(WindowTicketRestController.class).microPayQuery(currentBooking.getId())).withRel("payQuery"));


        return entityModel;
    }





    public Reservation makeByPojo(WindowsTicketBookingPojo pojo) {


        if(pojo.getReservation()!= null){
            return pojo.getReservation();
        }
        List<Product> productList = productRepository.findAllByCodeIn(pojo.getProducts().stream().map(e->e.getId()).collect(Collectors.toList()));


        Map<Long,Product>  productMap = productList.stream().collect(Collectors.toMap(e->e.getId(), e->e));


        if(productList.size() == 0 ){
            throw new BookNotFoundException(Enumfailures.not_found,"产品不能为空");
        }

        List<BookingTypeTowhoVo> list =  pojo.getProducts().stream().map(e->{

            Product product = productMap.get(e.getId());

            BookingTypeTowhoVo bookingTypeTowhoVo = new BookingTypeTowhoVo();
            bookingTypeTowhoVo.setToWhoTyp(EnumBookingOjbectType.Product);
            bookingTypeTowhoVo.setProduct(product);
            bookingTypeTowhoVo.setQuantity(e.getCount());

            return bookingTypeTowhoVo;
        }).collect(Collectors.toList());


        //  String ip = HttpUtils.getRequestIP(request);





        Pair<Reservation, List<BookingTypeTowhoVo>> booking =bookingService.booking(list, pojo);

        pojo.setReservation(booking.getValue0());


        return booking.getValue0();
    }



    @PostMapping(value = "/qr_notify") //consumes= "text/html",  //  produces = "text/plain"
    public String Deactivate(@RequestBody String params,@RequestHeader Map<String, String> headers) {


        if(ObjectUtils.isEmpty(currentBooking)){

            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到当前要支付的订单啊啊");
        }




        synchronized (currentBooking){

            headers.forEach((key, value) -> {
                System.out.println(String.format("Header '%s' = %s", key, value));
            });
            System.out.println("==========="+ params);


            String[] a = params.split("&&");
            String vgdecoderesult_  = a[0];
            String devicenumber_  = a[1];

            String vgdecoderesult = vgdecoderesult_.split("=")[1];
            String devicenumber = devicenumber_.split("=")[1];

            QrcodeReaderResult qrcodeReaderResult = new QrcodeReaderResult(vgdecoderesult,devicenumber);

            System.out.println(qrcodeReaderResult.toString());
  /*      eventPublisher.publishEvent(new OnQrcodeReaderScanSuccessEvent(new User(),
                qrcodeReaderResult, EnumEvents.qrcode_reader$scan));
*/




            Reservation reservation = makeByPojo(currentBooking);


            Payment payment = paymentService.createPayment(new Gson().toJson(Arrays.asList(EnumPayChannel.wx)),reservation.getAmount(),1l,reservation);
            payment.setRecipient(reservation.getSupplier());

            //  resp.add(linkTo(methodOn(PaymentRestController.class).wxPayRequest(payment.getId(),null)).withRel("wx_pay_url"));


            // double totalAmount = 0.01;
            int totalAmount = payment.getAmount();
            // 微信的支付单位是分所以要转换一些单位
            long  totalproce =Long.parseUnsignedLong(totalAmount+"");//Long.parseUnsignedLong(totalAmount*100+"");


            Gson gson = new Gson();
            ChargeMetadataVo chargeMetadataVo = new ChargeMetadataVo();


            //   chargeMetadataVo.setPayer(userVo.getUser_id());
            chargeMetadataVo.setBooking(reservation.getId());
            String metadata = gson.toJson(chargeMetadataVo);


            Charge  charge = paymentService.createCharge(qrcodeReaderResult.getVgdecoderesult(),"ip",payment.getCode(),reservation,totalproce,metadata);



            // return charge;


            currentBooking = null;

            return "code=0000";

        }


    }



    /**
     * 查询订单
     */
    @GetMapping("/microPayQuery/{id}")
    public WxPayOrderQueryResultResp microPayQuery(@PathVariable String id) {


        WxPayOrderQueryRequest wxPayOrderQueryRequest = new WxPayOrderQueryRequest();

        MyConfig wxPayConfig = null;
        try {
            wxPayConfig = new MyConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }

        WXPay wxpay = null;
        try {
            wxpay = new WXPay(wxPayConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }

/*        map.put("version", version);
        map.put("transaction_id", transactionId);
        map.put("out_trade_no", outTradeNo);*/


        WxPayOrderQueryResultResp wxPayOrderQueryResultResp = new WxPayOrderQueryResultResp();

        wxPayOrderQueryResultResp.setPaied(true);


        return wxPayOrderQueryResultResp;//wxpay.orderQuery(wxPayOrderQueryRequest);


    }

}