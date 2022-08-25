package com.lt.dom.specification;

import com.lt.dom.oct.Voucher;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PersonSpecification implements Specification<Voucher> {

    private Voucher filter;

    public PersonSpecification(Voucher filter) {
        super();
        this.filter = filter;
    }

    public Predicate toPredicate(Root<Voucher> root, CriteriaQuery<?> cq,
                                 CriteriaBuilder cb) {

        Predicate p = cb.disjunction();

        if (filter.getType() != null) {
            p.getExpressions()
                    .add(cb.equal(root.get("name"), filter.getQuantity()));
        }

        if (filter.getCode() != null && filter.getStatus() != null) {
            p.getExpressions().add(
                    cb.and(cb.equal(root.get("surname"), filter.getQuantity()),
                            cb.equal(root.get("age"), filter.getCode())));
        }

        return p;
    }
}