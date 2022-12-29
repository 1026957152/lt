package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Employee;
import com.lt.dom.oct.Role;
import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.User;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeEditResp {


    private Map parameterList;

    public Map getParameterList() {
        return parameterList;
    }

    public void setParameterList(Map parameterList) {
        this.parameterList = parameterList;
    }

    private String suplier;

    private String firstName;

    private String lastName;
    private String phone;
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private String email;


    private String note;

    

private String code;
    private List<String> roles;
    private String real_name;
    private String status_text;
    private LocalDateTime created_at;
    private String nick_name;

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }
/*
    public static EmployeeEditResp sigleElementfrom(Triplet<Supplier,Employee, User> triplet) {
        EmployeeEditResp employeeResp = new EmployeeEditResp();

        Employee employee = triplet.getValue1();

        employeeResp.setSuplier(triplet.getValue0().getName());
        employeeResp.setPhone(triplet.getValue2().getPhone());
        employeeResp.setCreated_at(employee.getCreated_at());
        employeeResp.setStatus_text(employee.getStatus().toString());
        employeeResp.setCode(triplet.getValue1().getCode());
        List<Role> roles = triplet.getValue2().getRoles().stream().collect(Collectors.toList());
        employeeResp.setRoles(roles.stream().map(e->e.getName()).collect(Collectors.toList()));

        //     employeeResp.setRoles(RoleResp.fromWithoutModel(roles));


      //  entityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(null)).withRel("booking"));
      //  entityModel.add(linkTo(methodOn(EmployeeRestController.class).getEmployee(employee.getId())).withRel("getEmployee"));


        return employeeResp;
    }*/
/*
    public static EntityModel<EmployeeEditResp> pageElementfrom(Triplet<Supplier,Employee, User> triplet) {
        EmployeeEditResp employeeResp = new EmployeeEditResp();

        Employee employee = triplet.getValue1();
        User user = triplet.getValue2();

        employeeResp.setSuplier(triplet.getValue0().getName());
        employeeResp.setPhone(user.getPhone());
        employeeResp.setCreated_at(employee.getCreated_at());
        employeeResp.setStatus_text(employee.getStatus().toString());
        employeeResp.setNick_name(user.getNick_name()==null?"": user.getNick_name());
        employeeResp.setReal_name(user.getRealName()==null?"": user.getRealName());
        employeeResp.setCode(user.getCode());
        List<Role> roles = triplet.getValue2().getRoles().stream().collect(Collectors.toList());
        employeeResp.setRoles(roles.stream().map(e->e.getName()).collect(Collectors.toList()));


        EntityModel entityModel = EntityModel.of(employeeResp);


        return entityModel;
    }*/
    public static EmployeeEditResp pageElementfrom(Pair<Employee, User> pair) {
        EmployeeEditResp employeeResp = new EmployeeEditResp();

        User user = pair.getValue1();
        Employee employee = pair.getValue0();

        employeeResp.setCreated_at(employee.getCreated_at());
        employeeResp.setStatus_text(employee.getStatus().toString());
     //   employeeResp.setSuplier(employee.getValue0().getName());
        employeeResp.setPhone(employee.getPhone());
        employeeResp.setName(employee.getName());

        employeeResp.setCode(employee.getCode());
        System.out.println("=================================="+ user.getRoles().size());
        List<Role> roles = user.getRoles().stream().collect(Collectors.toList());
        System.out.println("=================================="+ roles.size());
        //   employeeResp.setRoles(RoleResp.fromWithoutModel(roles));
        employeeResp.setRoles(roles.stream().map(e->e.getName()).collect(Collectors.toList()));


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

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getNick_name() {
        return nick_name;
    }
}
