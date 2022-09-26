package com.lt.dom.controllerOct;

import com.google.gson.Gson;
import com.lt.dom.OctResp.ReferralResp;


import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.Referral;
import com.lt.dom.oct.User;
import com.lt.dom.otcReq.PassCreatePojo;
import com.lt.dom.otcReq.ReferralPojo;
import com.lt.dom.otcenum.EnumReferralType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.ReferralRepository;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.serviceOtc.ReferralServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.sql.Ref;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class ReferralRestController {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public ReferralRepository referralRepository;


    @Autowired
    public ReferralServiceImpl referralService;


    @PostMapping(value = "/users/{USER_ID}/referrals", produces = "application/json")
    public ReferralResp createReferral(@PathVariable long USER_ID,@RequestBody @Valid ReferralPojo pojo) {

        Optional<User> validatorOptional = userRepository.findById(USER_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
        }
        User user = validatorOptional.get();

        Referral referral = referralService.create(user,EnumReferralType.fill_up_passager_info,pojo);

        return ReferralResp.from(referral);

    }



    @GetMapping(value = "/referrals", produces = "application/json")
    public EntityModel getReferral(@RequestParam @Valid String id) {


        Optional<Referral> optionalReferral = referralRepository.findByCode(id);
        if(optionalReferral.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
        }
        Referral referral = optionalReferral.get();


        if(referral.getType().equals(EnumReferralType.fill_up_passager_info)){




            Map meta_data = new Gson().fromJson(referral.getMeta_data(), Map.class);

            ReferralResp referralResp = ReferralResp.from(referral);

            referralResp.setMessage((String)meta_data.get("message"));
            EntityModel entityModel = EntityModel.of(referralResp);
            entityModel.add(linkTo(methodOn(PassengerRestController.class).createPassenger(referral.getUser(),null)).withSelfRel());

            return entityModel;
        }

        return null;

    }


    @GetMapping(value = "/referrals/", produces = "application/json")
    public ReferralResp get(@RequestParam String  code) {  // scene 最大 32各字符


        Referral referral = referralService.getUserAndProductByScene(code);

        return ReferralResp.from(referral);
    }


    @GetMapping(value = "/users/{USER_ID}/referral_link", produces = "application/json")
    public ReferralResp getReferral_link(@PathVariable long USER_ID) {  // scene 最大 32各字符


            Optional<User> validatorOptional = userRepository.findById(USER_ID);

            if(validatorOptional.isEmpty()){
                throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
            }
            User user = validatorOptional.get();

            Referral referral = null;//referralService.getUserAndProductByScene(code);

        return ReferralResp.from(referral);
    }




    public void share() throws Exception {
        //创建图片
        BufferedImage img = new BufferedImage(750, 1334, BufferedImage.TYPE_INT_RGB);
        //开启画图
        Graphics g = img.getGraphics();
        //背景 -- 读取互联网图片
        BufferedImage back  = ImageIO.read(new URL("填写海报的背景图片链接"));
        g.drawImage(back.getScaledInstance(750, 1334, Image.SCALE_DEFAULT), 0, 0, null); // 绘制缩小后的图
        //商品  banner图
        //读取互联网图片
        BufferedImage priductUrl = ImageIO.read(new URL("填写自己的banner图，线上链接")); //TODO
        g.drawImage(priductUrl.getScaledInstance(690,516,Image.SCALE_DEFAULT),29,61,null);
        //文案标题
        g.setFont(new Font("微软雅黑", Font.BOLD, 34));
        g.setColor(new Color(29,29,29));
        //绘制文字
        g.drawString("填写文案标题", 31, 638);//TODO
        //文案
        g.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        g.setColor(new Color(47,47,47));
        int fontlen = getWatermarkLength("填写文内容", g);//TODO
        //文字长度相对于图片宽度应该有多少行
        int line = fontlen / (back.getWidth() - 90);
        //高度
        int y = back.getHeight() - (line + 1) * 30 - 500;
        //文字叠加,自动换行叠加
        int tempX = 32;
        int tempY = y;
        //单字符长度
        int tempCharLen = 0;
        //单行字符总长度临时计算
        int tempLineLen = 0;
        StringBuffer sb =new StringBuffer();
        for(int i=0; i < "填写文内容".length(); i++) {//TODO
            char tempChar = "填写文内容".charAt(i);//TODO
            tempCharLen = getCharLen(tempChar, g);
            tempLineLen += tempCharLen;
            if(tempLineLen >= (back.getWidth()-90)) {
                //长度已经满一行,进行文字叠加
                g.drawString(sb.toString(), tempX, tempY + 50);
                //清空内容,重新追加
                sb.delete(0, sb.length());
                //每行文字间距50
                tempY += 50;
                tempLineLen =0;
            }
            //追加字符
            sb.append(tempChar);
        }
        //最后叠加余下的文字
        g.drawString(sb.toString(), tempX, tempY + 50);

        //价格背景
        //读取互联网图片
        BufferedImage bground  = ImageIO.read(new URL("填写自己的价格背景图，七牛图片链接"));//TODO
        // 绘制缩小后的图
        g.drawImage(bground.getScaledInstance(160, 40, Image.SCALE_DEFAULT), 30, 1053, null);

        //限时促销价
        g.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        g.setColor(new Color(255,255,255));
        g.drawString("限时促销价", 50, 1080);

        //价格
        g.setFont(new Font("微软雅黑", Font.PLAIN, 50));
        g.setColor(new Color(249,64,64));
        g.drawString("¥" + "填写商品的价格", 29, 1162);//TODO

        //原价
        g.setFont(new Font("微软雅黑", Font.PLAIN, 36));
        g.setColor(new Color(171,171,171));
        String price = "¥" + "填写商品的原价";//TODO
        g.drawString(price, 260, 1160);
        g.drawLine(250,1148,260+150,1148);

        //商品名称
        g.setFont(new Font("微软雅黑", Font.PLAIN, 32));
        g.setColor(new Color(29,29,29));
        g.drawString("填写商品名称", 30, 1229);//TODO

        //生成二维码返回链接
        String url = "";//TODO
        //读取互联网图片
        BufferedImage qrCode  = ImageIO.read(new URL(url));
        // 绘制缩小后的图
        g.drawImage(qrCode.getScaledInstance(174, 174, Image.SCALE_DEFAULT), 536, 1057, null);

        //二维码字体
        g.setFont(new Font("微软雅黑", Font.PLAIN, 25));
        g.setColor(new Color(171,171,171));
        //绘制文字
        g.drawString("扫描或长按小程序码", 515, 1260);

        g.dispose();
        //保存到本地 生成文件名字
        String iconKey = UUID.randomUUID().toString() +".png"; //TODO
        //先将画好的海报写到本地
        String picUrl = "填写自己电脑的路径" + iconKey; // TODO
        File file = new File(picUrl);
        ImageIO.write(img, "jpg",file);
        //再将file上传至七牛返回链接存入数据库
        //end
        // TODO
    }

    /**
     * 获取水印文字总长度
     *@paramwaterMarkContent水印的文字
     *@paramg
     *@return水印文字总长度
     */
    public int getWatermarkLength(String waterMarkContent, Graphics g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(),0, waterMarkContent.length());
    }
    public int getCharLen(char c, Graphics g) {
        return g.getFontMetrics(g.getFont()).charWidth(c);
    }

}