package com.xyw.product.goods.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xyw.product.common.mybatis.base.BaseEntity;
import com.xyw.product.goods.vo.GoodsVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@TableName("goods")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商品表")
public class Good extends BaseEntity {

	/**
	 * 商品id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@Schema(description = "商品id")
	private Long goodsId;

	/**
	 * 商家id
	 */
	@Schema(description = "商家id")
	private Long businessId;

	/**
	 * 等级id
	 */
	@Schema(description = "等级id")
	private Long gradeId;

	/**
	 * 商品名
	 */
	@Schema(description = "商品名")
	private String goodsName;

	/**
	 * 库存
	 */
	@Schema(description = "库存")
	private Long stock;

	/**
	 * 商品销量
	 */
	@Schema(description = "商品销量")
	private Long sales;

	/**
	 * 商品展示图
	 */
	@Schema(description = "商品图")
	private String goodsImage;

	/**
	 * 商品介绍
	 */
	@Schema(description = "商品介绍")
	private String description;

	/**
	 * 起售状态
	 */
	@Schema(description = "起售状态")
	private Byte status;

}
