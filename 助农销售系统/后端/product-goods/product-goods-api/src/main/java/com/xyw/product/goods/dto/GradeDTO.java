package com.xyw.product.goods.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GradeDTO {

	private Long categoryId;

	private String name;

	private BigDecimal price;

}
