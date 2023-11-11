package com.xyw.product.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xyw.product.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("comment")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "")
public class Comment extends BaseEntity {

	/**
	 * 评论id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@Schema(description = "评论id")
	private Long commentId;

	/**
	 * 商品id
	 */
	@Schema(description = "商品id")
	private Long goodsId;

	/**
	 * 用户id
	 */
	@Schema(description = "用户id")
	private Long userId;

	/**
	 * 商户id
	 */
	@Schema(description = "商户id")
	private Long businessId;

	/**
	 * 评论内容
	 */
	@Schema(description = "评论内容")
	private String content;

	/**
	 * 点赞数
	 */
	@Schema(description = "点赞数")
	private Integer likeNum;

	@Schema(description = "星星")
	private Byte star;

	@Schema(description = "是否有图片")
	private Boolean isHaveImage;

}
