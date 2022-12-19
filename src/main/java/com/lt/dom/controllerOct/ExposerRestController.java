package com.lt.dom.controllerOct;

import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.SeckillServiceServiceImpl;
import com.lt.dom.serviceOtc.FulfillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oct")
public class ExposerRestController {


    @Autowired
    private SeckillServiceServiceImpl seckillServiceService;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private FulfillServiceImpl vonchorService;
    @Autowired
    private AvailabilityServiceImpl availabilityService;


    @GetMapping(value = "/{goodsId}/getUrl")
    public Exposer exposer(@PathVariable("goodsId") Long seckillGoodsId)
    {
        //goodsId表示是什么商品，然后根据该商品的数据库依次获得尚未被秒杀的每个商品的唯一ID，然后根据商品的唯一ID来生成唯一的秒杀URL
      //  seckillGoodsId为某个商品的唯一id
       // 其中这一步可以省，之间将goodsId表示的传递给exportSeckillUrl也可以完成
        //异常判断省掉，返回上述的model对象。即包含md5的对象
        System.out.println("dddddddddddddd");
        Exposer result =seckillServiceService.exportSeckillUrl(seckillGoodsId);
        System.out.println("dddddddddddddd");
        return result;
    }



    @PostMapping(value = "/{seckillGoodsId}/{md5}/execution")
    public Boolean execution(@PathVariable("seckillGoodsId") Long seckillGoodsId,@PathVariable("md5") String md5){
        Boolean result = seckillServiceService.executionSeckillId(seckillGoodsId,md5);
        //executionSeckillId的操作是强事务,操作为减库存+增加购买明细，最终返回是否秒杀成功，秒杀成功的商品消息等。这里省，只返回是否接口合理的信息。
        return  result;
    }



}