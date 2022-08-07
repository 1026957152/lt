package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.controllerOct.BookingRestController;
import com.lt.dom.controllerOct.EmployeeRestController;
import com.lt.dom.oct.Employee;
import com.lt.dom.oct.Role;
import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.User;
import com.lt.dom.otcenum.EnumEmployeeAccessLevel;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.hateoas.EntityModel;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResp {




    private String suplier;

    private String firstName;

    private String lastName;
    private String phone;


    private String email;


    private String note;
    private String name;
    private String code;
    private List<RoleResp> roles;

    public static EntityModel<EmployeeResp> sigleElementfrom(Triplet<Supplier,Employee, User> triplet) {
        EmployeeResp employeeResp = new EmployeeResp();

        Employee employee = triplet.getValue1();

        employeeResp.setSuplier(triplet.getValue0().getName());
        employeeResp.setPhone(triplet.getValue2().getPhone());
        employeeResp.setName(triplet.getValue2().getRealName());
        employeeResp.setCode(triplet.getValue1().getCode());
        List<Role> roles = triplet.getValue2().getRoles().stream().collect(Collectors.toList());
        employeeResp.setRoles(RoleResp.from(roles));


        EntityModel entityModel = EntityModel.of(employeeResp);
        entityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(null)).withRel("booking"));
        entityModel.add(linkTo(methodOn(EmployeeRestController.class).getEmployee(employee.getId())).withRel("getEmployee"));


        return entityModel;
    }

    public static EntityModel<EmployeeResp> pageElementfrom(Triplet<Supplier,Employee, User> triplet) {
        EmployeeResp employeeResp = new EmployeeResp();

        Employee employee = triplet.getValue1();

        employeeResp.setSuplier(triplet.getValue0().getName());
        employeeResp.setPhone(triplet.getValue2().getPhone());
        employeeResp.setName(triplet.getValue2().getRealName());
        employeeResp.setCode(triplet.getValue1().getCode());
        List<Role> roles = triplet.getValue2().getRoles().stream().collect(Collectors.toList());
        employeeResp.setRoles(RoleResp.from(roles));


        EntityModel entityModel = EntityModel.of(employeeResp);
        entityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(null)).withRel("booking"));
        entityModel.add(linkTo(methodOn(EmployeeRestController.class).getEmployee(employee.getId())).withRel("getEmployee"));
        entityModel.add(linkTo(methodOn(EmployeeRestController.class).delete(employee.getId())).withRel("deleteEmployee"));
        entityModel.add(linkTo(methodOn(EmployeeRestController.class).update(employee.getId(),null)).withRel("updateEmployee"));

        return entityModel;
    }
    public static EntityModel<EmployeeResp> pageElementfrom(Pair<Employee, User> pair) {
        EmployeeResp employeeResp = new EmployeeResp();

        User user = pair.getValue1();
        Employee employee = pair.getValue0();

        EntityModel entityModel = EntityModel.of(employeeResp);
     //   employeeResp.setSuplier(employee.getValue0().getName());
        employeeResp.setPhone(user.getPhone());
        employeeResp.setName(user.getRealName());
        employeeResp.setCode(employee.getCode());
        System.out.println("=================================="+ user.getRoles().size());
        List<Role> roles = user.getRoles().stream().collect(Collectors.toList());
        System.out.println("=================================="+ roles.size());
        employeeResp.setRoles(RoleResp.from(roles));

        entityModel.add(linkTo(methodOn(EmployeeRestController.class).getEmployee(employee.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(EmployeeRestController.class).getEmployeeparameters(employee.getId())).withRel("getParameters"));
       // entityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(null)).withRel("booking"));
        entityModel.add(linkTo(methodOn(EmployeeRestController.class).delete(employee.getId())).withRel("deleteEmployee"));
        entityModel.add(linkTo(methodOn(EmployeeRestController.class).update(employee.getId(),null)).withRel("updateEmployee"));


        return entityModel;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




    public String getSuplier() {
        return suplier;
    }

    public void setSuplier(String suplier) {
        this.suplier = suplier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setRoles(List<RoleResp> roles) {
        this.roles = roles;
    }

    public List<RoleResp> getRoles() {
        return roles;
    }
}
