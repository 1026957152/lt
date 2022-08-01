package com.lt.dom.minapp;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ƾ֤
 *
 * @author liufeng
 * @date 2013-10-17
 */
public class Token implements Serializable {
    private static final long serialVersionUID = -797586847427389162L;

    private String accessToken;

    private int expiresIn;

    private LocalDateTime reveiveTime;
    private String appId;

    public String getAccessToken() {

        return accessToken;
    }

    public LocalDateTime getReveiveTime() {
        return reveiveTime;
    }

    public void setReveiveTime(LocalDateTime reveiveTime) {
        this.reveiveTime = reveiveTime;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }
}