package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.*;
import com.lt.dom.controllerOct.*;
import com.lt.dom.controllerOct.Axh.XhEmployeeRestController;
import com.lt.dom.controllerOct.Axh.XhSupplierRestController;
import com.lt.dom.controllerOct.Axh.XhController;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.PrefereneVo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.vo.GuideSummaryVo;
import com.lt.dom.vo.UserVo;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserVoServiceImpl {


    @Value("${blog_flag}")
    boolean miniapp_release ;

    @Autowired
    private UserAuthorityServiceImpl userAuthorityService;
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
    private PassServiceImpl passService;


    @Autowired
    private PreferenceServiceImpl preferenceService;

    @Autowired
    private ApplyForApprovalServiceImpl requestService;

    public EntityModel getBigUser(User user, EnumLoginChannel web) {


        UserResp userResp =  UserResp.from(user);
        if(web.equals(EnumLoginChannel.miniapp)){


        }
        Optional<Openid> optionalOpenid =userAuthorityService.checkWeixinBind(user);//
        //openidRepository.findByUserIdAndLink(user.getId(),true);
        if(optionalOpenid.isPresent()){
            Openid openid = optionalOpenid.get();

            userResp = UserResp.userWithOpenidLink(Pair.with(user,openid));



            userResp.setBind(userAuthorityService.checkWeixinBind(openid,user));


        }


        EntityModel entityModel = EntityModel.of(userResp);

/*

        Optional<EntityModel<RequestResp>> requestRespOptional = requestService.getByUser(user,userResp);

        if(requestRespOptional.isPresent()){

            EntityModel<RequestResp> entityModel_ = requestRespOptional.get();

            userResp.setRequest(entityModel_);


            return entityModel;
        }
*/



        if(web.equals(EnumLoginChannel.web)){
            Optional<Asset> optionalAsset =assetService.getQrOptional(user.getCode());
            if(optionalAsset.isPresent()){
                userResp.setAsset(AssetResp.from(optionalAsset.get()));
            }
        }



        if(web.equals(EnumLoginChannel.web)){
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
                //    supplierRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).page_mainEmployee(employee.getSuplierId())).withRel("Page_createEmployee"));


                supplierRespEntityModel.add(linkTo(methodOn(TheatreRestController.class).Page_listTheatre(employee.getSuplierId())).withRel("Page_listTheatre"));


                //   supplierRespEntityModel.add(linkTo(methodOn(TheatreRestController.class).getTheatreList(employee.getSuplierId(),null,null)).withRel("getTheatreList"));

                supplierRespEntityModel.add(linkTo(methodOn(MediaRestController.class).Page_createMedia(employee.getSuplierId())).withRel("Page_createMedia"));
                supplierRespEntityModel.add(linkTo(methodOn(MediaRestController.class).getMediaList(employee.getSuplierId(),null,null)).withRel("getMediaList"));

                supplierRespEntityModel.add(linkTo(methodOn(MuseumRestController.class).Page_createMuseum(employee.getSuplierId())).withRel("Page_createMuseum"));
                supplierRespEntityModel.add(linkTo(methodOn(MuseumRestController.class).getMuseumList(employee.getSuplierId(),null,null)).withRel("getMuseumList"));




                supplierRespEntityModel.add(linkTo(methodOn(BookingMakeplanController.class).Page_createResource(employee.getSuplierId())).withRel("Page_createBookingResource"));

                supplierRespEntityModel.add(linkTo(methodOn(BookingMakeplanController.class).Page_createBookingService(employee.getSuplierId())).withRel("Page_createBookingService"));
                supplierRespEntityModel.add(linkTo(methodOn(BookingMakeplanController.class).getBookingServices(employee.getSuplierId(),null,null)).withRel("getBookingServiceList"));
                supplierRespEntityModel.add(linkTo(methodOn(BookingMakeplanController.class).getBookingResources(employee.getSuplierId(),null,null)).withRel("getBookingResourceList"));
                supplierRespEntityModel.add(linkTo(methodOn(BookingMakeplanController.class).getBookingProviders(employee.getSuplierId(),null,null)).withRel("getBookingProviderList"));


                supplierRespEntityModel.add(linkTo(methodOn(PassRestController.class).getBulkissuaknceList(employee.getSuplierId(),null,null)).withRel("getBulkIssuanceList"));
                supplierRespEntityModel.add(linkTo(methodOn(PassRestController.class).getPassList(null,employee.getSuplierId(),null,null)).withRel("getPassList"));
                supplierRespEntityModel.add(linkTo(methodOn(PassRestController.class).Page_createPass(employee.getSuplierId())).withRel("Page_createPass"));


                supplierRespEntityModel.add(linkTo(methodOn(PassRestController.class).Page_createBulkIssuance(employee.getSuplierId())).withRel("Page_createBulkIssuance"));
                supplierRespEntityModel.add(linkTo(methodOn(InvitationRestController.class).getInvitationList(employee.getSuplierId(),null,null)).withRel("getInvitationList"));
                supplierRespEntityModel.add(linkTo(methodOn(InvitationRestController.class).Page_createInvitation(employee.getSuplierId())).withRel("Page_createInvitation"));
                supplierRespEntityModel.add(linkTo(methodOn(PartnerRestController.class).getPartnerList(employee.getSuplierId(),null,null)).withRel("getPartnerList"));
                supplierRespEntityModel.add(linkTo(methodOn(PartnerRestController.class).Page_getPartnerList(employee.getSuplierId())).withRel("Page_getPartnerList"));

                supplierRespEntityModel.add(linkTo(methodOn(MarketRestController.class).Page_market()).withRel("Page_market"));



                supplierRespEntityModel.add(linkTo(methodOn(CityWalkRestController.class).Page_getCityWalkList(employee.getSuplierId())).withRel("Page_getCityWalkList"));



                supplierRespEntityModel.add(linkTo(methodOn(CityWalkRestController.class).getCityWalkList(employee.getSuplierId(),null,null)).withRel("getCityWalkList"));


                supplierRespEntityModel.add(linkTo(methodOn(InvoiceRestController.class).Page_listInvoice(employee.getSuplierId())).withRel("Page_listInvoice"));



                supplierRespEntityModel.add(linkTo(methodOn(SubscriptionRestController.class).Page_listSubscription(employee.getSuplierId())).withRel("Page_listSubscription"));

                //    supplierRespEntityModel.add(linkTo(methodOn(BookingRestController.class).getReservationList(employee.getSuplierId())).withRel("Page_listSubscription"));

                supplierRespEntityModel.add(linkTo(methodOn(BookingRestController.class).Page_listBooking(employee.getSuplierId())).withRel("Page_listBooking"));


                supplierRespEntityModel.add(linkTo(methodOn(MovieRestController.class).Page_listMovie(employee.getSuplierId())).withRel("Page_listMovie"));


                supplierRespEntityModel.add(linkTo(methodOn(SeatingLayoutRestController.class).Page_listSeatingLayout(employee.getSuplierId())).withRel("Page_listSeatingLayout"));

                supplierRespEntityModel.add(linkTo(methodOn(VoucherTicketRestController.class).Page_listSupplierVoucher(employee.getSuplierId())).withRel("Page_listVoucher"));

                supplierRespEntityModel.add(linkTo(methodOn(ThirdPartyRestController.class).Page_listThirdPartyProduct(employee.getSuplierId())).withRel("Page_listThirdPartyProduct"));
                supplierRespEntityModel.add(linkTo(methodOn(ThirdPartyRestController.class).Page_listThirdParty(employee.getSuplierId())).withRel("Page_listThirdParty"));

                supplierRespEntityModel.add(linkTo(methodOn(TripRestController.class).Page_listPlace(employee.getSuplierId())).withRel("Page_listPlace"));

                supplierRespEntityModel.add(linkTo(methodOn(ExtraRestController.class).Page_listExtra(employee.getSuplierId())).withRel("Page_listExtra"));

                supplierRespEntityModel.add(linkTo(methodOn(BlogRestController.class).Page_listBlog(employee.getSuplierId())).withRel("Page_listBlog"));


                supplierRespEntityModel.add(linkTo(methodOn(AgentRestController.class).Page_market_agent(employee.getSuplierId())).withRel("Page_agent"));
                supplierRespEntityModel.add(linkTo(methodOn(MarketRestController.class).Page_market_agent()).withRel("Page_market_agent"));

                supplierRespEntityModel.add(linkTo(methodOn(ReportRestController.class).Page_Booking_summary()).withRel("Page_Booking_summary"));

                supplierRespEntityModel.add(linkTo(methodOn(ReportRestController.class).Home_summary()).withRel("home_summary"));

                supplierRespEntityModel.add(linkTo(methodOn(HomeDashboardRestController.class).home(employee.getSuplierId())).withRel("Page_dashboard"));


                supplierRespEntityModel.add(linkTo(methodOn(ReportRestController.class).Page_booking_source()).withRel("Page_booking_source"));

                supplierRespEntityModel.add(linkTo(methodOn(ReportRestController.class).Page_arrivals()).withRel("Page_arrivals"));



                supplierRespEntityModel.add(linkTo(methodOn(AgentRestController.class).Page_agent(employee.getSuplierId())).withRel("Page_my_agent"));



                supplierRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).Page_mainEmployee(employee.getSuplierId())).withRel("Page_listEmployee"));

                supplierRespEntityModel.add(linkTo(methodOn(ComponentVoucherRestController.class).Page_component_vouchers(employee.getSuplierId())).withRel("Page_component_vouchers"));

                supplierRespEntityModel.add(linkTo(methodOn(MerchantSupplierRestController.class).Page_marchant_supplier(employee.getSuplierId())).withRel("Page_marchant_supplier"));

                supplierRespEntityModel.add(linkTo(methodOn(BusRestController.class).Page_listRoute(employee.getSuplierId())).withRel("Page_listRoute"));

                supplierRespEntityModel.add(linkTo(methodOn(BusRestController.class).Page_listBus(employee.getSuplierId())).withRel("Page_listBus"));

                supplierRespEntityModel.add(linkTo(methodOn(BusRestController.class).Page_listStop(employee.getSuplierId())).withRel("Page_listStop"));



                supplierRespEntityModel.add(linkTo(methodOn(RedemptionRestController.class).validate_by_id_number(null)).withRel("redeem_by_id_number"));




                userResp.setSupplier(supplierRespEntityModel);

                //   supplierResp.setVerify_status(EnumSupplierVerifiedStatus.verified.id());

                EntityModel<ReportSaleResp> reportSaleRespEntityModel = EntityModel.of(new ReportSaleResp());
                reportSaleRespEntityModel.add(linkTo(methodOn(ReportRestController.class).getPublicationStatistics(null)).withSelfRel());




                entityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(employeeOptional.get().getSuplierId())).withRel("getSupplier"));


                entityModel.add(linkTo(methodOn(RedemptionRestController.class).validateVoucherByCode(null)).withRel("redeem"));

                entityModel.add(linkTo(methodOn(RedemptionRestController.class).listRedemptionEntry(employee.getSuplierId(),null,null)).withRel("getRedemptionEntryList"));
                entityModel.add(linkTo(methodOn(RedemptionRestController.class).Page_pageRedemptionEntry(employee.getSuplierId())).withRel("Page_getRedemptionEntryList"));
                entityModel.add(linkTo(methodOn(ProductRestController.class).createProduct_for_attraction(employee.getSuplierId(),null)).withRel("createProduct"));
                entityModel.add(linkTo(methodOn(ProductRestController.class).Page_createProduct(employee.getSuplierId())).withRel("Page_createProduct"));
                entityModel.add(linkTo(methodOn(ProductRestController.class).getProductList(employee.getSuplierId(),null,null)).withRel("getPageProducts"));
                entityModel.add(linkTo(methodOn(ProductRestController.class).Page_getProductList(employee.getSuplierId())).withRel("Page_getProductList"));
                entityModel.add(linkTo(methodOn(TourCampaignRestController.class).Page_createTourBooking()).withRel("Page_createTourBooking"));
                entityModel.add(linkTo(methodOn(TourCampaignRestController.class).createTourBooking(null,null)).withRel("createTourBooking"));
                entityModel.add(linkTo(methodOn(SupplierRestController.class).listEmployee(employee.getSuplierId(),null,null)).withRel("getEmployeeList"));
                entityModel.add(linkTo(methodOn(SupplierRestController.class).linkEmployee(employee.getSuplierId(),null)).withRel("createEmployee"));

                entityModel.add(linkTo(methodOn(RequestFuckRestController.class).getRequestList(employee.getSuplierId(),null,null)).withRel("getRequestList"));
                entityModel.add(linkTo(methodOn(SettingRestController.class).Page_getSettingList(supplier.getId())).withRel("Page_getSettingList"));
                entityModel.add(linkTo(methodOn(RequestFuckRestController.class).page_getRequestList(employee.getSuplierId())).withRel("Page_getRequestList"));



                //  entityModel.add(linkTo(methodOn(RequestFuckRestController.class).page_getRequestList(employee.getSuplierId())).withRel("Page_getRequestList"));


                entityModel.add(linkTo(methodOn(UserRestController.class).getSupplier(employee.getSuplierId())).withRel("Page_getSupplier"));

            }else{
                userResp.setHired(false);
            }


            Preference preference = preferenceService.getValue(user,EnumPreference.working_space);

            EnumPreferenceSpace enumPreferenceSpace = EnumPreferenceSpace.valueOf(preference.getString_value());
            if(enumPreferenceSpace.equals(EnumPreferenceSpace.employee)){
                if(employeeOptional.isPresent()){
                    userResp.setFuctionLayout(featureService.meFuctionFill(user));;
                    userResp.setLayout(featureService.meFillEmployee(user,employeeOptional));;

                }else{
                    PrefereneVo prefereneVo = new PrefereneVo();
                    prefereneVo.setString_value(EnumPreferenceSpace.default_.name());
                    prefereneVo.setValue_type(EnumPreference.working_space.getType());
                    preference = preferenceService.update(preference,prefereneVo);

                    userResp.setLayout(featureService.meFill(user,employeeOptional));;

                }

            }
            if(enumPreferenceSpace.equals(EnumPreferenceSpace.default_)){

                userResp.setLayout(featureService.meFill(user,employeeOptional));;
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




            entityModel.add(linkTo(methodOn(EmployeeRestController.class).page_addEmployee()).withRel("Page_createSupplierEmployee"));




            entityModel.add(linkTo(methodOn(EmployeeRestController.class).getEmployeeList(null,null)).withRel("xh_getEmployerList"));
            entityModel.add(linkTo(methodOn(EmployeeRestController.class).createEmployee(null)).withRel("xh_createEmployer"));

            entityModel.add(linkTo(methodOn(XhController.class).getAllRequest(null,null)).withRel("xh_getPushRequestList"));
            entityModel.add(linkTo(methodOn(XhController.class).getPull(null,null)).withRel("xh_getYxdRequestList"));


            entityModel.add(linkTo(methodOn(UserRestController.class).getProfile(user.getId())).withRel("Page_getProfile"));








            entityModel.add(linkTo(methodOn(CustomerRestController.class).Page_listUser()).withRel("Page_listUser"));

            entityModel.add(linkTo(methodOn(PassRestController.class).Page_linkPass(user.getId())).withRel("Page_linkUser"));




            entityModel.add(linkTo(methodOn(RealnameAuthRestController.class).Page_realName()).withRel("Page_realName"));




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





        }



        if(!web.equals(EnumLoginChannel.handset)){
            passService.withMe(userResp,user);
        }

        return entityModel;

    }

    public EntityModel getBigUser_(User user) {

        Optional<Openid> optionalOpenid = userAuthorityService.checkWeixinBind(user);

        UserResp userResp = null;
        if(optionalOpenid.isPresent()){


            userResp = UserResp.userWithOpenidLink(Pair.with(user,optionalOpenid.get()));



        }else{
            userResp = UserResp.from(user);
        }


        EntityModel entityModel = EntityModel.of(userResp);




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
            //    supplierRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).page_mainEmployee(employee.getSuplierId())).withRel("Page_createEmployee"));


            supplierRespEntityModel.add(linkTo(methodOn(TheatreRestController.class).Page_listTheatre(employee.getSuplierId())).withRel("Page_listTheatre"));


            //   supplierRespEntityModel.add(linkTo(methodOn(TheatreRestController.class).getTheatreList(employee.getSuplierId(),null,null)).withRel("getTheatreList"));

            supplierRespEntityModel.add(linkTo(methodOn(MediaRestController.class).Page_createMedia(employee.getSuplierId())).withRel("Page_createMedia"));
            supplierRespEntityModel.add(linkTo(methodOn(MediaRestController.class).getMediaList(employee.getSuplierId(),null,null)).withRel("getMediaList"));

            supplierRespEntityModel.add(linkTo(methodOn(MuseumRestController.class).Page_createMuseum(employee.getSuplierId())).withRel("Page_createMuseum"));
            supplierRespEntityModel.add(linkTo(methodOn(MuseumRestController.class).getMuseumList(employee.getSuplierId(),null,null)).withRel("getMuseumList"));




            supplierRespEntityModel.add(linkTo(methodOn(BookingMakeplanController.class).Page_createResource(employee.getSuplierId())).withRel("Page_createBookingResource"));

            supplierRespEntityModel.add(linkTo(methodOn(BookingMakeplanController.class).Page_createBookingService(employee.getSuplierId())).withRel("Page_createBookingService"));
            supplierRespEntityModel.add(linkTo(methodOn(BookingMakeplanController.class).getBookingServices(employee.getSuplierId(),null,null)).withRel("getBookingServiceList"));
            supplierRespEntityModel.add(linkTo(methodOn(BookingMakeplanController.class).getBookingResources(employee.getSuplierId(),null,null)).withRel("getBookingResourceList"));
            supplierRespEntityModel.add(linkTo(methodOn(BookingMakeplanController.class).getBookingProviders(employee.getSuplierId(),null,null)).withRel("getBookingProviderList"));


            supplierRespEntityModel.add(linkTo(methodOn(PassRestController.class).getBulkissuaknceList(employee.getSuplierId(),null,null)).withRel("getBulkIssuanceList"));
            supplierRespEntityModel.add(linkTo(methodOn(PassRestController.class).getPassList(null,employee.getSuplierId(),null,null)).withRel("getPassList"));
            supplierRespEntityModel.add(linkTo(methodOn(PassRestController.class).Page_createPass(employee.getSuplierId())).withRel("Page_createPass"));


            supplierRespEntityModel.add(linkTo(methodOn(PassRestController.class).Page_createBulkIssuance(employee.getSuplierId())).withRel("Page_createBulkIssuance"));
            supplierRespEntityModel.add(linkTo(methodOn(InvitationRestController.class).getInvitationList(employee.getSuplierId(),null,null)).withRel("getInvitationList"));
            supplierRespEntityModel.add(linkTo(methodOn(InvitationRestController.class).Page_createInvitation(employee.getSuplierId())).withRel("Page_createInvitation"));
            supplierRespEntityModel.add(linkTo(methodOn(PartnerRestController.class).getPartnerList(employee.getSuplierId(),null,null)).withRel("getPartnerList"));
            supplierRespEntityModel.add(linkTo(methodOn(PartnerRestController.class).Page_getPartnerList(employee.getSuplierId())).withRel("Page_getPartnerList"));

            supplierRespEntityModel.add(linkTo(methodOn(MarketRestController.class).Page_market()).withRel("Page_market"));



            supplierRespEntityModel.add(linkTo(methodOn(CityWalkRestController.class).Page_getCityWalkList(employee.getSuplierId())).withRel("Page_getCityWalkList"));



            supplierRespEntityModel.add(linkTo(methodOn(CityWalkRestController.class).getCityWalkList(employee.getSuplierId(),null,null)).withRel("getCityWalkList"));


            supplierRespEntityModel.add(linkTo(methodOn(InvoiceRestController.class).Page_listInvoice(employee.getSuplierId())).withRel("Page_listInvoice"));



            supplierRespEntityModel.add(linkTo(methodOn(SubscriptionRestController.class).Page_listSubscription(employee.getSuplierId())).withRel("Page_listSubscription"));

            //    supplierRespEntityModel.add(linkTo(methodOn(BookingRestController.class).getReservationList(employee.getSuplierId())).withRel("Page_listSubscription"));

            supplierRespEntityModel.add(linkTo(methodOn(BookingRestController.class).Page_listBooking(employee.getSuplierId())).withRel("Page_listBooking"));


            supplierRespEntityModel.add(linkTo(methodOn(MovieRestController.class).Page_listMovie(employee.getSuplierId())).withRel("Page_listMovie"));


            supplierRespEntityModel.add(linkTo(methodOn(SeatingLayoutRestController.class).Page_listSeatingLayout(employee.getSuplierId())).withRel("Page_listSeatingLayout"));

            supplierRespEntityModel.add(linkTo(methodOn(VoucherTicketRestController.class).Page_listSupplierVoucher(employee.getSuplierId())).withRel("Page_listVoucher"));

            supplierRespEntityModel.add(linkTo(methodOn(ThirdPartyRestController.class).Page_listThirdPartyProduct(employee.getSuplierId())).withRel("Page_listThirdPartyProduct"));
            supplierRespEntityModel.add(linkTo(methodOn(ThirdPartyRestController.class).Page_listThirdParty(employee.getSuplierId())).withRel("Page_listThirdParty"));

            supplierRespEntityModel.add(linkTo(methodOn(TripRestController.class).Page_listPlace(employee.getSuplierId())).withRel("Page_listPlace"));

            supplierRespEntityModel.add(linkTo(methodOn(ExtraRestController.class).Page_listExtra(employee.getSuplierId())).withRel("Page_listExtra"));

            supplierRespEntityModel.add(linkTo(methodOn(BlogRestController.class).Page_listBlog(employee.getSuplierId())).withRel("Page_listBlog"));


            supplierRespEntityModel.add(linkTo(methodOn(AgentRestController.class).Page_market_agent(employee.getSuplierId())).withRel("Page_agent"));
            supplierRespEntityModel.add(linkTo(methodOn(MarketRestController.class).Page_market_agent()).withRel("Page_market_agent"));

            supplierRespEntityModel.add(linkTo(methodOn(ReportRestController.class).Page_Booking_summary()).withRel("Page_Booking_summary"));

            supplierRespEntityModel.add(linkTo(methodOn(ReportRestController.class).Home_summary()).withRel("home_summary"));

            supplierRespEntityModel.add(linkTo(methodOn(HomeDashboardRestController.class).home(employee.getSuplierId())).withRel("Page_dashboard"));


            supplierRespEntityModel.add(linkTo(methodOn(ReportRestController.class).Page_booking_source()).withRel("Page_booking_source"));

            supplierRespEntityModel.add(linkTo(methodOn(ReportRestController.class).Page_arrivals()).withRel("Page_arrivals"));



            supplierRespEntityModel.add(linkTo(methodOn(AgentRestController.class).Page_agent(employee.getSuplierId())).withRel("Page_my_agent"));



            supplierRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).Page_mainEmployee(employee.getSuplierId())).withRel("Page_listEmployee"));

            supplierRespEntityModel.add(linkTo(methodOn(ComponentVoucherRestController.class).Page_component_vouchers(employee.getSuplierId())).withRel("Page_component_vouchers"));

            supplierRespEntityModel.add(linkTo(methodOn(MerchantSupplierRestController.class).Page_marchant_supplier(employee.getSuplierId())).withRel("Page_marchant_supplier"));

            supplierRespEntityModel.add(linkTo(methodOn(BusRestController.class).Page_listRoute(employee.getSuplierId())).withRel("Page_listRoute"));

            supplierRespEntityModel.add(linkTo(methodOn(BusRestController.class).Page_listBus(employee.getSuplierId())).withRel("Page_listBus"));

            supplierRespEntityModel.add(linkTo(methodOn(BusRestController.class).Page_listStop(employee.getSuplierId())).withRel("Page_listStop"));



            supplierRespEntityModel.add(linkTo(methodOn(RedemptionRestController.class).validate_by_id_number(null)).withRel("redeem_by_id_number"));




            userResp.setSupplier(supplierRespEntityModel);

            //   supplierResp.setVerify_status(EnumSupplierVerifiedStatus.verified.id());

            EntityModel<ReportSaleResp> reportSaleRespEntityModel = EntityModel.of(new ReportSaleResp());
            reportSaleRespEntityModel.add(linkTo(methodOn(ReportRestController.class).getPublicationStatistics(null)).withSelfRel());




            entityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(employeeOptional.get().getSuplierId())).withRel("getSupplier"));


            entityModel.add(linkTo(methodOn(RedemptionRestController.class).validateVoucherByCode(null)).withRel("redeem"));

            entityModel.add(linkTo(methodOn(RedemptionRestController.class).listRedemptionEntry(employee.getSuplierId(),null,null)).withRel("getRedemptionEntryList"));
            entityModel.add(linkTo(methodOn(RedemptionRestController.class).Page_pageRedemptionEntry(employee.getSuplierId())).withRel("Page_getRedemptionEntryList"));
            entityModel.add(linkTo(methodOn(ProductRestController.class).createProduct_for_attraction(employee.getSuplierId(),null)).withRel("createProduct"));
            entityModel.add(linkTo(methodOn(ProductRestController.class).Page_createProduct(employee.getSuplierId())).withRel("Page_createProduct"));
            entityModel.add(linkTo(methodOn(ProductRestController.class).getProductList(employee.getSuplierId(),null,null)).withRel("getPageProducts"));
            entityModel.add(linkTo(methodOn(ProductRestController.class).Page_getProductList(employee.getSuplierId())).withRel("Page_getProductList"));
            entityModel.add(linkTo(methodOn(TourCampaignRestController.class).Page_createTourBooking()).withRel("Page_createTourBooking"));
            entityModel.add(linkTo(methodOn(TourCampaignRestController.class).createTourBooking(null,null)).withRel("createTourBooking"));
            entityModel.add(linkTo(methodOn(SupplierRestController.class).listEmployee(employee.getSuplierId(),null,null)).withRel("getEmployeeList"));
            entityModel.add(linkTo(methodOn(SupplierRestController.class).linkEmployee(employee.getSuplierId(),null)).withRel("createEmployee"));

            entityModel.add(linkTo(methodOn(RequestFuckRestController.class).getRequestList(employee.getSuplierId(),null,null)).withRel("getRequestList"));
            entityModel.add(linkTo(methodOn(SettingRestController.class).Page_getSettingList(supplier.getId())).withRel("Page_getSettingList"));
            entityModel.add(linkTo(methodOn(RequestFuckRestController.class).page_getRequestList(employee.getSuplierId())).withRel("Page_getRequestList"));



            //  entityModel.add(linkTo(methodOn(RequestFuckRestController.class).page_getRequestList(employee.getSuplierId())).withRel("Page_getRequestList"));


            entityModel.add(linkTo(methodOn(UserRestController.class).getSupplier(employee.getSuplierId())).withRel("Page_getSupplier"));

        }else{
            userResp.setHired(false);
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




        entityModel.add(linkTo(methodOn(EmployeeRestController.class).page_addEmployee()).withRel("Page_createSupplierEmployee"));




        entityModel.add(linkTo(methodOn(EmployeeRestController.class).getEmployeeList(null,null)).withRel("xh_getEmployerList"));
        entityModel.add(linkTo(methodOn(EmployeeRestController.class).createEmployee(null)).withRel("xh_createEmployer"));

        entityModel.add(linkTo(methodOn(XhController.class).getAllRequest(null,null)).withRel("xh_getPushRequestList"));
        entityModel.add(linkTo(methodOn(XhController.class).getPull(null,null)).withRel("xh_getYxdRequestList"));


        entityModel.add(linkTo(methodOn(UserRestController.class).getProfile(user.getId())).withRel("Page_getProfile"));








        entityModel.add(linkTo(methodOn(CustomerRestController.class).Page_listUser()).withRel("Page_listUser"));

        entityModel.add(linkTo(methodOn(PassRestController.class).Page_linkPass(user.getId())).withRel("Page_linkUser"));




        entityModel.add(linkTo(methodOn(RealnameAuthRestController.class).Page_realName()).withRel("Page_realName"));




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


        Preference preference = preferenceService.getValue(user,EnumPreference.working_space);

        EnumPreferenceSpace enumPreferenceSpace = EnumPreferenceSpace.valueOf(preference.getString_value());
        if(enumPreferenceSpace.equals(EnumPreferenceSpace.employee)){
            if(employeeOptional.isPresent()){
                userResp.setFuctionLayout(featureService.meFuctionFill(user));;
                userResp.setLayout(featureService.meFillEmployee(user,employeeOptional));;

            }else{
                PrefereneVo prefereneVo = new PrefereneVo();
                prefereneVo.setString_value(EnumPreferenceSpace.default_.name());
                prefereneVo.setValue_type(EnumPreference.working_space.getType());
                preference = preferenceService.update(preference,prefereneVo);

                userResp.setLayout(featureService.meFill(user,employeeOptional));;

            }

        }
        if(enumPreferenceSpace.equals(EnumPreferenceSpace.default_)){

            userResp.setLayout(featureService.meFill(user,employeeOptional));;
        }



        passService.withMe(userResp,user);
        return entityModel;

    }




    public UserVo getInverUser(User user) {


        Optional<Openid> optionalOpenid = userAuthorityService.checkWeixinBind(user);


        UserVo userResp = null;
        if(optionalOpenid.isPresent()){
            userResp = UserVo.userWithOpenidLink(Pair.with(user,optionalOpenid.get()));
            userResp.setBind(userAuthorityService.checkWeixinBind(optionalOpenid.get(),user));
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
