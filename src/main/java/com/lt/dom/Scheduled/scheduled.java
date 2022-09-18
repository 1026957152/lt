package com.lt.dom.Scheduled;


import com.lt.dom.credit.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.IdGenServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class scheduled {
    private static final Logger logger = LoggerFactory.getLogger(scheduled.class);
    @Autowired
    信贷Repository 信贷Repository;

    @Autowired
    _信合_信用等级信息Repository  _信合_信用等级信息Repository;

    @Autowired
    com.lt.dom.repository._信合_税票信息Repository _信合_税票信息Repository;
    @Autowired
    com.lt.dom.repository._信合_欠税信息Repository _信合_欠税信息Repository;
    @Autowired
    com.lt.dom.repository._信合_纳税信息Repository _信合_纳税信息Repository;
    @Autowired
    _信合_非正常户信息Repository _信合_非正常户信息Repository;
    @Autowired
    _信合_连续缴税周期信息Repository _信合_连续缴税周期信息Repository;


    @Autowired
    Request授信Repository request授信Repository;
    @Autowired
    StepRepository stepRepository;

    @Autowired
    IdGenServiceImpl idGenService;

  //  @Scheduled(fixedDelay = 500000)
   // @Scheduled(cron = "${erwin.cron:0/2 * * * * ?}")
    public void cronTaskYmlDemo() {

        RequestCredit requestCredit = new RequestCredit();
        requestCredit.setCredit_NSRMC_企业名称("榆林市林荣林业科技有限公司");
        requestCredit.setCreditSHXYDM统一社会信用代码("91610800MA7033PJ9M");
        requestCredit.setCreated_at(LocalDateTime.now());
        requestCredit.setStatus(EnumCreditStatus.请求);
        requestCredit.setVolume_up_授权额度(0);
        requestCredit.setCredit_leader_name_联系人姓名("赵远");
        requestCredit.setCredit_leader_phone_联系人电话("13468801683");

        requestCredit.setCode(idGenService.requestCreditNo());

        requestCredit.setCredit_bussiness_经营范围("1、贸易及相关经营范围 建筑材料、日用百货、机械设备、机电设备、电子产品、床上用品、化妆品、玩具、工艺品、食品等产品的销售。 2、贸易公司可兼营的经营范围 投资管理咨询，礼仪服务，公关活动策划，电脑图文设计制作，企业形象策划，商务咨询，企业管理咨询，等等。信息技术领域的技术开发、技术服务、技术咨询，等等。");
        requestCredit.setCredit_bussiness_location_注册地址("陕西省榆林市红石峡镇北台");

        requestCredit.setCredit_行业类别("计算机互联网");
        requestCredit.setCredit_product_type_信贷产品类型("小微税贷");
        requestCredit = request授信Repository.save(requestCredit);


        stepRepository.save(new CreditStep(requestCredit,LocalDateTime.now(),"信易贷",EnumCreditAction.新建));

        {
            _信合_信用等级信息 s_ = new _信合_信用等级信息(requestCredit);


            s_.set信用等级信息_ID_id("350D82860DFA47A58CDDBDE27FF05684");
            s_.set信用等级信息_NSRMC_企业名称("榆林市林荣林业科技有限公司");
            s_.set信用等级信息_SHXYDM_统一社会信用代码("91610800MA7033PJ9M");
            s_.set信用等级信息_PJND_评审年度("B");
            s_.set信用等级信息_PDJB_纳税信用等级("2020");

            _信合_信用等级信息Repository.save(s_);
        }

        {


            Credit_信合_税票信息 s_ = new Credit_信合_税票信息(requestCredit);
            s_.set税票信息_ID_id("D153A07DB49A485A8AD998A8FE17A25D");
            s_.set税票信息_XFMC_销售名称("D153A07DB49A485A8AD998A8FE17A25D");
            s_.set税票信息_XFSBH_销售识别号("D153A07DB49A485A8AD998A8FE17A25D");
            s_.set税票信息_XFHYDM_销售行业代码("D153A07DB49A485A8AD998A8FE17A25D");
            s_.set税票信息_GFMC_购买名称("D153A07DB49A485A8AD998A8FE17A25D");
            s_.set税票信息_GFSBH_购买识别号("D153A07DB49A485A8AD998A8FE17A25D");
            s_.set税票信息_GFHYDM_购买行业代码("D153A07DB49A485A8AD998A8FE17A25D");
            s_.set税票信息_SPBM_商品编码("D153A07DB49A485A8AD998A8FE17A25D");
            s_.set税票信息_SPMC_商品名称("D153A07DB49A485A8AD998A8FE17A25D");
            s_.set税票信息_JE_同类商品月销售金额("D153A07DB49A485A8AD998A8FE17A25D");
            s_.set税票信息_KPCS_月开票次数("D153A07DB49A485A8AD998A8FE17A25D");
            _信合_税票信息Repository.save(s_);
        }


        {
            Credit_信合_税票信息 s_ = new Credit_信合_税票信息(requestCredit);
            s_.set税票信息_ID_id("D153A07DB49A485A8AD998A8FE17A25D");
            s_.set税票信息_XFMC_销售名称("榆林市计量技术研究院");
            s_.set税票信息_XFHYDM_销售行业代码("7499");
            s_.set税票信息_GFMC_购买名称("神木市奔腾汽车加气有限责任公司");
            s_.set税票信息_GFSBH_购买识别号("916108215835270064");
            s_.set税票信息_GFHYDM_购买行业代码("4511");
            s_.set税票信息_SPBM_商品编码("3040602990000000000");
            s_.set税票信息_SPMC_商品名称("其他鉴证服务");
            s_.set税票信息_JE_同类商品月销售金额("4992.45");
            s_.set税票信息_KPCS_月开票次数("1");
            s_.set税票信息_KPYF_开票月份("2022-01");
            _信合_税票信息Repository.save(s_);

        }

        {
            Credit_信合_税票信息 s_ = new Credit_信合_税票信息(requestCredit);
            s_.set税票信息_ID_id("5D8D70449A624E41B386DF3FC9D48410");
            s_.set税票信息_XFMC_销售名称("榆林市计量技术研究院");
            s_.set税票信息_XFHYDM_销售行业代码("7499");
            s_.set税票信息_GFMC_购买名称("神木市凯泰鑫能源运销有限公司");
            s_.set税票信息_GFSBH_购买识别号("91610821694922463");
            s_.set税票信息_GFHYDM_购买行业代码("0610");
            s_.set税票信息_SPBM_商品编码("3040602990000000000");
            s_.set税票信息_SPMC_商品名称("其他鉴证服务");
            s_.set税票信息_JE_同类商品月销售金额("1783.02");
            s_.set税票信息_KPCS_月开票次数("1");
            s_.set税票信息_KPYF_开票月份("2022-01");
            _信合_税票信息Repository.save(s_);
        }


        {
            _信合_欠税信息 s_ = new _信合_欠税信息(requestCredit);
            s_.set欠税信息_ID_id("AE968D8A0E443D5A73643378FCDFE27");
            s_.set欠税信息_DJXH_登记序号("10216108000000036464");
            s_.set欠税信息_NSRMC_企业名称("榆林市横山区砼砼鑫建筑工程有限公司");
            s_.set欠税信息_SHXYDM_统一社会信用代码("91610823MA70C5LF5K");
            s_.set欠税信息_SFQS_当前是否欠税("是");
            s_.set欠税信息_QSJE_欠税金额("0");
            s_.set欠税信息_NDYF_月份("2022-03");
            _信合_欠税信息Repository.save(s_);
        }

        {
            _信合_欠税信息 s_ = new _信合_欠税信息(requestCredit);
            s_.set欠税信息_ID_id("1458D5D8F6D74EF4BF0F1EADD5D6FEF5");
            s_.set欠税信息_DJXH_登记序号("10216108000000036464");
            s_.set欠税信息_NSRMC_企业名称("榆林市横山区砼砼鑫建筑工程有限公司");
            s_.set欠税信息_SHXYDM_统一社会信用代码("91610823MA70C5LF5K");
            s_.set欠税信息_SFQS_当前是否欠税("是");
            s_.set欠税信息_QSJE_欠税金额("0");
            s_.set欠税信息_NDYF_月份("2022-01");
            _信合_欠税信息Repository.save(s_);
        }


        {
            _信合_欠税信息 s_ = new _信合_欠税信息(requestCredit);
            s_.set欠税信息_ID_id("67627D45A383427ABFB5BE5707E4E2C7");
            s_.set欠税信息_DJXH_登记序号("10216108000000036464");
            s_.set欠税信息_NSRMC_企业名称("榆林市横山区砼砼鑫建筑工程有限公司");
            s_.set欠税信息_SHXYDM_统一社会信用代码("91610823MA70C5LF5K");
            s_.set欠税信息_SFQS_当前是否欠税("是");
            s_.set欠税信息_QSJE_欠税金额("0");
            s_.set欠税信息_NDYF_月份("2022-02");
            _信合_欠税信息Repository.save(s_);
        }


        {
            _信合_纳税信息 s_ = new _信合_纳税信息(requestCredit);
            s_.set纳税信息_ID_id("0F7CCDE2B2874060A90D5C87F690436C");
            s_.set纳税信息_NSRMC_企业名称("榆林市公安局交通警察支队绕城快速路大队");
            s_.set纳税信息_SHXYDM_统一社会信用代码("11610800353683783W");
            s_.set纳税信息_ZZSYNSE_增值税应纳税金额_万元_("0");
            s_.set纳税信息_ZZSSJJE_增值税实际缴税金额_万元_("0");
            s_.set纳税信息_SDSYNSE_企业所得税应纳税金额_万元_("0");
            s_.set纳税信息_SDSSJJE_企业所得税实际缴税金额_万元_("0");
            s_.set纳税信息_RKYF_缴税日期("2019-03");
            _信合_纳税信息Repository.save(s_);
        }
        {
            _信合_纳税信息 s_ = new _信合_纳税信息(requestCredit);
            s_.set纳税信息_ID_id("CEA003E63CB45ABBD30A63810D44919");
            s_.set纳税信息_NSRMC_企业名称("榆林市公安局交通警察支队绕城快速路大队");
            s_.set纳税信息_SHXYDM_统一社会信用代码("11610800353683783W");
            s_.set纳税信息_ZZSYNSE_增值税应纳税金额_万元_("0");
            s_.set纳税信息_ZZSSJJE_增值税实际缴税金额_万元_("0");
            s_.set纳税信息_SDSYNSE_企业所得税应纳税金额_万元_("0");
            s_.set纳税信息_SDSSJJE_企业所得税实际缴税金额_万元_("0");
            s_.set纳税信息_RKYF_缴税日期("2019-05");
            _信合_纳税信息Repository.save(s_);
        }


        {
            _信合_纳税信息 s_ = new _信合_纳税信息(requestCredit);
            s_.set纳税信息_ID_id("40F48E3F5DCE49619391FB8677CE90F7");
            s_.set纳税信息_NSRMC_企业名称("榆林市公安局交通警察支队绕城快速路大队");
            s_.set纳税信息_SHXYDM_统一社会信用代码("11610800353683783W");
            s_.set纳税信息_ZZSYNSE_增值税应纳税金额_万元_("0");
            s_.set纳税信息_ZZSSJJE_增值税实际缴税金额_万元_("0");
            s_.set纳税信息_SDSYNSE_企业所得税应纳税金额_万元_("0");
            s_.set纳税信息_SDSSJJE_企业所得税实际缴税金额_万元_("0");
            s_.set纳税信息_RKYF_缴税日期("2019-08");
            _信合_纳税信息Repository.save(s_);
        }


        {
            _信合_连续缴税周期信息 s_ = new _信合_连续缴税周期信息(requestCredit);
            s_.set连续缴税周期信息_ID_id("FE1A33A4F9F541098CECD11C161119BC");
            s_.set连续缴税周期信息_SHXYDM_统一社会信用代码("11610800MB2913874N");
            s_.set连续缴税周期信息_NSRMC_企业名称("榆林市文化和旅游局");
            s_.set连续缴税周期信息_LYYF_连续正常缴税周期_月_("0");
            s_.set连续缴税周期信息_ZJRKYF_月份("");
            s_.set连续缴税周期信息_JSSJ_计算时间("1647792000000");
            _信合_连续缴税周期信息Repository.save(s_);
        }

        {
            _信合_连续缴税周期信息 s_ = new _信合_连续缴税周期信息(requestCredit);
            s_.set连续缴税周期信息_ID_id("FE1A33A4F9F541098CECD11C161119BC");
            s_.set连续缴税周期信息_SHXYDM_统一社会信用代码("11610800MB2913874N");
            s_.set连续缴税周期信息_NSRMC_企业名称("榆林市文化和旅游局");
            s_.set连续缴税周期信息_LYYF_连续正常缴税周期_月_("0");
            s_.set连续缴税周期信息_ZJRKYF_月份("");
            s_.set连续缴税周期信息_JSSJ_计算时间("1647792000000");
            _信合_连续缴税周期信息Repository.save(s_);
        }



        {
            _信合_非正常户信息 s_ = new _信合_非正常户信息(requestCredit);
            s_.set非正常户信息_ID_id("BB5EA3B77432490EB2F5816D18CCDC26");
            s_.set非正常户信息_NSRMC_纳税人名称("榆林永安汇鑫农业科技开发有限公司");
            s_.set非正常户信息_SHXYDM_社会信用代码("91610800352313262Y");
            s_.set非正常户信息_RDRQ_认定日期("1553443200000");
            s_.set非正常户信息_SFFZC_是否非正常("是");
            _信合_非正常户信息Repository.save(s_);
        }








        System.out.println("cron yml demo");
    }
}
