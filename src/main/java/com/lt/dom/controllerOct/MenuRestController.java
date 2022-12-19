package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.MenuResp;
import com.lt.dom.otcenum.EnumMenu;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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





/*

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

*/












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

        menuResp_Suppliers.getChildren().add(new MenuResp("/agent/index","IndexAgent",
                new MenuResp.Meta( "我的代理商", "el-icon-s-finance",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT"))

        ));
        menuResp_Suppliers.getChildren().add(new MenuResp("/merchant/supplier/index","IndexSupplier",
                new MenuResp.Meta( "我的供应商", "el-icon-s-finance",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT"))

        ));
        menuResp_Suppliers.getChildren().add(new MenuResp("/agent/detail",true,"DetailAgent",
                new MenuResp.Meta( "未实现_代理商详情", "el-icon-s-check",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT"))
        ));
        menuResp_Suppliers.getChildren().add(new MenuResp("/merchant/supplier/detail",true,"DetailSupplier",
                new MenuResp.Meta( "未实现_供应商详情", "el-icon-s-finance",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT"))

        ));

/*
        menuResp_Suppliers.getChildren().add(new MenuResp("/suppliers/employees/index","IndexEmployees",
                new MenuResp.Meta( "职工管理","el-icon-s-custom",Arrays.asList("ROLE_ADMIN","ROLE_MERCHANT","ROLE_TRAVEL_AGENCY"))
        ));
*/

/*        menuResp_Suppliers.getChildren().add(new MenuResp("/suppliers/guide/index",true,"IndexGuide",
                new MenuResp.Meta( "导游列表","el-icon-service",Arrays.asList("ROLE_ADMIN","ROLE_MERCHANT","ROLE_TRAVEL_AGENCY"))
        ));*/
        menuResp_Suppliers.getChildren().add(new MenuResp("/suppliers/balance/index","IndexBalance",
                new MenuResp.Meta( "余额明细","el-icon-s-finance",Arrays.asList("ROLE_ADMIN"))
        ));
        menuResp_Suppliers.getChildren().add(new MenuResp("/suppliers/charge/index","IndexCharge",
                new MenuResp.Meta( "付款记录","'el-icon-s-finance",Arrays.asList("ROLE_ADMIN"))
        ));
        menuResp_Suppliers.getChildren().add(new MenuResp("/suppliers/refund/index","IndexRefund",
                new MenuResp.Meta( "退款记录","'el-icon-s-finance",Arrays.asList("ROLE_ADMIN"))
        ));


        menuRespList.addAll(Arrays.asList(menuResp_Suppliers));




        MenuResp menuResp_employees =new MenuResp("/suppliers/employees","/suppliers/employees/index",
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



/*
        MenuResp menuResp_balance =new MenuResp("/suppliers/balance",true,"/suppliers/guide/index",
                "Balance'",
                new MenuResp.Meta( "余额管理", "el-icon-s-finance",Arrays.asList("ROLE_ADMIN"))
        );

        menuResp_balance.getChildren().add(new MenuResp("/suppliers/balance/index",true,"IndexBalance",
                new MenuResp.Meta( "余额明细", "el-icon-s-finance",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT"))
        ));

        menuRespList.addAll(Arrays.asList(menuResp_balance));
*/






/*

        MenuResp menuResp_Charge =new MenuResp("/suppliers/charge",true,"/suppliers/charge/index",
                "Charge",
                new MenuResp.Meta( "付款记录", "el-icon-s-finance",Arrays.asList("ROLE_ADMIN"))
        );

        menuResp_Charge.getChildren().add(new MenuResp("/suppliers/charge/index",true,"IndexCharge",
                new MenuResp.Meta( "付款记录", "el-icon-s-finance",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT"))
        ));

        menuRespList.addAll(Arrays.asList(menuResp_Charge));
*/




/*        MenuResp menuResp_refund =new MenuResp("/suppliers/refund",true,"/suppliers/refund/index",
                "Refund",
                new MenuResp.Meta( "退款记录", "el-icon-s-finance",Arrays.asList("ROLE_ADMIN"))
        );

        menuResp_refund.getChildren().add(new MenuResp("/suppliers/refund/index",true,"IndexRefund",
                new MenuResp.Meta( "退款记录", "el-icon-s-finance",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT"))
        ));

        menuRespList.addAll(Arrays.asList(menuResp_refund));*/





        MenuResp menuResp_approval =new MenuResp("/approval","/approval/index",
                "Approval",
                new MenuResp.Meta( "审批管理", "el-icon-s-check",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT")
                )
        );

        menuResp_approval.getChildren().add(new MenuResp("index","IndexApproval",
                new MenuResp.Meta( "审批列表", "el-icon-s-finance",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT"))

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

        menuResp_products.getChildren().add(new MenuResp("/products/index","IndexProducts",
                new MenuResp.Meta( "产品列表", "el-icon-menu",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));
        menuResp_products.getChildren().add(new MenuResp("/products/post",true,"PostProducts",
                new MenuResp.Meta( "发布产品", "el-icon-s-check",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"),true),true
        ));

        menuResp_products.getChildren().add(new MenuResp("/componentright/index","IndexComponentright",
                new MenuResp.Meta( "权益列表", "el-icon-thumb",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));
        menuResp_products.getChildren().add(new MenuResp("/attractions/post","IndexAttractions",
                new MenuResp.Meta( "景点列表", "el-icon-guide",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));
        menuResp_products.getChildren().add(new MenuResp("/attractions/create",true,"CreateAttractions",
                new MenuResp.Meta( "新建景点", true)
        ));

        menuResp_products.getChildren().add(new MenuResp("/attractions/edit",true,"EditAttractions",
                new MenuResp.Meta( "编辑景点", true)
        ));

        menuResp_products.getChildren().add(new MenuResp("/theater/create","IndexTheater",
                new MenuResp.Meta( "剧院列表", "el-icon-video-camera",true)
        ));

        menuResp_products.getChildren().add(new MenuResp("/theater/post",true,"PostTheater",
                new MenuResp.Meta( "发布剧院", true)
        ));


        menuResp_products.getChildren().add(new MenuResp("/movies/index","IndexMovies",
                new MenuResp.Meta( "剧目列表", "el-icon-video-camera-solid")
        ));

        menuResp_products.getChildren().add(new MenuResp("/movies/post",true,"PostMovies",
                new MenuResp.Meta( "发布剧目", true)
        ));

        menuResp_products.getChildren().add(new MenuResp("/citywalk/index","IndexCitywalk",
                new MenuResp.Meta( "自助旅行", "el-icon-menu",Arrays.asList("ROLE_ADMIN"))
        ));

        menuResp_products.getChildren().add(new MenuResp("/citywalk/edit",true,"EditCitywalk",
                new MenuResp.Meta( "详情","el-icon-menu", Arrays.asList("ROLE_ADMIN")),true
        ));


        menuResp_products.getChildren().add(new MenuResp("/campaigns/index","IndexCampaigns",
                new MenuResp.Meta( "优惠券管理", "el-icon-s-ticket",Arrays.asList("ROLE_ADMIN"))
        ));

        menuResp_products.getChildren().add(new MenuResp("/campaigns/detail",true,"detailCampaigns",
                new MenuResp.Meta( "活动详情","el-icon-menu", Arrays.asList("ROLE_ADMIN"),true),true
        ));



 /*       menuResp_products.getChildren().add(new MenuResp("/campaigns/index",true,"IndexCampaign",
                new MenuResp.Meta( "优惠券管理", "el-icon-s-ticket",Arrays.asList("ROLE_ADMIN"))
        ));*/

        menuResp_products.getChildren().add(new MenuResp("/campaigns/create",true,"CreateCampaign",
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



















        MenuResp menuResp_order =new MenuResp("/order",
                "Order",
                new MenuResp.Meta( "订单管理", "el-icon-s-order",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT")
                )
        );
        menuResp_order.getChildren().add(new MenuResp("/bookingtour/index","IndexBookingtour",
                new MenuResp.Meta( "团订单列表","el-icon-s-order", Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT","ROLE_TRAVEL_AGENCY","ROLE_MERCHANT"))
        ));



        menuResp_order.getChildren().add(new MenuResp("/bookingtour/detail",true,"DetailBookingtour",
                new MenuResp.Meta( "团订单详情", Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY","ROLE_MERCHANT"),true)
        ));


        menuResp_order.getChildren().add(new MenuResp("/booking/index","IndexBooking",
                new MenuResp.Meta( "订单列表", "el-icon-s-order",Arrays.asList("ROLE_ADMIN"))
        ));





        menuResp_order.getChildren().add(new MenuResp("/booking/detail",true,"DetailBooking",
                new MenuResp.Meta( "订单详情", Arrays.asList("ROLE_ADMIN"),true)
        ));
        menuResp_order.getChildren().add(new MenuResp("/order/redemption","IndexRedemption",
                new MenuResp.Meta( "核销记录","el-icon-document", Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));



        String link_voucher = linkTo(methodOn(ComponentVoucherRestController.class).listVoucher(null,3l,null,null)).withSelfRel().getHref();

        menuResp_order.getChildren().add(new MenuResp("/order/componentvoucher/index","IndexComponentVoucher",
                new MenuResp.Meta( "权益券记录","el-icon-document", Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
                ,link_voucher));

        menuResp_order.getChildren().add(new MenuResp("/order/componentvoucher/detail","DetailComponentVoucher",
                new MenuResp.Meta( "权益券详情","el-icon-document", Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
                ,link_voucher));


        menuResp_order.getChildren().add(new MenuResp("/order/create",true,"CreateOrder",
                new MenuResp.Meta("下单领券", Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));

        menuResp_order.getChildren().add(new MenuResp("/voucher/index","IndexVoucher",
                new MenuResp.Meta( "券列表", "el-icon-s-ticket",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY","ROLE_MERCHANT"))
        ));

        menuResp_order.getChildren().add(new MenuResp("/voucher/detail",true,"DetailVoucher",
                new MenuResp.Meta( "券详情", "el-icon-s-order",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY","ROLE_MERCHANT")),
                true
        ));
        menuRespList.addAll(Arrays.asList(menuResp_order));




        MenuResp menuResp_bookingservice =new MenuResp("/bookingservice","/bookingservice/index",
                "Bookingservice",
                new MenuResp.Meta( "预约服务管理", "el-icon-film",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT")
                )       );

        menuResp_bookingservice.getChildren().add(new MenuResp("/bookingservice/index","IndexBookingservice",
                new MenuResp.Meta( "服务列表", "el-icon-coordinate",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));
        menuResp_bookingservice.getChildren().add(new MenuResp("/bookingservice/edit",true,"EditBookingservice",
                new MenuResp.Meta( "编辑预约服务", "el-icon-coordinate",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));
        menuResp_bookingservice.getChildren().add(new MenuResp("/bookingresource/index","IndexBookingresource",
                new MenuResp.Meta( "资源列表", "el-icon-film",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));
        menuRespList.addAll(Arrays.asList(menuResp_bookingservice));




        MenuResp menuResp_bulkissuances =new MenuResp("/bulkissuances","/bulkissuances",
                "Bulkissuances",
                new MenuResp.Meta( "城市主人卡管理", "el-icon-files",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT")
                )
        );

        menuResp_bulkissuances.getChildren().add(new MenuResp("/passes/index","IndexPasses",
                new MenuResp.Meta( "卡列表", "el-icon-copy-document",Arrays.asList("ROLE_ADMIN"))
        ));

        menuResp_bulkissuances.getChildren().add(new MenuResp("/passes/detail",true,"DetailPasses",
                new MenuResp.Meta( "卡详情", "el-icon-copy-document",Arrays.asList("ROLE_ADMIN"),true)
        ));



        menuResp_bulkissuances.getChildren().add(new MenuResp("/bulkissuances/index","IndexBulkissuances",
                new MenuResp.Meta( "批量卡列表","el-icon-files", Arrays.asList("ROLE_ADMIN"),true)
        ));
        menuResp_bulkissuances.getChildren().add(new MenuResp("/bulkissuances/detail",true,"DetailBulkissuances",
                new MenuResp.Meta( "批量卡详情", Arrays.asList("ROLE_ADMIN"),true)
        ));
        menuRespList.addAll(Arrays.asList(menuResp_bulkissuances));







        MenuResp menuResp_bus =new MenuResp("/transfer","/bus",
                "Transfer",
                new MenuResp.Meta( "驼城小易", "el-icon-files",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT")
                )
        );

        menuResp_bus.getChildren().add(new MenuResp("/transfer/line/index","IndexLine",
                new MenuResp.Meta( "路线管理", "el-icon-copy-document",Arrays.asList("ROLE_ADMIN"))
        ));

        menuResp_bus.getChildren().add(new MenuResp("/transfer/line/post",true,"DetaiLine",
                new MenuResp.Meta( "路线详情", "el-icon-copy-document",Arrays.asList("ROLE_ADMIN"),true)
        ));



        menuResp_bus.getChildren().add(new MenuResp("/transfer/bus/index","IndexBus",
                new MenuResp.Meta( "车辆列表","el-icon-files", Arrays.asList("ROLE_ADMIN"),true)
        ));
        menuResp_bus.getChildren().add(new MenuResp("/transfer/bus/post",true,"DetailBus",
                new MenuResp.Meta( "车辆详情", Arrays.asList("ROLE_ADMIN"),true)
        ));
        menuRespList.addAll(Arrays.asList(menuResp_bus));









        MenuResp menuResp_invoice =new MenuResp("/invoice","/Invoice",
                "Invoice",
                new MenuResp.Meta( "票据管理", "el-icon-menu",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT")
                )
        );

        menuResp_invoice.getChildren().add(new MenuResp("/invoice/index","IndexInvoice",
                new MenuResp.Meta( "应收账款", "el-icon-menu",Arrays.asList("ROLE_ADMIN"))
        ));
        menuResp_invoice.getChildren().add(new MenuResp("/invoice/detail",true,"DetailInvoice",
                new MenuResp.Meta( "发票详情","el-icon-menu", Arrays.asList("ROLE_ADMIN"),true)
        ));
        menuRespList.addAll(Arrays.asList(menuResp_invoice));



        MenuResp menuResp_subscribe =new MenuResp("/subscribe","/Invoice",
                "Subscribe",
                new MenuResp.Meta( "订阅管理", "el-icon-menu",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT")
                )
        );

        menuResp_subscribe.getChildren().add(new MenuResp("/subscribe/index","IndexSubscribe",
                new MenuResp.Meta( "订阅列表", "el-icon-menu",Arrays.asList("ROLE_ADMIN"))
        ));
        menuResp_subscribe.getChildren().add(new MenuResp("/subscribe/detail",true,"DetailSubscribe",
                new MenuResp.Meta( "订阅详情","el-icon-menu", Arrays.asList("ROLE_ADMIN"),true)
        ));
        menuRespList.addAll(Arrays.asList(menuResp_subscribe));










        MenuResp menuResp_market =new MenuResp("/market",
                "Market",
                new MenuResp.Meta( "市场管理", "el-icon-s-flag",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT")
                )
        );

        menuResp_market.getChildren().add(new MenuResp("/invitations/index","IndexInvitations",
                new MenuResp.Meta( "邀请列表", "el-icon-thumb",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));
        menuResp_market.getChildren().add(new MenuResp("/market/product/index","MarketIndexProduct",
                new MenuResp.Meta( "产品列表","el-icon-menu", Arrays.asList("ROLE_ADMIN"),true)
        ));

        menuResp_market.getChildren().add(new MenuResp("/market/product/detail",true,"MarketDetailProduct",
                new MenuResp.Meta( "产品详情", "el-icon-s-order",Arrays.asList("ROLE_ADMIN"),true)
        ));
        menuResp_market.getChildren().add(new MenuResp("/partners/index","IndexPartners",
                new MenuResp.Meta( "合作方列表","el-icon-s-flag", Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));

        menuResp_market.getChildren().add(new MenuResp("/partners/detail",true,"DetailPartners",
                new MenuResp.Meta( "合作方详情", "el-icon-s-order",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY","ROLE_MERCHANT"),true)
        ));

        menuResp_market.getChildren().add(new MenuResp("/market/agent/index","IndexMarketAgent",
                new MenuResp.Meta( "未实现_代理商列表","el-icon-s-flag", Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));
        menuResp_market.getChildren().add(new MenuResp("/market/agent/detail",true,"DetailMarketAgent",
                new MenuResp.Meta( "未实现_代理商编辑", Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"),true)
        ));
        menuRespList.addAll(Arrays.asList(menuResp_market));






        MenuResp menuResp_user =new MenuResp("/user",
                "User",
                new MenuResp.Meta( "用户管理", "el-icon-user-solid",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT")
                )
        );

        menuResp_user.getChildren().add(new MenuResp("/user/index","IndexUser",
                new MenuResp.Meta( "用户列表", "el-icon-user-solid",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));
        menuResp_user.getChildren().add(new MenuResp("/user/detail",true,"DetailUser",
                new MenuResp.Meta( "用户详情","el-icon-user-solid", Arrays.asList("ROLE_ADMIN"),true)
        ));
        menuRespList.addAll(Arrays.asList(menuResp_user));























        MenuResp menuResp_blog =new MenuResp("/blog",
                "Blog",
                new MenuResp.Meta( "社区与文案", "el-icon-s-opportunity",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT")
                )
        );

        menuResp_blog.getChildren().add(new MenuResp("/blog/index","IndexBlog",
                new MenuResp.Meta( "文案列表", "el-icon-s-order",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));
        menuResp_blog.getChildren().add(new MenuResp("/blog/post",true,"PostBlog",
                new MenuResp.Meta( "文案详情", Arrays.asList("ROLE_ADMIN"),true)
        ));


        menuResp_blog.getChildren().add(new MenuResp("/community/index","IndexCommunity",
                new MenuResp.Meta( "社区列表", "el-icon-s-opportunity",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));
        menuResp_blog.getChildren().add(new MenuResp("/community/post",true,"PostCommunity",
                new MenuResp.Meta( "PostCommunity", Arrays.asList("ROLE_ADMIN"),true)
        ));
        menuRespList.addAll(Arrays.asList(menuResp_blog));













        MenuResp menuResp_thirdplatform =new MenuResp("/thirdplatform",
                "Thirdplatform",
                new MenuResp.Meta( "第三方平台", "el-icon-user-solid",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT")
                )
        );

        menuResp_thirdplatform.getChildren().add(new MenuResp("/thirdplatform/index","IndexThirdplatform",
                new MenuResp.Meta( "第三方平台", "el-icon-s-order",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));
        menuResp_thirdplatform.getChildren().add(new MenuResp("/thirdplatform/product/index","IndexThirdplatformProduct",
                new MenuResp.Meta( "产品列表","el-icon-s-order", Arrays.asList("ROLE_ADMIN"))
        ));
        menuRespList.addAll(Arrays.asList(menuResp_thirdplatform));







        MenuResp menuResp_export =new MenuResp("/export","/export/index" ,"Export",
                new MenuResp.Meta( "导出", "el-icon-upload",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT")));

            menuResp_export.getChildren().add(new MenuResp("/export/index","IndexExport",
                new MenuResp.Meta( "导出", "el-icon-upload",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT","ROLE_MERCHANT","ROLE_TRAVEL_AGENCY"))
        ));


        menuRespList.addAll(Arrays.asList(menuResp_export));






        MenuResp menuResp_statistics =new MenuResp("/statistics",
                "Statistics",
                new MenuResp.Meta( "统计数据", "el-icon-s-data",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT")
                )
        );

        menuResp_statistics.getChildren().add(new MenuResp("/statistics/index","IndexStatistics",
                new MenuResp.Meta( "统计数据", "el-icon-s-data",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));


        String link = linkTo(methodOn(ReportRestController.class).Booking_summary(null)).withRel("create").getHref();

        menuResp_statistics.getChildren().add(new MenuResp("/statistics/booking-summary","Booking_summary",
                new MenuResp.Meta( "订单概要", "el-icon-s-data",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ,link));
        menuResp_statistics.getChildren().add(new MenuResp("/statistics/channel-summary","Channel_summary",
                new MenuResp.Meta( "渠道概要", "el-icon-s-data",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
                ,link));


        String link_Page_arrivals = linkTo(methodOn(ReportRestController.class).Page_arrivals()).withRel("Page_arrivals").getHref();


        menuResp_statistics.getChildren().add(new MenuResp("/statistics/arrival_summary","Arrival_summary",
                new MenuResp.Meta( "未实现_权益核销概要", "el-icon-s-data",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
                ,link_Page_arrivals));


        menuRespList.addAll(Arrays.asList(menuResp_statistics));






























        MenuResp menuResp_device =new MenuResp("/device","/device/index",
                "Device",
                new MenuResp.Meta( "设备管理", "el-icon-film",Arrays.asList("ROLE_ADMIN"))
        );

        menuResp_device.getChildren().add(new MenuResp("/device/index","IndexDevice",
                new MenuResp.Meta( "设备列表", "el-icon-film")
        ));
        menuResp_device.getChildren().add(new MenuResp("/device/detail",true,"DetailDevice",
                new MenuResp.Meta( "设备详情", "el-icon-film")
        ));
        menuRespList.addAll(Arrays.asList(menuResp_device));









        MenuResp menuResp_settings =new MenuResp("/settings",
                "Xtsettings",
                new MenuResp.Meta( "系统设置", "el-icon-setting",Arrays.asList("ROLE_ADMIN","ROLE_GOVERNMENT")
                )
        );



        menuResp_settings.getChildren().add(new MenuResp("/roles/index","IndexBroles",
                new MenuResp.Meta( "角色列表", "el-icon-user-solid",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));



        menuResp_settings.getChildren().add(new MenuResp("/roles/edit",true,"EditBroles",
                new MenuResp.Meta( "编辑角色", Arrays.asList("ROLE_ADMIN"),true)
        ));






        menuResp_settings.getChildren().add(new MenuResp("/settings/index","IndexXtsettings",
                new MenuResp.Meta( "系统设置", "el-icon-setting",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));




        menuResp_settings.getChildren().add(new MenuResp("/valuelist/index","IndexValuelist",
                new MenuResp.Meta( "组列表", "el-icon-film",Arrays.asList("ROLE_ADMIN","ROLE_TRAVEL_AGENCY"))
        ));



        menuResp_settings.getChildren().add(new MenuResp("/valuelist/edit",true,"EditValuelist",
                new MenuResp.Meta( "编辑组", Arrays.asList("ROLE_ADMIN"),true)
        ));



        menuResp_settings.getChildren().add(new MenuResp("/valuelist/create",true,"CreateValuelist",
                new MenuResp.Meta( "添加组", Arrays.asList("ROLE_ADMIN"),true)
        ));


        menuResp_settings.getChildren().add(new MenuResp("/attachment/index","IndexAttachment",
                new MenuResp.Meta( "附件管理","el-icon-folder-opened", Arrays.asList("ROLE_ADMIN"))
        ));
        menuRespList.addAll(Arrays.asList(menuResp_settings));





        menuRespList = menuRespList.stream().map(e->{

            if(e.getChildren().size()>0){
                e.setComponent("#");
                if(e.getRedirect()== null) {
                    e.setRedirect("noRedirect");
                }
                e.setChildren(e.getChildren().stream().map(chiildren->{
                    chiildren.setComponent(chiildren.getPath());
                    chiildren.setChildren(null);
                    return chiildren;
                }).collect(Collectors.toList()));

            }


            return e;
        }).collect(Collectors.toList());



        return menuRespList;//Arrays.asList(menuResp,menuResp_settings,menuResp_roles,menuResp_campaigns);

    }
}