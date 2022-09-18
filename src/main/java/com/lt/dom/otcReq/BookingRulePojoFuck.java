package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumAvailabilityRangetype;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class BookingRulePojoFuck {

    //Period From/To

    @NotNull
    private EnumAvailabilityRangetype type;


    private List<Object> values;
    @NotNull
    private Boolean bookable ;

    @NotNull
    private Integer priority ;

    public EnumAvailabilityRangetype getType() {
        return type;
    }

    public void setType(EnumAvailabilityRangetype type) {
        this.type = type;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    public Boolean getBookable() {
        return bookable;
    }

    public void setBookable(Boolean bookable) {
        this.bookable = bookable;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
