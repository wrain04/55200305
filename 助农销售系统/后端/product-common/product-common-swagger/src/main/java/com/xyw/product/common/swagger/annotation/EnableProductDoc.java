package com.xyw.product.common.swagger.annotation;

import com.xyw.product.common.swagger.config.SwaggerAutoConfiguration;
import com.xyw.product.common.swagger.support.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableConfigurationProperties(SwaggerProperties.class)
@Import({ SwaggerAutoConfiguration.class })
public @interface EnableProductDoc {

}
