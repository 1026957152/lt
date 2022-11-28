package com.lt.dom.repository;

import com.lt.dom.oct.ValueList;
import com.lt.dom.oct.ValueListItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ValueListItemRepository extends JpaRepository<ValueListItem
			, Long> {

	List<ValueListItem> findAllByValueListIn(List<Long> collect);

	Optional<ValueListItem> findByValueListAndValue(long id, String i);

    List<ValueListItem> findAllByValueList(long id);

    Page<ValueListItem> findAllByValueList(long id, Pageable pageable);

    void deleteAllByValueList(long id);
}