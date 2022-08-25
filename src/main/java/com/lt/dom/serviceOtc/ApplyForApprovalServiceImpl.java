package com.lt.dom.serviceOtc;


import com.google.gson.Gson;
import com.lt.dom.OctResp.RequestResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.MerchantsSettledReq;
import com.lt.dom.otcReq.RequestReq;
import com.lt.dom.otcReq.ReviewReq;
import com.lt.dom.otcenum.EnumRequestStatus;
import com.lt.dom.otcenum.EnumRequestType;
import com.lt.dom.otcenum.EnumReviewerType;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.VoucherBatch;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplyForApprovalServiceImpl {

    @Autowired
    private IdGenServiceImpl idGenService;
    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private TourBookingRepository tourBookingRepository;
    @Autowired
    private ReviewerRepository reviewerRepository;
    @Autowired
    private ReviewRepository reviewRepository;

/*
    @Autowired
    private TourCampaignServiceImpl tourCampaignService;
*/

    public void review(TourBooking tourBooking) {
        tourBooking.setReviewing(true);
        tourBookingRepository.save(tourBooking);

    }
    public Review addReviews(Request request, ReviewReq reviewReq) {
        Review reviewer = new Review();
        reviewer.setRequest(request.getId());
        reviewer.setReason(reviewReq.getReason());
        return reviewRepository.save(reviewer);
    }


    public Request apply(Supplier supplier, RequestReq pojo) {

        Request request = new Request();
        request.setType(pojo.getType());
        request.setOwner(supplier.getId());

        if(pojo.getType().equals(EnumRequestType.Review_and_approve_batches)){

            VoucherBatch requestMerchants_settled =pojo.getVoucherBatch();// (VoucherBatch)JSONObject.stringToValue(pojo.getAdditional_info());

           // request.setSource(requestMerchants_settled.getId());
            request.setAdditional_info(JSONObject.valueToString(requestMerchants_settled));
        }



        if(pojo.getType().equals(EnumRequestType.tour_approve)){

            Optional<TourBooking> optionalTourBooking = tourBookingRepository.findById(pojo.getTourBooking());

            TourBooking tourBooking = optionalTourBooking.get();

            review(tourBooking);
           // tourBooking.setStatus(EnumOrderStatus.);
            VoucherBatch requestMerchants_settled =pojo.getVoucherBatch();// (VoucherBatch)JSONObject.stringToValue(pojo.getAdditional_info());

            request.setSource(tourBooking.getId());
            request.setAdditional_info(JSONObject.valueToString(requestMerchants_settled));
        }




        request.setStatus(EnumRequestStatus.Pending);

        request = requestRepository.save(request);

        Request finalRequest = request;
        List<Reviewer> reviewers = pojo.getReviewers().stream().map(x->{
            Reviewer reviewer = new Reviewer();
            reviewer.setRequest(finalRequest.getId());
            reviewer.setType(EnumReviewerType.individal);
            return reviewer;
        }).collect(Collectors.toList());

        List<Reviewer> team_reviewers = pojo.getTeam_reviewers().stream().map(x->{
            Reviewer reviewer = new Reviewer();
            reviewer.setRequest(finalRequest.getId());
            reviewer.setType(EnumReviewerType.team);
            return reviewer;
        }).collect(Collectors.toList());
        reviewers.addAll(team_reviewers);

        reviewerRepository.saveAll(reviewers);
        return request;
    }



    public Request reject(Request request, ReviewReq pojo) {
        request.setStatus(EnumRequestStatus.REJECT);
        request = requestRepository.save(request);
        Review review = new Review();
        review.setRequest(request.getId());
        review.setReason(pojo.getReason());
        review.setCreated_at(LocalDateTime.now());
        reviewRepository.save(review);
        return request;
    }



    public Request approve(Request request, ReviewReq pojo) {

        if(request.getType().equals(EnumRequestType.Merchants_settled)){

            Gson gson = new Gson();

            MerchantsSettledReq requestMerchants_settled =  gson.fromJson(request.getAdditional_info(), MerchantsSettledReq.class);

        }

        request.setStatus(EnumRequestStatus.APPROVE);

        request = requestRepository.save(request);

        Review review = new Review();

        review.setRequest(request.getId());
        review.setReason(pojo.getReason()== null? "":pojo.getReason());
        review.setCreated_at(LocalDateTime.now());
        reviewRepository.save(review);

        return request;
    }




    public void create(EnumRequestType merchants_settled, @Valid MerchantsSettledReq supplier, User user) {
        Request request = new Request();
        request.setCode(idGenService.requestNo());
        if(merchants_settled.equals(EnumRequestType.Merchants_settled)){
            request.setType(EnumRequestType.Merchants_settled);
        }

        Gson gson = new Gson();
        request.setAdditional_info(gson.toJson(supplier));
        request.setStatus(EnumRequestStatus.Pending);
        request.setApplied_at(LocalDateTime.now());
        request.setOwner(user.getId());
        request = requestRepository.save(request);


    }




    public Request create(EnumRequestType merchants_settled, TourBooking tourBooking, User user) {
        Request request = new Request();
        request.setCode(idGenService.requestNo());
        if(merchants_settled.equals(EnumRequestType.tour_approve)){
            request.setType(EnumRequestType.Merchants_settled);
        }

        Gson gson = new Gson();
        request.setAdditional_info(gson.toJson(tourBooking));
        request.setIdId(tourBooking.getCode());
        request.setStatus(EnumRequestStatus.Pending);
        request.setApplied_at(LocalDateTime.now());
        request.setOwner(user.getId());
        request = requestRepository.save(request);

        return request;

    }

    public Review addReviews(EnumRequestType tour_approve, String code, ReviewReq reviewReq) {

        Optional<Request> optionalRequest = requestRepository.findByTypeAndIdId(tour_approve,code);

        Request request = optionalRequest.get();

        Review reviewer = new Review();
        reviewer.setRequest(request.getId());
        reviewer.setReason(reviewReq.getReason());
        return reviewRepository.save(reviewer);
    }


    public Optional<RequestResp> getRequst(EnumRequestType tour_approve, String code) {


        Optional<Request> optionalRequest = requestRepository.findByTypeAndIdId(tour_approve,code);
        if(optionalRequest.isPresent()){
            Request request = optionalRequest.get();

            List<Reviewer> reviewers = reviewerRepository.findAllByRequest(request.getId());


            RequestResp requestResp = RequestResp.from(request, reviewers);
            return Optional.of(requestResp);
        }else{
            return Optional.empty();
        }



    }
}
