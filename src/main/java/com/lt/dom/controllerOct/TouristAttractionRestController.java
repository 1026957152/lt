package com.lt.dom.controllerOct;

import com.lt.dom.oct.Attraction;
import com.lt.dom.oct.Feature;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.TouristAttraction;
import com.lt.dom.repository.AttractionRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.TouristAttractionRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.TouristAttractionServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.print.Pageable;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/oct")
public class TouristAttractionRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private TouristAttractionServiceImpl touristAttractionService;
    @Autowired
    private TouristAttractionRepository touristAttractionRepository;

}