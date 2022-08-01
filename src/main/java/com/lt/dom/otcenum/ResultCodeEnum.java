package com.lt.dom.otcenum;

/**
 * 统一返回结果状态信息类
 * #200表示成功
 * #1001～1999 区间表示参数错误
 * #2001～2999 区间表示用户错误
 * #3001～3999 区间表示接口异常
 * #4001～4999 区间表示业务错误
 * #5001～5999 区间表示部门错误
 * #9001～9999 区间表示运行时异常
 */
public enum ResultCodeEnum{

    // 统一状态码
    SUCCESS(200,"成功"),
    FAIL(999, "失败"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),

    /* 用户错误 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "您的登录已经超时或者已经在另一台机器登录，您被迫下线"),
    USER_ACCOUNT_NEED_BIND(2010,"请与系统账号进行绑定"),

    DGUT_ACCOUNT_GET_FAIL(2011,"莞工认证失败"),

    /* 业务错误 */
    NO_PERMISSION(4001, "没有权限"),

    /*数据获取失败*/
    DATA_NOT_EXIST(5001, "无数据可获取"),
    TIMEFORMAT_ERROR(5002, "时间格式必须为 yyyy-mm-dd"),

    DEPLOYER_PERMIT_API(6001,"开发者接口，请勿使用"),

    REDIRECT_REQUEST(7002,"重定向请求"),
    REDIRECT_REQUEST_LOGIN_SUCCESS(7001,"莞工登陆成功！"),

    /*运行时异常*/
    ARITHMETIC_EXCEPTION(9001,"算数异常"),
    NULL_POINTER_EXCEPTION(9002,"空指针异常"),
    ARRAY_INDEX_OUTOfBOUNDS_EXCEPTION(9003,"数组越界"),

    SERVER_ERROR(500,"服务器错误");
    //LOGIN_ERROR( 23005, "登录失败");

    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}