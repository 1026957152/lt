package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public enum EnumMiniappPagePath {
    home_city_pass("/pages/ownercard/show"),
    user_support("/pages/user/about"),
    address_list("/pages/user/address/list"),
    address_add("/pages/user/address/post"),
    employee_list("/pages/admin/staff/list"),
    audio_guide("/pages/citywalk/index"),

    city_walk("/pages/citywalk/show"),
    user_allowed_component_right("/pages/admin/writeoff/auth"),

    nearby_car_rent("/pages/rentcars/list2"),



    ;

    public static List<EnumResp> from() {
        return Arrays.asList(EnumMiniappPagePath.values()).stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());

            return enumResp;
        }).collect(Collectors.toList());
    }

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String path;

    @Autowired
    private MessageSource messageSource;

    EnumMiniappPagePath(String name) {

        this.path = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    /*
    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.pass.type."
                + this.name());
        return displayStatusString;
    }*/

}
