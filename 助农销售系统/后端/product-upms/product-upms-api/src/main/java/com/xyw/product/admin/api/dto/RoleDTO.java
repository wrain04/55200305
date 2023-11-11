package com.xyw.product.admin.api.dto;

import com.xyw.product.admin.api.entity.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends SysRole {

	/**
	 * 角色部门Id
	 */
	private Long roleDeptId;

	/**
	 * 部门名称
	 */
	private String deptName;

}
