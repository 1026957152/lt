package com.lt.dom.notification.event;

import com.lt.dom.oct.User;
import com.lt.dom.otcenum.EnumEvents;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnChargePaidCompleteEvent extends ApplicationEvent {
    private EnumEvents appUrl;
    private Locale locale;
    private User user;

    public OnChargePaidCompleteEvent(
      User user, Locale locale, EnumEvents appUrl) {
        super(user);
        
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public EnumEvents getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(EnumEvents appUrl) {
        this.appUrl = appUrl;
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