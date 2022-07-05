package com.lt.dom.controllerOct;

import com.lt.dom.domain.CouponTemplatePojo;
import com.lt.dom.domain.CouponTemplatePojoList;
import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.ComponentRight;
import com.lt.dom.oct.ComponentRightVounch;
import com.lt.dom.oct.Validator;
import com.lt.dom.repository.ValidatorRepository;
import com.lt.dom.serviceOtc.IdGenServiceImpl;
import com.lt.dom.serviceOtc.ValidatorScanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/oct/validators")
public class ValidatorRestController {


    @Autowired
    private ValidatorScanServiceImpl validatorScanService;

    @Autowired
    private ValidatorRepository validatorRepository;

    @Autowired
    private IdGenServiceImpl idGenService;

/*
    @GetMapping(value = "/co/{ID}", produces = "application/json")
    public AccessValidator getComponentRight_Validator(@PathVariable int COMPONENT_RIGHTS_ID, @PathVariable int COUPON_TMPL_ID) {
        AccessValidator componentRight = new AccessValidator();

        return componentRight;
    }

    @PostMapping(value = "/coupon_templates", produces = "application/json")
    public ComponentRight createCouponTemplate(@RequestBody CouponTemplatePojo  pojo) {
        ComponentRight componentRight = new ComponentRight();

        componentRight.setName(pojo.getName());
        componentRight.setNote(idGenService.nextId("Coupon_"));
        return componentRight;
    }






    @PostMapping(value = "/componet_rights/{COMPONENT_RIGHTS_ID}/access_validators", produces = "application/json")
    public AccessValidator addComponentRight_Validator(@PathVariable int COMPONENT_RIGHTS_ID, @PathVariable int COUPON_TMPL_ID) {
        AccessValidator componentRight = new AccessValidator();
        return componentRight;
    }
*/


    @GetMapping(value = " /{VALIDATOR_ID}/component_rights", produces = "application/json")
    public List<ComponentRight> listComponentRight(@PathVariable long VALIDATOR_ID) {

        Optional<Validator> validatorOptional = validatorRepository.findById(VALIDATOR_ID);
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
    public List<ComponentRightVounch> listComponentRightVounch(@PathVariable long VALIDATOR_ID) {

        Optional<Validator> validatorOptional = validatorRepository.findById(VALIDATOR_ID);
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