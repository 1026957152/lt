package com.lt.dom.RealNameAuthentication;


import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.error.User_realname_auth_failureException;
import com.lt.dom.oct.MemberCertification;
import com.lt.dom.oct.Openid;
import com.lt.dom.oct.User;
import com.lt.dom.otcReq.RealnameAuthsReq;
import com.lt.dom.otcReq.UserPojo;
import com.lt.dom.otcenum.EnumIdentityType;
import com.lt.dom.repository.MemberCertificationRepository;
import com.lt.dom.repository.OpenidRepository;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.serviceOtc.OpenidServiceImpl;
import com.lt.dom.serviceOtc.UserServiceImpl;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RealNameAuthenticationServiceImpl {

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

            if(c){
                real.add(x);
            }else{
                notReal.add(x);
            }
        });
        return Pair.with(real,notReal);
    }








    public User postRealnameAuths(User user, RealnameAuthsReq realnameAuthsReq) {


        PhoneAuth auth = new PhoneAuth();
        auth.setIdCardName(realnameAuthsReq.getReal_name());
        auth.setIdCardNo(realnameAuthsReq.getId_card());
       // auth.setPhoneNo(realnameAuthsReq.get());

        if(!realnameAuthsReq.isLive_mode() || checkRealname(auth)){

            user = setRealname(user,realnameAuthsReq.getReal_name(),realnameAuthsReq.getId_card());
            return user;
        }else{

            throw new User_realname_auth_failureException(user.getId(),User.class.getSimpleName(),"用户实名认证失败");
        }



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

    public Pair<User,Openid> postWxRealnameAuths(Openid openid, RealnameAuthsReq realnameAuthsReq) {


        Optional<User> optionalUser = userRepository.findByPhone(realnameAuthsReq.getPhone());

        if(optionalUser.isPresent()){ // 如果手机号存在 且
            User user = optionalUser.get();
            if(!optionalUser.get().isRealNameVerified()){
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
}
