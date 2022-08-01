package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcReq.SupplierPojo;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.repository.EmployeeRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.util.CodeConfig;
import com.lt.dom.util.VoucherCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegisterServiceImpl {


    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private UserRepository userRepository;

    public Supplier register(SupplierPojo pojo,MultipartFile multipartFile) {

        User user = new User();
        user.setPhone(pojo.getPhone());

        user.setFirst_name(pojo.getFirstName());
        user.setLast_name(pojo.getLastName());
        String no = VoucherCodes.generate(new CodeConfig(8,null,"use",null,null));
        user.setNo(no);
        user = userRepository.save(user);


        Supplier supplier = new Supplier();
        supplier.setName(pojo.getSupplierName());
        supplier.setCode(idGenService.supplierNo());

        supplier = supplierRepository.save(supplier);
        return supplier;
    }

    public void register(String phone,String userF,String userL, String supplierN) {

        User user = new User();
        user.setPhone(phone);

        user.setFirst_name(userF);
        user.setLast_name(userL);
        String no = VoucherCodes.generate(new CodeConfig(8,null,"use",null,null));
        user.setNo(no);
        user = userRepository.save(user);


        Supplier supplier = new Supplier();
        supplier.setName(supplierN);
        supplier.setCode(idGenService.supplierNo());

        supplier = supplierRepository.save(supplier);

    }

    public void registerEmployee(String phone, String firstName, String lastName, long supplierId) {
        User user = new User();
        user.setPhone(phone);

        user.setFirst_name(firstName);
        user.setLast_name(lastName);
        String no = VoucherCodes.generate(new CodeConfig(8,null,"use",null,null));
        user.setNo(no);
        user = userRepository.save(user);


        Employee supplier = new Employee();
        supplier.setPhone(phone);
        supplier.setFirstName(firstName);
        supplier.setLastName(lastName);
        supplier.setSuplierId(supplierId);
        supplier.setUserId(user.getId());

        supplier = employeeRepository.save(supplier);
    }

    public void delete(Supplier supplier) {

        supplierRepository.delete(supplier);


    }


}
