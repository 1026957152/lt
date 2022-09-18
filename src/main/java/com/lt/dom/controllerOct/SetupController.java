package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.AssetResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.Asset;
import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.Voucher;
import com.lt.dom.repository.AssetRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AssetServiceImpl;
import com.lt.dom.util.ZxingBarcodeGenerator;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class SetupController {


    private ResponseEntity<BufferedImage> okResponse(BufferedImage image) {
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }
    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetServiceImpl assetService;
    @Autowired
    private SupplierRepository supplierRepository;



    @PostMapping(value = "/setup", produces = "application/json")
    public ResponseEntity<EntityModel<AssetResp>> createAsset(@PathVariable long SUPPLIER_ID) {

        Optional<Supplier> validatorOptional = supplierRepository.findById(SUPPLIER_ID);
        if(validatorOptional.isPresent()){
            try {
                Asset asset= assetService.newQr(validatorOptional.get());
                EntityModel<AssetResp> assetResp = AssetResp.from(asset);

                return ResponseEntity.ok(assetResp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new BookNotFoundException(SUPPLIER_ID,Supplier.class.getSimpleName());



    }


}