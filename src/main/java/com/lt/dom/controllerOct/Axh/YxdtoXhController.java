package com.lt.dom.controllerOct.Axh;

import com.google.gson.Gson;

import com.lt.dom.JwtUtils;
import com.lt.dom.controllerOct.Axh.model.AuthRequest;
import com.lt.dom.controllerOct.Axh.model.XhToYxdResponse;
import com.lt.dom.controllerOct.Axh.model.XydToXhLogRequest;
import com.lt.dom.controllerOct.Axh.model.YxdToXhLoginResponse;
import com.lt.dom.oct.Axh.XydToXhPushRequest;
import com.lt.dom.oct.Axh.XydToXhPushRequestJsonFit;
import com.lt.dom.oct.User;
import com.lt.dom.otcenum.EnumIdentityType;
import com.lt.dom.repository.Axh.XydToXhPushRequestRepository;
import com.lt.dom.vo.IdentityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/xh")
public class YxdtoXhController {


    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    XhToYxdService xhToYxdService;

    @Autowired
    AuthenticationManager authManager;
/*    @Autowired
    JwtTokenUtil jwtUtil;*/
    @Autowired
    XydToXhPushRequestRepository xydToXhPushRequestRepository;

    @PostMapping(value = "/v1/back-center/login")
    public YxdToXhLoginResponse login(@RequestBody XydToXhLogRequest xydToXhLogRequest) {


        Gson gson = new Gson();
        IdentityVo identityVo = new IdentityVo(EnumIdentityType.phone,xydToXhLogRequest.getUsername());


        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(gson.toJson(identityVo),xydToXhLogRequest.getPassword()));


/*        System.out.println("登录登录；额啊啊啊");
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        xydToXhLogRequest.getUsername(), xydToXhLogRequest.getPassword())
        );*/

        System.out.println("自己安排搭配；额啊啊啊");
     //   User user = (User) authentication.getPrincipal();
        String accessToken = jwtUtils.generateJwtToken(1,authentication);

        YxdToXhLoginResponse xhToYxdLoginResponse = new YxdToXhLoginResponse();

        xhToYxdLoginResponse.setAccessToken(accessToken);
        xhToYxdLoginResponse.setRefreshToken(UUID.randomUUID().toString());
        xhToYxdLoginResponse.setExpiresIn(new Date().getTime());
        return xhToYxdLoginResponse;


    }

/*

    @PostMapping(value="/auth/login",produces = "application/json")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            System.out.println("登录登录；额啊啊啊");
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );

            System.out.println("自己安排搭配；额啊啊啊");
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(user.getEmail(), accessToken);

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            ex.printStackTrace();

            System.out.println("报错了暴多了；"+ex.toString());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
*/






    @RequestMapping(value = "/v1/back-center/request", method = RequestMethod.POST)
    public XhToYxdResponse request(@RequestBody XydToXhPushRequestJsonFit xydToXhPushRequestJsonFit) {

        System.out.println("=====ddd===="+xydToXhPushRequestJsonFit.toString());



        String json_string  = new Gson().toJson(xydToXhPushRequestJsonFit).toString();
        System.out.println("看看推送过来的json"+(new Gson().toJson(xydToXhPushRequestJsonFit).toString()));

        XydToXhPushRequest xydToXhPushRequest = new XydToXhPushRequest();
        xydToXhPushRequest.setJson(json_string);
        xydToXhPushRequest.setOrderIdX申请id(xydToXhPushRequestJsonFit.getOrderId());
        xydToXhPushRequest = xydToXhPushRequestRepository.save(xydToXhPushRequest);

        xhToYxdService.cronTaskYmlDemo();

        XhToYxdResponse xhToYxdResponse = new XhToYxdResponse();
        xhToYxdResponse.setRespCode(0);
        xhToYxdResponse.setRespMsg("成功");
        xhToYxdResponse.setDatas(xydToXhPushRequest);
        return xhToYxdResponse;
    }
}


