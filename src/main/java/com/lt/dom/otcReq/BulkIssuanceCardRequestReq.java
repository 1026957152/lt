package com.lt.dom.otcReq;


import com.lt.dom.oct.Base;
import com.lt.dom.otcenum.EnumCardStatus;
import com.lt.dom.otcenum.EnumPassExpiry;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class BulkIssuanceCardRequestReq extends Base {




    private EnumPassExpiry passexpiry;
    @NotNull
    private Long card_product_id;



    @NotEmpty
    private String name_on_card;
    @NotNull
    private Long card_validity_term;
    private EnumCardStatus status;

    private Integer amount;



    @Valid
    private ShippingCardAddressReq return_address;
    @Valid

    private ShippingCardAddressReq shipping_address;
    @NotNull
    private Long number_of_cards;


    public EnumPassExpiry getPassexpiry() {
        return passexpiry;
    }

    public void setPassexpiry(EnumPassExpiry passexpiry) {
        this.passexpiry = passexpiry;
    }

    public Long getCard_product_id() {
        return card_product_id;
    }

    public void setCard_product_id(Long card_product_id) {
        this.card_product_id = card_product_id;
    }

    public String getName_on_card() {
        return name_on_card;
    }

    public void setName_on_card(String name_on_card) {
        this.name_on_card = name_on_card;
    }

    public Long getCard_validity_term() {
        return card_validity_term;
    }

    public void setCard_validity_term(Long card_validity_term) {
        this.card_validity_term = card_validity_term;
    }

    public EnumCardStatus getStatus() {
        return status;
    }

    public void setStatus(EnumCardStatus status) {
        this.status = status;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public ShippingCardAddressReq getReturn_address() {
        return return_address;
    }

    public void setReturn_address(ShippingCardAddressReq return_address) {
        this.return_address = return_address;
    }

    public ShippingCardAddressReq getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(ShippingCardAddressReq shipping_address) {
        this.shipping_address = shipping_address;
    }


    public Long getNumber_of_cards() {
        return number_of_cards;
    }

    public void setNumber_of_cards(Long number_of_cards) {
        this.number_of_cards = number_of_cards;
    }
}
