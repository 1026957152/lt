package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.PublicationResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.DataNotFound;
import com.lt.dom.oct.Campaign;
import com.lt.dom.oct.Product;
import com.lt.dom.otcReq.PublicationPojo;
import com.lt.dom.otcReq.PublicationSearchPojo;
import com.lt.dom.repository.CampaignRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.PublicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class PublicationRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private PublicationServiceImpl publicationService;
    @Autowired
    private AvailabilityServiceImpl availabilityService;



    @GetMapping(value = "/publications", produces = "application/json")
    public List<PublicationResp> listPublicationResp(@RequestParam PublicationSearchPojo pojo) {

        Optional<Product> validatorOptional = productRepository.findById(1l);
        if(validatorOptional.isPresent()){
            try {
                return publicationService.listPublication(pojo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));

    }




    @PostMapping(value = "/campaigns/{CAMPAIGN_ID}/publications", produces = "application/json")
    public PublicationResp createPublication(@PathVariable long CAMPAIGN_ID, @RequestBody PublicationPojo publicationPojo) {

        //publicationPojo.
        Optional<Campaign> validatorOptional = campaignRepository.findById(CAMPAIGN_ID);
        if(validatorOptional.isPresent()){
            try {
                return publicationService.CreatePublication(validatorOptional.get(),publicationPojo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new BookNotFoundException(CAMPAIGN_ID,Campaign.class.getSimpleName());

    }

}