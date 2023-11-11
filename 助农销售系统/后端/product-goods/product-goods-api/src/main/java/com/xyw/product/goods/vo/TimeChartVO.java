package com.xyw.product.goods.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xyw.product.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TimeChartVO {

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
	 * 描述
	 */
	@Schema(description = "描述")
	private String description;

	private List<String> imageList;

}
