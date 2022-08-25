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



    @GetMapping(value = "menus", produces = "application/json")
    public List<MenuResp> listAvailability() {

        List<MenuResp> menuRespList = new ArrayList<>();
        MenuResp menuResp = MenuResp.from(EnumMenu.root);
        boolean isLeaf = true;

        MenuResp menuResp_rout_index = MenuResp.from(EnumMenu.root_index,isLeaf);
        menuResp.getChildren().add(menuResp_rout_index);

        menuRespList.add(menuResp);


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

}