package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.ReferralResp;
import com.lt.dom.OctResp.RewardResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.Referral;
import com.lt.dom.oct.Reward;
import com.lt.dom.otcReq.ReferralPojo;
import com.lt.dom.otcReq.RewardReq;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.RewardServiceImpl;
import com.lt.dom.vo.AvailabilityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class RewardRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private BookingRuleRepository bookingRuleRepository;
    @Autowired
    private RewardServiceImpl rewardService;



    @GetMapping(value = "rewards/{PRODUCT_ID}/availability", produces = "application/json")
    public List<AvailabilityVO> listAvailability(@PathVariable long PRODUCT_ID) {

        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Product product = validatorOptional.get();

        List<BookingRule> bookingRuleList = bookingRuleRepository.findByProduct(product.getId());

        return null;//availabilityService.getAvailability_(product,bookingRuleList, LocalDate.now().plusDays(10));

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
    @PostMapping(value = "/rewards", produces = "application/json")
    public Reward createReferral(@RequestBody @Valid RewardReq pojo) {


        Reward referral = rewardService.create(pojo);

        return RewardResp.from(referral);

    }

}