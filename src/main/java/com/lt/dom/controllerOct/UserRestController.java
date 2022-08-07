package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.BookingResp;
import com.lt.dom.OctResp.PublicationResp;
import com.lt.dom.OctResp.SupplierResp;
import com.lt.dom.OctResp.UserResp;
import com.lt.dom.RealNameAuthentication.RealNameAuthenticationServiceImpl;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.User_already_be_guideException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingDocumentIdsResp;
import com.lt.dom.otcReq.PageReq;
import com.lt.dom.otcReq.RealnameAuthsReq;
import com.lt.dom.otcReq.UserPojo;
import com.lt.dom.otcenum.EnumProductType;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.javatuples.Quintet;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class UserRestController {


    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private RealNameAuthenticationServiceImpl realnameAuthsService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GuideServiceImpl guideService;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private GuideRepository guideRepository;
    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping(value = "/users/current",produces = "application/json")
    public ResponseEntity<EntityModel> getCurrent() {


        Authentication authentication =  authenticationFacade.getAuthentication();

     //   UserDetails user_de = (UserDetails)authentication.getPrincipal();

        Optional<User> optionalUser = userRepository.findByPhone("13468801684");

     //   Optional<User> optionalUser = userRepository.findByPhone(user_de.getUsername());

        if(optionalUser.isPresent()){
            User user = optionalUser.get();

            Optional<Employee> optional = employeeRepository.findByUserId(user.getId());
            UserResp userResp = UserResp.from(user);
            EntityModel entityModel = EntityModel.of(userResp);

            if(optional.isPresent()){
                userResp.setHired(true);

                Optional<Supplier> optionalSupplier = supplierRepository.findById(optional.get().getSuplierId());

                SupplierResp supplierResp = SupplierResp.from(optionalSupplier.get());
                EntityModel supplierRespEntityModel = EntityModel.of(supplierResp);


                supplierRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(optional.get().getSuplierId())).withRel("getSupplier"));

/*
                supplierRespEntityModel.add(linkTo(methodOn(RedemptionRestController.class).redeemVonchorBycode(null,null)).withRel("redeem"));


                supplierRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).linkEmployee(optional.get().getSuplierId(),null)).withRel("addEmployees"));
                supplierRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).page_linkEmployee(optional.get().getSuplierId())).withRel("prepareAddEmployees"));
*/

/*
                userResp.add(linkTo(methodOn(SupplierRestController.class).getSupplier(optional.get().getSuplierId())).withRel("supplier_url"));
                userResp.add(linkTo(methodOn(RedemptionRestController.class).redeemVonchorBycode(null,null)).withRel("redeem_url"));
                userResp.add(linkTo(methodOn(RedemptionRestController.class).getRedemptionEntry(optional.get().getSuplierId())).withRel("redemption_entries_url"));
                userResp.add(linkTo(methodOn(SupplierRestController.class).linkEmployee(optional.get().getSuplierId(),null)).withRel("add_employees_url"));
                userResp.add(linkTo(methodOn(SupplierRestController.class).page_linkEmployee(optional.get().getSuplierId(),null)).withRel("page_add_employees_url"));
*/
                userResp.setSupplier(supplierRespEntityModel);

                entityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(optional.get().getSuplierId())).withRel("getSupplier"));
                entityModel.add(linkTo(methodOn(RedemptionRestController.class).redeemVonchorBycode(null,null)).withRel("redeem"));
                entityModel.add(linkTo(methodOn(RedemptionRestController.class).pageRedemptionEntry(optional.get().getSuplierId(),null,null)).withRel("getRedemptionEntrys"));

            }
            entityModel.add(linkTo(methodOn(UserRestController.class).beGuide(user.getId())).withRel("beGuide"));
            entityModel.add(linkTo(methodOn(UserRestController.class).postRealnameAuths(user.getId(),null)).withRel("realnameAuths"));
            entityModel.add(linkTo(methodOn(PublicationRestController.class).pageUserPublicationResp(user.getId(),null,null,null)).withRel("getVoucherList"));
            entityModel.add(linkTo(methodOn(UserRestController.class).pageReservation(user.getId(),null,null)).withRel("getBookingList"));





            return ResponseEntity.ok(entityModel);
        }else{
            throw new BookNotFoundException(0,User.class.getSimpleName());

        }

    }



    @GetMapping(value = "/users/{USER_ID}",produces = "application/json")
    public ResponseEntity<UserResp> get(@PathVariable long  USER_ID) {

/*        User user = new User();
        user.setPhone(phone);
        Example<User> example = Example.of(user);*/

        Optional<User> optionalUser = userRepository.findById(USER_ID);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();


            UserResp userResp = UserResp.from(user);

            userResp.add(linkTo(methodOn(UserRestController.class).beGuide(user.getId())).withRel("be_guide_url"));
            userResp.add(linkTo(methodOn(UserRestController.class).postRealnameAuths(user.getId(),null)).withRel("realname_auths_url"));

            return ResponseEntity.ok(userResp);
        }else{
            throw new BookNotFoundException(USER_ID,User.class.getSimpleName());

        }

    }



    @GetMapping(value = "/users/{USER_ID}/balance",produces = "application/json")
    public ResponseEntity<Balance> getBalance(@PathVariable long  USER_ID) {


        Optional<Balance> optionalUser = balanceRepository.findById(USER_ID);

        if(optionalUser.isPresent()){
            Balance user = optionalUser.get();
         //   UserResp userResp = UserResp.from(user);

         //   userResp.add(linkTo(methodOn(UserRestController.class).beGuide(user.getId())).withRel("be_guide_url"));
         //   userResp.add(linkTo(methodOn(UserRestController.class).postRealnameAuths(user.getId(),null)).withRel("realname_auths_url"));

            return ResponseEntity.ok(user);
        }else{
            throw new BookNotFoundException(USER_ID,User.class.getSimpleName());

        }

    }


    @PostMapping(value = "/users",produces = "application/json")
    public User createUser(@RequestBody @Valid UserPojo pojo) {

        User user = userService.createUser(pojo);

        return user;
    }





    @Operation(summary = "1、实名认证")
    @PostMapping(value = "/users/{USER_ID}/realname-auths/individual", produces = "application/json")
    public ResponseEntity<User> postRealnameAuths(@PathVariable long USER_ID, @RequestBody @Valid RealnameAuthsReq realnameAuthsReq) {

        Optional<User> optionalUser = userRepository.findById(USER_ID);

        if(optionalUser.isPresent()){
            User user = realnameAuthsService.postRealnameAuths(optionalUser.get(),realnameAuthsReq);
            return ResponseEntity.ok(user);

        }else{
            throw new BookNotFoundException(USER_ID,User.class.getSimpleName());

        }

    }






    @Operation(summary = "1、成为一名导游")
    @PostMapping(value = "/users/{USER_ID}/guide", produces = "application/json")
    public ResponseEntity<Guide> beGuide(@PathVariable long USER_ID) {

        Optional<User> optionalUser = userRepository.findById(USER_ID);

        if(optionalUser.isPresent()){
            Optional<Guide> user = guideRepository.findByUserId(optionalUser.get().getId());
            if(user.isEmpty()){
                Guide guide = guideService.beGuide(optionalUser.get());
                return ResponseEntity.ok(guide);
            }else {
                throw new User_already_be_guideException(USER_ID,User.class.getSimpleName(),optionalUser.get().getCode() + "该用户已经是 导游身份了");


            }


        }else{
            throw new BookNotFoundException(USER_ID,User.class.getSimpleName());
        }

    }






    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/users/{USER_ID}/bookings", produces = "application/json")
    public PagedModel pageReservation(@PathVariable long USER_ID, Pageable pageable , PagedResourcesAssembler<BookingResp> assembler) {


        Optional<User> optionalUser = userRepository.findById(USER_ID);

        if(optionalUser.isEmpty()){
            throw new BookNotFoundException(USER_ID,User.class.getSimpleName());

        }
        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);
        Page<BookingResp> page =  reservationPage.map(x->{
            Optional<Product> product = productRepository.findById(x.getProductId());
            BookingResp resp = BookingResp.toResp(Pair.with(x,product.get()));
            resp.add(linkTo(methodOn(BookingRestController.class).pay(x.getId(),null)).withRel("pay_url"));
            return resp;
        });
        return assembler.toModel(page);
    }

}