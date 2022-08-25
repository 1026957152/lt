package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.repository.OpenidRepository;
import com.lt.dom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpenidServiceImpl {


    @Autowired
    private OpenidRepository openidRepository;
    @Autowired
    private UserRepository userRepository;


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

        List<Openid> openids = openidRepository.findByUserId(user.getId());
        openidRepository.saveAll(openids.stream().map(x->{
            x.setUserId(0);
            x.setLink(false);

            return x;
        }).collect(Collectors.toList()));

        openid.setUserId(user.getId());
        openid.setLink(true);

        user.setOpenid(openid.getOpenid());
        user.setOpenidLink(true);
        userRepository.save(user);

        return openidRepository.save(openid);

    }
}
