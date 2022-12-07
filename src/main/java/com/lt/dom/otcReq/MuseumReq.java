package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lt.dom.OctResp.LocationReq;
import com.lt.dom.OctResp.PhoneResp;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.oct.Base;
import com.lt.dom.oct.Museum;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

public class MuseumReq extends Base {

    private String name;
    private String name_long;

    private String desc_short;
    private String desc_long;
    private String slug;

    private LocationReq location;

    public LocationReq getLocation() {
        return location;
    }

    public void setLocation(LocationReq location) {
        this.location = location;
    }

    @DateTimeFormat(pattern = "HH:mm:ss")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime close_time;
    @DateTimeFormat(pattern = "HH:mm:ss")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime open_time;
    private String guestServicesPhoneNumber;




    private MediaReq media;
    private PhotoResp cover;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_long() {
        return name_long;
    }

    public void setName_long(String name_long) {
        this.name_long = name_long;
    }

    public String getDesc_short() {
        return desc_short;
    }

    public void setDesc_short(String desc_short) {
        this.desc_short = desc_short;
    }

    public String getDesc_long() {
        return desc_long;
    }

    public void setDesc_long(String desc_long) {
        this.desc_long = desc_long;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public LocalTime getClose_time() {
        return close_time;
    }

    public void setClose_time(LocalTime close_time) {
        this.close_time = close_time;
    }

    public LocalTime getOpen_time() {
        return open_time;
    }

    public void setOpen_time(LocalTime open_time) {
        this.open_time = open_time;
    }

    public String getGuestServicesPhoneNumber() {
        return guestServicesPhoneNumber;
    }

    public void setGuestServicesPhoneNumber(String guestServicesPhoneNumber) {
        this.guestServicesPhoneNumber = guestServicesPhoneNumber;
    }

    public MediaReq getMedia() {
        return media;
    }

    public void setMedia(MediaReq media) {
        this.media = media;
    }

    public static MuseumReq from(Museum e) {
        MuseumReq museumResp = new MuseumReq();

        return museumResp;
    }

    public PhotoResp getCover() {
        return cover;
    }

    public void setCover(PhotoResp cover) {
        this.cover = cover;
    }

    public static class MediaReq{
        private String  icon;
        private String  large;
        private String  standard;
        private String  thumbnail;
        private PhotoResp  audio;

        private PhotoResp  portrait;
        private PhotoResp  introductionAudio;

        public PhotoResp getPortrait() {
            return portrait;
        }

        public void setPortrait(PhotoResp portrait) {
            this.portrait = portrait;
        }

        public PhotoResp getIntroductionAudio() {
            return introductionAudio;
        }

        public void setIntroductionAudio(PhotoResp introductionAudio) {
            this.introductionAudio = introductionAudio;
        }

        public PhotoResp getAudio() {
            return audio;
        }

        public void setAudio(PhotoResp audio) {
            this.audio = audio;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getStandard() {
            return standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }
}
