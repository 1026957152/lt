package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.domain.SettleAccount;

import javax.persistence.*;

@Entity
public class Supplier {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonProperty("id")
    private long id;
    private String desc;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Transient
    private PingxxUser pingxxUser;

    @Transient
    private SettleAccount settleAccount;

    public SettleAccount getSettleAccount() {
        return settleAccount;
    }

    public void setSettleAccount(SettleAccount settleAccount) {
        this.settleAccount = settleAccount;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("name")
    private String name;
    @JsonProperty("endpoint")
    private String endpoint;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    @JsonProperty("contact")
    private ContactDTO contact;


    @Transient
    private Supplier parant;

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static class ContactDTO {
        @JsonProperty("website")
        private String website;
        @JsonProperty("email")
        private String email;
        @JsonProperty("telephone")
        private String telephone;
        @JsonProperty("address")
        private String address;
    }











/*    supplier_id	Supplier ID
    channel_id	Channel ID
    account_id	Account ID
    name	Display name for the suplier
    code	Code for the supplier
    home_url	Website homepage url
    logo_url	Logo image url
    contact_name	Name of the contact (person) at the supplier
    email_bookings	Email address for sending booking information
    email_accounts	Email address for accounts
    address	Postal address (multiline)
    short_desc	Short description
    long_desc	Long description
    why_desc	Why us?
    bonding_desc	Bonding / financial protection
    certification	e.g. for activity companies could be industry association memberships
    cancel_policy	Cancellation policy
    terms_and_conditions	Terms and conditions for booking*/

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
