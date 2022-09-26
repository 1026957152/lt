package com.lt.dom;

//https://blog.csdn.net/Dawn____Dawn/article/details/118080415

import com.lt.dom.OctResp.layout.LayoutResp;
import com.lt.dom.OctResp.layout.MeResp;
import com.lt.dom.controllerOct.*;
import com.lt.dom.serviceOtc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class MeController {


    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    private FeatureServiceImpl featureService;


    /**
     * QQ登录
     * 步骤一：用户跳转至授权页面
     * @author 点点
     * @date 2021年6月17日
     */
    @GetMapping(value = "/me" , produces = "application/json")
    public EntityModel<LayoutResp> me(HttpServletRequest request)  {



      //  MeResp homeResp = new MeResp();
        EntityModel<LayoutResp> entityModel = EntityModel.of(featureService.meFill());



       // homeResp.setLayout(featureService.meFill());;


        entityModel.add(linkTo(methodOn(CampaignRestController.class).listCampaign(1)).withRel("get"));
        entityModel.add(linkTo(methodOn(CampaignRestController.class).listCampaign(1)).withRel("realnameAuth"));



            return entityModel;


    }






}