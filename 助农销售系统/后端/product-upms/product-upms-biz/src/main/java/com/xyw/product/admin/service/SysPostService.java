package com.xyw.product.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xyw.product.admin.api.entity.SysPost;
import com.xyw.product.admin.api.vo.PostExcelVO;
import com.xyw.product.common.core.util.R;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface SysPostService extends IService<SysPost> {

	/**
	 * 导入岗位
	 * @param excelVOList 岗位列表
	 * @param bindingResult 错误信息列表
	 * @return ok fail
	 */
	R importPost(List<PostExcelVO> excelVOList, BindingResult bindingResult);

	/**
	 * 导出excel 表格
	 * @return
	 */
	List<PostExcelVO> listPost();

}
