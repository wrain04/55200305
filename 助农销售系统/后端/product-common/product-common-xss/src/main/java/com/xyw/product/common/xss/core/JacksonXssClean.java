package com.xyw.product.common.xss.core;

import cn.hutool.core.util.ArrayUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.xyw.product.common.xss.config.ProductXssProperties;
import com.xyw.product.common.xss.utils.XssUtil;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class JacksonXssClean extends XssCleanDeserializerBase {

	private final ProductXssProperties properties;

	private final XssCleaner xssCleaner;

	@Override
	public String clean(String name, String text) throws IOException {
		if (XssHolder.isEnabled() && Objects.isNull(XssHolder.getXssCleanIgnore())) {
			String value = xssCleaner.clean(XssUtil.trim(text, properties.isTrimText()));
			log.debug("Json property value:{} cleaned up by mica-xss, current value is:{}.", text, value);
			return value;
		}
		else if (XssHolder.isEnabled() && Objects.nonNull(XssHolder.getXssCleanIgnore())) {
			XssCleanIgnore xssCleanIgnore = XssHolder.getXssCleanIgnore();
			if (ArrayUtil.contains(xssCleanIgnore.value(), name)) {
				return XssUtil.trim(text, properties.isTrimText());
			}

			String value = xssCleaner.clean(XssUtil.trim(text, properties.isTrimText()));
			log.debug("Json property value:{} cleaned up by mica-xss, current value is:{}.", text, value);
			return value;
		}
		else {
			return XssUtil.trim(text, properties.isTrimText());
		}
	}

}
