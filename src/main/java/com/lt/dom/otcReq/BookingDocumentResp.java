package com.lt.dom.otcReq;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.util.List;

public class BookingDocumentResp {

    List<MultipartFile> files;

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    @Size(min = 0, max = 10)
    List<MultipartFile> insurance_policy_files;
    @Size(min = 0, max = 10)
    List<MultipartFile> contract_files;
    @Size(min = 0, max = 10)
    List<MultipartFile> photo_files;
    @Size(min = 0, max = 10)
    List<MultipartFile> bill_files;

    public List<MultipartFile> getInsurance_policy_files() {
        return insurance_policy_files;
    }

    public void setInsurance_policy_files(List<MultipartFile> insurance_policy_files) {
        this.insurance_policy_files = insurance_policy_files;
    }

    public List<MultipartFile> getContract_files() {
        return contract_files;
    }

    public void setContract_files(List<MultipartFile> contract_files) {
        this.contract_files = contract_files;
    }

    public List<MultipartFile> getPhoto_files() {
        return photo_files;
    }

    public void setPhoto_files(List<MultipartFile> photo_files) {
        this.photo_files = photo_files;
    }

    public List<MultipartFile> getBill_files() {
        return bill_files;
    }

    public void setBill_files(List<MultipartFile> bill_files) {
        this.bill_files = bill_files;
    }
}
