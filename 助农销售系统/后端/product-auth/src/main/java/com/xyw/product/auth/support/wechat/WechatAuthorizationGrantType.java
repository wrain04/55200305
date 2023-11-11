package com.xyw.product.auth.support.wechat;

import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.io.Serializable;

public final class WechatAuthorizationGrantType implements Serializable {

	public static final AuthorizationGrantType WX_CODE = new AuthorizationGrantType("wx_code");

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

}
