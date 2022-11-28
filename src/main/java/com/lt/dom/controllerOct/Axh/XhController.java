package com.lt.dom.controllerOct.Axh;

import com.google.gson.Gson;
import com.lt.dom.controllerOct.Axh.model.*;
import com.lt.dom.credit.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.Axh.PullFromYxdRequest;
import com.lt.dom.oct.Axh.XydToXhPushRequest;
import com.lt.dom.oct.Axh.XydToXhPushRequestJsonFit;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.otcenum.enum_.EnumBusinessType业务类型;
import com.lt.dom.otcenum.enum_.EnumClinchPayWay还款方式;
import com.lt.dom.otcenum.enum_.EnumGuarantyWay担保方式;
import com.lt.dom.otcenum.enum_.EnumPullRequestStatus;
import com.lt.dom.repository.Axh.PullFromYxdRequestRepository;
import com.lt.dom.repository.Axh.XydToXhPushRequestRepository;
import com.lt.dom.serviceOtc.AuthenticationFacade;
import com.lt.dom.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/oct/xh")
public class XhController {



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


    @Autowired
    PullFromYxdRequestRepository pullFromYxdRequestRepository;
    @Autowired
    XydToXhPushRequestRepository xydToXhPushRequestRepository;
    @Autowired
    XhToYxdService xhToYxdService;


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


            entityModel.add(linkTo(methodOn(XhController.class).request授信Resps(x.getId())).withRel("getRequest"));
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
/*
        entityModel.add(linkTo(methodOn(XhController.class).request授信Resps(requestCredit.getId())).withSelfRel());

        entityModel.add(linkTo(methodOn(XhController.class).授信(requestCredit.getId(),null)).withRel("授权操作"));
        entityModel.add(linkTo(methodOn(XhController.class).放款通知(requestCredit.getId())).withRel("放款通知"));
*/

        return entityModel;

    }

/*

    @RequestMapping(value = "/v1/xhtoyxd/login", method = RequestMethod.POST)

    public XhToYxdResponse login() {




        XhToYxdLoginResponse xhToYxdLoginResponse = xhToYxdService.login();



        XhToYxdResponse xhToYxdResponse = new XhToYxdResponse();
        xhToYxdResponse.setRespCode(0);
        xhToYxdResponse.setRespMsg("成功");
        xhToYxdResponse.setDatas(xhToYxdLoginResponse);
        return xhToYxdResponse;
    }

*/

    private String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

















    @RequestMapping(value = "/xh/v1/xhtoyxd/creditWaitConfirmVO/{id}", method = RequestMethod.POST)

    public XhToYxdResponse creditWaitConfirmVO(@PathVariable Integer id,@Valid @RequestBody  CreditWaitConfirmReq creditWaitConfirmReq) {
        System.out.println("拒绝授授信参数 "+ creditWaitConfirmReq.toString());

        System.out.println("id:"+id);
        Optional<PullFromYxdRequest> xydToXhPushRequests   = pullFromYxdRequestRepository.findById(id.longValue());

        //Optional<XydToXhPushRequest> xydToXhPushRequests   = xydToXhPushRequestRepository.findById(id.longValue());

        if(xydToXhPushRequests.isEmpty()){
            XhToYxdResponse xhToYxdResponse = new XhToYxdResponse();
            xhToYxdResponse.setRespCode(-1);
            xhToYxdResponse.setRespMsg("找不到这个申请，请检查 orderIdX");
            xhToYxdResponse.setRequestDatas(id);
            return xhToYxdResponse;
        }
        PullFromYxdRequest xydToXhPushRequest = xydToXhPushRequests.get();

        String url_website = "https://wbank.96262.com/icap-wbank/page/03/0315/031501/031501.html?t=1662004370329?code=011Cvj100knSnK1L88400OdfCv2Cvj1T&state=1&t=1662004370330";


        String url_qr = "http://111.20.184.51:81/upload-files/1/1268859028401577986/cadddeba-22da-4bbe-b59b-8f3359ef613ansh_xwsd_qrcod.png";
        CreditWaitConfirmVO code = new CreditWaitConfirmVO();
        //   code.setQrcode(url_qr);
        //  code.setAttachment_上传返款证明返回信息(url);

        code.setQrcode(encodeValue(url_qr));
        code.setAttachment_上传返款证明返回信息(encodeValue(url_website));

     //   code.setBusinessType_业务类型(EnumBusinessType业务类型.直租.getId());
     //   code.setCreateBy("");
      //  code.setCreateTime("");
        code.setCreditAmount_授信额度(creditWaitConfirmReq.getCreditAmount_授信额度());
      //  code.setDisabled(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        code.setEndTime_授信额度有效期至(creditWaitConfirmReq.getEndTime_授信额度有效期至().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

      //  code.setEndTime_授信额度有效期至(formatter.format(LocalDateTime.now().plusYears(1)));
      //  code.setGuarantyWay_担保方式(EnumGuarantyWay担保方式.保证.getId());

        //  code.setId(0);
        code.setOrderId_申请id(xydToXhPushRequest.getIdX());
        // code.setInstId_机构id(xydToXhPushRequest.getInstInfo金融机构().getIdX());
        code.setInstId_机构id(9);

      //  code.setInstName("");

        code.setLoanLimit_贷款期限(creditWaitConfirmReq.getClinchLoanLimit_贷款期限());
        code.setLoanRate_贷款年化利率(creditWaitConfirmReq.getClinchLoanRate_贷款年化利率());
       // code.setOrderId_申请id(xydToXhPushRequest.getOrderIdX申请id());
       // code.setOtherFee_担保服务费(0);



        code.setPayWay_还款方式(creditWaitConfirmReq.getClinchPayWay_还款方式().getId());
        code.setGuarantyWay_担保方式(creditWaitConfirmReq.getClinchGuarantyWay_担保方式().getId());
        //code.setPayWayDisplay("");

       // code.setStartTime("");
       // code.setStatus("");
      //  code.setTenantId(0);
      //  code.setUpdateBy("");
      //  code.setUpdateTime("");
      //  code.setUserId(0);


        System.out.println("=====ddd===="+code.toString());

        System.out.println("请求信易贷："+new Gson().toJson(code));
        YxdToXHResponse yxdToXHResponse =  xhToYxdService.creditWaitConfirmVO(xydToXhPushRequest,code,XhToYxdService.older_token.getAccessToken());

        if(yxdToXHResponse != null){
           // System.out.println("=====ddd===="+code.toString());

            XhToYxdResponse xhToYxdResponse = new XhToYxdResponse();
            xhToYxdResponse.setRespCode(0);
            xhToYxdResponse.setRespMsg("成功");
            xhToYxdResponse.setDatas(yxdToXHResponse);
            xhToYxdResponse.setRequestDatas(code);
            return xhToYxdResponse;
        }else{

            XhToYxdResponse xhToYxdResponse = new XhToYxdResponse();
            xhToYxdResponse.setRespCode(-1);
            xhToYxdResponse.setRespMsg("没有登录，请先登录");
            xhToYxdResponse.setRequestDatas(code);
            return xhToYxdResponse;
        }




    }





    @RequestMapping(value = "/xh/v1/xhtoyxd/insReject/{id}", method = RequestMethod.POST)

    public XhToYxdResponse insReject(@PathVariable Integer id,@Valid @RequestBody  InsRejectReq insReject) {
        System.out.println("拒绝授授信参数 "+ insReject.toString());

        Optional<PullFromYxdRequest> xydToXhPushRequests   = pullFromYxdRequestRepository.findById(id.longValue());

      //  Optional<XydToXhPushRequest> xydToXhPushRequests   = xydToXhPushRequestRepository.findById(id.longValue());

        if(xydToXhPushRequests.isEmpty()){
            XhToYxdResponse xhToYxdResponse = new XhToYxdResponse();
            xhToYxdResponse.setRespCode(-1);
            xhToYxdResponse.setRespMsg("找不到这个申请，请检查 orderIdX");
            xhToYxdResponse.setRequestDatas(id);
            return xhToYxdResponse;
        }
        PullFromYxdRequest xydToXhPushRequest = xydToXhPushRequests.get();
        //   xydToXhPushRequest.getInstInfo金融机构().getInstCreditCode();


        InsReject code = new InsReject();

        code.setOrderId_申请id(xydToXhPushRequest.getIdX());

        code.setMsg(insReject.getMsg());

        System.out.println("我的请求参数是"+(new Gson().toJson(code).toString()));

        //

        YxdToXHResponse xhResponse = xhToYxdService.insReject(xydToXhPushRequest,code);

        System.out.println("=====ddd===="+code.toString());
        if(xhResponse != null){
            XhToYxdResponse xhToYxdResponse = new XhToYxdResponse();
            xhToYxdResponse.setRespCode(0);
            xhToYxdResponse.setRespMsg("成功");
            xhToYxdResponse.setDatas(xhResponse);
            xhToYxdResponse.setRequestDatas(code);
            return xhToYxdResponse;
        }else{

            XhToYxdResponse xhToYxdResponse = new XhToYxdResponse();
            xhToYxdResponse.setRespCode(-1);
            xhToYxdResponse.setRespMsg("没有登录");
            xhToYxdResponse.setRequestDatas(code);
            return xhToYxdResponse;
        }




    }


    @RequestMapping(value = "/xh/v1/xhtoyxd/addClinchInfo/{id}", method = RequestMethod.POST)

    public XhToYxdResponse 放款通知(@PathVariable Integer id, @Valid @RequestBody AddClinchInfoReq addClinchInfoReq) {
        Optional<PullFromYxdRequest> xydToXhPushRequests   = pullFromYxdRequestRepository.findById(id.longValue());


        System.out.println("放款通知参数 "+ addClinchInfoReq.toString());
      //  Optional<XydToXhPushRequest> xydToXhPushRequests   = xydToXhPushRequestRepository.findById(id.longValue());

        if(xydToXhPushRequests.isEmpty()){
            System.out.println("没有找到id 为 的 推送放款信息");
            XhToYxdResponse xhToYxdResponse = new XhToYxdResponse();
            xhToYxdResponse.setRespCode(-1);
            xhToYxdResponse.setRespMsg("找不到这个申请，请检查 orderIdX");
            xhToYxdResponse.setRequestDatas(id);
            return xhToYxdResponse;
        }
        System.out.println("找到了推送放款信息"+ xydToXhPushRequests.toString());



        PullFromYxdRequest xydToXhPushRequest = xydToXhPushRequests.get();
        String url = "https://wbank.96262.com/icap-wbank/page/03/0315/031501/031501.html?t=1662004370329?code=011Cvj100knSnK1L88400OdfCv2Cvj1T&state=1&t=1662004370330";

        AddClinchInfoVO code = new AddClinchInfoVO();
        code.setAttachment放款证明或其他补充资料(encodeValue(url));

       // code.setBackMoneyTypeDisplay("");

      //  code.setBusinessType_业务类型(EnumBusinessType业务类型.直租.getId());
        code.setClinchGuarantyWay_担保方式(addClinchInfoReq.getClinchGuarantyWay_担保方式().getId());
       // code.setClinchGuarantyWayDisplay("");

        code.setClinchLoanAmount_实际放款金额(addClinchInfoReq.getClinchLoanAmount_实际放款金额());

        code.setClinchLoanLimit_实际贷款期限(addClinchInfoReq.getClinchLoanLimit_实际贷款期限());

        code.setClinchLoanRate_贷款年化利率(addClinchInfoReq.getClinchLoanRate_贷款年化利率());


       code.setClinchPayWay_还款方式(addClinchInfoReq.getClinchPayWay_还款方式().getId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        //  code.setEndTime_授信额度有效期至(formatter.format(LocalDateTime.now().plusYears(1)));

        code.setClinchTime_成交时间(addClinchInfoReq.getClinchTime_成交时间().format(formatter));


      //  code.setCreateBy("");
      //  code.setCreateTime("");
       // code.setCreditType("");
       // code.setDisabled(0);
        code.setEntId_企业ID(xydToXhPushRequest.getEntId());
      //  code.setEntId_企业ID(-1);
        // code.setGuarantySubEnt_担保主体企业(xydToXhPushRequest.getBaseInfo企业信息().getEntName());

     //   code.setGuarantySubEnt_担保主体企业("");

      //  code.setId(0);
        code.setInstId_机构id(9);

        //  code.setInstId_机构id(xydToXhPushRequest.getInstInfo金融机构().getIdX());
     //   code.setInstName("");
        code.setOrderId_申请id(xydToXhPushRequest.getIdX());
     //   code.setOtherFee_担保服务费(0);

     //   code.setTenantId(0);
      //  code.setUpdateBy("");
      //  code.setUpdateTime("");
     //   code.setUserId(0);

        System.out.println("请求信易贷："+new Gson().toJson(code));

        XhToYxdResponse xhToYxdResponse = new XhToYxdResponse();
        YxdToXHResponse yxdToXHResponse = xhToYxdService.addClinchInfo(xydToXhPushRequest,code);

        if(yxdToXHResponse!= null){
            xhToYxdResponse.setRespCode(0);
            xhToYxdResponse.setRespMsg("成功");
            xhToYxdResponse.setDatas(yxdToXHResponse);
        }else{
            xhToYxdResponse.setRespCode(-0);
            xhToYxdResponse.setRespMsg("失败");
            xhToYxdResponse.setRequestDatas(code);
        }


        System.out.println("=====ddd===="+code.toString());




        return xhToYxdResponse;
    }














    @GetMapping(value = "/xh/v1/getAllRequest")
    public PagedModel<XydToXhPushRequestJsonFit> getAllRequest(Pageable pageable ,PagedResourcesAssembler<EntityModel<XydToXhPushRequest>> assembler) {
        // queueSender.send("test message");

        PullRequest pullRequest = xhToYxdService.getXydfinanceproductorderinfo();

        if(pullRequest != null){
            System.out.println("================="+ pullRequest.toString());

        }else{
            System.out.println("没有获得啊啊啊");
        }
        Page<XydToXhPushRequest> userList = xydToXhPushRequestRepository.findAll(pageable);

        return assembler.toModel(userList.map(e->{

            System.out.println(e.getStatus_xh()+"看一看状态");
            XydToXhPushRequestJsonFit xydToXhPushRequestJsonFit = new Gson().fromJson(e.getJson(),XydToXhPushRequestJsonFit.class);
            EntityModel entityModel = EntityModel.of(xydToXhPushRequestJsonFit);

            entityModel.add(linkTo(methodOn(XhController.class).getPushReque(e.getId())).withSelfRel());


            entityModel.add(linkTo(methodOn(XhController.class).creditWaitConfirmVO(Long.valueOf(e.getId()).intValue(),null)).withRel("预授信操作"));

            entityModel.add(linkTo(methodOn(XhController.class).insReject(Long.valueOf(e.getId()).intValue(),null)).withRel("授权操作"));
            entityModel.add(linkTo(methodOn(XhController.class).放款通知(Long.valueOf(e.getId()).intValue(),null)).withRel("放款通知"));


            return entityModel;

        }));
    }


    @GetMapping(value = "/xh/v1/sync")
    public PullRequest getSync() {

            PullRequest pullRequest = xhToYxdService.getXydfinanceproductorderinfo();

        return pullRequest;
    }

    @GetMapping(value = "/xh/v1/getPull")
    public PagedModel getPull(@PageableDefault(sort = {"updateTime", "createTime"}, direction = Sort.Direction.DESC) final Pageable pageable ,
                              PagedResourcesAssembler<EntityModel<XydToXhPushRequest>> assembler) {
        Page<PullFromYxdRequest> userList = pullFromYxdRequestRepository.findAll(pageable);


        if(userList.isEmpty()){
            PullRequest pullRequest = xhToYxdService.getXydfinanceproductorderinfo();

        }

        return assembler.toModel(userList.map(e->{


            e.setStatus_text(EnumPullRequestStatus.from(e.getStatus()).getText());

            EntityModel entityModel = EntityModel.of(e);

            entityModel.add(linkTo(methodOn(XhController.class).getPullReque(e.getId())).withSelfRel());



            entityModel.add(linkTo(methodOn(XhController.class).creditWaitConfirmVO(Long.valueOf(e.getId()).intValue(),null)).withRel("预授信操作"));

            entityModel.add(linkTo(methodOn(XhController.class).insReject(Long.valueOf(e.getId()).intValue(),null)).withRel("授权操作"));
            entityModel.add(linkTo(methodOn(XhController.class).放款通知(Long.valueOf(e.getId()).intValue(),null)).withRel("放款通知"));

            return entityModel;

        }));
    }




    @GetMapping(value = "/xh/v1/push_request/{ID}",produces = "application/json")
    public EntityModel getPushReque(@PathVariable(value = "ID") Long id) {
        // queueSender.send("test message");


        Optional<XydToXhPushRequest> optionalPullFromYxdRequest = xydToXhPushRequestRepository.findById(id);

        if(optionalPullFromYxdRequest.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.not_found,"找不到信易贷 贷款请求");

        }
        XydToXhPushRequest pullFromYxdRequest = optionalPullFromYxdRequest.get();
        XydToXhPushRequestJsonFit xydToXhPushRequestJsonFit = new Gson().fromJson(pullFromYxdRequest.getJson(),XydToXhPushRequestJsonFit.class);

        //XydToXhPushRequestJsonFit.FinanceAttachment financeAttachment = new Gson().fromJson(xydToXhPushRequestJsonFit.getBaseInfo().getFinanceAttachment(),XydToXhPushRequestJsonFit.FinanceAttachment.class);


    //    xydToXhPushRequestJsonFit.getBaseInfo().setFinanceAttachmentDto(financeAttachment);
        EntityModel entityModel = EntityModel.of(xydToXhPushRequestJsonFit);

        entityModel.add(linkTo(methodOn(XhController.class).getPullReque(pullFromYxdRequest.getId())).withSelfRel());


        entityModel.add(linkTo(methodOn(XhController.class).creditWaitConfirmVO(Long.valueOf(pullFromYxdRequest.getId()).intValue(),null)).withRel("预授信操作"));

        entityModel.add(linkTo(methodOn(XhController.class).insReject(Long.valueOf(pullFromYxdRequest.getId()).intValue(),null)).withRel("拒绝操作"));
        entityModel.add(linkTo(methodOn(XhController.class).放款通知(Long.valueOf(pullFromYxdRequest.getId()).intValue(),null)).withRel("放款通知"));


        return entityModel;
    }



    @GetMapping(value = "/xh/v1/pull_request/{ID}", produces = "application/json")
    public EntityModel getPullReque(@PathVariable(value = "ID") Long id) {
        // queueSender.send("test message");




        Optional<PullFromYxdRequest> optionalPullFromYxdRequest = pullFromYxdRequestRepository.findById(id);

        if(optionalPullFromYxdRequest.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.not_found,"找不到信易贷 贷款请求");

        }
        PullFromYxdRequest pullFromYxdRequest = optionalPullFromYxdRequest.get();

        EntityModel entityModel = EntityModel.of(pullFromYxdRequest);
        entityModel.add(linkTo(methodOn(XhController.class).getPullReque(pullFromYxdRequest.getId())).withSelfRel());


        entityModel.add(linkTo(methodOn(XhController.class).creditWaitConfirmVO(Long.valueOf(pullFromYxdRequest.getId()).intValue(),null)).withRel("预授信操作"));

        entityModel.add(linkTo(methodOn(XhController.class).insReject(Long.valueOf(pullFromYxdRequest.getId()).intValue(),null)).withRel("拒绝操作"));
        entityModel.add(linkTo(methodOn(XhController.class).放款通知(Long.valueOf(pullFromYxdRequest.getId()).intValue(),null)).withRel("放款通知"));

        return entityModel;
    }
}