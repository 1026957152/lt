package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.OctResp.PublicationEntryResp;
import com.lt.dom.otcenum.EnumVoucherStatus;
import com.lt.dom.otcenum.EnumVoucherType;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Voucher {


    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
    @SequenceGenerator(name = "seqGen", sequenceName = "products_id_seq", allocationSize = 1)
  //  @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
  //  @JsonProperty("id")
    private long id;

    private String code;
    private long campaign;
    private long relateId;
    private boolean published;
    private String additionalInfo;

    private LocalDateTime expiry_datetime;
    private EnumVoucherStatus status;

    public LocalDateTime getExpiry_datetime() {
        return expiry_datetime;
    }

    public void setExpiry_datetime(LocalDateTime expiry_datetime) {
        this.expiry_datetime = expiry_datetime;
    }

    @Version
    private int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String barcode_data;

    private String redemptionMethod;


    @Transient
    private List<ComponentVounch> componentVounchList;

    public List<ComponentVounch> getComponentRightVounchList() {
        return componentVounchList;
    }

    public void setComponentRightVounchList(List<ComponentVounch> componentVounchList) {
        this.componentVounchList = componentVounchList;
    }

    /*
    How the voucher can be redeemed. Possible values are:
    MANIFEST The guest name will be written down and they just need to show up
    DIGITAL The tickets/voucher must be scanned but can be on mobile
    PRINT The tickets/voucher must be printed and presented on arrival
*/






    @Transient
    private Discount discount ;
    @Transient
    private GiftVoucher giftVoucher ;




    @Transient
    private List assets;



    private LocalDateTime start_date;
    private LocalDateTime expiration_date;

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public LocalDateTime getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(LocalDateTime expiration_date) {
        this.expiration_date = expiration_date;
    }

    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Transient
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


    public void setCampaign(long campaign) {
        this.campaign = campaign;
    }

    public long getCampaign() {
        return campaign;
    }

    public void setRelateId(long relateId) {
        this.relateId = relateId;
    }

    public long getRelateId() {
        return relateId;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean getPublished() {
        return published;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }


    public void setStatus(EnumVoucherStatus status) {
        this.status = status;
    }

    public EnumVoucherStatus getStatus() {
        return status;
    }
}
