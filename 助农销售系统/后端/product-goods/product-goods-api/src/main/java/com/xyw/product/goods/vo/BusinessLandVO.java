package com.xyw.product.goods.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xyw.product.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class BusinessLandVO {

	@Schema(description = "地块id")
	private Long landId;

	/**
	 * 商户id
	 */
	@Schema(description = "商户id")
	private Long businessId;

	/**
	 * 生产品
	 */
	@Schema(description = "生产品")
	private String mainProducts;

	/**
	 * 地块名
	 */
	@Schema(description = "地块名")
	private String landName;

	private String introduction;

	/**
	 * 占地面积
	 */
	@Schema(description = "占地面积")
	private Float landArea;

	/**
	 * 地块图片
	 */
	@Schema(description = "地块图片")
	private List<String> landImageList;

	/**
	 * 详细地址
	 */
	@Schema(description = "详细地址")
	private String address;

}
