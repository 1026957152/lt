package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    private AssetServiceImpl assetService;
    @Autowired
    private ValueListServiceImpl valueListService;


    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/campaigns", produces = "application/json"/*produces = MediaType.MULTIPART_FORM_DATA_VALUE*/)
    public ResponseEntity<CampaignResp> createCompaign(@RequestBody @Valid CompaignPojo pojo/*,@RequestPart List<MultipartFile> files*/) {
        Optional<Scenario> scenario = scenarioRepository.findById(pojo.getScenario());
        if(scenario.isEmpty()){

            throw new BookNotFoundException(pojo.getScenario(),"找不到这个应用场景");
        }
        System.out.println("dddddddddd777773333333333333333333333555555555555577777777777777777777777");
        Campaign booking = voucherService.createLoyaltyCompaign(pojo,scenario.get());
        System.out.println("dddddddddd77777554444444444444444444444444444447777777777");
        List<Document> fileNames_ = new ArrayList<Document>();
/*        files.stream().forEach(x->{
            Document document = fileStorageService.saveWithDocument(booking.getId(), EnumDocumentType.estimate, x);
            fileNames_.add(DocumentResp.onefrom(document));
        });*/
        List<Quota> Quota = Arrays.asList();


        System.out.println("dddddddddd77777777777777777777777777777777777");
        CampaignResp campaignResp =  CampaignResp.from(Quartet.with(booking,scenario, Quota,fileNames_));
        System.out.println("dddddddddd7777777777775555555555555555555555555577777777777777777777777");


        EntityModel<AssetResp> asset = assetService.getQr(booking);
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






    @GetMapping(value = "/campaigns/{CAMPAIGN_ID}", produces = "application/json")
    public ResponseEntity<CampaignResp> getCampaign(@PathVariable long CAMPAIGN_ID) {

        Optional<Campaign> validatorOptional = campaignRepository.findById(CAMPAIGN_ID);
        if(validatorOptional.isPresent()){

            List<Quota> quotas = quotaRepository.findByCompaign(validatorOptional.get().getId());
            try {

                Optional<Scenario> optional = scenarioRepository.findById(validatorOptional.get().getScenario());

                List<Document> documents = documentRepository.findAllByRaletiveId(validatorOptional.get().getId());
                CampaignResp campaignResp =  CampaignResp.from(Quartet.with(validatorOptional.get(),optional,quotas,documents));
                return ResponseEntity.ok(campaignResp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));


    }


    @GetMapping(value = "/campaigns_", produces = "application/json")
    public ResponseEntity<Page<CampaignResp>> pageCampaign( Pageable pageable, PagedResourcesAssembler<EntityModel<RedemptionEntryResp>> assembler) {

        Page<Campaign> campaignPageable = campaignRepository.findAll(pageable);




        Page<CampaignResp> campaignResps =  CampaignResp.pageMap(campaignPageable);
        return ResponseEntity.ok(campaignResps);


    }
    @GetMapping(value = "/campaigns", produces = "application/json")
    public ResponseEntity pageCampaign(@RequestParam(value = "scenario", required = true) long scenario, Pageable pageable, PagedResourcesAssembler<CampaignResp> assembler) {

        Page<Campaign> campaignPageable = campaignRepository.findAll(pageable);




        Page<CampaignResp> campaignResps =  CampaignResp.pageMap(campaignPageable);
        return ResponseEntity.ok(assembler.toModel(campaignResps));


    }


    @GetMapping(value = "/campaigns/list", produces = "application/json")
    public ResponseEntity<List<CampaignResp>> listCampaign(@RequestParam(value = "scenario", required = true) long scenario) {

        Page<Campaign> campaignPageable = campaignRepository.findAll(PageRequest.of(0,1000));

        List<CampaignResp> campaignResps =  CampaignResp.pageMapToList(campaignPageable);

        return ResponseEntity.ok(campaignResps);


    }



    @PostMapping(value = "/campaigns/{CAMPAIGN_ID}/quotas", produces = "application/json")
    public ResponseEntity<Quota> createQuota(@PathVariable long CAMPAIGN_ID, @RequestBody @Valid QuotaReq clainQuotaReq) {


        System.out.println("----"+CAMPAIGN_ID);
        Optional<Campaign> optional = campaignRepository.findById(CAMPAIGN_ID);


        if(optional.isEmpty()) {
            throw new BookNotFoundException(CAMPAIGN_ID,"找活动对象");
        }
            System.out.println("----"+optional.get());

            Quota campaignResps =  clainQuotaService.createQuota(optional.get(),clainQuotaReq);
            return ResponseEntity.ok(campaignResps);



    }





    @GetMapping(value = "/campaigns/{CAMPAIGN_ID}/quotas", produces = "application/json")
    public ResponseEntity<Page<Quota>> pageQuota(@PathVariable long CAMPAIGN_ID, Pageable pageable) {


        System.out.println("----"+CAMPAIGN_ID);
        Optional<Campaign> optional = campaignRepository.findById(CAMPAIGN_ID);


        if(optional.isPresent()){
            System.out.println("----"+optional.get());
            Page<Quota> campaignResps =  voucherService.pageQuota(optional.get(),pageable);
            return ResponseEntity.ok(campaignResps);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));


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
}