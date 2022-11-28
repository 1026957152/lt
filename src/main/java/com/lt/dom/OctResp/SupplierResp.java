package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.controllerOct.RequestFuckRestController;
import com.lt.dom.controllerOct.SupplierRestController;
import com.lt.dom.domain.SettleAccount;
import com.lt.dom.oct.Asset;
import com.lt.dom.oct.Supplier;
import com.lt.dom.otcenum.EnumBussinessType;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumSupplierStatus;
import com.lt.dom.otcenum.EnumSupplierType;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupplierResp   {


    private String desc;


    private String object = "supplier";
    private String type_text;
    private String business_type_text;
    private EnumSupplierStatus status;
    private String status_text;
    private String location;
    private int verify_status;
    private EntityModel<BalanceResp> balance;
    private PhotoResp logo;
    private String registered_address;
    private String uniformSocialCreditCode;
    private String registered_name;
    private String contact_detail;
    private String about;

    public static SupplierResp from(RequestResp request) {
        SupplierResp supplierResp = new SupplierResp();
    //    supplierResp.setCode(request.getMerchantsSettledReq().getSupplier_name());
        supplierResp.setName(request.getMerchantsSettledReq().getSupplier_name());
        supplierResp.setDesc(request.getMerchantsSettledReq().getDesc());
        supplierResp.setType(request.getMerchantsSettledReq().getSupplier_type());
        supplierResp.setLocation(request.getMerchantsSettledReq().getLocation_text());
        return supplierResp;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    private SettleAccount settleAccount;
    private List<EntityModel<AssetResp>> assets;
    private EnumSupplierType type;
    private EnumBussinessType business_type;

    public static SupplierResp from(Supplier supplier, List<Asset> assets) {
        SupplierResp supplierResp = SupplierResp.from(supplier);
        supplierResp.setAssets(assets.stream().map(x->AssetResp.from(x)).collect(Collectors.toList()));
        return supplierResp;
    }

    public static SupplierResp from(Supplier supplier) {
        SupplierResp supplierResp = new SupplierResp();
        supplierResp.setCode(supplier.getCode());
        supplierResp.setName(supplier.getName());
        supplierResp.setDesc(supplier.getDesc());
        supplierResp.setType(supplier.getType());
        supplierResp.setType_text(supplier.getType().toString());
        supplierResp.setBusiness_type(supplier.getBusiness_type());
        supplierResp.setBusiness_type_text(supplier.getBusiness_type().toString());
        supplierResp.setStatus(supplier.getStatus());
        supplierResp.setStatus_text(supplier.getStatus().toString());
        supplierResp.setLocation(supplier.getLocationName());
        supplierResp.setRegistered_address(supplier.getLocationName());

        supplierResp.setUniformSocialCreditCode(supplier.getUniformSocialCreditCode());
        supplierResp.setContact_detail(supplier.getContact_detail());
        supplierResp.setRegistered_name(supplier.getName());
        supplierResp.setAbout(supplier.getDesc());
        return supplierResp;
    }

    public static SupplierResp simpleFrom(Supplier supplier) {
        SupplierResp supplierResp = new SupplierResp();
        supplierResp.setCode(supplier.getCode());
        supplierResp.setName(supplier.getName());
        supplierResp.setDesc(supplier.getDesc());
        supplierResp.setLocation(supplier.getLocationName());
        return supplierResp;
    }
    public static SupplierResp verySimpleFrom(Supplier supplier) {
        SupplierResp supplierResp = new SupplierResp();
        supplierResp.setCode(supplier.getCode());
        supplierResp.setName(supplier.getName());
        return supplierResp;
    }

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

    public List<EntityModel<AssetResp>> getAssets() {
        return assets;
    }

    public void setAssets(List<EntityModel<AssetResp>> assets) {
        this.assets = assets;
    }

    public void setType(EnumSupplierType type) {
        this.type = type;
    }

    public EnumSupplierType getType() {
        return type;
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

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setVerify_status(int verify_status) {
        this.verify_status = verify_status;
    }

    public int getVerify_status() {
        return verify_status;
    }

    public void setBalance(EntityModel<BalanceResp> balance) {

        this.balance = balance;
    }

    public EntityModel<BalanceResp> getBalance() {
        return balance;
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

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAbout() {
        return about;
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



    private Statistics statistics;

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
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
