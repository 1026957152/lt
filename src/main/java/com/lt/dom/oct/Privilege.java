package com.lt.dom.oct;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Privilege {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Transient
    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;


}