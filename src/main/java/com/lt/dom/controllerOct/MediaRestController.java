package com.lt.dom.controllerOct;


import com.lt.dom.OctResp.MediaResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.MediaGenralEditResp;
import com.lt.dom.otcReq.MediaGenralReq;

import com.lt.dom.otcenum.EnumMediaType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.MediaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class MediaRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private MediaRepository MediaRepository;
    @Autowired
    private MediaServiceImpl MediaService;

    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private SeatingLayoutRepository seatingLayoutRepository;




    @Autowired
    private ShowtimeRepository showtimeRepository;

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/medias", produces = "application/json")
    public PagedModel getMediaList(@PathVariable long SUPPLIER_ID ,
                                   @PageableDefault(sort = {"createdDate", "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable,

                                   PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

/*        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Product product = validatorOptional.get();*/

        Page<Media> bookingRuleList = MediaRepository.findAll(pageable);

        return assembler.toModel(bookingRuleList.map(e->{

            MediaResp mediaResp = MediaResp.from(e);

            EntityModel entityModel = EntityModel.of(mediaResp);
            entityModel.add(linkTo(methodOn(MediaRestController.class).getMedia(e.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(MediaRestController.class).editMedia(e.getId(),null)).withRel("edit"));


            return entityModel;
        }));

    }


    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/medias", produces = "application/json")
    public EntityModel<Media> createMedia(@PathVariable long SUPPLIER_ID , @RequestBody @Valid MediaGenralReq MediaReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Media Media = MediaService.create(supplier,MediaReq);


        EntityModel entityModel = EntityModel.of(Media);

        return entityModel;

    }


    @PutMapping(value = "/medias/{Media_ID}", produces = "application/json")
    public EntityModel<Media> editMedia(@PathVariable long Media_ID ,@RequestBody @Valid MediaGenralEditResp MediaReq) {

        Optional<Media> supplierOptional = MediaRepository.findById(Media_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.not_found,"找不到媒体对象");
        }

        Media supplier = supplierOptional.get();


        Media Media = MediaService.update(supplier,MediaReq);


        EntityModel entityModel = EntityModel.of(Media);

        return entityModel;

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/medias/Page_createMedia", produces = "application/json")
    public EntityModel<Media> Page_createMedia(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("type_list", EnumMediaType.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(MediaRestController.class).createMedia(supplier.getId(),null)).withRel("createMedia"));
        entityModel.add(linkTo(methodOn(DocumentRestController.class).uploadDocument(null)).withRel("upload"));


        return entityModel;

    }



    @GetMapping(value = "/medias/{Media_ID}", produces = "application/json")
    public EntityModel<Media> getMedia(@PathVariable long Media_ID) {

        Optional<Media> validatorOptional = MediaRepository.findById(Media_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Media Media = validatorOptional.get();
        MediaResp mediaResp = MediaResp.from(Media);
        EntityModel entityModel = EntityModel.of(mediaResp);
        entityModel.add(linkTo(methodOn(MediaRestController.class).editMedia(Media.getId(),null)).withRel("edit"));
        entityModel.add(linkTo(methodOn(MediaRestController.class).getMedia(Media.getId())).withSelfRel());

        return entityModel;

    }











    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/showtimes_medias", produces = "application/json")
    public EntityModel<Showtime> createShowtime(@PathVariable long SUPPLIER_ID , @RequestBody @Valid ShowtimeReq MediaReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Optional<Media> MediaOptional = MediaRepository.findById(MediaReq.getLayout());

        if(MediaOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到剧目");

        }
        Media Media = MediaOptional.get();

        Optional<Theatre> theatreOptional =theatreRepository.findById(MediaReq.getTheatre());
        if(theatreOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到剧院");

        }
        Theatre theatre = theatreOptional.get();


        Optional<SeatingLayout> seatingLayoutOptional =seatingLayoutRepository.findById(MediaReq.getLayout());
        if(theatreOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到剧院");

        }
        SeatingLayout seatingLayout = seatingLayoutOptional.get();
        Showtime showtime =null;// MediaService.createShowtime(supplier,theatre,Media,seatingLayout,MediaReq);


        EntityModel entityModel = EntityModel.of(showtime);

        return entityModel;

    }
}