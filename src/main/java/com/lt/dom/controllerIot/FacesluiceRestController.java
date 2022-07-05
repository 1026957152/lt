package com.lt.dom.controllerIot;


import com.lt.dom.domainIot.Facesluice;
import com.lt.dom.otcReq.FacesluicePojo;
import com.lt.dom.otcReq.FacesluicePojoList;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//@RestController
//@RequestMapping("Facesluice-rest")
public class FacesluiceRestController {
    
    @GetMapping(value = "/{Facesluice_ID}", produces = "application/json")
    public Facesluice getBook(@PathVariable int APP_ID,@PathVariable int COUPON_TMPL_ID) {
        return null;
    }
    @GetMapping(value = "/{APP_ID}/{COUPON_TMPL_ID}", produces = "application/json")
    public Facesluice listFacesluice(@PathVariable int APP_ID, FacesluicePojoList FacesluicePojoList) {
        return null;
    }



    @PostMapping(value = "/coupon_templates", produces = "application/json")
    public Facesluice createFacesluice(FacesluicePojo pojo) {
        return null;
    }


    @PutMapping(value = "/{APP_ID}/coupon_templates/{COUPON_TMPL_ID}", produces = "application/json")
    public Facesluice updateBook(@PathVariable int APP_ID, Map metadata) {
        return null;
    }

    @DeleteMapping(value = "/coupon_templates", produces = "application/json")
    public Facesluice createFacesluice(@PathVariable int id) {
        return null;
    }

}