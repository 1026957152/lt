package com.lt.dom.serviceOtc;

import com.lt.dom.oct.Product;
import com.lt.dom.oct.Referral;
import com.lt.dom.oct.RoyaltyRuleData;
import com.lt.dom.oct.RoyaltyTemplate;
import com.lt.dom.util.Snowflake;
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