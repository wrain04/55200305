package com.xyw.product.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xyw.product.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("time_chart")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "时间表")
public class TimeChart extends BaseEntity {

	/**
	 * 图表id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@Schema(description = "图表id")
	private Long chartId;

	/**
	 * 商品id
	 */
	@Schema(description = "商品id")
	private Long goodId;

	/**
	 * 时间
	 */
	@Schema(description = "时间")
	private LocalDateTime time;

	/**
	 * 图片json数组
	 */
	@Schema(description = "图片json数组")
	private String images;

	/**
	 * 描述
	 */
	@Schema(description = "描述")
	private String description;

}
