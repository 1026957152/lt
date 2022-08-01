package com.lt.dom.thirdPft.domainPftTolt;

public class PftToLvOrderCreate {

    private String orderNum;//	string	订单号	是
    private String ticketNum;//	string	门票数量	是
    private String contactName;//	string	联系人	是
    private String contactPhone;//	string	联系人手机号	是
    private String touristName;//	string	游客姓名(多个逗号分隔)	是
    private String touristPhone;//	string	游客手机号(多个逗号分隔)	否
    private String touristIDCard;//	string	游客身份证信息(多个逗号分隔)：没有时候为空	否
    private String playTime;//	string	预计游玩日期(时间戳)	是
    private String goodsNo;//	string	三方系统配置的景点门票等信息	是
    private String pftCode;//	string	票付通凭证码	是
    private String clientId;//	string	三方系统客户标识	是
    private String timeShareBegin;//	string	分时时间段开始时间 10:00	是
    private String timeShareEnd;//	string	分时时间段结束时间 16:00	是
    private String sectionId;//	string	票务系统场次编码id	否
    private String costPrice;//	string	成本价（分）	否

}
