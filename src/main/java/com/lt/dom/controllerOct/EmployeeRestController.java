package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.EmployeeResp;
import com.lt.dom.OctResp.EnumResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class EmployeeRestController {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SupplierServiceImp supplierService;

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private RoleRepository roleRepository;



    @GetMapping(value = "/employees/{EMPLOYEE_ID}/parameters", produces = "application/json")
    public EntityModel getEmployeeparameters(@PathVariable long EMPLOYEE_ID) {


        Optional<Employee> optional = employeeRepository.findById(EMPLOYEE_ID);
        if(optional.isEmpty()) {
            throw new BookNotFoundException(EMPLOYEE_ID,"找不到供应商");
        }
        Optional<User> optionalUser = userRepository.findById(optional.get().getUserId());

        List<String> roleList = optionalUser.get().getRoles().stream().map(x->x.getName()).collect(toList());

        Optional<Supplier> supplier = supplierRepository.findById(optional.get().getSuplierId());
        if(supplier.isEmpty()) {
            throw new BookNotFoundException(optional.get().getSuplierId(),"找不到供应商");
        }
            List<Role> roles = roleRepository.findAll();
            EntityModel entityModel = EntityModel.of(Map.of("roles", roles.stream().map(x->{
                        EnumResp enumResp = new EnumResp();
                        enumResp.setId(x.getName());
                        enumResp.setName(x.getName());
                        enumResp.setText(x.getName());
                        enumResp.setSelected(roleList.contains(x.getName()));
                        return enumResp;
                    }).collect(Collectors.toList()),
                    "_link",linkTo(methodOn(SupplierRestController.class).linkEmployee(supplier.get().getId(),null)).withRel("addEmployees")));

        entityModel.add(linkTo(methodOn(SupplierRestController.class).linkEmployee(supplier.get().getId(),null)).withRel("addEmployees"));
        entityModel.add(linkTo(methodOn(SupplierRestController.class).pageEmployess(supplier.get().getId(),null)).withRel("getPageEmployees"));

        return entityModel;
    }


    @Operation(summary = "4、成为员工")
    @GetMapping(value = "/employees/{EMPLOYEE_ID}", produces = "application/json")
    public ResponseEntity<EntityModel<EmployeeResp>> getEmployee(@PathVariable long EMPLOYEE_ID) {




        Optional<Employee> optional = employeeRepository.findById(EMPLOYEE_ID);
        if(optional.isEmpty()) {
            throw new BookNotFoundException(EMPLOYEE_ID,"找不到供应商");

        }

        Employee employee = optional.get();
        Optional<User> optionalUser = userRepository.findById(employee.getUserId());
        System.out.println("=================================="+ optionalUser.get());
        System.out.println("=================================="+ optionalUser.get().getRoles().size());
        return ResponseEntity.ok(EmployeeResp.pageElementfrom(Pair.with(employee,optionalUser.get())));





    }



/*

    @Operation(summary = "3、list所有员工")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/employees", produces = "application/json")
    public Page<EmployeeResp> getSupplier_Employers(@PathVariable long SUPPLIER_ID) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);
        if(optionalSupplier.isEmpty()) {
            throw new BookNotFoundException(SUPPLIER_ID,"找不到供应商");

        }
        Supplier supplier = optionalSupplier.get();

        Page<Employee> employees = employeeRepository.findBySuplierId(supplier.getId(),PageRequest.of(0,10));

        List<User> users = userRepository.findAllById(employees.stream().map(x->x.getUserId()).collect(Collectors.toList()));
        Map<Long,User> userMap = users.stream().collect(Collectors.toMap(x->x.getId(),x->x));
        return employees.map(x->{
            return EmployeeResp.from(Triplet.with(supplier,x,userMap.get(x.getUserId())));
        });

    }

*/


    @Operation(summary = "3、更新")
    @PutMapping(value = "/employees/{EMPLOYEE_ID}", produces = "application/json")
    public ResponseEntity<Employee> update(@PathVariable long EMPLOYEE_ID,  @RequestBody @Valid EmployerUpdatePojo employerPojo) {

        Optional<Employee> optional = employeeRepository.findById(EMPLOYEE_ID);
        if(optional.isEmpty()) {
            throw new BookNotFoundException(EMPLOYEE_ID,"找不到供应商");
        }

            Employee componentRight = supplierService.updateEmployee(optional.get(),employerPojo);
            return ResponseEntity.ok(componentRight);


    }


    @DeleteMapping(value = "/employees/{EMPLOYEE_ID}", produces = "application/json")
public ResponseEntity delete(@PathVariable long EMPLOYEE_ID) {

    Optional<Employee> optional = employeeRepository.findById(EMPLOYEE_ID);
    if(optional.isEmpty()) {
        throw new BookNotFoundException(EMPLOYEE_ID,"找不到供应商");
    }
    Employee employee = optional.get();
    employeeRepository.delete(employee);
    return ResponseEntity.ok(HttpStatus.NO_CONTENT);
}

}