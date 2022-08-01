package com.lt.dom.notification;

import com.lt.dom.oct.Component;
import com.lt.dom.oct.User;

import java.util.List;

public class OrderPaidVo {
	
    private long id;
    private String name;
    private String email;
    private String mobile;
    private List<Component> components;
    private User user;

    public void setComponents(List<Component> components) {

        this.components = components;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }


    // getter and setter methods
}