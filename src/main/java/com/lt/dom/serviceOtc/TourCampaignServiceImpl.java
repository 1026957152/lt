package com.lt.dom.serviceOtc;

import com.lt.dom.OctResp.ChargeResp;
import com.lt.dom.RealNameAuthentication.PhoneAuth;
import com.lt.dom.RealNameAuthentication.RealNameAuthenticationServiceImpl;
import com.lt.dom.error.Booking_not_pendingException;
import com.lt.dom.error.Missing_customerException;
import com.lt.dom.excel.ImportExcelBookingTraveler;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.ProductSubVo;
import com.lt.dom.vo.TravelerVo;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
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
    private CampaignAssignToTourProductRepository campaignAssignToTourProductRepository;
    @Autowired
    private CampaignAssignToTourBookingRepository campaignAssignToTourBookingRepository;

    @Autowired
    private PricingTypeRepository pricingTypeRepository;
    @Autowired
    private RealNameAuthenticationServiceImpl realNameAuthenticationService;
    @Autowired
    private TravelerRepository travelerRepository;
    @Autowired
    private VoucherServiceImpl voucherService;
    @Autowired
    private ChargeRepository chargeRepository;

    @Autowired
    private TourBookingRepository tourBookingRepository;

    @Autowired
    private AssetServiceImpl assetService;


    @Autowired
    private ApplyForApprovalServiceImpl applyForApprovalService;

    @Autowired
    private RedeemServiceImpl redeemService;


    @Autowired
    private PublicationServiceImpl publicationService;

    @Autowired
    private InchargeBookingRepository inchargeBookingRepository;

    @Autowired
    private UserServiceImpl userService;


    @Autowired
    private GuideServiceImpl guideService;

    public Triplet<TourBooking,Product,ProductSubVo> book(Product product, List<Campaign> campaignList, Guide guide, TourBookingPojo pojo, List<TravelerVo> travelerVoList, Supplier supplier, Session session) {



        TourBooking tourBooking = new TourBooking();
        tourBooking.setAdditional_info(JSONObject.valueToString(pojo.getTour()));
        tourBooking.setCode(idGenService.tourBookingNo());
      //  reservation.setItems_discount_amount(va);
      //  reservation.setTotal_discount_amount(va);
        tourBooking.setProductType(product.getType());
        tourBooking.setProductId(product.getId());
        tourBooking.setRedeemer(product.getSupplierId());
        tourBooking.setReviewing(false);



     //   List<PricingType> pricingTypes = getForProduct(product);

/*        Map<EnumProductPricingTypeByPerson,Integer> enumProductPricingTypeByPersonIntegerMap = pricingTypes.stream().filter(x->x.getType().equals(EnumProductPricingType.ByPerson)).collect(Collectors.toMap(x->x.getBy(),x->x.getPrice()));

        Integer total  = pojo.getTravelers().stream().mapToInt(x->{

            Integer price = enumProductPricingTypeByPersonIntegerMap.get(x.getBy());

            return price;
        }).sum();*/

     //   reservation.setAmount(total);
      //  reservation.setTotal_amount(reservation.getAmount()-reservation.getTotal_discount_amount());
        tourBooking.setStatus(EnumTourBookingStatus.Draft);
        tourBooking.setCreated_at(LocalDateTime.now());

        tourBooking.setOwner(supplier.getId());
        additional_info(tourBooking,pojo,guide);

        tourBooking = tourBookingRepository.save(tourBooking);



        guideService.guideToTourBooking(guide,tourBooking);



        Asset asset = assetService.newQr(tourBooking);







        ProductSubVo productSubVo = new ProductSubVo();
        productSubVo.setType(product.getType());


        if(product.getType().equals(EnumProductType.Daytour)){
            Optional<Tour> tour = tourRepository.findById(product.getTypeTo());
            productSubVo.setTour(tour.get());


            TourBooking finalTourBooking1 = tourBooking;
            List<Traveler> travelers = travelerRepository.saveAll(travelerVoList.stream().map(x->{
                return addTraveler(finalTourBooking1,x.getName(),x.getId(),x.getTel_home());
            }).collect(Collectors.toList()));



            System.out.println("添加 旅行人啊啊啊啊"+travelers.size());
            System.out.println("添加 旅行人啊啊啊啊"+travelers);

            TourBooking finalTourBooking = tourBooking;


            campaignList.stream().forEach(x->{
                publicationService.bulkPublish(supplier,travelers, x,EnumAssociatedType.tour_booking, finalTourBooking.getId(),productSubVo,session);

            });

            List<CampaignAssignToTourBooking> campaignAssignToTourBookingList = bookingCopyCampaigsFromProduct(tourBooking,product,travelers);

           // assetService.newQrWithSave(campaignAssignToTourBookingList.stream().map(x->Pair.with(x.getCode(),x.getId())).collect(Collectors.toList()));



            return Triplet.with(tourBooking,product,productSubVo);
        }



        return Triplet.with(tourBooking,product,productSubVo);

    }


    private TourBooking additional_info(TourBooking tourBooking, TourBookingPojo pojo, Guide guide){

        tourBooking.setAdditional_info_guide_name(guide.getReal_name());
        tourBooking.setAdditional_info_guide_phone(guide.getPhone());
        tourBooking.setAdditional_info_guide_id(guide.getId_card());
        tourBooking.setAdditional_info_tour_line_info(guide.getReal_name());
        tourBooking.setAdditional_info_tour_line_info(pojo.getTour().getLine_info());
        tourBooking.setAdditional_info_tour_title(pojo.getTour().getTitle());
        tourBooking.setAdditional_info_tour_code(pojo.getTour().getCode());
        tourBooking.setAdditional_info_tour_starts_at(pojo.getTour().getStarts_at());
        tourBooking.setAdditional_info_tour_ends_at(pojo.getTour().getEnds_at());
        return tourBooking;
    }



    private List<PricingType> additional_info(Product product){

        List<PricingType> pricingTypes = pricingTypeRepository.findByProductId(product.getId());

        return pricingTypes;
    }












    public List<Traveler> addBulkTraveler(TourBooking booking, List<TravelerReq> travelerReqList) {

        List<PhoneAuth> travelerReqs = travelerReqList.stream().map(x->{
            PhoneAuth auth = new PhoneAuth();
            auth.setIdCardName(x.getName());
            auth.setIdCardNo(x.getId());
            auth.setPhoneNo(x.getTel_home());
            return auth;
        }).collect(Collectors.toList());

        Pair<List<PhoneAuth>, List<PhoneAuth>> phoneAuthPhoneAuthPair = realNameAuthenticationService.bulkCheckRealname(travelerReqs);

        List<Traveler> transfers = phoneAuthPhoneAuthPair.getValue1().stream().map(x->{
            return addTraveler(booking,x.getIdCardName(),x.getIdCardNo(),x.getPhoneNo());
        }).collect(Collectors.toList());

        List<Traveler> travelers = travelerRepository.saveAll(transfers);

        return travelers;
    }

    public Traveler addTraveler(TourBooking booking, TravelerReq travelerReq) {

        PhoneAuth auth = new PhoneAuth();
        auth.setIdCardName(travelerReq.getName());
        auth.setIdCardNo(travelerReq.getId());
        auth.setPhoneNo(travelerReq.getTel_home());
        boolean isRealName = realNameAuthenticationService.checkRealname(auth);
        if(isRealName){



          //  realNameCreateUser(auth.getIdCardName(),auth.getIdCardNo(),auth.getPhoneNo());

            return addTraveler(booking,auth.getIdCardName(),auth.getIdCardNo(),auth.getPhoneNo());
        }else{
            throw new RuntimeException();
        }
    }


    public User realNameCreateUser(String real_name,String id_card,String phone) {


        UserPojo userPojo = new UserPojo();

        userPojo.setRealName(real_name);
        userPojo.setPhone(phone);
        userPojo.setIdCard(id_card);
        userPojo.setRoles(Arrays.asList("ROLE_ADMIN"));
        User user = userService.createUser(userPojo,Arrays.asList());
        return user;
    }




    private Traveler addTraveler(TourBooking booking,String name,String id_card,String phone) {

            Traveler transfer = new Traveler();
            transfer.setBooking(booking.getId());
            transfer.setName(name);
            transfer.setTel_home(phone);
            transfer.setIdNo(id_card);
            transfer = travelerRepository.save(transfer);
            return transfer;
    }


    public ChargeResp pay(TourBooking reservation, BookingPayPojo transferPojo) {

        List<Voucher> vouchers = transferPojo.getVoucherPojoList().stream().map(x->new Voucher()).collect(Collectors.toList());




        //int va = redeemService.redeemVounchor(vouchers);  //销和这些券

        reservation.setStatus(EnumTourBookingStatus.Completed);
        reservation = tourBookingRepository.save(reservation);


        TourBooking finalReservation = reservation;
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

















    public TourBooking submit(Supplier supplier, TourBooking tourBooking, BookingPayPojo transferPojo) {


        List<Traveler> travelers = travelerRepository.findAllByBooking(tourBooking.getId());

        if(travelers.size() == 0){
            throw new Missing_customerException(tourBooking.getId(),Reservation.class.getSimpleName(),"订单没有游客，需要添加游客再提交");
        }

        if(!tourBooking.getStatus().equals(EnumTourBookingStatus.Pending)){
            throw new Booking_not_pendingException(tourBooking.getId(),Reservation.class.getSimpleName(),"订单非等待提交状态，不得提交"+tourBooking.getStatus());
        }


        //获得了 消费券，获得了消费券，获得了消费券，
        List<Pair<Long,Voucher>> vouchers = null;// publicationService.bulkPublish(supplier,travelers.stream().map(x->x.getId()).collect(Collectors.toList()),tourBooking.getCampaign(), EnumAssociatedType.tour_booking, tourBooking.getId());

        Map<Long,Voucher> voucherMap = vouchers.stream().collect(Collectors.toMap(x->x.getValue0(), x->x.getValue1()));

        List<Pair<Traveler,Voucher>> sss = travelers.stream().map(x->{
            Voucher voucher = voucherMap.get(x.getId());
            return Pair.with(x,voucher);
        }).collect(Collectors.toList());


        int va = redeemService.RedeemVounchor(tourBooking.getId(),EnumAssociatedType.booking,sss);  //销和这些券

        tourBooking.setStatus(EnumTourBookingStatus.Completed);
        tourBooking = tourBookingRepository.save(tourBooking);


        return tourBooking;

    }



    public TourBooking tourBookingRequestLvApproval(Supplier supplier, TourBooking tourBooking, BookingPayPojo transferPojo,User user) {


        List<Traveler> travelers = travelerRepository.findAllByBooking(tourBooking.getId());

        if(travelers.size() == 0){
            throw new Missing_customerException(tourBooking.getId(),Reservation.class.getSimpleName(),"订单没有游客，需要添加游客再提交");
        }

        if(!tourBooking.getStatus().equals(EnumTourBookingStatus.Pending)){
            throw new Booking_not_pendingException(tourBooking.getId(),Reservation.class.getSimpleName(),"订单非等待提交状态，不得提交"+tourBooking.getStatus());
        }




       // Request va = applyForApprovalService.create(EnumRequestType.tour_approve,tourBooking,user);  //销和这些券

        tourBooking.setStatus(EnumTourBookingStatus.Pending);
        tourBooking = tourBookingRepository.save(tourBooking);


        return tourBooking;

    }




    public List<Campaign> listCampaigns(TourBooking id) {


            List<CampaignAssignToTourBooking> campaignAssignToTourProducts = campaignAssignToTourBookingRepository.findByTourBooking(id.getId());

            List<Campaign> campaigns =  campaignRepository.findAllById(campaignAssignToTourProducts.stream().map(x->x.getCampaign()).collect(Collectors.toList()));

            return campaigns;

    }



    private List<CampaignAssignToTourBooking> bookingCopyCampaigsFromProduct(TourBooking tourBooking, Product product, List<Traveler> travelers){
        if(product.getType().equals(EnumProductType.Daytour)){
            List<CampaignAssignToTourProduct> campaignAssignToTourProducts = campaignAssignToTourProductRepository.findByTourId(product.getTypeTo());



            List<CampaignAssignToTourBooking>  campaignAssignToTourBookingList =  campaignAssignToTourBookingRepository.saveAll(campaignAssignToTourProducts.stream().map(x->{
                CampaignAssignToTourBooking campaignAssignToTourBooking = new CampaignAssignToTourBooking();
                campaignAssignToTourBooking.setCampaign(x.getCampaign());
                campaignAssignToTourBooking.setCode(idGenService.campaignToTourBookingCode());
                campaignAssignToTourBooking.setBooking_code(x.getCampaign_code());
                campaignAssignToTourBooking.setUnredeemed_count(travelers.size());
                campaignAssignToTourBooking.setRedeemed_count(0);
                campaignAssignToTourBooking.setStatus(EnumCampaignToTourBookingStatus.NotRedeemed);
                campaignAssignToTourBooking.setTourBooking(tourBooking.getId());
                campaignAssignToTourBooking.setCampaign_code(x.getCampaign_code());
                return campaignAssignToTourBooking;
            }).collect(Collectors.toList()));

            assetService.newQrWithSave(campaignAssignToTourBookingList.stream().map(x->Pair.with(x.getCode(),x.getId())).collect(Collectors.toList()));

            return campaignAssignToTourBookingList;
        }else{
            throw new RuntimeException();
        }

    }


}
