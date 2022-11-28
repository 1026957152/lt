package com.lt.dom.controllerOct;

//http://developers.amctheatres.com/SeatingLayouts

import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumSeatTier;
import com.lt.dom.otcenum.EnumSeatType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.FileStorageService;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.SeatingLayoutServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class SeatingLayoutRestController {


    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private FileStorageServiceImpl fileStorageService;


    @Autowired
    private SeatingLayoutRepository seatingLayoutRepository;
    @Autowired
    private SeatingLayoutServiceImpl seatingLayoutService;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ZoneRepository zoneRepository;




    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/seating-layouts/Page_listLayout", produces = "application/json")
    public EntityModel<Media> Page_listSeatingLayout(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

        List<Theatre> theatreList = theatreRepository.findAllBySupplier(supplier.getId());

        Map map = Map.of("theatre_list", Theatre.List(theatreList));


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(SeatingLayoutRestController.class).createSeatingLayout(supplier.getId(),null)).withRel("create"));

        entityModel.add(linkTo(methodOn(SeatingLayoutRestController.class).listSeatingLayouts(supplier.getId(),null,null)).withRel("list"));


        return entityModel;

    }
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/seating-layouts", produces = "application/json")

    public PagedModel<SeatingLayoutResq> listSeatingLayouts(@PathVariable long SUPPLIER_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<SeatingLayoutResq>> assembler) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

        Page<SeatingLayout> seatingLayoutList = seatingLayoutRepository.findAllBySupplier(supplier,pageable);


        return assembler.toModel(seatingLayoutList.map(e->{
            SeatingLayoutResq seatingLayoutResq = SeatingLayoutResq.from(e);
            EntityModel entityModel = EntityModel.of(seatingLayoutResq);
          //  entityModel.add(linkTo(methodOn(SeatingLayoutRestController.class).getSeatingLayoutEdit(e.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(SeatingLayoutRestController.class).getSeatingLayoutEdit(e.getId(),null)).withSelfRel());

            return entityModel;

        }));

    }
    @GetMapping(value = "/theatre/{theatre_number}/seating-layouts", produces = "application/json")

    public SeatingLayout listSeatingLayoutsForTheatre(@PathVariable long theatre_number) {

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
    public SeatingLayout createSeatingLayoutForSupplier(@PathVariable long theatre_number, SeatingLayoutPojo pojo) {

        Optional<Theatre> validatorOptional = theatreRepository.findById(theatre_number);
        if(validatorOptional.isEmpty()){
            System.out.println("抛出异常");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));

        }

        return seatingLayoutService.createSeatingLayout(validatorOptional.get(),pojo);

    }

    @PutMapping(value = "/seating_layouts/{seating_layout_id}", produces = "application/json")
    public SeatingLayout updateSeatingLayout(@PathVariable long seating_layout_id,@RequestBody @Valid SeatingLayoutPojo pojo) {
        Optional<SeatingLayout> supplierOptional = seatingLayoutRepository.findById(seating_layout_id);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        SeatingLayout supplier = supplierOptional.get();

        return seatingLayoutService.updateSeatingLayout(supplier,pojo);

    }

    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/seating_layouts", produces = "application/json")
    public SeatingLayout createSeatingLayout(@PathVariable long SUPPLIER_ID,@RequestBody @Valid SeatingLayoutPojo pojo) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

        return seatingLayoutService.createSeatingLayout(supplier,pojo);

    }

    @GetMapping(value = "/seating_layouts/{LAYOUT_ID}", produces = "application/json")
    public SeatingLayoutResq getSeatingLayout(@PathVariable long LAYOUT_ID,@RequestBody @Valid SeatingLayoutPojo pojo) {
        Optional<SeatingLayout> supplierOptional = seatingLayoutRepository.findById(LAYOUT_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        SeatingLayout supplier = supplierOptional.get();

        List<Zone> zoneResp = zoneRepository.findBySeatingLayoutId(supplier.getId());

        SeatingLayoutResq seatingLayoutResq = SeatingLayoutResq.from(supplier);

        seatingLayoutResq.setZones(zoneResp.stream().map(e->{
            return ZoneResp.from(e);
        }).collect(Collectors.toList()));

        return seatingLayoutResq;

    }
    @GetMapping(value = "/seating_layouts/{LAYOUT_ID}/edit", produces = "application/json")
    public EntityModel getSeatingLayoutEdit(@PathVariable long LAYOUT_ID,@RequestBody @Valid SeatingLayoutPojo pojo) {
        Optional<SeatingLayout> supplierOptional = seatingLayoutRepository.findById(LAYOUT_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        SeatingLayout supplier = supplierOptional.get();

        SeatingLayoutEdit seatingLayoutResq = SeatingLayoutEdit.from(supplier);

        Seat seat = new Seat();
        seat.setSeatTier(EnumSeatTier.Regular);
        seatingLayoutResq.setParameterList(
                Map.of(
                        "seat_tier_type_list", EnumSeatTier.EnumList(),
                        "seat_type_list", EnumSeatType.EnumList()

                ));

        List<Zone> zoneResp = zoneRepository.findBySeatingLayoutId(supplier.getId());

        seatingLayoutResq.setZones(zoneResp.stream().map(e->{
            return ZoneResp.from(e);
        }).collect(Collectors.toList()));


        List<Seat> list = seatRepository.findBySeatingLayoutId(supplier.getId());

        seatingLayoutResq.setSeats(list.stream().map(x->{
            SeatResp seatPojo = SeatResp.from(x);
            return seatPojo;
        }).collect(Collectors.toList()));
        EntityModel entityModel = EntityModel.of(seatingLayoutResq);
        //  entityModel.add(linkTo(methodOn(SeatingLayoutRestController.class).getSeatingLayoutEdit(e.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(SeatingLayoutRestController.class).getSeatingLayoutEdit(supplier.getId(),null)).withSelfRel());
        entityModel.add(linkTo(methodOn(SeatingLayoutRestController.class).updateSeatingLayout(supplier.getId(),null)).withRel("edit"));

        entityModel.add(linkTo(methodOn(SeatingLayoutRestController.class).addSeatForSeatingLayout(supplier.getId(),null)).withRel("createSeat"));

        entityModel.add(linkTo(methodOn(SeatingLayoutRestController.class).addZoneForSeatingLayout(supplier.getId(),null)).withRel("createZone"));





        return entityModel;

    }




    @PostMapping(value = "/seating-layouts/{seating_layout_id}/seats", produces = "application/json")
    public List<SeatPojo> addSeatForSeatingLayout(@PathVariable long seating_layout_id, @RequestBody List<SeatPojo> pojos) {

        Optional<SeatingLayout> validatorOptional = seatingLayoutRepository.findById(seating_layout_id);
        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到");
        }
        System.out.println("抛出异常");
        return seatingLayoutService.addSeatForSeatingLayout(validatorOptional.get(),pojos);
    }


    @PostMapping(value = "/zones/{ZONE_id}/seats", produces = "application/json")
    public List<ZoneRowResp> addSeatForZone(@PathVariable long ZONE_id, @RequestBody List<ZoneRowReq> pojos) {

        Optional<Zone> validatorOptional = zoneRepository.findById(ZONE_id);
        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到");
        }
        System.out.println("抛出异常");
        return seatingLayoutService.addSeatForZone(validatorOptional.get(),pojos);
    }


    @PostMapping(value = "/seating-layouts/{seating_layout_id}/zones", produces = "application/json")
    public List<ZoneResp> addZoneForSeatingLayout(@PathVariable long seating_layout_id, @RequestBody List<ZoneReq> pojos) {

        Optional<SeatingLayout> validatorOptional = seatingLayoutRepository.findById(seating_layout_id);
        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到");
        }
        System.out.println("抛出异常");
        return seatingLayoutService.addZoneForSeatingLayout(validatorOptional.get(),pojos);
    }
}