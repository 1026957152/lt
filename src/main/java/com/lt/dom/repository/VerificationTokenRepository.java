package com.lt.dom.repository;

import com.lt.dom.oct.VerificationToken;
import com.lt.dom.otcenum.EnumSmsVerificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken
			, Long> {


	Optional<VerificationToken> findByToken(String verificationToken);

	List<VerificationToken> findByTokenAndUserCode(String token, String user_code);

	List<VerificationToken> findByPhoneAndUserCode(String phone, String user_code);

	Optional<VerificationToken> findByUuid(String id);

	List<VerificationToken> findByPhoneAndType(String phone, EnumSmsVerificationType barcode);

}