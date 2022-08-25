package com.lt.dom.serviceOtc;


import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.Departures;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.Supplier;
import com.lt.dom.otcenum.EnumBussinessType;
import com.lt.dom.otcenum.EnumRole;
import com.lt.dom.repository.BookingRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl {


    @Autowired
    private BookingRuleRepository bookingRuleRepository;

    public List<EnumRole> get(Supplier supplier) {

        if(supplier.getBusiness_type().equals(EnumBussinessType.government_entity)){
            return Arrays.asList();
        }
        if(supplier.getBusiness_type().equals(EnumBussinessType.company)){
            return Arrays.asList(EnumRole.ROLE_VOUCHER_REDEEMER);
        }

        if(supplier.getBusiness_type().equals(EnumBussinessType.non_profit)){
            return Arrays.asList(EnumRole.ROLE_VOUCHER_REDEEMER);
        }


        throw new BookNotFoundException("未知的机构 类型，找不到相关的 权限列表","");
    }


    public boolean valid(List<String> roles, EnumRole roleTravelAgency) {

        System.out.println("user 的权限"+roles);
        System.out.println("需要验证的 role"+roleTravelAgency.name());
        return roles.stream().filter(e->roleTravelAgency.name().equals(e)).findAny().isPresent();

    }
}
