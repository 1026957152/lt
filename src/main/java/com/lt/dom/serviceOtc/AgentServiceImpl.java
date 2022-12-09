package com.lt.dom.serviceOtc;


import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.AgentEditReq;
import com.lt.dom.otcReq.AgentReq;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgentServiceImpl {

    Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private AgentProductRepository agentProductRepository;


    @Autowired
    private FileStorageServiceImpl fileStorageService;
    @Autowired
    private PricingTypeRepository pricingTypeRepository;



    @Autowired
    private ContactServiceImpl contactService;
    @Autowired
    private ProductRepository productRepository;



    @Autowired
    private IdGenServiceImpl idGenService;


    @Transient
    public Agent create(Supplier supplier, Supplier agent, AgentReq theatreReq) {
        Agent theatre = new Agent();



        theatre.setSupplier(supplier.getId());
        theatre.setName(theatreReq.getName());
        theatre.setCode(idGenService.nextId("thpa"));
        theatre.setDesc(theatreReq.getDesc());

        theatre.setAgent(theatreReq.getAgent());

        theatre.setPartnerId(theatreReq.getPartner_id());
        theatre.setAuthorizationCode(theatreReq.getAuthorization_code());
        theatre.setStatus(EnumAgentStatus.Active);


        theatre = agentRepository.save(theatre);


        if(theatreReq.getLogo()!= null && theatreReq.getLogo().getCode()!=null){
            fileStorageService.updateFromTempDocument(theatre.getCode(),theatreReq.getLogo(), EnumDocumentType.region_photo);

        }
        if(theatreReq.getContacts() != null ){
            contactService.create(EnumRelatedObjectType.agent,theatre.getId(),theatreReq.getContacts());

        }



        return theatre;


    }

    @Transactional
    public Agent update(Agent theatre, Supplier agent, AgentEditReq.EditReq tripReq) {

        List<Product> productList = productRepository.findAllById(tripReq.getProducts().stream().map(e->e.getId()).collect(Collectors.toList()));

        theatre.setName(tripReq.getName());
        theatre.setAgent(agent.getId());
        theatre.setDesc(tripReq.getDesc());
        theatre.setBilling(tripReq.getBilling());

      //  theatre.setBaseURL(tripReq.getBaseURL());
       // theatre.setPlatform(tripReq.getPlatform());
        theatre.setPartnerId(tripReq.getPartner_id());
        theatre.setAuthorizationCode(tripReq.getAuthorization_code());





        Agent finalTheatre = theatre;
        theatre.getProducts().clear();
        theatre = agentRepository.save(theatre);





        if(tripReq.getContacts() != null ){
            Contact contact = contactService.createUpdate(EnumRelatedObjectType.agent,theatre.getId(),tripReq.getContacts());

          theatre.setContact(contact);
        }

/*
        if(theatre.getContact()!= null){

            Contact contact = theatre.getContact();
            contact.setIdentifiers(tripReq.getContacts().stream().map(e->{
                Identifier identifier = new Identifier();
                identifier.setContact(contact);
                identifier.setType(e.getType());
                identifier.setStatus(EnumChannelStatus.subscribed);
                identifier.setLinkId(e.getId());
                return identifier;
            }).collect(Collectors.toList()));


        }else{

            Contact contact = new Contact();
            contact.setIdentifiers(tripReq.getContacts().stream().map(e->{
                Identifier identifier = new Identifier();
                identifier.setContact(contact);
                identifier.setType(e.getType());
                identifier.setStatus(EnumChannelStatus.subscribed);
                identifier.setLinkId(e.getId());
                return identifier;
            }).collect(Collectors.toList()));

            theatre.setContact(contact);
        }
*/


        theatre.getProducts().addAll(tripReq.getProducts().stream().map(e->{

            AgentEditReq.ProductReq productReq = e;


            Optional<PricingRate> pricingRateOptional = pricingTypeRepository.findById(e.getSku());

         //   Optional<Product> optionalProduct = productRepository.findById(e.getId());
            if(pricingRateOptional.isEmpty()){
                return null;
            }
            PricingRate pricingRate = pricingRateOptional.get();

            Optional<Product> optionalProduct = productRepository.findById(pricingRate.getProductId());
            if(optionalProduct.isEmpty()){
                return null;
            }

            Product product = optionalProduct.get();


            Optional<AgentProduct> a =  agentProductRepository.findById(new AgentProductKey(finalTheatre.getId(), e.getId()));

            if(a.isEmpty()){
                AgentProduct agentProduct = new AgentProduct();

                agentProduct.setAgent(finalTheatre);
                agentProduct.setNet(productReq.getNet());
                agentProduct.setOriginal(productReq.getRetail());
                agentProduct.setMarket(productReq.getMarket());
                agentProduct.setSku(pricingRate.getId());


                agentProduct.setProduct(product);

                agentProduct.setId(new AgentProductKey(finalTheatre.getId(), product.getId()));
                return agentProduct;

            }else{
                AgentProduct stopRegistration = a.get();
                stopRegistration.setNet(productReq.getNet());
                stopRegistration.setSku(productReq.getSku());
                stopRegistration.setOriginal(productReq.getRetail());
                stopRegistration.setMarket(productReq.getMarket());
                return stopRegistration;
            }


        }).filter(e->e !=null).collect(Collectors.toList()));


        theatre = agentRepository.save(theatre);


        if(tripReq.getLogo()!=null && tripReq.getLogo().getCode()!=null){
            fileStorageService.updateFromTempDocument(theatre.getCode(),tripReq.getLogo(), EnumDocumentType.agent_logo);

        }




        return theatre;
    }

    public Optional<Pair<Agent,Supplier>> find(String pid) {


        Optional<Agent> agentOptional = agentRepository.findByPartnerId(pid);

        if(agentOptional.isPresent()){
            Optional<Supplier> supplier = supplierRepository.findById(agentOptional.get().getAgent());

            if(supplier.isEmpty()){
                throw new BookNotFoundException(Enumfailures.resource_not_found,"代理connection 不完整，未完善 合作机构");
            }

            return Optional.of(Pair.with(agentOptional.get(),supplier.get()));
        }else{
            return Optional.empty();
        }


    }
/*

    public ThirdParty createThirdPartyProduct(ThirdParty busRoute, List<BusStopReq> theatreReq) {


        List<Product> busStopList = productRepository.findAllById(theatreReq.stream().map(e->e.getId()).collect(Collectors.toList()));

        ThirdParty finalBusRoute = busRoute;
        busStopList.stream().forEach(e->{

            ThirdPartyProduct stopRegistration = new ThirdPartyProduct();
            stopRegistration.setThirdParty(finalBusRoute);
            stopRegistration.setProduct(e);

            stopRegistration.setId(new ThirdPartyProductKey(finalBusRoute.getId(), e.getId()));
            finalBusRoute.addProductInList(stopRegistration);

        });


        busRoute = agentRepository.save(finalBusRoute);


        return busRoute;


    }


    public Optional<Product> find(EnumThirdParty ts, Integer item_id) {

        ThirdParty thirdParty = agentRepository.findById(1l).get();
        ThirdPartyProductKey thirdPartyProductKey = new ThirdPartyProductKey(thirdParty.getId(),item_id);
        Optional<ThirdPartyProduct> optionalProduct = thirdPartyProductRepository.findById(thirdPartyProductKey);


        if(optionalProduct.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(optionalProduct.get().getProduct());

    }*/

    public Optional<Triplet<Product,PricingRate,AgentProduct>> find(EnumThirdParty ts,Agent agent, Integer item_id) {


        Optional<AgentProduct> agentProducts = agent.getProducts().stream().filter(e->e.getProduct().getId().equals(item_id.longValue())).findAny();


        if(agentProducts.isEmpty()){
            logger.error(" 供销社产品列表 {}找不到产品 {}",
                    agent.getProducts().stream().map(e->e.getProduct().getId()+"").collect(Collectors.joining(",")),item_id);
            throw new BookNotFoundException(Enumfailures.resource_not_found,String.format(" 供销社产品列表 %s 找不到产品 %s",
                    agent.getProducts().stream().map(e->e.getProduct().getId()+"").collect(Collectors.joining(",")),item_id));

        }
        AgentProduct agentProduct = agentProducts.get();

        Optional<PricingRate> pricingRateOptional = pricingTypeRepository.findById(agentProduct.getSku());
        if(pricingRateOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 sku ");


        }
        return Optional.of(Triplet.with(agentProducts.get().getProduct(),pricingRateOptional.get(),agentProduct));

    }


    public List<Pair<Product,AgentProduct>> findAll(EnumThirdParty ts, Agent agent,String key_word, PageRequest of) {


        logger.info("第三方{}对接,按照关键字 {} 请求产品列表",ts,key_word);


        List<Pair<Product,AgentProduct>> list = agent.getProducts().stream().map(e->{
            //   e.getProduct().getId()).collect(Collectors.toList())


            if(e.getMarket() == null){
                e.setMarket(e.getNet());
            }
            if(e.getOriginal() == null){
                e.setOriginal(e.getNet());
            }
            return Pair.with(e.getProduct(),e);

        }).collect(Collectors.toList());


        Page<Pair<Product,AgentProduct>> optionalProduct =new PageImpl(list,of,list.size());// productRepository.findAllByIdIn(list,of);


        return optionalProduct.getContent();

    }

    public List<Pair<Product,AgentProduct>>  findAll(EnumThirdParty ts,Agent agent, long longValue) {



        logger.error("第三方{}对接,按照ID{} 请求产品列表",ts,longValue);


        List<Pair<Product,AgentProduct>> list = agent.getProducts().stream().filter(e->e.getProduct().getId() == longValue).map(e->{
         //   e.getProduct().getId()).collect(Collectors.toList())

            if(e.getMarket() == null){
                e.setMarket(e.getNet());
            }
            if(e.getOriginal() == null){
                e.setOriginal(e.getNet());
            }

            return Pair.with(e.getProduct(),e);

                        }).collect(Collectors.toList());


        return list;
    }
}
