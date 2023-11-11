package com.xyw.product.goods.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyw.product.common.core.util.R;
import com.xyw.product.common.log.annotation.SysLog;
import com.xyw.product.goods.entity.LandLog;
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
@RequestMapping("/landLogApi")
@Tag(name = "地块日志管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class LandLogController {

	private final LandLogService landLogService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param landLog
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('goods_landlog_get')")
	public R getLandLogPage(Page page, LandLog landLog) {
		return R.ok(landLogService.page(page, Wrappers.query(landLog)));
	}

	/**
	 * 通过id查询
	 * @param logId id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{logId}")
	@PreAuthorize("@pms.hasPermission('goods_landlog_get')")
	public R getById(@PathVariable("logId") Long logId) {
		return R.ok(landLogService.getById(logId));
	}

	/**
	 * 新增
	 * @param landLog
	 * @return R
	 */
	@Operation(summary = "新增", description = "新增")
	@SysLog("新增")
	@PostMapping
	public R save(@RequestBody LandLog landLog) {
		return R.ok(landLogService.save(landLog));
	}

	/**
	 * 修改
	 * @param landLog
	 * @return R
	 */
	@Operation(summary = "修改", description = "修改")
	@SysLog("修改")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('goods_landlog_edit')")
	public R updateById(@RequestBody LandLog landLog) {
		return R.ok(landLogService.updateById(landLog));
	}

	/**
	 * 通过id删除
	 * @param logId id
	 * @return R
	 */
	@Operation(summary = "通过id删除", description = "通过id删除")
	@SysLog("通过id删除")
	@DeleteMapping("/{logId}")
	@PreAuthorize("@pms.hasPermission('goods_landlog_del')")
	public R removeById(@PathVariable Long logId) {
		return R.ok(landLogService.removeById(logId));
	}

	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/getBySelf")
	public R getBySelf(String landId) {
		return R.ok(landLogService.getBySelf(landId));
	}

}
