package com.lt.dom.notification.event;

import com.lt.dom.oct.Charge;
import com.lt.dom.oct.User;
import com.lt.dom.otcenum.EnumEvents;
import org.springframework.context.ApplicationEvent;

public class OnRefundCreatedEvent extends ApplicationEvent {
    private EnumEvents event;
    private Charge charge;
    private User user;

    public OnRefundCreatedEvent(
            User user, Charge charge, EnumEvents appUrl) {
        super(user);
        
        this.user = user;
        this.charge = charge;
        this.event = appUrl;
    }

    public EnumEvents getEvent() {
        return event;
    }

    public void setEvent(EnumEvents event) {
        this.event = event;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // standard getters and setters
}