package com.lt.dom.controllerOct;


import com.lt.dom.domain.TouristAttraction;

import com.lt.dom.otcReq.TouristAttractionePojo;
import com.lt.dom.otcReq.TouristAttractionePojoList;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//@RestController
//@RequestMapping("/dddddoct")
public class TouristAttractionRestController {
    
    @GetMapping(value ="/{SUPPLER_ID}/touristAttractions/{Tourist_Attraction_ID}", produces = "application/json")
    public TouristAttraction getBook(@PathVariable int SUPPLER_ID, @PathVariable int Tourist_Attraction_ID) {
        return null;
    }
    @GetMapping(value ="/{SUPPLER_ID}/touristAttractions", produces = "application/json")
    public TouristAttraction listTouristAttractione(@PathVariable int APP_ID, TouristAttractionePojoList TouristAttractionePojoList) {
        return null;
    }



    @PostMapping(value ="/{SUPPLER_ID}/touristAttractions", produces = "application/json")
    public TouristAttraction createTouristAttractione(@PathVariable int APP_ID, TouristAttractionePojo pojo) {



        return null;
    }


    @PutMapping(value = "/{SUPPLER_ID}/touristAttractions/{Tourist_Attraction_ID}", produces = "application/json")
    public TouristAttraction updateTouristAttraction(@PathVariable int SUPPLER_ID, @PathVariable int Tourist_Attraction_ID, Map metadata) {
        return null;//
    }

    @DeleteMapping(value = "/{SUPPLER_ID}/touristAttractions/{Tourist_Attraction_ID}", produces = "application/json")
    public TouristAttraction deleteTouristAttractione(@PathVariable int id) {

        return null;
    }




    // 添加 景观
    @PostMapping(value = "/{SUPPLER_ID}/touristAttractions/{Tourist_Attraction_ID}/attractions", produces = "application/json")
    public TouristAttraction addAttractione(@PathVariable int id) {
        return null;
    }


}