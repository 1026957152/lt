package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumMediaType;


public class MediaGenralReq {


    private String title;//"theatreImageIcon": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/StoreIcon/Thumb/outage.jpg",
    private String  description_lang;//       "theatreImageLarge": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/Primary/Large/outage.jpg",
    private EnumMediaType mediaType;//          "theatreImageStandard": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/Primary/Standard/outage.jpg",
    private String  href;//          "theatreImageThumbnail": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/Primary/Thumb/outage.jpg",
    private String  description_text;//       "theatreImageLarge": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/Primary/Large/outage.jpg",
    private String preview_image_url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription_lang() {
        return description_lang;
    }

    public void setDescription_lang(String description_lang) {
        this.description_lang = description_lang;
    }

    public EnumMediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(EnumMediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getDescription_text() {
        return description_text;
    }

    public void setDescription_text(String description_text) {
        this.description_text = description_text;
    }

    public String getPreview_image_url() {
        return preview_image_url;
    }

    public void setPreview_image_url(String preview_image_url) {
        this.preview_image_url = preview_image_url;
    }
}
