package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.*;
import com.lt.dom.controllerOct.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.username_already_exists_errorException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.UserPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.vo.GuideSummaryVo;
import com.lt.dom.vo.UserVo;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserServiceImpl {




    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private OpenidRepository openidRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private GuideRepository guideRepository;

    @Autowired
    private PaymentServiceImpl paymentService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IdGenServiceImpl idGenService;
    @Autowired
    UserAuthorityRepository userAuthorityRepository;
    @Autowired
    GuideServiceImpl guideService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AssetServiceImpl assetService;
    public User createUser(@Valid UserPojo pojo,List<Pair<EnumIdentityType,String>> enumIdentityTypes) {

        Optional<User> optional = userRepository.findByPhone(pojo.getPhone());
        if(optional.isPresent()){
            throw new username_already_exists_errorException(1,pojo.getPhone()+"手机号已经注册","手机号已经注册");
        }
        User user = new User();


        user.setFirst_name(pojo.getFirst_name());
        user.setLast_name(pojo.getLast_name());
        user.setRealName(pojo.getRealName());
        user.setUsername(pojo.getUsername());
        user.setNick_name(pojo.getNick_name());
        user.setPhone(pojo.getPhone());
        user.setPassword(passwordEncoder.encode(pojo.getPassword()));
        user.setCode(idGenService.userNo());

       // user.setCreated_at(LocalDateTime.now());

        createRoleIfNotFound(user,pojo.getRoles());

        //createRoleIfNotFound(user,"ROLE_ADMIN");
        user.setEnabled(true);
        user = userRepository.save(user);


        User finalUser = user;
        enumIdentityTypes.forEach(x->{
            UserAuthority userAuthority = new UserAuthority();
            userAuthority.setUser_id(finalUser.getId());

            EnumIdentityType type = x.getValue0();
            String identifier = x.getValue1();
            switch (type){
                case phone:
                    userAuthority.setIdentifier(identifier);
                    userAuthorityRepository.save(userAuthority);
                    break;
                case weixin:
                    userAuthority.setIdentifier(identifier);
                    userAuthorityRepository.save(userAuthority);
                    break;
                case identity_card:
                    userAuthority.setIdentifier(identifier);
                    userAuthorityRepository.save(userAuthority);
                    break;
            }



        });



        assetService.newQr(user);

        return user;
    }

    //@Transactional
    User createRoleIfNotFound(User user,String name) {

        Role role = roleRepository.findByName(name);
        if (role != null) {
            user.setRoles(new HashSet<>(Arrays.asList(role)));
            roleRepository.save(role);
        }else{

        }
        return user;
    }

    User createRoleIfNotFound(User user,List<String> name) {

        List<Role> role = roleRepository.findByNameIn(name);
        if (role.size()> 0 && role.size() == name.size()) {
            user.setRoles(new HashSet<>(role));
           // roleRepository.saveAll(role);
        }else{
            throw new BookNotFoundException(0,"找不到权限"+name);
        }
        return user;
    }




    public Optional<User> getActiveOne(String real_name, String id_card, String phone) {
        Optional<User> optionalUser = userRepository.findByRealNameAndIdCardAndEnabled(real_name,id_card,true);


        return optionalUser;

    }

    public Optional<User> getActiveOneByPhone(String s) {
        Optional<User> optionalUser = userRepository.findByPhoneAndEnabled(s,true);
        return optionalUser;
    }


    public EntityModel getBigUser(User user) {


        Optional<Openid> optionalOpenid = openidRepository.findByUserIdAndLink(user.getId(),true);
        UserResp userResp = null;
        if(optionalOpenid.isPresent()){
            userResp = UserResp.userWithOpenidLink(Pair.with(user,optionalOpenid.get()));
        }else{
            userResp = UserResp.from(user);
        }
        EntityModel entityModel = EntityModel.of(userResp);




        List<Request> requestList = requestRepository.findByOwner(user.getId());
        Optional<Request> optionalRequest = requestList.stream().filter(x->x
                .getType().equals(EnumRequestType.Merchants_settled) & x.getStatus().equals(EnumRequestStatus.Pending)).findAny();
        if(optionalRequest.isPresent()){

            RequestResp requestResp = RequestResp.simpleFrom(optionalRequest.get(),user);
            EntityModel entityModel_ = EntityModel.of(requestResp);
            userResp.setRequest(entityModel_);

            SupplierResp supplierResp = SupplierResp.from(requestResp);
            supplierResp.setVerify_status(EnumSupplierVerifiedStatus.unverified.id());

            EntityModel supplierRespEntityModel = EntityModel.of(supplierResp);
            userResp.setSupplier(supplierRespEntityModel);

            return entityModel;
        }



        Optional<Asset> optionalAsset =assetService.getQrOptional(user.getCode());
        if(optionalAsset.isPresent()){
            userResp.setAsset(AssetResp.from(optionalAsset.get()));
        }
        Optional<Employee> optional = employeeRepository.findByUserId(user.getId());
        if(optional.isPresent()){



            Employee employee = optional.get();
            userResp.setHired(true);
            Optional<Supplier> optionalSupplier = supplierRepository.findById(optional.get().getSuplierId());


            Supplier supplier = optionalSupplier.get();

/*            if(supplier.getStatus().equals(EnumSupplierStatus.PendingApproval)){
                return entityModel;
            }*/



            Balance balance = paymentService.balance(supplier.getId(), EnumUserType.business);



            SupplierResp supplierResp = SupplierResp.from(optionalSupplier.get());

            EntityModel<BalanceResp> balanceRespEntityModel = EntityModel.of(BalanceResp.from(balance));
            balanceRespEntityModel.add(linkTo(methodOn(BalanceRestController.class).getBalance(1)).withSelfRel());
            supplierResp.setBalance(balanceRespEntityModel);
            EntityModel supplierRespEntityModel = EntityModel.of(supplierResp);
            supplierRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(optional.get().getSuplierId())).withRel("getSupplier"));
            supplierRespEntityModel.add(linkTo(methodOn(RedemptionRestController.class).getRedemptionEntrySummary(optional.get().getSuplierId())).withRel("getRedemptionSummary"));



            userResp.setSupplier(supplierRespEntityModel);

            supplierResp.setVerify_status(EnumSupplierVerifiedStatus.verified.id());

            EntityModel<ReportSaleResp> reportSaleRespEntityModel = EntityModel.of(new ReportSaleResp());
            reportSaleRespEntityModel.add(linkTo(methodOn(ReportRestController.class).getPublicationStatistics(null)).withSelfRel());




            entityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(optional.get().getSuplierId())).withRel("getSupplier"));


            entityModel.add(linkTo(methodOn(RedemptionRestController.class).validateVoucherByCode(null)).withRel("redeem"));

            entityModel.add(linkTo(methodOn(RedemptionRestController.class).pageRedemptionEntry(employee.getSuplierId(),null,null)).withRel("getRedemptionEntryList"));
            entityModel.add(linkTo(methodOn(RedemptionRestController.class).Page_pageRedemptionEntry(employee.getSuplierId())).withRel("Page_getRedemptionEntryList"));
            entityModel.add(linkTo(methodOn(ProductRestController.class).createProduct(employee.getSuplierId(),null)).withRel("createProduct"));
            entityModel.add(linkTo(methodOn(ProductRestController.class).createProductParameters(employee.getSuplierId())).withRel("Page_createProduct"));
            entityModel.add(linkTo(methodOn(ProductRestController.class).pageProduct(employee.getSuplierId(),null,null)).withRel("getPageProducts"));
            entityModel.add(linkTo(methodOn(ProductRestController.class).Page_pageProduct(employee.getSuplierId())).withRel("Page_getPageProducts"));
            entityModel.add(linkTo(methodOn(TourCampaignRestController.class).Page_createTourBooking()).withRel("Page_createTourBooking"));
            entityModel.add(linkTo(methodOn(TourCampaignRestController.class).createTourBooking(null)).withRel("createTourBooking"));
            entityModel.add(linkTo(methodOn(SupplierRestController.class).getEmployeeList(employee.getSuplierId(),null)).withRel("getEmployeeList"));
            entityModel.add(linkTo(methodOn(SupplierRestController.class).page_addEmployee(employee.getSuplierId())).withRel("Page_createEmployee"));
            entityModel.add(linkTo(methodOn(SupplierRestController.class).linkEmployee(employee.getSuplierId(),null)).withRel("createEmployee"));

            entityModel.add(linkTo(methodOn(RequestFuckRestController.class).getRequestList(employee.getSuplierId(),null,null)).withRel("getRequestList"));
            entityModel.add(linkTo(methodOn(SettingRestController.class).Page_getSettingList()).withRel("Page_getSettingList"));
            entityModel.add(linkTo(methodOn(RequestFuckRestController.class).page_getRequestList(employee.getSuplierId())).withRel("Page_getRequestList"));



        }
        entityModel.add(linkTo(methodOn(UserRestController.class).beGuide(user.getId())).withRel("beGuide"));

        if(!user.isRealNameVerified()){
            entityModel.add(linkTo(methodOn(UserRestController.class).postRealnameAuths(user.getId(),null)).withRel("realnameAuths"));
        }
     //   entityModel.add(linkTo(methodOn(PublicationRestController.class).pageUserPublicationResp(user.getId(),null,null,null)).withRel("getVoucherList"));
        entityModel.add(linkTo(methodOn(PublicationRestController.class).getVoucherList(user.getId(),null,null,null)).withRel("getVoucherList"));

        entityModel.add(linkTo(methodOn(TourCampaignRestController.class).Page_pageReservation()).withRel("Page_getBookingList"));
        entityModel.add(linkTo(methodOn(SearchRestController.class).searchTourBooking(null,null,null)).withRel("getBookingList"));
        entityModel.add(linkTo(methodOn(SearchRestController.class).Page_searchTourBooking()).withRel("Page_searchTourBooking"));

        entityModel.add(linkTo(methodOn(SearchRestController.class).searchTourBooking(null,null,null)).withRel("searchTourBooking"));

        entityModel.add(linkTo(methodOn(SearchRestController.class).searchUserForGuide(null,null,null)).withRel("searchUserForGuide"));


        entityModel.add(linkTo(methodOn(CampaignRestController.class).createCampaign(null)).withRel("createCampaign"));

        entityModel.add(linkTo(methodOn(CampaignRestController.class).Page_createCampaign()).withRel("Page_createCampaign"));



        entityModel.add(linkTo(methodOn(CampaignRestController.class).Page_getCampaignList()).withRel("Page_getCampaignList"));
        entityModel.add(linkTo(methodOn(CampaignRestController.class).getCampaignList(null,null)).withRel("getCampaignList"));



        entityModel.add(linkTo(methodOn(RoleRestController.class).pageRolesEnums(null,null)).withRel("getRoleList"));


        entityModel.add(linkTo(methodOn(RoleRestController.class).addRole(null)).withRel("createRole"));

        entityModel.add(linkTo(methodOn(GuideInchargeBookingRestController.class).listAvailability(user.getId(),null)).withRel("getInchargeTourList"));

        entityModel.add(linkTo(methodOn(SearchRestController.class).searchCampaign(null,null,null)).withRel("searchCampaign"));



        entityModel.add(linkTo(methodOn(SearchRestController.class).searchCompany(null,null,null)).withRel("searchSupplier"));
        entityModel.add(linkTo(methodOn(SearchRestController.class).Page_searchCompany()).withRel("Page_searchSupplier"));



        entityModel.add(linkTo(methodOn(SearchRestController.class).Page_searchRedemptions()).withRel("Page_searchRedemptions"));
        //entityModel.add(linkTo(methodOn(ExportRestController.class).createExport(null)).withRel("createExport"));
        entityModel.add(linkTo(methodOn(ExportRestController.class).pageExports(null,null)).withRel("getExportList"));


        Optional<Guide> optionalGuide = guideRepository.findByUserId(user.getId());
        if(optionalGuide.isPresent()){
            Guide guide = optionalGuide.get();
            userResp.setTour_guide(true);
            GuideSummaryVo guideSummaryVo = guideService.guideSummary(guide);

            EntityModel<GuideSummaryVo> guideSummaryVoEntityModel = EntityModel.of(guideSummaryVo);
            guideSummaryVoEntityModel.add(linkTo(methodOn(GuideRestController.class).listAvailability(guide.getId(),null,null)).withRel("getInchargeTourList"));

            userResp.setTour(guideSummaryVoEntityModel);
        }else{
            userResp.setTour_guide(false);
        }

        return entityModel;

    }

    public UserVo getInverUser(User user) {


        Optional<Openid> optionalOpenid = openidRepository.findByUserIdAndLink(user.getId(),true);
        UserVo userResp = null;
        if(optionalOpenid.isPresent()){
            userResp = UserVo.userWithOpenidLink(Pair.with(user,optionalOpenid.get()));
        }else{
            userResp = UserVo.from(user);
        }

        Optional<Employee> optional = employeeRepository.findByUserId(user.getId());
        if(optional.isPresent()){

            userResp.setHired(true);
            Optional<Supplier> optionalSupplier = supplierRepository.findById(optional.get().getSuplierId());
            Supplier supplier = optionalSupplier.get();
            userResp.setSupplier(supplier);

        }

        Optional<Guide> optionalGuide = guideRepository.findByUserId(user.getId());
        if(optionalGuide.isPresent()){
            Guide guide = optionalGuide.get();
            userResp.setTour_guide(true);

        }else{
            userResp.setTour_guide(false);
        }

        return userResp;

    }

}



/*
image.*.id	string
        The asset ID

        image.*.url	string
        The asset URL that you may use to serve the asset

        image.*.description	string
        A description of the asset

        image.*.is_image	boolean
        Whether the asset is an image

        image.*.filename	string
        The original filename that the file was uploaded with

        image.*.file_extension	string
        The file extension for the asset

        image.*.file_size	integer
        The file size in bytes

        image.*.image_dimensions.width	integer
        The width in pixels (if the asset is an image)

        image.*.image_dimensions.height	integer
        The height in pixels (if the asset is an image)

        image.*.meta	object
        A given array or keyed object with metadata

        image.*.created_at	integer
        A unix timestamp when the asset was originally uploaded*/
