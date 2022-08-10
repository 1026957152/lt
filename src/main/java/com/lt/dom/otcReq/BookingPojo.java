package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.PublicationEntryResp;
import com.lt.dom.oct.DayRule;
import com.lt.dom.otcenum.EnumProductPricingTypeByPerson;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


public class BookingPojo {

    @NotNull
    private Tour additional_info;

    private String important_note;//	The staff entered "pinned note" on the booking
    private String workflow_note;//	The staff entered note form the "workflow" tab on the booking

    @Valid
    public Tour getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(Tour additional_info) {
        this.additional_info = additional_info;
    }

    public String getImportant_note() {
        return important_note;
    }

    public void setImportant_note(String important_note) {
        this.important_note = important_note;
    }

    public String getWorkflow_note() {
        return workflow_note;
    }

    public void setWorkflow_note(String workflow_note) {
        this.workflow_note = workflow_note;
    }

    public static class Tour {

        private String guide_id;//	ID number for the lead customer

        @NotNull
        private Long guide;//

        public Long getGuide() {
            return guide;
        }

        public void setGuide(Long guide) {
            this.guide = guide;
        }

        private String title;//
        private String code;//
        private String customer_count;//
        private String starts_at;//
        private String ends_at;//
        private String line_info;//

        public String getGuide_id() {
            return guide_id;
        }

        public void setGuide_id(String guide_id) {
            this.guide_id = guide_id;
        }

        public String getLine_info() {
            return line_info;
        }

        public void setLine_info(String line_info) {
            this.line_info = line_info;
        }
/*
                      "": "2014-09-01",
                              "ends_at": "2014-09-30",*/
       // tour guide 旅行团：tour group 旅行社： travel agency

        private String lead_customer_id;//	ID number for the lead customer

        @NotNull
        @Size(max = 20)
        private String lead_customer_name;//	Lead customer full name. Built from other name components
        private String lead_customer_email;//	Email address
      //  private String lead_customer_tel_home;//	Home number
      @NotNull
      @Size(max = 11)
        private String lead_customer_tel_mobile;//	Mobile number
      //  lead_customer_contact_note	E.g. "Call after 7pm"
      //  lead_customer_travelling	1 if the lead customer is travelling on the booking, 0 if they aren't

        public String getLead_customer_id() {
            return lead_customer_id;
        }

        public void setLead_customer_id(String lead_customer_id) {
            this.lead_customer_id = lead_customer_id;
        }

        public String getLead_customer_name() {
            return lead_customer_name;
        }

        public void setLead_customer_name(String lead_customer_name) {
            this.lead_customer_name = lead_customer_name;
        }

        public String getLead_customer_email() {
            return lead_customer_email;
        }

        public void setLead_customer_email(String lead_customer_email) {
            this.lead_customer_email = lead_customer_email;
        }

        public String getLead_customer_tel_mobile() {
            return lead_customer_tel_mobile;
        }

        public void setLead_customer_tel_mobile(String lead_customer_tel_mobile) {
            this.lead_customer_tel_mobile = lead_customer_tel_mobile;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCustomer_count() {
            return customer_count;
        }

        public void setCustomer_count(String customer_count) {
            this.customer_count = customer_count;
        }

        public String getStarts_at() {
            return starts_at;
        }

        public void setStarts_at(String starts_at) {
            this.starts_at = starts_at;
        }

        public String getEnds_at() {
            return ends_at;
        }

        public void setEnds_at(String ends_at) {
            this.ends_at = ends_at;
        }
    }








    private long productId;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }


    private List<Long> discounts;

    public List<Long> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Long> discounts) {
        this.discounts = discounts;
    }


    @Valid
    @Size(min=1, max=100)
    List<TravelerReq> travelers;

    public List<TravelerReq> getTravelers() {
        return travelers;
    }

    public void setTravelers(List<TravelerReq> travelers) {
        this.travelers = travelers;
    }


    public static class TravelerReq {

        public EnumProductPricingTypeByPerson getBy() {
            return by;
        }

        public void setBy(EnumProductPricingTypeByPerson by) {
            this.by = by;
        }

        private EnumProductPricingTypeByPerson by;
        @NotEmpty
        private String name;///

        private String family_name;///
        private String given_name;///
        @NotEmpty
        private String id;
        @NotEmpty
        private String tel_home;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFamily_name() {
            return family_name;
        }

        public void setFamily_name(String family_name) {
            this.family_name = family_name;
        }

        public String getGiven_name() {
            return given_name;
        }

        public void setGiven_name(String given_name) {
            this.given_name = given_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTel_home() {
            return tel_home;
        }

        public void setTel_home(String tel_home) {
            this.tel_home = tel_home;
        }
    }

}
