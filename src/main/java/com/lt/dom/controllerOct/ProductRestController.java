package com.lt.dom.controllerOct;

import com.google.gson.Gson;
import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.serviceOtc.product.AttractionTicketServiceImpl;
import com.lt.dom.serviceOtc.product.BusTicketServiceImpl;
import com.lt.dom.serviceOtc.product.CityPassServiceImpl;
import com.lt.dom.serviceOtc.product.ShowtimeTicketServiceImpl;
import com.lt.dom.vo.AvailabilityCalendarVO;
import com.lt.dom.vo.BookingRuleVO;
import com.lt.dom.vo.NegotiatedPricingType;
import com.lt.dom.vo.Tip;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class ProductRestController {
    @Autowired
    private ReviewCountServiceImpl reviewCountService;

    @Autowired
    private BusTicketServiceImpl busTicketService;

    @Autowired
    private CityPassServiceImpl cityPassService;
    @Autowired
    private AttractionTicketServiceImpl attractionTicketService;

    @Autowired
    private ShowtimeTicketServiceImpl showtimeTicketService;



    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private CampaignAssignToTourProductRepository campaignAssignToTourProductRepository;

    @Autowired
    private TempDocumentRepository tempDocumentRepository;
    @Autowired
    private FileStorageServiceImpl fileStorageService;

    @Autowired
    private ValueListServiceImpl valueListService;


    @Autowired
    private BookingRuleRepository bookingRuleRepository;
    @Autowired
    private AvailabilityServiceImpl availabilityService;

    @Autowired
    private ShippingRateRepository shippingRateRepository;
    @Autowired
    private ExtraRepository extraRepository;



    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private ContactServiceImpl contactService;


    @Autowired
    private PassProductRepository passProductRepository;

    @Autowired
    private MovieProductRepository movieProductRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ZoneRepository zoneRepository;
    @Autowired
    private TheatreRepository theatreRepository;


    @Autowired
    private SeatingLayoutRepository seatingLayoutRepository;


    @Autowired
    private PricingTypeRepository pricingTypeRepository;
    @Autowired
    private RatePlanRepository ratePlanRepository;

    @Autowired
    private PartnerShareRatePlanRepository partnerShareRatePlanRepository;

    @Autowired
    private YoulikeServiceImpl youlikeService;
    @Autowired
    private TermServiceImpl termService;

    @Autowired
    private TermRepository termRepository;


    @Autowired
    private PriceServiceImpl priceService;


    @Autowired
    private PartnerServiceImpl partnerService;




    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private ComponentRightRepository componentRightRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ValidateServiceImpl validateService;
    @Autowired
    private RoyaltyRuleRepository royaltyRuleRepository;

    @Autowired
    private ValueListRepository valueListRepository;

    @Autowired
    private ValueListItemRepository valueListItemRepository;

    @Autowired
    private CommentServiceImpl commentService;



    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/products/{PRODUCT_ID}/Page_editProduct", produces = "application/json")
    public EntityModel<ProductEditResp> Page_editProduct(@PathVariable long PRODUCT_ID) {

        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }

        Product product = validatorOptional.get();




        ProductEditResp productResp = null;
        if(product.getType().equals(EnumProductType.Daytour)){

            Optional<Tour> tour = tourRepository.findByProduct(product.getId());

            List<CampaignAssignToTourProduct> campaignAssignToTourProducts = campaignAssignToTourProductRepository.findByTourId(tour.get().getId());

            List<Campaign> campaigns = campaignRepository.findAllById(campaignAssignToTourProducts.stream().map(x->x.getCampaign()).collect(Collectors.toList()));
            productResp = ProductEditResp.dayTourFrom(product,tour.get(),campaigns);


        }else{
            productResp = new ProductEditResp();
            if(product.getType().equals(EnumProductType.Attraction)){
/*
                Optional<Attraction> attractionOptional = attractionRepository.findById(product.getTypeTo());


                if(attractionOptional.isPresent()){

                    productResp.setAttraction(attractionOptional.get());
                }
*/


            }


            ProductEditResp.InfoTab infoTab = ProductEditResp.InfoTab.from(product);
            infoTab.setParameterList(
                    Map.of(
                            "privacy_level_list",EnumPrivacyLevel.List(),
                            "tag_list",EnumTags.List(),
                            "feature_tag_list",EnumFeatureTag.List(),
                            "payment_method_list",   EnumPayChannel.from(Arrays.asList(EnumPayChannel.wx,EnumPayChannel.balance)))
            );

            EntityModel entityModel_infoTab = EntityModel.of(infoTab);
            entityModel_infoTab.add(linkTo(methodOn(ProductRestController.class).editProductInfo(product.getId(),null)).withRel("edit"));

            infoTab.setImages(fileStorageService.loadDocumentsWithCodeEdit( EnumDocumentType.product_photos,product.getCode()));
            infoTab.setVideo(fileStorageService.loadDocumentWithCodeEdit( EnumDocumentType.product_video,product.getCode()));
            infoTab.setThumb(fileStorageService.loadDocumentWithCodeEdit( EnumDocumentType.product_thumb,product.getCode()));
            productResp.setInfoTab(entityModel_infoTab);















            ProductEditResp.CommentTab commentTab = ProductEditResp.CommentTab.from(commentService.list(product));

            EntityModel entityModel_commentTab = EntityModel.of(commentTab);
            entityModel_commentTab.add(linkTo(methodOn(CommentRestController.class).createComment(product.getId(),null)).withRel("create"));

            productResp.setCommentTab(entityModel_commentTab);



            //           List<PricingType> pricingTypes = pricingTypeRepository.findByProductId(product.getId());




  /*          productResp.setPriceTypes(pricingTypes.stream().map(e->{

                PricingTypeResp pricingTypeResp = PricingTypeResp.from(e);

                EntityModel entityModel1 = EntityModel.of(pricingTypeResp);

                entityModel1.add(linkTo(methodOn(BookingRestController.class).Page_createBookingFromPriceType(e.getId())).withRel("Page_createBooking"));

                return entityModel1;
            }).collect(Collectors.toList()));


*/

        }




        Optional<Supplier> supplierOptional= supplierRepository.findById(product.getSupplierId());
        Supplier supplier = supplierOptional.get();



        List<ComponentRight> componentRightList = componentRightRepository.findAllBySupplier(supplier.getId());

        List<RoyaltyRule> royaltyRuleList = royaltyRuleRepository.findAllByComponentRightIn(componentRightList.stream().map(e->e.getId()).collect(Collectors.toList()));

        Map<Long,List<RoyaltyRule>> royaltyRuleListMap =  royaltyRuleList.stream().collect(Collectors.groupingBy(e->e.getComponentRight()));

        List<Attraction> attractionList = attractionRepository.findAllBySupplier(supplier.getId());






        productResp.setParameterList(
                Map.of(
                        "attraction_list", AttractionResp.EnumList(attractionList)
                      //  "range_type_list",EnumAvailabilityRangetype.from(),
                     //   "component_right_list",ComponentRight.List(componentRightList,supplier,royaltyRuleListMap),
                     //   "daytour_days_list", EnumDaytourDays.from(),
                    //    "product_type_list",EnumProductType.from(productService.getSupportProductType()),
                   //     "price_type_list", EnumProductPricingType.from(),
                    //    "payment_method_list",   EnumPayChannel.from(Arrays.asList(EnumPayChannel.wx,EnumPayChannel.balance)))
        ));




        List<BookingRule> bookingRuleList = bookingRuleRepository.findByProduct(product.getId());
        ProductEditResp.AvailabilityTab availability =ProductEditResp.AvailabilityTab.from(product,bookingRuleList);
        EntityModel entityModel_a = EntityModel.of(availability);
        availability.setParameterList(
                Map.of(
                        "range_type_list",EnumAvailabilityRangetype.simplefrom(),
                        "pass_expiry_type_list", EnumPassExpiry.EnumList(),
                        "pass_capacity_type_list",EnumPassCapacity.EnumList(),

                        "month_list",EnumAvailabilityRangetype.Month(),
                        "week_list",EnumAvailabilityRangetype.DayOfWeek(),

                        "availability_type_list",EnumAvailabilityType.EnumList()
                        //  "daytour_days_list", EnumDaytourDays.from()
                ));
        entityModel_a.add(linkTo(methodOn(ProductRestController.class).addBookingRule(product.getId(),null)).withRel("update"));
        entityModel_a.add(linkTo(methodOn(ProductRestController.class).editProductAvailabilityTab(product.getId(),null)).withRel("edit"));
        productResp.setAvailabilityTab(entityModel_a);






        List<PricingRate> pricingRates = pricingTypeRepository.findByProductId(product.getId());
        List<Component> componentRightPage= componentRepository.findAllByProduct(product.getId());
        ProductResp.ComponentTap componentTap = ProductResp.ComponentTap.from(componentRightPage);



        componentTap.setRoyalties(componentRightPage.stream().map(e->{
            ProductPojo.Royalty royalty = new ProductPojo.Royalty();
            royalty.setComponent_right(e.getComponentRightId());
            royalty.setPercent(e.getRoyaltyPercent());
            royalty.setLimit_quantity(e.getUnit_off());
            royalty.setValidate_way(e.getValidate_way());
            royalty.setCollection_method(e.getCollection_method());
            royalty.setId(e.getId());
            return royalty;
        }).collect(Collectors.toList()));

        componentTap.setPrices(pricingRates.stream().map(e->{
            ProductPojo.Price price = ProductPojo.Price.from(e);


            //   ProductEditComponentTabPojo pricingTypeResp = ProductEditComponentTabPojo.from(e);

            EntityModel entityModel1 = EntityModel.of(price);

         //   entityModel1.add(linkTo(methodOn(BookingRestController.class).Page_createBookingFromPriceType(e.getId())).withRel("Page_createBooking"));

            return entityModel1;
        }).collect(Collectors.toList()));


        componentTap.setParameterList(
                Map.of(
                        "validator_way_list",EnumValidateWay.EnumList(),
                        "royalty_mode_list",EnumRoyaltyRuleCategory.EnumList(),
                        "by_person_group_type_list",EnumProductPricingTypeByPersonGroupType.EnumList(),
                        "collection_method_list",EnumRoyaltyCollection_method.List(),
                        "range_type_list",EnumAvailabilityRangetype.from(),
                        "component_right_list",ComponentRight.List(componentRightList,supplier,royaltyRuleListMap),
                        /// "daytour_days_list", EnumProductPricingTypeByPersonGroupType.(),
                       "by_person_type_list",EnumProductPricingTypeByPerson.EnumList(),
                        "price_type_list", EnumProductPricingType.from()
        ));
        EntityModel entityModel_ComponentTap = EntityModel.of(componentTap);
        entityModel_ComponentTap.add(linkTo(methodOn(ProductRestController.class).addComponentRight(product.getId(),null)).withRel("update"));
        entityModel_ComponentTap.add(linkTo(methodOn(ProductRestController.class).editProductComponentTab(product.getId(),null)).withRel("edit"));
        productResp.setComponentTab(entityModel_ComponentTap);






















        ProductResp.RightTap rightTap = ProductResp.RightTap.from(componentRightPage);

        rightTap.setRoyalties(componentRightPage.stream().map(e->{
            ProductPojo.Royalty royalty = new ProductPojo.Royalty();
            royalty.setComponent_right(e.getComponentRightId());
            royalty.setPercent(e.getRoyaltyPercent());
            royalty.setLimit_quantity(e.getUnit_off());
            royalty.setValidate_way(e.getValidate_way());
            royalty.setCollection_method(e.getCollection_method());
            royalty.setId(e.getId());
            return royalty;
        }).collect(Collectors.toList()));


        rightTap.setParameterList(
                Map.of(
                        "validator_way_list",EnumValidateWay.EnumList(),
                        "royalty_mode_list",EnumRoyaltyRuleCategory.EnumList(),
                        "collection_method_list",EnumRoyaltyCollection_method.List(),
                        "component_right_list",ComponentRight.List(componentRightList,supplier,royaltyRuleListMap)
                ));
        EntityModel entityModel_rightTap = EntityModel.of(rightTap);
        entityModel_rightTap.add(linkTo(methodOn(ProductRestController.class).editProductRightTab(product.getId(),null)).withRel("edit"));
        productResp.setRightTab(entityModel_rightTap);





        ProductResp.RuleTap ruleTap = ProductResp.RuleTap.from(product);
        Tip tip = new Tip();
        tip.setKey("confirmationType");
        tip.setName("confirmationType");
        tip.setDesc("这里是描述");

        ruleTap.setTips(Arrays.asList(tip));




        List<Term> termList = termRepository.findAllByRelatedObjectTypeAndRelatedObjectId(EnumRelatedObjectType.product,product.getId());

      //  Map<EnumTermType,Term> map = termList.stream().collect(Collectors.toMap(e->e.getType(),e->e));

        ruleTap.setTerms(termList.stream().map(e->{
            TermReq termResp = TermReq.from(e);
            return termResp;
        }).collect(Collectors.toList()));


        ruleTap.setParameterList(
                Map.of(
                        "confirmation_type_list",EnumConfirmationType.List(),
                        "term_type_list",EnumTermType.List()

                ));
        EntityModel entityModel_ruleTap = EntityModel.of(ruleTap);
        entityModel_ruleTap.add(linkTo(methodOn(ProductRestController.class).editProductRuleTab(product.getId(),null)).withRel("edit"));
        productResp.setRuleTab(entityModel_ruleTap);


        ProductEditResp.PaymentTap paymentTap = new ProductEditResp.PaymentTap();
        if(product.getPaymentMethods_json()!= null){
            paymentTap.setPayment_methods(Arrays.stream(new Gson().fromJson(product.getPaymentMethods_json(),EnumPayChannel[].class)).toList());

        }else{
            paymentTap.setPayment_methods(Arrays.asList());
        }
        paymentTap.setParameterList(
                Map.of("payment_method_list",   EnumPayChannel.from(Arrays.asList(EnumPayChannel.wx,EnumPayChannel.balance)))
        );
        EntityModel entityModel_paymentTap = EntityModel.of(paymentTap);
        entityModel_paymentTap.add(linkTo(methodOn(ProductRestController.class).editPaymentTab(product.getId(),null)).withRel("edit"));
        productResp.setPaymentTab(entityModel_paymentTap);



        ProductEditResp.QuestionTap questionTap = new ProductEditResp.QuestionTap();
        if(product.getPaymentMethods_json()!= null){
            paymentTap.setPayment_methods(Arrays.stream(new Gson().fromJson(product.getPaymentMethods_json(),EnumPayChannel[].class)).toList());

        }else{
            paymentTap.setPayment_methods(Arrays.asList());
        }
        paymentTap.setParameterList(
                Map.of("payment_method_list",   EnumPayChannel.from(Arrays.asList(EnumPayChannel.wx,EnumPayChannel.balance)))
        );
        EntityModel entityModel_questionTap = EntityModel.of(questionTap);
        entityModel_questionTap.add(linkTo(methodOn(ProductRestController.class).editQuestionTab(product.getId(),null)).withRel("edit"));
        productResp.setQuestionTap(entityModel_questionTap);




        ProductEditResp.PublishTab publishTab = new ProductEditResp.PublishTab();
        List<RatePlan> ratePlanPage = ratePlanRepository.findAllByProduct(product.getId());
        publishTab.setParameterList(
                Map.of(
                         "billing_period_list",EnumBillingPeriod.from(),
                        "charge_model_list",EnumChargeModel.from()
                        ));
        publishTab.setNegotiatedRates(ratePlanPage.stream().map(e->{
            RatePlanResp resp = RatePlanResp.from(e);
            EntityModel entityModel_publishTab = EntityModel.of(resp);
            entityModel_publishTab.add(linkTo(methodOn(SubscriptionRestController.class).delete(e.getId())).withRel("delete"));
           // entityModel_publishTab.add(linkTo(methodOn(SubscriptionRestController.class).delete(e.getId())).withRel("delete"));
            return entityModel_publishTab;
        }).collect(Collectors.toList()));
        EntityModel entityModel_publishTab = EntityModel.of(publishTab);

        entityModel_publishTab.add(linkTo(methodOn(ProductRestController.class).publishTab(product.getId(),null)).withRel("edit"));

        entityModel_publishTab.add(linkTo(methodOn(SubscriptionRestController.class).createRatePlan(product.getId(),null)).withRel("createNegotiatedRate"));
        productResp.setPublishTab(entityModel_publishTab);




        if(product.getType().equals(EnumProductType.Pass)){
            Optional<PassProduct> passProduct = passProductRepository.findByProduct(product.getId());

            PassProduct passProduct1 =null;
            if(passProduct.isEmpty()){
                passProduct1 = new PassProduct();
                passProduct1.setProduct(product.getId());
                passProductRepository.save(passProduct1);
            }else{
                passProduct1 = passProduct.get();
            }

            ProductEditResp.PassTab passTab = ProductEditResp.PassTab.from(passProduct1);
            passTab.setParameterList(
                    Map.of(
                            "pass_duration_unit_list",EnumPassDorationUnit.from()
                    ));


            passTab.setCover(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.card_cover,passProduct1.getUuid()));

            EntityModel entityModel_passTab = EntityModel.of(passTab);
            entityModel_passTab.add(linkTo(methodOn(ProductRestController.class).editPassTab(product.getId(),null)).withRel("edit"));
            productResp.setPassTab(entityModel_passTab);
        }



        if(product.getType().equals(EnumProductType.Showtime)){
            Optional<MovieProduct> passProduct = movieProductRepository.findByProduct(product.getId());

            MovieProduct passProduct1 =null;
            if(passProduct.isEmpty()){
                passProduct1 = new MovieProduct();
                passProduct1.setZonePricings(Arrays.asList());
                passProduct1.setProduct(product.getId());
                movieProductRepository.save(passProduct1);
            }else{
                passProduct1 = passProduct.get();
            }


            List<Movie> movieList = movieRepository.findAll();
            List<Theatre> theatreList = theatreRepository.findAll();

            List<SeatingLayout> seatingLayoutList = seatingLayoutRepository.findAll();
            ProductEditResp.MovieTab movieTab = ProductEditResp.MovieTab.from(passProduct1);
            movieTab.setParameterList(
                    Map.of(
                            "movie_list",Movie.List(movieList),
                            "layout_list",SeatingLayout.List(seatingLayoutList),
                            "theatre_list",Theatre.List(theatreList),
                            "pricing_type_list", PricingRate.List(pricingRates)

                    ));




            //finalPassProduct.getZonePricings().s

            if(passProduct1.getSeatingLayout()!=null){
                List<Zone> zoneList = zoneRepository.findBySeatingLayoutId(passProduct1.getSeatingLayout());
                Map<Long,List<Sku>> longListMap = passProduct1.getZonePricings().stream().collect(Collectors.groupingBy(e->e.getZone()));

                movieTab.setZonePricings(zoneList.stream().map(e->{
                    ZonePricingReq zonePricingReq =  ZonePricingReq.from(e, longListMap.getOrDefault(e.getId(),Arrays.asList()));
                    return zonePricingReq;
                }).collect(Collectors.toList()));
            }






       //     movieTab.setParameterList();

/*                Movie movie = movieRepository.findById(passProduct1.getMovie()).get();

                SeatingLayout seatingLayout = seatingLayoutRepository.findById(showtime.getLayout()).get();

                List<Zone> zones = zoneRepository.findBySeatingLayoutId(showtime.getLayout());*/




         //   passTab.setCover(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.card_cover,passProduct1.getUuid()));

            EntityModel entityModel_movieTab = EntityModel.of(movieTab);
            entityModel_movieTab.add(linkTo(methodOn(ProductRestController.class).movieTabPerformance(product.getId(),null)).withRel("createPerformance"));

            entityModel_movieTab.add(linkTo(methodOn(ProductRestController.class).movieTab(product.getId(),null)).withRel("edit"));
            productResp.setMovieTab(entityModel_movieTab);
        }





        List<Attribute> attributeList = attributeRepository.findAllByObjectCode(product.getCode());





        ProductEditResp.AboutTab aboutTab = ProductEditResp.AboutTab.from(attributeList);
        Optional<Contact> contactOptional = contactService.find(EnumRelatedObjectType.product,product.getId());

        if(contactOptional.isPresent()){
            aboutTab.setContacts(contactOptional.get().getIdentifiers()
                    .stream().map(e->{
                        return IdentifierReq.from(e);
                    }).collect(Collectors.toList()));
        }
        aboutTab.setParameterList(
                Map.of(
                        "feature_tag_list",EnumFeatureTag.List()
                ));
        EntityModel entityModel_aboutTap = EntityModel.of(aboutTab);
        entityModel_aboutTap.add(linkTo(methodOn(ProductRestController.class).editProductAboutTab(product.getId(),null)).withRel("edit"));
        productResp.setAboutTab(entityModel_aboutTap);



        ProductEditResp.CrossSellTab crossSellTab = ProductEditResp.CrossSellTab.from(product.getCrossSells());
/*        Optional<Contact> contactOptional = contactService.find(EnumRelatedObjectType.product,product.getId());

        if(contactOptional.isPresent()){
            aboutTab.setContacts(contactOptional.get().getIdentifiers()
                    .stream().map(e->{
                        return IdentifierReq.from(e);
                    }).collect(Collectors.toList()));
        }*/
        crossSellTab.setParameterList(
                Map.of(
                        "feature_tag_list",EnumFeatureTag.List()
                ));
        EntityModel entityModel_crossSellTab = EntityModel.of(crossSellTab);
        entityModel_crossSellTab.add(linkTo(methodOn(ProductRestController.class).editProductAboutTab(product.getId(),null)).withRel("edit"));
        productResp.setCrossSellTab(entityModel_crossSellTab);


        ProductEditResp.ShippingTab shippingTab = ProductEditResp.ShippingTab.from(product);

        List<ShippingRate> shippingRates = shippingRateRepository.findAllBySupplier(product.getSupplierId());
        shippingTab.setParameterList(
                Map.of(
                        "delivery_format_list",EnumDeliveryFormat.List(),
                        "shippingAddressCollection_type_list",EnumShippingAddressCollection.List(),
                        "feature_tag_list",EnumFeatureTag.List(),
                        "shippingRate_list",ShippingRate.List(shippingRates)

                ));

        EntityModel entityModelshippingTap = EntityModel.of(shippingTab);
        entityModelshippingTap.add(linkTo(methodOn(ProductRestController.class).editProductShippingTab(product.getId(),null)).withRel("edit"));
        productResp.setShippingTab(entityModelshippingTap);


        ProductEditResp.BundleTab bundleTab = ProductEditResp.BundleTab.from(product.getBundles());
        bundleTab.setParameterList(
                Map.of(
                        "delivery_format_list",EnumDeliveryFormat.List()
                ));
        EntityModel entityModel_bundleTab = EntityModel.of(bundleTab);
        entityModel_bundleTab.add(linkTo(methodOn(ProductRestController.class).editProductBundleTab(product.getId(),null)).withRel("edit"));
        productResp.setBundleTab(entityModel_bundleTab);




        List<Extra> extraList = extraRepository.findAllBySupplier(product.getSupplierId());




        ProductEditResp.UpsellingTab upsellingTab = ProductEditResp.UpsellingTab.from(product.getBundles());
        upsellingTab.setParameterList(
                Map.of(
                        "delivery_format_list",EnumDeliveryFormat.List(),
                        "extra_list",Extra.List(extraList)
                ));
        EntityModel entityModel_upsellingTab = EntityModel.of(upsellingTab);
        entityModel_upsellingTab.add(linkTo(methodOn(ProductRestController.class).editProductBundleTab(product.getId(),null)).withRel("edit"));
        productResp.setUpsellingTab(entityModel_upsellingTab);





        List<AvailabilityCalendarVO> availabilityCalendarVOList = availabilityService.getAvailability_(product,bookingRuleList, LocalDate.now().plusDays(10));

        productResp.setBookingAvailability(availabilityCalendarVOList);

        EntityModel entityModel = EntityModel.of(productResp);
        entityModel.add(linkTo(methodOn(ProductRestController.class).getProduct_componentRights(product.getId(),null,null)).withRel("getcomponentRightList"));

        entityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(product.getId())).withSelfRel());

        entityModel.add(linkTo(methodOn(ProductRestController.class).editProduct(product.getId(),null)).withRel("edit"));

        return entityModel;


    }


    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/products/{PRODUCT_ID}/edit", produces = "application/json")
    public EntityModel<ProductResp> getProductEdit(@PathVariable long PRODUCT_ID) {

        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }

        Product product = validatorOptional.get();


        List<Component> componentRightPage= componentRepository.findAllByProduct(product.getId());


        ProductResp productResp = null;
        if(product.getType().equals(EnumProductType.Daytour)){

            Optional<Tour> tour = tourRepository.findByProduct(product.getId());

            List<CampaignAssignToTourProduct> campaignAssignToTourProducts = campaignAssignToTourProductRepository.findByTourId(tour.get().getId());

            List<Campaign> campaigns = campaignRepository.findAllById(campaignAssignToTourProducts.stream().map(x->x.getCampaign()).collect(Collectors.toList()));
            productResp = ProductResp.dayTourFrom(product,tour.get(),campaigns);


        }else{
            productResp = ProductResp.from(product);
            if(product.getType().equals(EnumProductType.Attraction)){
                Optional<Attraction> attractionOptional = attractionRepository.findById(product.getTypeTo());


                if(attractionOptional.isPresent()){

                    productResp.setAttraction(AttractionResp.from(attractionOptional.get()));
                }


            }






            List<PricingRate> pricingRates = pricingTypeRepository.findByProductId(product.getId());



            PricingRate pricingRate = pricingRates.get(0);
            productResp.setDefault_price(PricingTypeResp.from(pricingRate));

            productResp.setPriceTypes(pricingRates.stream().map(e->{

                PricingTypeResp pricingTypeResp = PricingTypeResp.from(e);

                EntityModel entityModel1 = EntityModel.of(pricingTypeResp);

           //     entityModel1.add(linkTo(methodOn(BookingRestController.class).Page_createBookingFromPriceType(e.getId())).withRel("Page_createBooking"));

                return entityModel1;
            }).collect(Collectors.toList()));




        }

        List<BookingRule> bookingRuleList = bookingRuleRepository.findByProduct(product.getId());







        ProductResp.AvailabilityTab availability = new ProductResp.AvailabilityTab();//availabilityService.form(bookingRuleList);

        availability.setParameter(Map.of("range_type_list",EnumAvailabilityRangetype.from()));

        EntityModel entityModel_a = EntityModel.of(availability);


        entityModel_a.add(linkTo(methodOn(ProductRestController.class).addBookingRule(product.getId(),null)).withRel("update"));

        productResp.setAvailability(entityModel_a);

        List<AvailabilityCalendarVO> availabilityCalendarVOList = availabilityService.getAvailability_(product,bookingRuleList, LocalDate.now().plusDays(10));

        productResp.setBookingAvailability(availabilityCalendarVOList);


        //   entityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(product.getId(),null)).withRel("createBooking"));


        EntityModel entityModel = EntityModel.of(productResp);
        productResp.setComponents(componentRightPage.stream().map(e->{
            return ComponentResp.from(e);
        }).collect(Collectors.toList()));


        String note = "游客预约系统在线预约时需要提供出行人的身份证西南西，并需要在进馆时出具对应身份证件用于验证，请确保录入信息真实有效。一码君行将通过加密等方式保护您录入的身份证件信息。";

        productResp.setBooking_note(note);
        productResp.setTraveller_term(note);


        entityModel.add(linkTo(methodOn(ProductRestController.class).getProduct_componentRights(product.getId(),null,null)).withRel("getcomponentRightList"));

        entityModel.add(linkTo(methodOn(BookingRestController.class).Page_createBooking(product.getId(), null)).withRel("Page_createBooking").expand());

        entityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(product.getSupplierId())).withRel("getSupplier"));

        entityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(product.getId())).withSelfRel());

        return entityModel;


    }




    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/products/{PRODUCT_ID}", produces = "application/json")
    public EntityModel<ProductResp> getProduct(@PathVariable long PRODUCT_ID) {

        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }

        Product product = validatorOptional.get();


        List<Component> componentRightPage= componentRepository.findAllByProduct(product.getId());




        ProductResp productResp  = ProductResp.fromFront(product);

        if(product.getType().equals(EnumProductType.Daytour)){

            Optional<Tour> tour = tourRepository.findByProduct(product.getId());

            List<CampaignAssignToTourProduct> campaignAssignToTourProducts = campaignAssignToTourProductRepository.findByTourId(tour.get().getId());

            List<Campaign> campaigns = campaignRepository.findAllById(campaignAssignToTourProducts.stream().map(x->x.getCampaign()).collect(Collectors.toList()));
            productResp = ProductResp.dayTourFrom(product,tour.get(),campaigns);


        }



            Optional<Supplier> supplierOptional = supplierRepository.findById(product.getSupplierId());
            if(supplierOptional.isPresent()){

                Supplier supplier = supplierOptional.get();

                SupplierResp supplierResp = SupplierResp.verySimpleFrom(supplier);
                EntityModel entityModel = EntityModel.of(supplierResp);
                entityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(supplier.getId())).withSelfRel());

                productResp.withSupplier(entityModel);
            }

            reviewCountService.fill(product,productResp);


        productResp.setOptions(Arrays.asList());

        busTicketService.show(productResp,product);
            cityPassService.show(productResp,product);
            attractionTicketService.show(productResp,product);
            showtimeTicketService.show(productResp,product);





            List<PricingRate> pricingRates = pricingTypeRepository.findByProductId(product.getId()).stream().filter(e->e.getActive()).collect(Collectors.toList());



            if(pricingRates.size() !=0){
                PricingRate pricingRate = pricingRates.get(0);
                productResp.setDefault_sku(PricingTypeResp.sku_simple(pricingRate));

            }else{
                productResp.setDefault_sku(new PricingTypeResp());

            }


/*            Optional<Partner> partner = partnerService.getPartner(product.getSupplierId(),product.getSupplierId());
            if(partner.isEmpty()){
                Partner partner1 = partner.get();
                List<PartnerShareRatePlan> partnerList = partnerShareRatePlanRepository.findAllByPartnerAndProduct(partner1.getId(),product.getId());
                productResp.setSkus(pricingTypes.stream().map(e->{

                    PricingTypeResp pricingTypeResp = PricingTypeResp.sku(e);

                    EntityModel entityModel1 = EntityModel.of(pricingTypeResp);

                    entityModel1.add(linkTo(methodOn(BookingRestController.class).Page_createBookingFromPriceType(e.getId())).withRel("Page_createBooking"));

                    return entityModel1;
                }).collect(Collectors.toList()));

            }*/



            productResp.setSkus(pricingRates.stream().map(e->{
                PricingTypeResp pricingTypeResp = PricingTypeResp.sku_simpleBooking(e);
                Optional<Partner> partner = partnerService.getPartner(product.getSupplierId(),product.getSupplierId());



                if(partner.isEmpty()){
                    Partner partner1 = partner.get();
                    Optional<PartnerShareRatePlan> partnerList = partnerShareRatePlanRepository.findAllByPartnerAndProduct(partner1.getId(),product.getId());

                    NegotiatedPricingType negotiatedPricingType = priceService.fill(e,partnerList);

                    pricingTypeResp.setNegotiatedSku(negotiatedPricingType);

                }





                EntityModel entityModel1 = EntityModel.of(pricingTypeResp);

            //    entityModel1.add(linkTo(methodOn(BookingRestController.class).Page_createBookingFromPriceType(e.getId())).withRel("Page_createBooking"));

                return entityModel1;
            }).collect(Collectors.toList()));



       List<BookingRule> bookingRuleList = bookingRuleRepository.findByProduct(product.getId());

        List<RatePlan> ratePlanList = ratePlanRepository.findAllByProduct(product.getId());

       // productResp.withRateplans(ratePlanList);


        productResp.setRatePlans(ratePlanList.stream().map(e->{

            RatePlanResp resp = RatePlanResp.from(e);
            EntityModel entityModel = EntityModel.of(resp);
            entityModel.add(linkTo(methodOn(SubscriptionRestController.class).createSubscription(e.getId(), null)).withRel("subscribe").expand());

            return entityModel;
        }).collect(Collectors.toList()));





/*        ProductResp.Availability availability =availabilityService.form(bookingRuleList);

     //   availability.setParameter(Map.of("range_type_list",EnumAvailabilityRangetype.from()));

        EntityModel entityModel_a = EntityModel.of(availability);


        entityModel_a.add(linkTo(methodOn(ProductRestController.class).addBookingRule(product.getId(),null)).withRel("update"));

        productResp.setAvailability(entityModel_a);*/

        List<AvailabilityCalendarVO> availabilityCalendarVOList = availabilityService.getAvailability_(product,bookingRuleList, LocalDate.now().plusDays(10));

      //  productResp.setBookingAvailability(availabilityCalendarVOList);


        ProductResp.Availability availability1 = ProductResp.Availability.from(product);
        availability1.setBookingAvailability(availabilityCalendarVOList);
        productResp.setAvailability(EntityModel.of(availability1));


        //   entityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(product.getId(),null)).withRel("createBooking"));


        productResp.setYouLike(youlikeService.youlike(product));


        EntityModel entityModel = EntityModel.of(productResp);
        productResp.setComponents(componentRightPage.stream().map(e->{
            return ComponentResp.from(e);
        }).collect(Collectors.toList()));






       // terms_of_sevice

        RestrictionResp restriction1 = new RestrictionResp();
        restriction1.setMinQuantity(product.getRestriction_minQuantity());
        restriction1.setMaxQuantity(product.getRestriction_maxQuantity());
        restriction1.setIdRequired(product.getRestriction_passenger_identity_documents_required());

        Map<EnumTermType,TermResp> termMap = termService.getMapResp(product);
        TermResp term = termMap.get(EnumTermType.booking);
     //   Term term1 = new Term();
      //  term1.setText(term.);
     //   term1.setRequiresExplicitConsent(false);
    //    term1.setType(EnumTermType.TermsOfUse);
        productResp.setBooking(EntityModel.of(Map.of("termsOfService",term,
                "terms",termMap,
                "terms_of_sevice",true,
                "Restriction",restriction1
                )));


        productResp.setComments(commentService.list(product).stream().map(e->{
            CommentResp commentResp = CommentResp.from(e);
            return commentResp;
        }).collect(Collectors.toList()));


        entityModel.add(linkTo(methodOn(ProductRestController.class).getProduct_componentRights(product.getId(),null,null)).withRel("getcomponentRightList"));

        entityModel.add(linkTo(methodOn(BookingRestController.class).Page_createBooking(product.getId(), null)).withRel("Page_createBooking").expand());

        entityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(product.getSupplierId())).withRel("getSupplier"));

        entityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(product.getId())).withSelfRel());




        entityModel.add(linkTo(methodOn(BookingRestController.class).Page_createBooking(product.getId(), null)).withRel("booking").expand());


/*

        AttributeResp attributeResp =new AttributeResp();
        attributeResp.setName("购票须知");
        attributeResp.setDescription(" you will need to wait in line to receive a time slot. We recommend arriving between 9AM and 3PM for the shortest wait times. Use the Rockefeller Plaza entrance on 50th Street (between 5th and 6th Avenues) and go down the staircase to enter the Go City pass line.");

        AttributeResp attributeResp2 =new AttributeResp();
        attributeResp2.setName("购票须知");
        attributeResp2.setDescription(" you will need to wait in line to receive a time slot. We recommend arriving between 9AM and 3PM for the shortest wait times. Use the Rockefeller Plaza entrance on 50th Street (between 5th and 6th Avenues) and go down the staircase to enter the Go City pass line.");
        AttributeResp attributeResp5 =new AttributeResp();
        attributeResp5.setName("团购票");
        attributeResp5.setDescription("if you are traveling with a tour group, please send a tour group leader to exchange all Go City Passes for timed admission tickets between 10 AM and 11 AM on the day you plan to visit the observatory. Tour groups that arrive after 11 AM without timed admission tickets may be turned away.");
*/

        List<Attribute> attributes = attributeRepository.findAllByObjectCode(product.getCode());
        productResp.setAbout(attributes.stream().map(e->{
            return AttributeResp.from(e);
        }).collect(Collectors.toList()));
        productResp.setImages(fileStorageService.loadDocuments(Arrays.asList(EnumPhotos.full),EnumDocumentType.product_photos,product.getCode()));



        if(fileStorageService.isExsit(EnumDocumentType.product_video,product.getCode())){
            productResp.setHasVideo(true);
            productResp.setVideo(fileStorageService.loadDocument( Arrays.asList(EnumPhotos.full),EnumDocumentType.product_video,product.getCode()));

        }else{
            productResp.setHasVideo(false);
        }


        return entityModel;


    }




    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/products/{PRODUCT_ID}/component_rights", produces = "application/json")
    public PagedModel<ComponentResp> getProduct_componentRights(@PathVariable long PRODUCT_ID,Pageable pageable,PagedResourcesAssembler<EntityModel<ComponentResp>> assembler) {

        Optional<Product> productOptional = productRepository.findById(PRODUCT_ID);

        if(productOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }

        Product product = productOptional.get();


       Page<Component> components= componentRepository.findAllByProduct(product.getId(),pageable);


        List<ComponentRight> componentRights= componentRightRepository.findAllById(components.stream().map(e->e.getProduct()).collect(Collectors.toList()));

        Map<Long,ComponentRight> componentRightMap = componentRights.stream().collect(Collectors.toMap(e->e.getId(),e->e));


        return  assembler.toModel(components.map(x->{


            ComponentRight componentRight = componentRightMap.get(x.getComponentRightId());
            EntityModel entityModel= EntityModel.of(ComponentResp.from(x,componentRight));

         //   entityModel.add(linkTo(methodOn(ComponentRightRestController.class).delete(x.getId())).withRel("delete"));
            entityModel.add(linkTo(methodOn(ComponentRightRestController.class).getComponentRighte(x.getProduct())).withSelfRel());


            return entityModel;
        }));




    }




    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/products/page", produces = "application/json")
    public EntityModel Page_getProductList(@PathVariable long SUPPLIER_ID) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("找不到供应商","找不到供应商");
        }
        Supplier supplier = supplierOptional.get();


        EntityModel entityModel = EntityModel.of(Map.of(

                "product_type_list", Arrays.stream(EnumProductType.values()).map(x->{


                    EnumResp enumResp = new EnumResp();

                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    EntityModel entityModel1 =EntityModel.of(enumResp);
                    if(x.equals(EnumProductType.Attraction)){
                        entityModel1.add(linkTo(methodOn(ProductRestController.class).Page_createProduct_By_Type(supplier.getId(),EnumProductType.Attraction)).withRel("Page_create"));
                    }
                    if(x.equals(EnumProductType.Pass)){
                        entityModel1.add(linkTo(methodOn(ProductRestController.class).Page_createProduct_By_Type(supplier.getId(),EnumProductType.Pass)).withRel("Page_create"));
                    }
                    if(x.equals(EnumProductType.Daytour)){
                        entityModel1.add(linkTo(methodOn(ProductRestController.class).Page_createProduct_By_Type(supplier.getId(),EnumProductType.Daytour)).withRel("Page_create"));
                    }
                    return entityModel1;
                }).collect(Collectors.toList()),



                "status_list", Arrays.stream(EnumProductStatus.values()).map(x->{



                    EnumResp enumResp = new EnumResp();

                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
                }).collect(Collectors.toList())
        ));





        entityModel.add(linkTo(methodOn(ProductRestController.class).getProductList(supplier.getId(),null,null)).withRel("getProductList"));

        entityModel.add(linkTo(methodOn(ProductRestController.class).createProduct(supplier.getId(),null)).withRel("create"));



        return entityModel;
    }



    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/products", produces = "application/json")
    public PagedModel getProductList(@PathVariable long SUPPLIER_ID,
                                     @PageableDefault(sort = {"createdDate",
                                             "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable ,

                                     PagedResourcesAssembler<EntityModel<ProductResp>> assembler) {

        Optional<Supplier> validatorOptional = supplierRepository.findById(SUPPLIER_ID);
        if(validatorOptional.isEmpty()) {

            throw new BookNotFoundException("找不到供应商","找不到供应商");
        }



          return  assembler.toModel(productService.listProduct(validatorOptional.get(),pageable).map(x->{
              EntityModel entityModel= EntityModel.of(ProductResp.Simplefrom(x));

          //    EntityModel entityModel= EntityModel.of(ProductResp.Simplefrom(Pair.with(x,validatorOptional.get())));
              entityModel.add(linkTo(methodOn(ProductRestController.class).delete(x.getId())).withRel("delete"));
              entityModel.add(linkTo(methodOn(ProductRestController.class).Page_editProduct(x.getId())).withRel("Page_edit"));


            //  entityModel.add(linkTo(methodOn(ProductRestController.class).editProduct(x.getId(),null)).withRel("edit"));
              entityModel.add(linkTo(methodOn(ProductRestController.class).Page_getProductList(x.getId())).withSelfRel());



                      return entityModel;
                }));


    }










    @Operation(summary = "1、查询权益")
    @GetMapping(value = "/supliers/{SUPPLIER_ID}/component_rights_from_product", produces = "application/json")
    public PagedModel getComponentRightList(@PathVariable long SUPPLIER_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<ComponentRight>> assembler) {



        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);
        if(optionalSupplier.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = optionalSupplier.get();


        Page<ComponentRight> componentRights = componentRightRepository.findAllBySupplier(supplier.getId(),pageable);



        return assembler.toModel(componentRights.map(e->{

         //   ComponentRightResp.ComponentRightOption enumResp = new ComponentRightResp.ComponentRightOption();

            EnumProductComponentSource origin = null;
            if(supplier.getId() == supplier.getId()){
                origin = EnumProductComponentSource.own;
            }else{
                origin = EnumProductComponentSource.partner;
            }


            ComponentRightResp componentRightResp = ComponentRightResp.from(e,EnumValidateWay.EnumList(),origin);


            EntityModel entityModel = EntityModel.of(componentRightResp);



            return entityModel;
        }));
    }






    @GetMapping(value = "/supplier/{SUPPLIER_ID}/products/page_create_by_type/{type}", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_createProduct_By_Type(@PathVariable long SUPPLIER_ID, @PathVariable EnumProductType type) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 商户"+SUPPLIER_ID);
        }
        Supplier supplier = supplierOptional.get();

      //  List<Campaign> campaignList = campaignRepository.findAll();


        List<ComponentRight> componentRightList = componentRightRepository.findAllBySupplier(supplier.getId());


        List<Attraction> attractionList = attractionRepository.findAllBySupplier(supplier.getId());


        List<RoyaltyRule> royaltyRuleList = royaltyRuleRepository.findAllByComponentRightIn(componentRightList.stream().map(e->e.getId()).collect(Collectors.toList()));

        Map<Long,List<RoyaltyRule>> royaltyRuleListMap =  royaltyRuleList.stream().collect(Collectors.groupingBy(e->e.getComponentRight()));


        validateService.newValidator(type,componentRightList,attractionList);



        List<ValueList> valueLists = valueListRepository.findAllByType(EnumValueListType.city_pass_right_recommendation);



        List<ValueListItem> valueListItemList = valueListItemRepository
                .findAllByValueListIn(valueLists.stream().map(e->e.getId()).collect(Collectors.toList()));

        Map<Long,List<ValueListItem>>  longListMap  =     valueListItemList.stream().collect(Collectors.groupingBy(e->e.getValueList()));





        List<EnumLongIdResp> city_pass_right_list = valueLists.stream().map(e->{

            List<Long> ids = longListMap.getOrDefault(e.getId(),Arrays.asList()).stream().map(ee->Long.valueOf(ee.getValue()).longValue()).collect(Collectors.toList());

            List<ComponentRight> componentRightMap =componentRightRepository.findAllById(ids);
            EnumLongIdResp enumResp_sup = new EnumLongIdResp();
            enumResp_sup.setId(e.getId());
            enumResp_sup.setText(e.getName()+"_"+e.getCode());

            enumResp_sup.setSubitems(
            ComponentRight.List(componentRightMap,supplier,royaltyRuleListMap));
/*            enumResp_sup.setSubitems(componentRightMap.stream().map(ee->{
                EnumLongIdResp enumResp_sup_sub = new EnumLongIdResp();
                enumResp_sup_sub.setId(ee.getId());
                enumResp_sup_sub.setText(ee.getName()+"_"+ee.getCode());
                return enumResp_sup_sub;
            }).collect(Collectors.toList()));*/
            return enumResp_sup;
        }).collect(Collectors.toList());



        EnumResp enumResp = EnumResp.of(type,type.toString());

        EntityModel entityModel = EntityModel.of(Map.of(
                "city_pass_right_list",city_pass_right_list,
                "type",enumResp,
                "attraction_list", AttractionResp.EnumList(attractionList),
                "range_type_list",EnumAvailabilityRangetype.from(),
                "availability_type_list",EnumAvailabilityType.EnumList(),

                "component_right_list",ComponentRight.List(componentRightList,supplier,royaltyRuleListMap),
              //  "daytour_days_list", EnumDaytourDays.from(),
                "identity_document_type_list",Arrays.asList(EnumResp.of("身份证","身份证")),
                "product_type_list",EnumProductType.from(productService.getSupportProductType()),
                "price_type_list", EnumProductPricingType.from(),
                //"campaign_list", Campaign.EnumList(campaignList),
                "payment_method_list",   EnumPayChannel.from(Arrays.asList(EnumPayChannel.wx,EnumPayChannel.balance))));

        if(type.equals(EnumProductType.Pass)){
            entityModel.add(linkTo(methodOn(ProductRestController.class).createProductPass(supplier.getId(),null)).withRel("create"));

        }else{
            entityModel.add(linkTo(methodOn(ProductRestController.class).createProduct_for_attraction(supplier.getId(),null)).withRel("create"));

        }
        entityModel.add(linkTo(methodOn(ProductRestController.class).getComponentRightList(supplier.getId(),null,null)).withRel("getComponentRightList"));

        // 基本信息价格权益支付与退款订票规则


        return entityModel;
    }






    @GetMapping(value = "/supplier/{SUPPLIER_ID}/products/page_create", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_createProduct(@PathVariable long SUPPLIER_ID) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 商户"+SUPPLIER_ID);
        }
        Supplier supplier = supplierOptional.get();

        List<Campaign> campaignList = campaignRepository.findAll();


        List<ComponentRight> componentRightList = componentRightRepository.findAllBySupplier(supplier.getId());


        List<Attraction> attractionList = attractionRepository.findAllBySupplier(supplier.getId());


        List<RoyaltyRule> royaltyRuleList = royaltyRuleRepository.findAllByComponentRightIn(componentRightList.stream().map(e->e.getId()).collect(Collectors.toList()));

        Map<Long,List<RoyaltyRule>> royaltyRuleListMap =  royaltyRuleList.stream().collect(Collectors.groupingBy(e->e.getComponentRight()));

    //    validateService.newValidator(type, componentRightList,attractionList);




        EntityModel entityModel = EntityModel.of(Map.of(
                "attraction_list", AttractionResp.EnumList(attractionList),
                "range_type_list",EnumAvailabilityRangetype.from(),

                "component_right_list",ComponentRight.List(componentRightList,supplier,royaltyRuleListMap),
                "daytour_days_list", EnumDaytourDays.from(),
                "product_type_list",EnumProductType.from(productService.getSupportProductType()),
                "price_type_list", EnumProductPricingType.from(),
                "campaign_list", Campaign.EnumList(campaignList),
                "payment_method_list",   EnumPayChannel.from(Arrays.asList(EnumPayChannel.wx,EnumPayChannel.balance))));

        entityModel.add(linkTo(methodOn(ProductRestController.class).createProduct_for_attraction(supplier.getId(),null)).withRel("create"));
        entityModel.add(linkTo(methodOn(ProductRestController.class).getComponentRightList(supplier.getId(),null,null)).withRel("getComponentRightList"));

       // 基本信息价格权益支付与退款订票规则


        return entityModel;
    }






    @Operation(summary = "2、创建Product对象")
    @PostMapping(value = "/supplier/{SUPPLIER_ID}/products", produces = "application/json")
    public ResponseEntity<ProductResp> createProduct(@PathVariable long SUPPLIER_ID,@RequestBody  ProductPojo pojo) {

        System.out.println("---------------"+ pojo.toString());
        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 商户"+SUPPLIER_ID);
        }

        Supplier supplier = supplierOptional.get();


        if(pojo.getType().equals(EnumProductType.Daytour)){

            List<Campaign> campaignList = Arrays.asList();

            if(pojo.getDay_tour().getCampaigns().size()< 1){
                throw new BookNotFoundException(Enumfailures.resource_not_found,pojo.getDay_tour().getCampaigns()+ "必须包含活动");

            }

            campaignList = campaignRepository.findAllById(new HashSet(pojo.getDay_tour().getCampaigns()));
            if(campaignList.size() != pojo.getDay_tour().getCampaigns().size()){

                throw new BookNotFoundException("",pojo.getDay_tour().getCampaigns()+ "有不存在的 campaigns");
            }


            Pair<Product,Supplier> productSupplierPair=  productService.createProduct(supplier,pojo,campaignList);

            Product product = productSupplierPair.getValue0();

            ValueListItemReq valueListItemReq = new ValueListItemReq();
            valueListItemReq.setValue(product.getId()+"");

            valueListService.addItmeByListName(EnumValueListDefault.High_Quality_Product_recommendation,valueListItemReq);


            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(product.getId())
                    .toUri();
            return ResponseEntity.created(uri)
                    .body(ProductResp.from(product));
        }




 /*       List<Campaign> campaignList = Arrays.asList();
        if(pojo.getType().equals(EnumProductType.Daytour)){

        }*/




/*        if(pojo.getType().equals(EnumProductType.Attraction)){
            Optional<Attraction> attractionOptional = attractionRepository.findById(pojo.getAttraction().getId());
            if(attractionOptional.isEmpty()){
                throw new BookNotFoundException(Enumfailures.not_found,"找不到景区");
            }
        }*/










        Pair<Product,Supplier> product=  productService.createProduct(supplier,pojo,Arrays.asList());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getValue0().getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(ProductResp.from(product));


    }




    @Operation(summary = "2、创建Product对象")
    @PostMapping(value = "/supplier/{SUPPLIER_ID}/products_pass", produces = "application/json")
    public ResponseEntity<ProductResp> createProductPass(@PathVariable long SUPPLIER_ID,@RequestBody @Valid ProductPassCreatePojo pojo) {

        System.out.println("---------------"+ pojo.toString());
        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 商户"+SUPPLIER_ID);
        }

        Supplier supplier = supplierOptional.get();


            List<ComponentRight> campaignList = componentRightRepository.findAllById(pojo.getPass().getRoyalties().stream().map(e->e.getComponent_right()).collect(Collectors.toList()));
            if(campaignList.size() != pojo.getPass().getRoyalties().size()){
                throw new BookNotFoundException("",pojo.getPass().getRoyalties().stream().map(e->e.getComponent_right()).collect(Collectors.toList())+ "有不存在的 campaigns");
            }



            Pair<Product,Supplier> product=  productService.createPass(supplier,pojo,campaignList);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(product.getValue0().getId())
                    .toUri();
            return ResponseEntity.created(uri)
                    .body(ProductResp.from(product));


    }




    @Operation(summary = "2、创建Product对象")
    @PostMapping(value = "/suppler/{SUPPLIER_ID}/products_atraction", produces = "application/json")
    public ResponseEntity<Product> createProduct_for_attraction(@PathVariable long SUPPLIER_ID,@RequestBody @Valid ProductPojo pojo) {

        System.out.println("---------------"+ pojo.toString());
        Optional<Supplier> validatorOptional = supplierRepository.findById(SUPPLIER_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 商户"+SUPPLIER_ID);
        }

        Supplier supplier = validatorOptional.get();


        if(pojo.getType().equals(EnumProductType.Attraction)){
            Optional<Attraction> attractionOptional = attractionRepository.findById(pojo.getAttraction().getId());
            if(attractionOptional.isEmpty()){
                throw new BookNotFoundException(Enumfailures.not_found,"找不到景区");
            }
        }



/*
        List<ComponentRight> componentRights = componentRightRepository.findAllById(pojo.getRoyalties().stream().map(e->e.getComponent_right()).collect(Collectors.toList()));

        if((componentRights.size() == 0)  || (componentRights.size() != pojo.getRoyalties().size())){
            throw new BookNotFoundException(Enumfailures.resource_not_found,pojo.getRoyalties().stream().map(e->e.getComponent_right()).collect(Collectors.toList())+ "找不到权益，或权益为空");
        }
*/



        Pair<Product,Supplier> productSupplierPair=  productService.createProduct(supplier,pojo,Arrays.asList());
        Product product = productSupplierPair.getValue0();



/*
        ValueListItemReq valueListItemReq = new ValueListItemReq();
        valueListItemReq.setValue(product.getId()+"");
        valueListService.addItmeByListName(EnumValueListDefault.city_pass_right_recommendation,valueListItemReq);
*/

/*
        List<TempDocument> tempDocuments = tempDocumentRepository.findAllByCodeIn(pojo.getImages().stream().map(e->e.getImage()).distinct().collect(Collectors.toList()));


        if(tempDocuments.size() != 0){
            fileStorageService.saveFromTempDocument(product.getCode(),EnumDocumentType.product_thumb,tempDocuments.get(0));

        }


        List<Document> documents = fileStorageService.saveFromTempDocumentCode(
                product.getCode(),tempDocuments.stream().map(e->{
                    return Pair.with(EnumDocumentType.product_photos,e);
                }).collect(Collectors.toList()));*/


        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(product);


    }




/*

    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/products/{PRODUCT_ID}/page", produces = "application/json")
    public EntityModel Page_editProduct(@PathVariable long PRODUCT_ID) {
        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(PRODUCT_ID,Supplier.class.getSimpleName());
        }
        Product product = validatorOptional.get();
        List<Campaign> campaigns = campaignRepository.findAll();
        List<CampaignAssignToTourProduct> campaignAssignToTourProducts = campaignAssignToTourProductRepository.findByProduct(product.getId());

        List<Long> hasList = campaignAssignToTourProducts.stream().map(x->x.getCampaign()).collect(Collectors.toList());
        EntityModel entityModel = EntityModel.of(Map.of("product_type_list", Arrays.asList(EnumProductType.Daytour).stream().map(x->{

                    EnumResp enumResp = new EnumResp();
                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    enumResp.setSelected(product.getType().equals(x));
                    return enumResp;
                }).collect(Collectors.toList()),
                "product",ProductResp.from(product),
                "campaign_list", campaigns.stream().map(x->{
                    EnumLongIdResp enumResp = new EnumLongIdResp();
                    enumResp.setId(x.getId());
                    enumResp.setText(x.getName());
                    enumResp.setSelected(hasList.contains(x.getId()));
                    return enumResp;
                }).collect(Collectors.toList())
        ));

        entityModel.add(linkTo(methodOn(ProductRestController.class).editProduct(product.getId(),null)).withRel("editProduct"));
        return entityModel;
    }*/




    @PutMapping(value = "/products/{PRODUCT_ID}", produces = "application/json")
    public ResponseEntity<ProductResp> editProduct(@PathVariable long PRODUCT_ID,@RequestBody @Valid ProductEditPojo pojo) {

        System.out.println("---------------"+ pojo.toString());
        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(PRODUCT_ID,Supplier.class.getSimpleName());
        }
        Product product = validatorOptional.get();

        List<Campaign> campaigns = campaignRepository.findAllById(new HashSet(pojo.getCampaigns()));
        if(campaigns.size() != pojo.getCampaigns().size()){

            throw new BookNotFoundException("",pojo.getCampaigns()+ "有不存在的 campaigns");
        }


        Optional<Supplier> optionalSupplier = supplierRepository.findById(product.getSupplierId());
        Supplier supplier = optionalSupplier.get();

        Pair<Product,Supplier> product_pair=  productService.editProduct(product,supplier,pojo,campaigns);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product_pair.getValue0().getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(ProductResp.from(product));


    }

    @PutMapping(value = "/products/{PRODUCT_ID}/info", produces = "application/json")
    public ResponseEntity<ProductResp> editProductInfo(@PathVariable long PRODUCT_ID,@RequestBody @Valid ProductEditResp.InfoTab pojo) {

        System.out.println("---------------"+ pojo.toString());
        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(PRODUCT_ID,Supplier.class.getSimpleName());
        }
        Product product = validatorOptional.get();



        Product product_pair=  productService.editProductInfo(product,pojo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product_pair.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(ProductResp.from(product));


    }


    @PutMapping(value = "/products/{PRODUCT_ID}/componentTab", produces = "application/json")
    public ResponseEntity<ProductResp> editProductComponentTab(@PathVariable long PRODUCT_ID,@RequestBody @Valid ProductEditComponentTabPojo pojo) {

        System.out.println("---------------"+ pojo.toString());
        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(PRODUCT_ID,Supplier.class.getSimpleName());
        }
        Product product = validatorOptional.get();



        Product product_pair=  productService.editProductComponentTab(product,pojo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product_pair.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(ProductResp.from(product));


    }


    @PutMapping(value = "/products/{PRODUCT_ID}/shippingTab", produces = "application/json")
    public ResponseEntity<ProductResp> editProductShippingTab(@PathVariable long PRODUCT_ID,@RequestBody @Valid ProductEditResp.ShippingTab pojo) {

        System.out.println("---------------"+ pojo.toString());
        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(PRODUCT_ID,Supplier.class.getSimpleName());
        }
        Product product = validatorOptional.get();



        Product product_pair=  productService.editProductShippingTab(product,pojo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product_pair.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(ProductResp.from(product));


    }


    @PutMapping(value = "/products/{PRODUCT_ID}/bundleTab", produces = "application/json")
    public ResponseEntity<ProductResp> editProductBundleTab(@PathVariable long PRODUCT_ID,
                                                            @RequestBody @Valid  ProductEditResp.BundleTab bundleTab) {

        System.out.println("---------------"+ bundleTab.toString());
        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(PRODUCT_ID,Supplier.class.getSimpleName());
        }
        Product product = validatorOptional.get();

        Product product_pair=  productService.editProductBundleTab(product,bundleTab);



        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product_pair.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(ProductResp.from(product));


    }

    @PutMapping(value = "/products/{PRODUCT_ID}/aboutTab", produces = "application/json")
    public ResponseEntity<ProductResp> editProductAboutTab(@PathVariable long PRODUCT_ID,@RequestBody @Valid ProductEditResp.AboutTab pojo) {

        System.out.println("---------------"+ pojo.toString());
        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(PRODUCT_ID,Supplier.class.getSimpleName());
        }
        Product product = validatorOptional.get();



        Product product_pair=  productService.editProductAboutTab(product,pojo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product_pair.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(ProductResp.from(product));


    }
    @PutMapping(value = "/products/{PRODUCT_ID}/ruleTab", produces = "application/json")
    public ResponseEntity<ProductResp> editProductRuleTab(@PathVariable long PRODUCT_ID,@RequestBody @Valid ProductResp.RuleTap pojo) {

        System.out.println("---------------"+ pojo.toString());
        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(PRODUCT_ID,Supplier.class.getSimpleName());
        }
        Product product = validatorOptional.get();


        Product product_pair=  productService.editProductRuleTab(product,pojo);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product_pair.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(ProductResp.from(product));


    }




    @PutMapping(value = "/products/{PRODUCT_ID}/rightTab", produces = "application/json")
    public ResponseEntity<ProductResp> editProductRightTab(@PathVariable long PRODUCT_ID,@RequestBody @Valid ProductEditComponentTabPojo pojo) {

        System.out.println("---------------"+ pojo.toString());
        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(PRODUCT_ID,Supplier.class.getSimpleName());
        }
        Product product = validatorOptional.get();



        Product product_pair=  productService.editProductRightTab(product,pojo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product_pair.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(ProductResp.from(product));


    }

    @PutMapping(value = "/products/{PRODUCT_ID}/questionTab", produces = "application/json")
    public ResponseEntity<ProductResp> editQuestionTab(@PathVariable long PRODUCT_ID,@RequestBody @Valid ProductEditResp.QuestionTap pojo) {

        System.out.println("---------------"+ pojo.toString());
        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(PRODUCT_ID,Supplier.class.getSimpleName());
        }
        Product product = validatorOptional.get();



        Product product_pair=  productService.editQuestionTab(product,pojo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product_pair.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(ProductResp.from(product));


    }


    @PutMapping(value = "/products/{PRODUCT_ID}/paymentTab", produces = "application/json")
    public ResponseEntity<ProductResp> editPaymentTab(@PathVariable long PRODUCT_ID,@RequestBody @Valid ProductEditPaymentPojo pojo) {

        System.out.println("---------------"+ pojo.toString());
        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(PRODUCT_ID,Supplier.class.getSimpleName());
        }
        Product product = validatorOptional.get();



        Product product_pair=  productService.editProducPaymentTab(product,pojo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product_pair.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(ProductResp.from(product));


    }



    @PutMapping(value = "/products/{PRODUCT_ID}/publishTab", produces = "application/json")
    public ResponseEntity publishTab(@PathVariable long PRODUCT_ID,@RequestBody @Valid ProductEditPaymentPojo pojo) {

        System.out.println("---------------"+ pojo.toString());
        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(PRODUCT_ID,Supplier.class.getSimpleName());
        }
        Product product = validatorOptional.get();



        Triplet<Boolean,Product,List<Map>> product_pair=  productService.editPublishTab(product,pojo);

        if(product_pair.getValue0()){
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(product_pair.getValue1().getId())
                    .toUri();
            return ResponseEntity.created(uri)
                    .body(ProductResp.from(product_pair.getValue1()));
        }else{
            throw new BookNotFoundException(Enumfailures.resource_not_found,product_pair.getValue2().toString());

        }


    }


    @PutMapping(value = "/products/{PRODUCT_ID}/movieTab", produces = "application/json")
    public ResponseEntity<ProductEditResp.MovieTab> movieTab(@PathVariable long PRODUCT_ID,@RequestBody @Valid ProductEditResp.MovieTab pojo) {

        System.out.println("---------------"+ pojo.toString());

        Optional<Product> optionalProduct = productRepository.findById(PRODUCT_ID);
        if(optionalProduct.isEmpty()) {
            throw new BookNotFoundException(PRODUCT_ID,Supplier.class.getSimpleName());
        }

        Product product = optionalProduct.get();


        Optional<MovieProduct> validatorOptional = movieProductRepository.findByProduct(product.getId());
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(PRODUCT_ID,Supplier.class.getSimpleName());
        }

        MovieProduct passProduct = validatorOptional.get();

        passProduct=  productService.editMovieTab(passProduct,pojo);

        ProductEditResp.MovieTab po = ProductEditResp.MovieTab.from(passProduct);
     //   po.setCover(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.card_cover,passProduct.getUuid()));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(po);


    }


    @PutMapping(value = "/products/{PRODUCT_ID}/movieTabPerformance", produces = "application/json")
    public ResponseEntity<ProductEditResp.MovieTab> movieTabPerformance(@PathVariable long PRODUCT_ID,@RequestBody @Valid ProductEditResp.MovieTab pojo) {

        System.out.println("---------------"+ pojo.toString());

        Optional<Product> optionalProduct = productRepository.findById(PRODUCT_ID);
        if(optionalProduct.isEmpty()) {
            throw new BookNotFoundException(PRODUCT_ID,Supplier.class.getSimpleName());
        }

        Product product = optionalProduct.get();


        Optional<MovieProduct> validatorOptional = movieProductRepository.findByProduct(product.getId());
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(PRODUCT_ID,Supplier.class.getSimpleName());
        }

        MovieProduct passProduct = validatorOptional.get();

        passProduct=  productService.editMovieTabPerformance(passProduct,pojo);

        ProductEditResp.MovieTab po = ProductEditResp.MovieTab.from(passProduct);
        //   po.setCover(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.card_cover,passProduct.getUuid()));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(po);


    }
    @PutMapping(value = "/products/{PRODUCT_ID}/passTab", produces = "application/json")
    public ResponseEntity<ProductEditResp.PassTab> editPassTab(@PathVariable long PRODUCT_ID,@RequestBody @Valid ProductEditResp.PassTab pojo) {

        System.out.println("---------------"+ pojo.toString());

        Optional<Product> optionalProduct = productRepository.findById(PRODUCT_ID);
        if(optionalProduct.isEmpty()) {
            throw new BookNotFoundException(PRODUCT_ID,Supplier.class.getSimpleName());
        }

        Product product = optionalProduct.get();


        Optional<PassProduct> validatorOptional = passProductRepository.findByProduct(product.getId());
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(PRODUCT_ID,Supplier.class.getSimpleName());
        }

        PassProduct passProduct = validatorOptional.get();

        passProduct=  productService.editPassTab(passProduct,pojo);

        ProductEditResp.PassTab po = ProductEditResp.PassTab.from(passProduct);
        po.setCover(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.card_cover,passProduct.getUuid()));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(po);


    }
    @Operation(summary = "3、更改Product对象")
    @PutMapping(value = "/___suppler/{SUPPLIER_ID}/products/{PRODUCT_ID}", produces = "application/json")
    public ProductResp updateComponentRight(@PathVariable int SUPPLIER_ID,@PathVariable int PRODUCT_ID, Map metadata) {
        return null;
    }


    @Operation(summary = "4、删除Product对象")
    @DeleteMapping(value = "/products/{PRODUCT_ID}", produces = "application/json")
    public ResponseEntity<Void> delete(@PathVariable long PRODUCT_ID) {

        productRepository.deleteById(PRODUCT_ID);

        return ResponseEntity.ok().build();
    }











    @GetMapping(value = "/products/{PRODUCT_ID}/booking-rules/page", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_createBookingRule(@PathVariable long PRODUCT_ID) {


        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }
        Product product = validatorOptional.get();


        List<PricingRate> pricingRates = pricingTypeRepository.findByProductId(product.getId());




        Map map = Map.of(

                "range_type_list",EnumAvailabilityRangetype.from(),

                "price_type_list", pricingRates.stream().map(e->{

                    EntityModel pricingTypeEntityModel = EntityModel.of(e);
                    //         pricingTypeEntityModel.add(linkTo(methodOn(BookingRestController.class).Page_createBookingFromPriceType(e.getId())).withRel("Page_createBooking"));


                    return pricingTypeEntityModel;

                }).collect(Collectors.toList())

                //  "status_list", EnumTourBookingStatus_.from()
        );


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(DocumentRestController.class).idScan(null)).withRel("idcardOcr"));

        entityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(product.getId(),null)).withRel("booking"));
        return entityModel;
    }


    @Operation(summary = "5、增加订票规则")
    @PostMapping(value = "/products/{PRODUCT_ID}/booking-rules", produces = "application/json")
    public List<BookingRule> addBookingRule(@PathVariable long PRODUCT_ID, @RequestBody List<BookingRulePojoFuck> pojo) {
        Optional<Product> productOptional = productRepository.findById(PRODUCT_ID);

        if(productOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }
        Product product = productOptional.get();


        List<BookingRuleVO> bookingRuleVOList  = productService.addBookingRuleWithValidate(product,pojo);

        List<BookingRule> bookingRule  = productService.addBookingRule(product,bookingRuleVOList);


        return bookingRule;
    }


    @Operation(summary = "5、增加订票规则")
    @PutMapping(value = "/products/{PRODUCT_ID}/availabilityTab", produces = "application/json")
    public Product editProductAvailabilityTab(@PathVariable long PRODUCT_ID, @RequestBody ProductEditResp.AvailabilityTab pojo) {
        Optional<Product> productOptional = productRepository.findById(PRODUCT_ID);

        if(productOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }
        Product product = productOptional.get();


        List<BookingRuleVO> bookingRuleVOList  = productService.addBookingRuleWithValidate(product,pojo.getRanges());

        Pair<Product,List<BookingRule>> bookingRule  = productService.editProductAvailabilityTab(product,bookingRuleVOList,pojo);


        return bookingRule.getValue0();
    }













    public class addComponentRightPojo{
        private long componentRightId;
        private EnumProductComponentSource sourceType; // own, partner

        private Long supplier; //结算账号

        public Long getSupplier() {
            return supplier;
        }

        public void setSupplier(Long supplier) {
            this.supplier = supplier;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        private long recipient; //结算账号
        private Long quantity; //结算账号

        public Long getQuantity() {
            return quantity;
        }

        public void setQuantity(Long quantity) {
            this.quantity = quantity;
        }

        public EnumProductComponentSource getSourceType() {
            return sourceType;
        }

        public void setSourceType(EnumProductComponentSource sourceType) {
            this.sourceType = sourceType;
        }

        private String note;

        public long getComponentRightId() {
            return componentRightId;
        }

        public void setComponentRightId(long componentRightId) {
            this.componentRightId = componentRightId;
        }


        List<RoyaltyRulePojo> royaltyRules;

        public List<RoyaltyRulePojo> getRoyaltyRules() {
            return royaltyRules;
        }

        public void setRoyaltyRules(List<RoyaltyRulePojo> royaltyRules) {
            this.royaltyRules = royaltyRules;
        }

        public long getRecipient() {
            return recipient;
        }

        public void setRecipient(long recipient) {
            this.recipient = recipient;
        }
    }






    @Operation(summary = "6、增加权益")
    @PostMapping(value = "/products/{PRODUCT_ID}/components", produces = "application/json")
    public Component addComponentRight(@PathVariable long PRODUCT_ID, addComponentRightPojo pojo) {
        Optional<Product> optionalProduct = productRepository.findById(PRODUCT_ID);
        if(optionalProduct.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }
        Product product = optionalProduct.get();

        Component component  = productService.addComponent(product,pojo);

        return component;
    }







    @Operation(summary = "7、对产品权益增加分容规则")
    @PostMapping(value = "products/{PRODUCT_ID}/componet_rights/{COMPONENT_RIGHTS_ID}/royalty_rules", produces = "application/json")
    public RoyaltyRule addRoyaltyRuleToComponentRight(@PathVariable int PRODUCT_ID,@PathVariable int COMPONENT_RIGHTS_ID, RoyaltyRulePojo pojo) throws Exception {
        Optional<ComponentRight> optionalProduct = productService.getByComponentRightForProduct(PRODUCT_ID,COMPONENT_RIGHTS_ID);
        RoyaltyRule bookingRule  = productService.addRoyaltyRuleToComponentRight(optionalProduct.get(),pojo);
        return bookingRule;
    }




    @Operation(summary = "8、对产品增加问题")
    @PostMapping(value = "products/{PRODUCT_ID}/questions", produces = "application/json")
    public BookingQuestion addQuestion(@PathVariable int PRODUCT_ID, BookingQuestionPojo pojo) throws Exception {
        Optional<Product> optionalProduct = productService.getByProductId(PRODUCT_ID);
        BookingQuestion bookingRule  = productService.addBookingQuestion(optionalProduct.get(),pojo);
        return bookingRule;
    }

    @Operation(summary = "9、获取 Question 列表 GET")
    @GetMapping(value = "products/{PRODUCT_ID}/questions", produces = "application/json")
    public List<BookingQuestion> getQuestion(@PathVariable int PRODUCT_ID) throws Exception {
        Optional<Product> optionalProduct = productService.getByProductId(PRODUCT_ID);
        List<BookingQuestion> bookingQuestion  = productService.listQuestions(optionalProduct.get());
        return bookingQuestion;
    }




/*    @Operation(summary = "10、为产品添加评论")
    @PostMapping(value = "products/{PRODUCT_ID}/comments", produces = "application/json")
    public Comment addComments(@PathVariable int PRODUCT_ID, CommentPojo commentPojo) throws Exception {
        Optional<Product> optionalProduct = productService.getByProductId(PRODUCT_ID);
        Comment bookingQuestion  = productService.addComment(optionalProduct.get(),commentPojo);
        return bookingQuestion;
    }*/





/*    @Operation(summary = "10、为产品添加评论")
    @PostMapping(value = "products/{PRODUCT_ID}/campaigns", produces = "application/json")
    public Comment addCampaigns(@PathVariable long PRODUCT_ID, CommentPojo commentPojo)  {
        Optional<Product> optionalProduct = productRepository.findById(PRODUCT_ID);
        if(optionalProduct.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }
        Comment bookingQuestion  = productService.addComment(optionalProduct.get(),commentPojo);
        return bookingQuestion;
    }*/



}