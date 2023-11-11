package com.xyw.product.goods.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyw.product.admin.api.feign.RemoteFileService;
import com.xyw.product.common.core.util.R;
import com.xyw.product.common.log.annotation.SysLog;
import com.xyw.product.common.security.annotation.Inner;
import com.xyw.product.goods.dto.CategoryDTO;
import com.xyw.product.goods.entity.Category;
import com.xyw.product.goods.service.CategoryService;
import com.xyw.product.goods.vo.CategoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categoryApi")
@Tag(name = "分类")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CategoryController {

	private final CategoryService categoryService;

	private final RemoteFileService fileService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param category
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('category_category_get')")
	public R<Object> getCategoryPage(Page page, Category category) {
		return R.ok(categoryService.page(page, Wrappers.query(category)));
	}

	/**
	 * 通过id查询
	 * @param categoryId id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{categoryId:\\d+}")
	@PreAuthorize("@pms.hasPermission('category_category_get')")
	public R<Object> getById(@PathVariable("categoryId") Long categoryId) {
		return R.ok(categoryService.getById(categoryId));
	}

	/**
	 * 新增
	 * @param categoryDTO
	 * @return R
	 */
	@Operation(summary = "新增", description = "新增")
	@SysLog("新增")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('category_category_add')")
	public R<Object> save(@RequestBody CategoryDTO categoryDTO) {
		return R.ok(categoryService.save(BeanUtil.copyProperties(categoryDTO, Category.class)));
	}

	/**
	 * 修改
	 * @param category
	 * @return R
	 */
	@Operation(summary = "修改", description = "修改")
	@SysLog("修改")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('category_category_edit')")
	public R<Object> updateById(@RequestBody Category category) {
		return R.ok(categoryService.updateById(category));
	}

	/**
	 * 通过id删除
	 * @param categoryId id
	 * @return R
	 */
	@Operation(summary = "通过id删除", description = "通过id删除")
	@SysLog("通过id删除")
	@DeleteMapping("/{categoryId:\\d+}")
	@PreAuthorize("@pms.hasPermission('category_category_del')")
	public R<Object> removeById(@PathVariable Long categoryId) {
		return R.ok(categoryService.removeById(categoryId));
	}

	/**
	 * @param :
	 * @return R<List < CategoryVO>>
	 * @author 徐志炫
	 * @description 直接查询出父ID为空的分类即为一级分类
	 * @date 2022/10/28 15:17
	 */
	@Operation(summary = "查询一级分类", description = "查询一级分类")
	@SysLog("查询一级分类")
	@Inner(value = false)
	@GetMapping("/info/parent")
	// @PreAuthorize("@pms.hasPermission('category_info')")
	public R<List<CategoryVO>> getCategoryInfo() {
		return R.ok(categoryService.getCategoryParentInfo());
	}

	/**
	 * @param parentId:
	 * @return R<List < CategoryVO>>
	 * @author 徐志炫
	 * @description 通过父ID查询子分类的信息
	 * @date 2022/10/28 15:17
	 */
	@Operation(summary = "查询子分类", description = "查询子分类")
	@SysLog("查询子分类")
	@Inner(value = false)
	@GetMapping("/info/{parentId:\\d+}")
	// @PreAuthorize("@pms.hasPermission('category_info')")
	public R<List<CategoryVO>> getCategoryInfo(@PathVariable String parentId) {
		return R.ok(categoryService.getCategoryInfo(parentId, fileService));
	}

	@Operation(summary = "搜索分类", description = "搜索分类")
	@SysLog("搜索分类")
	@Inner(value = false)
	@GetMapping("/search")
	public R<List<CategoryVO>> searchCategoryInfo(@RequestParam String categoryName) {
		return R.ok(categoryService.searchCategoryInfo(categoryName));
	}

}