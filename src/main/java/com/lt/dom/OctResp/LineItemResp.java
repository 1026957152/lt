package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.LineItem;
import com.lt.dom.otcenum.*;

import javax.persistence.*;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class LineItemResp {

    private EnumLineType type;
    private Object data;
    private String CVC;
    private List<TravelerResp> passengers;
    private String status_text;
    private EnumLineItemStatus status;
    private EnumDeliveryFormats deliveryFormats;
    private EnumFulfillmentInstructionsType fulfillmentInstructionsType;
    private String fulfillmentInstructionsType_text;
    private List intelliCodes;

    public void setType(EnumLineType type) {
        this.type = type;
    }

    public EnumLineType getType() {
        return type;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setCVC(String cvc) {
        this.CVC = cvc;
    }

    public String getCVC() {
        return CVC;
    }

    public void setPassengers(List<TravelerResp> passengers) {
        this.passengers = passengers;
    }

    public List<TravelerResp> getPassengers() {
        return passengers;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus(EnumLineItemStatus status) {
        this.status = status;
    }

    public EnumLineItemStatus getStatus() {
        return status;
    }

    public void setDeliveryFormats(EnumDeliveryFormats deliveryFormats) {
        this.deliveryFormats = deliveryFormats;
    }

    public EnumDeliveryFormats getDeliveryFormats() {
        return deliveryFormats;
    }

    public void setFulfillmentInstructionsType(EnumFulfillmentInstructionsType fulfillmentInstructionsType) {
        this.fulfillmentInstructionsType = fulfillmentInstructionsType;
    }

    public EnumFulfillmentInstructionsType getFulfillmentInstructionsType() {
        return fulfillmentInstructionsType;
    }

    public void setFulfillmentInstructionsType_text(String fulfillmentInstructionsType_text) {
        this.fulfillmentInstructionsType_text = fulfillmentInstructionsType_text;
    }

    public String getFulfillmentInstructionsType_text() {
        return fulfillmentInstructionsType_text;
    }

    public <R> void setIntelliCodes(List intelliCodes) {
        this.intelliCodes = intelliCodes;
    }

    public List getIntelliCodes() {
        return intelliCodes;
    }

    public static class Pass {
        private String CVC;
        private Integer amount;//  - amount (Integereger) - quantity * price (you should provide it to retrieve discount_amount for a particular order item if the discount is applied only to this item learn more
        private Integer price;//          - price (Integereger) - A positive Integereger representing the cost of an item.
        private String title;
        private Integer quantity;//          - quantity (Integereger) - A positive Integereger representing the number of instances of item that are included in this order line.

        public String getCVC() {
            return CVC;
        }

        public void setCVC(String CVC) {
            this.CVC = CVC;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }



    public static class Voucher {
        private String CVC;
        private Integer amount;//  - amount (Integereger) - quantity * price (you should provide it to retrieve discount_amount for a particular order item if the discount is applied only to this item learn more
        private Integer price;//          - price (Integereger) - A positive Integereger representing the cost of an item.
        private String title;
        private Integer quantity;//          - quantity (Integereger) - A positive Integereger representing the number of instances of item that are included in this order line.

        public String getCVC() {
            return CVC;
        }

        public void setCVC(String CVC) {
            this.CVC = CVC;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
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

    private EnumProductBookingFullfullmentStatus fullfullmentStatus;
    private PhotoResp image;

    public static LineItemResp from(LineItem e) {
        LineItemResp lineItemResp = new LineItemResp();
        lineItemResp.setType(e.getLineType());
        lineItemResp.setAmount(e.getAmount());
        lineItemResp.setPrice(e.getUnitPrice());
        lineItemResp.setTitle(e.getTitle());
        lineItemResp.setQuantity(e.getQuantity());
        lineItemResp.setStatus(e.getStatus());



        lineItemResp.setFulfillmentInstructionsType(e.getFulfillmentInstructionsType());

        if(e.getFulfillmentInstructionsType().equals(EnumFulfillmentInstructionsType.DIGITAL)){

            lineItemResp.setDeliveryFormats(e.getDeliveryFormats());
        }

        lineItemResp.setFulfillmentInstructionsType_text(e.getFulfillmentInstructionsType().toString());
        lineItemResp.setStatus_text(e.getStatus().toString());
        if(e.getLineType().equals(EnumLineType.Pass)){
            Pass pass = new  Pass();
            pass.setAmount(e.getAmount());
            pass.setPrice(e.getUnitPrice());
            pass.setTitle(e.getTitle());
            pass.setQuantity(e.getQuantity());

            lineItemResp.setData(pass);
        }
        if(e.getLineType().equals(EnumLineType.Showtime)){
            Voucher pass = new  Voucher();
            pass.setAmount(e.getAmount());
            pass.setPrice(e.getUnitPrice());
            pass.setTitle(e.getTitle());
            pass.setQuantity(e.getQuantity());

            lineItemResp.setData(pass);
        }

        if(e.getLineType().equals(EnumLineType.TICKIT)){
            Voucher pass = new  Voucher();
            pass.setAmount(e.getAmount());
            pass.setPrice(e.getUnitPrice());
            pass.setTitle(e.getTitle());
            pass.setQuantity(e.getQuantity());

            lineItemResp.setData(pass);
        }

        if(e.getLineType().equals(EnumLineType.JOURNEY)){
            Voucher voucher = new  Voucher();
            voucher.setAmount(e.getAmount());
            voucher.setPrice(e.getUnitPrice());
            voucher.setTitle(e.getTitle());
            voucher.setQuantity(e.getQuantity());

            lineItemResp.setData(voucher);
        }
        //bookingProductFuckResp.setTotal_amount(e.getPrice()*e.getQuantity());
        return lineItemResp;
    }
    public static LineItemResp fromWithCvc(LineItem e) {
        LineItemResp lineItemResp = new LineItemResp();
        lineItemResp.setAmount(e.getAmount());
        lineItemResp.setPrice(e.getUnitPrice());
        lineItemResp.setTitle(e.getTitle());
        lineItemResp.setQuantity(e.getQuantity());

        lineItemResp.setFullfullmentStatus(e.getFullfullmentStatus());

        //bookingProductFuckResp.setTotal_amount(e.getPrice()*e.getQuantity());
        return lineItemResp;
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




    public void setFullfullmentStatus(EnumProductBookingFullfullmentStatus fullfullmentStatus) {
        this.fullfullmentStatus = fullfullmentStatus;
    }

    public EnumProductBookingFullfullmentStatus getFullfullmentStatus() {
        return fullfullmentStatus;
    }

    public void setImage(PhotoResp image) {
        this.image = image;
    }

    public PhotoResp getImage() {
        return image;
    }
}
