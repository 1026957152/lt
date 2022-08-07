package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


public class RealnameAuthsReq {


    @JsonProperty("verified_number")
    private String verifiedNumber;
    @JsonProperty("verified_file_url")
    private List<String> verifiedFileUrl;
    @JsonProperty("name")
    private String name;
    @JsonProperty("change_type")
    private Integer changeType;
    @JsonProperty("customer_id")
    private String customerId;
    @JsonProperty("verified_type")
    private Integer verifiedType;
    @JsonProperty("identify_type")
    private Integer identifyType;
    @JsonProperty("xaccount_type")
    private String xaccountType;


    @NotEmpty
    private String real_name;
    @NotEmpty
    private String id_card;

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }
}
