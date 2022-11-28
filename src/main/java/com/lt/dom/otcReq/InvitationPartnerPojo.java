package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumSupplierType;

import javax.validation.constraints.NotEmpty;

public class InvitationPartnerPojo {


    @NotEmpty
    private String companyName;//	string	yes (if user_id isn’t provided)	The email of the new member or multiple emails separated by commas.

    private EnumSupplierType type;///	integer	yes	A valid access level

    private String firstName;///	integer	yes	A valid access level

    private String lastName;///	integer	yes	A valid access level

    private String phone;///	integer	yes	A valid access level


    private String address;
    private String cityTownSuburb;
    private String stateCountyRegion;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public EnumSupplierType getType() {
        return type;
    }

    public void setType(EnumSupplierType type) {
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityTownSuburb() {
        return cityTownSuburb;
    }

    public void setCityTownSuburb(String cityTownSuburb) {
        this.cityTownSuburb = cityTownSuburb;
    }

    public String getStateCountyRegion() {
        return stateCountyRegion;
    }

    public void setStateCountyRegion(String stateCountyRegion) {
        this.stateCountyRegion = stateCountyRegion;
    }
}
