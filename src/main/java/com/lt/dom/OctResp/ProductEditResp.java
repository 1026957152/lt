package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingRulePojo;
import com.lt.dom.otcenum.EnumPayChannel;
import com.lt.dom.otcenum.EnumPaymentOption;
import com.lt.dom.otcenum.EnumProductStatus;
import com.lt.dom.otcenum.EnumProductType;
import com.lt.dom.vo.AvailabilityVO;
import org.javatuples.Pair;
import org.springframework.hateoas.EntityModel;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductEditResp {



    private ProductTourResp tour;
    private String type_text;
    private EnumProductStatus status;
    private String status_text;
    private List<EntityModel> priceTypes;
    private List<String> tags;
    private PricingTypeResp priceType;
    private List<PhotoResp> images;
    private PhotoResp thumbnail_image;
    private EntityModel availabilityTab;
    private List<AvailabilityVO> bookingAvailability;
    private Map parameterList;
    private EntityModel componentTab;
    private String long_desc;

    public static ProductEditResp dayTourFrom(Product product, Tour tour, List<Campaign> campaignAssignToTourProducts) {



        ProductEditResp resp = new ProductEditResp();
        resp.setSupplier(product.getSupplierId()+"");
        resp.setPaymentOptionList(Arrays.asList(EnumPaymentOption.giftCard,
                EnumPaymentOption.wechat_pay).stream().map(x->{
                    EnumResp enumResp = new EnumResp();
                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
        }).collect(Collectors.toList()));
        resp.setType(product.getType());
        resp.setType_text(product.getType().toString());
        resp.setCode(product.getCode());
/*        resp.setSupplier(supplier.getName());
        resp.setSupplierCode(supplier.getCode());*/
        resp.setName(product.getName());
        resp.setName_long(product.getName_long());
        List<EnumLongIdResp> campaignList = campaignAssignToTourProducts.stream().map(x->{
            EnumLongIdResp enumLongIdResp = new EnumLongIdResp();
            enumLongIdResp.setId(x.getId());
            enumLongIdResp.setText(x.getName());
            return enumLongIdResp;
        }).collect(Collectors.toList());
        resp.setTour(ProductTourResp.from(tour,campaignList));
        resp.setStatus(product.getStatus());
        resp.setStatus_text(product.getStatus().toString());

        return resp;
    }








    public static ProductEditResp from(Product e, List<PricingType> pricingTypeList) {

        ProductEditResp productResp = ProductEditResp.from(e);

/*        productResp.setPriceTypes(pricingTypeList.stream().map(ee->{
            return EntityModel.of(PricingTypeResp.from(ee));
        }).collect(Collectors.toList()));*/


        PricingType pricingType = pricingTypeList.get(0);
        productResp.setPriceType(PricingTypeResp.from(pricingType));
        return productResp;
    }


    public ProductTourResp getTour() {
        return tour;
    }

    public void setTour(ProductTourResp tour) {
        this.tour = tour;
    }

    private String supplier;
    
//##@Column(unique=true) 
private String code;
    private String supplierCode;
    private String name;
    private String nameLong;


    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }




    public EnumProductType getType() {
        return type;
    }

    public void setType(EnumProductType type) {
        this.type = type;
    }

    public List<AttributeResp> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeResp> attributes) {
        this.attributes = attributes;
    }

    public List<EnumResp> getPaymentOptionList() {
        return paymentOptionList;
    }

    public void setPaymentOptionList(List<EnumResp> paymentOptionList) {
        this.paymentOptionList = paymentOptionList;
    }


    public ProductTheatre getTheatre() {
        return theatre;
    }

    public void setTheatre(ProductTheatre theatre) {
        this.theatre = theatre;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }




    private EnumProductType type ;

    List<AttributeResp> attributes;
    List<EnumResp> paymentOptionList;

    @Transient
    private ProductTheatre theatre;
    @Transient
    private Attraction attraction;


    public static ProductEditResp from(Pair<Product,Supplier> pair) {
        Product product = pair.getValue0();
        Supplier supplier = pair.getValue1();

        ProductEditResp resp = ProductEditResp.from(product);
        resp.setSupplier(supplier.getName());
        resp.setSupplierCode(supplier.getCode());
        return resp;
    }

    public static ProductEditResp from(Product product) {


        ProductEditResp resp = new ProductEditResp();

        resp.setPaymentOptionList(EnumPayChannel.from(Arrays.stream((new Gson()).fromJson(product.getPaymentMethods_json(), EnumPayChannel[].class)).collect(Collectors.toList())));
        resp.setType(product.getType());
        resp.setType_text(product.getType().toString());
        resp.setCode(product.getCode());

        resp.setName(product.getName());
        resp.setName_long(product.getName_long());
        resp.setLong_desc(product.getLong_desc());

        resp.setPaymentOptionList((new Gson()).fromJson(product.getPaymentMethods_json(),List.class));

        resp.setStatus(product.getStatus());
        resp.setStatus_text(product.getStatus().toString());

        resp.setTags((new Gson()).fromJson(product.getTags_json(),List.class));


        return resp;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName_long(String nameLong) {
        this.nameLong = nameLong;
    }

    public String getNameLong() {
        return nameLong;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getType_text() {
        return type_text;
    }

    public void setStatus(EnumProductStatus status) {
        this.status = status;
    }

    public EnumProductStatus getStatus() {
        return status;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setPriceTypes(List<EntityModel> priceTypes) {
        this.priceTypes = priceTypes;
    }

    public List<EntityModel> getPriceTypes() {
        return priceTypes;
    }

    public  void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setPriceType(PricingTypeResp priceType) {
        this.priceType = priceType;
    }

    public PricingTypeResp getPriceType() {
        return priceType;
    }

    public void setImages(List<PhotoResp> images) {
        this.images = images;
    }

    public List<PhotoResp> getImages() {
        return images;
    }

    public void setThumbnail_image(PhotoResp thumbnail_image) {
        this.thumbnail_image = thumbnail_image;
    }

    public PhotoResp getThumbnail_image() {
        return thumbnail_image;
    }

    public void setAvailabilityTab(EntityModel availability) {
        this.availabilityTab = availability;
    }

    public EntityModel getAvailabilityTab() {
        return availabilityTab;
    }

    public void setBookingAvailability(List<AvailabilityVO> bookingAvailability) {
        this.bookingAvailability = bookingAvailability;
    }

    public List<AvailabilityVO> getBookingAvailability() {
        return bookingAvailability;
    }

    public  void setParameterList(Map parameterList) {
        this.parameterList = parameterList;
    }

    public Map getParameterList() {
        return parameterList;
    }

    public void setComponentTab(EntityModel componentTab) {
        this.componentTab = componentTab;
    }

    public EntityModel getComponentTab() {
        return componentTab;
    }

    public void setLong_desc(String long_desc) {
        this.long_desc = long_desc;
    }

    public String getLong_desc() {
        return long_desc;
    }


    public static class Availability {

        private List<BookingRulePojo> ranges;
        private Map parameter;

        public static Availability from(List<BookingRule> bookingRuleList) {
            Availability availability = new Availability();
            availability.setRanges(bookingRuleList.stream().map(e->{
                BookingRulePojo bookingRulePojo = new BookingRulePojo();
                bookingRulePojo.setBookable(e.getBookable());
                bookingRulePojo.setPriority(e.getPriority());

                bookingRulePojo.setRangetype(e.getRangetype());


               // bookingRulePojo.setTo(e.get());
           //     bookingRulePojo.setFrom(e.getPriority());

                return bookingRulePojo;
            }).collect(Collectors.toList()));

            return availability;
        }

        public List<BookingRulePojo> getRanges() {
            return ranges;
        }

        public void setRanges(List<BookingRulePojo> ranges) {
            this.ranges = ranges;
        }

        public  void setParameter(Map parameter) {
            this.parameter = parameter;
        }

        public Map getParameter() {
            return parameter;
        }
    }

/*
    public static class Availability {

        private List<BookingRulePojo> ranges;
        private Map parameter;

        public static Availability from(List<BookingRule> bookingRuleList) {
            Availability availability = new Availability();
            availability.setRanges(bookingRuleList.stream().map(e->{
                BookingRulePojo bookingRulePojo = new BookingRulePojo();
                bookingRulePojo.setBookable(e.getBookable());
                bookingRulePojo.setPriority(e.getPriority());

                bookingRulePojo.setRangetype(e.getRangetype());


                // bookingRulePojo.setTo(e.get());
                //     bookingRulePojo.setFrom(e.getPriority());

                return bookingRulePojo;
            }).collect(Collectors.toList()));

            return availability;
        }

        public List<BookingRulePojo> getRanges() {
            return ranges;
        }

        public void setRanges(List<BookingRulePojo> ranges) {
            this.ranges = ranges;
        }

        public  void setParameter(Map parameter) {
            this.parameter = parameter;
        }

        public Map getParameter() {
            return parameter;
        }
    }
*/

}
