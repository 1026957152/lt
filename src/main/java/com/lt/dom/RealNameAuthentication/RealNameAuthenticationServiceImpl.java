package com.lt.dom.RealNameAuthentication;


import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.User_realname_auth_failureException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingSkuPojo;
import com.lt.dom.otcReq.RealnameAuthsReq;
import com.lt.dom.otcReq.UserPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.IdGenServiceImpl;
import com.lt.dom.serviceOtc.OpenidServiceImpl;
import com.lt.dom.serviceOtc.UserServiceImpl;
import com.lt.dom.thiirdAli.idcard.IdCard实名认证OcrService;
import com.lt.dom.util.CheckIdcardIsLegal;
import com.lt.dom.vo.UserVo;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class RealNameAuthenticationServiceImpl {
    Logger logger = LoggerFactory.getLogger(RealNameAuthenticationServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private OpenidRepository openidRepository;

    @Autowired
    private OpenidServiceImpl openidService;

    @Autowired
    private MemberCertificationRepository memberCertificationRepository;

    @Autowired
    private VerificationReportRepository verificationReportRepository;

    @Autowired
    private VerificationSessionRepository verificationSessionRepository;



    @Autowired
    private FileStorageServiceImpl fileStorageService;

    @Autowired
    private IdCard实名认证OcrService idCard实名认证OcrService;


    @Autowired
    private IdGenServiceImpl idGenService;


    UserService service
            = GitHubServiceGenerator.createService(UserService.class);



    public boolean checkRealname(PhoneAuth phoneAuth) {


        Optional<MemberCertification> memberCertificationOptional = memberCertificationRepository.findByNameAndIdCardNumber(phoneAuth.getIdCardName(),phoneAuth.getPhoneNo());

        if(memberCertificationOptional.isPresent()){
            return true;
        }


        Call<ResultPhoneAuth> callAsync = service.getUser("eugenp",phoneAuth);

        try {
            Response response = callAsync.execute();

            if(response.code() == 200){
                ResultPhoneAuth user = (ResultPhoneAuth)response.body();
                System.out.println(response.code()+"dddddddddddddddddddd"+response.errorBody());
                if(Integer.getInteger(user.getResultcode()) == Resultcode._0.getCode()){
                    MemberCertification memberCertification = new MemberCertification();
                    memberCertification.setName(user.getIdCardName());
                    memberCertification.setIdCardNumber(user.getIdCardNo());
                    memberCertification.setUpdate_at(LocalDate.now());
                    memberCertification = memberCertificationRepository.save(memberCertification);

                    return true;
                }else{
                    throw new User_realname_auth_failureException(0,ResultPhoneAuth.class.getSimpleName(),"实名验证失败");

                }
            }else{
                throw new User_realname_auth_failureException(0,ResultPhoneAuth.class.getSimpleName(),"实名验证失败,返回错我");

            }


        }catch (IOException E){
            throw new User_realname_auth_failureException(0,ResultPhoneAuth.class.getSimpleName(),"实名验证失败，网络/服务器异常");
        }



     /*

        callAsync.enqueue(new Callback<ResultPhoneAuth>() {
            @Override
            public void onResponse(Call<ResultPhoneAuth> call, Response<ResultPhoneAuth> response) {

                ResultPhoneAuth user = response.body();

                System.out.println("dddddddddddddddddddd"+response.errorBody());
                if(Integer.getInteger(user.getResultcode()) == Resultcode._0.getCode()){
                    MemberCertification memberCertification = new MemberCertification();
                    memberCertification.setName(user.getIdCardName());
                    memberCertification.setIdCardNumber(user.getIdCardNo());
                    memberCertification.setUpdate_at(LocalDate.now());
                    memberCertification = memberCertificationRepository.save(memberCertification);
                }



            }

            @Override
            public void onFailure(Call<ResultPhoneAuth> call, Throwable throwable) {
                System.out.println("错误了啊"+throwable);
            }
        });
        return true;*/


    }


    public Pair<List<PhoneAuth>, List<PhoneAuth>> bulkCheckRealname(List<PhoneAuth> travelerReqs) {

        List<PhoneAuth> real = new ArrayList<>();
        List<PhoneAuth> notReal = new ArrayList<>();
        travelerReqs.forEach(x->{

            boolean c = false;
            try{
                c = checkRealname(x);
            }catch (Exception e){
                e.printStackTrace();
            }

            if(true){
                real.add(x);
            }else{
                notReal.add(x);
            }
        });
        return Pair.with(real,notReal);
    }








    public void postRealnameAuths(User user, RealnameAuthsReq realnameAuthsReq) {


        VerificationReport verificationReport = new VerificationReport();

        verificationReport.setName(realnameAuthsReq.getName());
        verificationReport.setUser(user.getId());

        verificationReport.setCode(idGenService.nextId("vs_"));
        verificationReport.setType(EnumVerificationCheckType.id_number);

        verificationReport.setStatus(EnumVerificationStatus.processing);




        verificationReport = verificationReportRepository.save(verificationReport);

        if(realnameAuthsReq.getSelfie()!= null){
            fileStorageService.updateFromTempDocument(verificationReport.getCode(),realnameAuthsReq.getSelfie(), EnumDocumentType.liability_insurance);
        }
        if(realnameAuthsReq.getDocument()!= null){
            fileStorageService.updateFromTempDocument(verificationReport.getCode(),realnameAuthsReq.getDocument(),EnumDocumentType.license_for_opening_bank_account);
        }


        VerifiedOutputs verifiedOutputs = checkRealname(verificationReport,realnameAuthsReq);


        if(verifiedOutputs!= null){
            user = setRealname(user,realnameAuthsReq.getReal_name(),realnameAuthsReq.getId_card());

        }
       // verificationReport.setSelfie_document();
       // verificationReport.setSelfie_selfie();



        PhoneAuth auth = new PhoneAuth();
        auth.setIdCardName(realnameAuthsReq.getReal_name());
        auth.setIdCardNo(realnameAuthsReq.getId_card());
       // auth.setPhoneNo(realnameAuthsReq.get());

/*        if(true || !realnameAuthsReq.isLive_mode() || checkRealname(auth)){
            user = setRealname(user,realnameAuthsReq.getReal_name(),realnameAuthsReq.getId_card());
            return user;
        }else{
            throw new User_realname_auth_failureException(user.getId(),User.class.getSimpleName(),"用户实名认证失败");
        }*/
    }


    public VerifiedOutputs checkRealname(VerificationReport verificationReport, RealnameAuthsReq realnameAuthsReq) {







        if(verificationReport.getType().equals(EnumVerificationCheckType.id_number)){


            verificationReport.setStatus(EnumVerificationStatus.verified);
            verificationReport = verificationReportRepository.save(verificationReport);


            Boolean result = idCard实名认证OcrService.main(realnameAuthsReq.getReal_name(),realnameAuthsReq.getId_card());

           if(result){
               VerifiedOutputs verifiedOutputs = new VerifiedOutputs();
               verifiedOutputs.setId_number(realnameAuthsReq.getId_card());
               verifiedOutputs.setFirst_name(realnameAuthsReq.getReal_name());

               VerificationSession verificationSession = new VerificationSession();
               verificationSession.setName(realnameAuthsReq.getReal_name());
               verificationSession.setIdType(EnumIdType.身份证);
               verificationSession.setIdNumber(realnameAuthsReq.getId_card());
               verificationSession.setResultStatus(EnumVerificationResultStatus.verified);

               verificationSessionRepository.save(verificationSession);

               return verifiedOutputs;
           }else{
               VerificationSession verificationSession = new VerificationSession();
               verificationSession.setName(realnameAuthsReq.getReal_name());
               verificationSession.setIdType(EnumIdType.身份证);
               verificationSession.setIdNumber(realnameAuthsReq.getId_card());
               verificationSession.setResultStatus(EnumVerificationResultStatus.unverified);
               verificationSessionRepository.save(verificationSession);


               throw new BookNotFoundException(Enumfailures.user_realname_auth_failure,"用户实名认证失败");

           }

        }

        if(verificationReport.getType().equals(EnumVerificationCheckType.document)){

            return null;
        }
        throw new BookNotFoundException(Enumfailures.user_realname_auth_failure,"用户实名认证失败");

    }



        private User setRealname(User user,String real_name, String id_card){
        user.setRealName(real_name);
        user.setIdCard(id_card);
        user.setRealNameVerified(true);
        user.setIdCardType(1);
        user = userRepository.save(user);
        return user;
    }

    public User postWxRealnameAuths(User user, RealnameAuthsReq realnameAuthsReq) {


        user = setRealname(user,realnameAuthsReq.getReal_name(),realnameAuthsReq.getId_card());

        return user;

    }
    public User postWxRealnameAuths_for_real_name(UserVo userVo, RealnameAuthsReq realnameAuthsReq) {

        User user = userRepository.findById(userVo.getUser_id()).get();

        VerificationSession verificationSession = checkRealname(realnameAuthsReq.getName(),realnameAuthsReq.getId_card());
        if(verificationSession.getResultStatus().equals(EnumVerificationResultStatus.verified)){
            user = setRealname(user,realnameAuthsReq.getReal_name(),realnameAuthsReq.getId_card());

        }


        return user;

    }


    public User setupData(User user,String real_name, String id_card) {
        user = setRealname(user,real_name,id_card);

        return user;


    }

    public Pair<User,Openid> postWxRealnameAuths(Openid openid, RealnameAuthsReq realnameAuthsReq) {


        Optional<User> optionalUser = userRepository.findByPhone(realnameAuthsReq.getPhone());

        if(optionalUser.isPresent()){ // TODO 如果手机号存在
            User user = optionalUser.get();
            if(!optionalUser.get().isRealNameVerified()){ //TODO 但未实名
                user = setRealname(user,realnameAuthsReq.getReal_name(),realnameAuthsReq.getId_card());
            }
            openid.setLink(true);
            openid.setUserId(user.getId());
            openidRepository.save(openid);
            return Pair.with(user,openid);

        }else{
            UserPojo userPojo = new UserPojo();
            //userPojo.setFirst_name(wxlinkUserReq.getFirst_name());
            //userPojo.setLast_name(wxlinkUserReq.getLast_name());
            userPojo.setUsername(openid.getOpenid());

            userPojo.setPhone(realnameAuthsReq.getPhone());
            userPojo.setPassword("wxlinkUserReq.getUser_password()");
            userPojo.setRoles(Arrays.asList("ROLE_ADMIN"));

            User user = userService.createUser(userPojo,Arrays.asList(Pair.with(EnumIdentityType.identity_card,"")));

            user = setRealname(user,realnameAuthsReq.getReal_name(),realnameAuthsReq.getId_card());


            openidService.linkUser(openid,user);


            return Pair.with(user,openid);
        }





    }

    public void checkRealname(List<BookingSkuPojo.TravelerReq> travelerReqs) {


        List<VerificationSession> verifiedOutputs = travelerReqs.stream().map(e->{

            VerificationReport verificationReport = new VerificationReport();

            verificationReport.setName(e.getName());

            verificationReport.setCode(idGenService.nextId("vs_"));
            verificationReport.setId_number(e.getId_card());
            verificationReport.setType(EnumVerificationCheckType.id_number);

            verificationReport.setStatus(EnumVerificationStatus.processing);

            VerificationSession verifiedOutputs1 = checkRealname(verificationReport);

            return verifiedOutputs1;
        }).collect(Collectors.toList());




/*
        List<Map> aa = verifiedOutputs.stream().filter(x->x.getStatus().equals(EnumVerificationResultStatus.verified))
                .map(x->Map.of("id",x.getId_number(),"error",x.getError())).collect(toList());
*/

        List<Map> aaerr = verifiedOutputs.stream().filter(x->x.getResultStatus().equals(EnumVerificationResultStatus.unverified))
                .map(x->Map.of("id",x.getIdNumber(),"error",x.getLastError())).collect(toList());



/*        String greetings = String.format(
                "成功 %1$d, 失败 %2$d !",
                aa.size(),
                aaerr.size());*/


        if(aaerr.size()> 0){

            throw new BookNotFoundException(Enumfailures.resource_not_found,aaerr.toString());


        }


    }


    public VerificationSession checkRealname(String name,String id_number) {


            VerificationReport verificationReport = new VerificationReport();

            verificationReport.setName(name);

            verificationReport.setCode(idGenService.nextId("vs_"));
            verificationReport.setId_number(id_number);
            verificationReport.setType(EnumVerificationCheckType.id_number);

            verificationReport.setStatus(EnumVerificationStatus.processing);

            VerificationSession verifiedOutputs1 = checkRealname(verificationReport);


            return verifiedOutputs1;


    }



    private MemberCertification updateMemberCertification(String name, String Id_number) {

        Optional<MemberCertification> memberCertificationOptional = memberCertificationRepository.findByNameAndIdCardNumber(name,Id_number);
        MemberCertification memberCertification =null;

        if(memberCertificationOptional.isEmpty()){
            memberCertification = new MemberCertification();

        }else{
            memberCertification = memberCertificationOptional.get();
        }

        memberCertification.setName(name);
        memberCertification.setIdCardNumber(Id_number);
        memberCertification.setUpdate_at(LocalDate.now());
        memberCertification = memberCertificationRepository.save(memberCertification);



        return memberCertification;
    }


    private VerificationSession aliyun_chanal(String name, String Id_number) {
        VerificationSession verificationSession = new VerificationSession();
        verificationSession.setName(name);
        verificationSession.setIdType(EnumIdType.身份证);
        verificationSession.setIdNumber(Id_number);


        logger.info("调用阿里 实名认证服务");


        Boolean result = idCard实名认证OcrService.main(name,Id_number);

        if(result){


            MemberCertification memberCertification = updateMemberCertification(name,Id_number);
            VerifiedOutputs verifiedOutputs = new VerifiedOutputs();
            verifiedOutputs.setId_number(memberCertification.getIdCardNumber());
            verifiedOutputs.setFirst_name(memberCertification.getName());
            verifiedOutputs.setStatus(EnumVerificationResultStatus.verified);


            verificationSession.setResultStatus(EnumVerificationResultStatus.verified);
            verificationSession = verificationSessionRepository.save(verificationSession);

            return verificationSession;
        }else{

            verificationSession.setResultStatus(EnumVerificationResultStatus.unverified);
            verificationSession.setLastError(EnumIdentityLastError.id_number_mismatch);
            verificationSessionRepository.save(verificationSession);

            return verificationSession;
        }


    }




    private VerificationSession checkRealname(VerificationReport verificationReport) {
        Optional<VerificationSession> verificationSessionOptional = verificationSessionRepository.findByIdTypeAndNameAndIdNumber(EnumIdType.身份证,verificationReport.getName(),verificationReport.getId_number());
        if(verificationSessionOptional.isPresent()){


            VerificationSession verificationSession = verificationSessionOptional.get();

            return verificationSession;
       /*     if(verificationSession.getResultStatus().equals(EnumVerificationResultStatus.verified)){
                VerifiedOutputs verifiedOutputs = new VerifiedOutputs();
                verifiedOutputs.setId_number(verificationReport.getId_number());
                verifiedOutputs.setFirst_name(verificationReport.getName());
                verifiedOutputs.setStatus(EnumVerificationResultStatus.verified);
                return verifiedOutputs;
            }
            if(verificationSession.getResultStatus().equals(EnumVerificationResultStatus.unverified)){
                VerifiedOutputs verifiedOutputs = new VerifiedOutputs();
                verifiedOutputs.setId_number(verificationReport.getId_number());
                verifiedOutputs.setFirst_name(verificationReport.getName());
                verifiedOutputs.setStatus(EnumVerificationResultStatus.unverified);
                return verifiedOutputs;
            }*/
        }
        if(verificationReport.getType().equals(EnumVerificationCheckType.id_number)){
            logger.info("id_number 方式，实名认证，{},{}",verificationReport.getName(),verificationReport.getId_number());


            String checkIdCard = CheckIdcardIsLegal.checkIdCard(verificationReport.getId_number());

            if(!checkIdCard.equals("yes")){
                throw new BookNotFoundException(Enumfailures.user_realname_auth_failure,checkIdCard);

            }


            Optional<MemberCertification> memberCertificationOptional = memberCertificationRepository
                    .findByNameAndIdCardNumber(verificationReport.getName(),verificationReport.getId_number());

            if(memberCertificationOptional.isPresent()){
                VerificationSession verificationSession = new VerificationSession();
                verificationSession.setName(verificationReport.getName());
                verificationSession.setIdType(EnumIdType.身份证);
                verificationSession.setIdNumber(verificationReport.getId_number());
                verificationSession.setResultStatus(EnumVerificationResultStatus.verified);
                verificationSession = verificationSessionRepository.save(verificationSession);

                return verificationSession;
            }




            return  aliyun_chanal(verificationReport.getName(),verificationReport.getId_number());



        }

        if(verificationReport.getType().equals(EnumVerificationCheckType.document)){

        }
        throw new BookNotFoundException(Enumfailures.user_realname_auth_failure,"不支持的认证类型");

    }

}
