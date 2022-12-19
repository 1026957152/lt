package com.lt.dom.controllerOct;

import com.lt.dom.repository.AttractionRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.TouristAttractionRepository;
import com.lt.dom.serviceOtc.TouristAttractionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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