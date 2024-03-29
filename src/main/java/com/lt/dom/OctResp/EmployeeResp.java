package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.controllerOct.BookingRestController;
import com.lt.dom.controllerOct.EmployeeRestController;
import com.lt.dom.oct.*;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResp extends BaseResp {




    private String suplier;

    private String firstName;

    private String lastName;
    private String phone;


    private String email;


    private String note;

    

private String code;
    private List<RoleResp> roles;
    private String real_name;
    private String status_text;

    private String nick_name;
    private String name;

    public static EmployeeResp sigleElementfrom(Employee triplet) {
        EmployeeResp employeeResp = new EmployeeResp();



        employeeResp.setSuplier(triplet.getSuplierId()+"");
        employeeResp.setPhone(triplet.getPhone());

        employeeResp.setStatus_text(triplet.getStatus().toString());
        employeeResp.setCode(triplet.getCode());

        return employeeResp;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public static EmployeeResp sigleElementfrom(Triplet<Supplier,Employee, User> triplet) {
        EmployeeResp employeeResp = new EmployeeResp();

        Employee employee = triplet.getValue1();

        employeeResp.setSuplier(triplet.getValue0().getName());
        employeeResp.setPhone(triplet.getValue2().getPhone());

        employeeResp.setStatus_text(employee.getStatus().toString());
        employeeResp.setCode(triplet.getValue1().getCode());
        List<Role> roles = triplet.getValue2().getRoles().stream().collect(Collectors.toList());
        employeeResp.setRoles(RoleResp.fromWithoutModel(roles));


      //  entityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(null)).withRel("booking"));
      //  entityModel.add(linkTo(methodOn(EmployeeRestController.class).getEmployee(employee.getId())).withRel("getEmployee"));


        return employeeResp;
    }

    public static EntityModel<EmployeeResp> pageElementfrom(Triplet<Supplier,Employee, User> triplet) {
        EmployeeResp employeeResp = new EmployeeResp();
        Supplier supplier = triplet.getValue0();
        Employee employee = triplet.getValue1();
        User user = triplet.getValue2();

        employeeResp.setSuplier(supplier.getName());


        employeeResp.setStatus_text(employee.getStatus().toString());
        employeeResp.setName(employee.getName());
        employeeResp.setPhone(employee.getPhone());
        employeeResp.setCode(employee.getCode());
        employeeResp.setCreatedDate(employee.getCreatedDate());
        employeeResp.setModifiedDate(employee.getModifiedDate());


        List<Role> roles = user.getRoles().stream().collect(Collectors.toList());
        employeeResp.setRoles(RoleResp.fromWithoutModel(roles));


        EntityModel entityModel = EntityModel.of(employeeResp);


        return entityModel;
    }
    public static EmployeeResp pageElementfrom(Pair<Employee, User> pair) {
        EmployeeResp employeeResp = new EmployeeResp();

        User user = pair.getValue1();
        Employee employee = pair.getValue0();


        employeeResp.setStatus_text(employee.getStatus().toString());
     //   employeeResp.setSuplier(employee.getValue0().getName());
        employeeResp.setPhone(user.getPhone());
        employeeResp.setReal_name(user.getRealName()==null?"": user.getRealName());

        employeeResp.setCode(employee.getCode());
        System.out.println("=================================="+ user.getRoles().size());
        List<Role> roles = user.getRoles().stream().collect(Collectors.toList());
        System.out.println("=================================="+ roles.size());
        employeeResp.setRoles(RoleResp.fromWithoutModel(roles));


        return employeeResp;
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

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getReal_name() {
        return real_name;
    }


    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
