package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.GuideInchargeBookingResp;
import com.lt.dom.OctResp.PassResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.PassActivePojo;
import com.lt.dom.otcReq.PassCreatePojo;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.thiirdAli.idfaceIdentity.IdfaceIdentityOcrService;
import com.lt.dom.thiirdAli.idfaceIdentity.IdfaceIdentityVo;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/oct")
public class PassRestController {


    @Autowired
    private PassServiceImpl passService;

    @Autowired
    private PassRepository passRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ComponentRightRepository componentRightRepository;
    @Autowired
    private IdfaceIdentityOcrService idfaceIdentityOcrService;
    @Autowired
    private ComponentRightServiceImpl componentRightService;
    @Autowired
    private ValidatorServiceImpl validatorService;

    @Autowired
    private ComponentRepository componentRepository;



    @Autowired
    private ProductRepository   productRepository ;

    @GetMapping(value = "/passes", produces = "application/json")
    public PagedModel getPassList( Pageable pageable, PagedResourcesAssembler<EntityModel<Pass>> assembler) {

        Page<Pass> validatorOptional = passRepository.findAll(pageable);


        return assembler.toModel(validatorOptional.map(e->{
            PassResp passResp = PassResp.from(e);
            EntityModel entityModel = EntityModel.of(e);
            return entityModel;
        }));
    }


    @GetMapping(value = "/users/{USER_ID}/passes", produces = "application/json")
    public PagedModel getPassList(@PathVariable long USER_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<Pass>> assembler) {

        Page<Pass> validatorOptional = passRepository.findAllByUser(USER_ID,pageable);

        return assembler.toModel(validatorOptional.map(e->{
            EntityModel entityModel = EntityModel.of(e);
            return entityModel;
        }));
    }



    @PostMapping(value = "/users/{USER_ID}/passes", produces = "application/json")
    public EntityModel createPass(@PathVariable long USER_ID,  @RequestBody PassCreatePojo pojo) {

        Optional<User> validatorOptional = userRepository.findById(USER_ID);

if(validatorOptional.isEmpty()){
    throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
}
        User user = validatorOptional.get();




        Optional<Product> productOptional = productRepository.findById(pojo.getProductId());

        if(productOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品 ");
        }


        Product product = productOptional.get();


        List<Component> componentRightList = componentRepository.findAllByProductId(product.getId());

        List<ComponentVounch> componentVounchList = componentRightService.createComponentVounch(null,componentRightList,user);


        Map<Long,User> longUserMap = userRepository.findAllById(componentVounchList.stream().map(e->e.getUser()).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(e->e.getId(),e->e));

        validatorService.push( componentVounchList.stream().map(e->{
            User user_ = longUserMap.get(e.getUser());

            return Pair.with(e,user_);
        }).collect(Collectors.toList()));

        Pass pass = passService.create_virtual(user,12);
        componentRightService.groupby(componentVounchList,pass);
        componentRightService.bulkUpdata(componentVounchList);


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






}