package com.lt.dom.otcReq;

public  class DescriptionReq {
        private String  lang;//       "theatreImageLarge": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/Primary/Large/outage.jpg","theatreImageThumbnail": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/Primary/Thumb/outage.jpg",
        private String  text;//       "theatreImageLarge": "https://amc-theatres-res.cloudinary.com/amc-cdn/theatres/images/Primary/Large/outage.jpg",

        public DescriptionReq(String description_lang, String description_text) {
            this.lang = description_lang;
            this.text = description_text;
        }

    public DescriptionReq() {
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