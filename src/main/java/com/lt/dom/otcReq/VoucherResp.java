package com.lt.dom.otcReq;

import com.lt.dom.OctResp.AssetResp;
import com.lt.dom.OctResp.PublicationEntryResp;
import com.lt.dom.oct.ComponentVounch;
import com.lt.dom.oct.Discount;
import com.lt.dom.oct.GiftVoucher;
import com.lt.dom.otcenum.EnumDiscountVoucherCategory;
import com.lt.dom.otcenum.EnumVoucherType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


public class VoucherResp {

    private String code;
    private String campaign;

    private PublicationPojo publication;
    private AssetResp assets;

    public void setAssets(AssetResp assets) {
        this.assets = assets;
    }

    public AssetResp getAssets() {
        return assets;
    }


    public static class PublicationPojo {

        private List<PublicationEntryResp> entries;
        private String object = "list";
        private int count;
        private String data_ref = "entries";

        public List<PublicationEntryResp> getEntries() {
            return entries;
        }

        public void setEntries(List<PublicationEntryResp> entries) {
            this.entries = entries;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getData_ref() {
            return data_ref;
        }

        public void setData_ref(String data_ref) {
            this.data_ref = data_ref;
        }
    }






    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private long validatorSupplier;

    public long getValidatorSupplier() {
        return validatorSupplier;
    }

    public void setValidatorSupplier(long validatorSupplier) {
        this.validatorSupplier = validatorSupplier;
    }






    private String barcode_data;

    private String redemptionMethod;





    /*
    How the voucher can be redeemed. Possible values are:
    MANIFEST The guest name will be written down and they just need to show up
    DIGITAL The tickets/voucher must be scanned but can be on mobile
    PRINT The tickets/voucher must be printed and presented on arrival
*/






    private GiftVoucher giftVoucher ;







    private LocalDate start_date;
    private LocalDate expiration_date;

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(LocalDate expiration_date) {
        this.expiration_date = expiration_date;
    }

    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    private ComponentVounch componentVounch ;

    public ComponentVounch getComponentVounch() {
        return componentVounch;
    }

    public void setComponentVounch(ComponentVounch componentVounch) {
        this.componentVounch = componentVounch;
    }

    private EnumVoucherType type;

    public EnumVoucherType getType() {
        return type;
    }

    public void setType(EnumVoucherType type) {
        this.type = type;
    }








    private int quantity;// (integer, required) - Default: null. How many times a voucher can be redeemed. A null value means unlimited.

    private int redeemed_quantity;// (integer, required) - How many times a voucher has already been redeemed.

    private int redeemed_amount;// (integer) - Total Amount redeemed by the voucher. Value is multiplied by 100 to precisely represent 2 decimal places. For example, $100 balance is written as 10000.

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

    public int getRedeemed_amount() {
        return redeemed_amount;
    }

    public void setRedeemed_amount(int redeemed_amount) {
        this.redeemed_amount = redeemed_amount;
    }


    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getCampaign() {
        return campaign;
    }


    public DiscountPojo getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountPojo discount) {
        this.discount = discount;
    }


    private DiscountPojo discount;

    public PublicationPojo getPublication() {
        return publication;
    }

    public void setPublication(PublicationPojo publication) {
        this.publication = publication;
    }

    public static class DiscountPojo {



        private int amount_off;
        private int percent_off;
        private int unit_off;
        private EnumDiscountVoucherCategory category;


        public int getAmount_off() {
            return amount_off;
        }

        public void setAmount_off(int amount_off) {
            this.amount_off = amount_off;
        }

        public int getPercent_off() {
            return percent_off;
        }

        public void setPercent_off(int percent_off) {
            this.percent_off = percent_off;
        }

        public int getUnit_off() {
            return unit_off;
        }

        public void setUnit_off(int unit_off) {
            this.unit_off = unit_off;
        }

        public EnumDiscountVoucherCategory getCategory() {
            return category;
        }

        public void setCategory(EnumDiscountVoucherCategory category) {
            this.category = category;
        }
    }
}
