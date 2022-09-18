package com.lt.dom.otcReq;


import javax.validation.constraints.NotEmpty;
import java.util.List;


public class IndexPhotosReq {




    private List<ImageReq> images;

    public List<ImageReq> getImages() {
        return images;
    }

    public void setImages(List<ImageReq> images) {
        this.images = images;
    }
}
