package com.xyw.product.common.core.constant;

import java.util.concurrent.TimeUnit;

public interface ProductConstants {

	// Redis命名
	String STATISTICS_INFO_BUSINESS = "STATISTICS:INFO:";

	Integer STATISTICS_INFO_BUSINESS_TIMEOUT = 30;

	TimeUnit STATISTICS_INFO_BUSINESS_TIMEUNIT = TimeUnit.MINUTES;

	// 商品状态
	/**
	 * 销售中
	 */
	Byte IN_SALES = 0;

	/**
	 * 已停售
	 */
	Byte DISCONTINUED = 1;

	/**
	 * 无货
	 */
	Byte OUT_OF_STOCK = 2;

	/**
	 * 审核中
	 */
	Byte IN_PROCESS = 9;

	// 订单状态
	// 待付款
	Byte WAIT_PAY = 0;

	// 待发货
	Byte WAIT_SEND = 1;

	// 待收货
	Byte WAIT_OBTAIN = 2;

	// 退款/退货
	Byte REFUND_OR_RETURN = 3;

	// 待退款/退货
	Byte WAIT_REFUND_OR_RETURN = 4;

	// 已收货
	Byte FINISH_OBTAIN = 5;

	/**
	 * 商户信息标识
	 */

	/**
	 * 商户锁定
	 */
	Byte BUSINESS_LOCK = 9;
	/**
	 * 商户可用
	 */
	Byte BUSINESS_USED = 1;
	/**
	 * 审核被打回
	 */
	Byte BUSINESS_REVIEW_REPULSE = 4;
	/**
	 * 商户审核中
	 */
	Byte BUSINESS_REVIEW = 5;


}