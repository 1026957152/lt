package com.lt.dom.repository;

import com.lt.dom.oct.ValueList;
import com.lt.dom.oct.ValueListItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValueListItemRepository extends JpaRepository<ValueListItem
			, Long> {

}