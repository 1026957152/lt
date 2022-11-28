package com.lt.dom.otcReq;

public class FeatureTag {

        private String key;
        private String icon;
        private String text;

    public static FeatureTag of(String key, String icon, String text) {
        FeatureTag featureTag = new FeatureTag();
        featureTag.icon = icon;
        featureTag.key = key;
        featureTag.text = text;
        return featureTag;
    }

    public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
