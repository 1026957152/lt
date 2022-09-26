package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.BookingProductFuck;
import com.lt.dom.otcenum.EnumProductType;

import javax.persistence.*;



@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingProductFuckResp {


    private String code;


    private Integer cost;//	The unit cost of the product as a decimal.
    private Integer tax;//	The tax on the product as a decimal.
    private String name;//	The name of the product.


    private String theatre;//	The name of the Theatre showing the movie (tickets only).
    private Long theatreId;//	The Id of the Theatre showing the movie (tickets only).
    private String movie;//	The name of the movie (tickets only).
    private String showDateTime;//	The Date time of the movie showing in local theatre time (tickets only).
    private String showDateTimeUtc;//	The Date time of the movie showing in UTC (tickets only).
    private Long performanceNumber;//	The showtimes performance number (tickets only).
    private Long auditorium;//	The auditorium the showtime is in (tickets only).

    @Column(name="row_")
    private Integer row;//	The row of the currently selected seat (reserved-seating tickets only).
    @Column(name="column_")

    private Integer column;//	The column of the currently selected seat (reserved-seating tickets only).
    private String seatName;//	The displayable seat name of the currently selected seat (reserved-seating tickets only).




    private String product_id;//-  (string) - The ID of the associated product object for this line item.
    private String sku_id;//        - sku_id (string) - The ID of the associated variant (sku) object for this line item.
    private Integer quantity;//          - quantity (Integereger) - A positive Integereger representing the number of instances of item that are included in this order line.
    private Integer price;//          - price (Integereger) - A positive Integereger representing the cost of an item.
    private Integer amount;//  - amount (Integereger) - quantity * price (you should provide it to retrieve discount_amount for a particular order item if the discount is applied only to this item learn more
    private EnumProductType productType;
    private Long product;
    private Long supplier;
    private String paymentMethods_json;
    private Boolean paymentSplit;
    private String paymentSplitCode;
    private String title;
    private Integer total_amount;

    public static BookingProductFuckResp from(BookingProductFuck e) {
        BookingProductFuckResp bookingProductFuckResp = new BookingProductFuckResp();
       bookingProductFuckResp.setAmount(e.getAmount());
        bookingProductFuckResp.setPrice(e.getPrice());
        bookingProductFuckResp.setTitle("这是一个硬编码的title");
        bookingProductFuckResp.setQuantity(e.getQuantity());
        //bookingProductFuckResp.setTotal_amount(e.getPrice()*e.getQuantity());
        return bookingProductFuckResp;
    }

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setProductType(EnumProductType productType) {
        this.productType = productType;
    }

    public EnumProductType getProductType() {
        return productType;
    }



    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public Long getSupplier() {
        return supplier;
    }

    public void setPaymentMethods_json(String paymentMethods_json) {
        this.paymentMethods_json = paymentMethods_json;
    }

    public String getPaymentMethods_json() {
        return paymentMethods_json;
    }

    public void setPaymentSplit(Boolean paymentSplit) {
        this.paymentSplit = paymentSplit;
    }

    public Boolean getPaymentSplit() {
        return paymentSplit;
    }

    public void setPaymentSplitCode(String paymentSplitCode) {
        this.paymentSplitCode = paymentSplitCode;
    }

    public String getPaymentSplitCode() {
        return paymentSplitCode;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

    public Long getProduct() {
        return product;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getTitle() {
        return title;
    }


}
