package com.xyw.product.auth.support.wechat;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xyw.product.auth.support.base.OAuth2ResourceOwnerBaseAuthenticationProvider;
import com.xyw.product.auth.support.password.OAuth2ResourceOwnerPasswordAuthenticationToken;
import com.xyw.product.common.security.util.WechatParameterNames;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.util.Map;

public class OAuth2ResourceOwnerWechatAuthenticationProvider
		extends OAuth2ResourceOwnerBaseAuthenticationProvider<OAuth2ResourceOwnerWechatAuthenticationToken> {

	private static final Logger LOGGER = LogManager.getLogger(OAuth2ResourceOwnerWechatAuthenticationProvider.class);

	/**
	 * Constructs an {@code OAuth2AuthorizationCodeAuthenticationProvider} using the
	 * provided parameters.
	 * @param authenticationManager
	 * @param authorizationService the authorization service
	 * @param tokenGenerator the token generator
	 * @since 0.2.3
	 */
	public OAuth2ResourceOwnerWechatAuthenticationProvider(AuthenticationManager authenticationManager,
			OAuth2AuthorizationService authorizationService,
			OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
		super(authenticationManager, authorizationService, tokenGenerator);
	}

	@Override
	public UsernamePasswordAuthenticationToken buildToken(Map<String, Object> reqParameters) {
		String code = (String) reqParameters.get(WechatParameterNames.WECHAT_CODE);
		if (StrUtil.isEmpty(code)) {
			throw new OAuth2AuthenticationException("code不能为空");
		}

		return new UsernamePasswordAuthenticationToken(JSONUtil.toJsonStr(reqParameters), null);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		boolean supports = OAuth2ResourceOwnerWechatAuthenticationToken.class.isAssignableFrom(authentication);
		LOGGER.debug("supports authentication=" + authentication + " returning " + supports);
		return supports;
	}

	@Override
	public void checkClient(RegisteredClient registeredClient) {
		assert registeredClient != null;
		if (!registeredClient.getAuthorizationGrantTypes().contains(WechatAuthorizationGrantType.WX_CODE)) {
			throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
		}
	}

}
