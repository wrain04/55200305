package com.xyw.product.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xyw.product.admin.api.dto.UserInfo;
import com.xyw.product.admin.api.entity.SysUser;
import com.xyw.product.admin.service.SysUserService;
import com.xyw.product.common.core.exception.ErrorCodes;
import com.xyw.product.common.core.util.MsgUtils;
import com.xyw.product.common.core.util.R;
import com.xyw.product.common.security.annotation.Inner;
import com.xyw.product.common.security.util.SecurityUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/wechat")
@Tag(name = "移动端登录模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class WechatController {

	private final SysUserService userService;

	@Inner
	@GetMapping("/info/{openid}")
	public R<UserInfo> infoByOpenid(@PathVariable("openid") String openid) {
		SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getOpenid, openid));
		if (user == null)
			return R.failed(MsgUtils.getMessage(ErrorCodes.SYS_USER_USERINFO_EMPTY, openid));
		return R.ok(userService.getUserInfo(user));
	}

	@Inner
	@GetMapping("/exist/{openid}")
	R<Boolean> existByOpenid(@PathVariable("openid") String openid) {
		SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getOpenid, openid));
		if (user == null)
			return R.ok(false);
		return R.ok(true);
	}

	@PutMapping("/updateInfo")
	R<Boolean> updateInfo(@RequestParam("username") String username, @RequestParam("avatar") String avatar) {
		SysUser user = userService
				.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getOpenid, SecurityUtils.getUser().getOpenid()));
		if (user == null)
			return R.failed(false);
		user.setUsername(username);
		user.setAvatar(avatar);
		return R.ok(userService.updateById(user));
	}

	@GetMapping("/isFresh")
	R<Boolean> isFresh() {
		SysUser user = userService
				.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getOpenid, SecurityUtils.getUser().getOpenid()));
		if (user == null)
			return R.failed(false);
		if ("微信用户".equals(user.getUsername()))
			return R.ok(true);
		return R.ok(false);
	}

}