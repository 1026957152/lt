package com.lt.dom.otcenum;
//https://pic.c-ctrip.com/pic/platform/bbz/h5/h5home/v1/nav/main/flight_package.png
public enum Enumfeatured {

    city_hero("城市主人卡","周边有",
            "http://yulinmei.cn:8080/oct/files/hero.png","icon"),
    city_pass("榆林PASS","周边有",
            "http://yulinmei.cn:8080/oct/files/citypass.png","icon"),
    tours("线路旅游","周边有",
            "http://yulinmei.cn:8080/oct/files/tours.png","icon"),


    activity("活动","周边有",
            "http://yulinmei.cn:8080/oct/files/activity.jpg","icon"),
    rentcars("租车服务","周边有",
            "http://yulinmei.cn:8080/oct/files/rentcar.png","icon"),
    导游("导游服务","周边有",
            "https://pic.c-ctrip.com/pic/platform/bbz/h5/h5home/v1/nav/main/weekend.png","icon"),
    bigdata("文旅大数据","周边有",
            "http://yulinmei.cn:8080/oct/files/bigdata.png","icon"),


    City_walk("City walk","周边有",
            "http://yulinmei.cn:8080/oct/files/walk.png","icon"),

    线上博物馆("线上博物馆","周边有",
            "http://yulinmei.cn:8080/oct/files/广东省博物馆.png","icon"),
    信用榆林("信用榆林","周边有",
            "http://yulinmei.cn:8080/oct/files/信用榆林.png","icon"),

    驼城小易("驼城小易","驼城小易",
            "http://yulinmei.cn:8080/oct/files/bus.png","icon"),

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

    Enumfeatured(String title, String Display_name, String feature_image, String icon) {
        this.title = title;
        this.Display_name = Display_name;
        this.feature_image = feature_image;
        this.icon = icon;

    }
}
