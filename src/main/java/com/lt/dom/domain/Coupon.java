package com.lt.dom.domain;

import java.util.Map;

public class Coupon {

    private boolean redeemed;//	是否已经使用（核销）。
    private Map coupon_template;//	关联的 coupon_template 对象。 hash
    private String user;//	user 对象的 id。
    private int time_start;//	可用的开始时间，用 Unix 时间戳表示。 timestamp
    private int time_end;//	可用的结束时间，用 Unix 时间戳表示。 timestamp
    private String order;//	关联 order 对象的 id。使用后生效。
    private int actual_amount;//	关联 order 对象的应付金额，单位为分。使用后生效。
    private int user_times_circulated;//	优惠券在该用户下当前生成次数。
    private Map metadata;//	详见 元数据。 hash


}
