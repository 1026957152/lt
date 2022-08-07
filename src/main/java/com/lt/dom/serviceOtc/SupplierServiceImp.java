package com.lt.dom.serviceOtc;

import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.repository.EmployeeRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.util.CodeConfig;
import com.lt.dom.util.VoucherCodes;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupplierServiceImp {
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private EmployeeRepository employeeRepository;


    public Employee 成为员工(Supplier referral, User user){

        Employee royaltyRuleData = new Employee();
        royaltyRuleData.setCode(idGenService.employeeNo());
        royaltyRuleData.setSuplierId(referral.getId());
        royaltyRuleData.setUserId(user.getId());
        royaltyRuleData = employeeRepository.save(royaltyRuleData);
        return royaltyRuleData;

    }

    public Supplier put(Supplier supplier, SupplierPutPojo pojo) {

        supplier.setDesc(pojo.getDescription());
        return supplier;
    }

   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Supplier createSupplier(SupplierPojo pojo) {

        Supplier supplier = new Supplier();
        supplier.setType(pojo.getType());
        supplier.setBusiness_type(pojo.getBusiness_type());
/*        supplier.setStreet(pojo.getStreet());
        supplier.setLocality(pojo.getLocality());
        supplier.setRegion(pojo.getRegion());
        supplier.setState(pojo.getState());*/
        supplier.setLocation(pojo.getLocation());
        supplier.setLocationName(pojo.getLocationName());
        supplier.setLatitude(Float.valueOf(pojo.getLat()));
        supplier.setLongitude(Float.valueOf(pojo.getLng()));

        supplier.setName(pojo.getSupplierName());
        supplier.setCode(idGenService.supplierNo());
        supplier.setDesc(pojo.getDesc());
        supplier = supplierRepository.save(supplier);
        return supplier;

    }



    public Triplet<Supplier,Employee,User> createEmployee(Supplier supplier, EmployerPojo employerPojo) {


        UserPojo userPojo = new UserPojo();
        userPojo.setFirst_name(employerPojo.getFirst_name());
        userPojo.setUsername(employerPojo.getLast_name());
        userPojo.setPhone(employerPojo.getPhone());
        userPojo.setUsername(employerPojo.getName());
        userPojo.setPassword(employerPojo.getPassword());
        userPojo.setRoles(employerPojo.getRoles());
        User user = userService.createUser(userPojo);


        Employee employee = 成为员工(supplier,user);
        return Triplet.with(supplier,employee,user);
    }

    public Employee updateEmployee(Employee employee, EmployerUpdatePojo employerPojo) {
        return employee;
    }
}
