package com.lt.dom.repository;

import com.lt.dom.oct.Term;
import com.lt.dom.oct.TransactionEntry;
import com.lt.dom.otcenum.EnumRelatedObjectType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TermRepository extends JpaRepository<Term
			, Long> {


	List<Term> findAllByRelatedObjectTypeAndRelatedObjectId(EnumRelatedObjectType product, Long id);
}