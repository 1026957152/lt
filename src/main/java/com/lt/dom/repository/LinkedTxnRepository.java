package com.lt.dom.repository;

import com.lt.dom.oct.Activity;
import com.lt.dom.oct.LinkedTxn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkedTxnRepository extends JpaRepository<LinkedTxn
			, Long> {

}