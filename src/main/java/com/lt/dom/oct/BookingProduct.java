package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumProductType;

import javax.persistence.*;

@Entity
public class BookingProduct {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // @GeneratedValue//(strategy = GenerationType.AUTO)
    @Id
    private long id;
    @Column(unique=true)
    private String code;


    private int cost;//	The unit cost of the product as a decimal.
    private int tax;//	The tax on the product as a decimal.
    private String name;//	The name of the product.


    private String theatre;//	The name of the Theatre showing the movie (tickets only).
    private long theatreId;//	The Id of the Theatre showing the movie (tickets only).
    private String movie;//	The name of the movie (tickets only).
    private String showDateTime;//	The Date time of the movie showing in local theatre time (tickets only).
    private String showDateTimeUtc;//	The Date time of the movie showing in UTC (tickets only).
    private long performanceNumber;//	The showtimes performance number (tickets only).
    private long auditorium;//	The auditorium the showtime is in (tickets only).

    private int row;//	The row of the currently selected seat (reserved-seating tickets only).
    private int column;//	The column of the currently selected seat (reserved-seating tickets only).
    private String seatName;//	The displayable seat name of the currently selected seat (reserved-seating tickets only).




    private String product_id;//-  (string) - The ID of the associated product object for this line item.
    private String sku_id;//        - sku_id (string) - The ID of the associated variant (sku) object for this line item.
    private int quantity;//          - quantity (integer) - A positive integer representing the number of instances of item that are included in this order line.
    private int price;//          - price (integer) - A positive integer representing the cost of an item.
    private int amount;//  - amount (integer) - quantity * price (you should provide it to retrieve discount_amount for a particular order item if the discount is applied only to this item learn more
    private EnumProductType productType;
    private long product;
    private long supplier;
    private String paymentMethods_json;
    private boolean paymentSplit;
    private String paymentSplitCode;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setProductType(EnumProductType productType) {
        this.productType = productType;
    }

    public EnumProductType getProductType() {
        return productType;
    }



    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getSupplier() {
        return supplier;
    }

    public void setPaymentMethods_json(String paymentMethods_json) {
        this.paymentMethods_json = paymentMethods_json;
    }

    public String getPaymentMethods_json() {
        return paymentMethods_json;
    }

    public void setPaymentSplit(boolean paymentSplit) {
        this.paymentSplit = paymentSplit;
    }

    public boolean getPaymentSplit() {
        return paymentSplit;
    }

    public void setPaymentSplitCode(String paymentSplitCode) {
        this.paymentSplitCode = paymentSplitCode;
    }

    public String getPaymentSplitCode() {
        return paymentSplitCode;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    public long getProduct() {
        return product;
    }
}
