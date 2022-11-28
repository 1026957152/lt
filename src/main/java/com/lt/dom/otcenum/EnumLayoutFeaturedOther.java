package com.lt.dom.otcenum;

public enum EnumLayoutFeaturedOther {

    登出("profile","登出","全部订单",
            "http://yulinmei.cn:8080/oct/files/icon_me/dingdan.png","icon"),
    更换账号("profile","更换账号","待付款",
            "http://yulinmei.cn:8080/oct/files/icon_me/daifukuan.png","icon"),

    ;
    private String group;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    EnumLayoutFeaturedOther(String group, String title, String Display_name, String feature_image, String icon) {
        this.group = group;
        this.title = title;
        this.Display_name = Display_name;
        this.feature_image = feature_image;
        this.icon = icon;

    }
}
