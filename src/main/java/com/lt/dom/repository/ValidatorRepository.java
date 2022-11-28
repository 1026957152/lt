package com.lt.dom.repository;

import com.lt.dom.oct.Validator_;
import com.lt.dom.otcenum.EnumValidatorType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValidatorRepository extends JpaRepository<Validator_
			, Long> {


    List<Validator_> findAllByTypeAndDevice(EnumValidatorType 特定机器, long id);

    Page<Validator_> findByComponentRightId(long id, Pageable pageable);

    List<Validator_> findAllByComponentRightId(long id);

    void deleteAllByComponentRightId(long id);

    List<Validator_> findAllByComponentRightIdIn(List<Long> collect);

    List<Validator_> findAllByTypeAndUser(EnumValidatorType 特定的人员, Long user_id);

    void deleteAllByDevice(long id);

    Page<Validator_> findAllByTypeAndUser(EnumValidatorType 特定的人员, long id, Pageable pageable);
}