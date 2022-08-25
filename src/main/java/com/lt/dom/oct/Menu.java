package com.lt.dom.oct;

import javax.persistence.*;
import java.util.List;

@Entity
public class Menu {

    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private String id_id;

    private String description;
    private String imageUrl;

    private String name;
    private String path;
    private String component;
    private String redirect;
    private Boolean hidden;
    private Boolean alwaysShow;
    @Transient
    private Meta meta;

    @Transient
    private List<Menu> children;


    public static class Meta {

        private  boolean affix;
        private String title;
        private String icon;
        private boolean noCache;
        private List<String> roles;

        public Meta(String title, String icon) {
            this.title = title;
            this.icon = icon;
        }

        public Meta(String title, String icon, boolean affix) {
            this.title = title;
            this.icon = icon;
            this.affix = affix;
        }

        public Meta(String title) {
            this.title = title;
        }
    }
}
