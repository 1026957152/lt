package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumIdentityType;
import com.lt.dom.repository.*;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserAuthorityServiceImpl {





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


        enumIdentityTypes = enumIdentityTypes.stream().filter(e->{
            Optional<UserAuthority> userAuthority = userAuthorityRepository.findByIdentityTypeAndIdentifier(e.getValue0(),e.getValue1());

            return userAuthority.isEmpty();

        }).collect(Collectors.toList());

        enumIdentityTypes.forEach(x->{
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

        if(userAuthorityOptional.isPresent()){
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



