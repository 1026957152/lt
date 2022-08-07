package com.lt.dom.repository;

import com.lt.dom.oct.Balance;
import com.lt.dom.otcenum.EnumUserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance
			, Long> {


    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<Balance> findByUserAndType(long user, EnumUserType payment);
}