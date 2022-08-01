package com.lt.dom.otcReq;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class InvitationEmployeePojo {

    @NotNull
    private long supplierId;//	string	yes (if user_id isn’t provided)	The email of the new member or multiple emails separated by commas.
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String phone;
  //  private String email;
    private String note;

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
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


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
