package com.lt.dom.otcenum.layout;

public enum EnumLayoutFeatured {
    Marketing("线路旅游","周边有","image","icon"),
    Sales("城市主人卡","周边有","image","icon"),
    Support("租车服务","周边有","image","icon"),
    退款_售后("导游服务","周边有","image","icon"),


    申请入驻("文旅大数据","周边有","image","icon"),
    成为导游("文旅大数据","周边有","image","icon"),



    
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

    EnumLayoutFeatured(String title, String Display_name, String feature_image, String icon) {
        this.title = title;
        this.Display_name = Display_name;
        this.feature_image = feature_image;
        this.icon = icon;

    }
}
