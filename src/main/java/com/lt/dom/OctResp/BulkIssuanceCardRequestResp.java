package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Base;
import com.lt.dom.oct.BulkIssuanceCardRequest;
import com.lt.dom.otcenum.EnumBulkIssuanceCardRequestStatus;
import com.lt.dom.otcenum.EnumPassExpiry;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BulkIssuanceCardRequestResp extends BaseResp {




    private EnumPassExpiry passexpiry;
    @NotNull
    private Long card_product_id;



    @NotEmpty
    private String name_on_card;
    @NotNull
    private Long card_validity_term;
    private EnumBulkIssuanceCardRequestStatus status;

    private Integer amount;



    @Valid
    private ShippingCardAddressResp return_address;
    @Valid

    private ShippingCardAddressResp shipping_address;
    @NotNull
    private Long number_of_cards;
    private String status_text;
    private Map parameters;
    private String code;

    public static BulkIssuanceCardRequestResp from(BulkIssuanceCardRequest e) {

        BulkIssuanceCardRequestResp bulkIssuanceCardRequestResp = new BulkIssuanceCardRequestResp();
        bulkIssuanceCardRequestResp.setCard_product_id(e.getCard_product_id());
        bulkIssuanceCardRequestResp.setCode(e.getCode());

        bulkIssuanceCardRequestResp.setName_on_card(e.getName_on_card());
        bulkIssuanceCardRequestResp.setCard_validity_term(e.getCard_validity_term());
        bulkIssuanceCardRequestResp.setNumber_of_cards(e.getNumber_of_cards());
        bulkIssuanceCardRequestResp.setStatus(e.getStatus());
        bulkIssuanceCardRequestResp.setStatus_text(e.getStatus().toString());
        ShippingCardAddressResp returnAddress = ShippingCardAddressResp.from(e.getReturn_address());

        bulkIssuanceCardRequestResp.setReturn_address(returnAddress);

        ShippingCardAddressResp shippingCardAddressReq = ShippingCardAddressResp.from(e.getShipping_address());

        bulkIssuanceCardRequestResp.setShipping_address(shippingCardAddressReq);
        bulkIssuanceCardRequestResp.setShipping_address(shippingCardAddressReq);
        bulkIssuanceCardRequestResp.setCreatedDate(e.getCreatedDate());
        bulkIssuanceCardRequestResp.setModifiedDate(e.getModifiedDate());

        return bulkIssuanceCardRequestResp;
    }


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

    public EnumBulkIssuanceCardRequestStatus getStatus() {
        return status;
    }

    public void setStatus(EnumBulkIssuanceCardRequestStatus status) {
        this.status = status;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public ShippingCardAddressResp getReturn_address() {
        return return_address;
    }

    public void setReturn_address(ShippingCardAddressResp return_address) {
        this.return_address = return_address;
    }

    public ShippingCardAddressResp getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(ShippingCardAddressResp shipping_address) {
        this.shipping_address = shipping_address;
    }


    public Long getNumber_of_cards() {
        return number_of_cards;
    }

    public void setNumber_of_cards(Long number_of_cards) {
        this.number_of_cards = number_of_cards;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setParameters(Map parameters) {

        this.parameters = parameters;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
