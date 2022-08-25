package com.lt.dom.controllerOct;

import com.google.gson.Gson;
import com.lt.dom.JwtUtils;
import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.Voucher_has_no_permistion_redeemException;
import com.lt.dom.error.voucher_disabledException;
import com.lt.dom.error.voucher_not_publishException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.RedemByCryptoCodePojo;
import com.lt.dom.otcReq.RedemBycodePojo;
import com.lt.dom.otcReq.ReviewReq;
import com.lt.dom.otcenum.EnumExportVoucher;
import com.lt.dom.otcenum.EnumVoucherRedemptionStatus;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.PublishTowhoVo;
import com.lt.dom.serviceOtc.IAuthenticationFacade;
import com.lt.dom.serviceOtc.RedeemServiceImpl;
import com.lt.dom.serviceOtc.RedemptionServiceImpl;
import com.lt.dom.vo.CodeWithLatLngVo;
import com.lt.dom.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Quintet;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class RedemptionRestController {

    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private RedemptionServiceImpl redemptionService;

    @Autowired
    private RedemptionRepository redemptionRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private RedemptionEntryRepository redemptionEntryRepository;


    @Autowired
    private ReviewRepository reviewRepository;


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PublicationEntryRepository publicationEntryRepository;
    @Autowired
    private TravelerRepository travelerRepository;
    @Autowired
    private DiscountRepository discountRepository;



    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedeemServiceImpl redeemService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TourBookingRepository tourBookingRepository;

    @Autowired
    private CampaignAssignToTourProductRepository campaignAssignToTourProductRepository;

    @Autowired
    private CampaignAssignToTourBookingRepository campaignAssignToTourBookingRepository;



    @PostMapping(value = "/redemptions/{REDEMPTION_ID}", produces = "application/json")
    public EntityModel<Redemption> getRedemption(@PathVariable long REDEMPTION_ID) {


        Optional<Redemption> validatorOptional = redemptionRepository.findById(REDEMPTION_ID);

        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(REDEMPTION_ID,"找不到核销");

        }


        EntityModel<Redemption> entityModel = EntityModel.of(validatorOptional.get());

        return entityModel;


    }

    @PostMapping(value = "/redemptions/{REDEMPTION_ID}/rollback", produces = "application/json")
    public List<Redemption> redemption(@PathVariable long REDEMPTION_ID) {


        Optional<Redemption> validatorOptional = redemptionRepository.findById(REDEMPTION_ID);

        if(validatorOptional.isPresent()){
            try {
                return redemptionService.rollback(validatorOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));



    }



    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/redemption_entries/Page_pageRedemptionEntry", produces = "application/json")
    public EntityModel Page_pageRedemptionEntry(@PathVariable long SUPPLIER_ID) {

        EntityModel entityModel = EntityModel.of(Map.of("status_list", Arrays.stream(EnumVoucherRedemptionStatus.values()).map(x->{

                    EnumResp enumResp = new EnumResp();

                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
                }).collect(Collectors.toList())
        ));
        entityModel.add(linkTo(methodOn(ExportRestController.class).createExport(EnumExportVoucher.redemption,null)).withRel("createExport"));
        entityModel.add(linkTo(methodOn(SearchRestController.class).searchRedemptions(null,null,null)).withRel("searchRedemption"));

        return entityModel;
    }

    @Operation(summary = "3、list所有员工")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/redemption_entries", produces = "application/json")
    public PagedModel pageRedemptionEntry(@PathVariable long SUPPLIER_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<RedemptionEntryResp>> assembler) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);

        if(optionalSupplier.isPresent()){
          //  optionalSupplier.get().getId(),
            Page<RedemptionEntry> clainQuotas = redemptionEntryRepository.findAll( pageable);


            return assembler.toModel(RedemptionResp.pageFrom(clainQuotas));
        }

        throw new RuntimeException();
    }


    @Operation(summary = "3、list所有员工")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/redemption_entries/summary", produces = "application/json")
    public ResponseEntity<SummarySupplierRedemptionResp> getRedemptionEntrySummary(@PathVariable long SUPPLIER_ID) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);

        if(optionalSupplier.isPresent()){

            List<RedemptionEntry> clainQuotas = redemptionEntryRepository.findAllBySupplier(optionalSupplier.get().getId());

            List<RedemptionEntry> today = redemptionEntryRepository
                    .findAllBySupplierAndCreatedAtAfter(optionalSupplier.get().getId(),LocalDate.now().atStartOfDay());


            SummarySupplierRedemptionResp summarySupplierRedemptionResp =SummarySupplierRedemptionResp.from(optionalSupplier.get(),clainQuotas);
            summarySupplierRedemptionResp.setToday_redeemed(today.size());
            return ResponseEntity.ok(summarySupplierRedemptionResp);
        }

        throw new RuntimeException();
    }






    //TODO 这里有点问题
    @PostMapping(value = "/validate", produces = "application/json")
    public Object validateVoucherByCode(@RequestBody RedemBycodePojo pojo_) {


        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails user_de = (UserDetails)authentication.getPrincipal();


        Optional<User> optionalUser = userRepository.findByPhone(user_de.getUsername());
        if(optionalUser.isEmpty()) {
            throw  new BookNotFoundException(pojo_.getCode(),"找不到登录用户");
        }


        Gson gson = new Gson();

        RedemBycodePojo.Code code =  gson.fromJson(pojo_.getCode(),RedemBycodePojo.Code.class);
        User user = optionalUser.get();
        Optional<Employee> optional = employeeRepository.findByUserId(user.getId());



        Optional<Supplier> optionalSupplier = supplierRepository.findById(optional.get().getSuplierId());

        CodeWithLatLngVo codeWithLatLngVo = new CodeWithLatLngVo();
        codeWithLatLngVo.setC(code.getCode());
        codeWithLatLngVo.setCt(code.getLatitude());
        codeWithLatLngVo.setCg(code.getLongitude());
        codeWithLatLngVo.setSt(pojo_.getLatitude());
        codeWithLatLngVo.setSg(pojo_.getLongitude());


        if(code.getCode().startsWith("TT-NI")){
            Optional<Voucher> optionalVoucher = voucherRepository.findByCode(code.getCode());



            if(optionalVoucher.isEmpty()) {
                throw  new BookNotFoundException(code.getCode(),"找不到消费券");


            }
            Voucher voucher =optionalVoucher.get();
            if(!voucher.isActive()){
                throw new voucher_disabledException(voucher.getId(),"");
            }
            if(!voucher.getPublished()){
                throw new voucher_not_publishException(voucher.getId(),code.getCode()+"券换没有被认领","券换没有被认领");
            }



            Optional<Campaign> optionalCampaign = campaignRepository.findById(voucher.getCampaign());
            boolean v = redeemService.是否符合验证(optionalCampaign.get(),new Supplier());


            if(v){




                Quintet<PublicationEntry,Voucher,PublishTowhoVo,Campaign,Supplier> pair= redeemService.ValidateVounchor(voucher,optionalCampaign.get(),optionalSupplier.get());





                String jwt = jwtUtils.generateJwtToken(gson.toJson(codeWithLatLngVo));

                RedemptionResp.SingleTowho resp = RedemptionResp.sigleEntryfrom(pair);

                EntityModel<RedemptionResp.SingleTowho> redemptionEntryEntityModel =  EntityModel.of(resp);
                resp.setCrypto_code(jwt);

                redemptionEntryEntityModel.add(linkTo(methodOn(RedemptionRestController.class).redeemVonchorByCryptoCode(null)).withRel("redeemByCryptoCode"));

                return redemptionEntryEntityModel;
            }else{
                throw new Voucher_has_no_permistion_redeemException(voucher.getId(),"Foo Not Found","该商户无法核销该券");
            }
        }

        if(code.getCode().startsWith("ctt")){
            Optional<CampaignAssignToTourBooking> campaignAssignToTourBookingOptional = campaignAssignToTourBookingRepository.findByCode(code.getCode());
            if(campaignAssignToTourBookingOptional.isEmpty()) {
                throw  new BookNotFoundException(code.getCode(),"找不到消费券");
            }

            CampaignAssignToTourBooking campaignAssignToTourBooking = campaignAssignToTourBookingOptional.get();


            Optional<Campaign> campaigns = campaignRepository.findById(campaignAssignToTourBooking.getCampaign());
            Optional<TourBooking> optionalTourBooking = tourBookingRepository.findById(campaignAssignToTourBooking.getTourBooking());


            List<Traveler> travelerList = travelerRepository.findAllByBooking(campaignAssignToTourBooking.getTourBooking());



            RedemptionResp.SingleTowho resp = RedemptionResp.sigleEntryfrom(optionalTourBooking.get(),campaigns,travelerList);


            EntityModel<RedemptionResp.SingleTowho> redemptionEntryEntityModel =  EntityModel.of(resp);
            String jwt = jwtUtils.generateJwtToken(gson.toJson(codeWithLatLngVo));

            resp.setCrypto_code(jwt);

            redemptionEntryEntityModel.add(linkTo(methodOn(RedemptionRestController.class).redeemVonchorByCryptoCode(null)).withRel("redeemByCryptoCode"));

            return redemptionEntryEntityModel;

        }


        throw new BookNotFoundException("找不到核销券","找不到核销券");
    }




/*



    //TODO 这里有点问题
    @PostMapping(value = "/redemptions", produces = "application/json")
    public Object redeemVonchorBycode(@RequestBody RedemBycodePojo pojo) {


        Authentication authentication = authenticationFacade.getAuthentication();


         UserDetails user_de = (UserDetails)authentication.getPrincipal();

        Optional<User> optionalUser = userRepository.findByPhone(user_de.getUsername());


        if(optionalUser.isEmpty()) {
            throw  new BookNotFoundException(pojo.getCode(),"找不到登录用户");
        }


        Gson gson = new Gson();
        RedemBycodePojo.Code code =  gson.fromJson(pojo.getCode(),RedemBycodePojo.Code.class);

        User user = optionalUser.get();
        Optional<Employee> optional = employeeRepository.findByUserId(user.getId());

        Optional<Supplier> optionalSupplier = supplierRepository.findById(optional.get().getSuplierId());

        // Asset asset = assetRepository.findById();

        if(code.getCode().startsWith("TT-NI")){
            Optional<Voucher> optionalVoucher = voucherRepository.findByCode(code.getCode());


            if(optionalVoucher.isEmpty()) {
                throw  new BookNotFoundException(pojo.getCode(),"找不到消费券");
            }
            Voucher voucher =optionalVoucher.get();
            if(!voucher.isActive()){
                throw new voucher_disabledException(voucher.getId(),"");
            }
            if(!voucher.getPublished()){
                throw new voucher_not_publishException(voucher.getId(),pojo.getCode()+"券换没有被认领","券换没有被认领");
            }



            Optional<Campaign> optionalCampaign = campaignRepository.findById(voucher.getCampaign());
            boolean v = redeemService.是否符合验证(optionalCampaign.get(),new Supplier());
            if(v){
                Triplet<RedemptionEntry,Redemption, PublishTowhoVo> pair= redeemService.bulkRedeemVounchor(voucher,optionalCampaign.get(),optionalSupplier.get());


                RedemptionResp resp = RedemptionResp.from(pair);
                EntityModel<RedemptionResp> redemptionEntryEntityModel =  EntityModel.of(resp);


                return redemptionEntryEntityModel;
            }else{
                throw new Voucher_has_no_permistion_redeemException(voucher.getId(),"Foo Not Found","该商户无法核销该券");
            }
        }

        if(code.getCode().startsWith("ctt")){
            Optional<TourBooking> optionalTourBooking = tourBookingRepository.findByCode(pojo.getCode());
            if(optionalTourBooking.isEmpty()) {
                throw  new BookNotFoundException(pojo.getCode(),"找不到消费券");
            }


            TourBooking tourBooking = optionalTourBooking.get();

            List<CampaignAssignToTourProduct> campaignAssignToTourProductList = campaignAssignToTourProductRepository.findByProduct(tourBooking.getProductId());

            List<Campaign> campaigns = campaignRepository.findAllById(campaignAssignToTourProductList.stream().map(x->x.getCampaign()).collect(Collectors.toList()));

            List<Triplet<RedemptionEntry,Redemption,PublishTowhoVo>> pair= redeemService.bulkRedeemVounchor(tourBooking,campaigns,optionalSupplier.get());
            return RedemptionResp.from(pair);

        }




        throw new BookNotFoundException("找不到核销券","找不到核销券");
    }

*/



    //TODO 这里有点问题
    @PostMapping(value = "/crypto_redemptions", produces = "application/json")
    public Object redeemVonchorByCryptoCode(@RequestBody RedemByCryptoCodePojo pojo___) {





        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userOv = authenticationFacade.getUserVo(authentication);



        String code_json = jwtUtils.getUserNameFromJwtToken(pojo___.getCrypto_code());
        Gson gson = new Gson();
        CodeWithLatLngVo codeWithLatLngVo =  gson.fromJson(code_json,CodeWithLatLngVo.class);

        Supplier supplier = userOv.getSupplier();


        if(codeWithLatLngVo.getC().startsWith("TT-NI")){
            Optional<Voucher> optionalVoucher = voucherRepository.findByCode(codeWithLatLngVo.getC());


            if(optionalVoucher.isEmpty()) {
                throw  new BookNotFoundException(codeWithLatLngVo.getC(),"找不到消费券");
            }
            Voucher voucher =optionalVoucher.get();
            if(!voucher.isActive()){
                throw new voucher_disabledException(voucher.getId(),"");
            }
            if(!voucher.getPublished()){
                throw new voucher_not_publishException(voucher.getId(),codeWithLatLngVo.getC()+"券换没有被认领","券换没有被认领");
            }



            Optional<Campaign> optionalCampaign = campaignRepository.findById(voucher.getCampaign());
            boolean v = redeemService.是否符合验证(optionalCampaign.get(),supplier);
            if(v){
                Triplet<RedemptionEntry,Redemption, PublishTowhoVo> pair= redeemService.bulkRedeemVounchor(voucher,optionalCampaign.get(),supplier);


                RedemptionResp resp = RedemptionResp.from(pair);
                EntityModel<RedemptionResp> redemptionEntryEntityModel =  EntityModel.of(resp);


                return redemptionEntryEntityModel;
            }else{
                throw new Voucher_has_no_permistion_redeemException(voucher.getId(),"Foo Not Found","该商户无法核销该券");
            }
        }

        if(codeWithLatLngVo.getC().startsWith("ctt")){

            Optional<CampaignAssignToTourBooking> campaignAssignToTourBookingOptional = campaignAssignToTourBookingRepository.findByCode(codeWithLatLngVo.getC());
            if(campaignAssignToTourBookingOptional.isEmpty()) {
                throw  new BookNotFoundException(Enumfailures.resource_not_found,"找不到消费券");
            }

            CampaignAssignToTourBooking campaignAssignToTourBooking = campaignAssignToTourBookingOptional.get();


            Optional<Campaign> campaigns = campaignRepository.findById(campaignAssignToTourBooking.getCampaign());
            Optional<TourBooking> optionalTourBooking = tourBookingRepository.findById(campaignAssignToTourBooking.getTourBooking());
            TourBooking tourBooking = optionalTourBooking.get();
            Campaign campaign = campaigns.get();

            List<Traveler> travelerList = travelerRepository.findAllByBooking(campaignAssignToTourBooking.getTourBooking());



            List<Triplet<RedemptionEntry,Redemption,PublishTowhoVo>> pair= redeemService.bulkRedeemVounchor(campaignAssignToTourBooking,tourBooking,campaign,supplier);
            return RedemptionResp.from(pair);

        }




        throw new BookNotFoundException("找不到核销券","找不到核销券");
    }


















    @PostMapping(value = "/redemptions/{REDEMPTION_ID}/reviews", produces = "application/json")
    public Review createReviews(@PathVariable long REDEMPTION_ID, @RequestBody ReviewReq pojo) {


        Optional<Redemption> validatorOptional = redemptionRepository.findById(REDEMPTION_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(REDEMPTION_ID,"找不到核销");
        }
        Redemption redemption = validatorOptional.get();
        Review review = new Review();
        review.setCharge(redemption.getCode());
        review.setCreated_at(LocalDateTime.now());
        review.setOpen(true);
        review = reviewRepository.save(review);


        return review;

    }




}