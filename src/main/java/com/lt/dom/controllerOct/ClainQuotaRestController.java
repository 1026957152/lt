package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.ClainQuotaStatisticsResp;
import com.lt.dom.oct.Campaign;
import com.lt.dom.oct.ClainQuota;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.Quota;
import com.lt.dom.otcReq.ClainQuotaReq;
import com.lt.dom.otcenum.EnumClainQuotaType;
import com.lt.dom.repository.CampaignRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.ClainQuotaServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/oct")
public class ClainQuotaRestController {



    @Autowired
    private ClainQuotaServiceImpl clainQuotaService;
    @Autowired
    private CampaignRepository campaignRepository;




    @GetMapping(value = "/campaigns/{CAMPAIGN_ID}/clain_quotas__form", produces = "application/json")
    public ResponseEntity<Map> getQuotaStatistics(@PathVariable long CAMPAIGN_ID) {

        System.out.println("----"+CAMPAIGN_ID);
        Optional<Campaign> optional = campaignRepository.findById(CAMPAIGN_ID);


        if(optional.isPresent()){
            System.out.println("----"+optional.get());

            Map map =  Map.of("EnumClainQuotaType",Arrays.stream(EnumClainQuotaType.values()).sequential().map(x->x.name()).collect(Collectors.toList()));
            return ResponseEntity.ok(map);

        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));

    }

    @PostMapping(value = "/campaigns/{CAMPAIGN_ID}/clain_quotas", produces = "application/json")
    public ResponseEntity<ClainQuota> createQuota(@PathVariable long CAMPAIGN_ID, @RequestBody ClainQuotaReq clainQuotaReq) {


        System.out.println("----"+CAMPAIGN_ID);
        Optional<Campaign> optional = campaignRepository.findById(CAMPAIGN_ID);


        if(optional.isPresent()){
            System.out.println("----"+optional.get());

            ClainQuota campaignResps =  clainQuotaService.createQuota(optional.get(),clainQuotaReq);
            return ResponseEntity.ok(campaignResps);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));


    }
}