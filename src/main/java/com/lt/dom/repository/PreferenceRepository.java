package com.lt.dom.repository;

import com.lt.dom.oct.Preference;
import com.lt.dom.oct.Setting;
import com.lt.dom.otcenum.EnumPreference;
import com.lt.dom.otcenum.EnumPreferenceSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PreferenceRepository extends JpaRepository<Preference
			, Long> {


	List<Preference> findAllByUser(long id);

	List<Preference> findAllByUserAndSpace(Long user, EnumPreferenceSpace default_);

	Setting findByUserAndSpaceAndName(long id, EnumPreferenceSpace default_, String name);

    Optional<Preference> findByUserAndName(long id, EnumPreference working_space);


}