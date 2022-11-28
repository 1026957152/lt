package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumIdentityRequiredType;
import com.lt.dom.serviceOtc.IdGenServiceImpl;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestrictionReq {


        @JsonProperty("minAge")
        private Integer minAge;
        @JsonProperty("maxAge")
        private Integer maxAge;
        @JsonProperty("idRequired")
        private Boolean idRequired;
        private EnumIdentityRequiredType identityRequiredType;

        public EnumIdentityRequiredType getIdentityRequiredType() {
        return identityRequiredType;
    }

    public void setIdentityRequiredType(EnumIdentityRequiredType identityRequiredType) {
        this.identityRequiredType = identityRequiredType;
    }

    @JsonProperty("minQuantity")
        private Integer minQuantity;
        @JsonProperty("maxQuantity")
        private Integer maxQuantity;
        @JsonProperty("paxCount")
        private Integer paxCount;

    @JsonProperty("passenger_identity_documents_required")
    private Boolean passenger_identity_documents_required;

    public Boolean getPassenger_identity_documents_required() {
        return passenger_identity_documents_required;
    }

    public void setPassenger_identity_documents_required(Boolean passenger_identity_documents_required) {
        this.passenger_identity_documents_required = passenger_identity_documents_required;
    }

    @JsonProperty("accompaniedBy")
        private List<String> accompaniedBy;

        public Integer getMinAge() {
            return minAge;
        }

        public void setMinAge(Integer minAge) {
            this.minAge = minAge;
        }

        public Integer getMaxAge() {
            return maxAge;
        }

        public void setMaxAge(Integer maxAge) {
            this.maxAge = maxAge;
        }

        public Boolean getIdRequired() {
            return idRequired;
        }

        public void setIdRequired(Boolean idRequired) {
            this.idRequired = idRequired;
        }

        public Integer getMinQuantity() {
            return minQuantity;
        }

        public void setMinQuantity(Integer minQuantity) {
            this.minQuantity = minQuantity;
        }

        public Integer getMaxQuantity() {
            return maxQuantity;
        }

        public void setMaxQuantity(Integer maxQuantity) {
            this.maxQuantity = maxQuantity;
        }

        public Integer getPaxCount() {
            return paxCount;
        }

        public void setPaxCount(Integer paxCount) {
            this.paxCount = paxCount;
        }

        public List<String> getAccompaniedBy() {
            return accompaniedBy;
        }

        public void setAccompaniedBy(List<String> accompaniedBy) {
            this.accompaniedBy = accompaniedBy;
        }
    }