package com.xyw.product.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyw.product.admin.api.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

	/**
	 * 通过角色编号查询菜单
	 * @param roleId 角色ID
	 * @return
	 */
	Set<SysMenu> listMenusByRoleId(Long roleId);

}
