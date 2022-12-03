package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.home.FeatureResp;
import com.lt.dom.OctResp.home.HomeResp;
import com.lt.dom.OctResp.layout.LayoutResp;
import com.lt.dom.controllerOct.*;
import com.lt.dom.oct.CityWalk;
import com.lt.dom.oct.Employee;
import com.lt.dom.oct.User;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.CityWalkRepository;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class FeatureServiceImpl {

    @Value("${blog_flag}")
    boolean miniapp_release ;
    @Autowired
    private CityWalkRepository cityWalkRepository;

    @Autowired
    private FileStorageServiceImpl fileStorageService;
    @Autowired
    private PreferenceServiceImpl preferenceService;



    public void fill(HomeResp homeResp) {

        List<Enumfeatured> enumfeatureds = Arrays.stream(Enumfeatured.values()).collect(Collectors.toList());
        if(miniapp_release){
            enumfeatureds = new ArrayList<>();
        }

        homeResp.setFeatures(enumfeatureds.stream().map(e->{

            FeatureResp resp = FeatureResp.from(e);
            EntityModel<FeatureResp> entityModel1 = EntityModel.of(resp);


           // resp.setFeature_image(fileStorageService.loadDocument(Arrays.asList(EnumPhotos.thumb),EnumDocumentType.home_page_feature_logo,e.name()));
            resp.setFeature_image(e.getFeature_image());
            if(e.equals(Enumfeatured.tours)){
                String link = linkTo(methodOn(TripRestController.class).Page_listTrip()).withSelfRel().getHref();
                //  entityModel1.add(linkTo(methodOn(BookingMakeplanController.class).getGuideList(3,null,null)).withSelfRel());
                resp.setPath("/pages/tours/list?url="+link);
            }
            if(e.equals(Enumfeatured.city_hero)){
                String link = linkTo(methodOn(IndexController.class).heroPass(null)).withSelfRel().getHref();
                entityModel1.add(linkTo(methodOn(IndexController.class).heroPass(null)).withSelfRel());
                resp.setPath("/pages/onecard/show?url="+link);
            }

            if(e.equals(Enumfeatured.city_pass)){
                String link = linkTo(methodOn(IndexController.class).cityPass(null)).withSelfRel().getHref();
                entityModel1.add(linkTo(methodOn(IndexController.class).cityPass(null)).withSelfRel());
                resp.setPath("/pages/passcard/show?url="+link);
            }



            if(e.equals(Enumfeatured.线上博物馆)){
                String link = linkTo(methodOn(IndexController.class).museum(null)).withSelfRel().getHref();
                entityModel1.add(linkTo(methodOn(IndexController.class).museum(null)).withSelfRel());
                resp.setPath("/pages/museum/list?url="+link);
            }
            if(e.equals(Enumfeatured.City_walk)){

                List<CityWalk> cityWalks = cityWalkRepository.findAll();
                CityWalk cityWalk = cityWalks.get(0);

                String link = linkTo(methodOn(CityWalkRestController.class).getCityWalk(cityWalk.getId())).withSelfRel().getHref();
                entityModel1.add(linkTo(methodOn(CityWalkRestController.class).getCityWalk(cityWalk.getId())).withSelfRel());
                resp.setPath(EnumMiniappPagePath.city_walk.getPath()+"/pages/citywalk/show?url="+link);
            }
            if(e.equals(Enumfeatured.activity)){
                String link = linkTo(methodOn(IndexController.class).activities(null)).withSelfRel().getHref();
                //  entityModel1.add(linkTo(methodOn(BookingMakeplanController.class).getGuideList(3,null,null)).withSelfRel());
                resp.setPath("/pages/activitie/list?url="+link);
            }
            if(e.equals(Enumfeatured.rentcars)){
                String link = linkTo(methodOn(CarRestController.class).Page_cars()).withSelfRel().getHref();
                //  entityModel1.add(linkTo(methodOn(BookingMakeplanController.class).getGuideList(3,null,null)).withSelfRel());
                resp.setPath("/pages/rentcars/list?url="+link);
            }


            if(e.equals(Enumfeatured.导游)){
                String link = linkTo(methodOn(BookingMakeplanController.class).home(3,null,null)).withSelfRel().getHref();
              //  entityModel1.add(linkTo(methodOn(BookingMakeplanController.class).getGuideList(3,null,null)).withSelfRel());
                resp.setPath("/pages/provider/list?url="+link);
            }
            if(e.equals(Enumfeatured.bigdata)){
                String link = linkTo(methodOn(BookingMakeplanController.class).home(3,null,null)).withSelfRel().getHref();
                //  entityModel1.add(linkTo(methodOn(BookingMakeplanController.class).getGuideList(3,null,null)).withSelfRel());
                resp.setPath("/pages/bigdata/list?url="+link);
            }

            if(e.equals(Enumfeatured.信用榆林)){
                String link = linkTo(methodOn(RewardRestController.class).home()).withSelfRel().getHref();
                //  entityModel1.add(linkTo(methodOn(BookingMakeplanController.class).getGuideList(3,null,null)).withSelfRel());
                resp.setPath("/pages/rewards/index?url="+link);
            }
            if(e.equals(Enumfeatured.驼城小易)){
                String link = linkTo(methodOn(BusRestController.class).Page_bues()).withSelfRel().getHref();
                //  entityModel1.add(linkTo(methodOn(BookingMakeplanController.class).getGuideList(3,null,null)).withSelfRel());
                resp.setPath("/pages/bus/index?url="+link);
            }
            return entityModel1;
        }).collect(Collectors.toList()));
    }

    public LayoutResp meFillEmployee(User user, Optional<Employee> employeeOptional) {


        Map<String,Object> map =
                Arrays.asList(EnumLayoutFeatured.核销列表,
                                EnumLayoutFeatured.商户首页,
                                EnumLayoutFeatured.核销详情,
                                EnumLayoutFeatured.授权核销,

                                EnumLayoutFeatured.职工列表).stream()
                        .collect(Collectors.groupingBy(e->e.getGroup())).entrySet()
                        .stream().map(e->{

                            return Pair.with(e.getKey(),e.getValue().stream().map(ee->{

                                return filter(user,ee,employeeOptional);


                            }).collect(Collectors.toList()));

                        }).collect(Collectors.toMap(e->e.getValue0(),e->e.getValue1()));

        LayoutResp layoutResp = new LayoutResp();
        layoutResp.setName("home");
        layoutResp.setVersion(1);
        layoutResp.setLayout(map);

        return layoutResp;
    }
    public EntityModel<FeatureResp> filter(User user,EnumLayoutFeatured ee, Optional<Employee> employeeOptional) {




                                FeatureResp resp = FeatureResp.from(ee);


                                    System.out.println("是企业职工 "+ resp.getPath());

                                    if(ee.equals(EnumLayoutFeatured.商户首页)){

                                        Employee employee = employeeOptional.get();

                                        String link = linkTo(methodOn(RedemptionRestController.class).listRedemptionEntry(employee.getSuplierId(),null,null)).withSelfRel().getHref();
                                        // entityModel1.add(linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel());
                                        resp.setPath("/pages/admin/index?url="+link);
                                    }

                                    else

                                    if(ee.equals(EnumLayoutFeatured.核销列表)){
                                        Employee employee = employeeOptional.get();
                                        String link = linkTo(methodOn(RedemptionRestController.class).listRedemption(employee.getSuplierId(),null,null)).withSelfRel().getHref();

                                        // entityModel1.add(linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel());
                                        resp.setPath("/pages/admin/writeoff/list?url="+link);
                                    }
                                    else if(ee.equals(EnumLayoutFeatured.职工列表)){
                                        Employee employee = employeeOptional.get();
                                        String link = linkTo(methodOn(SupplierRestController.class).Page_listEmployeePhone(employee.getSuplierId())).withSelfRel().getHref();

                                        // entityModel1.add(linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel());
                                        resp.setPath(EnumMiniappPagePath.employee_list.path+"?url="+link);
                                    }
                                    else if(ee.equals(EnumLayoutFeatured.授权核销)){
                                        Employee employee = employeeOptional.get();
                                        String link = linkTo(methodOn(ValidatorRestController.class).Page_validators(user.getId())).withSelfRel().getHref();

                                        // entityModel1.add(linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel());
                                        resp.setPath(EnumMiniappPagePath.user_allowed_component_right.getPath()+"?url="+link);
                                    }

                                    else
                                    if(ee.equals(EnumLayoutFeatured.核销详情)){
                                        Employee employee = employeeOptional.get();
                                        String link = linkTo(methodOn(RedemptionRestController.class).getRedemptionEntrySummary(employee.getSuplierId())).withSelfRel().getHref();

                                        // entityModel1.add(linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel());
                                        resp.setPath("/pages/admin/writeoff/show?url="+link);
                                    }else



                                    if(ee.equals(EnumLayoutFeatured.全部订单)){
                                        String link = linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel().getHref();
                                        // entityModel1.add(linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel());
                                        resp.setPath("/pages/user/order/list?url="+link);
                                    }
                                    else if(ee.equals(EnumLayoutFeatured.待付款)){
                                        String link = linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel().getHref();
                                        // entityModel1.add(linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel());
                                        resp.setPath("/pages/user/order/list?url="+link);
                                    }
                                    else if(ee.equals(EnumLayoutFeatured.优惠券)){
                                        String link = linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel().getHref();
                                        // entityModel1.add(linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel());
                                        resp.setPath("/pages/user/order/list?url="+link);
                                    }
                                    else if(ee.equals(EnumLayoutFeatured.退款_售后)){
                                        String link = linkTo(methodOn(BookingRestController.class).getReservationListForRefund(user.getId(),null,null)).withSelfRel().getHref();
                                        // entityModel1.add(linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel());
                                        resp.setPath("/pages/user/order/list?url="+link);
                                    }



                                    else if(ee.equals(EnumLayoutFeatured.卡包)){
                                        String link = linkTo(methodOn(PassRestController.class).getPassListForUser(user.getId(),null,null)).withSelfRel().getHref();
                                        // entityModel1.add(linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel());
                                        resp.setPath("/pages/user/card/list?url="+link);
                                    }
                                    else if(ee.equals(EnumLayoutFeatured.门票)){
                                        String link = linkTo(methodOn(VoucherTicketRestController.class).listVoucher(user.getId(),null,null)).withSelfRel().getHref();
                                        // entityModel1.add(linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel());
                                        resp.setPath("/pages/user/tickets/list?url="+link);
                                    }

                                    else if(ee.equals(EnumLayoutFeatured.申请入驻)){
                                        String link = linkTo(methodOn(OpenidRestController.class).Page_merchants_settled()).withSelfRel().getHref();
                                        // entityModel1.add(linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel());
                                        resp.setPath("/pages/admin/join?url="+link);
                                    }
                                    else if(ee.equals(EnumLayoutFeatured.主人卡激活)){
                                        String link = linkTo(methodOn(PassRestController.class).Page_activePass()).withSelfRel().getHref();
                                        resp.setPath("/pages/ownercard/activate?url="+link);
                                    }
                                    else if(ee.equals(EnumLayoutFeatured.添加卡)){
                                        String link = linkTo(methodOn(PassRestController.class).Page_linkPass(user.getId())).withSelfRel().getHref();
                                        resp.setPath("/pages/user/card/add?url="+link);
                                    }
                                    else if(ee.equals(EnumLayoutFeatured.客服中心)){
                                        String link = linkTo(methodOn(IndexController.class).Page_support(user.getId(),null)).withSelfRel().getHref();
                                        resp.setPath(EnumMiniappPagePath.user_support.path+"?url="+link);
                                    }

                                    else  if(ee.equals(EnumLayoutFeatured.常用出行人)){
                                        String link = linkTo(methodOn(PassengerRestController.class).getPassengerList(user.getId(),null,null)).withSelfRel().getHref();
                                        //  entityModel1.add(linkTo(methodOn(IndexController.class).cityPass(null)).withSelfRel());
                                        resp.setPath("/pages/user/people/list?url="+link);
                                    }
                                    else  if(ee.equals(EnumLayoutFeatured.收货地址)){
                                        String link = linkTo(methodOn(AddressRestController.class).Page_listUserAddress(user.getId())).withSelfRel().getHref();
                                        //  entityModel1.add(linkTo(methodOn(IndexController.class).cityPass(null)).withSelfRel());
                                        resp.setPath(EnumMiniappPagePath.address_list.path+"?url="+link);


                                    }

                                    else  {
                                        String link = linkTo(methodOn(IndexController.class).heroPass(null)).withSelfRel().getHref();
                                        //  entityModel1.add(linkTo(methodOn(IndexController.class).cityPass(null)).withSelfRel());
                                        resp.setPath("/pages/museum/list?url="+link);
                                        //resp.setUrl("/pages/onecard/show?url="+link);
                                    }

        EntityModel<FeatureResp> entityModel1 = EntityModel.of(resp);

                                    System.out.println("------------path"+ resp.getPath());
                                return entityModel1;


    }


    public LayoutResp meFill(User user, Optional<Employee> employeeOptional) {

        System.out.println(" 这里是 meFill");

      List companyAllowed = Arrays.asList(EnumLayoutFeatured.核销列表,EnumLayoutFeatured.商户首页,EnumLayoutFeatured.核销详情,EnumLayoutFeatured.职工列表,EnumLayoutFeatured.授权核销);


      List<EnumLayoutFeatured> enumLayoutFeatureds = Arrays.stream(EnumLayoutFeatured.values()).collect(Collectors.toList());
if(miniapp_release){
    enumLayoutFeatureds = new ArrayList<>();
}

        Map<String,Object> map =
                enumLayoutFeatureds.stream().filter(e->{
                          //  return !companyAllowed.contains(e);
                    if(companyAllowed.contains(e)){

                        return false;
                    }
 /*                      if(!employeeOptional.isEmpty()){
                           return !companyAllowed.contains(e);

                              //  return !companyAllowed.contains(e);
                                //      return !(e.equals(EnumLayoutFeatured.核销列表) || e.equals(EnumLayoutFeatured.商户首页) || e.equals(EnumLayoutFeatured.核销详情));
                            }*/


                        if(e.equals(EnumLayoutFeatured.申请入驻)){
                            if(employeeOptional.isPresent()) {
                                return false;
                            }

                        }


                            return true;

                        })
                        .collect(Collectors.groupingBy(e->e.getGroup())).entrySet()
                        .stream().map(e->{

            return Pair.with(e.getKey(),e.getValue().stream().map(ee->{


                return filter(user,ee,employeeOptional);


            }).collect(Collectors.toList()));

        }).collect(Collectors.toMap(e->e.getValue0(),e->e.getValue1()));

        LayoutResp layoutResp = new LayoutResp();
        layoutResp.setName("home");
        layoutResp.setVersion(1);
        layoutResp.setLayout(map);

        return layoutResp;
    }


    public Map<String,EntityModel> meFuctionFill(User user) {



        Map<String,EntityModel> map = Arrays.asList(EnumLayoutFeaturedFuction.扫码核销,EnumLayoutFeaturedFuction.输码核销).stream()
                .collect(Collectors.toMap(e->e.getKey(),e->{



                    FeatureResp resp = FeatureResp.from(e);
                    EntityModel<FeatureResp> entityModel1 = EntityModel.of(resp);

//
                    //       商户首页（核销操作，数据统计都在首页）



                    if(e.equals(EnumLayoutFeatured.商户首页)){
                        String link = linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel().getHref();
                        // entityModel1.add(linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel());
                        resp.setPath("/pages/supplier/index?url="+link);
                    }
                    if(e.equals(EnumLayoutFeatured.核销列表)){
                        String link = linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel().getHref();
                        // entityModel1.add(linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel());
                        resp.setPath("/pages/supplier/writeoff/list?url="+link);
                    }


                    return entityModel1;

        }));


        return map;
    }


    public LayoutResp meFillWithoutUser() {

        Map<String,Object> map =Arrays.asList(EnumLayoutFeaturedOther.更换账号,EnumLayoutFeaturedOther.登出).stream().collect(Collectors.groupingBy(e->e.getGroup())).entrySet()
                .stream().map(e->{

                    return Pair.with(e.getKey(),e.getValue().stream().map(ee->{


                        FeatureResp resp = FeatureResp.from(ee);
                        EntityModel<FeatureResp> entityModel1 = EntityModel.of(resp);


                        //  resp.setIcon(fileStorageService.loadDocument(Arrays.asList(EnumPhotos.thumb),EnumDocumentType.home_page_feature_logo,ee.name()).getUrl_thumbnail());


                        if(ee.equals(EnumLayoutFeatured.主人卡激活)){
                            String link = linkTo(methodOn(PassRestController.class).Page_activePass()).withSelfRel().getHref();
                            resp.setPath("/pages/ownercard/activate?url="+link);
                        }
                        else{
                            String link = linkTo(methodOn(IndexController.class).heroPass(null)).withSelfRel().getHref();
                            //  entityModel1.add(linkTo(methodOn(IndexController.class).cityPass(null)).withSelfRel());
                            resp.setPath("/pages/museum/list?url="+link);
                            //resp.setUrl("/pages/onecard/show?url="+link);
                        }



                        return entityModel1;

                    }).collect(Collectors.toList()));

                }).collect(Collectors.toMap(e->e.getValue0(),e->e.getValue1()));

        LayoutResp layoutResp = new LayoutResp();
        layoutResp.setName("home");
        layoutResp.setVersion(1);
        layoutResp.setLayout(map);

        return layoutResp;
    }





    public LayoutResp profileFill(User user) {

        Map<String,Object> map = Arrays.asList(EnumLayoutFeaturedOther.更换账号,EnumLayoutFeaturedOther.登出).stream().collect(Collectors.groupingBy(e->e.getTitle())).entrySet()
                .stream().map(e->{

                    return Pair.with(e.getKey(),e.getValue().stream().map(ee->{


                        FeatureResp resp = FeatureResp.from(ee);
                        EntityModel<FeatureResp> entityModel1 = EntityModel.of(resp);


                        //  resp.setIcon(fileStorageService.loadDocument(Arrays.asList(EnumPhotos.thumb),EnumDocumentType.home_page_feature_logo,ee.name()).getUrl_thumbnail());

                        if(ee.equals(EnumLayoutFeatured.全部订单)){
                            String link = linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel().getHref();
                            // entityModel1.add(linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel());
                            resp.setPath("/pages/user/order/list?url="+link);
                        }
                        else if(ee.equals(EnumLayoutFeatured.待付款)){
                            String link = linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel().getHref();
                            // entityModel1.add(linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel());
                            resp.setPath("/pages/user/order/list?url="+link);
                        }
                        else if(ee.equals(EnumLayoutFeatured.优惠券)){
                            String link = linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel().getHref();
                            // entityModel1.add(linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel());
                            resp.setPath("/pages/user/order/list?url="+link);
                        }
                        else if(ee.equals(EnumLayoutFeatured.退款_售后)){
                            String link = linkTo(methodOn(BookingRestController.class).getReservationListForRefund(user.getId(),null,null)).withSelfRel().getHref();
                            // entityModel1.add(linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel());
                            resp.setPath("/pages/user/order/list?url="+link);
                        }
                        else if(ee.equals(EnumLayoutFeatured.卡包)){
                            String link = linkTo(methodOn(PassRestController.class).getPassListForUser(user.getId(),null,null)).withSelfRel().getHref();
                            // entityModel1.add(linkTo(methodOn(BookingRestController.class).getReservationList(null,null)).withSelfRel());
                            resp.setPath("/pages/user/card/list?url="+link);
                        }

                        else if(ee.equals(EnumLayoutFeatured.主人卡激活)){
                            String link = linkTo(methodOn(PassRestController.class).Page_activePass()).withSelfRel().getHref();
                            resp.setPath("/pages/ownercard/activate?url="+link);
                        }
                        else{
                            String link = linkTo(methodOn(IndexController.class).heroPass(null)).withSelfRel().getHref();
                            //  entityModel1.add(linkTo(methodOn(IndexController.class).cityPass(null)).withSelfRel());
                            resp.setPath("/pages/museum/list?url="+link);
                            //resp.setUrl("/pages/onecard/show?url="+link);
                        }



                        return entityModel1;

                    }).collect(Collectors.toList()));

                }).collect(Collectors.toMap(e->e.getValue0(),e->e.getValue1()));

        LayoutResp layoutResp = new LayoutResp();
        layoutResp.setName("profile");
        layoutResp.setVersion(1);
        layoutResp.setLayout(map);

        return layoutResp;
    }

}
