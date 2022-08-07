package com.lt.dom.OctResp;

import com.lt.dom.oct.RedemptionEntry;
import com.lt.dom.oct.Supplier;

import java.util.List;
import java.util.stream.Collectors;


public class SummarySupplierResp {


    private int quantity;
    private int redeemed_quantity;

    private List<RedemptionEntryResp> entries;

    public static SummarySupplierResp from(Supplier supplier, List<RedemptionEntry> redemptionEntries) {
        SummarySupplierResp redemptionResp = new SummarySupplierResp();
        redemptionResp.setTotal_redeemed(supplier.getTotal_redeemed());
        redemptionResp.setTotal_succeeded(supplier.getTotal_succeeded());
        redemptionResp.setTotal_failed(supplier.getTotal_failed());
        redemptionResp.setTotal_rollback_succeeded(supplier.getTotal_rollback_succeeded());
        redemptionResp.setTotal_rollback_failed(supplier.getTotal_rollback_failed());
        redemptionResp.setEntries(redemptionEntries.stream().map(x->{
            RedemptionEntryResp redemptionEntryResp = new RedemptionEntryResp();
/*            redemptionEntryResp.setRedemption(x.getRedemption());
            redemptionEntryResp.setVoucher(x.getVoucher());
            redemptionEntryResp.setGift_amount(x.getGift_amount());*/
            redemptionEntryResp.setRelatedObjectType(x.getRelatedObjectType());
           // redemptionEntryResp.setRelatedObjectId(x.getRelatedObjectId());
            return redemptionEntryResp;
        }).collect(Collectors.toList()));
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



    private long total_redeemed;
    private long total_failed;
    private long total_succeeded;
    private long total_rolled_back;
    private long total_rollback_failed;
    private long total_rollback_succeeded;

    public long getTotal_redeemed() {
        return total_redeemed;
    }

    public void setTotal_redeemed(long total_redeemed) {
        this.total_redeemed = total_redeemed;
    }

    public long getTotal_failed() {
        return total_failed;
    }

    public void setTotal_failed(long total_failed) {
        this.total_failed = total_failed;
    }

    public long getTotal_succeeded() {
        return total_succeeded;
    }

    public void setTotal_succeeded(long total_succeeded) {
        this.total_succeeded = total_succeeded;
    }

    public long getTotal_rolled_back() {
        return total_rolled_back;
    }

    public void setTotal_rolled_back(long total_rolled_back) {
        this.total_rolled_back = total_rolled_back;
    }

    public long getTotal_rollback_failed() {
        return total_rollback_failed;
    }

    public void setTotal_rollback_failed(long total_rollback_failed) {
        this.total_rollback_failed = total_rollback_failed;
    }

    public long getTotal_rollback_succeeded() {
        return total_rollback_succeeded;
    }

    public void setTotal_rollback_succeeded(long total_rollback_succeeded) {
        this.total_rollback_succeeded = total_rollback_succeeded;
    }
}
