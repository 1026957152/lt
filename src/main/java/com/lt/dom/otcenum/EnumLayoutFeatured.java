package com.lt.dom.otcenum;

import java.util.Arrays;

public enum EnumLayoutFeatured {

    商户首页("商户操作","商户首页","全部订单",
            "http://yulinmei.cn:8080/oct/files/icon_me/dingdan.png","icon","key"),
    核销列表("商户操作","核销列表","待付款",
            "http://yulinmei.cn:8080/oct/files/icon_me/daifukuan.png","icon","key"),
    核销详情("商户操作","核销详情","未出现",
            "http://yulinmei.cn:8080/oct/files/icon_me/daifukuan.png","icon","key"),

    职工列表("商户操作","职工列表","职工列表",
            "http://yulinmei.cn:8080/oct/files/icon_me/employee.png","icon","key"),

    授权核销("商户操作","授权核销","授权核销",
            "http://yulinmei.cn:8080/oct/files/icon_me/shield.png","icon","key"),


/*
    商户入驻("商户操作","商户入驻","退款/售后",
            "http://yulinmei.cn:8080/oct/files/icon_me/shouhou.png","icon","key"),
*/




    全部订单("我的订单","全部订单","全部订单",
            "http://yulinmei.cn:8080/oct/files/icon_me/dingdan.png","icon","key"),
    待付款("我的订单","待付款","待付款",
            "http://yulinmei.cn:8080/oct/files/icon_me/daifukuan.png","icon","key"),
/*    未出现("我的订单","未出行","未出现",
            "http://yulinmei.cn:8080/oct/files/icon_me/信用榆林.png","icon","key"),*/
    退款_售后("我的订单","退款/售后","退款/售后",
            "http://yulinmei.cn:8080/oct/files/icon_me/shouhou.png","icon","key"),
    卡包("我的订单","卡包","我的卡包",
            "http://yulinmei.cn:8080/oct/files/icon_me/cards.png","icon","key"),
    门票("我的订单","门票","我的门票",
            "http://yulinmei.cn:8080/oct/files/icon_me/ticket.png","icon","key"),

    优惠券("我的订单","优惠券","我的优惠券",
            "http://yulinmei.cn:8080/oct/files/icon_me/voucher.png","icon","key"),

    申请入驻("我的工具","申请入驻","申请入驻",
            "http://yulinmei.cn:8080/oct/files/icon_me/shangjiaruzhu.png","icon","key"),
    成为导游("我的工具","成为导游","成为导游",
            "http://yulinmei.cn:8080/oct/files/icon_me/daohang.png","icon","key"),
    主人卡激活("我的工具","主人卡激活","主人卡激活",
            "http://yulinmei.cn:8080/oct/files/icon_me/pass.jpg","icon","key"),
    添加卡("我的工具","添加卡","添加Expiry卡",
            "http://yulinmei.cn:8080/oct/files/icon_me/pass.jpg","icon","key"),
    客服中心("我的服务","客服中心","客服中心",
            "http://yulinmei.cn:8080/oct/files/icon_me/kefu.png","icon","key"),
    常用出行人("我的服务","常用出行人","常用出行人",
            "http://yulinmei.cn:8080/oct/files/icon_me/peisongrenyuan.png","icon","key"),
    收货地址("我的服务","收货地址","收货地址",
            "http://yulinmei.cn:8080/oct/files/icon_me/shouhuodizhi.png","icon","key"),

    ;
    private String group;
    private String title;
    private String Display_name;
    private String feature_image;

    private String url;
    private String icon;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    EnumLayoutFeatured(String group, String title, String Display_name, String feature_image, String icon,String key) {
        this.key = key;
        this.group = group;
        this.title = title;
        this.Display_name = Display_name;
        this.feature_image = feature_image;
        this.icon = icon;

    }



}
