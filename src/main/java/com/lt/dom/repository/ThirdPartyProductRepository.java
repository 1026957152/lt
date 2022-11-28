package com.lt.dom.repository;

import com.lt.dom.oct.Exhibition;
import com.lt.dom.oct.ThirdPartyProduct;
import com.lt.dom.oct.ThirdPartyProductKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThirdPartyProductRepository extends JpaRepository<ThirdPartyProduct
			, ThirdPartyProductKey> {


}