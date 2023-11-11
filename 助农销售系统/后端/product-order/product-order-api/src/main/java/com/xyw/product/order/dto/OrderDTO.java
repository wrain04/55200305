package com.xyw.product.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDTO implements Serializable {

	/**
	 * 订单id
	 */
	private Long orderId;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 商户id
	 */
	private Long businessId;

	/**
	 * 收货人
	 */
	private String consignee;

	/**
	 * 手机号码
	 */
	private Long phone;

	/**
	 * 省
	 */
	private String provinceName;

	/**
	 * 市
	 */
	private String cityName;

	/**
	 * 区、县
	 */
	private String countyName;

	/**
	 * 详细地址
	 */
	private String detailAddress;

	/**
	 * 数量
	 */
	private Long amount;

	/**
	 * 价格
	 */
	private BigDecimal price;

	/**
	 * 商品详细信息
	 */
	private Long goodId;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 订单失效时间
	 */
	private LocalDateTime timeExpire;

	/**
	 * 支付方式
	 */
	private String payMethod;

}
