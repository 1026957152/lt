package com.lt.dom.serviceOtc;

import com.google.gson.Gson;
import com.lt.dom.OctResp.ProductEditResp;
import com.lt.dom.OctResp.ProductResp;
import com.lt.dom.controllerOct.ProductRestController;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcReq.product.ProductGiftVoucherPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.specification.ProductQueryfieldsCriteria;
import com.lt.dom.specification.ProductSpecification;
import com.lt.dom.vo.BookingRuleVO;
import org.apache.commons.lang3.ObjectUtils;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.lt.dom.serviceOtc.JsonParse.GSON;
import static org.springframework.data.jpa.domain.Specification.where;


@Service
public class ProductServiceImpl {


    @Autowired
    private TourRepository tourRepository;


    @Autowired
    private PricingTypeRepository pricingTypeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TermRepository termRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private PassProductRepository passProductRepository;


    @Autowired
    private FileStorageServiceImpl fileStorageService;
    @Autowired
    private MovieProductRepository movieProductRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private TheatreRepository theatreRepository;


    @Autowired
    private MovieRepository movieRepository;


    @Autowired
    private BookingRuleRepository bookingRuleRepository;

    @Autowired
    private BookingQuestionRepository bookingQuestionRepository;
    @Autowired
    private RoyaltyRuleRepository royaltyRuleRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private ComponentRightRepository componentRightRepository;

    @Autowired
    private CampaignAssignToTourProductRepository campaignAssignToTourProductRepository;

    @Autowired
    private AttractionRepository attractionRepository;


    @Autowired
    private PriceServiceImpl priceService;

    @Autowired
    private ComponentRightServiceImpl componentRightService;
    private List<Product> active;


    public Page<Product> listProduct(Supplier supplier, Pageable pageable) {

        return productRepository.findBySupplierId(supplier.getId(),pageable);
    }


    Gson gson = new Gson();




    @Transactional
    public Pair<Product,Supplier> createProduct(Supplier supplier, ProductPojo pojo, List<Campaign> campaigns) {
        Product product = new Product();

       // product.setN(pojo.getName());

        product.setPackage_(pojo.getPackage_());
        product.setFree(pojo.getFree());
        product.setType(pojo.getType());
        product.setCode(idGenService.productNo());
        product.setSupplierId(supplier.getId());
        product.setName(pojo.getName());
        product.setName_long(pojo.getName_long());
        product.setDesc_long(pojo.getLong_desc());

        product.setStatus(EnumProductStatus.draft);

        product.setAcitve(true);

        product.setAvailability_type(pojo.getAvailability_type());
        product.setShippable(false);

        product.setShippable(pojo.getShippable());
        product.setShipping_rate(pojo.getShipping_rate());
        ;
        product.setRefund(pojo.getIs_refund());
        product.setDesc_long(pojo.getLong_desc());
        product.setPaymentMethods_json(new Gson().toJson(Arrays.asList()));
      //  product.setRefund_note(pojo.getRefund().getNote());
        product.setNote(pojo.getNote());

    //    product.setPayment_behavior(EnumFulfillment_behavior)
/*
        product.setTags_json((new Gson()).toJson(pojo.getTags()));
        product.setRestriction_maxQuantity(pojo.getRestriction().getMaxQuantity());
        product.setRestriction_minQuantity(pojo.getRestriction().getMinQuantity());
*/

     //   product.setRestriction_passenger_identity_documents_required(pojo.getRestriction().getPassenger_identity_documents_required());


    //    product.setPaymentMethods_json((new Gson()).toJson(pojo.getPayment_methods()));


/*        if(pojo.getRoyalties().stream()
                .filter(e->e.getValidate_way().equals(EnumValidateWay.offline_manual))
                .findAny().isPresent()){



            product.setValidate_way(EnumValidateWay.offline_manual);
        }else{
            product.setValidate_way(EnumValidateWay.none);

        }*/


        product.setAvailabilityRequired(pojo.getAvailabilityRequired());


        if(product.getType().equals(EnumProductType.Multi_Ticket)){
            product.setPackage_(true);
        }
        product = productRepository.save(product);





        if(product.getType().equals(EnumProductType.Attraction)){


            if(pojo.getAttraction() != null){
                Optional<Attraction> attractionOptional = attractionRepository.findById(pojo.getAttraction().getId());
                if(attractionOptional.isEmpty()){
                    throw new BookNotFoundException(Enumfailures.not_found,"找不到景区");
                }
                Attraction attraction = attractionOptional.get();

                product.setTypeTo(attraction.getId());

                //  product.setDefault_price(0L);
                product = productRepository.save(product);
            }


/*


            if(pojo.getRoyalties() != null){

                if(pojo.getRoyalties().stream()
                        .filter(e->e.getValidate_way().equals(EnumValidateWay.offline_manual))
                        .findAny().isPresent()){
                    product.setValidate_way(EnumValidateWay.offline_manual);
                }else{
                    product.setValidate_way(EnumValidateWay.none);

                }

                componentRightService.assingtoProduct(product,pojo.getRoyalties());

            }
      */

        }



        if(product.getType().equals(EnumProductType.Pass)){


            PassProduct passProduct = new PassProduct();

            passProduct.setProduct(product.getId());

            passProductRepository.save(passProduct);

           // componentRightService.assingtoProduct(product,pojo.getPass().getRoyalties());


        }

        if(product.getType().equals(EnumProductType.Daytour)){
            Tour tour = new Tour();

            tour.setTour_name(pojo.getName());
            tour.setTour_name_long(pojo.getName_long());
            tour.setProduct(product.getId());
            tour.setLine_info(pojo.getDay_tour().getLine_info());
            tour.setDays(pojo.getDay_tour().getDays().value);
            tour.setHotels(gson.toJson(pojo.getDay_tour().getHotels()));

            tour = tourRepository.save(tour);


            product.setTypeTo(tour.getId());
            product = productRepository.save(product);


            Product finalProduct1 = product;
            Tour finalTour = tour;
            List<CampaignAssignToTourProduct> list =  campaigns.stream().map(x->{
                CampaignAssignToTourProduct campaignAssignToTourProduct = new CampaignAssignToTourProduct();
                campaignAssignToTourProduct.setCampaign(x.getId());
                campaignAssignToTourProduct.setCampaign_code(x.getCode());
                campaignAssignToTourProduct.setProduct(finalProduct1.getId());
                campaignAssignToTourProduct.setTourId(finalTour.getId());
                return campaignAssignToTourProduct;
            }).collect(Collectors.toList());

            list = campaignAssignToTourProductRepository.saveAll(list);



        }
/*        Product finalProduct = product;
        if(pojo.getAttributes() != null){
            List<Attribute> attributes = pojo.getAttributes().stream().map(x->{
                Attribute attribute = new Attribute();
                attribute.setName(x.getName());
                attribute.setDescription(x.getDescription());
                attribute.setObjectId(finalProduct.getId());
                return attribute;
            }).collect(Collectors.toList());

            attributeRepository.saveAll(attributes);
        }*/





/*


        if(pojo.getPrices() != null){


            List<ComponentRight> componentRights = componentRightRepository
                    .findAllById(pojo.getPrices().stream().map(e->e.getRights()).flatMap(List::stream).collect(Collectors.toList()));




            List<Triplet<PricingType,String,List<ComponentRight>>>  priceTyps = pojo.getPrices().stream().map(x->{

                PricingType pricingType = priceService.getPriceType(finalProduct,x);

                String seq = UUID.randomUUID().toString();
                pricingType.setStreamSeq(seq);
                return Triplet.with(pricingType,seq,componentRights);
            }).collect(Collectors.toList());




            List<PricingType> priceTypeList = pricingTypeRepository.saveAll(priceTyps.stream().map(e->e.getValue0()).collect(Collectors.toList()));



            product.setDefault_price(priceTypeList.get(0).getId());


            Map<String,PricingType> pricingTypeMap = priceTypeList.stream().collect(Collectors.toMap(e->e.getStreamSeq(),e->e));



            List<Component> componentList_all_pricetype = priceTyps.stream().map(ee->{
                List<ComponentRight> componentRightList = ee.getValue2();
                PricingType pricingType = pricingTypeMap.get(ee.getValue1());

                return componentRightList.stream().map(com->{
                    Component component = new Component();
                    component.setUnit_off(com.getLimit_quantity());
                    component.setDuration(com.getDuration());
                    component.setComponentRight(com.getId());
                    component.setPriceingType(pricingType.getId());
                    component.setSupplier(com.getSupplier());
                    return component;
                }).collect(Collectors.toList());

            }).flatMap(List::stream).collect(Collectors.toList());


            componentRepository.saveAll(componentList_all_pricetype);

        }



        if(pojo.getPrice() != null){

            PricingType pricingType = priceService.getPriceType(product,pojo.getPrice());
            pricingType = pricingTypeRepository.save(pricingType);
            product.setDefault_price(pricingType.getId());

        }


        if(pojo.getBooking_rules() != null){
            List<BookingRuleVO> bookingRuleVOList  = addBookingRuleWithValidate(product,pojo.getBooking_rules());

            List<BookingRule> bookingRule  = addBookingRule(product,bookingRuleVOList);


        }


*/


        product = productRepository.save(product);

        return Pair.with(product,supplier);
    }













    public Pair<Product,Supplier> createPass(Supplier supplier, ProductPassCreatePojo pojo, List<ComponentRight> componentRightList_) {
        Product product = new Product();
        product.setDesc_short(pojo.getDesc_short());
        product.setType(pojo.getType());
        product.setCode(idGenService.productNo());
        product.setPrivacyLevel(pojo.getPrivacyLevel());

        product.setSupplierId(supplier.getId());
        product.setName(pojo.getName());
        product.setName_long(pojo.getName_long());

        product.setStatus(EnumProductStatus.draft);
        product.setCreated_at(LocalDateTime.now());
        product.setUpdated_at(LocalDateTime.now());
        product.setAcitve(true);
        product.setShippable(false);

        if(pojo.getPass().getRoyalties().stream()
                .filter(e->e.getValidate_way().equals(EnumValidateWay.offline_manual))
                .findAny().isPresent()){
            product.setValidate_way(EnumValidateWay.offline_manual);
        }else{
            product.setValidate_way(EnumValidateWay.none);

        }


        product.setRefund(pojo.getIs_refund());
        product.setDesc_long(pojo.getLong_desc());
        product.setRefund_note(pojo.getRefund().getNote());
        product.setNote(pojo.getNote());
        product.setTags_json((new Gson()).toJson(pojo.getTags()));
        product.setPaymentMethods_json((new Gson()).toJson(pojo.getPayment_methods()));
        product.setAvailability_type(pojo.getAvailability_type());
        product.setFree(pojo.getFree());
        product = productRepository.save(product);








            PassProduct passProduct = new PassProduct();
            passProduct.setProduct(product.getId());
            passProduct.setDuration(pojo.getPass().getCard_setting().getCard_validity_term());
            passProduct.setDurationUnit(pojo.getPass().getCard_setting().getCard_validity_term_unit());

            passProductRepository.save(passProduct);
            componentRightService.assingtoProduct(product,pojo.getPass().getRoyalties());



        if(pojo.getPrices() != null){

            List<PricingType> pricingTypes = priceService.getPriceType(product,pojo.getPrices());
            List<PricingType> priceTypeList = pricingTypeRepository.saveAll(pricingTypes);
            product.setDefault_price(priceTypeList.get(0).getId());
            product = productRepository.save(product);
        }


        if(pojo.getPrice() != null){
            PricingType pricingType = priceService.getPriceType(product,pojo.getPrice());
            pricingType = pricingTypeRepository.save(pricingType);
            product.setDefault_price(pricingType.getId());
            product = productRepository.save(product);

        }


        return Pair.with(product,supplier);
    }














    public Optional<Product> getById(long supplier_id, long product_id) {
        Product user = new Product();
        user.setId(product_id);
        user.setSupplierId(supplier_id);
        Example<Product> example = Example.of(user);

        return productRepository.findOne(example);
    }


    @Transactional
    public List<BookingRule> addBookingRule(Product product, List<BookingRuleVO> pojo) {
        bookingRuleRepository.deleteAllByProduct(product.getId());




        List<BookingRule> bookingRuleList = pojo.stream().map(e->{


            BookingRule bookingRuleVO = new BookingRule();

            bookingRuleVO.setProduct(product.getId());
            bookingRuleVO.setRangetype(e.getRangetype());
            bookingRuleVO.setBookable(e.getBookable());
            bookingRuleVO.setPriority(e.getPriority());

            if(e.getRangetype().equals(EnumAvailabilityRangetype.Date_range)){

                // Parses the date
/*                LocalDate dt1
                        = LocalDate
                        .parse("2018-11-03T12:45:30");*/
                bookingRuleVO.setBookings_from(e.getBookings_from());
                bookingRuleVO.setBookings_to(e.getBookings_to());

            }

            if(e.getRangetype().equals(EnumAvailabilityRangetype.Time_Range_$all_week$)){


                // Parses the date
/*                LocalDate dt1
                        = LocalDate
                        .parse("2018-11-03T12:45:30");*/

                bookingRuleVO.setTime_from(e.getTime_from());
                bookingRuleVO.setTime_to(e.getTime_to());

            }
                        if(e.getRangetype().equals(EnumAvailabilityRangetype.Range_of_months)){

                         //   bookingRuleVO.setMonth_from(e.getMonth_from());
                         //   bookingRuleVO.setMonth_to(e.getMonth_to());
                            bookingRuleVO.setMonths_json(new Gson().toJson(e.getMonths()));
                        }
            if(e.getRangetype().equals(EnumAvailabilityRangetype.Range_of_weeks)){

             //   bookingRuleVO.setWeek_from(e.getWeek_from());
                bookingRuleVO.setWeeks_json(new Gson().toJson(e.getWeeks()));
              //  bookingRuleVO.setWeek_to(e.getWeek_to());

            }
            return bookingRuleVO;
        }).collect(Collectors.toList());



        return bookingRuleRepository.saveAll(bookingRuleList);
    }


    @Transactional
    public Pair<Product,List<BookingRule>> editProductAvailabilityTab(Product product, List<BookingRuleVO> pojo, ProductEditResp.AvailabilityTab availabilityTabReq) {

        System.out.println("availabilityTabReq" + availabilityTabReq.toString());
        bookingRuleRepository.deleteAllByProduct(product.getId());

        Boolean availabilityRequired = availabilityTabReq.getAvailabilityRequired();
        EnumAvailabilityType availability_type = availabilityTabReq.getAvailability_type();

        product.setAvailabilityRequired(availabilityRequired);
        product.setAvailability_type(availability_type);

        product.setFixedPassExpiryDate(availabilityTabReq.getFixedPassExpiryDate());
        product.setPassValidForDays(availabilityTabReq.getPassValidForDays());
        product.setPassExpiry(availabilityTabReq.getPassExpiry());
        product.setPassCapacityType(availabilityTabReq.getPassCapacityType());
        product.setPassCapacity(availabilityTabReq.getPassCapacity() == null? availabilityTabReq.getPassesCapacity() : availabilityTabReq.getPassCapacity());

        product = productRepository.save(product);

        Product finalProduct = product;
        List<BookingRule> bookingRuleList = pojo.stream().map(e->{


            BookingRule bookingRuleVO = new BookingRule();

            bookingRuleVO.setProduct(finalProduct.getId());
            bookingRuleVO.setRangetype(e.getRangetype());
            bookingRuleVO.setBookable(e.getBookable());
            bookingRuleVO.setPriority(e.getPriority());

            if(e.getRangetype().equals(EnumAvailabilityRangetype.Date_range)){

                // Parses the date
/*                LocalDate dt1
                        = LocalDate
                        .parse("2018-11-03T12:45:30");*/
                bookingRuleVO.setBookings_from(e.getBookings_from());
                bookingRuleVO.setBookings_to(e.getBookings_to());

            }

            if(e.getRangetype().equals(EnumAvailabilityRangetype.Time_Range_$all_week$)){


                // Parses the date
/*                LocalDate dt1
                        = LocalDate
                        .parse("2018-11-03T12:45:30");*/

                bookingRuleVO.setTime_from(e.getTime_from());
                bookingRuleVO.setTime_to(e.getTime_to());

            }
            if(e.getRangetype().equals(EnumAvailabilityRangetype.Range_of_months)){

                //   bookingRuleVO.setMonth_from(e.getMonth_from());
                //   bookingRuleVO.setMonth_to(e.getMonth_to());
                bookingRuleVO.setMonths_json(new Gson().toJson(e.getMonths()));
            }
            if(e.getRangetype().equals(EnumAvailabilityRangetype.Range_of_weeks)){

                //   bookingRuleVO.setWeek_from(e.getWeek_from());
                bookingRuleVO.setWeeks_json(new Gson().toJson(e.getWeeks()));
                //  bookingRuleVO.setWeek_to(e.getWeek_to());

            }
            return bookingRuleVO;
        }).collect(Collectors.toList());



        List<BookingRule> bookingRules =  bookingRuleRepository.saveAll(bookingRuleList);

        return Pair.with(product,bookingRules);
    }


    //为产品增加 权益，一个产品可以有多个权益，这些权益 可以是别家的，也可以是自家的，
    // 增加权益，如果是别人家的，就要分润，自己家的不需要分润，所以就没有 RoyaltyRule了。
    public Component addComponent(Product product, ProductRestController.addComponentRightPojo pojo) {

        Optional<ComponentRight> optionalComponentRight = componentRightRepository.findById(pojo.getComponentRightId());

        optionalComponentRight.isPresent();
        ComponentRight componentRight = optionalComponentRight.get();



        Component component = new Component();


        //component.setO(pojo.getSourceType());


        component.setProduct(product.getId());
        component.setSupplier(componentRight.getSupplier());
        component.setRecipient(pojo.getRecipient());
        component.setComponentRight(pojo.getComponentRightId());
        component.setUnit_off(pojo.getQuantity());

        component =componentRepository.save(component);

        Component finalComponent = component;
        List<RoyaltyRule> royaltyRules = pojo.getRoyaltyRules().stream().map(x->{
            RoyaltyRule royaltyRule = new RoyaltyRule();
            royaltyRule.setWhenSettle(EnumWhenSettle.redeemed);
            royaltyRule.setRecipient(finalComponent.getRecipient());
            royaltyRule.setActive(true);
            return royaltyRule;
        }).collect(Collectors.toList());
        royaltyRules = royaltyRuleRepository.saveAll(royaltyRules);


        component.setRoyaltyRuleCount(royaltyRules.size());
        return componentRepository.save(component);


    }

    public Optional<Product> getByProductId(int product_id) {
        return null;
    }





    public RoyaltyRule addRoyaltyRuleToComponentRight(ComponentRight componentRight, RoyaltyRulePojo pojo) throws Exception {


        Optional<Component> component = componentRepository.findByProductAndComponentRightId(componentRight.getId(),componentRight.getId());

        if( component.isPresent()){

            RoyaltyRule royaltyRule = new RoyaltyRule();
           // royaltyRule.setSourceType(component.get().getSupplier());
            royaltyRule.setRecipient(pojo.getRecipient());
            royaltyRule.setComponentId(component.get().getId());
            royaltyRule.setComponentRight(component.get().getComponentRightId());
            royaltyRule.setProductId(component.get().getProduct());
            royaltyRule.setWhenSettle(EnumWhenSettle.payed);
            royaltyRule.setCategory(pojo.getCategory());
            royaltyRule.setAmount(pojo.getAmount());
            royaltyRule.setPercent(pojo.getPercent());

            royaltyRule = royaltyRuleRepository.save(royaltyRule);
            return royaltyRule;
        }

        throw new Exception();

    }

    public Optional<ComponentRight> getByComponentRightForProduct(int product_id, int component_rights_id) {
        return null;
    }


    public BookingQuestion addBookingQuestion(Product product, BookingQuestionPojo pojo) {
        BookingQuestion bookingQuestion = new BookingQuestion();
        bookingQuestion.setProductId(product.getId());

        bookingQuestion = bookingQuestionRepository.save(bookingQuestion);

        return bookingQuestion;
    }

    public List<BookingQuestion> listQuestions(Product product) {
        return bookingQuestionRepository.findByProductId(product.getId());
    }



    public Product createProductGiftVoucher(Supplier supplier, ProductGiftVoucherPojo pojo) {


        Product product = new Product();

        // product.setN(pojo.getName());
        product.setType(pojo.getType());
        product.setSupplierId(supplier.getId());

        product = productRepository.save(product);

        Product finalProduct = product;
        List<Attribute> attributes = pojo.getAttributes().stream().map(x->{
            Attribute attribute = new Attribute();
            attribute.setName(x.getName());
            attribute.setDescription(x.getDescription());
            attribute.setObjectId(finalProduct.getId());
            return attribute;
        }).collect(Collectors.toList());

        attributeRepository.saveAll(attributes);



        return product;

    }

    @Transactional
    public Product editProductInfo(Product product, ProductEditResp.InfoTab pojo) {

        // product.setN(pojo.getName());


/*        productResp.setFeatureTags(Arrays.asList(FeatureTag.of("","gem-o","45分~1小时"),
                FeatureTag.of("","gift-o","接送")));*/

        product.setFeatureTags_json(new Gson().toJson(pojo.getFeatureTags()));
        product.setName(pojo.getName());
        product.setName_long(pojo.getName_long());
        product.setDesc_long(pojo.getDesc_long());
        product.setDesc_short(pojo.getDesc_short());
        product.setNote(pojo.getNote());
        product.setShow_note(pojo.getShow_note());
        product.setPrivacyLevel(pojo.getPrivacyLevel());
        product.setFree(pojo.getFree());

      //  product.setAvailability_type(pojo.getAvailabilityType());

        product.setTags_json((new Gson()).toJson(pojo.getTags()));

        product = productRepository.save(product);


        Product finalProduct = product;
        pojo.getImages().stream().forEach(photoResp->{

            if(photoResp.getCode()!=null){

                fileStorageService.updateFromTempDocument(finalProduct.getCode(),photoResp,EnumDocumentType.product_photos);

            }

        });
        if(pojo.getVideo()!=null && pojo.getVideo().getCode()!= null){
            fileStorageService.updateFromTempDocumentCode(finalProduct.getCode(), EnumDocumentType.product_video,pojo.getVideo().getCode());
        }

        if(pojo.getThumb()!=null && pojo.getThumb().getCode()!= null){
            fileStorageService.updateFromTempDocumentCode(finalProduct.getCode(), EnumDocumentType.product_thumb,pojo.getThumb().getCode());
        }

        return product;
    }





    @Transactional
    public Product editProductBundleTab(Product product, ProductEditResp.BundleTab pojo) {


        Product finalProduct = product;
        List<ProductBundle> productBundleList = pojo.getBundles().stream().map(e->{
            Product burdle = productRepository.findById(e.getId()).get();
            ProductBundle productBundle = new ProductBundle();
            productBundle.setProduct(finalProduct);
            productBundle.setBurdle(burdle.getId());
            return productBundle;
        }).collect(Collectors.toList());
        product.getBundles().stream().forEach(e->{
            System.out.println("--保存的元素 啊啊 ----------"+ e.getBurdle());
        });

        product.getBundles().clear();

       product.getBundles().addAll(productBundleList);

        productBundleList.stream().forEach(e->{
            System.out.println("--getBurdle----------"+ e.getBurdle());
        });



        product = productRepository.save(product);
        return product;



    }
    @Transactional
    public Product editProductShippingTab(Product product, ProductEditResp.ShippingTap pojo) {


        if(pojo.getDeliveryFormats()!= null){
            product.setDeliveryFormats_json(GSON.toJson(pojo.getDeliveryFormats()));
        }


        if(pojo.getShipping_rates()!= null){
            product.setShipping_rates_json(GSON.toJson(pojo.getShipping_rates()));
        }



        product.setShippable(pojo.isShippable());

        product.setShippingAddressCollection(pojo.getShippingAddressCollection());
        product.setShipping_rate(pojo.getShipping_rate());



        product = productRepository.save(product);
        return product;



    }
    @Transactional
    public Product editProductAboutTab(Product product, ProductEditAboutTabPojo pojo) {


        List<Long> has = pojo.getAttributes().stream().map(e->e.getId()).collect(Collectors.toList());


        List<Attribute> attributes = attributeRepository.findAllByObjectCode(product.getCode());

        Map<Long,Attribute> attributeMap = attributes.stream().collect(Collectors.toMap(e->e.getId(),e->e));

        attributeRepository.deleteAllById(attributes.stream().filter(e->!has.contains(e.getId())).map(e->e.getId()).collect(Collectors.toList()));
        List<Attribute> attributeList = pojo.getAttributes().stream().map(e->{

            Attribute attribute = attributeMap.getOrDefault(e.getId(), new Attribute());
            attribute.setDescription(e.getText());
            attribute.setFeatureType(e.getType());
            attribute.setObjectCode(product.getCode());
            attribute.setName(e.getName());
            return attribute;

        }).collect(Collectors.toList());


        attributeRepository.saveAll(attributeList);

        return product;



    }
    @Transactional
    public Product editProductRuleTab(Product product, ProductResp.RuleTap pojo) {


        Map<Long,Term> longTermMap = termRepository.findAllById(   pojo.getTerms().stream().map(e->e.getId()).collect(Collectors.toList())).stream().collect(Collectors.toMap(e->e.getId(),e->e));
        termRepository.saveAll(pojo.getTerms().stream().map(e->{
            Term term = longTermMap.getOrDefault(e.getId(),new Term());
            term.setText(e.getText());
            term.setType(e.getType());
            term.setRelatedObjectId(product.getId());
            term.setRelatedObjectType(EnumRelatedObjectType.product);
            term.setRequiresExplicitConsent(e.getRequiresExplicitConsent());
            return term;
        }).collect(Collectors.toList()));



        product.setRule_confirmationType(pojo.getConfirmationType());
        product.setRestriction_maxQuantity(pojo.getRestriction().getMaxQuantity());
        product.setRestriction_minQuantity(pojo.getRestriction().getMinQuantity());
        product.setRestriction_passenger_identity_documents_required(pojo.getRestriction().getIdRequired());

       // product.setRestriction_passenger_identity_documents_required(pojo.getRestriction().getPassenger_identity_documents_required());

        productRepository.save(product);
        return product;



    }
    @Transactional
    public Product editProductRightTab(Product product, ProductEditComponentTabPojo pojo) {


        componentRightService.assingtoProduct(product,pojo.getRoyalties());

        if(pojo.getRoyalties().stream()
                .filter(e->e.getValidate_way().equals(EnumValidateWay.offline_manual))
                .findAny().isPresent()){

            product.setValidate_way(EnumValidateWay.offline_manual);
        }else{
            product.setValidate_way(EnumValidateWay.none);

        }




        productRepository.save(product);
        return product;



    }

    @Transactional
    public Product editProductComponentTab(Product product, ProductEditComponentTabPojo pojo) {

        List<PricingType> pricingTypes = pricingTypeRepository.findByProductId(product.getId());


        List<Long> contain = pojo.getPrices().stream().filter(e->e.getId() != null).map(e->e.getId()).collect(Collectors.toList());

        Map<Long, PricingType> pricingTypeMap = pricingTypes
                .stream().collect(Collectors.toMap(e->e.getId(),e->e));
        ;

        pricingTypeRepository.deleteAllById(pricingTypes.stream().filter(e->!contain.contains(e.getId())).map(e->e.getId()).collect(Collectors.toList()));



      //      componentRightService.assingtoProduct(product,pojo.getRoyalties());

       // pricingTypeRepository.deleteAllByProductId(product.getId());
            List<PricingType>  priceTyps = pojo.getPrices().stream().map(x->{

                PricingType pricingType = pricingTypeMap.getOrDefault(x.getId(),new PricingType());

                pricingType = priceService.updatePriceType(product,pricingType,x);
                return pricingType;
            }).collect(Collectors.toList());

            List<PricingType> priceTypeList = pricingTypeRepository.saveAll(priceTyps);









        product.setDefault_price(priceTypeList.get(0).getId());

        productRepository.save(product);
        return product;



    }
    @Transactional
    public Product editQuestionTab(Product product, ProductEditResp.QuestionTap pojo) {


        product.setPickupQuestions(Arrays.asList(new BookingQuestion()));
        product.setDropoffQuestions(Arrays.asList(new BookingQuestion()));
        product.setQuestions(Arrays.asList(new BookingQuestion()));

        productRepository.save(product);
        return product;

    }

    @Transactional
    public Product editProducPaymentTab(Product product, ProductEditPaymentPojo pojo) {


        product.setPaymentMethods_json((new Gson()).toJson(pojo.getPayment_methods()));

        productRepository.save(product);
        return product;

    }



    @Transactional
    public Triplet<Boolean,Product,List<Map>> editPublishTab(Product product, ProductEditPaymentPojo pojo) {

        List<Map> errors = new ArrayList<>();

        if(ObjectUtils.isEmpty(product.getDesc_short())){
            errors.add(Map.of("id","产品 短描述 不能为空",
                    "text","产品 短描述 不能为空"));
        }

        Optional<PricingType> pricingType = priceService.getDefault_price(product);

        if(pricingType.isEmpty()){
            errors.add(Map.of("id","至少要添加一个价格",
                    "text","至少要添加一个价格"));
        }


        if(ObjectUtils.isEmpty(product.getPaymentMethods_json())){

            errors.add(Map.of("id","需要添加支付方式",
                    "text","需要添加支付方式"));
        }

        if(ObjectUtils.isEmpty(product.getAvailability_type())){

            errors.add(Map.of("id","需要设置可用性",
                    "text","需要设置可用性"));


        }else{


            switch (product.getAvailability_type()){
                case PASS -> {

                    if(ObjectUtils.isEmpty(product.getPassExpiry())){

                        errors.add(Map.of("id","当 可用性是通行证时， 需要 设置 通行证国企时间 ",
                                "text","当 可用性是通行证时， 需要 设置 通行证国企时间"));

                    }else{

                        switch (product.getPassExpiry()){
                            case FIXED_DATE -> {
                                if(ObjectUtils.isEmpty(product.getFixedPassExpiryDate())){

                                    errors.add(Map.of("id","当 可用性是通行证时， 需要 设置 通行证国企时间 ",
                                            "text","当 可用性是通行证时， 需要 设置 通行证国企时间"));
                                }

                            }

                            case NEVER -> {

                            }
                            case RELATIVE_DATE -> {
                                if(ObjectUtils.isEmpty(product.getPassValidForDays())){
                                    errors.add(Map.of("id","当 可用性是通行证时， 需要 设置 通行证国企时间 ",
                                            "text","当 可用性是通行证时， 需要 设置 通行证国企时间"));
                                }
                            }
                            default -> throw new IllegalStateException("Unexpected value: " + product.getPassExpiry());
                        }


                    }
                }
            }



        }
        ;



        String error = componentRightService.checkPublishProduct(product);
        if(ObjectUtils.isNotEmpty(error)){
            errors.add(Map.of("id","至少要添加一个权益",
                    "text","至少要添加一个权益"));
        }




        if(errors.isEmpty()){
            product.setStatus(EnumProductStatus.active);
            productRepository.save(product);
            return Triplet.with(true,product,errors);
        }else{
            return Triplet.with(false,product,errors);
        }




    }
    @Transactional
    public MovieProduct editMovieTabPerformance(MovieProduct product, ProductEditResp.MovieTab pojo) {

/*
        List<ZonePricing> zonePricingList =zonePricingRepository.findAllByZoneIn(pojo.getZonePricings()
                .stream()
                .map(e->e.getZone()).collect(Collectors.toList()));*/



/*
        Map<Long,List<ZonePricing>> zonePricingListMap =zonePricingRepository.findAllByZoneIn(pojo.getZonePricings()
                .stream()
                .map(e->e.getZone()).collect(Collectors.toList())).stream().collect(Collectors.groupingBy(e->e.getZone()));
*/

        product.setMovie(pojo.getMovie());
        product.setSeatingLayout(pojo.getSeatingLayout());
        product.setTheatre(pojo.getTheatre());


        product = movieProductRepository.save(product);



        return product;

    }
    @Transactional
    public MovieProduct editMovieTab(MovieProduct product, ProductEditResp.MovieTab pojo) {


        skuRepository.deleteAllByMovieProduct(product);

/*        List<ZonePricing> zonePricingList =zonePricingRepository.findAllByZoneIn(pojo.getZonePricings()
                .stream()
                .map(e->e.getZone()).collect(Collectors.toList()));*/



/*
        Map<Long,List<ZonePricing>> zonePricingListMap =zonePricingRepository.findAllByZoneIn(pojo.getZonePricings()
                .stream()
                .map(e->e.getZone()).collect(Collectors.toList())).stream().collect(Collectors.groupingBy(e->e.getZone()));
*/

/*        product.setMovie(pojo.getMovie());
        product.setSeatingLayout(pojo.getSeatingLayout());
        product.setTheatre(pojo.getTheatre());*/


        MovieProduct finalProduct = product;
        product.setZonePricings(pojo.getZonePricings().

                stream().
    /*            filter(e->{
                  return zonePricingList.stream().filter(ee->ee.getZone()== e.getZone() && ee.getPricingType()== e.getPricingType())
                          .findAny().isEmpty();
                }).*/
                map(e->{
                    Sku sku = new Sku();
                    sku.setPricingType(e.getPricingType());
                    sku.setZone(e.getZone());
                    sku.setProduct(finalProduct.getProduct());
                    sku.setMovieProduct(finalProduct);

            return sku;
        }).collect(Collectors.toList()));


        product = movieProductRepository.save(product);
/*
        if(pojo.getCover()!=null && pojo.getCover().getCode()!= null){
            fileStorageService.updateFromTempDocumentCode(product.getUuid(), EnumDocumentType.card_cover,pojo.getCover().getCode());
        }
*/

        return product;

    }
    @Transactional
    public PassProduct editPassTab(PassProduct product, ProductEditResp.PassTab pojo) {

        product.setUuid(UUID.randomUUID().toString());
        product.setDuration(pojo.getDuration());
        product.setDurationUnit(pojo.getDurationUnit());
        product.setLabel(pojo.getLabel());
        product.setActiveExpiryDuration(pojo.getActiveExpiryDuration());
        product.setActiveExpiryDurationUnit(pojo.getActiveExpiryDurationUnit());


        product = passProductRepository.save(product);
        if(pojo.getCover()!=null && pojo.getCover().getCode()!= null){
            fileStorageService.updateFromTempDocumentCode(product.getUuid(), EnumDocumentType.card_cover,pojo.getCover().getCode());
        }

        return product;

    }



    public Pair<Product, Supplier> editProduct(Product product, Supplier supplier, ProductEditPojo pojo, List<Campaign> campaigns) {

        // product.setN(pojo.getName());



        product.setName(pojo.getName());
        product.setName_long(pojo.getName_long());

        product.setUpdated_at(LocalDateTime.now());



        product = productRepository.save(product);

        List<CampaignAssignToTourProduct> campaignAssignToTourProducts = campaignAssignToTourProductRepository.findByProduct(product.getId());
        campaignAssignToTourProductRepository.deleteAll(campaignAssignToTourProducts);

        Product finalProduct1 = product;
        if(product.getType().equals(EnumProductType.Daytour)){
            Optional<Tour> optional = tourRepository.findByProduct(product.getId());
            Tour finalTour = optional.get();
            List<CampaignAssignToTourProduct> list =  campaigns.stream().map(x->{
                CampaignAssignToTourProduct campaignAssignToTourProduct = new CampaignAssignToTourProduct();
                campaignAssignToTourProduct.setCampaign(x.getId());
                campaignAssignToTourProduct.setCampaign_code(x.getCode());
                campaignAssignToTourProduct.setProduct(finalProduct1.getId());
                campaignAssignToTourProduct.setTourId(finalTour.getId());
                return campaignAssignToTourProduct;
            }).collect(Collectors.toList());

            list = campaignAssignToTourProductRepository.saveAll(list);
        }


        return Pair.with(product,supplier);
    }



    public List<EnumProductType> getSupportProductType() {


        return Arrays.asList(EnumProductType.Daytour,
                EnumProductType.Pass,
                EnumProductType.Attraction);
    }

    public List<BookingRuleVO> addBookingRuleWithValidate(Product product, List<BookingRulePojoFuck> pojo) {



        List<BookingRulePojo> bookingRuleVOList = pojo.stream().map(e->{
            BookingRulePojo bookingRuleVO = new BookingRulePojo();
            bookingRuleVO.setBookable(e.getBookable());
            bookingRuleVO.setPriority(e.getPriority());
            bookingRuleVO.setRangetype(e.getType());

            if(e.getType().equals(EnumAvailabilityRangetype.Date_range)){



                // Parses the date
/*                LocalDate dt1
                        = LocalDate
                        .parse("2018-11-03T12:45:30");*/
                bookingRuleVO.setFrom((String)(e.getValues().get(0)));
                bookingRuleVO.setTo((String)(e.getValues().get(1)));

            }
            if(e.getType().equals(EnumAvailabilityRangetype.Range_of_months)){


                bookingRuleVO.setMonths((e.getValues().stream().map(month->{
                    return (String)month;
                }).collect(Collectors.toList())));
                //   bookingRuleVO.setMonth_to(Month.valueOf(e.getTo()));
            }
            if(e.getType().equals(EnumAvailabilityRangetype.Time_Range_$all_week$)){


                // Parses the date
/*                LocalDate dt1
                        = LocalDate
                        .parse("2018-11-03T12:45:30");*/

                bookingRuleVO.setFrom((String)e.getValues().get(0));
                bookingRuleVO.setTo((String)e.getValues().get(1));

            }

            if(e.getType().equals(EnumAvailabilityRangetype.Range_of_weeks)){

                bookingRuleVO.setWeeks((e.getValues().stream().map(month->{
                    return (String)month;
                }).collect(Collectors.toList())));


            }
            return bookingRuleVO;
        }).collect(Collectors.toList());





        List<BookingRuleVO> bookingRuleVOList_ = bookingRuleVOList.stream().map(e->{
            BookingRuleVO bookingRuleVO = new BookingRuleVO();
            bookingRuleVO.setBookable(e.getBookable());
            bookingRuleVO.setPriority(e.getPriority());
            bookingRuleVO.setRangetype(e.getRangetype());
            if(e.getRangetype().equals(EnumAvailabilityRangetype.Date_range)){


                // Parses the date
/*                LocalDate dt1
                        = LocalDate
                        .parse("2018-11-03T12:45:30");*/
                bookingRuleVO.setBookings_from(LocalDate
                        .parse(e.getFrom()));
                bookingRuleVO.setBookings_to(LocalDate
                        .parse(e.getTo()));

            }
            if(e.getRangetype().equals(EnumAvailabilityRangetype.Range_of_months)){


                bookingRuleVO.setMonths((e.getMonths().stream().map(month->{
                    return Month.valueOf(month);
                }).collect(Collectors.toList())));
                //   bookingRuleVO.setMonth_to(Month.valueOf(e.getTo()));
            }
            if(e.getRangetype().equals(EnumAvailabilityRangetype.Time_Range_$all_week$)){


                // Parses the date
/*                LocalDate dt1
                        = LocalDate
                        .parse("2018-11-03T12:45:30");*/

                bookingRuleVO.setTime_from(LocalTime
                        .parse(e.getFrom()));
                bookingRuleVO.setTime_to(LocalTime
                        .parse(e.getTo()));

            }

            if(e.getRangetype().equals(EnumAvailabilityRangetype.Range_of_weeks)){

                bookingRuleVO.setWeeks((e.getWeeks().stream().map(month->{
                    return DayOfWeek.valueOf(month);
                }).collect(Collectors.toList())));


            }
            return bookingRuleVO;
        }).collect(Collectors.toList());

        return bookingRuleVOList_;//

    }

    public Quartet<Zone,Movie,Theatre,Sku> showTime(long sku_id) {
        Optional<Sku> sku_op = skuRepository.findById(sku_id);
        Sku  sku = sku_op.get();

        Optional<MovieProduct> passProduct = movieProductRepository.findByProduct(sku.getProduct());

        MovieProduct movieProduct = passProduct.get();



        Optional<Zone> zoneOptional = zoneRepository.findById(sku.getZone());
        Optional<Movie>  movieOptional = movieRepository.findById(movieProduct.getMovie());


        Optional<Theatre> theatreOptional = theatreRepository.findById(movieProduct.getTheatre());


        return Quartet.with(zoneOptional.get(),movieOptional.get(),theatreOptional.get(),sku);

    }




    public Pair<Product,Attraction> attract(long sku_id) {


/*        Optional<PricingType> sku_op = pricingTypeRepository.findById(sku_id);
        PricingType  sku = sku_op.get();*/
        Optional<Product> passProduct = productRepository.findById(sku_id);

        Optional<Attraction> attractionOptional = attractionRepository.findById(passProduct.get().getTypeTo());

        Attraction attraction = attractionOptional.get();


/*
        Optional<Zone> zoneOptional = zoneRepository.findById(sku.getZone());
        Optional<Movie>  movieOptional = movieRepository.findById(movieProduct.getMovie());

        Optional<Theatre> theatreOptional = theatreRepository.findById(movieProduct.getTheatre());*/

        return Pair.with(passProduct.get(),attraction);


    }

    public List<Product> getActive() {

        List<Product> productList = productRepository.findAllByStatusAndPrivacyLevel(EnumProductStatus.active,EnumPrivacyLevel.public_);
        return productList;
    }


    public List<Product> find(ProductQueryfieldsCriteria searchQuery) {


        ProductSpecification spec = new ProductSpecification(searchQuery); //, "code", "claim_note"
        List<Product> productList = productRepository.findAll(where(spec));
        return productList;
    }
}
