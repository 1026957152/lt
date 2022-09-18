package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.ComponentVounchResp;
import com.lt.dom.OctResp.IntoOnecodeResp;
import com.lt.dom.OctResp.PassResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.PassActivePojo;
import com.lt.dom.otcReq.PassCreatePojo;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.ComponentRightRepository;
import com.lt.dom.repository.ComponentVounchRepository;
import com.lt.dom.repository.PassRepository;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.serviceOtc.IntoOnecodeServiceImpl;
import com.lt.dom.serviceOtc.PassServiceImpl;
import com.lt.dom.thiirdAli.idfaceIdentity.IdfaceIdentityOcrService;
import com.lt.dom.thiirdAli.idfaceIdentity.IdfaceIdentityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class IntoOnecodeController {


    @Autowired
    private PassServiceImpl passService;

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
        EntityModel entityModel = EntityModel.of(e);
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

        List<ComponentVounch> componentRightList = componentVounchRepository.findAllByUser(user.getId());
        IntoOnecodeResp intoOnecodeResp = IntoOnecodeResp.from(user,intoOnecode);


        intoOnecodeResp.setComponentVounch(componentRightList.stream().map(e->{
            return ComponentVounchResp.from(e);
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


}