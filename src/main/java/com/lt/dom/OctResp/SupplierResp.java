package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.controllerOct.FileUploadController;
import com.lt.dom.controllerOct.SupplierRestController;
import com.lt.dom.domain.SettleAccount;
import com.lt.dom.oct.Asset;
import com.lt.dom.oct.Supplier;
import com.lt.dom.otcenum.EnumBussinessType;
import com.lt.dom.otcenum.EnumSupplierType;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public class SupplierResp  extends RepresentationModel<SupplierResp> {


    private String desc;


    private String object = "supplier";

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

        supplierResp.add(linkTo(methodOn(SupplierRestController.class).applyCertification(supplier.getId(),null)).withRel("apply_for_approval_url"));
        supplierResp.add(linkTo(methodOn(SupplierRestController.class).pageEmployess(supplier.getId(),null)).withRel("employee_url"));


        supplierResp.setAssets(assets.stream().map(x->AssetResp.from(x)).collect(Collectors.toList()));
        return supplierResp;
    }

    public static SupplierResp from(Supplier supplier) {
        SupplierResp supplierResp = new SupplierResp();
        supplierResp.setCode(supplier.getCode());
        supplierResp.setName(supplier.getName());
        supplierResp.setDesc(supplier.getDesc());
        supplierResp.setType(supplier.getType());
        supplierResp.setBusiness_type(supplier.getBusiness_type());
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
