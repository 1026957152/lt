package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumEvents;
import com.lt.dom.otcenum.EnumLoyaltyType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class EarningRule {


        @Version
        private Integer version;
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        @Id
        private long id  ;

        private EnumLoyaltyType loyalty_type  ;


        private EnumEvents event    ;

    private Boolean active    ;
    private LocalDateTime start_date    ;
    private LocalDateTime expiration_date    ;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EnumLoyaltyType getLoyalty_type() {
        return loyalty_type;
    }

    public void setLoyalty_type(EnumLoyaltyType loyalty_type) {
        this.loyalty_type = loyalty_type;
    }

    public EnumEvents getEvent() {
        return event;
    }

    public void setEvent(EnumEvents event) {
        this.event = event;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public LocalDateTime getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(LocalDateTime expiration_date) {
        this.expiration_date = expiration_date;
    }
}
