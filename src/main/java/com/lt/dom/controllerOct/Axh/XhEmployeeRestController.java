package com.lt.dom.controllerOct.Axh;

import com.lt.dom.OctResp.EmployeeResp;
import com.lt.dom.OctResp.EnumResp;
import com.lt.dom.controllerOct.SupplierRestController;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.Axh.PullFromYxdRequest;
import com.lt.dom.oct.Employee;
import com.lt.dom.oct.Role;
import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.User;
import com.lt.dom.otcReq.EmployerUpdatePojo;
import com.lt.dom.otcenum.EnumRole;
import com.lt.dom.repository.Axh.PullFromYxdRequestRepository;
import com.lt.dom.repository.EmployeeRepository;
import com.lt.dom.repository.RoleRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.serviceOtc.RoleServiceImpl;
import com.lt.dom.serviceOtc.SupplierServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/xh")
public class XhEmployeeRestController {

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
    @Autowired
    private RoleServiceImpl roleService;


    @Autowired
    private PullFromYxdRequestRepository pullFromYxdRequestRepository;

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
        entityModel.add(linkTo(methodOn(SupplierRestController.class).getEmployeeList(supplier.get().getId(),null)).withRel("getPageEmployees"));

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

        EmployeeResp employeeResp = EmployeeResp.pageElementfrom(Pair.with(employee,optionalUser.get()));
        EntityModel entityModel = EntityModel.of(employeeResp);

        entityModel.add(linkTo(methodOn(XhEmployeeRestController.class).getEmployee(employee.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(XhEmployeeRestController.class).getEmployeeparameters(employee.getId())).withRel("getParameters"));
        // entityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(null)).withRel("booking"));
        entityModel.add(linkTo(methodOn(XhEmployeeRestController.class).delete(employee.getId())).withRel("deleteEmployee"));
        entityModel.add(linkTo(methodOn(XhEmployeeRestController.class).updateEmployee(employee.getId(),null)).withRel("updateEmployee"));


        return ResponseEntity.ok(entityModel);





    }





    @Operation(summary = "3、list所有员工")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/employees", produces = "application/json")
    public PagedModel getEmployerList(@PathVariable long SUPPLIER_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<EmployeeResp>> assembler) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);
        if(optionalSupplier.isEmpty()) {
            throw new BookNotFoundException(SUPPLIER_ID,"找不到供应商");

        }
        Supplier supplier = optionalSupplier.get();

        Page<Employee> employees = employeeRepository.findBySuplierId(supplier.getId(),pageable);

        List<User> users = userRepository.findAllById(employees.stream().map(x->x.getUserId()).collect(Collectors.toList()));
        Map<Long,User> userMap = users.stream().collect(Collectors.toMap(x->x.getId(),x->x));
        return assembler.toModel(employees.map(x->{

            EmployeeResp employeeResp = EmployeeResp.sigleElementfrom(Triplet.with(supplier,x,userMap.get(x.getUserId())));

            EntityModel entityModel = EntityModel.of(employeeResp);

            entityModel.add(linkTo(methodOn(XhEmployeeRestController.class).delete(x.getId())).withRel("delete"));
            return entityModel;
        }));

    }









    @GetMapping(value = "/employees/{EMPLOYEE_ID}/page", produces = "application/json")
    public EntityModel Page_updateEmployee(@PathVariable long EMPLOYEE_ID) {
        Optional<Employee> optional = employeeRepository.findById(EMPLOYEE_ID);
        if(optional.isEmpty()) {
            throw new BookNotFoundException(EMPLOYEE_ID,"找不到供应商");

        }

        Employee employee = optional.get();

        Supplier supplier = supplierRepository.findById(employee.getSuplierId()).get();
        Optional<User> optionalUser = userRepository.findById(employee.getUserId());
        User user = optionalUser.get();

        System.out.println("=================================="+ optionalUser.get());
        System.out.println("=================================="+ optionalUser.get().getRoles().size());


        EmployeeResp employeeResp = EmployeeResp.pageElementfrom(Pair.with(employee,optionalUser.get()));

        Map<String,Role> userMap = user.getRoles().stream().collect(Collectors.toMap(e->e.getName(),e->e));


        List<EnumRole> enumRoles = roleService.get(supplier);

        EntityModel entityModel =  EntityModel.of( Map.of(
                "employee",employeeResp,
                "role_list", enumRoles.stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            enumResp.setSelected(userMap.containsKey(x.name()));
            return enumResp;
        }).collect(Collectors.toList())/*,
                    "_link",linkTo(methodOn(SupplierRestController.class).linkEmployee(supplier.get().getId(),null)).withRel("addEmployees"))*/));

        entityModel.add(linkTo(methodOn(XhEmployeeRestController.class).updateEmployee(employee.getId(),null)).withRel("updateEmployee"));

        return entityModel;
    }


    @Operation(summary = "3、更新")
    @PutMapping(value = "/employees/{EMPLOYEE_ID}", produces = "application/json")
    public ResponseEntity<EntityModel<EmployeeResp>> updateEmployee(@PathVariable long EMPLOYEE_ID, @RequestBody @Valid EmployerUpdatePojo employerPojo) {

        Optional<Employee> optional = employeeRepository.findById(EMPLOYEE_ID);
        if(optional.isEmpty()) {
            throw new BookNotFoundException(EMPLOYEE_ID,"找不到供应商");
        }

        Employee employee = optional.get();
        Triplet<Supplier,Employee,User> componentRight = supplierService.updateEmployee(optional.get(),employerPojo);



        EntityModel<EmployeeResp> employeeRespEntityModel= EmployeeResp.pageElementfrom(componentRight);

        employeeRespEntityModel.add(linkTo(methodOn(XhEmployeeRestController.class).getEmployee(employee.getId())).withSelfRel());
        employeeRespEntityModel.add(linkTo(methodOn(XhEmployeeRestController.class).getEmployeeparameters(employee.getId())).withRel("getParameters"));
        // entityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(null)).withRel("booking"));
        employeeRespEntityModel.add(linkTo(methodOn(XhEmployeeRestController.class).delete(employee.getId())).withRel("deleteEmployee"));
        employeeRespEntityModel.add(linkTo(methodOn(XhEmployeeRestController.class).updateEmployee(employee.getId(),null)).withRel("updateEmployee"));


        return ResponseEntity.ok(employeeRespEntityModel);


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