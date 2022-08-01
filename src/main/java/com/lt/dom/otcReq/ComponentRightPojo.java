package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumDiscountVoucherCategory;
import com.lt.dom.otcenum.EnumWhenSettle;

import java.util.List;



public class ComponentRightPojo {   // 这个是 下单的时候， 从 product 中生成 的





    private String name;
    private String note;



    List<ValidatorPojo> validators;

    public class ValidatorPojo  {
        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }


    List<RoyaltyRulePojo> royaltyRules;


    public class RoyaltyRulePojo  {

        private int amount ;// 折扣金额，在 type 为 1 时必传，取值范围 1~1000000000。
        private int percent;// 折扣百分比，在 type 为 2 时必传。例如值为 "20" 表示 8 折，值为 "100" 表示免费。

        private EnumDiscountVoucherCategory category;
        private EnumWhenSettle enumWhenSettle;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getPercent() {
            return percent;
        }

        public void setPercent(int percent) {
            this.percent = percent;
        }

        public EnumDiscountVoucherCategory getCategory() {
            return category;
        }

        public void setCategory(EnumDiscountVoucherCategory category) {
            this.category = category;
        }

        public EnumWhenSettle getEnumWhenSettle() {
            return enumWhenSettle;
        }

        public void setEnumWhenSettle(EnumWhenSettle enumWhenSettle) {
            this.enumWhenSettle = enumWhenSettle;
        }
    }


    public List<ValidatorPojo> getValidators() {
        return validators;
    }

    public void setValidators(List<ValidatorPojo> validators) {
        this.validators = validators;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
