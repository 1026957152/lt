package com.lt.dom.repository;

import com.lt.dom.oct.ComponentVounch;
import com.lt.dom.otcenum.EnumBelongType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComponentVounchRepository extends JpaRepository<ComponentVounch
			, Long> {

	List<ComponentVounch> findByComponentRight(long id);

	List<ComponentVounch> findByVoucherId(long id);

    List<ComponentVounch> findAllByUser(long id);

    Page<ComponentVounch> findAllByUser(long id, Pageable pageable);

    Optional<ComponentVounch> findByCode(String code);

    List<ComponentVounch> findByBelongTypeAndBelong(EnumBelongType componentVounch, long id);

    List<ComponentVounch> findAllByReference(String code);


    List<ComponentVounch> findAllByReservation(long id);
}