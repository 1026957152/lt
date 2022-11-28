package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.domain.SettleAccount;
import com.lt.dom.oct.Asset;

import com.lt.dom.oct.Supplier;
import com.lt.dom.otcReq.LocationResp;
import com.lt.dom.otcReq.SettleAccountReq;
import com.lt.dom.otcenum.EnumBussinessType;
import com.lt.dom.otcenum.EnumSupplierStatus;
import com.lt.dom.otcenum.EnumSupplierType;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



public class SupplierEditResp {

    private EntityModel<BalanceResp> balance;

    private List<EntityModel<AssetResp>> assets;




    private EntityModel infoTab;
    private Map assetMap;

    public EntityModel getInfoTab() {
        return infoTab;
    }

    public void setInfoTab(EntityModel infoTab) {
        this.infoTab = infoTab;
    }

    private Statistics statistics;

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public <T> void setBalance(EntityModel balance) {
        this.balance = balance;
    }

    public EntityModel getBalance() {
        return balance;
    }

    public <R> void setAssets(List assets) {
        this.assets = assets;
    }

    public List getAssets() {
        return assets;
    }

    public void setAssetMap(Map assetMap) {
        this.assetMap = assetMap;
    }

    public Map getAssetMap() {
        return assetMap;
    }



    public static class InfoTab {


        private String desc;

        private String type_text;
        private String business_type_text;
        private EnumSupplierStatus status;
        private String status_text;
        private LocationReq location;
/*        private int verify_status;*/

        private PhotoResp logo;

        private PhotoResp bussiness_license;
        private PhotoResp license_image;
        private PhotoResp liability_insurance_image;
        private PhotoResp license_for_opening_bank_account;

        public PhotoResp getBussiness_license() {
            return bussiness_license;
        }

        public void setBussiness_license(PhotoResp bussiness_license) {
            this.bussiness_license = bussiness_license;
        }

        public PhotoResp getLicense_image() {
            return license_image;
        }

        public void setLicense_image(PhotoResp license_image) {
            this.license_image = license_image;
        }

        public PhotoResp getLiability_insurance_image() {
            return liability_insurance_image;
        }

        public void setLiability_insurance_image(PhotoResp liability_insurance_image) {
            this.liability_insurance_image = liability_insurance_image;
        }

        public PhotoResp getLicense_for_opening_bank_account() {
            return license_for_opening_bank_account;
        }

        public void setLicense_for_opening_bank_account(PhotoResp license_for_opening_bank_account) {
            this.license_for_opening_bank_account = license_for_opening_bank_account;
        }

        private String registered_address;
        private String uniformSocialCreditCode;
        private String registered_name;
        private String contact_detail;

        private String slug;


        private SettleAccountReq settleAccount;


        private EnumBussinessType business_type;

/*        public static com.lt.dom.OctResp.SupplierEditResp from(Supplier supplier, List<Asset> assets) {
            com.lt.dom.OctResp.SupplierEditResp supplierResp = com.lt.dom.OctResp.SupplierEditResp.from(supplier);
            supplierResp.setAssets(assets.stream().map(x -> AssetResp.from(x)).collect(Collectors.toList()));
            return supplierResp;
        }*/

        public static InfoTab from(Supplier supplier) {
            InfoTab supplierResp = new InfoTab();

            supplierResp.setName(supplier.getName());
            supplierResp.setDesc(supplier.getDesc());

            supplierResp.setType_text(supplier.getType().toString());
            supplierResp.setBusiness_type(supplier.getBusiness_type());
            supplierResp.setBusiness_type_text(supplier.getBusiness_type().toString());
            supplierResp.setStatus(supplier.getStatus());
            supplierResp.setStatus_text(supplier.getStatus().toString());


            supplierResp.setLocation(LocationReq.from(supplier.getLocation()));
            supplierResp.setSettleAccount(SettleAccountReq.from(supplier.getSettleAccount()));

            supplierResp.setRegistered_address(supplier.getLocationName());

            supplierResp.setUniformSocialCreditCode(supplier.getUniformSocialCreditCode());
            supplierResp.setContact_detail(supplier.getContact_detail());
            supplierResp.setRegistered_name(supplier.getRegistered_name());

            return supplierResp;
        }


        public SettleAccountReq getSettleAccount() {
            return settleAccount;
        }

        public void setSettleAccount(SettleAccountReq settleAccount) {
            this.settleAccount = settleAccount;
        }


        private String name;

        private String endpoint;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        private ContactDTO contact;


        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }



        public void setBusiness_type(EnumBussinessType business_type) {
            this.business_type = business_type;
        }

        public EnumBussinessType getBusiness_type() {
            return business_type;
        }

        public void setType_text(String type_text) {
            this.type_text = type_text;
        }

        public String getType_text() {
            return type_text;
        }

        public void setBusiness_type_text(String business_type_text) {
            this.business_type_text = business_type_text;
        }

        public String getBusiness_type_text() {
            return business_type_text;
        }

        public void setStatus(EnumSupplierStatus status) {
            this.status = status;
        }

        public EnumSupplierStatus getStatus() {
            return status;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }

        public String getStatus_text() {
            return status_text;
        }

        public LocationReq getLocation() {
            return location;
        }

        public void setLocation(LocationReq location) {
            this.location = location;
        }





        public void setLogo(PhotoResp logo) {
            this.logo = logo;
        }

        public PhotoResp getLogo() {
            return logo;
        }

        public void setRegistered_address(String registered_address) {
            this.registered_address = registered_address;
        }

        public String getRegistered_address() {
            return registered_address;
        }

        public void setUniformSocialCreditCode(String uniformSocialCreditCode) {
            this.uniformSocialCreditCode = uniformSocialCreditCode;
        }

        public String getUniformSocialCreditCode() {
            return uniformSocialCreditCode;
        }

        public void setRegistered_name(String registered_name) {
            this.registered_name = registered_name;
        }

        public String getRegistered_name() {
            return registered_name;
        }

        public void setContact_detail(String contact_detail) {
            this.contact_detail = contact_detail;
        }

        public String getContact_detail() {
            return contact_detail;
        }


        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
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




    }
    public static class Statistics {
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
}
