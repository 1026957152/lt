package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.CardholderResp;
import com.lt.dom.OctResp.UserResp;
import com.lt.dom.OctResp.home.HomeHeroPassResp;
import com.lt.dom.OctResp.home.HomeResp;
import com.lt.dom.PassAsyncServiceImpl;
import com.lt.dom.controllerOct.IndexController;
import com.lt.dom.controllerOct.PassRestController;
import com.lt.dom.controllerOct.RandomCardNumberGenerator;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.util.AtomicSequenceGenerator;
import com.lt.dom.vo.UserVo;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PassServiceImpl {
    @Autowired
    private PassAsyncServiceImpl passAsyncService;


    @Autowired
    private FileStorageServiceImpl fileStorageService;


    @Autowired
    private PassRepository passRepository;

    @Autowired
    private CardholderRepository cardholderRepository;

    @Autowired
    private UserServiceImpl userService;




    @Autowired
    private AssetServiceImpl assetService;



    @Autowired
    private BulkIssuanceCardRequestRepository bulkIssuanceCardRequestRepository;

    @Autowired
    private PassProductRepository passProductRepository;


    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private ComponentRightRepository componentRightRepository;



    @Autowired
    private ComponentRightServiceImpl componentRightService;


/*
    public  void setupData(User user) {

        Pass pass = new Pass();
        pass.setUser(user.getId());
        pass.setCode("code");
        passRepository.save(pass);
    }
*/




public Pass active(Pass pass, PassActivePojo wxlinkUserReq) {




    UserPojo userPojo = new UserPojo();
    //userPojo.setFirst_name(wxlinkUserReq.getFirst_name());
    //userPojo.setLast_name(wxlinkUserReq.getLast_name());
    //userPojo.setNick_name(wxlinkUserReq.getUser_name());
    userPojo.setRealName(wxlinkUserReq.getName());
  //  userPojo.setPhone(wxlinkUserReq.getUser_phone());
    userPojo.setPassword(wxlinkUserReq.getId_card());
    userPojo.setRoles(Arrays.asList(EnumRole.ROLE_BANK_STAFF.name()));




/*    if(wxlinkUserReq.getAllowed_supplier().getType().equals(EnumSupplierType.TravelAgent)){
        userPojo.setRoles(Arrays.asList(EnumRole.ROLE_TRAVEL_AGENCY.name()));
    }else{

        if(wxlinkUserReq.getBusiness_type().equals(EnumBussinessType.government_entity)){
            userPojo.setRoles(Arrays.asList(EnumRole.ROLE_GOVERNMENT.name()));
        }else{
            userPojo.setRoles(Arrays.asList(EnumRole.ROLE_MERCHANT.name()));
        }

    }*/


 //   User user = userService.createUser(userPojo,Arrays.asList(Pair.with(EnumIdentityType.identity_card,userPojo.getIdCard())));


    Cardholder cardholder = new Cardholder();
    cardholder.setCompany(pass.getId());
    cardholder.setName(wxlinkUserReq.getName());
    cardholder.setIdentity(wxlinkUserReq.getId_card());

    cardholder.setUuid(UUID.randomUUID().toString());
    cardholder = cardholderRepository.save(cardholder);




    if(wxlinkUserReq.getFront() != null){
        fileStorageService.updateFromTempDocumentCode(cardholder.getUuid(), EnumDocumentType.document_front_file, wxlinkUserReq.getFront().getCode());
    }
    if(wxlinkUserReq.getBack() != null){
        fileStorageService.updateFromTempDocumentCode(cardholder.getUuid(), EnumDocumentType.document_back_file, wxlinkUserReq.getBack().getCode());
    }



    pass.setCardholder(cardholder);

    pass.setStatus(EnumCardStatus.active);
    if(pass.getDurationUnit().equals(EnumPassDorationUnit.months)){
        pass.setExpiringDate(LocalDateTime.now().plusMonths(pass.getDuration()));
    }
    if(pass.getDurationUnit().equals(EnumPassDorationUnit.days)){
        pass.setExpiringDate(LocalDateTime.now().plusDays(pass.getDuration()));
    }


    passRepository.save(pass);
    return pass;
}





    public Pass create(User user, List<ComponentRight> componentRightList) {

        //Optional<Pass> passOptional = passRepository.findByUser(user.getId());
        Pass pass = create_virtual(user,12);

  //      pass =  passOptional.get();

        return pass;
    }





    public Cardholder create(User user, CardholderResp cardholderResp) {

        Cardholder cardholder = new Cardholder();
        cardholder.setCreated_at(LocalDateTime.now());
        cardholder.setUser(user.getId());
        cardholder.setStatus(EnumCardholderStatus.DATE_ENQUIRY);
        cardholder.setName(cardholderResp.getName());
        cardholder.setPhoneNumber(cardholderResp.getPhoneNumber());
        cardholder.setType(cardholderResp.getType());
        return cardholder;
    }

    public Pass create(Supplier supplier, Product product, User user, PassCreatePojo pojo) {


        Optional<PassProduct> productOptional = passProductRepository.findByProduct(product.getId());

        if(productOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到 card 产品");
        }
        PassProduct passProduct = productOptional.get();


        Pass pass = new Pass();
        pass.setSupplier(supplier.getId());

        pass.setCode(idGenService.passCode());
        pass.setProduct(passProduct.getProduct());
        pass.setProductPass(passProduct.getId());
        pass.setDuration(passProduct.getDuration());
        pass.setDurationUnit(passProduct.getDurationUnit());
        pass.setUser(user.getId());

        pass.setType(pojo.getType());

        pass.setActive(false);
        pass.setStatus(EnumCardStatus.active);
        pass.setShipping_statis(EnumPassShippingStatus.delivered);

        if(pass.getDurationUnit().equals(EnumPassDorationUnit.months)){
            pass.setExpiringDate(LocalDateTime.now().plusMonths(pass.getDuration()));
        }
        if(pass.getDurationUnit().equals(EnumPassDorationUnit.days)){
            pass.setExpiringDate(LocalDateTime.now().plusDays(pass.getDuration()));
        }

        pass.setPersonal(EnumCardPersonalized.Personalized);
        pass.setFulfillment_status(EnumCardFullfullmentStatus.Created);

        pass.setProductPassUuid(passProduct.getUuid());
        pass= passRepository.save(pass);





        return pass;
    }

    AtomicSequenceGenerator atomicSequenceGenerator = new AtomicSequenceGenerator(1);


    public Pass create_virtual(User user,Integer duration) {
/*
        List<Pass> passOptional = passRepository.findByUser(user.getId());
        Pass pass = null;
        if(passOptional.isEmpty()){*/
        Pass pass =  new Pass();
            pass.setCode(idGenService.passCode());
            pass.setUser(user.getId());

            pass.setNumber(atomicSequenceGenerator.getNextString());

            pass.setFulfillment_status(EnumCardFullfullmentStatus.Created);

            pass.setPersonal(EnumCardPersonalized.Personalized);


            pass.setDuration(duration);
            pass.setFormFactor(EnumFormFactorType.physical);

            pass.setType(EnumCardType.GOOD_FUNDS_CREDIT);
            pass.setDurationUnit(EnumPassDorationUnit.months);
            pass.setDurationUnit(EnumPassDorationUnit.months);
            pass.setActive(false);
            pass.setStatus(EnumCardStatus.active);
            pass.setShipping_statis(EnumPassShippingStatus.delivered);

            if(pass.getDurationUnit().equals(EnumPassDorationUnit.months)){
                pass.setExpiringDate(LocalDateTime.now().plusMonths(pass.getDuration()));
            }
            if(pass.getDurationUnit().equals(EnumPassDorationUnit.days)){
                pass.setExpiringDate(LocalDateTime.now().plusDays(pass.getDuration()));
            }

            pass= passRepository.save(pass);
  /*      }else{
            pass =  passOptional.get(0);
        }
*/



        assetService.getWithNew(pass.getCode(),pass.getId());


        return pass;
    }


    public Pass create_virtual(LineItem product, Cardholder cardholder, Long user, Integer duration) {



        Pass pass = create_virtual_witoutUser(product,cardholder,duration);

        pass.setCardholder(cardholder);
        pass.setUser(user);
        pass.setOwner(user);

        cardholder.setPass(pass);


        pass = passRepository.save(pass);



        return pass;
    }

    public Pass create_virtual(LineItem lineItem, Cardholder cardholder, Integer duration) {


        Pass pass = create_virtual_witoutUser(lineItem,cardholder,duration);


        pass = passRepository.save(pass);

        return pass;
    }


    private Pass create_virtual_witoutUser(LineItem product, Cardholder cardholder, Integer duration) {





        Pass pass =  new Pass();





        pass.setCode(idGenService.passCode());
        pass.setSupplier(product.getSupplier());


        pass.setBooking(product.getBooking());
        pass.setBookingLine(product.getId());


        ;
        pass.setNumber(RandomCardNumberGenerator.generateMasterCardNumber());
        //  pass.setNumber(atomicSequenceGenerator.getNextString());
        pass.setFulfillment_status(EnumCardFullfullmentStatus.Created);

        pass.setPersonal(EnumCardPersonalized.Personalized);


        pass.setDuration(duration);
        pass.setFormFactor(EnumFormFactorType.physical);

        pass.setType(EnumCardType.GOOD_FUNDS_CREDIT);
        pass.setDurationUnit(EnumPassDorationUnit.months);
        pass.setDurationUnit(EnumPassDorationUnit.months);

        //  String generatedString = RandomStringUtils.randomAlphabetic(4);
        String generatedString = RandomStringUtils.randomNumeric(4);
        pass.setPin(generatedString);

        pass.setActive(true);
        pass.setStatus(EnumCardStatus.inactive);
        pass.setShipping_statis(EnumPassShippingStatus.delivered);

        if(pass.getDurationUnit().equals(EnumPassDorationUnit.months)){
            pass.setExpiringDate(LocalDateTime.now().plusMonths(pass.getDuration()));
        }
        if(pass.getDurationUnit().equals(EnumPassDorationUnit.days)){
            pass.setExpiringDate(LocalDateTime.now().plusDays(pass.getDuration()));
        }


        Optional<PassProduct> productOptional = passProductRepository.findByProduct(product.getProduct());

        if(productOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到 card 产品");
        }
        PassProduct passProduct = productOptional.get();


        pass.setProductPassUuid(passProduct.getUuid());

        pass.setLabel(passProduct.getLabel());


        //assetService.getWithNew(pass.getCode(),pass.getId());


        return pass;
    }





    public List<ComponentRight> fromValueList(List<Long> collect) {




        List<ComponentRight> componentRightList = componentRightRepository.findAllById(collect);
        return componentRightList;

    }

    public Pass link(Pass pass, User user) {

        pass.setUser(user.getId());
      //  pass.setStatus(user.getId());
        return passRepository.save(pass);
    }

    public Pass changeStatus(Pass pass, PassStatusChangePojo pojo) {
        pass.setUser(pass.getId());
        pass.setStatus(pojo.getStatus());
        pass.setStatus_reason_code(pojo.getStatus_reason_code());
        return passRepository.save(pass);

    }













    public BulkIssuanceCardRequest createBulkIssuaknceCardRequests(Supplier supplier, Product product, BulkIssuanceCardRequestReq pojo) {
        BulkIssuanceCardRequest bulkIssuanceCardRequest = new BulkIssuanceCardRequest();
        bulkIssuanceCardRequest.setSupplier(supplier.getId());
        bulkIssuanceCardRequest.setCode(idGenService.nextId(""));
        bulkIssuanceCardRequest.setCard_product_id(product.getId());
        bulkIssuanceCardRequest.setName_on_card(pojo.getName_on_card());
        bulkIssuanceCardRequest.setNumber_of_cards(pojo.getNumber_of_cards());
        bulkIssuanceCardRequest.setCard_validity_term(pojo.getCard_validity_term());

        ShippingCardAddress returnCardAddress = new ShippingCardAddress();
        returnCardAddress.setAddress_line1(pojo.getReturn_address().getAddress_line1());
        returnCardAddress.setAddress_line2(pojo.getReturn_address().getAddress_line2());
        returnCardAddress.setFirst_name(pojo.getReturn_address().getFirst_name());
        returnCardAddress.setMobile(pojo.getReturn_address().getMobile());
        returnCardAddress.setCity(pojo.getReturn_address().getCity());
        returnCardAddress.setCity(pojo.getReturn_address().getState());
        returnCardAddress.setPostal_code(pojo.getReturn_address().getPostal_code());
        bulkIssuanceCardRequest.setReturn_address(returnCardAddress);


        ShippingCardAddress shippingCardAddress = new ShippingCardAddress();
        shippingCardAddress.setAddress_line1(pojo.getShipping_address().getAddress_line1());
        shippingCardAddress.setAddress_line2(pojo.getShipping_address().getAddress_line2());
        shippingCardAddress.setFirst_name(pojo.getShipping_address().getFirst_name());
        shippingCardAddress.setMobile(pojo.getShipping_address().getMobile());
        shippingCardAddress.setCity(pojo.getShipping_address().getCity());
        shippingCardAddress.setCity(pojo.getShipping_address().getState());
        shippingCardAddress.setPostal_code(pojo.getShipping_address().getPostal_code());
        bulkIssuanceCardRequest.setShipping_address(shippingCardAddress);

        bulkIssuanceCardRequest.setStatus(EnumBulkIssuanceCardRequestStatus.DRAFT);



        bulkIssuanceCardRequest =  bulkIssuanceCardRequestRepository.save(bulkIssuanceCardRequest);

        passAsyncService.异步新建(idGenService.passfig, bulkIssuanceCardRequest);


        return bulkIssuanceCardRequest;
    }


    public void withMe(HomeHeroPassResp userResp, UserVo user) {


        if(user == null || !user.getBind()){
            userResp.setHasPass(false);
            userResp.setLable("您是否已有榆林主人卡");
            userResp.setLines(Arrays.asList(Map.of("lable","我有一张数字主人卡","path","path_添加卡","image",EnumLayoutFeatured.添加卡.getFeature_image()),
                    Map.of("lable","我有一张实体主人卡","path","path_添加卡","image",EnumLayoutFeatured.添加卡.getFeature_image()),
                    Map.of("lable","我帮别人激活","path","path_主人卡激活","image",EnumLayoutFeatured.主人卡激活.getFeature_image()),
                    Map.of("lable","我没有主人卡","path","path_link_hero","image",EnumLayoutFeatured.主人卡激活.getFeature_image())));


            return;
        }

        List<Pass> passOptional = passRepository.findByUser(user.getUser_id());

        if(!passOptional.isEmpty()){
            Pass pass = passOptional.get(0);

            userResp.setHasPass(true);
            String link = linkTo(methodOn(PassRestController.class).getPass(pass.getId())).withSelfRel().getHref();


            System.out.println(pass.getLabel());
            System.out.println(pass.getCode());
            System.out.println(pass.getNumber());
            System.out.println(pass.getExpiringDate());


            EntityModel entityModel = EntityModel.of(Map.of(
                    "title",pass.getLabel(),
                    "code",pass.getCode(),
                    "number",pass.getNumber(),
                    "expiringDate",pass.getExpiringDate(),
                    "path","/pages/ownercard/show?url="+link));

            entityModel.add(linkTo(methodOn(PassRestController.class).getPass(pass.getId())).withSelfRel());

            userResp.setDefaultPass(entityModel);

        }else{

            //   else if(ee.equals(EnumLayoutFeatured.主人卡激活)){
                String link = linkTo(methodOn(PassRestController.class).Page_activePass()).withSelfRel().getHref();
                String path_主人卡激活 = "/pages/ownercard/activate?url="+link;
           // }
          //  else if(ee.equals(EnumLayoutFeatured.添加卡)){
                String link_添加卡 = linkTo(methodOn(PassRestController.class).Page_linkPass(user.getUser_id())).withSelfRel().getHref();
             //   resp.setPath("/pages/user/card/add?url="+link);
                String path_添加卡 = "/pages/user/card/add?url="+link_添加卡;

          //  }

            String link_hero = linkTo(methodOn(IndexController.class).heroPass(null)).withSelfRel().getHref();
            String path_link_hero = "/pages/onecard/show?url="+link_hero;

            userResp.setLable("您是否已有榆林主人卡");
            userResp.setLines(Arrays.asList(Map.of("lable","我有一张数字主人卡","path",path_添加卡,"image",EnumLayoutFeatured.添加卡.getFeature_image()),
                    Map.of("lable","我有一张实体主人卡","path",path_添加卡,"image",EnumLayoutFeatured.添加卡.getFeature_image()),
                    Map.of("lable","我帮别人激活","path",path_主人卡激活,"image",EnumLayoutFeatured.主人卡激活.getFeature_image()),
                    Map.of("lable","我没有主人卡","path",path_link_hero,"image",EnumLayoutFeatured.主人卡激活.getFeature_image())));





            EntityModel entityModel = EntityModel.of(Map.of("title","城市主人尊享卡","path","/pages/onecard/show?url="+link));
            entityModel.add(linkTo(methodOn(IndexController.class).heroPass(null)).withSelfRel());

            userResp.setHasPass(false);

        }
    }

    public void withMe(UserResp userResp, User user) {



        List<Pass> passOptional = passRepository.findByUser(user.getId());

        if(!passOptional.isEmpty()){
            Pass pass = passOptional.get(0);

            userResp.setHasPass(true);
            String link = linkTo(methodOn(PassRestController.class).getPass(pass.getId())).withSelfRel().getHref();


            System.out.println(pass.getLabel());
            System.out.println(pass.getCode());
            System.out.println(pass.getNumber());
            System.out.println(pass.getExpiringDate());


            EntityModel entityModel = EntityModel.of(Map.of(
                    "title",pass.getLabel(),
                    "code",pass.getCode(),
                    "number",pass.getNumber(),

                    "expiringDate",pass.getExpiringDate(),

                    "path","/pages/ownercard/show?url="+link));

            entityModel.add(linkTo(methodOn(PassRestController.class).getPass(pass.getId())).withSelfRel());

            userResp.setDefaultPass(entityModel);

        }else{

            String link = linkTo(methodOn(IndexController.class).heroPass(null)).withSelfRel().getHref();


            EntityModel entityModel = EntityModel.of(Map.of("title","城市主人尊享卡","path","/pages/onecard/show?url="+link));
            entityModel.add(linkTo(methodOn(IndexController.class).heroPass(null)).withSelfRel());




            userResp.setHasPass(false);
            userResp.setDefaultPass(entityModel);
        }
    }
    public Pass active(Pass pass, List<ComponentRight> componentRightList) {
        return null;
    }

    public Pass block(Pass pass, PassBlockPojo pojo) {
        return null;
    }

    public Pass sendToFriend(PassActivePojo pojo) {
        return null;
    }

    public void withMe(HomeResp homeResp) {
      //  String link_hero = linkTo(methodOn(IndexController.class).heroPass(null)).withSelfRel().getHref();

        Map map = Map.of("hasPass",false,
                "lable","我没有主人卡",
                "path",EnumMiniappPagePath.home_city_pass.path,
                "image",EnumLayoutFeatured.主人卡激活.getFeature_image());


        homeResp.setCityPass(map);

    }
}
