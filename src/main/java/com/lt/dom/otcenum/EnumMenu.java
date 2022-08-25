package com.lt.dom.otcenum;

import com.lt.dom.oct.Menu;

import javax.persistence.Transient;
import java.util.Arrays;
import java.util.List;

public enum EnumMenu {
    root("/","Layout","index",null,
            new EnumMenu.Meta("控制台管理","el-icon-user-solid"),
            Arrays.asList()),
    root_index("index","Layout","index",
            new EnumMenu.Meta("控制台","dashboard",true),
            Arrays.asList()),


    profile_index("/profile","Layout","profile",
            new EnumMenu.Meta("个人信息","dashboard"),
            Arrays.asList()),


    settings("/settings","Layout","/settings/index","Settings",
            new EnumMenu.Meta("设置","el-icon-user-solid"),
            Arrays.asList(EnumMenu.root_index)),

    settings_index("index","/settings/index","Settings",
            new EnumMenu.Meta("系统设置","el-icon-setting",false),
            Arrays.asList()),



    redemption("/redemption","Layout","/redemption","Redemption",
            new EnumMenu.Meta("核销管理","el-icon-user-solid"),
            Arrays.asList(EnumMenu.root_index)),

    redemption_index("redemption/index","/redemption/index","Redemption",
            new EnumMenu.Meta("核销列表","el-icon-setting",false),
            Arrays.asList()),




    roles("/roles","Layout","/roles/index","Roles",
            new EnumMenu.Meta("角色管理","el-icon-user-solid"),Arrays.asList()),
    roles_index("index","/roles/index","Roles",
            new EnumMenu.Meta("角色列表","el-icon-user-solid"),Arrays.asList()),
    roles_edit("edit","/roles/edit","EditRoles",
            new EnumMenu.Meta("编辑角色"),Arrays.asList()),







    campaigns("/campaigns","Layout","/campaigns/index","Campaigns",
            new EnumMenu.Meta("优惠券活动管理","el-icon-s-ticket"),Arrays.asList()),

    campaigns_index("index","/campaigns/index","Campaigns",
            new EnumMenu.Meta("优惠券活动列表","el-icon-s-ticket"),Arrays.asList()),
    campaigns_create("create","/campaigns/create","CreateCampaigns",
            new EnumMenu.Meta("新建优惠券活动"),Arrays.asList()),
    campaigns_edit("edit",true,"/campaigns/edit","EditCampaigns",
            new EnumMenu.Meta("编辑优惠券活动"),Arrays.asList()),


    product("/product","Layout","/product/index","Product",
            new EnumMenu.Meta("产品管理","el-icon-s-ticket"),Arrays.asList()),

    product_index("index","/product/index","Product",
            new EnumMenu.Meta("产品列表","el-icon-s-ticket"),Arrays.asList()),
    product_create("create",true,"/product/create","CreateProduct",
            new EnumMenu.Meta("新建产品"),Arrays.asList()),
    product_edit("edit",true,"/product/edit","EditProduct",
            new EnumMenu.Meta("编辑产品"),Arrays.asList()),





    suppliers("/suppliers","Layout","/suppliers/index","Campaigns",
            new EnumMenu.Meta("商户管理","el-icon-s-ticket"),
            Arrays.asList()),

    suppliers_index("index","Layout","/suppliers/index","Suppliers",
            new EnumMenu.Meta("商户列表","el-icon-s-ticket"),
            Arrays.asList()),

    suppliers_create("create",true,"Layout","/suppliers/create","Suppliers",
            new EnumMenu.Meta("添加商户","el-icon-s-ticket"),
            Arrays.asList()),

    suppliers_edit("edit",true,"Layout","/suppliers/edit","Suppliers",
            new EnumMenu.Meta("编辑商户","el-icon-s-ticket"),
            Arrays.asList()),


    balance("/balance","Layout","/balance","Balance",
            new EnumMenu.Meta("余额管理","el-icon-s-ticket"),
            Arrays.asList()),
    balance_index("index","Layout","/balance/index","Balance",
            new EnumMenu.Meta("余额明细","el-icon-s-ticket"),
            Arrays.asList()),

    balance_charge_index("charge_index","Layout","/balance/charge/index","Balance",
            new EnumMenu.Meta("付款记录","el-icon-s-ticket"),
            Arrays.asList()),
    balance_refund_index("refund_index","Layout","/balance/refund/index","Balance",
            new EnumMenu.Meta("退款记录","el-icon-s-ticket"),
            Arrays.asList()),



    employee("/employee","Layout","/balance","Employee",
            new EnumMenu.Meta("职工管理","el-icon-s-ticket"),
            Arrays.asList()),
    employee_index("index","Layout","/employee/index","Employee",
            new EnumMenu.Meta("职工列表","el-icon-s-ticket"),
            Arrays.asList()),
    employee_create("create",true,"Layout","/employee/create","Employee",
            new EnumMenu.Meta("添加职工","el-icon-s-ticket"),
            Arrays.asList()),


    tour_booking("/tour_booking","Layout","/balance","TourBooking",
            new EnumMenu.Meta("旅行团订单管理","el-icon-s-ticket"),
            Arrays.asList()),
    tour_booking_index("index","Layout","/tour_booking/index","TourBooking",
            new EnumMenu.Meta("旅行团订单列表","el-icon-s-ticket"),
            Arrays.asList()),
    tour_booking_detail("detail",true,"Layout","/tour_booking/detail","TourBooking",
            new EnumMenu.Meta("旅行团订单详情","el-icon-s-ticket"),
            Arrays.asList()),

    ;


    private String path;
    private Boolean hidden;
    private String component;
    private String redirect;
    private String name;
    private EnumMenu.Meta meta;


    private List<EnumMenu> children;



    public static class Meta {

        private String title;
        private String icon;
        private Boolean noCache;
        private Boolean Affix;
        private List<String> roles;


        public Meta(String title, String icon) {
            this.icon = icon;
            this.title = title;
        }

        public Meta(String title) {
            this.title = title;
        }

        public Meta(String title, String icon, Boolean noCache) {
            this.icon = icon;
            this.title = title;
            this.noCache = noCache;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Boolean isNoCache() {
            return noCache;
        }

        public void setNoCache(Boolean noCache) {
            this.noCache = noCache;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }

        public Boolean isAffix() {
            return Affix;
        }

        public void setAffix(Boolean affix) {
            Affix = affix;
        }
    }

    EnumMenu(String path,String component,String redirect,String name,EnumMenu.Meta meta,List<EnumMenu> children) {

        this.path = path;
        this.component = component;
        this.redirect = redirect;
        this.name = name;
        this.meta = meta;
        this.children = children;
    }
    EnumMenu(String path,Boolean hidden,String component,String redirect,String name,EnumMenu.Meta meta,List<EnumMenu> children) {
        this.path = path;
        this.hidden = hidden;
        this.component = component;
        this.redirect = redirect;
        this.name = name;
        this.meta = meta;
        this.children = children;
    }
    EnumMenu(String path,Boolean hidden,String component,String name,EnumMenu.Meta meta,List<EnumMenu> children) {
        this.path = path;
        this.hidden = hidden;
        this.component = component;
        this.name = name;
        this.meta = meta;
        this.children = children;
    }
    EnumMenu(String path,String component,String name,EnumMenu.Meta meta,List<EnumMenu> children) {
        this.path = path;
        this.component = component;
        this.name = name;
        this.meta = meta;
        this.children = children;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnumMenu.Meta getMeta() {
        return meta;
    }

    public void setMeta(EnumMenu.Meta meta) {
        this.meta = meta;
    }

    public List<EnumMenu> getChildren() {
        return children;
    }

    public void setChildren(List<EnumMenu> children) {
        this.children = children;
    }
}
