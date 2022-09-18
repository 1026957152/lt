package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.Cardholder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardholderRepository extends JpaRepository<Cardholder
			, Long> {


}