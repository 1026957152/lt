package com.lt.dom.repository.Axh;

import com.lt.dom.credit._信合_信用等级信息;
import com.lt.dom.oct.Axh.XydToXhPushRequest;
import com.lt.dom.oct.Axh.XydToXhPushRequestJsonFit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface XydToXhPushRequestRepository extends JpaRepository<XydToXhPushRequest
			, Long> {

	Optional<XydToXhPushRequest> findByOrderIdX申请id(Integer id);
}