package com.lt.dom.otcReq;

public class UserPojo {

    private String first_name;//	string	The user’s first name
    private String last_name;//	string	The user’s last name
    private String username;//	string	The user’s username*
    private String email;//	string	The user’s email address
    private String phone;//	string	The user’s phone number

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
