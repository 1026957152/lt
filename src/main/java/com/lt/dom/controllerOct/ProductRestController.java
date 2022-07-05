package com.lt.dom.controllerOct;

import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingRuleDeparturePojo;
import com.lt.dom.otcReq.ComponentRightPojo;
import com.lt.dom.otcReq.ProductPojo;
import com.lt.dom.otcReq.RoyaltyPojo;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.serviceOtc.ProductServiceImpl;
import com.lt.dom.serviceOtc.ValidatorScanServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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


    @Operation(summary = "1、增删改查")
    @GetMapping(value = "/suppler/{SUPPLIER_ID}/products", produces = "application/json")
    public List<Product> listProduct(@PathVariable long SUPPLIER_ID) {

        Optional<Supplier> validatorOptional = supplierRepository.findById(SUPPLIER_ID);
        if(validatorOptional.isPresent()){
            try {
                return productService.listProduct(validatorOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));



    }
    @Operation(summary = "1、增删改查")
    @PostMapping(value = "/suppler/{SUPPLIER_ID}/products", produces = "application/json")
    public Product createCouponTemplate(@PathVariable long SUPPLIER_ID,@RequestBody ProductPojo pojo) {
        Optional<Supplier> validatorOptional = supplierRepository.findById(SUPPLIER_ID);
        if(validatorOptional.isPresent()){
            try {
                return productService.createProduct(validatorOptional.get(),pojo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));




    }
    @Operation(summary = "1、增删改查")
    @PutMapping(value = "/suppler/{SUPPLIER_ID}/products/{PRODUCT_ID}", produces = "application/json")
    public Product updateComponentRight(@PathVariable int SUPPLIER_ID,@PathVariable int PRODUCT_ID, Map metadata) {
        return null;
    }
    @Operation(summary = "1、增删改查")
    @DeleteMapping(value = "/suppler/{SUPPLIER_ID}/products/{PRODUCT_ID}", produces = "application/json")
    public Product createCouponTemplate(@PathVariable String SUPPLIER_ID,@PathVariable int PRODUCT_ID) {
        return null;
    }




    @Operation(summary = "4、增加订票规则")
    @PostMapping(value = "supplers/{SUPPLIER_ID}/products/{PRODUCT_ID}/booking-rules", produces = "application/json")
    public Product addBookingRule(@PathVariable long SUPPLIER_ID, @PathVariable long PRODUCT_ID, BookingRuleDeparturePojo pojo) {
        Optional<Product> product = productService.getById(SUPPLIER_ID,PRODUCT_ID);

        BookingRule bookingRule  = productService.addBookingRule(product.get(),pojo);


        return null;
    }


    @Operation(summary = "2、为子产品或本产品权益添加分润")
    @PostMapping(value = "supplers/{SUPPLIER_ID}/products/{PRODUCT_ID}/royalties", produces = "application/json")
    public Royalty addBookingRule(@PathVariable long SUPPLIER_ID, @PathVariable int PRODUCT_ID, RoyaltyPojo pojo) {
        Optional<Product> optionalProduct = productService.getById(SUPPLIER_ID,PRODUCT_ID);


        Royalty bookingRule  = productService.addRoyalty(optionalProduct.get(),pojo);

        return bookingRule;
    }


    @Operation(summary = "6、增加权益")
    @PostMapping(value = "supplers/{SUPPLIER_ID}/products/{PRODUCT_ID}/componet_rights", produces = "application/json")
    public ComponentRight addComponentRight(@PathVariable long SUPPLIER_ID, @PathVariable int PRODUCT_ID, ComponentRightPojo pojo) {
        Optional<Product> optionalProduct = productService.getById(SUPPLIER_ID,PRODUCT_ID);
        ComponentRight bookingRule  = productService.addComponentRight(optionalProduct.get(),pojo);
        return bookingRule;
    }



/*


    @PostMapping(value = "/componet_rights/{COMPONENT_RIGHTS_ID}/access_validators", produces = "application/json")
    public AccessValidator addComponentRight_Validator(@PathVariable int COMPONENT_RIGHTS_ID, @PathVariable int COUPON_TMPL_ID) {
        AccessValidator componentRight = new AccessValidator();
        return componentRight;
    }
*/

/*

    @GetMapping(value = " /{VALIDATOR_ID}/component_rights", produces = "application/json")
    public List<ComponentRight> listComponentRight(@PathVariable long VALIDATOR_ID) {

        Optional<Validator> validatorOptional = validatorRepository.findById(VALIDATOR_ID);
        if(validatorOptional.isPresent()){
            try {
                return validatorScanService.找出当前验证者管理的权益(validatorOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
    }


    @GetMapping(value = " /{VALIDATOR_ID}/component_right_vonchors", produces = "application/json")
    public List<ComponentRightVounch> listComponentRightVounch(@PathVariable long VALIDATOR_ID) {

        Optional<Validator> validatorOptional = validatorRepository.findById(VALIDATOR_ID);
        if(validatorOptional.isPresent()){
            try {
                return validatorScanService.当前核验者的权益券(validatorOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
    }


*/


/*

*/

}