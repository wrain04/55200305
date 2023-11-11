package com.xyw.product.admin.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyw.product.admin.api.entity.SysFile;
import com.xyw.product.admin.service.SysFileService;
import com.xyw.product.common.core.util.R;
import com.xyw.product.common.log.annotation.SysLog;
import com.xyw.product.common.security.annotation.Inner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sys-file")
@Tag(name = "文件管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class FileController {

	private final SysFileService sysFileService;

	/**
	 * 分页查询
	 *
	 * @param page    分页对象
	 * @param sysFile 文件管理
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	public R<IPage<SysFile>> getSysFilePage(Page page, SysFile sysFile) {
		return R.ok(sysFileService.page(page, Wrappers.<SysFile>lambdaQuery()
				.like(StrUtil.isNotBlank(sysFile.getFileName()), SysFile::getFileName, sysFile.getFileName())));
	}

	/**
	 * 通过id删除文件管理
	 *
	 * @param id id
	 * @return R
	 */
	@Operation(summary = "通过id删除文件管理", description = "通过id删除文件管理")
	@SysLog("删除文件管理")
	@DeleteMapping("/{id:\\d+}")
	@PreAuthorize("@pms.hasPermission('sys_file_del')")
	public R<Boolean> removeById(@PathVariable Long id) {
		return R.ok(sysFileService.deleteFile(id));
	}

	/**
	 * 上传文件 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
	 *
	 * @param file 资源
	 * @return R(/ admin / bucketName / filename)
	 */
	@PostMapping(value = "/upload")
	public R upload(@RequestPart("file") MultipartFile file) {
		if (file == null) {
			return R.failed("文件为空");
		}
		if (!SysFileService.checkFileSizeIsLimit(file.getSize(), 1, "M")) {
			return R.failed("文件大于1M,请重新上传");
		}
		return sysFileService.uploadFileByToken(file);
	}

	@PostMapping(value = "/uploadByBase64")
	public R uploadByBase64(String base64) {
		if (StrUtil.isBlankIfStr(base64)) {
			return R.failed("文件为空");
		}
		return sysFileService.uploadBase64ByToken(base64);
	}

	/**
	 * 获取文件
	 *
	 * @param bucket   桶名称
	 * @param fileName 文件空间/名称
	 * @param response
	 * @return
	 */
	@Inner(false)
	@GetMapping("/{bucket}/{fileName}")
	public void file(@PathVariable String bucket, @PathVariable String fileName, HttpServletResponse response) {
		sysFileService.getFile(bucket, fileName, response);
	}

	/**
	 * 获取本地（resources）文件
	 *
	 * @param fileName 文件名称
	 * @param response 本地文件
	 */
	@SneakyThrows
	@GetMapping("/local/{fileName}")
	public void localFile(@PathVariable String fileName, HttpServletResponse response) {
		ClassPathResource resource = new ClassPathResource("file/" + fileName);
		response.setContentType("application/octet-stream; charset=UTF-8");
		IoUtil.copy(resource.getInputStream(), response.getOutputStream());
	}

	/**
	 * 获取文件外网的访问地址
	 *
	 * @param bucket
	 * @param fileName
	 * @return
	 */
	@Inner(false)
	@GetMapping("/online/{bucket}/{fileName}")
	public R<String> onlineFile(@PathVariable String bucket, @PathVariable String fileName) {
		return R.ok(sysFileService.onlineFile(bucket, fileName));
	}

}
