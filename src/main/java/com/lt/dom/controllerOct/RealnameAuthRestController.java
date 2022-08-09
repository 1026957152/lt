package com.lt.dom.controllerOct;

import com.lt.dom.JwtUtils;
import com.lt.dom.OctResp.UserResp;
import com.lt.dom.RealNameAuthentication.RealNameAuthenticationServiceImpl;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.oct.Openid;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.User;
import com.lt.dom.otcReq.OpenidResp;
import com.lt.dom.otcReq.RealnameAuthsReq;
import com.lt.dom.repository.OpenidRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.IAuthenticationFacade;
import com.lt.dom.serviceOtc.UserServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class RealnameAuthRestController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RealNameAuthenticationServiceImpl realnameAuthsService;
    @Autowired
    private OpenidRepository openidRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private UserServiceImpl userService;


    @Operation(summary = "1、实名认证")
    @PostMapping(value = "/realname-auths/individual", produces = "application/json")
    public ResponseEntity postRealnameAuths( @RequestBody @Valid RealnameAuthsReq realnameAuthsReq) {
        System.out.println("参数参数"
            +realnameAuthsReq.toString()
        );
        Authentication authentication =  authenticationFacade.getAuthentication();

        UserDetails user_ = (UserDetails)authentication.getPrincipal();

        Optional<Openid> optional = openidRepository.findByOpenid(user_.getUsername());
        if(optional.isEmpty()) {
            throw new BookNotFoundException("","找不到 openid 对象");
        }

        if(optional.get().getLink()){
            throw new ExistException("已经绑定了用户");
        }

        String phone = null;
        if(!ObjectUtils.isEmpty(realnameAuthsReq.getCrypto_phone())){
            try{
                phone = jwtUtils.getUserNameFromJwtToken(realnameAuthsReq.getCrypto_phone());

            }catch (Exception e){
                throw new BookNotFoundException("","加密手机号传入错误");
            }

        }else{
            phone = realnameAuthsReq.getPhone();
        }



        Optional<User> optionalUser = userService.getActiveOne(realnameAuthsReq.getReal_name(),realnameAuthsReq.getId_card(),phone);

        if(optionalUser.isPresent()){

                throw new ExistException("已经存在该实名用户");

        }

/*        有wxlinkUser == 有userReal // 则已经绑定，无需再操作；


        有wxlinkUser ! 有userReal // 则已经绑定，要换一个人了已经有的人，


        有wxlinkUser   没userReal // 则已经绑定，要换一个人了新的人，

        没有wxlinkUser   没有userReal // 正常绑定*/




        Pair<User, Openid> user = realnameAuthsService.postWxRealnameAuths(optional.get(),realnameAuthsReq);

        EntityModel entityModel = EntityModel.of(UserResp.userWithOpenidLink(user));
        entityModel.add(linkTo(methodOn(UserRestController.class).getCurrent()).withRel("getCurrent"));

        return ResponseEntity.ok(entityModel);



    }

}