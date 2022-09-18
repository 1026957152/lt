package com.lt.dom.controllerOct;

import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ValidatorPojo;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.ComponentRightRepository;
import com.lt.dom.repository.ValidatorRepository;
import com.lt.dom.serviceOtc.ValidatorScanServiceImpl;
import com.lt.dom.serviceOtc.ValidatorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class ValidatorRestController {


    @Autowired
    private ValidatorScanServiceImpl validatorScanService;

    @Autowired
    private ValidatorRepository validatorRepository;

    @Autowired
    private ValidatorServiceImpl validatorService;

    @Autowired
    private ComponentRightRepository componentRightRepository;






    @GetMapping(value = " /{VALIDATOR_ID}/component_rights", produces = "application/json")
    public List<ComponentRight> listComponentRight(@PathVariable long VALIDATOR_ID) {

        Optional<Validator_> validatorOptional = validatorRepository.findById(VALIDATOR_ID);
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
    public List<ComponentVounch> listComponentRightVounch(@PathVariable long VALIDATOR_ID) {

        Optional<Validator_> validatorOptional = validatorRepository.findById(VALIDATOR_ID);
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



/*
    @PostMapping(produces = "application/json")
    public Validator_ createValidatorWithEquipment(@RequestBody ValidatorPojo pojo) {

        return validatorService.newValidator(null, pojo);


    }
*/



    @GetMapping(value = " /validators", produces = "application/json")
    public PagedModel listComponentRight(Pageable pageable, PagedResourcesAssembler<EntityModel<Validator_>> assembler) {

        Page<Validator_> validatorOptional = validatorRepository.findAll(pageable);


        return assembler.toModel(validatorOptional.map(e->{

            EntityModel en = EntityModel.of(e);
            return en;
        }));

    }

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

}