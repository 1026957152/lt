package com.lt.dom.controllerOct;

import com.lt.dom.oct.Product;
import com.lt.dom.repository.CampaignRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.serviceOtc.ClainQuotaServiceImpl;
import com.lt.dom.serviceOtc.FulfillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")  // 将消费券分配配额
public class VoucherQuotaRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private FulfillServiceImpl vonchorService;
    @Autowired
    private ClainQuotaServiceImpl clainQuotaService;



    @GetMapping(value = "clain_quotas/{PRODUCT_ID}/availability", produces = "application/json")
    public List<Calendar> listAvailability(@PathVariable long PRODUCT_ID) {

        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isPresent()){
            try {
                return null;//availabilityService.listAvailability(validatorOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));


    }


/*
    @Operation(summary = "2、新建配额")
    @PostMapping(value = "/clain_quotas", produces = "application/json")
    public Quota createClainQuota(@RequestBody QuotaReq pojo) {


        Optional<Campaign> compaignOptional = campaignRepository.findById(pojo.getCompaign());
        if(compaignOptional.isPresent()){
            Quota componentRight = clainQuotaService.createQuota(compaignOptional.get(),pojo);

            return componentRight;
        }
        return null;//

    }
*/


}