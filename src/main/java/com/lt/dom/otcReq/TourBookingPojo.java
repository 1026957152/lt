package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lt.dom.otcenum.EnumProductPricingTypeByPerson;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class TourBookingPojo {

    @NotNull
    private Tour tour;

/*
    private String important_note;//	The staff entered "pinned note" on the booking
    private String workflow_note;//	The staff entered note form the "workflow" tab on the booking
*/

    @Valid
    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }



    public static class Tour {


        @NotNull(message = "需要指定一名导游，如果没有搜索不到导游，请再商户管理中添加导游")
        private Long guide;//

        public Long getGuide() {
            return guide;
        }

        public void setGuide(Long guide) {
            this.guide = guide;
        }
        @NotNull(message = "日线游需要填写名字")
        private String title;//
        @NotNull(message = "日线游需要编号")
        

private String code;//

        @NotNull(message = "需要填写游客数量")
        private String traveler_count;//
        @NotNull(message = "需要填写车牌号")
        private String license;//


        @NotNull
       // @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        // @JSONField(format = "yyyy-MM-dd HH:mm:ss")
      //  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDateTime starts_at;//
        @NotNull
      //  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        // @JSONField(format = "yyyy-MM-dd HH:        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)mm:ss")
       // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDateTime ends_at;//
        private String line_info;//




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


        @Size(max = 20)
        private String lead_customer_name;//	Lead customer full name. Built from other name components
        @Size(max = 11)
        private String lead_customer_email;//	Email address
      //  private String lead_customer_tel_home;//	Home number
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

        public String getTraveler_count() {
            return traveler_count;
        }

        public void setTraveler_count(String traveler_count) {
            this.traveler_count = traveler_count;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public LocalDateTime getStarts_at() {
            return starts_at;
        }

        public void setStarts_at(LocalDateTime starts_at) {
            this.starts_at = starts_at;
        }

        public LocalDateTime getEnds_at() {
            return ends_at;
        }

        public void setEnds_at(LocalDateTime ends_at) {
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



    @Valid
    @Size(min=1, max=100,message = "游客添加至少1人，最多不超过100人")
    List<TravelerReq> travelers;

    public List<TravelerReq> getTravelers() {
        return travelers;
    }

    public void setTravelers(List<TravelerReq> travelers) {
        this.travelers = travelers;
    }

/*
    public static class TravelerReq {


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
    }*/



    @Size(min = 0, max = 10)
    List<String> insurance_policy_files;
    @Size(min = 0, max = 10)
    List<String> contract_files;
    @Size(min = 0, max = 10)
    List<String> photo_files;
    @Size(min = 0, max = 10)
    List<String> bill_files;

    public List<String> getInsurance_policy_files() {
        return insurance_policy_files;
    }

    public void setInsurance_policy_files(List<String> insurance_policy_files) {
        this.insurance_policy_files = insurance_policy_files;
    }

    public List<String> getContract_files() {
        return contract_files;
    }

    public void setContract_files(List<String> contract_files) {
        this.contract_files = contract_files;
    }

    public List<String> getPhoto_files() {
        return photo_files;
    }

    public void setPhoto_files(List<String> photo_files) {
        this.photo_files = photo_files;
    }

    public List<String> getBill_files() {
        return bill_files;
    }

    public void setBill_files(List<String> bill_files) {
        this.bill_files = bill_files;
    }
}
