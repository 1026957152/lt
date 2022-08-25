package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.BookingResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.error.No_voucher_suitable_for_publicationException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingPojo;
import com.lt.dom.otcReq.CategoryPojo;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.CategoryRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
    private CategoryRepository categoryRepository;


    @GetMapping(value = "/categories", produces = "application/json")
    public CollectionModel<Category> listAvailability() {

        List<Category> validatorOptional = categoryRepository.findAll();


        return CollectionModel.of(validatorOptional)
                .add(linkTo(methodOn(CategoryRestController.class).createCategory(null)).withRel("createCategory"));

    }



    @PostMapping(value = "/categories", produces = "application/json")
    public EntityModel<Category> createCategory(@RequestBody @Valid CategoryPojo pojo) {


        Optional<Category> optionalProduct = categoryRepository.findByCategory(pojo.getCategory());

        if(optionalProduct.isPresent()) {
            System.out.println("找不到产品");
            throw new ExistException(Enumfailures.general_exists_error,"已经存在");
        }
        Category category = new Category();
        category.setCategory(pojo.getCategory());
        category.setCode(pojo.getCode());
        category.setName(pojo.getName());
        return EntityModel.of(categoryRepository.save(category));


    }

}