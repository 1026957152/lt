package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.AssetResp;
import com.lt.dom.OctResp.ComponentResp;
import com.lt.dom.OctResp.PassengerResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.PassengerReq;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AssetServiceImpl;
import com.lt.dom.serviceOtc.IdGenServiceImpl;
import com.lt.dom.util.ZxingBarcodeGenerator;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class PassengerRestController {



    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping(value = "/users/{USER_ID}/passengers", produces = "application/json")
    public ResponseEntity<PassengerResp> createPassenger(@PathVariable long USER_ID, @RequestBody PassengerReq passengerReq) {
        Optional<User> optionalUser = userRepository.findById(USER_ID);

        if(optionalUser.isEmpty()){
            throw new BookNotFoundException(USER_ID,User.class.getSimpleName());

        }
        User user = optionalUser.get();

        Passenger passenger = new Passenger();
        passenger.setName(passengerReq.getName());
        passenger.setCode(UUID.randomUUID().toString());
        passenger.setIdNo(passengerReq.getId_card());
        passenger.setTel_home(passengerReq.getPhone());
        passenger.setUser(user.getId());
        passenger = passengerRepository.save(passenger);
        PassengerResp passengerResp = PassengerResp.of(passenger);

                return ResponseEntity.ok(passengerResp);



    }



    @PutMapping(value = "/passengers/{PASSENGER_ID}", produces = "application/json")
    public ResponseEntity<PassengerResp> edit(@PathVariable long PASSENGER_ID, @RequestBody PassengerReq passengerReq) {
        Optional<Passenger> optionalUser = passengerRepository.findById(PASSENGER_ID);

        if(optionalUser.isEmpty()){
            throw new BookNotFoundException(PASSENGER_ID,User.class.getSimpleName());

        }
        Passenger passenger = optionalUser.get();


        passenger.setName(passengerReq.getName());
        passenger.setIdNo(passengerReq.getId_card());
        passenger.setTel_home(passengerReq.getPhone());
        passenger = passengerRepository.save(passenger);


        return ResponseEntity.ok(PassengerResp.of(passenger));



    }

    @GetMapping(value = "/passengers/{PASSENGER_ID}", produces = "application/json")
    public ResponseEntity<PassengerResp> get(@PathVariable long PASSENGER_ID) {
        Optional<Passenger> optionalUser = passengerRepository.findById(PASSENGER_ID);

        if(optionalUser.isEmpty()){
            throw new BookNotFoundException(PASSENGER_ID,User.class.getSimpleName());

        }
        Passenger passenger = optionalUser.get();

        PassengerResp passengerResp = PassengerResp.of(passenger);


        return ResponseEntity.ok(passengerResp);



    }



        @GetMapping(value = "/users/{USER_ID}/passengers", produces = "application/json")
    public PagedModel getPassengerList(@PathVariable long USER_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<PassengerResp>> assembler) {

            Optional<User> optionalUser = userRepository.findById(USER_ID);

            if(optionalUser.isEmpty()){
                throw new BookNotFoundException(USER_ID,User.class.getSimpleName());

            }
            User user = optionalUser.get();



        Page<Passenger> passengers = passengerRepository.findAllByUser(user.getId(),pageable);



        return assembler.toModel(passengers.map(e->{


            PassengerResp passengerResp = PassengerResp.of(e);
            EntityModel entityModel = EntityModel.of(passengerResp);
            entityModel.add(linkTo(methodOn(PassengerRestController.class).edit(e.getId(),null)).withRel("edit"));
            entityModel.add(linkTo(methodOn(PassengerRestController.class).get(e.getId())).withSelfRel());

            return entityModel;

        }));



    }

}