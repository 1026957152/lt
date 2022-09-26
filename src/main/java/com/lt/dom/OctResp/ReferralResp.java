package com.lt.dom.OctResp;

import com.lt.dom.oct.Referral;

public class ReferralResp {

    private String referral_url;// 和 referral_code
    private String referral_code;//
    private String path;
    private String message;

    public static ReferralResp from(Referral referral) {

        ReferralResp referralResp = new ReferralResp();
        referralResp.setReferral_code(referral.getCode());
        referralResp.setReferral_url(referral.getPath());
        referralResp.setPath(referral.getPath());
        return referralResp;
    }

    public String getReferral_url() {
        return referral_url;
    }

    public void setReferral_url(String referral_url) {
        this.referral_url = referral_url;
    }

    public String getReferral_code() {
        return referral_code;
    }

    public void setReferral_code(String referral_code) {
        this.referral_code = referral_code;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
