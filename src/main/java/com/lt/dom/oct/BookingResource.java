package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.OctResp.EnumLongIdResp;
import com.lt.dom.otcenum.EnumGuideLevel;
import com.lt.dom.otcenum.EnumGuideService;
import com.lt.dom.otcenum.EnumPrivacyLevel;
import com.lt.dom.otcenum.EnumResourceType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;
import java.util.stream.Collectors;


//https://developer.makeplans.com/#slots
//https://docs.ventrata.com/capabilities/resources
@Entity
public class BookingResource extends Base{

    private String desc_short;
    private String desc_long;
    private EnumPrivacyLevel privacyLevel;

    public static List List(List<BookingResource> componentRightMap) {
        return componentRightMap.stream().map(x->{

            EnumLongIdResp enumResp = new EnumLongIdResp();
            enumResp.setId(x.getId());

            enumResp.setText(x.getTitle()+"_"+x.getCode());
            return enumResp;
        }).collect(Collectors.toList());
    }
    private EnumGuideLevel guideLevel;


    private EnumGuideService service;

    private EnumResourceType type;


    @JsonProperty("capacity")
    private Integer capacity;


    @JsonProperty("opening_hours_mon")
    private String openingHoursMon;


    @JsonProperty("opening_hours_tue")
    private String openingHoursTue;

    @JsonProperty("opening_hours_wed")
    private String openingHoursWed;

    @JsonProperty("opening_hours_thu")
    private String openingHoursThu;

    @JsonProperty("opening_hours_fri")
    private String openingHoursFri;

    @JsonProperty("opening_hours_sat")
    private String openingHoursSat;

    @JsonProperty("opening_hours_sun")
    private String openingHoursSun;
    @JsonProperty("title")
    private String title;
    private long supplier;
    private String code;

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getOpeningHoursMon() {
        return openingHoursMon;
    }

    public void setOpeningHoursMon(String  openingHoursMon) {
        this.openingHoursMon = openingHoursMon;
    }

    public String getOpeningHoursTue() {
        return openingHoursTue;
    }

    public void setOpeningHoursTue(String openingHoursTue) {
        this.openingHoursTue = openingHoursTue;
    }

    public String getOpeningHoursWed() {
        return openingHoursWed;
    }

    public void setOpeningHoursWed(String openingHoursWed) {
        this.openingHoursWed = openingHoursWed;
    }

    public String getOpeningHoursThu() {
        return openingHoursThu;
    }

    public void setOpeningHoursThu(String openingHoursThu) {
        this.openingHoursThu = openingHoursThu;
    }

    public String getOpeningHoursFri() {
        return openingHoursFri;
    }

    public void setOpeningHoursFri(String openingHoursFri) {
        this.openingHoursFri = openingHoursFri;
    }

    public String getOpeningHoursSat() {
        return openingHoursSat;
    }

    public void setOpeningHoursSat(String openingHoursSat) {
        this.openingHoursSat = openingHoursSat;
    }

    public String getOpeningHoursSun() {
        return openingHoursSun;
    }

    public void setOpeningHoursSun(String openingHoursSun) {
        this.openingHoursSun = openingHoursSun;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getSupplier() {
        return supplier;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public EnumGuideLevel getGuideLevel() {
        return guideLevel;
    }

    public void setGuideLevel(EnumGuideLevel guideLevel) {
        this.guideLevel = guideLevel;
    }

    public EnumGuideService getService() {
        return service;
    }

    public void setService(EnumGuideService service) {
        this.service = service;
    }

    public EnumResourceType getType() {
        return type;
    }

    public void setType(EnumResourceType type) {
        this.type = type;
    }

    public void setDesc_short(String desc_short) {
        this.desc_short = desc_short;
    }

    public String getDesc_short() {
        return desc_short;
    }

    public void setDesc_long(String desc_long) {
        this.desc_long = desc_long;
    }

    public String getDesc_long() {
        return desc_long;
    }

    public void setPrivacyLevel(EnumPrivacyLevel privacyLevel) {
        this.privacyLevel = privacyLevel;
    }

    public EnumPrivacyLevel getPrivacyLevel() {
        return privacyLevel;
    }
}
