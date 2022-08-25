package com.lt.dom.serviceOtc;

import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.EnumEmployeeStatus;
import com.lt.dom.otcenum.EnumSupplierStatus;
import com.lt.dom.repository.EmployeeRepository;
import com.lt.dom.repository.RoleRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.vo.SupplierPojoVo;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    public Employee 成为员工(Supplier referral, User user){

        Employee royaltyRuleData = new Employee();
        royaltyRuleData.setCode(idGenService.employeeNo());
        royaltyRuleData.setSuplierId(referral.getId());
        royaltyRuleData.setUserId(user.getId());
        royaltyRuleData.setCreated_at(LocalDateTime.now());
        royaltyRuleData.setStatus(EnumEmployeeStatus.active);

        royaltyRuleData = employeeRepository.save(royaltyRuleData);
        return royaltyRuleData;

    }

    public Supplier put(Supplier supplier, SupplierPutPojo pojo) {

        supplier.setDesc(pojo.getDescription());
        return supplier;
    }

   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Supplier createSupplier(SupplierPojoVo pojo, EnumSupplierStatus status) {


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
        if(status.equals(EnumSupplierStatus.PendingApproval)){
            supplier.setReview(true);
        }
        supplier.setStatus(status);
        supplier = supplierRepository.save(supplier);
        return supplier;

    }



    public Triplet<Supplier,Employee,User> createEmployee(Supplier supplier, EmployerPojo employerPojo, List<String> hasEnumRoles) {


        UserPojo userPojo = new UserPojo();
        userPojo.setFirst_name(employerPojo.getFirst_name());
        userPojo.setLast_name(employerPojo.getLast_name());

        userPojo.setPhone(employerPojo.getPhone());

        userPojo.setNick_name(employerPojo.getName());
        userPojo.setRealName(employerPojo.getName());
        userPojo.setPassword(employerPojo.getPassword());
        userPojo.setRoles(hasEnumRoles);
        User user = userService.createUser(userPojo, Arrays.asList());


        Employee employee = 成为员工(supplier,user);
        return Triplet.with(supplier,employee,user);
    }



    public Triplet<Supplier,Employee,User> updateEmployee(Employee employee, EmployerUpdatePojo employerPojo) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(employee.getSuplierId());


        Optional<User> users = userRepository.findById(employee.getUserId());
        User user = users.get();

      //  List<Role> roleList = roleRepository.findByNameIn(employerPojo.getRoles());

        user = userService.createRoleIfNotFound(user,employerPojo.getRoles());
        user = userRepository.save(user);
        employerPojo.getRoles();
        return Triplet.with(optionalSupplier.get(),employee,user);
    }
}
