package com.xyw.product.auth.support.handler;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.xyw.product.admin.api.entity.SysLog;
import com.xyw.product.common.core.constant.CommonConstants;
import com.xyw.product.common.core.constant.SecurityConstants;
import com.xyw.product.common.core.util.SpringContextHolder;
import com.xyw.product.common.log.event.SysLogEvent;
import com.xyw.product.common.log.util.SysLogUtils;
import com.xyw.product.common.security.service.ProductUser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ProductAuthenticationSuccessEventHandler implements AuthenticationSuccessHandler {

	private final HttpMessageConverter<OAuth2AccessTokenResponse> accessTokenHttpResponseConverter = new OAuth2AccessTokenResponseHttpMessageConverter();

	/**
	 * Called when a user has been successfully authenticated.
	 * @param request the request which caused the successful authentication
	 * @param response the response
	 * @param authentication the <tt>Authentication</tt> object which was created during
	 * the authentication process.
	 */
	@SneakyThrows
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;
		Map<String, Object> map = accessTokenAuthentication.getAdditionalParameters();
		if (MapUtil.isNotEmpty(map)) {
			// 发送异步日志事件
			ProductUser userInfo = (ProductUser) map.get(SecurityConstants.DETAILS_USER);

			log.info("用户：{} 登录成功", userInfo.getName());
			SecurityContextHolder.getContext().setAuthentication(accessTokenAuthentication);
			SysLog logVo = SysLogUtils.getSysLog();
			logVo.setTitle("登录成功");
			String startTimeStr = request.getHeader(CommonConstants.REQUEST_START_TIME);
			if (StrUtil.isNotBlank(startTimeStr)) {
				Long startTime = Long.parseLong(startTimeStr);
				Long endTime = System.currentTimeMillis();
				logVo.setTime(endTime - startTime);
			}
			logVo.setCreateBy(userInfo.getName());
			logVo.setUpdateBy(userInfo.getName());
			SpringContextHolder.publishEvent(new SysLogEvent(logVo));
		}

		// 输出token
		sendAccessTokenResponse(request, response, authentication);
	}

	private void sendAccessTokenResponse(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;

		OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
		OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
		Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();

		OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
				.tokenType(accessToken.getTokenType()).scopes(accessToken.getScopes());
		if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
			builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
		}
		if (refreshToken != null) {
			builder.refreshToken(refreshToken.getTokenValue());
		}
		if (!CollectionUtils.isEmpty(additionalParameters)) {
			ProductUser userInfo = (ProductUser) (additionalParameters.get("user_info"));

			builder.additionalParameters(new HashMap<String, Object>() {
				{
					put("user_info", new HashMap<String, Object>() {
						{
							put("user_id", "" + userInfo.getId());
							put("user_name", userInfo.getName());
							put("phone", userInfo.getPhone());
							put("dept_id", "" + userInfo.getDeptId());
						}
					});
					put("code", CommonConstants.SUCCESS);
				}
			});
		}
		OAuth2AccessTokenResponse accessTokenResponse = builder.build();
		ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);

		// 无状态 注意删除 context 上下文的信息
		SecurityContextHolder.clearContext();
		this.accessTokenHttpResponseConverter.write(accessTokenResponse, null, httpResponse);
	}

}