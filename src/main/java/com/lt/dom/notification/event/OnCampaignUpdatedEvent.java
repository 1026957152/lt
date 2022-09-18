package com.lt.dom.notification.event;

import com.lt.dom.oct.User;
import com.lt.dom.otcenum.EnumEvents;
import com.lt.dom.vo.UserVo;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnCampaignUpdatedEvent extends ApplicationEvent {
    private EnumEvents event;
    private Locale locale;
    private UserVo user;
    public OnCampaignUpdatedEvent(
            UserVo user, Locale locale, EnumEvents appUrl) {
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

    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
        this.user = user;
    }
    // standard getters and setters
}