package com.lt.dom.requestvo;

import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.Traveler;
import com.lt.dom.oct.User;
import com.lt.dom.otcenum.EnumPublicationObjectType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class PublishTowhoVo {

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
    private EnumPublicationObjectType toWhoTyp;
    private Traveler traveler;
    private Supplier supplier;
    private User user;
    private long toWho;

    public long getToWho() {
        return toWho;
    }

    public void setToWho(long toWho) {
        this.toWho = toWho;
    }

    public void setToWhoTyp(EnumPublicationObjectType toWhoTyp) {
        this.toWhoTyp = toWhoTyp;
    }

    public EnumPublicationObjectType getToWhoTyp() {
        return toWhoTyp;
    }

    public void setTraveler(Traveler traveler) {
        this.traveler = traveler;
    }

    public Traveler getTraveler() {
        return traveler;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
