package com.lt.dom.controllerOct;

import com.google.gson.Gson;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lt.dom.JwtUtils;
import com.lt.dom.OctResp.*;
import com.lt.dom.error.*;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.PublishTowhoVo;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.serviceOtc.product.BusTicketServiceImpl;
import com.lt.dom.serviceOtc.product.CityPassServiceImpl;
import com.lt.dom.serviceOtc.product.MultiTicketServiceImpl;
import com.lt.dom.vo.CodeWithLatLngVo;
import com.lt.dom.vo.PersonBean;
import com.lt.dom.vo.UserVo;
import com.lt.dom.vo.WrittenOffMerchantVo;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Quintet;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class RedemptionRestController {
    private static final Logger logger = LoggerFactory.getLogger(RedemptionRestController.class);

    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private CardholderRepository cardholderRepository;



    @Autowired
    private MultiTicketServiceImpl multiTicketService;
    @Autowired
    private BusTicketServiceImpl busTicketService;


    @Autowired
    private PassRepository passRepository;

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private ValidatorServiceImpl validatorService;

    @Autowired
    private CryptoServiceImpl cryptoService;




    @Autowired
    private ComponentRightServiceImpl componentRightService;

    @Autowired
    private CityPassServiceImpl cityPassService;



    @Autowired
    private ComponentVounchRepository componentVounchRepository;

    @Autowired
    private ValidateServiceImpl validateService;




    @Autowired
    private IntoOnecodeServiceImpl intoOnecodeService;



    @Autowired
    private RedemptionServiceImpl redemptionService;

    @Autowired
    private VoucherTicketRepository voucherTicketRepository;


    @Autowired
    private RedemptionRepository redemptionRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private RedemptionEntryRepository redemptionEntryRepository;
    @Autowired
    private RightRedemptionEntryRepository rightRedemptionEntryRepository;


    @Autowired
    private ReviewRepository reviewRepository;


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QrcodeServiceImpl qrcodeService;
    @Autowired
    private TravelerRepository travelerRepository;
    @Autowired
    private ClainQuotaServiceImpl clainQuotaService;



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
    private CampaignAssignToTourBookingRepository campaignAssignToTourBookingRepository;



    @PostMapping(value = "/right_redemption_entries/{REDEMPTION_ID}", produces = "application/json")
    public EntityModel getRightRedemptionEntry(@PathVariable long REDEMPTION_ID) {


        Optional<RightRedemptionEntry> validatorOptional = rightRedemptionEntryRepository.findById(REDEMPTION_ID);

        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(REDEMPTION_ID,"找不到核销");

        }
        RightRedemptionEntry rightRedemptionEntry = validatorOptional.get();


        EntityModel<RightRedemptionEntry> entityModel = EntityModel.of(rightRedemptionEntry);

        return entityModel;


    }







    @GetMapping(value = "/redemptions/{REDEMPTION_ID}", produces = "application/json")
    public EntityModel<Redemption> getRedemption(@PathVariable long REDEMPTION_ID) {


        Optional<Redemption> validatorOptional = redemptionRepository.findById(REDEMPTION_ID);

        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(REDEMPTION_ID,"找不到核销");

        }

        Redemption x = validatorOptional.get();

        RedemptionResp entry = RedemptionResp.from(x);


        RedemptionResp.RelateObject relateObject = new RedemptionResp.RelateObject();
        relateObject.setType(x.getRelatedObjectType());
        relateObject.setType_text(x.getRelatedObjectType().toString());
        relateObject.setId(x.getRelatedObjectId());
        relateObject.setCode(x.getLog_RelatedObject_code());
        relateObject.setLable(x.getLog_RelatedObject_lable());
        relateObject.setObjectType(x.getLog_RelatedObject_type());
        relateObject.setObjectType_text(x.getLog_RelatedObject_type().toString());


        entry.setRelateObject(relateObject);



        RedemptionResp.Customer customer = RedemptionResp.Customer.from(x);
        entry.setCustomer(customer);




        List<RightRedemptionEntry> componentVounchList = rightRedemptionEntryRepository.findAllByRedemption(x.getId());

        entry.setEntries(componentVounchList.stream().map(e->{
            return RightRedemptionEntryResp.from(e);
        }).collect(Collectors.toList()));



        EntityModel entityModel = EntityModel.of(entry);
        entityModel.add(linkTo(methodOn(RedemptionRestController.class).getRedemption(x.getId())).withSelfRel());

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

    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/redemptions", produces = "application/json")
    public PagedModel listRedemption(@PathVariable long SUPPLIER_ID ,
                                     @PageableDefault(sort = {"createdDate",
                                             "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable ,
                                     PagedResourcesAssembler<EntityModel<RedemptionEntryResp>> assembler) {


        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);

        if(optionalSupplier.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.not_found,"找不到供应商");
        }
            //  optionalSupplier.get().getId(),
            Page<Redemption> clainQuotas = redemptionRepository.findAll( pageable);



            return assembler.toModel(clainQuotas.map(x -> {
                RedemptionResp entry = RedemptionResp.from(x);


                RedemptionResp.RelateObject relateObject = new RedemptionResp.RelateObject();
                relateObject.setType(x.getRelatedObjectType());
                relateObject.setId(x.getRelatedObjectId());
                relateObject.setCode(x.getLog_RelatedObject_code());
                relateObject.setLable(x.getLog_RelatedObject_lable());
                relateObject.setObjectType(x.getLog_RelatedObject_type());


/*                if(x.getRelatedObjectType().equals(EnumRelatedObjectType.voucher)){
                    VoucherTicket voucherTicket = voucherTicketRepository.findById(x.getRelatedObjectId()).get();
                    relateObject.setReferer(VoucherTicketResp.from(voucherTicket));
                }*/
                entry.setRelateObject(relateObject);




              //  User user = userRepository.findById(x.getCustomer()).get();

                RedemptionResp.Customer customer = RedemptionResp.Customer.from(x);
                entry.setCustomer(customer);



                EntityModel redemptionEntryEntityModel = EntityModel.of(entry);

                List<RightRedemptionEntry> componentVounchList = rightRedemptionEntryRepository.findAllByRedemption(x.getId());

                entry.setEntries(componentVounchList.stream().map(e->{
                    return RightRedemptionEntryResp.from(e);
                }).collect(Collectors.toList()));
                redemptionEntryEntityModel.add(linkTo(methodOn(RedemptionRestController.class).getRedemption(x.getId())).withSelfRel());

                return redemptionEntryEntityModel;
            }));


    }
    @Operation(summary = "3、list所有员工")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/redemption_entries", produces = "application/json")
    public PagedModel listRedemptionEntry(@PathVariable long SUPPLIER_ID,
                                          Pageable pageable,
                                          PagedResourcesAssembler<EntityModel<RedemptionEntryResp>> assembler) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);

        if(optionalSupplier.isPresent()){
          //  optionalSupplier.get().getId(),
            Page<RedemptionEntry> clainQuotas = redemptionEntryRepository.findAll( pageable);



            return assembler.toModel(clainQuotas.map(x -> {
                RedemptionEntryResp entry = new RedemptionEntryResp();
                // entry.setResult(x.getResult());
                // entry.setCustomer_id(x.getCustomer_id());
                entry.setRelatedObjectType(x.getRelatedObjectType());

                entry.setRedeem_at(x.getRedeemed_at());
                entry.setRedeemed_amount(x.getRedeemed_amount());
                entry.setRedeemed_quantity(x.getRedeemed_quantity());
                entry.setCampaign_name(x.getCampaign_name());
                entry.setVoucher_code(x.getVoucher_code());

                entry.setVoucher_code(x.getMerchant_code());
                entry.setHolder_id(x.getHolder_id_card());
                entry.setHolder_phone(x.getHolder_phone());
                entry.setHolder_real_name(x.getHolder_real_name());
                entry.setHolder_name(x.getHolder_real_name());
                entry.setRedeemer_code(x.getMerchant_code());
                entry.setRedeemer_name(x.getMerchant_name());


                entry.setRedeemer_employee_code(x.getWritten_off_staff_name());
                entry.setRedeemer_employee_name(x.getWritten_off_staff_name());
                entry.setRedeemer_employee_id(x.getWritten_off_staff_id());
                entry.setRedeemer_name(x.getMerchant_name());

                entry.setCode(x.getCode());

                entry.setPublished_at(x.getPublished_at());
                entry.setRedeemed_at(x.getRedeemed_at());




                EntityModel<RedemptionEntryResp> redemptionEntryEntityModel = EntityModel.of(entry);

                redemptionEntryEntityModel.add(linkTo(methodOn(RedemptionRestController.class).getRedemption(x.getId())).withSelfRel());

                return redemptionEntryEntityModel;
            }));
        }

        throw new RuntimeException();
    }

    @Operation(summary = "3、list所有员工")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/right_entries", produces = "application/json")
    public PagedModel listRightRedemptionEntry(@PathVariable long SUPPLIER_ID,
                                          Pageable pageable,
                                          PagedResourcesAssembler<EntityModel<RedemptionEntryResp>> assembler) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);

        if(optionalSupplier.isEmpty()) {

            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到供应商");
        }
            //  optionalSupplier.get().getId(),
            Page<RightRedemptionEntry> clainQuotas = rightRedemptionEntryRepository.findAll( pageable);



            return assembler.toModel(clainQuotas.map(x -> {
                RightRedemptionEntryResp entry = RightRedemptionEntryResp.from(x);

                ComponentVounch componentVounch = componentVounchRepository.findById(x.getComponentVoucher()).get();

                entry.setComponentVoucher(ComponentVounchResp.from(componentVounch));

                EntityModel redemptionEntryEntityModel = EntityModel.of(entry);


                redemptionEntryEntityModel.add(linkTo(methodOn(RedemptionRestController.class).getRightRedemptionEntry(x.getId())).withSelfRel());

                return redemptionEntryEntityModel;
            }));

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


        UserVo userVo = authenticationFacade.getUserVo(authentication);


        System.out.println("参数啊啊 {}"+pojo_.toString());
        System.out.println("参数啊啊 getUser_id {}"+userVo.getUser_id());


        Gson gson = new Gson();


        RedemBycodePojo.Code code = new RedemBycodePojo.Code();//  gson.fromJson(pojo_.getCode(),RedemBycodePojo.Code.class);
        code.setCode(pojo_.getCode());

    //    code.setCode(qrcodeService._decode(code.getCode()));




        Supplier supplier = userVo.getSupplier();

        CodeWithLatLngVo codeWithLatLngVo = new CodeWithLatLngVo();
/*        codeWithLatLngVo.setC(code.getCode());
        codeWithLatLngVo.setCt(code.getLatitude());
        codeWithLatLngVo.setCg(code.getLongitude());*/
        codeWithLatLngVo.setSt(pojo_.getLatitude());
        codeWithLatLngVo.setSg(pojo_.getLongitude());





        if(code.getCode().startsWith("TT-NI")){
            Optional<Voucher> optionalVoucher = voucherRepository.findByCode(code.getCode());


            if(optionalVoucher.isEmpty()) {
                throw  new BookNotFoundException(Enumfailures.resource_not_found,"找不到消费券"+code.getCode());


            }
            Voucher voucher =optionalVoucher.get();
            if(!voucher.isActive()){
                throw new BookNotFoundException(Enumfailures.voucher_not_active,"该券状态不活跃");
            }
            if(!voucher.getPublished()){
                throw new voucher_not_publishException(voucher.getId(),code.getCode()+"券换没有被认领","券换没有被认领");
            }



            Optional<Campaign> optionalCampaign = campaignRepository.findById(voucher.getCampaign());
            Campaign campaign = optionalCampaign.get();


            boolean v = clainQuotaService.validate_writenOff(supplier,campaign);


            if(!v) {
                throw new BookNotFoundException(Enumfailures.voucher_has_no_permistion_redeem,"您没有核销配额，请联系发券方申请配额","该商户无法核销该券");

            }



                Quintet<PublicationEntry,Voucher,PublishTowhoVo,Campaign,Supplier> pair= redeemService.ValidateVounchor(voucher,optionalCampaign.get(),supplier);





                String jwt = jwtUtils.generateJwtToken(gson.toJson(codeWithLatLngVo));

                RedemptionResp.SingleTowho resp = RedemptionResp.sigleEntryfrom(pair);

                EntityModel<RedemptionResp.SingleTowho> redemptionEntryEntityModel =  EntityModel.of(resp);
                resp.setCrypto_code(jwt);

                redemptionEntryEntityModel.add(linkTo(methodOn(RedemptionRestController.class).redeemVonchorByCryptoCode(null)).withRel("redeemByCryptoCode"));

                return redemptionEntryEntityModel;

        }

        if(code.getCode().startsWith("ctt")){
            Optional<CampaignAssignToTourBooking> campaignAssignToTourBookingOptional = campaignAssignToTourBookingRepository.findByCode(code.getCode());
            if(campaignAssignToTourBookingOptional.isEmpty()) {
                throw  new BookNotFoundException(code.getCode(),"找不到消费券");
            }

            CampaignAssignToTourBooking campaignAssignToTourBooking = campaignAssignToTourBookingOptional.get();
            if(campaignAssignToTourBooking.getStatus().equals(EnumCampaignToTourBookingStatus.AlreadyRedeemed)){
                throw  new ExistException(Enumfailures.invalid_voucher,"团订单领票已核销");
            }

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






        PersonBean.Person person = null;
        try {
            person = PersonBean.Person.parseFrom(Base64.getDecoder().decode(code.getCode()));


            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(person.getAge()), TimeZone
                    .getDefault().toZoneId());
            logger.info("扫到了 主人卡 解密前：{} ,{},  {}",code.getCode(),person.getName(),localDateTime);

            code.setCode(person.getName());
        } catch (InvalidProtocolBufferException e) {
            logger.error("解析扫码错误,{}",code.getCode());
            throw new RuntimeException(e);
        }catch (Exception e){
            logger.error("解析扫码错误,{}  {}a    q",code.getCode(),e);
          //  throw new BookNotFoundException(Enumfailures.not_found,"解析码错误");
            code.setCode(pojo_.getCode());
        }




        if(code.getCode().startsWith("into_")){
            User user = intoOnecodeService.byCode(code.getCode());

            //  Authentication authentication = authenticationFacade.getAuthentication();

            UserVo userOv = authenticationFacade.getUserVo(authentication);

            //  Supplier supplier = userOv.getSupplier();

            List<ComponentVounch> componentVounchList = componentVounchRepository.findAllByUser(user.getId());
            if(componentVounchList.size() ==0){
                throw new BookNotFoundException(Enumfailures.not_found,"该 用户无可核销得 权益"+user.getPhone());

            }

            return validateService.user扫文旅码(user,userOv,componentVounchList);
        }

        Optional<VoucherTicket> optionalVoucher = voucherTicketRepository.findByCode(code.getCode());


        EntityModel entityModel_city = cityPassService.validate(optionalVoucher.get(),userVo);
        if(entityModel_city != null){
            return entityModel_city;
        }


        EntityModel entityModel = multiTicketService.validate(optionalVoucher.get(),userVo);
        if(entityModel != null){
            return entityModel;
        }


        entityModel = busTicketService.validate(optionalVoucher.get(),userVo);
        if(entityModel != null){
            return entityModel;
        }


        throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到核销券_验证");
    }




/*
    //TODO 这里有点问题
    @PostMapping(value = "/validate_by_id_number", produces = "application/json")
    public Object validate_by_id_number(@RequestBody OnecodeScanPojo pojo___) {



        //  User user_文旅码用户 = intoOnecodeService.byCode(pojo___.getCode());


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userOv_景区检票员 = authenticationFacade.getUserVo(authentication);


        List<Cardholder> cardholderList = cardholderRepository.findByIdentity(pojo___.getId());


        RedemBycodePojo.Code redemBycodePojo = new RedemBycodePojo.Code();
        redemBycodePojo.setCode(cardholderList.get(0).getIdentity());
        EntityModel entityModel = cityPassService.validate_by_idnumber(redemBycodePojo,userOv_景区检票员);


        if(entityModel!= null){
            return entityModel;
        }


        throw new BookNotFoundException(Enumfailures.not_found,"找不到这个权益券 "+pojo___.getCode());



    }*/

    @PostMapping(value = "/crypt", produces = "application/json")
    public Object getCrypto(@RequestBody RedemBycodeFromBusScannerPojo code) {

        String code_st = cryptoService.decorated_encode(code.getCode());

        return code_st;

    }




    //TODO 这里有点问题
    @PostMapping(value = "/lookup", produces = "application/json")
    public Object Lookup(@RequestBody RedemBycodeFromBusScannerPojo code) {





        String code_st = cryptoService.decorated_decode(code.getCode());


        Optional<VoucherTicket> voucherTicketOptional = voucherTicketRepository.findByCode(code_st);
        if(voucherTicketOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到券");
        }


        VoucherTicket voucherTicket = voucherTicketOptional.get();

        List<ComponentVounch> componentVounchList = validatorService.getComponents(voucherTicket.getCode());


        RedemptionTryResp resp = new RedemptionTryResp();


        componentRightService.sentEntries(voucherTicket.getCode(),resp,componentVounchList);
        //  resp.setRedeem_voucher_key(voucherTicket.getCode());
        resp.setAvailable(resp.isRedeemAllowed());
        resp.setRedeemAllowed(null);
        resp.setPartiallyRedeemable(voucherTicket.getPartiallyRedeemable());

        if(resp.getAvailable()){
            resp.setRedeem_voucher_key(voucherTicket.getCode());

        }
        resp.setCrypto_code(null);
        resp.setType_text(voucherTicket.getType().toString());
        return resp;




    }

    //TODO 这里有点问题
    @PostMapping(value = "/verify", produces = "application/json")
    public Object bus_ticket_validator(@RequestBody RedemBycodeFromBusScannerPojo code) {



        Optional<Device> deviceOptional = deviceRepository.findByCode(code.getDeviceCode());


        if(deviceOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到设备");
        }
        Device device = deviceOptional.get();

        if(!device.getType().equals(EnumDeviceType.BUS_QRCODE_READER)){
            throw new BookNotFoundException(Enumfailures.not_found,"不支持的设备");
        }


        String code_st = cryptoService.decorated_decode(code.getCode());

/*
        if(!code_st.startsWith("tike_")) {

            throw new BookNotFoundException(Enumfailures.not_found,"不是券");

        }*/

        Optional<VoucherTicket> voucherTicketOptional = voucherTicketRepository.findByCode(code_st);
            if(voucherTicketOptional.isEmpty()){

                throw new BookNotFoundException(Enumfailures.not_found,"找不到券");
            }


        VoucherTicket voucherTicket = voucherTicketOptional.get();
        if(voucherTicket.getType().equals(EnumVoucherType.PASS)){

            Optional<Pass> optionalVoucher= passRepository.findById(voucherTicket.getRelateId());
            Pass pass = optionalVoucher.get();
            List<ComponentVounch> componentVounchList = validatorService.check(device.getId(),pass.getCode());


            RedemptionTryResp resp = new RedemptionTryResp();


            componentRightService.sentEntries(pass.getCode(),resp,componentVounchList);
            resp.setCrypto_code(pass.getCode());

            resp.setType_text(voucherTicket.getType().toString());

            return resp;
        }

        List<ComponentVounch> componentVounchList = validatorService.check(device.getId(),voucherTicket.getCode());


        RedemptionTryResp resp = new RedemptionTryResp();


        componentRightService.sentEntries(voucherTicket.getCode(),resp,componentVounchList);


        resp.setAvailable(resp.isRedeemAllowed());
        resp.setRedeemAllowed(null);
        if(resp.getAvailable()){
            resp.setRedeem_voucher_key(voucherTicket.getCode());

        }
        resp.setCrypto_code(null);
        resp.setType_text(voucherTicket.getType().toString());
        return resp;





    }


    @PostMapping(value = "/code_availability", produces = "application/json")
    public Object code_availability(@RequestBody RedemBycodeFromBusScannerPojo pojo_) {


        String code_st = cryptoService.decorated_decode(pojo_.getCode());

        System.out.println("参数啊啊 {}"+pojo_.toString());

        logger.debug("解析 code {}",code_st);
        Optional<VoucherTicket> voucherTicketOptional = voucherTicketRepository.findByCode(code_st);
        if(voucherTicketOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到券");
        }

        VoucherTicket voucherTicket = voucherTicketOptional.get();



        if(Arrays.asList(EnumVoucherStatus.PartiallyRedeemed,
                EnumVoucherStatus.InGracePeriod,
                EnumVoucherStatus.Valid
                ).contains(voucherTicket.getStatus())){

            return Map.of("available",true);
        }else{
            return Map.of("available",false);
        }



    }


    //TODO 这里有点问题
    @PostMapping(value = "/quick_redeem", produces = "application/json")
    public Object quick_redeem_busticket(@RequestBody RedemBycodeFromBusScannerPojo pojo_) {




        Optional<Device> deviceOptional = deviceRepository.findByCode(pojo_.getDeviceCode());


        if(deviceOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到设备");
        }
        Device device = deviceOptional.get();
        logger.debug("设备id{} {}", device.getType(),device.getCode());
        if(!device.getType().equals(EnumDeviceType.BUS_QRCODE_READER)){


            throw new BookNotFoundException(Enumfailures.not_found,"不支持的设备");
        }

        String code_st = cryptoService.decorated_decode(pojo_.getCode());

        System.out.println("参数啊啊 {}"+pojo_.toString());

        logger.debug("解析 code {}",code_st);
        Optional<VoucherTicket> voucherTicketOptional = voucherTicketRepository.findByCode(code_st);
        if(voucherTicketOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到券");
        }

        VoucherTicket voucherTicket = voucherTicketOptional.get();


        CodeWithLatLngVo codeWithLatLngVo = new CodeWithLatLngVo();
/*        codeWithLatLngVo.setC(code.getCode());
        codeWithLatLngVo.setCt(code.getLatitude());
        codeWithLatLngVo.setCg(code.getLongitude());*/
        codeWithLatLngVo.setSt(pojo_.getLatitude());
        codeWithLatLngVo.setSg(pojo_.getLongitude());

        codeWithLatLngVo.setC(code_st);


        EntityModel entityModel =  redemptionService.redeem(voucherTicket,device);


        if(entityModel != null){
            return entityModel;
        }



        throw new BookNotFoundException(Enumfailures.not_found,"找不到核销券");


    }



    //TODO 这里有点问题 redemptions
    @PostMapping(value = "/crypto_redemptions", produces = "application/json")
    public Object redeemVonchor(@RequestBody RedemByCryptoCodePojo pojo___) {

        logger.debug("参数 {}", pojo___);

        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userOv = authenticationFacade.getUserVo(authentication);



        CodeWithLatLngVo codeWithLatLngVo =  new CodeWithLatLngVo();
        codeWithLatLngVo.setC(pojo___.getCode());


        codeWithLatLngVo.setC(pojo___.getCrypto_code());

        Supplier supplier = userOv.getSupplier();




        EntityModel entityModel = multiTicketService.redeem(codeWithLatLngVo,userOv);



        if(entityModel != null){
            return entityModel;
        }












        if(codeWithLatLngVo.getC().startsWith("TT-NI")){
            Optional<Voucher> optionalVoucher = voucherRepository.findByCode(codeWithLatLngVo.getC());


            if(optionalVoucher.isEmpty()) {
                throw  new BookNotFoundException(Enumfailures.resource_not_found,"找不到消费券"+codeWithLatLngVo.getC());
            }

            Voucher voucher =optionalVoucher.get();
            if(voucher.getStatus().equals(EnumVoucherStatus.Redeemed)){
                throw new ExistException(Enumfailures.invalid_voucher,"该券已核销");
            }

            if(!voucher.isActive()){
                throw new BookNotFoundException(Enumfailures.voucher_not_active,"该券状态不活跃");
            }
            if(!voucher.getPublished()){
                throw new voucher_not_publishException(voucher.getId(),codeWithLatLngVo.getC()+"券换没有被认领","券换没有被认领");
            }



            Optional<Campaign> optionalCampaign = campaignRepository.findById(voucher.getCampaign());

            WrittenOffMerchantVo writtenOffMerchantVo = new WrittenOffMerchantVo(supplier,userOv);
            boolean v = redeemService.是否符合验证(optionalCampaign.get(),supplier);
            if(v){
                Triplet<RedemptionEntry,Redemption, PublishTowhoVo> pair= redeemService.RedeemVounchor(voucher,optionalCampaign.get(),writtenOffMerchantVo,codeWithLatLngVo);


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


            if(campaignAssignToTourBooking.getStatus().equals(EnumCampaignToTourBookingStatus.AlreadyRedeemed)){
                throw  new ExistException(Enumfailures.invalid_voucher,"团订单领票已核销");
            }
            Optional<Campaign> campaigns = campaignRepository.findById(campaignAssignToTourBooking.getCampaign());
            Optional<TourBooking> optionalTourBooking = tourBookingRepository.findById(campaignAssignToTourBooking.getTourBooking());
            TourBooking tourBooking = optionalTourBooking.get();
            Campaign campaign = campaigns.get();

            List<Traveler> travelerList = travelerRepository.findAllByBooking(campaignAssignToTourBooking.getTourBooking());


            WrittenOffMerchantVo writtenOffMerchantVo = new WrittenOffMerchantVo(supplier,userOv);


            List<Triplet<RedemptionEntry,Redemption,PublishTowhoVo>> pair= redeemService.RedeemVounchor(campaignAssignToTourBooking,tourBooking,campaign,writtenOffMerchantVo,codeWithLatLngVo);
            return RedemptionResp.from(pair);

        }



        String code_de = cryptoService.decorated_decode(codeWithLatLngVo.getC());

        System.out.println("加密前"+codeWithLatLngVo.getC()+"解密后"+code_de);

        Optional<VoucherTicket> optionalVoucher = voucherTicketRepository.findByCode(code_de);

        if(optionalVoucher.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到核销券"+code_de);

        }

        codeWithLatLngVo.setC(code_de);
        entityModel = cityPassService.redeem(optionalVoucher.get(),userOv);
        if(entityModel != null){
            return entityModel;
        }



        entityModel = busTicketService.redeem(optionalVoucher.get(),userOv);
        if(entityModel != null){
            return entityModel;
        }




        throw new BookNotFoundException(Enumfailures.not_found,"找不到核销券"+pojo___.getCrypto_code());
    }


    //TODO 这里有点问题
    @PostMapping(value = "/crypto_redemptions__b", produces = "application/json")
    public Object redeemVonchorByCryptoCode(@RequestBody RedemByCryptoCodePojo pojo___) {


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userOv = authenticationFacade.getUserVo(authentication);

System.out.println("---------------"+pojo___.getCrypto_code());



        String code_json = jwtUtils.getUserNameFromJwtToken(pojo___.getCrypto_code());
        Gson gson = new Gson();
        CodeWithLatLngVo codeWithLatLngVo =  gson.fromJson(code_json,CodeWithLatLngVo.class);

        Supplier supplier = userOv.getSupplier();





        EntityModel entityModel = multiTicketService.redeem(codeWithLatLngVo,userOv);
        if(entityModel != null){
            return entityModel;
        }




        if(codeWithLatLngVo.getC().startsWith("TT-NI")){
            Optional<Voucher> optionalVoucher = voucherRepository.findByCode(codeWithLatLngVo.getC());


            if(optionalVoucher.isEmpty()) {
                throw  new BookNotFoundException(Enumfailures.resource_not_found,"找不到消费券"+codeWithLatLngVo.getC());
            }

            Voucher voucher =optionalVoucher.get();
            if(voucher.getStatus().equals(EnumVoucherStatus.Redeemed)){
                throw new ExistException(Enumfailures.invalid_voucher,"该券已核销");
            }

            if(!voucher.isActive()){
                throw new BookNotFoundException(Enumfailures.voucher_not_active,"该券状态不活跃");
            }
            if(!voucher.getPublished()){
                throw new voucher_not_publishException(voucher.getId(),codeWithLatLngVo.getC()+"券换没有被认领","券换没有被认领");
            }



            Optional<Campaign> optionalCampaign = campaignRepository.findById(voucher.getCampaign());

            WrittenOffMerchantVo writtenOffMerchantVo = new WrittenOffMerchantVo(supplier,userOv);
            boolean v = redeemService.是否符合验证(optionalCampaign.get(),supplier);
            if(v){
                Triplet<RedemptionEntry,Redemption, PublishTowhoVo> pair= redeemService.RedeemVounchor(voucher,optionalCampaign.get(),writtenOffMerchantVo,codeWithLatLngVo);


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


            if(campaignAssignToTourBooking.getStatus().equals(EnumCampaignToTourBookingStatus.AlreadyRedeemed)){
                throw  new ExistException(Enumfailures.invalid_voucher,"团订单领票已核销");
            }
            Optional<Campaign> campaigns = campaignRepository.findById(campaignAssignToTourBooking.getCampaign());
            Optional<TourBooking> optionalTourBooking = tourBookingRepository.findById(campaignAssignToTourBooking.getTourBooking());
            TourBooking tourBooking = optionalTourBooking.get();
            Campaign campaign = campaigns.get();

            List<Traveler> travelerList = travelerRepository.findAllByBooking(campaignAssignToTourBooking.getTourBooking());


            WrittenOffMerchantVo writtenOffMerchantVo = new WrittenOffMerchantVo(supplier,userOv);


            List<Triplet<RedemptionEntry,Redemption,PublishTowhoVo>> pair= redeemService.RedeemVounchor(campaignAssignToTourBooking,tourBooking,campaign,writtenOffMerchantVo,codeWithLatLngVo);
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