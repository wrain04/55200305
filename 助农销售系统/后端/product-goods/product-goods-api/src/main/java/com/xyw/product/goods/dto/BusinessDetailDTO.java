package com.xyw.product.goods.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "BusinessDetailDTO")
public class BusinessDetailDTO {

	@Schema(description = "商户编号")
	private Long businessId;

	/**
	 * 经营者姓名
	 */
	@Schema(description = "店铺名称")
	private String storeName;

	/**
	 * 店铺logo
	 */
	@Schema(description = "店铺logo")
	private String businessLogo;

	@Schema(description = "经营者姓名")
	private String realName;

	@Schema(description = "身份证号码")
	private String idCard;

	/**
	 * 商户类型
	 */
	@Schema(description = "商户类型")
	private String businessType;

	/**
	 * 详细地址
	 */
	@Schema(description = "详细地址")
	private String address;

	/**
	 * 经营执照
	 */
	@Schema(description = "经营执照")
	private List<String> licenseList;

	/**
	 * 店铺电话
	 */
	@Schema(description = "店铺电话")
	private String businessPhone;

	/**
	 * 商户描述
	 */
	@Schema(description = "商户描述")
	private String description;

	/**
	 * 店铺绑定银行卡号
	 */
	@Schema(description = "店铺绑定银行卡号")
	private String bankCardNumber;

	/**
	 * 主要产品
	 */
	@Schema(description = "主要产品")
	private String mainProducts;

}