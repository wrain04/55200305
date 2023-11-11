package com.xyw.product.admin.api.dto;

import com.xyw.product.admin.api.entity.SysPost;
import com.xyw.product.admin.api.entity.SysRole;
import com.xyw.product.admin.api.entity.SysUser;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserInfo implements Serializable {

	/**
	 * 用户基本信息
	 */
	private SysUser sysUser;

	/**
	 * 权限标识集合
	 */
	private String[] permissions;

	/**
	 * 角色集合
	 */
	private Long[] roles;

	/**
	 * 角色集合
	 */
	private List<SysRole> roleList;

	/**
	 * 岗位集合
	 */
	private Long[] posts;

	/**
	 * 岗位集合
	 */
	private List<SysPost> postList;

}
