package com.lt.dom.serviceOtc;


import com.google.gson.Gson;
import com.lt.dom.JwtUtils;
import com.lt.dom.OctResp.UserWithTokenResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.Missing_documentException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.MerchantsSettledReq;
import com.lt.dom.otcReq.SettleAccountPojo;
import com.lt.dom.otcReq.SupplierPojo;
import com.lt.dom.otcReq.UserPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.vo.SupplierPojoVo;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class OpenidServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(OpenidServiceImpl.class);


    @Autowired
    private SettleAccountServiceImpl settleAccountService;

    @Autowired
    private UserServiceImpl userService;


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAuthorityServiceImpl userAuthorityService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private TempDocumentRepository tempDocumentRepository;


    @Autowired
    private OpenidRepository openidRepository;

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;



    @Autowired
    private IdGenServiceImpl idGenService;


    public Openid create(String openid, String nickName, Integer gender, String avatarUrl) {
        //添加用户的信息
        Openid user=new Openid();
        user.setOpenid(openid);
        user.setCode(idGenService.openidNo());
        user.setOpenid_name(nickName);
        user.setOpenid_gender(gender);
        user.setOpenid_image(avatarUrl);
        return openidRepository.save(user);
    }

    public Openid linkUser(Openid openid, User user) {



        userAuthorityService.checkWeixinBind(openid,user);
        User user_ = userService.userAuth(user,Arrays.asList(Pair.with(EnumIdentityType.weixin,openid.getOpenid())));

/*


        List<User> userList = userRepository.findByOpenid(openid.getOpenid());
        userRepository.saveAll(userList.stream().map(e->{
         //   e.setOpenid(null);
            e.setOpenidLink(false);
            return e;
        }).collect(Collectors.toList()));

        List<Openid> openids = openidRepository.findByUserId(user.getId());
        openidRepository.saveAll(openids.stream().map(x->{
            x.setUserId(null);
            x.setLink(false);

            return x;
        }).collect(Collectors.toList()));

        openid.setUserId(user.getId());
        openid.setLink(true);

      //  user.setOpenid(openid.getOpenid());
        user.setOpenidLink(true);
        userRepository.save(user);
*/

        return openid;

    }

    @Transactional
    public void unLinkUser(Openid openid) {
        logger.info("解除绑定 openid{}, link{}, userid{}",openid.getOpenid(),openid.getLink());

 /*       List<User> userList = userRepository.findByOpenid(openid.getOpenid());


        userRepository.saveAll(userList.stream().map(e->{
            e.setOpenid(null);
            e.setOpenidLink(false);
            return e;
        }).collect(Collectors.toList()));


        List<Openid> openids = openidRepository.findByUserIdIn(userList.stream().map(e->e.getId()).collect(Collectors.toList()));
        openidRepository.saveAll(openids.stream().map(x->{
            x.setUserId(0);
            x.setLink(false);
            return x;
        }).collect(Collectors.toList()));
*/
        Optional<UserAuthority> userAuthorityOptional = userAuthorityRepository.findByIdentityTypeAndIdentifier(EnumIdentityType.weixin,openid.getOpenid());
        if(userAuthorityOptional.isPresent()){
            userAuthorityRepository.delete(userAuthorityOptional.get());

        }else{
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到登录收取");
        }



    }

    public Openid setupData(String openid) {

        return create(openid,"程龙龙",1,"");

    }
}
