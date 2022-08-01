package com.lt.dom.controllerOct;

//http://developers.amctheatres.com/SeatingLayouts

import com.lt.dom.oct.Product;
import com.lt.dom.oct.Seat;
import com.lt.dom.oct.SeatingLayout;
import com.lt.dom.oct.Theatre;
import com.lt.dom.otcReq.SeatPojo;
import com.lt.dom.otcReq.SeatingLayoutPojo;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.SeatingLayoutServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class SeatingLayoutRestController {


    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private SeatingLayoutRepository seatingLayoutRepository;
    @Autowired
    private SeatingLayoutServiceImpl seatingLayoutService;
    @Autowired
    private AvailabilityServiceImpl availabilityService;



    @GetMapping(value = "/theatre/{theatre_number}/seating-layouts", produces = "application/json")

    public SeatingLayout listAvailability(@PathVariable long theatre_number) {

        Optional<Theatre> validatorOptional = theatreRepository.findById(theatre_number);
        if(validatorOptional.isPresent()){
            try {
                return seatingLayoutService.get(validatorOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));



    }


    @PostMapping(value = "/theatre/{theatre_number}/seating-layouts", produces = "application/json")
    public SeatingLayout createSeatingLayout(@PathVariable long theatre_number, SeatingLayoutPojo pojo) {

        Optional<Theatre> validatorOptional = theatreRepository.findById(theatre_number);
        if(validatorOptional.isPresent()){
            try {
                return seatingLayoutService.createSeatingLayout(validatorOptional.get(),pojo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));

    }


    @PostMapping(value = "/theatre/{theatre_number}/seating-layouts/{seating_layout_id}/seats", produces = "application/json")
    public List<SeatPojo> addSeatForSeatingLayout(@PathVariable long theatre_number,@PathVariable long seating_layout_id, @RequestBody List<SeatPojo> pojos) {

        Optional<SeatingLayout> validatorOptional = seatingLayoutRepository.findById(seating_layout_id);
        if(validatorOptional.isPresent()){
            try {
                return seatingLayoutService.addSeatForSeatingLayout(validatorOptional.get(),pojos);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));

    }
}