package com.lt.dom.util;

import cn.hutool.core.codec.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * 微信解密工具类(AES-256-ECB解密 PKCS7Padding)
 * 解密方式官方文档：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_16&index=10#menu1
 * 解密步骤如下：
 * （1）对加密串A做base64解码，得到加密串B
 * （2）对商户key做md5，得到32位小写key* ( key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置 )
 * （3）用key*对加密串B做AES-256-ECB解密（PKCS7Padding）
 */
@Component
public class WxDecodeUtil {

    private String algorithm = "AES";

    private String algorithmModePadding = "AES/ECB/PKCS7Padding";

    private String key;

    SecretKeySpec secretKey;
	
	private boolean initialized = false;

    /**
     * AES解密
     * @param base64Data
     * @return
     * @throws Exception
     */
    public String decryptData(String base64Data) throws Exception {
		initialize();
 
        // 获取解码器实例，"BC"指定Java使用BouncyCastle库里的加/解密算法。
        Cipher cipher = Cipher.getInstance(algorithmModePadding, "BC");
        // 使用秘钥并指定为解密模式初始化解码器
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        // cipher.doFinal(byte[] b)在单部分操作中加密或解密数据，或完成多部分操作。 根据此秘钥的初始化方式，对数据进行加密或解密。
        return new String(cipher.doFinal(Base64.decode(base64Data)));
    }
    
    /**
     * 安全提供者列表中注册解密算法提供者，这个加载过程还挺慢的，有时候要好几秒，只需要加载一次就能一直使用。
     */
    private void initialize() {
        if (initialized) {
            return;
        }

        Security.addProvider(new BouncyCastleProvider());
        initialized = true;
    }


    /**
     * 构造方法(容器初始化时从配置文件中获取key，在全局中维护一个唯一的SecretKeySpec)
     * @param key
     */
    public WxDecodeUtil(@Value("${wxpay.key}") String key) {
        this.key = key;
        // 转化成JAVA的密钥格式
        secretKey = new SecretKeySpec(MD5Util.MD5Encode(key, "UTF-8").toLowerCase().getBytes(), algorithm);
    }

}
/*
————————————————
版权声明：本文为CSDN博主「我这不是胖我是幸福肥」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq_41693150/article/details/102543016*/
