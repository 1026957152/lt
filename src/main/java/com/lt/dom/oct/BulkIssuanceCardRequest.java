package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumBulkIssuanceCardRequestStatus;
import com.lt.dom.otcenum.EnumPassExpiry;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class BulkIssuanceCardRequest extends Base{


    @NotNull
    private EnumBulkIssuanceCardRequestStatus status;
    private long supplier;
    private String code;

    public EnumBulkIssuanceCardRequestStatus getStatus() {
        return status;
    }

    public void setStatus(EnumBulkIssuanceCardRequestStatus status) {
        this.status = status;
    }

    private EnumPassExpiry passexpiry;
    private Long card_product_id;

    private Long number_of_cards;

    public Long getNumber_of_cards() {
        return number_of_cards;
    }

    public void setNumber_of_cards(Long number_of_cards) {
        this.number_of_cards = number_of_cards;
    }

    private String name_on_card;
    private Long card_validity_term;


    private Integer amount;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "return_address", referencedColumnName = "id")
    private ShippingCardAddress return_address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address", referencedColumnName = "id")
    private ShippingCardAddress shipping_address;



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



    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public ShippingCardAddress getReturn_address() {
        return return_address;
    }

    public void setReturn_address(ShippingCardAddress return_address) {
        this.return_address = return_address;
    }

    public ShippingCardAddress getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(ShippingCardAddress shipping_address) {
        this.shipping_address = shipping_address;
    }

    public long getSupplier() {
        return supplier;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
