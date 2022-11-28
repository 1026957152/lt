package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.*;
import com.lt.dom.OctResp.home.HomeTrustResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.RewardReq;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.RewardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class RewardRestController {

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private FileStorageServiceImpl fileStorageService;


    @Autowired
    private BookingRuleRepository bookingRuleRepository;
    @Autowired
    private RewardServiceImpl rewardService;




    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/rewards", produces = "application/json")
    public PagedModel getTheatreList(@PathVariable long SUPPLIER_ID , Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();



        Page<Reward> bookingRuleList = rewardRepository.findAll(pageable);

        return assembler.toModel(bookingRuleList.map(e->{

            RewardResp theatreResp = RewardResp.from(e);

            EntityModel entityModel = EntityModel.of(theatreResp);
            entityModel.add(linkTo(methodOn(RewardRestController.class).getExhibit(e.getId())).withSelfRel());
          //  entityModel.add(linkTo(methodOn(RewardRestController.class).editTheatre(e.getId(),null)).withRel("edit"));
            return entityModel;
        }));

    }


    @GetMapping(value = "rewards/{PRODUCT_ID}/booking-rules", produces = "application/json")
    public Page<BookingRule> getbookingRuleList(@PathVariable long PRODUCT_ID, Pageable pageable) {

        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Product product = validatorOptional.get();

        Page<BookingRule> bookingRuleList = bookingRuleRepository.findByProduct(product.getId(),pageable);

        return bookingRuleList;

    }
    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/rewards", produces = "application/json")
    public RewardResp createReferral(@PathVariable long SUPPLIER_ID,@RequestBody @Valid RewardReq pojo) {


            Optional<Supplier> validatorOptional = supplierRepository.findById(SUPPLIER_ID);

            if(validatorOptional.isEmpty()){
                throw new BookNotFoundException(Enumfailures.not_found,"找不到供应商");
            }

        Supplier product = validatorOptional.get();
        Reward referral = rewardService.create(product,pojo);

        return RewardResp.from(referral);

    }




    @GetMapping(value = "/rewards/{REWARD_ID}", produces = "application/json")
    public EntityModel<Reward> getExhibit(@PathVariable long REWARD_ID) {

        Optional<Reward> supplierOptional = rewardRepository.findById(REWARD_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Reward exhibition = supplierOptional.get();

      //  List<Artwork> artworks = collectionItemRepository.findAll();

        RewardResp exhibitionReq = RewardResp.from(exhibition);

        MediaResp mediaResp = new MediaResp();
        mediaResp.setPortrait(fileStorageService.loadDocumentWithDefault(EnumDocumentType.makeplan_resource,exhibition.getCode()));

        exhibitionReq.setMedia(mediaResp);


       /*
        exhibitionReq.setArtworkss(artworks.stream().map(e->{



            ArtworkResp artworkResp = ArtworkResp.from(e);



            EntityModel entityModel = EntityModel.of(artworkResp);
            //  entityModel.add(linkTo(methodOn(MuseumRestController.class).getExhibit(e.getId())).withSelfRel());

            return entityModel;
        }).collect(Collectors.toList()));

*/

        EntityModel entityModel = EntityModel.of(exhibitionReq);

        return entityModel;

    }

    @GetMapping(value = "/rewards/home", produces = "application/json")
    public EntityModel<HomeTrustResp> home() {


        HomeTrustResp exhibition = new HomeTrustResp();

        List<Reward> artworks = rewardRepository.findAll();


/*        MediaResp mediaResp = new MediaResp();
        mediaResp.setPortrait(fileStorageService.loadDocumentWithCodeToUrl(EnumDocumentType.artwork_thumbnail,exhibition.getCode()));
        mediaResp.setAudio(fileStorageService.loadDocumentWithCodeToUrl(EnumDocumentType.artwork_audio,exhibition.getCode()));
        exhibition.setMedia(mediaResp);*/


        exhibition.setRewards(artworks.stream().map(e->{

            RewardResp rewardResp = RewardResp.from(e);

            EntityModel entityModel = EntityModel.of(rewardResp);
            entityModel.add(linkTo(methodOn(RewardRestController.class).getExhibit(e.getId())).withSelfRel());

            return entityModel;

        }).collect(Collectors.toList()));



        exhibition.setTaohuaScore(1000);
        exhibition.setTaohuaLevel("A");

        exhibition.setTaohuaImage("http://yulinmei.cn:8080/oct/files/桃花分.png");
        EntityModel entityModel = EntityModel.of(exhibition);

        return entityModel;

    }

}