package com.lt.dom.otcReq;

import com.lt.dom.OctResp.PublicationEntryResp;
import com.lt.dom.oct.DayRule;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


public class BookingPojo {


    @NotNull
    private Tour additional_info;


    private String important_note;//	The staff entered "pinned note" on the booking
    private String workflow_note;//	The staff entered note form the "workflow" tab on the booking

    public Tour getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(Tour additional_info) {
        this.additional_info = additional_info;
    }

    public String getImportant_note() {
        return important_note;
    }

    public void setImportant_note(String important_note) {
        this.important_note = important_note;
    }

    public String getWorkflow_note() {
        return workflow_note;
    }

    public void setWorkflow_note(String workflow_note) {
        this.workflow_note = workflow_note;
    }

    public static class Tour {

        private String tour_name;//

        private String lead_customer_id;//	ID number for the lead customer

        @NotNull
        @Size(max = 20)
        private String lead_customer_name;//	Lead customer full name. Built from other name components
        private String lead_customer_email;//	Email address
      //  private String lead_customer_tel_home;//	Home number
      @NotNull
      @Size(max = 11)
        private String lead_customer_tel_mobile;//	Mobile number
      //  lead_customer_contact_note	E.g. "Call after 7pm"
      //  lead_customer_travelling	1 if the lead customer is travelling on the booking, 0 if they aren't

        public String getLead_customer_id() {
            return lead_customer_id;
        }

        public void setLead_customer_id(String lead_customer_id) {
            this.lead_customer_id = lead_customer_id;
        }

        public String getLead_customer_name() {
            return lead_customer_name;
        }

        public void setLead_customer_name(String lead_customer_name) {
            this.lead_customer_name = lead_customer_name;
        }

        public String getLead_customer_email() {
            return lead_customer_email;
        }

        public void setLead_customer_email(String lead_customer_email) {
            this.lead_customer_email = lead_customer_email;
        }

        public String getLead_customer_tel_mobile() {
            return lead_customer_tel_mobile;
        }

        public void setLead_customer_tel_mobile(String lead_customer_tel_mobile) {
            this.lead_customer_tel_mobile = lead_customer_tel_mobile;
        }

        public String getTour_name() {
            return tour_name;
        }

        public void setTour_name(String tour_name) {
            this.tour_name = tour_name;
        }
    }








    private long productId;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }


    private List<Long> discounts;

    public List<Long> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Long> discounts) {
        this.discounts = discounts;
    }
}
