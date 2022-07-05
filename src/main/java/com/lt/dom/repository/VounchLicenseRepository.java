package com.lt.dom.repository;

import com.lt.dom.oct.ComponentRight;
import com.lt.dom.oct.ValidatorGroup;
import com.lt.dom.oct.ValidatorParking.VounchLicense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VounchLicenseRepository extends JpaRepository<VounchLicense
			, Long> {

    VounchLicense findByLicense(String license);
}