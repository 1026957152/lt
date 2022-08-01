package com.lt.dom.repository;

import com.lt.dom.oct.Quota;
import com.lt.dom.otcenum.EnumQuotaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface QuotaRepository extends JpaRepository<Quota
			, Long> {


    List<Quota> findByCompaign(long id);

    Page<Quota> findByCompaign(long id, Pageable pageable);

    List<Quota> findByTypeAndSupplier(EnumQuotaType nominatedSupplier, long id);



    List<Quota> findAllByTypeAndScenarioIn(EnumQuotaType scenario, Set<Long> scenarioIds);
}