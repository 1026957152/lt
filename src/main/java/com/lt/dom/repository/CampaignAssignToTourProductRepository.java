package com.lt.dom.repository;

import com.lt.dom.oct.CampaignAssignToTourProduct;
import com.lt.dom.oct.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CampaignAssignToTourProductRepository extends JpaRepository<CampaignAssignToTourProduct
			, Long> {


    List<CampaignAssignToTourProduct> findByTourId(Long raletiveId);

    List<CampaignAssignToTourProduct> findByProductIn(List<Long> collect);

    List<CampaignAssignToTourProduct> findByProduct(Long productId);


    void deleteAllByProduct(long product);

}