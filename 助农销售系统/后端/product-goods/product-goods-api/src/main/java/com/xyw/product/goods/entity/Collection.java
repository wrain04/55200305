package com.xyw.product.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xyw.product.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("collection")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "收藏表")
public class Collection extends BaseEntity {

	/**
	 * 收藏id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@Schema(description = "收藏id")
	private Long collectionId;

	/**
	 * 用户id
	 */
	@Schema(description = "用户id")
	private Long userId;

	/**
	 * 收藏标识
	 */
	@Schema(description = "收藏标识")
	private Byte mark;

	/**
	 * 收藏信息
	 */
	@Schema(description = "收藏信息")
	private String info;

}
