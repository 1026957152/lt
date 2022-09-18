package com.lt.dom.vo;

import com.lt.dom.OctResp.RoleResp;
import com.lt.dom.oct.Openid;
import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.User;
import com.lt.dom.otcenum.EnumBussinessType;
import com.lt.dom.otcenum.EnumSupplierType;
import org.javatuples.Pair;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;


public class UserMachentSettedValidatVo  {

    @NotNull(message = "用户姓名不能为空")
    private String user_name;
    @NotNull(message = "密码不能为空")
    private String user_password;
    @NotNull(message = "手机号不能为空")
    private String user_phone;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }
}
