package com.lt.dom.otcenum;

public enum EnumLayoutFeatured {
    全部订单("我的订单","全部订单","全部订单","image","icon"),
    待付款("我的订单","待付款","待付款","image","icon"),
    未出现("我的订单","未出现","未出现","image","icon"),
    退款_售后("我的订单","退款/售后","退款/售后","icon","icon"),


    申请入驻("我的工具","申请入驻","申请入驻","image","icon"),
    成为导游("我的工具","成为导游","成为导游","image","icon"),

    客服中心("我的服务","客服中心","客服中心","image","icon"),
    常用出行人("我的服务","常用出行人","常用出行人","image","icon"),
    收货地址("我的服务","收货地址","收货地址","image","icon"),

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

    EnumLayoutFeatured(String group, String title, String Display_name, String feature_image, String icon) {
        this.group = group;
        this.title = title;
        this.Display_name = Display_name;
        this.feature_image = feature_image;
        this.icon = icon;

    }
}
