package com.xyw.product.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xyw.product.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@TableName("statistics")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "统计")
public class Statistics extends BaseEntity {

	/**
	 * 统计id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@Schema(description = "统计id")
	private Long statisticsId;

	/**
	 * 商户id
	 */
	@Schema(description = "商户id")
	private Long businessId;

	/**
	 * 每日访问量
	 */
	@Schema(description = "每日访问量")
	private Integer dateView;

	/**
	 * 每日订单量
	 */
	@Schema(description = "每日订单量")
	private Integer orderNum;

	/**
	 * 每日成交额
	 */
	@Schema(description = "每日成交额")
	private BigDecimal deal;

}
