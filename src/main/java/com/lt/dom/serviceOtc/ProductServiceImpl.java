package com.lt.dom.serviceOtc;

import com.lt.dom.controllerOct.ProductRestController;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcReq.product.ProductGiftVoucherPojo;
import com.lt.dom.otcenum.EnumProductType;
import com.lt.dom.otcenum.EnumWhenSettle;
import com.lt.dom.repository.*;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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



    public Page<Product> listProduct(Supplier supplier, Pageable pageable) {



        return productRepository.findBySupplierId(supplier.getId(),pageable);
    }







    public Pair<Product,Supplier> createProduct(Supplier supplier, ProductPojo pojo) {
        Product product = new Product();

       // product.setN(pojo.getName());
        product.setType(pojo.getType());
        product.setCode(idGenService.productNo());
        product.setSupplierId(supplier.getId());
        product.setName(pojo.getName());
        product.setName_long(pojo.getName_long());

        product = productRepository.save(product);


        if(product.getType().equals(EnumProductType.Daytour)){
            Tour tour = new Tour();

            tour.setTour_name(pojo.getName());
            tour.setTour_name_long(pojo.getName_long());
            tour = tourRepository.save(tour);
            product.setRaletiveId(tour.getId());
            product = productRepository.save(product);
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
            List<PricingType>  priceTyps = pojo.getPrices().stream().map(x->{

                PricingType pricingType = new PricingType();
                pricingType.setProductId(finalProduct.getId());
                switch (x.getType()){
                    case ByPerson :{
                        pricingType.setPrice(x.getByPerson().getPrice());
                        pricingType.setBy(x.getBy());
                    };
                    case ByItem :{
                        pricingType.setLable(x.getByItem().getLable());
                        pricingType.setPrice(x.getByItem().getPrice());
                    };
                    case Fixed :{
                        pricingType.setPrice(x.getFixed().getPrice());
                    };
                    case ByHour :{
                        pricingType.setPrice(x.getByHour().getPrice());
                        pricingType.setMax(x.getByHour().getMax());
                        pricingType.setMin(x.getByHour().getMin());
                    };
                    case ByDay:{
                        pricingType.setPrice(x.getByHour().getPrice());
                        pricingType.setMax(x.getByHour().getMax());
                        pricingType.setMin(x.getByHour().getMin());
                    };
                    default:

                }


                return pricingType;
            }).collect(Collectors.toList());


            pricingTypeRepository.saveAll(priceTyps);
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

    public BookingRule addBookingRule(Product product, BookingRuleDeparturePojo pojo) {

        BookingRule bookingRule = new BookingRule();
        bookingRule.setProductId(product.getId());

        bookingRuleRepository.save(bookingRule);

        return bookingRuleRepository.save(bookingRule);
    }



    //为产品增加 权益，一个产品可以有多个权益，这些权益 可以是别家的，也可以是自家的，
    // 增加权益，如果是别人家的，就要分润，自己家的不需要分润，所以就没有 RoyaltyRule了。
    public Component addComponent(Product product, ProductRestController.addComponentRightPojo pojo) {

        Optional<ComponentRight> optionalComponentRight = componentRightRepository.findById(pojo.getComponentRightId());

        optionalComponentRight.isPresent();
        ComponentRight componentRight = optionalComponentRight.get();



        Component component = new Component();
        component.setSupplier(pojo.getSupplier());
        component.setProductId(product.getId());
        component.setSupplierId(componentRight.getSupplierId());
        component.setRecipient(pojo.getRecipient());
        component.setComponentRightId(pojo.getComponentRightId());
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
            royaltyRule.setComponentRightId(component.get().getComponentRightId());
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

    public Optional<Component> getComponentById(long product_id, int component_rights_id) {
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
}
