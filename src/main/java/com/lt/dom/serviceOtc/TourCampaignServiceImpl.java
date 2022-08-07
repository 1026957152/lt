package com.lt.dom.serviceOtc;

import com.lt.dom.OctResp.ChargeResp;
import com.lt.dom.OctResp.ReservationResp;
import com.lt.dom.RealNameAuthentication.PhoneAuth;
import com.lt.dom.RealNameAuthentication.RealNameAuthenticationServiceImpl;
import com.lt.dom.error.Booking_not_pendingException;
import com.lt.dom.error.Missing_customerException;
import com.lt.dom.excel.ImportExcelBookingTraveler;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingPayPojo;
import com.lt.dom.otcReq.BookingPojo;
import com.lt.dom.otcReq.BulkTravelerReq;
import com.lt.dom.otcReq.TravelerReq;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TourCampaignServiceImpl {

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
    private CampaignAssignToReservationRepository campaignAssignToReservationRepository;

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
    TourBookingRepository tourBookingRepository;



    @Autowired
    ProductRepository productRepository;

    @Autowired
    RedeemServiceImpl redeemService;


    @Autowired
    PublicationServiceImpl publicationService;

    @Autowired
    InchargeBookingRepository inchargeBookingRepository;


    public Triplet<TourBooking,Product,Tour> book(Product product,Campaign campaign, BookingPojo pojo) {





        List<Voucher> vouchers = pojo.getDiscounts().stream().map(x->new Voucher()).collect(Collectors.toList());
       // Voucher voucher = voucherService.领券(product);


        int va = voucherService.获得折扣金额(product,vouchers);


        TourBooking reservation = new TourBooking();
        reservation.setAdditional_info(JSONObject.valueToString(pojo.getAdditional_info()));
        reservation.setCode(idGenService.bookingNo());
        reservation.setItems_discount_amount(va);
        reservation.setTotal_discount_amount(va);
        reservation.setProductType(product.getType());
        reservation.setProductId(product.getId());
        reservation.setRedeemer(product.getSupplierId());
        reservation.setCampaign(campaign.getId());




        List<PricingType> pricingTypes = getForProduct(product);

        Map<EnumProductPricingTypeByPerson,Integer> enumProductPricingTypeByPersonIntegerMap = pricingTypes.stream().filter(x->x.getType().equals(EnumProductPricingType.ByPerson)).collect(Collectors.toMap(x->x.getBy(),x->x.getPrice()));

        Integer total  = pojo.getTravelers().stream().mapToInt(x->{

            Integer price = enumProductPricingTypeByPersonIntegerMap.get(x.getBy());

            return price;
        }).sum();

        reservation.setAmount(total);
      //  reservation.setTotal_amount(reservation.getAmount()-reservation.getTotal_discount_amount());
        reservation.setStatus(EnumOrderStatus.Pending);

        reservation = tourBookingRepository.save(reservation);



        GuideInchargeBooking guideInchargeBooking = new GuideInchargeBooking();
        guideInchargeBooking.setBooking(reservation.getId());
        guideInchargeBooking.setGuideId(pojo.getAdditional_info().getGuide());
        inchargeBookingRepository.save(guideInchargeBooking);

        TourBooking finalReservation = reservation;
        if(product.getComponents() != null){
            List<ComponentVounch> componentVounchList =  product.getComponents().stream().map(x->{
                ComponentVounch componentVounch = new ComponentVounch();
                componentVounch.setReservationId(finalReservation.getId());
                componentVounch.setComponentRightId(x.getComponentRightId());
                componentVounch.setComponentId(x.getId());
                componentVounch.setCount(1);
                componentVounch.setSnCode("DDD");
                componentVounch.setRoyaltyRuleId(x.getRoyaltyRule().getId());
                return componentVounch;
            }).collect(Collectors.toList());
        }

        if(product.getType().equals(EnumProductType.Daytour)){
            Optional<Tour> tour = tourRepository.findById(product.getRaletiveId());
            return Triplet.with(reservation,product,tour.get());
        }

        return Triplet.with(reservation,product,null);

    }


    private List<PricingType> getForProduct(Product product){

        List<PricingType> pricingTypes = pricingTypeRepository.findByProductId(product.getId());

        return pricingTypes;
    }
    public Traveler addTraveler(TourBooking booking, TravelerReq travelerReq) {


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





    public ChargeResp pay(TourBooking reservation, BookingPayPojo transferPojo) {

        List<Voucher> vouchers = transferPojo.getVoucherPojoList().stream().map(x->new Voucher()).collect(Collectors.toList());




        //int va = redeemService.redeemVounchor(vouchers);  //销和这些券

        reservation.setStatus(EnumOrderStatus.Submitted);
        reservation = tourBookingRepository.save(reservation);


        TourBooking finalReservation = reservation;
        List<Charge> charges = transferPojo.getPaymentOptions().stream().map(x->{
            Charge charge = new Charge();
            charge.setAmount(finalReservation.getTotal_amount());
            charge.setPaid(false);
            charge.setOrderNo(finalReservation.getId());
           // charge.setChannel(x);
            return charge;
        }).collect(Collectors.toList());

        charges =  chargeRepository.saveAll(charges);

        return new ChargeResp();

    }



    public List<Traveler> addBulkTraveler(TourBooking booking, BulkTravelerReq transferPojo) {


        List<PhoneAuth> travelerReqs = transferPojo.getTravelers().stream().map(x->{
            PhoneAuth auth = new PhoneAuth();
            auth.setIdCardName(x.getName());
            auth.setIdCardNo(x.getId());
            auth.setPhoneNo(x.getTel_home());
            return auth;
        }).collect(Collectors.toList());


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

    public ReservationResp toResp(Pair<Reservation,Product> pair) {
        Reservation booking = pair.getValue0();
        Product product = pair.getValue1();
        ReservationResp reservationResp = new ReservationResp();

        reservationResp.setAmount(booking.getAmount());
        reservationResp.setCode(booking.getCode());
        reservationResp.setStatus(booking.getStatus());

        reservationResp.setTotal_amount(booking.getTotal_amount());
        reservationResp.setTotal_discount_amount(booking.getTotal_discount_amount());

        reservationResp.setProductType(booking.getProductType());
        reservationResp.setProductCode(product.getCode());

        return reservationResp;
    }















    public TourBooking submit(Supplier supplier, TourBooking reservation, BookingPayPojo transferPojo) {


        List<Traveler> travelers = travelerRepository.findAllByBooking(reservation.getId());

        if(travelers.size() == 0){
            throw new Missing_customerException(reservation.getId(),Reservation.class.getSimpleName(),"订单没有游客，需要添加游客再提交");
        }

        if(!reservation.getStatus().equals(EnumOrderStatus.Pending)){

            throw new Booking_not_pendingException(reservation.getId(),Reservation.class.getSimpleName(),"订单非等待提交状态，不得提交"+reservation.getStatus());

        }

        //获得了 消费券，获得了消费券，获得了消费券，
        List<Pair<Long,Voucher>> vouchers = publicationService.bulkPublish(supplier,travelers.stream().map(x->x.getId()).collect(Collectors.toList()),reservation.getCampaign());

        Map<Long,Voucher> voucherMap = vouchers.stream().collect(Collectors.toMap(x->x.getValue0(), x->x.getValue1()));

        List<Pair<Traveler,Voucher>> sss = travelers.stream().map(x->{
            Voucher voucher = voucherMap.get(x.getId());
            return Pair.with(x,voucher);
        }).collect(Collectors.toList());


        int va = redeemService.bulkRedeemVounchor(reservation.getId(),Reservation.class,sss);  //销和这些券

        reservation.setStatus(EnumOrderStatus.Submitted);
        reservation = tourBookingRepository.save(reservation);


        return reservation;

    }




    public List<Campaign> listCampaigns(TourBooking id) {


        Optional<Product> optional = productRepository.findById(id.getProductId());

        return campaignForProduct(optional.get());
    }


    private List<Campaign> campaignForProduct(Product product){
        if(product.getType().equals(EnumProductType.Daytour)){
            List<CampaignAssignToTourProduct> campaignAssignToTourProducts = campaignAssignToReservationRepository.findByTourId(product.getRaletiveId());

            List<Campaign> campaigns =  campaignRepository.findAllById(campaignAssignToTourProducts.stream().map(x->x.getCampaign()).collect(Collectors.toList()));

            return campaigns;
        }else{
            throw new RuntimeException();
        }

    }
}
