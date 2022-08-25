package com.lt.dom.otcReq;

import com.lt.dom.oct.NameAllowedPair;
import com.lt.dom.oct.Privilege;
import com.lt.dom.oct.User;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

public class RoleReq {



    @NotEmpty
    private String name;



    @NotNull
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
}
