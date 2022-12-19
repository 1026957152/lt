package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.EnumLongIdResp;
import com.lt.dom.OctResp.EnumResp;
import com.lt.dom.OctResp.RoleResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.Privilege;
import com.lt.dom.oct.Role;
import com.lt.dom.otcReq.RoleEditReq;
import com.lt.dom.otcReq.RoleReq;
import com.lt.dom.otcenum.EnumAvailableActions;
import com.lt.dom.otcenum.EnumRole;
import com.lt.dom.repository.PrivilegeRepository;
import com.lt.dom.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class RoleRestController {


    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;


    @GetMapping(value = "/suppliers/{PRODUCT_ID}/roles", produces = "application/json")
    public CollectionModel<RoleResp> listSupplierRoles(@PathVariable long PRODUCT_ID) {

        List<Role> validatorOptional = roleRepository.findAll();

        return RoleResp.from(validatorOptional);
    }


    @PostMapping(value = "/roles", produces = "application/json")
    public EntityModel<Role> addRole(@RequestBody @Valid RoleReq roleReq) {


        Role role = new Role();
        role.setName(roleReq.getName());
        role.setEnabled(roleReq.isEnabled());
        role = roleRepository.save(role);

        return EntityModel.of(role);
    }

/*

    @GetMapping(value = "/roles", produces = "application/json")
    public PagedModel pageRoles(Pageable pageable , PagedResourcesAssembler<EntityModel<Role>> assembler) {

        Page<Role> validatorOptional = roleRepository.findAll(pageable);




        return assembler.toModel(validatorOptional.map(x->{
            EntityModel entityModel = EntityModel.of(x);
            entityModel.add(linkTo(methodOn(RoleRestController.class).getRole(x.getId(),null)).withRel("Page_editRole"));
            return entityModel;


        }));
    }
*/


    @GetMapping(value = "/privileges", produces = "application/json")
    public CollectionModel<Privilege> listPrivileges() {
        List<Privilege> validatorOptional = privilegeRepository.findAll();

        return CollectionModel.of(validatorOptional);
    }


   @GetMapping(value = "/roles/enums", produces = "application/json")
    public PagedModel pageRolesEnums(Pageable pageable , PagedResourcesAssembler<EntityModel<EnumLongIdResp>> assembler) {


        Page<Role> validatorOptional = roleRepository.findAll(pageable);

        return assembler.toModel(validatorOptional.map(x->{
            EnumResp ea= new EnumResp();
            EnumRole enumRole = EnumRole.valueOf(x.getName());
            ea.setId(x.getName());
            ea.setText(enumRole.toString());
            EntityModel entityModel =  EntityModel.of(ea);

            entityModel.add(linkTo(methodOn(RoleRestController.class).getRole(x.getId(),null)).withRel("Page_editRole"));

            return entityModel;
        }));
    }



    @GetMapping(value = "/roles/{ROLE_ID}/privileges", produces = "application/json")
    public CollectionModel<RoleResp> pageRoles(@PathVariable long ROLE_ID) {

        Optional<Role> roleOptional = roleRepository.findById(ROLE_ID);
        if(roleOptional.isEmpty()){
            throw new BookNotFoundException(ROLE_ID,"找不到角色");
        }

        Collection<Privilege> privilegeList = roleOptional.get().getPrivileges();

        return null;//assembler.toModel(validatorOptional.map(x->EntityModel.of(x)));
    }



    @GetMapping(value = "/roles/{ROLE_ID}", produces = "application/json")
    public EntityModel<RoleResp> getRole(@PathVariable long ROLE_ID,RoleReq roleReq) {




        Optional<Role> roleOptional = roleRepository.findById(ROLE_ID);
        if(roleOptional.isEmpty()){
            throw new BookNotFoundException(ROLE_ID,"找不到角色");
        }
        Role role = roleOptional.get();
        RoleResp roleResp = RoleResp.from(role);


        List<String> stringList = role.getPrivileges().stream().map(x->x.getName()).collect(Collectors.toList());


        System.out.println("角色自己有的："+stringList);

        System.out.println();

        List<EnumResp> stringListMap = Arrays.stream(EnumAvailableActions.values()).collect(Collectors.groupingBy(x->x.getType())).entrySet().stream().map(x->{

            EnumResp topResp = new EnumResp();

            topResp.setText(x.getKey().toString());
            topResp.setId(x.getKey().name());
            topResp.setSubitems(x.getValue().stream().map(xx->{

                System.out.println("全部的："+xx.name());
                EnumResp resp = new EnumResp();
                resp.setId(xx.name());
                resp.setText(xx.toString());
                boolean privilege = stringList.contains(xx.name());
                resp.setSelected(privilege);
                return resp;
            }).collect(Collectors.toList()));

            return topResp;
        }).collect(Collectors.toList());


        roleResp.setPrivileges(stringListMap);


        EntityModel entityModel = EntityModel.of(roleResp);

        entityModel.add(linkTo(methodOn(RoleRestController.class).editRole(role.getId(),null)).withRel("editRole"));


        return entityModel;
    }





    @PutMapping(value = "/roles/{ROLE_ID}", produces = "application/json")
    public EntityModel<Role> editRole(@PathVariable long ROLE_ID,@RequestBody @Valid RoleEditReq roleEditReq) {

        Optional<Role> roleOptional = roleRepository.findById(ROLE_ID);
        if(roleOptional.isEmpty()){
            throw new BookNotFoundException(ROLE_ID,"找不到角色");
        }
        Role role = roleOptional.get();


        List<Privilege> privilegeList = privilegeRepository.findByNameIn(roleEditReq.getPrivileges());

        System.out.println(" 获取 特定角色的 权限"+ privilegeList.toString());
/*
        List<String> privilegeList = roleEditReq.getPrivileges().stream().map(x->{
            Privilege privilege = new Privilege();
            privilege.setName(x.getName());
            return privilege;
        }).collect(Collectors.toList());
*/

        role.setPrivileges(privilegeList);

        role = roleRepository.save(role);

        System.out.println(" 获取 特定角色的 跟新后的样子"+ role.getPrivileges().toString());


        return EntityModel.of(role);

    }




}