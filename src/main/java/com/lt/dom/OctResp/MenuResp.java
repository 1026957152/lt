package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Menu;
import com.lt.dom.otcenum.EnumMenu;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuResp {

    public MenuResp(String s) {
        this.children = new ArrayList<>();
        this.name = s;
    }

    public Boolean getNoCache() {
        return noCache;
    }

    public void setNoCache(Boolean noCache) {
        this.noCache = noCache;
    }

    private Boolean noCache;
    private String name;
    private String path;
    private String component;
    private String redirect;
    private Boolean hidden;
    private Boolean alwaysShow;
    private Meta meta;
    private boolean leaf;

    private List<MenuResp> children;

    public MenuResp(String path, String name, Meta meta) {
        this.path = path;
        this.name = name;
        this.meta = meta;
        this.children = new ArrayList<>();
    }
    public MenuResp(String path, String re,String name, Meta meta) {
        this.path = path;
        this.redirect = re;
        this.name = name;
        this.meta = meta;
        this.children = new ArrayList<>();
    }
    public MenuResp() {
        this.children = new ArrayList<>();

    }
    public MenuResp(String path,boolean hidden, String name, Meta meta) {
        this.path = path;
        this.hidden = hidden;

        this.name = name;
        this.meta = meta;
        this.children = new ArrayList<>();
    }

    public MenuResp(String path,boolean hidden, String redirect,String name, Meta meta) {
        this.path = path;
        this.hidden = hidden;
        this.redirect = redirect;

        this.name = name;
        this.meta = meta;
        this.children = new ArrayList<>();
    }
    public MenuResp(String path,boolean hidden, String redirect,String name, Meta meta,Boolean noCache) {
        this.path = path;
        this.hidden = hidden;
        this.redirect = redirect;

        this.name = name;
        this.meta = meta;
        this.noCache = noCache;
        this.children = new ArrayList<>();
    }
    public MenuResp(String path,boolean hidden,String name, Meta meta,Boolean noCache) {
        this.path = path;
        this.hidden = hidden;


        this.name = name;
        this.meta = meta;
        this.noCache = noCache;
        this.children = new ArrayList<>();
    }
    public static MenuResp from(EnumMenu root,boolean isLeaf) {
        MenuResp menuResp = new MenuResp();
        menuResp.setName(root.getName());
        menuResp.setPath(root.getPath());
        menuResp.setHidden(root.getHidden());
        menuResp.setRedirect(root.getRedirect());
        MenuResp.Meta meta = MenuResp.Meta.from(root.getMeta(),Arrays.asList("ROLE_ADMIN"));
        menuResp.setMeta(meta);
        if(!isLeaf){
            menuResp.setChildren(new ArrayList<>());
        }

        //MenuResp.Meta meta = MenuResp.Meta.from(root.getMeta());
     //   menuResp.setChildren(root.getChildren());
      //  menuResp.setMeta(root.getMeta());
        menuResp.setComponent(root.getComponent());
        return menuResp;
    }

    public static MenuResp from(EnumMenu root) {
        MenuResp menuResp = new MenuResp();
        menuResp.setName(root.getName());
        menuResp.setPath(root.getPath());
        menuResp.setHidden(root.getHidden());
        menuResp.setRedirect(root.getRedirect());
        menuResp.setChildren(new ArrayList<>());

        MenuResp.Meta meta = MenuResp.Meta.from(root.getMeta(),Arrays.asList("ROLE_ADMIN"));
        menuResp.setMeta(meta);
        menuResp.setComponent(root.getComponent());
        return menuResp;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Meta {

        private  Boolean affix;
        private String title;
        private String icon;
        private Boolean noCache;
        private List<String> roles;

        public Meta(String title, String icon) {
            this.title = title;
            this.icon = icon;
        }

        public Meta(String title, String icon, boolean noCache) {
            this.title = title;
            this.icon = icon;
            this.noCache = noCache;
        }
        public Meta(String title, String icon, List<String> roles) {
            this.title = title;
            this.icon = icon;
            this.roles = roles;
        }
        public Meta(String title, String icon, List<String> roles,Boolean noCache) {
            this.title = title;
            this.icon = icon;
            this.roles = roles;
            this.noCache = true;
        }
        public Meta(String title) {
            this.title = title;
        }

        public Meta() {

        }

        public Meta(String title, boolean noCache) {
            this.title = title;
            this.noCache = noCache;
        }


        public Meta(String title, List<String> roles,Boolean noCache) {
            this.title = title;

            this.roles = roles;
            this.noCache = true;
        }

        public Meta(String title, List<String> roles) {
            this.title = title;

            this.roles = roles;

        }

        public static Meta from(EnumMenu.Meta meta) {
            Meta meta1 = new Meta(meta.getClass().getName());
            meta1.setAffix(meta.isAffix());
            meta1.setTitle(meta.getTitle());
            meta1.setIcon(meta.getIcon());
            meta1.setRoles(meta.getRoles());
            return meta1;
        }
        public static Meta from(EnumMenu.Meta meta,List<String> roles) {
            Meta meta1 = new Meta(meta.getClass().getName());
            meta1.setAffix(meta.isAffix());
            meta1.setTitle(meta.getTitle());
            meta1.setIcon(meta.getIcon());
            meta1.setRoles(roles);
            return meta1;
        }

        public Boolean isAffix() {
            return affix;
        }

        public void setAffix(Boolean affix) {
            this.affix = affix;
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
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean getAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(Boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<MenuResp> getChildren() {
        return children;
    }

    public void setChildren(List<MenuResp> children) {
        this.children = children;
    }
}
