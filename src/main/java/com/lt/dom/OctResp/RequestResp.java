package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.MerchantsSettledReq;
import com.lt.dom.otcenum.EnumRequestStatus;
import com.lt.dom.otcenum.EnumRequestType;
import com.lt.dom.requestvo.RequestMerchants_settled;
import org.apache.commons.collections.map.HashedMap;
import org.json.JSONObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestResp {

    private long owner;

    private String additional_info;
    private EntityModel tour_booking;
    private String type_text;
    private List<ReviewResp> reviews;

    private MerchantsSettledReqResp merchantsSettledReq;
    private String status_text;
    private LocalDateTime applied_at;
    
//##@Column(unique=true) 
private String code;
    private ReviewResp reject_review;
    private MerchantsSettledReq origin_merchantsSettledReqResp;

    public static RequestResp from(Request request, List<Reviewer> reviewers) {
        RequestResp requestResp = new RequestResp();
        requestResp.setStatus(request.getStatus());
        requestResp.setCode(request.getCode());
        requestResp.setStatus_text(request.getStatus().toString());
        requestResp.setSource(request.getSource());
        requestResp.setType(request.getType());
        requestResp.setOwner(request.getOwner());
        requestResp.setApplied_at(request.getApplied_at());
        requestResp.setType_text(request.getType().toString());

/*
        // requestResp.setAdditional_info(request.getAdditional_info());
       if(request.getType().equals(EnumRequestType.Merchants_settled)){
           Gson gson = new Gson();
           MerchantsSettledReq max = gson.fromJson(request.getAdditional_info(), MerchantsSettledReq.class);
           requestResp.setMerchantsSettledReq(MerchantsSettledReqResp.from(max, new HashedMap()));
        }
*/

        requestResp.setReviewers(reviewers.stream().map(x->{
            ReviewerResp reviewer = new ReviewerResp();
            reviewer.setName(x.getReviewer()+"");
            return reviewer;
        }).collect(Collectors.toList()));
        requestResp.setTeam_reviewers(reviewers.stream().map(x->{
            ReviewerResp reviewer = new ReviewerResp();
            reviewer.setName(x.getReviewer()+"");
            return reviewer;
        }).collect(Collectors.toList()));
        return requestResp;
    }




    public static RequestResp simpleFrom(Request request, User user) {
        RequestResp requestResp = new RequestResp();
        requestResp.setStatus(request.getStatus());
        requestResp.setStatus_text(request.getStatus().toString());
        requestResp.setSource(request.getSource());
        requestResp.setType(request.getType());
        requestResp.setOwner(request.getOwner());
        requestResp.setType_text(request.getType().toString());

        if(request.getType().equals(EnumRequestType.Merchants_settled)){
            Gson gson = new Gson();
            MerchantsSettledReq max = gson.fromJson(request.getAdditional_info(), MerchantsSettledReq.class);
            max.setUser_password("");

            requestResp.setMerchantsSettledReq(MerchantsSettledReqResp.from(max));
        }


        return requestResp;
    }

    public static RequestResp fromWithMearchantSettled(Request request, List<Reviewer> reviewers, MerchantsSettledReqResp merchantsSettledReqResp1) {

        RequestResp requestResp = from(request,reviewers);
        requestResp.setMerchantsSettledReq(merchantsSettledReqResp1);
        return requestResp;
    }

    public static RequestResp fromWithTour_approve(Request request, List<Reviewer> reviewers, EntityModel entityModel1) {
        RequestResp requestResp = from(request,reviewers);

        requestResp.setTour_booking(entityModel1);
        requestResp.setType_text(request.getType().toString());
        return requestResp;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }


    private EnumRequestType type;
    private String idId;
    private String url;
    private long source;
    private EnumRequestStatus status;


    private List<ReviewerResp> reviewers;

    public List<ReviewerResp> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<ReviewerResp> reviewers) {
        this.reviewers = reviewers;
    }

    public void setTour_booking(EntityModel tour_booking) {
        this.tour_booking = tour_booking;
    }

    public EntityModel getTour_booking() {
        return tour_booking;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getType_text() {
        return type_text;
    }

    public  void setReviews(List<ReviewResp> reviews) {
        this.reviews = reviews;
    }

    public List<ReviewResp> getReviews() {
        return reviews;
    }


    public void setMerchantsSettledReq(MerchantsSettledReqResp merchantsSettledReq) {
        this.merchantsSettledReq = merchantsSettledReq;
    }

    public MerchantsSettledReqResp getMerchantsSettledReq() {
        return merchantsSettledReq;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setApplied_at(LocalDateTime applied_at) {
        this.applied_at = applied_at;
    }

    public LocalDateTime getApplied_at() {
        return applied_at;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setReject_review(ReviewResp reject_review) {
        this.reject_review = reject_review;
    }

    public ReviewResp getReject_review() {
        return reject_review;
    }

    public void setOrigin_merchantsSettledReqResp(MerchantsSettledReq origin_merchantsSettledReqResp) {
        this.origin_merchantsSettledReqResp = origin_merchantsSettledReqResp;
    }

    public MerchantsSettledReq getOrigin_merchantsSettledReqResp() {
        return origin_merchantsSettledReqResp;
    }

    public static class ReviewerResp {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public EnumRequestStatus getStatus() {
        return status;
    }

    public void setStatus(EnumRequestStatus status) {
        this.status = status;
    }

    public long getSource() {
        return source;
    }

    public void setSource(long source) {
        this.source = source;
    }

    public EnumRequestType getType() {
        return type;
    }

    public void setType(EnumRequestType type) {
        this.type = type;
    }

    public String getIdId() {
        return idId;
    }

    public void setIdId(String idId) {
        this.idId = idId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    private EnumRequestType requestType;

    private boolean isDeleted;

    private String justification;

    private String createdAt;

    private String lastModifiedAt;

    private String revision;

    private String name;
    private String description;



    private String policy;



    private List<ReviewerResp> team_reviewers;

    public List<ReviewerResp> getTeam_reviewers() {
        return team_reviewers;
    }

    public void setTeam_reviewers(List<ReviewerResp> team_reviewers) {
        this.team_reviewers = team_reviewers;
    }
}
