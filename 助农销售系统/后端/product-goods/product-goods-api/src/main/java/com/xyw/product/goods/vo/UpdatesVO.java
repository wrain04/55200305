package com.xyw.product.goods.vo;

import com.xyw.product.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatesVO implements Serializable {

	/**
	 * 动态id
	 */
	@Schema(description = "动态id")
	private Long updateId;

	/**
	 * 商户id
	 */
	@Schema(description = "商户id")
	private String businessId;

	/**
	 * 内容
	 */
	@Schema(description = "内容")
	private String info;

	private LocalDateTime createTime;

	private static final long serialVersionUID = 1L;

}