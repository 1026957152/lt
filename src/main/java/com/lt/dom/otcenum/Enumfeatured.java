package com.lt.dom.otcenum;

public enum Enumfeatured {
    barcode("线路旅游","周边有","image","icon"),
    city_hero("城市主人卡","周边有","image","icon"),
    Barcodescan("租车服务","周边有","image","icon"),
    FacialRecognition("导游服务","周边有","image","icon"),
    bigdata("文旅大数据","周边有","image","icon"),

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
