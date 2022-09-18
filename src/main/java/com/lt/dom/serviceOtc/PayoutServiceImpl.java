package com.lt.dom.serviceOtc;


import com.github.binarywang.wxpay.bean.entpay.EntPayBankRequest;
import com.github.binarywang.wxpay.bean.entpay.EntPayBankResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.lt.dom.domain.SettleAccount;
import com.lt.dom.error.PaymentException;
import com.lt.dom.oct.Payout;
import com.lt.dom.otcenum.EnumPayoutMethod;
import com.lt.dom.otcenum.EnumPayoutStatus;
import com.lt.dom.otcenum.EnumWxEntPayErrorcode;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.PayoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PayoutServiceImpl {
    @Autowired
    private PayoutRepository payoutRepository;

    @Autowired
    private WxPayService wxService;

    @Autowired
    private IdGenServiceImpl idGenService;

    public EntPayBankResult payBank(EntPayBankRequest request) throws WxPayException {
        return this.wxService.getEntPayService().payBank(request);
    }



    public Payout createPayout(int amount, SettleAccount settleAccount) {
        Payout payout = new Payout();
        payout.setAutomatic(true);
        payout.setAmount(amount);
        payout.setMethod(EnumPayoutMethod.standard);
        payout.setCode(idGenService.payoutCode());
        payout = payoutRepository.save(payout);



        EntPayBankRequest entPayBankRequest = new EntPayBankRequest();
        entPayBankRequest.setPartnerTradeNo(payout.getCode());
        try {
            EntPayBankResult entPayBankResult = payBank(entPayBankRequest);
            if(entPayBankResult.getReturnCode().equals("SUCCESS")){
                if(entPayBankResult.getResultCode().equals("SUCCESS")){

                    switch (EnumWxEntPayErrorcode.fromName(entPayBankResult.getErrCode())){
                        case SUCCESS :{
                            entPayBankResult.getPaymentNo();
                            entPayBankResult.getCmmsAmount();

                            payout.setEnumPayoutStatus(EnumPayoutStatus.paid);
                            payoutRepository.save(payout);
                        }
                            break;
                        case INVALID_REQUEST:
                            break;
                        case SYSTEMERROR:
                            break;
                        case PARAM_ERROR:
                            break;
                        case SIGNERROR:
                            break;
                        case AMOUNT_LIMIT:
                            break;
                        case ORDERPAID:
                            break;
                        case FATAL_ERROR:
                            break;
                        case NOTENOUGH:
                            break;
                        case FREQUENCY_LIMITED:
                            break;
                        case RECV_ACCOUNT_NOT_ALLOWED:
                            break;
                        case PAY_CHANNEL_NOT_ALLOWED:
                            break;
                        case SENDNUM_LIMIT:
                            break;
                        case NO_AUTH:
                            break;
                    }


                }
                if(entPayBankResult.getResultCode().equals("FAIL")){

                    //重新查询
                }
            }
            if(entPayBankResult.getReturnCode().equals("FAIL")){
                entPayBankResult.getResultCode();
                throw new PaymentException(Enumfailures.payment_login_error,"");
            }

        } catch (WxPayException e) {
            throw new PaymentException(Enumfailures.payment_login_error,"");
        }


        return payoutRepository.save(payout);

    }
}
