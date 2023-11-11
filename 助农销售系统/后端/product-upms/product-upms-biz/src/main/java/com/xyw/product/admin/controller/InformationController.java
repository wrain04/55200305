package com.xyw.product.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyw.product.admin.api.entity.Information;
import com.xyw.product.admin.service.InformationService;
import com.xyw.product.common.core.util.R;
import com.xyw.product.common.log.annotation.SysLog;
import com.xyw.product.common.security.annotation.Inner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/informationApi")
@Tag(name = "资讯表管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class InformationController {

	private final InformationService informationService;

	/**
	 * 分页查询
	 *
	 * @param page        分页对象
	 * @param information 资讯表
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('admin_information_get')")
	public R getInformationPage(Page page, Information information) {
		return R.ok(informationService.page(page, Wrappers.query(information)));
	}

	/**
	 * 通过id查询资讯表
	 *
	 * @param informationId id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{informationId:\\d+}")
	@Inner(false)
	public R getById(@PathVariable("informationId") String informationId) {
		return R.ok(informationService.getById(informationId));
	}

	@Operation(summary = "获取n条资讯", description = "获取n条资讯")
	@GetMapping("/info/{n:\\d+}")
	@Inner(false)
	public R getInfoByNumber(@PathVariable("n") Long n) {
		return R.ok(informationService.getBaseMapper()
						.selectList(Wrappers.<Information>lambdaQuery().eq(Information::getStatus, 1).last("limit " + n)))
				.setMsg("成功获取到数据");
	}

	@Operation(summary = "获取所有资讯", description = "获取所有资讯")
	@GetMapping("/info/all")
	public R getAllInfo() {
		return R.ok(informationService.getBaseMapper()
						.selectList(Wrappers.lambdaQuery()))
				.setMsg("成功获取到数据");
	}

	@Operation(summary = "获取审核中的资讯", description = "获取审核中的资讯")
	@GetMapping("/info/review")
	public R getReviewInfo() {
		return R.ok(informationService.getBaseMapper().selectList(Wrappers.<Information>lambdaQuery()
				.eq(Information::getStatus, 0))).setMsg("成功获取到数据");
	}

	/**
	 * 新增资讯表
	 *
	 * @param information 资讯表
	 * @return R
	 */
	@Operation(summary = "新增资讯", description = "新增资讯")
	@SysLog("新增资讯")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('admin_information_add')")
	public R save(@RequestBody Information information) {
		return R.ok(informationService.save(information));
	}

	/**
	 * 修改资讯表
	 *
	 * @param information 资讯表
	 * @return R
	 */
	@Operation(summary = "修改资讯", description = "修改资讯")
	@SysLog("修改资讯")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('admin_information_edit')")
	public R updateById(@RequestBody Information information) {
		return R.ok(informationService.updateById(information));
	}

	/**
	 * 通过id删除资讯表
	 *
	 * @param informationId id
	 * @return R
	 */
	@Operation(summary = "通过id删除资讯", description = "通过id删除资讯")
	@SysLog("通过id删除资讯")
	@DeleteMapping("/{informationId}")
	@PreAuthorize("@pms.hasPermission('admin_information_del')")
	public R removeById(@PathVariable Long informationId) {
		return R.ok(informationService.removeById(informationId));
	}

}
