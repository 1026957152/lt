package com.lt.dom.serviceOtc;


import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.Departures;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.User;
import com.lt.dom.otcReq.RealnameAuthsReq;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RealnameAuthsServiceImpl {


    @Autowired
    private UserRepository userRepository;


    public User postRealnameAuths(User user, RealnameAuthsReq realnameAuthsReq) {

        user.setRealName(realnameAuthsReq.getRealName());
        user.setIdCard(realnameAuthsReq.getIdCard());
        user.setRealNameVerified(true);
        user.setIdCardType(1);

        user = userRepository.save(user);
        return user;
    }
}
