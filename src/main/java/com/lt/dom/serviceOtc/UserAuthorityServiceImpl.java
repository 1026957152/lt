package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumIdentityType;
import com.lt.dom.repository.*;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserAuthorityServiceImpl {


    private static final Logger logger = LoggerFactory.getLogger(UserAuthorityServiceImpl.class);



    @Autowired
    private OpenidRepository openidRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IdGenServiceImpl idGenService;
    @Autowired
    UserAuthorityRepository userAuthorityRepository;
    @Autowired
    GuideServiceImpl guideService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AssetServiceImpl assetService;



    public User userAuth(User finalUser, List<Pair<EnumIdentityType,String>> enumIdentityTypes) {


        List<Pair<EnumIdentityType,String>> list = new ArrayList<>();
        enumIdentityTypes.stream().forEach(e->{


            //TODO 原来的 user 绑定的渠道 ，全部关闭
            Optional<UserAuthority> optionalUserAuthority_ = userAuthorityRepository.findByIdentityTypeAndUserId(e.getValue0(),finalUser.getId());
            if(optionalUserAuthority_.isPresent()){
                UserAuthority userAuthority1 = optionalUserAuthority_.get();
                logger.debug("成功 解除原来绑定的内容 type{}, id{},user{}",userAuthority1.getIdentityType(),userAuthority1.getIdentifier(),userAuthority1.getUserId());
                userAuthority1.setUserId(null);
                userAuthorityRepository.save(userAuthority1);
            }

            //TODO 新渠道不需新建的，则绑定user
            logger.debug("解除原来绑定的内容 type{}, id{}",e.getValue0(),e.getValue1());
            Optional<UserAuthority> optionalUserAuthority = userAuthorityRepository.findByIdentityTypeAndIdentifier(e.getValue0(),e.getValue1());
            if(optionalUserAuthority.isPresent()){
                UserAuthority userAuthority1 = optionalUserAuthority.get();
                logger.debug("成功 解除原来绑定的内容 type{}, id{},user{}",userAuthority1.getIdentityType(),userAuthority1.getIdentifier(),userAuthority1.getUserId());
                userAuthority1.setUserId(finalUser.getId());
                userAuthorityRepository.save(userAuthority1);
            }else{
                list.add(e);
            }
        });

/*
        enumIdentityTypes = enumIdentityTypes.stream().filter(e->{
            Optional<UserAuthority> userAuthority = userAuthorityRepository.findByIdentityTypeAndIdentifier(e.getValue0(),e.getValue1());


            return userAuthority.isEmpty();

        }).collect(Collectors.toList());
*/






        list.forEach(x->{
            UserAuthority userAuthority = new UserAuthority();
            userAuthority.setUserId(finalUser.getId());


            EnumIdentityType type = x.getValue0();
            String identifier = x.getValue1();
            userAuthority.setIdentityType(type);
            switch (type){
                case phone:
                    userAuthority.setIdentifier(identifier);
                    userAuthority.setCredential(passwordEncoder.encode("123"));

                    userAuthorityRepository.save(userAuthority);
                    break;
                case weixin:


                    userAuthority.setIdentifier(identifier);
                    userAuthority.setCredential(passwordEncoder.encode("123"));
                    userAuthorityRepository.save(userAuthority);



                    break;
                case identity_card:
                    userAuthority.setIdentifier(identifier);
                    userAuthority.setCredential(passwordEncoder.encode("123"));

                    userAuthorityRepository.save(userAuthority);
                    break;
            }



        });

        return finalUser;
    }


    public Boolean checkWeixinBind(Openid openid, User user) {

        Optional<UserAuthority> userAuthorityOptional = userAuthorityRepository.findByIdentityTypeAndIdentifierAndUserId(EnumIdentityType.weixin,openid.getOpenid(),user.getId());

        return userAuthorityOptional.isPresent();
    }

    public Optional<Openid> checkWeixinBind(User user) {


        return checkWeixinBind(user.getId());

    }

    public Optional<Openid> checkWeixinBind(String credential) {
        Optional<UserAuthority> userAuthorityOptional = userAuthorityRepository.findByIdentityTypeAndIdentifier(EnumIdentityType.weixin,credential);

        if(userAuthorityOptional.isPresent()){
            Optional<Openid> optionalOpenid = openidRepository.findByOpenid(userAuthorityOptional.get().getIdentifier());

            return optionalOpenid;
        }

        return Optional.empty();
    }

    public Optional<User>  getWeixinBindUser(Openid openid) {
        Optional<UserAuthority> userAuthorityOptional = userAuthorityRepository.findByIdentityTypeAndIdentifier(EnumIdentityType.weixin,openid.getOpenid());

        if(userAuthorityOptional.isPresent() && userAuthorityOptional.get().getUserId()!= null){

            Optional<User> user = userRepository.findById(userAuthorityOptional.get().getUserId());

            return user;

        }

        return Optional.empty();



    }

    public Optional<Openid> checkWeixinBind(Long user_id) {

        Optional<UserAuthority> userAuthorityOptional = userAuthorityRepository.findByIdentityTypeAndUserId(EnumIdentityType.weixin,user_id);

        if(userAuthorityOptional.isPresent()){
            Optional<Openid> optionalOpenid = openidRepository.findByOpenid(userAuthorityOptional.get().getIdentifier());

            return optionalOpenid;
        }

        return Optional.empty();

    }
}



