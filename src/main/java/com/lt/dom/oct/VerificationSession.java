package com.lt.dom.oct;

import com.lt.dom.otcenum.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class VerificationSession extends Base {
    private String name;
    private String idNumber;

    @Enumerated(EnumType.STRING)
    private EnumIdType idType;
    @Enumerated(EnumType.STRING)
    private EnumIdentityLastError lastError;

    public EnumIdentityLastError getLastError() {
        return lastError;
    }

    public void setLastError(EnumIdentityLastError lastError) {
        this.lastError = lastError;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public EnumIdType getIdType() {
        return idType;
    }

    public void setIdType(EnumIdType idType) {
        this.idType = idType;
    }

    public EnumVerificationCheckType getType() {
        return type;
    }

    public void setType(EnumVerificationCheckType type) {
        this.type = type;
    }

    @Enumerated(EnumType.STRING)
    private EnumVerificationCheckType type;

    @Enumerated(EnumType.STRING)
    private EnumVerificationReportid_number_type enumVerificationReportid_number_type;

    @Enumerated(EnumType.STRING)
    private EnumVerificationDocumentType_type documentType;

    @Enumerated(EnumType.STRING)
    private EnumVerificationStatus status;


    @Enumerated(EnumType.STRING)
    private EnumVerificationResultStatus resultStatus;

    public EnumVerificationResultStatus getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(EnumVerificationResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }

    public EnumVerificationStatus getStatus() {
        return status;
    }

    public void setStatus(EnumVerificationStatus status) {
        this.status = status;
    }

    private String require_matching_selfie;
    private String require_live_capture;
    private String require_id_number;
/*    options.document.require_id_number
            optional
    Collect an ID number and perform an ID number check with the document’s extracted name and date of birth.

            options.document.require_live_capture
            optional
    Disable image uploads, identity document images have to be captured using the device’s camera.

    options.document.require_matching_selfie*/
}
