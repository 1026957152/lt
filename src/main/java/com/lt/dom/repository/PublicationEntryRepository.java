package com.lt.dom.repository;

import com.lt.dom.oct.Attraction;
import com.lt.dom.oct.PublicationEntry;
import com.lt.dom.otcenum.EnumPublicationObjectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PublicationEntryRepository extends JpaRepository<PublicationEntry
			, Long> {

    Optional<PublicationEntry> findByVoucherId(long id);

    List<PublicationEntry> findByToWhoTypeAndToWho(EnumPublicationObjectType customer, long id);

    Page<PublicationEntry> findByToWhoTypeAndToWho(EnumPublicationObjectType customer, long id, Pageable pageable);
    /// List<PublicationEntry> findByVoucherId(long id);
}