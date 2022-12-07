package com.lt.dom.specification;

import com.lt.dom.oct.Cardholder;
import com.lt.dom.oct.Pass;
import com.lt.dom.oct.Product;
import com.lt.dom.otcenum.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.quartz.SchedulerAccessor;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PassSpecification implements Specification<Pass> {
    private List<String> columns;
    private PassQueryfieldsCriteria criteria;

    public PassSpecification(List<String> columns ,PassQueryfieldsCriteria searchCriteria) {

        this.columns = columns;
        this.criteria = searchCriteria;
    }
/*    public Predicate toPredicate(Root<Pass> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<Predicate>();

            final Path<Collection<Cardholder>> recipes = root.get(Recipe_.recipes);
        final Path<Date> dateModified = recipes.get(Recipe_.dateModified);
        return builder.greaterThan(dateModified, date);
    };*/
    @Override
    public Predicate toPredicate

      (Root<Pass> root, CriteriaQuery<?> query, CriteriaBuilder builder) {



        List<Predicate> predicates = new ArrayList<>();

  /*      if (!ObjectUtils.isEmpty(criteria.getHoder_name())) {
            predicates.add(builder.equal(
                    root.<String> get("cardholder").get("name"),(criteria.getHoder_name())));
        }
        if (!ObjectUtils.isEmpty(criteria.getCode())) {
            predicates.add(builder.like(
                    root.<String> get("code"), ""+criteria.getCode()+"%"));
        }
        if (!ObjectUtils.isEmpty(criteria.getTitle())) {
            predicates.add(builder.like(
                    root.<String> get("name"), "%" + criteria.getTitle() + "%"));
        }*/


        if(!ObjectUtils.isEmpty(criteria.getKeyword())){

            List<Predicate> predicates_or = new ArrayList<>();
            for (int i = 0; i < columns.size(); i++) {

                if(Arrays.asList("code").contains(columns.get(i))){

                    System.out.println("========="+criteria.getKeyword());
                    predicates.add(builder.or(builder.like(root.get(String.valueOf(columns.get(i))).as(String.class), "%" + criteria.getKeyword() + "%")));

                    predicates_or.add(builder.or(builder.like(root.get(String.valueOf(columns.get(i))).as(String.class), "%" + criteria.getKeyword() + "%")));

                    //   builder.or(predicates.toArray(new Predicate[predicates.size()]));

                }
                if(Arrays.asList("cardholder").contains(columns.get(i))){
                    predicates_or.add(builder.or(builder.equal(
                            root.<String> get("cardholder").get("name"),(criteria.getKeyword()))));


                }

            }

            predicates.add(builder.or(predicates_or.toArray(new Predicate[predicates_or.size()])));

        }



        if (!ObjectUtils.isEmpty(criteria.getFormFactorType())) {
            predicates.add(builder.equal(
                    root.<EnumFormFactorType> get("formFactor"),(criteria.getFormFactorType())));
        }

        if (!ObjectUtils.isEmpty(criteria.getProduct())) {
            predicates.add(builder.equal(
                    root.<String> get("cardholder").get("name"),(criteria.getProduct())));
        }



        if (!ObjectUtils.isEmpty(criteria.getStatuses())) {
            predicates.add(builder.in(
                    root.<List<EnumCardStatus>> get("status")).value(criteria.getStatuses()));
        }



/*

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
*/

/*        if (!ObjectUtils.isEmpty(criteria.getCreated_from())) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("createdDate"), criteria.getCreated_from()));
        }
        if (!ObjectUtils.isEmpty(criteria.getCreated_to())) {
            predicates.add(builder.lessThanOrEqualTo(root.get("createdDate"), criteria.getCreated_to()));
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