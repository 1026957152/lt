package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.AssetResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.Asset;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.Supplier;
import com.lt.dom.repository.AssetRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AssetServiceImpl;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import com.lt.dom.util.ZxingBarcodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class AssetRestController {

    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetServiceImpl assetService;
    @Autowired
    private SupplierRepository supplierRepository;



    @PostMapping(value = "supplier/{SUPPLIER_ID}/assets/qr", produces = "application/json")
    public ResponseEntity<AssetResp> createAsset(@PathVariable long SUPPLIER_ID) {

        Optional<Supplier> validatorOptional = supplierRepository.findById(SUPPLIER_ID);
        if(validatorOptional.isPresent()){
            try {
                Asset asset= assetService.newQr(validatorOptional.get());
                AssetResp assetResp = AssetResp.from(asset);

                return ResponseEntity.ok(assetResp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new BookNotFoundException(SUPPLIER_ID,Supplier.class.getSimpleName());



    }


    @GetMapping(value = "/assets/{ASSET_ID}/qrcodes/download",  produces = MediaType.IMAGE_PNG_VALUE)

    public ResponseEntity<BufferedImage> getAsset(@PathVariable long ASSET_ID) throws Exception {

        Optional<Asset> validatorOptional = assetRepository.findById(ASSET_ID);
        if(validatorOptional.isPresent()){
            return okResponse(ZxingBarcodeGenerator.generateQRCodeImage(validatorOptional.get().getUrl()));

        }
        System.out.println("抛出异常");
        throw new BookNotFoundException(ASSET_ID,Asset.class.getSimpleName());



    }

    private ResponseEntity<BufferedImage> okResponse(BufferedImage image) {
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
}