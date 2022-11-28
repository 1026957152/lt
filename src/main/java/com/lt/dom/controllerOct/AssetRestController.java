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
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/oct")
public class AssetRestController {


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



    @PostMapping(value = "assets/{SUPPLIER_ID}/regenerate", produces = "application/json")
    public ResponseEntity<EntityModel<AssetResp>> regenerate(@PathVariable long SUPPLIER_ID) {

        Assert.notNull(SUPPLIER_ID, "Link must not be null!");


        Optional<Asset> validatorOptional = assetRepository.findById(SUPPLIER_ID);
        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(SUPPLIER_ID,Asset.class.getSimpleName());
        }


        Asset asset= assetService.regenerate(validatorOptional.get());
        EntityModel<AssetResp> assetResp = AssetResp.from(asset);
        return ResponseEntity.ok(assetResp);

    }
    @PostMapping(value = "supplier/{SUPPLIER_ID}/assets/qr", produces = "application/json")
    public ResponseEntity<EntityModel<AssetResp>> createAsset(@PathVariable long SUPPLIER_ID) {

        Assert.notNull(SUPPLIER_ID, "Link must not be null!");

        Optional<Supplier> validatorOptional = supplierRepository.findById(SUPPLIER_ID);
        if(validatorOptional.isPresent()) {
            throw new BookNotFoundException(SUPPLIER_ID,Supplier.class.getSimpleName());

        }

        Asset asset= assetService.getWithNew(validatorOptional.get());
        EntityModel<AssetResp> assetResp = AssetResp.from(asset);
        return ResponseEntity.ok(assetResp);

    }


    @GetMapping(value = "/assets/{ASSET_ID}",   produces = "application/json")
    public ResponseEntity<Asset> getAsset(@PathVariable long ASSET_ID) throws Exception {

        Optional<Asset> validatorOptional = assetRepository.findById(ASSET_ID);
        if(validatorOptional.isPresent()){
            return ResponseEntity.ok(validatorOptional.get());
        }else{
            System.out.println("抛出异常");
            throw new BookNotFoundException(ASSET_ID,Asset.class.getSimpleName());
        }

    }

    @GetMapping(value = "/assets/{ASSET_ID}/qrcodes/download",  produces = MediaType.IMAGE_PNG_VALUE)

    public ResponseEntity<BufferedImage> downloadAsset(@PathVariable long ASSET_ID) throws Exception {

        Optional<Asset> validatorOptional = assetRepository.findById(ASSET_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(ASSET_ID,Asset.class.getSimpleName());

        }
        Asset asset = validatorOptional.get();

        return okResponse(ZxingBarcodeGenerator.generateQRCodeImage(asset.getCode()));


    }











    @GetMapping(value = "assets/qr/{QR_CODE}", produces = "application/json")
    public ResponseEntity<String> listAvailability(@PathVariable long QR_CODE) throws JOSEException {




        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                //  .withIgnorePaths("id")
                .withIgnorePaths("campaign")
                .withIgnorePaths("active")
                .withIgnorePaths("code");

        //   .withMatcher("model", ignoreCase());

        Voucher probe = new Voucher();
        //   probe.setCode(QR_CODE);
        probe.setId(QR_CODE);
        Example<Voucher> example = Example.of(probe,modelMatcher);


        Optional<Voucher> optionalVoucher = voucherRepository.findOne(example);


        System.out.println(JSONObject.valueToString(optionalVoucher.get()));

        if(optionalVoucher.isPresent()){
            // Create an HMAC-protected JWS object with some payload
            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256),
                    new Payload(JSONObject.valueToString(optionalVoucher.get())));

// We need a 256-bit key for HS256 which must be pre-shared
            byte[] sharedKey = new byte[32];
            new SecureRandom().nextBytes(sharedKey);

// Apply the HMAC to the JWS object
            jwsObject.sign(new MACSigner(sharedKey));
// Output in URL-safe format
            System.out.println(jwsObject.serialize());



            return ResponseEntity.ok(jwsObject.serialize());
        }



        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));




    }

}