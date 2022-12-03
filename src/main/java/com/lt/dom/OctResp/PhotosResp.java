package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Media;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhotosResp {

    private List<PhotoResp> gallery;
    private PhotoResp header;
    private PhotoResp teaser;

    public List<PhotoResp> getGallery() {
        return gallery;
    }

    public void setGallery(List<PhotoResp> gallery) {
        this.gallery = gallery;
    }

    public PhotoResp getHeader() {
        return header;
    }

    public void setHeader(PhotoResp header) {
        this.header = header;
    }

    public PhotoResp getTeaser() {
        return teaser;
    }

    public void setTeaser(PhotoResp teaser) {
        this.teaser = teaser;
    }
}
