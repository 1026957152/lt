package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.ComponentRightResp;
import com.lt.dom.OctResp.ValidatorEditResp;
import com.lt.dom.OctResp.ValidatorResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ValidatorPojo;
import com.lt.dom.otcenum.EnumValidatorType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.ComponentRightRepository;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.repository.ValidatorRepository;
import com.lt.dom.serviceOtc.ValidatorScanServiceImpl;
import com.lt.dom.serviceOtc.ValidatorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class ValidatorRestController {

    private static final Logger logger = LoggerFactory.getLogger(RedemptionRestController.class);

    @Autowired
    private ValidatorScanServiceImpl validatorScanService;

    @Autowired
    private ValidatorRepository validatorRepository;

    @Autowired
    private ValidatorServiceImpl validatorService;

    @Autowired
    private ComponentRightRepository componentRightRepository;

    @Autowired
    private UserRepository userRepository;





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





        @GetMapping(value = "/users/{USER_ID}/validators", produces = "application/json")
    public PagedModel listUserComponentRight(@PathVariable long USER_ID ,Pageable pageable, PagedResourcesAssembler<EntityModel<Validator_>> assembler) {
        Optional<User> supplierOptional =userRepository.findById(USER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        User user = supplierOptional.get();

        Page<Validator_> validatorOptional = validatorRepository.findAllByTypeAndUser(EnumValidatorType.特定的人员,user.getId(),pageable);


        return assembler.toModel(validatorOptional.map(e->{

            Optional<ComponentRight> componentRight = componentRightRepository.findById(e.getComponentRightId());
            ComponentRightResp componentRightResp = ComponentRightResp.from(componentRight.get());

            EntityModel en = EntityModel.of(componentRightResp);
            return en;
        }));

    }




    @GetMapping(value = "/users/{USER_ID}/Page_validators", produces = "application/json")
    public EntityModel Page_validators(@PathVariable long USER_ID ) {
        Optional<User> supplierOptional =userRepository.findById(USER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        User user = supplierOptional.get();

        ValidatorEditResp validatorEditResp = new ValidatorEditResp();

        List<Validator_> validatorOptional = validatorRepository.findAllByTypeAndUser(EnumValidatorType.特定的人员,user.getId());


        List<ValidatorResp> validator_s = validatorOptional.stream().map(e->{

            Optional<ComponentRight> componentRight = componentRightRepository.findById(e.getComponentRightId());

            ValidatorResp validatorResp = new ValidatorResp();
            validatorResp.setId(e.getId());
            validatorResp.setComponentRight(ComponentRightResp.from(componentRight.get()));

            return validatorResp;
        }).collect(Collectors.toList());

        validatorEditResp.setIds(validatorOptional
                .stream().filter(e->e.isActive()).map(e->e.getId()).collect(Collectors.toList()));


        validatorEditResp.setParameterList(Map.of("validator_list", ValidatorResp.EnumList(validator_s)));
        EntityModel entityModel = EntityModel.of(validatorEditResp);
        entityModel.add(linkTo(methodOn(ValidatorRestController.class).update(user.getId(),null)).withSelfRel());

        return entityModel;


    }




    @PutMapping(value = "/users/{USER_ID}/validators", produces = "application/json")
    public EntityModel update(@PathVariable long USER_ID,@RequestBody @Valid ValidatorEditResp validatorEditResp ) {


        logger.debug("参数{} ",validatorEditResp.toString());

        Optional<User> supplierOptional =userRepository.findById(USER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        User user = supplierOptional.get();



        List<Validator_> validatorOptional = validatorRepository.findAllByTypeAndUser(EnumValidatorType.特定的人员,user.getId());


        validatorService.updateUserValidator(user,validatorOptional,validatorEditResp);

        EntityModel en = EntityModel.of(validatorEditResp);
        return en;


    }

}