package com.lt.dom.repository;

import com.lt.dom.oct.VerificationSession;
import com.lt.dom.otcenum.EnumIdType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationSessionRepository extends JpaRepository<VerificationSession
			, Long> {

	Optional<VerificationSession> findByIdTypeAndNameAndIdNumber(EnumIdType 身份证, String name, String id_card);

}