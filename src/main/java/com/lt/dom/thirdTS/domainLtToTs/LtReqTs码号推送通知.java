package com.lt.dom.thirdTS.domainLtToTs;

import javax.validation.constraints.NotNull;

public class LtReqTs码号推送通知 {

    @NotNull
    private String method ;//:接口方法名,固定值：item_list


    public static class ToLtReqTs码号推送通知 {
        private String orders_id;//本平台订单ID（天时同城）
        private String out_orders_id;//out_orders_id:第三方平台订单ID
        private String out_code;//out_code:码号，存在多个码号时默认展示第一个
        private String out_codes;//out_codes:多个码号时以英文逗号分隔(,)
        private String qrcode_images;//qrcode_images:二维码图片，多个用英文逗号(,)分隔
        private String qrcode_image;//qrcode_image:二维码图片
        private String qrcode_href;//qrcode_href:二维码链接
        private String qrcode;//qrcode:二维码数据(用于生成二维码图片)
        private String out_money_send;//out_money_send:采购发送费
        private String out_money_one;//out_money_one:采购单价

        private String out_send_content;//out_send_content:发送内容
        private String is_real_code;//is_real_code:是否真实码号
        private String post_tracking_no;//post_tracking_no:快递单号





        public LtReqTs码号推送通知 To() {
            LtReqTs码号推送通知 ltReqTs验证核销通知 = new LtReqTs码号推送通知();
            ltReqTs验证核销通知.setOrders_id(this.orders_id);//本平台订单ID（天时同城）
            ltReqTs验证核销通知.setOut_orders_id(this.out_orders_id);//out_orders_id:第三方平台订单ID
            ltReqTs验证核销通知.setOut_code(this.out_code);//out_code:码号，存在多个码号时默认展示第一个
            ltReqTs验证核销通知.setOut_codes(this.out_codes);//out_codes:多个码号时以英文逗号分隔(,)

            ltReqTs验证核销通知.setQrcode_images(this.qrcode_images);//qrcode_images:二维码图片，多个用英文逗号(,)分隔
            ltReqTs验证核销通知.setQrcode_image(this.qrcode_image);//qrcode_image:二维码图片
            ltReqTs验证核销通知.setQrcode_href(this.qrcode_href);////qrcode_href:二维码链接

            ltReqTs验证核销通知.setQrcode(this.qrcode);//qrcode:二维码数据(用于生成二维码图片)

            ltReqTs验证核销通知.setOut_money_send(this.out_money_send);//out_money_send:采购发送费
            ltReqTs验证核销通知.setOut_money_one(this.out_money_one);//out_money_one:采购单价
            ltReqTs验证核销通知.setOut_send_content(this.out_send_content);//out_send_content:发送内容
            ltReqTs验证核销通知.setIs_real_code(this.is_real_code);//is_real_code:是否真实码号

            ltReqTs验证核销通知.setPost_tracking_no(this.post_tracking_no);//post_tracking_no:快递单号

            return ltReqTs验证核销通知;


        }

        public String getOrders_id() {
            return orders_id;
        }

        public void setOrders_id(String orders_id) {
            this.orders_id = orders_id;
        }

        public String getOut_orders_id() {
            return out_orders_id;
        }

        public void setOut_orders_id(String out_orders_id) {
            this.out_orders_id = out_orders_id;
        }

        public String getOut_code() {
            return out_code;
        }

        public void setOut_code(String out_code) {
            this.out_code = out_code;
        }

        public String getOut_codes() {
            return out_codes;
        }

        public void setOut_codes(String out_codes) {
            this.out_codes = out_codes;
        }

        public String getQrcode_images() {
            return qrcode_images;
        }

        public void setQrcode_images(String qrcode_images) {
            this.qrcode_images = qrcode_images;
        }

        public String getQrcode_image() {
            return qrcode_image;
        }

        public void setQrcode_image(String qrcode_image) {
            this.qrcode_image = qrcode_image;
        }

        public String getQrcode_href() {
            return qrcode_href;
        }

        public void setQrcode_href(String qrcode_href) {
            this.qrcode_href = qrcode_href;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public String getOut_money_send() {
            return out_money_send;
        }

        public void setOut_money_send(String out_money_send) {
            this.out_money_send = out_money_send;
        }

        public String getOut_money_one() {
            return out_money_one;
        }

        public void setOut_money_one(String out_money_one) {
            this.out_money_one = out_money_one;
        }

        public String getOut_send_content() {
            return out_send_content;
        }

        public void setOut_send_content(String out_send_content) {
            this.out_send_content = out_send_content;
        }

        public String getIs_real_code() {
            return is_real_code;
        }

        public void setIs_real_code(String is_real_code) {
            this.is_real_code = is_real_code;
        }

        public String getPost_tracking_no() {
            return post_tracking_no;
        }

        public void setPost_tracking_no(String post_tracking_no) {
            this.post_tracking_no = post_tracking_no;
        }
    }

    private String orders_id;//本平台订单ID（天时同城）
    private String out_orders_id;//out_orders_id:第三方平台订单ID
    private String out_code;//out_code:码号，存在多个码号时默认展示第一个
    private String out_codes;//out_codes:多个码号时以英文逗号分隔(,)
    private String qrcode_images;//qrcode_images:二维码图片，多个用英文逗号(,)分隔
    private String qrcode_image;//qrcode_image:二维码图片
    private String qrcode_href;//qrcode_href:二维码链接
    private String qrcode;//qrcode:二维码数据(用于生成二维码图片)
    private String out_money_send;//out_money_send:采购发送费
    private String out_money_one;//out_money_one:采购单价

    private String out_send_content;//out_send_content:发送内容
    private String is_real_code;//is_real_code:是否真实码号
    private String post_tracking_no;//post_tracking_no:快递单号


    public String getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(String orders_id) {
        this.orders_id = orders_id;
    }

    public String getOut_orders_id() {
        return out_orders_id;
    }

    public void setOut_orders_id(String out_orders_id) {
        this.out_orders_id = out_orders_id;
    }

    public String getOut_code() {
        return out_code;
    }

    public void setOut_code(String out_code) {
        this.out_code = out_code;
    }

    public String getOut_codes() {
        return out_codes;
    }

    public void setOut_codes(String out_codes) {
        this.out_codes = out_codes;
    }

    public String getQrcode_images() {
        return qrcode_images;
    }

    public void setQrcode_images(String qrcode_images) {
        this.qrcode_images = qrcode_images;
    }

    public String getQrcode_image() {
        return qrcode_image;
    }

    public void setQrcode_image(String qrcode_image) {
        this.qrcode_image = qrcode_image;
    }

    public String getQrcode_href() {
        return qrcode_href;
    }

    public void setQrcode_href(String qrcode_href) {
        this.qrcode_href = qrcode_href;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getOut_money_send() {
        return out_money_send;
    }

    public void setOut_money_send(String out_money_send) {
        this.out_money_send = out_money_send;
    }

    public String getOut_money_one() {
        return out_money_one;
    }

    public void setOut_money_one(String out_money_one) {
        this.out_money_one = out_money_one;
    }

    public String getOut_send_content() {
        return out_send_content;
    }

    public void setOut_send_content(String out_send_content) {
        this.out_send_content = out_send_content;
    }

    public String getIs_real_code() {
        return is_real_code;
    }

    public void setIs_real_code(String is_real_code) {
        this.is_real_code = is_real_code;
    }

    public String getPost_tracking_no() {
        return post_tracking_no;
    }

    public void setPost_tracking_no(String post_tracking_no) {
        this.post_tracking_no = post_tracking_no;
    }

    @NotNull
    private String _sig;//:请求签名，见说明文档

    @NotNull
    private String _pid;//:合作伙伴id

    @NotNull
    private String format;//:返回数据格式，json

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }



    public String get_sig() {
        return _sig;
    }

    public void set_sig(String _sig) {
        this._sig = _sig;
    }

    public String get_pid() {
        return _pid;
    }

    public void set_pid(String _pid) {
        this._pid = _pid;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
