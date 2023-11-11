package com.xyw.product.goods.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyw.product.common.core.util.R;
import com.xyw.product.common.log.annotation.SysLog;
import com.xyw.product.common.security.annotation.Inner;
import com.xyw.product.goods.entity.Updates;
import com.xyw.product.goods.service.UpdatesService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/updatesApi")
public class UpdatesController {

	/**
	 * 服务对象
	 */
	@Resource
	private UpdatesService updatesService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param updates
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('goods_Updates_get')")
	public R getUpdatesPage(Page page, Updates updates) {
		return R.ok(updatesService.page(page, Wrappers.query(updates)));
	}

	/**
	 * 新增
	 * @param updates
	 * @return R
	 */
	@Operation(summary = "新增", description = "新增")
	@SysLog("新增")
	@PostMapping
	public R save(@RequestBody Updates updates) {
		return R.ok(updatesService.save(updates));
	}

	/**
	 * 修改
	 * @param updates
	 * @return R
	 */
	@Operation(summary = "修改", description = "修改")
	@SysLog("修改")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('goods_Updates_edit')")
	public R updateById(@RequestBody Updates updates) {
		return R.ok(updatesService.updateById(updates));
	}

	/**
	 * 通过id删除
	 * @param BusinessId id
	 * @return R
	 */
	@Operation(summary = "通过id删除", description = "通过id删除")
	@SysLog("通过id删除")
	@DeleteMapping("/{BusinessId}")
	@PreAuthorize("@pms.hasPermission('goods_Updates_del')")
	public R removeById(@PathVariable Long BusinessId) {
		return R.ok(updatesService.removeById(BusinessId));
	}

	/**
	 * 根据商户id查询
	 * @return R
	 */
	@Operation(summary = "根据商户id查询", description = "根据商户id查询")
	@SysLog("根据商户id查询")
	@GetMapping("/info@{businessId:\\d+}")
	@Inner(false)
	public R selectByBusinessId(Page page, @PathVariable("businessId") Long businessId) {
		return R.ok(updatesService.selectByBusinessId(page, businessId));
	}

}
