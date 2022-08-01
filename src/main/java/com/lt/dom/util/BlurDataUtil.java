package com.lt.dom.util;

import org.apache.commons.lang.StringUtils;
 
/**
 * 数据脱敏工具类
 *
 */
public class BlurDataUtil {
 
    /**
     * 手机号脱敏处理
     * 脱敏规则: 保留前三后四, 比如 18738291234 置换为 187****1234
     * @param phone
     * @return
     */
    public static final String blurPhone(String phone) {
        if (StringUtils.isEmpty(phone) || (phone.length() != 11)) {
            return phone;
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }
 
    /**
     * 身份证号脱敏处理
     * 原身份证号：500222202110275699，脱敏后：132****99308084911
     * @param idCard
     * @return
     */
    public static String blurIdCard(String idCard) {
        if (StringUtils.isEmpty(idCard)) {
            return "";
        }
        /*
         * 参数1：证件号，参数2（OVERLAY）：替换后的字符串，
         * 参数3（START）：替换的起始下标，参数4（END）：替换的结束下标（不包含）
         */
        return null;//StringUtils.overlay(idCard, "****", 3, 7);
    }
 
      /**
     * 身份证号脱敏处理
     * 展示 前6位和后6位
     * @param idCard
     * @return
     */
/*    public static String hiddenIdCard(String idCard) {//身份证
        if (StringUtils.isBlank(idCard)) {
            return "";
        }
        return StringUtils.left(idCard, 6).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(idCard, 4), StringUtils.length(idCard), "*"), "***"));
    }
 */
    /**
     * 邮箱脱敏处理
     * 原邮箱：zhangsan@qq.com，脱敏后：zhang***@qq.com
     * @param email
     * @return
     */
    public static String blurEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return email;
        }
        String encrypt = email.replaceAll("(\\w+)\\w{3}@(\\w+)", "$1***@$2");
        if(StringUtils.equalsIgnoreCase(email, encrypt)){
            encrypt = email.replaceAll("(\\w*)\\w{1}@(\\w+)", "$1*@$2");
        }
        return encrypt;
    }
 
    /**
     * 护照脱敏处理
     * 脱敏规则：护照前2后3位脱敏，护照一般为8或9位
     * @param passport
     * @return
     */
    public static String blurPassport(String passport) {
        if (StringUtils.isEmpty(passport) || (passport.length() < 8)) {
            return passport;
        }
        return passport.substring(0, 2) + new String(new char[passport.length() - 5]).replace("\0", "*") + passport.substring(passport.length() - 3);
    }
 
    /**
     * 字段信息脱敏
     * 脱敏规则:如果字符长度大于3位，则隐藏最后三位，否则隐藏最后1位
     * @param field
     * @return
     */
    public static String blurField(String field) {
        if (StringUtils.isEmpty(field)) {
            return field;
        }
        String encrypt = field.replaceAll("(\\w+)\\w{3}", "$1***");
        if(StringUtils.equalsIgnoreCase(field, encrypt)){
            encrypt = field.replaceAll("(\\w*)\\w{1}", "$1*");
        }
        return encrypt;
    }
 
    public static void main(String[] args) {
        System.out.println(blurPhone("18738291234"));  // 187****1234
        System.out.println(blurIdCard("500222202110275699"));  // 500****99410275467
        System.out.println(blurEmail("zhangsan@qq.com"));  // zhang***@qq.com
        System.out.println(blurPassport("12345678"));  // 12***678
        System.out.println(blurField("I feel so good"));  // I f*** so g***
    }
 
    /*
     * 备注：
     * 1、String.replaceAll(第1个参数是脱敏筛选的正则，第2个参数是脱敏替换的正则)
     * 2、需要引入commons-lang3，这个基本每个项目都用到
     * <dependency>
     *     <groupId>org.apache.commons</groupId>
     *     <artifactId>commons-lang3</artifactId>
     *     <version>3.7</version>
     * </dependency>
     */
}
/*
————————————————
版权声明：本文为CSDN博主「格子衫111」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/u012660464/article/details/116235082*/
