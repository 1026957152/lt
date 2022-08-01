package com.lt.dom.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class LtConfig {
    // 服务商 AppID
    @Value("${lt.url}")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String Url(String code) {
        return url+"/q/"+code;
    }
}