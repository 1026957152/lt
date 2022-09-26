package com.lt.dom.serviceOtc;


import com.google.gson.Gson;
import com.lt.dom.OctResp.ProductResp;
import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.Departures;
import com.lt.dom.oct.Product;
import com.lt.dom.otcReq.BookingRulePojo;
import com.lt.dom.otcenum.EnumAvailabilityRangetype;
import com.lt.dom.otcenum.EnumAvailabilityStatus;
import com.lt.dom.otcenum.EnumAvailabilityType;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.vo.AvailabilityVO;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AvailabilityServiceImpl {

    @Autowired
    private BookingRuleRepository bookingRuleRepository;



    public static List<LocalDate> getAvailability(List<Departures> departures) {


        List<LocalDate> localDates = departures.stream().filter(x -> x.getType() == "").map(x -> {

            return Arrays.asList(LocalDate.now());

        }).flatMap(List::stream).collect(Collectors.toList());

        List<LocalDate> close = departures.stream().filter(x -> x.getType() == "").map(x -> {

            return Arrays.asList(LocalDate.now());

        }).flatMap(List::stream).collect(Collectors.toList());


        return localDates.stream().filter(close::contains).collect(Collectors.toList());


    }


    // 这里都取得 交集  都是 可下下单则 可下单， 其他都是 不下单
    public List<AvailabilityVO> getAvailability_(Product product, List<BookingRule> bookingRules, LocalDate localDate) {



/*
        List<Pair<LocalDate,Boolean>> localDates_date_rang = bookingRules.stream().filter(x->x.getRangetype() == EnumAvailabilityRangetype.Date_range).map(x->{
            if(x.getBookings_to().isAfter(localDate)) {
                x.setBookings_to(localDate);
            }

            List<Pair<LocalDate,Boolean>> localDateList =  x.getBookings_from().datesUntil(x.getBookings_to())
                    .collect(Collectors.toList()).stream().map(e->{
                        return Pair.with(e,x.getBookable());
                    }).collect(Collectors.toList());

            return localDateList;

        }).flatMap(List::stream).collect(Collectors.toList());*/



        List<Pair<LocalDate,Boolean>> pairList = bookingRules.stream().filter(x->x.getRangetype() == EnumAvailabilityRangetype.Date_range).map(x->{


            if(x.getBookings_to().isBefore(LocalDate.now())){
                List<Pair<LocalDate,Boolean>> LIST =  Arrays.asList();
                return LIST;
            }

            if(localDate.isBefore(x.getBookings_to())) {
                x.setBookings_to(localDate);
            }

            if(LocalDate.now().isAfter(x.getBookings_from())) {
                x.setBookings_from(LocalDate.now());
            }


            List<Pair<LocalDate,Boolean>> localDateList =  x.getBookings_from().datesUntil(x.getBookings_to())
                    .collect(Collectors.toList()).stream().map(e->{
                        return Pair.with(e,x.getBookable());
                    }).collect(Collectors.toList());

            return localDateList;

        }).flatMap(List::stream).collect(Collectors.groupingBy(e->e.getValue0())).entrySet().stream()
                .sorted(Comparator.comparing(e->e.getKey()))
                .map(ee->{


            ;
            return Pair.with(ee.getKey(),ee.getValue().stream().map(e->e.getValue1()).reduce((a,b)-> a && b).orElse(true));
        }).collect(Collectors.toList());



        System.out.println("这里时 获得了 每日的时间 序列"+pairList);




        Set<Month> monthList_bookable = bookingRules.stream()
                .filter(x->x.getRangetype() == EnumAvailabilityRangetype.Range_of_months )
                .filter(x->x.getBookable()).map(e->{
            return  Arrays.asList(new Gson().fromJson(e.getMonths_json(),Month[].class));
        }).flatMap(List::stream).collect(Collectors.toSet());

        Set<Month> monthList_no_bookable = bookingRules.stream()
                .filter(x->x.getRangetype() == EnumAvailabilityRangetype.Range_of_months )
                .filter(x->!x.getBookable()).map(e->{
                    return  Arrays.asList(new Gson().fromJson(e.getMonths_json(),Month[].class));
                }).flatMap(List::stream).collect(Collectors.toSet());


        System.out.println(monthList_bookable+" 需要包含的月份 ");
        System.out.println(monthList_no_bookable+" 不需要包含的月份 ");
        List<Pair<LocalDate,Boolean>> localDates_Range_of_months = pairList.stream().map(pair->{

            LocalDate xx = pair.getValue0();
            if(pair.getValue1()){

                if(monthList_no_bookable.contains(xx.getMonth())){
                    return  Pair.with(xx,false);// && monthList_no_bookable.contains(xx.getMonth()) ;
                }
              if(monthList_bookable.contains(xx.getMonth())){
                    return  Pair.with(xx,true);// && monthList_no_bookable.contains(xx.getMonth()) ;
                }
                return  Pair.with(xx,false);

            }else{
                return  Pair.with(xx,false);
            }


            // && monthList_no_bookable.contains(xx.getMonth()) ;

        }).collect(Collectors.toList());
        System.out.println("这里时 日期获得 序列"+localDates_Range_of_months);

            /*
                bookingRules.stream()
                .anyMatch(x->x.getRangetype() == EnumAvailabilityRangetype.Range_of_months)? bookingRules.stream()
                .filter(x->x.getRangetype() == EnumAvailabilityRangetype.Range_of_months).map(x->{

            return ;


        }).flatMap(List::stream).collect(Collectors.toList()) :localDates;*/





        List<DayOfWeek> weekList_bookable = bookingRules.stream()
                .filter(x->x.getRangetype() == EnumAvailabilityRangetype.Range_of_weeks )
                .filter(x->x.getBookable()).map(e->{
                    return  Arrays.asList(new Gson().fromJson(e.getWeeks_json(),DayOfWeek[].class));
                }).flatMap(List::stream).collect(Collectors.toList());

        List<DayOfWeek> weekList_no_bookable = bookingRules.stream()
                .filter(x->x.getRangetype() == EnumAvailabilityRangetype.Range_of_weeks )
                .filter(x->!x.getBookable()).map(e->{
                    return  Arrays.asList(new Gson().fromJson(e.getWeeks_json(),DayOfWeek[].class));
                }).flatMap(List::stream).collect(Collectors.toList());




        List<Pair<LocalDate,Boolean>>  close = localDates_Range_of_months.stream().map(xx->{
            LocalDate l = xx.getValue0();
            Boolean bookable = xx.getValue1();

            System.out.println(l.getMonthValue()+" 某个月份");

            if(bookable){
                if(weekList_no_bookable.contains(l.getDayOfWeek())){
                    return  Pair.with(l,false);// && monthList_no_bookable.contains(xx.getMonth()) ;
                }
                if(weekList_bookable.contains(l.getDayOfWeek())){
                    return  Pair.with(l,true);// && monthList_no_bookable.contains(xx.getMonth()) ;
                }
                return  Pair.with(l,false);// && monthList_no_bookable.contains(xx.getMonth()) ;

            }else{
                return  Pair.with(l,true);// && monthList_no_bookable.contains(xx.getMonth()) ;

            }
/*            if(weekList_bookable.contains(l.getDayOfWeek())){
                return  Pair.with(l, bookable && true);// && monthList_no_bookable.contains(xx.getMonth()) ;
            }
    */


         //   return  Pair.with(l,bookable && weekList_bookable.contains(l.getDayOfWeek())) ;//&& weekList_no_bookable.contains(xx.getMonth()) ;
        }).collect(Collectors.toList());


      /*
        List<LocalDate> close =bookingRules.stream().anyMatch(x->x.getRangetype() == EnumAvailabilityRangetype.Range_of_weeks )?
                bookingRules.stream().filter(bookingRule->bookingRule.getRangetype() == EnumAvailabilityRangetype.Range_of_weeks ).map(bookingRule->{
                //    return getDatesBetweenUsingJava9(localDates_month,bookingRule);

                    List<Integer> integers = IntStream.range(bookingRule.getWeek_from().getValue(),bookingRule.getWeek_to().getValue()).mapToObj(e->e).collect(Collectors.toList());
                    return localDates.stream().filter(x->{

                        System.out.println(x.getDayOfWeek().getValue()+"dd");
                        System.out.println(integers+"dddddddd");
                        if(bookingRule.getBookable()){
                            return  integers.contains(x.getDayOfWeek().getValue());

                        }else{
                            return  !integers.contains(x.getDayOfWeek().getValue());

                        }
                    }).collect(Collectors.toList());

                }).flatMap(List::stream).collect(Collectors.toList()) :localDates_month;


*/
        System.out.println("这里时 星期 过来出来的 序列"+close);

/*
        List<Pair<LocalDateTime,LocalDateTime>> pairList_ = departures.stream().filter(x->x.getRangtype() == EnumAvailabilityRangetype.Time_Range_$all_week$ ).map(x->{
            return getTimeBetween(close,x);


        }).flatMap(List::stream).collect(Collectors.toList());

*/



        int index = 0;
        List<BookingRule> finalBookingRules = bookingRules;



        List<AvailabilityVO> pairList_withtime = IntStream.range(0,close.size()).mapToObj(e->{

            Pair<LocalDate,Boolean> x = close.get(e);
            LocalDate l = x.getValue0();
            Boolean bookable = x.getValue1();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
            AvailabilityVO availabilityVO = new AvailabilityVO();

            availabilityVO.setBooking_at(l);
            availabilityVO.setBooking_at_text(formatter.format(l));

            if(e == 0){
                availabilityVO.setBooking_at_text("明天");
            }
            if(e == 1){
                availabilityVO.setBooking_at_text("后天");
            }

            availabilityVO.setAvailable(bookable);
            availabilityVO.setWeek_text(l.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.CHINA));
            if(bookable){
                availabilityVO.setOpeningHours(Arrays.asList());
                availabilityVO.setSpaces_remaining(200);
                availabilityVO.setCapacity(200);

                availabilityVO.setStatus(EnumAvailabilityStatus.AVAILABLE);
            }else{
                availabilityVO.setSpaces_remaining(0);
                availabilityVO.setStatus(EnumAvailabilityStatus.CLOSED);
            }


           // availabilityVO.setBookable(true);




           // availabilityVO.setPriority(x.getPriority());


            availabilityVO.setOpeningHours(finalBookingRules.stream().filter(bookingRule->bookingRule.getRangetype() == EnumAvailabilityRangetype.Time_Range_$all_week$ ).map(bookingRule->{


                AvailabilityVO.OpeningHour time = new AvailabilityVO.OpeningHour();
                time.setFrom(bookingRule.getTime_from());
                time.setTo(bookingRule.getTime_to());
                return time;

            }).collect(Collectors.toList()));


            return availabilityVO;
        }).collect(Collectors.toList());




 /*       List<AvailabilityVO> pairList =
                bookingRules.stream().anyMatch(x->x.getRangtype() == EnumAvailabilityRangetype.Time_Range_$all_week$ )?
                bookingRules.stream().filter(x->x.getRangtype() == EnumAvailabilityRangetype.Time_Range_$all_week$ ).map(x->{


        //    return getTimeBetweenUsingJava9(close,x);


                    LocalDateTime.now().toLocalTime();


        }).flatMap(List::stream).collect(Collectors.toList()) : close.stream().map(e->{

                        AvailabilityVO availabilityVO = new AvailabilityVO();
                        availabilityVO.setBookings_at(e);
                        availabilityVO.setTime_from(e.atStartOfDay().toLocalTime());
                        availabilityVO.setTime_to(e.atStartOfDay().toLocalTime());
                     //   availabilityVO.setBookable(e.getBookable());
                       // availabilityVO.setPriority(e.getPriority());
                        return availabilityVO;
                    }).collect(Collectors.toList());
*/



        return pairList_withtime;


    }




    public static List<LocalDate> getDatesBetweenUsingJava9(List<LocalDate> localDates ,BookingRule bookingRule) {

        List<Integer> integers = IntStream.range(bookingRule.getWeek_from().getValue(),bookingRule.getWeek_to().getValue()).mapToObj(e->e).collect(Collectors.toList());
        return localDates.stream().filter(x->{

            System.out.println(x.getDayOfWeek().getValue()+"dd");
            System.out.println(integers+"dddddddd");
            if(bookingRule.getBookable()){
                return  integers.contains(x.getDayOfWeek().getValue());

            }else{
                return  !integers.contains(x.getDayOfWeek().getValue());

            }
        }).collect(Collectors.toList());
    }

    public static List<Pair<LocalDateTime,LocalDateTime>> getTimeBetween(List<LocalDate> localDates , BookingRule departures) {

        LocalDateTime.now().toLocalTime();
        return localDates.stream().map(x->{
            return Pair.with(x.atTime(departures.getTime_from()),x.atTime(departures.getTime_to()));
        }).collect(Collectors.toList());
    }



    public static List<AvailabilityVO> getTimeBetweenUsingJava9(List<LocalDate> localDates , BookingRule departures) {

        LocalDateTime.now().toLocalTime();
        return localDates.stream().map(x->{

            AvailabilityVO availabilityVO = new AvailabilityVO();
         //   availabilityVO.setBooking_at(x);

      /*      availabilityVO.setTime_from(departures.getTime_from());
            availabilityVO.setTime_to(departures.getTime_to());*/
            availabilityVO.setBookable(departures.getBookable());
         //   availabilityVO.setPriority(departures.getPriority());
            return availabilityVO;
        }).collect(Collectors.toList());
    }
    public List<Calendar> listAvailability(Product product) {
        return null;
    }

    public ProductResp.Availability form(List<BookingRule> bookingRuleList) {



            ProductResp.Availability availability = new ProductResp.Availability();
            availability.setRanges(bookingRuleList.stream().map(e->{
                BookingRulePojo bookingRuleVO = new BookingRulePojo();
                bookingRuleVO.setBookable(e.getBookable());
                bookingRuleVO.setPriority(e.getPriority());

                bookingRuleVO.setRangetype(e.getRangetype());




                if(e.getRangetype().equals(EnumAvailabilityRangetype.Date_range)){


                    // Parses the date
/*                LocalDate dt1
                        = LocalDate
                        .parse("2018-11-03T12:45:30");*/
                    bookingRuleVO.setFrom(e.getBookings_from().toString());
                    bookingRuleVO.setTo(e.getBookings_to().toString());

                }
                if(e.getRangetype().equals(EnumAvailabilityRangetype.Range_of_months)){
   /*                 bookingRuleVO.setFrom(e.getMonth_from().toString());
                    bookingRuleVO.setTo(e.getMonth_to().toString());*/
                    bookingRuleVO.setWeeks(Arrays.stream(new Gson().fromJson(e.getMonths_json(),DayOfWeek[].class)).map(ee->ee.getDisplayName(TextStyle.SHORT, Locale.CHINA)).collect(Collectors.toList()));

                }
                if(e.getRangetype().equals(EnumAvailabilityRangetype.Time_Range_$all_week$)){

                    bookingRuleVO.setFrom(e.getTime_from().toString());
                    bookingRuleVO.setTo(e.getTime_to().toString());

                }

                if(e.getRangetype().equals(EnumAvailabilityRangetype.Range_of_weeks)){
                   // bookingRuleVO.setFrom(e.getWeek_from().toString());
                    bookingRuleVO.setWeeks(Arrays.stream(new Gson().fromJson(e.getWeeks_json(),DayOfWeek[].class)).map(ee->ee.getDisplayName(TextStyle.SHORT, Locale.CHINA).toString()).collect(Collectors.toList()));
                 //   bookingRuleVO.setTo(e.getWeek_to().toString());


                }
                return bookingRuleVO;
            }).collect(Collectors.toList()));

            return availability;
    }







/*


    public static List<LocalDate> getDatesBetweenUsingJava9(Departures departures) {

        List<LocalDate> localDates =  departures.getPeriodFrom().datesUntil(departures.getPeriodTo())
                .collect(Collectors.toList());


        return localDates.stream().filter(x->{
            if(departures.isMonday()){
                return x.getDayOfWeek() == DayOfWeek.MONDAY;
            }
            if(departures.isThursday()){
                return x.getDayOfWeek() == DayOfWeek.THURSDAY;
            }
            if(departures.isWednesday()){
                return x.getDayOfWeek() == DayOfWeek.WEDNESDAY;
            }
            if(departures.isWednesday()){
                return x.getDayOfWeek() == DayOfWeek.WEDNESDAY;
            }
            return false;
        }).collect(Collectors.toList());
    }

    public List<Calendar> listAvailability(Product product) {

        List<BookingRule> bookingRules = bookingRuleRepository.findByProduct(product.getId());
        BookingRule bookingRule = bookingRules.get(0);
        List<LocalDate> localDates = null;//getDatesBetweenUsingJava9(bookingRule.getBookings_from(),bookingRule.getBookings_to());


        return null;
    }*/
}
