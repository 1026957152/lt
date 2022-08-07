package com.lt.dom.config;

//https://www.baeldung.com/role-and-privilege-for-spring-security-registration

import com.lt.dom.oct.Openid;
import com.lt.dom.oct.Privilege;
import com.lt.dom.oct.Role;
import com.lt.dom.oct.User;
import com.lt.dom.otcReq.ScenarioReq;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.CampaignServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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