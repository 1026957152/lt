package com.lt.dom.OctResp;


import com.lt.dom.oct.Campaign;
import com.lt.dom.oct.Document;
import com.lt.dom.oct.Scenario;
import com.lt.dom.otcenum.EnumDocumentType;
import org.springframework.hateoas.CollectionModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.Map;


public class ScenarioResp {

    private String image = "未设置";
    private String name;
    private String code;
    private CollectionModel<CampaignResp> campaigns;
    private String image_small;

    public static ScenarioResp from(Scenario scenario, Map<EnumDocumentType,List<String>> document) {

        ScenarioResp scenarioResp = new ScenarioResp();
        scenarioResp.setName(scenario.getName());
        scenarioResp.setCode(scenario.getCode());
        if(document.containsKey(EnumDocumentType.scenario_image_small)){
            scenarioResp.setImage_small(document.get(EnumDocumentType.scenario_image_small).stream().findAny().get());

        }
        if(document.containsKey(EnumDocumentType.scenario_image)){
            scenarioResp.setImage(document.get(EnumDocumentType.scenario_image).stream().findAny().get());

        }
        return scenarioResp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static ScenarioResp from(Scenario scenario) {

        ScenarioResp scenarioResp = new ScenarioResp();
        scenarioResp.setName(scenario.getName());
        scenarioResp.setCode(scenario.getCode());
        return scenarioResp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  void setCampaigns(CollectionModel<CampaignResp> campaigns) {
        this.campaigns = campaigns;
    }

    public CollectionModel<CampaignResp> getCampaigns() {
        return campaigns;
    }

    public void setImage_small(String image_small) {
        this.image_small = image_small;
    }

    public String getImage_small() {
        return image_small;
    }
}
