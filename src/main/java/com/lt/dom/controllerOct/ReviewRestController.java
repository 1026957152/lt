package com.lt.dom.controllerOct;

import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.Review;
import com.lt.dom.otcReq.ReviewReq;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.ReviewRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class ReviewRestController {




    @Autowired
    private ReviewRepository reviewRepository;


    @PostMapping(value = "/reviews/{REVIEW_ID}/approve", produces = "application/json")
    public Review createReviews(@PathVariable long REVIEW_ID, @RequestBody ReviewReq pojo) {



        Optional<Review> validatorOptional = reviewRepository.findById(REVIEW_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(REVIEW_ID,"找不到审核");

        }

        Review review = validatorOptional.get();
        review.setOpen(false);
        review.setClosedReason("dddddddddddd");
        return reviewRepository.save(review);

    }

}