package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Blog;
import com.lt.dom.oct.User;
import com.lt.dom.otcenum.EnumReminderSearchItemType;

import java.util.List;


//https://metmuseum.github.io/


@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomeSearchItemResp {


    private String lable;
    private EnumReminderSearchItemType type;
    private PhotoResp photo;
    private long id;

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public EnumReminderSearchItemType getType() {
        return type;
    }

    public void setType(EnumReminderSearchItemType type) {
        this.type = type;
    }

    public PhotoResp getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoResp photo) {
        this.photo = photo;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
