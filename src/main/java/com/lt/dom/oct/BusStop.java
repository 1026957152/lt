package com.lt.dom.oct;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

//https://www3.septa.org/#/Real%20Time%20Data/trainView
@Entity
public class BusStop extends Base{

    @OneToMany(mappedBy = "route")
    Set<StopRegistration> registrations;





    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY,    cascade = CascadeType.ALL)
    List<PlaceRegistration> placeRegistrations;
    public void addPlaceInList(PlaceRegistration addst) {
        placeRegistrations.add(addst);
    }


    public List<PlaceRegistration> getPlaceRegistrations() {
        return placeRegistrations;
    }

    public void setPlaceRegistrations(List<PlaceRegistration> placeRegistrations) {
        this.placeRegistrations = placeRegistrations;
    }

    /*    Whether the waypoint belongs to a contract.

                Attribute:	Read-only, cannot be set
        Return type:	bool
        Game Scenes:	Flight
                contract*/
    private String code;


    private Boolean has_contract;
    private String name;
    private Double lat;//Trip was soft deleted and should not be displayed.
    private Double lng;//Trip was soft deleted and should not be displayed.
    private Long supplier;
    private String description;
    private String shortName;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public Long getSupplier() {
        return supplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Set<StopRegistration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(Set<StopRegistration> registrations) {
        this.registrations = registrations;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setShortName(String shortName) {

        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }
}
