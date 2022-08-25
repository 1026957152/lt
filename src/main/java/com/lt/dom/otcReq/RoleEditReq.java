package com.lt.dom.otcReq;


import com.lt.dom.oct.Role;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

public class RoleEditReq {



    private String name;




    private boolean enabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }





    private List<String> privileges;

    public List<String> getPrivileges() {
        return privileges;
    }




}
