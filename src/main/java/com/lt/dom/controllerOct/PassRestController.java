package com.lt.dom.controllerOct;

import cn.hutool.core.util.DesensitizedUtil;
import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.serviceOtc.product.CityPassServiceImpl;
import com.lt.dom.specification.PassQueryfieldsCriteria;
import com.lt.dom.specification.PassSpecification;
import com.lt.dom.specification.ProductSpecification;
import com.lt.dom.thiirdAli.idfaceIdentity.IdfaceIdentityOcrService;
import com.lt.dom.thiirdAli.idfaceIdentity.IdfaceIdentityVo;
import com.lt.dom.util.ZxingBarcodeGenerator;
import com.lt.dom.vo.UserVo;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.core.EmbeddedWrapper;
import org.springframework.hateoas.server.core.EmbeddedWrappers;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class PassRestController {

    Logger logger = LoggerFactory.getLogger(CityPassServiceImpl.class);

    @Autowired
    private PassServiceImpl passService;


    @Autowired
    private AssetServiceImpl assetService;

    @Autowired
    private CardholderRepository cardholderRepository;



    @Autowired
    private ComponentVounchRepository componentVounchRepository;
    @Autowired
    private FileStorageServiceImpl fileStorageService;

    @Autowired
    private MediaRepository mediaRepository;



    @Autowired
    private LineItemRepository lineItemRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;
    @Autowired
    private PassRepository passRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IntelliCodeRepository intelliCodeRepository;


    @Autowired
    private ComponentRightRepository componentRightRepository;
    @Autowired
    private IdfaceIdentityOcrService idfaceIdentityOcrService;
    @Autowired
    private ComponentRightServiceImpl componentRightService;
    @Autowired
    private ValidatorServiceImpl validatorService;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private SupplierRepository   supplierRepository ;
    @Autowired
    private BulkIssuanceCardRequestRepository   bulkIssuanceCardRequestRepository ;


    @Autowired
    private ProductRepository   productRepository ;

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/passes") //produces = "application/json"
    public PagedModel getPassList(PassQueryfieldsCriteria searchQuery ,@PathVariable long SUPPLIER_ID,
                                  @PageableDefault(sort = {"createdDate",
                                          "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable ,

                                  PagedResourcesAssembler<EntityModel<Pass>> assembler) {


        logger.debug("请求 参数是：{}",searchQuery);
        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("找不到供应商","找不到供应商");
        }
        Supplier supplier = supplierOptional.get();




     //   searchQuery.setHoder_name("赵源");
    //    searchQuery.setProduct(1l);
     //   searchQuery.setFormFactorType(EnumFormFactorType.physical);
   //     searchQuery.setKeyword("");
     //   searchQuery.setStatuses(Arrays.asList(EnumCardStatus.active));

        PassSpecification spec =new PassSpecification(Arrays.asList("cardholder","code","name"),searchQuery); //, "code", "claim_note"

        Page<Pass> validatorOptional = passRepository.findAll(where(spec),pageable);


        return assembler.toModel(validatorOptional.map(e->{
            PassResp passResp = PassResp.from(e);

            passResp.setThumb(fileStorageService.loadDocumentWithDefault(EnumDocumentType.card_cover,e.getProductPassUuid()));
            EntityModel entityModel = EntityModel.of(passResp);
            entityModel.add(linkTo(methodOn(PassRestController.class).getPassEdit(e.getId())).withSelfRel());


            return entityModel;
        }));
    }




    @GetMapping(value = "/users/{USER_ID}/passes", produces = "application/json")
    public PagedModel getPassListForUser(@PathVariable long USER_ID,
                                         @PageableDefault(sort = {"createdDate",
                                                 "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable ,
                                         PagedResourcesAssembler<EntityModel<Pass>> assembler) {

        Page<Pass> validatorOptional = passRepository.findAllByOwner(USER_ID,pageable);

        return assembler.toModel(validatorOptional.map(e->{

            PassResp passResp = PassResp.Simplfrom(e);


            passResp.setThumb(fileStorageService.loadDocumentWithDefault(EnumDocumentType.card_cover,e.getProductPassUuid()));
            EntityModel entityModel = EntityModel.of(passResp);

            entityModel.add(linkTo(methodOn(PassRestController.class).getPass(e.getId())).withSelfRel());
            return entityModel;
        }));
    }




    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/bulkissuances/Page_createBulkIssuance", produces = "application/json")
    public EntityModel<Media> Page_createBulkIssuance(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("status_list", EnumBulkIssuanceCardRequestStatus.values());

      //  Map map = Map.of("status_list", EnumBulkIssuanceCardRequestStatus.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(PassRestController.class).createbul_issuaknce_card_requests(supplier.getId(),null)).withRel("create"));
        entityModel.add(linkTo(methodOn(PassRestController.class).getBulkissuaknceList(supplier.getId(),null,null)).withRel("list"));
        entityModel.add(linkTo(methodOn(PassRestController.class).Page_createValueList_getItems(supplier.getId())).withRel("getProductList"));


        return entityModel;

    }

    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/bulkissuaknces", produces = "application/json")
    public EntityModel createbul_issuaknce_card_requests(@PathVariable long SUPPLIER_ID, @RequestBody BulkIssuanceCardRequestReq pojo) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("找不到供应商","找不到供应商");
        }
        Supplier supplier = supplierOptional.get();

        Optional<Product> productOptional = productRepository.findById(pojo.getCard_product_id());

        if(productOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品 ");
        }


        Product product = productOptional.get();
        BulkIssuanceCardRequest pass = passService.createBulkIssuaknceCardRequests(supplier,product,pojo);

        return EntityModel.of(pass);
    }





    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/passes/Page_createPass", produces = "application/json")
    public EntityModel<Media> Page_createPass(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        List<Product> productList = productRepository.findAllByType(EnumProductType.Pass);

        Map map = Map.of("type_list", EnumCardType.from(),
                "product_list",Product.List(productList),
                "formFactorType_list",EnumFormFactorType.from()

        );

        //  Map map = Map.of("status_list", EnumBulkIssuanceCardRequestStatus.values());


        EntityModel entityModel = EntityModel.of(map);
        entityModel.add(linkTo(methodOn(PassRestController.class).createPass(supplier.getId(),null)).withRel("create"));
        entityModel.add(linkTo(methodOn(PassRestController.class).getPassList(null,supplier.getId(),null,null)).withRel("list"));
        entityModel.add(linkTo(methodOn(PassRestController.class).Page_createValueList_getItems(supplier.getId())).withRel("getProductList"));

        return entityModel;

    }

    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/passes", produces = "application/json")
    public EntityModel createPass(@PathVariable long SUPPLIER_ID, @RequestBody @Valid PassCreatePojo pojo) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("找不到供应商","找不到供应商");
        }
        Supplier supplier = supplierOptional.get();

        Optional<Product> productOptional = productRepository.findById(pojo.getProduct());

        if(productOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品 ");
        }


        Product product = productOptional.get();



        Optional<User> optionalUser = userRepository.findById(pojo.getUser());
        if(optionalUser.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户 ");

        }
        User user = optionalUser.get();

        Pass pass = passService.create(supplier,product,user,pojo);


        return EntityModel.of(pass);
    }
    @PostMapping(value = "/passes/{PASS_ID}/change", produces = "application/json")
    public EntityModel changeStatus(@PathVariable long PASS_ID,  @RequestBody PassStatusChangePojo pojo) {
        Optional<Pass> passOptional = passRepository.findById(PASS_ID);

        if(passOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
        }

        Pass pass = passOptional.get();




        pass = passService.changeStatus(pass,pojo);


        return EntityModel.of(pass);
    }


    @GetMapping(value = "/users/{USER_ID}/passes/Page_link", produces = "application/json")
    public EntityModel<Media> Page_linkPass(@PathVariable Long USER_ID) {

        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVoWithUser(authentication);


        Optional<User> validatorOptional = userRepository.findById(userVo.getUser_id());

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
        }
        User user = validatorOptional.get();

        Map map = Map.of("name", DesensitizedUtil.chineseName(user.getRealName()),
                "id_card",
                DesensitizedUtil.idCardNum(user.getIdCard(), 1, 2));
        EntityModel entityModel = EntityModel.of(map);
        entityModel.add(linkTo(methodOn(DocumentRestController.class).uploadDocument(null)).withRel("uploadFile"));

        entityModel.add(linkTo(methodOn(PassRestController.class).linkPass(user.getId(),null)).withRel("link"));
        return entityModel;
    }
    @GetMapping(value = "/passes/Page_link", produces = "application/json")
    public EntityModel<Media> Page_linkPass() {

        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVoWithUserRealname(authentication);


        Optional<User> validatorOptional = userRepository.findById(userVo.getUser_id());

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
        }
        User user = validatorOptional.get();

        Map map = Map.of("name", DesensitizedUtil.chineseName(user.getRealName()),
                "id_card",
                DesensitizedUtil.idCardNum(user.getIdCard(), 1, 2));
        EntityModel entityModel = EntityModel.of(map);
        entityModel.add(linkTo(methodOn(DocumentRestController.class).uploadDocument(null)).withRel("uploadFile"));

        entityModel.add(linkTo(methodOn(PassRestController.class).linkPass(user.getId(),null)).withRel("link"));
        return entityModel;
    }


    @PostMapping(value = "/passes/{PASS_ID}/link", produces = "application/json")
    public EntityModel linkUser(@PathVariable long PASS_ID,  @RequestBody PassLinkPojo pojo) {
        Optional<Pass> passOptional = passRepository.findById(PASS_ID);

        if(passOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
        }

        Pass pass = passOptional.get();



        Optional<User> validatorOptional = userRepository.findById(pojo.getUser());

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
        }
        User user = validatorOptional.get();





        pass = passService.link(pass,user);


        return EntityModel.of(pass);
    }


    @PostMapping(value = "/users/{USER_ID}/passes/link________", produces = "application/json")
    public EntityModel linkPass_______(@PathVariable long USER_ID,  @RequestBody PassCreatePojo pojo) {

        Optional<User> validatorOptional = userRepository.findById(USER_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
        }
        User user = validatorOptional.get();




        Optional<Product> productOptional = productRepository.findById(pojo.getProduct());

        if(productOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品 ");
        }


        Product product = productOptional.get();


        List<Component> componentRightList = componentRepository.findAllByProduct(product.getId());

        List<ComponentVounch> componentVounchList = componentRightService.createComponentVounch(null,componentRightList,user);


        Map<Long,User> longUserMap = userRepository.findAllById(componentVounchList.stream().map(e->e.getUser()).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(e->e.getId(),e->e));

        validatorService.push( componentVounchList.stream().map(e->{
            User user_ = longUserMap.get(e.getUser());

            return Pair.with(e,user_);
        }).collect(Collectors.toList()));

        Pass pass = passService.create_virtual(user,12);
        componentRightService.groupby(componentVounchList,pass);
        componentRightService.bulkUpdata(componentVounchList);


        return EntityModel.of(pass);
    }



    @PostMapping(value = "/users/{USER_ID}/passes/link", produces = "application/json")
    public EntityModel linkPass(@PathVariable Long USER_ID,  @RequestBody PassLinkPojo pojo) {

        Authentication authentication = authenticationFacade.getAuthentication();
        UserVo userVo = authenticationFacade.getUserVoWithUser(authentication);

        Optional<User> validatorOptional = userRepository.findById(USER_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
        }
        User user = validatorOptional.get();

        Optional<Pass> productOptional = passRepository.findByNumber(pojo.getPassNumber());



        if(productOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品 ");
        }


        Pass pass = productOptional.get();

        if(pass.getStatus().equals(EnumCardStatus.active)){

            if(!pass.getCardholder().getName().equals(user.getRealName())
                    || !pass.getCardholder().getIdentity().equals(user.getIdCard())){

                throw new BookNotFoundException(Enumfailures.not_found,"该卡已激活，您非该卡持卡人");

            }
            passService.link(pass,user);
        }else{

            PassActivePojo pojo1 = new PassActivePojo();
            pojo1.setName(user.getRealName());
            pojo1.setId_card(user.getIdCard());

            passService.active(pass,pojo1);
            passService.link(pass,user);
        }





        return EntityModel.of(pass);
    }




















    @GetMapping(value = "/passes/Page_active", produces = "application/json")
    public EntityModel<Media> Page_activePass() {

       // "type_list", EnumCardType.from(),
        Map map = Map.of("verification_check_type",EnumVerificationCheckType.id_number);
        EntityModel entityModel = EntityModel.of(map);
        entityModel.add(linkTo(methodOn(DocumentRestController.class).uploadDocument(null)).withRel("uploadFile"));

       // entityModel.add(linkTo(methodOn(PassRestController.class).active_by_order_number(null)).withRel("active"));

        entityModel.add(linkTo(methodOn(PassRestController.class).activePass_(null)).withRel("active"));

        return entityModel;
    }













    @GetMapping(value = "/passes/{PASS_ID}/Page_createPass", produces = "application/json")
    public EntityModel<Media> Page_activePass(@PathVariable long PASS_ID ) {

        Optional<Pass> passOptional = passRepository.findById(PASS_ID);

        if(passOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
        }
        Pass pass = passOptional.get();

        Map map = Map.of("type_list", EnumCardType.from());
        EntityModel entityModel = EntityModel.of(map);
        entityModel.add(linkTo(methodOn(PassRestController.class).activePass(pass.getId(),null,null)).withRel("create"));
        return entityModel;
    }









    @PostMapping(value = "/passes/{PASS_ID}//block", produces = "application/json")
    public EntityModel block(@PathVariable long PASS_ID , @RequestBody PassBlockPojo pojo) {

        Optional<Pass> passOptional = passRepository.findById(PASS_ID);

        if(passOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
        }
        Pass pass = passOptional.get();

        pass = passService.block(pass,pojo);


        return EntityModel.of(pass);
    }



    @PostMapping(value = "/passes/active_by_order_number", produces = "application/json")
    public EntityModel active_by_order_number( @RequestBody PassActivePojo pojo) {

        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);

        User user = userRepository.findById(userVo.getUser_id()).get();

        Optional<IntelliCode> bookingProductFuck = intelliCodeRepository.findByPin(pojo.getCvc());


        if(bookingProductFuck.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
        }
        IntelliCode intelliCode = bookingProductFuck.get();

        if(!intelliCode.isActive() || !intelliCode.getStatus().equals(EnumIntelliCodeStatus.not_redeemed)){
            throw new BookNotFoundException(Enumfailures.not_found,"激活码失效，或已核销");
        }
        LineItem lineItem = lineItemRepository.findById(intelliCode.getLineItem()).get();




        Cardholder cardholder = new Cardholder();
        //cardholder.setCompany(pass.getId());
        cardholder.setName(pojo.getName());
        cardholder.setIdentity(pojo.getId_card());



        cardholder = cardholderRepository.save(cardholder);
        if(pojo.getFront() != null){
            fileStorageService.updateFromTempDocumentCode(cardholder.getUuid(), EnumDocumentType.document_front_file, pojo.getFront().getCode());
        }
        if(pojo.getBack() != null){
            fileStorageService.updateFromTempDocumentCode(cardholder.getUuid(), EnumDocumentType.document_back_file, pojo.getBack().getCode());
        }

        Pass pass = passService.create_virtual(lineItem,cardholder,10);


        intelliCode.setStatus(EnumIntelliCodeStatus.redeemed);
        intelliCode.setActive(false);
        intelliCodeRepository.save(intelliCode);



        PassResp passResp =PassResp.from(pass);

        return EntityModel.of(passResp);
    }





    @PostMapping(value = "/passes/active", produces = "application/json")
    public EntityModel activePass_( @RequestBody PassActivePojo pojo) {

    //    List<ComponentRight> componentRightList = componentRightRepository.findAllById(pojo.getRights());


     //   IdfaceIdentityVo idfaceIdentityVo = idfaceIdentityOcrService.main(pojo.getName(), pojo.getId_card(),file);

      //  Optional<Pass> optionalOptional = passRepository.findById(Long.valueOf(pojo.getCode()).longValue());

        Optional<Pass> optionalOptional = passRepository.findByNumber(pojo.getCode());

        if(optionalOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到卡，请确认卡号输入正确");
        }
        Pass pass = optionalOptional.get();

        if(!pass.getStatus().equals(EnumCardStatus.inactive)){
            throw new BookNotFoundException(Enumfailures.not_found,"该卡 非未激活卡");

        }


/*
        if(!pass.getPin().equals(pojo.getCvc())){
            throw new BookNotFoundException(Enumfailures.not_found,"验证错误");
        }*/
        pass = passService.active(pass,pojo);

        PassResp passResp =PassResp.from(pass);

        return EntityModel.of(passResp);
    }





    @PostMapping(value = "/passes/{USER_ID}/active", produces = "application/json")
    public EntityModel activePass(@PathVariable long PASS_ID,  @RequestBody PassActivePojo pojo, MultipartFile file) {

        Optional<Pass> passOptional = passRepository.findById(PASS_ID);

        if(passOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
        }
        Pass pass = passOptional.get();


        List<ComponentRight> componentRightList = componentRightRepository.findAllById(pojo.getRights());


        IdfaceIdentityVo idfaceIdentityVo = idfaceIdentityOcrService.main(pojo.getName(), pojo.getId_card(),file);

    //    Optional<User> validatorOptional = userRepository.findById(USER_ID);


        pass = passService.active(pass,componentRightList);


        return EntityModel.of(pass);
    }

    @PostMapping(value = "/users/{USER_ID}/passes/active", produces = "application/json")
    public EntityModel activePass___(@PathVariable long USER_ID,  @RequestBody PassActivePojo pojo, MultipartFile file) {




        List<ComponentRight> componentRightList = componentRightRepository.findAllById(pojo.getRights());


        IdfaceIdentityVo idfaceIdentityVo = idfaceIdentityOcrService.main(pojo.getName(), pojo.getId_card(),file);

        Optional<User> validatorOptional = userRepository.findById(USER_ID);

      //  PassCreatePojo passCreatePojo = pojo;

        Pass pass = passService.create(validatorOptional.get(),componentRightList);


        return EntityModel.of(pass);
    }








    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/bulkissuances", produces = "application/json")
    public PagedModel getBulkissuaknceList(@PathVariable long SUPPLIER_ID,
                                           @PageableDefault(sort = {"createdDate",
                                                   "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable ,


                                           PagedResourcesAssembler<EntityModel<Pass>> assembler) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("找不到供应商","找不到供应商");
        }
        Supplier supplier = supplierOptional.get();

        Page<BulkIssuanceCardRequest> validatorOptional = bulkIssuanceCardRequestRepository.findAllBySupplier(supplier.getId(),pageable);

        return assembler.toModel(validatorOptional.map(e->{
            BulkIssuanceCardRequestResp resp = BulkIssuanceCardRequestResp.from(e);

            EntityModel entityModel = EntityModel.of(resp);

            entityModel.add(linkTo(methodOn(PassRestController.class).getBulkissuaknce(e.getId())).withSelfRel());

            return entityModel;
        }));
    }

    @GetMapping(value = "/bulkissuances/{BULK_ISSUAKNE_ID}", produces = "application/json")
    public EntityModel getBulkissuaknce(@PathVariable long BULK_ISSUAKNE_ID) {

        Optional<BulkIssuanceCardRequest> supplierOptional = bulkIssuanceCardRequestRepository.findById(BULK_ISSUAKNE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("找不到供应商","找不到供应商");
        }
        BulkIssuanceCardRequest supplier = supplierOptional.get();
        BulkIssuanceCardRequestResp resp = BulkIssuanceCardRequestResp.from(supplier);


        Map map = Map.of("status_list",
                EnumBulkIssuanceCardRequestStatus.from(),
                "pass_status_list", EnumCardStatus.from());

        resp.setParameters(map);


        EntityModel entityModel = EntityModel.of(resp);

        entityModel.add(linkTo(methodOn(PassRestController.class).getPassListForIssuance(supplier.getId(),null,null)).withRel("getPassList"));
        entityModel.add(linkTo(methodOn(MovieRestController.class).editMovie(supplier.getId(),null)).withRel("edit"));

        return entityModel;
    }




    @GetMapping(value = "/bulkissuaknces/{BULK_ISSUAKNE_ID}/passes", produces = "application/json")
    public PagedModel getPassListForIssuance(@PathVariable long BULK_ISSUAKNE_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<Pass>> assembler) {
        Optional<BulkIssuanceCardRequest> supplierOptional = bulkIssuanceCardRequestRepository.findById(BULK_ISSUAKNE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("找不到供应商","找不到供应商");
        }
        BulkIssuanceCardRequest supplier = supplierOptional.get();

        Page<Pass> validatorOptional = passRepository.findAllByBulkIssuanceId(supplier.getId(),pageable);

        return assembler.toModel(validatorOptional.map(e->{

            PassResp passResp = PassResp.from(e);

            EntityModel entityModel = EntityModel.of(passResp);

            entityModel.add(linkTo(methodOn(PassRestController.class).getPassListForIssuance(e.getBulkIssuanceId(),null,null)).withRel("getBulkIssuance"));
            entityModel.add(linkTo(methodOn(PassRestController.class).getBulkissuaknce(e.getBulkIssuanceId())).withSelfRel());
            entityModel.add(linkTo(methodOn(PassRestController.class).linkUser(e.getId(),null)).withRel("linkUser"));


            return entityModel;
        }));
    }





    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/bulkissuances/get_product_list",produces = "application/json")
    public CollectionModel Page_createValueList_getItems(@PathVariable Long SUPPLIER_ID) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

        CollectionModel entityModel = null;

            List<Product> productList = productRepository.findAllByType(EnumProductType.Pass);

            if(productList.isEmpty()){
                EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
                EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(EnumLongIdResp.class);
                //Resources<Object> resources = new Resources<>(Arrays.asList(wrapper));

                entityModel  = CollectionModel.of(Arrays.asList(wrapper));
            }else{
                entityModel = CollectionModel.of(Product.List(productList));

            }
        entityModel.add(linkTo(methodOn(PassRestController.class).Page_createValueList_getItems(SUPPLIER_ID)).withSelfRel());
        return entityModel;
    }




    @GetMapping(value = "/passes/{PASS_ID}", produces = "application/json")
    public EntityModel getPass(@PathVariable long PASS_ID) {
        Optional<Pass> passOptional = passRepository.findById(PASS_ID);

        if(passOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
        }
        Pass pass = passOptional.get();


        List<ComponentVounch> componentVounchList = componentVounchRepository.findByBelongTypeAndBelong(EnumBelongType.Pass,pass.getId());

        Map<Long,ComponentRight> componentRightMap = componentRightRepository
                .findAllById(componentVounchList.stream().map(e->e.getComponentRight()).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(e->e.getId(),e->e));

        PassResp passResp = null;

        if(pass.getStatus().equals(EnumCardStatus.active)){
            passResp = PassResp.fromActive(pass);
            Asset asset = assetService.getWithNew(pass.getCode(),pass.getId());
            passResp.setCode_base64_src(ZxingBarcodeGenerator.base64_png_src(pass.getCode()));
            EntityModel<AssetResp>  assetResp = AssetResp.from(asset);
            passResp.setAsset(assetResp);
        }else{
            passResp = PassResp.fromInactive(pass);


                passResp.setCardholder(CardholderResp.from(pass.getCardholder()));

        }


        Optional<Supplier> optionalSupplier = supplierRepository.findById(pass.getSupplier());

        Supplier supplier = optionalSupplier.get();


        passResp.setAdditionalInfo(Map.of("createdDate",pass.getCreatedDate(),"issuer",supplier.getName()));



        passResp.setComponentVounch(componentVounchList.stream().map(E->{
            ComponentRight componentRight = componentRightMap.get(E.getComponentRight());
            ComponentVounchResp componentVounchResp = ComponentVounchResp.Simplefrom(E,componentRight);
            return componentVounchResp;
        }).collect(Collectors.toList()));






        passResp.setCover(fileStorageService.loadDocumentWithDefault(EnumDocumentType.card_cover,pass.getProductPassUuid()));
        EntityModel entityModel = EntityModel.of(passResp);
        entityModel.add(linkTo(methodOn(PassRestController.class).sendToFriend(pass.getId(),null)).withRel("sendToFriend"));

        return entityModel;
    }





    @GetMapping(value = "/passes/{PASS_ID}/edit", produces = "application/json")
    public EntityModel getPassEdit(@PathVariable long PASS_ID) {
        Optional<Pass> passOptional = passRepository.findById(PASS_ID);

        if(passOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
        }
        Pass pass = passOptional.get();


        List<ComponentVounch> componentVounchList = componentVounchRepository.findByBelongTypeAndBelong(EnumBelongType.Pass,pass.getId());

        Map<Long,ComponentRight> componentRightMap = componentRightRepository
                .findAllById(componentVounchList.stream().map(e->e.getComponentRight()).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(e->e.getId(),e->e));

        PassResp passResp =PassResp.from(pass);

        if(pass.getStatus().equals(EnumCardStatus.active)){
            Asset asset = assetService.getWithNew(pass.getCode(),pass.getId());
            passResp.setCode_base64_src(ZxingBarcodeGenerator.base64_png_src(pass.getCode()));
            EntityModel<AssetResp>  assetResp = AssetResp.from(asset);
            passResp.setAsset(assetResp);
        }else{

        }

        CardholderResp cardholderResp =  CardholderResp.from(pass.getCardholder());
        cardholderResp.setFront(fileStorageService.loadDocumentWithDefault( EnumDocumentType.document_front_file,pass.getCardholder().getUuid()));
        cardholderResp.setBack(fileStorageService.loadDocumentWithDefault(EnumDocumentType.document_back_file,pass.getCardholder().getUuid()));

        passResp.setCardholder(cardholderResp);



        passResp.setComponentVounch(componentVounchList.stream().map(E->{
            ComponentRight componentRight = componentRightMap.get(E.getComponentRight());
            ComponentVounchResp componentVounchResp = ComponentVounchResp.Simplefrom(E,componentRight);
            return componentVounchResp;
        }).collect(Collectors.toList()));

        passResp.setCover(fileStorageService.loadDocumentWithDefault(EnumDocumentType.card_cover,pass.getProductPassUuid()));


        return EntityModel.of(passResp);
    }



    @PostMapping(value = "/passes/{PASS_ID}", produces = "application/json")
    public EntityModel sendToFriend(@PathVariable long PASS_ID,  @RequestBody PassActivePojo pojo) {

        Optional<Pass> passOptional = passRepository.findById(PASS_ID);

        if(passOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
        }
        Pass pass = passOptional.get();

        pass = passService.sendToFriend(pojo);


        return EntityModel.of(pass);
    }




}