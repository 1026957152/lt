package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.ProductResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.EnumProductComponentSource;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.serviceOtc.ProductServiceImpl;
import com.lt.dom.serviceOtc.ValidatorScanServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/oct")
public class ProductRestController {


    @Autowired
    private ValidatorScanServiceImpl validatorScanService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private VonchorServiceImpl vonchorService;

    @Autowired
    private SupplierRepository supplierRepository;


    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/suppler/{SUPPLIER_ID}/products", produces = "application/json")
    public Page<ProductResp> listProduct(@PathVariable long SUPPLIER_ID, Pageable pageable) {

        Optional<Supplier> validatorOptional = supplierRepository.findById(SUPPLIER_ID);
        if(validatorOptional.isPresent()){
            try {
                return productService.listProduct(validatorOptional.get(),pageable).map(x->ProductResp.from(Pair.with(x,validatorOptional.get())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
    }







    @Operation(summary = "2、创建Product对象")
    @PostMapping(value = "/suppler/{SUPPLIER_ID}/products", produces = "application/json")
    public ResponseEntity<ProductResp> createProduct(@PathVariable long SUPPLIER_ID,@RequestBody @Valid ProductPojo pojo) {

        System.out.println("---------------"+ pojo.toString());
        Optional<Supplier> validatorOptional = supplierRepository.findById(SUPPLIER_ID);
        if(validatorOptional.isPresent()){
                Pair<Product,Supplier> product=  productService.createProduct(validatorOptional.get(),pojo);
                URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(product.getValue0().getId())
                        .toUri();
                return ResponseEntity.created(uri)
                        .body(ProductResp.from(product));
        }else{
            throw new BookNotFoundException(SUPPLIER_ID,Supplier.class.getSimpleName());
        }


    }
    @Operation(summary = "3、更改Product对象")
    @PutMapping(value = "/___suppler/{SUPPLIER_ID}/products/{PRODUCT_ID}", produces = "application/json")
    public ProductResp updateComponentRight(@PathVariable int SUPPLIER_ID,@PathVariable int PRODUCT_ID, Map metadata) {
        return null;
    }
    @Operation(summary = "4、删除Product对象")
    @DeleteMapping(value = "/suppler/{SUPPLIER_ID}/products/{PRODUCT_ID}", produces = "application/json")
    public void delete(@PathVariable String SUPPLIER_ID,@PathVariable int PRODUCT_ID) {

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








}