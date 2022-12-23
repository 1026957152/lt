package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumRelatedObjectType;

import javax.persistence.*;
import java.util.List;

@Entity
public class Contact extends Base {




        @OneToMany(mappedBy="contact",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
        List<Identifier> identifiers;


        private String website;

        private String email;

        private String telephone;


        private String name;
        private String title;



        @Enumerated(EnumType.STRING)
        private EnumRelatedObjectType relatedObjectType;

        private Long relatedObjectId;

        public List<Identifier> getIdentifiers() {
                return identifiers;
        }

        public void setIdentifiers(List<Identifier> identifiers) {
                this.identifiers = identifiers;
        }

        public EnumRelatedObjectType getRelatedObjectType() {
                return relatedObjectType;
        }

        public void setRelatedObjectType(EnumRelatedObjectType relatedObjectType) {
                this.relatedObjectType = relatedObjectType;
        }

        public Long getRelatedObjectId() {
                return relatedObjectId;
        }

        public void setRelatedObjectId(Long relatedObjectId) {
                this.relatedObjectId = relatedObjectId;
        }

        public String getWebsite() {
                return website;
        }

        public void setWebsite(String website) {
                this.website = website;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getTelephone() {
                return telephone;
        }

        public void setTelephone(String telephone) {
                this.telephone = telephone;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }
}