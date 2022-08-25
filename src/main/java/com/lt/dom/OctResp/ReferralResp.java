package com.lt.dom.OctResp;

import com.lt.dom.oct.Referral;

public class ReferralResp {

    private String referral_url;// 和 referral_code
    private String referral_code;//

    public static ReferralResp from(Referral referral) {
        return null;
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
}
