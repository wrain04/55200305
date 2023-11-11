package com.xyw.product.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xyw.product.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Updates extends BaseEntity implements Serializable {

	/**
	 * 动态id
	 */
	@Schema(description = "动态id")
	@TableId(type = IdType.ASSIGN_ID)
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

	private static final long serialVersionUID = 1L;

}