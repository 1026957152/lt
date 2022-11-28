package com.lt.dom.controllerOct.third;

import com.lt.dom.OctResp.BlogResp;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BlogReq;
import com.lt.dom.otcReq.CommentReq;
import com.lt.dom.otcenum.EnumBookingStatus;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AuthenticationFacade;
import com.lt.dom.serviceOtc.BlogServiceImpl;
import com.lt.dom.serviceOtc.CommentServiceImpl;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.thirdTS.TsToLtServiceImpl;
import com.lt.dom.thirdTS.domainTsToLt.*;
import com.lt.dom.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/third")
public class TSRestController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileStorageServiceImpl fileStorageService;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TsToLtServiceImpl tsToLtService;
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogServiceImpl blogService;

    @Autowired
    private OpenidRepository openidRepository;




    @PostMapping(value = "/item_list", produces = "application/json")
    public LtRespToTs产品列表 item_list(@RequestBody TsReqLt产品列表 tsReqLt产品列表) {


        if(tsReqLt产品列表.get_pid().equals("dddddd")){
            throw new BookNotFoundException(Enumfailures.not_found,"找不断哦合作商"+tsReqLt产品列表.get_pid());
        }
        LtRespToTs产品列表 ltRespToTs产品列表 = tsToLtService.getTsReqLt产品列表(tsReqLt产品列表);

        return ltRespToTs产品列表;

    }


    @PostMapping(value = "/item_orders", produces = "application/json")
    public LtRespToTs下单接口 item_orders(@RequestBody TsReqLt下单接口 tsReqLt产品列表) {



        try{
            if(tsReqLt产品列表.get_pid().equals("dddddd")){
                throw new BookNotFoundException(Enumfailures.not_found,"找不断哦合作商"+tsReqLt产品列表.get_pid());
            }
            LtRespToTs下单接口.InfoDTO infoDTO  = tsToLtService.getTsReqLt下单接口(tsReqLt产品列表);
            LtRespToTs下单接口 ltRespToTs产品列表 = new LtRespToTs下单接口();

            ltRespToTs产品列表.setInfo(infoDTO);
            ltRespToTs产品列表.setSuccess(true);
            ltRespToTs产品列表.setMessage("成功");
            return ltRespToTs产品列表;
        }catch (Exception e){

            e.printStackTrace();

            LtRespToTs下单接口 ltRespToTs产品列表 = new LtRespToTs下单接口();
            ltRespToTs产品列表.setSuccess(false);
            ltRespToTs产品列表.setMessage(e.toString());
            return ltRespToTs产品列表;
        }





    }




    @PostMapping(value = "/item_refund", produces = "application/json")
    public LtRespToTs退单接口 item_refund(@RequestBody @Valid TsReqLt退单接口 tsReqLt产品列表) {


        try{
            if(tsReqLt产品列表.get_pid().equals("dddddd")){
                throw new BookNotFoundException(Enumfailures.not_found,"找不断哦合作商"+tsReqLt产品列表.get_pid());
            }
            LtRespToTs退单接口.InfoDTO infoDTO = tsToLtService.getTsReqLt退单接口(tsReqLt产品列表);


            LtRespToTs退单接口 ltRespToTs产品列表 = new LtRespToTs退单接口();

            ltRespToTs产品列表.setInfo(infoDTO);
            ltRespToTs产品列表.setSuccess(true);
            ltRespToTs产品列表.setMessage("成功");
            return ltRespToTs产品列表;


    }catch (Exception e){

        e.printStackTrace();

            LtRespToTs退单接口 ltRespToTs产品列表 = new LtRespToTs退单接口();

            ltRespToTs产品列表.setSuccess(false);
            ltRespToTs产品列表.setMessage("失败"+ e.toString());
        return ltRespToTs产品列表;
    }


    }




    @PostMapping(value = "/item_orders_modify", produces = "application/json")
    public LtRespToTs产品列表 item_orders_modify(@RequestBody TsReqLt产品列表 tsReqLt产品列表) {


        if(tsReqLt产品列表.get_pid().equals("dddddd")){
            throw new BookNotFoundException(Enumfailures.not_found,"找不断哦合作商"+tsReqLt产品列表.get_pid());
        }
        LtRespToTs产品列表 ltRespToTs产品列表 = tsToLtService.getTsReqLt产品列表(tsReqLt产品列表);

        return ltRespToTs产品列表;

    }




}