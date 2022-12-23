package com.lt.dom.util;

import java.math.BigDecimal;
import java.util.Properties;

public class CommonUtils {
    public static BigDecimal random(int min, int max, int num){

        return new BigDecimal(min+Math.random()*(max-min)).setScale(num,BigDecimal.ROUND_HALF_UP);

    }

    /**
     * 获取zfbinfo.properties文件里的值
     * @param name key
     * @return
     * @throws Exception
     */
    public String getZFBinfoValue(String name) throws Exception{
        Properties props = new Properties();
        props.load(getClass().getResourceAsStream("/zfbinfo.properties"));
        String filepath = props.getProperty(name);;
        return filepath;
    }
}
/*
————————————————
版权声明：本文为CSDN博主「蓝胖子哇」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/weixin_44004020/article/details/111472797*/
