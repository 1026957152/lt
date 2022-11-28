package com.lt.dom.controllerOct.Axh;

import com.google.gson.*;

import com.lt.dom.controllerOct.Axh.model.*;
import com.lt.dom.credit.PullRequest;
import com.lt.dom.credit.RequestCredit;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.Axh.PullFromYxdRequest;
import com.lt.dom.oct.Axh.XydToXhPushRequest;
import com.lt.dom.oct.Axh.XydToXhPushRequestJsonFit;
import com.lt.dom.otcenum.EnumBookingStatus;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.otcenum.enum_.EnumXhPushRequestStatus;
import com.lt.dom.repository.Axh.PullFromYxdRequestRepository;
import com.lt.dom.repository.Axh.XydToXhPushRequestRepository;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class XhToYxdService {
    String baseUrl = "http://www-transform.yulinrongzi.cn:81/api-xinyidai";




    public static LocalDateTime token_time = LocalDateTime.now();

    public static XhToYxdLoginResponse older_token = null;

     String username = "91610800719764087X";
     String password = "ylnsh403";


    @Autowired
    PullFromYxdRequestRepository pullFromYxdRequestRepository;

    @Autowired
    XydToXhPushRequestRepository xydToXhPushRequestRepository;

    @Autowired
    FileStorageServiceImpl fileStorageService;

    public XhToYxdLoginResponse loginRecent(){


        String url = String.format("%s/back-center/login",baseUrl);


        // 发送请求参数
        Map<String,Object> paramJson = new HashMap<>();
        //  paramJson.put("username","admin111");
        //  paramJson.put("password","admin123");
        paramJson.put("username",username);
        paramJson.put("password",password);
        RestTemplate restTemplate = new RestTemplate();



        System.out.println("url :"+ url);

        ResponseEntity<XhToYxdLoginResponse> responseEntity = restTemplate.postForEntity(url,paramJson, XhToYxdLoginResponse.class);
        System.out.println("getStatusCode"+responseEntity.getStatusCode());
        XhToYxdLoginResponse buffer = responseEntity.getBody();
        System.out.println("登录获得的 accessToken"+buffer.getAccessToken());
        older_token = buffer;

        return buffer;

    }


    public XhToYxdLoginResponse login(){


        String url = String.format("%s/back-center/login",baseUrl);


        // 发送请求参数
        Map<String,Object> paramJson = new HashMap<>();
      //  paramJson.put("username","admin111");
      //  paramJson.put("password","admin123");
        paramJson.put("username",username);
        paramJson.put("password",password);
        RestTemplate restTemplate = new RestTemplate();



        System.out.println("url :"+ url);


        ResponseEntity<XhToYxdLoginResponse> responseEntity = restTemplate.postForEntity(url,paramJson, XhToYxdLoginResponse.class);
        System.out.println("getStatusCode"+responseEntity.getStatusCode());
        XhToYxdLoginResponse buffer = responseEntity.getBody();

        if(!ObjectUtils.isEmpty(buffer.getAccessToken())){
            token_time = LocalDateTime.now().plusMinutes(10);
            older_token = buffer;

        }else{
            throw new BookNotFoundException(Enumfailures.not_found,"登录失败了");
        }
        System.out.println(buffer);

        return buffer;

    }


    public YxdToXHResponse creditWaitConfirmVO(CreditWaitConfirmVO code, String accessToken){


        if(!isLoging()){

            login();

        }



        String url = String.format("%s/xydfinanceproductorderinfo/creditWaitConfirm",baseUrl);

        System.out.println("url 我的:"+ url);
        System.out.println("url 我的:"+ accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("Authorization","bearer "+accessToken );


        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<CreditWaitConfirmVO> entity = new HttpEntity<CreditWaitConfirmVO>(code,headers);



        try{
            ResponseEntity<YxdToXHResponse> responseEntity = restTemplate.postForEntity(url,entity, YxdToXHResponse.class);
            System.out.println("getStatusCode"+responseEntity.getStatusCode());


            if(responseEntity.getStatusCode() == HttpStatus.OK){


                YxdToXHResponse buffer = responseEntity.getBody();
                System.out.println(buffer);
                if(buffer.getRespCode() == 0){

                    System.out.println("状态改变成功"+ buffer.getRespMsg());
                }else{
                    System.out.println("状态改变失败"+ buffer.getRespMsg());
                    throw new BookNotFoundException(Enumfailures.not_found,"状态改变失败:"+ buffer.getRespMsg());

                }
                return buffer;

            }else{

                throw new BookNotFoundException(Enumfailures.not_found,"请求信易贷操作失败");

            }
        }catch (Exception e){
            e.printStackTrace();

            if(e instanceof HttpClientErrorException.Unauthorized){
                login();

            }else{
            }
            throw e;

        }


    }

    public YxdToXHResponse creditWaitConfirmVO(PullFromYxdRequest xydToXhPushRequest, @RequestBody CreditWaitConfirmVO code, String accessToken){


        if(!isLoging()){

            login();

        }



        String url = String.format("%s/xydfinanceproductorderinfo/creditWaitConfirm",baseUrl);

        System.out.println("url 我的:"+ url);
        System.out.println("url 我的:"+ accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
      //  headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Authorization","bearer "+accessToken );

/*        HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);

        // 发送请求参数
        Map<String,Object> paramJson = new HashMap();
        paramJson.put("orderId",code.getOrderId_申请id());*/

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<CreditWaitConfirmVO> entity = new HttpEntity<CreditWaitConfirmVO>(code,headers);



        try{
            ResponseEntity<YxdToXHResponse> responseEntity = restTemplate.postForEntity(url,entity, YxdToXHResponse.class);
            System.out.println("getStatusCode"+responseEntity.getStatusCode());


            if(responseEntity.getStatusCode() == HttpStatus.OK){


                YxdToXHResponse buffer = responseEntity.getBody();
                System.out.println(buffer);
                if(buffer.getRespCode() == 0){

                    xydToXhPushRequest.setStatus_xh(EnumXhPushRequestStatus.Approved);

                    pullFromYxdRequestRepository.save(xydToXhPushRequest);
                    System.out.println("状态改变成功"+ buffer.getRespMsg());
                }else{
                    System.out.println("状态改变失败"+ buffer.getRespMsg());
                    throw new BookNotFoundException(Enumfailures.not_found,"状态改变失败:"+ buffer.getRespMsg());

                }
                return buffer;

            }else{

                throw new BookNotFoundException(Enumfailures.not_found,"请求信易贷操作失败");

            }
        }catch (Exception e){
            e.printStackTrace();

            if(e instanceof HttpClientErrorException.Unauthorized){
                login();

            }else{
            }
            throw e;

        }


    }




    public YxdToXHResponse insReject(PullFromYxdRequest xydToXhPushRequest,@RequestBody InsReject code){

        XhToYxdLoginResponse xhToYxdLoginResponse =     loginRecent();



        String url = String.format("%s/xydfinanceproductorderinfo/insReject",baseUrl);



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        //  headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Authorization","bearer "+xhToYxdLoginResponse.getAccessToken() );


        HttpEntity<InsReject> entity = new HttpEntity<InsReject>(code,headers);



/*        // 发送请求参数
        Map<String,Object> paramJson = new HashMap();
        paramJson.put("code","code.getCode()");*/

        try{

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<YxdToXHResponse> responseEntity = restTemplate.postForEntity(url,entity, YxdToXHResponse.class);
            System.out.println("getStatusCode"+responseEntity.getStatusCode());

            if(responseEntity.getStatusCode() == HttpStatus.OK){


                YxdToXHResponse buffer = responseEntity.getBody();
                System.out.println(buffer);
                if(buffer.getRespCode() == 0){

                    xydToXhPushRequest.setStatus_xh(EnumXhPushRequestStatus.Rejected);

                    pullFromYxdRequestRepository.save(xydToXhPushRequest);
                    System.out.println("状态改变成功"+ buffer.getRespMsg());
                }else{
                    System.out.println("状态改变失败"+ buffer.getRespMsg());
                    throw new BookNotFoundException(Enumfailures.not_found,"状态改变失败:"+ buffer.getRespMsg());

                }
                return buffer;

            }else{

                throw new BookNotFoundException(Enumfailures.not_found,"请求信易贷操作失败");

            }
        }catch (Exception e){

            e.printStackTrace();

            throw e;
        }



    }

    public YxdToXHResponse insReject(InsReject code){




        XhToYxdLoginResponse xhToYxdLoginResponse =     loginRecent();



        String url = String.format("%s/xydfinanceproductorderinfo/insReject",baseUrl);



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("Authorization","bearer "+xhToYxdLoginResponse.getAccessToken() );


        HttpEntity<InsReject> entity = new HttpEntity<InsReject>(code,headers);



        try{

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<YxdToXHResponse> responseEntity = restTemplate.postForEntity(url,entity, YxdToXHResponse.class);
            System.out.println("getStatusCode"+responseEntity.getStatusCode());

            if(responseEntity.getStatusCode() == HttpStatus.OK){


                YxdToXHResponse buffer = responseEntity.getBody();
                System.out.println(buffer);
                if(buffer.getRespCode() == 0){


                    System.out.println("状态改变成功"+ buffer.getRespMsg());
                }else{
                    System.out.println("状态改变失败"+ buffer.getRespMsg());

                }
                return buffer;

            }else{

                throw new BookNotFoundException(Enumfailures.not_found,"请求信易贷操作失败");

            }
        }catch (Exception e){

            e.printStackTrace();

            throw e;
        }



    }

    private boolean isLoging() {

        if(token_time.isAfter(LocalDateTime.now())){

            return true;
        }
        return false;
    }


    public YxdToXHResponse addClinchInfo(PullFromYxdRequest xydToXhPushRequest, @RequestBody AddClinchInfoVO code){


        String url = String.format("%s/xydfinanceproductorderinfo/addClinchInfo",baseUrl);




        XhToYxdLoginResponse xhToYxdLoginResponse =    loginRecent();







        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","bearer "+xhToYxdLoginResponse.getAccessToken() );
        HttpEntity<AddClinchInfoVO> entity = new HttpEntity<AddClinchInfoVO>(code,headers);


        try{

            System.out.println("这里是 token:"+older_token.getAccessToken());

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<YxdToXHResponse> responseEntity = restTemplate.postForEntity(url,entity, YxdToXHResponse.class);

            System.out.println("getStatusCode"+responseEntity.getStatusCode());





            if(responseEntity.getStatusCode() == HttpStatus.OK){


                YxdToXHResponse buffer = responseEntity.getBody();
                System.out.println(buffer);
                if(buffer.getRespCode() == 0){

                    xydToXhPushRequest.setStatus_xh(EnumXhPushRequestStatus.Approved);

                    pullFromYxdRequestRepository.save(xydToXhPushRequest);
                    System.out.println("状态改变成功:"+ buffer.getRespMsg());
                }else{



                    throw new BookNotFoundException(Enumfailures.not_found,"状态改变失败:"+ buffer.getRespMsg());


                }
                return buffer;

            }else{

                throw new BookNotFoundException(Enumfailures.not_found,"请求信易贷操作失败");

            }


        }catch (Exception e){

            if(e instanceof HttpClientErrorException.Unauthorized){
                login();
            }
            e.printStackTrace();

            throw e;
        }





    }



   // @Scheduled(fixedDelay =1000*60*1)
    // @Scheduled(cron = "${erwin.cron:0/2 * * * * ?}")
    public void cronTaskYmlDemo() {
        PullRequest pullRequest = getXydfinanceproductorderinfo();

        if(pullRequest != null){
            System.out.println("================="+ pullRequest.toString());

        }else{
            System.out.println("没有获得啊啊啊");
        }
    }

    public PullRequest getXydfinanceproductorderinfo(){


        String url = String.format("%s/xydfinanceproductorderinfo/list",baseUrl);



        XhToYxdLoginResponse xhToYxdLoginResponse =     loginRecent();




        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","bearer "+xhToYxdLoginResponse.getAccessToken() );
      //  HttpEntity<AddClinchInfoVO> entity = new HttpEntity<AddClinchInfoVO>(code,headers);

        RestTemplate restTemplate = new RestTemplate();
        // build the request
      HttpEntity entity = new HttpEntity(headers);


        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", 1);
        params.put("limit", 10);
     //   params.put("access_token", "clientVersion");
      //  params.put("entName", "");
     //   params.put("status", 0);


        try{

// make an HTTP GET request with headers
            ResponseEntity<PullRequest> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    PullRequest.class,
                    params
            );



            //ResponseEntity<PullRequest> responseEntity = restTemplate.getForEntity(url,entity, PullRequest.class);
            System.out.println("getStatusCode"+response.getStatusCode());
            PullRequest buffer = response.getBody();

            List<PullFromYxdRequest> dataDTOList = buffer.getData();

            if(!dataDTOList.isEmpty()) {

                List<PullFromYxdRequest> pullFromYxdRequestList_新的 = dataDTOList.stream().map(e -> {
                    PullFromYxdRequest pullFromYxdRequest = new PullFromYxdRequest();
                    pullFromYxdRequest = e;
                    pullFromYxdRequest.setCreateDate(LocalDateTime.now());
                    return pullFromYxdRequest;
                }).collect(Collectors.toList());


                List<PullFromYxdRequest> pullFromYxdRequestList1_旧的 = pullFromYxdRequestRepository.findAllByIdXIn(dataDTOList.stream().map(e -> {

                    return e.getIdX();
                }).collect(Collectors.toList()));

                Map<Integer, PullFromYxdRequest> 已经存在的 = pullFromYxdRequestList1_旧的.stream().collect(Collectors.toMap(e -> e.getIdX(), e -> e));


                //   pullFromYxdRequestList.stream().filter(e->已经存在的.contains(e.getIdX()) ).collect(Collectors.toList());

                pullFromYxdRequestList_新的 = pullFromYxdRequestRepository.saveAll(pullFromYxdRequestList_新的.stream()
                        .map(e -> {

                            if (已经存在的.containsKey(e.getIdX())) {
                                PullFromYxdRequest p = 已经存在的.get(e.getIdX());
                                if (p.getUpdateTime() != e.getUpdateTime()) {
                                    p.setUpdateTime(e.getUpdateTime());
                                    p.setStatus(e.getStatus());
                                    return p;
                                } else {
                                    return p;
                                }
                            }
                            return e;


                        }).collect(Collectors.toList()));
                System.out.println(buffer);

                return buffer;
            }else{
                throw new BookNotFoundException(Enumfailures.not_found,"请求信易贷返回为空");

            }
        }catch (Exception e){
            e.printStackTrace();

            if (e instanceof HttpClientErrorException.Unauthorized){
                login();
            }
            throw e;
        }



    }



/*
    @Retryable(value = SQLException.class)
    void retryServiceWithRecovery(String sql) throws SQLException;

    @Recover
    void recover(SQLException e, String sql);

*/




    public static Resource getTestFile() throws IOException {
        Path testFile = Files.createTempFile("test-file", ".txt");
        System.out.println("Creating and Uploading Test File: " + testFile);
        Files.write(testFile, "Hello World !!, This is a test file.".getBytes());
        return new FileSystemResource(testFile.toFile());
    }

    public static Resource saveTempFile(MultipartFile file, String s) throws IOException {
        Path testFile = Files.createTempFile(file.getOriginalFilename(),null);
       // System.out.println("Creating and Uploading Test File: " + testFile);
      //  Files.write(testFile, "Hello World !!, This is a test file.".getBytes());
        return new FileSystemResource(testFile.toFile());
    }



    public YxdToXHResponse file_upload(@RequestBody UploadVO code,MultipartFile file){


        fileStorageService.saveWithFileName(file,file.getOriginalFilename());


        Resource resource = fileStorageService.load(file.getOriginalFilename());
        String url = String.format("%s/file/upload",baseUrl);


        System.out.println("url 我的:"+ url);
        System.out.println("url 我的:"+ older_token.getAccessToken());


        if(!isLoging()){
            return null;
        }


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        //headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        //  headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Authorization","bearer "+older_token.getAccessToken() );



       MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

     /*     try {
          body.add("file", new InputStreamResource(file.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        body.add("file",resource);// code.getFile());
        body.add("module", code.getModule());
        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<YxdToXHResponse> responseEntity = restTemplate.postForEntity(url,requestEntity, YxdToXHResponse.class);
        System.out.println("getStatusCode"+responseEntity.getStatusCode());
        YxdToXHResponse buffer = responseEntity.getBody();

        System.out.println(buffer);

        return buffer;

    }


    public void putData(String js) {


        XydToXhPushRequestJsonFit xydToXhPushRequestJsonFit = new Gson().fromJson(js, XydToXhPushRequestJsonFit.class);

        XydToXhPushRequest xydToXhPushRequest = new XydToXhPushRequest();
        xydToXhPushRequest.setJson(new Gson().toJson(xydToXhPushRequestJsonFit));
        xydToXhPushRequest.setOrderIdX申请id(xydToXhPushRequestJsonFit.getOrderId());
        xydToXhPushRequest.setCreatedDate(LocalDateTime.now());
        xydToXhPushRequestRepository.save(xydToXhPushRequest);


    }




    public void setupData(){


        String aa  = " {\n" +
                "                \"baseInfo\": {\n" +
                "                    \"financeAttachmentDto\": null,\n" +
                "                    \"accountId\": 1528545907919578907,\n" +
                "                    \"address\": \"陕西省西安市高新区丈八五路高科尚都.摩卡1幢1单元15层11510号\",\n" +
                "                    \"authorizeTime\": 1663558604000,\n" +
                "                    \"busLicense\": \"\",\n" +
                "                    \"certificationTime\": 1663558604000,\n" +
                "                    \"createTime\": 1663558445000,\n" +
                "                    \"creditCode\": \"916100005637708187\",\n" +
                "                    \"creditRating\": \"CR5\",\n" +
                "                    \"currencyCode\": \"1\",\n" +
                "                    \"disabled\": 0,\n" +
                "                    \"enabled\": true,\n" +
                "                    \"entGdsszb\": 10000,\n" +
                "                    \"entName\": \"陕西西部资信股份有限公司\",\n" +
                "                    \"entType\": \"1110\",\n" +
                "                    \"entYgsl\": 10000,\n" +
                "                    \"entYysr\": 10000,\n" +
                "                    \"entZcze\": 10000,\n" +
                "                    \"financeAttachment\": \"[]\",\n" +
                "                    \"id\": 107397,\n" +
                "                    \"identification\": \"230107197205250228\",\n" +
                "                    \"isAuthorize\": \"1\",\n" +
                "                    \"isBondIssue\": \"0\",\n" +
                "                    \"isCertified\": \"1\",\n" +
                "                    \"isDomesticListing\": \"0\",\n" +
                "                    \"isHongkongListing\": \"0\",\n" +
                "                    \"isListedCompany\": \"0\",\n" +
                "                    \"legalRepresentative\": \"姜海欧\",\n" +
                "                    \"linkEmail\": null,\n" +
                "                    \"linkMan\": null,\n" +
                "                    \"linkPhone\": \"15029297929\",\n" +
                "                    \"logo\": \"\",\n" +
                "                    \"mainIndustryCode\": \"0111\",\n" +
                "                    \"operatingStatus\": \"1\",\n" +
                "                    \"password\": \"$2a$10$YAEGUz0LeuFrrEtflV1v2.SzIZMseFXxaYKwreh2N4nC9l.jo4hwe\",\n" +
                "                    \"phone\": \"15029297929\",\n" +
                "                    \"registeredCapital\": 10000.0,\n" +
                "                    \"setupDate\": 946656000000,\n" +
                "                    \"shareholderLayers\": null,\n" +
                "                    \"updateBy\": \"1528545907919578907\",\n" +
                "                    \"updateTime\": 1663558445000,\n" +
                "                    \"userId\": 1528545907919578907\n" +
                "                },\n" +
                "                \"orderId\": 414,\n" +
                "                \"taxInf\": [],\n" +
                "                \"productInfo\": {\n" +
                "                    \"applyCount\": \"0\",\n" +
                "                    \"createBy\": \"91610800719764087X\",\n" +
                "                    \"createTime\": 1663899254000,\n" +
                "                    \"disabled\": 0,\n" +
                "                    \"guarantyWay\": \"1 \",\n" +
                "                    \"id\": 156,\n" +
                "                    \"institutionId\": 9,\n" +
                "                    \"liaison\": \"余耀\",\n" +
                "                    \"loanAmountHigh\": \"500.00\",\n" +
                "                    \"loanAmountLow\": \"1.00\",\n" +
                "                    \"loanLimitLong\": \"36\",\n" +
                "                    \"loanLimitShort\": \"3\",\n" +
                "                    \"loanRateHigh\": 8.0,\n" +
                "                    \"loanRateLow\": 3.85,\n" +
                "                    \"productDetails\": \"<p _mstvisible=\\\"10\\\" _msthash=\\\"20490\\\" _msttexthash=\\\"6933371315\\\">\\\"小 V 税贷\\\"以小微企业、个体工商户在国家税务部门的税务数据为核心，结合其他行内外多维大数据，通过建立风控模型，为榆阳区辖区域内符合条件的小微企业、小微企业 主、个体工商户等自然人或法人提供的线上电子渠道自助申请，大数据风险筛查，风险模型自动评级授信，纯线上或线上线下一体化等方式完成授信、合同签订、贷款支用、贷后 风险监测等核心业务环节，用于借款人生产经营等用途的线上数字普惠贷款。</p>\",\n" +
                "                    \"productLogo\": \"http://111.20.184.51:81/upload-files/1/1268859028401577986/d0332648-489b-4bc2-b921-79b940847bb311.png\",\n" +
                "                    \"productName\": \"小V税贷\",\n" +
                "                    \"productType\": \"1\",\n" +
                "                    \"publishTime\": \"2022-09-23 10:17:13\",\n" +
                "                    \"status\": \"2\",\n" +
                "                    \"telephone\": \"19929129030\",\n" +
                "                    \"tenantId\": 1,\n" +
                "                    \"updateBy\": \"91610800719764087X\",\n" +
                "                    \"updateTime\": 1663899254000,\n" +
                "                    \"userId\": 1268859028401577986,\n" +
                "                    \"verifyTime\": \"2022-09-23 10:25:46\"\n" +
                "                },\n" +
                "                \"instInfo\": {\n" +
                "                    \"account\": \"91610800719764087X\",\n" +
                "                    \"accountId\": 1268859028401577986,\n" +
                "                    \"createBy\": \"admin\",\n" +
                "                    \"createTime\": 1591354541000,\n" +
                "                    \"disabled\": \"0\",\n" +
                "                    \"id\": 9,\n" +
                "                    \"instAddress\": \"陕西省榆林市榆阳区长城北路2号\",\n" +
                "                    \"instCreditCode\": \"91610800MA70DPXH8H\",\n" +
                "                    \"instEmail\": \"C6CA39A82891D3A26B6FFBD7140D5AF88A84AB3AA7134531050300D06DE1F2F9\",\n" +
                "                    \"instItemType\": \"yh\",\n" +
                "                    \"instLandline\": \"1B35CD1417207971F621FE6783099EA7800F811DB2551EAAC4AE395D3E487095\",\n" +
                "                    \"instLicense\": \"http://10.127.149.22:81/upload-files/1/1268859028401577986/7d362349-1abc-411e-83be-54baee2d56d02副本.png\",\n" +
                "                    \"instLinkPerson\": \"云丹\",\n" +
                "                    \"instLinkPhone\": \"19909120299\",\n" +
                "                    \"instLogo\": \"http://10.127.149.22:81/upload-files/1/1268859028401577986/d65cb450-9668-49bc-aa85-71e7325d64af1.png\",\n" +
                "                    \"instName\": \"陕西榆林农村商业银行股份有限公司\",\n" +
                "                    \"instType\": \"jrjg\",\n" +
                "                    \"tenantId\": 1,\n" +
                "                    \"updateBy\": \"91610800719764087X\",\n" +
                "                    \"updateTime\": 1662436398000,\n" +
                "                    \"userId\": 1\n" +
                "                }\n" +
                "            }";


        String js = "{\"baseInfo\":{\"accountId\":1265918116818087937,\"address\":\"陕西省西安市高新区丈八五路高科尚都.摩卡1幢1单元15层11510号\",\"authorizeTime\":1590653541000,\"busLicense\":\"http://111.20.184.51:81/upload-files/1/1/1a2d0fd9-4c72-408c-85d3-c90a45bdfd57xibuzixin-logo.png\",\"certificationTime\":1590653541000,\"createTime\":1590610173000,\"creditCode\":\"916100005637708187\",\"creditRating\":\"CR7\",\"currencyCode\":\"1\",\"disabled\":0,\"enabled\":true,\"entGdsszb\":111,\"entName\":\"陕西西部资信股份有限公司\",\"entType\":\"9900\",\"entYgsl\":11,\"entYysr\":2000,\"entZcze\":11,\"financeAttachment\":\"[{\\\"status\\\":\\\"success\\\",\\\"name\\\":\\\"图片.jpg\\\",\\\"size\\\":2668,\\\"percentage\\\":100,\\\"uid\\\":1622700786568,\\\"raw\\\":{\\\"uid\\\":1622700786568},\\\"response\\\":{\\\"datas\\\":\\\"http://192.168.10.114:91/finance/1/1265918116818087937/9dd4571f-d706-494d-90c6-056fdd8e26e1图片.jpg\\\",\\\"resp_code\\\":0,\\\"resp_msg\\\":\\\"上传成功\\\"}}]\",\"id\":1,\"identification\":\"2301************28\",\"isAuthorize\":\"1\",\"isBondIssue\":\"1\",\"isCertified\":\"1\",\"isDomesticListing\":\"1\",\"isHongkongListing\":\"1\",\"isListedCompany\":\"1\",\"legalRepresentative\":\"姜海欧\",\"linkEmail\":\"18691218181@163.com\",\"linkMan\":\"董立强\",\"linkPhone\":\"16601109305\",\"logo\":\"http://111.20.184.51:81/upload-files/1/1/d6f3259a-8969-4b70-9b42-a203d8a4b761xibuzixin-logo.png\",\"mainIndustryCode\":\"7295\",\"operatingStatus\":\"1\",\"password\":\"$2a$10$NxMvpPVzWfQmuP/hLSDVf.t6JAU7jyXebAzFwET2dDd5SR6dYv/tO\",\"phone\":\"16601109305\",\"registeredCapital\":111.000000,\"setupDate\":1254326400000,\"shareholderLayers\":6,\"updateBy\":\"1265918116818087937\",\"updateTime\":1622700040000,\"userId\":1265918116818087937},\"orderId\":\"146\",\"taxInf\":[{\"data\":[{\"NSRMC\":\"-\",\"PJND\":\"-\",\"PDJB\":\"-\",\"SHXYDM\":\"-\"}],\"metadata\":{\"NSRMC\":\"企业名称\",\"SHXYDM\":\"统一社会信用代码\",\"PJND\":\"评审年度\",\"PDJB\":\"纳税信用等级\"}},{\"data\":[{\"NSRMC\":\"-\",\"RDRQ\":\"-\",\"SHXYDM\":\"-\",\"SFFZC\":\"-\"}],\"metadata\":{\"NSRMC\":\"纳税人名称\",\"SHXYDM\":\"社会信用代码\",\"RDRQ\":\"认定日期\",\"SFFZC\":\"是否非正常\"}},{\"data\":[{\"NSRMC\":\"-\",\"SFQS\":\"-\",\"DJXH\":\"-\",\"NDYF\":\"-\",\"QSJE\":\"-\",\"SHXYDM\":\"-\"}],\"metadata\":{\"DJXH\":\"登记序号\",\"NSRMC\":\"企业名称\",\"SHXYDM\":\"统一社会信用代码\",\"SFQS\":\"当前是否欠税\",\"QSJE\":\"欠税金额\",\"NDYF\":\"月份\"}},{\"data\":[{\"RKYF\":\"-\",\"NSRMC\":\"-\",\"SDSYNSE\":\"-\",\"SDSSJJE\":\"-\",\"ZZSSJJE\":\"-\",\"ZZSYNSE\":\"-\",\"SHXYDM\":\"-\"}],\"metadata\":{\"NSRMC\":\"企业名称\",\"SHXYDM\":\"统一社会信用代码\",\"ZZSYNSE\":\"增值税应纳税金额（万元）\",\"ZZSSJJE\":\"增值税实际缴税金额（万元）\",\"SDSYNSE\":\"企业所得税应纳税金额（万元）\",\"SDSSJJE\":\"企业所得税实际缴税金额（万元）\",\"RKYF\":\"缴税日期\"}}],\"productInfo\":{\"applyCount\":\"1\",\"createBy\":\"916108007486148973\",\"createTime\":1611651290000,\"disabled\":0,\"guarantyWay\":\"2\",\"id\":92,\"institutionId\":8,\"liaison\":\"郑经理\",\"loanAmountHigh\":\"2000.00\",\"loanAmountLow\":\"1.00\",\"loanLimitLong\":\"36\",\"loanLimitShort\":\"6\",\"loanRateHigh\":6.84,\"loanRateLow\":6.84,\"productDetails\":\"<p><span style=\\\"color: rgb(0, 0, 0);\\\">发放的用于大、中、小、微企业用于日常生产经营周转的贷款，包括周转性流动资金贷款和临时性流动资金贷款；</span></p>\",\"productLogo\":\"http://111.20.184.51:81/upload-files/1/1/758aa5c4-978b-43e7-9d91-1dd07a36693b子洲农商银行logo.jpg\",\"productName\":\"流动资金贷款\",\"productType\":\"1\",\"publishTime\":\"2021-01-26 16:54:42\",\"status\":\"2\",\"telephone\":\"18791540999\",\"tenantId\":1,\"updateBy\":\"916108007486148973\",\"updateTime\":1611651290000,\"userId\":1268857938289385474,\"verifyTime\":\"2021-01-29 08:34:16\"},\"instInfo\":{\"account\":\"916108007486148973\",\"accountId\":1268857938289385474,\"createBy\":\"admin\",\"createTime\":1591354281000,\"disabled\":\"0\",\"id\":8,\"instAddress\":\"陕西省榆林市子洲县大理路西段子洲农商银行\",\"instCreditCode\":\"916108007486148973\",\"instEmail\":\"734656168@qq.com\",\"instItemType\":\"yh\",\"instLandline\":\"0912-7221030\",\"instLicense\":\"http://111.20.184.51:81/upload-files/1/1/239888db-9bff-4a87-870c-4240daabf7d2758aa5c4-978b-43e7-9d91-1dd07a36693b子洲农商银行logo.jpg\",\"instLinkPerson\":\"赵武瑞经理\",\"instLinkPhone\":\"15596569333\",\"instLogo\":\"http://111.20.184.51:81/upload-files/1/1/758aa5c4-978b-43e7-9d91-1dd07a36693b子洲农商银行logo.jpg\",\"instName\":\"子洲农村商业银行股份有限公司\",\"instType\":\"jrjg\",\"tenantId\":1,\"updateBy\":\"916108007486148973\",\"updateTime\":1611651418000,\"userId\":1}}";


      //  putData(js);
     //   putData(aa);


        String json = "\t{\n" +
                "\t\t\t\"id\": 150,\n" +
                "\t\t\t\"userId\": \"1273874405540089857\",\n" +
                "\t\t\t\"tenantId\": null,\n" +
                "\t\t\t\"instId\": 9,\n" +
                "\t\t\t\"entId\": 7,\n" +
                "\t\t\t\"entName\": \"子洲县鑫润生物科技有限责任公司\",\t\n" +
                "\t\t\t\"creditCode\": null,\n" +
                "\t\t\t\"linkMan\": null,\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\"linkPhone\": \"18392291173\",\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\"evaluateId\": 324,\n" +
                "\t\t\t\"productId\": 63,\n" +
                "\t\t\t\"productName\": \"固定资产贷款\",\t\t\t\t\t\t\t\n" +
                "\t\t\t\"productLogo\": \"http://111.20.184.51:81/upload-files/1/1/f969aefc-9b9b-47f8-bd69-5d7e60855b32附件1logo.jpg\",\n" +
                "\t\t\t\"productType\": \"1\",\n" +
                "\t\t\t\"loanAmountLow\": 50,\n" +
                "\t\t\t\"loanAmountHigh\": 20000,\n" +
                "\t\t\t\"loanRateLow\": 5.5,\n" +
                "\t\t\t\"loanRateHigh\": 10.08,\n" +
                "\t\t\t\"loanLimitShort\": 6,\n" +
                "\t\t\t\"loanLimitLong\": 120,\n" +
                "\t\t\t\"guarantyWay\": \"4\",\n" +
                "\t\t\t\"businessType\": null,\n" +
                "\t\t\t\"backMoneyType\": null,\n" +
                "\t\t\t\"loanTimeLimit\": null,\n" +
                "\t\t\t\"liaison\": null,\n" +
                "\t\t\t\"telephone\": \"18392291173\",\n" +
                "\t\t\t\"productDetails\": \"固定资产贷款是指向辖内经国家工商行政管理机关核准登记的企业法人和其他经济组织发放的，用于企业固定资产项目投资的人民币贷款。可采用保证、抵押、质押担保的方式进行。\",\t\n" +
                "\t\t\t\"publishTime\": \"2020-07-17 09:45:41\",\n" +
                "\t\t\t\"verifyTime\": \"2020-07-23 09:06:17\",\n" +
                "\t\t\t\"bringTogetherTime\": \"2022-09-21 10:41:19\",\n" +
                "\t\t\t\"startTime\": \"2022-09-21 10:40:46\",\n" +
                "\t\t\t\"endTime\": \"2022-10-21 10:40:46\",\n" +
                "\t\t\t\"status\": \"2\",\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\"instRejectReason\": null,\n" +
                "\t\t\t\"createBy\": \"\",\n" +
                "\t\t\t\"createTime\": \"2022-09-21 10:40:13\",\n" +
                "\t\t\t\"updateBy\": \"18392291173\",\n" +
                "\t\t\t\"updateTime\": \"2022-09-21 10:41:19\",\n" +
                "\t\t\t\"disabled\": 0,\n" +
                "\t\t\t\"subjectName\": null,\n" +
                "\t\t\t\"subjectValue\": null,\n" +
                "\t\t\t\"financingPurpose\": null,\n" +
                "\t\t\t\"repaymentSource\": null,\n" +
                "\t\t\t\"instName\": null,\n" +
                "\t\t\t\"creditRating\": \"CR7\"\t\t\t\t\t\t\n" +
                "\t\t}";


/*        Gson gson_ = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,  new JsonSerializer<LocalDateTime>() {
            @Override
            public JsonElement serialize(LocalDateTime date, Type typeOfSrc, JsonSerializationContext context) {
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                return new JsonPrimitive(date.format(formatter)); // "yyyy-mm-dd"
            }
        }).create();
        PullFromYxdRequest pullFromYxdRequest = gson_.fromJson(json,PullFromYxdRequest.class);


        pullFromYxdRequestRepository.save(pullFromYxdRequest);*/
    }

}