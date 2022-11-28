package com.lt.dom.serviceOtc;

import com.lt.dom.oct.*;
import com.lt.dom.util.CodeConfig;
import com.lt.dom.util.Snowflake;
import com.lt.dom.util.VoucherCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.lt.dom.util.CodeConfig.Charset.NUMBERS;


@Service
public class IdGenServiceImpl {//** * redis lock Operation Service * *

    private Snowflake snowflake = new Snowflake();

    public String nextId(String type){


        Referral referral = new Referral();

        return type+snowflake.nextId();


    }

    CodeConfig configRatePlan = new CodeConfig(6,null,null,null,"plan_#######");

    public String ratePlanCode(){
        String no = VoucherCodes.generate(configRatePlan);
        return no;
    }

    CodeConfig configSupplier = new CodeConfig(6,null,null,null,"sup_#######");

    public String supplierNo(){
        String no = VoucherCodes.generate(configSupplier);
        return no;
    }
    CodeConfig exportConfig = new CodeConfig(6,null,null,null,"exp_#######");
    public String exportNo(){

        String no = VoucherCodes.generate(exportConfig);

        return no;


    }
    CodeConfig seatingLayoutConfig = new CodeConfig(6,null,null,null,"sela_#######");
    public String seatingLayoutCode(){

        String no = VoucherCodes.generate(seatingLayoutConfig);

        return no;


    }
    CodeConfig valueListConfig = new CodeConfig(6,null,null,null,"rsl_#######");
    public String valueListCode(){

        String no = VoucherCodes.generate(valueListConfig);

        return no;


    }
    CodeConfig assetDeviceConfig = new CodeConfig(6,null,null,null,"asset_#################");
    public String assetDeviceCode(){

        String no = VoucherCodes.generate(assetDeviceConfig);

        return no;


    }



    CodeConfig partnerConfig = new CodeConfig(20,null,null,null,"part_#########");
    public String partnerNo(){

        String no = VoucherCodes.generate(partnerConfig);

        return no;


    }
    CodeConfig openidConfig = new CodeConfig(20,null,null,null,"ope_##########################################");
    public String openidNo(){

        String no = VoucherCodes.generate(openidConfig);

        return no;


    }
    CodeConfig invoiceConfig = new CodeConfig(20,null,null,null,"invo_##########################################");
    public String invoiceNo(){

        String no = VoucherCodes.generate(invoiceConfig);

        return no;


    }
    CodeConfig cityWalkConfig = new CodeConfig(20,null,null,null,"ciw_##########################################");
    public String cityWalkCode(){

        String no = VoucherCodes.generate(cityWalkConfig);

        return no;


    }
    CodeConfig requestConfig = new CodeConfig(6,null,null,null,"req_#######");
    public String requestNo(){

        String no = VoucherCodes.generate(requestConfig);

        return no;


    }

    CodeConfig redemptionConfig = new CodeConfig(6,null,null,null,"rede_#######");
    public String redemptionNo(){

        String no = VoucherCodes.generate(redemptionConfig);

        return no;


    }
    CodeConfig employeeConfig = new CodeConfig(6,null,null,null,"emp_#######");
    public String employeeNo(){

        String no = VoucherCodes.generate(employeeConfig);

        return no;


    }
    CodeConfig busConfig = new CodeConfig(6,null,null,null,"bus_#######");
    public String busNo(){

        String no = VoucherCodes.generate(busConfig);

        return no;


    }
    CodeConfig oneCodeConfig = new CodeConfig(6,null,null,null,"into_##############");
    public String oneCodeNo(){

        String no = VoucherCodes.generate(oneCodeConfig);

        return no;


    }
    CodeConfig tempDocumentConfig = new CodeConfig(40,null,null,null,"doc_########################################################");
    public String tempDocumentCode(){

        String no = VoucherCodes.generate(tempDocumentConfig);

        return no;


    }
    CodeConfig documentConfig = new CodeConfig(30,null,null,null,"doc_########################################");
    public String documentCode(){

        String no = VoucherCodes.generate(documentConfig);

        return no;


    }

    CodeConfig productConfig = new CodeConfig(20,null,null,null,"pro_#######");
    public String productNo(){

        String no = VoucherCodes.generate(productConfig);

        return no;


    }

    CodeConfig redemptionEntryConfig = new CodeConfig(20,null,null,null,"r_#####################");
    public String redemptionEntryCode(){

        String no = VoucherCodes.generate(redemptionEntryConfig);

        return no;


    }

    CodeConfig tranConfig = new CodeConfig(20,null,null,null,"pay_###################");
    public String tranNo(){

        String no = VoucherCodes.generate(tranConfig);

        return no;

    }

    CodeConfig quotaConfig = new CodeConfig(20,null,null,null,"quota_###################");
    public String quotaNo(){

        String no = VoucherCodes.generate(quotaConfig);

        return no;


    }
    CodeConfig payConfig = new CodeConfig(20,null,null,null,"pay_###################");
    public String paymentNo(){

        String no = VoucherCodes.generate(payConfig);

        return no;


    }
    CodeConfig subscriptionConfig = new CodeConfig(20,null,null,null,"subs_###################");
    public String subscriptionCode(){

        String no = VoucherCodes.generate(subscriptionConfig);

        return no;


    }
    CodeConfig attractionConfig = new CodeConfig(20,null,null,null,"attr_###################");
    public String attractionCode(){

        String no = VoucherCodes.generate(attractionConfig);

        return no;


    }
    CodeConfig requestCreditfig = new CodeConfig(20,null,null,null,"cre_###################");
    public String requestCreditNo(){

        String no = VoucherCodes.generate(requestCreditfig);

        return no;


    }

    CodeConfig importConfig = new CodeConfig(20,null,null,null,"imp_###############################");
    public String importNo(){

        String no = VoucherCodes.generate(importConfig);

        return no;


    }
    CodeConfig userConfig = new CodeConfig(20,null,null,null,"use_###############");
    public String userNo(){

        String no = VoucherCodes.generate(userConfig);

        return no;


    }

    CodeConfig passfig = new CodeConfig(20,NUMBERS,null,null,"###################");
    public String passCode(){

        String no = VoucherCodes.generate(passfig);

        return no;


    }

    CodeConfig lineItemfig = new CodeConfig(20,null,null,null,"li_###################");
    public String lineItemCode(){

        String no = VoucherCodes.generate(lineItemfig);

        return no;


    }
    CodeConfig refundfig = new CodeConfig(20,null,null,null,"ref_###########################");
    public String refundCode(){

        String no = VoucherCodes.generate(refundfig);

        return no;


    }
    CodeConfig guideConfig = new CodeConfig(8,null,null,null,"gui_###########################");
    public String guideCode(){

        String no = VoucherCodes.generate(guideConfig);
        return no;
    }

    CodeConfig serviceConfig = new CodeConfig(8,null,null,null,"serv_###########################");
    public String serviceCode(){

        String no = VoucherCodes.generate(serviceConfig);
        return no;
    }

    CodeConfig busStopConfig = new CodeConfig(6,null,null,null,"bsto_#####");
    public String busStopCode(){

        String no = VoucherCodes.generate(busStopConfig);
        return no;
    }
    CodeConfig theatreConfig = new CodeConfig(8,null,null,null,"thea_###########################");
    public String theatreCode(){

        String no = VoucherCodes.generate(theatreConfig);
        return no;
    }
    CodeConfig makePlanConfig = new CodeConfig(8,null,null,null,"makeplan_############");
    public String makePlanCode(){

        String no = VoucherCodes.generate(makePlanConfig);
        return no;
    }

    CodeConfig ticketConfig = new CodeConfig(8,null,null,null,"tike_############");
    public String ticketCode(){

        String no = VoucherCodes.generate(ticketConfig);
        return no;
    }
    CodeConfig tripConfig = new CodeConfig(8,null,null,null,"trip_############");
    public String tripCode(){

        String no = VoucherCodes.generate(tripConfig);
        return no;
    }

    CodeConfig campaignToTourBookingConfig = new CodeConfig(20,null,null,null,"ctt_##################");
    public String campaignToTourBookingCode(){

        String no = VoucherCodes.generate(campaignToTourBookingConfig);
        return no;
    }
    CodeConfig payoutConfig = new CodeConfig(20,null,null,null,"pot_###########################");
    public String payoutCode(){

        String no = VoucherCodes.generate(payoutConfig);
        return no;
    }
    CodeConfig chargeConfig = new CodeConfig(20,null,null,null,"cha_###########################");
    public String chargeCode(){

        String no = VoucherCodes.generate(chargeConfig);

        return no;


    }

    CodeConfig tourBookingConfig = new CodeConfig(20,null,null,null,"tob_################");
    public String tourBookingNo(){

        String no = VoucherCodes.generate(tourBookingConfig);

        return no;


    }
    CodeConfig bookingConfig = new CodeConfig(20,null,null,null,"res_#########");
    public String bookingNo(){

        String no = VoucherCodes.generate(bookingConfig);

        return no;


    }
    CodeConfig scenarioConfig = new CodeConfig(6,null,null,null,"sce_#######");
    public String scenarioNo(){

        String no = VoucherCodes.generate(scenarioConfig);

        return no;


    }
    CodeConfig compaignConfig = new CodeConfig(6,null,null,null,"cam_#######");

    public String campaignNo() {
        String no = VoucherCodes.generate(compaignConfig);

        return no;

    }

    CodeConfig componentRightConfig = new CodeConfig(6,null,null,null,"cpr_##########");

    public String componentRightCode() {
        String no = VoucherCodes.generate(componentRightConfig);

        return no;

    }

    CodeConfig componentVoucherConfig = new CodeConfig(6,null,null,null,"cov_#####################");

    public String componentVouncherCode() {
        return VoucherCodes.generate(componentVoucherConfig);


    }

/*
    @Resource(name = "Redislockserviceimpl") private Redislockservice Redislockservice;

    @Autowired
    private Ordernumberdao DAO;


    @Override public int getmaxordernumberfromdate (map<string, object> parammap) {//Lock table (used to determine if an order can now be created)
        An hour timeout if (!redislockservice.addredislock (Constantkey.redis_lock_max_order_number, 2000, 2)) {
            return 0;
            int number = Converterutils.toint (Parammap.get ("number"));
            int nowdate = Converterutils.toint (Parammap.get ("date"));
            Get the latest order information for this type from the library ordernumber ordernumber = Dao.getordernumber (Parammap);
            OrderNumber newordernumber = new OrderNumber ();
            Newordernumber.settype (Converterutils.toint (Parammap.get ("type")));
            Newordernumber.setdate (nowdate);
            int startnum = 1; if (! Checkutils.isnullorempty (OrderNumber) && Nowdate = = Ordernumber.getdate ()) {//library has and is today, get the data in the library Startnum = Ordernumbe
                R.getnumber () + 1;
                Newordernumber.setnumber (Ordernumber.getnumber () + number);
                Dao.updateordernumber (Newordernumber);
                Redislockservice.delredislock (Constantkey.redis_lock_max_order_number);
                return startnum;
            } newordernumber.setnumber (number);
            Dao.addordernumber (Newordernumber);
            Unlock Redislockservice.delredislock (Constantkey.redis_lock_max_order_number);
            return startnum; }*/
 }