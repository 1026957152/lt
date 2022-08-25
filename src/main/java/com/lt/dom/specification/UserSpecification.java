package com.lt.dom.specification;

import com.lt.dom.oct.Campaign;
import com.lt.dom.oct.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification implements Specification<User> {

    private UserQueryfieldsCriteria criteria;

    public UserSpecification(UserQueryfieldsCriteria searchCriteria) {

        this.criteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate

      (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
 






        List<Predicate> predicates = new ArrayList<>();
        if (!ObjectUtils.isEmpty(criteria.getName())) {
            predicates.add(builder.like(
                    root.<String> get("realName"), "%" + criteria.getName() + "%"));
        }
        if (!ObjectUtils.isEmpty(criteria.getReal_name_verified())) {
            predicates.add(builder.equal(
                    root.<String> get("realNameVerified"), criteria.getReal_name_verified()));
        }

        if (!ObjectUtils.isEmpty(criteria.getReal_name_verified())) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("created_at"), criteria.getCreated_from()));
        }

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