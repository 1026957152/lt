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
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Triplet;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    private AssetRepository assetRepository;

    @Autowired
    private RegisterServiceImpl registerService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplyCertificationRepository applyCertificationRepository;

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


/*
    @Autowired
    private PagedResourcesAssembler<EmployeeResp> pagedResourcesAssembler;
*/

    @Operation(summary = "1、获得")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}", produces = "application/json")
    public ResponseEntity<SupplierResp> getSupplier(@PathVariable  long SUPPLIER_ID) {
        Optional<Supplier> supplier = supplierRepository.findById(SUPPLIER_ID);
        if(supplier.isPresent()){

            List<Asset> assets = assetRepository.findAllBySource(supplier.get().getId());

            SupplierResp supplierResp = SupplierResp.from(supplier.get(),assets);
            supplierResp.add(linkTo(methodOn(SupplierRestController.class).createRequest(supplier.get().getId(),EnumApplicationType.Merchants_settled,null)).withRel("upload_file_url"));
            supplierResp.add(linkTo(methodOn(RedemptionRestController.class).redeemVonchorBycode(null,null)).withRel("redeem_url"));
            supplierResp.add(linkTo(methodOn(ProductRestController.class).createProduct(supplier.get().getId(),null)).withRel("create_product_url"));
            supplierResp.add(linkTo(methodOn(BookingRestController.class).createBooking(null)).withRel("booking_url"));

            return ResponseEntity.ok(SupplierResp.from(supplier.get(),assets));

        }else{
            System.out.println("抛出异常");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }


    }

    @Operation(summary = "1、获得")
    @GetMapping(value = "/suppliers", produces = "application/json")
    public ResponseEntity<Page<SupplierResp>> pageSupplier(@PageableDefault(size = 20) final Pageable pageable) {

        System.out.println("=============="+ pageable.getPageSize());
        System.out.println("=============="+ pageable.getPageNumber());
        Pageable paging = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());


        Supplier probe = new Supplier();
       // probe.setId(SUPPLIER_ID);

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

        Page<Supplier> supplier = supplierRepository.findAll(example,paging);

        Page<SupplierResp> page1 = supplier.map(x->{
            List<Asset> assets = assetRepository.findAllBySource(x.getId());
            return SupplierResp.from(x,assets);

        });



        return ResponseEntity.ok(page1);


    }




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



    }


    @Operation(summary = "2、新建")
    @PostMapping(value = "/suppliers", produces = "application/json")
    public ResponseEntity<SupplierResp> createSupplier(@RequestBody SupplierPojo pojo) {


        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnorePaths("desc")
                .withIgnorePaths("code")
                .withMatcher("model", ignoreCase());

        Supplier probe = new Supplier();
        probe.setName(pojo.getSupplierName());
        Example<Supplier> example = Example.of(probe, modelMatcher);
            Optional<Supplier> optionalSupplier = supplierRepository.findOne(example);


            if (optionalSupplier.isEmpty()) {
                Supplier supplier = supplierService.createSupplier(pojo);



                SupplierResp supplierResp = SupplierResp.from(supplier, Arrays.asList());
                supplierResp.add(linkTo(methodOn(SupplierRestController.class).createSupplier(null)).withSelfRel());
                supplierResp.add(linkTo(methodOn(SupplierRestController.class).updateSupplier(supplier.getId(),null)).withRel("update_url"));

                supplierResp.add(linkTo(methodOn(ProductRestController.class).createProduct(supplier.getId(),null)).withRel("add_product_url"));
                supplierResp.add(linkTo(methodOn(FileUploadController.class).upload(null)).withRel("upload_file_url"));



                return ResponseEntity.ok(supplierResp);

            }else{

                System.out.println("商户名称 已经存在");
                throw new ExistException(pojo.getSupplierName()+" 商户名称 已经存在");

            }



/*
        System.out.println("抛出异常createSupplier");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));*/


    }

    @Operation(summary = "3、更新")
    @PutMapping(value = "/suppliers/{SUPPLIER_ID}", produces = "application/json")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable long SUPPLIER_ID, @RequestBody SupplierPutPojo pojo) {

        Optional<Supplier> supplier = supplierRepository.findById(SUPPLIER_ID);
        if(supplier.isPresent()){
            Supplier componentRight = supplierService.put(supplier.get(),pojo);
            return ResponseEntity.ok(componentRight);

        }else{
            System.out.println("抛出异常");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }

    }

    @Operation(summary = "4、删除")
    @DeleteMapping(value = "/suppliers/{SUPPLIER_ID}", produces = "application/json")
    public ResponseEntity<Supplier> deleteSupplier(@PathVariable long SUPPLIER_ID) {

        Optional<Supplier> supplier = supplierRepository.findById(SUPPLIER_ID);
        if(supplier.isPresent()){
            registerService.delete(supplier.get());
            return ResponseEntity.noContent().build();
        }else{
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }





    }




  //  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
  //  @Secured("ROLE_USER")
  //  @PreAuthorize("hasRole('ROLE_ADMIN')")








    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/employees/main_page", produces = "application/json")
    public EntityModel page_mainEmployee(@PathVariable long SUPPLIER_ID) {

/*

        Authentication authentication =  authenticationFacade.getAuthentication();

        UserDetails user = (UserDetails)authentication.getPrincipal();

        System.out.println(user.getAuthorities());*/

        Optional<Supplier> supplier = supplierRepository.findById(SUPPLIER_ID);
        if(supplier.isEmpty()) {
            throw new BookNotFoundException(SUPPLIER_ID,"找不到供应商");

        }
            List<Role> roles = roleRepository.findAll();
            EntityModel entityModel =  EntityModel.of( Map.of("roles", roles.stream().map(x->{
                        EnumResp enumResp = new EnumResp();
                        enumResp.setId(x.getName());
                        enumResp.setName(x.getName());
                        enumResp.setText(x.getName());
                        return enumResp;
                    }).collect(Collectors.toList())/*,
                    "_link",linkTo(methodOn(SupplierRestController.class).linkEmployee(supplier.get().getId(),null)).withRel("addEmployees"))*/));

        entityModel.add(linkTo(methodOn(SupplierRestController.class).page_addEmployee(supplier.get().getId())).withRel("Page_addEmployees"));

        entityModel.add(linkTo(methodOn(SupplierRestController.class).pageEmployess(supplier.get().getId(),null)).withRel("listEmployees"));

            return entityModel;
    }



    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/employees/page", produces = "application/json")
    public EntityModel page_addEmployee(@PathVariable long SUPPLIER_ID) {

/*

        Authentication authentication =  authenticationFacade.getAuthentication();

        UserDetails user = (UserDetails)authentication.getPrincipal();

        System.out.println(user.getAuthorities());*/

        Optional<Supplier> supplier = supplierRepository.findById(SUPPLIER_ID);
        if(supplier.isEmpty()) {
            throw new BookNotFoundException(SUPPLIER_ID,"找不到供应商");

        }
        List<Role> roles = roleRepository.findAll();
        EntityModel entityModel =  EntityModel.of( Map.of("roles", roles.stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.getName());
            enumResp.setName(x.getName());
            enumResp.setText(x.getName());
            return enumResp;
        }).collect(Collectors.toList())/*,
                    "_link",linkTo(methodOn(SupplierRestController.class).linkEmployee(supplier.get().getId(),null)).withRel("addEmployees"))*/));

        entityModel.add(linkTo(methodOn(SupplierRestController.class).linkEmployee(supplier.get().getId(),null)).withRel("addEmployees"));

        return entityModel;
    }

    @Operation(summary = "4、成为员工")
    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/employees", produces = "application/json")
    public ResponseEntity<EntityModel<EmployeeResp>> linkEmployee(@PathVariable long SUPPLIER_ID, @RequestBody @Valid EmployerPojo employerPojo) {


        Authentication authentication =  authenticationFacade.getAuthentication();

        UserDetails user = (UserDetails)authentication.getPrincipal();

        System.out.println(user.getAuthorities());


        Optional<Supplier> supplier = supplierRepository.findById(SUPPLIER_ID);
        if(supplier.isPresent()){
            Triplet<Supplier,Employee,User> employee = supplierService.createEmployee(supplier.get(),employerPojo);
            return ResponseEntity.ok(EmployeeResp.sigleElementfrom(employee));
        }else{
            throw new BookNotFoundException(SUPPLIER_ID,"找不到供应商");
        }




    }




    @Operation(summary = "3、list所有员工")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/employees", produces = "application/json")
    public PagedModel pageEmployess(@PathVariable long SUPPLIER_ID,PagedResourcesAssembler<EntityModel<EmployeeResp>> assembler) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);
        if(optionalSupplier.isEmpty()) {
            throw new BookNotFoundException(SUPPLIER_ID,"找不到供应商");

        }
        Supplier supplier = optionalSupplier.get();

        Page<Employee> employees = employeeRepository.findBySuplierId(supplier.getId(),PageRequest.of(0,10));

        List<User> users = userRepository.findAllById(employees.stream().map(x->x.getUserId()).collect(Collectors.toList()));
        Map<Long,User> userMap = users.stream().collect(Collectors.toMap(x->x.getId(),x->x));

        Page<EntityModel<EmployeeResp>> list = employees.map(x->{
            EntityModel<EmployeeResp> employeeRespEntityModel= EmployeeResp.pageElementfrom(Triplet.with(supplier,x,userMap.get(x.getUserId())));
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


    @Operation(summary = "4、邀请成为")
    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/invitations-partner", produces = "application/json")
    public Invitation 邀请合作(@PathVariable long SUPPLIER_ID, @RequestBody InvitationPartnerPojo pojo) {


        Optional<Supplier> supplier = supplierRepository.findById(SUPPLIER_ID);
        if(supplier.isPresent()){

            Invitation componentRight = invitationService.invitePartner(supplier,pojo);
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



    @Operation(summary = "1、申请认证")
    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/applications", produces = "application/json")
    public ApplyCertification applyCertification(@PathVariable long SUPPLIER_ID, @RequestBody @Valid CertificationApplyReq pojo) {


        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);

        if(optionalSupplier.isPresent()){
            ApplyCertification settleAccount = applyForApprovalService.apply(optionalSupplier.get(),pojo);

            return settleAccount;
        }else{
            throw new BookNotFoundException(SUPPLIER_ID,"找不到供应商");
        }


    }



    @Operation(summary = "1、申请")
    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/request", produces = "application/json")
    public ApplyCertification createRequest(@PathVariable long SUPPLIER_ID,@RequestParam EnumApplicationType type, @RequestBody @Valid CertificationApplyReq pojo) {


        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);

        if(optionalSupplier.isPresent()){
            ApplyCertification settleAccount = applyForApprovalService.apply(optionalSupplier.get(),pojo);

            return settleAccount;
        }else{
            throw new BookNotFoundException(SUPPLIER_ID,"找不到供应商");
        }


    }


    @Operation(summary = "1、拒绝")
    @DeleteMapping(value = "/requests/{REQUEST_ID}", produces = "application/json")
    public ApplyCertification rejectRequest(@PathVariable long REQUEST_ID, @RequestBody @Valid CertificationApplyReq pojo) {


        Optional<ApplyCertification> optionalSupplier = applyCertificationRepository.findById(REQUEST_ID);

        if(optionalSupplier.isPresent()){
            ApplyCertification settleAccount = applyForApprovalService.reject(optionalSupplier.get(),pojo);

            return settleAccount;
        }else{
            throw new BookNotFoundException(REQUEST_ID,"找不到请求"+ EnumApplicationType.Merchants_settled.name());
        }


    }


    @Operation(summary = "1、批准申请")
    @PutMapping(value = "/requests/{REQUEST_ID}", produces = "application/json")
    public ApplyCertification approveRequest(@PathVariable long REQUEST_ID, @RequestBody @Valid CertificationApplyReq pojo) {

        Optional<ApplyCertification> optionalSupplier = applyCertificationRepository.findById(REQUEST_ID);

        if(optionalSupplier.isPresent()){
            ApplyCertification settleAccount = applyForApprovalService.approve(optionalSupplier.get(),pojo);
            return settleAccount;
        }else{
            throw new BookNotFoundException(REQUEST_ID,"找不到请求"+ EnumApplicationType.Merchants_settled.name());
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
                    .collect(groupingBy(PublicationEntry::getCampaign_id,Collectors.counting() ));



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
}