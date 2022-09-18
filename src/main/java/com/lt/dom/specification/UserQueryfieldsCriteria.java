package com.lt.dom.specification;


import com.lt.dom.otcenum.EnumTourBookingStatus;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public class UserQueryfieldsCriteria {

   // @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created_from;
    private String email;//	email~"amyt"	string
    private String metadata;//	metadata["key"]:"value"	token
    private String name;//		name~"amy"	string
    private String phone;//		phone:"+19999999999"	string

    private Boolean real_name_verified;//		phone:"+19999999999"	string

    private List<EnumTourBookingStatus> statuses;

    public List<EnumTourBookingStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<EnumTourBookingStatus> statuses) {
        this.statuses = statuses;
    }

    public Boolean getReal_name_verified() {
        return real_name_verified;
    }

    public void setReal_name_verified(Boolean real_name_verified) {
        this.real_name_verified = real_name_verified;
    }


    public LocalDateTime getCreated_from() {
        return created_from;
    }

    public void setCreated_from(LocalDateTime created_from) {
        this.created_from = created_from;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}