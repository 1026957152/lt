package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumDiscountVoucherCategory;
import com.lt.dom.otcenum.EnumDuration;
import com.lt.dom.otcenum.EnumPrivacyLevel;
import com.lt.dom.otcenum.EnumWhenSettle;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;



public class ComponentRightPojo {   // 这个是 下单的时候， 从 product 中生成 的




    @NotNull
    private String name;
    private String note;

    @NotEmpty
    @Length(max = 200)
    private String name_long;
    @NotEmpty
    @Length(max = 3000)
    private String long_desc;

    @NotNull
    @JsonProperty("private")
    private Boolean private_ = false;

    private EnumPrivacyLevel privacy_level = EnumPrivacyLevel.private_;

    public EnumPrivacyLevel getPrivacy_level() {
        return privacy_level;
    }

    public void setPrivacy_level(EnumPrivacyLevel privacy_level) {
        this.privacy_level = privacy_level;
    }

    public Boolean getPrivate_() {
        return private_;
    }

    public void setPrivate_(Boolean private_) {
        this.private_ = private_;
    }

    public String getName_long() {
        return name_long;
    }

    public void setName_long(String name_long) {
        this.name_long = name_long;
    }

    public String getLong_desc() {
        return long_desc;
    }

    public void setLong_desc(String long_desc) {
        this.long_desc = long_desc;
    }

    public List<RoyaltyRulePojo> getRoyaltyRules() {
        return royaltyRules;
    }

    public void setRoyaltyRules(List<RoyaltyRulePojo> royaltyRules) {
        this.royaltyRules = royaltyRules;
    }

    List<ValidatorPojo> validators;
    @NotNull
    private EnumDuration duration;


    private Long quantity;

    public EnumDuration getDuration() {
        return duration;
    }

    public void setDuration(EnumDuration duration) {
        this.duration = duration;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

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
