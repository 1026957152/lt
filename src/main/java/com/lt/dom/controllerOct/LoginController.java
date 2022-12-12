package com.lt.dom.controllerOct;

//https://blog.csdn.net/Dawn____Dawn/article/details/118080415

import com.google.gson.Gson;
import com.lt.dom.JwtUtils;
import com.lt.dom.OctResp.UserResp;
import com.lt.dom.OctResp.UserWithTokenResp;
import com.lt.dom.OctResp.VerifyPhoneResp;
import com.lt.dom.config.AuthenticationTokenProvider;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.notification.event.OnRegistrationCompleteEvent;
import com.lt.dom.oct.Openid;
import com.lt.dom.oct.User;
import com.lt.dom.oct.UserAuthority;
import com.lt.dom.oct.VerificationToken;
import com.lt.dom.otcReq.AuthsReq;
import com.lt.dom.otcReq.VerifyPhoneReq;
import com.lt.dom.otcReq.VerifyPhoneSendCodeReq;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;

import com.lt.dom.serviceOtc.*;
import com.lt.dom.vo.IdentityVo;
import com.lt.dom.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private OpenidRepository openidRepository;
    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OpenidServiceImpl openidService;



/*    @Autowired
    private AuthenticationTokenProvider authenticationTokenProvider;*/
    @Autowired
    private AuthenticationTokenProvider authenticationTokenProvider;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserAuthorityRepository userAuthorityRepository;
    @Autowired
    private IUserService service;
    @Autowired
    ApplicationEventPublisher eventPublisher;


    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserVoServiceImpl userVoService;
    @Autowired
    UserServiceImpl userService;

    @Autowired
    private MessageSource messages;
    @Autowired
    private OTPService otpService;

    @Autowired
    private SMSServiceImpl smsService;


    @GetMapping("/regitrationConfirm")
    public String confirmRegistration
            (WebRequest request, Model model, @RequestParam("token") String token) {

        Locale locale = request.getLocale();

        VerificationToken verificationToken = service.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().isAfter(LocalDateTime.now()))) {
            String messageValue = messages.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", messageValue);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        user.setEnabled(true);
        service.saveRegisteredUser(user);
        return "redirect:/login.html?lang=" + request.getLocale().getLanguage();
    }



    @PostMapping("/verifications/confirm")
    public VerifyPhoneResp sms_confirm(@RequestBody @Valid VerifyPhoneReq request) {

        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);


        Optional<VerificationToken> verificationToken = service.getByUuid(request.getId());


        if(verificationToken.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到核验对象");

        }
            VerificationToken verificationToken1 = verificationToken.get();

                if(!verificationToken1.getStatus().equals(EnumVefifyStatus.pending)) {

                    throw new BookNotFoundException(Enumfailures.resource_not_found,"验证状态错误");

                }
                    if(verificationToken1.getExpiryDate().isBefore(LocalDateTime.now())) {
                        throw new BookNotFoundException(Enumfailures.resource_not_found,"超时，请重新发送验证码");
                    }


                        if(!verificationToken1.getToken().equals(request.getCode())) {
                            throw new BookNotFoundException(Enumfailures.resource_not_found,"验证错误");

                        }
                            VerificationToken verificationToken____ = service.verified(verificationToken1);


                        VerifyPhoneResp verifyPhoneResp = new VerifyPhoneResp();

                        verifyPhoneResp.setMessage("验证成功");
                        return verifyPhoneResp;





    }





    @GetMapping(value = "/Page_verifyPhone", produces = "application/json")
    public EntityModel Page_verification() {



        Map map = Map.of("status_list", EnumUserType.from());


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(LoginController.class).getPhoneCode__(null)).withRel("send_verification_code"));

        entityModel.add(linkTo(methodOn(LoginController.class).sms_confirm(null)).withRel("verify"));

        return entityModel;

    }




    @PostMapping("/verifications")
    public EntityModel getPhoneCode__(@RequestBody @Valid VerifyPhoneReq request) {

        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);


       // Integer integer = otpService.generateOTP(userVo.getUser_id()+"");



        VerificationToken verificationToken = service.createVerificationToken(userVo, request.getPhone());

        if(verificationToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"短信发送太频繁，稍等发送");

        }

            verificationToken = service.retry(verificationToken);

            String greetings = String.format(
                    "你的验证码是%s",
                    verificationToken.getToken());

            System.out.println("-------发了短信，发了短信");
            smsService.singleSend(greetings,request.getPhone());

            VerifyPhoneResp verifyPhoneResp = new VerifyPhoneResp();
            verifyPhoneResp.setId(verificationToken.getUuid());
            verifyPhoneResp.setExpiryDate(verificationToken.getExpiryDate());
            verifyPhoneResp.setPhone(request.getPhone());
            verifyPhoneResp.setMessage("短信发送成功，注意查收"+verificationToken.getToken());

            EntityModel entityModel = EntityModel.of(verifyPhoneResp);

            entityModel.add(linkTo(methodOn(LoginController.class).sms_confirm(null)).withRel("verify"));

            return entityModel;




    }




    @GetMapping("/getPhoneCode")
    public String getPhoneCode(WebRequest request) {

       String appUrl = request.getContextPath();
       Optional<User> registered = userRepository.findById(1l);
       eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered.get(),
               request.getLocale(), appUrl));

       return "";
    }























    @GetMapping(value = "/openid/{OPEN_ID}/Page_smsLogin", produces = "application/json")
    public EntityModel Page_smsLogin(@PathVariable String OPEN_ID) {

        Optional<Openid> optional = openidRepository.findByOpenid(OPEN_ID);

        if(optional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到openid");
        }


        Map map = Map.of("status_list", EnumUserType.from());


        EntityModel entityModel = EntityModel.of(map);
        entityModel.add(linkTo(methodOn(EmpowerRestController.class).mini_getPhone(null)).withRel("getPhone"));


        entityModel.add(linkTo(methodOn(LoginController.class).login_send_code(null)).withRel("send_verification_code"));

        entityModel.add(linkTo(methodOn(LoginController.class).login_sms_confirm(optional.get().getOpenid(),null)).withRel("login"));



        return entityModel;

    }


    @PostMapping("/verifications/login_send_code")
    public EntityModel login_send_code(@RequestBody @Valid VerifyPhoneReq request) {
/*
        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);*/


        VerificationToken verificationToken = service.createVerificationTokenForLogin(EnumSmsVerificationType.login_miniapp,request.getPhone());



        VerifyPhoneResp verifyPhoneResp = new VerifyPhoneResp();
        verifyPhoneResp.setId(verificationToken.getUuid());
        verifyPhoneResp.setExpiryDate(verificationToken.getExpiryDate());
        verifyPhoneResp.setPhone(request.getPhone());
        verifyPhoneResp.setMessage("短信发送成功，注意查收"+verificationToken.getToken());

        EntityModel entityModel = EntityModel.of(verifyPhoneResp);

        entityModel.add(linkTo(methodOn(LoginController.class).sms_confirm(null)).withRel("verify"));

        return entityModel;




    }


    @PostMapping("/openid/{OPEN_ID}/verifications/login_confirm")
    public EntityModel login_sms_confirm(@PathVariable String OPEN_ID,@RequestBody @Valid VerifyPhoneReq request) {

        System.out.println(request.getId());
        System.out.println(request.getCode());
        System.out.println(request.getPhone());
        Optional<Openid> optional = openidRepository.findByOpenid(OPEN_ID);

        if(optional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到openid");
        }
        Openid openid = optional.get();



        Optional<VerificationToken> verificationToken = service.getByUuid(request.getId());


        if(verificationToken.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到核验对象");

        }
        VerificationToken verificationToken1 = verificationToken.get();

        if(!verificationToken1.getStatus().equals(EnumVefifyStatus.pending)) {

            throw new BookNotFoundException(Enumfailures.resource_not_found,"验证状态错误");

        }
        if(verificationToken1.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"超时，请重新发送验证码");
        }


        if(!verificationToken1.getToken().equals(request.getCode())) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"验证错误");

        }
        VerificationToken verificationToken____ = service.verified(openid,verificationToken1);


      //  Optional<UserAuthority> userAuthority = userAuthorityRepository.findByIdentityTypeAndIdentifier(EnumIdentityType.phone,verificationToken____.getPhone());

/*        Gson gson = new Gson();
        IdentityVo identityVo = new IdentityVo(EnumIdentityType.phone,verificationToken____.getPhone());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(gson.toJson(identityVo),verificationToken____.getPhone()));

        System.out.println(" ------=+++++++++++"+ authentication.getPrincipal());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(1,authentication);*/

        Optional<UserAuthority> userAuthority = userAuthorityRepository.findByIdentityTypeAndIdentifier(EnumIdentityType.phone,verificationToken____.getPhone());


        Optional<User> user = userRepository.findById(userAuthority.get().getUser_id());

     //   openid = openidService.linkUser(openid,user.get());


        UserResp openidResp = UserResp.from(user.get());

        EntityModel entityModel = EntityModel.of(openidResp);

        entityModel.add(linkTo(methodOn(EmpowerRestController.class).mini_getPhone(null)).withRel("getPhone"));

        //openidResp.setToken(jwt);
      //  System.out.println("====================:"+jwt);
        return entityModel;





    }
































    @GetMapping(value = "/Page_login", produces = "application/json")
    public EntityModel Page_login() {


        Map map = Map.of(
                "left_image",FileStorageServiceImpl.loadDocumentWithDefault("left_image.png"),
                "logo",FileStorageServiceImpl.loadDocumentWithDefault("lt.png"),
                "topMsg","下一站智慧旅游平台",
                "footer","下一站，2022"
                );


        EntityModel entityModel = EntityModel.of(map);
        entityModel.add(linkTo(methodOn(LoginController.class).Page_login()).withRel("Page_register"));
        entityModel.add(linkTo(methodOn(LoginController.class).Page_login()).withRel("Page_reset"));



        entityModel.add(linkTo(methodOn(LoginController.class).login_sms_send(null)).withRel("send_verification_code"));

        entityModel.add(linkTo(methodOn(LoginController.class).qqLogin(null,null,null)).withRel("login_password").expand());


        entityModel.add(linkTo(methodOn(LoginController.class).login_sms(null)).withRel("login_sms"));

        return entityModel;

    }









    @PostMapping(value = "/login_sms_send" , produces = "application/json")
    public EntityModel login_sms_send(@RequestBody @Valid VerifyPhoneSendCodeReq request) {

        VerificationToken verificationToken = service.createVerificationTokenForLogin(EnumSmsVerificationType.login_pc, request.getPhone());



        VerifyPhoneResp verifyPhoneResp = new VerifyPhoneResp();
        verifyPhoneResp.setId(verificationToken.getUuid());
        verifyPhoneResp.setExpiryDate(verificationToken.getExpiryDate());
        verifyPhoneResp.setPhone(request.getPhone());
        verifyPhoneResp.setMessage("短信发送成功，注意查收"+verificationToken.getToken());

        EntityModel entityModel = EntityModel.of(verifyPhoneResp);

        entityModel.add(linkTo(methodOn(LoginController.class).sms_confirm(null)).withRel("verify"));

        return entityModel;




    }


    @PostMapping(value = "/login_sms" , produces = "application/json")
    public EntityModel login_sms(@RequestBody @Valid VerifyPhoneReq request) {

        System.out.println(request.getId());
        System.out.println(request.getCode());
        System.out.println(request.getPhone());



        Optional<VerificationToken> verificationToken = service.getByUuid(request.getId());


        if(verificationToken.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到核验对象");

        }
        VerificationToken verificationToken1 = verificationToken.get();

        if(!verificationToken1.getStatus().equals(EnumVefifyStatus.pending)) {

            throw new BookNotFoundException(Enumfailures.resource_not_found,"验证状态错误");

        }
        if(verificationToken1.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"超时，请重新发送验证码");
        }


        if(!verificationToken1.getToken().equals(request.getCode())) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"验证错误");

        }
        UserAuthority userAuthority = service.verified_pc(verificationToken1);



        Gson gson = new Gson();
        IdentityVo identityVo = new IdentityVo(EnumIdentityType.phone,userAuthority.getIdentifier());


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(gson.toJson(identityVo),"123"));

        System.out.println(" -pC登录-----=+++++++++++"+ authentication.getPrincipal());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(1,authentication);


        Optional<User> user = userRepository.findById(userAuthority.getUser_id());

        UserResp openidResp = UserResp.from(user.get());

        EntityModel entityModel = EntityModel.of(openidResp);

        entityModel.add(linkTo(methodOn(EmpowerRestController.class).mini_getPhone(null)).withRel("getPhone"));

        openidResp.setToken(jwt);
        return entityModel;





    }
























    //QQ审核获取的qppid和appkey
/*    @Value("${qq.appid}")
    private String appid;
    @Value("${qq.appkey}")
    private String appkey;*/
    /**
     * QQ登录
     * 步骤一：用户跳转至授权页面
     * @author 点点
     * @date 2021年6月17日
     */




    @PostMapping(value = "/login" , produces = "application/json")
    public EntityModel<UserWithTokenResp> qqLogin(HttpServletRequest request,
                                                  @RequestParam(value = "token",required = false) String token,
                                                  @RequestBody AuthsReq display)  {



        System.out.println(" -pC登录-----=+++++++++++"+ display.getPhone());
       // passwordEncoder.encode(display.getPassword())

        Gson gson = new Gson();
        IdentityVo identityVo = new IdentityVo(EnumIdentityType.phone,display.getPhone());


       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(gson.toJson(identityVo),display.getPassword()));

        System.out.println(" -pC登录-----=+++++++++++"+ authentication.getPrincipal());
/*        Authentication authentication = authenticationTokenProvider.authenticate(
                new AuthenticationToken("123", Collections.emptyList()));*/

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(1,authentication);


        Optional<UserAuthority> userAuthority = userAuthorityRepository.findByIdentityTypeAndIdentifier(EnumIdentityType.phone,display.getPhone());

        Optional<User> userOptional = userRepository.findById(userAuthority.get().getUser_id());









        if(userOptional.isPresent()){

            User user = userOptional.get();
            UserWithTokenResp authsResp = new UserWithTokenResp();


            System.out.println("这里了   的地方士大夫撒旦 "+ user);
            authsResp.setInfo(userVoService.getBigUser(user));


            authsResp.setToken(jwt);
            System.out.println("这里了   的顶顶顶顶顶顶顶夫撒旦 "+ user);
            return EntityModel.of(authsResp);

 /*
            System.out.println("=================================="+ user);
            System.out.println("=================================="+ user.getRoles().size());


            Optional<Employee> optional = employeeRepository.findByUserId(user.getId());


            AuthsResp authsResp = new AuthsResp();

            EntityModel<AuthsResp> entityModel = EntityModel.of(authsResp);
            if(optional.isPresent()){
                Optional<Supplier> optionalSupplier = supplierRepository.findById(optional.get().getSuplierId());
                UserResp userResp = UserResp.from(user);
                EntityModel<UserResp> userRespEntityModel = EntityModel.of(userResp);

                userRespEntityModel.add(linkTo(methodOn(UserRestController.class).beGuide(user.getId())).withRel("beGuide"));
                userRespEntityModel.add(linkTo(methodOn(UserRestController.class).postRealnameAuths(user.getId(),null)).withRel("realnameAuth"));
                userRespEntityModel.add(linkTo(methodOn(PublicationRestController.class).pageUserPublicationResp(user.getId(),null,null,null)).withRel("getVoucherList"));
                userRespEntityModel.add(linkTo(methodOn(UserRestController.class).pageReservation(user.getId(),null,null)).withRel("getBookingList"));



                authsResp.setProfile(userRespEntityModel);
                SupplierResp supplierResp = SupplierResp.from(optionalSupplier.get());
                EntityModel supplierRespEntityModel = EntityModel.of(supplierResp);
                if(optionalSupplier.isPresent()){
                    Supplier supplier = optionalSupplier.get();
                    supplierRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(supplier.getId())).withRel("getSupplier"));
                  //  supplierRespEntityModel.add(linkTo(methodOn(RedemptionRestController.class).redeemVonchorBycode(null,null)).withRel("redeem"));
                    supplierRespEntityModel.add(linkTo(methodOn(RedemptionRestController.class).pageRedemptionEntry(supplier.getId(),null,null)).withRel("getRedemptionEntries"));
                  //  supplierRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).linkEmployee(supplier.getId(),null)).withRel("addEmployees"));
                    supplierRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).page_mainEmployee(supplier.getId())).withRel("getPageEmployee"));
                    userRespEntityModel.add(linkTo(methodOn(RedemptionRestController.class).redeemVonchorBycode(null,null)).withRel("redeem"));
                    userRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(supplier.getId())).withRel("getSupplier"));
                    userRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).getRedemptionEntry(supplier.getId())).withRel("getRedemptionEntrySummary"));


                    if(supplier.getType().equals(EnumSupplierType.TravelAgent)){
                        supplierRespEntityModel.add(linkTo(methodOn(TourCampaignRestController.class).createBooking(null)).withRel("createTourCampaign"));
                        supplierRespEntityModel.add(linkTo(methodOn(ProductRestController.class).createProduct(supplier.getId(),null)).withRel("createProduct"));

                    }


                }


                authsResp.setSupplier(supplierRespEntityModel);
*/
/*                authsResp.setSupplier_name(userResp.getSupplier_name());
                authsResp.setSupplier_name(userResp.getSupplier_desc());
                authsResp.setSupplier_type(userResp.getSupplier_type());
                authsResp.setSupplier_bussiness_type(userResp.getSupplier_bussiness_type());
                authsResp.setSupplier_code(userResp.getSupplier_code());
                authsResp.setRoles(userResp.getRoles());*//*

            }else {
                UserResp userResp = UserResp.from(user);
                EntityModel<UserResp> userRespEntityModel = EntityModel.of(userResp);

                authsResp.setProfile(userRespEntityModel);

            }




            authsResp.setReal_name(user.getRealName());
            authsResp.setFirst_name(user.getFirst_name());
            authsResp.setLast_name(user.getLast_name());
            authsResp.setPhone(display.getPhone());
            authsResp.setToken(jwt);
*/


        }else{


            throw new AuthenticationCredentialsNotFoundException(display.getPhone());

        }

    }



}