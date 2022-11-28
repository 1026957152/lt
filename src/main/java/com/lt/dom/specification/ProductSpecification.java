package com.lt.dom.specification;

import com.lt.dom.oct.Product;
import com.lt.dom.oct.TourBooking;
import com.lt.dom.otcenum.EnumPrivacyLevel;
import com.lt.dom.otcenum.EnumProductStatus;
import com.lt.dom.otcenum.EnumProductType;
import com.lt.dom.otcenum.EnumTourBookingStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification implements Specification<Product> {

    private ProductQueryfieldsCriteria criteria;

    public ProductSpecification(ProductQueryfieldsCriteria searchCriteria) {

        this.criteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate

      (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
 






        List<Predicate> predicates = new ArrayList<>();


        if (!ObjectUtils.isEmpty(criteria.getPrivacyLevel())) {
            predicates.add(builder.equal(
                    root.<EnumPrivacyLevel> get("privacyLevel"),(criteria.getPrivacyLevel())));
        }
        if (!ObjectUtils.isEmpty(criteria.getStatuses())) {
            predicates.add(builder.in(
                    root.<List<EnumProductStatus>> get("status")).value(criteria.getStatuses()));
        }
        if (!ObjectUtils.isEmpty(criteria.getCode())) {
            predicates.add(builder.like(
                    root.<String> get("code"), ""+criteria.getCode()+"%"));
        }
        if (!ObjectUtils.isEmpty(criteria.getTitle())) {
            predicates.add(builder.like(
                    root.<String> get("name"), "%" + criteria.getTitle() + "%"));
        }


        if (!ObjectUtils.isEmpty(criteria.getType())) {
            predicates.add(builder.equal(
                    root.<EnumProductType> get("type"), criteria.getType()));
        }

        if (!ObjectUtils.isEmpty(criteria.getGuide_phone())) {
            predicates.add(builder.like(
                    root.<String> get("additional_info_guide_phone"), "%" + criteria.getGuide_phone() + "%"));
        }
        if (!ObjectUtils.isEmpty(criteria.getGuide_id())) {
            predicates.add(builder.like(
                    root.<String> get("additional_info_guide_id"), "%" + criteria.getGuide_id() + "%"));
        }
        if (!ObjectUtils.isEmpty(criteria.getLine_info())) {
            predicates.add(builder.like(
                    root.<String> get("additional_info_tour_line_info"), "%" + criteria.getLine_info() + "%"));
        }

        if (!ObjectUtils.isEmpty(criteria.getCreated_from())) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("created_at"), criteria.getCreated_from()));
        }
        if (!ObjectUtils.isEmpty(criteria.getCreated_to())) {
            predicates.add(builder.lessThanOrEqualTo(root.get("created_at"), criteria.getCreated_to()));
        }
/*        private String additional_info_guide_name;
        private String additional_info_guide_id;
        private String additional_info_guide_phone;
        private String additional_info_tour_line_info;
        private String additional_info_tour_title;
        private String additional_info_tour_code;
        private LocalDate additional_info_tour_starts_at;
        private LocalDate additional_info_tour_ends_at;*/

/*
        if (gameId != null) {
            predicates.add(builder.equal(root.get("gameId"), gameId));
        }*/

        return builder.and(predicates.toArray(new Predicate[0]));

      /*  else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
              root.<String> get(criteria.getKey()), criteria.getValue().toString());
        } 
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                  root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }*/
     //   return null;
    }
}