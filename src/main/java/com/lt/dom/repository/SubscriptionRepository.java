package com.lt.dom.repository;

import com.lt.dom.oct.AssetAuthorized;
import com.lt.dom.oct.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {



}