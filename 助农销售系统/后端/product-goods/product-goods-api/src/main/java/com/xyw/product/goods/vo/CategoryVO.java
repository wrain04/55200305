package com.xyw.product.goods.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class CategoryVO implements Serializable {

	/**
	 * 分类id
	 */
	private Long categoryId;

	/**
	 * 父id
	 */
	private Long parentId;

	/**
	 * 分类名称
	 */
	private String name;

	/**
	 * 图标
	 */
	private String logo;

}
