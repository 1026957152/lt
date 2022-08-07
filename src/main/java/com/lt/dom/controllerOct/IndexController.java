package com.lt.dom.controllerOct;

//https://blog.csdn.net/Dawn____Dawn/article/details/118080415

import com.lt.dom.JwtUtils;
import com.lt.dom.OctResp.*;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.AuthsReq;
import com.lt.dom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class IndexController {


    @Autowired
    private ScenarioRepository scenarioRepository;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CampaignRepository campaignRepository;

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
    @GetMapping(value = "/index" , produces = "application/json")
    public EntityModel<HomeResp> qqLogin(HttpServletRequest request)  {


        List<Scenario> scenarios = scenarioRepository.findAll();





        HomeResp homeResp = new HomeResp();
        EntityModel<HomeResp> entityModel = EntityModel.of(homeResp);





           /*     Page<Campaign> campaignPageable = campaignRepository.findAll(PageRequest.of(0,1000));

        List<CampaignResp> campaignResps =  CampaignResp.pageMapToList(campaignPageable);
*/
/*        CollectionModel collectionModel = CollectionModel.of(campaignResps);
        homeResp.setCampaigns(collectionModel);


                collectionModel.add(linkTo(methodOn(CampaignRestController.class).listCampaign(1)).withRel("fuckyou"));

        */







        CollectionModel categoryCollectionModel = CollectionModel.of(scenarios.stream().map(x->{

            EntityModel<Scenario> scenarioEntityModel = EntityModel.of(x);

            scenarioEntityModel.add(linkTo(methodOn(ScenarioRestController.class).getScenario(x.getId())).withRel("getScenario"));
            return scenarioEntityModel;

        }).collect(Collectors.toList()));

        homeResp.setCampaignCategories(categoryCollectionModel);



        entityModel.add(linkTo(methodOn(CampaignRestController.class).listCampaign(1)).withRel("get"));
        entityModel.add(linkTo(methodOn(CampaignRestController.class).listCampaign(1)).withRel("realnameAuth"));



            return entityModel;


    }



}