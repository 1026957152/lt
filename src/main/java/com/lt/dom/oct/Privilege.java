package com.lt.dom.oct;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Privilege {
    @Version
    private Integer version;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Transient
    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;


    public Privilege(String name) {
        this.name = name;
    }

    public Privilege() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}