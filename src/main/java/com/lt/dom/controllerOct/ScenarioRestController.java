package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.CampaignResp;
import com.lt.dom.OctResp.ScenarioResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.error.Missing_documentException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ScenarioAddCampaignReq;
import com.lt.dom.otcReq.ScenarioAssignmentReq;
import com.lt.dom.otcReq.ScenarioReq;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class ScenarioRestController {


    @Autowired
    private TempDocumentRepository tempDocumentRepository;

    @Autowired
    private ScenarioRepository scenarioRepository;

    @Autowired
    private CampaignServiceImpl campaignService;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ScenarioAssignmentRepository scenarioAssignmentRepository;
    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private ScenarioServiceImpl scenarioService;
    @Autowired
    private FileStorageService fileStorageService;


    @GetMapping(value = "scenarios/{Scenario_ID}", produces = "application/json")
    public EntityModel<ScenarioResp> getScenario(@PathVariable long Scenario_ID) {

        Optional<Scenario> validatorOptional = scenarioRepository.findById(Scenario_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Scenario_ID,"找不到 该场景 ");
        }


            Scenario scenario =  campaignService.getScenario(validatorOptional.get());


            Map<EnumDocumentType,List<String>> document = fileStorageService.loadDocuments(
                    Arrays.asList(EnumDocumentType.scenario_image,
                            EnumDocumentType.scenario_image_small),scenario.getId());

            ScenarioResp scenarioResp =  ScenarioResp.from(scenario,document);


            EntityModel entityModel = EntityModel.of(scenarioResp);
            entityModel.add(linkTo(methodOn(CampaignRestController.class).getCampaignList(null,null,null)).withRel("getCampaigns"));

            Page<Campaign> campaignPageable = campaignRepository.findAllByScenario(scenario.getId(),PageRequest.of(0,1000));

        //    List<CampaignResp> campaignResps =  CampaignResp.pageMapToListSimple(campaignPageable).getContent();

        List<EntityModel<CampaignResp>> campaignResps = campaignPageable.map(x->{

            EntityModel<CampaignResp> entityModel1 =
                    EntityModel.of(CampaignResp.simple(Pair.with(x,Optional.empty())));

            entityModel1.add(linkTo(methodOn(BookingRestController.class).立即下单(x.getId(),null)).withRel("clainWithPay"));
            entityModel1.add(linkTo(methodOn(PublicationRestController.class).createPublication(x.getId(),null,null)).withRel("clain"));

            entityModel1.add(linkTo(methodOn(ScenarioRestController.class).getCampaign(x.getId())).withSelfRel());

            return entityModel1;
        }).getContent();

            scenarioResp.setCampaigns(campaignResps);

            return entityModel;





    }

    @GetMapping(value = "/scenarios/campaigns/{CAMPAIGN_ID}", produces = "application/json")
    public ResponseEntity<EntityModel> getCampaign(@PathVariable long CAMPAIGN_ID) {

        Optional<Campaign> validatorOptional = campaignRepository.findById(CAMPAIGN_ID);
        if(validatorOptional.isEmpty()) {

            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 活动"+CAMPAIGN_ID);
        }
        Campaign campaign = validatorOptional.get();

                CampaignResp campaignResp = CampaignResp.simple(Pair.with(campaign,Optional.empty()));

                EntityModel entityModel = EntityModel.of(campaignResp);
        entityModel.add(linkTo(methodOn(ScenarioRestController.class).getCampaign(campaign.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(BookingRestController.class).立即下单(campaign.getId(),null)).withRel("clainWithPay"));
        entityModel.add(linkTo(methodOn(PublicationRestController.class).createPublication(campaign.getId(),null,null)).withRel("clain"));

        return ResponseEntity.ok(entityModel);

    }


    @GetMapping(value = "/scenarios", produces = "application/json")
    public ResponseEntity<Page<ScenarioResp>> pageScenario(Pageable pageable) {

        Page<Scenario> validatorOptional = scenarioRepository.findAll(pageable);

        return ResponseEntity.ok(validatorOptional.map(x->scenarioService.toScenarioResp(x)));

    }



    @PostMapping(value = "/scenarios", produces = "application/json")
    public ResponseEntity<ScenarioResp> createScenario(@RequestBody @Valid ScenarioReq scenarioReq) {


        scenarioReq.getImage();


        Optional<TempDocument> tempDocument_image = tempDocumentRepository.findByCode(scenarioReq.getImage());
        Optional<TempDocument> tempDocument_small = tempDocumentRepository.findByCode(scenarioReq.getImage_small());
        if(tempDocument_image.isEmpty()  || tempDocument_small.isEmpty() ){
            throw new Missing_documentException(1,Scenario.class.getSimpleName(),"需要附上申请文档， 合同，合影照片，保险单和发票");

        }






        Scenario scenario =  campaignService.createScenario(scenarioReq);
        List<Document> documents = fileStorageService.saveFromTempDocumentList(scenario.getId(),
                Arrays.asList(Pair.with(EnumDocumentType.scenario_image,tempDocument_image.get()),
                Pair.with(EnumDocumentType.scenario_image_small,tempDocument_small.get())));


        Map<EnumDocumentType,List<String>> document = fileStorageService.loadDocuments(
                Arrays.asList(EnumDocumentType.scenario_image,
                EnumDocumentType.scenario_image_small),scenario.getId());
        return ResponseEntity.ok(ScenarioResp.from(scenario,document));





/*        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));*/



    }




    //应用场景 中 添加 供应商
    @PostMapping(value = "/scenarios/{SCENARIO_ID}/assignments", produces = "application/json")
    public ResponseEntity<ScenarioAssignment> addScenarioAssignment(@PathVariable long SCENARIO_ID, @RequestBody ScenarioAssignmentReq scenarioReq) {




        Optional<Scenario> optional = scenarioRepository.findById(SCENARIO_ID);
        Optional<Supplier> optionalSupplier = supplierRepository.findById(scenarioReq.getSupplier().longValue());
        if(optional.isPresent() && optionalSupplier.isPresent()) {
            throw new BookNotFoundException(SCENARIO_ID,"找不到 该场景 ");
        }

        List<ScenarioAssignment> scenarioAssignments = scenarioAssignmentRepository.findByScenarioAndSupplier(optional.get().getId(),optionalSupplier.get().getId());
        if(!scenarioAssignments.isEmpty()){
            throw new ExistException(Enumfailures.general_exists_error,"已经存在了");
        }
        ScenarioAssignment scenarioAssignment =  campaignService.addScenarioAssignment(optional.get(),optionalSupplier.get(),scenarioReq.getNote());
        return ResponseEntity.ok(scenarioAssignment);




/*        "related_object_id": "v_tQnP9G78q2s7GU1P74WXehorBeRm17K2",
                "related_object_type": "voucher",
                "created_at": "2018-12-20T09:54:44Z",
                "updated_at": null,*/



/*        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));*/



    }




    @PostMapping(value = "/scenarios/{SCENARIO_ID}/campaigns", produces = "application/json")
    public ResponseEntity<Campaign> addScenarioCampaign(@PathVariable long SCENARIO_ID, @RequestBody ScenarioAddCampaignReq scenarioReq) {




        Optional<Scenario> optional = scenarioRepository.findById(SCENARIO_ID);
        System.out.println("抛出异常"+optional.get().getId());
        Optional<Campaign> optionalCampaign = campaignRepository.findById(scenarioReq.getCampaign());
        System.out.println("抛出异常"+optionalCampaign.get().getId());
        if(optional.isPresent() && optionalCampaign.isPresent()) {
            throw new BookNotFoundException(SCENARIO_ID,"找不到 该场景 ");

        }
        if(optionalCampaign.get().getScenario() == optional.get().getId() ){
            throw new ExistException(Enumfailures.general_exists_error,"已经存在了");
        }
        Campaign scenarioAssignment =  campaignService.addScenarioCampaign(optional.get(),optionalCampaign.get());
        return ResponseEntity.ok(scenarioAssignment);


/*        "related_object_id": "v_tQnP9G78q2s7GU1P74WXehorBeRm17K2",
                "related_object_type": "voucher",
                "created_at": "2018-12-20T09:54:44Z",
                "updated_at": null,*/



/*        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));*/



    }
}