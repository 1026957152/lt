package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.MemberCertification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberCertificationRepository extends JpaRepository<MemberCertification
			, Long> {


    Optional<MemberCertification> findByNameAndIdCardNumber(String idCardName, String phoneNo);
}