package com.xyw.product.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xyw.product.admin.api.entity.SysRoleMenu;

public interface SysRoleMenuService extends IService<SysRoleMenu> {

	/**
	 * 更新角色菜单
	 * @param roleId 角色
	 * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
	 * @return
	 */
	Boolean saveRoleMenus(Long roleId, String menuIds);

}
