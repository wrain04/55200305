package com.xyw.product.common.job.annotation;

import com.xyw.product.common.job.XxlJobAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ XxlJobAutoConfiguration.class })
public @interface EnableProductXxlJob {

}
