package com.lt.dom.oct;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AgentProductKey implements Serializable {

    @Column(name = "agent_id")
    Long agentId;

    @Column(name = "product_id")
    Long productId;

    public AgentProductKey(long id, long id1) {
        this.agentId = id;
        this.productId = id1;
    }

    public AgentProductKey() {

    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}