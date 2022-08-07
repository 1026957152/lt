package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.RoleResp;
import com.lt.dom.oct.Privilege;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.Role;
import com.lt.dom.repository.PrivilegeRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.RoleRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class RoleRestController {


    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;


    @GetMapping(value = "/suppliers/{PRODUCT_ID}/roles", produces = "application/json")
    public List<RoleResp> listSupplierRoles(@PathVariable long PRODUCT_ID) {

        List<Role> validatorOptional = roleRepository.findAll();

        return RoleResp.from(validatorOptional);
    }

    @GetMapping(value = "/roles", produces = "application/json")
    public List<Role> listRoles() {

        List<Role> validatorOptional = roleRepository.findAll();

        return validatorOptional;
    }


    @GetMapping(value = "/privileges", produces = "application/json")
    public List<Privilege> listPrivileges() {
        List<Privilege> validatorOptional = privilegeRepository.findAll();

        return validatorOptional;
    }
}