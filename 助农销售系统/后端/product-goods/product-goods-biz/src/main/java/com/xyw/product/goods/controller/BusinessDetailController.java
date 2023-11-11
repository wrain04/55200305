package com.xyw.product.goods.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyw.product.admin.api.feign.RemoteFileService;
import com.xyw.product.common.core.util.R;
import com.xyw.product.common.log.annotation.SysLog;
import com.xyw.product.common.security.annotation.Inner;
import com.xyw.product.common.security.util.SecurityUtils;
import com.xyw.product.goods.dto.BusinessDetailDTO;
import com.xyw.product.goods.entity.BusinessDetail;
import com.xyw.product.goods.service.BusinessDetailService;
import com.xyw.product.goods.util.ImageSizeUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/businessDetailApi")
@Tag(name = "管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class BusinessDetailController {

	private final BusinessDetailService businessDetailService;

	private final RemoteFileService fileService;

	/**
	 * 分页查询
	 *
	 * @param page           分页对象
	 * @param BusinessDetail
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('goods_Businessdetail_get')")
	public R getBusinessDetailPage(Page page, BusinessDetail BusinessDetail) {
		return R.ok(businessDetailService.page(page, Wrappers.query(BusinessDetail)));
	}

	/**
	 * 通过id查询
	 *
	 * @param BusinessId id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{BusinessId:\\d+}")
	@PreAuthorize("@pms.hasPermission('goods_Businessdetail_get')")
	public R getById(@PathVariable("BusinessId") Long BusinessId) {
		return R.ok(businessDetailService.getById(BusinessId));
	}

	@Operation(summary = "上传图片", description = "上传图片")
	@SysLog("上传图片")
	@PostMapping("upload")
	public R uploadImage(@RequestPart("file") MultipartFile file) {
		try {
			if (!("jpg".contains(ImageSizeUtil.getFileType(file)))) {
				return R.failed("文件格式不对，仅支持jpg/jpeg图片");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		MultipartFile[] fun = ImageSizeUtil.byte2Base64StringFun(file);
		return fileService.upload(fun[0]);
	}

	/**
	 * 新增
	 *
	 * @param dto
	 * @return R
	 */
	@Operation(summary = "新增", description = "新增")
	@SysLog("新增")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('goods_Businessdetail_add')")
	public R save(@RequestBody BusinessDetailDTO dto) {
		return uploadDetail(dto);
	}

	/**
	 * 修改
	 *
	 * @param dto
	 * @return R
	 */
	@Operation(summary = "修改", description = "修改")
	@SysLog("修改")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('goods_Businessdetail_update')")
	public R updateById(@RequestBody BusinessDetailDTO dto) {
		return uploadDetail(dto);
	}

	/**
	 * 通过id删除
	 *
	 * @param BusinessId id
	 * @return R
	 */
	@Operation(summary = "通过id删除", description = "通过id删除")
	@SysLog("通过id删除")
	@DeleteMapping("/{BusinessId}")
	@PreAuthorize("@pms.hasPermission('goods_Businessdetail_remove')")
	public R removeById(@PathVariable Long BusinessId) {
		return businessDetailService.deleteById(BusinessId);
	}

	@Operation(summary = "通过id查询商户信息", description = "通过id查询")
	@GetMapping("/getBusinessDetail/{BusinessId:\\d+}")
	@Inner(false)
	public R getBusinessDetail(@PathVariable("BusinessId") Long BusinessId) {
		return R.ok(businessDetailService.getBusinessDetail(BusinessId));
	}

	@Operation(summary = "商户上传信息审核", description = "商户上传信息审核")
	@SysLog("商户上传信息审核")
	@PostMapping("/uploadDetail")
	public R uploadDetail(@RequestBody BusinessDetailDTO dto) {
		if (StrUtil.isBlankIfStr(dto.getBusinessId())) {
			dto.setBusinessId(SecurityUtils.getUser().getId());
		}
		return businessDetailService.uploadDetail(dto);
	}

	@Operation(summary = "商户获取信息", description = "商户获取信息")
	@SysLog("商户获取信息")
	@GetMapping("/getSelfDetail")
	public R getSelfDetail() {
		return R.ok(businessDetailService.getSelfDetail());
	}

}
