package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.AssetResp;
import com.lt.dom.OctResp.HistoryResp;
import com.lt.dom.config.LtConfig;
import com.lt.dom.credit.EnumActionType;
import com.lt.dom.credit.EnumHistoryType;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.repository.AssetRepository;
import com.lt.dom.repository.HistoryRepository;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HistoryServiceImpl {

    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private IdGenServiceImpl idGenService;




    public List<HistoryResp> list(Long id) {


        return historyRepository.findAllByBillId(id).stream().map(e->{
            HistoryResp historyResp = HistoryResp.from(e);
            return historyResp;
        }).collect(Collectors.toList());
    }


        public History create(long id, EnumActionType invoice_created, String 新建) {


            History asset = new History();
            asset.setType(EnumHistoryType.internal);

            asset.setBillId(id);
            asset.setDescription(新建);
            asset.setOperationType(invoice_created);
            asset = historyRepository.save(asset);

            return asset;
        }

}
