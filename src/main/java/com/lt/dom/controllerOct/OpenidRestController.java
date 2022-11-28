package com.lt.dom.controllerOct;

import com.lt.dom.JwtUtils;
import com.lt.dom.OctResp.EnumResp;
import com.lt.dom.OctResp.ReviewResp;
import com.lt.dom.OctResp.UserResp;
import com.lt.dom.OctResp.UserWithTokenResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.Missing_documentException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.OpenidRepository;
import com.lt.dom.repository.RequestRepository;
import com.lt.dom.repository.ReviewRepository;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.vo.SupplierPojoVo;
import com.lt.dom.vo.UserMachentSettedValidatVo;
import com.lt.dom.vo.UserVo;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.lt.dom.serviceOtc.JsonParse.GSON;
import static java.util.Objects.nonNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class OpenidRestController {

    @Autowired
    private SupplierServiceImp supplierServiceImp;

    @Autowired
    private IAuthenticationFacade authenticationFacade;
    @Autowired
    private SettleAccountServiceImpl settleAccountService;
    @Autowired
    private UserVoServiceImpl userVoService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private OpenidServiceImpl openidService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MarchertSettledServiceImpl marchertSettledService;


    @Autowired
    private ApplyForApprovalServiceImpl applyForApprovalService;
    @Autowired
    private RequestRepository requestRepository;


    @Autowired
    private ReviewRepository reviewRepository;


    @Autowired
    private IdGenServiceImpl idGenService;


    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private FileStorageServiceImpl fileStorageService;


    @Autowired
    private OpenidRepository openidRepository;

    @Autowired
    private Validator validator;

    @GetMapping(value = "openid/{OPEN_ID}", produces = "application/json")
    public ResponseEntity<EntityModel> getOpenid(@PathVariable String OPEN_ID) {

        Optional<Openid> optional = openidRepository.findByOpenid(OPEN_ID);

        if(optional.isPresent()){

            UserResp openidResp = new UserResp();
            openidResp.from(optional.get());

            EntityModel entityModel = EntityModel.of(openidResp);
            entityModel.add(linkTo(methodOn(OpenidRestController.class).linkUser(optional.get().getOpenid(),null)).withRel("link_user_url"));
            entityModel.add(linkTo(methodOn(OpenidRestController.class).merchants_settled(optional.get().getOpenid(),null)).withRel("merchants_settled_url"));
            entityModel.add(linkTo(methodOn(OpenidRestController.class).createUser(optional.get().getOpenid(),null)).withRel("register_url"));


            return ResponseEntity.ok(entityModel);

        }else{
            throw new BookNotFoundException(optional.get().getOpenid(),"找不到openid");

        }


    }










    @GetMapping(value = "/openid/{OPEN_ID}/Page_linkUser", produces = "application/json")
    public EntityModel Page_linkUser(@PathVariable String OPEN_ID ) {



        Optional<Openid> optional = openidRepository.findByOpenid(OPEN_ID);

        if(optional.isEmpty()) {

            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到openid ");

        }

        Openid openid = optional.get();

        Map map = Map.of("status_list", EnumUserType.from());


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(OpenidRestController.class).linkUser(openid.getOpenid(),null)).withRel("link"));


        return entityModel;

    }


    @PostMapping(value = "/openid/{OPEN_ID}/link_user", produces = "application/json")
    public ResponseEntity<Openid> linkUser(@PathVariable String OPEN_ID, @RequestBody WxlinkUserReq wxlinkUserReq) {


        Optional<Openid> optional = openidRepository.findByOpenid(OPEN_ID);

        if(optional.isEmpty()) {
            throw new BookNotFoundException(wxlinkUserReq.getUser_name(),"账号或密码错误");

        }
            Optional<User> optionalUser = userRepository.findByCode(wxlinkUserReq.getUser_name());

            if(optionalUser.isPresent()){
                throw new BookNotFoundException(wxlinkUserReq.getUser_name(),"账号或密码错误");

            }

        Openid openid = openidService.linkUser(optional.get(),optionalUser.get());
        return ResponseEntity.ok(openid);
    }













    @GetMapping(value = "/openid/merchants_settled/page/user", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_merchants_settled() {

        Authentication authentication =  authenticationFacade.getAuthentication();

        UserVo user = authenticationFacade.getUserVo(authentication);




        Optional<Request> optional = requestRepository.findByOwnerAndTypeAndActive(user.getUser_id(),EnumRequestType.Merchants_settled,true);





        MerchantsSettledEdit merchantsSettledEdit = null;
        Request request = null;
        if(optional.isEmpty()){


            request = new Request();

             merchantsSettledEdit = new MerchantsSettledEdit();
            merchantsSettledEdit.setMerchant_settled_status(EnumSupplierVerifiedStatus.none);

        }else{
            request = optional.get();
            merchantsSettledEdit = GSON.fromJson(request.getAdditional_info(),MerchantsSettledEdit.class);

            if(request.getStatus().equals(EnumRequestStatus.Pending)){
                merchantsSettledEdit.setMerchant_settled_status(EnumSupplierVerifiedStatus.pending);
                merchantsSettledEdit.setBussiness_license_image(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.business_license,request.getCode()));
                merchantsSettledEdit.setLicense_image(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.license,request.getCode()));
                merchantsSettledEdit.setLiability_insurance_image(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.liability_insurance,request.getCode()));
                merchantsSettledEdit.setLicense_for_opening_bank_account_image(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.license_for_opening_bank_account,request.getCode()));

            }
            if(request.getStatus().equals(EnumRequestStatus.REJECT)){


                merchantsSettledEdit.setMerchant_settled_status(EnumSupplierVerifiedStatus.rejected);

                List<Review> reviews = reviewRepository.findAllByRequest(request.getId());
                merchantsSettledEdit.setReject_review(ReviewResp.from(reviews.get(0)));

                merchantsSettledEdit.setBussiness_license_image(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.business_license,request.getCode()));
                merchantsSettledEdit.setLicense_image(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.license,request.getCode()));
                merchantsSettledEdit.setLiability_insurance_image(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.liability_insurance,request.getCode()));
                merchantsSettledEdit.setLicense_for_opening_bank_account_image(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.license_for_opening_bank_account,request.getCode()));



            }
            if(request.getStatus().equals(EnumRequestStatus.APPROVE)){


                merchantsSettledEdit.setMerchant_settled_status(EnumSupplierVerifiedStatus.completed);
                merchantsSettledEdit = new MerchantsSettledEdit();
                merchantsSettledEdit.setMerchant_settled_status(EnumSupplierVerifiedStatus.none);



            }
        }








      //  if(request.)



        Map<EnumSupplierDisplayType,List<EnumSupplier>> enumSupplierTypeListMap = Arrays.stream(EnumSupplier.values()).collect(Collectors.groupingBy(e->e.getCategory()));

        List<EnumResp> enumResps = enumSupplierTypeListMap.entrySet().stream().map(x->{

            EnumSupplierDisplayType type = x.getKey();
            List<EnumSupplier> enumSuppliers = x.getValue();

            EnumResp enumResp = new EnumResp();
            enumResp.setId(type.name());
            //  enumResp.setName(x.name());
            enumResp.setText(type.toString());


            enumResp.setSubitems(enumSuppliers.stream().map(e->{
                EnumResp enumResp_sub = new EnumResp();
                enumResp_sub.setId(e.name());
                //  enumResp.setName(x.name());
                enumResp_sub.setText(e.getName());
                enumResp_sub.setType(e.getType());
                return enumResp_sub;
            }).collect(Collectors.toList()));
            return enumResp;
        }).collect(Collectors.toList());










        merchantsSettledEdit.setParameterList(Map.of("term","这里是注册下一站告诉你的协议",
                "allowed_supplier_list", enumResps,
                "notes",      Map.of("account_opening_license",EnumNote.account_opening_license),
/*                "supplier_type_list", Arrays.stream(EnumSupplierType.values()).map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
          //  enumResp.setName(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList()),*/
                "bussiness_type_list", Arrays.stream(EnumBussinessType.values()).map(x->{
                    EnumResp enumResp = new EnumResp();
                    enumResp.setId(x.name());
                    //   enumResp.setName(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
                }).collect(Collectors.toList())));

        EntityModel entityModel = EntityModel.of(merchantsSettledEdit);

        entityModel.add(linkTo(methodOn(OpenidRestController.class).merchants_settled__request_(request.getId(),null)).withRel("merchants_settled"));

        entityModel.add(linkTo(methodOn(DocumentRestController.class).uploadDocument(null)).withRel("uploadDocument"));
        return entityModel;


    }




    @PostMapping(value = "/openid/merchants_settled_request/{id}", produces = "application/json")
    public EntityModel merchants_settled__request_(@PathVariable Long id, @RequestBody @Valid MerchantsSettledEdit wxlinkUserReq) {


        Optional<Request> optional = requestRepository.findById(id);

        Authentication authentication =  authenticationFacade.getAuthentication();

        UserVo user = authenticationFacade.getUserVo(authentication);



        Request request = null;

        if(optional.isEmpty()){


            Optional<Request> optionalRequest = requestRepository.findByOwnerAndTypeAndActive(user.getUser_id(),EnumRequestType.Merchants_settled,true);
            if(optionalRequest.isEmpty()){
                request = new Request();
                request.setAdditional_info(GSON.toJson(new MerchantsSettledEdit()));

                request.setCode(idGenService.requestNo());
                request.setType(EnumRequestType.Merchants_settled);

                request.setApplied_at(LocalDateTime.now());
                request.setOwner(user.getUser_id());
                request.setActive(true);
                request = requestRepository.save(request);
            }else{
                request = optionalRequest.get();
            }


        }else{
            request = optional.get();
        }



/*
        List<Openid> optional = openidRepository.findByUserId(user.getUser_id());
        if(optional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到openid");

        }

        Openid openid = optional.get(0);*/

/*
        UserMachentSettedValidatVo validatVo = wxlinkUserReq.toUserMachentSettedValidatVo();

        Set<ConstraintViolation<UserMachentSettedValidatVo>> violations = validator.validate(validatVo);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<UserMachentSettedValidatVo> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }
*/




       // MerchantsSettledReq merchantsSettledReq = marchertSettledService.validat(wxlinkUserReq);






 /*       UserPojo userPojo = new UserPojo();
        userPojo.setFirst_name(wxlinkUserReq.getFirst_name());
        userPojo.setLast_name(wxlinkUserReq.getLast_name());
        userPojo.setNick_name(wxlinkUserReq.getUser_name());
        userPojo.setRealName(wxlinkUserReq.getUser_name());
        userPojo.setPhone(wxlinkUserReq.getUser_phone());
        userPojo.setPassword(wxlinkUserReq.getUser_password());


        if(wxlinkUserReq.getAllowed_supplier().getType().equals(EnumSupplierType.TravelAgent)){
            userPojo.setRoles(Arrays.asList(EnumRole.ROLE_TRAVEL_AGENCY.name()));
        }else{

            if(wxlinkUserReq.getBusiness_type().equals(EnumBussinessType.government_entity)){
                userPojo.setRoles(Arrays.asList(EnumRole.ROLE_GOVERNMENT.name()));
            }else{
                userPojo.setRoles(Arrays.asList(EnumRole.ROLE_MERCHANT.name()));
            }

        }*/
        MerchantsSettledEdit merchantsSettledEdit = applyForApprovalService.update(request,wxlinkUserReq);



        return EntityModel.of(merchantsSettledEdit);
    }


 //   @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/openid/merchants_settled", produces = "application/json")
    public EntityModel merchants_settled___(@RequestParam(required = false) String OPEN_ID, @RequestBody @Valid MerchantsSettledReq wxlinkUserReq) {


        UserMachentSettedValidatVo validatVo = wxlinkUserReq.toUserMachentSettedValidatVo();

        Set<ConstraintViolation<UserMachentSettedValidatVo>> violations = validator.validate(validatVo);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<UserMachentSettedValidatVo> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }


        Optional<Openid> optional = openidRepository.findByOpenid(OPEN_ID);




        MerchantsSettledReq merchantsSettledReq = marchertSettledService.validat(wxlinkUserReq);


        if(optional.isPresent()) {
            throw new BookNotFoundException(OPEN_ID,"未找到授权登录");

        }




        UserPojo userPojo = new UserPojo();
        userPojo.setFirst_name(wxlinkUserReq.getFirst_name());
        userPojo.setLast_name(wxlinkUserReq.getLast_name());
        userPojo.setNick_name(wxlinkUserReq.getUser_name());
        userPojo.setRealName(wxlinkUserReq.getUser_name());
        userPojo.setPhone(wxlinkUserReq.getUser_phone());
        userPojo.setPassword(wxlinkUserReq.getUser_password());


        if(wxlinkUserReq.getAllowed_supplier().getType().equals(EnumSupplierType.TravelAgent)){
            userPojo.setRoles(Arrays.asList(EnumRole.ROLE_TRAVEL_AGENCY.name()));
        }else{

            if(wxlinkUserReq.getBusiness_type().equals(EnumBussinessType.government_entity)){
                userPojo.setRoles(Arrays.asList(EnumRole.ROLE_GOVERNMENT.name()));
            }else{
                userPojo.setRoles(Arrays.asList(EnumRole.ROLE_MERCHANT.name()));
            }

        }


        User user = userService.createUser(userPojo,Arrays.asList(Pair.with(EnumIdentityType.phone,userPojo.getPhone())));




        String jwt = jwtUtils.generateJwtToken(1,user.getPhone());

        UserWithTokenResp authsResp = new UserWithTokenResp();


        System.out.println("这里了   的地方士大夫撒旦 "+ user);
        authsResp.setInfo(userVoService.getBigUser(user));
        authsResp.setToken(jwt);
        System.out.println("这里了   的顶顶顶顶顶顶顶夫撒旦 "+ user);
        return EntityModel.of(authsResp);
    }


    @PostMapping(value = "/openid/{OPEN_ID}/user", produces = "application/json")
    public ResponseEntity<User> createUser(@PathVariable String OPEN_ID, @RequestBody @Valid OpenidCreateUserReq wxlinkUserReq) {

        Optional<Openid> optional = openidRepository.findByOpenid(OPEN_ID);

        if(optional.isPresent()){

            UserPojo userPojo = new UserPojo();
            userPojo.setFirst_name(wxlinkUserReq.getUser_name());
            userPojo.setUsername(wxlinkUserReq.getUser_name());
            userPojo.setPhone(wxlinkUserReq.getUser_phone());

            User user = userService.createUser(userPojo,Arrays.asList());

            openidService.linkUser(optional.get(),user);
            return ResponseEntity.ok(user);

        }else{
            throw new BookNotFoundException(OPEN_ID,"未找到授权登录");

        }


    }




















    @PostMapping(value = "/openid/merchants_settled_backup", produces = "application/json")
    public EntityModel merchants_settled(@RequestParam(required = false) String OPEN_ID, @RequestBody @Valid MerchantsSettledEdit wxlinkUserReq) {


        Optional<Openid> optional = openidRepository.findByOpenid(OPEN_ID);

        List<Pair<EnumDocumentType,String>> docTypeWithDocCodepairList = new ArrayList<>();

        EnumSupplier enumSupplier = null;//wxlinkUserReq.getAllowed_supplier();




        Request request =null;// applyForApprovalService.create(EnumRequestType.Merchants_settled,wxlinkUserReq,null);


        if(enumSupplier.getType().equals(EnumSupplierType.TravelAgent)){
            if(nonNull(wxlinkUserReq.getBussiness_license_image())){

                fileStorageService.saveFromTempDocumentWithRich(request.getCode(),wxlinkUserReq.getBussiness_license_image(),EnumDocumentType.business_license);

             //   docTypeWithDocCodepairList.add(Pair.with(EnumDocumentType.business_license,wxlinkUserReq.getBussiness_license()));
            }else{
                throw new Missing_documentException(1, Supplier.class.getSimpleName(),"需要上传 营业执照");
            }

            if(nonNull(wxlinkUserReq.getLicense_for_opening_bank_account_image())){
                fileStorageService.saveFromTempDocumentWithRich(request.getCode(),wxlinkUserReq.getLicense_for_opening_bank_account_image(),EnumDocumentType.license_for_opening_bank_account);

              //  docTypeWithDocCodepairList.add(Pair.with(EnumDocumentType.license_for_opening_bank_account,wxlinkUserReq.getLicense_for_opening_bank_account()));
            }else{
                throw new Missing_documentException(1, Supplier.class.getSimpleName(),"需要上传 开户许可证");
            }

            if(nonNull(wxlinkUserReq.getLicense_image())){
                fileStorageService.saveFromTempDocumentWithRich(request.getCode(),wxlinkUserReq.getLicense_for_opening_bank_account_image(),EnumDocumentType.license);

              //  docTypeWithDocCodepairList.add(Pair.with(EnumDocumentType.license,wxlinkUserReq.getLicense_image()));

                // docTypeWithDocCodepairList.addAll(wxlinkUserReq.getBussiness_license().stream().map(x->Pair.with(EnumDocumentType.license,x)).collect(Collectors.toList()));
            }else{
                throw new Missing_documentException(1, Supplier.class.getSimpleName(),"需要上传 责任保险");

            }
            if(nonNull(wxlinkUserReq.getLiability_insurance_image())){
                fileStorageService.saveFromTempDocumentWithRich(request.getCode(),wxlinkUserReq.getLiability_insurance_image(),EnumDocumentType.liability_insurance);

               // docTypeWithDocCodepairList.add(Pair.with(EnumDocumentType.liability_insurance,wxlinkUserReq.getLiability_insurance_image()));

                // docTypeWithDocCodepairList.addAll(wxlinkUserReq.getBussiness_license().stream().map(x->Pair.with(EnumDocumentType.liability_insurance,x)).collect(Collectors.toList()));
            }else{
                throw new Missing_documentException(1, Supplier.class.getSimpleName(),"需要上传 责任保险");
            }
        }else{
            if(nonNull(wxlinkUserReq.getBussiness_license_image())){
               // docTypeWithDocCodepairList.add(Pair.with(EnumDocumentType.business_license,wxlinkUserReq.getBussiness_license()));
                fileStorageService.saveFromTempDocumentWithRich(request.getCode(),wxlinkUserReq.getBussiness_license_image(),EnumDocumentType.business_license);

                //    docTypeWithDocCodepairList.addAll(wxlinkUserReq.getBussiness_license().stream().map(x->Pair.with(EnumDocumentType.contract,x)).collect(Collectors.toList()));
            }else{
                throw new Missing_documentException(1, Supplier.class.getSimpleName(),"需要上传营业执照");

            }

        }



        if(optional.isPresent()) {
            throw new BookNotFoundException(OPEN_ID,"未找到授权登录");

        }
/*
        List<TempDocument> tempDocuments = tempDocumentRepository.findAllByCodeIn(docTypeWithDocCodepairList.stream().map(x->x.getValue1()).distinct().collect(Collectors.toList()));


        System.out.println("------------------------------------------------"+tempDocuments.toString());


        Map<String,TempDocument> documentMap = tempDocuments.stream().collect(Collectors.toMap(x->x.getCode(),x->x));

        List<Pair<EnumDocumentType,TempDocument>> docTypeWithTempDocPairList = docTypeWithDocCodepairList.stream().map(x->{
            TempDocument tempDocument = documentMap.get(x.getValue1());
            return Pair.with(x.getValue0(),tempDocument);
        }).collect(Collectors.toList());


        if(docTypeWithTempDocPairList.isEmpty()){
            throw new Missing_documentException(1, Supplier.class.getSimpleName(),"需要附上申请相关文档");
        }

*/


        SupplierPojo supplierPojo = new SupplierPojo();
        supplierPojo.setDesc(wxlinkUserReq.getDesc());
        supplierPojo.setName(wxlinkUserReq.getSupplier_name());



        supplierPojo.setType(enumSupplier.getType());



        // supplierPojo.setLocation(wxlinkUserReq.getLocation());

        LocationEditResp location = wxlinkUserReq.getLocation();

        supplierPojo.setLat(location.getLatitude());
        supplierPojo.setLng(location.getLongitude());
        supplierPojo.setLocation(location.getAddress());
    //    supplierPojo.setLocationName(location.getName());

/*            supplierPojo.setRegion(location.getRegion());
            supplierPojo.setLocality(location.getLocality());
            supplierPojo.setStreet(location.getStreet());*/
        if(wxlinkUserReq.getBusiness_type() == null){
            supplierPojo.setBusiness_type(EnumBussinessType.company);
        }else{
            supplierPojo.setBusiness_type(wxlinkUserReq.getBusiness_type());
        }


        SupplierPojoVo supplierPojoVo = SupplierPojoVo.fromPojo(supplierPojo);


        Supplier supplier = supplierServiceImp.createSupplier(supplierPojoVo,EnumSupplierStatus.PendingApproval);


        // List<Document> documents = fileStorageService.saveFromTempDocumentList(supplier.getId(),docTypeWithTempDocPairList);



        UserPojo userPojo = new UserPojo();
        userPojo.setFirst_name(wxlinkUserReq.getFirst_name());
        userPojo.setLast_name(wxlinkUserReq.getLast_name());
        userPojo.setNick_name(wxlinkUserReq.getUser_name());
        userPojo.setRealName(wxlinkUserReq.getUser_name());
        userPojo.setPhone(wxlinkUserReq.getUser_phone());
        userPojo.setPassword(wxlinkUserReq.getUser_password());


        if(supplier.getType().equals(EnumSupplierType.TravelAgent)){
            userPojo.setRoles(Arrays.asList(EnumRole.ROLE_TRAVEL_AGENCY.name()));
        }else{

            if(supplier.getBusiness_type().equals(EnumBussinessType.government_entity)){
                userPojo.setRoles(Arrays.asList(EnumRole.ROLE_GOVERNMENT.name()));
            }else{
                userPojo.setRoles(Arrays.asList(EnumRole.ROLE_MERCHANT.name()));
            }

        }


        User user = userService.createUser(userPojo,Arrays.asList(Pair.with(EnumIdentityType.phone,userPojo.getPhone())));
        supplierServiceImp.成为员工(supplier,user);


     //   applyForApprovalService.create(EnumRequestType.Merchants_settled,wxlinkUserReq,user);

        //  openidService.linkUser(optional.get(),user);


        SettleAccountPojo settleAccountPojo = new SettleAccountPojo();
        settleAccountPojo.setName(wxlinkUserReq.getAccount_name());
        settleAccountPojo.setAccount(wxlinkUserReq.getBank_account_number());
        settleAccountPojo.setOpen_bank_code(wxlinkUserReq.getBank_name());

        settleAccountService.add(supplier,settleAccountPojo);


        String jwt = jwtUtils.generateJwtToken(1,user.getPhone());

        UserWithTokenResp authsResp = new UserWithTokenResp();


        System.out.println("这里了   的地方士大夫撒旦 "+ user);
        authsResp.setInfo(userVoService.getBigUser(user));
        authsResp.setToken(jwt);
        System.out.println("这里了   的顶顶顶顶顶顶顶夫撒旦 "+ user);
        return EntityModel.of(authsResp);
    }

}