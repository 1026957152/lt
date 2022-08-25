package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumReferralType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Reference;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Referral {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @JsonProperty("id")
    private long id;


    @CreatedDate
    LocalDateTime created_at ;

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    private  String code;    //"level": 0,

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private  int level;    //"level": 0,
    private EnumReferralType type; // 邀请码，   特定产品营销码

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

    public EnumReferralType getType() {
        return type;
    }

    public void setType(EnumReferralType type) {
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



    private long long_value;

    private String meta_data;

    private String string_value;

    public long getLong_value() {
        return long_value;
    }

    public void setLong_value(long long_value) {
        this.long_value = long_value;
    }

    public String getMeta_data() {
        return meta_data;
    }

    public void setMeta_data(String meta_data) {
        this.meta_data = meta_data;
    }

    public String getString_value() {
        return string_value;
    }

    public void setString_value(String string_value) {
        this.string_value = string_value;
    }
}
