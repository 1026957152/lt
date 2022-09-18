package com.lt.dom.repository;

import com.lt.dom.oct.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken
			, Long> {


	VerificationToken findByToken(String verificationToken);
}