package com.lt.dom.controllerOct;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSONObject;
import com.lt.dom.JwtUtils;
import com.lt.dom.OctResp.EmpowerResp;
import com.lt.dom.OctResp.PhoneResp;
import com.lt.dom.config.AuthenticationToken;
import com.lt.dom.config.AuthenticationTokenProvider;
import com.lt.dom.config.WxConfig;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.minapp.CommonUtil;
import com.lt.dom.minapp.Token;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.EmpowerGetPhoneReq;
import com.lt.dom.otcReq.OpenidResp;
import com.lt.dom.otcReq.WxloginReq;
import com.lt.dom.repository.OpenidRepository;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.serviceOtc.OpenidServiceImpl;
import com.lt.dom.util.RestTemplateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    @Autowired
    private WxConfig wxConfig;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private OpenidServiceImpl openidService;
    @Autowired
    private OpenidRepository openidRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationTokenProvider authenticationTokenProvider;


    @PostMapping(value = "/getPhone", produces = "application/json")// consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public EmpowerResp mini_getPhone(@RequestBody @Valid EmpowerGetPhoneReq code)


 //   public EmpowerResp mini_getPhone(@RequestParam(value = "code",required = true) String code)
    {
        ///     POST https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=ACCESS_TOKEN

        Token accessToken = CommonUtil.getToken(wxConfig.getAppId(), wxConfig.getSecret());

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





    /**
     * 微信授权（登录）
     * @param code 状态码
     * @param rawData 原始数据
     * @return
     */
    @PostMapping(value = "/wxLogin", produces = "application/json")// consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public OpenidResp wxLogin(@RequestBody @Valid WxloginReq wxloginReq){ //,

  //  @PostMapping(value = "/wxLogin", produces = "application/json")// consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  //  public OpenidResp wxLogin(@RequestBody @Valid WxloginReq wxloginReq){ //,
/*                              @RequestParam(value = "rawData", required = false) String rawData,
                              @RequestParam(value = "signature",required = false) String signature) {*/
//		登录凭证校验。通过 wx.login 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程

        String code = wxloginReq.getCode();
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


            if(false && forObject.containsKey("errcode") && !forObject.getString("errcode").equals("0")){
                throw new BookNotFoundException("","请求微信 s/jscode2session 错我"+forObject);

            }


        } catch(HttpStatusCodeException e) {

            throw new BookNotFoundException("","请求微信 服务报错"+e.getMessage());

        }


        System.out.println(forObject);
        //接收微信接口服务 获取返回的参数

       // String openid =  "odzgk0ZpWx4NpQC4B1Tu1hnTgYwE";//
        String openid =  forObject.getString("openid");
        String sessionKey = forObject.getString("session_key");
        //校验签名 小程序发送的签名signature与服务器端生成的签名signature2 = sha1(rawData + sessionKey)



      //  String signature2 = DigestUtils.sha1Hex(rawData + sessionKey);   // String signature2 = DigestUtils.sha1Hreex(rawData + sessionKey);

   //     System.out.println(signature2);



/*        if (!signature.equals(signature2)) {
            throw new wx_login_errorException(1,"微信","签名校验失败");//ResultUtil.error(500, "签名校验失败");
        }*/

        WxloginReq.RawData rawData = wxloginReq.getRawData();

        System.out.println("rawData"+ rawData);
        System.out.println("code"+ code);
        // 用户非敏感信息：rawData
       // JSONObject rawDataJson = JSONObject.parseObject(rawData);
        //判断用户是否存在


        Optional<Openid> optional = openidRepository.findByOpenid(openid);
        Openid openid1 = null;
        if(optional.isEmpty()){
            // 用户信息入库
            String nickName =rawData.getNickName();// rawDataJson.getString("nickName");
            String avatarUrl =rawData.getAvatarUrl();// rawDataJson.getString("avatarUrl");
            Integer gender =rawData.getGender();// Integer.valueOf(rawDataJson.getString("gender"));

            openid1 = openidService.create(openid,nickName,gender,avatarUrl);





        }else{
            openid1 = optional.get();


        }

        OpenidResp openidResp = OpenidResp.from(openid1);
       // openidResp.add(linkTo(methodOn(OpenidRestController.class).linkUser(openid1.getOpenid(),null)).withRel("link_user_url"));
       // openidResp.add(linkTo(methodOn(OpenidRestController.class).merchants_settled(openid1.getOpenid(),null)).withRel("merchants_settled_url"));
        //openidResp.add(linkTo(methodOn(OpenidRestController.class).createUser(openid1.getOpenid(),null)).withRel("register_url"));


        openidResp.add(linkTo(methodOn(RealnameAuthRestController.class).postRealnameAuths(null)).withRel("realnameAuth"));



/*        Authentication authentication = authenticationTokenProvider.authenticate(
                new AuthenticationToken(openid1.getOpenid(), Collections.emptyList()));
        SecurityContextHolder.getContext().setAuthentication(authentication);*/


        String jwt = jwtUtils.generateJwtToken(0, openid);
        openidResp.setToken(jwt);

        System.out.println("====================:"+openid);
        System.out.println("====================:"+jwt);
        return openidResp;

    }
}