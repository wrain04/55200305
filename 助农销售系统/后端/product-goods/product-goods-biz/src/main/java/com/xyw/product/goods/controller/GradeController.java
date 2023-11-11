package com.xyw.product.goods.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyw.product.admin.api.feign.RemoteFileService;
import com.xyw.product.common.core.util.R;
import com.xyw.product.common.log.annotation.SysLog;
import com.xyw.product.common.security.annotation.Inner;
import com.xyw.product.goods.dto.GradeDTO;
import com.xyw.product.goods.entity.Grade;
import com.xyw.product.goods.service.GradeService;
import com.xyw.product.goods.util.ImageSizeUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/gradeApi")
@Tag(name = "品级管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class GradeController {

	private final GradeService gradeService;

	private final RemoteFileService fileService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param grade 品级表
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('goods_grade_get')")
	public R getGradePage(Page page, Grade grade) {
		return R.ok(gradeService.page(page, Wrappers.query(grade)));
	}

	/**
	 * 新增品级
	 * @param dto 品级DTO
	 * @return R
	 */
	@Operation(summary = "新增品级", description = "新增品级")
	@SysLog("新增品级")
	@PostMapping
	// @PreAuthorize("@pms.hasPermission('goods_grade_add')")
	public R save(@RequestBody GradeDTO dto) {
		return R.ok(gradeService.save(BeanUtil.copyProperties(dto, Grade.class)));
	}

	/**
	 * 修改品级
	 * @param grade 品级
	 * @return R
	 */
	@Operation(summary = "修改品级", description = "修改品级")
	@SysLog("修改品级表")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('goods_grade_edit')")
	public R updateById(@RequestBody Grade grade) {
		return R.ok(gradeService.updateById(grade));
	}

	/**
	 * 通过id删除品级表
	 * @param gradeId id
	 * @return R
	 */
	@Operation(summary = "通过id删除品级", description = "通过id删除品级")
	@SysLog("通过id删除品级")
	@DeleteMapping("/{gradeId:\\d+}")
	@PreAuthorize("@pms.hasPermission('goods_grade_del')")
	public R removeById(@PathVariable Long gradeId) {
		return R.ok(gradeService.removeById(gradeId));
	}

	/**
	 * 通过分类ID查询品级
	 * @param categoryId id
	 * @return R
	 */
	@Inner(value = false)
	@Operation(summary = "通过category_id查询", description = "通过category_id查询")
	@GetMapping("/getByCategoryId/{categoryId:\\d+}")
	public R getByCategoryId(@PathVariable("categoryId") String categoryId) {
		return R.ok(gradeService.list(Wrappers.<Grade>query().lambda().eq(Grade::getCategoryId, categoryId)));
	}

	/**
	 * 通过id查询品级表
	 * @param gradeId id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{gradeId:\\d+}")
	public R getById(@PathVariable("gradeId") String gradeId) {
		return R.ok(gradeService.getById(gradeId));
	}

	/**
	 * @param gradeId:
	 * @return R
	 * @author 徐志炫
	 * @description 前台通过id查询
	 * @date 2023/1/20 18:57
	 */
	@Inner(false)
	@Operation(summary = "前台通过id查询", description = "前台通过id查询")
	@GetMapping("/getById/{gradeId:\\d+}")
	public R getInfoById(@PathVariable("gradeId") String gradeId) {
		return R.ok(gradeService.getOne(Wrappers.<Grade>lambdaQuery().eq(Grade::getGradeId, gradeId)));
	}

}