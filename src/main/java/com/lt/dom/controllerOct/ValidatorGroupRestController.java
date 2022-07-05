package com.lt.dom.controllerOct;

import com.lt.dom.oct.ComponentRight;
import com.lt.dom.oct.ComponentRightVounch;
import com.lt.dom.oct.Validator;
import com.lt.dom.oct.ValidatorGroup;
import com.lt.dom.repository.ValidatorRepository;
import com.lt.dom.serviceOtc.IdGenServiceImpl;
import com.lt.dom.serviceOtc.ValidatorScanServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class ValidatorGroupRestController {


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



*/


    @Operation(summary = "1、建核销组")
    @PostMapping(value = "/suppler/{SUPPLIER_ID}/validator_groups", produces = "application/json")
    public ValidatorGroup createValidatorGroup(@PathVariable int COMPONENT_RIGHTS_ID, ValidatorGroup validatorGroup) {
        ValidatorGroup componentRight = new ValidatorGroup();
        return componentRight;
    }

    @Operation(summary = "2、添加核销权益类型")
    @PostMapping(value = "/oct/validator_groups/{VALIDATOR_GROUP_ID}/component_rights", produces = "application/json")
    public ComponentRight addComponentRightToValidatorGroup(@PathVariable int SUPPLIER_ID, ValidatorGroup validatorGroup) {
        ComponentRight componentRight = new ComponentRight();
        return componentRight;
    }

    @Operation(summary = "3、添加核销成员")
    @PostMapping(value = "/oct/validator_groups/{VALIDATOR_GROUP_ID}/validators", produces = "application/json")
    public Validator addValidator(@PathVariable int SUPPLIER_ID, ValidatorGroup validatorGroup) {
        Validator componentRight = new Validator();
        return componentRight;
    }

    @Operation(summary = "4、获取核销组可以核销的权益")

    @GetMapping(value = "validator_groups/{VALIDATOR_GROUP_ID}/component_rights", produces = "application/json")
    public List<ComponentRight> listComponentRight(@PathVariable long VALIDATOR_GROUP_ID) {

        Optional<Validator> validatorOptional = validatorRepository.findById(VALIDATOR_GROUP_ID);
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

    @Operation(summary = "5、获取核销组成员")
    @GetMapping(value = "validator_groups/{VALIDATOR_GROUP_ID}/validators", produces = "application/json")
    public List<ComponentRight> listValidator(@PathVariable long VALIDATOR_GROUP_ID) {

        Optional<Validator> validatorOptional = validatorRepository.findById(VALIDATOR_GROUP_ID);
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


}