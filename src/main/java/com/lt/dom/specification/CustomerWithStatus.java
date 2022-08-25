package com.lt.dom.specification;

import com.alipay.api.domain.Status;
import com.lt.dom.oct.Customer;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

class CustomerWithStatus implements Specification<Customer> {

    private Status status;

// constructor omitted for brevity

    public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (status == null) {
            return null;//cb.equal("");
        }
        return cb.equal(root.get("status"), "this.firstName");
    }
}