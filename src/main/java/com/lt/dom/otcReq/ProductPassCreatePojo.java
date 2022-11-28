package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.OctResp.AttributeResp;
import com.lt.dom.otcReq.product.ProductGiftVoucherPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.vo.ByHour;
import com.lt.dom.vo.ByItem;
import com.lt.dom.vo.ByPerson;
import com.lt.dom.vo.Fixed;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class ProductPassCreatePojo extends ProductPojo {



    public static class Card_setting {
        private Integer card_validity_term;

        private Integer num_of_physical_cards_per_user;

        private Integer num_of_virtual_cards_per_user;
        private Integer initial_status_of_physical_card;
        private Integer initial_status_of_virtual_card;
        private Integer soft_expiry_period;

        private Boolean allow_contactless;
        private EnumPassDorationUnit card_validity_term_unit;

        public Integer getCard_validity_term() {
            return card_validity_term;
        }

        public void setCard_validity_term(Integer card_validity_term) {
            this.card_validity_term = card_validity_term;
        }

        public Integer getNum_of_physical_cards_per_user() {
            return num_of_physical_cards_per_user;
        }

        public void setNum_of_physical_cards_per_user(Integer num_of_physical_cards_per_user) {
            this.num_of_physical_cards_per_user = num_of_physical_cards_per_user;
        }

        public Integer getNum_of_virtual_cards_per_user() {
            return num_of_virtual_cards_per_user;
        }

        public void setNum_of_virtual_cards_per_user(Integer num_of_virtual_cards_per_user) {
            this.num_of_virtual_cards_per_user = num_of_virtual_cards_per_user;
        }

        public Integer getInitial_status_of_physical_card() {
            return initial_status_of_physical_card;
        }

        public void setInitial_status_of_physical_card(Integer initial_status_of_physical_card) {
            this.initial_status_of_physical_card = initial_status_of_physical_card;
        }

        public Integer getInitial_status_of_virtual_card() {
            return initial_status_of_virtual_card;
        }

        public void setInitial_status_of_virtual_card(Integer initial_status_of_virtual_card) {
            this.initial_status_of_virtual_card = initial_status_of_virtual_card;
        }

        public Integer getSoft_expiry_period() {
            return soft_expiry_period;
        }

        public void setSoft_expiry_period(Integer soft_expiry_period) {
            this.soft_expiry_period = soft_expiry_period;
        }

        public Boolean getAllow_contactless() {
            return allow_contactless;
        }

        public void setAllow_contactless(Boolean allow_contactless) {
            this.allow_contactless = allow_contactless;
        }

        public EnumPassDorationUnit getCard_validity_term_unit() {
            return card_validity_term_unit;
        }

        public void setCard_validity_term_unit(EnumPassDorationUnit card_validity_term_unit) {
            this.card_validity_term_unit = card_validity_term_unit;
        }
    }
    public static class Account_setting {

        private Boolean link_account_to_card;
        private Boolean num_of_cards_per_accountd;

        public Boolean getLink_account_to_card() {
            return link_account_to_card;
        }

        public void setLink_account_to_card(Boolean link_account_to_card) {
            this.link_account_to_card = link_account_to_card;
        }

        public Boolean getNum_of_cards_per_accountd() {
            return num_of_cards_per_accountd;
        }

        public void setNum_of_cards_per_accountd(Boolean num_of_cards_per_accountd) {
            this.num_of_cards_per_accountd = num_of_cards_per_accountd;
        }
    }


    @Valid
    public Pass pass;

    public Pass getPass() {
        return pass;
    }

    public void setPass(Pass pass) {
        this.pass = pass;
    }

    public static class Pass {
        @Size(min = 1,max = 30)
        private List<Royalty> royalties;
        private Iterable<Long> rights;

        public List<Royalty> getRoyalties() {
            return royalties;
        }


        private Card_setting card_setting;
        private Account_setting account_setting;

        public Card_setting getCard_setting() {
            return card_setting;
        }

        public void setCard_setting(Card_setting card_setting) {
            this.card_setting = card_setting;
        }

        public Account_setting getAccount_setting() {
            return account_setting;
        }

        public void setAccount_setting(Account_setting account_setting) {
            this.account_setting = account_setting;
        }

        public void setRights(Iterable<Long> rights) {
            this.rights = rights;
        }
    }

}
