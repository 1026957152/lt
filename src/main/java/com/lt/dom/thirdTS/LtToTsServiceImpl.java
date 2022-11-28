package com.lt.dom.thirdTS;


import cn.gjing.EncryptionUtil;
import com.beust.jcommander.internal.Lists;
import com.lt.dom.OctResp.TermResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingSkuPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.ReservationRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.requestvo.BookingTypeTowhoVo;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.thirdPft.RequestPojo;
import com.lt.dom.thirdPft.domainPftTolt.PftToLvRet;
import com.lt.dom.thirdTS.domainLtToTs.LtReqTs退单审核通知;
import com.lt.dom.thirdTS.domainLtToTs.LtReqTs验证核销通知;
import com.lt.dom.thirdTS.domainLtToTs.TsRespLt退单审核通知;
import com.lt.dom.thirdTS.domainLtToTs.TsRespLt验证核销通知;
import com.lt.dom.thirdTS.domainTsToLt.*;
import com.lt.dom.vo.PlatUserVo;
import org.apache.commons.lang3.StringUtils;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.threeten.bp.LocalDate;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LtToTsServiceImpl {


    String baseUrl_请求地址 = "baseUrl_请求地址";//
    String 合作伙伴ID = "合作伙伴ID";//
    String 授权编码 = "授权编码";//

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReservationRepository reservationRepository;




    @Autowired
    private ThirdPartyServiceImpl thirdPartyService;


    @Autowired
    private BookingServiceImpl bookingService;


    @Autowired
    private FileStorageServiceImpl fileStorageService;

    @Autowired
    private TermServiceImpl termService;
    @Autowired
    private PriceServiceImpl priceService;


    @Autowired
    private SupplierRepository supplierRepository;





    //TODO 验证核销通知(第三方通知天时同城)

    public TsRespLt验证核销通知 ltReqTs验证核销通知(LtReqTs验证核销通知.ToLtReqTs验证核销通知 to) {




        LtReqTs验证核销通知 ltReqTs验证核销通知 = to.To();
        ltReqTs验证核销通知.set_pid(合作伙伴ID);




        String url = String.format("%s/back-center/login",baseUrl_请求地址);

    //    POST  / HTTP/1.1
     //   Host: demo.json
      //  Content-Type:application/x-www-form-urlencoded
       // method=item_list&amount=2&another_orders_id=91474102737444075801&my_orders_id=&codes=&_sig=B8C3AA43C70D7FE464CC180AA78E3F90&_pid=4&format=json

            // 发送请求参数
            Map<String,Object> paramJson = new HashMap<>();
            //  paramJson.put("username","admin111");
            //  paramJson.put("password","admin123");
          //  paramJson.put("username",username);
         //   paramJson.put("password",password);
            RestTemplate restTemplate = new RestTemplate();





        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        //  headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
      //  headers.set("Authorization","bearer "+xhToYxdLoginResponse.getAccessToken() );





        ltReqTs验证核销通知= getTsRespLt验证核销通知(授权编码,EnumMethord.validate,ltReqTs验证核销通知);


        HttpEntity<LtReqTs验证核销通知> entity = new HttpEntity<LtReqTs验证核销通知>(ltReqTs验证核销通知,headers);


            ResponseEntity<TsRespLt验证核销通知> responseEntity = restTemplate.postForEntity(url,entity, TsRespLt验证核销通知.class);


            System.out.println("getStatusCode"+responseEntity.getStatusCode());
            TsRespLt验证核销通知 buffer = responseEntity.getBody();
            System.out.println("登录获得的 accessToken"+buffer.getMessage());

            return buffer;

        }




    public   LtReqTs验证核销通知 getTsRespLt验证核销通知(String 授权编码,EnumMethord methord, LtReqTs验证核销通知 data){



      //  111&_pid=4&format=json&item_id=12345965&method=item_orders&mobile=15597207761&orders_id=2019122011460002&players=[0=[id_number=632128200104030018&mobile=15597207761&name=韩紫阳]&1=[id_number=460100197605131824&mobile=15597207761&name=韩阳]]&size=2&start_date=1576822388&111



        SortedMap sortedMap = new TreeMap();
        sortedMap.put("method", data.getMethod());
        sortedMap.put("amount", data.getAmount());
        sortedMap.put("another_orders_id", data.getAnother_orders_id());
        sortedMap.put("my_orders_id", data.getMy_orders_id());
        sortedMap.put("codes", data.getCodes());
     //   sortedMap.put("_sig", data.get_sig());
        sortedMap.put("_pid", data.get_pid());
        sortedMap.put("format", data.getFormat());


        String aa  = 授权编码+"&"+获得拼接EncodeSign(sortedMap)+"&"+授权编码;


        String _sig =  EncryptionUtil.md5(aa);

        data.set_sig(_sig);

        return data;
    }
    public static String 获得拼接EncodeSign(SortedMap<String,String> map){
/*        if(StringUtils.isEmpty(key)){
            throw new RuntimeException("签名key不能为空");
        }*/
        Set<Map.Entry<String, String>> entries = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        List<String> values = Lists.newArrayList();

        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            String k = String.valueOf(entry.getKey());
            String v = String.valueOf(entry.getValue());
            if (StringUtils.isNotEmpty(v) && entry.getValue() !=null && !"sign".equals(k) && !"key".equals(k)) {
                values.add(k + "=" + v);
            }
        }
        //   values.add("key="+ key);
        String sign = StringUtils.join(values, "&");

        return sign;
        //return encodeByMD5(sign);
    }

































    //TODO 验证核销通知(第三方通知天时同城)

    public TsRespLt退单审核通知 ltReqTs退单审核通知(LtReqTs退单审核通知.ToLtReqTs验证核销通知 to) {




        LtReqTs退单审核通知 ltReqTs验证核销通知 = to.To();
        ltReqTs验证核销通知.set_pid(合作伙伴ID);

        String url = String.format("%s/back-center/login",baseUrl_请求地址);

        //    POST  / HTTP/1.1
        //   Host: demo.json
        //  Content-Type:application/x-www-form-urlencoded
        // method=item_list&amount=2&another_orders_id=91474102737444075801&my_orders_id=&codes=&_sig=B8C3AA43C70D7FE464CC180AA78E3F90&_pid=4&format=json


        RestTemplate restTemplate = new RestTemplate();



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        ltReqTs验证核销通知= getLtReqTs退单审核通知(授权编码,EnumMethord.validate,ltReqTs验证核销通知);

        HttpEntity<LtReqTs退单审核通知> entity = new HttpEntity<LtReqTs退单审核通知>(ltReqTs验证核销通知,headers);

        ResponseEntity<TsRespLt退单审核通知> responseEntity = restTemplate.postForEntity(url,entity, TsRespLt退单审核通知.class);

        System.out.println("getStatusCode"+responseEntity.getStatusCode());
        TsRespLt退单审核通知 buffer = responseEntity.getBody();
        System.out.println("登录获得的 accessToken"+buffer.getMessage());

        return buffer;

    }



    public   LtReqTs退单审核通知 getLtReqTs退单审核通知(String 授权编码,EnumMethord methord, LtReqTs退单审核通知 data){



        //  111&_pid=4&format=json&item_id=12345965&method=item_orders&mobile=15597207761&orders_id=2019122011460002&players=[0=[id_number=632128200104030018&mobile=15597207761&name=韩紫阳]&1=[id_number=460100197605131824&mobile=15597207761&name=韩阳]]&size=2&start_date=1576822388&111



        SortedMap sortedMap = new TreeMap();
        sortedMap.put("method", data.getMethod());

        sortedMap.put("serial_no", data.getSerial_no());
        sortedMap.put("orders_id", data.getOrders_id());
        sortedMap.put("type", data.getType());
        sortedMap.put("message", data.getMessage());


        sortedMap.put("_pid", data.get_pid());
        sortedMap.put("format", data.getFormat());


        String aa  = 授权编码+"&"+获得拼接EncodeSign(sortedMap)+"&"+授权编码;


        String _sig =  EncryptionUtil.md5(aa);

        data.set_sig(_sig);

        return data;
    }


}
