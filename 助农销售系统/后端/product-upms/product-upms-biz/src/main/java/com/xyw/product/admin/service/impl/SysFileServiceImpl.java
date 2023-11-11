package com.xyw.product.admin.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.amazonaws.services.s3.model.S3Object;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.plugin.oss.OssProperties;
import com.pig4cloud.plugin.oss.service.OssTemplate;
import com.xyw.product.admin.api.entity.SysFile;
import com.xyw.product.admin.mapper.SysFileMapper;
import com.xyw.product.admin.service.SysFileService;
import com.xyw.product.admin.util.Base64ToMultipartFile;
import com.xyw.product.common.core.util.R;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {

	private static String FILE_TOKEN = null;
	private final OssProperties ossProperties;
	private final OssTemplate ossTemplate;

	public static File transferToFile(MultipartFile multipartFile) {
//        选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
		File file = null;
		try {
			String originalFilename = multipartFile.getOriginalFilename();
			String[] filename = originalFilename.split("\\.");
			file = File.createTempFile("temp" + filename[0], "." + filename[1]);
			multipartFile.transferTo(file);
			file.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	public R uploadFileByToken(MultipartFile file) {
		String fileName = IdUtil.simpleUUID() + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());
		HashMap<String, Object> map = new HashMap<>(2);
		if (FILE_TOKEN == null || "".equals(FILE_TOKEN)) {
			map.put("email", "2281015832@qq.com");
			map.put("password", "3485983aaa");
			HttpResponse execute = HttpUtil.createPost("https://imgkb.com/api/token").form(map).execute();
			JSONObject obj = JSONUtil.parseObj(execute.body());
			if (obj.get("code", Integer.class) != 200) {
				return R.failed("上传失败,管理员关闭了连接");
			}
			FILE_TOKEN = obj.get("data", JSONObject.class).get("token", String.class);
		}
		map.put("token", FILE_TOKEN);
		File tempFile = transferToFile(file);
		map.put("image", tempFile);
		HttpResponse execute = HttpUtil.createPost("https://imgkb.com/api/upload").form(map).execute();
		JSONObject obj = JSONUtil.parseObj(execute.body());
		if (obj.get("code", Integer.class) != 200) {
			return R.failed("上传失败:" + obj.get("msg"));
		}
		new Thread(() -> FileUtil.del(tempFile)).start();
		return R.ok(JSONUtil.parseObj(obj.get("data", JSONObject.class)));

	}

	public R uploadBase64ByToken(String base64) {
		final String[] base64Array = base64.split(",");
		String dataUir, data;
		if (base64Array.length > 1) {
			dataUir = base64Array[0];
			data = base64Array[1];
		} else {
			//根据你base64代表的具体文件构建
			dataUir = "data:image/jpg;base64";
			data = base64Array[0];
		}
		MultipartFile file = new Base64ToMultipartFile(data, dataUir);
		if (!SysFileService.checkFileSizeIsLimit(file.getSize(), 1, "M")) {
			return R.failed("文件大于1M,请重新上传");
		}
		return uploadFileByToken(file);
	}

	/**
	 * 上传文件
	 *
	 * @param file
	 * @return
	 */
	@Override
	public R uploadFile(MultipartFile file) {
		String fileName = IdUtil.simpleUUID() + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());
		Map<String, String> resultMap = new HashMap<>(4);
		resultMap.put("bucketName", ossProperties.getBucketName());
		resultMap.put("fileName", fileName);
		resultMap.put("url", String.format("/admin/sys-file/%s/%s", ossProperties.getBucketName(), fileName));
		resultMap.put("online", String.format("%s/%s/%s", ossProperties.getEndpoint(), ossProperties.getBucketName(), fileName));
		try {
			ossTemplate.putObject(ossProperties.getBucketName(), fileName, file.getContentType(), file.getInputStream());
			// 文件管理数据记录,收集管理追踪文件
			fileLog(file, fileName);
		} catch (Exception e) {
			log.error("上传失败", e);
			return R.failed(e.getLocalizedMessage());
		}
		return R.ok(resultMap);
	}

	/**
	 * 读取文件
	 *
	 * @param bucket
	 * @param fileName
	 * @param response
	 */
	@Override
	public void getFile(String bucket, String fileName, HttpServletResponse response) {
		try (S3Object s3Object = ossTemplate.getObject(bucket, fileName)) {
			response.setContentType("application/octet-stream; charset=UTF-8");
			IoUtil.copy(s3Object.getObjectContent(), response.getOutputStream());
		} catch (Exception e) {
			log.error("文件读取异常: {}", e.getLocalizedMessage());
		}
	}

	/**
	 * 删除文件
	 *
	 * @param id
	 * @return
	 */
	@Override
	@SneakyThrows
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteFile(Long id) {
		SysFile file = this.getById(id);
		ossTemplate.removeObject(ossProperties.getBucketName(), file.getFileName());
		return this.removeById(id);
	}

	/**
	 * 文件管理数据记录,收集管理追踪文件
	 *
	 * @param file     上传文件格式
	 * @param fileName 文件名
	 */
	private void fileLog(MultipartFile file, String fileName) {
		SysFile sysFile = new SysFile();
		sysFile.setFileName(fileName);
		sysFile.setOriginal(file.getOriginalFilename());
		sysFile.setFileSize(file.getSize());
		sysFile.setType(FileUtil.extName(file.getOriginalFilename()));
		sysFile.setBucketName(ossProperties.getBucketName());
		this.save(sysFile);
	}

	/**
	 * 默认获取文件的在线地址
	 *
	 * @param bucket
	 * @param fileName
	 * @return
	 */
	@Override
	public String onlineFile(String bucket, String fileName) {
		return ossTemplate.getObjectURL(bucket, fileName, Duration.of(7, ChronoUnit.DAYS));
	}

}
