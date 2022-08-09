package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Openid;
import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.User;
import com.lt.dom.otcenum.EnumBussinessType;
import com.lt.dom.otcenum.EnumSupplierType;
import net.minidev.json.annotate.JsonIgnore;
import org.javatuples.Pair;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.stream.Collectors;


//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRespFull extends RepresentationModel<UserRespFull> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  boolean hired;
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private String first_name;//	string	The user’s first name
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private String last_name;//	string	The user’s last name
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private String username;//	string	The user’s username*
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private String email;//	string	The user’s email address

    private String mobile;//	string	The user’s phone number
    private String user_code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private EnumSupplierType supplier_type;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String supplier_name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String supplier_desc;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private EnumBussinessType supplier_bussiness_type;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String supplier_code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private EntityModel supplier;
    private String real_name;

    private boolean bind;

    private String id_card;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;
    private String nick_name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private EntityModel<AssetResp> asset;
    private Integer gender;
    private String avatar;
    private boolean real_name_verified;

/*    nick_name 昵称：
    gender 性别：
    avatar 头像:
    link 是否绑定：

    mobile: 用户手机号
    user_code: 用户编号
    roles: 用户权限

    real_name_verified:  是否实名
    real_name: 身份证姓名
    id_card: 身份证号码号码*/


    public static UserRespFull from(User user, Supplier supplier) {
        UserRespFull userResp = new UserRespFull();
        userResp.setMobile(user.getPhone());
        userResp.setFirst_name(user.getFirst_name());
        userResp.setLast_name(user.getLast_name());
        userResp.setUser_code(user.getCode());
        userResp.setSupplier_name(supplier.getCode());
        userResp.setSupplier_desc(supplier.getDesc());
        userResp.setSupplier_bussiness_type(supplier.getBusiness_type());
        userResp.setSupplier_type(supplier.getType());
        userResp.setSupplier_code(supplier.getCode());
        userResp.setRoles(user.getRoles().stream().map(x->{
            return RoleResp.from(x);
        }).collect(Collectors.toList()));
        return userResp;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public static UserRespFull from(User user) {
        UserRespFull userResp = new UserRespFull();




        //userResp.setFirst_name(user.getFirst_name());
        //userResp.setLast_name(user.getLast_name());
        userResp.setReal_name_verified(user.isRealNameVerified());
        if(user.isRealNameVerified()){
            userResp.setReal_name(user.getRealName());
            userResp.setId_card(user.getIdCard());
        }


        userResp.setMobile(user.getPhone());
        userResp.setUser_code(user.getCode());
        userResp.setRoles(user.getRoles().stream().map(x->{
            return RoleResp.from(x);
        }).collect(Collectors.toList()));
        return userResp;
    }


/*    public static OpenidResp Realnamefrom(Pair<User, Openid> pair) {
        Openid openid = pair.getValue1();
        User user = pair.getValue0();
        OpenidResp openidResp = new OpenidResp();
        //openidResp.setOpenid(openid1.getOpenid());
        //openidResp.setCode(openid.getCode());
        openidResp.setName(openid.getOpenid_name());
        //openidResp.setGender(openid.getOpenid_gender());
        //openidResp.setImage(openid.getOpenid_image());
        openidResp.setLink(openid.getLink());
        openidResp.setReal_name(user.isRealNameVerified());//nonNull(openid.getUserId())
        openidResp.setFull_name(user.getRealName());
        openidResp.setId_card(user.getIdCard());

        return openidResp;

    }*/
    public static UserRespFull from(Openid openid) {



        UserRespFull openidResp = new UserRespFull();
        //openidResp.setOpenid(openid1.getOpenid());
        //openidResp.setCode(openid.getCode());
        openidResp.setNick_name(openid.getOpenid_name());
        openidResp.setGender(openid.getOpenid_gender());
        openidResp.setAvatar(openid.getOpenid_image());
        openidResp.setBind(openid.getLink());


        /*

        openidResp.setFirst_name(user.getFirst_name());
        openidResp.setLast_name(user.getLast_name());
        openidResp.setRealNameVerified(user.isRealNameVerified());

        if(user.isRealNameVerified()){
            openidResp.setFull_name(user.getRealName());
            openidResp.setId_card(user.getIdCard());
        }

        openidResp.setPhone(user.getPhone());
        openidResp.setCode(user.getCode());
        openidResp.setRoles(user.getRoles().stream().map(x->{
            return RoleResp.from(x);
        }).collect(Collectors.toList()));*/
        return openidResp;

    }
    public static UserRespFull userWithOpenidLink(Pair<User, Openid> pair) {
        Openid openid = pair.getValue1();
        User user = pair.getValue0();
        UserRespFull openidResp = new UserRespFull();

        openidResp.setNick_name(openid.getOpenid_name());
        openidResp.setGender(openid.getOpenid_gender());
        openidResp.setAvatar(openid.getOpenid_image());
        openidResp.setBind(openid.getLink());




      //  openidResp.setFirst_name(user.getFirst_name());
      //  openidResp.setLast_name(user.getLast_name());
        openidResp.setReal_name_verified(user.isRealNameVerified());

        if(user.isRealNameVerified()){
            openidResp.setReal_name(user.getRealName());
            openidResp.setId_card(user.getIdCard());
        }

        openidResp.setMobile(user.getPhone());
        openidResp.setUser_code(user.getCode());
        openidResp.setRoles(user.getRoles().stream().map(x->{
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


    private List<RoleResp> roles;

    public List<RoleResp> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleResp> roles) {
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

    public void setSupplier(EntityModel supplier) {
        this.supplier = supplier;
    }

    public EntityModel getSupplier() {
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



    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setNick_name(String nickName) {
        this.nick_name = nickName;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setAsset(EntityModel<AssetResp> asset) {

        this.asset = asset;
    }

    public EntityModel<AssetResp> getAsset() {
        return asset;
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
}
