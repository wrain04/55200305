package com.xyw.product.admin.api.dto;

import com.xyw.product.admin.api.entity.SysUser;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends SysUser {

	/**
	 * 角色ID
	 */
	private List<Long> role;

	private Long deptId;

	/**
	 * 岗位ID
	 */
	private List<Long> post;

	/**
	 * 新密码
	 */
	private String newpassword1;

	/**
	 * 验证码
	 */
	private String code;

}
