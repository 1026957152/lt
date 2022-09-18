package com.lt.dom.notification.event;

import com.lt.dom.oct.User;
import com.lt.dom.oct.Voucher;
import com.lt.dom.otcenum.EnumEvents;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnVoucherRedeamedEvent extends ApplicationEvent {
    private EnumEvents events;
    private Locale locale;
    private User user;

    public OnVoucherRedeamedEvent(
      User user, Locale locale, EnumEvents appUrl) {
        super(user);
        
        this.user = user;
        this.locale = locale;
        this.events = appUrl;
    }

    public OnVoucherRedeamedEvent(User user, Voucher x, Object appUrl, EnumEvents bulk_redemption$succeeded) {
        super(user);
    }

    public EnumEvents getAppUrl() {
        return events;
    }

    public void setAppUrl(EnumEvents appUrl) {
        this.events = appUrl;
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