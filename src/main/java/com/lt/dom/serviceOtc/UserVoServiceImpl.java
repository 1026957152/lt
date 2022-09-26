package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.*;
import com.lt.dom.controllerOct.*;
import com.lt.dom.controllerOct.Axh.XhEmployeeRestController;
import com.lt.dom.controllerOct.Axh.XhSupplierRestController;
import com.lt.dom.controllerOct.Axh.XhController;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.vo.GuideSummaryVo;
import com.lt.dom.vo.UserVo;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserVoServiceImpl {




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
    private BalanceServiceImpl balanceService;

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

    @Autowired
    private FeatureServiceImpl featureService;



    @Autowired
    private ApplyForApprovalServiceImpl requestService;

    public EntityModel getBigUser(User user) {


        Optional<Openid> optionalOpenid = openidRepository.findByUserIdAndLink(user.getId(),true);
        UserResp userResp = null;
        if(optionalOpenid.isPresent()){


            userResp = UserResp.userWithOpenidLink(Pair.with(user,optionalOpenid.get()));
        }else{
            userResp = UserResp.from(user);
        }
        EntityModel entityModel = EntityModel.of(userResp);




        Optional<EntityModel<RequestResp>> requestRespOptional = requestService.getByUser(user,userResp);

        if(requestRespOptional.isPresent()){

          //  RequestResp requestResp = RequestResp.simpleFrom(optionalRequest.get(),user);

            EntityModel<RequestResp> entityModel_ = requestRespOptional.get();

            userResp.setRequest(entityModel_);

           // SupplierResp supplierResp = SupplierResp.from(requestResp);
          //  supplierResp.setVerify_status(EnumSupplierVerifiedStatus.unverified.id());

           // EntityModel supplierRespEntityModel = EntityModel.of(supplierResp);
       //     userResp.setSupplier(supplierRespEntityModel);

            return entityModel;
        }




        Optional<Asset> optionalAsset =assetService.getQrOptional(user.getCode());
        if(optionalAsset.isPresent()){
            userResp.setAsset(AssetResp.from(optionalAsset.get()));
        }
        Optional<Employee> employeeOptional = employeeRepository.findByUserIdAndStatus(user.getId(),EnumEmployeeStatus.active);
        if(employeeOptional.isPresent()){

            Employee employee = employeeOptional.get();
            userResp.setHired(true);
            Optional<Supplier> optionalSupplier = supplierRepository.findById(employeeOptional.get().getSuplierId());


            Supplier supplier = optionalSupplier.get();


            Balance balance = balanceService.balance(supplier.getId(), EnumUserType.business);



            SupplierResp supplierResp = SupplierResp.from(optionalSupplier.get());

            EntityModel<BalanceResp> balanceRespEntityModel = EntityModel.of(BalanceResp.from(balance));
            balanceRespEntityModel.add(linkTo(methodOn(BalanceRestController.class).getBalance(1)).withSelfRel());
            supplierResp.setBalance(balanceRespEntityModel);
            EntityModel supplierRespEntityModel = EntityModel.of(supplierResp);
            supplierRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(employeeOptional.get().getSuplierId())).withRel("getSupplier"));
            supplierRespEntityModel.add(linkTo(methodOn(RedemptionRestController.class).getRedemptionEntrySummary(employeeOptional.get().getSuplierId())).withRel("getRedemptionSummary"));


            supplierRespEntityModel.add(linkTo(methodOn(GuideRestController.class).Page_getGuideList()).withRel("Page_getGuideList"));

            supplierRespEntityModel.add(linkTo(methodOn(GuideRestController.class).getGuideList(null,null,null)).withRel("getGuideList"));
            supplierRespEntityModel.add(linkTo(methodOn(DeviceController.class).registerDevice(null)).withRel("createDevice"));

            supplierRespEntityModel.add(linkTo(methodOn(DeviceController.class).getDeviceList(null,null)).withRel("getDeviceList"));
            supplierRespEntityModel.add(linkTo(methodOn(DeviceController.class).Page_getDeviceList()).withRel("Page_getDeviceList"));




            supplierRespEntityModel.add(linkTo(methodOn(ValueListRestController.class).Page_createValueList()).withRel("Page_createValueList"));
            supplierRespEntityModel.add(linkTo(methodOn(ValueListRestController.class).Page_getValueListList()).withRel("Page_getValueListList"));

            supplierRespEntityModel.add(linkTo(methodOn(ValueListRestController.class).getValueListList(null,null)).withRel("getValueList"));


            supplierRespEntityModel.add(linkTo(methodOn(ValueListRestController.class).createValueList(null)).withRel("createValueList"));


            supplierRespEntityModel.add(linkTo(methodOn(ComponentRightRestController.class).Page_createComponentRight(supplier.getId())).withRel("Page_createComponentRight"));

            supplierRespEntityModel.add(linkTo(methodOn(ComponentRightRestController.class).createComponentRight(supplier.getId(),null)).withRel("createComponentRight"));

            supplierRespEntityModel.add(linkTo(methodOn(ComponentRightRestController.class).getComponentRightList(supplier.getId(),null,null)).withRel("getComponentRightList"));

           supplierRespEntityModel.add(linkTo(methodOn(AttractionRestController.class).getAttractionList(supplier.getId(),null,null)).withRel("getAttractionList"));

            supplierRespEntityModel.add(linkTo(methodOn(AttractionRestController.class).Page_getAttractionList(supplier.getId())).withRel("Page_getAttractionList"));

            supplierRespEntityModel.add(linkTo(methodOn(AttractionRestController.class).createAttraction(supplier.getId(),null)).withRel("createAttraction"));



            supplierRespEntityModel.add(linkTo(methodOn(WindowTicketRestController.class).getCasher(supplier.getId())).withRel("getCashier"));

            supplierRespEntityModel.add(linkTo(methodOn(XhEmployeeRestController.class).getEmployerList(employee.getSuplierId(),null,null)).withRel("xh_getEmployerList"));
            supplierRespEntityModel.add(linkTo(methodOn(XhSupplierRestController.class).createEmployee(employee.getSuplierId(),null)).withRel("xh_createEmployer"));
            supplierRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).page_addEmployee(employee.getSuplierId())).withRel("Page_createEmployee"));


            userResp.setSupplier(supplierRespEntityModel);

         //   supplierResp.setVerify_status(EnumSupplierVerifiedStatus.verified.id());

            EntityModel<ReportSaleResp> reportSaleRespEntityModel = EntityModel.of(new ReportSaleResp());
            reportSaleRespEntityModel.add(linkTo(methodOn(ReportRestController.class).getPublicationStatistics(null)).withSelfRel());




            entityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(employeeOptional.get().getSuplierId())).withRel("getSupplier"));


            entityModel.add(linkTo(methodOn(RedemptionRestController.class).validateVoucherByCode(null)).withRel("redeem"));

            entityModel.add(linkTo(methodOn(RedemptionRestController.class).pageRedemptionEntry(employee.getSuplierId(),null,null)).withRel("getRedemptionEntryList"));
            entityModel.add(linkTo(methodOn(RedemptionRestController.class).Page_pageRedemptionEntry(employee.getSuplierId())).withRel("Page_getRedemptionEntryList"));
            entityModel.add(linkTo(methodOn(ProductRestController.class).createProduct_for_attraction(employee.getSuplierId(),null)).withRel("createProduct"));
            entityModel.add(linkTo(methodOn(ProductRestController.class).Page_createProduct(employee.getSuplierId())).withRel("Page_createProduct"));
            entityModel.add(linkTo(methodOn(ProductRestController.class).getProductList(employee.getSuplierId(),null,null)).withRel("getPageProducts"));
            entityModel.add(linkTo(methodOn(ProductRestController.class).Page_getProductList(employee.getSuplierId())).withRel("Page_getProductList"));
            entityModel.add(linkTo(methodOn(TourCampaignRestController.class).Page_createTourBooking()).withRel("Page_createTourBooking"));
            entityModel.add(linkTo(methodOn(TourCampaignRestController.class).createTourBooking(null,null)).withRel("createTourBooking"));
            entityModel.add(linkTo(methodOn(SupplierRestController.class).getEmployeeList(employee.getSuplierId(),null)).withRel("getEmployeeList"));
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

        entityModel.add(linkTo(methodOn(IntoOnecodeController.class).getPass(user.getId())).withRel("getOnecode"));


        entityModel.add(linkTo(methodOn(SearchRestController.class).Page_searchRedemptions()).withRel("Page_searchRedemptions"));
        //entityModel.add(linkTo(methodOn(ExportRestController.class).createExport(null)).withRel("createExport"));
        entityModel.add(linkTo(methodOn(ExportRestController.class).getExportList(null,null)).withRel("getExportList"));
        entityModel.add(linkTo(methodOn(ExportRestController.class).Page_getExportList()).withRel("Page_getExportList"));



        entityModel.add(linkTo(methodOn(ComponentRightRestController.class).getComponentVouncherList(user.getId(),null,null)).withRel("getComponentVoucherList"));

  //      entityModel.add(linkTo(methodOn(ComponentRightRestController.class).getComponentVouncherList(user.getId(),null,null)).withRel("getComponentVoucherList"));





        entityModel.add(linkTo(methodOn(XhSupplierRestController.class).createSupplier(null)).withRel("xh_createSupplier"));
        entityModel.add(linkTo(methodOn(XhSupplierRestController.class).pageSupplier(null,null)).withRel("xh_getSupplierList"));




        entityModel.add(linkTo(methodOn(EmployeeRestController.class).page_addEmployee()).withRel("Page_createEmployee"));

        entityModel.add(linkTo(methodOn(EmployeeRestController.class).getEmployeeList(null,null)).withRel("xh_getEmployerList"));
        entityModel.add(linkTo(methodOn(EmployeeRestController.class).createEmployee(null)).withRel("xh_createEmployer"));

            entityModel.add(linkTo(methodOn(XhController.class).getAllRequest(null,null)).withRel("xh_getPushRequestList"));
     entityModel.add(linkTo(methodOn(XhController.class).getPull(null,null)).withRel("xh_getYxdRequestList"));






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


        userResp.setLayout(featureService.meFill());;


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

        Optional<Employee> optional = employeeRepository.findByUserIdAndStatus(user.getId(),EnumEmployeeStatus.active);
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
