package com.xyw.product.common.security.service;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xyw.product.admin.api.dto.UserDTO;
import com.xyw.product.admin.api.dto.UserInfo;
import com.xyw.product.admin.api.feign.RemoteUserService;
import com.xyw.product.common.core.constant.CacheConstants;
import com.xyw.product.common.core.constant.SecurityConstants;
import com.xyw.product.common.core.util.R;
import com.xyw.product.common.security.util.WechatParameterNames;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
public class ProductWechatUserDetailsServiceImpl implements ProductUserDetailsService {

	private static final Long DEFAULT_DEPT_ID = 1585257693814792194L;

	private static final Long DEFAULT_ROLE_ID = 2L;

	private static final Long DEFAULT_POST_ID = 1L;

	private final RemoteUserService remoteUserService;

	private final CacheManager cacheManager;

	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String code) {
		JSONObject obj = JSONUtil.parseObj(code);
		code = obj.getStr("wx_code");
		String openid;
		JSONObject object = JSONUtil.parseObj(HttpUtil.get(WechatParameterNames.WECHAT_LOGIN_URL + code));
		openid = object.getStr("openid");
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		if (cache != null && cache.get(openid) != null) {
			return (ProductUser) cache.get(openid).get();
		}
		UserDetails userDetails = null;
		try {
			Boolean exist = remoteUserService.existByOpenid(openid, SecurityConstants.FROM_IN).getData();
			if (exist != null && !exist) {
				UserDTO userDTO = getDefaultUserDTO(openid);
				remoteUserService.wechatUser(userDTO, SecurityConstants.FROM_IN);
			}
			R<UserInfo> result = remoteUserService.infoByOpenid(openid, SecurityConstants.FROM_IN);
			if (result != null && result.getData() != null) {
				userDetails = getUserDetails(result);
				if (cache != null) {
					cache.put(openid, userDetails);
				}
			}
		}
		catch (Exception e) {
			log.error("loadUserByUsername error: {}", e.getMessage(), e);
		}
		return userDetails;
	}

	private UserDTO getDefaultUserDTO(String openid) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("微信用户");
		userDTO.setOpenid(openid);
		userDTO.setAvatar(
				"https://thirdwx.qlogo.cn/mmopen/vi_32/POgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg/132");
		userDTO.setDeptId(DEFAULT_DEPT_ID);
		userDTO.setRole(Collections.singletonList(DEFAULT_ROLE_ID));
		userDTO.setPost(Collections.singletonList(DEFAULT_POST_ID));
		return userDTO;
	}

	/**
	 * check-token 使用
	 * @param productUser user
	 * @return
	 */
	@Override
	public UserDetails loadUserByUser(ProductUser productUser) {
		return this.loadUserByUsername(productUser.getOpenid());
	}

	/**
	 * 是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	@Override
	public boolean support(String clientId, String grantType) {
		return SecurityConstants.WECHAT_CODE.equals(grantType);
	}

}
