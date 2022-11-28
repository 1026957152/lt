package com.lt.dom.serviceOtc;


import com.google.gson.Gson;
import com.lt.dom.OctResp.*;
import com.lt.dom.controllerOct.RequestFuckRestController;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.MerchantsSettledEdit;
import com.lt.dom.otcReq.MerchantsSettledReq;
import com.lt.dom.otcReq.RequestReq;
import com.lt.dom.otcReq.ReviewReq;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.VoucherBatch;
import org.javatuples.Pair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.lt.dom.serviceOtc.JsonParse.GSON;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    @Autowired
    private FileStorageServiceImpl fileStorageService;

    @Autowired
    private MarchertSettledServiceImpl marchertSettledService;


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


            MerchantsSettledEdit requestMerchants_settled =  GSON.fromJson(request.getAdditional_info(), MerchantsSettledEdit.class);


            marchertSettledService.merchants_settled(requestMerchants_settled,request.getOwner());
        }

        request.setStatus(EnumRequestStatus.APPROVE);

        request.setActive(false);
        request = requestRepository.save(request);

        Review review = new Review();

        review.setRequest(request.getId());
        review.setReason(pojo.getReason()== null? "":pojo.getReason());
        review.setCreated_at(LocalDateTime.now());
        reviewRepository.save(review);

        return request;
    }




    public MerchantsSettledEdit update(Request request,  @Valid MerchantsSettledEdit merchantsSettledReq) {





        request.setAdditional_info(GSON.toJson(merchantsSettledReq));
        request.setStatus(EnumRequestStatus.Pending);


        request = requestRepository.save(request);


        if(merchantsSettledReq.getBussiness_license_image()!= null){
            fileStorageService.updateFromTempDocument(request.getCode(),merchantsSettledReq.getBussiness_license_image(),EnumDocumentType.business_license);
        }
        if(merchantsSettledReq.getLicense_image()!= null){
            fileStorageService.updateFromTempDocument(request.getCode(),merchantsSettledReq.getLicense_image(),EnumDocumentType.license);
        }
        if(merchantsSettledReq.getLiability_insurance_image()!= null){
            fileStorageService.updateFromTempDocument(request.getCode(),merchantsSettledReq.getLiability_insurance_image(),EnumDocumentType.liability_insurance);
        }
        if(merchantsSettledReq.getLicense_for_opening_bank_account_image()!= null){
            fileStorageService.updateFromTempDocument(request.getCode(),merchantsSettledReq.getLicense_for_opening_bank_account_image(),EnumDocumentType.license_for_opening_bank_account);
        }

        return merchantsSettledReq;

    }




    public Request update(EnumRequestType merchants_settled, TourBooking tourBooking, User user) {
        Request request = new Request();
        request.setCode(idGenService.requestNo());
        if(merchants_settled.equals(EnumRequestType.tour_approve)){
            request.setType(EnumRequestType.Merchants_settled);
        }

        Gson gson = new Gson();
       // request.setAdditional_info(gson.toJson(tourBooking));
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




            Gson gson = new Gson();
            MerchantsSettledReq merchantsSettledReq = gson.fromJson(request.getAdditional_info(), MerchantsSettledReq.class);


/*            Map<EnumDocumentType,TempDocument> enumDocumentTypeTempDocumentMap = fileStorageService.loadTempDocument(Arrays.asList(
                    Pair.with(EnumDocumentType.business_license,merchantsSettledReq.getBussiness_license()),
                    Pair.with(EnumDocumentType.license,merchantsSettledReq.getLicense_image()),
                    Pair.with(EnumDocumentType.liability_insurance,merchantsSettledReq.getLiability_insurance_image()),
                    Pair.with(EnumDocumentType.license_for_opening_bank_account,merchantsSettledReq.getLicense_for_opening_bank_account())
            ));*/
            MerchantsSettledReqResp merchantsSettledReqResp1 = MerchantsSettledReqResp.from(merchantsSettledReq);


            RequestResp requestResp = RequestResp.fromWithMearchantSettled(request, reviewers,merchantsSettledReqResp1);
            return Optional.of(requestResp);
        }else{
            return Optional.empty();
        }



    }


    public Optional<EntityModel<RequestResp>> getByUser(User user, UserResp userResp) {



        List<Request> requestList = requestRepository.findByOwner(user.getId());
        Optional<Request> optionalRequest = requestList.stream()

                .filter(x->x.getType().equals(EnumRequestType.Merchants_settled))
                .filter(x->!x.getStatus().equals(EnumRequestStatus.APPROVE))
                .findAny();


        userResp.setMerchant_settled_status(EnumSupplierVerifiedStatus.completed);

        if(optionalRequest.isPresent()){

            Request request = optionalRequest.get();

            List<Reviewer> reviewers = reviewerRepository.findAllByRequest(request.getId());
            List<Review> reviews = reviewRepository.findAllByRequest(request.getId());




            if(request.getType().equals(EnumRequestType.Merchants_settled)){


                RequestResp requestResp = Merchants_settled_images(request,reviewers);

                //好丑陋的 代码
                SupplierResp supplierResp = SupplierResp.from(requestResp);

                supplierResp.setStatus(EnumSupplierStatus.PendingApproval);
                EntityModel supplierRespEntityModel = EntityModel.of(supplierResp);
                userResp.setSupplier(supplierRespEntityModel);
                EntityModel entityModel_ = EntityModel.of(requestResp);
                if( optionalRequest.get().getStatus().equals(EnumRequestStatus.Pending)){
                    userResp.setMerchant_settled_status(EnumSupplierVerifiedStatus.pending);
                }


                if(optionalRequest.isPresent() &&  optionalRequest.get().getStatus().equals(EnumRequestStatus.REJECT)){

                    userResp.setMerchant_settled_status(EnumSupplierVerifiedStatus.rejected);

                    requestResp.setReject_review(ReviewResp.from(reviews.get(0)));
                    entityModel_.add(linkTo(methodOn(RequestFuckRestController.class).editRequest(optionalRequest.get().getId(),null)).withRel("editRequest"));


                }



                requestResp.setReviews(reviews.stream().map(x->{
                    return ReviewResp.from(x);
                }).collect(Collectors.toList()));

                return Optional.of(entityModel_);
            }

        }
            return Optional.empty();
    }




    RequestResp Merchants_settled_images(Request request,List<Reviewer> reviewers){
        MerchantsSettledReq merchantsSettledReq = MerchantsSettledReqResp.fromJsonString(request.getAdditional_info());
/*

        Map<EnumDocumentType,TempDocument> enumDocumentTypeTempDocumentMap = fileStorageService.loadTempDocument(Arrays.asList(
                Pair.with(EnumDocumentType.business_license,merchantsSettledReq.getBussiness_license()),
                Pair.with(EnumDocumentType.license,merchantsSettledReq.getLicense_image()),
                Pair.with(EnumDocumentType.liability_insurance,merchantsSettledReq.getLiability_insurance_image()),
                Pair.with(EnumDocumentType.license_for_opening_bank_account,merchantsSettledReq.getLicense_for_opening_bank_account())
        ));

*/

        MerchantsSettledReqResp merchantsSettledReqResp1 = MerchantsSettledReqResp.from(merchantsSettledReq);



        RequestResp requestResp = RequestResp.fromWithMearchantSettled(request,reviewers,merchantsSettledReqResp1);
        merchantsSettledReq.setUser_phone("");
        merchantsSettledReq.setUser_name("");
        merchantsSettledReq.setUser_password("");
        requestResp.setOrigin_merchantsSettledReqResp(merchantsSettledReq);




        return requestResp;
    }



    public Request edit(EnumRequestType merchants_settled, Request request, MerchantsSettledReq merchantsSettledReq) {


        if(merchants_settled.equals(EnumRequestType.Merchants_settled)){
            Gson gson = new Gson();
            request.setAdditional_info(gson.toJson(merchantsSettledReq));
            request.setStatus(EnumRequestStatus.Pending);
            request.setUpdated_at(LocalDateTime.now());
            request = requestRepository.save(request);
        }



        return request;

    }

/*    public static void main(String[] args) throws Exception{
        String bankNo = "银行卡号";
        //银行代码请求接口 url
        String url = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo="+bankNo+"&cardBinCheck=true";
        //发送请求，得到 josn 类型的字符串
        String result = HttpUtil.get(url);
        // 转为 Json 对象
        JSONObject json = new JSONObject(result);
        //获取到 bank 代码
        String bank = String.valueOf(json.get("bank"));
        //爬取支付宝银行合作商页面
        String listContent = HttpUtil.get("http://ab.alipay.com/i/yinhang.htm","gb2312");
        //过滤得到需要的银行名称
        List<String> titles = ReUtil.findAll("<span title=\"(.*?)\" class=\"icon "+bank+"\">(.*?)</span>", listContent, 2);
        for (String title : titles) {
            //打印银行名称
          //  Console.log(title);
        }
    }*/
}
