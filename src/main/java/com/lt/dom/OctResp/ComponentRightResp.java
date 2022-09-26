package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumComponentStatus;
import com.lt.dom.otcenum.EnumDuration;
import com.lt.dom.otcenum.EnumProductComponentSource;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComponentRightResp {
    private EnumComponentStatus status;
    private String status_text;
    private EnumDuration duration;
    private Long quantity;
    private String duration_text;
    private long id;


    public static ComponentRightResp from(ComponentRight componentRight) {

        ComponentRightResp componentRightResp = new ComponentRightResp();
        componentRightResp.setName(componentRight.getName());
        componentRightResp.setSupplierId(componentRight.getSupplier());
        componentRightResp.setNote(componentRight.getNote());
        componentRightResp.setStatus(componentRight.getStatus());
        componentRightResp.setStatus_text(componentRight.getStatus().toString());
        componentRightResp.setDuration(componentRight.getDuration());
        componentRightResp.setQuantity(componentRight.getQuantity());
        componentRightResp.setDuration_text(componentRight.getDuration().toString());
        componentRightResp.setId(componentRight.getId());


        return componentRightResp;
    }   // 这个是 下单的时候， 从 product 中生成 的


    private String text;
    private List<EnumResp> validatorWays;
    private EnumProductComponentSource origin;


    public static ComponentRightResp from(ComponentRight componentRight, List<EnumResp> validatorWays,EnumProductComponentSource origin) {

        ComponentRightResp componentRightResp = new ComponentRightResp();
        componentRightResp.setName(componentRight.getName());
        componentRightResp.setSupplierId(componentRight.getSupplier());
        componentRightResp.setNote(componentRight.getNote());
        componentRightResp.setStatus(componentRight.getStatus());
        componentRightResp.setStatus_text(componentRight.getStatus().toString());
        componentRightResp.setDuration(componentRight.getDuration());
        componentRightResp.setQuantity(componentRight.getQuantity());
        componentRightResp.setDuration_text(componentRight.getDuration().toString());
        componentRightResp.setId(componentRight.getId());

        componentRightResp.setValidatorWays(validatorWays);
        componentRightResp.setOrigin(origin);
        return componentRightResp;
    }   // 这个是 下单的时候， 从 product 中生成 的


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EnumProductComponentSource getOrigin() {
        return origin;
    }

    public void setOrigin(EnumProductComponentSource origin) {
        this.origin = origin;
    }

    public List<EnumResp> getValidatorWays() {
        return validatorWays;
    }

    public void setValidatorWays(List<EnumResp> validatorWays) {
        this.validatorWays = validatorWays;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }



    public static class ComponentRightOption {

        private long id;
        private String text;
        private List<EnumResp> validatorWays;
        private EnumProductComponentSource origin;
        private List<EnumResp> royalty_modes;

        public void setId(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setValidatorWays(List<EnumResp> validatorWays) {
            this.validatorWays = validatorWays;
        }

        public List<EnumResp> getValidatorWays() {
            return validatorWays;
        }

        public void setOrigin(EnumProductComponentSource origin) {
            this.origin = origin;
        }

        public EnumProductComponentSource getOrigin() {
            return origin;
        }

        public void setRoyalty_modes(List<EnumResp> royalty_modes) {
            this.royalty_modes = royalty_modes;
        }

        public List<EnumResp> getRoyalty_modes() {
            return royalty_modes;
        }
    }


/*    List<RatePlan> ratePlans;*/

    private long supplierId;

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

/*

    private RoyaltyRule royaltyRule ;

    public RoyaltyRule getRoyaltyRule() {
        return royaltyRule;
    }

    public void setRoyaltyRule(RoyaltyRule royaltyRule) {
        this.royaltyRule = royaltyRule;
    }
*/


    private String name;
    private String note;


/*
    private AccessValidator accessValidator ;
    private long accessValidatorId;

    public long getAccessValidatorId() {
        return accessValidatorId;
    }

    public void setAccessValidatorId(long accessValidatorId) {
        this.accessValidatorId = accessValidatorId;
    }


    List<ComponentVounch> componentVounches;


    public List<ComponentVounch> getComponentRightVounches() {
        return componentVounches;
    }

    public void setComponentRightVounches(List<ComponentVounch> componentVounches) {
        this.componentVounches = componentVounches;
    }
*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

/*    public AccessValidator getAccessValidator() {
        return accessValidator;
    }

    public void setAccessValidator(AccessValidator accessValidator) {
        this.accessValidator = accessValidator;
    }*/

    public void setStatus(EnumComponentStatus status) {
        this.status = status;
    }

    public EnumComponentStatus getStatus() {
        return status;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setDuration(EnumDuration duration) {
        this.duration = duration;
    }

    public EnumDuration getDuration() {
        return duration;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setDuration_text(String duration_text) {
        this.duration_text = duration_text;
    }

    public String getDuration_text() {
        return duration_text;
    }
}
