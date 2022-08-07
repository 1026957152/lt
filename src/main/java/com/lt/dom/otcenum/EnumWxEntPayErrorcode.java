package com.lt.dom.otcenum;

public enum EnumWxEntPayErrorcode {


    INVALID_REQUEST("无效的请求，商户系统异常导致，商户权限异常、证书错误、频率限制等","使用原单号以及原请求参数重试"),
    SYSTEMERROR("业务错误导致交易失败","请先调用查询接口，查询此次付款结果，如结果为不明确状态（如订单号不存在），请务必使用原商户订单号及原请求参数重试"),
    PARAM_ERROR("参数错误，商户系统异常导致","商户检查请求参数是否合法，证书"),
    SIGNERROR("签名错误","按照文档签名算法进行签名值计算"),
    AMOUNT_LIMIT("超额；已达到今日付款金额上限或已达到今日银行卡收款金额上限","今天暂停该商户发起代付请求或今日暂停向该银行卡转账"),
    ORDERPAID("超过付款重入有效期","请勿重复请求，通过查询接口确认订单信息, 以查单结果为准"),
    FATAL_ERROR("已存在该单，并且订单信息不一致；或订单太老","核定订单信息"),
    NOTENOUGH("账号余额不足","请用户充值或更换支付卡后再支付"),
    FREQUENCY_LIMITED("超过每分钟600次的频率限制","稍后用原单号重试"),
    SUCCESS("Wx侧受理成功",""),
    RECV_ACCOUNT_NOT_ALLOWED("收款账户不在收款账户列表","请登陆商户平台，查看产品中心企业付款到银行卡的产品配置"),
    PAY_CHANNEL_NOT_ALLOWED("本商户号未配置API发起能力","请登陆商户平台，查看产品中心企业付款到银行卡的产品配置"),
    SENDNUM_LIMIT("已达到付款给此用户次数上限","请登陆商户平台，查看产品中心企业付款到银行卡的产品配置"),
    NO_AUTH("此IP地址不允许调用接口","请登陆商户平台，查看产品中心企业付款到零钱的产品配置"),
    ;


    EnumWxEntPayErrorcode(String barcode,String aa) {

    }


    public static EnumWxEntPayErrorcode fromName(String id){
        for(EnumWxEntPayErrorcode e:EnumWxEntPayErrorcode.values()){
            if(e.name().equals(id)){
                return e;
            }
        }
        throw new RuntimeException("Enum not found");
    }
}
