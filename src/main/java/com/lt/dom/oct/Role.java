package com.lt.dom.oct;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Entity
public class Role {

    @Id
    private long id;
    private String name;


    @Transient
    private List<NameAllowedPair> authorizedViewsAndActions;

    @Transient
    private List<String> roleUsers;

    private boolean enabled;


}
