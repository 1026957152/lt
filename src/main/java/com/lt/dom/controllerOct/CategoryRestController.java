package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.BookingResp;
import com.lt.dom.OctResp.CategoryResp;
import com.lt.dom.OctResp.ProductResp;
import com.lt.dom.OctResp.RegionResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.error.No_voucher_suitable_for_publicationException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingPojo;
import com.lt.dom.otcReq.CategoryPojo;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.CategoryRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.CategoryServiceImpl;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class CategoryRestController {
    @Autowired
    private CategoryServiceImpl categoryService;


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileStorageServiceImpl fileStorageService;


    @GetMapping(value = "/categories", produces = "application/json")
    public PagedModel getMuseumList(
            @PageableDefault(sort = {"createdDate", "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable,
            PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {



        Page<Category> bookingRuleList = categoryRepository.findAll(pageable);


        return assembler.toModel(bookingRuleList.map(e->{

            CategoryResp movieResp = CategoryResp.simpleFrom(e);


            movieResp.setIcon(fileStorageService.loadDocumentWithDefault(EnumDocumentType.category_icon,e.getCode()));

            EntityModel entityModel = EntityModel.of(movieResp);
            entityModel.add(linkTo(methodOn(RegionRestController.class).getMuseum(e.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(RegionRestController.class).editMuseum(e.getId(),null)).withRel("edit"));


            return entityModel;
        }));

    }
    @GetMapping(value = "/categories__", produces = "application/json")
    public CollectionModel<Category> listAvailability() {

        List<Category> validatorOptional = categoryRepository.findAll();




        return CollectionModel.of(validatorOptional)
                .add(linkTo(methodOn(CategoryRestController.class).createCategory(null)).withRel("createCategory"));

    }



    @PostMapping(value = "/categories", produces = "application/json")
    public EntityModel<Category> createCategory(@RequestBody @Valid CategoryPojo pojo) {


        Optional<Category> optionalProduct = categoryRepository.findByName(pojo.getName());

        if(optionalProduct.isPresent()) {
            System.out.println("找不到产品");
            throw new ExistException(Enumfailures.general_exists_error,"已经存在");
        }

        Category category = categoryService.create(pojo);

        return EntityModel.of(categoryRepository.save(category));


    }

    @PutMapping(value = "/categories/{ID}", produces = "application/json")
    public EntityModel<Category> editCategory(@PathVariable long ID,@RequestBody @Valid CategoryPojo pojo) {


        Optional<Category> optionalProduct = categoryRepository.findById(ID);

        if(optionalProduct.isEmpty()) {

            throw new ExistException(Enumfailures.general_exists_error,"已经存在");
        }
        Category category = optionalProduct.get();

        category = categoryService.edit(category,pojo);

        return EntityModel.of(categoryRepository.save(category));


    }


}