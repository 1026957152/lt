package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.SummarySupplierRedemptionResp;
import com.lt.dom.OctResp.SummarySupplierResp;
import com.lt.dom.OctResp.SupplierResp;
import com.lt.dom.domain.SettleAccount;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.EnumQuotaType;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;
import static org.springframework.data.util.Pair.toMap;
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
    private InvitationServiceImpl invitationService;

    @Autowired
    private RedemptionEntryRepository redemptionEntryRepository;



    @Operation(summary = "1?????????")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}", produces = "application/json")
    public ResponseEntity<SupplierResp> getComponentRight_Validator(@PathVariable  long SUPPLIER_ID) {
        Optional<Supplier> supplier = supplierRepository.findById(SUPPLIER_ID);
        if(supplier.isPresent()){

            List<Asset> assets = assetRepository.findAllBySource(supplier.get().getId());
            return ResponseEntity.ok(SupplierResp.from(supplier.get(),assets));

        }else{
            System.out.println("????????????");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }


    }

    @Operation(summary = "1?????????")
    @GetMapping(value = "/suppliers", produces = "application/json")
    public ResponseEntity<Page<SupplierResp>> pageSupplier(
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "3") int size) {

        Pageable paging = PageRequest.of(page, size);


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




    @Operation(summary = "1.2?????????")
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



            System.out.println("????????????");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }else {
            Supplier supplier = registerService.register(pojo,multipartFile);
            return ResponseEntity.ok(supplier);
        }



    }


    @Operation(summary = "2?????????")
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

                System.out.println("???????????? ????????????");
                throw new ExistException(pojo.getSupplierName()+" ???????????? ????????????");

            }



/*
        System.out.println("????????????createSupplier");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));*/


    }

    @Operation(summary = "3?????????")
    @PutMapping(value = "/suppliers/{SUPPLIER_ID}", produces = "application/json")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable long SUPPLIER_ID, @RequestBody SupplierPutPojo pojo) {

        Optional<Supplier> supplier = supplierRepository.findById(SUPPLIER_ID);
        if(supplier.isPresent()){
            Supplier componentRight = supplierService.put(supplier.get(),pojo);
            return ResponseEntity.ok(componentRight);

        }else{
            System.out.println("????????????");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }

    }

    @Operation(summary = "4?????????")
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






    @Operation(summary = "4???????????????")
    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/employees", produces = "application/json")
    public Employee ????????????(@PathVariable String SUPPLIER_ID, @RequestBody EmployerPojo employerPojo) {
        Employee componentRight = new Employee();
        return componentRight;
    }




    @Operation(summary = "3???list????????????")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/employees", produces = "application/json")
    public List<Employee> getSupplier_Employers(@PathVariable String SUPPLIER_ID) {
        Supplier componentRight = new Supplier();
        return new ArrayList<Employee>();
    }





    @Operation(summary = "4????????????????????????")
    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/invitations-employee", produces = "application/json")
    public Invitation ????????????(@PathVariable long SUPPLIER_ID, @RequestBody InvitationEmployeePojo employerPojo) {


        Optional<Supplier> supplier = supplierRepository.findById(SUPPLIER_ID);
        if(supplier.isPresent()){
            Invitation componentRight = invitationService.inviteEmployee(supplier,employerPojo);
            return componentRight;
        }else{
            System.out.println("????????????");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }
    }


    @Operation(summary = "4???????????????")
    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/invitations-partner", produces = "application/json")
    public Invitation ????????????(@PathVariable long SUPPLIER_ID, @RequestBody InvitationPartnerPojo pojo) {


        Optional<Supplier> supplier = supplierRepository.findById(SUPPLIER_ID);
        if(supplier.isPresent()){

            Invitation componentRight = invitationService.invitePartner(supplier,pojo);
            return componentRight;
        }else{
            System.out.println("????????????");
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



    @Operation(summary = "1?????????????????????")
    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/settle_accounts", produces = "application/json")
    public SettleAccount createSettleAccounts(@PathVariable long SUPPLIER_ID, SettleAccountPojo pojo) {


        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);

        if(optionalSupplier.isPresent()){
            SettleAccount settleAccount = settleAccountService.add(optionalSupplier.get(),pojo);

        }

        SettleAccount componentRight = new SettleAccount();
        return componentRight;
    }









/*    @Operation(summary = "3???list????????????")
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

    @Operation(summary = "3???list????????????")
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











    @Operation(summary = "3???list????????????")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/summary", produces = "application/json")
    public ResponseEntity<SummarySupplierResp> getRedemptionEntry(@PathVariable long SUPPLIER_ID) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);

        if(optionalSupplier.isPresent()){

            List<RedemptionEntry> clainQuotas = redemptionEntryRepository.findAllBySupplier(optionalSupplier.get().getId());

  /*          Map<Long, Integer> ???????????????????????? = clainQuotas.stream()
                    .collect(groupingBy(RedemptionEntry::getCampaign, summingInt(RedemptionEntry::getGift_amount)));
*/


            List<PublicationEntry> publicationEntryList = publicationEntryRepository.findAll();
            Map<Long, Long> ???????????????????????????????????? = publicationEntryList.stream()
                    .collect(groupingBy(PublicationEntry::getCampaign_id,Collectors.counting() ));



            Map<Long, Triplet<Long,Long,Long>> ???????????????????????????????????? = clainQuotas.stream()
                    .collect(groupingBy(RedemptionEntry::getCampaign, collectingAndThen(toList(), list -> {
                        long count = list.stream()
                                .map(x->x.getSupplier())
                                .collect(counting());
       /*                 String titles = list.stream()
                                .map(BlogPost::getTitle)
                                .collect(joining(" : "));
                        IntSummaryStatistics summary = list.stream()
                                .collect(summarizingInt(BlogPost::getLikes));*/

                        Long cou = ????????????????????????????????????.get(list.get(0).getCampaign());
                        if(cou!= null){
                            return Triplet.with(count, 1L,cou);
                        }else{
                            return Triplet.with(count, 1L,0L);
                        }

                    })));









            SummarySupplierResp summarySupplierRedemptionResp =null;// SummarySupplierResp.from(????????????????????????????????????);
            return ResponseEntity.ok(summarySupplierRedemptionResp);
        }

        throw new RuntimeException();
    }
}