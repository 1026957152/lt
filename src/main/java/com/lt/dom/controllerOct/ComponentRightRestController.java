package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.*;


import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ComponentRightPojo;
import com.lt.dom.otcReq.ComponentRightValidatorUpdatePojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.ComponentRightServiceImpl;
import com.lt.dom.serviceOtc.ValidatorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ValidatorServiceImpl validatorService;

    @Autowired
    private ComponentVounchRepository componentVounchRepository;

    @Autowired
    private ValidatorRepository validatorRepository;

    @Autowired
    private DeviceRepository deviceRepository;



    @GetMapping(value = "/componet_rights/{COMPONENT_RIGHT_ID}/validators/{VALIDATOR_ID}", produces = "application/json")
    public Validator_ getComponentRight_Validator(@PathVariable int COMPONENT_RIGHT_ID, @PathVariable int VALIDATOR_ID) {

        AccessValidator probe = new AccessValidator();
        probe.setRalativeId(COMPONENT_RIGHT_ID);
        probe.setValidatorId(VALIDATOR_ID);
        probe.setExtend(EnumValidatorRedemExtent.特定权益券);
        Example<AccessValidator> example = Example.of(probe);
        Optional<AccessValidator> accessValidator = accessValidatorRepository.findOne(example);

        Optional<Validator_> validatorOptional = validatorRepositorya.findById(accessValidator.get().getValidatorId());


        return validatorOptional.get();
    }














    @GetMapping(value = "/supliers/{SUPPLIER_ID}/component_rights/page",produces = "application/json")
    public EntityModel Page_createComponentRight(@PathVariable long SUPPLIER_ID) {

        Optional<Supplier> optionalComponentRight = supplierRepository.findById(SUPPLIER_ID);

        if(optionalComponentRight.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到 供应商");
        }

        EntityModel entityModel = EntityModel.of(Map.of(

                "duration_list", Arrays.stream(EnumDuration.values()).map(x->{

                    EnumResp enumResp = new EnumResp();
                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;

                }).collect(Collectors.toList())

           /*     "device_type_list", Arrays.asList(EnumDeviceType.OUTLET,EnumDeviceType.HANDSET).stream().map(x->{

                    EnumResp enumResp = new EnumResp();

                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
                }).collect(Collectors.toList())*/
        ));


        entityModel.add(linkTo(methodOn(DeviceController.class).registerDevice(null)).withRel("createDevice"));

        return entityModel;
    }


    @Operation(summary = "1、新建权益")

    @PostMapping(value = "/supliers/{SUPPLIER_ID}/component_rights", produces = "application/json")
    public ComponentRightResp createComponentRight(@PathVariable long SUPPLIER_ID,@RequestBody ComponentRightPojo pojo) {


        Optional<Supplier> optionalComponentRight = supplierRepository.findById(SUPPLIER_ID);

        if(optionalComponentRight.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"商城不能为空");
        }

        ComponentRight componentRight = componentRightService.createComponentRight(optionalComponentRight.get(),pojo);

        return ComponentRightResp.from(componentRight);
    }





/*

    @Operation(summary = "1、为权益 添加核验者")
    @PostMapping(value = "/componet_rights/{COMPONENT_RIGHTS_ID}/access_validators", produces = "application/json")
    public AccessValidator addComponentRight_Validator(@PathVariable long COMPONENT_RIGHTS_ID, @RequestBody AccessValidatorPojo pojo) {
        Optional<ComponentRight> optionalComponentRight = componentRightRepository.findById(COMPONENT_RIGHTS_ID);

        AccessValidator accessValidator = componentRightService.addAccessValidator(optionalComponentRight.get(),pojo);

        return accessValidator;
    }*/


    @Operation(summary = "1、查询权益")
    @GetMapping(value = "/supliers/{SUPPLIER_ID}/component_rights", produces = "application/json")
    public PagedModel getComponentRightList(@PathVariable long SUPPLIER_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<ComponentRight>> assembler) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);
        if(optionalSupplier.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = optionalSupplier.get();

        ComponentRight probe = new ComponentRight();

        probe.setSupplier(SUPPLIER_ID);
        Example<ComponentRight> example = Example.of(probe);


        Page<ComponentRight> componentRights = componentRightRepository.findAllBySupplier(supplier.getId(),pageable);

        return assembler.toModel(componentRights.map(e->{

            ComponentRightResp componentRightResp = ComponentRightResp.from(e);



            ComponentRightResp.ComponentRightOption enumResp = new ComponentRightResp.ComponentRightOption();



            EntityModel entityModel = EntityModel.of(componentRightResp);

            entityModel.add(linkTo(methodOn(ComponentRightRestController.class).getComponentRighte(e.getId())).withSelfRel());

            entityModel.add(linkTo(methodOn(ComponentRightRestController.class).Page_createValidator(e.getId())).withRel("Page_updateValidator"));
            entityModel.add(linkTo(methodOn(ComponentRightRestController.class).updateValidator(e.getId(),null)).withRel("updateValidator"));
            entityModel.add(linkTo(methodOn(ComponentRightRestController.class).getComponentRight_Validator(e.getId(),null,null)).withRel("getValidatorList"));


            return entityModel;
        }));
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

    @Operation(summary = "1、删除权益信息")
    @GetMapping(value = "/component_rights/{COMPONENT_RIGHT_ID}", produces = "application/json")
    public EntityModel getComponentRighte(@PathVariable long COMPONENT_RIGHT_ID) {

        Optional<ComponentRight> optionalComponentRight = componentRightRepository.findById(COMPONENT_RIGHT_ID);
        if(optionalComponentRight.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.not_found,"找不到权益");
        }
        ComponentRight componentRight = optionalComponentRight.get();


        ComponentRightResp componentRightResp = ComponentRightResp.from(componentRight);
        EntityModel entityModel = EntityModel.of(componentRightResp);

        return entityModel;
    }








    @GetMapping(value = "/component_vouchers/{COMPONENT_VOUCHER_ID}", produces = "application/json")
    public EntityModel getComponentVoucher(@PathVariable long COMPONENT_VOUCHER_ID) {

        Optional<ComponentVounch> optionalComponentRight = componentVounchRepository.findById(COMPONENT_VOUCHER_ID);
        if(optionalComponentRight.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.not_found,"找不到权益");
        }
        ComponentVounch componentRight = optionalComponentRight.get();


        ComponentVounchResp componentRightResp = ComponentVounchResp.from(componentRight);
        EntityModel entityModel = EntityModel.of(componentRightResp);

        return entityModel;
    }
/*
    @Operation(summary = "1、为权益 增加分润")
    @PostMapping(value = "/componet_rights/{COMPONENT_RIGHTS_ID}/royaltyRules", produces = "application/json")
    public List<RoyaltyRule> addRoyaltyRule(@PathVariable long COMPONENT_RIGHTS_ID, @RequestBody RoyaltyRulePojo pojo) {
        Optional<ComponentRight> optionalComponentRight = componentRightRepository.findById(COMPONENT_RIGHTS_ID);

        List<RoyaltyRule> accessValidator = componentRightService.addRoyaltyRule(optionalComponentRight.get(),pojo);

        return accessValidator;
    }*/




    @Operation(summary = "1、查询权益")
    @GetMapping(value = "/users/{USER_ID}/component_rights", produces = "application/json")
    public PagedModel getComponentVouncherList(@PathVariable long USER_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<ComponentRight>> assembler) {

        Optional<User> optionalSupplier = userRepository.findById(USER_ID);
        if(optionalSupplier.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        User supplier = optionalSupplier.get();


        Page<ComponentVounch> componentVounchPage = componentVounchRepository.findAllByUser(supplier.getId(),pageable);

        List<ComponentRight> componentRights= componentRightRepository.findAllById(componentVounchPage.stream().map(e->e.getComponentRight()).collect(Collectors.toList()));

        Map<Long,ComponentRight> componentRightMap = componentRights.stream().collect(Collectors.toMap(e->e.getId(),e->e));

        List<Supplier> supplierList= supplierRepository.findAllById(componentVounchPage.stream().map(e->e.getSupplier()).collect(Collectors.toList()));
        Map<Long,Supplier> longSupplierMap = supplierList.stream().collect(Collectors.toMap(e->e.getId(),e->e));


        return assembler.toModel(componentVounchPage.map(e->{

            ComponentRight componentRight = componentRightMap.get(e.getComponentRight());

            Supplier supplier_ = longSupplierMap.get(e.getSupplier());

            ComponentVounchResp componentVounchResp = ComponentVounchResp.from(e,componentRight,supplier_);
            EntityModel entityModel = EntityModel.of(componentVounchResp);

            entityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(supplier_.getId())).withRel("getSupplier"));

            return entityModel;
        }));
    }




    @GetMapping(value = "/component_rights/{COMPONENT_RIGHTS_ID}/validators", produces = "application/json")
    public PagedModel getComponentRight_Validator(@PathVariable long COMPONENT_RIGHTS_ID, Pageable pageable , PagedResourcesAssembler<EntityModel<Validator_>> assembler) {
        Optional<ComponentRight> optionalComponentRight = componentRightRepository.findById(COMPONENT_RIGHTS_ID);
        if(optionalComponentRight.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.not_found,"找不到权益");
        }
        ComponentRight componentRight = optionalComponentRight.get();


        Page<Validator_> accessValidator = validatorRepositorya.findByComponentRightId(componentRight.getId(),pageable);


        return assembler.toModel(accessValidator.map(e->{

            EntityModel entityModel = EntityModel.of(e);
            return entityModel;
        }));
    }



    @GetMapping(value = "/component_rights/{COMPONENT_RIGHT_ID}/validators/page",produces = "application/json")
    public EntityModel Page_createValidator(@PathVariable long COMPONENT_RIGHT_ID) {

        Optional<ComponentRight> validatorOptional = componentRightRepository.findById(COMPONENT_RIGHT_ID);
        if(validatorOptional.isEmpty()) {

            throw new BookNotFoundException(Enumfailures.not_found,"找不到权益");

        }
        ComponentRight componentRight = validatorOptional.get();


        List<Validator_> validator_list = validatorRepository.findAllByComponentRightId(componentRight.getId());



        Map<EnumValidatorType,List<Validator_>> enumValidatorTypeListMap =  validator_list.stream().collect(Collectors.groupingBy(e->e.getType()));


        List<Device> deviceList = deviceRepository.findAll();

        List<User> userList = userRepository.findAll();


        EntityModel entityModel = EntityModel.of(Map.of(

                "all_list", Arrays.stream(EnumValidatorType.values()).map(x->{


                    EnumWithSelectListResp enumResp = new EnumWithSelectListResp();
                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    enumResp.setSubitems(Arrays.asList());


                    List<Validator_> validatorS = enumValidatorTypeListMap.getOrDefault(x,Arrays.asList());
                    enumResp.setSelectedItems(validatorS.stream().map(v->{
                        return v.getId();
                    }).collect(Collectors.toList()));

                    if(x.equals(EnumValidatorType.特定机器)){
                        enumResp.setSubitems( deviceList.stream().map(v->{
                            EnumLongIdResp enumResp_validator = new EnumLongIdResp();
                            enumResp_validator.setId(v.getId());
                            enumResp_validator.setText(v.getName()+"_"+v.getId_());
                            return enumResp_validator;

                        }).collect(Collectors.toList()));

                        enumResp.setSelectedItems(validatorS.stream().map(v->{
                            return v.getDevice();
                        }).collect(Collectors.toList()));

                    }

                    if(x.equals(EnumValidatorType.特定的人员)){
                        enumResp.setSubitems( userList.stream().map(v->{
                            EnumLongIdResp enumResp_validator = new EnumLongIdResp();
                            enumResp_validator.setId(v.getId());
                            enumResp_validator.setText(v.getRealName()+"_"+v.getCode());
                            return enumResp_validator;

                        }).collect(Collectors.toList()));

                        enumResp.setSelectedItems(validatorS.stream().map(v->{
                            return v.getUser();
                        }).collect(Collectors.toList()));
                    }



                    return enumResp;
                }).collect(Collectors.toList())


/*

           "device_type_list", Arrays.stream(EnumValidatorType.values()).map(x->{
                    List<Validator_> validatorS = enumValidatorTypeListMap.getOrDefault(x,Arrays.asList());

                    EnumResp enumResp = new EnumResp();

                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());

                    enumResp.setSubitems(validatorS.stream().map(v->{
                        EnumLongIdResp enumResp_validator = new EnumLongIdResp();
                        enumResp_validator.setId(v.getId());
                        enumResp_validator.setText(v.getName());
                        return enumResp_validator;

                    }).collect(Collectors.toList()));


                    return enumResp;
                }).collect(Collectors.toList())*/
        ));


        entityModel.add(linkTo(methodOn(ComponentRightRestController.class).updateValidator(componentRight.getId(),null)).withRel("updateValidator"));

        return entityModel;

    }



    @PutMapping(value = "/component_rights/{COMPONENT_RIGHT_ID}/validators",produces = "application/json")
    public List<Validator_>  updateValidator(@PathVariable long COMPONENT_RIGHT_ID, @RequestBody List<ComponentRightValidatorUpdatePojo> pojo) {

        Optional<ComponentRight> validatorOptional = componentRightRepository.findById(COMPONENT_RIGHT_ID);
        if(validatorOptional.isEmpty()) {

            throw new BookNotFoundException(Enumfailures.not_found,"找不到权益");

        }
        ComponentRight componentRight = validatorOptional.get();

        return validatorService.newValidator(componentRight,pojo);

    }
}