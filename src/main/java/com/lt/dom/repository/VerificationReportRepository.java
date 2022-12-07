package com.lt.dom.repository;

import com.lt.dom.oct.MemberCertification;
import com.lt.dom.oct.VerificationReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationReportRepository extends JpaRepository<VerificationReport
			, Long> {


}