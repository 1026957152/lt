/*
package com.lt.dom.state;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;

@Configuration
public class StateMachineJpaConfig {
*/
/*
*
     * StateMachineRuntimePersister为状态机运行时持久化配置
     * @param jpaStateMachineRepository
     * @return
*//*



    @Bean
    public StateMachineRuntimePersister<ApplicationReviewStates, ApplicationReviewEvents, String> stateMachineRuntimePersister(
            JpaStateMachineRepository jpaStateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
    }
*/
/**
     * StateMachineService为状态状态机持久化控制,用于获取或关闭状态机
     * @param stateMachineFactory
     * @param stateMachineRuntimePersister
     * @return*//*



    @Bean
    public StateMachineService<ApplicationReviewStates, ApplicationReviewEvents> stateMachineService(
            StateMachineFactory<ApplicationReviewStates, ApplicationReviewEvents> stateMachineFactory,
            StateMachineRuntimePersister<ApplicationReviewStates, ApplicationReviewEvents, String> stateMachineRuntimePersister) {
        return new DefaultStateMachineService<ApplicationReviewStates, ApplicationReviewEvents>(stateMachineFactory, stateMachineRuntimePersister);
    }
}
*/
