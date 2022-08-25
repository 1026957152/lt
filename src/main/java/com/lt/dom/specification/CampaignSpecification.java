package com.lt.dom.specification;

import com.lt.dom.oct.Campaign;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CampaignSpecification implements Specification<Campaign> {

    private List<String> columns;

    private String keyword;
    public CampaignSpecification(List<String> columns,String keyword) {

        this.columns = columns;
        this.keyword = keyword;
    }

    @Override
    public Predicate toPredicate

      (Root<Campaign> root, CriteriaQuery<?> query, CriteriaBuilder builder) {





      //  CriteriaQuery<Campaign> q = builder.createQuery(Campaign.class);
     //   Root<Campaign> studentDAORoot = q.from(Campaign.class);

        List<Predicate> predicates = new ArrayList<>();



        for (int i = 0; i < columns.size(); i++) {
            predicates.add(builder.or(builder.like(root.get(String.valueOf(columns.get(i))).as(String.class), "%" + keyword + "%")));
        }


         return  builder.or(predicates.toArray(new Predicate[predicates.size()]));



    }
}