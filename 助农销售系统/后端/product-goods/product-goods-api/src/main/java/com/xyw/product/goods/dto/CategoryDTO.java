package com.xyw.product.goods.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {

	/**
	 * 父id
	 */
	private Long parentId;

	/**
	 * 分类名称
	 */
	private String name;

	private String fileName;

}