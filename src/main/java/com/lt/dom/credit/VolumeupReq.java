package com.lt.dom.credit;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lt.dom.oct.ValueList;
import com.lt.dom.oct.ValueListItem;
import com.lt.dom.otcenum.EnumValueListItemType;
import com.lt.dom.otcenum.EnumValueListType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;


public class VolumeupReq {


    @NotNull
    private Integer volume_up_授权额度;

    public Integer getVolume_up_授权额度() {
        return volume_up_授权额度;
    }

    public void setVolume_up_授权额度(Integer volume_up_授权额度) {
        this.volume_up_授权额度 = volume_up_授权额度;
    }
}
