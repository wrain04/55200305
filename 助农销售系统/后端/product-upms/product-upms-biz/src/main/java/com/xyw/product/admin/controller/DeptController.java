package com.xyw.product.admin.controller;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xyw.product.admin.api.entity.SysDept;
import com.xyw.product.admin.service.SysDeptService;
import com.xyw.product.common.core.util.R;
import com.xyw.product.common.log.annotation.SysLog;
import com.xyw.product.common.security.annotation.Inner;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dept")
@Tag(name = "部门管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class DeptController {

	private final SysDeptService sysDeptService;

	/**
	 * 通过ID查询
	 * @param id ID
	 * @return SysDept
	 */
	@GetMapping("/{id:\\d+}")
	public R<SysDept> getById(@PathVariable Long id) {
		return R.ok(sysDeptService.getById(id));
	}

	/**
	 * 返回树形菜单集合
	 * @return 树形菜单
	 */
	@GetMapping(value = "/tree")
	public R<List<Tree<Long>>> listDeptTrees() {
		return R.ok(sysDeptService.listDeptTrees());
	}

	/**
	 * 返回当前用户树形菜单集合
	 * @return 树形菜单
	 */
	@GetMapping(value = "/user-tree")
	public R<List<Tree<Long>>> listCurrentUserDeptTrees() {
		return R.ok(sysDeptService.listCurrentUserDeptTrees());
	}

	/**
	 * 添加
	 * @param sysDept 实体
	 * @return success/false
	 */
	@SysLog("添加部门")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_dept_add')")
	public R<Boolean> save(@Valid @RequestBody SysDept sysDept) {
		return R.ok(sysDeptService.saveDept(sysDept));
	}

	/**
	 * 删除
	 * @param id ID
	 * @return success/false
	 */
	@SysLog("删除部门")
	@DeleteMapping("/{id:\\d+}")
	@PreAuthorize("@pms.hasPermission('sys_dept_del')")
	public R<Boolean> removeById(@PathVariable Long id) {
		return R.ok(sysDeptService.removeDeptById(id));
	}

	/**
	 * 编辑
	 * @param sysDept 实体
	 * @return success/false
	 */
	@SysLog("编辑部门")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_dept_edit')")
	public R<Boolean> update(@Valid @RequestBody SysDept sysDept) {
		return R.ok(sysDeptService.updateDeptById(sysDept));
	}

	/**
	 * 根据部门名查询部门信息
	 * @param deptname 部门名
	 * @return
	 */
	@GetMapping("/details/{deptname}")
	public R<SysDept> user(@PathVariable String deptname) {
		SysDept condition = new SysDept();
		condition.setName(deptname);
		return R.ok(sysDeptService.getOne(new QueryWrapper<>(condition)));
	}

	/**
	 * 查收子级id列表
	 * @return 返回子级id列表
	 */
	@Inner
	@GetMapping(value = "/child-id/{deptId:\\d+}")
	public R<List<Long>> listChildDeptId(@PathVariable Long deptId) {
		return R.ok(sysDeptService.listChildDeptId(deptId));
	}

}
