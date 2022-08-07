package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.NameAllowedPair;
import com.lt.dom.oct.Privilege;
import com.lt.dom.oct.Role;
import com.lt.dom.oct.User;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class RoleResp {

    private String name;


/*
    private List<NameAllowedPair> authorizedViewsAndActions;

    private List<String> roleUsers;*/

    private boolean enabled;
    private String text;
    private String id;

    public RoleResp(String name) {
        this.name = name;
    }

    public static List<RoleResp> from(List<Role> validatorOptional) {
        return validatorOptional.stream().map(x->{

            return RoleResp.from(x);

/*            RoleResp resp = new RoleResp(x.getName());
            resp.setEnabled(x.isEnabled());
            resp.setPrivileges(x.getPrivileges().stream().map(xp->{
                PrivilegeResp privilege = new PrivilegeResp(xp.getName());
                return privilege;
            }).collect(Collectors.toList()));
            return resp;*/
        }).collect(Collectors.toList());

    }

    public static RoleResp from(Role x) {
        RoleResp roleResp = new RoleResp(x.getName());
        roleResp.setEnabled(x.isEnabled());
        roleResp.setId(x.getName());
        roleResp.setText(x.getName());
        roleResp.setEnabled(x.isEnabled());

        return roleResp;
    }


    @Override
    public String toString() {
        return this.name;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

/*    public List<NameAllowedPair> getAuthorizedViewsAndActions() {
        return authorizedViewsAndActions;
    }

    public void setAuthorizedViewsAndActions(List<NameAllowedPair> authorizedViewsAndActions) {
        this.authorizedViewsAndActions = authorizedViewsAndActions;
    }

    public List<String> getRoleUsers() {
        return roleUsers;
    }

    public void setRoleUsers(List<String> roleUsers) {
        this.roleUsers = roleUsers;
    }*/

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }





    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<PrivilegeResp> privileges;

    public void setPrivileges(Collection<PrivilegeResp> privileges) {

        this.privileges = privileges;
    }

    public Collection<PrivilegeResp> getPrivileges() {
        return privileges;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
