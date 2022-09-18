package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ProductPojo;
import com.lt.dom.repository.BookingRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PriceServiceImpl {


    @Autowired
    private BookingRuleRepository bookingRuleRepository;

    public static List<LocalDate> getAvailability(List<Departures> departures) {


        List<LocalDate> localDates = departures.stream().filter(x->x.getType() == "" ).map(x->{

            return getDatesBetweenUsingJava9(x);

        }).flatMap(List::stream).collect(Collectors.toList());

        List<LocalDate> close = departures.stream().filter(x->x.getType() == "" ).map(x->{

            return getDatesBetweenUsingJava9(x);

        }).flatMap(List::stream).collect(Collectors.toList());


        return localDates.stream().filter(close::contains).collect(Collectors.toList());


    }


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
        List<LocalDate> localDates =null;// getDatesBetweenUsingJava9(bookingRule.getBookings_from(),bookingRule.getBookings_to());


        return null;
    }

    public List<PricingType> getPriceType(Product finalProduct ,List<ProductPojo.Price> prices) {

        List<PricingType> priceTyps = prices.stream().map(x->{

            return getPriceType(finalProduct,x);
        }).collect(Collectors.toList());

        return priceTyps;
    }



    public PricingType getPriceType(Product finalProduct ,ProductPojo.Price x) {



            PricingType pricingType = new PricingType();
            pricingType.setProductId(finalProduct.getId());
            pricingType.setType(x.getType());
            switch (x.getType()){
                case ByPerson :{
                    pricingType.setPrice(x.getByPerson().getPrice());
                    pricingType.setBy(x.getBy());
                }
                break; //可选
                case ByItem :{
                    pricingType.setLable(x.getByItem().getLable());
                    pricingType.setPrice(x.getByItem().getPrice());
                }
                break; //可选
                case Fixed :{
                    pricingType.setPrice(x.getFixed().getPrice());
                }
                break; //可选
                case ByHour :{
                    pricingType.setPrice(x.getByHour().getPrice());
                    pricingType.setMax(x.getByHour().getMax());
                    pricingType.setMin(x.getByHour().getMin());
                };
                break; //可选
                case ByDay:{
                    pricingType.setPrice(x.getByHour().getPrice());
                    pricingType.setMax(x.getByHour().getMax());
                    pricingType.setMin(x.getByHour().getMin());
                };
                break; //可选
                default:

            }


            String seq = UUID.randomUUID().toString();
            pricingType.setStreamSeq(seq);
            return pricingType;


    }
}
