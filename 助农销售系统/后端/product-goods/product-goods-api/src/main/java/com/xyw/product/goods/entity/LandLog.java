package com.xyw.product.goods.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.xyw.product.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@TableName("land_log")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "")
public class LandLog extends BaseEntity {

	/**
	 * 地块记录id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@Schema(description = "地块记录id")
	private Long logId;

	/**
	 * 地块id
	 */
	@Schema(description = "地块id")
	private Long landId;

	/**
	 * 产值
	 */
	@Schema(description = "产值")
	private Integer productionValue;

	@Schema(description = "产值时间")
	private LocalDateTime DataTime;

}
