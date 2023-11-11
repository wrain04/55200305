package com.xyw.product.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xyw.product.admin.api.entity.SysFile;
import com.xyw.product.common.core.util.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface SysFileService extends IService<SysFile> {

	static boolean checkFileSizeIsLimit(Long fileLen, int fileSize, String fileUnit) {
//        long len = file.length();
		double fileSizeCom = 0;
		if ("B".equals(fileUnit.toUpperCase())) {
			fileSizeCom = (double) fileLen;
		} else if ("K".equals(fileUnit.toUpperCase())) {
			fileSizeCom = (double) fileLen / 1024;
		} else if ("M".equals(fileUnit.toUpperCase())) {
			fileSizeCom = (double) fileLen / (1024 * 1024);
		} else if ("G".equals(fileUnit.toUpperCase())) {
			fileSizeCom = (double) fileLen / (1024 * 1024 * 1024);
		}
		if (fileSizeCom > fileSize) {
			return false;
		}
		return true;
	}

	/**
	 * 上传文件
	 *
	 * @param file
	 * @return
	 */
	R uploadFile(MultipartFile file);

	R uploadFileByToken(MultipartFile file);

	R uploadBase64ByToken(String base64);

	/**
	 * 读取文件
	 *
	 * @param bucket   桶名称
	 * @param fileName 文件名称
	 * @param response 输出流
	 */
	void getFile(String bucket, String fileName, HttpServletResponse response);

	/**
	 * 删除文件
	 *
	 * @param id
	 * @return
	 */
	Boolean deleteFile(Long id);

	/**
	 * 获取外网访问地址
	 *
	 * @param bucket
	 * @param fileName
	 * @return
	 */
	String onlineFile(String bucket, String fileName);

}
