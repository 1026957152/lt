package com.lt.dom.otcReq;


import com.google.gson.Gson;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.OctResp.ReviewResp;
import com.lt.dom.oct.TempDocument;
import com.lt.dom.otcenum.*;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;


//@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerchantsSettledEdit {
    private EnumSupplierVerifiedStatus merchant_settled_status;
    private ReviewResp reject_review;

    public EnumSupplierVerifiedStatus getMerchant_settled_status() {
        return merchant_settled_status;
    }

    public void setMerchant_settled_status(EnumSupplierVerifiedStatus merchant_settled_status) {
        this.merchant_settled_status = merchant_settled_status;
    }

    private Map parameterList;

    public void setParameterList(Map parameterList) {
        this.parameterList = parameterList;
    }

    public Map getParameterList() {
        return parameterList;
    }

    @NotEmpty
    private String supplier_name;
    @NotEmpty
    private String desc;
  //  @NotEmpty
    private String user_name;
  //  @NotEmpty
    private String user_password;
   // @NotEmpty
    private String user_phone;
  //  @NotNull
    private EnumSupplierType supplier_type;
    @Valid
    private LocationEditResp location;

    @NotNull
    private EnumSupplier allowed_supplier;

    public EnumSupplier getAllowed_supplier() {
        return allowed_supplier;
    }

    public void setAllowed_supplier(EnumSupplier allowed_supplier) {
        this.allowed_supplier = allowed_supplier;
    }

    private EnumBussinessType business_type;
    private String first_name;
    private String last_name;
    private String business_type_text;
    private String location_text;
    private String supplier_type_text;
    private String termText;

    public static MerchantsSettledEdit from(MerchantsSettledReq max, Map<EnumDocumentType,TempDocument> documents) {
        MerchantsSettledEdit merchantsSettledReqResp = new MerchantsSettledEdit();
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
        merchantsSettledReqResp.setSupplier_type_text(max.getAllowed_supplier().getType().toString());


        merchantsSettledReqResp.setBusiness_type_text(max.getBusiness_type().toString());

        merchantsSettledReqResp.setLocation_text(max.getLocation().getAddress());

        if(!documents.isEmpty()){

            if(documents.containsKey(EnumDocumentType.liability_insurance)){
                merchantsSettledReqResp.setLiability_insurance_image(FileStorageServiceImpl.url(documents.get(EnumDocumentType.liability_insurance)));
            }else{
                merchantsSettledReqResp.setLiability_insurance_image(new PhotoResp());
            }
            if(documents.containsKey(EnumDocumentType.license)){
                merchantsSettledReqResp.setLicense_image(FileStorageServiceImpl.url(documents.get(EnumDocumentType.license)));
            }else{
                merchantsSettledReqResp.setLicense_image(new PhotoResp());
            }
            if(documents.containsKey(EnumDocumentType.business_license)){
                merchantsSettledReqResp.setBussiness_license_image(FileStorageServiceImpl.url(documents.get(EnumDocumentType.business_license)));
            }else {
                merchantsSettledReqResp.setBussiness_license_image(new PhotoResp());
            }
            if(documents.containsKey(EnumDocumentType.license_for_opening_bank_account)){
                merchantsSettledReqResp.setLicense_for_opening_bank_account_image(FileStorageServiceImpl.url(documents.get(EnumDocumentType.license_for_opening_bank_account)));
            }else{
                merchantsSettledReqResp.setLicense_for_opening_bank_account_image(new PhotoResp());
            }
        }

        return merchantsSettledReqResp;
    }

    public static MerchantsSettledReq fromJsonString(String additional_info) {
        Gson gson = new Gson();

        return gson.fromJson(additional_info,MerchantsSettledReq.class);
    }

    public void setLocation(LocationEditResp location) {
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

    public void setTermText(String termText) {
        this.termText = termText;
    }

    public String getTermText() {
        return termText;
    }

    @NotEmpty
    private String bank_account_number;
    @NotEmpty
    private String account_name;
    @NotEmpty
    private String bank_name;
  //  @Size(min = 1,max = 10)


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

    public LocationEditResp getLocation() {
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


    public Boolean getTerm() {
        return term;
    }

    public void setTerm(Boolean term) {
        this.term = term;
    }

    PhotoResp bussiness_license_image;
    PhotoResp license_image;
    PhotoResp liability_insurance_image;
    PhotoResp license_for_opening_bank_account_image;

   //TODO @NotEmpty
    String phone; //官方联系电话

    public PhotoResp getBussiness_license_image() {
        return bussiness_license_image;
    }

    public void setBussiness_license_image(PhotoResp bussiness_license_image) {
        this.bussiness_license_image = bussiness_license_image;
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

    public PhotoResp getLicense_for_opening_bank_account_image() {
        return license_for_opening_bank_account_image;
    }

    public void setLicense_for_opening_bank_account_image(PhotoResp license_for_opening_bank_account_image) {
        this.license_for_opening_bank_account_image = license_for_opening_bank_account_image;
    }

    public void setReject_review(ReviewResp reject_review) {
        this.reject_review = reject_review;
    }

    public ReviewResp getReject_review() {
        return reject_review;
    }
}
