package com.lt.dom.serviceOtc;

import com.lt.dom.OctResp.EmployeeEditResp;
import com.lt.dom.OctResp.SupplierEditResp;
import com.lt.dom.controllerOct.EmployeeRestController;
import com.lt.dom.domain.SettleAccount;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.vo.SupplierPojoVo;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImp {

    Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private FileStorageServiceImpl fileStorageService;
    @Autowired
    private UserAuthorityRepository userAuthorityRepository;


    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;




    public Employee 成为员工(Supplier referral, User user, EmployerLinkPojo o){

        List<Employee> employees = employeeRepository.findBySuplierId(referral.getId());


        if(employees.stream() // 如果存在，且 不活跃，则激活。
                .filter(x->x.getStatus().equals(EnumEmployeeStatus.active))
                .filter(x->x.getUserId().equals(user.getId()))
             .findAny().isPresent()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"已经是公司 职工");
        }



        employeeRepository.saveAll(employeeRepository.findAllByUserId(user.getId()).stream() // TODO 把仙子服务的全部给 不服务
                .filter(x->x.getStatus().equals(EnumEmployeeStatus.active)).map(e->{
                    e.setStatus(EnumEmployeeStatus.inactive);
                    return e;
                }).collect(Collectors.toList()));


        Optional<Employee> employeeOptional = employees.stream() // TODO 曾经服务过，拿过恢复过来，
                .filter(x->x.getUserId() == user.getId())
                .filter(x->x.getStatus().equals(EnumEmployeeStatus.inactive)).findAny();

        if(employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            employee.setStatus(EnumEmployeeStatus.active);
            employee.setPhone(o.getPhone());
            employee.setName(o.getName());
            employee =employeeRepository.save(employee);
            return employee;
        }




        Employee employee = new Employee();
        employee.setCode(idGenService.employeeNo());
        employee.setSuplierId(referral.getId());
        employee.setUserId(user.getId());
        employee.setCreated_at(LocalDateTime.now());
        employee.setStatus(EnumEmployeeStatus.active);
        employee.setPhone(o.getPhone());
        employee.setName(o.getName());

        employee = employeeRepository.save(employee);
        return employee;

    }

    @Transactional
    public Supplier edit(Supplier supplier, SupplierEditResp.InfoTab pojo) {

        supplier.setDesc(pojo.getDesc());



        supplier.setRegistered_address(pojo.getRegistered_address());
        supplier.setRegistered_name(pojo.getRegistered_name());
        supplier.setUniformSocialCreditCode(pojo.getUniformSocialCreditCode());


        supplier.setName(pojo.getName());
        supplier.setSlug(pojo.getSlug());

        SettleAccount settleAccount = new SettleAccount();
        settleAccount.setAccountName(pojo.getSettleAccount().getAccountName());
        settleAccount.setBankAccount(pojo.getSettleAccount().getBankAccountNumber());
        settleAccount.setBankName(pojo.getSettleAccount().getBankName());

        supplier.setSettleAccount(settleAccount);


        Address address = new Address();
        address.setAddressLine1(pojo.getLocation().getAddress());
        address.setLatitude(pojo.getLocation().getLatitude());
        address.setLongitude(pojo.getLocation().getLongitude());

        supplier.setLocation(address);

        supplier.setSlug(pojo.getSlug());
        supplierRepository.save(supplier);



        if(pojo.getLogo() != null){
            fileStorageService.updateFromTempDocument(supplier.getCode(),pojo.getLogo(),EnumDocumentType.supllier_logo);
        }
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
      //  supplier.setLocation(pojo.getLocation());
        supplier.setLocationName(pojo.getLocationName());
        supplier.setLatitude(pojo.getLat());
        supplier.setLongitude(pojo.getLng());

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

    public Triplet<Supplier,Employee,User> linkEmployee(Supplier supplier, EmployerLinkPojo employerPojo, List<String> hasEnumRoles) {


        String auth_code = CryptoServiceImpl.decode(employerPojo.getAuth_code());

/*

        Optional<UserAuthority> optional = userAuthorityRepository.findByIdentityTypeAndIdentifier(EnumIdentityType.phone,employerPojo.getPhone());
        if(optional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
        }
        UserAuthority userAuthority = optional.get();

        Optional<User> optionalUser = userRepository.findById(userAuthority.getUserId());
*/

        Optional<User> optionalUser = userRepository.findByCode(auth_code);

        if(optionalUser.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
        }
        User user = optionalUser.get();


        Employee employee = 成为员工(supplier,user,employerPojo);

     //   List<String> hasEnumRoles  = employerPojo.getRoles().stream().filter(x-> enumRoles.contains(x)).collect(Collectors.toList());

        logger.debug("这里 看提交来的  {}",employerPojo.getRoles());

        user = userService.createRoleIfNotFound(user,employerPojo.getRoles());
        user = userRepository.save(user);


        return Triplet.with(supplier,employee,user);
    }




    public Employee createEmployee(Supplier supplier, EmployerPojo employerPojo, List<String> hasEnumRoles) {

        Optional<User> optional = userRepository.findByPhone(employerPojo.getPhone());
        if(optional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到");
        }
        User user = optional.get();

        Employee employee = 成为员工(supplier,user, null);
        return employee;
/*
        UserPojo userPojo = new UserPojo();
        userPojo.setFirst_name(employerPojo.getFirst_name());
        userPojo.setLast_name(employerPojo.getLast_name());

        userPojo.setPhone(employerPojo.getPhone());

        userPojo.setNick_name(employerPojo.getName());
        userPojo.setRealName(employerPojo.getName());
        userPojo.setPassword(employerPojo.getPassword());
        userPojo.setRoles(hasEnumRoles);
        User user = userService.createUser(userPojo, Arrays.asList());


        Employee employee = 成为员工(supplier,user);*/
     //   return Triplet.with(supplier,employee,user);
    }



    public Triplet<Supplier,Employee,User> updateEmployee(Employee employee, EmployeeEditResp employerPojo) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(employee.getSuplierId());


        Optional<User> users = userRepository.findById(employee.getUserId());
        User user = users.get();


        user = userService.createRoleIfNotFound(user,employerPojo.getRoles());

        user = userRepository.save(user);
        employerPojo.getRoles();
        employee.setName(employerPojo.getName());
        employee.setPhone(employerPojo.getPhone());

        employee = employeeRepository.save(employee);
        return Triplet.with(optionalSupplier.get(),employee,user);
    }
 /*   public Triplet<Supplier,Employee,User> updateEmployeeBackup(Employee employee, EmployerUpdatePojo employerPojo) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(employee.getSuplierId());


        Optional<User> users = userRepository.findById(employee.getUserId());
        User user = users.get();

      //  List<Role> roleList = roleRepository.findByNameIn(employerPojo.getRoles());

        user = userService.createRoleIfNotFound(user,employerPojo.getRoles());
        user = userRepository.save(user);
        employerPojo.getRoles();
        return Triplet.with(optionalSupplier.get(),employee,user);
    }*/
}
