package com.lt.dom.state;


import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;


@Configuration
//@EnableStateMachine
@EnableStateMachineFactory

public class SimpleEnumStateMachineConfiguration extends StateMachineConfigurerAdapter<ApplicationReviewStates, ApplicationReviewEvents> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<ApplicationReviewStates, ApplicationReviewEvents> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(new StateMachineListener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<ApplicationReviewStates, ApplicationReviewEvents> states) throws Exception {
        states
                .withStates()
                .initial(ApplicationReviewStates.DRAFT)
                .state(ApplicationReviewStates.APPLY)
                .state(ApplicationReviewStates.AWAITING_DETAIL_APPLY)
                .state(ApplicationReviewStates.AWAITING_REDEEMING_旅客游玩中)
                .state(ApplicationReviewStates.APPLY_FUND)
                .state(ApplicationReviewStates.AWAITING_PAYMENT)
                .end(ApplicationReviewStates.FUND_DONE);

    }

    @Override
    public void configure(StateMachineTransitionConfigurer<ApplicationReviewStates, ApplicationReviewEvents> transitions) throws Exception {
        transitions.withExternal()
                .source(ApplicationReviewStates.DRAFT).target(ApplicationReviewStates.APPLY).event(ApplicationReviewEvents.旅行社_申请)
                .and().withExternal()
                .source(ApplicationReviewStates.APPLY).target(ApplicationReviewStates.AWAITING_DETAIL_APPLY).event(ApplicationReviewEvents.旅投_第一次审核)
                .and().withExternal()
                .source(ApplicationReviewStates.APPLY).target(ApplicationReviewStates.DRAFT).event(ApplicationReviewEvents.旅投_第一次审核拒绝)
                .and().withExternal()

                .source(ApplicationReviewStates.AWAITING_DETAIL_APPLY).target(ApplicationReviewStates.DETAIL_APPLY).event(ApplicationReviewEvents.旅行社_上报详细客源后_再次提交审核)
                .and().withExternal()

                .source(ApplicationReviewStates.DETAIL_APPLY).target(ApplicationReviewStates.AWAITING_REDEEMING_旅客游玩中).event(ApplicationReviewEvents.旅投_第二次审核通过)
                .and().withExternal()
                .source(ApplicationReviewStates.DETAIL_APPLY).target(ApplicationReviewStates.AWAITING_DETAIL_APPLY).event(ApplicationReviewEvents.旅投第二次审核拒绝)
                .and().withExternal()

                .source(ApplicationReviewStates.AWAITING_REDEEMING_旅客游玩中).target(ApplicationReviewStates.APPLY_FUND).event(ApplicationReviewEvents.旅行社_提交结算申请)

                .and().withExternal()
                .source(ApplicationReviewStates.APPLY_FUND).target(ApplicationReviewStates.AWAITING_PAYMENT).event(ApplicationReviewEvents.旅投_结算审核通过)

                .and().withExternal()
                .source(ApplicationReviewStates.APPLY_FUND).target(ApplicationReviewStates.AWAITING_REDEEMING_旅客游玩中).event(ApplicationReviewEvents.旅投_计算审核拒绝)

                .and().withExternal()
                .source(ApplicationReviewStates.AWAITING_PAYMENT).target(ApplicationReviewStates.FUND_DONE).event(ApplicationReviewEvents.财政局_拨付款)



        ;
    }
}