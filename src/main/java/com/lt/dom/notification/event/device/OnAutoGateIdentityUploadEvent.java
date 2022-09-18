package com.lt.dom.notification.event.device;

import com.lt.dom.oct.User;
import com.lt.dom.otcenum.EnumEvents;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnAutoGateIdentityUploadEvent extends ApplicationEvent {
    private EnumEvents event;
    private Locale locale;
    private User user;
    public OnAutoGateIdentityUploadEvent(
      User user, Locale locale, EnumEvents appUrl) {
        super(user);
        
        this.user = user;
        this.locale = locale;
        this.event = appUrl;
    }

    public EnumEvents getEvent() {
        return event;
    }

    public void setEvent(EnumEvents event) {
        this.event = event;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // standard getters and setters
}