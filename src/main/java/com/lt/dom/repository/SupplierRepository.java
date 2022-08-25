package com.lt.dom.repository;

import com.lt.dom.oct.RedemptionEntry;
import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.TouristAttraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SupplierRepository extends JpaRepository<Supplier
			, Long> , JpaSpecificationExecutor<Supplier> {

}