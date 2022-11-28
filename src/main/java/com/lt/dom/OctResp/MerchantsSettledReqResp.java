package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.lt.dom.oct.TempDocument;
import com.lt.dom.otcReq.MerchantsSettledReq;
import com.lt.dom.otcenum.EnumBussinessType;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumSupplierType;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerchantsSettledReqResp {
    @NotEmpty
    private String supplier_name;
    @NotEmpty
    private String desc;
    @NotEmpty
    private String user_name;
    @NotEmpty
    private String user_password;
    @NotEmpty
    private String user_phone;
    @NotNull
    private EnumSupplierType supplier_type;
    @Valid
    private Location location;


    private EnumBussinessType business_type;
    private String first_name;
    private String last_name;
    private String business_type_text;
    private String location_text;
    private String supplier_type_text;

    public static MerchantsSettledReqResp from(MerchantsSettledReq max) {
        MerchantsSettledReqResp merchantsSettledReqResp = new MerchantsSettledReqResp();
        merchantsSettledReqResp.setAccount_name(max.getAccount_name());
        merchantsSettledReqResp.setDesc(max.getDesc());
        merchantsSettledReqResp.setBusiness_type(max.getBusiness_type());
        merchantsSettledReqResp.setBank_account_number(max.getBank_account_number());
        merchantsSettledReqResp.setBank_name(max.getBank_name());
        merchantsSettledReqResp.setPhone(max.getPhone());
        merchantsSettledReqResp.setUser_name(max.getUser_name());
        merchantsSettledReqResp.setUser_password(max.getUser_password());
        merchantsSettledReqResp.setUser_phone(max.getUser_phone());
        merchantsSettledReqResp.setSupplier_name(max.getSupplier_name());
        merchantsSettledReqResp.setSupplier_type(max.getSupplier_type());
    //    merchantsSettledReqResp.setSupplier_type_text(max.getAllowed_supplier().getType().toString());


        merchantsSettledReqResp.setBusiness_type_text(max.getBusiness_type().toString());

        merchantsSettledReqResp.setLocation_text(max.getLocation().getAddress());

        return merchantsSettledReqResp;
    }

    public static MerchantsSettledReq fromJsonString(String additional_info) {
        Gson gson = new Gson();

        return gson.fromJson(additional_info,MerchantsSettledReq.class);
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public EnumBussinessType getBusiness_type() {

        return business_type;
    }

    public void setBusiness_type(EnumBussinessType business_type) {
        this.business_type = business_type;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {

        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setBusiness_type_text(String business_type_text) {
        this.business_type_text = business_type_text;
    }

    public String getBusiness_type_text() {
        return business_type_text;
    }

    public void setLocation_text(String location_text) {
        this.location_text = location_text;
    }

    public String getLocation_text() {
        return location_text;
    }

    public void setSupplier_type_text(String supplier_type_text) {
        this.supplier_type_text = supplier_type_text;
    }

    public String getSupplier_type_text() {
        return supplier_type_text;
    }


    public static class Location {
        @NotNull
        private Float latitude;
        @NotNull
        private Float longitude;
//"name":"榆林政府","address":"陕西省榆林市","latitude":12.22,"longitude":12.22
        @NotEmpty
        private String address; // 街道
        @NotEmpty
        private String name; // 街道

        public Float getLatitude() {
            return latitude;
        }

        public void setLatitude(Float latitude) {
            this.latitude = latitude;
        }

        public Float getLongitude() {
            return longitude;
        }

        public void setLongitude(Float longitude) {
            this.longitude = longitude;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String street; // 街道
        private String postal_code; // 邮编
        private String locality; // 县/区
        private String region; // 市
        private String state; //省
        private String country; // 国家


        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getPostal_code() {
            return postal_code;
        }

        public void setPostal_code(String postal_code) {
            this.postal_code = postal_code;
        }

        public String getLocality() {
            return locality;
        }

        public void setLocality(String locality) {
            this.locality = locality;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }


    @NotEmpty
    private String bank_account_number;
    @NotEmpty
    private String account_name;
    @NotEmpty
    private String bank_name;
  //  @Size(min = 1,max = 10)
     @NotEmpty
    private PhotoResp bussiness_license;
    @NotNull
    private Boolean term;  // 这个就是机器了啊

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public EnumSupplierType getSupplier_type() {
        return supplier_type;
    }

    public void setSupplier_type(EnumSupplierType supplier_type) {
        this.supplier_type = supplier_type;
    }

    public Location getLocation() {
        return location;
    }


    public String getBank_account_number() {
        return bank_account_number;
    }

    public void setBank_account_number(String bank_account_number) {
        this.bank_account_number = bank_account_number;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public PhotoResp getBussiness_license() {
        return bussiness_license;
    }

    public void setBussiness_license(PhotoResp bussiness_license) {
        this.bussiness_license = bussiness_license;
    }

    public Boolean getTerm() {
        return term;
    }

    public void setTerm(Boolean term) {
        this.term = term;
    }

    PhotoResp business_license_image;
    PhotoResp license_image;
    PhotoResp liability_insurance_image;
    PhotoResp license_for_opening_bank_account;

   //TODO @NotEmpty
    String phone; //官方联系电话

    public PhotoResp getBusiness_license_image() {
        return business_license_image;
    }

    public void setBusiness_license_image(PhotoResp business_license_image) {
        this.business_license_image = business_license_image;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public PhotoResp getLicense_for_opening_bank_account() {
        return license_for_opening_bank_account;
    }

    public void setLicense_for_opening_bank_account(PhotoResp license_for_opening_bank_account) {
        this.license_for_opening_bank_account = license_for_opening_bank_account;
    }
}
