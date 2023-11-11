package com.xyw.product.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xyw.product.admin.api.entity.SysRole;
import com.xyw.product.admin.api.vo.RoleExcelVO;
import com.xyw.product.common.core.util.R;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

	/**
	 * 通过角色ID，删除角色
	 * @param id
	 * @return
	 */
	Boolean removeRoleById(Long id);

	/**
	 * 导入角色
	 * @param excelVOList 角色列表
	 * @param bindingResult 错误信息列表
	 * @return ok fail
	 */
	R importRole(List<RoleExcelVO> excelVOList, BindingResult bindingResult);

	/**
	 * 查询全部的角色
	 * @return list
	 */
	List<RoleExcelVO> listRole();

}
