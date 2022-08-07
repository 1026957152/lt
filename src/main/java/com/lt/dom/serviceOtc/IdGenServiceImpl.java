package com.lt.dom.serviceOtc;

import com.lt.dom.oct.Product;
import com.lt.dom.oct.Referral;
import com.lt.dom.oct.RoyaltyRuleData;
import com.lt.dom.oct.RoyaltyTemplate;
import com.lt.dom.util.CodeConfig;
import com.lt.dom.util.Snowflake;
import com.lt.dom.util.VoucherCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class IdGenServiceImpl {//** * redis lock Operation Service * *

    private Snowflake snowflake = new Snowflake();

    public String nextId(String type){


        Referral referral = new Referral();

        return type+snowflake.nextId();


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

    CodeConfig employeeConfig = new CodeConfig(6,null,null,null,"emp_#######");
    public String employeeNo(){

        String no = VoucherCodes.generate(employeeConfig);

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

    CodeConfig productConfig = new CodeConfig(20,null,null,null,"pro_###############################");
    public String productNo(){

        String no = VoucherCodes.generate(productConfig);

        return no;


    }

    CodeConfig redemptionEntryConfig = new CodeConfig(20,null,null,null,"r_###############################");
    public String redemptionEntryCode(){

        String no = VoucherCodes.generate(redemptionEntryConfig);

        return no;


    }
    CodeConfig payConfig = new CodeConfig(20,null,null,null,"pay_###############################");
    public String paymentNo(){

        String no = VoucherCodes.generate(payConfig);

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
    CodeConfig refundfig = new CodeConfig(20,null,null,null,"ref_###########################");
    public String refundCode(){

        String no = VoucherCodes.generate(refundfig);

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
    CodeConfig bookingConfig = new CodeConfig(20,null,null,null,"res_###########################");
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