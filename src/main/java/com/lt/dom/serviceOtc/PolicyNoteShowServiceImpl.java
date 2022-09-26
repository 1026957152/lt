package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.AssetResp;
import com.lt.dom.config.LtConfig;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumNoteShowWhereAndWhen;
import com.lt.dom.repository.AssetRepository;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PolicyNoteShowServiceImpl {

    @Autowired
    private LtConfig ltConfig;

    @Autowired
    private AssetRepository assetRepository;



    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    public Pair<Boolean,String> getQr(EnumNoteShowWhereAndWhen enumNoteShowWhereAndWhen) {

        if(enumNoteShowWhereAndWhen.equals(EnumNoteShowWhereAndWhen.home_page)){
            return Pair.with(getRandomNumberInRange(1,10)%10 == 2,"需要24小时核算，容许入园");
        }

        return Pair.with(false,"需要24小时核算，容许入园");

    }

}
