package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.*;
import com.lt.dom.domain.SettleAccount;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.vo.SupplierPojoVo;
import com.lt.dom.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class SupplierRestController {
    @Autowired
    private QuotaRepository quotaRepository;
    @Autowired
    private SettleAccountServiceImpl settleAccountService;
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private PublicationEntryRepository publicationEntryRepository;

    @Autowired
    private ScenarioAssignmentRepository scenarioAssignmentRepository;


    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierServiceImp supplierService;

    @Autowired
    private FileStorageServiceImpl fileStorageService;

    @Autowired
    private AssetServiceImpl assetService;
    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private RegisterServiceImpl registerService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BalanceServiceImpl balanceService;

    @Autowired
    private ApplyForApprovalServiceImpl applyForApprovalService;

    @Autowired
    private InvitationServiceImpl invitationService;

    @Autowired
    private RedemptionEntryRepository redemptionEntryRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleServiceImpl roleService;


    @Autowired
    private PaymentServiceImpl paymentService;

/*
    @Autowired
    private PagedResourcesAssembler<EmployeeResp> pagedResourcesAssembler;
*/

    @Operation(summary = "1、获得")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}", produces = "application/json")
    public EntityModel<SupplierResp> getSupplier(@PathVariable  long SUPPLIER_ID) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

        Balance balance = balanceService.balance(supplier.getId(), EnumUserType.business);

        List<Asset> assets = assetRepository.findAllBySource(supplier.getId());

        SupplierResp supplierResp = SupplierResp.from(supplier,assets);

        supplierResp.setBalance(EntityModel.of(BalanceResp.from(balance)));
        supplierResp.setLogo(fileStorageService.loadDocumentWithDefault(EnumDocumentType.supllier_logo,supplier.getCode()));

        EntityModel entityModel = EntityModel.of(supplierResp);
        entityModel.add(linkTo(methodOn(RequestFuckRestController.class).applyCertification(supplier.getId(),null)).withRel("apply_for_approval_url"));
        entityModel.add(linkTo(methodOn(SupplierRestController.class).listEmployee(supplier.getId(),null,null)).withRel("employee_url"));


        entityModel.add(linkTo(methodOn(RequestFuckRestController.class).approveRequest(supplier.getId(),null)).withRel("upload_file_url"));
        entityModel.add(linkTo(methodOn(RedemptionRestController.class).validateVoucherByCode(null)).withRel("redeem_url"));
        entityModel.add(linkTo(methodOn(ProductRestController.class).createProduct(supplier.getId(),null)).withRel("create_product_url"));

            return EntityModel.of(supplierResp);


    }

    @Operation(summary = "1、获得")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/edit", produces = "application/json")
    public EntityModel<SupplierEditResp.InfoTab> getSupplierEdit(@PathVariable  long SUPPLIER_ID) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

        Balance balance = balanceService.balance(supplier.getId(), EnumUserType.business);

        List<Asset> assets = assetRepository.findByTypeAndIdIdIn(EnumAssetType.qr,new HashSet<>(Arrays.asList(supplier.getCode())));

        Asset asset_booking = assetService.getWithNew(supplier.getCode(),supplier.getId(),EnumAssetType.supplier_qr);


        SupplierEditResp supplierEditResp = new SupplierEditResp();
        supplierEditResp.setBalance(EntityModel.of(BalanceResp.from(balance)));
        //supplierEditResp.setAssets(assets.stream().map(x -> AssetResp.from(x)).collect(Collectors.toList()));
        supplierEditResp.setAssetMap(AssetResp.toMap(Arrays.asList(asset_booking)));

        SupplierEditResp.InfoTab supplierResp = SupplierEditResp.InfoTab.from(supplier);
       // supplierResp.setBalance(EntityModel.of(BalanceResp.from(balance)));


        supplierResp.setLogo(fileStorageService.loadDocumentWithDefault(EnumDocumentType.artwork_audio,supplier.getCode()));
        supplierResp.setLogo(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.supllier_logo,supplier.getCode()));
        supplierResp.setLicense_image(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.license,supplier.getCode()));
        supplierResp.setLiability_insurance_image(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.liability_insurance,supplier.getCode()));
        supplierResp.setBussiness_license(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.business_license,supplier.getCode()));
        supplierResp.setLicense_for_opening_bank_account(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.license_for_opening_bank_account,supplier.getCode()));

        EntityModel entityModel = EntityModel.of(supplierResp);




        entityModel.add(linkTo(methodOn(SupplierRestController.class).updateSupplier(supplier.getId(),null)).withRel("edit"));
        supplierEditResp.setInfoTab(entityModel);



        EntityModel entityModel1 = EntityModel.of(supplierEditResp);



        entityModel1.add(linkTo(methodOn(RequestFuckRestController.class).applyCertification(supplier.getId(),null)).withRel("apply_for_approval_url"));
        entityModel1.add(linkTo(methodOn(SupplierRestController.class).listEmployee(supplier.getId(),null,null)).withRel("employee_url"));


        entityModel1.add(linkTo(methodOn(RedemptionRestController.class).validateVoucherByCode(null)).withRel("redeem_url"));
        entityModel1.add(linkTo(methodOn(ProductRestController.class).createProduct(supplier.getId(),null)).withRel("create_product_url"));

        return entityModel1;


    }

    @Operation(summary = "1、获得")
    @GetMapping(value = "/suppliers", produces = "application/json")
    public PagedModel pageSupplier(@PageableDefault(size = 20) final Pageable pageable,PagedResourcesAssembler<EntityModel<SupplierResp>> assembler) {

        System.out.println("=============="+ pageable.getPageSize());
        System.out.println("=============="+ pageable.getPageNumber());
        Pageable paging = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());


        Supplier probe = new Supplier();
       // probe.setId(SUPPLIER_ID);

/*
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()//same as matchingAll()
                .withIgnorePaths("id")
                .withIgnorePaths("total_redeemed")
                .withIgnorePaths("total_failed")
                .withIgnorePaths("total_succeeded")
                .withIgnorePaths("total_rolled_back")
                .withIgnorePaths("total_rollback_failed")
                .withIgnorePaths("total_rollback_succeeded")

                ;
        Example<Supplier> example = Example.of(probe,exampleMatcher);
*/

     //   Page<Supplier> supplier = supplierRepository.findAll(example,paging);
        Page<Supplier> supplier = supplierRepository.findAll(paging);

        Page<EntityModel<SupplierResp>> page1 = supplier.map(x->{
            List<Asset> assets = assetRepository.findAllBySource(x.getId());
            return EntityModel.of(SupplierResp.from(x,assets));

        });



        return assembler.toModel(page1);


    }



/*
    @Operation(summary = "1.2、新建")
    @PostMapping(value = "/suppliers/register", produces = "application/json")
    public ResponseEntity<Supplier> reigisterSupplier(@Valid @RequestPart("user") String pojos,@RequestPart("file") MultipartFile multipartFile) {

        SupplierPojo pojo = (SupplierPojo)JSONObject.stringToValue(pojos);

        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("model", ignoreCase());

        User probe = new User();
        probe.setPhone(pojo.getPhone());
        Example<User> example = Example.of(probe, modelMatcher);



        boolean resourceById = userRepository.findOne(example).isPresent();


        if (!resourceById) {



            System.out.println("抛出异常");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }else {
            Supplier supplier = registerService.register(pojo,multipartFile);
            return ResponseEntity.ok(supplier);
        }



    }*/


    @Operation(summary = "2、新建")
    @PostMapping(value = "/suppliers", produces = "application/json")
    public ResponseEntity<EntityModel> createSupplier(@RequestBody SupplierPojo pojo) {


        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnorePaths("desc")
                .withIgnorePaths("code")
                .withMatcher("model", ignoreCase());

        Supplier probe = new Supplier();
        probe.setName(pojo.getName());
        Example<Supplier> example = Example.of(probe, modelMatcher);
            Optional<Supplier> optionalSupplier = supplierRepository.findOne(example);


            if (optionalSupplier.isEmpty()) {

                SupplierPojoVo supplierPojoVo = SupplierPojoVo.fromPojo(pojo);
                Supplier supplier = supplierService.createSupplier(supplierPojoVo,EnumSupplierStatus.Active);



                SupplierResp supplierResp = SupplierResp.from(supplier, Arrays.asList());

                EntityModel entityModel = EntityModel.of(supplierResp);

                entityModel.add(linkTo(methodOn(SupplierRestController.class).createSupplier(null)).withSelfRel());
                entityModel.add(linkTo(methodOn(SupplierRestController.class).updateSupplier(supplier.getId(),null)).withRel("update_url"));

                entityModel.add(linkTo(methodOn(ProductRestController.class).createProduct(supplier.getId(),null)).withRel("add_product_url"));
                entityModel.add(linkTo(methodOn(FileUploadController.class).upload(null)).withRel("upload_file_url"));



                return ResponseEntity.ok(entityModel);

            }else{

                System.out.println("商户名称 已经存在");
                throw new ExistException(Enumfailures.general_exists_error,pojo.getName()+" 商户名称 已经存在");

            }



/*
        System.out.println("抛出异常createSupplier");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));*/


    }

    @Operation(summary = "3、更新")
    @PutMapping(value = "/suppliers/{SUPPLIER_ID}", produces = "application/json")
    public ResponseEntity<SupplierEditResp.InfoTab> updateSupplier(@PathVariable long SUPPLIER_ID, @RequestBody SupplierEditResp.InfoTab pojo) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);
        if(optionalSupplier.isEmpty()){

            throw new BookNotFoundException(Enumfailures.resource_not_found,"没有找到供应商");

        }
        Supplier supplier = optionalSupplier.get();

        supplier = supplierService.edit(supplier,pojo);



        SupplierEditResp.InfoTab supplierEditResp = SupplierEditResp.InfoTab.from(supplier);

        supplierEditResp.setLogo(fileStorageService.loadDocumentWithDefault(EnumDocumentType.supllier_logo,supplier.getCode()));
        supplierEditResp.setLicense_image(fileStorageService.loadDocumentWithDefault(EnumDocumentType.license,supplier.getCode()));
        supplierEditResp.setLiability_insurance_image(fileStorageService.loadDocumentWithDefault(EnumDocumentType.liability_insurance,supplier.getCode()));
        supplierEditResp.setBussiness_license(fileStorageService.loadDocumentWithDefault(EnumDocumentType.business_license,supplier.getCode()));
        supplierEditResp.setLicense_for_opening_bank_account(fileStorageService.loadDocumentWithDefault(EnumDocumentType.license_for_opening_bank_account,supplier.getCode()));

        return ResponseEntity.ok(supplierEditResp);

    }

    @Operation(summary = "4、删除")
    @DeleteMapping(value = "/suppliers/{SUPPLIER_ID}", produces = "application/json")
    public ResponseEntity<Void> deleteSupplier(@PathVariable long SUPPLIER_ID) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);

        if(supplierOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.resource_not_found,"没有找到供应商");

        }
        Supplier supplier = supplierOptional.get();
        registerService.delete(supplier);

        return ResponseEntity.ok().build();





    }




  //  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
  //  @Secured("ROLE_USER")
  //  @PreAuthorize("hasRole('ROLE_ADMIN')")








    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/employees/main_page", produces = "application/json")
    public EntityModel Page_mainEmployee(@PathVariable long SUPPLIER_ID) {

/*

        Authentication authentication =  authenticationFacade.getAuthentication();

        UserDetails user = (UserDetails)authentication.getPrincipal();

        System.out.println(user.getAuthorities());*/

        Optional<Supplier> supplier = supplierRepository.findById(SUPPLIER_ID);
        if(supplier.isEmpty()) {
            throw new BookNotFoundException(SUPPLIER_ID,"找不到供应商");

        }
        List<Role> roles = roleRepository.findAll();

Map maps  =         Map.of("roles", roles.stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.getName());
            //  enumResp.setName(x.getName());
            enumResp.setText(EnumRole.valueOf(x.getName()).toString());
            return enumResp;
        }).collect(Collectors.toList()));




            EntityModel entityModel =  EntityModel.of(maps);/*,
                    "_link",linkTo(methodOn(SupplierRestController.class).linkEmployee(supplier.get().getId(),null)).withRel("addEmployees"))*/

            entityModel.add(linkTo(methodOn(SupplierRestController.class).page_addEmployee(supplier.get().getId())).withRel("Page_addEmployees"));
            entityModel.add(linkTo(methodOn(SupplierRestController.class).listEmployee(supplier.get().getId(),null,null)).withRel("list"));

            return entityModel;
    }



    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/employees/Page_listEmployee/phone", produces = "application/json")
    public EntityModel Page_listEmployeePhone(@PathVariable long SUPPLIER_ID) {

/*

        Authentication authentication =  authenticationFacade.getAuthentication();

        UserDetails user = (UserDetails)authentication.getPrincipal();

        System.out.println(user.getAuthorities());*/

        Optional<Supplier> supplier = supplierRepository.findById(SUPPLIER_ID);
        if(supplier.isEmpty()) {
            throw new BookNotFoundException(SUPPLIER_ID,"找不到供应商");

        }
        List<Role> roles = roleRepository.findAll();

        Map maps  =         Map.of("role_list", roles.stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.getName());
            //  enumResp.setName(x.getName());
            enumResp.setText(EnumRole.valueOf(x.getName()).toString());
            return enumResp;
        }).collect(Collectors.toList()));





        EntityModel entityModel =  EntityModel.of(Map.of("parameterList",maps));/*,
                    "_link",linkTo(methodOn(SupplierRestController.class).linkEmployee(supplier.get().getId(),null)).withRel("addEmployees"))*/

        entityModel.add(linkTo(methodOn(SupplierRestController.class).page_addEmployee(supplier.get().getId())).withRel("Page_createEmployee"));
        entityModel.add(linkTo(methodOn(SupplierRestController.class).listEmployee(supplier.get().getId(),null,null)).withRel("list"));

        return entityModel;
    }






    @Operation(summary = "4、成为员工")
    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/employees", produces = "application/json")
    public ResponseEntity linkEmployee(@PathVariable long SUPPLIER_ID, @RequestBody @Valid EmployerPojo employerPojo) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);
        if(optionalSupplier.isEmpty()) {
            throw new BookNotFoundException(SUPPLIER_ID,"找不到供应商");
        }

        Supplier supplier = optionalSupplier.get();


        List<String> enumRoles = roleService.get(supplier).stream().map(x->x.name()).collect(Collectors.toList());

        List<String> hasEnumRoles  = employerPojo.getRoles().stream().filter(x-> enumRoles.contains(x)).collect(Collectors.toList());

        if(hasEnumRoles.isEmpty()){
            throw new BookNotFoundException("无相关权限"+employerPojo.getRoles(),"");
        }

        Triplet<Supplier,Employee,User> triplet = supplierService.linkEmployee(supplier,employerPojo,hasEnumRoles);



        return ResponseEntity.ok( EmployeeResp.sigleElementfrom(triplet));


    }

    @Operation(summary = "4、成为员工")
    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/employees/create", produces = "application/json")
    public ResponseEntity createEmployee(@PathVariable long SUPPLIER_ID, @RequestBody @Valid EmployerPojo employerPojo) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);
        if(optionalSupplier.isEmpty()) {
            throw new BookNotFoundException(SUPPLIER_ID,"找不到供应商");
        }

        Supplier supplier = optionalSupplier.get();


        Authentication authentication =  authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);




        List<String> enumRoles = roleService.get(supplier).stream().map(x->x.name()).collect(Collectors.toList());

        List<String> hasEnumRoles  = employerPojo.getRoles().stream().filter(x-> enumRoles.contains(x)).collect(Collectors.toList());

        if(hasEnumRoles.isEmpty()){
            throw new BookNotFoundException("无相关权限"+employerPojo.getRoles(),"");
        }



        Employee triplet = supplierService.createEmployee(supplier,employerPojo,hasEnumRoles);



        return ResponseEntity.ok(triplet);


    }



    @Operation(summary = "3、list所有员工")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/employees", produces = "application/json")
    public PagedModel listEmployee(@PathVariable long SUPPLIER_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<EmployeeResp>> assembler) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);
        if(optionalSupplier.isEmpty()) {
            throw new BookNotFoundException(SUPPLIER_ID,"找不到供应商");

        }
        Supplier supplier = optionalSupplier.get();

        Page<Employee> employees = employeeRepository.findBySuplierId(supplier.getId(),pageable);

        List<User> users = userRepository.findAllById(employees.stream().map(x->x.getUserId()).collect(Collectors.toList()));
        Map<Long,User> userMap = users.stream().collect(Collectors.toMap(x->x.getId(),x->x));


        return assembler.toModel(employees.map(x->{

            EntityModel<EmployeeResp> employeeRespEntityModel= EmployeeResp.pageElementfrom(Triplet.with(supplier,x,userMap.get(x.getUserId())));
            //    employeeRespEntityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(null)).withRel("booking"));
            employeeRespEntityModel.add(linkTo(methodOn(EmployeeRestController.class).getEmployeeEdit(x.getId())).withRel("getEmployee"));
            employeeRespEntityModel.add(linkTo(methodOn(EmployeeRestController.class).delete(x.getId())).withRel("deleteEmployee"));
            employeeRespEntityModel.add(linkTo(methodOn(EmployeeRestController.class).Page_updateEmployee(x.getId())).withRel("Page_updateEmployee"));

            employeeRespEntityModel.add(linkTo(methodOn(EmployeeRestController.class).updateEmployee(x.getId(),null)).withRel("updateEmployee"));
            return employeeRespEntityModel;
        }));

    }

    @Operation(summary = "3、list所有员工")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/employees/phone", produces = "application/json")
    public PagedModel getEmployeeList_(@PathVariable long SUPPLIER_ID,  Pageable pageable, PagedResourcesAssembler<EntityModel<EmployeeResp>> assembler) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);
        if(optionalSupplier.isEmpty()) {
            throw new BookNotFoundException(SUPPLIER_ID,"找不到供应商");

        }
        Supplier supplier = optionalSupplier.get();

        Page<Employee> employees = employeeRepository.findBySuplierIdAndStatus(supplier.getId(),EnumEmployeeStatus.inactive,pageable);

        List<User> users = userRepository.findAllById(employees.stream().map(x->x.getUserId()).collect(Collectors.toList()));
        Map<Long,User> userMap = users.stream().collect(Collectors.toMap(x->x.getId(),x->x));

        Page<EntityModel<EmployeeResp>> list = employees.map(x->{

            EntityModel<EmployeeResp> employeeRespEntityModel= EmployeeResp.
                    pageElementfrom(Triplet.with(supplier,x,userMap.get(x.getUserId())));
            //    employeeRespEntityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(null)).withRel("booking"));
            employeeRespEntityModel.add(linkTo(methodOn(EmployeeRestController.class).getEmployeeEdit(x.getId())).withSelfRel());
            employeeRespEntityModel.add(linkTo(methodOn(EmployeeRestController.class).delete(x.getId())).withRel("delete"));
            employeeRespEntityModel.add(linkTo(methodOn(EmployeeRestController.class).Page_updateEmployee(x.getId())).withRel("Page_update"));

            employeeRespEntityModel.add(linkTo(methodOn(EmployeeRestController.class).updateEmployee(x.getId(),null)).withRel("update"));
            return employeeRespEntityModel;
        });


        return assembler.toModel(list); //PagedModel.of(list.getContent(), new PagedModel.PageMetadata(10,0,100,100));

    }




    @Operation(summary = "4、邀请成为合作商")
    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/invitations-employee", produces = "application/json")
    public Invitation 邀请员工(@PathVariable long SUPPLIER_ID, @RequestBody InvitationEmployeePojo employerPojo) {


        Optional<Supplier> supplier = supplierRepository.findById(SUPPLIER_ID);
        if(supplier.isPresent()){
            Invitation componentRight = invitationService.inviteEmployee(supplier,employerPojo);
            return componentRight;
        }else{
            System.out.println("抛出异常");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }
    }








/*
    @PostMapping(value = "/componet_rights/{COMPONENT_RIGHTS_ID}/access_validators", produces = "application/json")
    public AccessValidator addComponentRight_Validator(@PathVariable int COMPONENT_RIGHTS_ID, @PathVariable int COUPON_TMPL_ID) {
        AccessValidator componentRight = new AccessValidator();
        return componentRight;
    }


    @GetMapping(value = "/{APP_ID}/{COUPON_TMPL_ID}", produces = "application/json")
    public List<ComponentRight> listCouponTemplate(@PathVariable int APP_ID, CouponTemplatePojoList  couponTemplatePojoList) {
        return null;
    }
*/



    @Operation(summary = "1、增加结算账号")
    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/settle_accounts", produces = "application/json")
    public SettleAccount createSettleAccounts(@PathVariable long SUPPLIER_ID, SettleAccountPojo pojo) {


        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);

        if(optionalSupplier.isPresent()){
            SettleAccount settleAccount = settleAccountService.add(optionalSupplier.get(),pojo);

            return settleAccount;
        }else{
            throw new BookNotFoundException(SUPPLIER_ID,"找不到供应商");
        }


    }




/*    @Operation(summary = "3、list所有员工")
    @GetMapping(value = "/{SUPPLIER_ID}/campaigns", produces = "application/json")
    public List<Campaign> getCampagnss(@PathVariable long SUPPLIER_ID) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);

        if(optionalSupplier.isPresent()){

            List<ScenarioAssignment> scenarioAssignments = scenarioAssignmentRepository.findBySupplier(optionalSupplier.get().getId());

            Set<Long> longList = scenarioAssignments.stream().map(x->x.getScenario()).collect(Collectors.toSet()) ;

            List<Campaign> campaigns = campaignRepository.findAllById(longList);

            return campaigns;
        }

        throw new RuntimeException();
    }*/

    @Operation(summary = "3、list所有员工")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/campaigns", produces = "application/json")
    public List<Campaign> getCampagnss(@PathVariable long SUPPLIER_ID) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);

        if(optionalSupplier.isPresent()){

            List<Quota> quotas = quotaRepository.findByTypeAndSupplier(EnumQuotaType.NominatedSupplier,optionalSupplier.get().getId());

            List<ScenarioAssignment> scenarioAssignments = scenarioAssignmentRepository.findBySupplier(optionalSupplier.get().getId());

            Set<Long> scenarioIds = scenarioAssignments.stream().map(x->x.getScenario()).collect(Collectors.toSet()) ;

            List<Quota> quotas1 = quotaRepository.findAllByTypeAndScenarioIn(EnumQuotaType.Scenario,scenarioIds);


            List<Long> ids = Stream.concat(quotas1.stream().map(x->x.getCompaign()), quotas.stream().map(x->x.getCompaign())).collect(toList());


            List<Campaign> campaigns = campaignRepository.findAllById(ids);

            return campaigns;
        }else{
            throw new BookNotFoundException(SUPPLIER_ID,Supplier.class.getSimpleName());
        }


    }











    @Operation(summary = "3、list所有员工")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/summary", produces = "application/json")
    public ResponseEntity<SummarySupplierResp> getRedemptionEntry(@PathVariable long SUPPLIER_ID) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);

        if(optionalSupplier.isPresent()){

            List<RedemptionEntry> clainQuotas = redemptionEntryRepository.findAllBySupplier(optionalSupplier.get().getId());

  /*          Map<Long, Integer> 每种券核销的金额 = clainQuotas.stream()
                    .collect(groupingBy(RedemptionEntry::getCampaign, summingInt(RedemptionEntry::getGift_amount)));
*/


            List<PublicationEntry> publicationEntryList = publicationEntryRepository.findAll();
            Map<Long, Long> 每一个消费券的领取的数量 = publicationEntryList.stream()
                    .collect(groupingBy(PublicationEntry::getCampaign,Collectors.counting() ));



            Map<Long, Triplet<Long,Long,Long>> 每种订单的核销金额和数量 = clainQuotas.stream()
                    .collect(groupingBy(RedemptionEntry::getCampaign, collectingAndThen(toList(), list -> {
                        long count = list.stream()
                                .map(x->x.getSupplier())
                                .collect(counting());
       /*                 String titles = list.stream()
                                .map(BlogPost::getTitle)
                                .collect(joining(" : "));
                        IntSummaryStatistics summary = list.stream()
                                .collect(summarizingInt(BlogPost::getLikes));*/

                        Long cou = 每一个消费券的领取的数量.get(list.get(0).getCampaign());
                        if(cou!= null){
                            return Triplet.with(count, 1L,cou);
                        }else{
                            return Triplet.with(count, 1L,0L);
                        }

                    })));

            SummarySupplierResp summarySupplierRedemptionResp =null;// SummarySupplierResp.from(每种订单的核销金额和数量);
            return ResponseEntity.ok(summarySupplierRedemptionResp);
        }

        throw new RuntimeException();
    }



    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/employees/page_", produces = "application/json")
    public EntityModel page_addEmployee(@PathVariable long SUPPLIER_ID) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);
        if(optionalSupplier.isEmpty()) {
            throw new BookNotFoundException(SUPPLIER_ID,"找不到供应商");

        }
        Supplier supplier = optionalSupplier.get();

        List<String> enumRoles = Arrays.asList(EnumRole.ROLE_BANK_STAFF).stream().map(e->e.name()).collect(Collectors.toList());
        List<Role> roles = roleRepository.findAll();
        EntityModel entityModel =  EntityModel.of( Map.of(

                "roles", roles.stream().filter(e->enumRoles.contains(e.getName())).map(x->{
                    EnumResp enumResp = new EnumResp();
                    enumResp.setId(x.getName());
                    //   enumResp.setName(x.getName());
                    enumResp.setText(EnumRole.valueOf(x.getName()).toString());
                    return enumResp;
                }).collect(Collectors.toList())));


        entityModel.add(linkTo(methodOn(SupplierRestController.class).createEmployee(supplier.getId(),null)).withRel("create"));


        return entityModel;
    }

}