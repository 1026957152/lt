package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.EnumLongIdResp;
import com.lt.dom.OctResp.EnumResp;
import com.lt.dom.OctResp.ProductResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class ProductRestController {


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



    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/products/{PRODUCT_ID}", produces = "application/json")
    public EntityModel<ProductResp> getProduct(@PathVariable long PRODUCT_ID) {

        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);


        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }
        Product product = validatorOptional.get();

        EntityModel entityModel = null;
        if(product.getType().equals(EnumProductType.Daytour)){

            Optional<Tour> tour = tourRepository.findByProduct(product.getId());

            List<CampaignAssignToTourProduct> campaignAssignToTourProducts = campaignAssignToTourProductRepository.findByTourId(tour.get().getId());

            List<Campaign> campaigns = campaignRepository.findAllById(campaignAssignToTourProducts.stream().map(x->x.getCampaign()).collect(Collectors.toList()));

            entityModel = EntityModel.of(ProductResp.dayTourFrom(product,tour.get(),campaigns));

        }else{
            entityModel = EntityModel.of(ProductResp.from(product));

        }


        entityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(validatorOptional.get().getId())).withSelfRel());
      //  entityModel.add(linkTo(methodOn(ProductRestController.class).addCampaigns(validatorOptional.get().getId(),null)).withRel("addCampaign"));

        return entityModel;


    }

    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/suppler/{SUPPLIER_ID}/products/page", produces = "application/json")
    public EntityModel Page_pageProduct(@PathVariable long SUPPLIER_ID) {

        EntityModel entityModel = EntityModel.of(Map.of("status_list", Arrays.stream(EnumProductStatus.values()).map(x->{

                    EnumResp enumResp = new EnumResp();

                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
                }).collect(Collectors.toList())
        ));

        entityModel.add(linkTo(methodOn(TourCampaignRestController.class).pageReservation(null,null)).withRel("upload_bussiness_license_url"));
        return entityModel;
    }



    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/suppler/{SUPPLIER_ID}/products", produces = "application/json")
    public PagedModel pageProduct(@PathVariable long SUPPLIER_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<ProductResp>> assembler) {

        Optional<Supplier> validatorOptional = supplierRepository.findById(SUPPLIER_ID);
        if(validatorOptional.isEmpty()) {

            throw new BookNotFoundException("找不到供应商","找不到供应商");
        }



          return  assembler.toModel(productService.listProduct(validatorOptional.get(),pageable).map(x->{

              EntityModel entityModel= EntityModel.of(ProductResp.from(Pair.with(x,validatorOptional.get())));
              entityModel.add(linkTo(methodOn(ProductRestController.class).delete(x.getId())).withRel("delete"));
              entityModel.add(linkTo(methodOn(ProductRestController.class).Page_editProduct(x.getId())).withRel("Page_edit"));


              entityModel.add(linkTo(methodOn(ProductRestController.class).editProduct(x.getId(),null)).withRel("edit"));
              entityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(x.getId())).withSelfRel());



                      return entityModel;
                }));


    }













    @GetMapping(value = "/supplier/{SUPPLIER_ID}/products/parameters", produces = "application/json")
    public EntityModel<Map<String,Object>> createProductParameters(@PathVariable long SUPPLIER_ID) {

        List<Campaign> products = campaignRepository.findAll();


        EntityModel entityModel = EntityModel.of(Map.of(
                "product_type_list", Arrays.asList(EnumProductType.Daytour).stream().map(x->{
                    EnumResp enumResp = new EnumResp();
                    enumResp.setId(x.name());
                    //  enumResp.setName(x.name());
                    enumResp.setText(x.toString());

                    return enumResp;
                }).collect(Collectors.toList()),
                "price_type_list", Arrays.stream(EnumProductPricingType.values()).map(x->{
                    EnumResp enumResp = new EnumResp();
                    enumResp.setId(x.name());
                    //  enumResp.setName(x.name());
                    enumResp.setText(x.toString());

                    if(x.equals(EnumProductPricingType.ByPerson)){
                        enumResp.setSubitems(Arrays.stream(EnumProductPricingTypeByPerson.values()).map(x_by_person->{
                            EnumResp enumResp_by_person = new EnumResp();
                            enumResp_by_person.setId(x_by_person.name());
                            //  enumResp.setName(x.name());
                            enumResp_by_person.setText(x_by_person.toString());
                            return enumResp_by_person;
                        }).collect(Collectors.toList()));

                    }
                    return enumResp;
                }).collect(Collectors.toList()),
                "campaign_list", products.stream().map(x->{
                    EnumLongIdResp enumResp = new EnumLongIdResp();
                    enumResp.setId(x.getId());
                    enumResp.setText(x.getName());
                    return enumResp;
                }).collect(Collectors.toList())));

        entityModel.add(linkTo(methodOn(DocumentRestController.class).createDocuments(null)).withRel("upload_bussiness_license_url"));
        return entityModel;
    }
    @Operation(summary = "2、创建Product对象")
    @PostMapping(value = "/suppler/{SUPPLIER_ID}/products", produces = "application/json")
    public ResponseEntity<ProductResp> createProduct(@PathVariable long SUPPLIER_ID,@RequestBody @Valid ProductPojo pojo) {

        System.out.println("---------------"+ pojo.toString());
        Optional<Supplier> validatorOptional = supplierRepository.findById(SUPPLIER_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(SUPPLIER_ID,Supplier.class.getSimpleName());
        }


  /*      default boolean existsAllById(Set<UUID> ids) {
            return countAllByIdIn(ids).equals(ids.size());
        }*/

        if(pojo.getType().equals(EnumProductType.Daytour)){
            if(pojo.getCampaigns().size()< 1){
                throw new BookNotFoundException("",pojo.getCampaigns()+ "必须包含活动");

            }
        }
        List<Campaign> campaigns = campaignRepository.findAllById(new HashSet(pojo.getCampaigns()));
        if(campaigns.size() != pojo.getCampaigns().size()){

            throw new BookNotFoundException("",pojo.getCampaigns()+ "有不存在的 campaigns");
        }

                Pair<Product,Supplier> product=  productService.createProduct(validatorOptional.get(),pojo,campaigns);
                URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(product.getValue0().getId())
                        .toUri();
                return ResponseEntity.created(uri)
                        .body(ProductResp.from(product));


    }





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
        EntityModel entityModel = EntityModel.of(Map.of("product_type_list", Arrays.stream(EnumProductType.values()).map(x->{

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
    }




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




    @Operation(summary = "5、增加订票规则")
    @PostMapping(value = "supplers/{SUPPLIER_ID}/products/{PRODUCT_ID}/booking-rules", produces = "application/json")
    public Product addBookingRule(@PathVariable long SUPPLIER_ID, @PathVariable long PRODUCT_ID, BookingRuleDeparturePojo pojo) {
        Optional<Product> product = productService.getById(SUPPLIER_ID,PRODUCT_ID);

        BookingRule bookingRule  = productService.addBookingRule(product.get(),pojo);


        return null;
    }












    public class addComponentRightPojo{
        private long componentRightId;
        private EnumProductComponentSource supplier; // own, partner
        private long recipient; //结算账号
        private int quantity; //结算账号

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public EnumProductComponentSource getSupplier() {
            return supplier;
        }

        public void setSupplier(EnumProductComponentSource supplier) {
            this.supplier = supplier;
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
    @PostMapping(value = "supplers/{SUPPLIER_ID}/products/{PRODUCT_ID}/components", produces = "application/json")
    public Component addComponentRight(@PathVariable long SUPPLIER_ID, @PathVariable int PRODUCT_ID, addComponentRightPojo pojo) {
        Optional<Product> optionalProduct = productService.getById(SUPPLIER_ID,PRODUCT_ID);
        Component bookingRule  = productService.addComponent(optionalProduct.get(),pojo);
        return bookingRule;
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




    @Operation(summary = "10、为产品添加评论")
    @PostMapping(value = "products/{PRODUCT_ID}/comments", produces = "application/json")
    public Comment addComments(@PathVariable int PRODUCT_ID, CommentPojo commentPojo) throws Exception {
        Optional<Product> optionalProduct = productService.getByProductId(PRODUCT_ID);
        Comment bookingQuestion  = productService.addComment(optionalProduct.get(),commentPojo);
        return bookingQuestion;
    }





    @Operation(summary = "10、为产品添加评论")
    @PostMapping(value = "products/{PRODUCT_ID}/campaigns", produces = "application/json")
    public Comment addCampaigns(@PathVariable long PRODUCT_ID, CommentPojo commentPojo)  {
        Optional<Product> optionalProduct = productRepository.findById(PRODUCT_ID);
        if(optionalProduct.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }
        Comment bookingQuestion  = productService.addComment(optionalProduct.get(),commentPojo);
        return bookingQuestion;
    }



}