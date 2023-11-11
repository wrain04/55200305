package com.xyw.product.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.xyw.product.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@TableName("grade")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "品级表")
public class Grade extends BaseEntity {

	/**
	 * 品级id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@Schema(description = "品级id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long gradeId;

	/**
	 * 分类id
	 */
	@Schema(description = "分类id")
	private Long categoryId;

	/**
	 * 品级名
	 */
	@Schema(description = "品级名")
	private String name;

	/**
	 * 价格
	 */
	@Schema(description = "价格")
	private BigDecimal price;

}