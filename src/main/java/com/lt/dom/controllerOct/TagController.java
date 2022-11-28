package com.lt.dom.controllerOct;



import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.PlaceReq;
import com.lt.dom.otcReq.TagReq;

import com.lt.dom.otcenum.EnumRedemptionMethod;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.repository.TagRepository;

import com.lt.dom.serviceOtc.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class TagController {


    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private TagRepository movieRepository;
    @Autowired
    private TagServiceImpl tagService;




    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/tags", produces = "application/json")
    public PagedModel get_TagList(@PathVariable long SUPPLIER_ID ,Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Page<Tag> bookingRuleList = tagRepository.findAll(pageable);

        return assembler.toModel(bookingRuleList.map(e->{

            //_TagResp movieResp = _TagResp.from(e);

            EntityModel entityModel = EntityModel.of(e);
            entityModel.add(linkTo(methodOn(TagController.class).get_Tag(e.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(TagController.class).edit_Tag(e.getId(),null)).withRel("edit"));


            return entityModel;
        }));

    }


    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/tags", produces = "application/json")
    public EntityModel<Tag> create_Tag(@PathVariable long SUPPLIER_ID ,@RequestBody @Valid TagReq _TagReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Tag Tag = tagService.create(supplier,_TagReq);


        EntityModel entityModel = EntityModel.of(Tag);

        return entityModel;

    }


    @PostMapping(value = "/tags/{_Tag_ID}", produces = "application/json")
    public EntityModel<Tag> edit_Tag(@PathVariable long _Tag_ID ,@RequestBody @Valid TagReq _TagReq) {

        Optional<Tag> supplierOptional = tagRepository.findById(_Tag_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Tag supplier = supplierOptional.get();


        Tag Tag = tagService.update(supplier,_TagReq);


        EntityModel entityModel = EntityModel.of(Tag);

        return entityModel;

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/tags/Page_create_Tag", produces = "application/json")
    public EntityModel<Tag> Page_create_Tag(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("redemption_method_list", EnumRedemptionMethod.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(TagController.class).create_Tag(supplier.getId(),null)).withRel("create_Tag"));


        return entityModel;

    }



    @GetMapping(value = "/tags/{_Tag_ID}", produces = "application/json")
    public EntityModel<TagReq> get_Tag(@PathVariable long _Tag_ID) {

        Optional<Tag> validatorOptional = tagRepository.findById(_Tag_ID);
        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");
        }
        Tag tag = validatorOptional.get();
      //  _TagResp _TagResp = _TagResp.from(tag);
        EntityModel entityModel = EntityModel.of(tag);
        entityModel.add(linkTo(methodOn(TagController.class).get_Tag(tag.getId())).withRel("create_Tag"));

        return entityModel;

    }











    @PostMapping(value = "/tags/{TAG_ID}/places", produces = "application/json")
    public EntityModel<Place> apply(@PathVariable long TAG_ID , @RequestBody @Valid PlaceReq movieReq) {

        Optional<Tag> supplierOptional = tagRepository.findById(TAG_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Tag supplier = supplierOptional.get();




        Place place = tagService.createPlace(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(place);

        return entityModel;

    }
}