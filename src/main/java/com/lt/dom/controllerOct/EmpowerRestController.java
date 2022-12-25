package com.lt.dom.controllerOct;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSONObject;
import com.lt.dom.JwtUtils;
import com.lt.dom.OctResp.EmpowerResp;
import com.lt.dom.OctResp.PhoneResp;
import com.lt.dom.OctResp.UserResp;
import com.lt.dom.config.AuthenticationTokenProvider;
import com.lt.dom.config.WxConfig;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.minapp.CommonUtil;
import com.lt.dom.minapp.Token;
import com.lt.dom.minapp.UserService;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.EmpowerGetPhoneReq;
import com.lt.dom.otcReq.OpenidResp;
import com.lt.dom.otcReq.UserPojo;
import com.lt.dom.otcReq.WxloginReq;
import com.lt.dom.otcenum.EnumIdentityType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.OpenidRepository;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.serviceOtc.OpenidServiceImpl;
import com.lt.dom.serviceOtc.UserAuthorityServiceImpl;
import com.lt.dom.serviceOtc.UserServiceImpl;
import com.lt.dom.serviceOtc.product.CityPassServiceImpl;
import com.lt.dom.util.RestTemplateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.Valid;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class EmpowerRestController {
    Logger logger = LoggerFactory.getLogger(EmpowerRestController.class);

    @Autowired
    private WxConfig wxConfig;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private OpenidServiceImpl openidService;
    @Autowired
    private UserServiceImpl userService;


    @Autowired
    private OpenidRepository openidRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAuthorityServiceImpl userAuthorityService;

    @Autowired
    AuthenticationTokenProvider authenticationTokenProvider;



    /*
     * 微信授权（登录）
     * @param code 状态码
     * @param rawData 原始数据
     * @return
     */
    @PostMapping(value = "/wxlogout/{OPEN_ID}", produces = "application/json")// consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object wxlogout(@PathVariable String OPEN_ID){ //,


        System.out.println("微信login");
        Optional<Openid> optional = openidRepository.findByOpenid(OPEN_ID);

        if(optional.isEmpty()) {

            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到openid ");

        }

        Openid openid = optional.get();


        openidService.unLinkUser(openid);


        UserResp openidResp = UserResp.from(openid);

        EntityModel entityModel = EntityModel.of(openidResp);

        entityModel.add(linkTo(methodOn(EmpowerRestController.class).mini_getPhone(null)).withRel("getPhone"));

        return entityModel;

    }





    @PostMapping(value = "/getPhone", produces = "application/json")// consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public EmpowerResp mini_getPhone(@RequestBody @Valid EmpowerGetPhoneReq code)





 //   public EmpowerResp mini_getPhone(@RequestParam(value = "code",required = true) String code)
    {


        logger.info("请求微信 手机号认证 {}",code.toString());


        ///     POST https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=ACCESS_TOKEN
        Token accessToken = accessToken = CommonUtil.getToken(wxConfig.getAppId(), wxConfig.getSecret());



        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + accessToken.getAccessToken();

        // 发送请求参数
        Map<String,Object> paramJson = new HashedMap();
        paramJson.put("code",code.getCode());

        RestTemplate restTemplate = new RestTemplate();
      //  ResponseEntity<PhoneResp> responseEntity = restTemplate.getForEntity(url,PhoneResp.class);

        ResponseEntity<PhoneResp> responseEntity = restTemplate.postForEntity(url,paramJson,PhoneResp.class);
        System.out.println("getStatusCode"+responseEntity.getStatusCode());
        PhoneResp buffer = responseEntity.getBody();

        System.out.println(buffer);


        EmpowerResp resultPO = new EmpowerResp();

        resultPO.setPhone(buffer.getPhone_info().getPhoneNumber());
        String jwt = jwtUtils.generateJwtToken( buffer.getPhone_info().getPhoneNumber());
        resultPO.setCryptoPhone(jwt);
        return resultPO;
    }




    /**所需的maven依赖包
     <!-- 阿里JSON解析器 -->
     <dependency>
     <groupId>com.alibaba</groupId>
     <artifactId>fastjson</artifactId>
     <version>${fastjson.version}</version>
     </dependency>
     <!-- bouncycastle-->
     <dependency>
     <groupId>org.bouncycastle</groupId>
     <artifactId>bcprov-jdk15on</artifactId>
     <version>1.54</version>
     </dependency>
     **/

    /**
     * 解密获取手机号
     * @param encryptedData
     * @param iv
     * @param session_key
     * @return
     */
    @PostMapping("/getPhone_with")

    @ResponseBody
    public EmpowerResp mini_getPhone(@Param("encryptedData") String encryptedData, @Param("iv")String iv, @Param("session_key")String session_key)
    {
   ///     POST https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=ACCESS_TOKEN

        System.out.println("mini_getPhone");
        EmpowerResp resultPO = new EmpowerResp();
        JSONObject obj=getPhoneNumber(session_key,encryptedData,iv);//解密电话号码
        String sphone=obj.get("phoneNumber").toString();
        resultPO.setPhone(sphone);

        return resultPO;
    }



    //解析电话号码
    public JSONObject getPhoneNumber(String session_key, String encryptedData, String iv) {
        byte[] dataByte = Base64.decode(encryptedData);

        byte[] keyByte = Base64.decode(session_key);

        byte[] ivByte = Base64.decode(iv);
        try {

            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }





    /*
     * 微信授权（登录）
     * @param code 状态码
     * @param rawData 原始数据
     * @return
     */
    @PostMapping(value = "/wxLogin", produces = "application/json")// consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object wxLogin(@RequestBody  WxloginReq wxloginReq){ //,

  //  @PostMapping(value = "/wxLogin", produces = "application/json")// consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  //  public OpenidResp wxLogin(@RequestBody @Valid WxloginReq wxloginReq){ //,
/*                              @RequestParam(value = "rawData", required = false) String rawData,
                              @RequestParam(value = "signature",required = false) String signature) {*/
//		登录凭证校验。通过 wx.login 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程




        System.out.println("/wxLogin");
        String code = wxloginReq.getCode();

        logger.debug("微信登录，code {}",code);
        String openid = null;
        if(ObjectUtils.isEmpty(wxloginReq.getOpenid())){

            //  String rawData = wxloginReq.getRawData();

            String url = "https://api.weixin.qq.com/sns/jscode2session";
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
            param.add("appid", wxConfig.getAppId());
            param.add("secret", wxConfig.getSecret());
            param.add("js_code", code);
            param.add("grant_type", "authorization_code");
            //RestTemplate 的请求URL；
            // 开发者服务器 登录凭证校验接口 appi + appsecret + code
            JSONObject forObject = null;
            try {
                forObject = RestTemplateUtil.doPost(url,param);
                System.out.println(forObject);


                if( forObject.containsKey("errcode") && !forObject.getInteger("errcode").equals(0)){
                    logger.error("请求微信 jscode2session 服务报错 {}",forObject);

                    throw new BookNotFoundException("","请求微信 s/jscode2session 错我"+forObject);

                }


            } catch(HttpStatusCodeException e) {

                logger.error("请求微信 服务报错 {}",e.getMessage());

                throw new BookNotFoundException("","请求微信 服务报错"+e.getMessage());

            }


            System.out.println(forObject);
            //接收微信接口服务 获取返回的参数

            // String openid =  "odzgk0ZpWx4NpQC4B1Tu1hnTgYwE";//
            openid =  forObject.getString("openid");
            String sessionKey = forObject.getString("session_key");
            //校验签名 小程序发送的签名signature与服务器端生成的签名signature2 = sha1(rawData + sessionKey)



            //  String signature2 = DigestUtils.sha1Hex(rawData + sessionKey);   // String signature2 = DigestUtils.sha1Hreex(rawData + sessionKey);

            //     System.out.println(signature2);



/*        if (!signature.equals(signature2)) {
            throw new wx_login_errorException(1,"微信","签名校验失败");//ResultUtil.error(500, "签名校验失败");
        }*/




            // 用户非敏感信息：rawData
            // JSONObject rawDataJson = JSONObject.parseObject(rawData);
            //判断用户是否存在

        }else{
            openid = wxloginReq.getOpenid();
        }




        Optional<Openid> optional = openidRepository.findByOpenid(openid);
        Openid openid1 = null;
        if(optional.isEmpty()){
            // 用户信息入库
            WxloginReq.RawData rawData = wxloginReq.getRawData();

            System.out.println("rawData"+ rawData);
            System.out.println("code"+ code);

            String nickName =rawData.getNickName();// rawDataJson.getString("nickName");
            String avatarUrl =rawData.getAvatarUrl();// rawDataJson.getString("avatarUrl");
            Integer gender =rawData.getGender();// Integer.valueOf(rawDataJson.getString("gender"));

            openid1 = openidService.create(openid,nickName,gender,avatarUrl);

    /*
            UserPojo userPojo = new UserPojo();
            //userPojo.setFirst_name(wxlinkUserReq.getFirst_name());
            //userPojo.setLast_name(wxlinkUserReq.getLast_name());
            userPojo.setUsername(openid1.getOpenid());
         //   userPojo.setPhone(realnameAuthsReq.getPhone());
            userPojo.setPassword("wxlinkUserReq.getUser_password()");
            userPojo.setRoles(Arrays.asList("ROLE_ADMIN"));

            User user = userService.createUser(userPojo,Arrays.asList(Pair.with(EnumIdentityType.weixin,openid)));


            openidService.linkUser(openid1,user);*/


        }else{
            openid1 = optional.get();

            if(optional.get().getLink()){




                Optional<User> optionalUser = userAuthorityService.getWeixinBindUser(openid1);



              String jwt = jwtUtils.generateJwtToken(0, openid);


                UserResp openidResp = UserResp.userWithOpenidLink(Pair.with(optionalUser.get(),openid1));
                openidResp.setToken(jwt);

               // openidResp.add(linkTo(methodOn(PublicationRestController.class).pageUserPublicationResp(optionalUser.get().getId(),null,null,null)).withRel("getVoucherList"));

                EntityModel entityModel = EntityModel.of(openidResp);

                entityModel.add(linkTo(methodOn(PublicationRestController.class).getVoucherList(optionalUser.get().getId(),null,null,null)).withRel("getVoucherList"));

                entityModel.add(linkTo(methodOn(UserRestController.class).getCurrent()).withRel("getCurrent"));

                return openidResp;
            }


        }



        UserResp openidResp = UserResp.from(openid1);
       // openidResp.add(linkTo(methodOn(OpenidRestController.class).linkUser(openid1.getOpenid(),null)).withRel("link_user_url"));
       // openidResp.add(linkTo(methodOn(OpenidRestController.class).merchants_settled(openid1.getOpenid(),null)).withRel("merchants_settled_url"));
        //openidResp.add(linkTo(methodOn(OpenidRestController.class).createUser(openid1.getOpenid(),null)).withRel("register_url"));
        EntityModel entityModel = EntityModel.of(openidResp);

        entityModel.add(linkTo(methodOn(RealnameAuthRestController.class).postRealnameAuths(null)).withRel("realnameAuth"));
        entityModel.add(linkTo(methodOn(EmpowerRestController.class).mini_getPhone(null)).withRel("getPhone"));

        entityModel.add(linkTo(methodOn(LoginController.class).Page_smsLogin(openid)).withRel("Page_phoneLogin"));

        entityModel.add(linkTo(methodOn(LoginController.class).login_send_code(null)).withRel("send_verification_code"));

        entityModel.add(linkTo(methodOn(LoginController.class).login_sms_confirm(openid,null)).withRel("login"));


/*        Authentication authentication = authenticationTokenProvider.authenticate(
                new AuthenticationToken(openid1.getOpenid(), Collections.emptyList()));
        SecurityContextHolder.getContext().setAuthentication(authentication);*/


        String jwt = jwtUtils.generateJwtToken(0, openid);
        openidResp.setToken(jwt);

        System.out.println("====================:"+openid);
        System.out.println("====================:"+jwt);
        return entityModel;

    }






/*
    @GetMapping(value = "/users/current",produces = "application/json")
    public ResponseEntity<EntityModel> getCurrent() {


        Authentication authentication =  authenticationFacade.getAuthentication();

        //   UserDetails user_de = (UserDetails)authentication.getPrincipal();

        Optional<User> optionalUser = userRepository.findByPhone("13468801684");

        //   Optional<User> optionalUser = userRepository.findByPhone(user_de.getUsername());

        if(optionalUser.isEmpty()) {
            throw new BookNotFoundException(0,User.class.getSimpleName());

        }
            User user = optionalUser.get();

            Optional<Employee> optional = employeeRepository.findByUserId(user.getId());
            UserResp userResp = UserResp.from(user);
            EntityModel entityModel = EntityModel.of(userResp);


            entityModel.add(linkTo(methodOn(UserRestController.class).beGuide(user.getId())).withRel("beGuide"));
            entityModel.add(linkTo(methodOn(UserRestController.class).postRealnameAuths(user.getId(),null)).withRel("realnameAuths"));
            entityModel.add(linkTo(methodOn(PublicationRestController.class).pageUserPublicationResp(user.getId(),null,null,null)).withRel("getVoucherList"));
            entityModel.add(linkTo(methodOn(UserRestController.class).pageReservation(user.getId(),null,null)).withRel("getBookingList"));





            return ResponseEntity.ok(entityModel);

    }*/
}