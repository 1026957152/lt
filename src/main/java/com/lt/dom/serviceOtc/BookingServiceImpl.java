package com.lt.dom.serviceOtc;

import com.lt.dom.OctResp.ChargeResp;
import com.lt.dom.OctResp.ReservationResp;
import com.lt.dom.RealNameAuthentication.PhoneAuth;
import com.lt.dom.RealNameAuthentication.RealNameAuthenticationServiceImpl;
import com.lt.dom.error.Booking_not_pendingException;
import com.lt.dom.error.Missing_customerException;
import com.lt.dom.excel.ImportExcelBookingTraveler;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.BookingTypeTowhoVo;
import org.javatuples.Pair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    TourBookingRepository tourBookingRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    RedeemServiceImpl redeemService;


    @Autowired
    PublicationServiceImpl publicationService;

    @Autowired
    InchargeBookingRepository inchargeBookingRepository;


    public Pair<Reservation,BookingTypeTowhoVo> book(BookingTypeTowhoVo bookingTypeTowhoVo, BookingPojo pojo) {


        if(bookingTypeTowhoVo.getToWhoTyp().equals(EnumBookingOjbectType.Product)){
            Product product = bookingTypeTowhoVo.getProduct();
            List<Voucher> vouchers = pojo.getDiscounts().stream().map(x->new Voucher()).collect(Collectors.toList());
            // Voucher voucher = voucherService.领券(product);


            int va = voucherService.获得折扣金额(product,vouchers);


            Reservation reservation = new Reservation();
            reservation.setAdditional_info(JSONObject.valueToString(pojo.getAdditional_info()));
            reservation.setCode(idGenService.bookingNo());
            reservation.setItems_discount_amount(va);
            reservation.setTotal_discount_amount(va);
            reservation.setProductType(product.getType());
            reservation.setProductId(product.getId());
            reservation.setRedeemer(product.getSupplierId());





            List<PricingType> pricingTypes = getForProduct(product);

            Map<EnumProductPricingTypeByPerson,Integer> enumProductPricingTypeByPersonIntegerMap = pricingTypes.stream().filter(x->x.getType().equals(EnumProductPricingType.ByPerson)).collect(Collectors.toMap(x->x.getBy(),x->x.getPrice()));

            Integer total  = pojo.getTravelers().stream().mapToInt(x->{

                Integer price = enumProductPricingTypeByPersonIntegerMap.get(x.getBy());

                return price;
            }).sum();

            reservation.setAmount(total);
            //  reservation.setTotal_amount(reservation.getAmount()-reservation.getTotal_discount_amount());


            reservation = reservationRepository.save(reservation);
            Optional<Tour> tour = tourRepository.findById(product.getTypeToWho());
            bookingTypeTowhoVo.setTour(tour.get());

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

            reservation.setStatus(EnumTourBookingStatus.Draft);
            reservation = reservationRepository.save(reservation);


            return Pair.with(reservation,bookingTypeTowhoVo);
        }





        return Pair.with(null,null);

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





    public ChargeResp pay(Reservation reservation, BookingPayPojo transferPojo) {

        List<Voucher> vouchers = transferPojo.getVoucherPojoList().stream().map(x->new Voucher()).collect(Collectors.toList());




        //int va = redeemService.redeemVounchor(vouchers);  //销和这些券

        reservation.setStatus(EnumTourBookingStatus.Submitted);
        reservation = reservationRepository.save(reservation);


        Reservation finalReservation = reservation;
        List<Charge> charges = transferPojo.getPaymentOptions().stream().map(x->{
            Charge charge = new Charge();
            charge.setAmount(finalReservation.getTotal_amount());
            charge.setPaid(false);
            charge.setBooking(finalReservation.getId());
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















    public List<Campaign> listCampaigns(Reservation id) {


        Optional<Product> optional = productRepository.findById(id.getProductId());

        return campaignForProduct(optional.get());
    }


    private List<Campaign> campaignForProduct(Product product){
        if(product.getType().equals(EnumProductType.Daytour)){
            List<CampaignAssignToTourProduct> campaignAssignToTourProducts = campaignAssignToTourProductRepository.findByTourId(product.getTypeToWho());

            List<Campaign> campaigns =  campaignRepository.findAllById(campaignAssignToTourProducts.stream().map(x->x.getCampaign()).collect(Collectors.toList()));

            return campaigns;
        }else{
            throw new RuntimeException();
        }

    }
}
