package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.ComponentVounchResp;
import com.lt.dom.OctResp.IntoOnecodeResp;
import com.lt.dom.OctResp.PassResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.OnecodeScanPojo;
import com.lt.dom.otcenum.EnumValidatorType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AuthenticationFacade;
import com.lt.dom.serviceOtc.IntoOnecodeServiceImpl;
import com.lt.dom.serviceOtc.ValidateServiceImpl;
import com.lt.dom.util.ZxingBarcodeGenerator;
import com.lt.dom.vo.DeviceScanValidatorVo;
import com.lt.dom.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.Authentication;
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
public class HandSetController {


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
    private ValidateServiceImpl validateService;

    @Autowired
    private ValidatorRepository validatorRepository;





    //TODO 这里有点问题
    @PostMapping(value = "/handset/redeam", produces = "application/json")
    public Object redeemVonchorByCryptoCode(@RequestBody OnecodeScanPojo pojo___) {


      //  User user_文旅码用户 = intoOnecodeService.byCode(pojo___.getCode());


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userOv_景区检票员 = authenticationFacade.getUserVo(authentication);


        Supplier supplier = userOv_景区检票员.getSupplier();



        Optional<ComponentVounch> componentVounchList = componentVounchRepository.findByCode(pojo___.getCode());

        if(componentVounchList.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到这个权益券 "+pojo___.getCode());

        }

        Optional<User> user_文旅码用户 = userRepository.findById(componentVounchList.get().getUser());


            List<Validator_> validator_s = validatorRepository.findAllByTypeAndUser(EnumValidatorType.特定的人员,userOv_景区检票员.getUser_id());

            if(validator_s.isEmpty()){
                throw new BookNotFoundException(Enumfailures.not_found,"职工得 核销分配 对象 为空，请添加"+userOv_景区检票员.getPhone());
            }

            return  validateService.人员扫码(userOv_景区检票员,user_文旅码用户.get(),validator_s,
                    Arrays.asList(componentVounchList.get()));

    }



    //TODO 这里有点问题
    @PostMapping(value = "/handset/allowed_validator", produces = "application/json")
    public Object allowed_validator(@RequestBody OnecodeScanPojo pojo___) {


        //  User user_文旅码用户 = intoOnecodeService.byCode(pojo___.getCode());


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userOv_景区检票员 = authenticationFacade.getUserVo(authentication);


        Supplier supplier = userOv_景区检票员.getSupplier();



        Optional<ComponentVounch> componentVounchList = componentVounchRepository.findByCode(pojo___.getCode());

        if(componentVounchList.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到这个权益券 "+pojo___.getCode());

        }

        Optional<User> user_文旅码用户 = userRepository.findById(componentVounchList.get().getUser());


        List<Validator_> validator_s = validatorRepository.findAllByTypeAndUser(EnumValidatorType.特定的人员,userOv_景区检票员.getUser_id());

        if(validator_s.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"职工得 核销分配 对象 为空，请添加"+userOv_景区检票员.getPhone());
        }

        return  validateService.人员扫码(userOv_景区检票员,user_文旅码用户.get(),validator_s,
                Arrays.asList(componentVounchList.get()));

    }



}