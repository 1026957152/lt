package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Redemption;
import com.lt.dom.otcenum.EnumDiscountVoucherCategory;

import java.time.LocalDateTime;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class RightRedemptionSummaryRespV1 {


    private LocalDateTime redeemedDateTime;

    private List entries;
    private String validatorChannel;


    private String validatorCode;
    private String code;
    private Customer customer;
    private EnumDiscountVoucherCategory valuetype;

    public LocalDateTime getRedeemedDateTime() {
        return redeemedDateTime;
    }

    public void setRedeemedDateTime(LocalDateTime redeemedDateTime) {
        this.redeemedDateTime = redeemedDateTime;
    }

    public List getEntries() {
        return entries;
    }

    public void setEntries(List entries) {
        this.entries = entries;
    }

    public void setValidatorChannel(String validatorChannel) {
        this.validatorChannel = validatorChannel;
    }

    public String getValidatorChannel() {
        return validatorChannel;
    }

    public void setValidatorCode(String validatorCode) {
        this.validatorCode = validatorCode;
    }

    public String getValidatorCode() {
        return validatorCode;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setValuetype(EnumDiscountVoucherCategory valuetype) {
        this.valuetype = valuetype;
    }

    public EnumDiscountVoucherCategory getValuetype() {
        return valuetype;
    }


    public class Customer {


        private String name;
        private String code;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
        public void setCustomer(Redemption redemption) {

            Customer customer1 = new Customer();
            customer1.setName(redemption.getLog_Customer_name());
            customer1.setCode(redemption.getLog_Customer_code());

            this.customer = customer1;
        }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
