package com.lt.dom.vo;


import com.lt.dom.otcenum.EnumRatePlaneCommissionType;
import com.lt.dom.otcenum.EnumRoyaltyCollection_method;
import com.lt.dom.otcenum.EnumValidateWay;

import javax.validation.constraints.NotNull;

public class RoyaltyVo {


        private Long component_right;
        private int amount;
        private int percent;

        private EnumRatePlaneCommissionType ratePlaneCommissionType;


        @NotNull
        private EnumValidateWay validate_way = EnumValidateWay.none;
        private EnumRoyaltyCollection_method collection_method;
        private Long id;
        private Long limit_quantity;

        public EnumValidateWay getValidate_way() {
            return validate_way;
        }

        public void setValidate_way(EnumValidateWay validate_way) {
            this.validate_way = validate_way;
        }

        public Long getComponent_right() {
            return component_right;
        }

        public void setComponent_right(long component_right) {
            this.component_right = component_right;
        }

        public int getPercent() {
            return percent;
        }

        public void setPercent(int percent) {
            this.percent = percent;
        }

        public EnumRoyaltyCollection_method getCollection_method() {
            return collection_method;
        }

        public void setCollection_method(EnumRoyaltyCollection_method collection_method) {
            this.collection_method = collection_method;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public void setLimit_quantity(Long limit_quantity) {
            this.limit_quantity = limit_quantity;
        }

        public Long getLimit_quantity() {
            return limit_quantity;
        }
    }
