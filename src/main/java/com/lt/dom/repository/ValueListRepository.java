package com.lt.dom.repository;

import com.lt.dom.oct.ValueList;
import com.lt.dom.oct.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValueListRepository extends JpaRepository<ValueList
			, Long> {

}