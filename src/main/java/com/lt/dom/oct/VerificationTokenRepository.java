package com.lt.dom.oct;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken
			, Long> {


	VerificationToken findByToken(String verificationToken);
}