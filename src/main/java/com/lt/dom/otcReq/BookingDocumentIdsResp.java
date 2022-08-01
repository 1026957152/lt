package com.lt.dom.otcReq;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.util.List;

public class BookingDocumentIdsResp {

    List<MultipartFile> files;

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

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
