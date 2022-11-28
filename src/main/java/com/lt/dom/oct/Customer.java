package com.lt.dom.oct;

import com.lt.dom.OctResp.EnumLongIdResp;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Customer {


        @Version
        private Integer version;

        @GeneratedValue(strategy = GenerationType.IDENTITY)

        @Id
        private long id;


    //private String title;

   // private String givenName;
    private String name;
   // private String surname;
    private String phone;
    private String description;




    private String AddressLine;
    private String CityName;
    private String PostalCode;
    private String CountryName;
    private String CompanyName;


    public static List List(List<Customer> componentRightMap) {
        return componentRightMap.stream().map(x->{

            EnumLongIdResp enumResp = new EnumLongIdResp();
            enumResp.setId(x.getId());

            enumResp.setText(x.getName()+"_"+x.getPhone());
            return enumResp;
        }).collect(Collectors.toList());
    }
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
