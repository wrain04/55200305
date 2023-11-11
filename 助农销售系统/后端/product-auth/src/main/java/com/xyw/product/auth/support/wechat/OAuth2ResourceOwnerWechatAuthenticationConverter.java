package com.xyw.product.auth.support.wechat;

import com.xyw.product.auth.support.base.OAuth2ResourceOwnerBaseAuthenticationConverter;
import com.xyw.product.auth.support.password.OAuth2ResourceOwnerPasswordAuthenticationToken;
import com.xyw.product.common.security.util.OAuth2EndpointUtils;
import com.xyw.product.common.security.util.WechatParameterNames;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

public class OAuth2ResourceOwnerWechatAuthenticationConverter
		extends OAuth2ResourceOwnerBaseAuthenticationConverter<OAuth2ResourceOwnerWechatAuthenticationToken> {

	/**
	 * 支持小程序模式
	 * @param grantType 授权类型
	 */
	@Override
	public boolean support(String grantType) {
		return WechatAuthorizationGrantType.WX_CODE.getValue().equals(grantType);
	}

	@Override
	public OAuth2ResourceOwnerWechatAuthenticationToken buildToken(Authentication clientPrincipal, Set requestedScopes,
			Map additionalParameters) {
		return new OAuth2ResourceOwnerWechatAuthenticationToken(WechatAuthorizationGrantType.WX_CODE, clientPrincipal,
				requestedScopes, additionalParameters);
	}

	/**
	 * 校验扩展参数 小程序登录 code不为空
	 * @param request 参数列表
	 */
	@Override
	public void checkParams(HttpServletRequest request) {
		MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);
		// code (REQUIRED)
		String code = parameters.getFirst(WechatParameterNames.WECHAT_CODE);
		if (!StringUtils.hasText(code) || parameters.get(WechatParameterNames.WECHAT_CODE).size() != 1) {
			OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.USERNAME,
					OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
		}
	}

}
