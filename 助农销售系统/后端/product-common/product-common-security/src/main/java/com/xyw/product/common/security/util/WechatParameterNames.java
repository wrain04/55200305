package com.xyw.product.common.security.util;

public interface WechatParameterNames {

	String WECHAT_CODE = "wx_code";

	String WECHAT_META = "wx_meta";

	String WECHAT_IV = "wx_iv";

	String WECHAT_APP_ID = "wx837c5e1deff71df9";

	String WECHAT_APP_SECRET = "e46ccdbe15a247fdbef0496c2e110b0e";

	String WECHAT_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code&appid="
			+ WECHAT_APP_ID + "&secret=" + WECHAT_APP_SECRET + "&js_code=";

}