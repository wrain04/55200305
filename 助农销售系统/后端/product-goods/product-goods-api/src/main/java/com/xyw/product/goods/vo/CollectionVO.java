package com.xyw.product.goods.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "收藏表VO")
public class CollectionVO {

	/**
	 * 收藏id
	 */

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
	private List<String[]> infoArray;

}
