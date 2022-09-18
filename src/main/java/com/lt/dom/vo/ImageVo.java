package com.lt.dom.vo;


import com.fasterxml.jackson.annotation.JsonProperty;


public class ImageVo {


    private String image;


    @JsonProperty("default")
    private boolean default_;
    private Integer index;
    private boolean visible;



    private String title; //Short description (NO HTML)

    private String desc; //Short description (NO HTML)

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isDefault_() {
        return default_;
    }

    public void setDefault_(boolean default_) {
        this.default_ = default_;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
