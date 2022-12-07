package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.requestvo.BookingTypeTowhoVo;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingTrialResp {



    private Integer subTotal;
    private int heroCard_amount;
    private int amount_due;
    private Integer shippingFee;


    private List summary;



    private Map total;//	The order's current total price, included all items, fees, and taxes.



    private Integer amount;
    //Integereger	Represents a total amount of order items (sum of item.amount)

    //Value is multiplied by 100 to precisely represent 2 decimal places. For example, $100 is written as 10000.
    private Integer  items_discount_amount;//
    //Integereger	Represents total amount of the discount applied to order line items

    //Value is multiplied by 100 to precisely represent 2 decimal places. For example, $100 is written as 10000.
    private Integer  total_discount_amount;//
   // Integereger	Summarize all discounts applied to the order including discounts applied to particular order line items and discounts applied to the whole cart.

   // Value is multiplied by 100 to precisely represent 2 decimal places. For example, $100 is written as 10000.
    private Integer  total_amount;//

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getItems_discount_amount() {
        return items_discount_amount;
    }

    public void setItems_discount_amount(Integer items_discount_amount) {
        this.items_discount_amount = items_discount_amount;
    }

    public Integer getTotal_discount_amount() {
        return total_discount_amount;
    }

    public void setTotal_discount_amount(Integer total_discount_amount) {
        this.total_discount_amount = total_discount_amount;
    }

    public Integer getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Integer total_amount) {
        this.total_amount = total_amount;
    }





    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }



    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setHeroCard_amount(int heroCard_amount) {
        this.heroCard_amount = heroCard_amount;
    }

    public int getHeroCard_amount() {
        return heroCard_amount;
    }


    public void setAmount_due(int amount_due) {
        this.amount_due = amount_due;
    }

    public int getAmount_due() {
        return amount_due;
    }

    public void setShippingFee(Integer shippingFee) {
        this.shippingFee = shippingFee;
    }

    public Integer getShippingFee() {
        return shippingFee;
    }

    public <T> void setSummary(List summary) {
        this.summary = summary;
    }

    public List getSummary() {
        return summary;
    }

    public void setTotal(Map total) {
        this.total = total;
    }

    public Map getTotal() {
        return total;
    }
}
