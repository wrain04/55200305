package com.xyw.product.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyw.product.admin.api.dto.SysLogDTO;
import com.xyw.product.admin.api.entity.SysLog;

import java.util.List;

public interface SysLogService extends IService<SysLog> {

	/**
	 * 分页查询日志
	 * @param page
	 * @param sysLog
	 * @return
	 */
	Page<SysLog> getLogByPage(Page page, SysLogDTO sysLog);

	/**
	 * 列表查询日志
	 * @param sysLog 查询条件
	 * @return List
	 */
	List<SysLog> getLogList(SysLogDTO sysLog);

}
