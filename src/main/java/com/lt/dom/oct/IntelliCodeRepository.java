package com.lt.dom.oct;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IntelliCodeRepository extends JpaRepository<IntelliCode
			, Long> {

    Optional<IntelliCode> findByPin(String cvc);

    List<IntelliCode> findByLineItem(Long id);
}