package com.lt.dom.controllerOct;

import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.Review;
import com.lt.dom.otcReq.ReviewReq;
import com.lt.dom.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class ReviewRestController {




    @Autowired
    private ReviewRepository reviewRepository;


    @PostMapping(value = "/reviews/{REVIEW_ID}/approve", produces = "application/json")
    public EntityModel<Review> createReviews(@PathVariable long REVIEW_ID, @RequestBody ReviewReq pojo) {


        Optional<Review> validatorOptional = reviewRepository.findById(REVIEW_ID);
        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(REVIEW_ID,"找不到审核");
        }



        Review review = validatorOptional.get();
        review.setOpen(false);

        review.setOpen(false);
        review.setClosedReason("dddddddddddd");
        review = reviewRepository.save(review);


        return EntityModel.of(review);

    }

}