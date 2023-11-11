package com.xyw.product.common.security.annotation;

import com.xyw.product.common.security.component.ProductResourceServerAutoConfiguration;
import com.xyw.product.common.security.component.ProductResourceServerConfiguration;
import com.xyw.product.common.security.feign.ProductFeignClientConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ ProductResourceServerAutoConfiguration.class, ProductResourceServerConfiguration.class,
		ProductFeignClientConfiguration.class })
public @interface EnableProductResourceServer {

}
