package com.xyw.product.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.xyw.product.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("product_order.order")
@Schema(description = "订单表")
public class Order extends BaseEntity implements Serializable {

	/**
	 * 订单id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@Schema(description = "订单id")
	private Long orderId;

	/**
	 * 用户id
	 */
	@Schema(description = "用户id")
	private Long userId;

	/**
	 * 商户id
	 */
	@Schema(description = "商户id")
	private Long businessId;

	/**
	 * 收货人
	 */
	@Schema(description = "收货人")
	private String consignee;

	/**
	 * 手机号码
	 */
	@Schema(description = "手机号码")
	private Long phone;

	/**
	 * 省
	 */
	@Schema(description = "省")
	private String provinceName;

	/**
	 * 市
	 */
	@Schema(description = "市")
	private String cityName;

	/**
	 * 区、县
	 */
	@Schema(description = "区、县")
	private String countyName;

	/**
	 * 详细地址
	 */
	@Schema(description = "详细地址")
	private String detailAddress;

	/**
	 * 数量
	 */
	@Schema(description = "数量")
	private Long amount;

	/**
	 * 价格
	 */
	@Schema(description = "价格")
	private BigDecimal price;

	/**
	 * 商品详细信息
	 */
	@Schema(description = "商品Id")
	private Long goodId;

	/**
	 * 状态
	 */
	@Schema(description = "状态")
	private String status;

	/**
	 * 订单失效时间
	 */
	@Schema(description = "订单失效时间")
	private LocalDateTime timeExpire;

	/**
	 * 支付方式
	 */
	@Schema(description = "支付方式")
	private String payMethod;

}
