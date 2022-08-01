package com.lt.dom.serviceOtc;

import com.lt.dom.oct.*;
import com.lt.dom.otcReq.SupplierPojo;
import com.lt.dom.otcReq.SupplierPutPojo;
import com.lt.dom.repository.EmployeeRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.util.CodeConfig;
import com.lt.dom.util.VoucherCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImp {
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private IdGenServiceImpl idGenService;


    @Autowired
    private EmployeeRepository employeeRepository;


    public Employee 成为员工(Supplier referral, User user){

        Employee royaltyRuleData = new Employee();

        royaltyRuleData.setSuplierId(referral.getId());
        royaltyRuleData.setUserId(user.getId());
        royaltyRuleData = employeeRepository.save(royaltyRuleData);
        return royaltyRuleData;

    }

    public Supplier put(Supplier supplier, SupplierPutPojo pojo) {

        supplier.setDesc(pojo.getDescription());
        return supplier;
    }

    public Supplier createSupplier(SupplierPojo pojo) {




        Supplier supplier = new Supplier();
        supplier.setName(pojo.getSupplierName());
        supplier.setCode(idGenService.supplierNo());
        supplier.setDesc(pojo.getDesc());
        supplier = supplierRepository.save(supplier);
        return supplier;

    }
}
