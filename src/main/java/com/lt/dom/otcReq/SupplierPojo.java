package com.lt.dom.otcReq;

import javax.validation.constraints.Pattern;

public class SupplierPojo {


    private String firstName;//	string	The user’s first name
    private String lastName;//	string	The user’s last name
    private String phone;//	string	The user’s last name

    private String supplierName;// 公司名称	Display name for the suplier
    private String type;//  运营商， 售票者

    @Pattern(regexp = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$")
    private String location;//  运营商， 售票者
    private String shortDesc;//	string	The user’s first name
    private String desc;


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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
