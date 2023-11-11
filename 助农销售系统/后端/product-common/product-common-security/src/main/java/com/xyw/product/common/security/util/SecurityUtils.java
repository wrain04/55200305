package com.xyw.product.common.security.util;

import cn.hutool.core.util.StrUtil;
import com.xyw.product.common.core.constant.SecurityConstants;
import com.xyw.product.common.core.util.SpringContextHolder;
import com.xyw.product.common.security.service.ProductUser;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@UtilityClass
public class SecurityUtils {

	/**
	 * 获取Authentication
	 */
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取用户
	 */
	public ProductUser getUser(Authentication authentication) {
		// 通过这种方式查询最新的用户信息
		UserDetailsService detailsService = SpringContextHolder.getBean(UserDetailsService.class);
		UserDetails userDetails = detailsService.loadUserByUsername(authentication.getName());
		if (userDetails instanceof ProductUser) {
			return (ProductUser) userDetails;
		}
		return null;
	}

	/**
	 * 获取用户
	 */
	public ProductUser getUser() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		return getUser(authentication);
	}

	/**
	 * 获取用户角色信息
	 * @return 角色集合
	 */
	public List<Long> getRoles() {
		Authentication authentication = getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		List<Long> roleIds = new ArrayList<>();
		authorities.stream().filter(granted -> StrUtil.startWith(granted.getAuthority(), SecurityConstants.ROLE))
				.forEach(granted -> {
					String id = StrUtil.removePrefix(granted.getAuthority(), SecurityConstants.ROLE);
					roleIds.add(Long.parseLong(id));
				});
		return roleIds;
	}

}
