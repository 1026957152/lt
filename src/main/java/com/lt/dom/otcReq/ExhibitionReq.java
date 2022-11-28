package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumPrivacyLevel;

import java.time.LocalDateTime;


public class ExhibitionReq  {

    private String title;
    private String slug;

    private String desc_long;
    private String desc_short;

    private EnumPrivacyLevel privacyLevel;

    private MuseumReq.MediaReq media;


    private LocalDateTime opening_date;

    public LocalDateTime getOpening_date() {
        return opening_date;
    }

    public void setOpening_date(LocalDateTime opening_date) {
        this.opening_date = opening_date;
    }
    public MuseumReq.MediaReq getMedia() {
        return media;
    }

    public void setMedia(MuseumReq.MediaReq media) {
        this.media = media;
    }

    private String organizer;//   "": "ipsum eiusmod quis",
    ;

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDesc_long() {
        return desc_long;
    }

    public void setDesc_long(String desc_long) {
        this.desc_long = desc_long;
    }

    public String getDesc_short() {
        return desc_short;
    }

    public void setDesc_short(String desc_short) {
        this.desc_short = desc_short;
    }

    public EnumPrivacyLevel getPrivacyLevel() {
        return privacyLevel;
    }

    public void setPrivacyLevel(EnumPrivacyLevel privacyLevel) {
        this.privacyLevel = privacyLevel;
    }






    /*  {
        "primaryimageurl": "http://nrs.harvard.edu/urn-3:huam:GS04997_dynmc",
            "venues": [
        {
            "ishamvenue": 1,
                "venueid": 10615,
                "name": "Harvard University Art Museums",
                "zipcode": "02138",
                "state": "MA",
                "address1": "32 Quincy Street",
                "address2": null,
                "begindate": "1995-06-03",
                "enddate": "1996-05-05",
                "fullname": "Harvard University Art Museums",
                "city": "Cambridge",
                "country": null
        }
            ],
        "shortdescription": null,
            "id": 249,
            "lastupdate": "2015-09-17T08:43:08-0400",
            "title": "David Smith: \"This work is my identity\"",
            "temporalorder": 3045,
            "exhibitionid": 249,
            "color": null,
            "description": null,
            "poster": {
        "caption": null,
                "imageurl": "http://nrs.harvard.edu/urn-3:huam:GS04997_dynmc"
    },
        "images": [
        {
            "date": "2010-11-17",
                "copyright": null,
                "imageid": 400027,
                "idsid": 20480582,
                "format": "image/jpeg",
                "caption": null,
                "description": null,
                "technique": null,
                "renditionnumber": "GS04997",
                "displayorder": 1,
                "baseimageurl": "https://nrs.harvard.edu/urn-3:huam:GS04997_dynmc",
                "alttext": null,
                "width": 1024,
                "iiifbaseuri": "https://ids.lib.harvard.edu/ids/iiif/20480582",
                "height": 712
        }
            ],
        "enddate": "1996-05-05",
            "begindate": "1995-06-03"
    }
*/



}
