package com.lt.dom.OctResp;

import com.lt.dom.oct.Redemption;
import com.lt.dom.oct.RedemptionEntry;
import org.javatuples.Pair;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.List;


public class RedemptionResp {


    private int quantity;
    private int redeemed_quantity;

    private List<RedemptionEntryResp> entries;

    public static RedemptionResp from(Pair<RedemptionEntry, Redemption> pair) {
        RedemptionResp redemptionResp = new RedemptionResp();
        redemptionResp.setRedeemed_quantity(pair.getValue1().getRedeemed_quantity());
        redemptionResp.setRelatedObjectId(pair.getValue1().getRelatedObjectId());
        redemptionResp.setRelatedObjectType(pair.getValue1().getRelatedObjectType());

        RedemptionEntryResp redemptionEntryResp = new RedemptionEntryResp();
        redemptionEntryResp.setRedemption(pair.getValue1().getId());
       // redemptionEntryResp.setResult(pair.getValue0().getResult());
        redemptionEntryResp.setVoucher(pair.getValue0().getVoucher());
        redemptionEntryResp.setGift_amount(pair.getValue0().getGift_amount());
        redemptionEntryResp.setRelatedObjectType(pair.getValue0().getRelatedObjectType());
        redemptionEntryResp.setRelatedObjectId(pair.getValue0().getRelatedObjectId());
        redemptionResp.setEntries(Arrays.asList(redemptionEntryResp));
        return redemptionResp;
    }

    public List<RedemptionEntryResp> getEntries() {
        return entries;
    }

    public void setEntries(List<RedemptionEntryResp> entries) {
        this.entries = entries;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRedeemed_quantity() {
        return redeemed_quantity;
    }

    public void setRedeemed_quantity(int redeemed_quantity) {
        this.redeemed_quantity = redeemed_quantity;
    }


    private String relatedObjectType;
    private long relatedObjectId;

    public String getRelatedObjectType() {
        return relatedObjectType;
    }

    public void setRelatedObjectType(String related_object_type) {
        this.relatedObjectType = related_object_type;
    }

    public long getRelatedObjectId() {
        return relatedObjectId;
    }

    public void setRelatedObjectId(long related_object_id) {
        this.relatedObjectId = related_object_id;
    }
}
