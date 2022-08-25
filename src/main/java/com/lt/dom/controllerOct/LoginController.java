package com.lt.dom.controllerOct;

//https://blog.csdn.net/Dawn____Dawn/article/details/118080415

import com.google.gson.Gson;
import com.lt.dom.JwtUtils;
import com.lt.dom.OctResp.UserWithTokenResp;
import com.lt.dom.config.AuthenticationTokenProvider;
import com.lt.dom.notification.event.OnRegistrationCompleteEvent;
import com.lt.dom.oct.Request;
import com.lt.dom.oct.User;
import com.lt.dom.oct.VerificationToken;
import com.lt.dom.otcReq.AuthsReq;
import com.lt.dom.otcenum.EnumIdentityType;
import com.lt.dom.otcenum.EnumRequestStatus;
import com.lt.dom.otcenum.EnumRequestType;
import com.lt.dom.repository.*;

import com.lt.dom.serviceOtc.IUserService;
import com.lt.dom.serviceOtc.UserServiceImpl;
import com.lt.dom.vo.IdentityVo;
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
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/oct")
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private RequestRepository requestRepository;


    @Autowired
    private UserRepository userRepository;

/*    @Autowired
    private AuthenticationTokenProvider authenticationTokenProvider;*/
    @Autowired
    private AuthenticationTokenProvider authenticationTokenProvider;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private IUserService service;
    @Autowired
    ApplicationEventPublisher eventPublisher;


    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private MessageSource messages;

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
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String messageValue = messages.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", messageValue);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        user.setEnabled(true);
        service.saveRegisteredUser(user);
        return "redirect:/login.html?lang=" + request.getLocale().getLanguage();
    }




   @GetMapping("/getPhoneCode")
    public String getPhoneCode(WebRequest request) {

       String appUrl = request.getContextPath();
       Optional<User> registered = userRepository.findById(1l);
       eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered.get(),
               request.getLocale(), appUrl));

       return "";
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
    @PostMapping(value = "/login_sms" , produces = "application/json")
    public ResponseEntity SMSLogin(HttpServletRequest request,
                                  @RequestParam(value = "code",required = false) String code,
                                  @RequestBody AuthsReq display)  {


        VerificationToken verificationToken = service.getVerificationToken(code);

        System.out.println(code);
        System.out.println(verificationToken.getToken());
        System.out.println(verificationToken.getExpiryDate());
        System.out.println(verificationToken.getUser());
        System.out.println(verificationToken.getId());
        return ResponseEntity.ok(verificationToken);

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
                                                  @RequestBody AuthsReq display) throws IOException, AuthenticationException {



        System.out.println(" ------=+++++++++++"+ display.getPhone());
       // passwordEncoder.encode(display.getPassword())

        Gson gson = new Gson();
        IdentityVo identityVo = new IdentityVo(EnumIdentityType.phone,display.getPhone());


       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(gson.toJson(identityVo),display.getPassword()));

        System.out.println(" ------=+++++++++++"+ authentication.getPrincipal());
/*        Authentication authentication = authenticationTokenProvider.authenticate(
                new AuthenticationToken("123", Collections.emptyList()));*/

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(1,authentication);


        Optional<User> userOptional = userRepository.findByPhone(display.getPhone());









        if(userOptional.isPresent()){

            User user = userOptional.get();
            UserWithTokenResp authsResp = new UserWithTokenResp();


            System.out.println("这里了   的地方士大夫撒旦 "+ user);
            authsResp.setInfo(userService.getBigUser(user));
            List<Request> requestList = requestRepository.findByOwner(user.getId());
            Optional<Request> optionalRequest = requestList.stream().filter(x->x
                    .getType().equals(EnumRequestType.Merchants_settled) & x.getStatus().equals(EnumRequestStatus.Pending)).findAny();
            if(optionalRequest.isPresent()){
                authsResp.setStatus(-1);

            }else{
                authsResp.setStatus(0);
            }

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