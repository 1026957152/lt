package com.lt.dom;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.Base;
import com.lt.dom.oct.Invoice;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;


@Entity
public class InvoiceItem extends Base {

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="invoice", nullable=false)
    private Invoice invoice;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @JsonProperty("amount")
    private Integer amount;
    @JsonProperty("catalog_item")
    private Object catalogItem;
    @JsonProperty("description")
    private Object description;
    @JsonProperty("discountable")
    private Boolean discountable;
  //  @JsonProperty("discounts")
  //  private List<?> discounts;
    @JsonProperty("id")
    private Integer idX;
  //  @JsonProperty("metadata")
   // private MetadataDTO metadata;
    @JsonProperty("name")
    private String name;
    @JsonProperty("object")
    private String object;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("taxable")
    private Boolean taxable;
  //  @JsonProperty("taxes")
   // private List<?> taxes;
    @JsonProperty("type")
    private String type;
    @JsonProperty("unit_cost")
    private Integer unitCost;
    private Long ratePlan;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Integer unitCost) {
        this.unitCost = unitCost;
    }

    public void setRatePlan(Long ratePlan) {
        this.ratePlan = ratePlan;
    }

    public Long getRatePlan() {
        return ratePlan;
    }
//  @NoArgsConstructor
 //   @Data
  //  public static class MetadataDTO {
 //   }
}
