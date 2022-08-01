package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.ExportResp;
import com.lt.dom.OctResp.ScenarioResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ScenarioAddCampaignReq;
import com.lt.dom.otcReq.ScenarioAssignmentReq;
import com.lt.dom.otcReq.ScenarioReq;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.CampaignServiceImpl;
import com.lt.dom.serviceOtc.ScenarioServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class ScenarioRestController {


    @Autowired
    private ProductRepository productRepository;

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


    @GetMapping(value = "scenarios/{Scenario_ID}", produces = "application/json")
    public Scenario getScenario(@PathVariable long Scenario_ID) {

        Optional<Scenario> validatorOptional = scenarioRepository.findById(Scenario_ID);
        if(validatorOptional.isPresent()){
            try {
                return campaignService.getScenario(validatorOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));



    }


    @GetMapping(value = "/scenarios", produces = "application/json")
    public ResponseEntity<Page<ScenarioResp>> pageScenario(Pageable pageable) {

        Page<Scenario> validatorOptional = scenarioRepository.findAll(pageable);

        return ResponseEntity.ok(validatorOptional.map(x->scenarioService.toScenarioResp(x)));

    }



    @PostMapping(value = "/scenarios", produces = "application/json")
    public Scenario createScenario(@RequestBody ScenarioReq scenarioReq) {


        return campaignService.createScenario(scenarioReq);

/*        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));*/



    }




    //应用场景 中 添加 供应商
    @PostMapping(value = "/scenarios/{SCENARIO_ID}/assignments", produces = "application/json")
    public ResponseEntity<ScenarioAssignment> addScenarioAssignment(@PathVariable long SCENARIO_ID, @RequestBody ScenarioAssignmentReq scenarioReq) {




        Optional<Scenario> optional = scenarioRepository.findById(SCENARIO_ID);
        Optional<Supplier> optionalSupplier = supplierRepository.findById(scenarioReq.getSupplier().longValue());
        if(optional.isPresent() && optionalSupplier.isPresent()){
            List<ScenarioAssignment> scenarioAssignments = scenarioAssignmentRepository.findByScenarioAndSupplier(optional.get().getId(),optionalSupplier.get().getId());
            if(!scenarioAssignments.isEmpty()){
                throw new ResponseStatusException(
                        HttpStatus.ALREADY_REPORTED, "Foo Not Found", new Exception("DDDDDDDDDD"));
            }
            ScenarioAssignment scenarioAssignment =  campaignService.addScenarioAssignment(optional.get(),optionalSupplier.get(),scenarioReq.getNote());
            return ResponseEntity.ok(scenarioAssignment);
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));

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
        if(optional.isPresent() && optionalCampaign.isPresent()){
            if(optionalCampaign.get().getScenario() == optional.get().getId() ){
                throw new ResponseStatusException(
                        HttpStatus.ALREADY_REPORTED, "Foo Not Found", new Exception("DDDDDDDDDD"));
            }
            Campaign scenarioAssignment =  campaignService.addScenarioCampaign(optional.get(),optionalCampaign.get());
            return ResponseEntity.ok(scenarioAssignment);
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));

/*        "related_object_id": "v_tQnP9G78q2s7GU1P74WXehorBeRm17K2",
                "related_object_type": "voucher",
                "created_at": "2018-12-20T09:54:44Z",
                "updated_at": null,*/



/*        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));*/



    }
}