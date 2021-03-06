package com.lt.dom.controllerOct.product;

import com.lt.dom.OctResp.ProductResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcReq.product.ProductGiftVoucherPojo;
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

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/oct/gift")
public class GiftCardRestController {


    @Autowired
    private ValidatorScanServiceImpl validatorScanService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private VonchorServiceImpl vonchorService;

    @Autowired
    private SupplierRepository supplierRepository;







    @Operation(summary = "2、创建Product对象")
    @PostMapping(value = "/suppler/{SUPPLIER_ID}/products", produces = "application/json")
    public ResponseEntity<ProductResp> createProduct(@PathVariable long SUPPLIER_ID,@RequestBody ProductGiftVoucherPojo pojo) {
        Optional<Supplier> validatorOptional = supplierRepository.findById(SUPPLIER_ID);
        if(validatorOptional.isPresent()){
            try {
                Product product=  productService.createProductGiftVoucher(validatorOptional.get(),pojo);

                URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(product.getId())
                        .toUri();
                return ResponseEntity.created(uri)
                        .body(ProductResp.from(Pair.with(product,null)));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
   


        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));




    }
    @Operation(summary = "3、更改Product对象")
    @PutMapping(value = "/suppler/{SUPPLIER_ID}/products/{PRODUCT_ID}", produces = "application/json")
    public ProductResp updateComponentRight(@PathVariable int SUPPLIER_ID,@PathVariable int PRODUCT_ID, Map metadata) {
        return null;
    }



}