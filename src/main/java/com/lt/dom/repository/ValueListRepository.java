package com.lt.dom.repository;

import com.lt.dom.oct.ValueList;
import com.lt.dom.oct.ValueListItem;
import com.lt.dom.oct.Voucher;
import com.lt.dom.otcenum.EnumValueListType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ValueListRepository extends JpaRepository<ValueList
			, Long> {

    List<ValueList> findAllByType(EnumValueListType vendor_groups);

    Optional<ValueList> findByName(String name);
}