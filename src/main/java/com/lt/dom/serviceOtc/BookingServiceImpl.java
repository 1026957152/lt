package com.lt.dom.serviceOtc;

import com.google.gson.Gson;
import com.lt.dom.OctResp.LineItemResp;
import com.lt.dom.RealNameAuthentication.PhoneAuth;
import com.lt.dom.RealNameAuthentication.RealNameAuthenticationServiceImpl;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.excel.ImportExcelBookingTraveler;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.BookingTypeTowhoVo;
import com.lt.dom.requestvo.BookingTypeTowhoVoSku;
import com.lt.dom.serviceOtc.product.*;
import com.lt.dom.vo.PlatRefundVo;
import com.lt.dom.vo.PlatUserVo;
import com.lt.dom.vo.UserVo;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.lt.dom.serviceOtc.JsonParse.GSON;


@Service
public class BookingServiceImpl {
    Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Autowired
    private ImportExcelRepository importExcelRepository;
    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private IdGenServiceImpl idGenService;
    @Autowired
    private BookingAsycServiceImpl bookingAsycService;
    @Autowired
    private BusTicketServiceImpl busTicketService;

    @Autowired
    private RefundServiceImpl refundService;

    @Autowired
    private AudioGuideServiceImpl audioGuideService;



    @Autowired
    private ShowtimeTicketServiceImpl showtimeTicketService;

    @Autowired
    private AttractionTicketServiceImpl attractionTicketService;
    @Autowired
    private CityPassServiceImpl cityPassService;
    @Autowired
    private MultiTicketServiceImpl multiTicketService;

    @Autowired
    private PaymentServiceImpl paymentService;
    @Autowired
    private PaymentFreeServiceImpl paymentFreeService;

    @Autowired
    private ComponentRightResolveServiceImpl componentRightResolveService;


    @Autowired
    private CampaignAssignToTourProductRepository campaignAssignToTourProductRepository;

    @Autowired
    private PricingTypeRepository pricingTypeRepository;
    @Autowired
    RealNameAuthenticationServiceImpl realNameAuthenticationService;
    @Autowired
    TravelerRepository travelerRepository;
    @Autowired
    VoucherServiceImpl voucherService;
    @Autowired
    ChargeRepository chargeRepository;

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    LineItemRepository lineItemRepository;
    @Autowired
    ShippingAddressRepository shippingAddressRepository;


    @Autowired
    CancellationRepository cancellationRepository;


    @Autowired
    TourBookingRepository tourBookingRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    RedeemServiceImpl redeemService;


    @Autowired
    PublicationServiceImpl publicationService;

    @Autowired
    InchargeBookingRepository inchargeBookingRepository;


    public Pair<Reservation,BookingTypeTowhoVo> bookingOnlySku(BookingTypeTowhoVo bookingTypeTowhoVo, BookingPojo pojo, UserVo userVo) {


        if(bookingTypeTowhoVo.getToWhoTyp().equals(EnumBookingOjbectType.Product)){
            Product product = bookingTypeTowhoVo.getProduct();
  /*          List<Voucher> vouchers = pojo.getDiscounts().stream().map(x->new Voucher()).collect(Collectors.toList());
            // Voucher voucher = voucherService.领券(product);


            int va = voucherService.获得折扣金额(product,vouchers);
*/

            Reservation reservation = new Reservation();
            reservation.setAdditional_info(JSONObject.valueToString(pojo.getAdditional_info()));
            reservation.setCode(idGenService.bookingNo());
            // reservation.setItems_discount_amount(va);
            // reservation.setTotal_discount_amount(va);

    /*        if(product.getValidate_way().equals(EnumValidateWay.offline_manual)){
                reservation.setValidationStatus(EnumValidationStatus.NewAwaitingValidation);
            }else{*/
                reservation.setValidationStatus(EnumValidationStatus.None);

     //       }

          //  reservation.setSetValidate_way(product.getValidate_way());

            reservation.setProductType(product.getType());
            reservation.setProductId(product.getId());
            reservation.setSupplier(product.getSupplierId());

            if(product.getType().equals(EnumProductType.Pass)){
                reservation.setFulfillment_behavior(EnumFulfillment_behavior.Create_pass);
            }else{
                reservation.setFulfillment_behavior(EnumFulfillment_behavior.Universal);

            }



            reservation.setPaymentMethods_json(product.getPaymentMethods_json());

            EnumPayChannel[] enumPayChannels =   new Gson().fromJson(product.getPaymentMethods_json(),EnumPayChannel[].class);
            if(Arrays.asList(enumPayChannels).contains(EnumPayChannel.free)){
                reservation.setType(EnumBookingType.Free);
            }else{
                reservation.setType(EnumBookingType.Standard);
            }

            if(product.getType().equals(EnumProductType.Pass)){
                reservation.setPaymentSplit(true);
                reservation.setPaymentSplitCode(product.getCode());
            }



            Optional<PricingRate> pricingTypeOptional = pricingTypeRepository.findById(product.getDefault_price());

            if(pricingTypeOptional.isEmpty()){
                throw new BookNotFoundException(Enumfailures.not_found,"价格不能为空");
            }
            PricingRate pricingRate = pricingTypeOptional.get();

            Integer total  =  pricingRate.getPrice()*pojo.getCount().intValue();

/*            List<PricingType> pricingTypes = getForProduct(product);

            Map<EnumProductPricingTypeByPerson,Integer> enumProductPricingTypeByPersonIntegerMap = pricingTypes.stream().filter(x->x.getType().equals(EnumProductPricingType.ByPerson)).collect(Collectors.toMap(x->x.getBy(),x->x.getPrice()));

            Integer total  = pojo.getTravelers().stream().mapToInt(x->{

                Integer price = enumProductPricingTypeByPersonIntegerMap.get(x.getBy());

                return price== null? 0: price;
            }).sum();*/

            reservation.setAmount(total);
            //  reservation.setTotal_amount(reservation.getAmount()-reservation.getTotal_discount_amount());

            reservation.setStatus(EnumBookingStatus.Draft);

            if(userVo != null){
                reservation.setUser(userVo.getUser_id());
            }

            reservation = reservationRepository.save(reservation);

   /*         Optional<Tour> tour = tourRepository.findById(product.getTypeToWho());
            bookingTypeTowhoVo.setTour(tour.get());*/

            return Pair.with(reservation,bookingTypeTowhoVo);
        }

        if(bookingTypeTowhoVo.getToWhoTyp().equals(EnumBookingOjbectType.Voucher)) {

            Campaign campaign = bookingTypeTowhoVo.getCampaign();
            Reservation reservation = new Reservation();
            if(!ObjectUtils.isEmpty(pojo.getAdditional_info())){
                reservation.setAdditional_info(JSONObject.valueToString(pojo.getAdditional_info()));
            }
            reservation.setCode(idGenService.bookingNo());
            reservation.setAmount(campaign.getPayAmount());

            reservation.setStatus(EnumBookingStatus.Draft);
            reservation = reservationRepository.save(reservation);


            return Pair.with(reservation,bookingTypeTowhoVo);
        }



        return Pair.with(null,null);

    }
    @Transactional
    public Pair<Reservation,List<LineItem> > bookingsku(List<BookingTypeTowhoVoSku> bookingTypeTowhoVoList, BookingSkuPojo pojo, UserVo userVo) {

        Reservation reservation = new Reservation();

        reservation.setAdditional_info(JSONObject.valueToString(pojo.getAdditional_info()));
        reservation.setCode(idGenService.bookingNo());

        reservation.setValidationStatus(EnumValidationStatus.Unknow);



        reservation.setFulfillment_behavior(EnumFulfillment_behavior.Universal);

        reservation.setType(EnumBookingType.Unknow);


        reservation.setUser(userVo.getUser_id());



        reservation.setSetValidate_way(EnumValidateWay.none);

        reservation.setStatus(EnumBookingStatus.Draft); // 这里纯粹是 考虑 不要为空，实际可能是 free



        Optional<BookingTypeTowhoVoSku> shippingAddressCollection = bookingTypeTowhoVoList.stream().filter(e-> {
                    return e.getProduct().getShippingAddressCollection().equals(EnumShippingAddressCollection.required);
                }).findAny();

        if(true || shippingAddressCollection.isPresent()){
            reservation.setShippingAddressCollection(EnumShippingAddressCollection.required);
            BookingSkuPojo.Shipping shipping = pojo.getShipping();
            ShippingCardAddress shippingCardAddress = shippingAddressRepository.findById(shipping.getShippingAddress()).get();
            reservation.setShipingAddress_json(GSON.toJson(shippingCardAddress));
            reservation.setShippingRate(shipping.getShippingRate());
        }else{
            reservation.setShippingAddressCollection(EnumShippingAddressCollection.auto);
        }

        reservation = reservationRepository.save(reservation);


        Reservation finalReservation = reservation;

        List<Pair<LineItem,List<PhoneAuth>>> bookingProductFuckList =  bookingTypeTowhoVoList.stream().map(e->{

            Product product = e.getProduct();

            LineItem lineItem = new LineItem();

            PricingRate pricingRate = e.getPricingType();

            lineItem.setBooking(finalReservation.getId());
            lineItem.setProductType(product.getType());
            lineItem.setTitle(product.getName()+"-"+ pricingRate.getNick_name());
            lineItem.setProduct(product.getId());
            lineItem.setSupplier(product.getSupplierId());
            lineItem.setPaymentMethods_json(product.getPaymentMethods_json());


            lineItem.setQuantity(e.getQuantity().intValue());
            lineItem.setUnitPrice(pricingRate.getRetail());
            lineItem.setAmount(pricingRate.getRetail()*e.getQuantity().intValue());

            if(e.getNegotiatedSku() != null) {
                lineItem.setNegotiated(true);
                lineItem.setUnitPrice(pricingRate.getRetail());
                lineItem.setAmount(pricingRate.getRetail()*e.getQuantity().intValue());

            }else{
                lineItem.setNegotiated(false);
                lineItem.setUnitPrice(pricingRate.getRetail());
                lineItem.setAmount(pricingRate.getRetail()*e.getQuantity().intValue());

            }


            String reffer = UUID.randomUUID().toString();
            lineItem.setQuantity(e.getQuantity().intValue());
            lineItem.setFullfullmentStatus(EnumProductBookingFullfullmentStatus.Created);


            lineItem.setReferTraveler(reffer);
            lineItem.setSku(e.getSku().getId());

            lineItem.setProductCode(product.getCode());




            if(product.getType().equals(EnumProductType.Pass)){

                lineItem.setLineType(EnumLineType.Pass);
                lineItem.setFulfillment_behavior(EnumFulfillment_behavior.Create_pass);

            }else
            if(product.getType().equals(EnumProductType.Showtime)){

                lineItem.setLineType(EnumLineType.Showtime);
                lineItem.setFulfillment_behavior(EnumFulfillment_behavior.Create_voucher);

            }else if(product.getType().equals(EnumProductType.Attraction)){

                lineItem.setLineType(EnumLineType.TICKIT);
                lineItem.setFulfillment_behavior(EnumFulfillment_behavior.Create_voucher);

            }else{
                lineItem.setFulfillment_behavior(EnumFulfillment_behavior.Universal);

            }


            return Pair.with(lineItem,

                    e.getTraveler().stream().map(x->{
                        PhoneAuth auth = new PhoneAuth();
                        auth.setReferTraveler(reffer);
                        auth.setIdCardName(x.getName());
                        auth.setIdCardNo(x.getId_card());
                        auth.setPhoneNo(x.getPhone());

                        return auth;
                    }).collect(Collectors.toList()));


        }).collect(Collectors.toList());


        List<LineItem> lineItemList1 = bookingProductFuckList.stream().map(e->{

            return  e.getValue0();

        }).collect(Collectors.toList());

        lineItemList1 = lineItemRepository.saveAll(lineItemList1);


        reservation.setAmount(lineItemList1.stream().mapToInt(e->e.getAmount()).sum());





        if(bookingTypeTowhoVoList.stream().filter(e->!e.getProduct().getFree()).findAny().isEmpty()){
            reservation.setType(EnumBookingType.Free);
            reservation.setStatus(EnumBookingStatus.Draft);
        }else{
            reservation.setType(EnumBookingType.Standard);
            reservation.setStatus(EnumBookingStatus.Draft);
            Set<EnumPayChannel> enumPayChannelList = bookingTypeTowhoVoList.stream().map(e->{
                return Arrays.asList(new Gson().fromJson(e.getProduct().getPaymentMethods_json(),EnumPayChannel[].class));
            }).flatMap(List::stream).collect(Collectors.toSet());

            reservation.setPaymentMethods_json(new Gson().toJson(enumPayChannelList));

        }




        reservation = reservationRepository.save(reservation);

        List<PhoneAuth> travelerReqs = bookingProductFuckList.stream().map(e->{
            return  e.getValue1();
        }).flatMap(List::stream).collect(Collectors.toList());



        List<Traveler> travelerList = addBulkTraveler(reservation,travelerReqs );

        return Pair.with(reservation, lineItemList1);



    }


    @Transactional
    public Triplet<Reservation,List<LineItem>,PlatUserVo > bookingWithPayment(List<BookingTypeTowhoVo> bookingTypeTowhoVoList, BookingSkuPojo pojo, PlatUserVo userVo) {

        Triplet<Reservation,List<LineItem>,PlatUserVo > booking = bookingOnlySku(bookingTypeTowhoVoList,pojo,userVo);
        Reservation reservation = booking.getValue0();


        if(EnumBookingType.Free.equals(reservation.getType())){
            paymentFreeService.freePayFree(reservation,userVo.getUserVo());
        }


        if(EnumPayment_behavior.normal_payment.equals(reservation.getPayment_behavior())){
            Payment payment = paymentService.createPayment(
                    reservation.getPaymentMethods_json(),
                    reservation.getAmount(),
                    userVo.getUserVo(),
                    reservation);
            payment.setRecipient(reservation.getSupplier());
        }
        if(EnumPayment_behavior.record_payment.equals(reservation.getPayment_behavior())){
            Payment payment = paymentService.recodePayment(
                    reservation.getPaymentMethods_json(),
                    reservation.getAmount(),
                    booking.getValue2(),
                    reservation);
            payment.setRecipient(reservation.getSupplier());

            componentRightResolveService.resolve(reservation,reservation.getFulfillment_behavior());
        }


        return booking;

    }
    @Transactional
    public Triplet<Reservation,List<LineItem>,PlatUserVo > bookingOnlySku(List<BookingTypeTowhoVo> bookingTypeTowhoVoList, BookingSkuPojo pojo, PlatUserVo userVo) {






        Reservation reservation = new Reservation();


            reservation.setAdditional_info(JSONObject.valueToString(pojo.getAdditional_info()));
            reservation.setCode(idGenService.bookingNo());
            reservation.setTrackingId(userVo.getTracking_id());

           // reservation.setItems_discount_amount(va);
           // reservation.setTotal_discount_amount(va);



        reservation.setValidationStatus(EnumValidationStatus.Unknow);

        reservation.setFulfillment_behavior(EnumFulfillment_behavior.Universal);


        reservation.setType(EnumBookingType.Unknow);

   /*
            if(product.getType().equals(EnumProductType.Pass)){
                reservation.setPaymentSplit(true);
                reservation.setPaymentSplitCode(product.getCode());
            }
*/


/*            Optional<PricingType> pricingTypeOptional = pricingTypeRepository.findById(pojo.getPricingType());

            if(pricingTypeOptional.isEmpty()){
                throw new BookNotFoundException(Enumfailures.not_found,"价格不能为空");
            }
            PricingType pricingType = pricingTypeOptional.get();

            Integer total  =  pricingType.getPrice()*pojo.getCount().intValue();*/

/*            List<PricingType> pricingTypes = getForProduct(product);

            Map<EnumProductPricingTypeByPerson,Integer> enumProductPricingTypeByPersonIntegerMap = pricingTypes.stream().filter(x->x.getType().equals(EnumProductPricingType.ByPerson)).collect(Collectors.toMap(x->x.getBy(),x->x.getPrice()));

            Integer total  = pojo.getTravelers().stream().mapToInt(x->{

                Integer price = enumProductPricingTypeByPersonIntegerMap.get(x.getBy());

                return price== null? 0: price;
            }).sum();*/


            //  reservation.setTotal_amount(reservation.getAmount()-reservation.getTotal_discount_amount());


        reservation.setPlatform(userVo.getPlatform());

        if(userVo.getPlatform().equals(EnumPlatform.LT)){
            reservation.setUser(userVo.getUserVo().getUser_id());
            UserVo userVo1 = userVo.getUserVo();




            reservation.setLog_buyer_name(userVo1.getReal_name());
            reservation.setLog_buyer_phone(userVo1.getPhone());
         //   reservation.setLog_buyer_id_ntype();
            reservation.setLog_buyer_id_number(userVo1.getId_card());
            reservation.setPayment_behavior(EnumPayment_behavior.normal_payment);

        }
        if(userVo.getPlatform().equals(EnumPlatform.TS)){
            reservation.setLog_buyer_name(userVo.getName());
            reservation.setLog_buyer_phone(userVo.getPhone());
            reservation.setLog_buyer_id_ntype(userVo.getId_type());
            reservation.setLog_buyer_id_number(userVo.getId_number());

            if(userVo.isPaid()){
                reservation.setPayment_behavior(EnumPayment_behavior.record_payment);
            }else{
                reservation.setPayment_behavior(EnumPayment_behavior.normal_payment);

            }

            reservation.setAgent(userVo.getAgent().getId());
        }



        reservation.setSetValidate_way(EnumValidateWay.none);

        reservation.setStatus(EnumBookingStatus.Draft); // 这里纯粹是 考虑 不要为空，实际可能是 free


        Optional<BookingTypeTowhoVo> shippingAddressCollection = bookingTypeTowhoVoList.stream().filter(e-> {
            return EnumShippingAddressCollection.required.equals(e.getProduct().getShippingAddressCollection());
        }).findAny();

        if(shippingAddressCollection.isPresent()){
            reservation.setShippingAddressCollection(EnumShippingAddressCollection.required);
            BookingSkuPojo.Shipping shipping = pojo.getShipping();
            ShippingCardAddress shippingCardAddress = shippingAddressRepository.findById(shipping.getShippingAddress()).get();
            reservation.setShipingAddress_json(GSON.toJson(shippingCardAddress));
            reservation.setShippingRate(shipping.getShippingRate());
        }else{
            reservation.setShippingAddressCollection(EnumShippingAddressCollection.auto);
        }




        reservation.setNote(userVo.getRemark());

            reservation = reservationRepository.save(reservation);


        Reservation finalReservation = reservation;

        List<Pair<LineItem,List<PhoneAuth>>> bookingProductFuckList =  bookingTypeTowhoVoList.stream().map(e->{

            Product product = e.getProduct();

            LineItem lineItem = new LineItem();


/*
            Optional<PricingType> pricingTypeOptional = pricingTypeRepository.findById(product.getDefault_price());

            if(pricingTypeOptional.isEmpty()){
                throw new BookNotFoundException(Enumfailures.not_found,"价格不能为空");
            }*/
            PricingRate pricingRate = e.getSku();


            lineItem.setCode(idGenService.lineItemCode());

            lineItem.setBooking(finalReservation.getId());

            lineItem.setProductType(product.getType());
            lineItem.setTitle(product.getName()+"-"+ pricingRate.getNick_name());
            lineItem.setProduct(product.getId());
            lineItem.setSupplier(product.getSupplierId());
            lineItem.setPaymentMethods_json(product.getPaymentMethods_json());
            //   bookingProduct.setAdditional_info(JSONObject.valueToString(pojo.getAdditional_info()));
            if(product.getType().equals(EnumProductType.Pass)){
                lineItem.setPaymentSplit(true);
                lineItem.setPaymentSplitCode(product.getCode());
            }

            lineItem.setQuantity(e.getQuantity().intValue());

            if(userVo.getPlatform().equals(EnumPlatform.LT)){
                lineItem.setUnitPrice(pricingRate.getRetail());
            }else{
                lineItem.setUnitPrice(e.getRetail());
            }


            lineItem.setAmount(lineItem.getUnitPrice()*lineItem.getQuantity().intValue());

/*            if(e.getNegotiatedSku() != null) {
                lineItem.setNegotiated(true);
                lineItem.setUnitPrice(pricingRate.getRetail());
                lineItem.setAmount(pricingRate.getRetail()*e.getQuantity().intValue());

            }else{
                lineItem.setNegotiated(false);
                lineItem.setUnitPrice(pricingRate.getRetail());
                lineItem.setAmount(pricingRate.getRetail()*e.getQuantity().intValue());

            }*/


            String reffer = UUID.randomUUID().toString();
            lineItem.setQuantity(e.getQuantity().intValue());
            lineItem.setFullfullmentStatus(EnumProductBookingFullfullmentStatus.Created);
            lineItem.setStatus(EnumLineItemStatus.Executing);

            lineItem.setReferTraveler(reffer);
            lineItem.setBase_cost_price(e.getBase_cost_price());



            lineItem.setProductCode(product.getCode());

           // bookingProductFuck.setSku();


            lineItem.setDeliveryFormats(e.getDeliveryFormat());
            lineItem.setRemark(e.getRemark());


            lineItem = lineItemRepository.save(lineItem);


            LineItem finalLineItem = lineItem;
            lineItem.setTravelers(e.getTraveler().stream().map(travelerReq -> {
                Traveler traveler = new Traveler();

                traveler.setName(travelerReq.getName());
                traveler.setIdNo(travelerReq.getId_card());
                traveler.setTel_home(travelerReq.getPhone());
                traveler.setIdType(EnumIdType.身份证);
                traveler.setLineItem(finalLineItem);
                return traveler;

            }).collect(Collectors.toList()));

            multiTicketService.booking(lineItem,product);
            cityPassService.booking(lineItem,product,pricingRate);
            attractionTicketService.booking(lineItem,product);
            showtimeTicketService.booking(lineItem,product);
            busTicketService.booking(lineItem,product);
            audioGuideService.booking(lineItem,product);


            return Pair.with(lineItem,

                    e.getTraveler().stream().map(x->{
                        PhoneAuth auth = new PhoneAuth();
                        auth.setReferTraveler(reffer);
                        auth.setIdCardName(x.getName());
                        auth.setIdCardNo(x.getId_card());
                        auth.setPhoneNo(x.getPhone());

                        return auth;
                    }).collect(Collectors.toList()));


  /*          if(!e.getSkus().isEmpty()){
                Product product = e.getProduct();
                BookingProductFuck bookingProductFuck = new BookingProductFuck();

                bookingProductFuck.setQuantity(ee.getQuantity().intValue());
                bookingProductFuck.setPrice(ee.getId().getRetail());
                bookingProductFuck.setAmount(bookingProductFuck.getQuantity()*bookingProductFuck.getPrice());

                bookingProductFuck.setBooking(finalReservation.getId());
                bookingProductFuck.setProductType(product.getType());
                bookingProductFuck.setProduct(product.getId());
                bookingProductFuck.setSupplier(product.getSupplierId());
                bookingProductFuck.setPaymentMethods_json(product.getPaymentMethods_json());
                if(product.getType().equals(EnumProductType.Pass)){
                    bookingProductFuck.setPaymentSplit(true);
                    bookingProductFuck.setPaymentSplitCode(product.getCode());
                }
                return bookingProductFuck;

            *//*
                return e.getSkus().stream().map(ee->{
                    BookingProductFuck bookingProductFuck = new BookingProductFuck();

                    bookingProductFuck.setQuantity(ee.getQuantity().intValue());
                    bookingProductFuck.setPrice(ee.getId().getRetail());
                    bookingProductFuck.setAmount(bookingProductFuck.getQuantity()*bookingProductFuck.getPrice());

                    bookingProductFuck.setBooking(finalReservation.getId());
                    bookingProductFuck.setProductType(product.getType());
                    bookingProductFuck.setProduct(product.getId());
                    bookingProductFuck.setSupplier(product.getSupplierId());
                    bookingProductFuck.setPaymentMethods_json(product.getPaymentMethods_json());
                    if(product.getType().equals(EnumProductType.Pass)){
                        bookingProductFuck.setPaymentSplit(true);
                        bookingProductFuck.setPaymentSplitCode(product.getCode());
                    }
                    return bookingProductFuck;

                }).collect(Collectors.toList());
*//*

            }else{
                Product product = e.getProduct();
                BookingProductFuck bookingProductFuck = new BookingProductFuck();



                Optional<PricingType> pricingTypeOptional = pricingTypeRepository.findById(product.getDefault_price());

                if(pricingTypeOptional.isEmpty()){
                    throw new BookNotFoundException(Enumfailures.not_found,"价格不能为空");
                }
                PricingType pricingType = pricingTypeOptional.get();

                bookingProductFuck.setBooking(finalReservation.getId());
                bookingProductFuck.setProductType(product.getType());
                bookingProductFuck.setProduct(product.getId());
                bookingProductFuck.setSupplier(product.getSupplierId());
                bookingProductFuck.setPaymentMethods_json(product.getPaymentMethods_json());
                //   bookingProduct.setAdditional_info(JSONObject.valueToString(pojo.getAdditional_info()));
                if(product.getType().equals(EnumProductType.Pass)){
                    bookingProductFuck.setPaymentSplit(true);
                    bookingProductFuck.setPaymentSplitCode(product.getCode());
                }

                bookingProductFuck.setQuantity(e.getQuantity().intValue());
                bookingProductFuck.setPrice(pricingType.getRetail());
                bookingProductFuck.setAmount(pricingType.getRetail()*e.getQuantity().intValue());

                bookingProductFuck.setQuantity(e.getQuantity().intValue());
                return Arrays.asList(bookingProductFuck);
            }*/

        }).collect(Collectors.toList());


        List<LineItem> lineItemList1 = bookingProductFuckList.stream().map(e->{

            return  e.getValue0();

        }).collect(Collectors.toList());

        lineItemList1 = lineItemRepository.saveAll(lineItemList1);


        reservation.setAmount(lineItemList1.stream().mapToInt(e->e.getAmount()).sum());





        if(bookingTypeTowhoVoList.stream().filter(e->!e.getProduct().getFree()).findAny().isEmpty()){
            reservation.setType(EnumBookingType.Free);
            reservation.setStatus(EnumBookingStatus.Draft);
        }else{
            reservation.setType(EnumBookingType.Standard);
            reservation.setStatus(EnumBookingStatus.Draft);
         //   reservation.setPaymentMethods_json(new Gson().toJson(Arrays.asList(EnumPayChannel.wx)));
            Set<EnumPayChannel> enumPayChannelList = bookingTypeTowhoVoList.stream().map(e->{
                return Arrays.asList(new Gson().fromJson(e.getProduct().getPaymentMethods_json(),EnumPayChannel[].class));
            }).flatMap(List::stream).collect(Collectors.toSet());

            reservation.setPaymentMethods_json(new Gson().toJson(enumPayChannelList));

        }




        reservation = reservationRepository.save(reservation);

        List<PhoneAuth> travelerReqs = bookingProductFuckList.stream().map(e->{
            return  e.getValue1();
        }).flatMap(List::stream).collect(Collectors.toList());



    //    List<Traveler> travelerList = addBulkTraveler(reservation,travelerReqs );

            return Triplet.with(reservation, lineItemList1,userVo);



    }






    public Pair<Reservation,List<BookingTypeTowhoVo>> bookingOnlySku(List<BookingTypeTowhoVo> bookingTypeTowhoVo, WindowsTicketBookingPojo pojo) {



        System.out.println("原来的通道");

       List<LineItem> lineItemList =  bookingTypeTowhoVo.stream().map(e->{
            Product product = e.getProduct();
            LineItem lineItem = new LineItem();
            lineItem.setAmount(1);
            lineItem.setUnitPrice(1);


           lineItem.setProductType(product.getType());
           lineItem.setSku(e.getSku().getId());

           lineItem.setProduct(product.getId());
           lineItem.setSupplier(product.getSupplierId());
           lineItem.setPaymentMethods_json(product.getPaymentMethods_json());
        //   bookingProduct.setAdditional_info(JSONObject.valueToString(pojo.getAdditional_info()));
           if(product.getType().equals(EnumProductType.Pass)){
               lineItem.setPaymentSplit(true);
               lineItem.setPaymentSplitCode(product.getCode());
           }





  /*         Optional<PricingType> pricingTypeOptional = pricingTypeRepository.findById(pojo.getPricingType());

           if(pricingTypeOptional.isEmpty()){
               throw new BookNotFoundException(Enumfailures.not_found,"价格不能为空");
           }
           PricingType pricingType = pricingTypeOptional.get();
*/


           lineItem.setQuantity(e.getQuantity().intValue());

           return lineItem;
        }).collect(Collectors.toList());

        lineItemRepository.saveAll(lineItemList);



            Reservation reservation = new Reservation();
            reservation.setCode(idGenService.bookingNo());


            Integer total  = 1;// pricingType.getPrice()*pojo.getCount().intValue();

            reservation.setAmount(total);
            //  reservation.setTotal_amount(reservation.getAmount()-reservation.getTotal_discount_amount());

            reservation.setStatus(EnumBookingStatus.Draft);


        List<PhoneAuth> travelerReqs = Collections.singletonList(pojo.getTraveler()).stream().map(x->{
            PhoneAuth auth = new PhoneAuth();
            auth.setIdCardName(x.getName());
            auth.setIdCardNo(x.getId());
            auth.setPhoneNo(x.getTel_home());
            return auth;
        }).collect(Collectors.toList());

            List<Traveler> travelerList = addBulkTraveler(reservation,travelerReqs );


            if(travelerList.size() > 0 ){
                reservation.setUser(travelerList.get(0).getId());
            }else{
                reservation.setUser(1L);
            }


            reservation = reservationRepository.save(reservation);



            return Pair.with(reservation,bookingTypeTowhoVo);


    }



    private List<PricingRate> getForProduct(Product product){

        List<PricingRate> pricingRates = pricingTypeRepository.findByProductId(product.getId());

        return pricingRates;
    }


    public Traveler addTraveler(Reservation booking, TravelerReq travelerReq) {


        PhoneAuth auth = new PhoneAuth();
        auth.setIdCardName(travelerReq.getName());
        auth.setPhoneNo(travelerReq.getTel_home());



        boolean isRealName = realNameAuthenticationService.checkRealname(auth);

        if(isRealName){
            Traveler transfer = new Traveler();
            transfer.setBooking(booking.getId());
            transfer.setName(travelerReq.getName());
            transfer.setTel_home(travelerReq.getTel_home());
            transfer.setIdNo(travelerReq.getId());

            transfer = travelerRepository.save(transfer);
            return transfer;
        }else{
            throw new RuntimeException();

        }

    }






    public List<Traveler> addBulkTraveler(Reservation booking,  List<PhoneAuth> travelerReqs) {


/*
        List<PhoneAuth> travelerReqs = transferPojo.stream().map(x->{
            PhoneAuth auth = new PhoneAuth();
            auth.setIdCardName(x.getName());
            auth.setIdCardNo(x.getId());
            auth.setPhoneNo(x.getTel_home());
            return auth;
        }).collect(Collectors.toList());
*/


        Pair<List<PhoneAuth>, List<PhoneAuth>> phoneAuthPhoneAuthPair = realNameAuthenticationService.bulkCheckRealname(travelerReqs);


        List<Traveler> transfers = phoneAuthPhoneAuthPair.getValue0().stream().map(x->{
            Traveler transfer = new Traveler();
            transfer.setBooking(booking.getId());
            transfer.setName(x.getIdCardName());
            transfer.setTel_home(x.getPhoneNo());
            transfer.setIdNo(x.getIdCardNo());
            transfer.setReferSku(x.getReferTraveler());
            return transfer;
        }).collect(Collectors.toList());

        List<Traveler> travelers = travelerRepository.saveAll(transfers);


        return travelers;
    }



    public ImportExcel addBulkTravelerByImportCVS(TourBooking booking, List<ImportExcelBookingTraveler> importExcelBookingTravelers) {

        ImportExcel export = new ImportExcel();

        export.setCreated_at(LocalDateTime.now());
        export.setStatus(EnumExportStatus.SCHEDULED);
        export.setImportCSV_object(EnumImportCVS.traveler);
        export.setCode(idGenService.importNo());
        export.setRelativeid(booking.getId());

        export = importExcelRepository.save(export);

        bookingAsycService.addBulkTravelerByImportCVS(export, importExcelBookingTravelers);
        return export;

    }

    public List<Traveler> addBulkRealNameTraveler(Reservation booking, List<PhoneAuth> importExcelBookingTravelers) {

/*
        List<PhoneAuth> travelerReqs = importExcelBookingTravelers.stream().map(x->{
            PhoneAuth auth = new PhoneAuth();
            auth.setIdCardName(x.getIdCardName());
            auth.setIdCardNo(x.getIdCardNo());

            return auth;

        }).collect(Collectors.toList());


        Pair<List<PhoneAuth>, List<PhoneAuth>> phoneAuthPhoneAuthPair = realNameAuthenticationService.bulkCheckRealname(travelerReqs);
*/


        List<Traveler> transfers = importExcelBookingTravelers.stream().map(x->{
            Traveler transfer = new Traveler();
            transfer.setBooking(booking.getId());
            transfer.setName(x.getIdCardName());
            transfer.setTel_home(x.getPhoneNo());
            transfer.setIdNo(x.getIdCardNo());
            return transfer;
        }).collect(Collectors.toList());

        List<Traveler> travelers = travelerRepository.saveAll(transfers);


        return travelers;


    }















    public List<Campaign> listCampaigns(Reservation id) {


        Optional<Product> optional = productRepository.findById(id.getProductId());

        return campaignForProduct(optional.get());
    }


    private List<Campaign> campaignForProduct(Product product){
        if(product.getType().equals(EnumProductType.Daytour)){
            List<CampaignAssignToTourProduct> campaignAssignToTourProducts = campaignAssignToTourProductRepository.findByTourId(product.getTypeTo());

            List<Campaign> campaigns =  campaignRepository.findAllById(campaignAssignToTourProducts.stream().map(x->x.getCampaign()).collect(Collectors.toList()));

            return campaigns;
        }else{
            throw new RuntimeException();
        }

    }

    public void update(Reservation reservation) {

        reservationRepository.save(reservation);
    }



    public Reservation amendment(Reservation reservation, BookingAmendmentReq cancelBookingRequest) {


        reservation.setStatus(EnumBookingStatus.Cancelled);


        return reservationRepository.save(reservation);

    }


    public Pair<Refund,PlatRefundVo> refund(PlatRefundVo platRefundVo, Reservation reservation) {


        Refund refund = new Refund();


        String code = idGenService.refundCode();
        refund = refundService.createCharge(code,reservation,platRefundVo);


        return Pair.with(refund,platRefundVo);



        
    }

    public Double getCost(Reservation reservation) {


        List<LineItem> lineItemList = lineItemRepository.findAllByBooking(reservation.getId());

        Double cost = lineItemList.stream().mapToDouble(e->e.getBase_cost_price().doubleValue()).sum();

        return cost;
       // lineItemList.stream().map(e->e.get)
    }

    public Double getMargin(Reservation reservation) {


        List<LineItem> lineItemList = lineItemRepository.findAllByBooking(reservation.getId());

        Double cost = lineItemList.stream().mapToDouble(e->e.getBase_cost_price().doubleValue()).sum();

        return reservation.getAmount() - cost;
        // lineItemList.stream().map(e->e.get)
    }
}
