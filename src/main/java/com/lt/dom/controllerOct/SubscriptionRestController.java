package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.SupplierResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.Subscription;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.SubscriptionRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.SubscriptionServiceImpl;
import com.lt.dom.vo.AvailabilityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class SubscriptionRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private BookingRuleRepository bookingRuleRepository;
    @Autowired
    private SubscriptionServiceImpl subscriptionService;



    @GetMapping(value = "/subscriptions", produces = "application/json")
    public PagedModel listAvailability(Pageable pageable, PagedResourcesAssembler<EntityModel<Subscription>> assembler) {

        Page<Subscription> validatorOptional = subscriptionRepository.findAll(pageable);


        return assembler.toModel(validatorOptional.map(e->{
            EntityModel entityModel = EntityModel.of(e);
            return entityModel;
        }));

    }


    @GetMapping(value = "/subscriptions/{PRODUCT_ID}/booking-rules", produces = "application/json")
    public Page<BookingRule> getbookingRuleList(@PathVariable long PRODUCT_ID, Pageable pageable) {

        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Product product = validatorOptional.get();

        Page<BookingRule> bookingRuleList = bookingRuleRepository.findByProduct(product.getId(),pageable);

        return bookingRuleList;

    }

}