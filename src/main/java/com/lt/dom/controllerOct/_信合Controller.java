package com.lt.dom.controllerOct;

import com.lt.dom.credit.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.serviceOtc.AuthenticationFacade;
import com.lt.dom.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class _信合Controller {



    @Autowired
    com.lt.dom.repository._信合_税票信息Repository _信合_税票信息Repository;
    @Autowired
    com.lt.dom.repository._信合_欠税信息Repository _信合_欠税信息Repository;
    @Autowired
    com.lt.dom.repository._信合_纳税信息Repository _信合_纳税信息Repository;
    @Autowired
    com.lt.dom.repository._信合_非正常户信息Repository _信合_非正常户信息Repository;
    @Autowired
    com.lt.dom.repository._信合_连续缴税周期信息Repository _信合_连续缴税周期信息Repository;
    @Autowired
    Request授信Repository request授信Repository;

    @Autowired
    授信ServiceImpl 授信Service;

    @Autowired
    StepRepository stepRepository;
    @Autowired
    AuthenticationFacade authenticationFacade;

    @GetMapping(value = "/credit/requests", produces = "application/json")
    public PagedModel listAvailability(Pageable pageable, PagedResourcesAssembler<EntityModel<Request授信Resp>> assembler) {





        Page<RequestCredit> validatorOptional = request授信Repository.findAll(pageable);

        List<Credit_信合_税票信息>  list = _信合_税票信息Repository.findAllById(validatorOptional.stream().map(x->x.getId()).collect(Collectors.toList()));

        Map<Long, List<Credit_信合_税票信息>> longListMap = list.stream().collect(Collectors.groupingBy(x->x.getRequest()));

        List<_信合_欠税信息>  list_信合_欠税信息 = _信合_欠税信息Repository.findAllById(validatorOptional.stream().map(x->x.getId()).collect(Collectors.toList()));

        Map<Long, List<_信合_欠税信息>> longListMap_list_信合_欠税信息 = list_信合_欠税信息.stream().collect(Collectors.groupingBy(x->x.getRequest()));


        List<_信合_纳税信息>  list_信合_纳税信息 = _信合_纳税信息Repository.findAllById(validatorOptional.stream().map(x->x.getId()).collect(Collectors.toList()));

        Map<Long, List<_信合_纳税信息>> longListMap_list_信合_纳税信息 = list_信合_纳税信息.stream().collect(Collectors.groupingBy(x->x.getRequest()));

        List<_信合_非正常户信息>  list_信合_非正常户信息 = _信合_非正常户信息Repository.findAllById(validatorOptional.stream().map(x->x.getId()).collect(Collectors.toList()));

        Map<Long, List<_信合_非正常户信息>> longListMap_list_信合_非正常户信息 = list_信合_非正常户信息.stream().collect(Collectors.groupingBy(x->x.getRequest()));

        List<_信合_连续缴税周期信息>  list_信合_连续缴税周期信息 = _信合_连续缴税周期信息Repository.findAllById(validatorOptional.stream().map(x->x.getId()).collect(Collectors.toList()));
        Map<Long, List<_信合_连续缴税周期信息>> longListMap_list_信合_连续缴税周期信息 = list_信合_连续缴税周期信息.stream().collect(Collectors.groupingBy(x->x.getRequest()));



        List<CreditStep> creditSteps = stepRepository.findAllByRequestIn(validatorOptional.stream().map(x->x.getId()).collect(Collectors.toList()));
        Map<Long, List<CreditStep>> longListMap_CreditStep = creditSteps.stream().collect(Collectors.groupingBy(x->x.getId()));



        return assembler.toModel(validatorOptional.map(x->{

            Request授信Resp request授信Resp = Request授信Resp.from(x);
            request授信Resp.setCredit_信合_税票信息(longListMap.get(x.getId()));
            request授信Resp.setCredit_信合_欠税信息(longListMap_list_信合_欠税信息.get(x.getId()));
            request授信Resp.setCredit_信合_纳税信息(longListMap_list_信合_纳税信息.get(x.getId()));
            request授信Resp.setCredit_信合_非正常户信息(longListMap_list_信合_非正常户信息.get(x.getId()));
            request授信Resp.setCredit_信合_连续缴税周期信息(longListMap_list_信合_连续缴税周期信息.get(x.getId()));

            request授信Resp.setSteps(longListMap_CreditStep.get(x.getId()));

            EntityModel entityModel = EntityModel.of(request授信Resp);


            request授信Resp.setSteps(creditSteps);


            entityModel.add(linkTo(methodOn(_信合Controller.class).request授信Resps(x.getId())).withRel("getRequest"));
            return entityModel;
        }));
    }







    @GetMapping(value = "/credit/requests/{id}", produces = "application/json")
    public EntityModel request授信Resps(@PathVariable long id) {




        Optional<RequestCredit> validatorOptional = request授信Repository.findById(id);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.voucher_disabled,"找不到");
        }
        RequestCredit requestCredit = validatorOptional.get();

        List<CreditStep> creditSteps = stepRepository.findAllByRequest(requestCredit.getId());

        List<Credit_信合_税票信息>  list = _信合_税票信息Repository.findAllById(validatorOptional.stream().map(x->x.getId()).collect(Collectors.toList()));

        List<_信合_欠税信息>  list_信合_欠税信息 = _信合_欠税信息Repository.findAllById(validatorOptional.stream().map(x->x.getId()).collect(Collectors.toList()));

        List<_信合_纳税信息>  list_信合_纳税信息 = _信合_纳税信息Repository.findAllById(validatorOptional.stream().map(x->x.getId()).collect(Collectors.toList()));

        List<_信合_非正常户信息>  list_信合_非正常户信息 = _信合_非正常户信息Repository.findAllById(validatorOptional.stream().map(x->x.getId()).collect(Collectors.toList()));

        List<_信合_连续缴税周期信息>  list_信合_连续缴税周期信息 = _信合_连续缴税周期信息Repository.findAllById(validatorOptional.stream().map(x->x.getId()).collect(Collectors.toList()));


        Request授信Resp request授信Resp = Request授信Resp.from(requestCredit);

        request授信Resp.setCredit_信合_税票信息(list);

        request授信Resp.setCredit_信合_欠税信息(list_信合_欠税信息);
        request授信Resp.setCredit_信合_纳税信息(list_信合_纳税信息);
        request授信Resp.setCredit_信合_非正常户信息(list_信合_非正常户信息);
        request授信Resp.setCredit_信合_连续缴税周期信息(list_信合_连续缴税周期信息);

        request授信Resp.setSteps(creditSteps);




        request授信Resp.setSteps_summary(Map.of("all_status",
                Arrays.stream(EnumCreditStatus.values()).map(xx->xx.name()).collect(Collectors.toList()),
                "status",1));



        EntityModel entityModel = EntityModel.of(request授信Resp);
        entityModel.add(linkTo(methodOn(_信合Controller.class).request授信Resps(requestCredit.getId())).withSelfRel());

        entityModel.add(linkTo(methodOn(_信合Controller.class).授信(requestCredit.getId(),null)).withRel("授权操作"));
        entityModel.add(linkTo(methodOn(_信合Controller.class).放款通知(requestCredit.getId())).withRel("放款通知"));

        return entityModel;

    }




    @PostMapping(value = "/credit/requests/{id}/volume_up",produces = "application/json")
    public EntityModel<RequestCredit> 授信(@PathVariable long id, @RequestBody @Valid VolumeupReq pojo) {


        UserVo userVo = authenticationFacade.getUserVo(authenticationFacade.getAuthentication());

        Optional<RequestCredit> validatorOptional = request授信Repository.findById(id);


        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到");
        }





        RequestCredit requestCredit = validatorOptional.get();



        EnumCreditStatus old = requestCredit.getStatus();
        requestCredit.setStatus(EnumCreditStatus.已授信);
        EnumCreditStatus new_  = requestCredit.getStatus();

        requestCredit.setVolume_up_授权额度(pojo.getVolume_up_授权额度());

        requestCredit = request授信Repository.save(requestCredit);


        CreditStep creditStep = new CreditStep(requestCredit, LocalDateTime.now(),userVo.getReal_name(),EnumCreditAction.放款通知);

        creditStep.setFrom_(old);
        creditStep.setTo_(new_);

        stepRepository.save(creditStep);

        creditStep.setOperator_user_code_操作员编号(userVo.getUser_code());


        EntityModel<RequestCredit> entityModel = EntityModel.of(requestCredit);
        return entityModel;
    }



    @PostMapping(value = "/credit/requests/{id}/nofity_success",produces = "application/json")
    public EntityModel<RequestCredit> 放款通知(@PathVariable long id ) {


        UserVo userVo = authenticationFacade.getUserVo(authenticationFacade.getAuthentication());

        Optional<RequestCredit> validatorOptional = request授信Repository.findById(id);


        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到");
        }

        RequestCredit requestCredit = validatorOptional.get();
        EnumCreditStatus old = requestCredit.getStatus();
        requestCredit.setStatus(EnumCreditStatus.已放款);
        EnumCreditStatus new_  = requestCredit.getStatus();


        requestCredit = request授信Repository.save(requestCredit);


        CreditStep creditStep = new CreditStep(requestCredit, LocalDateTime.now(),userVo.getReal_name(),EnumCreditAction.放款通知);

        creditStep.setFrom_(old);
        creditStep.setTo_(new_);
        stepRepository.save(creditStep);
        creditStep.setOperator_user_code_操作员编号(userVo.getUser_code());


        EntityModel<RequestCredit> entityModel = EntityModel.of(requestCredit);
        return entityModel;
    }
}