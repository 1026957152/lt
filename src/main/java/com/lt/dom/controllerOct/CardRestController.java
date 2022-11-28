package com.lt.dom.controllerOct;


import com.lt.dom.OctResp.CardResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.BalanceTransaction;
import com.lt.dom.oct.SeatingLayout;
import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.Card;
import com.lt.dom.otcReq.CardReq;
import com.lt.dom.otcReq.SeatingLayoutReq;
import com.lt.dom.otcenum.EnumRedemptionMethod;
import com.lt.dom.otcenum.EnumSeatTier;
import com.lt.dom.otcenum.EnumSeatType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.repository.CardRepository;
import com.lt.dom.serviceOtc.CardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class CardRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardServiceImpl theatreService;

    @Autowired
    private CardRepository theatreRepository;


    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/cards", produces = "application/json")
    public PagedModel getCardList(@PathVariable long SUPPLIER_ID ,Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

/*        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Product product = validatorOptional.get();*/

        Page<Card> bookingRuleList = cardRepository.findAll(pageable);

        return assembler.toModel(bookingRuleList.map(e->{

            CardResp theatreResp = CardResp.of(e);

            EntityModel entityModel = EntityModel.of(theatreResp);
            entityModel.add(linkTo(methodOn(CardRestController.class).getCard(e.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(CardRestController.class).editCard(e.getId(),null)).withRel("edit"));


            return entityModel;
        }));

    }


    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/cards", produces = "application/json")
    public EntityModel<Card> createCard(@PathVariable long SUPPLIER_ID ,@RequestBody @Valid CardReq theatreReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Card theatre = null;//theatreService.create(supplier,theatreReq);


        EntityModel entityModel = EntityModel.of(theatre);

        return entityModel;

    }


    @PostMapping(value = "/cards/{THEATRE_ID}", produces = "application/json")
    public EntityModel<Card> editCard(@PathVariable long THEATRE_ID ,@RequestBody @Valid CardReq theatreReq) {

        Optional<Card> supplierOptional = theatreRepository.findById(THEATRE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Card supplier = supplierOptional.get();


        Card theatre =null;// theatreService.update(supplier,theatreReq);


        EntityModel entityModel = EntityModel.of(theatre);

        return entityModel;

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/cards/Page_createCard", produces = "application/json")
    public EntityModel<Card> Page_createCard(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("redemption_method_list", EnumRedemptionMethod.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(CardRestController.class).createCard(supplier.getId(),null)).withRel("createCard"));


        return entityModel;

    }



    @GetMapping(value = "/cards/{THEATRE_ID}", produces = "application/json")
    public EntityModel<Card> getCard(@PathVariable long THEATRE_ID) {

        Optional<Card> validatorOptional = theatreRepository.findById(THEATRE_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Card theatre = validatorOptional.get();
        CardResp theatreResp = CardResp.from(theatre);
        EntityModel entityModel = EntityModel.of(theatre);
        entityModel.add(linkTo(methodOn(CardRestController.class).getCard(theatre.getId())).withRel("createCard"));
        entityModel.add(linkTo(methodOn(CardRestController.class).Page_createSeatingLayout(theatre.getId())).withRel("Page_createSeatingLayout"));
        entityModel.add(linkTo(methodOn(CardRestController.class).createSeatingLayout(theatre.getId(),null)).withRel("createSeatingLayout"));


        return entityModel;

    }









    @GetMapping(value = "/cards/{THEATRE_ID}/Page_createSeatingLayout", produces = "application/json")
    public EntityModel<Card> Page_createSeatingLayout(@PathVariable long THEATRE_ID ) {


        Optional<Card> validatorOptional = theatreRepository.findById(THEATRE_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到剧院");

        }
        Card theatre = validatorOptional.get();


        Map map = Map.of("redemption_method_list", EnumRedemptionMethod.values(),
                "seat_type_list", EnumSeatType.values(),
                "seat_tier_list", EnumSeatTier.values()

        );


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(CardRestController.class).createSeatingLayout(theatre.getId(),null)).withRel("createCard"));


        return entityModel;

    }


    @PostMapping(value = "/cards/{THEATRE_ID}/seating_layouts", produces = "application/json")
    public EntityModel<SeatingLayout> createSeatingLayout(@PathVariable long THEATRE_ID ,@RequestBody @Valid SeatingLayoutReq theatreReq) {

        Optional<Card> validatorOptional = theatreRepository.findById(THEATRE_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到剧院");

        }
        Card theatre = validatorOptional.get();

        SeatingLayout seatingLayout =null;// theatreService.createSeatingLayout(theatre,theatreReq);


        EntityModel entityModel = EntityModel.of(seatingLayout);

        return entityModel;

    }

}