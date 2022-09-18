package com.lt.dom.repository;

import com.lt.dom.oct.Attraction;
import com.lt.dom.oct.PublicationEntry;
import com.lt.dom.otcenum.EnumAssociatedType;
import com.lt.dom.otcenum.EnumPublicationObjectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PublicationEntryRepository extends JpaRepository<PublicationEntry
			, Long> {

    List<PublicationEntry> findByVoucher(long id);

    List<PublicationEntry> findByToWhoTypeAndToWho(EnumPublicationObjectType customer, long id);

    Page<PublicationEntry> findByToWhoTypeAndToWho(EnumPublicationObjectType customer, long id, Pageable pageable);

    List<PublicationEntry> findByToWhoTypeAndToWhoIn(EnumPublicationObjectType traveler, List<Long> collect);

    List<PublicationEntry> findByAssociatedTypeAndAssociatedId(EnumAssociatedType tour_booking, long code);

    List<PublicationEntry> findAllByVoucherIn(List<Long> collect);

    List<PublicationEntry> findAllByToWhoTypeAndToWhoAndCampaign(EnumPublicationObjectType customer, long id, long id1);

    long countByToWhoTypeAndToWhoAndCampaign(EnumPublicationObjectType customer, long id, long id1);

    List<PublicationEntry> findByAssociatedTypeAndAssociatedIdAndCampaign(EnumAssociatedType tour_booking, long id, long id1);
    /// List<PublicationEntry> findByVoucherId(long id);
}