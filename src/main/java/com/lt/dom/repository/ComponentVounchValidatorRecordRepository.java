package com.lt.dom.repository;

import com.lt.dom.oct.ComponentVounchValidatorRecord;
import com.lt.dom.oct.Validator_;
import com.lt.dom.otcenum.EnumValidatorType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComponentVounchValidatorRecordRepository extends JpaRepository<ComponentVounchValidatorRecord
			, Long> {


	Page<ComponentVounchValidatorRecord> findAllByValidatorTypeAndDevice(EnumValidatorType 特定机器, long id, Pageable pageable);
}