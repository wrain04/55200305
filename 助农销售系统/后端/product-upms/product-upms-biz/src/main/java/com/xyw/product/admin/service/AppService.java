package com.xyw.product.admin.service;

import com.xyw.product.admin.api.dto.AppSmsDTO;
import com.xyw.product.common.core.util.R;

public interface AppService {

	/**
	 * 发送手机验证码
	 * @param sms phone
	 * @return code
	 */
	R<Boolean> sendSmsCode(AppSmsDTO sms);

	/**
	 * 校验验证码
	 * @param phone 手机号
	 * @param code 验证码
	 * @return
	 */
	boolean check(String phone, String code);

}
