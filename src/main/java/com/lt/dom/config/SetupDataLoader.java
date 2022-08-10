package com.lt.dom.config;

//https://www.baeldung.com/role-and-privilege-for-spring-security-registration

import com.alibaba.fastjson.JSONObject;
import com.lt.dom.RealNameAuthentication.RealNameAuthenticationServiceImpl;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.CompaignPojo;
import com.lt.dom.otcReq.RealnameAuthsReq;
import com.lt.dom.otcReq.ScenarioReq;
import com.lt.dom.otcReq.UserPojo;
import com.lt.dom.otcenum.EnumRuleAttributes;
import com.lt.dom.otcenum.EnumRuleConditionType;
import com.lt.dom.otcenum.EnumRuleOperator;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.CampaignServiceImpl;
import com.lt.dom.serviceOtc.UserServiceImpl;
import com.lt.dom.serviceOtc.VoucherAsyncServiceImpl;
import com.lt.dom.serviceOtc.VoucherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;
 
    @Autowired
    private RoleRepository roleRepository;
 
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
    private VoucherServiceImpl voucherService;

    @Autowired
    private RealNameAuthenticationServiceImpl realNameAuthenticationService;

    @Autowired
    private RuleRepository ruleRepository;
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
 
        if (alreadySetup)
            return;
        Privilege readPrivilege
          = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
          = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
 
        List<Privilege> adminPrivileges = Arrays.asList(
          readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
        createRoleIfNotFound("ROLE_NOT_REAL_NAME", adminPrivileges);



        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User user = new User();
        user.setFirst_name("Test");
        user.setLast_name("Test");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("test@test.com");
        user.setRoles(new HashSet(Arrays.asList(adminRole)));
        user.setPhone("1");
        user.setEnabled(true);
        userRepository.save(user);

        alreadySetup = true;




        ScenarioReq scenarioReq = new ScenarioReq();
        scenarioReq.setName("畅游消费券");
        scenarioReq.setNote("A景区、乡村旅游示范村\\r“十百千”工程文化产业示范基地\\r共计28家");
        campaignService.createScenario(scenarioReq);

        scenarioReq = new ScenarioReq();
        scenarioReq.setName("剧院消费券");
        scenarioReq.setNote("剧院,榆林大剧院");
        campaignService.createScenario(scenarioReq);

        scenarioReq = new ScenarioReq();
        scenarioReq.setName("旅行消费券");
        scenarioReq.setNote("在榆注册旅行社");
        campaignService.createScenario(scenarioReq);

        scenarioReq = new ScenarioReq();
        scenarioReq.setName("清爽消费券");
        scenarioReq.setNote("适用于市县 31 家电影院,1、影院推出300元面值卡，政府补贴60元，\\r客户自付10元，影院匹配30元\\r（对于客人来说等于100当做300花");
        campaignService.createScenario(scenarioReq);

       // Openid openid = new Openid();
       // openid.setOpenid("odzgk0ZpWx4NpQC4B1Tu1hnTgYwE");
       // openidRepository.save(openid);




        UserPojo userPojo = new UserPojo();
        userPojo.setFirst_name("wxlinkUserReq.getFirst_name()");
        userPojo.setLast_name("wxlinkUserReq.getLast_name()");
        userPojo.setUsername("wxlinkUserReq.getUser_name()");
        userPojo.setPhone("13468801684");
        userPojo.setPassword("wxlinkUserReq.getUser_password()");
        userPojo.setRoles(Arrays.asList("ROLE_ADMIN"));
        user = userService.createUser(userPojo);


        CompaignPojo compaignPojo = JSONObject.parseObject("{\"name\":\"天天\",\"category\":\"畅游消费券\",\"name_long\":\"描述\",\"claim_text\":\"一分领券\",\"claim_note\":\"一分领券\",\"clain_limit\":1,\"description\":\"我和我的祖国\",\"scenario\":1,\"campaignType\":\"LOYALTY_PROGRAM\",\"vouchers_count\":2,\"start_date\":\"2021-09-11\",\"active\":true,\"claimable\": true,\"expiry_days\":2,\"expiration_date\":\"2021-09-11\",\"code_config\":{\"charset\": \"0123456789\",\"prefix\":\"\",\"postfix\":\"\",\"pattern\":\"TT-NI-############\",\"category\":\"AMOUNT\"}, \"voucher\":{\"type\":\"DISCOUNT_VOUCHER\",\"category\":\"AMOUNT\",\"amount_off\":\"60\"}}" +
                "",CompaignPojo.class);

        Optional<Scenario> scenario = scenarioRepository.findById(compaignPojo.getScenario());

        System.out.println("dddddddddd777773333333333333333333333555555555555577777777777777777777777");
        Campaign booking = voucherService.createLoyaltyCompaign(compaignPojo,scenario.get());
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
}