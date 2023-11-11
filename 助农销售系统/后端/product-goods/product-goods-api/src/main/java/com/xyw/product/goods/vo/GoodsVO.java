package com.xyw.product.goods.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class GoodsVO {

	/**
	 * 商品id
	 */
	private Long goodsId;

	private Long businessId;
	private String businessAddress;

	/**
	 * 品级id
	 */

	private Long gradeId;

	private BigDecimal price;

	/**
	 * 库存
	 */

	private Long stock;

	/**
	 * 商品销量
	 */

	private Long sales;

	/**
	 * 商品名
	 */
	private String goodsName;

	/**
	 * 商品展示图
	 */
	private List<String> images;

	private List<CommentVO> commentList;

	/**
	 * 商品介绍
	 */
	private String description;

	/**
	 * 商品状态
	 */
	private Byte status;

}
