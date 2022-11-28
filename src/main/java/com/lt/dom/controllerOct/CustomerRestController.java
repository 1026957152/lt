package com.lt.dom.controllerOct;


import com.lt.dom.OctResp.CustomerResp;
import com.lt.dom.OctResp.UserResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;

import com.lt.dom.otcReq.CustomerReq;

import com.lt.dom.otcReq.InvitationPartnerPojo;
import com.lt.dom.otcenum.EnumBookingStatus;
import com.lt.dom.otcenum.EnumUserType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.CustomerServiceImpl;
import com.lt.dom.serviceOtc.FeatureServiceImpl;
import com.lt.dom.serviceOtc.InvitationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class CustomerRestController {


    @Autowired
    private FeatureServiceImpl featureService;

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository CustomerRepository;
    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private SeatingLayoutRepository seatingLayoutRepository;

    @Autowired
    private InvitationServiceImpl invitationService;



    @Autowired
    private InvitationsRepository invitationsRepository;


    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/customers/Page_getCustomerList", produces = "application/json")
    public EntityModel<Media> Page_listUser(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("status_list", EnumBookingStatus.from());


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(CustomerRestController.class).getCustomerList____(supplier.getId(),null,null)).withRel("list"));


        return entityModel;

    }

    @GetMapping(value = "/users/Page_listUser", produces = "application/json")
    public EntityModel Page_listUser( ) {



        Map map = Map.of("status_list", EnumUserType.from());


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(CustomerRestController.class).listUser(null,null)).withRel("list"));


        return entityModel;

    }



    @GetMapping(value = "/customers", produces = "application/json")
    public PagedModel getCustomerList(Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {




/*        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Product product = validatorOptional.get();*/

        Page<Customer> bookingRuleList = CustomerRepository.findAll(pageable);

        return assembler.toModel(bookingRuleList.map(e->{

            CustomerResp customerResp = CustomerResp.from(e);

            EntityModel entityModel = EntityModel.of(customerResp);
            entityModel.add(linkTo(methodOn(CustomerRestController.class).getCustomer(e.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(CustomerRestController.class).editCustomer(e.getId(),null)).withRel("edit"));


            return entityModel;
        }));

    }




    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/customers__", produces = "application/json")
    public PagedModel getCustomerList____(@PathVariable long SUPPLIER_ID ,Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


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

        Page<Customer> bookingRuleList = CustomerRepository.findAll(pageable);

        return assembler.toModel(bookingRuleList.map(e->{

            CustomerResp customerResp = CustomerResp.from(e);

            EntityModel entityModel = EntityModel.of(customerResp);
            entityModel.add(linkTo(methodOn(CustomerRestController.class).getCustomer(e.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(CustomerRestController.class).editCustomer(e.getId(),null)).withRel("edit"));


            return entityModel;
        }));

    }




    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/users", produces = "application/json")
    public PagedModel listUser(Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {



/*        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Product product = validatorOptional.get();*/

        Page<User> bookingRuleList = userRepository.findAll(pageable);

        return assembler.toModel(bookingRuleList.map(e->{

            UserResp customerResp = UserResp.pcFrom(e);

            EntityModel entityModel = EntityModel.of(customerResp);
            entityModel.add(linkTo(methodOn(CustomerRestController.class).getEditDetail(e.getId())).withSelfRel());


            return entityModel;
        }));

    }


    @GetMapping(value = "/users/{USER_ID}/edit",produces = "application/json")
    public ResponseEntity<EntityModel> getEditDetail(@PathVariable long  USER_ID) {

/*        User user = new User();
        user.setPhone(phone);
        Example<User> example = Example.of(user);*/

        Optional<User> optionalUser = userRepository.findById(USER_ID);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();


            UserResp userResp = UserResp.pcFrom(user);
            EntityModel entityModel = EntityModel.of(userResp);
            entityModel.add(linkTo(methodOn(UserRestController.class).beGuide(user.getId())).withRel("be_guide_url"));
            entityModel.add(linkTo(methodOn(UserRestController.class).postRealnameAuths(user.getId(),null)).withRel("realname_auths_url"));


            return ResponseEntity.ok(entityModel);
        }else{
            throw new BookNotFoundException(USER_ID,User.class.getSimpleName());

        }

    }


    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/customers", produces = "application/json")
    public EntityModel<Customer> createCustomer(@PathVariable long SUPPLIER_ID , @RequestBody @Valid CustomerResp customerResp) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Customer Customer = customerService.create(supplier,customerResp);


        EntityModel entityModel = EntityModel.of(Customer);

        return entityModel;

    }


    @PutMapping(value = "/customers/{Customer_ID}", produces = "application/json")
    public EntityModel<Customer> editCustomer(@PathVariable long Customer_ID ,@RequestBody @Valid CustomerReq customerResp) {

        Optional<Customer> supplierOptional = CustomerRepository.findById(Customer_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.not_found,"找不到媒体对象");
        }

        Customer supplier = supplierOptional.get();


        Customer Customer = customerService.update(supplier,customerResp);


        EntityModel entityModel = EntityModel.of(Customer);

        return entityModel;

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/customers/Page_createCustomer", produces = "application/json")
    public EntityModel<Customer> Page_createCustomer(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("type_list", EnumUserType.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(CustomerRestController.class).createCustomer(supplier.getId(),null)).withRel("createCustomer"));
        entityModel.add(linkTo(methodOn(DocumentRestController.class).uploadDocument(null)).withRel("upload"));
        entityModel.add(linkTo(methodOn(CustomerRestController.class).邀请合作(supplier.getId(),null)).withRel("getInvite"));

      //  entityModel.add(linkTo(methodOn(CustomerRestController.class).接受要求(null)).withRel("acceptInvite"));

        return entityModel;

    }



    @GetMapping(value = "/customers/{Customer_ID}", produces = "application/json")
    public EntityModel<Customer> getCustomer(@PathVariable long Customer_ID) {

        Optional<Customer> validatorOptional = CustomerRepository.findById(Customer_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Customer Customer = validatorOptional.get();
        CustomerResp customerResp = CustomerResp.from(Customer);
        EntityModel entityModel = EntityModel.of(customerResp);
        entityModel.add(linkTo(methodOn(CustomerRestController.class).editCustomer(Customer.getId(),null)).withRel("edit"));
        entityModel.add(linkTo(methodOn(CustomerRestController.class).getCustomer(Customer.getId())).withSelfRel());

        return entityModel;

    }











    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/showtimes_Customers", produces = "application/json")
    public EntityModel<Showtime> createShowtime(@PathVariable long SUPPLIER_ID , @RequestBody @Valid ShowtimeReq CustomerReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Optional<Customer> CustomerOptional = CustomerRepository.findById(CustomerReq.getLayout());

        if(CustomerOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到剧目");

        }
        Customer Customer = CustomerOptional.get();

        Optional<Theatre> theatreOptional =theatreRepository.findById(CustomerReq.getTheatre());
        if(theatreOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到剧院");

        }
        Theatre theatre = theatreOptional.get();


        Optional<SeatingLayout> seatingLayoutOptional =seatingLayoutRepository.findById(CustomerReq.getLayout());
        if(theatreOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到剧院");

        }
        SeatingLayout seatingLayout = seatingLayoutOptional.get();
        Showtime showtime =null;// CustomerService.createShowtime(supplier,theatre,Customer,seatingLayout,CustomerReq);


        EntityModel entityModel = EntityModel.of(showtime);

        return entityModel;

    }



    @Operation(summary = "4、邀请成为")
    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/invitations-partner", produces = "application/json")
    public Invitation 邀请合作(@PathVariable long SUPPLIER_ID, @RequestBody InvitationPartnerPojo pojo) {


        Optional<Supplier> supplier = supplierRepository.findById(SUPPLIER_ID);
        if(supplier.isPresent()){

            Invitation componentRight = invitationService.invitePartner(supplier,pojo);
            return componentRight;
        }else{
            System.out.println("抛出异常");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }


    }



/*    @Operation(summary = "4、邀请成为")
    @PostMapping(value = "/invitations/accept", produces = "application/json")
    public Invitation 接受要求( @RequestBody InvitationPartnerAcceptReq pojo) {


        String code = pojo.getCode();
        Optional<Invitation> optionalInvitation = invitationsRepository.findByCode(code);
        if(optionalInvitation.isPresent()){

            Invitation invitation = optionalInvitation.get();

            Invitation componentRight = invitationService.acceptPartner(invitation,pojo);
            return componentRight;
        }else{
            System.out.println("抛出异常");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }


    }*/

}