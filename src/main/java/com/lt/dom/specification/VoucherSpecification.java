package com.lt.dom.specification;

import com.lt.dom.oct.RedemptionEntry;
import com.lt.dom.oct.RedemptionEntry_;
import com.lt.dom.oct.Voucher;
import com.lt.dom.otcenum.EnumVoucherRedemptionStatus;
import com.lt.dom.otcenum.EnumVoucherStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VoucherSpecification implements Specification<Voucher> {

    private List<String> columns;

    private String keyword;

    private EnumVoucherRedemptionStatus status;

    public VoucherSpecification(List<String> columns, String keyword) {

        this.columns = columns;
        this.keyword = keyword;
    }


    public static Specification<Voucher> toP(VoucherQueryfieldsCriteria searchTerm) {
        return (root, query, cb) -> {
           // String containsLikePattern = getContainsLikePattern(searchTerm);


            List<Predicate> predicates = new ArrayList<>();

            if(!ObjectUtils.isEmpty(searchTerm.getStatus())){

                if(searchTerm.getStatus().equals(EnumVoucherStatus.Available)) {
                    predicates.add(cb.in(
                            root.<List<EnumVoucherStatus>>get("status")).value(Arrays.asList(EnumVoucherStatus.Published)));
                }else
                if(searchTerm.getStatus().equals(EnumVoucherStatus.Redeemed)) {
                    predicates.add(cb.in(
                            root.<List<EnumVoucherStatus>>get("status")).value(Arrays.asList(EnumVoucherStatus.Redeemed)));
                }else
                if(searchTerm.getStatus().equals(EnumVoucherStatus.Unavailable)) {
                    predicates.add(cb.in(
                            root.<List<EnumVoucherStatus>>get("status")).value(Arrays.asList(EnumVoucherStatus.Expired,EnumVoucherStatus.Unavailable)));
                }else{

                    predicates.add(cb.in(
                            root.<List<EnumVoucherStatus>> get("status")).value(Arrays.asList(searchTerm.getStatus())));
                }
            }


            if(!ObjectUtils.isEmpty(searchTerm.getPublishTo())){
                predicates.add(cb.equal(
                        root.<Long> get("publishTo"),searchTerm.getPublishTo()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));


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

/*    @Override
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



    }*/

    @Override
    public Predicate toPredicate(Root<Voucher> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}