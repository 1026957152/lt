package com.lt.dom.config;

//https://www.baeldung.com/role-and-privilege-for-spring-security-registration

import com.alibaba.fastjson.JSONObject;
import com.lt.dom.RealNameAuthentication.RealNameAuthenticationServiceImpl;
import com.lt.dom.controllerOct.Axh.XhToYxdService;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.vo.SupplierPojoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {



    @Value("${setupdata.alreadySetup}")
    boolean alreadySetup ;

    @Autowired
    private UserRepository userRepository;
 
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;
 
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ScenarioRepository scenarioRepository;
    @Autowired
    private CampaignServiceImpl campaignService;
    @Autowired
    private OpenidRepository openidRepository;

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private SupplierServiceImp supplierService;
    @Autowired
    private OpenidServiceImpl openidService;


    @Autowired
    private VoucherServiceImpl voucherService;

    @Autowired
    private RealNameAuthenticationServiceImpl realNameAuthenticationService;

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private SettingServiceImpl settingService;
    @Autowired
    private PaymentServiceImpl paymentService;
    @Autowired
    private GuideServiceImpl guideService;

    @Autowired
    private ClainQuotaServiceImpl clainQuotaService;
    @Autowired
    private PassServiceImpl passService;

    @Autowired
    XhToYxdService xhToYxdService;

    @Autowired
    private ValueListServiceImpl valueListService;


    @Autowired
    private DeviceService deviceService;


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        valueListService.setupDataSupper();
        settingService.setupDataSupper();

        if (alreadySetup)
            return;


        Arrays.stream(EnumAvailableActions.values()).forEach(x->{
            Privilege readPrivilege
                    = createPrivilegeIfNotFound(x.name());
        });


        Privilege readPrivilege
          = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
          = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        Privilege Campaigns_ModifyCampaigns
                = createPrivilegeIfNotFound(EnumAvailableActions.Campaigns_ModifyCampaigns.name());


        Privilege Campaigns_CreateCampaigns
                = createPrivilegeIfNotFound(EnumAvailableActions.Campaigns_CreateCampaigns.name());



        List<Privilege> adminPrivileges = Arrays.asList(
                Campaigns_ModifyCampaigns, Campaigns_CreateCampaigns);
/*        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
        createRoleIfNotFound("ROLE_NOT_REAL_NAME", adminPrivileges);

        createRoleIfNotFound("ROLE_VOUCHER_APPROVAL", adminPrivileges);
        createRoleIfNotFound("ROLE_VOUCHER_PROCESSING", adminPrivileges);
        createRoleIfNotFound("ROLE_AP_MANAGER", adminPrivileges);
        createRoleIfNotFound("ROLE_AP_SPECIALIST", adminPrivileges);
        createRoleIfNotFound("ROLE_VOUCHER_ENTRY", adminPrivileges);*/
/*
        createRoleIfNotFound(EnumRole.ROLE_MERCHANT.name(), adminPrivileges);
        createRoleIfNotFound(EnumRole.ROLE_GOVERNMENT.name(), Arrays.asList(readPrivilege));
        createRoleIfNotFound(EnumRole.ROLE_ADMIN.name(), adminPrivileges);

        createRoleIfNotFound(EnumRole.ROLE_TRAVEL_AGENCY.name(), adminPrivileges);
*/

        Arrays.stream(EnumRole.values()).forEach(x->{

                createRoleIfNotFound(x.name(), adminPrivileges);

        });

     //   ZZ Voucher Entry
     //   ZZ Voucher Processing
/*        ZZ Voucher Approval
        ZZ Voucher Processing
                ZZ_AP_MANAGER
        ZZ_AP_SPECIALIST*/


        Role adminRole = roleRepository.findByName(EnumRole.ROLE_ADMIN.name());
        User user = new User();
        user.setFirst_name("Test");
        user.setLast_name("Test");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("test@test.com");
        user.setRoles(new HashSet(Arrays.asList(adminRole)));
        user.setPhone("13468801683");
        user.setEnabled(true);
        user.setCreated_at(LocalDateTime.now());
        userRepository.save(user);

        alreadySetup = true;




        ScenarioReq scenarioReq = new ScenarioReq();
        scenarioReq.setName("畅游消费券");
        scenarioReq.setNote("A景区、乡村旅游示范村\\r“十百千”工程文化产业示范基地\\r共计28家");
        Scenario scenario畅游消费券 = campaignService.createScenario(scenarioReq);

        scenarioReq = new ScenarioReq();
        scenarioReq.setName("剧院消费券");
        scenarioReq.setNote("剧院,榆林大剧院");
        Scenario scenario剧院消费券 = campaignService.createScenario(scenarioReq);

        scenarioReq = new ScenarioReq();
        scenarioReq.setName("旅行消费券");
        scenarioReq.setNote("在榆注册旅行社");
        Scenario scenario旅行消费券 = campaignService.createScenario(scenarioReq);

        scenarioReq = new ScenarioReq();
        scenarioReq.setName("清爽消费券");
        scenarioReq.setNote("适用于市县 31 家电影院,1、影院推出300元面值卡，政府补贴60元，\\r客户自付10元，影院匹配30元\\r（对于客人来说等于100当做300花");
        Scenario scenario清爽消费券 = campaignService.createScenario(scenarioReq);





        UserPojo userPojo = new UserPojo();
        userPojo.setFirst_name("wxlinkUserReq.getFirst_name()");
        userPojo.setLast_name("wxlinkUserReq.getLast_name()");
        userPojo.setUsername("wxlinkUserReq.getUser_name()");
        userPojo.setRealName("我是一名虚拟的导游");
        userPojo.setIdCard("612724198409210000");
        userPojo.setPhone("13468801674");
        userPojo.setPassword("123456");
        userPojo.setRoles(Arrays.asList(EnumRole.ROLE_ADMIN.name()));
        user = userService.createUser(userPojo,Arrays.asList());

        guideService.beGuide(user);


       // passService.setupData(user);



        CompaignPojo compaignPojo = JSONObject.parseObject("{\"name\":\"镇北台\",\"category\":\"畅游消费券\",\"name_long\":\"国家4A级景区\",\"claim_text\":\"免费领券\",\"claim_note\":\"免费领券\",\"clain_limit\":1,\"description\":\"我和我的祖国\",\"scenario\":1,\"campaignType\":\"LOYALTY_PROGRAM\",\"vouchers_count\":2,\"start_date\":\"2021-09-11\",\"active\":true,\"claimable\": true,\"expiry_days\":2,\"expiration_date\":\"2021-09-11\",\"code_config\":{\"charset\": \"0123456789\",\"prefix\":\"\",\"postfix\":\"\",\"pattern\":\"TT-NI-############\",\"category\":\"AMOUNT\"}, \"voucher\":{\"type\":\"DISCOUNT_VOUCHER\",\"category\":\"AMOUNT\",\"amount_off\":\"60\"}}" +
                "",CompaignPojo.class);

   //     Optional<Scenario> scenario = scenarioRepository.findById(compaignPojo.getScenario());

        System.out.println("dddddddddd777773333333333333333333333555555555555577777777777777777777777");

        Map<EnumSupplierDisplayType,Scenario> typeMap = Map.of(EnumSupplierDisplayType.畅游,scenario畅游消费券,
                EnumSupplierDisplayType.剧院,scenario剧院消费券,
                EnumSupplierDisplayType.旅行,scenario旅行消费券,
                EnumSupplierDisplayType.清爽观影券,scenario清爽消费券);


        Arrays.stream(EnumCampaign.values()).forEach(x->{
            compaignPojo.setName(x.getName());
            compaignPojo.setDescription(x.getName());
            compaignPojo.setName_long(x.getName());
            compaignPojo.setName_long(x.getName());
            compaignPojo.getVoucher().setAmount_off(x.getAmount());
            compaignPojo.setScenario(typeMap.get(x.getCategory()).getId());

            compaignPojo.setClaim_note("仅限在榆注册的旅行社，通过电脑PC端后台注册后，提前三天，提交团队信息及线路详情提报审批（每团最多50人），审批通过后上传客户名单及相关保单等信息领取相应票劵。\n" +
                    "<br>消费者通过微信小程序搜索“清爽榆林消费券”关键字，进入平台，在小程序内点击相应券种，领取对应的产品消费券。\n");
            campaignService.create(compaignPojo,typeMap.get(x.getCategory()));

        });


        RealnameAuthsReq realnameAuthsReq = new RealnameAuthsReq();
        realnameAuthsReq.setReal_name("赵源");
        realnameAuthsReq.setId_card("612724198409210339");
        realnameAuthsReq.setPhone(user.getPhone());
        realNameAuthenticationService.postWxRealnameAuths(user,realnameAuthsReq);





        Rules rule = new Rules();
        rule.setAttribute(EnumRuleAttributes.boolean_attribute);
        rule.setOperator(EnumRuleOperator.Equal_to);
        rule.setConditionType(EnumRuleConditionType.country_attribute);
        rule.setNumeric_value(1l);
        rule = ruleRepository.save(rule);



        paymentService.setupData();


        settingService.setupData();


        AssetAddReq assetAddReq = new AssetAddReq();
        assetAddReq.setName("根设备");
        assetAddReq.setFull_name("根设备");
        deviceService.setupdata(assetAddReq);

        Supplier supplier = _景区();
        _文旅局();
        _旅投超级管理员();
        _旅行社();


        ValueListReq valueListReq = JSONObject.parseObject("{\n" +
                "            \"type\": \"Vendor_groups\",\n" +
                "                \"name\": \"我的配额\",\n" +
                "                \"ids\":[1,2,3,4]\n" +
                "        }",ValueListReq.class);
        valueListReq.setItem_ids(Arrays.asList(supplier.getId()));
        valueListService.createValueListWithAdd(valueListReq);





       /* EnumClainQuotaType.supplier_list;


        quota.setType(EnumClainQuotaType.customer__list);


        clainQuotaService.createQuota()*/



        _榆林信合();
        _横山支行();
        _信易贷账号();


        xhToYxdService.setupData();
/*        try {
            JxlsServiceImpl.getAvailability("name");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
 
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(
      String name, Collection<Privilege> privileges) {
 
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }



    private void _旅投超级管理员() {

        UserPojo userPojo = new UserPojo();

        userPojo.setPhone("admin");
        userPojo.setPassword("123456");

        userPojo.setRoles(Arrays.asList(EnumRole.ROLE_ADMIN.name()));
        User user = userService.createUser(userPojo,Arrays.asList());


        SupplierPojoVo supplierPojoVo = new SupplierPojoVo();
        supplierPojoVo.setDesc("榆林市旅游投资发展有限公司成立于2018年05月16日，注册地位于陕西省榆林市高新技术产业园区榆溪大道高科城C座4层，法定代表人为赵子富。经营范围包括旅游文化产业的投资、融资；旅游文化资源和项目的策划、开发、建设、经营；旅游商品开发销售、旅游景区及园林建设、景区游览服务；国内外旅游服务、旅游酒店管理、物业管理、旅行社、旅游产品开发销售；商务咨询、会务服务、展览展示服务；广告传媒、非遗文化的传承和保护等。(依法须经批准的项目，经相关部门批准后方可开展经营活动)（依法须经批准的项目，经相关部门批准后方可开展经营活动）榆林市旅游投资发展有限公司对外投资6家公司");
        supplierPojoVo.setType(EnumSupplierType.Other);
        supplierPojoVo.setBusiness_type(EnumBussinessType.non_profit);
        supplierPojoVo.setLocation("陕西省榆林市高新技术产业园区榆溪大道高科城C座4层");
        supplierPojoVo.setLocationName("陕西省榆林市高新技术产业园区榆溪大道高科城C座4层");
        supplierPojoVo.setLat(Double.valueOf(0));
        supplierPojoVo.setLng(Double.valueOf(0));
        supplierPojoVo.setSupplierName("榆林市旅游投资发展有限公司");
        Supplier supplier = supplierService.createSupplier(supplierPojoVo,EnumSupplierStatus.Active);

        supplierService.成为员工(supplier,user);
        Openid openid = openidService.setupData("oq1Er5Y7M9J4FnM262owaZHihOgQ");
        openidService.linkUser(openid,user);

        realNameAuthenticationService.setupData(user,"程龙龙","1234567890");
    }

    private void _文旅局() {

        UserPojo userPojo = new UserPojo();

        userPojo.setPhone("wlj01");
        userPojo.setPassword("123456");
        userPojo.setRoles(Arrays.asList(EnumRole.ROLE_GOVERNMENT.name()));
        User user = userService.createUser(userPojo,Arrays.asList());


        SupplierPojoVo supplierPojoVo = new SupplierPojoVo();
        supplierPojoVo.setDesc("榆林市文化和旅游局是市政府工作部门，为正县级，加挂市文物广电局的牌子。市文化和旅游局贯彻落实党中央、省委、市委关于文化和旅游、文物、广播电视工作的方针政策和决策部署，在履行职责过程中坚持和加强党的集中统一领导");
        supplierPojoVo.setType(EnumSupplierType.Other);
        supplierPojoVo.setBusiness_type(EnumBussinessType.government_entity);
        supplierPojoVo.setLocation("陕西省榆阳区青山东路1号");
        supplierPojoVo.setLocationName("陕西省榆阳区青山东路1号");
        supplierPojoVo.setLat(Double.valueOf(0));
        supplierPojoVo.setLng(Double.valueOf(0));
        supplierPojoVo.setSupplierName("榆林市文化和旅游局");
        Supplier supplier = supplierService.createSupplier(supplierPojoVo,EnumSupplierStatus.Active);

        supplierService.成为员工(supplier,user);




        Openid openid = openidService.setupData("oq1Er5RyRMmoSt2KWSS6fhgk3DWY");
        openidService.linkUser(openid,user);

        realNameAuthenticationService.setupData(user,"龙","1234567ddddd890");


        valueListService.setupData();

    }



    private void _旅行社() {

        UserPojo userPojo = new UserPojo();

        userPojo.setPhone("lxs01");
        userPojo.setPassword("123456");
        userPojo.setRoles(Arrays.asList(EnumRole.ROLE_TRAVEL_AGENCY.name()));
        User user = userService.createUser(userPojo,Arrays.asList());


        SupplierPojoVo supplierPojoVo = new SupplierPojoVo();
        supplierPojoVo.setDesc("榆林国际旅行社有限责任公司成立于2005年09月08日，注册地位于陕西省榆林市高新技术产业园区明珠大道人民大厦一楼办公区(商务中心)，法定代表人为冯震波。经营范围包括国内外旅游服务；文化旅游咨询服务；旅游景区、项目、基础设施的规划、建设、开发、运营、管理服务；日用百货、工艺品、食品及饮料的生产与销售；赛事活动策划、会议会展服务、文化艺术交流活动、企业形象策划；酒店管理服务；企业管理服务；互联网信息服务；汽车租赁服务；舞台、灯光设计服务；音响设备的安装、租赁、销售； 航空客运销售代理服务 运输代理服务；游乐设备、环保设备、办公设备、酒店设备、体育用品及器材的安装、设计、销售及租赁服务；旅游信息、资料的编绘；摄影服务；广告设计、代理、发布。（依法须经批准的项目，经相关部门批准后方可开展经营活动）榆林国际旅行社有限责任公司对外投资1家公司，具有8处分支机构。");
        supplierPojoVo.setType(EnumSupplierType.TravelAgent);
        supplierPojoVo.setBusiness_type(EnumBussinessType.company);
        supplierPojoVo.setLocation("陕西省榆林市高新技术产业园区明珠大道人民大厦一楼办公区");
        supplierPojoVo.setLocationName("陕西省榆林市高新技术产业园区明珠大道人民大厦一楼办公区");
        supplierPojoVo.setLat(Double.valueOf(0));
        supplierPojoVo.setLng(Double.valueOf(0));
        supplierPojoVo.setSupplierName("榆林国际旅行社有限责任公司");
        Supplier supplier = supplierService.createSupplier(supplierPojoVo,EnumSupplierStatus.Active);

        supplierService.成为员工(supplier,user);

    }


    private Supplier _景区() {

        UserPojo userPojo = new UserPojo();

        userPojo.setPhone("hsx01");
        userPojo.setPassword("123456");
        userPojo.setRoles(Arrays.asList(EnumRole.ROLE_MERCHANT.name()));
        User user = userService.createUser(userPojo,Arrays.asList());


        SupplierPojoVo supplierPojoVo = new SupplierPojoVo();
        supplierPojoVo.setDesc("红石峡位于榆林市城北3公里处，距离榆林市区仅5公里。红石峡谷长约350米，峡谷东崖高约11.5米，西崖高13米，东西对峙，峭拔雄伟。峡内榆溪河水穿峡而过直达城西。古代驻守榆林的文人墨客甚至武将都喜好到红石峡题刻以抒发边塞豪情壮志，所以红石峡又是长城书法艺术的一大宝库。从题字的内容，可以看出榆林古时“九边重镇”的地位。此外，还可以欣赏到宋元时期的石窟艺术。赶上晴天，就可以一睹“红山夕照”的风采：夕阳之下如同晚霞一般绚丽的红石峡风光。是著名的“榆林八景”之一。");
        supplierPojoVo.setType(EnumSupplierType.A级景区);
        supplierPojoVo.setBusiness_type(EnumBussinessType.company);
        supplierPojoVo.setLocation("榆林市榆阳区榆阳镇北岳庙村");
        supplierPojoVo.setLocationName("榆林市榆阳区榆阳镇北岳庙村");
        supplierPojoVo.setLat(Double.valueOf(0));
        supplierPojoVo.setLng(Double.valueOf(0));
        supplierPojoVo.setSupplierName("榆林市红石峡生态公园有限责任公司");
        Supplier supplier = supplierService.createSupplier(supplierPojoVo,EnumSupplierStatus.Active);

         supplierService.成为员工(supplier,user);
        return supplier;
    }












    private void _榆林信合() {



        UserPojo userPojo = new UserPojo();

        userPojo.setPhone("yb");
        userPojo.setPassword("123456");
        userPojo.setRoles(Arrays.asList(EnumRole.ROLE_ADMIN.name(),EnumRole.ROLE_HEAD_OFFICE.name()));
        User user = userService.createUser(userPojo,Arrays.asList());


        SupplierPojoVo supplierPojoVo = new SupplierPojoVo();
        supplierPojoVo.setDesc("榆林市文化和旅游局是市政府工作部门，为正县级，加挂市文物广电局的牌子。市文化和旅游局贯彻落实党中央、省委、市委关于文化和旅游、文物、广播电视工作的方针政策和决策部署，在履行职责过程中坚持和加强党的集中统一领导");
        supplierPojoVo.setType(EnumSupplierType.Bank);
        supplierPojoVo.setBusiness_type(EnumBussinessType.company);
        supplierPojoVo.setLocation("榆阳区长城中路2号");
        supplierPojoVo.setLocationName("榆阳区长城中路2号");
        supplierPojoVo.setLat(Double.valueOf(0));
        supplierPojoVo.setLng(Double.valueOf(0));
        supplierPojoVo.setSupplierName("榆林农村商业银行");
        Supplier supplier = supplierService.createSupplier(supplierPojoVo,EnumSupplierStatus.Active);

        supplierService.成为员工(supplier,user);

    }



    private void _横山支行() {



        UserPojo userPojo = new UserPojo();

        userPojo.setPhone("hs");
        userPojo.setPassword("123456");
        userPojo.setRoles(Arrays.asList(EnumRole.ROLE_BRANCH.name()));
        User user = userService.createUser(userPojo,Arrays.asList());


        SupplierPojoVo supplierPojoVo = new SupplierPojoVo();
        supplierPojoVo.setDesc("_");
        supplierPojoVo.setType(EnumSupplierType.Bank);
        supplierPojoVo.setBusiness_type(EnumBussinessType.company);
        supplierPojoVo.setLocation("陕西省榆林市横山区横山镇迎宾路北");
        supplierPojoVo.setLocationName("陕西省榆林市横山区横山镇迎宾路北");
        supplierPojoVo.setLat(Double.valueOf(0));
        supplierPojoVo.setLng(Double.valueOf(0));
        supplierPojoVo.setSupplierName("横山支行");
        Supplier supplier = supplierService.createSupplier(supplierPojoVo,EnumSupplierStatus.Active);

        supplierService.成为员工(supplier,user);

    }



    private void _信易贷账号() {



        UserPojo userPojo = new UserPojo();

        userPojo.setPhone("admin111");
        userPojo.setPassword("admin123");
        userPojo.setRoles(Arrays.asList(EnumRole.ROLE_BRANCH.name()));
        User user = userService.createUser(userPojo,Arrays.asList());



    }
}