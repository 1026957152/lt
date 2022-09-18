package com.lt.dom.repository;

import com.lt.dom.oct.AssetDevice;
import com.lt.dom.oct.AutoGate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutoGateRepository extends JpaRepository<AutoGate, Long> {


    Optional<AutoGate> findBySN(String id);
}