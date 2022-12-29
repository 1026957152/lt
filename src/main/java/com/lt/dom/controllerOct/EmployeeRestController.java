package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.EmployeeEditResp;
import com.lt.dom.OctResp.EmployeeResp;
import com.lt.dom.OctResp.EnumResp;
import com.lt.dom.OctResp.SupplierResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.EnumEmployeeStatus;
import com.lt.dom.otcenum.EnumRole;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.serviceOtc.product.CityPassServiceImpl;
import com.lt.dom.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PreferenceServiceImpl preferenceService;


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
    private IAuthenticationFacade authenticationFacade;

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
        entityModel.add(linkTo(methodOn(SupplierRestController.class).listEmployee(supplier.get().getId(),null,null)).withRel("getPageEmployees"));

        return entityModel;
    }




    @Operation(summary = "4、成为员工")
    @GetMapping(value = "/employees/{EMPLOYEE_ID}/edit", produces = "application/json")
    public EntityModel getEmployeeEdit(@PathVariable long EMPLOYEE_ID) {




        Optional<Employee> optional = employeeRepository.findById(EMPLOYEE_ID);
        if(optional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到供应商");

        }

        Employee employee = optional.get();
        Optional<User> optionalUser = userRepository.findById(employee.getUserId());
        System.out.println("=================================="+ optionalUser.get());
        System.out.println("=================================="+ optionalUser.get().getRoles().size());

        EmployeeEditResp employeeResp = EmployeeEditResp.pageElementfrom(Pair.with(employee,optionalUser.get()));

        List<Role> roleList = roleRepository.findAll();

        employeeResp.setParameterList( Map.of("role_list", roleList.stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.getName());
            //   enumResp.setName(x.getName());
            enumResp.setText(EnumRole.valueOf(x.getName()).toString());
            return enumResp;
        }).collect(Collectors.toList())));
        EntityModel entityModel = EntityModel.of(employeeResp);

        entityModel.add(linkTo(methodOn(EmployeeRestController.class).getEmployee(employee.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(EmployeeRestController.class).getEmployeeparameters(employee.getId())).withRel("getParameters"));
        // entityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(null)).withRel("booking"));
        entityModel.add(linkTo(methodOn(EmployeeRestController.class).delete(employee.getId())).withRel("deleteEmployee"));
        entityModel.add(linkTo(methodOn(EmployeeRestController.class).updateEmployee(employee.getId(),null)).withRel("updateEmployee"));


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

        entityModel.add(linkTo(methodOn(EmployeeRestController.class).getEmployee(employee.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(EmployeeRestController.class).getEmployeeparameters(employee.getId())).withRel("getParameters"));
        // entityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(null)).withRel("booking"));
        entityModel.add(linkTo(methodOn(EmployeeRestController.class).delete(employee.getId())).withRel("deleteEmployee"));
        entityModel.add(linkTo(methodOn(EmployeeRestController.class).updateEmployee(employee.getId(),null)).withRel("updateEmployee"));


        return ResponseEntity.ok(entityModel);





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




    @GetMapping(value = "/staffs/{EMPLOYEE_ID}/edit", produces = "application/json")
    public EntityModel staff(@PathVariable long EMPLOYEE_ID) {
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


        EmployeeEditResp employeeResp = EmployeeEditResp.pageElementfrom(Pair.with(employee,optionalUser.get()));



        Map<String,Role> userMap = user.getRoles().stream().collect(Collectors.toMap(e->e.getName(),e->e));


        List<EnumRole> enumRoles = roleService.get(supplier);



        List<Role> roleList = roleRepository.findAll();
        employeeResp.setParameterList(Map.of("role_list", roleList.stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.getName());
            enumResp.setText(EnumRole.valueOf(x.getName()).toString());
            return enumResp;
        }).collect(Collectors.toList())));

        EntityModel entityModel =  EntityModel.of(employeeResp);

        entityModel.add(linkTo(methodOn(EmployeeRestController.class).updateEmployee(employee.getId(),null)).withRel("updateEmployee"));

        return entityModel;
    }






    @GetMapping(value = "/employees/{EMPLOYEE_ID}/Page_updateEmployee", produces = "application/json")
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


        EmployeeEditResp employeeResp = EmployeeEditResp.pageElementfrom(Pair.with(employee,optionalUser.get()));

/*        Map<String,Role> userMap = user.getRoles().stream().collect(Collectors.toMap(e->e.getName(),e->e));


        List<EnumRole> enumRoles = roleService.get(supplier);*/


        List<Role> roleList = roleRepository.findAll();

        employeeResp.setParameterList(Map.of("role_list", roleList.stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.getName());
            //   enumResp.setName(x.getName());
            enumResp.setText(EnumRole.valueOf(x.getName()).toString());
            return enumResp;
        }).collect(Collectors.toList())));

        EntityModel entityModel =  EntityModel.of(employeeResp);// Map.of(
              //  "employee",employeeResp,
            //    "parameterList",);
/*                "role_list", enumRoles.stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            enumResp.setSelected(userMap.containsKey(x.name()));
            return enumResp;
        }


        ).collect(Collectors.toList())/*,
                    "_link",linkTo(methodOn(SupplierRestController.class).linkEmployee(supplier.get().getId(),null)).withRel("addEmployees"))*/

        entityModel.add(linkTo(methodOn(EmployeeRestController.class).updateEmployee(employee.getId(),null)).withRel("updateEmployee"));

        return entityModel;
    }


    @Operation(summary = "3、更新")
    @PutMapping(value = "/employees/{EMPLOYEE_ID}", produces = "application/json")
    public ResponseEntity<EntityModel<EmployeeResp>> updateEmployee(@PathVariable long EMPLOYEE_ID,

                                                                    @RequestBody @Valid EmployeeEditResp employerPojo) {

        Optional<Employee> optional = employeeRepository.findById(EMPLOYEE_ID);
        if(optional.isEmpty()) {
            throw new BookNotFoundException(EMPLOYEE_ID,"找不到供应商");
        }

        Employee employee = optional.get();
        Triplet<Supplier,Employee,User> componentRight = supplierService.updateEmployee(optional.get(),employerPojo);



        EntityModel<EmployeeResp> employeeRespEntityModel= EmployeeResp.pageElementfrom(componentRight);

        employeeRespEntityModel.add(linkTo(methodOn(EmployeeRestController.class).getEmployee(employee.getId())).withSelfRel());
        employeeRespEntityModel.add(linkTo(methodOn(EmployeeRestController.class).getEmployeeparameters(employee.getId())).withRel("getParameters"));
        // entityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(null)).withRel("booking"));
        employeeRespEntityModel.add(linkTo(methodOn(EmployeeRestController.class).delete(employee.getId())).withRel("deleteEmployee"));
        employeeRespEntityModel.add(linkTo(methodOn(EmployeeRestController.class).updateEmployee(employee.getId(),null)).withRel("updateEmployee"));


        return ResponseEntity.ok(employeeRespEntityModel);


    }


    @DeleteMapping(value = "/employees/{EMPLOYEE_ID}", produces = "application/json")
    public ResponseEntity delete(@PathVariable long EMPLOYEE_ID) {


        Authentication authentication =  authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);


        Optional<Employee> optional = employeeRepository.findById(EMPLOYEE_ID);
    if(optional.isEmpty()) {
        throw new BookNotFoundException(EMPLOYEE_ID,"找不到供应商");
    }


    Employee employee = optional.get();

    if(userVo.getUser_id().equals(employee.getUserId())){
        throw new BookNotFoundException(Enumfailures.resource_not_found,"不能删除自己");
    }

        preferenceService.whenDeleteEmployee(employee);
        employee.setUserId(null);
        employee.setStatus(EnumEmployeeStatus.inactive);
        employeeRepository.save(employee);

    return ResponseEntity.ok(HttpStatus.NO_CONTENT);
}







    @Operation(summary = "3、list所有员工")
    @GetMapping(value = "/employees", produces = "application/json")
    public PagedModel getEmployeeList(Pageable pageable , PagedResourcesAssembler<EntityModel<EmployeeResp>> assembler) {



        Page<Employee> employees = employeeRepository.findAll(pageable);

        List<User> users = userRepository.findAllById(employees.stream().map(x->x.getUserId()).collect(Collectors.toList()));
        Map<Long,User> userMap = users.stream().collect(Collectors.toMap(x->x.getId(),x->x));

        Page<EntityModel<EmployeeResp>> list = employees.map(x->{

            EmployeeResp employeeResp = EmployeeResp.pageElementfrom(Pair.with(x,userMap.get(x.getUserId())));
            EntityModel<EmployeeResp> employeeRespEntityModel= EntityModel.of(employeeResp);
            employeeRespEntityModel.add(linkTo(methodOn(EmployeeRestController.class).getEmployee(x.getId())).withSelfRel());
            employeeRespEntityModel.add(linkTo(methodOn(EmployeeRestController.class).delete(x.getId())).withRel("delete"));
            employeeRespEntityModel.add(linkTo(methodOn(EmployeeRestController.class).Page_updateEmployee(x.getId())).withRel("Page_update"));

            employeeRespEntityModel.add(linkTo(methodOn(EmployeeRestController.class).updateEmployee(x.getId(),null)).withRel("update"));
            return employeeRespEntityModel;
        });


        return assembler.toModel(list);

    }



    @Operation(summary = "4、成为员工")
    @PostMapping(value = "/employees", produces = "application/json")
    public ResponseEntity<EntityModel<EmployeeResp>> createEmployee(@RequestBody @Valid EmployerPojo employerPojo) {


        Authentication authentication =  authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);


        Optional<Supplier> supplierOptional =supplierRepository.findByCode(employerPojo.getSupplier());

        Supplier supplier = supplierOptional.get();


        List<String> enumRoles = roleService.get(supplier).stream().map(x->x.name()).collect(Collectors.toList());

        List<String> hasEnumRoles  = employerPojo.getRoles().stream().filter(x-> enumRoles.contains(x)).collect(Collectors.toList());

        if(hasEnumRoles.isEmpty()){
            throw new BookNotFoundException("无相关权限"+employerPojo.getRoles(),"");
        }





        Employee employee = supplierService.createEmployee(supplier,employerPojo,hasEnumRoles);

        EntityModel entityModel = EntityModel.of( EmployeeResp.sigleElementfrom(employee));



        return ResponseEntity.ok(entityModel);




    }





    @GetMapping(value = "/employees/page", produces = "application/json")
    public EntityModel page_addEmployee() {



        List<Supplier> supplierList = supplierRepository.findAll();


        List<String> enumRoles = Arrays.asList(EnumRole.ROLE_BANK_STAFF).stream().map(e->e.name()).collect(Collectors.toList());
        List<Role> roles = roleRepository.findAll();
        EntityModel entityModel =  EntityModel.of( Map.of(
                "suppliers",supplierList.stream().map(e->{

                    return SupplierResp.from(e);

                }).collect(Collectors.toList()),

                "roles", roles.stream().filter(e->enumRoles.contains(e.getName())).map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.getName());
            //   enumResp.setName(x.getName());
            enumResp.setText(EnumRole.valueOf(x.getName()).toString());
            return enumResp;
        }).collect(Collectors.toList())));


        entityModel.add(linkTo(methodOn(EmployeeRestController.class).createEmployee(null)).withRel("addEmployees"));


        return entityModel;
    }


}