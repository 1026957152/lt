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
import com.lt.dom.otcenum.Enumfailures;
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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
        System.out.println("参数参数"+realnameAuthsReq.toString());
        Authentication authentication =  authenticationFacade.getAuthentication();


        if (authentication instanceof AnonymousAuthenticationToken) {

        }else{
            throw new ExistException(Enumfailures.general_exists_error,"不是匿名用户错误返回");

        }


        System.out.println("--token认证认证认证goggggggg日日日日日日日日日日日日gggggggggggggggss"+(String)authentication.getPrincipal());


        Optional<Openid> optional = openidRepository.findByOpenid((String)authentication.getPrincipal());
        if(optional.isEmpty()) {
            throw new BookNotFoundException("","找不到 openid 对象");
        }
        if(optional.get().getLink()){
            throw new ExistException(Enumfailures.general_exists_error,"已经绑定了用户");
        }

        Openid openid = optional.get();

      //  UserDetails user_ = (UserDetails)authentication.getPrincipal();



        String phone = null;
        if(!ObjectUtils.isEmpty(realnameAuthsReq.getCrypto_phone())){
            try{
                phone = jwtUtils.getUserNameFromJwtToken(realnameAuthsReq.getCrypto_phone());

            }catch (Exception e){
                throw new BookNotFoundException("","加密手机号传入错误");
            }

        }else{
            if(realnameAuthsReq.getPhone() == null){
                throw new BookNotFoundException("","手机号传入是空");

            }
            phone = realnameAuthsReq.getPhone();
        }

        System.out.println("--token认证认证phone Phone phone 里了啊啊 ");



        Optional<User> optionalUser = userService.getActiveOne(realnameAuthsReq.getReal_name(),realnameAuthsReq.getId_card(),phone);

        if(optionalUser.isPresent()){
            System.out.println("--to已经存在改用户了， 实名的啊，里了啊啊 ");
                throw new ExistException(Enumfailures.general_exists_error,"已经存在该实名用户");
        }
        System.out.println("--token认证认证认证goggggggg日日日日日日日日_在这里了啊啊 ");

/*        有wxlinkUser == 有userReal // 则已经绑定，无需再操作；


        有wxlinkUser ! 有userReal // 则已经绑定，要换一个人了已经有的人，


        有wxlinkUser   没userReal // 则已经绑定，要换一个人了新的人，

        没有wxlinkUser   没有userReal // 正常绑定*/




         realnameAuthsReq.setPhone(phone);

        Pair<User, Openid> user = realnameAuthsService.postWxRealnameAuths(openid,realnameAuthsReq);

        EntityModel entityModel = EntityModel.of(UserResp.userWithOpenidLink(user));
        entityModel.add(linkTo(methodOn(UserRestController.class).getCurrent()).withRel("getCurrent"));

        return ResponseEntity.ok(entityModel);


    }

}