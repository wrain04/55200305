package com.xyw.product.admin.api.feign;

import com.xyw.product.common.core.constant.SecurityConstants;
import com.xyw.product.common.core.constant.ServiceNameConstants;
import com.xyw.product.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(contextId = "remoteFileService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteFileService {

	@GetMapping("/sys-file/online/{bucket}/{fileName}")
	R<String> onlineFile(@PathVariable("bucket") String bucket, @PathVariable("fileName") String fileName,
			@RequestHeader(SecurityConstants.FROM) String from);

	@PostMapping(value = "/sys-file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public R upload(@RequestPart("file") MultipartFile file);

}
