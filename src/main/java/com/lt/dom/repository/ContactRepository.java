package com.lt.dom.repository;

import com.lt.dom.oct.Attribute;
import com.lt.dom.oct.Contact;
import com.lt.dom.otcenum.EnumRelatedObjectType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact
			, Long> {


	Optional<Contact> findByRelatedObjectTypeAndRelatedObjectId(EnumRelatedObjectType product, Long id);
}