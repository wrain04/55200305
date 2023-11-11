package com.xyw.product.goods.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyw.product.admin.api.feign.RemoteFileService;
import com.xyw.product.common.core.constant.ProductConstants;
import com.xyw.product.common.core.util.R;
import com.xyw.product.common.log.annotation.SysLog;
import com.xyw.product.common.security.annotation.Inner;
import com.xyw.product.common.security.util.SecurityUtils;
import com.xyw.product.goods.dto.GoodsDTO;
import com.xyw.product.goods.entity.Good;
import com.xyw.product.goods.service.GoodsService;
import com.xyw.product.goods.util.ImageSizeUtil;
import com.xyw.product.goods.vo.GoodsVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.xyw.product.common.core.constant.ProductConstants.IN_PROCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/goodApi")
@Tag(name = "商品")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class GoodsController {

	private final GoodsService goodsService;

	private final RemoteFileService fileService;

	/**
	 * 新增
	 * @param good
	 * @return R
	 */
	@Operation(summary = "新增", description = "新增")
	@SysLog("新增")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('goods_goods_add')")
	public R save(@RequestBody Good good) {
		return R.ok(goodsService.save(good));
	}

	/**
	 * 修改
	 * @param goods
	 * @return R
	 */
	@Operation(summary = "修改", description = "修改")
	@SysLog("修改")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('goods_goods_edit')")
	public R updateById(@RequestBody GoodsDTO dto) {
		return goodsService.updateGoods(dto);
	}

	/**
	 * 通过id删除
	 * @param goodsId id
	 * @return R
	 */
	@Operation(summary = "通过id删除", description = "通过id删除")
	@SysLog("通过id删除")
	@DeleteMapping("/{goodsId:\\d+}")
	@PreAuthorize("@pms.hasPermission('goods_goods_del')")
	public R removeById(@PathVariable Long goodsId) {
		return R.ok(goodsService.removeById(goodsId));
	}

	/**
	 * @param dto:
	 * @return R
	 * @author 徐志炫
	 * @description 商户上架商品
	 * @date 2022/10/27 21:05
	 */
	@Operation(summary = "商户上架商品", description = "商户上架商品")
	@SysLog("商户上架商品")
	@PostMapping("/register")
	@PreAuthorize("@pms.hasPermission('goods_register')")
	public R registerGoods(@RequestBody GoodsDTO dto) {
		return goodsService.registerGoods(dto);
	}

	@Operation(summary = "商户上传图片", description = "商户上传图片")
	@SysLog("商户上传图片")
	@PostMapping("/upload")
	@PreAuthorize("@pms.hasPermission('goods_register')")
	public R uploadImage(MultipartFile file) {
		try {
			if (!("jpg".contains(ImageSizeUtil.getFileType(file)))) {
				return R.failed("文件格式不对，仅支持jpg/jpeg图片");
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		MultipartFile[] fun = ImageSizeUtil.byte2Base64StringFun(file);
		return fileService.upload(fun[0]);
	}

	@Operation(summary = "商户修改自己的商品", description = "商户修改自己的商品")
	@SysLog("商户修改自己的商品")
	@PutMapping("updateGoods")
	@PreAuthorize("@pms.hasPermission('goods_update')")
	public R updateGoods(@RequestBody GoodsDTO dto) {
		return goodsService.updateGoods(dto);
	}

	@Operation(summary = "商户查询自己的商品", description = "商户查询自己的商品")
	@SysLog("商户查询自己的商品")
	@GetMapping("/getBusinessGetInfo")
	public R getBusinessGetInfo(Page page) {
		return R.ok(goodsService.getGoodsByBusinessId(page, SecurityUtils.getUser().getId())).setMsg("成功获取到数据");
	}

	@Inner(false)
	@Operation(summary = "根据品级ID分页查询商品", description = "根据品级ID分页查询商品")
	@SysLog("根据品级ID分页查询商品")
	@GetMapping("/category")
	public R<Map<String, Object>> getGoodsInfoByCategoryIdAndGradeId(Page page, @RequestParam String gradeId) {
		return R.ok(goodsService.getGoodsInfoByGradeId(page, gradeId));
	}

	/**
	 * @param n:
	 * @return R<List < GoodsVO>>
	 * @author 徐志炫
	 * @description 通过算法获取几条资讯传给用户 TODO 目前使用随机算法，后续修改算法
	 * @date 2023/1/17 13:09
	 */
	@Inner(false)
	@Operation(summary = "随机获取几条商品数据到轮播图", description = "随机获取几条商品数据到轮播图")
	@SysLog("随机获取几条商品数据到轮播图")
	@GetMapping("/rotationChart/{n:\\d+}")
	public R<List<GoodsVO>> rotationChartRandomInfo(@PathVariable Long n) {
		return R.ok(goodsService.getGoodsInfoByRandom(n)).setMsg("成功获取到数据");
	}

	@Inner(false)
	@Operation(summary = "根据商户id查询商品", description = "根据商户id查询商品")
	@SysLog("根据商户id查询商品")
	@GetMapping("/getByBusinessId/{businessId:\\d+}")
	public R getByBusinessId(Page page, @PathVariable Long businessId) {
		return R.ok(goodsService.getByBusinessId(page, businessId)).setMsg("成功获取到数据");
	}

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param goods
	 * @return
	 */

	@Operation(summary = "后台分页查询", description = "后台分页查询")
	@PostMapping("/page")
	public R getGoodsPage(Page page, Good goods) {
		return R.ok(goodsService.page(page, Wrappers.lambdaQuery(goods)));
	}

	@Inner(false)
	@Operation(summary = "前台分页查询", description = "前台分页查询")
	@PostMapping("/pageInfo")
	public R getGoodsInfoPage(Page page, Good goods) {
		return R.ok(goodsService.page(page, Wrappers.lambdaQuery(goods.setStatus(null)).between(Good::getStatus,
				ProductConstants.IN_SALES, ProductConstants.OUT_OF_STOCK)));
	}

	/**
	 * 通过id查询
	 * @param goodId id
	 * @return R
	 */

	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{goodId:\\d+}")
	public R<GoodsVO> getById(@PathVariable("goodId") String goodId) {
		return R.ok(goodsService.getByIdToVO(goodId));
	}

	@Operation(summary = "通过idList查询", description = "通过idList查询")
	@GetMapping("getByIdList")
	public R<List<GoodsVO>> getByIdList(@RequestParam List<String> goodIdList) {
		return R.ok(goodsService.getByIdListToVO(goodIdList));
	}

	@Operation(summary = "前台通过id查询商品信息", description = "前台通过id查询商品信息")
	@GetMapping("/info/{goodId:\\d+}")
	@Inner(false)
	public R<GoodsVO> getInfoById(@PathVariable("goodId") String goodId) {
		return R.ok(goodsService.getByIdToVO(goodId));
	}

	@Operation(summary = "获取审核中的商品", description = "获取审核中的商品")
	@GetMapping("/reviewInfo")
	public R reviewInfo(Page page, Good goods) {
		return R.ok(goodsService.page(page, Wrappers.query(goods).eq("status", IN_PROCESS)));
	}

}