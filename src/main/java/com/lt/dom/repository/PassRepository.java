package com.lt.dom.repository;

import com.lt.dom.oct.Asset;
import com.lt.dom.oct.Pass;
import com.lt.dom.otcenum.EnumAssetType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PassRepository extends JpaRepository<Pass
			, Long> {


    Optional<Pass> findByCode(Object value);

    Page<Pass> findAllByUser(long user_id, Pageable pageable);

    Optional<Pass> findByUser(long id);

}