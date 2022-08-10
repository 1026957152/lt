package com.lt.dom.oct;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class Role {

    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;


    @Transient
    private List<NameAllowedPair> authorizedViewsAndActions;

    @Transient
    private List<String> roleUsers;

    private boolean enabled;

    public Role(String name) {
        this.name = name;
    }

    public Role() {

    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
/*    @Override
    public String toString() {
        return this.name;
    }*/
/*    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;*/


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NameAllowedPair> getAuthorizedViewsAndActions() {
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
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

/*    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }*/
@Transient
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;

    public void setPrivileges(Collection<Privilege> privileges) {

        this.privileges = privileges;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }
    // getters and setters are not shown for brevity


}
