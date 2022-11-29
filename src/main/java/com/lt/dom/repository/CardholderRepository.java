package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.Cardholder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardholderRepository extends JpaRepository<Cardholder
			, Long> {


    List<Cardholder> findByIdentity(String id);

    Optional<Cardholder> findByCode(String code);
}