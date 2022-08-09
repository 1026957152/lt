package com.lt.dom.controllerOct;

//https://blog.csdn.net/Dawn____Dawn/article/details/118080415

import com.lt.dom.JwtUtils;
import com.lt.dom.OctResp.AuthsResp;
import com.lt.dom.OctResp.SupplierResp;
import com.lt.dom.OctResp.UserResp;
import com.lt.dom.config.AuthenticationToken;
import com.lt.dom.config.AuthenticationTokenProvider;
import com.lt.dom.oct.Employee;
import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.User;
import com.lt.dom.otcReq.AuthsReq;
import com.lt.dom.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    private SupplierRepository supplierRepository;

    @Autowired
    JwtUtils jwtUtils;
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
    public EntityModel<AuthsResp> qqLogin(HttpServletRequest request, @RequestBody AuthsReq display) throws IOException, AuthenticationException {

       // passwordEncoder.encode(display.getPassword())
       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(display.getPhone(),display.getPassword()));


/*        Authentication authentication = authenticationTokenProvider.authenticate(
                new AuthenticationToken("123", Collections.emptyList()));*/

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(0,authentication);


        Optional<User> userOptional = userRepository.findByPhone(display.getPhone());
        if(userOptional.isPresent()){
            User user = userOptional.get();
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


                }


                authsResp.setSupplier(supplierRespEntityModel);
/*                authsResp.setSupplier_name(userResp.getSupplier_name());
                authsResp.setSupplier_name(userResp.getSupplier_desc());
                authsResp.setSupplier_type(userResp.getSupplier_type());
                authsResp.setSupplier_bussiness_type(userResp.getSupplier_bussiness_type());
                authsResp.setSupplier_code(userResp.getSupplier_code());
                authsResp.setRoles(userResp.getRoles());*/
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

            return entityModel;
        }else{
            throw new AuthenticationCredentialsNotFoundException(display.getPhone());

        }

    }



}