package com.xyw.product.common.security.service;

import com.xyw.product.admin.api.dto.UserInfo;
import com.xyw.product.admin.api.feign.RemoteUserService;
import com.xyw.product.common.core.constant.CacheConstants;
import com.xyw.product.common.core.constant.SecurityConstants;
import com.xyw.product.common.core.util.R;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
@RequiredArgsConstructor
public class ProductAppUserDetailsServiceImpl implements ProductUserDetailsService {

	private final RemoteUserService remoteUserService;

	private final CacheManager cacheManager;

	/**
	 * 手机号登录
	 * @param phone 手机号
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String phone) {
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		if (cache != null && cache.get(phone) != null) {
			return (ProductUser) cache.get(phone).get();
		}

		R<UserInfo> result = remoteUserService.infoByMobile(phone, SecurityConstants.FROM_IN);

		UserDetails userDetails = getUserDetails(result);
		if (cache != null) {
			cache.put(phone, userDetails);
		}
		return userDetails;
	}

	/**
	 * check-token 使用
	 * @param productUser user
	 * @return
	 */
	@Override
	public UserDetails loadUserByUser(ProductUser productUser) {
		return this.loadUserByUsername(productUser.getPhone());
	}

	/**
	 * 是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	@Override
	public boolean support(String clientId, String grantType) {
		return SecurityConstants.APP.equals(grantType);
	}

}
