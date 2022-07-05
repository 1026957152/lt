package com.lt.dom.serviceOtc;

import com.lt.dom.oct.*;
import com.lt.dom.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImp {


    @Autowired
    private EmployeeRepository employeeRepository;


    public Employee 成为员工(Supplier referral, User user){

        Employee royaltyRuleData = new Employee();

        royaltyRuleData.setSuplierId(referral.getNo());
        royaltyRuleData.setUserId(user.getNo());
        royaltyRuleData = employeeRepository.save(royaltyRuleData);
        return royaltyRuleData;

    }
}
