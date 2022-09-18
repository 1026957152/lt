package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumPaymentFlow;
import com.lt.dom.otcenum.EnumPaymentOption;
import com.lt.dom.otcenum.EnumPaymentStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PaymentMethod {


    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;//	integer	The payment’s unique ID
    
@Column(unique=true) 
private String code;//	string	Object type, payment
    private EnumPaymentOption payment_method;
    private long customer;//	integer	Associated Customer, if any
    private String object;//	string	Object type, payment
  //  private Object  wechat_pay ;//	object	Charge object included for processed payments
  //  private Object  billing_details ;//	object	Charge object included for processed payments





}
