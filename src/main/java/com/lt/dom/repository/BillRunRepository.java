package com.lt.dom.repository;

import com.lt.dom.oct.BillRun;
import com.lt.dom.oct.Component;
import com.lt.dom.otcenum.EnumBillRecurringInterval;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BillRunRepository extends JpaRepository<BillRun
			, Long> {



}