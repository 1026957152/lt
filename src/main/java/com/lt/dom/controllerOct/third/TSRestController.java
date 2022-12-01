package com.lt.dom.controllerOct.third;

import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AuthenticationFacade;
import com.lt.dom.serviceOtc.BlogServiceImpl;
import com.lt.dom.serviceOtc.CommentServiceImpl;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.thirdTS.EnumMethord;
import com.lt.dom.thirdTS.LtToTsServiceImpl;
import com.lt.dom.thirdTS.TsToLtServiceImpl;
import com.lt.dom.thirdTS.domainLtToTs.*;
import com.lt.dom.thirdTS.domainTsToLt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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
    private LtToTsServiceImpl ltToTsService;
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


    @PostMapping(value = "", produces = "application/json")
    public Object jk(@RequestBody TsReqLtBase tsReqLt产品列表) {

        System.out.println("dddddddddddd"+tsReqLt产品列表.toString());
        if(!tsReqLt产品列表.getFormat().equals("json")){
            throw new BookNotFoundException(Enumfailures.not_found,"请求 format 需要json 格式");

        }





        if (tsReqLt产品列表 instanceof TsReqLt产品列表){
            if(tsReqLt产品列表.get_pid().equals("dddddd")){
                throw new BookNotFoundException(Enumfailures.not_found,"找不断哦合作商"+tsReqLt产品列表.get_pid());
            }

            TsReqLt产品列表 tsReqLt产品列表1 = (TsReqLt产品列表) tsReqLt产品列表;

            System.out.println("TsReqLt产品列表dddddddddddd"+tsReqLt产品列表1.toString());
            LtRespToTs产品列表 ltRespToTs产品列表 = tsToLtService.getTsReqLt产品列表(tsReqLt产品列表1);

            return ltRespToTs产品列表;
        }
        if (tsReqLt产品列表 instanceof TsReqLt下单接口){
            if(tsReqLt产品列表.get_pid().equals("dddddd")){
                throw new BookNotFoundException(Enumfailures.not_found,"找不断哦合作商"+tsReqLt产品列表.get_pid());
            }

            TsReqLt下单接口 tsReqLt产品列表1 = (TsReqLt下单接口) tsReqLt产品列表;

            System.out.println("TsReqLt下单接口dddddddddddd"+tsReqLt产品列表1.toString());
            try{
                if(tsReqLt产品列表.get_pid().equals("dddddd")){
                    throw new BookNotFoundException(Enumfailures.not_found,"找不断哦合作商"+tsReqLt产品列表.get_pid());
                }
                LtRespToTs下单接口.InfoDTO infoDTO  = tsToLtService.getTsReqLt下单接口(tsReqLt产品列表1);
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





        if (tsReqLt产品列表 instanceof TsReqLt退单接口){
            if(tsReqLt产品列表.get_pid().equals("dddddd")){
                throw new BookNotFoundException(Enumfailures.not_found,"找不断哦合作商"+tsReqLt产品列表.get_pid());
            }

            TsReqLt退单接口 tsReqLt产品列表1 = (TsReqLt退单接口) tsReqLt产品列表;

            System.out.println("TsReqLt退单接口dddddddddddd"+tsReqLt产品列表1.toString());
            TsReqLt退单接口 tsReqLt退单接口 =new TsReqLt退单接口();// tsToLtService.getTsReqLt产品列表(tsReqLt产品列表1);

            try{
                if(tsReqLt产品列表.get_pid().equals("dddddd")){
                    throw new BookNotFoundException(Enumfailures.not_found,"找不断哦合作商"+tsReqLt产品列表.get_pid());
                }
                LtRespToTs退单接口.InfoDTO infoDTO = tsToLtService.getTsReqLt退单接口(tsReqLt退单接口);


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






        if (tsReqLt产品列表 instanceof TsReqLt重发接口_修改订单.ToTsReqLt重发接口_修改订单){
            if(tsReqLt产品列表.get_pid().equals("dddddd")){
                throw new BookNotFoundException(Enumfailures.not_found,"找不断哦合作商"+tsReqLt产品列表.get_pid());
            }

            TsReqLt重发接口_修改订单.ToTsReqLt重发接口_修改订单 tsReqLt产品列表1 = (TsReqLt重发接口_修改订单.ToTsReqLt重发接口_修改订单) tsReqLt产品列表;

            System.out.println("TsReqLt重发接口_修改订单dddddddddddd"+tsReqLt产品列表1.toString());
            LtRespToTs产品列表 ltRespToTs产品列表 =new LtRespToTs产品列表();// tsToLtService.getTsReqLt产品列表(tsReqLt产品列表1);

            return ltRespToTs产品列表;
        }
        return null;



    }

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
    public LtRespToTs重发接口_修改订单 item_orders_modify(@RequestBody TsReqLt重发接口_修改订单 tsReqLt产品列表) {


        if(tsReqLt产品列表.get_pid().equals("dddddd")){
            throw new BookNotFoundException(Enumfailures.not_found,"找不断哦合作商"+tsReqLt产品列表.get_pid());
        }

        EnumMethord enumMethord = EnumMethord.valueOf(tsReqLt产品列表.getMethod());


        if(!enumMethord.equals(EnumMethord.item_orders)){
            return null;
        }

        if(!tsReqLt产品列表.getFormat().equals("json")){
            return null;
        }


        TsReqLt重发接口_修改订单.ToTsReqLt重发接口_修改订单 toTsReqLt重发接口_修改订单 = new TsReqLt重发接口_修改订单.ToTsReqLt重发接口_修改订单();

        toTsReqLt重发接口_修改订单.setName("4"); //审核结果： 3退票成功，4退票不通过
  //      toTsReqLt重发接口_修改订单.setSerial_no(""); //退票记录id（申请退票有传此数据时，会返回申请退票时传入的流水号）
  //     toTsReqLt重发接口_修改订单.setMessage("ddd"); ;//本平台订单ID（天时同城）
        toTsReqLt重发接口_修改订单.setOrders_id(1);  //管理员审核备注/说明

        LtRespToTs重发接口_修改订单 ltRespToTs产品列表 = tsToLtService.getTsReqLt重发接口_修改订单(toTsReqLt重发接口_修改订单);

        return ltRespToTs产品列表;

    }



    @GetMapping(value = "/refundResult", produces = "application/json")
    public TsRespLt退单审核通知 getLtReqTs退单审核通知(@RequestBody TsReqLt产品列表 tsReqLt产品列表) {


        if(tsReqLt产品列表.get_pid().equals("dddddd")){
            throw new BookNotFoundException(Enumfailures.not_found,"找不断哦合作商"+tsReqLt产品列表.get_pid());
        }

        LtReqTs退单审核通知.ToLtReqTs退单审核通知 toLtReqTs退单审核通知 = new LtReqTs退单审核通知.ToLtReqTs退单审核通知();



        toLtReqTs退单审核通知.setType(4); //审核结果： 3退票成功，4退票不通过
        toLtReqTs退单审核通知.setSerial_no(""); //退票记录id（申请退票有传此数据时，会返回申请退票时传入的流水号）
        toLtReqTs退单审核通知.setMessage("ddd"); ;//本平台订单ID（天时同城）
        toLtReqTs退单审核通知.setOrders_id("ddd");  //管理员审核备注/说明
        TsRespLt退单审核通知 ltReqTs退单审核通知 = ltToTsService.ltReqTs退单审核通知(toLtReqTs退单审核通知);

        return ltReqTs退单审核通知;

    }



    @GetMapping(value = "/item_orders_modify", produces = "application/json")
    public TsRespLt验证核销通知 ltReqTs验证核销通知(@RequestBody TsReqLt产品列表 tsReqLt产品列表) {


        if(tsReqLt产品列表.get_pid().equals("dddddd")){
            throw new BookNotFoundException(Enumfailures.not_found,"找不断哦合作商"+tsReqLt产品列表.get_pid());
        }

        LtReqTs验证核销通知.ToLtReqTs验证核销通知 ltReqTs验证核销通知 = new LtReqTs验证核销通知.ToLtReqTs验证核销通知();




        ltReqTs验证核销通知.setAmount(4);//:当前使用数量
        ltReqTs验证核销通知.setAmount_used(1);//:累计使用数量(包含本次)
        ltReqTs验证核销通知.setAnother_orders_id("ddd"); //:本平台订单ID（天时同城）
        ltReqTs验证核销通知.setMy_orders_id("ddd"); //:第三方订单ID
        ltReqTs验证核销通知.setCodes(""); //:使用码号,多个','分割


        TsRespLt验证核销通知 ltReqTs退单审核通知 = ltToTsService.ltReqTs验证核销通知(ltReqTs验证核销通知);

        return ltReqTs退单审核通知;

    }










    @GetMapping(value = "/send", produces = "application/json")
    public TsRespLt码号推送通知 ltReqTs码号推送通知(@RequestBody TsReqLt产品列表 tsReqLt产品列表) {


        if(tsReqLt产品列表.get_pid().equals("dddddd")){
            throw new BookNotFoundException(Enumfailures.not_found,"找不断哦合作商"+tsReqLt产品列表.get_pid());
        }

        LtReqTs码号推送通知.ToLtReqTs码号推送通知 toLtReqTs码号推送通知 = new LtReqTs码号推送通知.ToLtReqTs码号推送通知();



        toLtReqTs码号推送通知.setOrders_id("this.orders_id");//本平台订单ID（天时同城）
        toLtReqTs码号推送通知.setOut_orders_id("this.out_orders_id");//out_orders_id:第三方平台订单ID
        toLtReqTs码号推送通知.setOut_code("this.out_code");//out_code:码号，存在多个码号时默认展示第一个
        toLtReqTs码号推送通知.setOut_codes("this.out_codes");//out_codes:多个码号时以英文逗号分隔(,)

        toLtReqTs码号推送通知.setQrcode_images("this.qrcode_images");//qrcode_images:二维码图片，多个用英文逗号(,)分隔
        toLtReqTs码号推送通知.setQrcode_image("this.qrcode_image");//qrcode_image:二维码图片
        toLtReqTs码号推送通知.setQrcode_href("this.qrcode_href");////qrcode_href:二维码链接

        toLtReqTs码号推送通知.setQrcode("this.qrcode");//qrcode:二维码数据(用于生成二维码图片)

        toLtReqTs码号推送通知.setOut_money_send("this.out_money_send");//out_money_send:采购发送费
        toLtReqTs码号推送通知.setOut_money_one("this.out_money_one");//out_money_one:采购单价
        toLtReqTs码号推送通知.setOut_send_content("this.out_send_content");//out_send_content:发送内容
        toLtReqTs码号推送通知.setIs_real_code("this.is_real_code");//is_real_code:是否真实码号


        TsRespLt码号推送通知 ltReqTs退单审核通知 = ltToTsService.ltReqTs码号推送通知(toLtReqTs码号推送通知);

        return ltReqTs退单审核通知;

    }

    @GetMapping(value = "/goods", produces = "application/json")
    public TsRespLt产品信息变更通知 ltReqTs产品信息变更通知(@RequestBody TsReqLt产品列表 tsReqLt产品列表) {


        if(tsReqLt产品列表.get_pid().equals("dddddd")){
            throw new BookNotFoundException(Enumfailures.not_found,"找不断哦合作商"+tsReqLt产品列表.get_pid());
        }

        LtReqTs产品信息变更通知.ToLtReqTs产品信息变更通知 toLtReqTs码号推送通知 = new LtReqTs产品信息变更通知.ToLtReqTs产品信息变更通知();



        LtReqTs产品信息变更通知 ltReqTs验证核销通知 = new LtReqTs产品信息变更通知();
        ltReqTs验证核销通知.setStatus(1); //产品状态（1：正常，12：下架）
        ltReqTs验证核销通知.setSeller_code("this.seller_code"); //产品ID


        TsRespLt产品信息变更通知 ltReqTs退单审核通知 = ltToTsService.ltReqTs产品信息变更通知(toLtReqTs码号推送通知);

        return ltReqTs退单审核通知;

    }






}