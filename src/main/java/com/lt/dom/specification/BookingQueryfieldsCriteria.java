package com.lt.dom.specification;


import com.lt.dom.otcenum.EnumBookingStatus;
import com.lt.dom.otcenum.EnumTourBookingStatus;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public class BookingQueryfieldsCriteria {


    private long campaign;
    private long guide;
    private boolean reviewing;

    private String title;//
    

private String code;//
    private String customer_count;//
    private String starts_at;//
    private String ends_at;//
    private String line_info;//
    private String guide_real_name;
    private String guide_phone;
    private String guide_id;

    private String lead_customer_name;
    private String lead_customer_tel_mobile;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime created_from;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime created_to;
    private Long agent;

    public boolean isReviewing() {
        return reviewing;
    }

    public void setReviewing(boolean reviewing) {
        this.reviewing = reviewing;
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

    public LocalDateTime getCreated_from() {
        return created_from;
    }

    public void setCreated_from(LocalDateTime created_from) {
        this.created_from = created_from;
    }

    public LocalDateTime getCreated_to() {
        return created_to;
    }

    public void setCreated_to(LocalDateTime created_to) {
        this.created_to = created_to;
    }

    private List<EnumBookingStatus> statuses;

    public List<EnumBookingStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<EnumBookingStatus> statuses) {
        this.statuses = statuses;
    }

    public long getCampaign() {
        return campaign;
    }

    public void setCampaign(long campaign) {
        this.campaign = campaign;
    }

    public long getGuide() {
        return guide;
    }

    public void setGuide(long guide) {
        this.guide = guide;
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

    public String getLine_info() {
        return line_info;
    }

    public void setLine_info(String line_info) {
        this.line_info = line_info;
    }

    public String getGuide_real_name() {
        return guide_real_name;
    }

    public void setGuide_real_name(String guide_real_name) {
        this.guide_real_name = guide_real_name;
    }

    public String getGuide_phone() {
        return guide_phone;
    }

    public void setGuide_phone(String guide_phone) {
        this.guide_phone = guide_phone;
    }

    public String getGuide_id() {
        return guide_id;
    }

    public void setGuide_id(String guide_id) {
        this.guide_id = guide_id;
    }

    public String getLead_customer_name() {
        return lead_customer_name;
    }

    public void setLead_customer_name(String lead_customer_name) {
        this.lead_customer_name = lead_customer_name;
    }

    public String getLead_customer_tel_mobile() {
        return lead_customer_tel_mobile;
    }

    public void setLead_customer_tel_mobile(String lead_customer_tel_mobile) {
        this.lead_customer_tel_mobile = lead_customer_tel_mobile;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public void setAgent(Long agent) {
        this.agent = agent;
    }

    public Long getAgent() {
        return agent;
    }
}