package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ProductPojo;
import com.lt.dom.otcenum.EnumProductPricingTypeByPerson;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.repository.PricingTypeRepository;
import com.lt.dom.vo.NegotiatedPricingType;
import com.lt.dom.vo.ProductPriceRangeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PriceServiceImpl {
    @Autowired
    private PricingTypeRepository pricingTypeRepository;

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

    public List<PricingRate> getPriceType(Product finalProduct , List<ProductPojo.Price> prices) {

        List<PricingRate> priceTyps = prices.stream().map(x->{

            return getPriceType(finalProduct,x);
        }).collect(Collectors.toList());

        return priceTyps;
    }
    public Optional<PricingRate> getDefault_price(Product finalProduct ) {
        Map<Long, PricingRate> longPricingTypeMap = pricingTypeRepository.findByProductId(finalProduct.getId())
                .stream().collect(Collectors.toMap(e->e.getId(),e->e));

        Optional<PricingRate> pricingType_default = longPricingTypeMap.values().stream().findAny();


        return pricingType_default;
    }


    public ProductPriceRangeVo getPriceRange(Product finalProduct ) {

        List<PricingRate> longPricingRateMap = pricingTypeRepository.findByProductId(finalProduct.getId());




        PricingRate minByAge = longPricingRateMap.stream()
                .min(Comparator.comparing(PricingRate::getPrice))
                .orElseThrow(NoSuchElementException::new);
        PricingRate maxByAge = longPricingRateMap.stream()
                .max(Comparator.comparing(PricingRate::getPrice))
                .orElseThrow(NoSuchElementException::new);

        ProductPriceRangeVo pricingType_default = new ProductPriceRangeVo();

        ProductPriceRangeVo.ProductPriceVo max = new ProductPriceRangeVo.ProductPriceVo();

        max.setOriginal(maxByAge.getOriginal());
        max.setRetail(maxByAge.getOriginal());
        pricingType_default.setTo(max);

        ProductPriceRangeVo.ProductPriceVo min = new ProductPriceRangeVo.ProductPriceVo();
        min.setOriginal(minByAge.getOriginal());
        min.setRetail(minByAge.getOriginal());
        pricingType_default.setFrom(min);


        return pricingType_default;
    }


    public ProductPriceRangeVo getPriceRange(List<PricingRate> pricingRateList) {




        PricingRate minByAge = pricingRateList.stream()
                .min(Comparator.comparing(PricingRate::getPrice))
                .orElseThrow(NoSuchElementException::new);
        PricingRate maxByAge = pricingRateList.stream()
                .max(Comparator.comparing(PricingRate::getPrice))
                .orElseThrow(NoSuchElementException::new);

        ProductPriceRangeVo pricingType_default = new ProductPriceRangeVo();

        ProductPriceRangeVo.ProductPriceVo max = new ProductPriceRangeVo.ProductPriceVo();

        max.setOriginal(maxByAge.getOriginal());
        max.setRetail(maxByAge.getOriginal());
        pricingType_default.setTo(max);

        ProductPriceRangeVo.ProductPriceVo min = new ProductPriceRangeVo.ProductPriceVo();
        min.setOriginal(minByAge.getOriginal());
        min.setRetail(minByAge.getOriginal());
        pricingType_default.setFrom(min);


        return pricingType_default;
    }


    public PricingRate getPriceType(Product finalProduct , ProductPojo.Price x) {


        Assert.notNull(x.getType(), "type 不能为空");

            PricingRate pricingRate = new PricingRate();
            pricingRate.setProductId(finalProduct.getId());
            pricingRate.setType(x.getType());
            switch (x.getType()){
                case ByPerson :{
                    Assert.notNull(x.getByPersonType(), "byPersonType 不能为空");
                    Assert.notNull(x.getByPerson().getPrice(), "price 不能为空");

                    pricingRate.setPrice(x.getByPerson().getPrice());
                    pricingRate.setBy(x.getByPersonType());
                    if(x.getByPersonType().equals(EnumProductPricingTypeByPerson.GROUP)){
                        Assert.notNull(x.getByPerson().getGroupType(), "groupType 不能为空");

                        pricingRate.setGroup_type(x.getByPerson().getGroupType());
                        pricingRate.setMax(x.getByPerson().getMax());
                        pricingRate.setMin(x.getByPerson().getMin());
                    }

                }
                break; //可选
                case ByItem :{
                    pricingRate.setLable(x.getByItem().getLable());
                    pricingRate.setPrice(x.getByItem().getPrice());
                }
                break; //可选
                case Fixed :{
                    pricingRate.setPrice(x.getFixed().getPrice());
                }
                break; //可选
                case ByHour :{
                    pricingRate.setPrice(x.getByHour().getPrice());
                    pricingRate.setMax(x.getByHour().getMax());
                    pricingRate.setMin(x.getByHour().getMin());
                };
                break; //可选
                case ByDay:{

                    pricingRate.setPrice(x.getByHour().getPrice());
                    pricingRate.setMax(x.getByHour().getMax());
                    pricingRate.setMin(x.getByHour().getMin());
                };
                break; //可选
                default:

            }
        pricingRate.setRetail(pricingRate.getPrice());
        pricingRate.setNet(pricingRate.getPrice());
        pricingRate.setOriginal(pricingRate.getPrice());
        pricingRate.setNick_name(x.getName());
            String seq = UUID.randomUUID().toString();
            pricingRate.setStreamSeq(seq);



        pricingRate.setRestriction_MaxAge(x.getRestriction().getMaxAge());
        pricingRate.setRestriction_MinAge(x.getRestriction().getMinAge());
/*        Assert.notNull(x.getByPerson().getMax(), "max 不能为空");
        Assert.notNull(x.getByPerson().getMin(), "min 不能为空");*/

/*        Assert.notNull(x.getByPerson().getMax(), "max 不能为空");
        Assert.notNull(x.getByPerson().getMin(), "min 不能为空");
        */
        pricingRate.setRestriction_MinQuantity(x.getRestriction().getMinQuantity());
        pricingRate.setRestriction_MaxQuantity(x.getRestriction().getMaxQuantity());
        pricingRate.setRestriction_IdRequired(x.getRestriction().getIdRequired());
        pricingRate.setRestriction_PaxCount(x.getRestriction().getPaxCount());

            return pricingRate;



    }

    public PricingRate updatePriceType(Product finalProduct , PricingRate pricingRate, ProductPojo.Price x) {


        Assert.notNull(x.getType(), "type 不能为空");

      //  PricingType pricingType = new PricingType();
        pricingRate.setProductId(finalProduct.getId());
        pricingRate.setType(x.getType());
        pricingRate.setActive(x.getActive());

        switch (x.getType()){
            case ByPerson :{
                Assert.notNull(x.getByPersonType(), "byPersonType 不能为空");
                Assert.notNull(x.getByPerson().getPrice(), "price 不能为空");

                pricingRate.setPrice(x.getByPerson().getPrice());
                pricingRate.setBy(x.getByPersonType());
                if(x.getByPersonType().equals(EnumProductPricingTypeByPerson.GROUP)){
                    Assert.notNull(x.getByPerson().getGroupType(), "groupType 不能为空");

                    pricingRate.setGroup_type(x.getByPerson().getGroupType());
                    pricingRate.setMax(x.getByPerson().getMax());
                    pricingRate.setMin(x.getByPerson().getMin());
                }

            }
            break; //可选
            case ByItem :{
                pricingRate.setLable(x.getByItem().getLable());
                pricingRate.setPrice(x.getByItem().getPrice());
            }
            break; //可选
            case Fixed :{
                pricingRate.setPrice(x.getFixed().getPrice());
            }
            break; //可选
            case ByHour :{
                pricingRate.setPrice(x.getByHour().getPrice());
                pricingRate.setMax(x.getByHour().getMax());
                pricingRate.setMin(x.getByHour().getMin());
            };
            break; //可选
            case ByDay:{

                pricingRate.setPrice(x.getByHour().getPrice());
                pricingRate.setMax(x.getByHour().getMax());
                pricingRate.setMin(x.getByHour().getMin());
            };
            break; //可选
            default:

        }
        pricingRate.setRetail(x.getByPerson().getRetail());
        pricingRate.setNet(x.getByPerson().getNet());
        pricingRate.setOriginal(x.getByPerson().getOriginal());
        pricingRate.setNick_name(x.getName());

        String seq = UUID.randomUUID().toString();
        pricingRate.setStreamSeq(seq);



        pricingRate.setRestriction_MaxAge(x.getRestriction().getMaxAge());
        pricingRate.setRestriction_MinAge(x.getRestriction().getMinAge());
/*        Assert.notNull(x.getByPerson().getMax(), "max 不能为空");
        Assert.notNull(x.getByPerson().getMin(), "min 不能为空");*/

/*        Assert.notNull(x.getByPerson().getMax(), "max 不能为空");
        Assert.notNull(x.getByPerson().getMin(), "min 不能为空");
        */
        pricingRate.setRestriction_MinQuantity(x.getRestriction().getMinQuantity());
        pricingRate.setRestriction_MaxQuantity(x.getRestriction().getMaxQuantity());
        pricingRate.setRestriction_IdRequired(x.getRestriction().getIdRequired());
        pricingRate.setRestriction_PaxCount(x.getRestriction().getPaxCount());

        return pricingRate;



    }

    public NegotiatedPricingType fill(PricingRate e, Optional<PartnerShareRatePlan> partnerList) {


        NegotiatedPricingType negotiatedPricingType = new NegotiatedPricingType();
        negotiatedPricingType.setPrice(e.getPrice());
        negotiatedPricingType.setRetail(e.getRetail());

        return negotiatedPricingType;
    }

    public List<PricingRate> find(Product product) {
        return pricingTypeRepository.findByProductId(product.getId());
    }
}
