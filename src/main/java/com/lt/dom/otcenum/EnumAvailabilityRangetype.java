package com.lt.dom.otcenum;

import cn.hutool.core.date.Week;
import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public enum EnumAvailabilityRangetype {
    Date_range("Date range"),
   // Range_of_days("Range of days"),
    Time_Range_$all_week$("Time Range (all week)"),
    Range_of_months("Range of months"),
    Range_of_weeks("Range of weeks"),
   // Date_range_with_time("Date range with time"),



    ;



    public static List<EnumResp> from() {
        return Arrays.stream(EnumAvailabilityRangetype.values()).map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());

            if(x.equals(EnumAvailabilityRangetype.Range_of_months)){
                enumResp.setSubitems(Arrays.stream(Month.values()).map(x_by_person->{
                    EnumResp resp_month = new EnumResp();
                    resp_month.setId(x_by_person.name());
                    //  enumResp.setName(x.name());
                    resp_month.setText(x_by_person.toString());
                    return resp_month;
                }).collect(Collectors.toList()));

            }
            if(x.equals(EnumAvailabilityRangetype.Range_of_weeks)){
                enumResp.setSubitems(Arrays.stream(Week.values()).map(x_by_person->{
                    EnumResp enumResp_by_person = new EnumResp();
                    enumResp_by_person.setId(x_by_person.name());
                    //  enumResp.setName(x.name());
                    enumResp_by_person.setText(x_by_person.toString());
                    return enumResp_by_person;
                }).collect(Collectors.toList()));

            }
/*            if(x.equals(EnumAvailabilityRangetype.Range_of_months)){
                enumResp.setSubitems(Arrays.stream(Week.values()).map(x_by_person->{
                    EnumResp enumResp_by_person = new EnumResp();
                    enumResp_by_person.setId(x_by_person.name());
                    //  enumResp.setName(x.name());
                    enumResp_by_person.setText(x_by_person.toString());
                    return enumResp_by_person;
                }).collect(Collectors.toList()));

            }*/
            return enumResp;
        }).collect(Collectors.toList());
    }


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumAvailabilityRangetype(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.availability.rangetype."
                + this.name());
        return displayStatusString;
    }

}
