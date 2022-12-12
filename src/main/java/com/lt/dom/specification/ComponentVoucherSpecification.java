package com.lt.dom.specification;

import com.lt.dom.oct.ComponentVounch;
import com.lt.dom.oct.Reservation;
import com.lt.dom.otcenum.EnumBookingStatus;
import com.lt.dom.otcenum.EnumComponentVoucherStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ComponentVoucherSpecification implements Specification<ComponentVounch> {

    private ComponentVoucherQueryfieldsCriteria criteria;

    public ComponentVoucherSpecification(ComponentVoucherQueryfieldsCriteria searchCriteria) {

        this.criteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate

      (Root<ComponentVounch> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
 






        List<Predicate> predicates = new ArrayList<>();
        if (!ObjectUtils.isEmpty(criteria.getStatuses()) && !criteria.getStatuses().isEmpty()) {

            System.out.println("添加了一个 status why " + criteria.getStatuses().size());
            System.out.println("添加了一个 status why " + criteria.getStatuses());
            predicates.add(builder.in(
                    root.<List<EnumComponentVoucherStatus>> get("status")).value(criteria.getStatuses()));
        }

        if (!ObjectUtils.isEmpty(criteria.getCheck_in_status())) {
            predicates.add(builder.equal(
                    root.<Boolean> get("check_in"), ""+criteria.getCode()+"%"));
        }

        if (!ObjectUtils.isEmpty(criteria.getCode())) {
            predicates.add(builder.like(
                    root.<String> get("code"), ""+criteria.getCode()+"%"));
        }
        if (!ObjectUtils.isEmpty(criteria.getTitle())) {
            predicates.add(builder.like(
                    root.<String> get("title"), "%" + criteria.getTitle() + "%"));
        }


        if (!ObjectUtils.isEmpty(criteria.getAgent())) {
            predicates.add(builder.equal(
                    root.<Long> get("agent"),  criteria.getAgent()));
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