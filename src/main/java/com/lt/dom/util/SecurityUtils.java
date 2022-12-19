package com.lt.dom.util;

import cn.hutool.crypto.symmetric.AES;
 
public class SecurityUtils {
 
    // 加密
    public static String encryption(String data){
        AES aes = new AES("CBC", "PKCS7Padding",
                // 密钥，不可更改
                "GzcccPCall012345".getBytes(),
                // iv加盐
                "GzcccCallDYgjCE0".getBytes());
        // 加密为16进制表示
        String encryptHex = aes.encryptHex(data);
        return encryptHex;
    }
    public static String encryption_base64(String data){
        AES aes = new AES("CBC", "PKCS7Padding",
                // 密钥，不可更改
                "GzcccPCall012345".getBytes(),
                // iv加盐
                "GzcccCallDYgjCE0".getBytes());
        // 加密为16进制表示
        String encryptHex = aes.encryptBase64(data);
        return encryptHex;
    }

    // 解密
    public static String decryption(String data){
        AES aes = new AES("CBC", "PKCS7Padding",
                // 密钥，不可更改
                "GzcccPCall012345".getBytes(),
                // iv加盐
                "GzcccCallDYgjCE0".getBytes());
        // 加密为16进制表示
        String decryptStr = aes.decryptStr(data);
        return decryptStr;
    }
 
 
}
/*
————————————————
版权声明：本文为CSDN博主「多汁多味」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/weixin_43652507/article/details/123208938*/
