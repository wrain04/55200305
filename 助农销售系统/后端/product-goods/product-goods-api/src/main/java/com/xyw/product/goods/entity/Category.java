package com.xyw.product.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xyw.product.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("category")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "分类实体")
public class Category extends BaseEntity {

	/**
	 * 分类id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@Schema(description = "分类id")
	private Long categoryId;

	/**
	 * 父id
	 */
	@Schema(description = "父id")
	private Long parentId;

	/**
	 * 分类名称
	 */
	@Schema(description = "分类名称")
	private String name;

	/**
	 * 图标
	 */
	@Schema(description = "图标")
	private String fileName;

}
