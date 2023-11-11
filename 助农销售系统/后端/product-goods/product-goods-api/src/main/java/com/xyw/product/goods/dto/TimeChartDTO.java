package com.xyw.product.goods.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xyw.product.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TimeChartDTO {

	/**
	 * 商品id
	 */

	private Long goodId;

	/**
	 * 时间
	 */
	private LocalDateTime time;

	/**
	 * 图片json数组
	 */

	private List<String> imageList;

	/**
	 * 描述
	 */

	private String description;

}
