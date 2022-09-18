package com.lt.dom.vo;

import com.lt.dom.OctResp.RoleResp;
import com.lt.dom.oct.Openid;
import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.User;
import com.lt.dom.otcenum.EnumBussinessType;
import com.lt.dom.otcenum.EnumSupplierType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.javatuples.Pair;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.stream.Collectors;


public class UserVo extends RepresentationModel<UserVo> {

    private  boolean hired;

    private String first_name;//	string	The user’s first name

    private String last_name;//	string	The user’s last name
    private String username;//	string	The user’s username*
    private String phone;//	string	The user’s phone number
    private String user_code;
    private EnumSupplierType supplier_type;
    private String supplier_name;
    private String supplier_desc;
    private EnumBussinessType supplier_bussiness_type;
    private String supplier_code;
    private Supplier supplier;
    private String real_name;
    private boolean bind;
    private String id_card;
    private String nick_name;
    private Integer gender;
    private String avatar;
    private boolean real_name_verified;
    private boolean tour_guide;
    private boolean phone_Verifid;
    private Long user_id;


    public  void setHired(boolean hired) {
        this.hired = hired;
    }

    public  boolean getHired() {
        return hired;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static UserVo from(User user) {
        UserVo userResp = new UserVo();

        userResp.setUser_id(user.getId());




        //userResp.setFirst_name(user.getFirst_name());
        //userResp.setLast_name(user.getLast_name());
        userResp.setReal_name_verified(user.isRealNameVerified());
        if(user.isRealNameVerified()){

            userResp.setId_card(user.getIdCard());
        }
        userResp.setReal_name(user.getRealName());
        userResp.setNick_name(user.getNick_name());

        userResp.setPhone_Verifid(user.isPhoneVerifid());
        userResp.setPhone(user.getPhone());
        userResp.setUser_code(user.getCode());
        userResp.setRoles_(user.getRoles().stream().map(x->{
            return RoleResp.simplefrom(x);
        }).collect(Collectors.toList()));

        userResp.setRoles(user.getRoles().stream().map(x->{
            return x.getName();
        }).collect(Collectors.toList()));
        return userResp;
    }


    public static UserVo from(Openid openid) {



        UserVo openidResp = new UserVo();
        //openidResp.setOpenid(openid1.getOpenid());
        //openidResp.setCode(openid.getCode());
        openidResp.setNick_name(openid.getOpenid_name());
        openidResp.setGender(openid.getOpenid_gender());
        openidResp.setAvatar(openid.getOpenid_image());
        openidResp.setBind(openid.getLink());


        return openidResp;

    }
    public static UserVo userWithOpenidLink(Pair<User, Openid> pair) {
        Openid openid = pair.getValue1();
        User user = pair.getValue0();
        UserVo openidResp = new UserVo();
        openidResp.setUser_id(user.getId());


        openidResp.setNick_name(openid.getOpenid_name());
        openidResp.setGender(openid.getOpenid_gender());
        openidResp.setAvatar(openid.getOpenid_image());
        openidResp.setBind(openid.getLink());




      //  openidResp.setFirst_name(user.getFirst_name());
      //  openidResp.setLast_name(user.getLast_name());
        openidResp.setReal_name_verified(user.isRealNameVerified());

        if(user.isRealNameVerified()){
            openidResp.setId_card(user.getIdCard());
        }
        openidResp.setReal_name(user.getRealName());
        openidResp.setPhone_Verifid(user.isPhoneVerifid());
        openidResp.setPhone(user.getPhone());
        openidResp.setUser_code(user.getCode());
        openidResp.setRoles_(user.getRoles().stream().map(x->{
            return RoleResp.from(x);
        }).collect(Collectors.toList()));
        return openidResp;

    }
    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getUser_code() {
        return user_code;
    }

    private List<RoleResp> _roles;
    private List<String> roles;

    public List<RoleResp> getRoles_() {
        return _roles;
    }

    public void setRoles_(List<RoleResp> roles) {
        this._roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setSupplier_type(EnumSupplierType supplier_type) {
        this.supplier_type = supplier_type;
    }

    public EnumSupplierType getSupplier_type() {
        return supplier_type;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_desc(String supplier_desc) {
        this.supplier_desc = supplier_desc;
    }

    public String getSupplier_desc() {
        return supplier_desc;
    }

    public void setSupplier_bussiness_type(EnumBussinessType supplier_bussiness_type) {
        this.supplier_bussiness_type = supplier_bussiness_type;
    }

    public EnumBussinessType getSupplier_bussiness_type() {
        return supplier_bussiness_type;
    }


    public String getSupplier_code() {
        return supplier_code;
    }

    public void setSupplier_code(String supplier_code) {
        this.supplier_code = supplier_code;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getReal_name() {
        return real_name;
    }



    public void setBind(boolean bind) {
        this.bind = bind;
    }

    public boolean getBind() {
        return bind;
    }



    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getId_card() {
        return id_card;
    }




    public void setNick_name(String nickName) {
        this.nick_name = nickName;
    }

    public String getNick_name() {
        return nick_name;
    }


    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getGender() {
        return gender;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setReal_name_verified(boolean real_name_verified) {

        this.real_name_verified = real_name_verified;
    }

    public boolean getReal_name_verified() {
        return real_name_verified;
    }

    public void setTour_guide(boolean tour_guide) {
        this.tour_guide = tour_guide;
    }

    public boolean getTour_guide() {
        return tour_guide;
    }


    public void setPhone_Verifid(boolean phone_verifid) {
        this.phone_Verifid = phone_verifid;
    }

    public boolean getPhone_Verifid() {
        return phone_Verifid;
    }


    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
