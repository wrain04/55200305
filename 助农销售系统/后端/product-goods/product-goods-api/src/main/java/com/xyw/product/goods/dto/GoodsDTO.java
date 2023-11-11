package com.xyw.product.goods.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "GoodsDTO")
public class GoodsDTO {

	private Long goodsId;

	private Long businessId;

	private Long gradeId;

	private String goodsName;

	private Long sales;

	private Long stock;

	private List<String> imageList;

	private String description;

	private Byte status;

}