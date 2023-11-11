package com.xyw.product.goods.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyw.product.common.core.util.R;
import com.xyw.product.common.log.annotation.SysLog;
import com.xyw.product.common.security.annotation.Inner;
import com.xyw.product.goods.dto.TimeChartDTO;
import com.xyw.product.goods.entity.TimeChart;
import com.xyw.product.goods.service.TimeChartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/timechartApi")
@Tag(name = "管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class TimeChartController {

	private final TimeChartService timeChartService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param timeChart
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('goods_timechart_get')")
	public R getTimeChartPage(Page page, TimeChart timeChart) {
		return R.ok(timeChartService.page(page, Wrappers.query(timeChart)));
	}

	/**
	 * 通过id查询
	 * @param chartId id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{chartId:\\d+}")
	@PreAuthorize("@pms.hasPermission('goods_timechart_get')")
	public R getById(@PathVariable("chartId") Long chartId) {
		return R.ok(timeChartService.getById(chartId));
	}

	/**
	 * 新增
	 * @param dto
	 * @return R
	 */
	// TODO 后台权限
	@Operation(summary = "新增", description = "新增")
	@SysLog("新增")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('goods_timechart_add')")
	public R save(@RequestBody TimeChartDTO dto) {
		return R.ok(timeChartService.save(new TimeChart().setGoodId(dto.getGoodId()).setTime(dto.getTime())
				.setDescription(dto.getDescription()).setImages(JSONUtil.toJsonStr(dto.getImageList()))));
	}

	/**
	 * 修改
	 * @param timeChart
	 * @return R
	 */
	@Operation(summary = "修改", description = "修改")
	@SysLog("修改")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('goods_timechart_edit')")
	public R updateById(@RequestBody TimeChart timeChart) {
		return R.ok(timeChartService.updateById(timeChart));
	}

	/**
	 * 通过id删除
	 * @param chartId id
	 * @return R
	 */
	@Operation(summary = "通过id删除", description = "通过id删除")
	@SysLog("通过id删除")
	@DeleteMapping("/{chartId:\\d+}")
	@PreAuthorize("@pms.hasPermission('goods_timechart_del')")
	public R removeById(@PathVariable Long chartId) {
		return R.ok(timeChartService.removeById(chartId));
	}

	@Inner(value = false)
	@Operation(summary = "通过商品id查询", description = "通过商品id查询")
	@SysLog("通过商品id查询")
	@GetMapping("/getInfo")
	public R getInfo(@RequestParam String goodId) {
		return R.ok(timeChartService.getByGoodId(goodId));
	}

}
