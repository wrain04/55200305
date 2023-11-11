package com.xyw.product.goods.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentVO {

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

	@Schema(description = "星")
	private Byte star;

	@Schema(description = "创建时间")
	private LocalDateTime createTime;

}