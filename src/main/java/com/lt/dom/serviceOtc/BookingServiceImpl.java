package com.lt.dom.serviceOtc;

import com.google.gson.Gson;
import com.lt.dom.RealNameAuthentication.PhoneAuth;
import com.lt.dom.RealNameAuthentication.RealNameAuthenticationServiceImpl;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.excel.ImportExcelBookingTraveler;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.BookingTypeTowhoVo;
import com.lt.dom.vo.UserVo;
import org.javatuples.Pair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class BookingServiceImpl {

    @Autowired
    private ImportExcelRepository importExcelRepository;
    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private IdGenServiceImpl idGenService;
    @Autowired
    private BookingAsycServiceImpl bookingAsycService;
    @Autowired
    private TourRepository tourRepository;
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
    BookingProductRepository bookingProductRepository;


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


    public Pair<Reservation,BookingTypeTowhoVo> booking(BookingTypeTowhoVo bookingTypeTowhoVo, BookingPojo pojo, UserVo userVo) {


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



            Optional<PricingType> pricingTypeOptional = pricingTypeRepository.findById(product.getDefault_price());

            if(pricingTypeOptional.isEmpty()){
                throw new BookNotFoundException(Enumfailures.not_found,"价格不能为空");
            }
            PricingType pricingType = pricingTypeOptional.get();

            Integer total  =  pricingType.getPrice()*pojo.getCount().intValue();

/*            List<PricingType> pricingTypes = getForProduct(product);

            Map<EnumProductPricingTypeByPerson,Integer> enumProductPricingTypeByPersonIntegerMap = pricingTypes.stream().filter(x->x.getType().equals(EnumProductPricingType.ByPerson)).collect(Collectors.toMap(x->x.getBy(),x->x.getPrice()));

            Integer total  = pojo.getTravelers().stream().mapToInt(x->{

                Integer price = enumProductPricingTypeByPersonIntegerMap.get(x.getBy());

                return price== null? 0: price;
            }).sum();*/

            reservation.setAmount(total);
            //  reservation.setTotal_amount(reservation.getAmount()-reservation.getTotal_discount_amount());

            reservation.setStatus(EnumBookingStatus.Draft);
            reservation.setCreated_at(LocalDateTime.now());
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
    public Pair<Reservation,List<BookingProductFuck> > booking(List<BookingTypeTowhoVo> bookingTypeTowhoVoList, BookingPojo pojo, UserVo userVo) {






        Reservation reservation = new Reservation();


            reservation.setAdditional_info(JSONObject.valueToString(pojo.getAdditional_info()));
            reservation.setCode(idGenService.bookingNo());
           // reservation.setItems_discount_amount(va);
           // reservation.setTotal_discount_amount(va);


                reservation.setValidationStatus(EnumValidationStatus.Unknow);

/*            reservation.setSetValidate_way(product.getValidate_way());

            reservation.setProductType(product.getType());
            reservation.setProductId(product.getId());
            reservation.setSupplier(product.getSupplierId());*/

/*            if(product.getType().equals(EnumProductType.Pass)){
                reservation.setFollowupPaid(EnumFulfillmentType.Create_pass);
            }else{
                reservation.setFollowupPaid(EnumFulfillmentType.Universal);

            }*/
        reservation.setFulfillment_behavior(EnumFulfillment_behavior.Universal);



          //  reservation.setPaymentMethods_json(product.getPaymentMethods_json());

/*            EnumPayChannel[] enumPayChannels =   new Gson().fromJson(product.getPaymentMethods_json(),EnumPayChannel[].class);
            if(Arrays.asList(enumPayChannels).contains(EnumPayChannel.free)){
                reservation.setType(EnumBookingType.Free);
            }else{
                reservation.setType(EnumBookingType.Standard);
            }*/
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


            if(userVo != null){
                reservation.setUser(userVo.getUser_id());
            }


            reservation.setCreated_at(LocalDateTime.now());
        reservation.setSetValidate_way(EnumValidateWay.none);

        reservation.setStatus(EnumBookingStatus.Draft); // 这里纯粹是 考虑 不要为空，实际可能是 free
            reservation = reservationRepository.save(reservation);


        Reservation finalReservation = reservation;
        List<BookingProductFuck> bookingProductFuckList =  bookingTypeTowhoVoList.stream().map(e->{
            if(!e.getSkus().isEmpty()){
                Product product = e.getProduct();

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
            }

        }).flatMap(List::stream).collect(Collectors.toList());

        bookingProductFuckList = bookingProductRepository.saveAll(bookingProductFuckList);
        reservation.setAmount(bookingProductFuckList.stream().mapToInt(e->e.getAmount()).sum());





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



        reservation.setCreated_at(LocalDateTime.now());
        reservation = reservationRepository.save(reservation);

        List<PhoneAuth> travelerReqs = pojo.getTravelers().stream().map(x->{
            PhoneAuth auth = new PhoneAuth();
            auth.setIdCardName(x.getName());
            auth.setIdCardNo(x.getId_card());
            auth.setPhoneNo(x.getPhone());
            return auth;
        }).collect(Collectors.toList());

        List<Traveler> travelerList = addBulkTraveler(reservation,travelerReqs );

            return Pair.with(reservation,bookingProductFuckList);



    }






    public Pair<Reservation,List<BookingTypeTowhoVo>> booking(List<BookingTypeTowhoVo> bookingTypeTowhoVo, WindowsTicketBookingPojo pojo) {



       List<BookingProductFuck> bookingProductFuckList =  bookingTypeTowhoVo.stream().map(e->{
            Product product = e.getProduct();
            BookingProductFuck bookingProductFuck = new BookingProductFuck();
            bookingProductFuck.setAmount(1);
            bookingProductFuck.setPrice(1);


           bookingProductFuck.setProductType(product.getType());
           bookingProductFuck.setProduct(product.getId());
           bookingProductFuck.setSupplier(product.getSupplierId());
           bookingProductFuck.setPaymentMethods_json(product.getPaymentMethods_json());
        //   bookingProduct.setAdditional_info(JSONObject.valueToString(pojo.getAdditional_info()));
           if(product.getType().equals(EnumProductType.Pass)){
               bookingProductFuck.setPaymentSplit(true);
               bookingProductFuck.setPaymentSplitCode(product.getCode());
           }





  /*         Optional<PricingType> pricingTypeOptional = pricingTypeRepository.findById(pojo.getPricingType());

           if(pricingTypeOptional.isEmpty()){
               throw new BookNotFoundException(Enumfailures.not_found,"价格不能为空");
           }
           PricingType pricingType = pricingTypeOptional.get();
*/


           bookingProductFuck.setQuantity(e.getQuantity().intValue());

           return bookingProductFuck;
        }).collect(Collectors.toList());

        bookingProductRepository.saveAll(bookingProductFuckList);



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



    private List<PricingType> getForProduct(Product product){

        List<PricingType> pricingTypes = pricingTypeRepository.findByProductId(product.getId());

        return pricingTypes;
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
}
