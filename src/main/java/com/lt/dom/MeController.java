package com.lt.dom;

//https://blog.csdn.net/Dawn____Dawn/article/details/118080415

import com.lt.dom.OctResp.layout.LayoutResp;
import com.lt.dom.controllerOct.*;
import com.lt.dom.oct.User;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class MeController {


    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    private FeatureServiceImpl featureService;
    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private UserRepository userRepository;


    /**
     * QQ登录
     * 步骤一：用户跳转至授权页面
     * @author 点点
     * @date 2021年6月17日
     */
    @GetMapping(value = "/me" , produces = "application/json")
    public EntityModel<LayoutResp> me(HttpServletRequest request)  {


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);

        User user = userRepository.findById(userVo.getUser_id()).get();

      //  MeResp homeResp = new MeResp();
        EntityModel<LayoutResp> entityModel = EntityModel.of(featureService.meFill(user, Optional.empty()));



       // homeResp.setLayout(featureService.meFill());;


        entityModel.add(linkTo(methodOn(CampaignRestController.class).listCampaign(1)).withRel("get"));
        entityModel.add(linkTo(methodOn(CampaignRestController.class).listCampaign(1)).withRel("realnameAuth"));



            return entityModel;


    }






}