package com.lt.dom.specification;

import com.lt.dom.oct.Campaign;
import com.lt.dom.oct.Redemption;
import com.lt.dom.oct.RedemptionEntry;
import com.lt.dom.oct.RedemptionEntry_;
import com.lt.dom.otcenum.EnumVoucherRedemptionStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RedemptionSpecification implements Specification<RedemptionEntry> {

    private List<String> columns;

    private String keyword;

    private EnumVoucherRedemptionStatus status;

    public RedemptionSpecification(List<String> columns, String keyword) {

        this.columns = columns;
        this.keyword = keyword;
    }


    static Specification<RedemptionEntry> titleOrDescriptionContainsIgnoreCase(String searchTerm) {
        return (root, query, cb) -> {
            String containsLikePattern = getContainsLikePattern(searchTerm);
            return cb.or(
                    cb.like(cb.lower(root.<String>get(RedemptionEntry_.CODE)), containsLikePattern),
                    cb.like(cb.lower(root.<String>get(RedemptionEntry_.holder)), containsLikePattern)
            );
        };
    }

    private static String getContainsLikePattern(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return "%";
        }
        else {
            return "%" + searchTerm.toLowerCase() + "%";
        }
    }
    private Specification<RedemptionEntry> belongsToCategory(List<EnumVoucherRedemptionStatus> categories){
        return (root, query, criteriaBuilder)->
                criteriaBuilder.in(root.get(RedemptionEntry_.CODE)).value(categories);
    }

/*    private Specification<Product> isPremium() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(
                                root.get(Product_.MANUFACTURING_PLACE)
                                        .get(Address_.STATE),
                                STATE.CALIFORNIA),
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get(Product_.PRICE), PREMIUM_PRICE));
    }*/

/*    private Specification<RedemptionEntry> nameLike(String name){
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get(Product_.NAME), "%"+name+"%");
    }



        public static Specification<Redemption> priceGreaterThan(double price){
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get(Product_.PRICE), price);
        }

        public static Specification<Redemption> nameEquals(String name){
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(Product_.NAME), name);
        }*/

        public static Specification<RedemptionEntry> expired(){
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThan(root.get(RedemptionEntry_.CREATED_AT), LocalDate.now());
        }

    @Override
    public Predicate toPredicate

      (Root<RedemptionEntry> root, CriteriaQuery<?> query, CriteriaBuilder builder) {





      //  CriteriaQuery<Campaign> q = builder.createQuery(Campaign.class);
     //   Root<Campaign> studentDAORoot = q.from(Campaign.class);

        List<Predicate> predicates = new ArrayList<>();



        for (int i = 0; i < columns.size(); i++) {

            if(!ObjectUtils.isEmpty(keyword)){
                System.out.println("========="+keyword);
                predicates.add(builder.or(builder.like(root.get(String.valueOf(columns.get(i))).as(String.class), "%" + keyword + "%")));

            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
        // return  builder.or(predicates.toArray(new Predicate[0]));



    }
}