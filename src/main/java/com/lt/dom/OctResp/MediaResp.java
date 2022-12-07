package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Media;
import com.lt.dom.otcenum.EnumMediaType;

import javax.persistence.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class MediaResp extends BaseResp{


    private String title;//"theatreImageIcon": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/StoreIcon/Thumb/outage.jpg",
    private Description  description;//       "theatreImageLarge": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/Primary/Large/outage.jpg",
    private EnumMediaType mediaType;//          "theatreImageStandard": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/Primary/Standard/outage.jpg",
    private String  href;//          "theatreImageThumbnail": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/Primary/Thumb/outage.jpg",
    private String preview_image_url;

    private String square;
    private String landscape;
    private PhotoResp portrait;
    private String video_preview;
    private PhotoResp audio;
    private PhotoResp introductionAudio;
    private String code;

    public PhotoResp getAudio() {
        return audio;
    }

    public void setAudio(PhotoResp audio) {
        this.audio = audio;
    }

    public String getSquare() {
        return square;
    }

    public void setSquare(String square) {
        this.square = square;
    }

    public String getLandscape() {
        return landscape;
    }

    public void setLandscape(String landscape) {
        this.landscape = landscape;
    }

    public PhotoResp getPortrait() {
        return portrait;
    }

    public void setPortrait(PhotoResp portrait) {
        this.portrait = portrait;
    }

    public String getVideo_preview() {
        return video_preview;
    }

    public void setVideo_preview(String video_preview) {
        this.video_preview = video_preview;
    }

    public static MediaResp from(Media media) {
        MediaResp mediaResp = new MediaResp();
        mediaResp.setHref(media.getHref());
        mediaResp.setCode(media.getCode());
        mediaResp.setPreview_image_url(media.getPreview_image_url());
        mediaResp.setMediaType(media.getMediaType());
        mediaResp.setTitle(media.getTitle());
        Description description1 = new Description(media.getDescription_lang(),media.getDescription_text());

        mediaResp.setDescription(description1);
        mediaResp.setCreatedDate(media.getCreatedDate());
        mediaResp.setModifiedDate(media.getModifiedDate());

        return mediaResp;
    }


    public static MediaResp simpleFrom(Media media) {
        MediaResp mediaResp = new MediaResp();
        mediaResp.setHref(media.getHref());
        mediaResp.setPreview_image_url(media.getPreview_image_url());
        mediaResp.setMediaType(media.getMediaType());

        return mediaResp;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setPreview_image_url(String preview_image_url) {

        this.preview_image_url = preview_image_url;
    }

    public String getPreview_image_url() {
        return preview_image_url;
    }

    public void setIntroductionAudio(PhotoResp introductionAudio) {

        this.introductionAudio = introductionAudio;
    }

    public PhotoResp getIntroductionAudio() {
        return introductionAudio;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static class Description {
        private String  lang;//       "theatreImageLarge": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/Primary/Large/outage.jpg","theatreImageThumbnail": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/Primary/Thumb/outage.jpg",
        private String  text;//       "theatreImageLarge": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/Primary/Large/outage.jpg",

        public Description(String description_lang, String description_text) {
            this.lang = description_lang;
            this.text = description_text;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

}
