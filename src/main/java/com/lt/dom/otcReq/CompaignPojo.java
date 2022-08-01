package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumCompaignType;
import com.lt.dom.otcenum.EnumDiscountVoucherCategory;
import com.lt.dom.otcenum.EnumVoucherType;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;



public class CompaignPojo {





    public class code_config {

        private Integer length;
        private String charset;
        private String prefix;
        private String postfix;

        @NotNull
        private String pattern;

        public Integer getLength() {
            return length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }

        public String getCharset() {
            return charset;
        }

        public void setCharset(String charset) {
            this.charset = charset;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getPostfix() {
            return postfix;
        }

        public void setPostfix(String postfix) {
            this.postfix = postfix;
        }

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }
        /*           "length": 8,
                   "charset": "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ",
                   "prefix": null,
                   "postfix": null,
                   "pattern": "TC6-PROMO-#######"*/
    }

    @NotNull
    private EnumCompaignType campaignType;

    @NotEmpty
    @Size(max = 50)
    private String name;

    @NotEmpty
    @Size(max = 100)
    private String name_long;

    public String getName_long() {
        return name_long;
    }

    public void setName_long(String name_long) {
        this.name_long = name_long;
    }

    @NotNull
    private LocalDate start_date;
    @NotNull
    private LocalDate expiration_date;
    private code_config code_config;
    private String scenario;

    @NotEmpty
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public String getScenario() {
        return scenario;
    }
    public CompaignPojo.code_config getCode_config() {
        return code_config;
    }

    public void setCode_config(CompaignPojo.code_config code_config) {
        this.code_config = code_config;
    }


    @NotNull
    private VoucherPojo voucher;

    public class VoucherPojo {

        @NotNull
        private EnumVoucherType type;


        private int amount_off;
        private int percent_off;
        private int unit_off;
        private EnumDiscountVoucherCategory category;

        public EnumVoucherType getType() {
            return type;
        }

        public void setType(EnumVoucherType type) {
            this.type = type;
        }

        public int getAmount_off() {
            return amount_off;
        }

        public void setAmount_off(int amount_off) {
            this.amount_off = amount_off;
        }

        public int getPercent_off() {
            return percent_off;
        }

        public void setPercent_off(int percent_off) {
            this.percent_off = percent_off;
        }

        public int getUnit_off() {
            return unit_off;
        }

        public void setUnit_off(int unit_off) {
            this.unit_off = unit_off;
        }

        public EnumDiscountVoucherCategory getCategory() {
            return category;
        }

        public void setCategory(EnumDiscountVoucherCategory category) {
            this.category = category;
        }
    }

    public VoucherPojo getVoucher() {
        return voucher;
    }

    public void setVoucher(VoucherPojo voucher) {
        this.voucher = voucher;
    }

    private int vouchers_count;
    private String vouchers_generation_status; //IN_PROGRESS, DONE, ERROR

    @NotEmpty
    @Size(max = 500)
    private String description;
    private boolean active;

    public EnumCompaignType getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(EnumCompaignType campaignType) {
        this.campaignType = campaignType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(LocalDate expiration_date) {
        this.expiration_date = expiration_date;
    }



    public int getVouchers_count() {
        return vouchers_count;
    }

    public void setVouchers_count(int vouchers_count) {
        this.vouchers_count = vouchers_count;
    }

    public String getVouchers_generation_status() {
        return vouchers_generation_status;
    }

    public void setVouchers_generation_status(String vouchers_generation_status) {
        this.vouchers_generation_status = vouchers_generation_status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }




}
