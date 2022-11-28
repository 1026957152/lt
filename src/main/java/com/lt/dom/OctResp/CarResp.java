package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.Base;
import com.lt.dom.oct.Car;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarResp  {

    @JsonProperty("carId")
    private String carId;
    @JsonProperty("operatorId")
    private String operatorId;
    @JsonProperty("operatorName")
    private String operatorName;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("endDate")
    private String endDate;


    @Transient
    @JsonProperty("costs")
    private CostsDTO costs;
    @JsonProperty("expireAt")
    private String expireAt;

    @Transient
    @JsonProperty("agencyReturn")
    private AgencyReturnDTO agencyReturn;
    @JsonProperty("agencyLogoUrl")
    private String agencyLogoUrl;
    @JsonProperty("agencyReturnLogoUrl")
    private String agencyReturnLogoUrl;
    @JsonProperty("name")
    private String name;
    @JsonProperty("carGroup")
    private String carGroup;
    @JsonProperty("pictureUrl")
    private String pictureUrl;
    @JsonProperty("seatNumber")
    private Integer seatNumber;

    @Transient

    @JsonProperty("luggage")
    private LuggageDTO luggage;
    @JsonProperty("co2Emission")
    private Object co2Emission;
    @JsonProperty("category")
    private String category;
    @JsonProperty("eliteCategory")
    private Boolean eliteCategory;
    @JsonProperty("type")
    private String type;
    @JsonProperty("gearbox")
    private String gearbox;
    @JsonProperty("fuel")
    private String fuel;
    @JsonProperty("airConditioning")
    private Boolean airConditioning;
    @JsonProperty("acriss")
    private String acriss;
    @JsonProperty("durationInDays")
    private Integer durationInDays;
    @JsonProperty("termsAndConditionsUrl")
    private String termsAndConditionsUrl;



    @Transient
    @JsonProperty("included")
    private List<String> included;

    @Transient
    @JsonProperty("excluded")
    private List<String> excluded;
    @JsonProperty("unlimitedDistance")
    private Boolean unlimitedDistance;


    @Transient
    @JsonProperty("noShowFee")
    private NoShowFeeDTO noShowFee;


    @Transient
    @JsonProperty("includedDistance")
    private IncludedDistanceDTO includedDistance;

    @Transient
    @JsonProperty("additionalDistancePrice")
    private AdditionalDistancePriceDTO additionalDistancePrice;

    @Transient
    @JsonProperty("deductible")
    private DeductibleDTO deductible;
    private PhotoResp photo;
    private String brand;
    private PhotoResp brand_logo;


    private List carFeatures;
    private List tags;

    public static CarResp of(Car e) {
        CarResp carResp = new CarResp();
        carResp.setName(e.getName());
        carResp.setSeatNumber(e.getSeatNumber());
        carResp.setAirConditioning(e.getAirConditioning());



        carResp.setBrand(e.getBrand());

        CostsDTO costsDTO = new CostsDTO();
        costsDTO.setTotal(20);
        carResp.setCosts(costsDTO);

        return carResp;
    }

    public void setPhoto(PhotoResp photo) {
        this.photo = photo;
    }

    public PhotoResp getPhoto() {
        return photo;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand_logo(PhotoResp brand_logo) {
        this.brand_logo = brand_logo;
    }

    public PhotoResp getBrand_logo() {
        return brand_logo;
    }

    public <R> void setCarFeatures(List carFeatures) {
        this.carFeatures = carFeatures;
    }

    public List getCarFeatures() {
        return carFeatures;
    }

    public <R> void setTags(List tags) {
        this.tags = tags;
    }

    public List getTags() {
        return tags;
    }


    public static class CostsDTO {
        @JsonProperty("total")
        private Integer total;
        @JsonProperty("currency")
        private String currency;
        @JsonProperty("symbol")
        private String symbol;

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }
    }


    public static class AgencyReturnDTO {

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @JsonProperty("agencyId")
        private String agencyId;
        @JsonProperty("address")
        private AddressDTO address;
        @JsonProperty("location")
        private LocationDTO location;
        @JsonProperty("name")
        private String name;
        @JsonProperty("openingPeriods")
        private OpeningPeriodsDTO openingPeriods;
        @JsonProperty("renter")
        private RenterDTO renter;
        @JsonProperty("timezone")
        private String timezone;

        public RenterDTO getRenter() {
            return renter;
        }

        public void setRenter(RenterDTO renter) {
            this.renter = renter;
        }

        @NoArgsConstructor
        @Data
        public static class AddressDTO {
            @JsonProperty("label")
            private String label;
            @JsonProperty("street")
            private String street;
            @JsonProperty("city")
            private String city;
            @JsonProperty("postcode")
            private String postcode;
            @JsonProperty("countryCode")
            private String countryCode;
        }

        @NoArgsConstructor
        @Data
        public static class LocationDTO {
            @JsonProperty("lat")
            private Double lat;
            @JsonProperty("lng")
            private Double lng;
        }

        @NoArgsConstructor
        @Data
        public static class OpeningPeriodsDTO {
            @JsonProperty("monday")
            private List<MondayDTO> monday;
            @JsonProperty("tuesday")
            private List<TuesdayDTO> tuesday;
            @JsonProperty("wednesday")
            private List<WednesdayDTO> wednesday;
            @JsonProperty("thursday")
            private List<ThursdayDTO> thursday;
            @JsonProperty("friday")
            private List<FridayDTO> friday;
            @JsonProperty("saturday")
            private List<SaturdayDTO> saturday;
            @JsonProperty("sunday")
            private List<SundayDTO> sunday;

            @NoArgsConstructor
            @Data
            public static class MondayDTO {
                @JsonProperty("start")
                private String start;
                @JsonProperty("end")
                private String end;
            }

            @NoArgsConstructor
            @Data
            public static class TuesdayDTO {
                @JsonProperty("start")
                private String start;
                @JsonProperty("end")
                private String end;
            }

            @NoArgsConstructor
            @Data
            public static class WednesdayDTO {
                @JsonProperty("start")
                private String start;
                @JsonProperty("end")
                private String end;
            }

            @NoArgsConstructor
            @Data
            public static class ThursdayDTO {
                @JsonProperty("start")
                private String start;
                @JsonProperty("end")
                private String end;
            }

            @NoArgsConstructor
            @Data
            public static class FridayDTO {
                @JsonProperty("start")
                private String start;
                @JsonProperty("end")
                private String end;
            }

            @NoArgsConstructor
            @Data
            public static class SaturdayDTO {
                @JsonProperty("start")
                private String start;
                @JsonProperty("end")
                private String end;
            }

            @NoArgsConstructor
            @Data
            public static class SundayDTO {
                @JsonProperty("start")
                private String start;
                @JsonProperty("end")
                private String end;
            }
        }


        public static class RenterDTO {



            @JsonProperty("id")
            private String idX;
            @JsonProperty("name")
            private String name;

            public String getIdX() {
                return idX;
            }

            public void setIdX(String idX) {
                this.idX = idX;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    @NoArgsConstructor
    @Data
    public static class LuggageDTO {
        @JsonProperty("big")
        private Integer big;
        @JsonProperty("small")
        private Integer small;
    }

    @NoArgsConstructor
    @Data
    public static class NoShowFeeDTO {
        @JsonProperty("total")
        private Integer total;
        @JsonProperty("currency")
        private String currency;
        @JsonProperty("symbol")
        private String symbol;
    }

    @NoArgsConstructor
    @Data
    public static class IncludedDistanceDTO {
        @JsonProperty("amount")
        private Integer amount;
        @JsonProperty("unit")
        private String unit;
    }

    @NoArgsConstructor
    @Data
    public static class AdditionalDistancePriceDTO {
        @JsonProperty("costs")
        private CostsDTO costs;
        @JsonProperty("unit")
        private String unit;

        @NoArgsConstructor
        @Data
        public static class CostsDTO {
            @JsonProperty("total")
            private Integer total;
            @JsonProperty("currency")
            private String currency;
            @JsonProperty("symbol")
            private String symbol;
        }
    }

    @NoArgsConstructor
    @Data
    public static class DeductibleDTO {
        @JsonProperty("thiefDeductible")
        private ThiefDeductibleDTO thiefDeductible;
        @JsonProperty("damageDeductible")
        private DamageDeductibleDTO damageDeductible;

        @NoArgsConstructor
        @Data
        public static class ThiefDeductibleDTO {
            @JsonProperty("total")
            private Integer total;
            @JsonProperty("currency")
            private String currency;
            @JsonProperty("symbol")
            private String symbol;
        }

        @NoArgsConstructor
        @Data
        public static class DamageDeductibleDTO {
        }
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public CostsDTO getCosts() {
        return costs;
    }

    public void setCosts(CostsDTO costs) {
        this.costs = costs;
    }

    public String getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(String expireAt) {
        this.expireAt = expireAt;
    }

    public AgencyReturnDTO getAgencyReturn() {
        return agencyReturn;
    }

    public void setAgencyReturn(AgencyReturnDTO agencyReturn) {
        this.agencyReturn = agencyReturn;
    }

    public String getAgencyLogoUrl() {
        return agencyLogoUrl;
    }

    public void setAgencyLogoUrl(String agencyLogoUrl) {
        this.agencyLogoUrl = agencyLogoUrl;
    }

    public String getAgencyReturnLogoUrl() {
        return agencyReturnLogoUrl;
    }

    public void setAgencyReturnLogoUrl(String agencyReturnLogoUrl) {
        this.agencyReturnLogoUrl = agencyReturnLogoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarGroup() {
        return carGroup;
    }

    public void setCarGroup(String carGroup) {
        this.carGroup = carGroup;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public LuggageDTO getLuggage() {
        return luggage;
    }

    public void setLuggage(LuggageDTO luggage) {
        this.luggage = luggage;
    }

    public Object getCo2Emission() {
        return co2Emission;
    }

    public void setCo2Emission(Object co2Emission) {
        this.co2Emission = co2Emission;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getEliteCategory() {
        return eliteCategory;
    }

    public void setEliteCategory(Boolean eliteCategory) {
        this.eliteCategory = eliteCategory;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public Boolean getAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(Boolean airConditioning) {
        this.airConditioning = airConditioning;
    }

    public String getAcriss() {
        return acriss;
    }

    public void setAcriss(String acriss) {
        this.acriss = acriss;
    }

    public Integer getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(Integer durationInDays) {
        this.durationInDays = durationInDays;
    }

    public String getTermsAndConditionsUrl() {
        return termsAndConditionsUrl;
    }

    public void setTermsAndConditionsUrl(String termsAndConditionsUrl) {
        this.termsAndConditionsUrl = termsAndConditionsUrl;
    }

    public List<String> getIncluded() {
        return included;
    }

    public void setIncluded(List<String> included) {
        this.included = included;
    }

    public List<String> getExcluded() {
        return excluded;
    }

    public void setExcluded(List<String> excluded) {
        this.excluded = excluded;
    }

    public Boolean getUnlimitedDistance() {
        return unlimitedDistance;
    }

    public void setUnlimitedDistance(Boolean unlimitedDistance) {
        this.unlimitedDistance = unlimitedDistance;
    }

    public NoShowFeeDTO getNoShowFee() {
        return noShowFee;
    }

    public void setNoShowFee(NoShowFeeDTO noShowFee) {
        this.noShowFee = noShowFee;
    }

    public IncludedDistanceDTO getIncludedDistance() {
        return includedDistance;
    }

    public void setIncludedDistance(IncludedDistanceDTO includedDistance) {
        this.includedDistance = includedDistance;
    }

    public AdditionalDistancePriceDTO getAdditionalDistancePrice() {
        return additionalDistancePrice;
    }

    public void setAdditionalDistancePrice(AdditionalDistancePriceDTO additionalDistancePrice) {
        this.additionalDistancePrice = additionalDistancePrice;
    }

    public DeductibleDTO getDeductible() {
        return deductible;
    }

    public void setDeductible(DeductibleDTO deductible) {
        this.deductible = deductible;
    }
}
