package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingRulePojo;
import com.lt.dom.otcReq.ProductPojo;
import com.lt.dom.otcReq.RestrictionReq;
import com.lt.dom.otcReq.ShippingAddressResp;
import com.lt.dom.otcenum.*;
import com.lt.dom.vo.AvailabilityCalendarVO;
import org.javatuples.Pair;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductBookingResp extends BaseResp{


    private List ratePlans;
    private PricingTypeResp default_sku;
    private PhotoResp video;
    private Boolean hasVideo;
    private List<FeatureTagResp> featureTags;
    private String availabilityNote;
    private List<CommentResp> comments;

    private List options;
    private Long reviewCount;


    private String desc_long;
    private List zones;
    private EnumAvailabilityType availability_type;
    private EntityModel booking;
    private List<EntityModel> youLike;
    private List extras;
    private List crossSells;
    private List shipping_options;
    private EnumShippingAddressCollection shippingAddressCollection;
    private Shipping shipping;

    public static ActivityResp toActivity(Product product) {
        ActivityResp resp = new ActivityResp();



        resp.setType_text(product.getType().toString());
        resp.setCode(product.getCode());
        resp.setName(product.getName());
        resp.setDesc_short(product.getDesc_short());

        resp.setStatus_text(product.getStatus().toString());
        return resp;
    }

    public  void setRatePlans(List ratePlans) {
        this.ratePlans = ratePlans;
    }

    public List getRatePlans() {
        return ratePlans;
    }

    public RestrictionResp getRestriction() {
        return restriction;
    }

    public void setRestriction(RestrictionResp restriction) {
        this.restriction = restriction;
    }

    private RestrictionResp restriction;




    private ProductTourResp tour;
    private String type_text;
    private EnumProductStatus status;
    private String status_text;
    private List<EntityModel> priceTypes;
    private List<String> tags;
    private PricingTypeResp default_price;
    private List<PhotoResp> images;
    private PhotoResp thumbnail_image;
    private EntityModel availability;
    private List<AvailabilityCalendarVO> bookingAvailability;
    private Boolean shippable;
    private String traveller_term;
    private String booking_note;
    private Long id;
    private String desc_short;
    private String short_name;
    private List<Long> component_rights;

    private List skus;
    private List about;




    public ProductTourResp getTour() {
        return tour;
    }

    public void setTour(ProductTourResp tour) {
        this.tour = tour;
    }

    private EntityModel supplier;
    

private String code;
    private String supplierCode;
    private String name;
    private String nameLong;


    public EntityModel getSupplier() {
        return supplier;
    }

    public void setSupplier(EntityModel supplier) {
        this.supplier = supplier;
    }

    public List<ComponentResp> getComponents() {
        return components;
    }

    public void setComponents(List<ComponentResp> components) {
        this.components = components;
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

/*
    public List<EnumResp> getPaymentOptionList() {
        return paymentOptionList;
    }

    public void setPaymentOptionList(List<EnumResp> paymentOptionList) {
        this.paymentOptionList = paymentOptionList;
    }
*/


    public ProductTheatre getTheatre() {
        return theatre;
    }

    public void setTheatre(ProductTheatre theatre) {
        this.theatre = theatre;
    }

    public AttractionResp getAttraction() {
        return attraction;
    }

    public void setAttraction(AttractionResp attraction) {
        this.attraction = attraction;
    }

    List<ComponentResp> components;



    private EnumProductType type ;

    List<AttributeResp> attributes;
   // List<EnumResp> paymentOptionList;


    private ProductTheatre theatre;

    private AttractionResp attraction;


    public static ProductBookingResp from(Pair<Product,Supplier> pair) {
        Product product = pair.getValue0();
        Supplier supplier = pair.getValue1();

        ProductBookingResp resp = ProductBookingResp.Simplefrom(product);
        resp.setSupplier(EntityModel.of(supplier));
        resp.setSupplierCode(supplier.getCode());
        return resp;
    }


    public static ProductBookingResp from(Product product) {

        ProductBookingResp resp = ProductBookingResp.Simplefrom(product);

        resp.setName_long(product.getName_long());
        resp.setShippable(product.getShippable());

        resp.setTags((new Gson()).fromJson(product.getTags_json(),List.class));
        resp.setStatus_text(product.getStatus().toString());
        resp.setStatus(product.getStatus());



        RestrictionResp restriction1 = new RestrictionResp();

        restriction1.setMinQuantity(product.getRestriction_minQuantity());
        restriction1.setMaxQuantity(product.getRestriction_maxQuantity());
      //  restriction1.setPassenger_identity_documents_required(product.getRestriction_passenger_identity_documents_required());
        restriction1.setIdRequired(product.getRestriction_passenger_identity_documents_required());

        resp.setRestriction(restriction1);


        return resp;
    }



    public static ProductBookingResp Simplefrom(Product product) {


        ProductBookingResp resp = new ProductBookingResp();
        resp.setDesc_short(product.getDesc_short());
        resp.setName_long(product.getName_long());
        resp.setShort_name(product.getName());

        resp.setType(product.getType());
        resp.setType_text(product.getType().toString());
        resp.setCode(product.getCode());
        resp.setName(product.getName());
        resp.setStatus_text(product.getStatus().toString());
        resp.setStatus(product.getStatus());



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

    public void setDefault_price(PricingTypeResp priceType) {
        this.default_price = priceType;
    }

    public PricingTypeResp getDefault_price() {
        return default_price;
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

    public void setAvailability(EntityModel availability) {
        this.availability = availability;
    }

    public EntityModel getAvailability() {
        return availability;
    }

    public void setBookingAvailability(List<AvailabilityCalendarVO> bookingAvailability) {
        this.bookingAvailability = bookingAvailability;
    }

    public List<AvailabilityCalendarVO> getBookingAvailability() {
        return bookingAvailability;
    }

    public void setShippable(Boolean shippable) {
        this.shippable = shippable;
    }

    public Boolean getShippable() {
        return shippable;
    }

    public void setTraveller_term(String traveller_term) {
        this.traveller_term = traveller_term;
    }

    public String getTraveller_term() {
        return traveller_term;
    }

    public void setBooking_note(String booking_note) {
        this.booking_note = booking_note;
    }

    public String getBooking_note() {
        return booking_note;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDesc_short(String desc_short) {
        this.desc_short = desc_short;
    }

    public String getDesc_short() {
        return desc_short;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setComponent_rights(List<Long> component_rights) {
        this.component_rights = component_rights;
    }

    public List<Long> getComponent_rights() {
        return component_rights;
    }



    public void setSkus(List skus) {
        this.skus = skus;
    }

    public List getSkus() {
        return skus;
    }

    public void setAbout(List about) {
        this.about = about;
    }

    public List getAbout() {
        return about;
    }

    public void setDefault_sku(PricingTypeResp default_sku) {
        this.default_sku = default_sku;
    }

    public PricingTypeResp getDefault_sku() {
        return default_sku;
    }

    public void setVideo(PhotoResp video) {
        this.video = video;
    }

    public PhotoResp getVideo() {
        return video;
    }

    public void setHasVideo(Boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public Boolean isHasVideo() {
        return hasVideo;
    }

    public <T> void setFeatureTags(List<FeatureTagResp> featureTags) {
        this.featureTags = featureTags;
    }

    public List<FeatureTagResp> getFeatureTags() {
        return featureTags;
    }

    public void setAvailabilityNote(String availabilityNote) {
        this.availabilityNote = availabilityNote;
    }

    public String getAvailabilityNote() {
        return availabilityNote;
    }


    public static void withRateplans(ProductBookingResp subscription, List<RatePlan> ratePlans) {


        subscription.setRatePlans(ratePlans.stream().map(e->{
            RatePlanResp ratePlanResp = RatePlanResp.from(e);
            return ratePlanResp;
        }).collect(Collectors.toList()));

    }

    public void setComments(List<CommentResp> comments) {
        this.comments = comments;
    }

    public List<CommentResp> getComments() {
        return comments;
    }



    public <T> void setOptions(List options) {
        this.options = options;
    }

    public List getOptions() {
        return options;
    }

    public void setReviewCount(Long reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Long getReviewCount() {
        return reviewCount;
    }




    public void setDesc_long(String desc_long) {
        this.desc_long = desc_long;
    }

    public String getDesc_long() {
        return desc_long;
    }

    public <R> void setZones(List zones) {
        this.zones = zones;
    }

    public List getZones() {
        return zones;
    }

    public void setAvailability_type(EnumAvailabilityType availability_type) {
        this.availability_type = availability_type;
    }

    public EnumAvailabilityType getAvailability_type() {
        return availability_type;
    }

    public <T> void setBooking(EntityModel booking) {
        this.booking = booking;
    }

    public EntityModel getBooking() {
        return booking;
    }

    public void setYouLike(List<EntityModel> youLike) {
        this.youLike = youLike;
    }

    public List<EntityModel> getYouLike() {
        return youLike;
    }

    public <R> void setExtras(List extras) {
        this.extras = extras;
    }

    public List getExtras() {
        return extras;
    }

    public <R> void setCrossSells(List crossSells) {
        this.crossSells = crossSells;
    }

    public List getCrossSells() {
        return crossSells;
    }

    public <R> void setShipping_options(List shipping_options) {
        this.shipping_options = shipping_options;
    }

    public List getShipping_options() {
        return shipping_options;
    }

    public void setShippingAddressCollection(EnumShippingAddressCollection shippingAddressCollection) {
        this.shippingAddressCollection = shippingAddressCollection;
    }

    public EnumShippingAddressCollection getShippingAddressCollection() {
        return shippingAddressCollection;
    }

    public void setShipping(Shipping shipping) {

        this.shipping = shipping;
    }

    public Shipping getShipping() {
        return shipping;
    }


    public static class AvailabilityTab {

        private List<BookingRulePojo> ranges;
        private Map parameter;

        public static AvailabilityTab from(List<BookingRule> bookingRuleList) {
            AvailabilityTab availability = new AvailabilityTab();
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













    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ComponentTap {

        private List<ComponentResp> components;

        private List<EntityModel> prices;
        private Map parameterList;
        private List<ProductPojo.Royalty> royalties;

        public List<EntityModel> getPrices() {
            return prices;
        }

        public void setPrices(List<EntityModel> prices) {
            this.prices = prices;
        }

        public static ComponentTap from(List<Component> bookingRuleList) {
            ComponentTap availability = new ComponentTap();

/*            availability.setComponents(bookingRuleList.stream().map(e->{
                return ComponentResp.from(e);
            }).collect(Collectors.toList()));*/

            return availability;
        }

        public List<ComponentResp> getComponents() {
            return components;
        }

        public void setComponents(List<ComponentResp> components) {
            this.components = components;
        }

        public <V, K> void setParameterList(Map parameterList) {
            this.parameterList = parameterList;
        }

        public Map getParameterList() {
            return parameterList;
        }

        public void setRoyalties(List<ProductPojo.Royalty> royalties) {
            this.royalties = royalties;
        }

        public List<ProductPojo.Royalty> getRoyalties() {
            return royalties;
        }
    }










    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RightTap {

        private List<ComponentResp> components;

        private List<EntityModel> prices;
        private Map parameterList;
        private List<ProductPojo.Royalty> royalties;

        public List<EntityModel> getPrices() {
            return prices;
        }

        public void setPrices(List<EntityModel> prices) {
            this.prices = prices;
        }

        public static RightTap from(List<Component> bookingRuleList) {
            RightTap availability = new RightTap();

/*            availability.setComponents(bookingRuleList.stream().map(e->{
                return ComponentResp.from(e);
            }).collect(Collectors.toList()));*/

            return availability;
        }

        public List<ComponentResp> getComponents() {
            return components;
        }

        public void setComponents(List<ComponentResp> components) {
            this.components = components;
        }

        public <V, K> void setParameterList(Map parameterList) {
            this.parameterList = parameterList;
        }

        public Map getParameterList() {
            return parameterList;
        }

        public void setRoyalties(List<ProductPojo.Royalty> royalties) {
            this.royalties = royalties;
        }

        public List<ProductPojo.Royalty> getRoyalties() {
            return royalties;
        }
    }




    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RuleTap {
        private EnumConfirmationType confirmationType;

        private RestrictionReq restriction;

        public RestrictionReq getRestriction() {
            return restriction;
        }

        public void setRestriction(RestrictionReq restriction) {
            this.restriction = restriction;
        }

        private Map parameterList;

        private List tips;


        public static RuleTap from(Product bookingRuleList) {
            RuleTap availability = new RuleTap();

            availability.setConfirmationType(bookingRuleList.getRule_confirmationType());

            RestrictionReq restrictionReq = new RestrictionReq();
            restrictionReq.setMaxAge(bookingRuleList.getRestriction_maxQuantity());
            restrictionReq.setMinQuantity(bookingRuleList.getRestriction_minQuantity());
            restrictionReq.setPassenger_identity_documents_required(bookingRuleList.getRestriction_passenger_identity_documents_required());

            availability.setRestriction(restrictionReq);


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

        public void setConfirmationType(EnumConfirmationType confirmationType) {
            this.confirmationType = confirmationType;
        }

        public EnumConfirmationType getConfirmationType() {
            return confirmationType;
        }


    }




    public static class Availability {

        private EnumAvailabilityType type;
        private String text;
        private List<AvailabilityCalendarVO> bookingAvailability;


        public static Availability from(Product product) {


            Availability availability = new Availability();
            availability.setType(product.getAvailability_type());


            if(product.getAvailability_type().equals(EnumAvailabilityType.PASS)){

                if(product.getPassExpiry().equals(EnumPassExpiry.FIXED_DATE)){
                    availability.setText(String.format("有效期截至: %s", product.getFixedPassExpiryDate().toString()));
                }
                if(product.getPassExpiry().equals(EnumPassExpiry.RELATIVE_DATE)){
                    availability.setText(String.format("购买后: %s 天有效", product.getPassValidForDays()));
                }
                if(product.getPassExpiry().equals(EnumPassExpiry.NEVER)){
                    availability.setText(String.format("一直有效"));
                }
            }



            return availability;
        }



        public void setType(EnumAvailabilityType type) {
            this.type = type;
        }

        public EnumAvailabilityType getType() {
            return type;
        }

        public void setText(String text) {

            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setBookingAvailability(List<AvailabilityCalendarVO> bookingAvailability) {

            this.bookingAvailability = bookingAvailability;
        }

        public List<AvailabilityCalendarVO> getBookingAvailability() {
            return bookingAvailability;
        }
    }



    public static class Shipping {


        private List shipping_options;
        private ShippingAddressResp defaultShippingAddress;

        public static Availability from(Product product) {


            Availability availability = new Availability();
            availability.setType(product.getAvailability_type());


            if(product.getAvailability_type().equals(EnumAvailabilityType.PASS)){

                if(product.getPassExpiry().equals(EnumPassExpiry.FIXED_DATE)){
                    availability.setText(String.format("有效期截至: %s", product.getFixedPassExpiryDate().toString()));
                }
                if(product.getPassExpiry().equals(EnumPassExpiry.RELATIVE_DATE)){
                    availability.setText(String.format("购买后: %s 天有效", product.getPassValidForDays()));
                }
                if(product.getPassExpiry().equals(EnumPassExpiry.NEVER)){
                    availability.setText(String.format("一直有效"));
                }
            }



            return availability;
        }


        public <R> void setShipping_options(List shipping_options) {
            this.shipping_options = shipping_options;
        }

        public List getShipping_options() {
            return shipping_options;
        }

        public void setDefaultShippingAddress(ShippingAddressResp defaultShippingAddress) {
            this.defaultShippingAddress = defaultShippingAddress;
        }

        public ShippingAddressResp getDefaultShippingAddress() {
            return defaultShippingAddress;
        }
    }

}
