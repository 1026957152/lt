package com.lt.dom.domain;

public class TouristAttraction {

    private String id; //优惠券模板对象 ID，由 Ping++ 生成。
    private String object; //coupon_template

    private String app; //对应 app 对象的 id，查看 如何获取App ID。

    private Boolean livemode ;// 是否处于 live 模式。
    private int created ; //优惠券模板创建时间，用 Unix 时间戳表示。
    private String  name;// string 优惠券模板名称。
    private int type ;  //优惠券模板的类型。取值包括 "1"：现金券、"2"：折扣券。

    private int amount_off ;// 折扣金额，在 type 为 1 时必传，取值范围 1~1000000000。
    private int percent_off;// 折扣百分比，在 type 为 2 时必传。例如值为 "20" 表示 8 折，值为 "100" 表示免费。
    private int discount_amount_limit;// 折扣上限，仅在 type 为 2 时生效。比如折扣上限为 20 元，当使用 8 折优惠券，订单金额为 200 元时，最高只可折扣 20 元，而非 40 元。
    private int amount_available;//订单金额大于等于该值时，优惠券有效（用于设置满减券）。"0" 表示无限制。
    private int max_circulation;//优惠券最大生成数量，当已生成数量达到最大值时，不能再生成优惠券；取值范围为 1 - 1000000。不填时默认为 null，表示可以无限生成。
    private int max_user_circulation;//单个用户优惠券最大生成数量，当已生成数量达到最大值时，不能再生成优惠券，删除优惠券不会影响该值；取值范围为 1 - 100，默认值为 1。
    private Boolean refundable; //订单全额退款时是否退还优惠券。

    private Expiration expiration;//hash 通过该优惠券模板创建的优惠券有效期规则，详见下方 expiration 参数说明 。

    private int times_circulated  ;//优惠券生成数量。
    private int metadata ;//详见 元数据。


    public static class Expiration {
        private String time_start;//优惠券可用的开始时间，用 Unix 时间戳表示。
        private String time_end ;//优惠券可用的结束时间，用 Unix 时间戳表示。
        private int duration;//优惠券创建后的过期时间，单位为秒。不能和 time_start、time_end 同时使用。

    }
}
