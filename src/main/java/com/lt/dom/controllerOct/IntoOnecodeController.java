package com.lt.dom.controllerOct;

import com.google.gson.Gson;
import com.lt.dom.OctResp.ComponentVounchResp;
import com.lt.dom.OctResp.IntoOnecodeResp;
import com.lt.dom.OctResp.PassResp;
import com.lt.dom.OctResp.RedemptionResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.error.Voucher_has_no_permistion_redeemException;
import com.lt.dom.error.voucher_not_publishException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.OnecodeScanPojo;
import com.lt.dom.otcReq.PassActivePojo;
import com.lt.dom.otcReq.PassCreatePojo;
import com.lt.dom.otcReq.RedemByCryptoCodePojo;
import com.lt.dom.otcenum.EnumCampaignToTourBookingStatus;
import com.lt.dom.otcenum.EnumVoucherStatus;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.PublishTowhoVo;
import com.lt.dom.serviceOtc.AuthenticationFacade;
import com.lt.dom.serviceOtc.IntoOnecodeServiceImpl;
import com.lt.dom.serviceOtc.PassServiceImpl;
import com.lt.dom.serviceOtc.ValidateServiceImpl;
import com.lt.dom.thiirdAli.idfaceIdentity.IdfaceIdentityOcrService;
import com.lt.dom.thiirdAli.idfaceIdentity.IdfaceIdentityVo;
import com.lt.dom.util.ZxingBarcodeGenerator;
import com.lt.dom.vo.CodeWithLatLngVo;
import com.lt.dom.vo.UserVo;
import com.lt.dom.vo.WrittenOffMerchantVo;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class IntoOnecodeController {


    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private PassRepository passRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ComponentRightRepository componentRightRepository;
    @Autowired
    private IntoOnecodeServiceImpl intoOnecodeService;
    @Autowired
    private ComponentVounchRepository componentVounchRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ValidateServiceImpl validateService;

/*


    @GetMapping(value = "/passes", produces = "application/json")
    public PagedModel getPassList( Pageable pageable, PagedResourcesAssembler<EntityModel<Pass>> assembler) {

        Page<Pass> validatorOptional = passRepository.findAll(pageable);


        return assembler.toModel(validatorOptional.map(e->{
            PassResp passResp = PassResp.from(e);
            EntityModel entityModel = EntityModel.of(e);
            return entityModel;
        }));
    }
*/
@GetMapping(value = "/onecode", produces = "application/json")
public PagedModel getPassList( Pageable pageable, PagedResourcesAssembler<EntityModel<Pass>> assembler) {

    Page<Pass> validatorOptional = passRepository.findAll(pageable);


    return assembler.toModel(validatorOptional.map(e->{
        PassResp passResp = PassResp.from(e);
        EntityModel entityModel = EntityModel.of(passResp);
        return entityModel;
    }));
}

    @GetMapping(value = "/users/{USER_ID}/onecode", produces = "application/json")
    public EntityModel getPass(@PathVariable long USER_ID) {


        Optional<User> validatorOptional = userRepository.findById(USER_ID);
        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
        }
        User user = validatorOptional.get();

        IntoOnecode intoOnecode = intoOnecodeService.getAvailability(validatorOptional.get());

        List<ComponentVounch> componentVounchList = componentVounchRepository.findAllByUser(user.getId());
        IntoOnecodeResp intoOnecodeResp = IntoOnecodeResp.from(user,intoOnecode);






        List<ComponentRight> componentRights= componentRightRepository.findAllById(componentVounchList.stream().map(e->e.getComponentRight()).collect(Collectors.toList()));

        Map<Long,ComponentRight> componentRightMap = componentRights.stream().collect(Collectors.toMap(e->e.getId(), e->e));

        List<Supplier> supplierList= supplierRepository.findAllById(componentVounchList.stream().map(e->e.getSupplier()).collect(Collectors.toList()));
        Map<Long,Supplier> longSupplierMap = supplierList.stream().collect(Collectors.toMap(e->e.getId(),e->e));





        intoOnecodeResp.setComponentVounch(componentVounchList.stream().map(e->{
            Supplier supplier = longSupplierMap.get(e.getSupplier());
            ComponentRight componentRight = componentRightMap.get(e.getComponentRight());

            ComponentVounchResp componentVounchResp = ComponentVounchResp.from(e,componentRight,supplier);
            componentVounchResp.setCode_base64_src(ZxingBarcodeGenerator.base64_png_src(e.getCode()));

            EntityModel entityModel = EntityModel.of(componentVounchResp);
            entityModel.add(linkTo(methodOn(ComponentRightRestController.class).getComponentVoucher(e.getId())).withSelfRel());

            return componentVounchResp;

        }).collect(Collectors.toList()));
        EntityModel entityModel = EntityModel.of(intoOnecodeResp);


        entityModel.add(linkTo(methodOn(ComponentRightRestController.class).getComponentVouncherList(user.getId(),null,null)).withRel("getComponentVoucherList"));


        return entityModel;
    }


/*

    @PostMapping(value = "/users/{USER_ID}/passes", produces = "application/json")
    public EntityModel createPass(@PathVariable long USER_ID,  @RequestBody PassCreatePojo pojo) {




        List<ComponentRight> componentRightList = componentRightRepository.findAllById(pojo.getRights());



        Optional<User> validatorOptional = userRepository.findById(USER_ID);

        PassCreatePojo passCreatePojo = pojo;

        Pass pass = passService.create(validatorOptional.get(),componentRightList);


        return EntityModel.of(pass);
    }





    @PostMapping(value = "/users/{USER_ID}/passes/active", produces = "application/json")
    public EntityModel activePass(@PathVariable long USER_ID,  @RequestBody PassActivePojo pojo, MultipartFile file) {




        List<ComponentRight> componentRightList = componentRightRepository.findAllById(pojo.getRights());


        IdfaceIdentityVo idfaceIdentityVo = idfaceIdentityOcrService.main(pojo.getName(), pojo.getId_card(),file);

        Optional<User> validatorOptional = userRepository.findById(USER_ID);

      //  PassCreatePojo passCreatePojo = pojo;

        Pass pass = passService.create(validatorOptional.get(),componentRightList);


        return EntityModel.of(pass);
    }




*/







    //TODO 这里有点问题
    @PostMapping(value = "/onecode/redeam", produces = "application/json")
    public Object redeemVonchorByCryptoCode(@RequestBody OnecodeScanPojo pojo___) {



        User user = intoOnecodeService.byCode(pojo___.getCode());



        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userOv = authenticationFacade.getUserVo(authentication);



        Supplier supplier = userOv.getSupplier();



        List<ComponentVounch> componentVounchList = componentVounchRepository.findAllByUser(user.getId());
if(componentVounchList.size() ==0){
    throw new BookNotFoundException(Enumfailures.not_found,"该 用户无可核销得 权益"+user.getPhone());

}

        return validateService.user扫文旅码(userOv,componentVounchList);


    }






}