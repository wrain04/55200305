package com.xyw.product.goods.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyw.product.common.core.util.R;
import com.xyw.product.common.log.annotation.SysLog;
import com.xyw.product.goods.dto.BusinessLandDTO;
import com.xyw.product.goods.entity.BusinessLand;
import com.xyw.product.goods.service.BusinessLandService;
import com.xyw.product.goods.service.LandLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/businessLandApi")
@Tag(name = "地块管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class BusinessLandController {

	private final BusinessLandService businessLandService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param businessLand
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('goods_businessland_get')")
	public R getBusinessLandPage(Page page, BusinessLand businessLand) {
		return R.ok(businessLandService.page(page, Wrappers.query(businessLand)));
	}

	/**
	 * 通过id查询
	 * @param landId id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{landId}")
	@PreAuthorize("@pms.hasPermission('goods_businessland_get')")
	public R getById(@PathVariable("landId") Long landId) {
		return R.ok(businessLandService.getById(landId));
	}

	/**
	 * 新增
	 * @param businessLand
	 * @return R
	 */
	@Operation(summary = "新增", description = "新增")
	@SysLog("新增")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('goods_businessland_add')")
	public R save(@RequestBody BusinessLand businessLand) {
		return R.ok(businessLandService.save(businessLand));
	}

	/**
	 * 修改
	 * @param businessLand
	 * @return R
	 */
	@Operation(summary = "修改", description = "修改")
	@SysLog("修改")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('goods_businessland_edit')")
	public R updateById(@RequestBody BusinessLand businessLand) {
		return R.ok(businessLandService.updateById(businessLand));
	}

	/**
	 * 通过id删除
	 * @param landId id
	 * @return R
	 */
	@Operation(summary = "通过id删除", description = "通过id删除")
	@SysLog("通过id删除")
	@DeleteMapping("/{landId}")
	@PreAuthorize("@pms.hasPermission('goods_businessland_del')")
	public R removeById(@PathVariable Long landId) {
		return R.ok(businessLandService.removeById(landId));
	}

	@Operation(summary = "新增地块", description = "新增地块")
	@SysLog("新增地块")
	@PostMapping("/addLand")
	@PreAuthorize("@pms.hasPermission('businessland_add')")
	public R addLand(@RequestBody BusinessLandDTO dto) {
		return R.ok(businessLandService.addLand(dto));
	}

	@Operation(summary = "获取地块信息", description = "获取地块信息")
	@SysLog("获取地块信息")
	@GetMapping("/getLands")
	public R getLands() {
		return R.ok(businessLandService.getLands());
	}

}
