package com.lt.dom.RealNameAuthentication;


import com.lt.dom.otcenum.EnumVerificationReportid_number_type;
import com.lt.dom.otcenum.EnumVerificationResultStatus;
import com.lt.dom.otcenum.EnumVerificationStatus;

public class VerifiedOutputs {
    private String address;// String 否 身份证姓名
    private String dob;// String 否 身份证号码
    private String first_name;// String 否 手机号码
    private String last_name;// String 否 手机号码
    private String id_number;


    private EnumVerificationResultStatus status;
    private String error;

    public EnumVerificationResultStatus getStatus() {
        return status;
    }

    public void setStatus(EnumVerificationResultStatus status) {
        this.status = status;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    private EnumVerificationReportid_number_type id_number_type;// String 否 手机号码

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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

    public EnumVerificationReportid_number_type getId_number_type() {
        return id_number_type;
    }

    public void setId_number_type(EnumVerificationReportid_number_type id_number_type) {
        this.id_number_type = id_number_type;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
