package com.lt.dom.otcReq;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.vo.ImageVo;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.List;


public class ImageReq {


    @NotNull
    private String image;


    @JsonProperty("default")
    private boolean default_;
    private Integer index;

    @NotNull
    private boolean visible;

    @NotNull
    private String desc; //Short description (NO HTML)

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

    public ImageVo toImageVo() {
        ImageVo imageVo = new ImageVo();
        imageVo.setImage(this.image);
        imageVo.setDefault_(this.default_);
        imageVo.setVisible(this.visible);
        imageVo.setIndex(this.index);
        imageVo.setDesc(this.desc);
        return imageVo;

    }
}
