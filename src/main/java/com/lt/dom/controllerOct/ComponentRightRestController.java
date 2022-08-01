package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.ComponentRightResp;


import com.lt.dom.oct.*;
import com.lt.dom.otcReq.AccessValidatorPojo;
import com.lt.dom.otcReq.ComponentRightPojo;
import com.lt.dom.otcenum.EnumValidatorRedemExtent;
import com.lt.dom.repository.AccessValidatorRepository;
import com.lt.dom.repository.ComponentRightRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.repository.ValidatorRepository;
import com.lt.dom.serviceOtc.ComponentRightServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class ComponentRightRestController {

    @Autowired
    private ComponentRightRepository componentRightRepository;
    @Autowired
    private ValidatorRepository validatorRepositorya;


    @Autowired
    private AccessValidatorRepository accessValidatorRepository;

    @Autowired
    private ComponentRightServiceImpl componentRightService;

    @Autowired
    private SupplierRepository supplierRepository;



    @GetMapping(value = "/componet_rights/{COMPONENT_RIGHT_ID}/validators/{VALIDATOR_ID}", produces = "application/json")
    public Validator getComponentRight_Validator(@PathVariable int COMPONENT_RIGHT_ID, @PathVariable int VALIDATOR_ID) {

        AccessValidator probe = new AccessValidator();
        probe.setRalativeId(COMPONENT_RIGHT_ID);
        probe.setValidatorId(VALIDATOR_ID);
        probe.setExtend(EnumValidatorRedemExtent.特定权益券);
        Example<AccessValidator> example = Example.of(probe);
        Optional<AccessValidator> accessValidator = accessValidatorRepository.findOne(example);

        Optional<Validator> validatorOptional = validatorRepositorya.findById(accessValidator.get().getValidatorId());


        return validatorOptional.get();
    }


    @Operation(summary = "1、新建权益")

    @PostMapping(value = "/supliers/{SUPPLIER_ID}/component_rights", produces = "application/json")
    public ComponentRightResp createComponentRight(@PathVariable long SUPPLIER_ID,@RequestBody ComponentRightPojo pojo) {


        Optional<Supplier> optionalComponentRight = supplierRepository.findById(SUPPLIER_ID);

        if(optionalComponentRight.isPresent()){
            ComponentRight componentRight = componentRightService.createComponentRight(optionalComponentRight.get(),pojo);
            return ComponentRightResp.from(componentRight);
        }

        throw new RuntimeException();
    }







    @Operation(summary = "1、为权益 添加核验者")
    @PostMapping(value = "/componet_rights/{COMPONENT_RIGHTS_ID}/access_validators", produces = "application/json")
    public AccessValidator addComponentRight_Validator(@PathVariable long COMPONENT_RIGHTS_ID, @RequestBody AccessValidatorPojo pojo) {
        Optional<ComponentRight> optionalComponentRight = componentRightRepository.findById(COMPONENT_RIGHTS_ID);

        AccessValidator accessValidator = componentRightService.addAccessValidator(optionalComponentRight.get(),pojo);

        return accessValidator;
    }


    @Operation(summary = "1、查询权益")
    @GetMapping(value = "/supliers/{SUPPLIER_ID}/component_rights", produces = "application/json")
    public List<ComponentRight> listComponentRight(@PathVariable long SUPPLIER_ID) {



        ComponentRight probe = new ComponentRight();
        probe.setSupplierId(SUPPLIER_ID);
        Example<ComponentRight> example = Example.of(probe);

        List<ComponentRight> componentRights = componentRightRepository.findAll(example);

        return componentRights;
    }





    @Operation(summary = "1、更新权益信息")
    @PutMapping(value = "/{APP_ID}/component_right/{COMPONENT_RIGHT_ID}", produces = "application/json")
    public ComponentRight updateComponentRight(@PathVariable int APP_ID, Map  metadata) {
        return null;
    }




    @Operation(summary = "1、删除权益信息")
    @DeleteMapping(value = "/component_rights/{COMPONENT_RIGHT_ID}", produces = "application/json")
    public ComponentRight deleteCouponTemplate(@PathVariable String id) {
        return null;
    }



/*
    @Operation(summary = "1、为权益 增加分润")
    @PostMapping(value = "/componet_rights/{COMPONENT_RIGHTS_ID}/royaltyRules", produces = "application/json")
    public List<RoyaltyRule> addRoyaltyRule(@PathVariable long COMPONENT_RIGHTS_ID, @RequestBody RoyaltyRulePojo pojo) {
        Optional<ComponentRight> optionalComponentRight = componentRightRepository.findById(COMPONENT_RIGHTS_ID);

        List<RoyaltyRule> accessValidator = componentRightService.addRoyaltyRule(optionalComponentRight.get(),pojo);

        return accessValidator;
    }*/

}