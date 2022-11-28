package com.lt.dom.oct;


import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;

@Entity
public class Pointsofinterest extends Base{

    private String title;//"addressLine1";//: "3000 Chesterfield Mall",
    private String description;//"addressLine1";//: "3000 Chesterfield Mall",

    private String snippet;//"addressLine1";//: "3000 Chesterfield Mall",




    private String location;//"addressLine1";//: "3000 Chesterfield Mall",
    private Long attraction;//"addressLine1";//: "3000 Chesterfield Mall",



    @Transient
    List<Attribute> attributes;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getAttraction() {
        return attraction;
    }

    public void setAttraction(Long attraction) {
        this.attraction = attraction;
    }
}
