package com.xyw.product.common.log.event;

import com.xyw.product.admin.api.entity.SysLog;
import org.springframework.context.ApplicationEvent;

public class SysLogEvent extends ApplicationEvent {

	public SysLogEvent(SysLog source) {
		super(source);
	}

}
