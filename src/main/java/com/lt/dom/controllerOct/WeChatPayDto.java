package com.lt.dom.controllerOct;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
	 * 微信:小程序支付 统一下单必要的参数
	 */

	public class WeChatPayDto implements Serializable {

		/**
		 * 商品描述
		 */
		private String body;

		/**
		 * 订单号
		 */
		@NotNull(message = "缺少请求参数")
		private String outTradeNo;

		/**
		 * 金额
		 */
		private String totalFee;

		/**
		 * 终端IP
		 */
		private String spbillCreateIp;

		/**
		 * 支付类型
		 */
		@NotBlank(message = "支付类型不能为空")
		private String payType;


	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
}