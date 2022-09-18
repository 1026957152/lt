package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.util.CodeConfig;
import com.lt.dom.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class CampaignRestController {



    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private QuotaRepository quotaRepository;
    @Autowired
    private AuthenticationFacade authenticationFacade;
    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private VoucherServiceImpl voucherService;
    @Autowired
    private ClainQuotaServiceImpl clainQuotaService;
    @Autowired
    private ScenarioRepository scenarioRepository;

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private ValueListRepository valueListRepository;


    @Autowired
    private AssetServiceImpl assetService;
    @Autowired
    private ValueListServiceImpl valueListService;

    @Autowired
    private CampaignServiceImpl campaignService;


    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    CustomerMapper mapper;

    @GetMapping(value = "/campaigns/page", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_createCampaign() {

        List<Scenario> scenarioList = scenarioRepository.findAll();

        EntityModel entityModel = EntityModel.of(Map.of("voucher_type_list", Arrays.asList(EnumVoucherType.DISCOUNT_VOUCHER).stream().map(x->{

                    EnumResp enumResp = new EnumResp();
                    if(x.equals(EnumVoucherType.DISCOUNT_VOUCHER)){
                        enumResp.setSubitems(Arrays.asList(EnumDiscountVoucherCategory.AMOUNT).stream().map(x_dis->{
                            EnumResp enumResp_dis = new EnumResp();
                            enumResp_dis.setId(x_dis.name());
                            enumResp_dis.setText(x_dis.toString());
                            return enumResp_dis;
                        }).collect(Collectors.toList()));
                    }
                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
                }).collect(Collectors.toList()),
                "scenario_list", scenarioList.stream().map(x->{
                    EnumLongIdResp enumResp = new EnumLongIdResp();
                    enumResp.setId(x.getId());
                    enumResp.setText(x.getName());
                    return enumResp;
                }).collect(Collectors.toList()),
                "category_list", Arrays.asList(EnumDiscountVoucherCategory.AMOUNT).stream().map(x->{
                    EnumResp enumResp = new EnumResp();
                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
                }).collect(Collectors.toList())));

        entityModel.add(linkTo(methodOn(DocumentRestController.class).createDocuments(null)).withRel("upload_bussiness_license_url"));
        return entityModel;
    }





    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/campaigns", produces = "application/json"/*produces = MediaType.MULTIPART_FORM_DATA_VALUE*/)
    public ResponseEntity<CampaignResp> createCampaign(@RequestBody @Valid CompaignPojo pojo/*,@RequestPart List<MultipartFile> files*/) {
        Optional<Scenario> scenario = scenarioRepository.findById(pojo.getScenario());
        if(scenario.isEmpty()){

            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到这个应用场景");
        }
        System.out.println("dddddddddd777773333333333333333333333555555555555577777777777777777777777");
       // Campaign booking = voucherService.createLoyaltyCompaign(pojo,scenario.get());


        Campaign campaign = campaignService.create(pojo,scenario.get());

        System.out.println("dddddddddd77777554444444444444444444444444444447777777777");
        List<Document> fileNames_ = new ArrayList<Document>();
/*        files.stream().forEach(x->{
            Document document = fileStorageService.saveWithDocument(booking.getId(), EnumDocumentType.estimate, x);
            fileNames_.add(DocumentResp.onefrom(document));
        });*/
        List<Quota> Quota = Arrays.asList();


        System.out.println("dddddddddd77777777777777777777777777777777777");
        CampaignResp campaignResp =  CampaignResp.from(Quartet.with(campaign,scenario, Quota,fileNames_));
        System.out.println("dddddddddd7777777777775555555555555555555555555577777777777777777777777");


        EntityModel<AssetResp> asset = assetService.getQr(campaign);
        campaignResp.setAsset(asset);
        return ResponseEntity.ok(campaignResp);

/*        Optional<Product> optionalProduct = productRepository.findById(pojo.getProductId());

        if(optionalProduct.isPresent()){
            return ResponseEntity.ok(booking);
        }

        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));*/
    }



    @PostMapping("/uploadFileWithAddtionalData")
    public ResponseEntity submit(
            @RequestParam MultipartFile file) {

        return ResponseEntity.ok("fileUploadView");
    }

    @PostMapping(value = "/campaigns/{CAMPAIGN_ID}/documents")
    public ResponseEntity createDocuments(@PathVariable long CAMPAIGN_ID,@RequestParam("files") List<MultipartFile> files) {
        //@RequestParam("files") List<MultipartFile> files
        Optional<Campaign> validatorOptional = campaignRepository.findById(CAMPAIGN_ID);
        if(validatorOptional.isPresent()){

            List<String> fileNames = files.stream().map(x->x.getOriginalFilename()).collect(Collectors.toList());


            try {

                List<String> fileNames_ = new ArrayList<>();
                files.stream().forEach(x->{
                    Document document = fileStorageService.saveWithDocument(validatorOptional.get().getId(), EnumDocumentType.campaign_logo, x);
                    fileNames_.add(x.getOriginalFilename());
                });

                return ResponseEntity.ok(new MessageFile("Upload file successfully: "+fileNames_));
            }catch (Exception e){

                e.printStackTrace();
                return ResponseEntity.badRequest()
                        .body(new MessageFile("Could not upload the file:"+fileNames));
            }
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
    }


    private Map<String,Object> function_Page_getCampaign( Campaign campaign) {



        List<Supplier> suppliers = supplierRepository.findAll();

        List<Scenario> scenarioList = scenarioRepository.findAll();



        List<ValueList> valueLists = valueListRepository.findAllByType(EnumValueListType.Vendor_groups);

        Map m = Map.of(

                "quota_type_list", Arrays.stream(EnumQuotaType.values()).map(x->{

                    EnumResp enumResp = new EnumResp();
                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());

                    if(x.equals(EnumQuotaType.NominatedSupplier)){

                        List<EnumLongIdResp> sup_enumResps = suppliers.stream().map(supplier->{
                            EnumLongIdResp enumResp_sup = new EnumLongIdResp();
                            enumResp_sup.setId(supplier.getId());
                            enumResp_sup.setText(supplier.getName()+"_"+supplier.getCode());
                            return enumResp_sup;
                        }).collect(Collectors.toList());
                        enumResp.setSubitems(sup_enumResps);
                    }
                    if(x.equals(EnumQuotaType.Scenario)){
                        List<EnumLongIdResp> sup_enumResps = scenarioList.stream().map(supplier->{
                            EnumLongIdResp enumResp_sup = new EnumLongIdResp();
                            enumResp_sup.setId(supplier.getId());
                            enumResp_sup.setText(supplier.getName()+"_"+supplier.getCode());
                            return enumResp_sup;
                        }).collect(Collectors.toList());
                        enumResp.setSubitems(sup_enumResps);
                    }
                    if(x.equals(EnumQuotaType.supplier_groups)){


                        List<EnumLongIdResp> sup_enumResps = valueLists.stream().map(supplier->{
                            EnumLongIdResp enumResp_sup = new EnumLongIdResp();
                            enumResp_sup.setId(supplier.getId());
                            enumResp_sup.setText(supplier.getName()+"_"+supplier.getCode());
                            return enumResp_sup;
                        }).collect(Collectors.toList());
                        enumResp.setSubitems(sup_enumResps);
                    }

                    return enumResp;
                }).collect(Collectors.toList()),
                "status_list", Arrays.stream(EnumCampaignStatus.values()).map(x->{

                    EnumResp enumResp = new EnumResp();
                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
                }).collect(Collectors.toList())
                );

        return m;
    }



    @GetMapping(value = "/campaigns/{CAMPAIGN_ID}/Page_getCampaign", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_getCampaign(@PathVariable long CAMPAIGN_ID) {

        Optional<Campaign> campaignOptional = campaignRepository.findById(CAMPAIGN_ID);
        if(campaignOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到活动"+CAMPAIGN_ID);
        }
        Campaign campaign = campaignOptional.get();

        EntityModel entityModel = EntityModel.of(function_Page_getCampaign(campaign));

        entityModel.add(linkTo(methodOn(CampaignRestController.class).getCampaign(campaign.getId())).withRel("getCampaign"));

        return entityModel;
    }


    @GetMapping(value = "/campaigns/{CAMPAIGN_ID}", produces = "application/json")
    public ResponseEntity<EntityModel<CampaignResp>> getCampaign(@PathVariable long CAMPAIGN_ID) {

        Optional<Campaign> validatorOptional = campaignRepository.findById(CAMPAIGN_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到活动"+CAMPAIGN_ID);
        }

        Campaign campaign = validatorOptional.get();
        List<Quota> quotas = quotaRepository.findByCompaign(campaign.getId());

        Optional<Scenario> optional = scenarioRepository.findById(campaign.getScenario());





        List<Document> documents = documentRepository.findAllByRaletiveId(campaign.getId());
        CampaignResp campaignResp =  CampaignResp.from(Quartet.with(campaign,optional,quotas,documents));

        campaignResp.setParameters(function_Page_getCampaign(campaign));
        campaignResp.setSummary(campaignService.getSummary(campaign));
        EntityModel entityModel = EntityModel.of(campaignResp);
        entityModel.add(linkTo(methodOn(CampaignRestController.class).getQuotaList(campaign.getId(),null,null)).withRel("getQuoteList"));

        entityModel.add(linkTo(methodOn(CampaignRestController.class).createQuotaClaim(campaign.getId(),null)).withRel("createClaimQuota"));
        entityModel.add(linkTo(methodOn(CampaignRestController.class).createQuotaRedeem(campaign.getId(),null)).withRel("createRedeemQuota"));

        entityModel.add(linkTo(methodOn(CampaignRestController.class).editCampaign(campaign.getId(),null)).withRel("editCampaign"));


        return ResponseEntity.ok(entityModel);

    }




    @GetMapping(value = "/campaigns/Page_getCampaignList", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_getCampaignList() {
        List<Scenario> scenarioList = scenarioRepository.findAll();

        EntityModel entityModel = EntityModel.of(Map.of("status_list", Arrays.stream(EnumCampaignStatus.values()).map(x->{

                    EnumResp enumResp = new EnumResp();
                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
                }).collect(Collectors.toList())));

        entityModel.add(linkTo(methodOn(CampaignRestController.class).getCampaignList(null,null)).withRel("getCampaignList"));
        return entityModel;
    }

    @GetMapping(value = "/campaigns_", produces = "application/json")
    public ResponseEntity<Page<EntityModel<CampaignResp>>> getCampaignList(Pageable pageable, PagedResourcesAssembler<EntityModel<CampaignResp>> assembler) {

        Page<Campaign> campaignPageable = campaignRepository.findAll(pageable);


        Page<EntityModel<CampaignResp>> campaignResps =  campaignPageable.map(x->{
            EntityModel entityModel = EntityModel.of(CampaignResp.getCampaign(Triplet.with(x,Optional.empty(), Arrays.asList())));

            entityModel.add(linkTo(methodOn(CampaignRestController.class).deleteCampaign(x.getId())).withRel("deleteCampaign"));
          //  entityModel.add(linkTo(methodOn(CampaignRestController.class).clain(x.getId(),null)).withRel("clainWithPay"));
            entityModel.add(linkTo(methodOn(PublicationRestController.class).createPublication(x.getId(),null,null)).withRel("clain"));

            return entityModel;

        });
        return ResponseEntity.ok(campaignResps);


    }




    @GetMapping(value = "/campaigns", produces = "application/json")
    public ResponseEntity getCampaignList(@RequestParam(value = "scenario", required = false) Long scenario,
                                        //  @PageableDefault(sort = {"active"}, direction = Sort.Direction.DESC)
                                          @SortDefault(sort = "active", direction = Sort.Direction.ASC) Pageable pageable,
                                          PagedResourcesAssembler<EntityModel<CampaignResp>> assembler) {

        Page<Campaign> campaignPageable = campaignRepository.findAll(pageable);


        Page<EntityModel<CampaignResp>> campaignResps =  campaignPageable.map(x->{
          EntityModel entityModel = EntityModel.of(CampaignResp.getCampaign(Triplet.with(x,Optional.empty(), Arrays.asList())));

           entityModel.add(linkTo(methodOn(CampaignRestController.class).deleteCampaign(x.getId())).withRel("deleteCampaign"));
          //  entityModel.add(linkTo(methodOn(CampaignRestController.class).clain(x.getId(),null)).withRel("clainWithPay"));
            entityModel.add(linkTo(methodOn(PublicationRestController.class).createPublication(x.getId(),null,null)).withRel("clain"));
            entityModel.add(linkTo(methodOn(CampaignRestController.class).getCampaign(x.getId())).withRel("getCampaign"));
            entityModel.add(linkTo(methodOn(CampaignRestController.class).Page_getCampaign(x.getId())).withRel("Page_getCampaign"));

            return entityModel;

        });

        return ResponseEntity.ok(assembler.toModel(campaignResps));


    }


    @GetMapping(value = "/campaigns/list", produces = "application/json")
    public ResponseEntity<List<CampaignResp>> listCampaign(@RequestParam(value = "scenario", required = true) long scenario) {

        Page<Campaign> campaignPageable = campaignRepository.findAll(PageRequest.of(0,1000));

        List<CampaignResp> campaignResps =  CampaignResp.pageMapToListSimple(campaignPageable).getContent();

        return ResponseEntity.ok(campaignResps);


    }


    @DeleteMapping(value = "/campaigns/{CAMPAIGN_ID}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable long CAMPAIGN_ID) {
        System.out.println("----"+CAMPAIGN_ID);
        Optional<Campaign> optional = campaignRepository.findById(CAMPAIGN_ID);


        if(optional.isEmpty()) {
            throw new BookNotFoundException(CAMPAIGN_ID,"找活动对象");
        }

        campaignRepository.delete(optional.get());
        return ResponseEntity.ok().build();
    }







    @PostMapping(value = "/campaigns/{CAMPAIGN_ID}/claim_quotas", produces = "application/json")
    public ResponseEntity<Quota> createQuotaClaim(@PathVariable long CAMPAIGN_ID, @RequestBody @Valid QuotaReq clainQuotaReq) {

        Optional<Campaign> optional = campaignRepository.findById(CAMPAIGN_ID);
        if(optional.isEmpty()) {
            String formatted = String.format("找不到活动 %s!，请新建活动", CAMPAIGN_ID);
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找活动对象",formatted);
        }

        System.out.println("----"+optional.get());

        Quota campaignResps =  clainQuotaService.createQuota(optional.get(), EnumQuotaClaimOrRedeem.claim,clainQuotaReq);
        return ResponseEntity.ok(campaignResps);

    }



    @PostMapping(value = "/campaigns/{CAMPAIGN_ID}/redeam_quotas", produces = "application/json")
    public ResponseEntity<Quota> createQuotaRedeem(@PathVariable long CAMPAIGN_ID, @RequestBody @Valid QuotaReq clainQuotaReq) {


        System.out.println("----"+CAMPAIGN_ID);
        Optional<Campaign> optional = campaignRepository.findById(CAMPAIGN_ID);
        if(optional.isEmpty()) {
            String formatted = String.format("找不到活动 %s!，请新建活动", CAMPAIGN_ID);
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找活动对象",formatted);
        }

        System.out.println("----"+optional.get());
        Quota campaignResps =  clainQuotaService.createQuota(optional.get(), EnumQuotaClaimOrRedeem.redeem,clainQuotaReq);
        return ResponseEntity.ok(campaignResps);

    }



    @GetMapping(value = "/campaigns/{CAMPAIGN_ID}/quotas", produces = "application/json")
    public ResponseEntity<PagedModel> getQuotaList(@PathVariable long CAMPAIGN_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<QuotaReq>> assembler) {


        System.out.println("----"+CAMPAIGN_ID);
        Optional<Campaign> optional = campaignRepository.findById(CAMPAIGN_ID);
        if(optional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到活动","请新建活动");
        }
        System.out.println("----"+optional.get());
        Page<Quota> campaignResps =  voucherService.pageQuota(optional.get(),pageable);
        return ResponseEntity.ok(assembler.toModel(campaignResps.map(x->{
            QuotaReq quotaReq = QuotaReq.from(x);
            return EntityModel.of(quotaReq);
        })));
    }






    @GetMapping(value = "/campaigns/{CAMPAIGN_ID}/summary", produces = "application/json")
    public ResponseEntity<ClainQuotaStatisticsResp> getQuotaStatistics(@PathVariable long CAMPAIGN_ID) {


        System.out.println("----"+CAMPAIGN_ID);
        Optional<Campaign> optional = campaignRepository.findById(CAMPAIGN_ID);

        if(optional.isPresent()){
            System.out.println("----"+optional.get());
            ClainQuotaStatisticsResp campaignResps =  voucherService.getQuotaStatistics(optional.get());
            return ResponseEntity.ok(campaignResps);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));

    }



/*


    @PostMapping(value = "/campaigns/{CAMPAIGN_ID}/clain", produces = "application/json")
    public ResponseEntity<Quota> clain(@PathVariable long CAMPAIGN_ID, @RequestBody QuotaReq clainQuotaReq) {

        System.out.println("----"+CAMPAIGN_ID);
        Optional<Campaign> optional = campaignRepository.findById(CAMPAIGN_ID);


        if(optional.isPresent()){
            System.out.println("----"+optional.get());

            Quota campaignResps =  clainQuotaService.createQuota(optional.get(),clainQuotaReq);
            return ResponseEntity.ok(campaignResps);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));


    }
*/





    @PostMapping(value = "/campaigns/{CAMPAIGN_ID}/clain_redeem_rule", produces = "application/json")
    public ResponseEntity<List<ValueListItem>> clainAndRedeemRule(@PathVariable long CAMPAIGN_ID,@RequestBody ClainRedeemAssignmentReq clainQuotaReq) {



        System.out.println("----"+CAMPAIGN_ID);
        Optional<Campaign> optional = campaignRepository.findById(CAMPAIGN_ID);


        if(optional.isEmpty()) {
            throw new BookNotFoundException("Foo Not Found", "找不到rule");
        }
            System.out.println("----"+optional.get());

        List<ValueListItem> campaignResps =  valueListService.addClainRedeem(optional.get(),clainQuotaReq);

            return ResponseEntity.ok(campaignResps);

    }













    @Operation(summary = "2、下单购买")
    @PutMapping(value = "/campaigns/{CAMPAIGN_ID}", produces = "application/json")
    public ResponseEntity<CampaignResp> editCampaign(@PathVariable long CAMPAIGN_ID, @RequestBody @Valid CompaignEditPojo pojo) {


        UserVo userVo = authenticationFacade.getUserVo(authenticationFacade.getAuthentication());


        Optional<Campaign> optional = campaignRepository.findById(CAMPAIGN_ID);
        if(optional.isEmpty()) {
            throw new BookNotFoundException("Foo Not Found", "找不到rule");
        }


        Campaign campaign = optional.get();
/*
        Campaign campaign_ = new Campaign();
        mapper.updateCustomerFromDto(pojo,campaign_);
*/


/*
        campaign.setActive(campaign_.isActive());
        campaign.setPay(campaign_.getPay());
        campaign.setPayAmount(campaign_.getPayAmount());
*/




        if(!ObjectUtils.isEmpty(pojo.isPay())){
            campaign.setPay(pojo.isPay());
            if(!ObjectUtils.isEmpty(pojo.getPay_amount())){
                campaign.setPayAmount(pojo.getPay_amount());
            }else{
                throw new RuntimeException("验证异常");
            }

        }
        if(!ObjectUtils.isEmpty(pojo.isActive())){
            campaign.setActive(pojo.isActive());
        }

        campaign = campaignService.edit(campaign,pojo,userVo);


        CampaignResp campaignResp =  CampaignResp.from(campaign);

        return ResponseEntity.ok(campaignResp);

    }


}