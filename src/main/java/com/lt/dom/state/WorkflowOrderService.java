package com.lt.dom.state;

import com.lt.dom.oct.TourBooking;
import com.lt.dom.repository.TourBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public
class WorkflowOrderService {

	@Autowired
	private TourBookingRepository tourBookingRepository;

	@Autowired
	private StateMachineFactory<ApplicationReviewStates, ApplicationReviewEvents> factory;


	private static final String ORDER_ID_HEADER = "orderId";



	public TourBooking byId(Long id) {
		return this.tourBookingRepository.findById(id).get();
	}




	public TourBooking 旅行社新建(Date when,TourBooking tourBooking) {
		tourBooking.setTest_Status(ApplicationReviewStates.DRAFT);
		return this.tourBookingRepository.save(tourBooking);
	}


	public StateMachine<ApplicationReviewStates, ApplicationReviewEvents> 旅行社_申请(Long orderId, String paymentConfirmationNumber) {
		StateMachine<ApplicationReviewStates, ApplicationReviewEvents> sm = this.build(orderId);

		Message<ApplicationReviewEvents> paymentMessage = MessageBuilder.withPayload(ApplicationReviewEvents.旅行社_申请)
				.setHeader(ORDER_ID_HEADER, orderId)
				.setHeader("paymentConfirmationNumber", paymentConfirmationNumber)
				.build();

		sm.sendEvent(paymentMessage);
		// todo
		return sm;
	}


	public StateMachine<ApplicationReviewStates, ApplicationReviewEvents> 旅投_第一次审核(Long orderId, String paymentConfirmationNumber) {
		StateMachine<ApplicationReviewStates, ApplicationReviewEvents> sm = this.build(orderId);

		Message<ApplicationReviewEvents> paymentMessage = MessageBuilder.withPayload(ApplicationReviewEvents.旅投_第一次审核)
				.setHeader(ORDER_ID_HEADER, orderId)
				.setHeader("paymentConfirmationNumber", paymentConfirmationNumber)
				.build();

		sm.sendEvent(paymentMessage);
		// todo
		return sm;
	}

	public StateMachine<ApplicationReviewStates, ApplicationReviewEvents> 旅行社_上报详细客源(Long orderId, String paymentConfirmationNumber) {
		StateMachine<ApplicationReviewStates, ApplicationReviewEvents> sm = this.build(orderId);

		Message<ApplicationReviewEvents> paymentMessage = MessageBuilder.withPayload(ApplicationReviewEvents.旅行社_上报详细客源后_再次提交审核)
				.setHeader(ORDER_ID_HEADER, orderId)
				.setHeader("paymentConfirmationNumber", paymentConfirmationNumber)
				.build();

		sm.sendEvent(paymentMessage);
		// todo
		return sm;
	}

	public StateMachine<ApplicationReviewStates, ApplicationReviewEvents> 旅投_第二次审核(Long orderId, String paymentConfirmationNumber) {
		StateMachine<ApplicationReviewStates, ApplicationReviewEvents> sm = this.build(orderId);

		Message<ApplicationReviewEvents> paymentMessage = MessageBuilder.withPayload(ApplicationReviewEvents.旅投_第二次审核通过)
				.setHeader(ORDER_ID_HEADER, orderId)
				.setHeader("paymentConfirmationNumber", paymentConfirmationNumber)
				.build();

		sm.sendEvent(paymentMessage);
		// todo
		return sm;
	}

	public StateMachine<ApplicationReviewStates, ApplicationReviewEvents> 旅投第二次审核拒绝(Long orderId, String paymentConfirmationNumber) {
		StateMachine<ApplicationReviewStates, ApplicationReviewEvents> sm = this.build(orderId);

		Message<ApplicationReviewEvents> paymentMessage = MessageBuilder.withPayload(ApplicationReviewEvents.旅投第二次审核拒绝)
				.setHeader(ORDER_ID_HEADER, orderId)
				.setHeader("paymentConfirmationNumber", paymentConfirmationNumber)
				.build();

		sm.sendEvent(paymentMessage);
		// todo
		return sm;
	}

	public StateMachine<ApplicationReviewStates, ApplicationReviewEvents> 旅行社_提交结算申请(Long orderId, String paymentConfirmationNumber) {
		StateMachine<ApplicationReviewStates, ApplicationReviewEvents> sm = this.build(orderId);

		Message<ApplicationReviewEvents> paymentMessage = MessageBuilder.withPayload(ApplicationReviewEvents.旅行社_提交结算申请)
				.setHeader(ORDER_ID_HEADER, orderId)
				.setHeader("paymentConfirmationNumber", paymentConfirmationNumber)
				.build();

		sm.sendEvent(paymentMessage);
		// todo
		return sm;
	}
	public StateMachine<ApplicationReviewStates, ApplicationReviewEvents> 旅投_计算申请通过(Long orderId, String paymentConfirmationNumber) {
		StateMachine<ApplicationReviewStates, ApplicationReviewEvents> sm = this.build(orderId);

		Message<ApplicationReviewEvents> paymentMessage = MessageBuilder.withPayload(ApplicationReviewEvents.旅投_计算审核拒绝)
				.setHeader(ORDER_ID_HEADER, orderId)
				.setHeader("paymentConfirmationNumber", paymentConfirmationNumber)
				.build();

		sm.sendEvent(paymentMessage);
		// todo
		return sm;
	}
	public StateMachine<ApplicationReviewStates, ApplicationReviewEvents> 旅投_结算审核通过(Long orderId, String paymentConfirmationNumber) {
		StateMachine<ApplicationReviewStates, ApplicationReviewEvents> sm = this.build(orderId);

		Message<ApplicationReviewEvents> paymentMessage = MessageBuilder.withPayload(ApplicationReviewEvents.旅投_结算审核通过)
				.setHeader(ORDER_ID_HEADER, orderId)
				.setHeader("paymentConfirmationNumber", paymentConfirmationNumber)
				.build();

		sm.sendEvent(paymentMessage);
		// todo
		return sm;
	}
	public StateMachine<ApplicationReviewStates, ApplicationReviewEvents> 财政局_拨付款(Long orderId, String paymentConfirmationNumber) {
		StateMachine<ApplicationReviewStates, ApplicationReviewEvents> sm = this.build(orderId);

		Message<ApplicationReviewEvents> paymentMessage = MessageBuilder.withPayload(ApplicationReviewEvents.财政局_拨付款)
				.setHeader(ORDER_ID_HEADER, orderId)
				.setHeader("paymentConfirmationNumber", paymentConfirmationNumber)
				.build();

		sm.sendEvent(paymentMessage);
		// todo
		return sm;
	}


/*	public StateMachine<ApplicationReviewStates, ApplicationReviewEvents> pay(Long orderId, String paymentConfirmationNumber) {
		StateMachine<ApplicationReviewStates, ApplicationReviewEvents> sm = this.build(orderId);

		Message<ApplicationReviewEvents> paymentMessage = MessageBuilder.withPayload(ApplicationReviewEvents.LT_APPROVE)
				.setHeader(ORDER_ID_HEADER, orderId)
				.setHeader("paymentConfirmationNumber", paymentConfirmationNumber)
				.build();

		sm.sendEvent(paymentMessage);
		// todo
		return sm;
	}*/

/*
	public StateMachine<ApplicationReviewStates, ApplicationReviewEvents> fulfill(Long orderId) {
		StateMachine<ApplicationReviewStates, ApplicationReviewEvents> sm = this.build(orderId);
		Message<ApplicationReviewEvents> fulfillmentMessage = MessageBuilder.withPayload(ApplicationReviewEvents.REJECT)
				.setHeader(ORDER_ID_HEADER, orderId)
				.build();
		sm.sendEvent(fulfillmentMessage);
		return sm;
	}
*/


	public StateMachine<ApplicationReviewStates, ApplicationReviewEvents> build(Long orderId) {
		TourBooking order = tourBookingRepository.findById(orderId).get();
		String orderIdKey = Long.toString(1l);

		StateMachine<ApplicationReviewStates, ApplicationReviewEvents> sm = factory.getStateMachine(orderIdKey);
		sm.stopReactively();
		sm.getStateMachineAccessor()
				.doWithAllRegions(sma -> {


					sma.addStateMachineInterceptor(new StateMachineInterceptorAdapter<ApplicationReviewStates, ApplicationReviewEvents>() {

						@Override
						public void preStateChange(State<ApplicationReviewStates, ApplicationReviewEvents> state,
												   Message<ApplicationReviewEvents> message,
												   Transition<ApplicationReviewStates, ApplicationReviewEvents> transition,
												   StateMachine<ApplicationReviewStates, ApplicationReviewEvents> stateMachine,
												   StateMachine<ApplicationReviewStates, ApplicationReviewEvents> rootStateMachine) {

							Optional.ofNullable(message).ifPresent(msg -> {

								Optional.ofNullable(Long.class.cast(msg.getHeaders().getOrDefault(ORDER_ID_HEADER, -1L)))
										.ifPresent(orderId1 -> {
											TourBooking order1 = tourBookingRepository.findById(orderId1).get();
											order1.setTest_Status(state.getId());
											tourBookingRepository.save(order1);
										});
							});

						}
					});
					//   sma.resetStateMachine(new DefaultStateMachineContext<ApplicationReviewStates, ApplicationReviewEvents>(order.getOrderState(), null, null, null));

					sma.resetStateMachineReactively(new DefaultStateMachineContext<ApplicationReviewStates, ApplicationReviewEvents>(order.getTest_Status(), null, null, null));

				});
		sm.startReactively();
		return sm;
	}

}
