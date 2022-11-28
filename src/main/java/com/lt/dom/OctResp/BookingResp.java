package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ShippingAddressResp;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingResp extends BaseResp {

    private Contact contact;
    private Map parameterList;
    private List charges;
    private List refunds;
    private EntityModel refund;
    private Shipping shipping;
    private String platform_text;
    private Fulfillment fulfillment;

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    public <K, V> void setParameterList(Map parameterList) {
        this.parameterList = parameterList;
    }

    public Map getParameterList() {
        return parameterList;
    }

    public <R> void setCharges(List charges) {
        this.charges = charges;
    }

    public List getCharges() {
        return charges;
    }

    public <R> void setPayments(List payments) {
        this.payments = payments;
    }

    public List getPayments() {
        return payments;
    }

    public <R> void setRefunds(List refunds) {
        this.refunds = refunds;
    }

    public List getRefunds() {
        return refunds;
    }

    public void setRefund(EntityModel refund) {
        this.refund = refund;
    }

    public EntityModel getRefund() {
        return refund;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setPlatform_text(String platform_text) {
        this.platform_text = platform_text;
    }

    public String getPlatform_text() {
        return platform_text;
    }

    public static class Contact {

        private String name;
        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    List<RoomStay> roomStays; // 表示库存 多久的库存。


    Product product;


    List<TravelerResp> travelers;


    List<ComponentRight> componentRights;


    List<RatePlan> ratePlans;


    List<RoyaltyRule> royaltyRules;


    private String code;
    private EnumProductType productType;
    private String productCode;
    private String note;
    private List<DocumentResp> documents;
    private EntityModel<AssetResp> asset;
    private Integer traveler_number;
    private List<EntityModel<PaymentMethodResp>> paymentMethods;
    private EnumBookingType type;
    private String type_text;
    private EnumValidateWay validate_way;
    private EnumValidationStatus validationStatus;
    private EntityModel payment;
    private Integer quantity;
    private String status_text;
    private LocalDateTime paid_at;
    private LocalDateTime created_at;


    public static class Fulfillment {
        private EnumFulfillment_behavior fulfillmentType;

        public EnumFulfillment_behavior getFulfillmentType() {
            return fulfillmentType;
        }

        public void setFulfillmentType(EnumFulfillment_behavior fulfillmentType) {
            this.fulfillmentType = fulfillmentType;
        }
    }

    private Integer subTotal;


    private int amount_due;
    private List lines;

    public static BookingResp toResp(Reservation booking, List<Traveler> travelers, List<Document> documents) {

        // Product product = pair.getValue1();
        // Tour tour = pair.getValue2();
        BookingResp reservationResp = new BookingResp();

        reservationResp.setAmount(booking.getAmount());
        reservationResp.setCode(booking.getCode());
        reservationResp.setStatus(booking.getStatus());

        reservationResp.setTotal_amount(booking.getTotal_amount());
        reservationResp.setTotal_discount_amount(booking.getTotal_discount_amount());

        reservationResp.setProductType(booking.getProductType());

        reservationResp.setDocuments(DocumentResp.Listfrom(documents));
        reservationResp.setTravelers(TravelerResp.Listfrom(travelers));
        //  reservationResp.setProductCode(product.getCode());
        // reservationResp.setNote(tour.getTour_name());
        //reservationResp.setNote(tour.getTour_name_long());
        return reservationResp;

    }

    public static BookingResp totoResp(Pair<Reservation, Product> with) {

        Reservation booking = with.getValue0();
        Product product = with.getValue1();

        BookingResp reservationResp = new BookingResp();

        reservationResp.setAmount(booking.getAmount());
        reservationResp.setCode(booking.getCode());
        reservationResp.setStatus(booking.getStatus());

        reservationResp.setTotal_amount(booking.getTotal_amount());
        reservationResp.setTotal_discount_amount(booking.getTotal_discount_amount());

        reservationResp.setProductType(booking.getProductType());
        reservationResp.setProductCode(product.getCode());

        //reservationResp.setNote(tour.getTour_name_long());
        return reservationResp;

    }

    public static BookingResp with(BookingResp bookingResp, List<Traveler> travelers) {

        bookingResp.setTraveler_number(travelers.size());
        bookingResp.setTravelers(TravelerResp.Listfrom(travelers));

        return bookingResp;
    }

    public List<RoyaltyRule> getRoyaltyRules() {
        return royaltyRules;
    }

    public void setRoyaltyRules(List<RoyaltyRule> royaltyRules) {
        this.royaltyRules = royaltyRules;
    }

    public List<RatePlan> getRatePlans() {
        return ratePlans;
    }

    public void setRatePlans(List<RatePlan> ratePlans) {
        this.ratePlans = ratePlans;
    }

    private List<Voucher> vouchers; //折扣券

    public List<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    List<BookAnswer> answers;


    private EnumBookingStatus status;

    public EnumBookingStatus getStatus() {
        return status;
    }

    public void setStatus(EnumBookingStatus status) {
        this.status = status;
    }
    /*    status	The order's current status:
    Pending
            Submitted
    Fulfilled
            Errored
    RefundInProgress
            Refunded
    PartialyRefunded
            Cancelled
    Abandonded*/


    private List<EnumPaymentOption> paymentOptions;

    private Integer total;//	The order's current total price, included all items, fees, and taxes.
    private Integer subtotal;//	The order's current total price of all active items.
    private boolean paid;//	The order's current value of all active tenders.
    private Integer savings;//	The order's current savings value for all active discounts and waived fees.
    private String token;//	A GUID identifier for the Order.


    List<EntityModel> products;

    List<BookingPayment> payments;

    public List<EntityModel> getProducts() {
        return products;
    }

    public void setProducts(List<EntityModel> products) {
        this.products = products;
    }


    List<Component> components;

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }


    private Integer amount;
    //Integereger	Represents a total amount of order items (sum of item.amount)

    //Value is multiplied by 100 to precisely represent 2 decimal places. For example, $100 is written as 10000.
    private Integer items_discount_amount;//
    //Integereger	Represents total amount of the discount applied to order line items

    //Value is multiplied by 100 to precisely represent 2 decimal places. For example, $100 is written as 10000.
    private Integer total_discount_amount;//
    // Integereger	Summarize all discounts applied to the order including discounts applied to particular order line items and discounts applied to the whole cart.

    // Value is multiplied by 100 to precisely represent 2 decimal places. For example, $100 is written as 10000.
    private Integer total_amount;//

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

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setProductType(EnumProductType productType) {
        this.productType = productType;
    }

    public EnumProductType getProductType() {
        return productType;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }


    public static BookingResp toResp_LIST(Pair<Reservation, List<LineItem>> pair) {

        List<LineItem> bookingTypeTowhoVolIST = pair.getValue1();

        //  if (bookingTypeTowhoVo.getToWhoTyp().equals(EnumBookingOjbectType.Product)) {
        Reservation booking = pair.getValue0();
        //   Product product = bookingTypeTowhoVo.getProduct();

        BookingResp reservationResp = new BookingResp();

        Fulfillment fulfillment = new Fulfillment();
        fulfillment.setFulfillmentType(booking.getFulfillment_behavior());
        reservationResp.setFulfillment(fulfillment);


        reservationResp.setValidate_way(booking.getSetValidate_way());
        reservationResp.setValidationStatus(booking.getValidationStatus());
        // reservationResp.setAmount(booking.getAmount());
        // reservationResp.setPaied(booking.getPaied());

        reservationResp.setCode(booking.getCode());
        reservationResp.setStatus(booking.getStatus());
        reservationResp.setStatus_text(booking.getStatus().toString());

        reservationResp.setTotal_amount(bookingTypeTowhoVolIST.stream().mapToInt(e -> e.getAmount()).sum());
        reservationResp.setTotal_discount_amount(booking.getTotal_discount_amount());

        reservationResp.setProductType(booking.getProductType());
        reservationResp.setCreated_at(booking.getCreatedDate());
        reservationResp.setPaid_at(booking.getPaied_at());

        //  reservationResp.setProductCode(product.getCode());

        reservationResp.setProducts(bookingTypeTowhoVolIST.stream().map(e -> {
            LineItemResp lineItemResp = LineItemResp.from(e);

            return EntityModel.of(lineItemResp);
        }).collect(Collectors.toList()));


        reservationResp.setType(booking.getType());
        reservationResp.setType_text(booking.getType().toString());
        reservationResp.setNote(booking.getNote());
        reservationResp.setPlatform_text(booking.getPlatform().toString());

        reservationResp.setCreatedDate(booking.getCreatedDate());
        reservationResp.setModifiedDate(booking.getModifiedDate());

        //reservationResp.setNote(tour.getTour_name_long());
        return reservationResp;
        //    }
   /*     if (bookingTypeTowhoVo.getToWhoTyp().equals(EnumBookingOjbectType.Voucher)) {
            Reservation booking = pair.getValue0();
            Campaign campaign = bookingTypeTowhoVo.getCampaign();

            BookingResp reservationResp = new BookingResp();

            reservationResp.setAmount(booking.getAmount());
            reservationResp.setCode(booking.getCode());
            reservationResp.setStatus(booking.getStatus());

            reservationResp.setTotal_amount(booking.getTotal_amount());
            reservationResp.setTotal_discount_amount(booking.getTotal_discount_amount());

            reservationResp.setProductType(booking.getProductType());
*//*            reservationResp.setProductCode(product.getCode());
            reservationResp.setNote(tour.getTour_name());*//*
            //reservationResp.setNote(tour.getTour_name_long());
            return reservationResp;
        }

        return null;*/
    }


    public static BookingResp from(Reservation booking) {


        //  if (bookingTypeTowhoVo.getToWhoTyp().equals(EnumBookingOjbectType.Product)) {

        //   Product product = bookingTypeTowhoVo.getProduct();

        BookingResp reservationResp = new BookingResp();

        Fulfillment fulfillment1 = new Fulfillment();
        fulfillment1.setFulfillmentType(booking.getFulfillment_behavior());

        reservationResp.setFulfillment(fulfillment1);


        //    reservationResp.setValidate_way(booking.getSetValidate_way());
        //   reservationResp.setValidationStatus(booking.getValidationStatus());
        // reservationResp.setAmount(booking.getAmount());
        // reservationResp.setPaied(booking.getPaied());

        reservationResp.setCode(booking.getCode());
        reservationResp.setStatus(booking.getStatus());
        reservationResp.setStatus_text(booking.getStatus().toString());

        reservationResp.setSubTotal(booking.getTotal_amount());
        reservationResp.setTotal_discount_amount(booking.getTotal_discount_amount());
    //    reservationResp.setHeroCard_amount(booking.getTotal_amount());

        reservationResp.setTotal_amount(booking.getTotal_amount());



        reservationResp.setAmount_due(booking.getTotal_amount());
        reservationResp.setProductType(booking.getProductType());
        reservationResp.setCreated_at(booking.getCreatedDate());
        reservationResp.setPaid_at(booking.getPaied_at());

        //  reservationResp.setProductCode(product.getCode());


        //   reservationResp.setType(booking.getType());
        //   reservationResp.setType_text(booking.getType().toString());
        reservationResp.setNote(booking.getNote());

        Contact contact = new Contact();
        contact.setName(booking.getContact_fullName());
        contact.setPhone(booking.getContact_phoneNumber());
        reservationResp.setContact(contact);


        //reservationResp.setNote(tour.getTour_name_long());
        return reservationResp;

    }

    public static BookingResp toResp(Pair<Reservation, BookingTypeTowhoVo> pair) {

        BookingTypeTowhoVo bookingTypeTowhoVo = pair.getValue1();

        if (bookingTypeTowhoVo.getToWhoTyp().equals(EnumBookingOjbectType.Product)) {
            Reservation booking = pair.getValue0();
            Product product = pair.getValue1().getProduct();

            BookingResp reservationResp = new BookingResp();
            reservationResp.setValidate_way(booking.getSetValidate_way());
            reservationResp.setValidationStatus(booking.getValidationStatus());
            reservationResp.setAmount(booking.getAmount());
            reservationResp.setCode(booking.getCode());
            reservationResp.setStatus(booking.getStatus());

            reservationResp.setTotal_amount(booking.getTotal_amount());
            reservationResp.setTotal_discount_amount(booking.getTotal_discount_amount());

            reservationResp.setProductType(booking.getProductType());
            reservationResp.setProductCode(product.getCode());
            reservationResp.setType(booking.getType());
            reservationResp.setType_text(booking.getType().toString());
            reservationResp.setNote(booking.getNote());
            //reservationResp.setNote(tour.getTour_name_long());
            return reservationResp;
        }
        if (bookingTypeTowhoVo.getToWhoTyp().equals(EnumBookingOjbectType.Voucher)) {
            Reservation booking = pair.getValue0();
            Campaign campaign = bookingTypeTowhoVo.getCampaign();

            BookingResp reservationResp = new BookingResp();

            reservationResp.setAmount(booking.getAmount());
            reservationResp.setCode(booking.getCode());
            reservationResp.setStatus(booking.getStatus());

            reservationResp.setTotal_amount(booking.getTotal_amount());
            reservationResp.setTotal_discount_amount(booking.getTotal_discount_amount());

            reservationResp.setProductType(booking.getProductType());
/*            reservationResp.setProductCode(product.getCode());
            reservationResp.setNote(tour.getTour_name());*/
            //reservationResp.setNote(tour.getTour_name_long());
            return reservationResp;
        }

        return null;
    }

    public static BookingResp toResp(Triplet<Reservation, BookingTypeTowhoVo, List<Traveler>> pair) {

        BookingResp resp = toResp(Pair.with(pair.getValue0(), pair.getValue1()));
        List<Traveler> travelers = pair.getValue2();
        ;
        resp.setTraveler_number(travelers.size());
        resp.setTravelers(TravelerResp.Listfrom(travelers));

        return resp;
    }

    public static BookingResp toResp(Quartet<Reservation, BookingTypeTowhoVo, List<Traveler>, List<Document>> pair, Asset asset) {

        BookingResp resp = toResp(Triplet.with(pair.getValue0(), pair.getValue1(), pair.getValue2()));
        List<Document> travelers = pair.getValue3();


        resp.setDocuments(DocumentResp.Listfrom(travelers));
        resp.setAsset(AssetResp.from(asset));


        return resp;
    }

    public static BookingResp with(BookingResp resp, List<Traveler> travelers, List<Document> documents, Asset asset) {

        resp.setTraveler_number(travelers.size());
        resp.setTravelers(TravelerResp.Listfrom(travelers));
        resp.setDocuments(DocumentResp.Listfrom(documents));
        resp.setAsset(AssetResp.from(asset));


        return resp;
    }

    public static BookingResp toResp(Quartet<Reservation, BookingTypeTowhoVo, List<Traveler>, List<Document>> pair) {

        BookingResp resp = toResp(Triplet.with(pair.getValue0(), pair.getValue1(), pair.getValue2()));
        List<Document> travelers = pair.getValue3();
        DocumentResp.Listfrom(travelers);
        resp.setDocuments(DocumentResp.Listfrom(travelers));


        return resp;
    }


    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setTravelers(List<TravelerResp> travelers) {
        this.travelers = travelers;
    }

    public List<TravelerResp> getTravelers() {
        return travelers;
    }

    public void setDocuments(List<DocumentResp> documents) {
        this.documents = documents;
    }

    public List<DocumentResp> getDocuments() {
        return documents;
    }

    public void setAsset(EntityModel<AssetResp> asset) {
        this.asset = asset;
    }

    public EntityModel<AssetResp> getAsset() {
        return asset;
    }

    public void setTraveler_number(Integer traveler_number) {
        this.traveler_number = traveler_number;
    }

    public Integer getTraveler_number() {
        return traveler_number;
    }


    public void setPaymentMethods(List<EntityModel<PaymentMethodResp>> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public List<EntityModel<PaymentMethodResp>> getPaymentMethods() {
        return paymentMethods;
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public void setType(EnumBookingType type) {
        this.type = type;
    }

    public EnumBookingType getType() {
        return type;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getType_text() {
        return type_text;
    }

    public void setValidate_way(EnumValidateWay validate_way) {
        this.validate_way = validate_way;
    }

    public EnumValidateWay getValidate_way() {
        return validate_way;
    }

    public void setValidationStatus(EnumValidationStatus validationStatus) {

        this.validationStatus = validationStatus;
    }

    public EnumValidationStatus getValidationStatus() {
        return validationStatus;
    }

    public void setPayment(EntityModel payment) {
        this.payment = payment;
    }

    public EntityModel getPayment() {
        return payment;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setStatus_text(String status_text) {

        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setPaid_at(LocalDateTime paid_at) {
        this.paid_at = paid_at;
    }

    public LocalDateTime getPaid_at() {
        return paid_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public Fulfillment getFulfillment() {
        return fulfillment;
    }

    public void setFulfillment(Fulfillment fulfillment) {
        this.fulfillment = fulfillment;
    }

/*    public void withLines(List<BookingProductFuck> bookingProductFuckList) {

        this.setProducts(bookingProductFuckList.stream().map(e->{
            BookingProductFuckResp bookingProductFuckResp =  BookingProductFuckResp.from(e);

            return EntityModel.of(bookingProductFuckResp);
        }).collect(Collectors.toList()));
    }*/

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getSubTotal() {
        return subTotal;
    }



    public void setAmount_due(int amount_due) {
        this.amount_due = amount_due;
    }

    public int getAmount_due() {
        return amount_due;
    }

    public <R> void setLines(List lines) {
        this.lines = lines;
    }

    public List getLines() {
        return lines;
    }


    public static class Refund {


        private Integer amountRefunded;
        private List refunds;


        public void setAmountRefunded(Integer amountRefunded) {
            this.amountRefunded = amountRefunded;
        }

        public Integer getAmountRefunded() {
            return amountRefunded;
        }

        public <R> void setRefunds(List refunds) {
            this.refunds = refunds;
        }

        public List getRefunds() {
            return refunds;
        }
    }

/*

    public static class PaymentResp {

        private Long customer;//	Integereger	Associated Customer, if any


        private Integer amount;//	number	Payment amount


        private String method;//	string	Payment instrument used
        private String reference;//	string	Reference number, i.e. check #
        private String source;//	string	Source of the payment
        private String notes;//	string	Integerernal notes

        private List<EntityModel<PaymentMethodResp>> methods;
        private String status_text;
        private EnumPaymentStatus status;
        private LocalDateTime expireTime;
        private String time_remaining;
        private long seconds_remaining;
        private List charges = Arrays.asList();
        private LocalDateTime expire_time;

        public Long getCustomer() {
            return customer;
        }

        public void setCustomer(Long customer) {
            this.customer = customer;
        }




        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }


        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }


        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }


        public List<EntityModel<PaymentMethodResp>> getMethods() {
            return methods;
        }

        public void setMethods(List<EntityModel<PaymentMethodResp>> methods) {
            this.methods = methods;
        }

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }

        public EnumPaymentStatus getStatus() {
            return status;
        }

        public void setStatus(EnumPaymentStatus status) {
            this.status = status;
        }

        public LocalDateTime getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(LocalDateTime expireTime) {
            this.expireTime = expireTime;
        }

        public String getTime_remaining() {
            return time_remaining;
        }

        public void setTime_remaining(String time_remaining) {
            this.time_remaining = time_remaining;
        }

        public long getSeconds_remaining() {
            return seconds_remaining;
        }

        public void setSeconds_remaining(long seconds_remaining) {
            this.seconds_remaining = seconds_remaining;
        }

        public List getCharges() {
            return charges;
        }

        public void setCharges(List charges) {
            this.charges = charges;
        }

        public static PaymentResp from(Payment payment) {

            PaymentResp paymentResp = new PaymentResp();
            //  paymentResp.setCustomer(payment.getCustomer());
            //   paymentResp.setBalance(payment.getBalance());
            paymentResp.setAmount(payment.getAmount());
            paymentResp.setStatus(payment.getStatus());
            paymentResp.setExpire_time(payment.getExpireTime());

            Instant instant = payment.getExpireTime().toInstant(ZoneOffset.UTC);
            Date date = Date.from(instant);
            paymentResp.setTime_remaining(DateUtils.fromDeadline(date));
            paymentResp.setSeconds_remaining(DateUtils.secondfromDeadline(date));

            paymentResp.setStatus_text(payment.getStatus().toString());
            return paymentResp;
        }

        public void setExpire_time(LocalDateTime expire_time) {
            this.expire_time = expire_time;
        }

        public LocalDateTime getExpire_time() {
            return expire_time;
        }
    }

*/


    public static class Shipping {


        private ShippingAddressResp shippingCardAddressResp;

        private ShippingRateResp shippingRate;

        public ShippingAddressResp getShippingCardAddressResp() {
            return shippingCardAddressResp;
        }

        public void setShippingCardAddressResp(ShippingAddressResp shippingCardAddressResp) {
            this.shippingCardAddressResp = shippingCardAddressResp;
        }



        public void setShippingRate(ShippingRateResp shippingRate) {
            this.shippingRate = shippingRate;
        }

        public ShippingRateResp getShippingRate() {
            return shippingRate;
        }
    }

}
