package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.LocationResp;
import com.lt.dom.otcReq.VoucherResp;
import com.lt.dom.otcReq.VoucherTicketResp;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AssetServiceImpl;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.QrcodeServiceImpl;
import com.lt.dom.serviceOtc.product.AttractionTicketServiceImpl;
import com.lt.dom.serviceOtc.product.BusTicketServiceImpl;
import com.lt.dom.serviceOtc.product.CityPassServiceImpl;
import com.lt.dom.serviceOtc.product.ShowtimeTicketServiceImpl;
import com.lt.dom.util.ZxingBarcodeGenerator;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class VoucherTicketRestController {
    private static final Logger logger = LoggerFactory.getLogger(RedemptionRestController.class);
    @Autowired
    private VoucherTicketRepository voucherTicketRepository;

    @Autowired
    private SupplierRepository supplierRepository;



    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusTicketServiceImpl busTicketService;

    @Autowired
    private ShowtimeTicketServiceImpl showtimeTicketService;

    @Autowired
    private AttractionTicketServiceImpl attractionTicketService;
    @Autowired
    private CityPassServiceImpl cityPassService;


    @Autowired
    private AttractionRepository attractionRepository;


    @Autowired
    private AssetServiceImpl assetService;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private FileStorageServiceImpl fileStorageService;
    @Autowired
    private ComponentRightRepository componentRightRepository;

    @Autowired
    private ComponentVounchRepository componentVounchRepository;

    @Autowired
    private RightRedemptionEntryRepository rightRedemptionEntryRepository;



    @Autowired
    QrcodeServiceImpl qrcodeService;

    @Operation(summary = "1、获得")
    @GetMapping(value = "/voucher_tickets/{VOUCHER_ID}", produces = "application/json")
    public EntityModel<VoucherTicketResp> getVoucher(@PathVariable long VOUCHER_ID) {


        Optional<VoucherTicket> optionalVoucher = voucherTicketRepository.findById(VOUCHER_ID);
        //Optional<Voucher> optionalVoucher = voucherRepository.findOne(example);

        if (optionalVoucher.isEmpty()) {
            throw new BookNotFoundException(VOUCHER_ID,"找不到优惠券");
        }
        VoucherTicket voucherTicket = optionalVoucher.get();





            VoucherTicketResp voucherTicketResp = VoucherTicketResp.from(voucherTicket);

            List<ComponentVounch> components = componentVounchRepository.findAllByReference(voucherTicket.getCode());
            voucherTicketResp.setComponents(components.stream().map(e->{

                ComponentVounchResp componentResp= ComponentVounchResp.from(e);

                if(e.getType().equals(EnumComponentVoucherType.Right)){
                    ComponentRight componentRight = componentRightRepository.findById(e.getComponentRight()).get();
                    componentResp.setComponentRightCode(componentRight.getCode());
                }else{

                }

                return componentResp;
            }).collect(Collectors.toList()));


        List<RightRedemptionEntry> redemptionEntryList =
                rightRedemptionEntryRepository.findAllByRelatedObjectTypeAndRelatedObjectId(EnumRelatedObjectType.voucher,voucherTicket.getId());


        VoucherTicketResp.Redemption redemption = new VoucherTicketResp.Redemption();


        redemption.setRedemption_entries(redemptionEntryList.stream().map(e->{

            RightRedemptionEntryResp redemptionEntryResp = RightRedemptionEntryResp.from(e);

            return redemptionEntryResp;
        }).collect(Collectors.toList()));

        voucherTicketResp.setRedemption(redemption);

        VoucherTicketResp.Booking booking = new VoucherTicketResp.Booking();
        booking.setCode(voucherTicket.getCode());
        booking.setPaied_at(LocalDateTime.now());
        booking.setCreated_at(LocalDateTime.now());
        EntityModel entityModel = EntityModel.of(booking);
        entityModel.add(linkTo(methodOn(BookingRestController.class).getReservation(voucherTicket.getBooking())).withSelfRel());

        voucherTicketResp.withBookingInfo(entityModel);

        showtimeTicketService.ticketShow(voucherTicketResp,voucherTicket);
/*        @Autowired
        private ShowtimeTicketServiceImpl showtimeTicketService;

        @Autowired
        private AttractionTicketServiceImpl attractionTicketService;
        @Autowired
        private CityPassServiceImpl cityPassService;*/


        busTicketService.ticketShow(voucherTicketResp,voucherTicket);

        attractionTicketService.ticketShow(voucherTicketResp,voucherTicket);





            Asset asset = assetService.getWithNew(voucherTicket.getCode(),voucherTicket.getId());



       // voucherTicketResp.setCode_base64_src(ZxingBarcodeGenerator.base64_png_src(voucherTicket.getCode()));

        VoucherTicketResp.DeliveryOption deliveryOption1 = new VoucherTicketResp.DeliveryOption();
        deliveryOption1.setDeliveryFormat(EnumDeliveryFormat.QRCODE);
        deliveryOption1.setDeliveryValue(asset.getIdId());
        deliveryOption1.setCode_base64(ZxingBarcodeGenerator.base64_png_src(asset.getIdId()));
        voucherTicketResp.setDeliveryOption(deliveryOption1);


        voucherTicketResp.setCover(fileStorageService.loadDocumentWithCode(EnumDocumentType.artwork_audio,voucherTicket.getCode()));

     //   EntityModel<AssetResp>  assetResp = AssetResp.from(asset);
       // voucherTicketResp.setAsset(assetResp);
            return EntityModel.of(voucherTicketResp);
    }









    @Operation(summary = "1、获得")
    @GetMapping(value = "/voucher_tickets/{VOUCHER_ID}/qr_refresh", produces = "application/json")
    public ResponseEntity<String> refresh(@PathVariable long VOUCHER_ID) {

        logger.debug("参数：",VOUCHER_ID);

        Optional<Voucher> optionalVoucher = voucherRepository.findById(VOUCHER_ID);

        if (optionalVoucher.isEmpty()) {
            throw new BookNotFoundException(VOUCHER_ID,"找不到优惠券");
        }
        Voucher voucher = optionalVoucher.get();
        String crypto = qrcodeService._encode(voucher.getCode());

        String 解密后的数据 = qrcodeService._decode(crypto);
        logger.debug("参数{} {} 加密{} 解密后的数据{}",VOUCHER_ID,voucher.getCode(),crypto,解密后的数据);
        return ResponseEntity.ok(crypto);
    }






    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/users/{USER_ID}/voucher_tickets", produces = "application/json")
    public PagedModel listVoucher(@PathVariable long USER_ID,
                                  @PageableDefault(sort = {"createdDate"}, direction = Sort.Direction.DESC) final Pageable pageable ,
                                  PagedResourcesAssembler<EntityModel<VoucherTicketResp>> assembler) {


        Optional<User> optionalUser = userRepository.findById(USER_ID);

        if(optionalUser.isEmpty()){
            throw new BookNotFoundException(USER_ID,User.class.getSimpleName());

        }


        Page<VoucherTicket> voucherPage = voucherTicketRepository.findAll(pageable);


        return assembler.toModel(voucherPage.map(e->{
            VoucherTicketResp Voucher = VoucherTicketResp.from(e);
            Voucher.setBooking_created_at(LocalDateTime.now());
            Voucher.setBooking_paied_at(LocalDateTime.now());


            showtimeTicketService.listShow(Voucher,e);
            attractionTicketService.listShow(Voucher,e);
            busTicketService.listShow(Voucher,e);

            if(e.getType().equals(EnumVoucherType.VOUCHER)){
                Voucher.setText("这是取票凭证");

            }

            EntityModel entityModel = EntityModel.of(Voucher);
            entityModel.add(linkTo(methodOn(VoucherTicketRestController.class).getVoucher(e.getId())).withSelfRel());

            return entityModel;
        }));


    }







    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/voucher_tickets/Page_listSupplierVoucher", produces = "application/json")
    public EntityModel<Media> Page_listSupplierVoucher(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

        Map map = Map.of("status_list", EnumVoucherStatus.values());


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(VoucherTicketRestController.class).listSupplierVoucher(supplier.getId(),null,null)).withRel("list"));


        return entityModel;

    }



    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/voucher_tickets", produces = "application/json")
    public PagedModel listSupplierVoucher(@PathVariable long SUPPLIER_ID,
                                  @PageableDefault(sort = {"createdDate"}, direction = Sort.Direction.DESC) final Pageable pageable ,
                                  PagedResourcesAssembler<EntityModel<VoucherTicketResp>> assembler) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);
        if(optionalSupplier.isEmpty()) {

            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到供应商");

        }
        Supplier supplier = optionalSupplier.get();



        Page<VoucherTicket> voucherPage = voucherTicketRepository.findAll(pageable);


        return assembler.toModel(voucherPage.map(e->{
            VoucherTicketResp voucher = VoucherTicketResp.pcfrom(e);
            voucher.setBooking_created_at(LocalDateTime.now());
            voucher.setBooking_paied_at(LocalDateTime.now());


            showtimeTicketService.listShow(voucher,e);
            attractionTicketService.listShow(voucher,e);
            busTicketService.listShow(voucher,e);

            if(e.getType().equals(EnumVoucherType.VOUCHER)){
                voucher.setText("这是取票凭证");

            }

            EntityModel entityModel = EntityModel.of(voucher);
            entityModel.add(linkTo(methodOn(VoucherTicketRestController.class).getVoucher(e.getId())).withSelfRel());

            return entityModel;
        }));


    }


/*        Optional<Product> optionalProduct = productRepository.findById(pojo.getProductId());

        if(optionalProduct.isPresent()){
            return ResponseEntity.ok(booking);
        }

        System.out.println("抛出异常");

    }


    @GetMapping(value = "/{VOUCHER_ID}/componet_right_vounchs", produces = "application/json")
    public List<ComponentVounch> listComponentRightVounch(@PathVariable long VOUCHER_ID) {

        Optional<Voucher> validatorOptional = voucherRepository.findById(VOUCHER_ID);
        if(validatorOptional.isPresent()){
            try {
                return vonchorService.get票的权益(validatorOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));

   // public ResponseEntity<VoucherResp> getVoucher(@PathVariable String CODE) {
/*        System.out.println("getVouchergetVouchergetVouchergetVoucher"+CODE);
        System.out.println("getVouchergetVouchergetVouchergetVoucher"+CODE);
        System.out.println("getVouchergetVouchergetVouchergetVoucher"+CODE);
        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnorePaths("campaign")
                .withIgnorePaths("relateId")
                .withIgnorePaths("relateId")
                .withIgnorePaths("start_date")
                .withIgnorePaths("active")
                .withIgnorePaths("published")
                .withIgnorePaths("redeemed_quantity")
                .withIgnorePaths("expiration_date");*/


    //   .withMatcher("model", ignoreCase());

/*        Voucher probe = new Voucher();
        probe.setCode(CODE);
        Example<Voucher> example = Example.of(probe,modelMatcher);*/

    }
/*
    @PostMapping(value = "/coupon_templates", produces = "application/json")
    public ComponentRight createCouponTemplate(@RequestBody CouponTemplatePojo  pojo) {
        ComponentRight componentRight = new ComponentRight();

        componentRight.setName(pojo.getName());
        componentRight.setNote(idGenService.nextId("Coupon_"));
        return componentRight;
    }






    @PostMapping(value = "/componet_rights/{COMPONENT_RIGHTS_ID}/access_validators", produces = "application/json")
    public AccessValidator addComponentRight_Validator(@PathVariable int COMPONENT_RIGHTS_ID, @PathVariable int COUPON_TMPL_ID) {
        AccessValidator componentRight = new AccessValidator();
        return componentRight;
    }
*/

/*

    @GetMapping(value = " /{VALIDATOR_ID}/component_rights", produces = "application/json")
    public List<ComponentRight> listComponentRight(@PathVariable long VALIDATOR_ID) {

        Optional<Validator> validatorOptional = validatorRepository.findById(VALIDATOR_ID);
        if(validatorOptional.isPresent()){
            try {
                return validatorScanService.找出当前验证者管理的权益(validatorOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
    }


    @GetMapping(value = " /{VALIDATOR_ID}/component_right_vonchors", produces = "application/json")
    public List<ComponentRightVounch> listComponentRightVounch(@PathVariable long VALIDATOR_ID) {

        Optional<Validator> validatorOptional = validatorRepository.findById(VALIDATOR_ID);
        if(validatorOptional.isPresent()){
            try {
                return validatorScanService.当前核验者的权益券(validatorOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
    }


*/


/*
    @PutMapping(value = "/{APP_ID}/component_right/{COMPONENT_RIGHT_ID}", produces = "application/json")
    public ComponentRight updateComponentRight(@PathVariable int APP_ID, Map  metadata) {
        return null;
    }

    @DeleteMapping(value = "/coupon_templates", produces = "application/json")
    public ComponentRight createCouponTemplate(@PathVariable String id) {
        return null;
    }
*/


