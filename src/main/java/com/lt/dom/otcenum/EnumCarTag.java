package com.lt.dom.otcenum;
//https://pic.c-ctrip.com/pic/platform/bbz/h5/h5home/v1/nav/main/flight_package.png
public enum EnumCarTag {

    with_driver("空间","配司机",
            "http://yulinmei.cn:8080/oct/files/hero.png","icon"),
    insurance("动力","有保险",
            "http://yulinmei.cn:8080/oct/files/citypass.png","icon"),


    at_least_2_day("活动","2天起租",
            "http://yulinmei.cn:8080/oct/files/activity.jpg","icon"),

    ;

    private String title;
    private String Display_name;
    private String feature_image;

    private String url;
    private String icon;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplay_name() {
        return Display_name;
    }

    public void setDisplay_name(String display_name) {
        Display_name = display_name;
    }

    public String getFeature_image() {
        return feature_image;
    }

    public void setFeature_image(String feature_image) {
        this.feature_image = feature_image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    EnumCarTag(String title, String Display_name, String feature_image, String icon) {
        this.title = title;
        this.Display_name = Display_name;
        this.feature_image = feature_image;
        this.icon = icon;

    }
}
