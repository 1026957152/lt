package com.lt.dom.vo;

public class ChargeMetadataVo {



    private long campaign;
    private String campaign_code;
    private long volume_up_voucher;
        private Long voucher;
    private long payer;
    private long booking;


    public void setCampaign(long campaign) {
        this.campaign = campaign;
    }

    public long getCampaign() {
        return campaign;
    }

    public void setCampaign_code(String campaign_code) {
        this.campaign_code = campaign_code;
    }

    public String getCampaign_code() {
        return campaign_code;
    }

    public void setVolume_up_voucher(long volume_up_voucher) {
        this.volume_up_voucher = volume_up_voucher;
    }

    public long getVolume_up_voucher() {
        return volume_up_voucher;
    }

    public void setVoucher(Long voucher) {
        this.voucher = voucher;
    }

    public Long getVoucher() {
        return voucher;
    }

    public void setPayer(long payer) {
        this.payer = payer;
    }

    public long getPayer() {
        return payer;
    }

    public void setBooking(long booking) {
        this.booking = booking;
    }

    public long getBooking() {
        return booking;
    }
}
