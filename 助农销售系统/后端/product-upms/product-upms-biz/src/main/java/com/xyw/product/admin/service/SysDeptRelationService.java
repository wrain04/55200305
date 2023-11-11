package com.xyw.product.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xyw.product.admin.api.entity.SysDept;
import com.xyw.product.admin.api.entity.SysDeptRelation;

public interface SysDeptRelationService extends IService<SysDeptRelation> {

	/**
	 * 新建部门关系
	 * @param sysDept 部门
	 */
	void saveDeptRelation(SysDept sysDept);

	/**
	 * 通过ID删除部门关系
	 * @param id
	 */
	void removeDeptRelationById(Long id);

	/**
	 * 更新部门关系
	 * @param relation
	 */
	void updateDeptRelation(SysDeptRelation relation);

}
