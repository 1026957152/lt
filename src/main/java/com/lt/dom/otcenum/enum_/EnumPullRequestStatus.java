package com.lt.dom.otcenum.enum_;

public enum EnumPullRequestStatus {
    授信中("0","授信中"),
    授信待确认("1","授信待确认"),
    撮合成功("2","撮合成功"),

    已成交("3","已成交"),
    机构驳回("4","机构驳回"),
    订单已过期("5","订单已过期"),
    授信已过期("6","授信已过期");

    private String id;
    private String text;

    public static EnumPullRequestStatus from(String status) {
        for(EnumPullRequestStatus enumPullRequestStatus:EnumPullRequestStatus.values()){
            if(enumPullRequestStatus.getId().equals(status)){
                return enumPullRequestStatus;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(String id) {
        this.id = id;
    }

    EnumPullRequestStatus(String id,String text){
        this.id = id;
        this.text = text;
    }
}
