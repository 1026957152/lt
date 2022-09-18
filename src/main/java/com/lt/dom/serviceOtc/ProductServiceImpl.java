package com.lt.dom.serviceOtc;

import com.google.gson.Gson;
import com.lt.dom.controllerOct.ProductRestController;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcReq.product.ProductGiftVoucherPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.vo.BookingRuleVO;
import org.javatuples.Pair;
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


@Service
public class ProductServiceImpl {


    @Autowired
    private TourRepository tourRepository;


    @Autowired
    private PricingTypeRepository pricingTypeRepository;

    @Autowired
    private ProductRepository productRepository;

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


    public Page<Product> listProduct(Supplier supplier, Pageable pageable) {

        return productRepository.findBySupplierId(supplier.getId(),pageable);
    }


    Gson gson = new Gson();




    @Transactional
    public Pair<Product,Supplier> createProduct(Supplier supplier, ProductPojo pojo, List<Campaign> campaigns) {
        Product product = new Product();

       // product.setN(pojo.getName());
        product.setType(pojo.getType());
        product.setCode(idGenService.productNo());
        product.setSupplierId(supplier.getId());
        product.setName(pojo.getName());
        product.setName_long(pojo.getName_long());
        product.setLong_desc(pojo.getLong_desc());

        product.setStatus(EnumProductStatus.draft);
        product.setCreated_at(LocalDateTime.now());
        product.setUpdated_at(LocalDateTime.now());
        product.setAcitve(true);
        product.setShippable(false);

        product.setAvailability_type(pojo.getAvailability_type());

        product.setShippable(pojo.getShippable());
        product.setShipping_rate(pojo.getShipping_rate());
        ;
        product.setRefund(pojo.getIs_refund());
        product.setLong_desc(pojo.getLong_desc());
        product.setRefund_note(pojo.getRefund().getNote());
        product.setNote(pojo.getNote());
        product.setTags_json((new Gson()).toJson(Arrays.asList(EnumProductTag.支持一卡通,EnumProductTag.支持一卡通)));

        product.setPaymentMethods_json((new Gson()).toJson(pojo.getPayment_methods()));
        product = productRepository.save(product);





        if(product.getType().equals(EnumProductType.Attraction)){


            Optional<Attraction> attractionOptional = attractionRepository.findById(pojo.getAttraction().getId());
            if(attractionOptional.isEmpty()){
                throw new BookNotFoundException(Enumfailures.not_found,"找不到景区");
            }
            Attraction attraction = attractionOptional.get();

            product.setTypeTo(attraction.getId());

            product.setDefault_price(0L);
            product = productRepository.save(product);




            if(pojo.getRoyalties() != null){


                componentRightService.assingtoProduct(product,pojo.getRoyalties());

            }
       /*     if(pojo.getRights() != null){
                List<ComponentRight> componentRights = componentRightRepository.findAllById(pojo.getRights());


                if((componentRights.size() == 0)  || (componentRights.size() != pojo.getRights().size())){

                    throw new BookNotFoundException(Enumfailures.not_found,pojo.getRights()+ "找不到权益，或权益为空");
                }
                Product finalProduct1 = product;



                List<Component> list =  componentRights.stream().map(x->{
                    Component component = new Component();
                    component.setUnit_off(x.getQuantity());
                    component.setDuration(x.getDuration());
                    component.setComponentRight(x.getId());
                    component.setProduct(finalProduct1.getId());
                    component.setSupplier(x.getSupplier());
                    component.setValidate_way(x.getV());
                    return component;
                }).collect(Collectors.toList());

                list = componentRepository.saveAll(list);

            }
*/



        }



        if(product.getType().equals(EnumProductType.Pass)){


            componentRightService.assingtoProduct(product,pojo.getPass().getRoyalties());


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
        Product finalProduct = product;
        if(pojo.getAttributes() != null){
            List<Attribute> attributes = pojo.getAttributes().stream().map(x->{
                Attribute attribute = new Attribute();
                attribute.setName(x.getName());
                attribute.setDescription(x.getDescription());
                attribute.setObjectId(finalProduct.getId());
                return attribute;
            }).collect(Collectors.toList());

            attributeRepository.saveAll(attributes);
        }







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
                    component.setUnit_off(com.getQuantity());
                    component.setDuration(com.getDuration());
                    component.setComponentRight(com.getId());
                    component.setPriceingType(pricingType.getId());
                    component.setSupplier(com.getSupplier());

                    //  component.setRoyaltyPercent(royalty.getPercent());

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




        product = productRepository.save(product);

        return Pair.with(product,supplier);
    }













    public Pair<Product,Supplier> createPass(Supplier supplier, ProductPojo pojo, List<ComponentRight> componentRightList_) {
        Product product = new Product();

        product.setType(pojo.getType());
        product.setCode(idGenService.productNo());
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
        product.setLong_desc(pojo.getLong_desc());
        product.setRefund_note(pojo.getRefund().getNote());
        product.setNote(pojo.getNote());
        product.setTags_json((new Gson()).toJson(Arrays.asList(EnumProductTag.支持一卡通,EnumProductTag.支持一卡通)));

        product.setPaymentMethods_json((new Gson()).toJson(pojo.getPayment_methods()));
        product = productRepository.save(product);








        if(product.getType().equals(EnumProductType.Pass)){
            componentRightService.assingtoProduct(product,pojo.getPass().getRoyalties());
        }



        if(pojo.getPrices() != null){

            List<PricingType> pricingTypes = priceService.getPriceType(product,pojo.getPrices());
            List<PricingType> priceTypeList = pricingTypeRepository.saveAll(pricingTypes);
        }


        if(pojo.getPrice() != null){
            PricingType pricingType = priceService.getPriceType(product,pojo.getPrice());
            pricingType = pricingTypeRepository.save(pricingType);
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


        Optional<Component> component = componentRepository.findByProductIdAndComponentRightId(componentRight.getId(),componentRight.getId());

        if( component.isPresent()){

            RoyaltyRule royaltyRule = new RoyaltyRule();
           // royaltyRule.setSourceType(component.get().getSupplier());
            royaltyRule.setRecipient(pojo.getRecipient());
            royaltyRule.setComponentId(component.get().getId());
            royaltyRule.setComponentRight(component.get().getComponentRightId());
            royaltyRule.setProductId(component.get().getProductId());
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

    public Comment addComment(Product product, CommentPojo commentPojo) {

        Comment comment = new Comment();
        comment.setText(commentPojo.getText());
        comment = commentRepository.save(comment);
        return comment;
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
                bookingRuleVO.setTo((String)e.getValues().get(0));

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
}
