package com.lt.dom.otcReq;

import com.alipay.api.internal.mapping.AlipayFieldMethod;
import com.lt.dom.otcenum.EnumPaymentOption;

import java.util.List;

public class BookingPayPojo {

    private List<VoucherPojo> voucherPojoList;
    private List<EnumPaymentOption> paymentOptions;

    public List<EnumPaymentOption> getPaymentOptions() {
        return paymentOptions;
    }

    public void setPaymentOptions(List<EnumPaymentOption> paymentOptions) {
        this.paymentOptions = paymentOptions;
    }

    public List<VoucherPojo> getVoucherPojoList() {
        return voucherPojoList;
    }

    public void setVoucherPojoList(List<VoucherPojo> voucherPojoList) {
        this.voucherPojoList = voucherPojoList;
    }
    /*    "id": "tr_HqbzHCvLOaL4La1ezHfDWTqH",
             "object": "transfer",
             "type": "b2c",
             "created": 1432724825,
             "time_transferred": null,
             "livemode": true,
             "status": "pending",
             "app": "app_1Gqj58ynP0mHeX1q",
             "channel": "wx_pub",
             "order_no": "123456789",
             "amount": 100,
             "amount_settle": 100,
             "currency": "cny",
             "recipient": "o7zpMs5MW2-52GAy5hRrjdYVCktU",
             "description": "Your Description",
             "transaction_no": "1000018301201505200184147302",
             "failure_msg":null,*/
}
