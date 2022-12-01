package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.MenuResp;
import com.lt.dom.oct.Menu;
import com.lt.dom.oct.Product;
import com.lt.dom.otcenum.EnumMenu;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/oct")
public class MenuRestController {



    @GetMapping(value = "/menus", produces = "application/json")
    public List<MenuResp> listAvailability() {

        List<MenuResp> menuRespList = new ArrayList<>();
        MenuResp menuResp = MenuResp.from(EnumMenu.root);
        boolean isLeaf = true;

        MenuResp menuResp_rout_index = MenuResp.from(EnumMenu.root_index,isLeaf);
        menuResp.getChildren().add(menuResp_rout_index);

        menuRespList.add(menuResp);






        //todo


        MenuResp menuResp_device =new MenuResp("Device","/device/index",
                "device",
                new MenuResp.Meta( "设备管理", "el-icon-film",Arrays.asList("ROLE_ADMIN"))
        );

        menuResp_device.getChildren().add(new MenuResp("/device/index","IndexDevice",
                new MenuResp.Meta( "设备列表", "el-icon-film")
        ));
        menuResp_device.getChildren().add(new MenuResp("/device/detail","DetailDevice",
                new MenuResp.Meta( "设备详情", "el-icon-film")
        ));
        menuRespList.addAll(Arrays.asList(menuResp_device));













        //todo
        MenuResp menuResp_settings = MenuResp.from(EnumMenu.settings);
        MenuResp menuResp_settings_index = MenuResp.from(EnumMenu.settings_index,isLeaf);
        menuResp_settings.getChildren().add(menuResp_settings_index);
        menuRespList.addAll(Arrays.asList(menuResp_settings));


        //todo
        MenuResp profile_index = MenuResp.from(EnumMenu.profile_index);
        menuRespList.addAll(Arrays.asList(profile_index));



        MenuResp menuResp_roles = MenuResp.from(EnumMenu.roles);
        MenuResp menuResp_roles_index = MenuResp.from(EnumMenu.roles_index,isLeaf);
        MenuResp menuResp_roles_edit = MenuResp.from(EnumMenu.roles_edit,isLeaf);
        menuResp_roles.getChildren().add(menuResp_roles_index);
        menuResp_roles.getChildren().add(menuResp_roles_edit);
        menuRespList.addAll(Arrays.asList(menuResp_roles));



        MenuResp menuResp_campaigns = MenuResp.from(EnumMenu.campaigns);
        MenuResp menuResp_campaigns_index = MenuResp.from(EnumMenu.campaigns_index,isLeaf);
        MenuResp menuResp_campaigns_edit = MenuResp.from(EnumMenu.campaigns_edit,isLeaf);
        MenuResp menuResp_campaigns_create = MenuResp.from(EnumMenu.campaigns_create,isLeaf);
        menuResp_campaigns.getChildren().add(menuResp_campaigns_index);
        menuResp_campaigns.getChildren().add(menuResp_campaigns_edit);
        menuResp_campaigns.getChildren().add(menuResp_campaigns_create);
        menuRespList.addAll(Arrays.asList(menuResp_campaigns));



        MenuResp menuResp_product = MenuResp.from(EnumMenu.product);
        MenuResp menuResp_product_index = MenuResp.from(EnumMenu.product_index,isLeaf);
        MenuResp menuResp_product_edit = MenuResp.from(EnumMenu.product_edit,isLeaf);
        MenuResp menuResp_product_create = MenuResp.from(EnumMenu.product_create,isLeaf);
        menuResp_product.getChildren().add(menuResp_product_index);
        menuResp_product.getChildren().add(menuResp_product_edit);
        menuResp_product.getChildren().add(menuResp_product_create);
        menuRespList.addAll(Arrays.asList(menuResp_product));



        MenuResp menuResp_balance = MenuResp.from(EnumMenu.balance);
        MenuResp menuResp_balance_refund_index = MenuResp.from(EnumMenu.balance_refund_index,isLeaf);
        MenuResp menuResp_balance_charge_index = MenuResp.from(EnumMenu.balance_charge_index,isLeaf);
        MenuResp menuResp_balance_idex = MenuResp.from(EnumMenu.balance_index,isLeaf);
        menuResp_balance.getChildren().add(menuResp_balance_refund_index);
        menuResp_balance.getChildren().add(menuResp_balance_charge_index);
        menuResp_balance.getChildren().add(menuResp_balance_idex);
        menuRespList.addAll(Arrays.asList(menuResp_balance));


        MenuResp menuResp_redemption = MenuResp.from(EnumMenu.redemption);
        MenuResp menuResp_redemption_index = MenuResp.from(EnumMenu.redemption_index,isLeaf);
        menuResp_redemption.getChildren().add(menuResp_redemption_index);
        menuRespList.addAll(Arrays.asList(menuResp_redemption));


        MenuResp menuResp_tour_booking = MenuResp.from(EnumMenu.tour_booking);
        MenuResp menuResp_tour_booking_index = MenuResp.from(EnumMenu.tour_booking_index,isLeaf);
        MenuResp menuResp_tour_booking_detail = MenuResp.from(EnumMenu.tour_booking_detail,isLeaf);
        menuResp_tour_booking.getChildren().add(menuResp_tour_booking_index);
        menuResp_tour_booking.getChildren().add(menuResp_tour_booking_detail);
        menuRespList.addAll(Arrays.asList(menuResp_tour_booking));




        return menuRespList;//Arrays.asList(menuResp,menuResp_settings,menuResp_roles,menuResp_campaigns);

    }






    @GetMapping(value = "/menus/new", produces = "application/json")
    public List<MenuResp> listnew() {

        List<MenuResp> menuRespList = new ArrayList<>();







        //todo


        MenuResp menuResp_device =new MenuResp("Device","/device/index",
                "device",
                new MenuResp.Meta( "设备管理", "el-icon-film",Arrays.asList("ROLE_ADMIN"))
        );

        menuResp_device.getChildren().add(new MenuResp("/device/index","IndexDevice",
                new MenuResp.Meta( "设备列表", "el-icon-film")
        ));
        menuResp_device.getChildren().add(new MenuResp("/device/detail","DetailDevice",
                new MenuResp.Meta( "设备详情", "el-icon-film")
        ));
        menuRespList.addAll(Arrays.asList(menuResp_device));










        MenuResp menuResp_Suppliers =new MenuResp("/suppliers","/suppliers/index",
                "Suppliers",
                new MenuResp.Meta( "商户管理", "el-icon-office-building",Arrays.asList("ROLE_ADMIN"))
        );

        menuResp_Suppliers.getChildren().add(new MenuResp("/suppliers/index","IndexSuppliers",
                new MenuResp.Meta( "商户列表", "el-icon-office-building",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT"))
        ));
        menuResp_Suppliers.getChildren().add(new MenuResp("/suppliers/post",true,"PostSuppliers",
                new MenuResp.Meta( "商户发布")
        ));

        menuResp_Suppliers.getChildren().add(new MenuResp("/suppliers/employees/index",true,"IndexEmployees",
                new MenuResp.Meta( "职工管理","el-icon-s-custom",Arrays.asList("ROLE_ADMIN","ROLE_MERCHANT","ROLE_TRAVEL_AGENCY"))
        ));

        menuResp_Suppliers.getChildren().add(new MenuResp("/suppliers/guide/index",true,"IndexGuide",
                new MenuResp.Meta( "导游列表","el-icon-service",Arrays.asList("ROLE_ADMIN","ROLE_MERCHANT","ROLE_TRAVEL_AGENCY"))
        ));
        menuResp_Suppliers.getChildren().add(new MenuResp("/suppliers/balance/index",true,"IndexBalance",
                new MenuResp.Meta( "余额明细","el-icon-s-finance",Arrays.asList("ROLE_ADMIN"))
        ));
        menuResp_Suppliers.getChildren().add(new MenuResp("/suppliers/charge/index",true,"IndexCharge",
                new MenuResp.Meta( "付款记录","'el-icon-s-finance",Arrays.asList("ROLE_ADMIN"))
        ));
        menuResp_Suppliers.getChildren().add(new MenuResp("/suppliers/refund/index",true,"IndexRefund",
                new MenuResp.Meta( "退款记录","'el-icon-s-finance",Arrays.asList("ROLE_ADMIN"))
        ));


        menuRespList.addAll(Arrays.asList(menuResp_Suppliers));




        MenuResp menuResp_employees =new MenuResp("/suppliers/employees",true,"/suppliers/employees/index",
                "Employees",
                new MenuResp.Meta( "职工管理", "el-icon-s-custom",Arrays.asList("ROLE_ADMIN","ROLE_MERCHANT", "ROLE_TRAVEL_AGENCY"))
        );

        menuResp_employees.getChildren().add(new MenuResp("/suppliers/employees/index",true,"IndexEmployees",
                new MenuResp.Meta( "职工列表", "el-icon-s-custom",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT"))
        ));

        menuResp_employees.getChildren().add(new MenuResp("/suppliers/employees/create",true,"CreateEmployees",
                new MenuResp.Meta( "添加职工")
        ));
        menuResp_employees.getChildren().add(new MenuResp("/suppliers/employees/edit",true,"EditEmployees",
                new MenuResp.Meta( "编辑职工")
        ));
        menuRespList.addAll(Arrays.asList(menuResp_employees));



        MenuResp menuResp_guide =new MenuResp("/suppliers/guide",true,"/suppliers/guide/index",
                "Guide",
                new MenuResp.Meta( "导游管理", "el-icon-service",Arrays.asList("ROLE_ADMIN","ROLE_MERCHANT", "ROLE_TRAVEL_AGENCY"))
        );

        menuResp_guide.getChildren().add(new MenuResp("/suppliers/guide/index",true,"IndexGuide",
                new MenuResp.Meta( "导游列表", "el-icon-service",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT"))
        ));

        menuRespList.addAll(Arrays.asList(menuResp_guide));



        MenuResp menuResp_balance =new MenuResp("/suppliers/balance",true,"/suppliers/guide/index",
                "Balance'",
                new MenuResp.Meta( "余额管理", "el-icon-s-finance",Arrays.asList("ROLE_ADMIN"))
        );

        menuResp_balance.getChildren().add(new MenuResp("/suppliers/balance/index",true,"IndexBalance",
                new MenuResp.Meta( "余额明细", "el-icon-s-finance",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT"))
        ));

        menuRespList.addAll(Arrays.asList(menuResp_balance));







        MenuResp menuResp_Charge =new MenuResp("/suppliers/charge",true,"/suppliers/charge/index",
                "Charge",
                new MenuResp.Meta( "付款记录", "el-icon-s-finance",Arrays.asList("ROLE_ADMIN"))
        );

        menuResp_Charge.getChildren().add(new MenuResp("/suppliers/charge/index",true,"IndexCharge",
                new MenuResp.Meta( "付款记录", "el-icon-s-finance",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT"))
        ));

        menuRespList.addAll(Arrays.asList(menuResp_Charge));




        MenuResp menuResp_refund =new MenuResp("/suppliers/refund",true,"/suppliers/refund/index",
                "Refund",
                new MenuResp.Meta( "退款记录", "el-icon-s-finance",Arrays.asList("ROLE_ADMIN"))
        );

        menuResp_refund.getChildren().add(new MenuResp("/suppliers/refund/index",true,"IndexRefund",
                new MenuResp.Meta( "退款记录", "el-icon-s-finance",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT"))
        ));

        menuRespList.addAll(Arrays.asList(menuResp_refund));





        MenuResp menuResp_approval =new MenuResp("/approval",true,"/approval/index",
                "Approval",
                new MenuResp.Meta( "审批管理", "el-icon-s-check",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT")
                ),true
        );

        menuResp_approval.getChildren().add(new MenuResp("index",true,"IndexApproval",
                new MenuResp.Meta( "审批列表", "el-icon-s-finance",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT")),
                true
        ));
        menuResp_approval.getChildren().add(new MenuResp("detail",true,"DetailApproval",
                new MenuResp.Meta( "审批详情", "el-icon-s-check",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT"))
        ));
        menuRespList.addAll(Arrays.asList(menuResp_approval));












        MenuResp menuResp_products =new MenuResp("/products","/products/index",
                "products",
                new MenuResp.Meta( "产品管理", "el-icon-menu",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT")
                )
        );

        menuResp_products.getChildren().add(new MenuResp("/products/index",true,"IndexProducts",
                new MenuResp.Meta( "产品列表", "el-icon-menu",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));
        menuResp_products.getChildren().add(new MenuResp("/products/post",true,"PostProducts",
                new MenuResp.Meta( "发布产品", "el-icon-s-check",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY")),true
        ));

        menuResp_products.getChildren().add(new MenuResp("/componentright/index",true,"IndexComponentright",
                new MenuResp.Meta( "权益列表", "el-icon-thumb",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));
        menuResp_products.getChildren().add(new MenuResp("/attractions/post",true,"IndexAttractions",
                new MenuResp.Meta( "景点列表", "el-icon-guide",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY")),true
        ));
        menuResp_products.getChildren().add(new MenuResp("/attractions/create",true,"CreateAttractions",
                new MenuResp.Meta( "新建景点", true)
        ));

        menuResp_products.getChildren().add(new MenuResp("/attractions/edit",true,"EditAttractions",
                new MenuResp.Meta( "编辑景点", true)
        ));

        menuResp_products.getChildren().add(new MenuResp("/theater/create",true,"IndexTheater",
                new MenuResp.Meta( "剧院列表", "el-icon-video-camera",true)
        ));

        menuResp_products.getChildren().add(new MenuResp("/theater/post",true,"PostTheater",
                new MenuResp.Meta( "发布剧院", true)
        ));


        menuResp_products.getChildren().add(new MenuResp("/movies/index",true,"IndexMovies",
                new MenuResp.Meta( "剧目列表", "el-icon-video-camera-solid")
        ));

        menuResp_products.getChildren().add(new MenuResp("/movies/post",true,"PostMovies",
                new MenuResp.Meta( "发布剧目", true)
        ));

        menuResp_products.getChildren().add(new MenuResp("/citywalk/index",true,"IndexCitywalk",
                new MenuResp.Meta( "自助旅行", "el-icon-menu",Arrays.asList("ROLE_ADMIN"))
        ));

        menuResp_products.getChildren().add(new MenuResp("/citywalk/edit",true,"EditCitywalk",
                new MenuResp.Meta( "详情","el-icon-menu", Arrays.asList("ROLE_ADMIN")),true
        ));


        menuResp_products.getChildren().add(new MenuResp("/campaigns/index",true,"IndexCampaigns",
                new MenuResp.Meta( "优惠券管理", "el-icon-s-ticket",Arrays.asList("ROLE_ADMIN"))
        ));

        menuResp_products.getChildren().add(new MenuResp("/campaigns/detail",true,"detailCampaigns",
                new MenuResp.Meta( "活动详情","el-icon-menu", Arrays.asList("ROLE_ADMIN"),true),true
        ));



        menuResp_products.getChildren().add(new MenuResp("/campaigns/create",true,"CreateCampaigns",
                new MenuResp.Meta( "优惠券管理", "el-icon-s-ticket",Arrays.asList("ROLE_ADMIN"))
        ));

        menuResp_products.getChildren().add(new MenuResp("/campaigns/detail",true,"detailCampaigns",
                new MenuResp.Meta( "新建活动","el-icon-menu", Arrays.asList("ROLE_ADMIN"))
        ));

        menuResp_products.getChildren().add(new MenuResp("/places/index","IndexPlaces",
                new MenuResp.Meta( "地点列表", "el-icon-place",Arrays.asList("ROLE_ADMIN"))
        ));

        menuResp_products.getChildren().add(new MenuResp("/places/post",true,"postPlaces",
                new MenuResp.Meta( "地点发布","el-icon-menu", Arrays.asList("ROLE_ADMIN"))
        ));



        menuResp_products.getChildren().add(new MenuResp("/extras/index","IndexExtras",
                new MenuResp.Meta( "附加产品列表", "el-icon-menu",Arrays.asList("ROLE_ADMIN"))
        ));

        menuResp_products.getChildren().add(new MenuResp("/extras/post",true,"postExtras",
                new MenuResp.Meta( "附加产品发布","el-icon-menu", Arrays.asList("ROLE_ADMIN"),true)
        ));
        menuRespList.addAll(Arrays.asList(menuResp_products));















        return menuRespList;//Arrays.asList(menuResp,menuResp_settings,menuResp_roles,menuResp_campaigns);

    }
}