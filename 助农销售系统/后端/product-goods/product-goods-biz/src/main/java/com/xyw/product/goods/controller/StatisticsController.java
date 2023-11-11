package com.xyw.product.goods.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyw.product.common.core.util.R;
import com.xyw.product.common.log.annotation.SysLog;
import com.xyw.product.common.security.annotation.Inner;
import com.xyw.product.common.security.util.SecurityUtils;
import com.xyw.product.goods.entity.Statistics;
import com.xyw.product.goods.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statisticsApi")
@Tag(name = "统计管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class StatisticsController {

	private final StatisticsService statisticsService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param statistics 统计
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('goods_statistics_get')")
	public R getStatisticsPage(Page page, Statistics statistics) {
		return R.ok(statisticsService.page(page, Wrappers.query(statistics)));
	}

	/**
	 * 通过id查询统计
	 * @param statisticsId id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{statisticsId:\\d+}")
	@PreAuthorize("@pms.hasPermission('goods_statistics_get')")
	public R getById(@PathVariable("statisticsId") Long statisticsId) {
		return R.ok(statisticsService.getById(statisticsId));
	}

	/**
	 * 新增统计
	 * @param statistics 统计
	 * @return R
	 */
	@Operation(summary = "新增统计", description = "新增统计")
	@SysLog("新增统计")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('goods_statistics_add')")
	public R save(@RequestBody Statistics statistics) {
		return R.ok(statisticsService.save(statistics));
	}

	/**
	 * 修改统计
	 * @param statistics 统计
	 * @return R
	 */
	@Operation(summary = "修改统计", description = "修改统计")
	@SysLog("修改统计")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('goods_statistics_edit')")
	public R updateById(@RequestBody Statistics statistics) {
		return R.ok(statisticsService.updateById(statistics));
	}

	/**
	 * 通过id删除统计
	 * @param statisticsId id
	 * @return R
	 */
	@Operation(summary = "通过id删除统计", description = "通过id删除统计")
	@SysLog("通过id删除统计")
	@DeleteMapping("/{statisticsId:\\d+}")
	@PreAuthorize("@pms.hasPermission('goods_statistics_del')")
	public R removeById(@PathVariable Long statisticsId) {
		return R.ok(statisticsService.removeById(statisticsId));
	}

	@Operation(summary = "根据商户id修改订单量", description = "根据商户id修改订单量")
	@SysLog("根据商户id修改订单量")
	@PutMapping("updateOrderSum/{BusinessId:\\d+}")
	@Inner
	public R updateOrderSumByBusinessId(@PathVariable("BusinessId") Long BusinessId) {
		return statisticsService.updateOrderSumByBusinessId(BusinessId);
	}

	@Operation(summary = "根据商户id修改访问量", description = "根据商户id修改访问量")
	@SysLog("根据商户id修改访问量")
	@PutMapping("updateDateView/{BusinessId:\\d+}")
	@Inner(false)
	public R updateDateViewByBusinessId(@PathVariable("BusinessId") Long BusinessId, @RequestParam String goodsId,
			@RequestParam(required = false) String userId) {
		return statisticsService.updateDateViewByBusinessId(BusinessId, goodsId, userId);
	}

	@Operation(summary = "查询商户数据", description = "查询商户数据")
	@SysLog("查询商户数据")
	@GetMapping("getInfo")
	public R getInfo() {
		return R.ok(statisticsService.getInfo(SecurityUtils.getUser().getId()));
	}

}