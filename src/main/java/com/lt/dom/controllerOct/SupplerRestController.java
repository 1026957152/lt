package com.lt.dom.controllerOct;

import com.lt.dom.domain.CouponTemplatePojoList;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.EmployerPojo;
import com.lt.dom.otcReq.InvitationPojo;
import com.lt.dom.otcReq.SupplierPojo;
import com.lt.dom.repository.InvitationsRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.serviceOtc.IdGenServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@RestController
@RequestMapping("/oct/suppliers/")
public class SupplerRestController {


    @Autowired
    private IdGenServiceImpl idGenService;


    @Autowired
    private SupplierRepository supplierRepository;


    @Autowired
    private InvitationsRepository invitationsRepository;


    @Operation(summary = "1、增删改查")
    @GetMapping(value = "/{SUPPLIER_ID}", produces = "application/json")
    public Supplier getComponentRight_Validator(@PathVariable String SUPPLIER_ID) {
        Supplier componentRight = new Supplier();
        return componentRight;
    }

    @Operation(summary = "1、增删改查")
    @PostMapping(value = "", produces = "application/json")
    public Supplier createSupplier(@RequestBody SupplierPojo pojo) {



        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("model", ignoreCase());

        Supplier probe = new Supplier();
        probe.setNo("bmw");
        Example<Supplier> example = Example.of(probe, modelMatcher);
            boolean resourceById = supplierRepository.exists(example);


            if (!resourceById) {

                System.out.println("抛出异常");
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
            }else {
                Supplier supplier = new Supplier();
                supplier.setName("旅投");
                supplier.setNo(idGenService.nextId("supplier"));


                supplier = supplierRepository.save(supplier);
                return supplier;
            }



    }

    @Operation(summary = "1、增删改查")
    @PutMapping(value = "/{SUPPLIER_ID}", produces = "application/json")
    public Supplier updateSupplier(@PathVariable String SUPPLIER_ID, Map  metadata) {
        Supplier componentRight = new Supplier();
        return componentRight;
    }

    @Operation(summary = "1、增删改查")
    @DeleteMapping(value = "/{SUPPLIER_ID}", produces = "application/json")
    public Supplier deleteSupplier(@PathVariable String id) {
        Supplier componentRight = new Supplier();
        return componentRight;
    }






    @Operation(summary = "2、成为员工")
    @PostMapping(value = "/{SUPPLIER_ID}/employees", produces = "application/json")
    public Employee 成为员工(@PathVariable String SUPPLIER_ID, @RequestBody EmployerPojo employerPojo) {
        Employee componentRight = new Employee();
        return componentRight;
    }




    @Operation(summary = "3、list所有员工")
    @GetMapping(value = "/{SUPPLIER_ID}/employees", produces = "application/json")
    public List<Employee> getSupplier_Employers(@PathVariable String SUPPLIER_ID) {
        Supplier componentRight = new Supplier();
        return new ArrayList<Employee>();
    }




    @Operation(summary = "4、邀请成为")
    @PostMapping(value = "/{SUPPLIER_ID}/invitations", produces = "application/json")
    public Invitation 邀请员工(@PathVariable String SUPPLIER_ID, @RequestBody InvitationPojo employerPojo) {
        Invitation componentRight = new Invitation();
        componentRight = invitationsRepository.save(componentRight);
        return componentRight;
    }








    @PostMapping(value = "/componet_rights/{COMPONENT_RIGHTS_ID}/access_validators", produces = "application/json")
    public AccessValidator addComponentRight_Validator(@PathVariable int COMPONENT_RIGHTS_ID, @PathVariable int COUPON_TMPL_ID) {
        AccessValidator componentRight = new AccessValidator();
        return componentRight;
    }


    @GetMapping(value = "/{APP_ID}/{COUPON_TMPL_ID}", produces = "application/json")
    public List<ComponentRight> listCouponTemplate(@PathVariable int APP_ID, CouponTemplatePojoList  couponTemplatePojoList) {
        return null;
    }






}