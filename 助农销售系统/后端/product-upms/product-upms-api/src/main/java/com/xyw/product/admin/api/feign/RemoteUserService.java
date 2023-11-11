package com.xyw.product.admin.api.feign;

import com.xyw.product.admin.api.dto.UserDTO;
import com.xyw.product.admin.api.dto.UserInfo;
import com.xyw.product.admin.api.vo.UserVO;
import com.xyw.product.common.core.constant.SecurityConstants;
import com.xyw.product.common.core.constant.ServiceNameConstants;
import com.xyw.product.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteUserService {

	/**
	 * 通过用户名查询用户、角色信息
	 * @param username 用户名
	 * @param from 调用标志
	 * @return R
	 */
	@GetMapping("/user/info/{username}")
	R<UserInfo> info(@PathVariable("username") String username, @RequestHeader(SecurityConstants.FROM) String from);

	@PostMapping("/user/wechat")
	R<Boolean> wechatUser(@RequestBody UserDTO userDto, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 通过手机号码查询用户、角色信息
	 * @param phone 手机号码
	 * @param from 调用标志
	 * @return R
	 */
	@GetMapping("/app/info/{phone}")
	R<UserInfo> infoByMobile(@PathVariable("phone") String phone, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * @param openid: 微信小程序用户唯一ID
	 * @param from: 调用标志
	 * @return R<UserInfo>
	 * @author 徐志炫
	 * @description 通过openid查询用户、角色信息
	 * @date 2022/10/26 15:05
	 */
	@GetMapping("/wechat/info/{openid}")
	R<UserInfo> infoByOpenid(@PathVariable("openid") String openid, @RequestHeader(SecurityConstants.FROM) String from);

	@GetMapping("/user/check/exsit")
	R<Boolean> isExsit(UserDTO userDTO);

	@GetMapping("/wechat/exist/{openid}")
	R<Boolean> existByOpenid(@PathVariable("openid") String openid, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 根据部门id，查询对应的用户 id 集合
	 * @param deptIds 部门id 集合
	 * @param from 调用标志
	 * @return 用户 id 集合
	 */
	@GetMapping("/user/ids")
	R<List<Long>> listUserIdByDeptIds(@RequestParam("deptIds") Set<Long> deptIds,
			@RequestHeader(SecurityConstants.FROM) String from);

	@GetMapping("/user/isBusiness/{id:\\d+}")
	R<Boolean> isBusiness(@PathVariable("id") Long id);

	@GetMapping("/user/{id:\\d+}")
	R<UserVO> user(@PathVariable("id") Long id);

}
