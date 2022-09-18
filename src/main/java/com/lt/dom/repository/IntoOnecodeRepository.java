package com.lt.dom.repository;

import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.IntoOnecode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface IntoOnecodeRepository extends JpaRepository<IntoOnecode
			, Long>, JpaSpecificationExecutor<BookingRule> {


    Optional<IntoOnecode> findByUser(long id);

    Optional<IntoOnecode> findByIdId(String value);
}