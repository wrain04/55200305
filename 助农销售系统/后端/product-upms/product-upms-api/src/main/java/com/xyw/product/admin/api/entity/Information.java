package com.xyw.product.admin.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xyw.product.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@TableName("information")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "资讯表")
public class Information extends BaseEntity {

	/**
	 * 分类id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@Schema(description = "分类id")
	private Long informationId;

	/**
	 * 资讯标题
	 */
	@Schema(description = "资讯标题")
	private String informationTitle;

	private String info;

	/**
	 * 资讯作者
	 */
	@Schema(description = "资讯作者")
	private String informationAuthor;

	/**
	 * 访问人数
	 */
	@Schema(description = "访问人数")
	private Long viewNumber;

	/**
	 * 封面图片链接
	 */
	@Schema(description = "封面图片链接")
	private String cover;

	private Integer status;

	/**
	 * 发布时间
	 */
	@Schema(description = "发布时间")
	private LocalDateTime releaseTime;

}
