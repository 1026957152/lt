package com.lt.dom.specification;

import com.lt.dom.oct.Campaign;
import com.lt.dom.oct.Supplier;
import com.lt.dom.otcenum.EnumSupplierStatus;
import com.lt.dom.otcenum.EnumSupplierType;
import com.lt.dom.otcenum.EnumTourBookingStatus_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SupplierSpecification implements Specification<Supplier> {

    private List<String> columns;

    private String keyword;

    private EnumSupplierType type;
    private EnumSupplierStatus status;
    public SupplierSpecification(List<String> columns, String keyword) {

        this.columns = columns;
        this.keyword = keyword;
    }
    public SupplierSpecification(List<String> columns, String keyword, EnumSupplierType type, EnumSupplierStatus status) {

        this.columns = columns;
        this.keyword = keyword;
        this.type = type;
        this.status = status;
    }

    public SupplierSpecification(List<String> columns, String keyword, EnumSupplierType type) {

        this.columns = columns;
        this.keyword = keyword;
        this.type = type;
    }
    @Override
    public Predicate toPredicate

      (Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder builder) {





      //  CriteriaQuery<Campaign> q = builder.createQuery(Campaign.class);
     //   Root<Campaign> studentDAORoot = q.from(Campaign.class);

        List<Predicate> predicates = new ArrayList<>();




        for (int i = 0; i < columns.size(); i++) {
            if(!ObjectUtils.isEmpty(keyword)){
                System.out.println("========="+keyword);
                predicates.add(builder.or(builder.like(root.get(String.valueOf(columns.get(i))).as(String.class), "%" + keyword + "%")));

            }
        }

       if (!ObjectUtils.isEmpty(status)) {
            predicates.add(builder.in(
                    root.<List<EnumSupplierStatus>> get("status")).value(Arrays.asList(status)));
        }
        if (!ObjectUtils.isEmpty(type)) {
            predicates.add(builder.equal(
                    root.<EnumSupplierType> get("type"),type));
        }
        return builder.or(predicates.toArray(new Predicate[0]));
      //   return  builder.or(predicates.toArray(new Predicate[predicates.size()]));



    }
}