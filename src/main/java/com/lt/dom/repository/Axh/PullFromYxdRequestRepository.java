package com.lt.dom.repository.Axh;

import com.lt.dom.credit._信合_信用等级信息;
import com.lt.dom.oct.Axh.PullFromYxdRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PullFromYxdRequestRepository extends JpaRepository<PullFromYxdRequest
			, Long> {

    List<PullFromYxdRequest> findAllByIdXIn(List<Integer> collect);

}