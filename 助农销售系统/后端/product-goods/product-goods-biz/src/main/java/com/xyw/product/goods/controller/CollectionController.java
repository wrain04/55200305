package com.xyw.product.goods.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyw.product.common.core.util.R;
import com.xyw.product.common.log.annotation.SysLog;
import com.xyw.product.common.security.util.SecurityUtils;
import com.xyw.product.goods.entity.Collection;
import com.xyw.product.goods.service.CollectionService;
import com.xyw.product.goods.vo.CollectionVO;
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
@RequestMapping("/collectionApi")
@Tag(name = "收藏表管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CollectionController {

	private final CollectionService collectionService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param collection 收藏表
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('goods_collection_get')")
	public R getCollectionPage(Page page, Collection collection) {
		return R.ok(collectionService.page(page, Wrappers.query(collection)));
	}

	/**
	 * 通过id查询收藏表
	 * @param collectionId id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{collectionId:\\d+}")
	@PreAuthorize("@pms.hasPermission('goods_collection_get')")
	public R getById(@PathVariable("collectionId") Long collectionId) {
		return R.ok(collectionService.getById(collectionId));
	}

	/**
	 * 新增收藏表
	 * @param vo 收藏表VO
	 * @return R
	 */
	@Operation(summary = "新增收藏表", description = "新增收藏表")
	@SysLog("新增收藏表")
	@PostMapping
	// @PreAuthorize("@pms.hasPermission('goods_collection_add')")
	public R save(@RequestBody CollectionVO vo) {
		return R.ok(collectionService
				.save(BeanUtil.copyProperties(vo, Collection.class).setInfo(JSONUtil.toJsonStr(vo.getInfoArray()))));
	}

	/**
	 * 修改收藏表
	 * @param collection 收藏表
	 * @return R
	 */
	@Operation(summary = "修改收藏表", description = "修改收藏表")
	@SysLog("修改收藏表")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('goods_collection_edit')")
	public R updateById(@RequestBody Collection collection) {
		return R.ok(collectionService.updateById(collection));
	}

	/**
	 * 通过id删除收藏表
	 * @param collectionId id
	 * @return R
	 */
	@Operation(summary = "通过id删除收藏表", description = "通过id删除收藏表")
	@SysLog("通过id删除收藏表")
	@DeleteMapping("/{collectionId:\\d+}")
	@PreAuthorize("@pms.hasPermission('goods_collection_del')")
	public R removeById(@PathVariable Long collectionId) {
		return R.ok(collectionService.removeById(collectionId));
	}

	@Operation(summary = "用户根据标识查询个人收藏商品或店铺", description = "用户根据标识查询个人收藏商品或店铺")
	@SysLog("用户根据标识查询个人收藏商品或者店铺")
	@GetMapping("/getCollectWithMark")
	public R getCollectWithMark(@RequestParam Byte mark) {
		return R.ok(collectionService.getCollectByUserIdAndMark(SecurityUtils.getUser().getId(), mark));
	}

	@Operation(summary = "用户收藏商品或店铺并用标识区分", description = "用户收藏商品或店铺并用标识区分")
	@SysLog("用户收藏商品或店铺并用标识区分")
	@PostMapping("/collectWithMark")
	public R collectWithMark(@RequestParam Byte mark, @RequestParam String dataId) {
		return collectionService.collectWithMark(SecurityUtils.getUser().getId(), dataId, mark);
	}

	@Operation(summary = "用户移除收藏或足迹并用标识区分", description = "用户移除收藏或足迹并用标识区分")
	@SysLog("用户移除收藏或足迹并用标识区分")
	@DeleteMapping("/removeWithMark")
	public R removeWithMark(@RequestParam Byte mark, @RequestParam List<String> dataIds) {
		return collectionService.removeWithMark(SecurityUtils.getUser().getId(), dataIds, mark);
	}

	@Operation(summary = "判断用户是否已收藏商品或店铺并用标识区分", description = "判断用户是否已收藏商品或店铺并用标识区分")
	@SysLog("判断用户是否已收藏商品或店铺并用标识区分")
	@GetMapping("/isCollect")
	public R isCollect(@RequestParam Byte mark, @RequestParam String dataId) {
		if (SecurityUtils.getUser().getId() == null) {
			return R.failed("用户未登录");
		}
		return R.ok(collectionService.isCollect(SecurityUtils.getUser().getId(), dataId, mark));
	}

	@Operation(summary = "查询用户的收藏，足迹", description = "查询用户的收藏，足迹")
	@SysLog("查询用户的收藏，足迹")
	@GetMapping("/simple")
	public R getSimpleInfo() {
		return R.ok(collectionService.getSimpleInfo(SecurityUtils.getUser().getId()));
	}

}