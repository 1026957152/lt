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

    public List<PricingType> getPriceType(Product finalProduct ,List<ProductPojo.Price> prices) {

        List<PricingType> priceTyps = prices.stream().map(x->{

            return getPriceType(finalProduct,x);
        }).collect(Collectors.toList());

        return priceTyps;
    }
    public Optional<PricingType> getDefault_price(Product finalProduct ) {
        Map<Long, PricingType> longPricingTypeMap = pricingTypeRepository.findByProductId(finalProduct.getId())
                .stream().collect(Collectors.toMap(e->e.getId(),e->e));

        Optional<PricingType> pricingType_default = longPricingTypeMap.values().stream().findAny();


        return pricingType_default;
    }


    public ProductPriceRangeVo getPriceRange(Product finalProduct ) {

        List<PricingType> longPricingTypeMap = pricingTypeRepository.findByProductId(finalProduct.getId());




        PricingType minByAge = longPricingTypeMap.stream()
                .min(Comparator.comparing(PricingType::getPrice))
                .orElseThrow(NoSuchElementException::new);
        PricingType maxByAge = longPricingTypeMap.stream()
                .max(Comparator.comparing(PricingType::getPrice))
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


    public ProductPriceRangeVo getPriceRange(List<PricingType> pricingTypeList ) {




        PricingType minByAge = pricingTypeList.stream()
                .min(Comparator.comparing(PricingType::getPrice))
                .orElseThrow(NoSuchElementException::new);
        PricingType maxByAge = pricingTypeList.stream()
                .max(Comparator.comparing(PricingType::getPrice))
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


    public PricingType getPriceType(Product finalProduct ,ProductPojo.Price x) {


        Assert.notNull(x.getType(), "type 不能为空");

            PricingType pricingType = new PricingType();
            pricingType.setProductId(finalProduct.getId());
            pricingType.setType(x.getType());
            switch (x.getType()){
                case ByPerson :{
                    Assert.notNull(x.getByPersonType(), "byPersonType 不能为空");
                    Assert.notNull(x.getByPerson().getPrice(), "price 不能为空");

                    pricingType.setPrice(x.getByPerson().getPrice());
                    pricingType.setBy(x.getByPersonType());
                    if(x.getByPersonType().equals(EnumProductPricingTypeByPerson.GROUP)){
                        Assert.notNull(x.getByPerson().getGroupType(), "groupType 不能为空");

                        pricingType.setGroup_type(x.getByPerson().getGroupType());
                        pricingType.setMax(x.getByPerson().getMax());
                        pricingType.setMin(x.getByPerson().getMin());
                    }

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
        pricingType.setRetail(pricingType.getPrice());
        pricingType.setNet(pricingType.getPrice());
        pricingType.setOriginal(pricingType.getPrice());
        pricingType.setNick_name(x.getName());
            String seq = UUID.randomUUID().toString();
            pricingType.setStreamSeq(seq);



        pricingType.setRestriction_MaxAge(x.getRestriction().getMaxAge());
        pricingType.setRestriction_MinAge(x.getRestriction().getMinAge());
/*        Assert.notNull(x.getByPerson().getMax(), "max 不能为空");
        Assert.notNull(x.getByPerson().getMin(), "min 不能为空");*/

/*        Assert.notNull(x.getByPerson().getMax(), "max 不能为空");
        Assert.notNull(x.getByPerson().getMin(), "min 不能为空");
        */
        pricingType.setRestriction_MinQuantity(x.getRestriction().getMinQuantity());
        pricingType.setRestriction_MaxQuantity(x.getRestriction().getMaxQuantity());
        pricingType.setRestriction_IdRequired(x.getRestriction().getIdRequired());
        pricingType.setRestriction_PaxCount(x.getRestriction().getPaxCount());

            return pricingType;



    }

    public PricingType updatePriceType(Product finalProduct ,PricingType pricingType,ProductPojo.Price x) {


        Assert.notNull(x.getType(), "type 不能为空");

      //  PricingType pricingType = new PricingType();
        pricingType.setProductId(finalProduct.getId());
        pricingType.setType(x.getType());
        pricingType.setActive(x.getActive());

        switch (x.getType()){
            case ByPerson :{
                Assert.notNull(x.getByPersonType(), "byPersonType 不能为空");
                Assert.notNull(x.getByPerson().getPrice(), "price 不能为空");

                pricingType.setPrice(x.getByPerson().getPrice());
                pricingType.setBy(x.getByPersonType());
                if(x.getByPersonType().equals(EnumProductPricingTypeByPerson.GROUP)){
                    Assert.notNull(x.getByPerson().getGroupType(), "groupType 不能为空");

                    pricingType.setGroup_type(x.getByPerson().getGroupType());
                    pricingType.setMax(x.getByPerson().getMax());
                    pricingType.setMin(x.getByPerson().getMin());
                }

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
        pricingType.setRetail(x.getByPerson().getRetail());
        pricingType.setNet(x.getByPerson().getNet());
        pricingType.setOriginal(x.getByPerson().getOriginal());
        pricingType.setNick_name(x.getName());

        String seq = UUID.randomUUID().toString();
        pricingType.setStreamSeq(seq);



        pricingType.setRestriction_MaxAge(x.getRestriction().getMaxAge());
        pricingType.setRestriction_MinAge(x.getRestriction().getMinAge());
/*        Assert.notNull(x.getByPerson().getMax(), "max 不能为空");
        Assert.notNull(x.getByPerson().getMin(), "min 不能为空");*/

/*        Assert.notNull(x.getByPerson().getMax(), "max 不能为空");
        Assert.notNull(x.getByPerson().getMin(), "min 不能为空");
        */
        pricingType.setRestriction_MinQuantity(x.getRestriction().getMinQuantity());
        pricingType.setRestriction_MaxQuantity(x.getRestriction().getMaxQuantity());
        pricingType.setRestriction_IdRequired(x.getRestriction().getIdRequired());
        pricingType.setRestriction_PaxCount(x.getRestriction().getPaxCount());

        return pricingType;



    }

    public NegotiatedPricingType fill(PricingType e, Optional<PartnerShareRatePlan> partnerList) {


        NegotiatedPricingType negotiatedPricingType = new NegotiatedPricingType();
        negotiatedPricingType.setPrice(e.getPrice());
        negotiatedPricingType.setRetail(e.getRetail());

        return negotiatedPricingType;
    }

    public List<PricingType> find(Product product) {
        return pricingTypeRepository.findByProductId(product.getId());
    }
}
