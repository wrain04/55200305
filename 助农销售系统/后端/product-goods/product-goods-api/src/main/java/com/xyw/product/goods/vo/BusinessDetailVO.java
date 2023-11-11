package com.xyw.product.goods.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xyw.product.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
public class BusinessDetailVO {

	/**
	 * 商户编号
	 */

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

	@Schema(description = "经营执照")
	private List<String> licenseList;

	@Schema(description = "店铺电话")
	private String businessPhone;

	/**
	 * 商户描述
	 */
	@Schema(description = "商户描述")
	private String description;

	@Schema(description = "店铺绑定银行卡号")
	private String bankCardNumber;

	/**
	 * 主要产品
	 */
	@Schema(description = "主要产品")
	private String mainProducts;

	/**
	 * 0-正常，9-锁定
	 */
	@Schema(description = "0-正常，9-锁定")
	private String lockFlag;

}
