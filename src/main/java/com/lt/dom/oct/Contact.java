package com.lt.dom.oct;


import javax.persistence.Entity;

@Entity
public class Contact extends Base {

        private String website;

        private String email;

        private String telephone;


        private String name;
        private String title;

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