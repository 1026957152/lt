package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.PassengerResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.PassengerReq;
import com.lt.dom.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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


    @GetMapping(value = "/passengers/{PASSENGER_ID}/edit", produces = "application/json")
    public ResponseEntity<EntityModel> getEdit(@PathVariable long PASSENGER_ID) {
        Optional<Passenger> optionalUser = passengerRepository.findById(PASSENGER_ID);

        if(optionalUser.isEmpty()){
            throw new BookNotFoundException(PASSENGER_ID,User.class.getSimpleName());

        }
        Passenger passenger = optionalUser.get();

        PassengerResp passengerResp = PassengerResp.of(passenger);


        EntityModel entityModel = EntityModel.of(passengerResp);

        entityModel.add(linkTo(methodOn(PassengerRestController.class).edit(passenger.getId(),null)).withRel("update"));

        return ResponseEntity.ok(entityModel);



    }




    @GetMapping(value = "/users/{USER_ID}/passengers/Page_listPassenger", produces = "application/json")
    public EntityModel<Address> Page_listPassenger(@PathVariable long USER_ID ) {


        Optional<User> supplierOptional =userRepository.findById(USER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        User supplier = supplierOptional.get();


        Map map = Map.of();


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(PassengerRestController.class).listPassenger(supplier.getId(),null,null)).withRel("list"));
        entityModel.add(linkTo(methodOn(PassengerRestController.class).createPassenger(supplier.getId(),null)).withRel("create"));




        return entityModel;

    }



    @GetMapping(value = "/users/{USER_ID}/passengers", produces = "application/json")
    public PagedModel listPassenger(@PathVariable long USER_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<PassengerResp>> assembler) {

            Optional<User> optionalUser = userRepository.findById(USER_ID);

            if(optionalUser.isEmpty()){
                throw new BookNotFoundException(USER_ID,User.class.getSimpleName());

            }
            User user = optionalUser.get();



        Page<Passenger> passengers = passengerRepository.findAllByUser(user.getId(),pageable);



        return assembler.toModel(passengers.map(e->{


            PassengerResp passengerResp = PassengerResp.of(e);
            EntityModel entityModel = EntityModel.of(passengerResp);
            entityModel.add(linkTo(methodOn(PassengerRestController.class).delete(e.getId())).withRel("delete"));

            entityModel.add(linkTo(methodOn(PassengerRestController.class).getEdit(e.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(PassengerRestController.class).edit(e.getId(),null)).withRel("edit"));

            return entityModel;

        }));



    }
    @Operation(summary = "4、删除Product对象")
    @DeleteMapping(value = "/passengers/{Movie_ID}", produces = "application/json")
    public ResponseEntity<Void> delete(@PathVariable long Movie_ID) {
        Optional<Passenger> supplierOptional = passengerRepository.findById(Movie_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Passenger supplier = supplierOptional.get();


        passengerRepository.delete(supplier);

        return ResponseEntity.ok().build();
    }

}