package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.vo.AvailabilityCalendarVO;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.hateoas.EntityModel;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lt.dom.serviceOtc.JsonParse.GSON;


public class ProductEditResp {

    private EntityModel rightTab;
    private EntityModel ruleTab;
    private EntityModel commentTab;
    private EntityModel aboutTab;
    private EntityModel shippingTab;
    private EntityModel questionTap;
    private EntityModel passTab;
    private List<AvailabilityCalendarVO> bookingAvailability;
    private EntityModel movieTab;
    private EntityModel bundleTab;
    private EntityModel upsellingTab;
    private EntityModel crossSellTab;


    public void setRightTab(EntityModel rightTab) {
        this.rightTab = rightTab;
    }

    public EntityModel getRightTab() {
        return rightTab;
    }

    public void setRuleTab(EntityModel ruleTab) {
        this.ruleTab = ruleTab;
    }

    public EntityModel getRuleTab() {
        return ruleTab;
    }

    public void setCommentTab(EntityModel commentTab) {
        this.commentTab = commentTab;
    }

    public EntityModel getCommentTab() {
        return commentTab;
    }

    public void setAboutTab(EntityModel aboutTab) {
        this.aboutTab = aboutTab;
    }

    public EntityModel getAboutTab() {
        return aboutTab;
    }

    public void setShippingTab(EntityModel shippingTab) {
        this.shippingTab = shippingTab;
    }

    public EntityModel getShippingTab() {
        return shippingTab;
    }

    public void setQuestionTap(EntityModel questionTap) {
        this.questionTap = questionTap;
    }

    public EntityModel getQuestionTap() {
        return questionTap;
    }

    public void setPassTab(EntityModel passTab) {
        this.passTab = passTab;
    }

    public EntityModel getPassTab() {
        return passTab;
    }

    public void setBookingAvailability(List<AvailabilityCalendarVO> bookingAvailability) {
        this.bookingAvailability = bookingAvailability;
    }

    public List<AvailabilityCalendarVO> getBookingAvailability() {
        return bookingAvailability;
    }

    public void setMovieTab(EntityModel movieTab) {
        this.movieTab = movieTab;
    }

    public EntityModel getMovieTab() {
        return movieTab;
    }

    public void setBundleTab(EntityModel bundleTab) {

        this.bundleTab = bundleTab;
    }

    public EntityModel getBundleTab() {
        return bundleTab;
    }

    public void setUpsellingTab(EntityModel upsellingTab) {

        this.upsellingTab = upsellingTab;
    }

    public EntityModel getUpsellingTab() {
        return upsellingTab;
    }

    public void setCrossSellTab(EntityModel crossSellTab) {
        this.crossSellTab = crossSellTab;
    }

    public EntityModel getCrossSellTab() {
        return crossSellTab;
    }

    public class GeneralTab {


    }
    private ProductTourResp tour;

    private EntityModel availabilityTab;
    private EntityModel componentTab;

    private EntityModel paymentTab;

    private Map parameterList;
   // private List<AvailabilityCalendarVO> bookingAvailability;




    public static ProductEditResp dayTourFrom(Product product, Tour tour, List<Campaign> campaignAssignToTourProducts) {



        ProductEditResp resp = new ProductEditResp();
     /*   resp.setSupplier(product.getSupplierId()+"");

        resp.setType(product.getType());
        resp.setType_text(product.getType().toString());
        resp.setCode(product.getCode());
*//*        resp.setSupplier(supplier.getName());
        resp.setSupplierCode(supplier.getCode());*//*
        resp.setName(product.getName());
        resp.setNameLong(product.getName_long());
        List<EnumLongIdResp> campaignList = campaignAssignToTourProducts.stream().map(x->{
            EnumLongIdResp enumLongIdResp = new EnumLongIdResp();
            enumLongIdResp.setId(x.getId());
            enumLongIdResp.setText(x.getName());
            return enumLongIdResp;
        }).collect(Collectors.toList());
        resp.setTour(ProductTourResp.from(tour,campaignList));
        resp.setStatus(product.getStatus());
        resp.setStatus_text(product.getStatus().toString());*/

        return resp;
    }








/*
    public static ProductEditResp from(Product e, List<PricingType> pricingTypeList) {

        ProductEditResp productResp = ProductEditResp.from(e);

*/
/*        productResp.setPriceTypes(pricingTypeList.stream().map(ee->{
            return EntityModel.of(PricingTypeResp.from(ee));
        }).collect(Collectors.toList()));*//*




        return productResp;
    }
*/


    public ProductTourResp getTour() {
        return tour;
    }

    public void setTour(ProductTourResp tour) {
        this.tour = tour;
    }

    private String supplier;
    
private String code;
    private String supplierCode;



    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }





    public List<AttributeResp> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeResp> attributes) {
        this.attributes = attributes;
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




    List<AttributeResp> attributes;


    @Transient
    private ProductTheatre theatre;
    @Transient
    private Attraction attraction;


/*    public static ProductEditResp from(Pair<Product,Supplier> pair) {
        Product product = pair.getValue0();
        Supplier supplier = pair.getValue1();

        ProductEditResp resp = ProductEditResp.from(product);
        resp.setSupplier(supplier.getName());
        resp.setSupplierCode(supplier.getCode());
        return resp;
    }*/



    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }





    public void setAvailabilityTab(EntityModel availability) {
        this.availabilityTab = availability;
    }

    public EntityModel getAvailabilityTab() {
        return availabilityTab;
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



    public void setPaymentTab(EntityModel paymentTab) {
        this.paymentTab = paymentTab;
    }

    public EntityModel getPaymentTab() {
        return paymentTab;
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




    public static class PaymentTap {
        private List<EnumPayChannel> payment_methods;
        private Map parameterList;

        public List<EnumPayChannel> getPayment_methods() {
            return payment_methods;
        }

        public void setPayment_methods(List<EnumPayChannel> payment_methods) {
            this.payment_methods = payment_methods;
        }

        public <K, V> void setParameterList(Map parameterList) {
            this.parameterList = parameterList;
        }

        public Map getParameterList() {
            return parameterList;
        }
    }


    private EntityModel publishTab;

    public EntityModel getPublishTab() {
        return publishTab;
    }

    public void setPublishTab(EntityModel publishTab) {
        this.publishTab = publishTab;
    }

    public static class PublishTab {


       // Flash Sale Countdown Campaign
   //     Give the Campaign Title => Select the Starting Date and => Ending Dat
        private String campaignTitle;
        private String startingDate;
        private String endingDate;

        public String getCampaignTitle() {
            return campaignTitle;
        }

        public void setCampaignTitle(String campaignTitle) {
            this.campaignTitle = campaignTitle;
        }

        public String getStartingDate() {
            return startingDate;
        }

        public void setStartingDate(String startingDate) {
            this.startingDate = startingDate;
        }

        public String getEndingDate() {
            return endingDate;
        }

        public void setEndingDate(String endingDate) {
            this.endingDate = endingDate;
        }

        private List<EntityModel> negotiatedRates;
        private Map<String, Object> parameterList;


        public  void setNegotiatedRates(List<EntityModel> negotiatedRates) {
            this.negotiatedRates = negotiatedRates;
        }

        public List<EntityModel> getNegotiatedRates() {
            return negotiatedRates;
        }

        public <K, V> void setParameterList(Map<String,Object> parameterList) {
            this.parameterList = parameterList;
        }

        public Map<String,Object> getParameterList() {
            return parameterList;
        }
    }

    private EntityModel infoTab;

    public EntityModel getInfoTab() {
        return infoTab;
    }

    public void setInfoTab(EntityModel infoTab) {
        this.infoTab = infoTab;
    }

    public static class InfoTab {
        private String code;
        private Map<String, Object> parameterList;
        private PhotoResp video;
        private EnumPrivacyLevel privacyLevel;
        private Boolean free;
        private Boolean show_note;
        private String note;
        private String desc_short;
        private List featureTags;

        public static InfoTab from(Product product) {


            InfoTab resp = new InfoTab();

            // resp.setPaymentOptionList(EnumPayChannel.from(Arrays.stream((new Gson()).fromJson(product.getPaymentMethods_json(), EnumPayChannel[].class)).collect(Collectors.toList())));
            resp.setType(product.getType());
            resp.setType_text(product.getType().toString());
            resp.setCode(product.getCode());

            resp.setName(product.getName());
            resp.setName_long(product.getName_long());
            resp.setDesc_long(product.getLong_desc());
            resp.setDesc_short(product.getDesc_short());
            resp.setPrivacyLevel(product.getPrivacyLevel());
            //  resp.setPaymentOptionList((new Gson()).fromJson(product.getPaymentMethods_json(),List.class));
            resp.setFree(product.getFree());
            resp.setShow_note(product.getShow_note());
            resp.setNote(product.getNote());
            resp.setStatus(product.getStatus());
            resp.setStatus_text(product.getStatus().toString());
            product.getFeatureTags_json();


            if(product.getFeatureTags_json()!= null){
                resp.setFeatureTags(Arrays.stream(new Gson().fromJson(product.getFeatureTags_json(), FeatureTagReq[].class)).map(e->{
                    FeatureTagResp featureTagResp = new FeatureTagResp();
                    featureTagResp.setType(e.getType());
                    featureTagResp.setText(e.getText());
                    featureTagResp.setIcon(e.getType().getIcon());
                    return featureTagResp;
                }).collect(Collectors.toList()));
            }else{
                resp.setFeatureTags(Arrays.asList());
            }



            resp.setStatus_text(product.getFeatureTags_json());
            resp.setTags((new Gson()).fromJson(product.getTags_json(),List.class));


            return resp;
        }
        private String type_text;
        private EnumProductStatus status;
        private String status_text;

        private List<String> tags;

        private List<PhotoResp> images;
        private PhotoResp thumb;
        private String desc_long;

        public String getType_text() {
            return type_text;
        }

        public void setType_text(String type_text) {
            this.type_text = type_text;
        }

        public EnumProductStatus getStatus() {
            return status;
        }

        public void setStatus(EnumProductStatus status) {
            this.status = status;
        }

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public List<PhotoResp> getImages() {
            return images;
        }

        public void setImages(List<PhotoResp> images) {
            this.images = images;
        }

        public PhotoResp getThumb() {
            return thumb;
        }

        public void setThumb(PhotoResp thumb) {
            this.thumb = thumb;
        }

        public String getDesc_long() {
            return desc_long;
        }

        public void setDesc_long(String desc_long) {
            this.desc_long = desc_long;
        }


        public void setCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        private String name;
        private String name_long;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName_long() {
            return name_long;
        }

        public void setName_long(String name_long) {
            this.name_long = name_long;
        }

        public EnumProductType getType() {
            return type;
        }

        public void setType(EnumProductType type) {
            this.type = type;
        }

        private EnumProductType type ;

        public <V, K> void setParameterList(Map<String, Object> parameterList) {
            this.parameterList = parameterList;
        }

        public Map<String, Object> getParameterList() {
            return parameterList;
        }

        public void setVideo(PhotoResp video) {
            this.video = video;
        }

        public PhotoResp getVideo() {
            return video;
        }

        public void setPrivacyLevel(EnumPrivacyLevel privacyLevel) {
            this.privacyLevel = privacyLevel;
        }

        public EnumPrivacyLevel getPrivacyLevel() {
            return privacyLevel;
        }

        public void setFree(Boolean free) {
            this.free = free;
        }

        public Boolean getFree() {
            return free;
        }

        public void setShow_note(Boolean show_note) {
            this.show_note = show_note;
        }

        public Boolean getShow_note() {
            return show_note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getNote() {
            return note;
        }

        public void setDesc_short(String desc_short) {
            this.desc_short = desc_short;
        }

        public String getDesc_short() {
            return desc_short;
        }

        public <R> void setFeatureTags(List featureTags) {
            this.featureTags = featureTags;
        }

        public List getFeatureTags() {
            return featureTags;
        }
    }




    public static class AvailabilityTab {
        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }
        private List<BookingRulePojoFuck> ranges;

        private Boolean availabilityRequired;//": true,  //是否需要选择时间
        private EnumAvailabilityType availability_type; // 可用类型， 枚举 参数表中有
        private EnumPassExpiry passExpiry;
        private LocalDate fixedPassExpiryDate
                ;
        private Long passValidForDays;
        private EnumPassCapacity passCapacityType;
        private Long passesCapacity;
        private Long passesAvailable;

        public Long getPassesCapacity() {
            return passesCapacity;
        }

        public void setPassesCapacity(Long passesCapacity) {
            this.passesCapacity = passesCapacity;
        }

        private String seasonalOpeningHours;
        private String defaultOpeningHours;
        private Long passCapacity;


        public Boolean getAvailabilityRequired() {
            return availabilityRequired;
        }

        public void setAvailabilityRequired(Boolean availabilityRequired) {
            this.availabilityRequired = availabilityRequired;
        }

        public EnumAvailabilityType getAvailability_type() {
            return availability_type;
        }

        public void setAvailability_type(EnumAvailabilityType availability_type) {
            this.availability_type = availability_type;
        }

        private Map parameterList;

        public static AvailabilityTab from(Product product, List<BookingRule> bookingRuleList) {
            AvailabilityTab availability = new AvailabilityTab();
            availability.setAvailability_type(product.getAvailability_type());
            availability.setAvailabilityRequired(product.getAvailabilityRequired());


            availability.setPassExpiry(product.getPassExpiry());
            availability.setFixedPassExpiryDate(product.getFixedPassExpiryDate());
            availability.setPassValidForDays(product.getPassValidForDays());
            availability.setPassExpiry(product.getPassExpiry());
            availability.setPassCapacityType(product.getPassCapacityType());
            availability.setPassCapacity(product.getPassCapacity());
            availability.setPassesCapacity(product.getPassCapacity());

            availability.setRanges(bookingRuleList.stream().map(e->{
                BookingRulePojoFuck bookingRuleVO = new BookingRulePojoFuck();
                bookingRuleVO.setBookable(e.getBookable());
                bookingRuleVO.setPriority(e.getPriority());

                bookingRuleVO.setType(e.getRangetype());




                if(e.getRangetype().equals(EnumAvailabilityRangetype.Date_range)){


                    // Parses the date
/*                LocalDate dt1
                        = LocalDate
                        .parse("2018-11-03T12:45:30");*/
                    bookingRuleVO.setValues(Arrays.asList(e.getBookings_from(),e.getBookings_to()));

                }
                if(e.getRangetype().equals(EnumAvailabilityRangetype.Range_of_months)){
   /*                 bookingRuleVO.setFrom(e.getMonth_from().toString());
                    bookingRuleVO.setTo(e.getMonth_to().toString());*/
                    bookingRuleVO.setValues(Arrays.stream(new Gson().fromJson(e.getMonths_json(), Month[].class)).collect(Collectors.toList()));

                }
                if(e.getRangetype().equals(EnumAvailabilityRangetype.Time_Range_$all_week$)){

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                 //   String formatDateTime = voucher.getExpiration_date().format(formatter);
                    bookingRuleVO.setValues(Arrays.asList(e.getTime_from().format(formatter),e.getTime_to().format(formatter)));


                }

                if(e.getRangetype().equals(EnumAvailabilityRangetype.Range_of_weeks)){
                    // bookingRuleVO.setFrom(e.getWeek_from().toString());
                    bookingRuleVO.setValues(Arrays.stream(new Gson().fromJson(e.getWeeks_json(), DayOfWeek[].class)).collect(Collectors.toList()));
                    //   bookingRuleVO.setTo(e.getWeek_to().toString());


                }
                return bookingRuleVO;
            }).collect(Collectors.toList()));

            return availability;

        }

        public List<BookingRulePojoFuck> getRanges() {
            return ranges;
        }

        public void setRanges(List<BookingRulePojoFuck> ranges) {
            this.ranges = ranges;
        }



        public <K, V> void setParameterList(Map parameterList) {
            this.parameterList = parameterList;
        }

        public Map getParameterList() {
            return parameterList;
        }

        public EnumPassExpiry getPassExpiry() {
            return passExpiry;
        }

        public void setPassExpiry(EnumPassExpiry passExpiry) {
            this.passExpiry = passExpiry;
        }

        public LocalDate getFixedPassExpiryDate() {
            return fixedPassExpiryDate;
        }

        public void setFixedPassExpiryDate(LocalDate fixedPassExpiryDate) {
            this.fixedPassExpiryDate = fixedPassExpiryDate;
        }

        public Long getPassValidForDays() {
            return passValidForDays;
        }

        public void setPassValidForDays(Long passValidForDays) {
            this.passValidForDays = passValidForDays;
        }

        public EnumPassCapacity getPassCapacityType() {
            return passCapacityType;
        }

        public void setPassCapacityType(EnumPassCapacity passCapacityType) {
            this.passCapacityType = passCapacityType;
        }

        public Long getPassCapacity() {
            return passCapacity;
        }

        public void setPassCapacity(Long passCapacity) {
            this.passCapacity = passCapacity;
        }
    }




    public static class CommentTab {

        private List<Comment> comments;

        private Map parameterList;

        public static CommentTab from(List<Comment> bookingRuleList) {
            CommentTab availability = new CommentTab();
            availability.setComments(bookingRuleList);

            return availability;
        }

        public List<Comment> getComments() {
            return comments;
        }

        public void setComments(List<Comment> comments) {
            this.comments = comments;
        }
    }



    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AboutTab {

        List<IdentifierReq> contacts;

        public List<IdentifierReq> getContacts() {
            return contacts;
        }

        public void setContacts(List<IdentifierReq> contacts) {
            this.contacts = contacts;
        }
        private Map parameterList;

        private List tips;
        private List<AttributeEditReq> attributes;


        public static AboutTab from(List<Attribute> bookingRuleList) {
            AboutTab availability = new AboutTab();

            availability.setAttributes(bookingRuleList.stream().map(e->{
                AttributeEditReq attributeEditReq = new AttributeEditReq();
                attributeEditReq.setName(e.getName());
                attributeEditReq.setText(e.getDescription());
                attributeEditReq.setType(e.getFeatureType());
                attributeEditReq.setId(e.getId());
                return attributeEditReq;

            }).collect(Collectors.toList()));
/*            availability.setComponents(bookingRuleList.stream().map(e->{
                return ComponentResp.from(e);
            }).collect(Collectors.toList()));*/

            return availability;
        }


        public <V, K> void setParameterList(Map parameterList) {
            this.parameterList = parameterList;
        }

        public Map getParameterList() {
            return parameterList;
        }


        public <T> void setTips(List tips) {
            this.tips = tips;
        }

        public List getTips() {
            return tips;
        }


        public void setAttributes(List<AttributeEditReq> attributes) {
            this.attributes = attributes;
        }

        public List<AttributeEditReq> getAttributes() {
            return attributes;
        }
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ShippingTab {
        private List<EnumDeliveryFormat> deliveryFormats;
        private EnumShippingAddressCollection shippingAddressCollection;



        public Integer getShipping_rate() {
            return shipping_rate;
        }

        public void setShipping_rate(Integer shipping_rate) {
            this.shipping_rate = shipping_rate;
        }

        public boolean isShippable() {
            return shippable;
        }

        public void setShippable(boolean shippable) {
            this.shippable = shippable;
        }

        private Map parameterList;

        private Integer shipping_rate;

        private List<Long> shipping_rates;


        private boolean shippable;
        private List tips;
        private List<AttributeEditReq> attributes;

        public List<Long> getShipping_rates() {
            return shipping_rates;
        }

        public void setShipping_rates(List<Long> shipping_rates) {
            this.shipping_rates = shipping_rates;
        }

        public static ShippingTab from(Product bookingRuleList) {
            ShippingTab availability = new ShippingTab();


            if(bookingRuleList.getShipping_rates_json()!=null){
                availability.setShipping_rates(Arrays.stream(GSON.fromJson(bookingRuleList.getShipping_rates_json(),Long[].class)).toList());
            }else{
                availability.setShipping_rates(Arrays.asList());
            }
            availability.setShippable(bookingRuleList.getShippable());
            availability.setShipping_rate(bookingRuleList.getShipping_rate());

            availability.setShippingAddressCollection(bookingRuleList.getShippingAddressCollection());

            if(bookingRuleList.getDeliveryFormats_json()!=null){
                availability.setDeliveryFormats(Arrays.stream(GSON.fromJson(bookingRuleList.getDeliveryFormats_json(),EnumDeliveryFormat[].class)).toList());
            }else{
                availability.setDeliveryFormats(Arrays.asList());
            }

            return availability;
        }


        public <V, K> void setParameterList(Map parameterList) {
            this.parameterList = parameterList;
        }

        public Map getParameterList() {
            return parameterList;
        }


        public <T> void setTips(List tips) {
            this.tips = tips;
        }

        public List getTips() {
            return tips;
        }


        public void setAttributes(List<AttributeEditReq> attributes) {
            this.attributes = attributes;
        }

        public List<AttributeEditReq> getAttributes() {
            return attributes;
        }

        public List<EnumDeliveryFormat> getDeliveryFormats() {
            return deliveryFormats;
        }

        public void setDeliveryFormats(List<EnumDeliveryFormat> deliveryFormats) {
            this.deliveryFormats = deliveryFormats;
        }

        public void setShippingAddressCollection(EnumShippingAddressCollection shippingAddressCollection) {
            this.shippingAddressCollection = shippingAddressCollection;
        }

        public EnumShippingAddressCollection getShippingAddressCollection() {
            return shippingAddressCollection;
        }
    }







    public static class QuestionTap {
        private List<EnumPayChannel> payment_methods;
        private Map parameterList;

        public List<EnumPayChannel> getPayment_methods() {
            return payment_methods;
        }

        public void setPayment_methods(List<EnumPayChannel> payment_methods) {
            this.payment_methods = payment_methods;
        }

        public <K, V> void setParameterList(Map parameterList) {
            this.parameterList = parameterList;
        }

        public Map getParameterList() {
            return parameterList;
        }
    }








    public static class PassTab {


        @NotNull
        private Integer duration ;//digital tickets or PDF tickets

        @NotNull
        private EnumPassDorationUnit durationUnit ;//digital tickets or PDF tickets
        private Map parameterList;
        private String label;
        private PhotoResp cover;
        private Integer activeExpiryDuration;
        private EnumPassDorationUnit activeExpiryDurationUnit;
        public static MovieTab from(MovieProduct passProduct) {
            MovieTab passTab = new MovieTab();
            passTab.setMovie(passProduct.getMovie());


            return passTab;
        }

        public static PassTab from(PassProduct passProduct) {
            PassTab passTab = new PassTab();
            passTab.setDuration(passProduct.getDuration());
            passTab.setDurationUnit(passProduct.getDurationUnit());
            passTab.setActiveExpiryDurationUnit(passProduct.getActiveExpiryDurationUnit());
            passTab.setActiveExpiryDuration(passProduct.getActiveExpiryDuration());

            passTab.setLabel(passProduct.getLabel());

            return passTab;
        }

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public EnumPassDorationUnit getDurationUnit() {
            return durationUnit;
        }

        public void setDurationUnit(EnumPassDorationUnit durationUnit) {
            this.durationUnit = durationUnit;
        }


        public  void setParameterList(Map parameterList) {
            this.parameterList = parameterList;
        }

        public Map getParameterList() {
            return parameterList;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public void setCover(PhotoResp cover) {
            this.cover = cover;
        }

        public PhotoResp getCover() {
            return cover;
        }

        public Integer getActiveExpiryDuration() {
            return activeExpiryDuration;
        }

        public void setActiveExpiryDuration(Integer activeExpiryDuration) {
            this.activeExpiryDuration = activeExpiryDuration;
        }

        public EnumPassDorationUnit getActiveExpiryDurationUnit() {
            return activeExpiryDurationUnit;
        }

        public void setActiveExpiryDurationUnit(EnumPassDorationUnit activeExpiryDurationUnit) {
            this.activeExpiryDurationUnit = activeExpiryDurationUnit;
        }
    }

    public static class MovieTab {

        private Map parameterList;
        private Long seatingLayout;
        private Long theatre;


        private List<ZonePricingReq> zonePricings;

        public List<ZonePricingReq> getZonePricings() {
            return zonePricings;
        }

        public void setZonePricings(List<ZonePricingReq> zonePricings) {
            this.zonePricings = zonePricings;
        }

        public Long getMovie() {
            return movie;
        }

        public void setMovie(Long movie) {
            this.movie = movie;
        }

        private Long movie;

        public static MovieTab from(MovieProduct passProduct) {
            MovieTab passTab = new MovieTab();
            passTab.setMovie(passProduct.getMovie());
            passTab.setZonePricings(passProduct.getZonePricings().stream().map(e->{
                return ZonePricingReq.from(e);
            }).collect(Collectors.toList()));
            passTab.setSeatingLayout(passProduct.getSeatingLayout());
            passTab.setTheatre(passProduct.getTheatre());
            return passTab;
        }

        public void setParameterList(Map parameterList) {
            this.parameterList = parameterList;
        }

        public Map getParameterList() {
            return parameterList;
        }

        public Long getSeatingLayout() {
            return seatingLayout;
        }

        public void setSeatingLayout(Long seatingLayout) {
            this.seatingLayout = seatingLayout;
        }

        public Long getTheatre() {
            return theatre;
        }

        public void setTheatre(Long theatre) {
            this.theatre = theatre;
        }
    }


    public static class BundleTab {

        private List<ProductBundleReq> bundles;

        private Map parameterList;

        public static BundleTab from(List<ProductBundle> bookingRuleList) {

            BundleTab availability = new BundleTab();
            availability.setBundles(bookingRuleList.stream().map(e->{
                ProductBundleReq productBundleReq = new ProductBundleReq();
                productBundleReq.setId(e.getBurdle());

                return productBundleReq;
            }).collect(Collectors.toList()));

            return availability;
        }

        public List<ProductBundleReq> getBundles() {
            return bundles;
        }

        public void setBundles(List<ProductBundleReq> bundles) {
            this.bundles = bundles;
        }

        public <K, V> void setParameterList(Map parameterList) {
            this.parameterList = parameterList;
        }

        public Map getParameterList() {
            return parameterList;
        }
    }



    public static class UpsellingTab {

        private List<ProductBundleReq> bundles;

        private Map parameterList;

        public static UpsellingTab from(List<ProductBundle> bookingRuleList) {

            UpsellingTab availability = new UpsellingTab();
            availability.setBundles(bookingRuleList.stream().map(e->{
                ProductBundleReq productBundleReq = new ProductBundleReq();
                productBundleReq.setId(e.getBurdle());

                return productBundleReq;
            }).collect(Collectors.toList()));

            return availability;
        }

        public List<ProductBundleReq> getBundles() {
            return bundles;
        }

        public void setBundles(List<ProductBundleReq> bundles) {
            this.bundles = bundles;
        }

        public <K, V> void setParameterList(Map parameterList) {
            this.parameterList = parameterList;
        }

        public Map getParameterList() {
            return parameterList;
        }
    }






















    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CrossSellTab {

        List<IdentifierReq> contacts;

        public List<IdentifierReq> getContacts() {
            return contacts;
        }

        public void setContacts(List<IdentifierReq> contacts) {
            this.contacts = contacts;
        }
        private Map parameterList;

        private List tips;
        private List<CrossSellReq> crossSells;


        public static CrossSellTab from(List<CrossSell> crossSells) {
            CrossSellTab availability = new CrossSellTab();

            availability.setCrossSells(crossSells.stream().map(e->{
                CrossSellReq attributeEditReq = new CrossSellReq();
                attributeEditReq.setCode(e.getCode());
                attributeEditReq.setTitle(e.getProduct().getName());

                attributeEditReq.setId(e.getProduct().getId());
                return attributeEditReq;

            }).collect(Collectors.toList()));
/*            availability.setComponents(bookingRuleList.stream().map(e->{
                return ComponentResp.from(e);
            }).collect(Collectors.toList()));*/

            return availability;
        }


        public <V, K> void setParameterList(Map parameterList) {
            this.parameterList = parameterList;
        }

        public Map getParameterList() {
            return parameterList;
        }


        public <T> void setTips(List tips) {
            this.tips = tips;
        }

        public List getTips() {
            return tips;
        }


        public void setCrossSells(List<CrossSellReq> crossSells) {
            this.crossSells = crossSells;
        }

        public List<CrossSellReq> getCrossSells() {
            return crossSells;
        }
    }
}
