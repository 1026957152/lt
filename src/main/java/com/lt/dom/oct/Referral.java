package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Reference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Referral {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @JsonProperty("id")
    private long id;

    @Transient
    List<RoyaltyRuleData> royaltyRuleDataList ;

    public List<RoyaltyRuleData> getRoyaltyRuleDataList() {
        return royaltyRuleDataList;
    }

    public void setRoyaltyRuleDataList(List<RoyaltyRuleData> royaltyRuleDataList) {
        this.royaltyRuleDataList = royaltyRuleDataList;
    }

    private  String code;    //"level": 0,

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private  int level;    //"level": 0,
    private String type; // 邀请码，   特定产品营销码

    //product_id, compainId 和 userId


    private String agentId;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    private String productId;
    private String userId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
/*    private String originalUser;
    private List<String> referralUser;*/

}
